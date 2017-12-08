package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	
	private static final int WEIGHT_DIVISOR = 139;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "width")
	private double width;
	
	@Column(name = "length")
	private double length;
	
	@Column(name = "height")
	private double height;
	
	@Column(name = "weight")
	private double weight;
	
	@Column(name = "price")
	private double price;
	
	public Product() {
		
	}
	
	public Product(String name, double width, double length, double height, double weight, double price) {
		super();
		this.name = name;
		this.width = width;
		this.length = length;
		this.height = height;
		this.weight = weight;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double calculateVolume() {
		return width * length * height; 
	}
	
	// dimensional weight
	// unit: pounds
	public double calculateVolumetricWeight() {
		return calculateVolume() / WEIGHT_DIVISOR; // 139 is divisor used for shipping parcel in domestic US
	}

	@Override
	public String toString() {
		return name + "\n" 
				+ "W x L x H: " + width + " x " + length + " x " + height + " x " + weight + "\n"
				+ "Price: $" + price;
	}
	
	
	
}
