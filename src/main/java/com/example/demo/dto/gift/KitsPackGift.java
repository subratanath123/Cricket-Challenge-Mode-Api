package com.example.demo.dto.gift;

public class KitsPackGift {

    private String jerseyWithShoeTextureUrl;
    private String jerseyWithPadHelmetGlovesTextureUrl;
    private String team;

    // Private constructor to prevent direct instantiation
    private KitsPackGift(Builder builder) {
        this.jerseyWithShoeTextureUrl = builder.jerseyWithShoeTextureUrl;
        this.jerseyWithPadHelmetGlovesTextureUrl = builder.jerseyWithPadHelmetGlovesTextureUrl;
        this.team = builder.team;
    }

    // Getter
    public String getJerseyWithShoeTextureUrl() {
        return jerseyWithShoeTextureUrl;
    }

    public String getJerseyWithPadHelmetGlovesTextureUrl() {
        return jerseyWithPadHelmetGlovesTextureUrl;
    }

    public String getTeam() {
        return team;
    }

    // Builder class
    public static class Builder {
        private String jerseyWithShoeTextureUrl;
        private String jerseyWithPadHelmetGlovesTextureUrl;
        private String team;

        public Builder setJerseyWithShoeTextureUrl(String jerseyWithShoeTextureUrl) {
            this.jerseyWithShoeTextureUrl = jerseyWithShoeTextureUrl;
            return this;
        }

        public Builder setTeam(String team) {
            this.team = team;
            return this;
        }

        public Builder setJerseyWithPadHelmetGlovesTextureUrl(String jerseyWithPadHelmetGlovesTextureUrl) {
            this.jerseyWithPadHelmetGlovesTextureUrl = jerseyWithPadHelmetGlovesTextureUrl;
            return this;
        }

        public KitsPackGift build() {
            return new KitsPackGift(this);
        }
    }

}

