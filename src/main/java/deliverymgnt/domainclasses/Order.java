package deliverymgnt.domainclasses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="delivery_option")
	private DeliveryOption deliveryOption; // what customer expects
	
	@Column(name="delivery_deadline")
	private Date deliveryDeadline;
	
	@Column(name="order_status")
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy="order")
	private Set<OrderItem> orderItems;
	
	@OneToMany(mappedBy="order")
	private Set<Delivery> deliveries;
	
	@Column(name = "delivery_type")
	private DeliveryType deliveryType; // what really happens
	
//	@Column(name = "delivery_address")
//	private String deliveryAddress; // the home address / someone's address or locker's location
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "delivery_addr_address")),
		@AttributeOverride(name = "city", column = @Column(name = "delivery_addr_city")),
		@AttributeOverride(name = "state", column = @Column(name = "delivery_addr_state")),                       
        @AttributeOverride(name = "zip", column = @Column(name = "delivery_addr_zip"))
    })
	private Address deliveryAddress;
	
	public Order() {
		this.orderItems = new HashSet<>();
		this.deliveries = new HashSet<>();
	}
	
	public Order(Date orderDate, DeliveryOption deliveryOption, Date deliveryDeadline, 
			DeliveryType deliveryType, Address deliveryAddress,
			OrderStatus orderStatus, Customer customer) {
		
		this.orderDate = orderDate;
		this.deliveryOption = deliveryOption;
		this.deliveryDeadline = deliveryDeadline;
		this.deliveryType = deliveryType;
		this.deliveryAddress = deliveryAddress;
		this.orderStatus = orderStatus;
		this.customer = customer;
		this.orderItems = new HashSet<>();
		this.deliveries = new HashSet<>();
	}
	
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public DeliveryOption getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(DeliveryOption deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public Date getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(Date deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	@Override
	public String toString() {
		
		String orderItemsString = "[";
		for(OrderItem oi : orderItems) {
			orderItemsString += oi.toString() + "| ";
		}
		orderItemsString += "]";
		
		return "Order [id=" + id + ", orderDate=" + orderDate + ", deliveryOption=" + deliveryOption
				+ ", deliveryDeadline=" + deliveryDeadline + ", orderStatus=" + orderStatus + ", customer=" + customer
				+ ", orderItems=" + orderItemsString + "]";
	}

	public double calculateTotalActualWeight() {
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateActualWeight();
		}
		return total;
	}
	
	public double calculateTotalVolume() {
		return 0.0;
	}
	
	public double calculateTotalPrice() {
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculatePrice();
		}
		return total;
	}
	
	public double calculateTotalVolumetricWeight() {
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateVolumetricWeight();
		}
		return total;
	}
	
	public Set<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Set<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public boolean hasProduct(Product product) {
		for(OrderItem oi : orderItems) {
			if (oi.getProduct().equals(product)) {
				return true;
			}
				
		}
		return false;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
	
	public List<OrderItem> getSortedOrderItemsByVolumetricWeight() {
		List<OrderItem> orderItemsList = new ArrayList<>(orderItems);
		orderItemsList.sort(new Comparator<OrderItem>() {
			@Override
			public int compare(OrderItem o1, OrderItem o2) {
				double diff = o1.calculateVolumetricWeight() - o2.calculateVolumetricWeight();
				return diff < 0 ? -1 : (diff == 0.0 ? 0 : 1);
			}
		});
		return orderItemsList;
	}
	
	public List<OrderItem> getSortedOrderItemsByActualWeight() {
		List<OrderItem> orderItemsList = new ArrayList<>(orderItems);
		orderItemsList.sort(new Comparator<OrderItem>() {
			@Override
			public int compare(OrderItem o1, OrderItem o2) {
				double diff = o1.calculateActualWeight() - o2.calculateActualWeight();
				return diff < 0 ? -1 : (diff == 0 ? 0 : 1);
			}
		});
		return orderItemsList;
	}
}
