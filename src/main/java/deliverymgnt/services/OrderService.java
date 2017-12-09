package deliverymgnt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.generic.GenericService;

@Service
public interface OrderService extends GenericService<Order> {
	List<Order> findByOrderStatus(OrderStatus orderStatus);
}
