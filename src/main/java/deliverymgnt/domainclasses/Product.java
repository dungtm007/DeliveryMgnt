package deliverymgnt.domainclasses;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "product_img_source")
	private String productImageSource;

	@ManyToMany
	private Set<Warehouse> warehouses;

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
	
	public Set<Warehouse> getWarehouses() {
		return warehouses;
	}

	public String getProductImageSource() {
		return productImageSource;
	}

	public void setProductImageSource(String productImageSource) {
		this.productImageSource = productImageSource;
	}
	
	public double calculateVolume() {
		return width * length * height; 
	}
	
	// dimensional weight
	// unit: pounds
	public double calculateVolumetricWeight() {
		return calculateVolume() / WEIGHT_DIVISOR; // 139 is divisor used for shipping parcel in domestic US
	}
	
	public double calculateShippingWeight() {
		return Math.max(calculateVolumetricWeight(), getWeight());
	}

	@Override
	public String toString() {
		return name + "\n" 
				+ "W x L x H: " + width + " x " + length + " x " + height + " x " + weight + "\n"
				+ "Price: $" + price;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Product other = (Product) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
			
		} else if (!name.equals(other.name))
			return false;
		
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		
		if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
			return false;
		
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		
		return true;
	}
	
	
	
}
