package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class ChallengeModeData {
    @JsonProperty("ChallengeId")
    private int challengeId;

    @JsonProperty("ChallengeTitle")
    private String challengeTitle;

    @Schema(description = "MyTeamName", example = "BAN", allowableValues = {"BAN", "ENG", "SA", "..."})
    @JsonProperty("MyTeamName")
    private String myTeam;

    @Schema(description = "OpponentTeamName", example = "BAN", allowableValues = {"BAN", "ENG", "SA", "..."})
    @JsonProperty("OpponentTeamName")
    private String opponentTeam;

    @JsonProperty("MatchTypeName")
    private String matchType;

    @Schema(description = "Venue", example = "MUMB", defaultValue = "MUMB", allowableValues = {"TRNTB", "MUMB", "HAML", "MELB", "SYL"})
    @JsonProperty("Venue")
    private String venue;

    @Schema(description = "ChallengeRoleName", example = "BATTING", defaultValue = "BATTING", allowableValues = {"BOWLING", "BATTING"})
    @JsonProperty("ChallengeRoleName")
    private String challengeRole;

    @JsonProperty("ChallengeDetails")
    private ChallengeDetails challengeDetails;

    @JsonProperty("BattingRole")
    private BattingRole battingRole;

    @JsonProperty("BowlingRole")
    private BowlingRole bowlingRole;

    @JsonProperty("Rewards")
    private Rewards rewards;

    private ChallengeModeData() {}

    public static class Builder {
        private final ChallengeModeData challengeModeData = new ChallengeModeData();

        public Builder setChallengeId(int challengeId) {
            challengeModeData.challengeId = challengeId;
            return this;
        }

        public Builder setChallengeTitle(String challengeTitle) {
            challengeModeData.challengeTitle = challengeTitle;
            return this;
        }

        public Builder setMyTeam(String myTeam) {
            challengeModeData.myTeam = myTeam;
            return this;
        }

        public Builder setOpponentTeam(String opponentTeam) {
            challengeModeData.opponentTeam = opponentTeam;
            return this;
        }

        public Builder setMatchType(String matchType) {
            challengeModeData.matchType = matchType;
            return this;
        }

        public Builder setVenue(String venue) {
            challengeModeData.venue = venue;
            return this;
        }

        public Builder setChallengeRole(String challengeRole) {
            challengeModeData.challengeRole = challengeRole;
            return this;
        }

        public Builder setChallengeDetails(ChallengeDetails challengeDetails) {
            challengeModeData.challengeDetails = challengeDetails;
            return this;
        }

        public Builder setBattingRole(BattingRole battingRole) {
            challengeModeData.battingRole = battingRole;
            return this;
        }

        public Builder setBowlingRole(BowlingRole bowlingRole) {
            challengeModeData.bowlingRole = bowlingRole;
            return this;
        }

        public Builder setRewards(Rewards rewards) {
            challengeModeData.rewards = rewards;
            return this;
        }

        public ChallengeModeData build() {
            return challengeModeData;
        }
    }

    // Getters
    public int getChallengeId() { return challengeId; }
    public String getChallengeTitle() { return challengeTitle; }
    public String getMyTeam() { return myTeam; }
    public String getOpponentTeam() { return opponentTeam; }
    public String getMatchType() { return matchType; }
    public String getVenue() { return venue; }
    public String getChallengeRole() { return challengeRole; }
    public ChallengeDetails getChallengeDetails() { return challengeDetails; }
    public BattingRole getBattingRole() { return battingRole; }
    public BowlingRole getBowlingRole() { return bowlingRole; }
    public Rewards getRewards() { return rewards; }


    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

}