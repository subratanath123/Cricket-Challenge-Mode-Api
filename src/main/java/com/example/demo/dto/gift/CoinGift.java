package com.example.demo.dto.gift;

public class CoinGift {

    private int gift;

    private CoinGift(Builder builder) {
        this.gift = builder.gift;
    }

    // Getters
    public int getGift() {
        return gift;
    }

    // Builder class
    public static class Builder {
        private int gift;

        public Builder setGift(int gift) {
            this.gift = gift;
            return this;
        }

        public CoinGift build() {
            return new CoinGift(this);
        }
    }
}

