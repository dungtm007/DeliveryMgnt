package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Warehouse;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
	
	
}
