package com.example.demo.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Non-consumable product model representing items that are purchased once and owned permanently (e.g., equipment, stadiums, kits, tournaments)")
public class NonConsumableProducts implements Serializable {

    @Schema(description = "Unique identifier for the non-consumable product", example = "bat_pack_premium_001")
    private String id;
    
    @Schema(description = "Indicates if this is a default/free product included with base game", example = "false")
    private boolean isDefault;
    
    @Schema(description = "Additional metadata about the product (e.g., team code for kits like 'IND', 'PAK')", example = "IND")
    private String metaInfo;
    
    @Schema(description = "Product category (e.g., 'BattingPacks', 'BowlingPacks', 'StadiumPacks', 'Kits')", example = "BattingPacks")
    private String category;
    
    @Schema(description = "Display name of the pack", example = "Premium Bat Pack")
    private String packName;
    
    @Schema(description = "URL for the product image/icon", example = "https://example.com/bat_pack.png")
    private String imageUrl;
    
    @Schema(description = "Price of the product as a string", example = "$4.99")
    private String price;
    
    @Schema(description = "Detailed information/description about the product benefits", example = "Unlock premium batting equipment with enhanced stats")
    private String information = "Jodi Tomar Sathe Amar Dekha Hoy\n" +
            "Jodi Tomar Sathe Abar Dekha Hoy\n" +
            "E Shudhu Dekha Noy Binimoy Bonimoy\n" +
            "Kotogulo Bochor Paar Hoye\n" +
            "Koyekta Golpo Roye Soye\n";

    @Schema(description = "Alert/promotional message for the product", example = "The offer is Limited")
    private String alertInformation = "The offer is Limited";
    
    @Schema(description = "Additional flip/tooltip information shown on hover or flip animation", example = "* Premium quality * Permanent unlock")
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
    
    @Schema(description = "Navigation URL for drill-down or detail pages", example = "/shop/batting/premium")
    private String nextUrl;
    
    @Schema(description = "Object type identifier, always 'NonConsumableProducts'", example = "NonConsumableProducts")
    private String objectType = "NonConsumableProducts";
    
    @Schema(description = "Store-specific purchase ID/SKU for In-App Purchase integration", example = "com.game.bat.premium")
    private String purchaseId;
    
    @Schema(description = "Additional payload data (JSON string or custom format)", example = "{\"stats\":\"+5 power\"}")
    private String payload;

    public NonConsumableProducts() {
    }

    public NonConsumableProducts(String id, String metaInfo, String packName, String price,
                                 String information, String imageUrl, String category, String nextUrl, String payload, boolean isDefault) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.packName = packName;
        this.price = price;
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
