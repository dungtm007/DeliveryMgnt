package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Delivery;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
	
	
}
