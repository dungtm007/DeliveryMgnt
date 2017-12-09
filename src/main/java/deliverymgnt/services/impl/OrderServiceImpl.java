package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.repositories.OrderItemRepository;
import deliverymgnt.repositories.OrderRepository;
import deliverymgnt.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public Order save(Order entity) {
		
		Order savedOrder = orderRepository.save(entity);

		// Save order items
		for(OrderItem oi : savedOrder.getOrderItems()) {
			orderItemRepository.save(oi);
		}
		
		return savedOrder;
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

	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		return orderRepository.findByOrderStatus(orderStatus);
	}

}
