package deliverymgnt.domainclasses;

public enum OrderStatus {
	Entered,
	Processing,
	Processed,
	Packaging,
	Packaged,
	Delivering,
	Delivered,
	Finished // when all deliveries are finished
}
