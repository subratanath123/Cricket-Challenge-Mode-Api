package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BowlingRole {
    @JsonProperty("TargetRun")
    private int targetRun;
    @JsonProperty("OverLimitation")
    private int overLimitation;

    private BowlingRole() {}

    public static class Builder {
        private final BowlingRole bowlingRole = new BowlingRole();

        public Builder setTargetRun(int targetRun) {
            bowlingRole.targetRun = targetRun;
            return this;
        }

        public Builder setOverLimitation(int overLimitation) {
            bowlingRole.overLimitation = overLimitation;
            return this;
        }

        public BowlingRole build() {
            return bowlingRole;
        }
    }

    // Getters
    public int getTargetRun() { return targetRun; }
    public int getOverLimitation() { return overLimitation; }
}