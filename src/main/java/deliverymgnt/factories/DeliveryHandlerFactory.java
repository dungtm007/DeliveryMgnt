package deliverymgnt.factories;

import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.deliveryhandlers.CourierDeliveryHandler;
import deliverymgnt.domainclasses.deliveryhandlers.DeliveryHandler;
import deliverymgnt.domainclasses.deliveryhandlers.DroneDeliveryHandler;

@Component
public class DeliveryHandlerFactory {

	private static CourierDeliveryHandler courierDeliveryHdlr = new CourierDeliveryHandler();
	private static DroneDeliveryHandler droneDeliveryHdlr = new DroneDeliveryHandler();
	
	public static DeliveryHandler GetDeliveryHandler(DeliveryMethod deliveryMethod) {
		if (deliveryMethod == DeliveryMethod.Courier) {
			return courierDeliveryHdlr;
		} else {
			return droneDeliveryHdlr;
		}
	}
}
