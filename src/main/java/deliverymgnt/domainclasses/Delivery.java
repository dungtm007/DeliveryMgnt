package deliverymgnt.domainclasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import deliverymgnt.domainclasses.deliveryhandlers.DeliveryHandler;

@Entity
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "delivery_status")
	private DeliveryStatus deliveryStatus;
	
	@Column(name = "delivery_address")
	private String deliveryAddress;
	
	@Column(name = "delivery_type")
	private DeliveryType deliveryType;
	
	@Column(name = "delivery_distance")
	private double distance;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "estimated_arrival_time")
	private Date estimatedArrivalTime; // will set by delivery handler
	
	private transient DeliveryHandler deliveryHandler;

	// Relationships
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@OneToMany(mappedBy = "delivery")
	private Set<Package> packages;
	
	public Delivery(DeliveryStatus deliveryStatus, String deliveryAddress, double distance,
			DeliveryType deliveryType, Order order, Set<Package> packages) {
		this.deliveryStatus = deliveryStatus;
		this.deliveryAddress = deliveryAddress;
		this.distance = distance;
		this.deliveryType = deliveryType;
		this.order = order;
		this.packages = packages;
	}
	
	public Date getStartTime() {
		return startTime;
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
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public DeliveryHandler getDeliveryHandler() {
		return deliveryHandler;
	}
	public void setDeliveryHandler(DeliveryHandler deliveryHandler) {
		this.deliveryHandler = deliveryHandler;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
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

	public double calculateDeliveryCost() {
		return deliveryHandler.calculateDeliveryCost(new ArrayList<>(packages), distance);
	}
	
}