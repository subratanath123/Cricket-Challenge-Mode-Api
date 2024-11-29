package com.example.demo.dto.subscription;

import java.io.Serializable;

public class IapProducts implements Serializable {

    private String id;
    private String price;
    private String purchaseId;
    private String type;

    public IapProducts() {
    }

    public IapProducts(String id, String price, String purchaseId, String type) {
        this.id = id;
        this.price = price;
        this.purchaseId = purchaseId;
        this.type = type;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
