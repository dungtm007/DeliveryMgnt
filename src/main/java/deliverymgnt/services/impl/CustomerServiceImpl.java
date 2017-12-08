package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Customer;
import deliverymgnt.repositories.CustomerRepository;
import deliverymgnt.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer save(Customer entity) {
		return customerRepository.save(entity);
	}

	@Override
	public Customer update(Customer entity) {
		return customerRepository.save(entity);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		customerRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Customer> entities) {
		customerRepository.deleteInBatch(entities);
	}

	@Override
	public Customer find(int id) {
		return customerRepository.findOne(id);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

}
