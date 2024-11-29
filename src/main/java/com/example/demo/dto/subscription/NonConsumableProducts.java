package com.example.demo.dto.subscription;

import java.io.Serializable;

public class NonConsumableProducts implements Serializable {

    private String id;
    private String metaInfo;
    private String category;
    private String packName;
    private String imageUrl;
    private String price;
    private String information;
    private String nextUrl;
    private String objectType = "NonConsumableProducts";
    private String purchaseId;

    public NonConsumableProducts() {
    }

    public NonConsumableProducts(String id, String metaInfo, String packName, String price,
                                 String information, String imageUrl, String category, String nextUrl) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.packName = packName;
        this.price = price;
        this.information = information;
        this.imageUrl = imageUrl;
        this.category = category;
        this.nextUrl = nextUrl;
    }

    public NonConsumableProducts(String id, String metaInfo, String packName, String price,
                                 String information, String imageUrl, String category, String nextUrl,
                                 String purchaseId) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.packName = packName;
        this.price = price;
        this.information = information;
        this.imageUrl = imageUrl;
        this.category = category;
        this.nextUrl = nextUrl;
        this.purchaseId = purchaseId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
