package com.hoqi.practic20.services;

import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PurchaseOrderService {
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public boolean create(String productionMethod,
                          String paymentMethod,
                          String address,
                          ShopCart cart) {
        if (productionMethod.equals("") || paymentMethod.equals("")
                || address.equals("")) {
            return false;
        }
        PurchaseOrder order = new PurchaseOrder(address, productionMethod, paymentMethod, cart);
        purchaseOrderRepository.save(order);
        return true;
    }

    public PurchaseOrder get(Integer id) {
        return purchaseOrderRepository.findById(id).get();
    }

    public Iterable<PurchaseOrder> getList(Integer clientId) {
        return purchaseOrderRepository.findByClientId(clientId);
    }
}
