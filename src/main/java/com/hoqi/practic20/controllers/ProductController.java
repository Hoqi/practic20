package com.hoqi.practic20.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoqi.practic20.domain.Product;
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




}
