package deliverymgnt.domainclasses;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "warehouses")
public class Warehouse {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "warehouse_addr_address")),
		@AttributeOverride(name = "city", column = @Column(name = "warehouse_addr_city")),
		@AttributeOverride(name = "state", column = @Column(name = "warehouse_addr_state")),                       
        @AttributeOverride(name = "zip", column = @Column(name = "warehouse_addr_zip"))
    })
	private Address warehouseAddress;
	
	@ManyToMany
	  @JoinTable(name="warehouses_products", 
	   	joinColumns = @JoinColumn(name="warehouse_id"),
	   	inverseJoinColumns = @JoinColumn(name="product_id"))
	private Set<Product> products;
	
	public Warehouse() {
		this.products = new HashSet<>();
	}
	
	public Warehouse(int id, String name, Address warehouseAddress) {
		this.id = id;
		this.name = name;
		this.warehouseAddress = warehouseAddress;
		this.products = new HashSet<>();
	}
	
	public Warehouse(int id, String name, Address warehouseAddress, Set<Product> products) {
		this.id = id;
		this.name = name;
		this.warehouseAddress = warehouseAddress;
		this.products = products;
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

	public Address getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(Address warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
}
