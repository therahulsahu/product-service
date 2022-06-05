package com.product.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import com.product.model.Product;

public interface ProductRepository extends CouchbaseRepository<Product, String> {

}
