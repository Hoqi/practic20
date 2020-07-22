package com.hoqi.practic20.controllers;


import com.hoqi.practic20.exceptions.*;
import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.models.User;
import com.hoqi.practic20.models.requests.SubmitCartRequest;
import com.hoqi.practic20.models.responses.GetOrderResponse;
import com.hoqi.practic20.repositories.PurchaseOrderRepository;
import com.hoqi.practic20.services.PurchaseOrderService;
import com.hoqi.practic20.services.ShopCartService;
import com.hoqi.practic20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ShopCartService shopCartService;
    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public UserController(UserService userService,
                          ShopCartService shopCartService,
                          PurchaseOrderService purchaseOrderService,
                          PurchaseOrderRepository purchaseOrderRepository) {
        this.userService = userService;
        this.shopCartService = shopCartService;
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @GetMapping()
    public ResponseEntity<User> getUser(@RequestParam String email) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(email));
    }

    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserExistException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
    }

    @PostMapping("{id}/cart/create")
    public ResponseEntity<ShopCart> createCart(@PathVariable Integer id) throws CartExistException {
        return ResponseEntity.status(HttpStatus.OK).body(shopCartService.create(id));
    }

    @GetMapping("{id}/cart")
    public ResponseEntity<ShopCart> getCart(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(shopCartService.get(id));
    }

    @PostMapping("{id}/cart/add/{vendorCode}")
    public ResponseEntity<ShopCart> addItem(@PathVariable Integer id, @PathVariable Integer vendorCode) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(shopCartService.changeItemCount(id, vendorCode, 1));
    }

    @PostMapping("{id}/cart/delete/{vendorCode}")
    public ResponseEntity<ShopCart> deleteItem(@PathVariable Integer id, @PathVariable Integer vendorCode) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(shopCartService.changeItemCount(id, vendorCode, -1));
    }

    @PostMapping(value = "{id}/cart/submit", consumes = {"application/json"})
    public ResponseEntity<GetOrderResponse> submitCart(@PathVariable Integer id, @RequestBody SubmitCartRequest request) throws NotFoundException,
            CartIsEmptyException,
            OrderRejectedException {
        this.checkOrderParams(request);
        return ResponseEntity.status(HttpStatus.OK).body(shopCartService.submit(id, request));
    }

    @GetMapping("{id}/orders")
    public ResponseEntity<List<GetOrderResponse>> getOrders(@PathVariable Integer id) throws NotFoundException {
        List<GetOrderResponse> targetOrder = purchaseOrderService.getList(id);
        return ResponseEntity.status(HttpStatus.OK).body(targetOrder);
    }

    @GetMapping("{id}/orders/{orderId}")
    public ResponseEntity<PurchaseOrder> getOneOrder(@PathVariable Integer id, @PathVariable Integer orderId) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderService.get(orderId));
    }

    @GetMapping("{id}/test")
    public ResponseEntity<List<GetOrderResponse>> getOrder(@PathVariable Integer id) {
        List<GetOrderResponse> targetOrders = purchaseOrderRepository.findAllByClientId(id);
        return ResponseEntity.status(HttpStatus.OK).body(targetOrders);
    }

    private void checkOrderParams(SubmitCartRequest request) throws OrderRejectedException {
        if (request.getProductionMethod().equals("") || request.getPaymentMethod().equals("")
                || request.getAddress().equals("")) {
            throw new OrderRejectedException("Неккоректные параметры");
        }
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        if (e instanceof NotFoundException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        else if (e instanceof CartIsEmptyException)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        else if (e instanceof CartExistException)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        else if (e instanceof OrderRejectedException)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        else if (e instanceof UserExistException)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
