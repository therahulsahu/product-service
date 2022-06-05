package com.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Product {
	@Id
	String productId;
	String productName;
	String productPrice;
	String productDesc;
	String productQuantity;
	String productType;
	String Productmul;
	String ProductUpload;
	boolean checked;
	
	public Product() {}
	
	public Product(String productId, String productName, String productPrice, String productDesc, String productQuantity, boolean checked) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDesc = productDesc;
		this.productQuantity = productQuantity;
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;

	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;

	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;

	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;

	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;

	}

	public String getProductmul() {
		return Productmul;
	}

	public void setProductmul(String Productmul) {
		this.Productmul = Productmul;

	}

	public String getProductUpload() {
		return ProductUpload;
	}

	public void setProductUpload(String productUpload) {
		this.ProductUpload = productUpload;

	}

}
