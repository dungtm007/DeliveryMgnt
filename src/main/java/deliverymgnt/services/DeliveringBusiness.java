package deliverymgnt.services;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.deliveryhandlers.DeliveryHandler;
import deliverymgnt.factories.DeliveryHandlerFactory;

public class DeliveringBusiness {

	public static void deliver(Delivery delivery, DeliveryService deliveryService, OrderService orderService) {
		
		DeliveryMethod method = delivery.getDeliveryMethod();
		DeliveryHandler hdlr = DeliveryHandlerFactory.GetDeliveryHandler(method);
		hdlr.deliver(delivery, deliveryService, orderService);
	}
}
