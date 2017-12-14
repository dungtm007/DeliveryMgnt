package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Drone;
import deliverymgnt.repositories.DroneRepository;
import deliverymgnt.services.DroneService;

@Service
public class DroneServiceImpl implements DroneService {
	@Autowired
	private DroneRepository droneRepository;
	
	@Override
	public Drone save(Drone entity) {
		// TODO Auto-generated method stub
		List<Drone> list = droneRepository.findAll();
		//entity.setId(list.get(list.size() - 1).getId() + 1);
		return droneRepository.save(entity);
	}

	@Override
	public Drone update(Drone entity) {
		// TODO Auto-generated method stub
		return droneRepository.save(entity);
	}

	@Override
	public void delete(Drone entity) {
		// TODO Auto-generated method stub
		droneRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		droneRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Drone> entities) {
		// TODO Auto-generated method stub
		droneRepository.deleteInBatch(entities);
	}

	@Override
	public Drone find(int id) {
		// TODO Auto-generated method stub
		return droneRepository.findOne(id);
	}

	@Override
	public List<Drone> findAll() {
		// TODO Auto-generated method stub
		return droneRepository.findAll();
	}

}
