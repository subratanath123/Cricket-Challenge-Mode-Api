package com.example.demo.dto.subscription;

import java.io.Serializable;

public class NonConsumableProducts implements Serializable {

    private String id;
    private boolean isDefault;
    private String metaInfo;
    private String category;
    private String packName;
    private String imageUrl;
    private String price;
    private String information = "Jodi Tomar Sathe Amar Dekha Hoy\n" +
            "Jodi Tomar Sathe Abar Dekha Hoy\n" +
            "E Shudhu Dekha Noy Binimoy Bonimoy\n" +
            "Kotogulo Bochor Paar Hoye\n" +
            "Koyekta Golpo Roye Soye\n";

    private String alertInformation = "The offer is Limited";
    private String flipInformation =  "Jodi Tomar Sathe Amar Dekha Hoy " +
            "Jodi Tomar Sathe Abar Dekha Hoy " +
            "E Shudhu Dekha Noy Binimoy Bonimoy " +
            "Kotogulo Bochor Paar Hoye " +
            "Koyekta Golpo Roye Soye " +
            "Tomar Amar Abar Jodi Kotha Hoy " +
            "Binimoy Bonimoy " +
            "Ekta Sohor Amader Noy " +
            "Ekta Golpo Amader Noy " +
            "Tomar Amar Abar Jodi Kotha Hoy " +
            "Binimoy Kotha Hoy";
    private String nextUrl;
    private String objectType = "NonConsumableProducts";
    private String purchaseId;
    private String payload;

    public NonConsumableProducts() {
    }

    public NonConsumableProducts(String id, String metaInfo, String packName, String price,
                                 String information, String imageUrl, String category, String nextUrl, String payload, boolean isDefault) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.packName = packName;
        this.price = price;
//        this.information = information;
        this.imageUrl = imageUrl;
        this.category = category;
        this.nextUrl = nextUrl;
        this.payload = payload;
        this.isDefault = isDefault;
    }

    public NonConsumableProducts(String id, String metaInfo, String packName, String price,
                                 String information, String imageUrl, String category, String nextUrl,
                                 String purchaseId, String payload, boolean isDefault) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.packName = packName;
        this.price = price;
        this.information = information;
        this.imageUrl = imageUrl;
        this.category = category;
        this.nextUrl = nextUrl;
        this.purchaseId = purchaseId;
        this.payload = payload;
        this.isDefault = isDefault;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
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

    public String getAlertInformation() {
        return alertInformation;
    }

    public void setAlertInformation(String alertInformation) {
        this.alertInformation = alertInformation;
    }

    public String getFlipInformation() {
        return flipInformation;
    }

    public void setFlipInformation(String flipInformation) {
        this.flipInformation = flipInformation;
    }
}
