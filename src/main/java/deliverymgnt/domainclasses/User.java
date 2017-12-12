package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "type")
	private UserType type;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer_id")
	private Customer customer;

	public User() {
		
	}
	
	public User(String name, String pass, UserType usertype) {
		userName = name;
		password = pass;
		type = usertype;
	}
	
	public String getUser() {
		return userName;
	}
	
	public UserType getUserType() {
		return type;
	}
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}