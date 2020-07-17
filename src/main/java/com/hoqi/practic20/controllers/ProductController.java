package com.hoqi.practic20.controllers;


import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Iterable<Product> list(){
        return productRepository.findAll();
    }

    @PostMapping
    public void insert(@RequestBody Product product){
        System.out.println(product.toString());
        productRepository.save(product);
    }

    @GetMapping("{id}")
    public Product getOne(@PathVariable Integer id){
        return productRepository.findById(id).get();
    }

}
