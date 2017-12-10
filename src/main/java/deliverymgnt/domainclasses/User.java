package deliverymgnt.domainclasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
}