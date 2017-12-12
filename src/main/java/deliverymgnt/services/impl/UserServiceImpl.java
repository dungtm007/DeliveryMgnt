package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.User;
import deliverymgnt.domainclasses.UserType;
import deliverymgnt.repositories.UserRepository;
import deliverymgnt.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User authenticate(String username, String password) {
		User user = userRepository.findByUserNameAndPassword(username, password);
		return user;
	}

	@Override
	public User save(User entity) {
		// TODO Auto-generated method stub
		return userRepository.save(entity);
	}

	@Override
	public User update(User entity) {
		// TODO Auto-generated method stub
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		userRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<User> entities) {
		// TODO Auto-generated method stub
		userRepository.deleteInBatch(entities);
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}
