package com.example.demo.dto.gift;

import java.util.List;

public class KitsPackGift {

    private List<String> jerseyWithShoeTextureUrls;
    private List<String> jerseyWithPadHelmetGlovesTextureUrls;
    private String team;

    // Private constructor to prevent direct instantiation
    private KitsPackGift(Builder builder) {
        this.jerseyWithShoeTextureUrls = builder.jerseyWithShoeTextureUrls;
        this.jerseyWithPadHelmetGlovesTextureUrls = builder.jerseyWithPadHelmetGlovesTextureUrls;
        this.team = builder.team;
    }

    // Getter
    public List<String> getJerseyWithShoeTextureUrls() {
        return jerseyWithShoeTextureUrls;
    }

    public List<String> getJerseyWithPadHelmetGlovesTextureUrls() {
        return jerseyWithPadHelmetGlovesTextureUrls;
    }

    public String getTeam() {
        return team;
    }

    // Builder class
    public static class Builder {
        private List<String> jerseyWithShoeTextureUrls;
        private List<String> jerseyWithPadHelmetGlovesTextureUrls;
        private String team;

        public Builder setJerseyWithShoeTextureUrls(List<String> jerseyWithShoeTextureUrls) {
            this.jerseyWithShoeTextureUrls = jerseyWithShoeTextureUrls;
            return this;
        }

        public Builder setTeam(String team) {
            this.team = team;
            return this;
        }

        public Builder setJerseyWithPadHelmetGlovesTextureUrls(List<String> jerseyWithPadHelmetGlovesTextureUrls) {
            this.jerseyWithPadHelmetGlovesTextureUrls = jerseyWithPadHelmetGlovesTextureUrls;
            return this;
        }

        public KitsPackGift build() {
            return new KitsPackGift(this);
        }
    }

}

