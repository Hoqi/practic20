package com.hoqi.practic20.domain;

import ch.qos.logback.core.net.server.Client;
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

    @OneToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    @JsonIgnore
    private User client;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "shopCart",targetEntity = PurchaseOrder.class)
    private PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "shopCart",targetEntity = ShopCartItem.class)
    private Set<ShopCartItem> shopCartItems;

    public ShopCart(){
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public ShopCart(User client) {
        this.client = client;
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
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


}
