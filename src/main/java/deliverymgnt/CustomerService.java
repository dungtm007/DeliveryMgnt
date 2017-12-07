package deliverymgnt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Customer;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository1 customerRepository1;
	
	public List<Customer> getAllCustomers() {
		List<Customer> result = new ArrayList<Customer>();
		for(Customer c : customerRepository1.findAll())
		{
			result.add(c);
		}
		return result;
	}
}
