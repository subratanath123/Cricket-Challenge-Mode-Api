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
    public ChallengeModeDataList getChallangeList(@RequestParam String level, @RequestParam(defaultValue = "BAN") String myTeam) {

        // Build ChallengeDetails
        return new ChallengeModeDataList(Arrays.asList(
                        getChallengeModeData("BATTING", myTeam, "IND", 12, 101, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BATTING", myTeam, "PAK", 15, 102, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BOWLING", myTeam, "AFG", 16, 103, Difficulty.EASY, "Level 1"),
                        getChallengeModeData("BOWLING", myTeam, "WI", 18, 104, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BOWLING", myTeam, "NZ", 20, 105, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", myTeam, "AUS", 27, 106, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", myTeam, "BAN", 23, 107, Difficulty.MEDIUM, "Level 2"),
                        getChallengeModeData("BATTING", myTeam, "IRE", 30, 108, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", myTeam, "SA", 21, 109, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", myTeam, "ENG", 28, 110, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", myTeam, "PAK", 26, 111, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BOWLING", myTeam, "NZ", 29, 112, Difficulty.HARD, "Level 3"),
                        getChallengeModeData("BATTING", myTeam, "WI", 33, 113, Difficulty.HARD, "Level 3")
                )
                .stream()
                .filter(challengeModeData -> challengeModeData.getChallengeDetails().getLevel().equals(level))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/progress")
    public MyChallengeLevelProgress saveProgress(@RequestParam(defaultValue = "Level 1") String level, @RequestParam(defaultValue = "BAN") String myTeam) {

        int completedChallengeID = 101;

        if (level.equals("Level 1")) {
            completedChallengeID = 101;

        } else if (level.equals("Level 2")) {
            completedChallengeID = 104;

        } else if (level.equals("Level 3")) {
            completedChallengeID = 108;
        }

        MyChallengeLevelProgress.ChallengeProgress challenge1 = new MyChallengeLevelProgress.ChallengeProgress.Builder()
                .setMyTeamName(myTeam)
                .setChallengeId(completedChallengeID)
                .setSummary("Challenge Completed")
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

    private static ChallengeModeData getChallengeModeData(String challengeRole, String myTeam, String team, int run, int challengeId, Difficulty difficulty, String level) {
        ChallengeDetails challengeDetails = new ChallengeDetails.Builder()
                .setLevel(difficulty)
                .setLevel(level)
                .build();

        // Build BattingRole
        BattingRole battingRole = new BattingRole.Builder()
                .setWicketLimit(1)
                .setTargetRun(run)
                .setOverLimitation(1)
                .build();

        // Build BowlingRole
        BowlingRole bowlingRole = new BowlingRole.Builder()
                .setTargetRun(run)
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
                .setMyTeam(myTeam)
                .setOpponentTeam(team)
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
