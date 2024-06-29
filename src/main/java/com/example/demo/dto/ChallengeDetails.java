package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class ChallengeDetails {

    @JsonProperty("Difficulty")
    @Schema(description = "Difficulty", example = "EASY", allowableValues = {"EASY", "MEDIUM", "HARD"})
    private ChallengeModeData.Difficulty difficulty;

    @Schema(description = "Level", example = "Level 1", allowableValues = {"Level 1", "Level 2", "Level 3", "...."})
    @JsonProperty("Level")
    private String level;

    private ChallengeDetails() {}

    public static class Builder {
        private final ChallengeDetails challengeDetails = new ChallengeDetails();

        public Builder setLevel(ChallengeModeData.Difficulty difficulty) {
            challengeDetails.difficulty = difficulty;
            return this;
        }

        public Builder setLevel(String level) {
            challengeDetails.level = level;
            return this;
        }

        public ChallengeDetails build() {
            return challengeDetails;
        }
    }

    // Getters
    public ChallengeModeData.Difficulty getDifficulty() { return difficulty; }

    public String getLevel() { return level; }

}
