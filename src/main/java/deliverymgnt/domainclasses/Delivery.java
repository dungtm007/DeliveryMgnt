package deliverymgnt.domainclasses;

import java.text.SimpleDateFormat;
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
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "delivery_status")
	private DeliveryStatus deliveryStatus;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "delivery_addr_address")),
		@AttributeOverride(name = "city", column = @Column(name = "delivery_addr_city")),
		@AttributeOverride(name = "state", column = @Column(name = "delivery_addr_state")),                       
        @AttributeOverride(name = "zip", column = @Column(name = "delivery_addr_zip"))
    })
	private Address deliveryAddress;
	
	@Column(name = "delivery_type")
	private DeliveryType deliveryType;
	
	@Column(name = "delivery_method")
	private DeliveryMethod deliveryMethod;
	
	@Column(name = "distance")
	private double distance;

	@Column(name = "remaining_distance")
	private double remainingDistance;
	
	@Column(name = "delivery_deadline")
	private Date deliveryDeadline;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "estimated_arrival_time")
	private Date estimatedArrivalTime; // will set by delivery handler
	
	@Column(name = "actual_arrival_time")
	private Date actualArrivalTime; // will set by delivery handler

	@Column(name = "delivery_cost")
	private double deliveryCost;
	
	@Column(name = "courier_service")
	private CourierService courierService;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "locker_id")
	private Locker locker;

	@OneToMany(mappedBy = "delivery", fetch=FetchType.EAGER)
	private Set<Package> packages;
	
	// does not allow to create a Delivery outside of Order
	Delivery() {
		this.packages = new HashSet<>();
	}
	
	public Delivery(Order order) {
		this.order = order;
		this.packages = new HashSet<>();
	}
	
	public Delivery(Order order, Set<Package> packages, DeliveryStatus deliveryStatus, 
			Address deliveryAddress, Date deliveryDeadline, 
			double distance, double remainingDistance, DeliveryType deliveryType) {
		
		this.order = order;
		this.packages = packages;
		this.deliveryStatus = deliveryStatus;
		this.deliveryAddress = deliveryAddress;
		this.deliveryDeadline = deliveryDeadline;
		this.distance = distance;
		this.remainingDistance = remainingDistance;
		this.deliveryType = deliveryType;
		this.packages = new HashSet<>();
	}
	
	public Delivery(Order order, DeliveryType deliveryType, DeliveryMethod deliveryMethod, 
			DeliveryStatus deliveryStatus, Address deliveryAddress, Date deliveryDeadline, 
			double distance, double remainingDistance) {
		
		this.order = order;
		this.deliveryType = deliveryType;
		this.deliveryMethod = deliveryMethod;
		this.deliveryStatus = deliveryStatus;
		this.deliveryAddress = deliveryAddress;
		this.deliveryDeadline = deliveryDeadline;
		this.distance = distance;
		this.remainingDistance = remainingDistance;
		this.packages = new HashSet<>();
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public String getStartTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(startTime);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}
	
	public void setEstimatedArrivalTime(Date estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}
	
	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public CourierService getCourieService() {
		return courierService;
	}

	public void setCourierService(CourierService courierService) {
		this.courierService = courierService;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}
	
	public Set<Package> getPackages() {
		return packages;
	}
	
	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}
	
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDeliveryCost() {
		return deliveryCost;
	}
	
	public String getDeliveryCostFormat() {
		return String.format("$ %.2f", deliveryCost);
	}

	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}
	
	public Date getActualArrivalTime() {
		return actualArrivalTime;
	}
	
	public String getActualArrivalTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(actualArrivalTime);
	}

	public void setActualArrivalTime(Date actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
	
	public double getRemainingDistance() {
		return remainingDistance;
	}

	public void setRemainingDistance(double remainingDistance) {
		this.remainingDistance = remainingDistance;
	}
	
	public String getOrderNo() {
		return order.getOrderNo();
	}
	
	public Date getOrderDate() {
		return order.getOrderDate();
	}
	
	public String getOrderDateFormat() {
		return order.getOrderDateFormat();
	}
	
	public String getDeliveryNo() {
		return "DLV" + String.format("%05d", id);
	}
	
	public void addPackage(Package pkg) {
		packages.add(pkg);
		pkg.setDelivery(this);
	}
	
	public void addPackages(List<Package> packages) {
		for(Package pkg : packages) {
			addPackage(pkg);
		}
	}
}
