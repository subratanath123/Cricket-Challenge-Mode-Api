package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rewards {
    @JsonProperty("Xps")
    private int xps;
    @JsonProperty("Coins")
    private int coins;
    @JsonProperty("Diamonds")
    private int diamonds;
    @JsonProperty("Stars")
    private int stars;

    private Rewards() {}

    public static class Builder {
        private final Rewards rewards = new Rewards();

        public Builder setXps(int xps) {
            rewards.xps = xps;
            return this;
        }

        public Builder setCoins(int coins) {
            rewards.coins = coins;
            return this;
        }

        public Builder setDiamonds(int diamonds) {
            rewards.diamonds = diamonds;
            return this;
        }

        public Builder setStars(int stars) {
            rewards.stars = stars;
            return this;
        }

        public Rewards build() {
            return rewards;
        }
    }

    // Getters
    public int getXps() { return xps; }
    public int getCoins() { return coins; }
    public int getDiamonds() { return diamonds; }
    public int getStars() { return stars; }
}