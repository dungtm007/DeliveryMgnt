package deliverymgnt.domainclasses.deliveryhandlers;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;

public interface DeliveryHandler {
	
	double calculateDeliveryCost(Delivery delivery);
	void deliver(Delivery delivery, DeliveryService deliveryService, OrderService orderService);
	void track(Delivery delivery, DeliveryService deliveryService, OrderService orderService);
	void arrive(Delivery delivery, DeliveryService deliveryService, OrderService orderService);
}
