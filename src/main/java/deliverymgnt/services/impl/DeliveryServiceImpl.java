package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.repositories.DeliveryRepository;
import deliverymgnt.repositories.OrderRepository;
import deliverymgnt.repositories.PackageRepository;
import deliverymgnt.services.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private PackageRepository packageRepository;
	
	@Override
	public Delivery save(Delivery entity) {
		return saveDeliveryAndPackages(entity);
	}

	@Override
	public Delivery update(Delivery entity) {
		//return deliveryRepository.save(entity);
		return saveDeliveryAndPackages(entity);
	}
	
	private Delivery saveDeliveryAndPackages(Delivery delivery) {
		Delivery savedDelivery = deliveryRepository.save(delivery);
		// Save packages
		for (Package p : savedDelivery.getPackages()) {
			packageRepository.save(p);
		}
		return savedDelivery;
	}

	@Override
	public void delete(Delivery entity) {
		deliveryRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		deliveryRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Delivery> entities) {
		deliveryRepository.deleteInBatch(entities);
	}

	@Override
	public Delivery find(int id) {
		return deliveryRepository.findOne(id);
	}

	@Override
	public List<Delivery> findAll() {
		return deliveryRepository.findAll();
	}

	@Override
	public List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus) {
		return deliveryRepository.findByDeliveryStatus(deliveryStatus);
	}

}
