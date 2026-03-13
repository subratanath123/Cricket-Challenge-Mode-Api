package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.ChallengeModeData.Difficulty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Challenge Mode", description = "API endpoints for the Challenge Mode game feature. Challenge Mode offers structured single-player challenges with specific objectives (batting/bowling targets) across different difficulty levels. Players progress through levels by completing challenges to earn rewards.")
public class ChallengeApiController {


    @Operation(
        summary = "Get challenge list filtered by level",
        description = "Retrieves a list of cricket challenges for a specific difficulty level. " +
                      "This endpoint filters challenges based on the requested level parameter. " +
                      "Each challenge includes: " +
                      "- Challenge ID and title " +
                      "- Challenge role (BATTING or BOWLING) " +
                      "- Your team vs opponent team " +
                      "- Match type and venue " +
                      "- Difficulty level (EASY, MEDIUM, HARD) " +
                      "- Specific objectives: " +
                      "  * Batting: Target runs, wicket limit, over limitation " +
                      "  * Bowling: Target to defend, over limitation " +
                      "- Rewards (XPs, coins, diamonds, stars). " +
                      "Available levels: 'Level 1', 'Level 2', 'Level 3', etc. " +
                      "Use this endpoint to display challenges for a specific level in the game UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved challenge list"),
        @ApiResponse(responseCode = "400", description = "Invalid level parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/challengeList")
    public ChallengeModeDataList getChallangeList(
        @Parameter(
            description = "Challenge level to filter by. Determines the difficulty tier of challenges returned. " +
                         "Each level contains multiple challenges with increasing difficulty.",
            required = true,
            example = "Level 1"
        )
        @RequestParam String level,
        @Parameter(
            description = "Your team code (3-letter country code) to use in the challenges. " +
                         "Defaults to 'BAN' (Bangladesh) if not specified.",
            required = false,
            example = "IND"
        )
        @RequestParam(defaultValue = "BAN") String myTeam
    ) {

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

    @Operation(
        summary = "Get challenge progress for a level",
        description = "Retrieves the challenge completion progress for a specific level. " +
                      "This endpoint returns mock/sample progress data showing which challenges have been completed. " +
                      "Progress includes: " +
                      "- Level name " +
                      "- Completed challenge IDs " +
                      "- Challenge summaries. " +
                      "The API currently returns hardcoded sample data based on the level parameter: " +
                      "- Level 1: Challenge ID 101 completed " +
                      "- Level 2: Challenge ID 104 completed " +
                      "- Level 3: Challenge ID 108 completed. " +
                      "Use this endpoint to display user's progress within a challenge level."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved challenge progress"),
        @ApiResponse(responseCode = "400", description = "Invalid level parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/progress")
    public MyChallengeLevelProgress saveProgress(
        @Parameter(
            description = "Challenge level to get progress for. Defaults to 'Level 1'.",
            required = false,
            example = "Level 2"
        )
        @RequestParam(defaultValue = "Level 1") String level,
        @Parameter(
            description = "Your team code (3-letter country code). Defaults to 'BAN' (Bangladesh).",
            required = false,
            example = "IND"
        )
        @RequestParam(defaultValue = "BAN") String myTeam
    ) {

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

    @Operation(
        summary = "Update/save challenge progress",
        description = "Records the completion of a challenge. " +
                      "This endpoint is called when a player completes a challenge to save their progress. " +
                      "Parameters include: " +
                      "- level: The challenge level (e.g., '1', '2', '3') " +
                      "- challengeId: Unique identifier of the completed challenge " +
                      "- summary: Summary or result of the challenge completion (e.g., 'Won by 5 wickets'). " +
                      "NOTE: This is currently a mock endpoint that doesn't persist data to a database. " +
                      "It always returns 'saved' as a success indicator. " +
                      "In production, this should be implemented to save progress to persistent storage."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progress saved successfully, returns 'saved'"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/progress/update")
    public String saveProgress(
        @Parameter(
            description = "Challenge level identifier. Defaults to '1'.",
            required = false,
            example = "2"
        )
        @RequestParam(defaultValue = "1") String level,
        @Parameter(
            description = "Challenge ID that was completed.",
            required = true,
            example = "105"
        )
        @RequestParam int challengeId,
        @Parameter(
            description = "Summary or result description of the challenge completion.",
            required = true,
            example = "Won by 6 wickets with 2 balls remaining"
        )
        @RequestParam String summary
    ) {
        return "saved";
    }

    @Operation(
        summary = "Get list of all available challenge levels",
        description = "Retrieves a complete list of all challenge levels available in the game. " +
                      "This endpoint returns level identifiers that can be used with other challenge endpoints. " +
                      "Currently returns 7 levels: 'Level 1' through 'Level 7'. " +
                      "Use this endpoint to: " +
                      "1. Display level selection menu " +
                      "2. Build challenge navigation UI " +
                      "3. Determine how many levels exist in the game."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved level list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/levelList")
    public LevelList getLevelsList() {

        return new LevelList(Arrays.asList(
                "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"
        ));

    }

    private static ChallengeModeData getChallengeModeData(String challengeRole, String myTeam, String team, int run, int challengeId, Difficulty difficulty, String level) {
        ChallengeDetails challengeDetails = new ChallengeDetails.Builder()
                .setLevel(difficulty)
                .setLevel(level)
                .build();

        BattingRole battingRole = new BattingRole.Builder()
                .setWicketLimit(1)
                .setTargetRun(run)
                .setOverLimitation(3)
                .build();

        BowlingRole bowlingRole = new BowlingRole.Builder()
                .setTargetRun(run)
                .setOverLimitation(4)
                .build();

        Rewards rewards = new Rewards.Builder()
                .setXps(5)
                .setCoins(5)
                .setDiamonds(5)
                .setStars(5)
                .build();

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
