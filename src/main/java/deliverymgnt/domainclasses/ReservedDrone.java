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
@Table(name="reserved_drones")
public class ReservedDrone {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="drone_id")
	private Drone drone;
	
	@ManyToOne
	@JoinColumn(name="delivery_id")
	private Delivery delivery;
	
	@Column(name="from")
	private Date from;

	@Column(name="to")
	private Date to;
	
	@Column(name="status")
	private ReservedDroneStatus status;

	public ReservedDrone(Drone drone, Delivery delivery, Date from, Date to, ReservedDroneStatus status) {
		this.drone = drone;
		this.delivery = delivery;
		this.from = from;
		this.to = to;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public ReservedDroneStatus getStatus() {
		return status;
	}

	public void setStatus(ReservedDroneStatus status) {
		this.status = status;
	}
}
