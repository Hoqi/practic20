package com.hoqi.practic20.controllers;


import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping
    public Iterable<PurchaseOrder> list(){
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        return purchaseOrders;
    }

}
