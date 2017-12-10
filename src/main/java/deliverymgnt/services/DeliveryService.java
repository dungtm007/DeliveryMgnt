package deliverymgnt.services;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.generic.GenericService;
import deliverymgnt.schedulers.DeliveryManagementScheduler;

public interface DeliveryService extends GenericService<Delivery> {
	List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus);
	List<Delivery> findByOrderId(int orderId);
	List<Delivery> findByStartTimeGreaterThanEqualAndStartTimeLessThanEqual(Date fromDate, Date toDate);
}
