package deliverymgnt.domainclasses.deliveryhandlers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Drone;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.ReservedDrone;
import deliverymgnt.domainclasses.ReservedDroneStatus;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;

//CAN BE CONSIDERED TO STATIC class & methods SO AT ANY TIME,
//DELIVERY CAN CALL calculateDeliveryCost
//AND BACKGROUND JOB CAN CALL deliver and arrive

// ASSUMPTION: Drone is always available
@Component
public class DroneDeliveryHandler implements DeliveryHandler {

	// private Drone drone;
	// maximum weight: 5 lbs
	// maximum package: 2 SMALL packages
	// maximum distance: 30 miles
	
	// deliver time: within 1 day 
	// (maximum 2 days for Express)
	// (maximum 5 days for Normal)
	
	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public double calculateDeliveryCost(List<Package> packages, double distance) {
		// validate packages and distance 
		// before return fixed cost
		
		return 0.5; // assumption Drone delivery has a fixed cost
	}

	@Override
	public void deliver(List<Package> packages) {
		
		// Select a drone, and create a reserve
		Drone drone = new Drone(); 
		Delivery delivery = packages.get(0).getDelivery();
		
		// A drone has velocity 10 mph
		ReservedDrone reservedDrone = new ReservedDrone(drone, packages.get(0).getDelivery(), new Date(), new Date(), ReservedDroneStatus.InUse);

		// Drone start (update Drone status inside)
		
		// Update Delivery status
		
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
		double seconds = distance * 7; // 7 seconds per mile for drone
		
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
	public void arrive(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {

		delivery.setRemainingDistance(0);
		delivery.setActualArrivalTime(new Date());
		delivery.setDeliveryStatus(DeliveryStatus.Finished);
		
		// set order status is Finished (if all deliveries are finished)
		// ...
		
		
		deliveryService.save(delivery);
	}

	@Override
	public double calculateDeliveryCost(Delivery delivery, double distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void track(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {
		// TODO Auto-generated method stub
		
	}
	
	
}
