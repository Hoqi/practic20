package com.hoqi.practic20.models;

import javax.persistence.*;

@Entity
@Table(name = "shop_cart_item")
public class ShopCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double price;

    private int count;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id",referencedColumnName = "vendor_code")
    private Product product;

    @ManyToOne(targetEntity = ShopCart.class)
    @JoinColumn(name = "shop_cart_id",referencedColumnName = "id")
    private ShopCart shopCart;

    public ShopCartItem(){

    }

    public ShopCartItem(Product product,ShopCart cart){
        this.shopCart = cart;
        this.product = product;
        this.price = product.getPrice();
        this.count = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    public void IncreaseCount(){
        this.count++;
    }
}
