package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.ChallengeModeData.Difficulty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Challenge Mode", description = "API endpoints for the Challenge Mode game feature. Challenge Mode offers structured single-player challenges with specific objectives (batting/bowling targets) across different difficulty levels. Players progress through levels by completing challenges to earn rewards.")
public class ChallengeApiController {
    private static final String CHALLENGE_DATA_URL = "https://cwapi.flyhr.net/IpJson/ChallengeModeData.json";
    private static final String CHALLENGE_LEVELS_URL = "https://cwapi.flyhr.net/IpJson/ChallengesLevel.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @Operation(
        summary = "Get challenge list filtered by level",
        description = "**Returns cricket challenges for a specific difficulty level.**\n\n" +
                      "**Each Challenge Contains:**\n" +
                      "- Challenge ID and title\n" +
                      "- Role: BATTING or BOWLING\n" +
                      "- Teams: Your team vs opponent\n" +
                      "- Match details: Type (T20) and venue\n" +
                      "- Difficulty: EASY, MEDIUM, or HARD\n\n" +
                      "**Challenge Objectives:**\n" +
                      "- **Batting Challenges:** Score target runs within wicket limit and overs\n" +
                      "- **Bowling Challenges:** Defend target runs within over limitation\n\n" +
                      "**Rewards:**\n" +
                      "- XP points for progression\n" +
                      "- Coins and Diamonds\n" +
                      "- Stars for completion\n\n" +
                      "**Available Levels:** 'Level 1', 'Level 2', 'Level 3', etc.\n\n" +
                      "**Use Case:** Display challenges when user selects a level from the challenge menu."
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

        return new ChallengeModeDataList(getChallengeModeDataWithFallback(myTeam)
                .stream()
                .filter(challengeModeData -> challengeModeData.getChallengeDetails().getLevel().equals(level))
                .collect(Collectors.toList())
        );
    }

    private List<ChallengeModeData> getChallengeModeDataWithFallback(String myTeam) {
        try (InputStream in = new URL(CHALLENGE_DATA_URL).openStream()) {
            ChallengeModeDataList challengeModeDataList = OBJECT_MAPPER.readValue(in, ChallengeModeDataList.class);
            return challengeModeDataList.getChallengeModeDataList()
                    .stream()
                    .map(challengeModeData -> {
                        String effectiveMyTeam = challengeModeData.getMyTeam();
                        if (effectiveMyTeam == null || effectiveMyTeam.isBlank()) {
                            effectiveMyTeam = myTeam;
                        }
                        return cloneChallengeWithMyTeam(challengeModeData, effectiveMyTeam);
                    })
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return getHardcodedChallengeModeData(myTeam);
        }
    }

    private List<ChallengeModeData> getHardcodedChallengeModeData(String myTeam) {
        return Arrays.asList(
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
        );
    }

    @Operation(
        summary = "Get challenge progress for a level",
        description = "**Retrieves challenge completion progress for a specific level.**\n\n" +
                      "**Progress Data Includes:**\n" +
                      "- Level name (e.g., 'Beginner')\n" +
                      "- List of completed challenge IDs\n" +
                      "- Summary text for each completion\n\n" +
                      "**Sample Data Returned:**\n" +
                      "- Level 1 → Challenge ID 101 marked complete\n" +
                      "- Level 2 → Challenge ID 104 marked complete\n" +
                      "- Level 3 → Challenge ID 108 marked complete\n\n" +
                      "**Use Case:** Display user's progress and unlocked challenges in the UI.\n\n" +
                      "⚠️ **Note:** Currently returns mock/hardcoded data. Implement database persistence for production."
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
        summary = "Save challenge completion",
        description = "**Records when a player completes a challenge.**\n\n" +
                      "**Required Data:**\n" +
                      "- `level`: Challenge level (1, 2, 3, etc.)\n" +
                      "- `challengeId`: ID of the completed challenge\n" +
                      "- `summary`: Result description (e.g., 'Won by 5 wickets', 'All wickets taken')\n\n" +
                      "**Response:**\n" +
                      "- Returns: `\"saved\"` on success\n\n" +
                      "**When to Call:**\n" +
                      "Call this immediately after a player successfully completes a challenge to record their progress.\n\n" +
                      "⚠️ **Important:** This is currently a mock endpoint. Always returns 'saved' but doesn't persist to database. " +
                      "Implement database storage for production use."
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
        summary = "Get all available challenge levels",
        description = "**Returns list of all challenge levels in the game.**\n\n" +
                      "**Returns:**\n" +
                      "Array of level names: `['Level 1', 'Level 2', 'Level 3', 'Level 4', 'Level 5', 'Level 6', 'Level 7']`\n\n" +
                      "**Use Cases:**\n" +
                      "1. Build level selection menu in the challenge mode UI\n" +
                      "2. Determine total number of available levels\n" +
                      "3. Validate level parameters before calling other endpoints\n\n" +
                      "**Integration:** Call this once on challenge mode initialization to build your navigation."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved level list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/levelList")
    public LevelList getLevelsList() {
        try (InputStream in = new URL(CHALLENGE_LEVELS_URL).openStream()) {
            return OBJECT_MAPPER.readValue(in, LevelList.class);
        } catch (Exception ex) {
            return new LevelList(Arrays.asList(
                    "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"
            ));
        }
    }

    private ChallengeModeData cloneChallengeWithMyTeam(ChallengeModeData challengeModeData, String myTeam) {
        return new ChallengeModeData.Builder()
                .setChallengeId(challengeModeData.getChallengeId())
                .setChallengeTitle(challengeModeData.getChallengeTitle())
                .setMyTeam(myTeam)
                .setOpponentTeam(challengeModeData.getOpponentTeam())
                .setMatchType(challengeModeData.getMatchType())
                .setVenue(challengeModeData.getVenue())
                .setChallengeRole(challengeModeData.getChallengeRole())
                .setChallengeDetails(challengeModeData.getChallengeDetails())
                .setBattingRole(challengeModeData.getBattingRole())
                .setBowlingRole(challengeModeData.getBowlingRole())
                .setRewards(challengeModeData.getRewards())
                .build();
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
