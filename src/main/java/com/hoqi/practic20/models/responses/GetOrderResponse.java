package com.hoqi.practic20.models.responses;

import com.hoqi.practic20.models.PurchaseOrder;
import com.hoqi.practic20.models.ShopCartItem;

import java.util.Date;
import java.util.List;

public class GetOrderResponse {

    private int id;

    private Date orderDate;

    private String address;

    private String productionMethod;

    private String paymentMethod;

    public List<ShopCartItem> shopCartItems;

    public GetOrderResponse(PurchaseOrder order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.address = order.getAddress();
        this.productionMethod = order.getProductionMethod();
        this.paymentMethod = order.getPaymentMethod();
        this.shopCartItems = order.getShopCart().getShopCartItems();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductionMethod() {
        return productionMethod;
    }

    public void setProductionMethod(String productionMethod) {
        this.productionMethod = productionMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public void setShopCartItems(List<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }
}
