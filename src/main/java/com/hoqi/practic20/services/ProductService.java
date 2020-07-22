package com.hoqi.practic20.services;

import com.hoqi.practic20.exceptions.NotFoundException;
import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.models.ShopCartItem;
import com.hoqi.practic20.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product get(Integer vendorCode) throws NotFoundException {
        return productRepository.findById(vendorCode).orElseThrow(NotFoundException::new);
    }

    public List<Product> getList() throws NotFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new NotFoundException("Товары не найдены");
        }
        products.sort(Comparator.comparingInt(Product::getVendorCode));
        return  products;
    }
}
