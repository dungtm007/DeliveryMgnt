package deliverymgnt.repositories;

import java.util.List;

import deliverymgnt.domainclasses.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
}
