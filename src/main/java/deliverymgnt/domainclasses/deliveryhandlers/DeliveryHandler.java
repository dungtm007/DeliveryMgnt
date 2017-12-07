package deliverymgnt.domainclasses.deliveryhandlers;

import java.util.List;
import java.util.Set;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.Package;

public interface DeliveryHandler {
	
	double calculateDeliveryCost(List<Package> packages, double distance);
	double calculateDeliveryCost(Delivery delivery, double distance);
	void deliver(List<Package> packages);
	void arrive(List<Package> packages);
	void deliver(Delivery delivery);
	void arrive(Delivery delivery);
}
