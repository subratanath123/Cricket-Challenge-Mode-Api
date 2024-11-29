package com.example.demo.dto.subscription;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ConsumableProducts implements Serializable {

    private String id;
    private String category;
    private String metaInfo;
    private String price;
    private Date expiryDate;
    private String information;
    private String imageUrl;
    private String objectType = "ConsumableProducts";
    private List<String> freeProducts;
    private String purchaseId;
    private String nextUrl;

    public ConsumableProducts() {
    }

    public ConsumableProducts(String id, String category, String metaInfo, String price,
                              Date expiryDate, String information, String objectType, String imageUrl,
                              List<String> freeProducts, String nextUrl) {
        this.id = id;
        this.category = category;
        this.metaInfo = metaInfo;
        this.price = price;
        this.expiryDate = expiryDate;
        this.information = information;
        this.objectType = objectType;
        this.imageUrl = imageUrl;
        this.freeProducts = freeProducts;
        this.nextUrl = nextUrl;
    }

    public ConsumableProducts(String id, String category, String metaInfo, String price,
                              Date expiryDate, String information, String objectType, String imageUrl,
                              List<String> freeProducts, String nextUrl, String purchaseId) {
        this.id = id;
        this.category = category;
        this.metaInfo = metaInfo;
        this.price = price;
        this.expiryDate = expiryDate;
        this.information = information;
        this.objectType = objectType;
        this.imageUrl = imageUrl;
        this.freeProducts = freeProducts;
        this.purchaseId = purchaseId;
        this.nextUrl = nextUrl;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<String> getFreeProducts() {
        return freeProducts;
    }

    public void setFreeProducts(List<String> freeProducts) {
        this.freeProducts = freeProducts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
