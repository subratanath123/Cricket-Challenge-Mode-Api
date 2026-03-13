package com.example.demo.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Schema(description = "Consumable product model representing items that can be purchased and consumed multiple times (e.g., coins, diamonds, subscription packs)")
public class ConsumableProducts implements Serializable {

    @Schema(description = "Unique identifier for the consumable product", example = "coin_pack_100")
    private String id;
    
    @Schema(description = "Product category (e.g., 'CoinsPacks', 'DiamondPacks', 'Subscription')", example = "CoinsPacks")
    private String category;
    
    @Schema(description = "Display name of the pack", example = "100 Coins Pack")
    private String packName;
    
    @Schema(description = "Additional metadata about the product (e.g., duration for subscriptions)", example = "Monthly")
    private String metaInfo;
    
    @Schema(description = "Price of the product as a string", example = "$0.99")
    private String price;
    
    @Schema(description = "Expiry date for time-limited products (mainly subscriptions)", example = "2026-04-13T00:00:00.000+00:00")
    private Date expiryDate;
    
    @Schema(description = "Detailed information/description about the product benefits", example = "Get 100 coins to use in the game shop")
    private String information = "Jodi Tomar Sathe Amar Dekha Hoy\n" +
            "Jodi Tomar Sathe Abar Dekha Hoy\n" +
            "E Shudhu Dekha Noy Binimoy Bonimoy\n" +
            "Kotogulo Bochor Paar Hoye\n" +
            "Sala tomaro lagiya, Jogini Sajibo\n" +
            "Koyekta Golpo Roye Soye\n";

    @Schema(description = "Alert/promotional message for the product", example = "The offer is Limited")
    private String alertInformation = "The offer is Limited";
    
    @Schema(description = "Additional flip/tooltip information shown on hover or flip animation", example = "* Limited time offer * Best value pack")
    private String flipInformation = "* Jodi Tomar Sathe Amar Dekha Hoy " +
            "* Jodi Tomar Sathe Abar Dekha Hoy " +
            "* E Shudhu Dekha Noy Binimoy Bonimoy " +
            "* Kotogulo Bochor Paar Hoye " +
            "* Koyekta Golpo Roye Soye " +
            "* Tomar Amar Abar Jodi Kotha Hoy " +
            "* Binimoy Bonimoy " +
            "* Ekta Sohor Amader Noy " +
            "* Ekta Golpo Amader Noy " +
            "* Tomar Amar Abar Jodi Kotha Hoy " +
            "* Binimoy Kotha Hoy";
    
    @Schema(description = "URL for the product image/icon", example = "https://example.com/coin_pack.png")
    private String imageUrl;
    
    @Schema(description = "Object type identifier, always 'ConsumableProducts'", example = "ConsumableProducts")
    private String objectType = "ConsumableProducts";
    
    @Schema(description = "List of product IDs that are included free with this product (mainly for subscriptions)", example = "[\"bat_pack_001\", \"stadium_pack_002\"]")
    private List<String> freeProducts;
    
    @Schema(description = "Store-specific purchase ID/SKU for In-App Purchase integration", example = "com.game.coins.100")
    private String purchaseId;
    
    @Schema(description = "Navigation URL for drill-down or detail pages", example = "/shop/coins/100")
    private String nextUrl;
    
    @Schema(description = "Additional payload data (JSON string or custom format)", example = "{\"bonus\":\"10\"}")
    private String payload;
    
    @Schema(description = "Indicates if this is a default/free product included with base game", example = "false")
    private boolean isDefault;

    public ConsumableProducts() {
    }

    public ConsumableProducts(String id, String category, String packName, String metaInfo, String price,
                              Date expiryDate, String information, String objectType, String imageUrl,
                              List<String> freeProducts, String nextUrl, String payload, boolean isDefault) {
        this.id = id;
        this.category = category;
        this.packName = packName;
        this.metaInfo = metaInfo;
        this.price = price;
        this.expiryDate = expiryDate;
        this.objectType = objectType;
        this.imageUrl = imageUrl;
        this.freeProducts = freeProducts;
        this.nextUrl = nextUrl;
        this.payload = payload;
        this.isDefault = isDefault;
    }

    public ConsumableProducts(String id, String category, String packName, String metaInfo, String price,
                              Date expiryDate, String information, String objectType, String imageUrl,
                              List<String> freeProducts, String nextUrl, String purchaseId, String payload, boolean isDefault) {
        this.id = id;
        this.category = category;
        this.packName = packName;
        this.metaInfo = metaInfo;
        this.price = price;
        this.expiryDate = expiryDate;
        this.information = information;
        this.objectType = objectType;
        this.imageUrl = imageUrl;
        this.freeProducts = freeProducts;
        this.purchaseId = purchaseId;
        this.nextUrl = nextUrl;
        this.payload = payload;
        this.isDefault = isDefault;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
