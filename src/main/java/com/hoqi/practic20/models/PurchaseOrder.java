package com.hoqi.practic20.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(generator = "purchaseOrderId",strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private String address;

    private String productionMethod;

    private String paymentMethod;


    @OneToOne(cascade = CascadeType.ALL, targetEntity = ShopCart.class)
    @JoinColumn(name = "shop_cart_id", referencedColumnName = "id")
    private ShopCart shopCart;


    public PurchaseOrder() {
    }

    public PurchaseOrder(String address, String productionMethod, String paymentMethod, ShopCart shopCart) {
        this.orderDate = new Date();
        this.address = address;
        this.productionMethod = productionMethod;
        this.paymentMethod = paymentMethod;
        this.shopCart = shopCart;
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

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }
}
