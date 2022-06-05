package com.product.model;

import java.util.ArrayList;
import java.util.List;

public class ProductRequest {
	
	List<Product> listOfProducts=new ArrayList<Product>();

	@Override
	public String toString() {
		return "ProductRequest [listOfProducts=" + listOfProducts + "]";
	}
}
