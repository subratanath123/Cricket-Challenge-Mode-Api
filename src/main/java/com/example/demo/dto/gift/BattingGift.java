package com.example.demo.dto.gift;

public class BattingGift {

    private String textureUrl;
    private int shotPower;
    private int defensePower;

    private BattingGift(Builder builder) {
        this.textureUrl = builder.textureUrl;
        this.shotPower = builder.shotPower;
        this.defensePower = builder.defensePower;
    }

    // Getters
    public String getTextureUrl() {
        return textureUrl;
    }

    public int getShotPower() {
        return shotPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    // Builder class
    public static class Builder {
        private String textureUrl;
        private int shotPower;
        private int defensePower;

        public Builder setTextureUrl(String textureUrl) {
            this.textureUrl = textureUrl;
            return this;
        }

        public Builder setShotPower(int shotPower) {
            this.shotPower = shotPower;
            return this;
        }

        public Builder setDefensePower(int defensePower) {
            this.defensePower = defensePower;
            return this;
        }

        public BattingGift build() {
            return new BattingGift(this);
        }
    }
}

