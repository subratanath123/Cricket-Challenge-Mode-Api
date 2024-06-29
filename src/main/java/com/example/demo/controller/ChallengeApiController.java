package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.ChallengeModeData.Difficulty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class ChallengeApiController {


    @GetMapping("/challengeList")
    public ChallengeModeDataList getChallangeList(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                  @RequestParam(required = false, defaultValue = "30") int total,
                                                  @RequestParam String level) {

        // Build ChallengeDetails
        return new ChallengeModeDataList(Arrays.asList(
                        getChallengeModeData("BATTING", 101, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BATTING", 102, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BATTING", 103, Difficulty.EASY, "Level 1"),

                        getChallengeModeData("BATTING", 104, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", 105, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", 106, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", 107, Difficulty.MEDIUM, "Level 2"),

                        getChallengeModeData("BATTING", 108, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 109, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 110, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 111, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 112, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 113, Difficulty.HARD, "Level 3")
                )
                .stream()
                .filter(challengeModeData -> challengeModeData.getChallengeDetails().getLevel().equals(level))
                .collect(Collectors.toList())
        );


    }


    @GetMapping("/levelList")
    public LevelList getLevelsList() {

        // Build ChallengeDetails
        return new LevelList(Arrays.asList(
                "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"
        ));

    }


    private static ChallengeModeData getChallengeModeData(String challengeRole, int challengeId, Difficulty difficulty, String level) {
        ChallengeDetails challengeDetails = new ChallengeDetails.Builder()
                .setLevel(difficulty)
                .setLevel(level)
                .build();

        // Build BattingRole
        BattingRole battingRole = new BattingRole.Builder()
                .setWicketLimit(1)
                .setTargetRun(2)
                .setOverLimitation(1)
                .build();

        // Build BowlingRole
        BowlingRole bowlingRole = new BowlingRole.Builder()
                .setTargetRun(2)
                .setOverLimitation(1)
                .build();

        // Build Rewards
        Rewards rewards = new Rewards.Builder()
                .setXps(5)
                .setCoins(5)
                .setDiamonds(5)
                .setStars(5)
                .build();

        // Build ChallengeModeData
        ChallengeModeData challengeData = new ChallengeModeData.Builder()
                .setChallengeId(challengeId)
                .setChallengeTitle("Epic Challenge")
                .setMyTeam("BAN")
                .setOpponentTeam("IND")
                .setMatchType("T20")
                .setVenue("MUMB")
                .setChallengeRole(challengeRole)
                .setChallengeDetails(challengeDetails)
                .setBattingRole(battingRole)
                .setBowlingRole(bowlingRole)
                .setRewards(rewards)
                .build();
        return challengeData;
    }

}
