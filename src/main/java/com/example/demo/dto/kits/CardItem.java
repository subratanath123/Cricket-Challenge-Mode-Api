package com.example.demo.dto.kits;

import java.io.Serializable;

public class CardItem implements Serializable {

    private String title;
    private String actionName;
    private String imageUrl;
    private String description;
    private String objectType;
    private String nextUrl;

    public CardItem() {
    }

    public CardItem(String title, String imageUrl, String description, String actionName, String objectType, String nextUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.actionName = actionName;
        this.objectType = objectType;
        this.nextUrl = nextUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
