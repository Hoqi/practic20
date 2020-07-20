package com.hoqi.practic20.services;

import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product get(Integer vendorCode){
        return productRepository.findById(vendorCode).orElse(null);
    }

    public Iterable<Product> getList(){
        return productRepository.findAll();
    }
}
