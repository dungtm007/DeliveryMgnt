package deliverymgnt.services;

import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.User;
import deliverymgnt.domainclasses.UserType;
import deliverymgnt.generic.GenericService;

@Service
public interface UserService extends GenericService<User> {
	User authenticate(String user, String password);
}
