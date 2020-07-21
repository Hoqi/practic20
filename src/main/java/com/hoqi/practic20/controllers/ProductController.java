package com.hoqi.practic20.controllers;

import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getList() {
        Iterable<Product> targetProducts = productService.getList();
        if (targetProducts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(targetProducts);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable Integer id) {
        Product targetProduct = productService.get(id);
        if (targetProduct != null)
            return ResponseEntity.status(HttpStatus.OK).body(targetProduct);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
