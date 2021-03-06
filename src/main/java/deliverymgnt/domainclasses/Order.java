package deliverymgnt.domainclasses;

import java.text.SimpleDateFormat;
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
import javax.persistence.FetchType;
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
	
	@Column(name = "delivery_type")
	private DeliveryType deliveryType; // what really happens
	
	@Column(name="delivery_deadline")
	private Date deliveryDeadline;
	
	@Column(name="order_status")
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;
	
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER)
	private Set<OrderItem> orderItems;
	
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER)
	private Set<Delivery> deliveries;
	
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
	
	public Order(Date orderDate, DeliveryType deliveryType, 
			Date deliveryDeadline, Address deliveryAddress,
			OrderStatus orderStatus, Customer customer) {
		
		this.orderDate = orderDate;
		this.deliveryType = deliveryType;
		this.deliveryDeadline = deliveryDeadline;
		this.deliveryAddress = deliveryAddress;
		this.orderStatus = orderStatus;
		this.customer = customer;
		this.orderItems = new HashSet<>();
		this.deliveries = new HashSet<>();
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public String getOrderDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(orderDate);
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDeadline() {
		return deliveryDeadline;
	}
	
	public String getDeliveryDeadlineFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(deliveryDeadline);
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
	
	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public double getTotalPrice() {
		return calculateTotalPrice();
	}

	@Override
	public String toString() {
		
		String orderItemsString = "[";
		for(OrderItem oi : orderItems) {
			orderItemsString += oi.toString() + "| ";
		}
		orderItemsString += "]";
		
		return "Order [id=" + id + ", orderDate=" + orderDate
				+ ", deliveryDeadline=" + deliveryDeadline 
				+ ", orderStatus=" + orderStatus 
				+ ", customer=" + customer
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
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateVolume();
		}
		return total;
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
	
	public double calculateTotalShippingWeight() {
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateShippingWeight();
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
	
	public String getDeliveryTypeShortDesc() {
		if (deliveryType == DeliveryType.HomeDelivery) {
			return "Home";
		} else {
			return "Locker Pickup";
		}
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
	
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public String getOrderNo() {
		return "ODR" + String.format("%05d", id);
	}
	
	// For binding data to front-end
	public String getPackagesShortSummary() {
		String summary = "";
		int count = 0;
		for(Delivery d : deliveries) {
			for(Package p : d.getPackages()) {
				count++;
				summary += "Package " + count + ": " + d.getDeliveryStatus() + "\n";
			}
		}
		summary = Integer.toString(count) + " package(s)" + "\n" + "-----" + "\n" + summary;
		return summary;
	}
	
	// For binding data to front-end
	public String getDeliveryMethods() {
		List<String> methodsList = new ArrayList<>();
		for(Delivery d : deliveries) {
			methodsList.add(d.getDeliveryMethod().toString());
		}
		String methods = "";
		if (methodsList.contains(DeliveryMethod.Courier.toString())) {
			methods += "Courier" + "\n";
		}
		if (methodsList.contains(DeliveryMethod.Drone.toString())) {
			methods += "Drone" + "\n";
		}
		return methods;
	}
	
	// For binding data to front-end
	public String getDistanceFormat() {
		double distance = 0.0;
		for(Delivery d : getDeliveries()) {
			distance = d.getDistance();
			break;
		}
		return distance + " mile(s)";	
	}
	
	// For binding data to front-end
	public String getShippingWeight() {
		return String.format("%.2f", calculateTotalShippingWeight()) + " lb(s)";
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
	
	public List<OrderItem> getSortedOrderItemsByShippingWeight() {
		List<OrderItem> orderItemsList = new ArrayList<>(orderItems);
		orderItemsList.sort(new Comparator<OrderItem>() {
			@Override
			public int compare(OrderItem o1, OrderItem o2) {
				double diff = o1.calculateShippingWeight() - o2.calculateShippingWeight();
				return diff < 0 ? -1 : (diff == 0 ? 0 : 1);
			}
		});
		return orderItemsList;
	}
}
