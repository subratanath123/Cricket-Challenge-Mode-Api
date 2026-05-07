package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BattingRole {
    @JsonProperty("WicketLimit")
    private int wicketLimit;
    @JsonProperty("TargetRun")
    private int targetRun;
    @JsonProperty("OverLimitation")
    private int overLimitation;

    public BattingRole() {}

    public static class Builder {
        private final BattingRole battingRole = new BattingRole();

        public Builder setWicketLimit(int wicketLimit) {
            battingRole.wicketLimit = wicketLimit;
            return this;
        }

        public Builder setTargetRun(int targetRun) {
            battingRole.targetRun = targetRun;
            return this;
        }

        public Builder setOverLimitation(int overLimitation) {
            battingRole.overLimitation = overLimitation;
            return this;
        }

        public BattingRole build() {
            return battingRole;
        }
    }

    // Setters for Jackson deserialization
    public void setWicketLimit(int wicketLimit) { this.wicketLimit = wicketLimit; }
    public void setTargetRun(int targetRun) { this.targetRun = targetRun; }
    public void setOverLimitation(int overLimitation) { this.overLimitation = overLimitation; }

    // Getters
    public int getWicketLimit() { return wicketLimit; }
    public int getTargetRun() { return targetRun; }
    public int getOverLimitation() { return overLimitation; }
}
