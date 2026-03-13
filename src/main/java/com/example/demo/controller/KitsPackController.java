package com.example.demo.controller;

import com.example.demo.dto.kits.CardItem;
import com.example.demo.dto.subscription.NonConsumableProducts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.controller.GameAssetController.teamListForKits;
import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Kits Packs", description = "API endpoints for managing team kits and jerseys. Kits are non-consumable in-game items that allow players to customize their team's appearance with authentic cricket team jerseys and equipment.")
public class KitsPackController {

    @Operation(
        summary = "Get list of available teams for kits",
        description = "Retrieves a list of cricket teams for which kits are available in the game. " +
                      "This endpoint returns parent-level team categories with team codes (e.g., PAK, IND, AUS). " +
                      "Each team entry includes: " +
                      "- title: Team code (3-letter country code) " +
                      "- imageUrl: Team logo or representative image " +
                      "- description: Marketing description " +
                      "- nextUrl: Navigation path to view specific kits for that team. " +
                      "Use this endpoint to display the team selection screen before showing individual kit options. " +
                      "Available teams: Pakistan, India, Bangladesh, Zimbabwe, Australia, South Africa, Sri Lanka, New Zealand, West Indies."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of teams"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams")
    public List<CardItem> getAvailableTeamsForKits() {
        return teamListForKits;
    }

    @Operation(
        summary = "Get kits for a specific team",
        description = "Retrieves all available kit/jersey options for a specific cricket team. " +
                      "This endpoint filters kits based on the team code provided in the path parameter. " +
                      "Each kit includes details like: " +
                      "- Kit design (home jersey, away jersey, special editions) " +
                      "- Price information " +
                      "- Image URLs showing the kit appearance " +
                      "- Metadata identifying the team. " +
                      "Use this endpoint after a user selects a team from /kits/teams to show purchasable kit options."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved kits for the team"),
        @ApiResponse(responseCode = "404", description = "Team not found or no kits available"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams/{team}")
    public List<NonConsumableProducts> getTeamsKits(
        @Parameter(
            description = "Team code (3-letter country code) to fetch kits for. " +
                         "Examples: PAK (Pakistan), IND (India), AUS (Australia), BAN (Bangladesh), etc.",
            required = true,
            example = "IND"
        )
        @PathVariable String team
    ) {

        return nonConsumableProducts
                .get("Kits")
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getMetaInfo().equals(team))
                .collect(Collectors.toList());
    }

    @Operation(
        summary = "Get purchased kits for a user",
        description = "Retrieves all team kits that have been purchased by a specific user across all teams. " +
                      "This endpoint combines two sources: " +
                      "1. Free kits included with active subscriptions (bonus kits from premium subscriptions) " +
                      "2. Kits directly purchased by the user through in-app purchases. " +
                      "Use this to determine which team jerseys/kits should be unlocked and available for the user in-game. " +
                      "The response includes kits from all teams that the user owns."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased kits"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased kits.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {

        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("Kits"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("Kits"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }


}
