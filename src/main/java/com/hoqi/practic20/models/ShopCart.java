package com.hoqi.practic20.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shop_cart")
public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "client_id")
    private Integer clientId;

    private Integer status;


    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER,mappedBy = "shopCart",targetEntity = PurchaseOrder.class)
    private PurchaseOrder purchaseOrder;


    @OneToMany(mappedBy = "shopCart",targetEntity = ShopCartItem.class)
    private Set<ShopCartItem> shopCartItems;

    public ShopCart(){
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public ShopCart(Integer client_id) {
        this.clientId = client_id;
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer client) {
        this.clientId = client;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Set<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public void setShopCartItems(Set<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
