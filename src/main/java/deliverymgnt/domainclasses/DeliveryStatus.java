package deliverymgnt.domainclasses;

public enum DeliveryStatus {
	Entered,
	Delivering,
	Delivered,
	Finished // after customer pickup packages (case of picking up at locker) 
	// assumes that all packages of the same delivery comes at the same time
}
