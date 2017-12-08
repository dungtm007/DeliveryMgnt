package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.repositories.OrderRepository;
import deliverymgnt.services.OrderService;

public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order save(Order entity) {
		return orderRepository.save(entity);
	}

	@Override
	public Order update(Order entity) {
		return orderRepository.save(entity);
	}

	@Override
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		orderRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Order> entities) {
		orderRepository.deleteInBatch(entities);
	}

	@Override
	public Order find(int id) {
		return orderRepository.findOne(id);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

}
