package com.example.demo.dto.gift;

public class BowlingGift {

    private String textureUrl;
    private int speed;
    private int swing;

    // Private constructor to prevent direct instantiation
    private BowlingGift(Builder builder) {
        this.textureUrl = builder.textureUrl;
        this.speed = builder.speed;
        this.swing = builder.swing;
    }

    // Getters
    public String getTextureUrl() {
        return textureUrl;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSwing() {
        return swing;
    }

    // Builder class
    public static class Builder {
        private String textureUrl;
        private int speed;
        private int swing;

        public Builder setTextureUrl(String textureUrl) {
            this.textureUrl = textureUrl;
            return this;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder setSwing(int swing) {
            this.swing = swing;
            return this;
        }

        public BowlingGift build() {
            return new BowlingGift(this);
        }
    }
}

