package deliverymgnt.domainclasses;

public enum OrderStatus {
	Entered,
	Processing,
	Packaging,
	Packaged,
	Delivering,
	Delivered,
	Finished // when all deliveries are finished (users have picked up all)
}
