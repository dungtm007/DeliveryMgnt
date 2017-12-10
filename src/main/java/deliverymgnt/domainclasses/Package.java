package deliverymgnt.domainclasses;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "packages")
public class Package {
	
	// Aggregation of order items
	// weak: does not need to create orderItem
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "size")
	private PackageSize size;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "packages_order_items", 
		joinColumns = @JoinColumn(name = "package_id"), 
		inverseJoinColumns = @JoinColumn(name = "order_item_id"))
	private Set<OrderItem> orderItems;

	// there should be an attribute Tracking ID
	
	@ManyToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	Package() {
		this.orderItems = new HashSet<>();
	}
	
	public Package(PackageSize size, Order order) {
		
		this.size = size;
		this.order = order;
		this.orderItems = new HashSet<>();
	}
	
	public Package(Order order) {
		
		this.order = order;
		this.orderItems = new HashSet<>();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PackageSize getSize() {
		return size;
	}

	public void setSize(PackageSize size) {
		this.size = size;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
	public double calculateVolume() {
		
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateVolume();
		}
		return total;
	}
	
	public double calculateActualWeight() {
		
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateActualWeight();
		}
		return total;
	}
	
	public double calculateVolumetricWeight() {
		
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateVolumetricWeight();
		}
		return total;
	}
	
	public double calculateShippingWeight() {
		
		double total = 0.0;
		for(OrderItem oi : orderItems) {
			total += oi.calculateShippingWeight();
		}
		return total;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
}
