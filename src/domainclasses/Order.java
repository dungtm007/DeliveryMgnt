package domainclasses;

import java.util.Date;
import java.util.Set;

public class Order {

	private int id;
	private Date orderDate;
	private Date deliveryDeadline;
	private OrderStatus orderStatus;
	
	private Set<OrderItem> _orderItems;
	
	
}
