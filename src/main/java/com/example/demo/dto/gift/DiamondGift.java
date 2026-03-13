package com.example.demo.dto.gift;

public class DiamondGift {
    private int gift;

    private DiamondGift(DiamondGift.Builder builder) {
        this.gift = builder.gift;
    }

    // Getters
    public int getGift() {
        return gift;
    }

    // Builder class
    public static class Builder {
        private int gift;

        public DiamondGift.Builder setGift(int gift) {
            this.gift = gift;
            return this;
        }

        public DiamondGift build() {
            return new DiamondGift(this);
        }
    }
}

