package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Locker;
import deliverymgnt.repositories.LockerRepository;
import deliverymgnt.services.LockerService;

@Service
public class LockerServiceImpl implements LockerService {
	@Autowired
	private LockerRepository lockerRepository;
	
	@Override
	public Locker save(Locker entity) {
		// TODO Auto-generated method stub
		return lockerRepository.save(entity);
	}

	@Override
	public Locker update(Locker entity) {
		// TODO Auto-generated method stub
		return lockerRepository.save(entity);
	}

	@Override
	public void delete(Locker entity) {
		// TODO Auto-generated method stub
		lockerRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		lockerRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Locker> entities) {
		// TODO Auto-generated method stub
		lockerRepository.deleteInBatch(entities);
	}

	@Override
	public Locker find(int id) {
		// TODO Auto-generated method stub
		return lockerRepository.findOne(id);
	}

	@Override
	public List<Locker> findAll() {
		// TODO Auto-generated method stub
		return lockerRepository.findAll();
	}

}
