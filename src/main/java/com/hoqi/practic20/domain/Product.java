package com.hoqi.practic20.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private Integer vendorCode;

    private Double price;

    private String name;

    private String description;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,targetEntity = ShopCartItem.class)
    @JsonIgnore
    private Set<ShopCartItem> shopCartItems;

    public Product(){
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public Product(Integer vendorCode,Double price, String name, String description){
        this.vendorCode = vendorCode;
        this.price = price;
        this.name = name;
        this.description = description;
        shopCartItems = new HashSet<ShopCartItem>();
    }

    public Set<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public void setShopCartItems(Set<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    public Integer getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(Integer vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Code: %d\nName: %s\nDescription: %s",vendorCode,name,description);
    }
}
