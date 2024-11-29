package com.example.demo.dto.subscription;

import java.io.Serializable;

public class Subscription implements Serializable {

    private String id;
    private String subscriptionName;
    private int expiryMonths;
    private int price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public int getExpiryMonths() {
        return expiryMonths;
    }

    public void setExpiryMonths(int expiryMonths) {
        this.expiryMonths = expiryMonths;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
