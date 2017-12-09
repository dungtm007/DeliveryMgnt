package deliverymgnt.services;

import java.util.List;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.generic.GenericService;

public interface DeliveryService extends GenericService<Delivery> {
	List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus);
}
