package com.example.demo.dto.subscription;

import java.io.Serializable;

public class UserNonConsumableProducts implements Serializable {

    private User user;
    private NonConsumableProducts nonConsumableProducts;
    private int amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NonConsumableProducts getNonConsumableProducts() {
        return nonConsumableProducts;
    }

    public void setNonConsumableProducts(NonConsumableProducts nonConsumableProducts) {
        this.nonConsumableProducts = nonConsumableProducts;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
