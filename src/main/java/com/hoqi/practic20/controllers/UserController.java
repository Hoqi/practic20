package com.hoqi.practic20.controllers;


import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.models.User;
import com.hoqi.practic20.services.PurchaseOrderService;
import com.hoqi.practic20.services.ShopCartService;
import com.hoqi.practic20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ShopCartService shopCartService;
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    public UserController(UserService userService,
                          ShopCartService shopCartService,
                          PurchaseOrderService purchaseOrderService){
        this.userService = userService;
        this.shopCartService = shopCartService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        User user = userService.getUser(id);
        if (user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity createUser(@RequestBody User user){
        if (userService.addUser(user)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return  new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{id}/cart/create")
    public ResponseEntity createCart(@PathVariable Integer id){
        if(shopCartService.create(id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @GetMapping("{id}/cart")
    public ResponseEntity<ShopCart> getCart(@PathVariable Integer id){
        return new ResponseEntity<ShopCart>(shopCartService.get(id),
                                            HttpStatus.OK);
    }

    @PostMapping("{id}/cart/add/{vendorCode}")
    public ResponseEntity addItem(@PathVariable Integer id,@PathVariable Integer vendorCode){
        shopCartService.addItem(id,vendorCode);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("{id}/cart/delete/{vedorCode}")
    public ResponseEntity deleteItem(@PathVariable Integer id,@PathVariable Integer vedorCode){
        shopCartService.deleteOne(id,vedorCode);
        return new ResponseEntity(HttpStatus.OK);
    }

   // @PostMapping("{id}/cart/submit")
  //  public ResponseEntity submitCart(@PathVariable Integer id){

 //   }

}
