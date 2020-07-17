package com.hoqi.practic20.controllers;

import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.repositories.ShopCartRepository;
import com.hoqi.practic20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class ShopCartController {

    @Autowired
    ShopCartRepository shopCartRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping()
    public Iterable<ShopCart> getList(){
        return shopCartRepository.findAll();
    }

    @GetMapping("{id}")
    public ShopCart getOne(@PathVariable Integer id){
        return shopCartRepository.findByClientId(id);
    }

   // @PostMapping("{id}")
   // public ShopCart insertOne( @PathVariable Integer id){
        //ShopCart newCart = new ShopCart(userRepository.getOne(id));
        //shopCartRepository.save(newCart);
        // shopCartRepository.findByClientId(id);
//
    //@GetMapping()
    //public void insert(@RequestBody String id){
    //    System.out.println(id);
        //shopCartRepository.saveWithUserId(id);
  //  }

}
