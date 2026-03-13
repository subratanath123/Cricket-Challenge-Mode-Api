package com.example.demo.dto.asset;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Game asset model containing image URLs and metadata for visual resources. Used for preloading and caching assets in the game client.")
public class GameAsset {

    @Schema(description = "Unique identifier for the asset/product", example = "bat_pack_premium_001")
    private String id;
    
    @Schema(description = "CDN URL for the asset image", example = "https://cdn.example.com/assets/bat_pack_premium.png")
    private String imageUrl;
    
    @Schema(description = "Category/type of the asset for organization", example = "BattingPacks")
    private String category;

    private GameAsset(Builder builder) {
        this.id = builder.id;
        this.imageUrl = builder.imageUrl;
        this.category = builder.category;
    }

    public static class Builder {
        private String id;
        private String imageUrl;
        private String category;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public GameAsset build() {
            return new GameAsset(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}

