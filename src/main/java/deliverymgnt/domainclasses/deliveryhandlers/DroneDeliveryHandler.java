package deliverymgnt.domainclasses.deliveryhandlers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.Drone;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.ReservedDrone;
import deliverymgnt.domainclasses.ReservedDroneStatus;

//CAN BE CONSIDERED TO STATIC class & methods SO AT ANY TIME,
//DELIVERY CAN CALL calculateDeliveryCost
//AND BACKGROUND JOB CAN CALL deliver and arrive

// ASSUMPTION: Drone is always available
public class DroneDeliveryHandler implements DeliveryHandler {

	// private Drone drone;
	// maximum weight: 5 lbs
	// maximum package: 2 packages
	// maximum distance: 30 miles
	
	// deliver time: within 1 day 
	// (maximum 2 days for Express)
	// (maximum 5 days for Normal)
	
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

	@Override
	public void deliver(Delivery delivery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrive(Delivery delivery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double calculateDeliveryCost(Delivery delivery, double distance) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
