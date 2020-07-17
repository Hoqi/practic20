package com.hoqi.practic20.controllers;

import com.hoqi.practic20.domain.ShopCart;
import com.hoqi.practic20.repositories.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class ShopCartController {

    @Autowired
    ShopCartRepository shopCartRepository;


    @GetMapping()
    public Iterable<ShopCart> getList(){
        return shopCartRepository.findAll();
    }

    @GetMapping("{id}")
    public ShopCart getOne( @PathVariable Integer id){
        return shopCartRepository.findByClientId(id);
    }

}
