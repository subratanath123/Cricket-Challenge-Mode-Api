package com.example.demo.dto.subscription;

import java.io.Serializable;
import java.util.List;

public class UserMetaData implements Serializable {

    private String id;
    private User user;
    private List<UserConsumableProducts> consumableProductsList;
    private List<UserNonConsumableProducts> nonConsumableProductsList;
    private List<UserSubscription> userSubscriptionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserConsumableProducts> getConsumableProductsList() {
        return consumableProductsList;
    }

    public void setConsumableProductsList(List<UserConsumableProducts> consumableProductsList) {
        this.consumableProductsList = consumableProductsList;
    }

    public List<UserNonConsumableProducts> getNonConsumableProductsList() {
        return nonConsumableProductsList;
    }

    public void setNonConsumableProductsList(List<UserNonConsumableProducts> nonConsumableProductsList) {
        this.nonConsumableProductsList = nonConsumableProductsList;
    }

    public List<UserSubscription> getUserSubscriptionList() {
        return userSubscriptionList;
    }

    public void setUserSubscriptionList(List<UserSubscription> userSubscriptionList) {
        this.userSubscriptionList = userSubscriptionList;
    }
}
