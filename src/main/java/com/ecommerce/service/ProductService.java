package com.ecommerce.service;

import com.ecommerce.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
} 