package com.hoqi.practic20.controllers;

import com.hoqi.practic20.exceptions.*;
import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getList() throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getList());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.get(id));
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        if (e instanceof NotFoundException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
