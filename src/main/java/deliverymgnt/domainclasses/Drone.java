package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="drones")
public class Drone {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="drone_id")
	private String droneId; // auto generated field (DRO001, DRO002, DRO003)
	
	@Column(name="model")
	private String model;
	
	@Column(name="is_available")
	private boolean isAvailable;
	
	public Drone(String droneId, String model, boolean isAvailable) {
		this.droneId = droneId;
		this.model = model;
		this.isAvailable = isAvailable;
	}
	
	public Drone() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getDroneId() {
		return droneId;
	}

	public void setDroneId(String droneId) {
		this.droneId = droneId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public void start() {
		
	}
	
	public void arrive() {
		// update delivery status: Delivered
		// (background process will update it to Finished)
		// 
	}
	
	public void comeBack() {
		// update drone reservation status to Finished
		// update drone itself 
	}
}
