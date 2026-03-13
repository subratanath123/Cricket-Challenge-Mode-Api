package com.example.demo.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "In-App Purchase product configuration model containing store-specific product information for IAP integration")
public class IapProducts implements Serializable {

    @Schema(description = "Internal product identifier matching the product ID in the game", example = "bat_pack_premium_001")
    private String id;
    
    @Schema(description = "Display price of the product", example = "$4.99")
    private String price;
    
    @Schema(description = "Store-specific purchase ID/SKU used for App Store and Google Play integration", example = "com.game.bat.premium")
    private String purchaseId;
    
    @Schema(description = "Product type for IAP handling: 'Consumable', 'NonConsumable', or 'Subscription'", example = "NonConsumable")
    private String type;

    public IapProducts() {
    }

    public IapProducts(String id, String price, String purchaseId, String type) {
        this.id = id;
        this.price = price;
        this.purchaseId = purchaseId;
        this.type = type;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
