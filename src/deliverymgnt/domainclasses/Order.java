package deliverymgnt.domainclasses;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order")
public class Order {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="delivery_option")
	private DeliveryOption deliveryOption;
	
	@Column(name="delivery_deadline")
	private Date deliveryDeadline;
	
	@Column(name="order_status")
	private OrderStatus orderStatus;
	
	
	private Customer customer;
	private Set<OrderItem> orderItems;
	
	
}
