package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.Warehouse;
import deliverymgnt.repositories.CustomerRepository;
import deliverymgnt.repositories.WarehouseRepository;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Override
	public Warehouse save(Warehouse entity) {
		return warehouseRepository.save(entity);
	}

	@Override
	public Warehouse update(Warehouse entity) {
		return warehouseRepository.save(entity);
	}

	@Override
	public void delete(Warehouse entity) {
		warehouseRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		warehouseRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Warehouse> entities) {
		warehouseRepository.deleteInBatch(entities);
	}

	@Override
	public Warehouse find(int id) {
		return warehouseRepository.findOne(id);
	}

	@Override
	public List<Warehouse> findAll() {
		return warehouseRepository.findAll();
	}

}
