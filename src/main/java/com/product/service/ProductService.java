package com.product.service;

import java.util.List;

import com.product.model.Product;

public interface ProductService {
	List<Product> getProductList();
	boolean createProducts(List<Product> productList);
	boolean deleteProducts(List<Product> productList);
	long getProductsCount();
	public boolean updateProduct(Product updatedProduct);
}
