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
	
	public void deliver(List<Package> packages) {
		
		// Select a drone, and create a reserve
		Drone drone = new Drone(); 
		Delivery delivery = packages.get(0).getDelivery();
		
		// A drone has velocity 10 mph
		ReservedDrone reservedDrone = new ReservedDrone(drone, packages.get(0).getDelivery(), new Date(), new Date(), ReservedDroneStatus.InUse);

		// Drone start (update Drone status inside)
		
		// Update Delivery status
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deliver(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {
		
		// set start time & estimate arrival time
		double distance = delivery.getDistance();
		int seconds = (int)Math.round(distance * 8); // 8 seconds per mile for drone
		int buffer = 15; // seconds
		
		Date start = new Date();
		Date estimatedArrival = new Date(start.getYear(), start.getMonth(), start.getDate(), 
				start.getHours(), start.getMinutes(), start.getSeconds() + seconds + buffer); 
		delivery.setStartTime(start);
		delivery.setEstimatedArrivalTime(estimatedArrival);
	
		// Drone start (update Drone status inside)
		// (Will be available in next release)
		
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
		
		double travel = seconds / 8; // miles (8 seconds per mile)  
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
		
		// set order status is Finished (if all deliveries are finished)
		// ...
		
		// Update cost
		double cost = calculateDeliveryCost(delivery);
		delivery.setDeliveryCost(cost);

		deliveryService.save(delivery);
	}

	@Override
	public double calculateDeliveryCost(Delivery delivery) {
		
		return 0.5; // assumption Drone delivery has a fixed cost
	}
	
}
