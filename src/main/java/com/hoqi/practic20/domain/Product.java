package com.hoqi.practic20.domain;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private Long vendorCode;

    private Double price;

    private String name;

    private String description;


    public Long getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(Long vendorCode) {
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
}
