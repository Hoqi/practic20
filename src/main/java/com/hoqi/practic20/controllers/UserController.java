package com.hoqi.practic20.controllers;


import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.models.User;
import com.hoqi.practic20.models.requests.SubmitCartRequest;
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
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public UserController(UserService userService,
                          ShopCartService shopCartService,
                          PurchaseOrderService purchaseOrderService) {
        this.userService = userService;
        this.shopCartService = shopCartService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        if (userService.addUser(user)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("{id}/cart/create")
    public ResponseEntity<Void> createCart(@PathVariable Integer id) {
        if (shopCartService.create(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("{id}/cart")
    public ResponseEntity<ShopCart> getCart(@PathVariable Integer id) {
        ShopCart targetCart = shopCartService.get(id);
        if (targetCart != null){
            return ResponseEntity.status(HttpStatus.OK).body(targetCart);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("{id}/cart/add/{vendorCode}")
    public ResponseEntity<Void> addItem(@PathVariable Integer id, @PathVariable Integer vendorCode) {
        shopCartService.addItem(id, vendorCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{id}/cart/delete/{vendorCode}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id, @PathVariable Integer vendorCode) {
        if(shopCartService.deleteOne(id, vendorCode))
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "{id}/cart/submit", consumes = {"application/json"})
    public ResponseEntity<Void> submitCart(@PathVariable Integer id, @RequestBody SubmitCartRequest request) {
        if(shopCartService.submit(id,request.getProductionMethod(),request.getPaymentMethod(),request.getAddress())){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}/orders")
    public ResponseEntity<Iterable<PurchaseOrder>> getOrders(@PathVariable Integer id){
        Iterable<PurchaseOrder> targetOrder = purchaseOrderService.getList(id);
        if (targetOrder != null){
            return ResponseEntity.status(HttpStatus.OK).body(targetOrder);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("{id}/orders/{orderId}")
    public ResponseEntity<PurchaseOrder> getOneOrder(@PathVariable Integer id,@PathVariable Integer orderId){
        PurchaseOrder targetOrder = purchaseOrderService.get(orderId);
        if (targetOrder != null){
            return ResponseEntity.status(HttpStatus.OK).body(targetOrder);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
