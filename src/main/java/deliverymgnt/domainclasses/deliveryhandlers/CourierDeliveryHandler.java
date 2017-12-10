package deliverymgnt.domainclasses.deliveryhandlers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.CourierSystem;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.CourierService;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;


// CAN BE CONSIDERED TO STATIC class & methods SO AT ANY TIME,
// DELIVERY CAN CALL calculateDeliveryCost
// AND BACKGROUND JOB CAN CALL deliver and arrive

@Component
public class CourierDeliveryHandler implements DeliveryHandler {

	private CourierService courierService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	public CourierDeliveryHandler() {
	}
	
	public CourierDeliveryHandler(CourierService courierService) {
		this.courierService = courierService;
	}
	
	@Override
	public double calculateDeliveryCost(List<Package> packages, double distance) {
		
		double totalChargedWeight = 0.0;
		
		for(Package p : packages) {
			double volumetricWeight = p.calculateVolumetricWeight();
			double actualWeight = p.calculateActualWeight();
			double chargedWeight = Math.max(volumetricWeight, actualWeight);
			totalChargedWeight += chargedWeight;
		}
		
		// ask the CourierSystem and pass the service (dollars per pound), totalChargedWeight and distance
		double totalPrice = CourierSystem.caculateDeliveryCost(courierService, totalChargedWeight, distance); 
		
		return totalPrice;
	}

	@Override
	public void deliver(List<Package> packages) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void arrive(List<Package> packages) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deliver(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {

		// set start time & estimate arrival time
		double distance = delivery.getDistance();
		double seconds = distance * 12; // 12 seconds per mile for drone
		
		Date start = new Date();
		Date estimatedArrival = new Date(start.getYear(), start.getMonth(), start.getDate(), 
				start.getHours(), start.getMinutes(), start.getSeconds() + (int)Math.round(seconds)); 
		delivery.setStartTime(start);
		delivery.setEstimatedArrivalTime(estimatedArrival);
	
		// set delivery status is Delivering
		delivery.setDeliveryStatus(DeliveryStatus.Delivering);
		
		// also, set order status is Delivering
		Order order = delivery.getOrder();
		order.setOrderStatus(OrderStatus.Delivering);
		
		deliveryService.save(delivery);
		orderService.save(order);
	}

	@Override
	public void track(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {

		Date startTime = delivery.getStartTime();
		Date curTime = new Date();
		
		long diff = curTime.getTime() - startTime.getTime();
		int seconds = (int)(diff / 1000);
		
		double travel = seconds / 12; // miles (12 seconds per miles)  
		double remainingDistance = delivery.getRemainingDistance();
		remainingDistance -= travel;
		if (remainingDistance <= 0) {
			arrive(delivery, deliveryService, orderService);
		}
		else {
			delivery.setRemainingDistance(remainingDistance);
			deliveryService.save(delivery);
		}
	}
	
	@Override
	public void arrive(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {
		
		delivery.setRemainingDistance(0);
		delivery.setActualArrivalTime(new Date());
		delivery.setDeliveryStatus(DeliveryStatus.Finished);
		
		// set order status is Finished (if all deliveries are finished)
		// ...
		
		// If this is Locker Delivery
		// Run Locker allocation business
		if (delivery.getDeliveryType() == DeliveryType.LockerPickupDelivery) {
			
		}
		
		deliveryService.save(delivery);
	}

	@Override
	public double calculateDeliveryCost(Delivery delivery, double distance) {
		// TODO Auto-generated method stub
		return 0;
	}

}
