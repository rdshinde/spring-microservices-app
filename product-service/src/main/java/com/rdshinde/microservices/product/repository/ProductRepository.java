package com.rdshinde.microservices.product.repository;

import com.rdshinde.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
