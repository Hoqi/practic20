package com.hoqi.practic20.controllers;

import com.hoqi.practic20.models.Product;
import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.models.ShopCartItem;
import com.hoqi.practic20.repositories.ProductRepository;
import com.hoqi.practic20.repositories.ShopCartItemRepository;
import com.hoqi.practic20.repositories.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("add")
public class ShopCartItemController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopCartRepository shopCartRepository;

    @Autowired
    ShopCartItemRepository shopCartItemRepository;

    @PostMapping("{product}_{cart}")
    public ShopCartItem add(@PathVariable("product") int productId,@PathVariable("cart") int cartID){
        Product product = productRepository.getOne(productId);
        ShopCart cart = shopCartRepository.findById(cartID).get();
        ShopCartItem shopCartItem = new ShopCartItem(product,cart);
        shopCartItemRepository.save(shopCartItem);
        return shopCartItem;
    }
}
