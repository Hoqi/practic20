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
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Iterable<Product> getList(){
        return productService.getList();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable Integer id){
        return new ResponseEntity<Product>(productService.get(id), HttpStatus.OK);
    }
}
