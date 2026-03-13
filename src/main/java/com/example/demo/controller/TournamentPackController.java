package com.example.demo.controller;

import com.example.demo.dto.subscription.NonConsumableProducts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Tournament Packs", description = "API endpoints for managing tournament packs. Tournament packs are non-consumable in-game items that unlock special tournament modes, leagues, and championship events, providing access to exclusive competitive gameplay experiences.")
public class TournamentPackController {


    @Operation(
        summary = "Get all available tournament packs",
        description = "Retrieves a complete list of all tournament packs available in the game store. " +
                      "This endpoint returns all tournament unlock options with different competitive events. " +
                      "Each pack contains details like: " +
                      "- Tournament name and type (e.g., World Cup, T20 League, Champions Trophy) " +
                      "- Price information " +
                      "- Tournament features and rewards " +
                      "- Preview images and descriptions. " +
                      "Tournament packs are non-consumable and remain unlocked permanently once purchased. " +
                      "Use this endpoint to display the tournament catalog in the game shop."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tournament packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tournamentPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("TournamentPacks"));
    }

    @Operation(
        summary = "Get purchased tournament packs for a user",
        description = "Retrieves all tournament packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Free tournaments included with active subscriptions (bonus tournament access from premium subscriptions) " +
                      "2. Tournament packs directly purchased by the user through in-app purchases. " +
                      "Use this to determine which tournament modes should be unlocked and accessible to the user. " +
                      "The response includes all tournament pack details for owned tournaments only."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased tournament packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tournamentPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased tournaments.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("TournamentPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("TournamentPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
