package deliverymgnt.factories;

import deliverymgnt.domainclasses.CourierSystem;

public class CourierSystemFactory {

	private static CourierSystem courierSystem = new CourierSystem();
	
	public static CourierSystem getCourierSystem() {
		return courierSystem;
	}
}
