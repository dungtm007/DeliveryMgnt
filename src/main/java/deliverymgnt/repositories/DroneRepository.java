package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Drone;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {
	
}
