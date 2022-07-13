package com.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.product.model.Product;
import com.product.repository.ProductRepository;

@Service
@Qualifier("ProductService")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getProductList() {
		List<Product> products = productRepository.findAll();
		if(products.size() == 0) {
			Product product1 = new Product("1", "Apple Iphone 13", "60000", "128GB storage, 8GB Ram", "10", false);
			Product product2 = new Product("2", "Samsung Galaxy S21", "50000", "256GB storage, 8GB Ram", "10", false);
			Product product3 = new Product("3", "VIVO V9 Pro", "45000", "128GB storage, 6GB Ram", "12", false);
			
			products.add(product1);
			products.add(product2);
			products.add(product3);
		}
		return products;
	}
	
	public boolean createProducts(List<Product> productList) {
		try {
			productRepository.saveAll(productList);
		}
		catch (IllegalArgumentException e) {
			// In case the given entities or any of the entities is null.
			return false;
		}
		return true;
	}
	
	public boolean deleteProducts(List<Product> productList) {
		try {
			productRepository.deleteAll(productList);
		}
		catch (Exception e) {
			// In case the given entities or any of the entities is null.
			// or the given id is not found
			return false;
		}
		return true;
	}

	public boolean updateProduct(Product updatedProduct) {
		Optional<Product> optionalProduct = productRepository.findById(updatedProduct.getProductId());
		if(optionalProduct.isEmpty()) {
			System.out.println("No product found with id : " + updatedProduct.getProductId());
			return false;
		}

		Product currentProduct = optionalProduct.get();
		copyProductProperties(currentProduct, updatedProduct);
		productRepository.save(currentProduct);
		return true;
	}

	private void copyProductProperties(Product productA, Product productB) {
		if(productB.getProductName() != null) {
			productA.setProductName(productB.getProductName());
		}
		if(productB.getProductPrice() != null) {
			productA.setProductPrice(productB.getProductPrice());
		}
		if(productB.getProductDesc() != null) {
			productA.setProductDesc(productB.getProductDesc());
		}
		if(productB.getProductQuantity() != null) {
			productA.setProductQuantity(productB.getProductQuantity());
		}
		if(productB.getProductType() != null) {
			productA.setProductType(productB.getProductType());
		}
		if(productB.getProductmul() != null) {
			productA.setProductmul(productB.getProductmul());
		}
	}
	
	public long getProductsCount() {
		return productRepository.count();
	}
}
