package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryStatus;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
	List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus);
	List<Delivery> findByOrderId(int orderId);
	List<Delivery> findByStartTimeGreaterThanEqualAndStartTimeLessThanEqual(Date fromDate, Date toDate);
	List<Delivery> findByStartTimeGreaterThanEqualAndStartTimeLessThanEqualAndDeliveryMethod(Date fromDate, Date toDate, DeliveryMethod method);
}
