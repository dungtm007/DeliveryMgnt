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
import deliverymgnt.factories.CourierSystemFactory;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;


// CAN BE CONSIDERED TO STATIC class & methods SO AT ANY TIME,
// DELIVERY CAN CALL calculateDeliveryCost
// AND BACKGROUND JOB CAN CALL deliver and arrive

@Component
public class CourierDeliveryHandler implements DeliveryHandler {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private OrderService orderService;
	
	public CourierDeliveryHandler() {
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void deliver(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {

		// set start time & estimate arrival time
		double distance = delivery.getDistance();
		int seconds = (int)Math.round(distance * 5); // 5 seconds per mile for drone
		int buffer = 15; // seconds
		
		Date start = new Date();
		Date estimatedArrival = new Date(start.getYear(), start.getMonth(), start.getDate(), 
				start.getHours(), start.getMinutes(), start.getSeconds() +  seconds + buffer); 
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
		
		double travel = seconds / 5; // miles (5 seconds per miles)  
		double distance = delivery.getDistance();
		double remainingDistance = distance - travel;
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
		
		// If this is Locker Delivery
		// Run Locker allocation business
		if (delivery.getDeliveryType() == DeliveryType.LockerPickupDelivery) {
			// (UPD) ...
			delivery.setDeliveryStatus(DeliveryStatus.Delivered);
		}
		
		// Update cost
		double cost = calculateDeliveryCost(delivery);
		delivery.setDeliveryCost(cost);
		
		deliveryService.save(delivery);
	}

	@Override
	public double calculateDeliveryCost(Delivery delivery) {

		double totalShippingWeight = 0.0;
		
		for(Package p : delivery.getPackages()) {
			double shippingWeight = p.calculateShippingWeight();
			totalShippingWeight += shippingWeight;
		}
		
		// Ask the CourierSystem and pass the service (dollars per pound), totalChargedWeight and distance
		double totalPrice = CourierSystem.calculateDeliveryCost(delivery.getCourieService(), totalShippingWeight, delivery.getDistance()); 
		
		return totalPrice;
	}
}
