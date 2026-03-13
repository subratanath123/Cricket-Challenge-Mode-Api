package com.example.demo.dto.kits;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Card item model representing team categories in the kits section. Used for navigation and team selection UI.")
public class CardItem implements Serializable {

    @Schema(description = "Team code or item title (e.g., 'PAK', 'IND', 'AUS')", example = "IND")
    private String title;
    
    @Schema(description = "Action button label", example = "Details")
    private String actionName;
    
    @Schema(description = "Image URL for the team logo or representative image", example = "https://i.imgur.com/Z0qwQbu.png")
    private String imageUrl;
    
    @Schema(description = "Description text shown on the card", example = "Buy India Team Jersey & Kits")
    private String description;
    
    @Schema(description = "Object type identifier, typically 'Parent' for team categories", example = "Parent")
    private String objectType;
    
    @Schema(description = "Navigation URL to view specific kits for this team", example = "/kits/teams/IND")
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
