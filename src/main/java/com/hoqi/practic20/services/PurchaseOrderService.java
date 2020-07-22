package com.hoqi.practic20.services;

import com.hoqi.practic20.exceptions.NotFoundException;
import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.ShopCart;
import com.hoqi.practic20.models.requests.SubmitCartRequest;
import com.hoqi.practic20.models.responses.GetOrderResponse;
import com.hoqi.practic20.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService {
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public GetOrderResponse create(SubmitCartRequest data, ShopCart cart) throws NotFoundException {
        PurchaseOrder order = new PurchaseOrder(data.getAddress(), data.getProductionMethod(), data.getPaymentMethod(), cart);
        return new GetOrderResponse(purchaseOrderRepository.save(order));
    }

    public PurchaseOrder get(Integer id) throws NotFoundException {
        return purchaseOrderRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<GetOrderResponse> getList(Integer clientId) throws NotFoundException {
        List<GetOrderResponse> orderList = purchaseOrderRepository.findAllByClientId(clientId);
        if (orderList.isEmpty()) {
            throw new NotFoundException("Orders not found");
        }
        return orderList;
    }
}
