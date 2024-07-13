package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MyChallengeLevelProgress {
    @JsonProperty("Level")
    private String level;

    @JsonProperty("ChallengeProgress")
    private List<ChallengeProgress> challengeProgress;

    private MyChallengeLevelProgress(Builder builder) {
        this.level = builder.level;
        this.challengeProgress = builder.challengeProgress;
    }

    // Getters
    public String getLevel() {
        return level;
    }

    public List<ChallengeProgress> getChallengeProgress() {
        return challengeProgress;
    }

    public static class Builder {
        private String level;
        private List<ChallengeProgress> challengeProgress;

        public Builder setLevel(String level) {
            this.level = level;
            return this;
        }

        public Builder setChallengeProgress(List<ChallengeProgress> challengeProgress) {
            this.challengeProgress = challengeProgress;
            return this;
        }

        public MyChallengeLevelProgress build() {
            return new MyChallengeLevelProgress(this);
        }
    }

    public static class ChallengeProgress {

        @JsonProperty("MyTeamName")
        private String myTeamName;

        @JsonProperty("ChallengeId")
        private int challengeId;

        @JsonProperty("Summary")
        private String summary;

        private ChallengeProgress(Builder builder) {
            this.myTeamName = builder.myTeamName;
            this.challengeId = builder.challengeId;
            this.summary = builder.summary;
        }

        public String getMyTeamName() {
            return myTeamName;
        }

        public int getChallengeId() {
            return challengeId;
        }

        public String getSummary() {
            return summary;
        }

        public static class Builder {
            private String myTeamName;
            private int challengeId;
            private String summary;

            public Builder setMyTeamName(String myTeamName) {
                this.myTeamName = myTeamName;
                return this;
            }

            public Builder setChallengeId(int challengeId) {
                this.challengeId = challengeId;
                return this;
            }

            public Builder setSummary(String summary) {
                this.summary = summary;
                return this;
            }

            public ChallengeProgress build() {
                return new ChallengeProgress(this);
            }
        }
    }
}
