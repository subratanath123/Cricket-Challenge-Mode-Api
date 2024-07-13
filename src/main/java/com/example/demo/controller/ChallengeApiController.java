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
    public ChallengeModeDataList getChallangeList(@RequestParam String level) {

        // Build ChallengeDetails
        return new ChallengeModeDataList(Arrays.asList(
                        getChallengeModeData("BATTING", 101, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BATTING", 102, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BOWLING", 103, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BOWLING", 103, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BATTING", 103, Difficulty.EASY, "Level 1"),

                        getChallengeModeData("BOWLING", 104, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BOWLING", 105, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", 106, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", 107, Difficulty.MEDIUM, "Level 2"),

                        getChallengeModeData("BATTING", 108, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", 109, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 110, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", 111, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", 112, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", 113, Difficulty.HARD, "Level 3")
                )
                .stream()
                .filter(challengeModeData -> challengeModeData.getChallengeDetails().getLevel().equals(level))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/progress")
    public MyChallengeLevelProgress saveProgress(@RequestParam(defaultValue = "1") String level, @RequestParam(defaultValue = "BAN") String myTeam) {

        int completedChallengeID = 101;

        if (level.equals("1")) {
            completedChallengeID = 101;

        } else if (level.equals("2")) {
            completedChallengeID = 104;

        } else if (level.equals("3")) {
            completedChallengeID = 108;
        }

        MyChallengeLevelProgress.ChallengeProgress challenge1 = new MyChallengeLevelProgress.ChallengeProgress.Builder()
                .setMyTeamName(myTeam)
                .setChallengeId(completedChallengeID)
                .setSummary("Complete the first challenge")
                .build();

        MyChallengeLevelProgress myChallengeLevelProgress = new MyChallengeLevelProgress.Builder()
                .setLevel("Beginner")
                .setChallengeProgress(Arrays.asList(challenge1))
                .build();

        return myChallengeLevelProgress;
    }

    @GetMapping("/progress/update")
    public String saveProgress(@RequestParam(defaultValue = "1") String level,
                                                 @RequestParam int challengeId,
                                                 @RequestParam String summary) {
        //not saving anything in DB. this is just a dummy rest api
        return "saved";
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
