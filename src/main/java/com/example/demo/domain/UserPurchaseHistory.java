package com.example.demo.domain;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_purchase_histories")
public class UserPurchaseHistory {

    @Id
    private String email;
    private List<ConsumableProducts> consumablePurchases = new ArrayList<>();
    private List<NonConsumableProducts> nonConsumablePurchases = new ArrayList<>();
    private List<ConsumableProducts> subscriptionPurchases = new ArrayList<>();

    public UserPurchaseHistory() {
    }

    public UserPurchaseHistory(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ConsumableProducts> getConsumablePurchases() {
        if (consumablePurchases == null) {
            consumablePurchases = new ArrayList<>();
        }
        return consumablePurchases;
    }

    public void setConsumablePurchases(List<ConsumableProducts> consumablePurchases) {
        this.consumablePurchases = consumablePurchases;
    }

    public List<NonConsumableProducts> getNonConsumablePurchases() {
        if (nonConsumablePurchases == null) {
            nonConsumablePurchases = new ArrayList<>();
        }
        return nonConsumablePurchases;
    }

    public void setNonConsumablePurchases(List<NonConsumableProducts> nonConsumablePurchases) {
        this.nonConsumablePurchases = nonConsumablePurchases;
    }

    public List<ConsumableProducts> getSubscriptionPurchases() {
        if (subscriptionPurchases == null) {
            subscriptionPurchases = new ArrayList<>();
        }
        return subscriptionPurchases;
    }

    public void setSubscriptionPurchases(List<ConsumableProducts> subscriptionPurchases) {
        this.subscriptionPurchases = subscriptionPurchases;
    }
}
