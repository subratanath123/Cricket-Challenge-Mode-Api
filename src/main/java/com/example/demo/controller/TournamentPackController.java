package com.example.demo.controller;

import com.example.demo.dto.subscription.NonConsumableProducts;
import com.example.demo.service.PurchaseHistoryService;
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

import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Tournament Packs", description = "API endpoints for managing tournament packs. Tournament packs are non-consumable in-game items that unlock special tournament modes, leagues, and championship events, providing access to exclusive competitive gameplay experiences.")
public class TournamentPackController {

    private final PurchaseHistoryService purchaseHistoryService;

    public TournamentPackController(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }


    @Operation(
        summary = "Get all available tournament packs",
        description = "**Returns complete catalog of tournament modes.**\n\n" +
                      "**What You Get:**\n" +
                      "- All tournament unlock options\n" +
                      "- Different competitive events (World Cup, T20 League, etc.)\n" +
                      "- Tournament details: type, rewards, features\n" +
                      "- Preview images and descriptions\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Non-consumable (one-time purchase)\n" +
                      "- Once purchased, owned permanently\n" +
                      "- Unlocks special tournament gameplay modes\n" +
                      "- Access to exclusive competitive events\n\n" +
                      "**Use Case:** Display tournament shop catalog in your game UI."
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
        summary = "Get user's purchased tournament packs",
        description = "**Returns all tournaments owned by a user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Free tournaments from premium subscriptions\n" +
                      "2. **Direct Purchases** → Tournaments bought through IAP\n\n" +
                      "**How It Works:**\n" +
                      "- Checks subscription for included tournaments\n" +
                      "- Adds directly purchased tournament packs\n" +
                      "- Returns complete list of owned tournament modes\n\n" +
                      "**Use Case:** Determine which tournament modes to unlock and make accessible.\n\n" +
                      "**Integration:** Call after login to enable owned tournaments in game mode selection."
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
                .filter(product -> purchaseHistoryService.getActiveSubscriptions(email)
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(purchaseHistoryService.getNonConsumablePurchases(email)
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("TournamentPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
