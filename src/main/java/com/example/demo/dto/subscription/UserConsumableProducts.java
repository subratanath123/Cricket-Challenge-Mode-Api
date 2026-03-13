package com.example.demo.dto.subscription;

import java.io.Serializable;

public class UserConsumableProducts implements Serializable {

    private User user;
    private ConsumableProducts consumableProducts;
    private int amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConsumableProducts getConsumableProducts() {
        return consumableProducts;
    }

    public void setConsumableProducts(ConsumableProducts consumableProducts) {
        this.consumableProducts = consumableProducts;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
