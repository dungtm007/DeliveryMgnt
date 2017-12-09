package deliverymgnt.repositories;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByOrderStatus(OrderStatus orderStatus);
}
