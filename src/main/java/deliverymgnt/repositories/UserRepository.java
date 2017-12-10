package deliverymgnt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import deliverymgnt.domainclasses.User;
import deliverymgnt.domainclasses.UserType;

public interface UserRepository  extends JpaRepository<User, Integer>{
	User findByUserNameAndPassword(String user, String pass);
}
