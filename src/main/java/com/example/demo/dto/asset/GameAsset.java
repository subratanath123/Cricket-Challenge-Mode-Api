package com.example.demo.dto.asset;

public class GameAsset {

    private String id;
    private String imageUrl;
    private String category;

    // Private constructor to enforce usage of the Builder
    private GameAsset(Builder builder) {
        this.id = builder.id;
        this.imageUrl = builder.imageUrl;
        this.category = builder.category;
    }

    // Static Builder class
    public static class Builder {
        private String id;
        private String imageUrl;
        private String category;

        // Setter methods in the builder class that return the Builder object
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

        // Build method to create the GameAsset object
        public GameAsset build() {
            return new GameAsset(this);
        }
    }

    // Getters for GameAsset fields (optional, depending on your needs)
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

