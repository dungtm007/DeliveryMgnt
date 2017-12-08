package deliverymgnt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.Product;
import deliverymgnt.repositories.CustomerRepository;
import deliverymgnt.repositories.ProductRepository;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public Product update(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void delete(int id) {
		productRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Product> entities) {
		productRepository.deleteInBatch(entities);
	}

	@Override
	public Product find(int id) {
		return productRepository.findOne(id);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

}
