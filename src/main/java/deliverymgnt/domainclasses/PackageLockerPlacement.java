package deliverymgnt.domainclasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="package_locker_placements")
public class PackageLockerPlacement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="delivery_time")
	private Date deliveryTime; // is the time the package is delivered (estimated)
	
	@Column(name="pickup_time")
	private Date pickupTime; // is the pickup time, or expired time, then it will be collected to return (assumption: 2 day in reality)
	
	@Column(name="open_code")
	private String openCode;
	
	@Column(name="status")
	private PackageLockerPlacementStatus status;
	
	@ManyToOne
	@JoinColumn(name = "box_id")
	private Box box;
	
	@ManyToOne
	@JoinColumn(name = "package_id")
	private Package deliveredPackage;
	
	public PackageLockerPlacement(Date deliveryTime, Date pickupTime, String openCode,
			PackageLockerPlacementStatus status, Box box, Package deliveredPackage) {	
		this.deliveryTime = deliveryTime;
		this.pickupTime = pickupTime;
		this.openCode = openCode;
		this.status = status;
		this.box = box;
		this.deliveredPackage = deliveredPackage;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getOpenCode() {
		return openCode;
	}
	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}
	public PackageLockerPlacementStatus getStatus() {
		return status;
	}
	public void setStatus(PackageLockerPlacementStatus status) {
		this.status = status;
	}
	public Package getDeliveredPackage() {
		return deliveredPackage;
	}
	public void setDeliveredPackage(Package deliveredPackage) {
		this.deliveredPackage = deliveredPackage;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
	
}
