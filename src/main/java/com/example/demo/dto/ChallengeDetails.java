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

    public ChallengeDetails() {}

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

    // Setters for Jackson deserialization
    public void setDifficulty(ChallengeModeData.Difficulty difficulty) { this.difficulty = difficulty; }
    public void setLevel(String level) { this.level = level; }

    // Getters
    public ChallengeModeData.Difficulty getDifficulty() { return difficulty; }

    public String getLevel() { return level; }

}
