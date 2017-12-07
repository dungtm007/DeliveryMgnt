package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_items")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="unit_price")
	private double unitPrice;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@OneToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public OrderItem(Product product, int amount, double unitPrice, Order order) {
		this.product = product;
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.order = order;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double calculatePrice() {
		return unitPrice * amount;
	}
	
	public double calculateVolume() {
		return product.calculateVolume() * amount;
	}
	
	public double calculateActualWeight() {
		return product.getWeight() * amount;
	}
	
	public double calculateVolumetricWeight() {
		return product.calculateVolumetricWeight() * amount;
	}
}
