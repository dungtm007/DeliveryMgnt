package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Locker;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Integer> {
	
}
