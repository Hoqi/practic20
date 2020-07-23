package com.hoqi.practic20.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shop_cart")
public class ShopCart {
    @Id
    @GeneratedValue(generator = "shopCartId", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;

    private Integer status;

    @OneToMany(mappedBy = "shopCart", targetEntity = ShopCartItem.class)
    private List<ShopCartItem> shopCartItems;


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

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public void setShopCartItems(List<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
