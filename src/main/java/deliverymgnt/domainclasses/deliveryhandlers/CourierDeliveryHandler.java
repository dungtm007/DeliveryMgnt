package deliverymgnt.domainclasses.deliveryhandlers;

import java.util.List;
import java.util.Set;

import deliverymgnt.domainclasses.CourierSystem;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryService;
import deliverymgnt.domainclasses.Package;


// CAN BE CONSIDERED TO STATIC class & methods SO AT ANY TIME,
// DELIVERY CAN CALL calculateDeliveryCost
// AND BACKGROUND JOB CAN CALL deliver and arrive

public class CourierDeliveryHandler implements DeliveryHandler {

	private DeliveryService deliveryService;
	
	public CourierDeliveryHandler(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
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
		double totalPrice = CourierSystem.caculateDeliveryCost(deliveryService, totalChargedWeight, distance); 
		
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
