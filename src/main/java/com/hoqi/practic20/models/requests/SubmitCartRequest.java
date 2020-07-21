package com.hoqi.practic20.models.requests;

public class SubmitCartRequest {

    private String address;

    private String productionMethod;

    private String paymentMethod;


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
}
