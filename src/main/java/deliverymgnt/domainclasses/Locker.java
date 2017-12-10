package deliverymgnt.domainclasses;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// Locker should be create as a whole (with boxes)
// Box should not be created individually

// Also, all of the access to box should go through locker

@Entity
@Table(name="lockers")
public class Locker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="address")
	private String address;
	
	@Column(name="large_boxes")
	private int numOfLargeBox;
	
	@Column(name="medium_boxes")
	private int numOfMediumBox;
	
	@Column(name="small_boxes")
	private int numOfSmallBox;
	
	@OneToMany(mappedBy="locker")
	private Set<Box> boxes;  
	
	public Locker() {
		
	}
	
	public Locker(String address, int numOfLargeBox, int numOfMediumBox, int numOfSmallBox) {
		this.address = address;
		// there should be corresponding boxes created
		this.numOfLargeBox = numOfLargeBox;
		this.numOfMediumBox = numOfMediumBox;
		this.numOfSmallBox = numOfSmallBox;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumOfLargeBox() {
		return numOfLargeBox;
	}

	public void setNumOfLargeBox(int numOfLargeBox) {
		this.numOfLargeBox = numOfLargeBox;
	}

	public int getNumOfMediumBox() {
		return numOfMediumBox;
	}

	public void setNumOfMediumBox(int numOfMediumBox) {
		this.numOfMediumBox = numOfMediumBox;
	}

	public int getNumOfSmallBox() {
		return numOfSmallBox;
	}

	public void setNumOfSmallBox(int numOfSmallBox) {
		this.numOfSmallBox = numOfSmallBox;
	}

	public Set<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(Set<Box> boxes) {
		this.boxes = boxes;
	}
	
	public int numberOfAvailableLargeBoxes() {
		return numOfLargeBox - numberOfOccupiedBoxes(PackageSize.Large);
	}
	
	public int numberOfAvailableMediumBoxes() {
		return numOfMediumBox - numberOfOccupiedBoxes(PackageSize.Medium);
	}
	
	public int numberOfAvailableSmallBoxes() {
		return numOfSmallBox - numberOfOccupiedBoxes(PackageSize.Small);
	}

	private int numberOfOccupiedBoxes(PackageSize size) {
		int occupied = 0;
		for(Box b : boxes) {
			if (b.getSize() == size && !b.isAvailable()) {
				occupied++;
			}
		}
		return occupied;
	}
}
