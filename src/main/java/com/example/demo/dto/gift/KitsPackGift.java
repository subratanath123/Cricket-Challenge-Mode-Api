package com.example.demo.dto.gift;

public class KitsPackGift {

    private String textureUrl;

    // Private constructor to prevent direct instantiation
    private KitsPackGift(Builder builder) {
        this.textureUrl = builder.textureUrl;
    }

    // Getter
    public String getTextureUrl() {
        return textureUrl;
    }

    // Builder class
    public static class Builder {
        private String textureUrl;

        public Builder setTextureUrl(String textureUrl) {
            this.textureUrl = textureUrl;
            return this;
        }

        public KitsPackGift build() {
            return new KitsPackGift(this);
        }
    }

}

