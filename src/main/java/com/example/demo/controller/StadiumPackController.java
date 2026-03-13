package com.example.demo.controller;

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
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Stadium Packs", description = "API endpoints for managing stadium/venue packs. Stadium packs are non-consumable in-game items that unlock different cricket venues and grounds where matches can be played, enhancing the visual variety and immersion of the game.")
public class StadiumPackController {


    @Operation(
        summary = "Get all available stadium packs",
        description = "**Returns complete catalog of cricket venues/stadiums.**\n\n" +
                      "**What You Get:**\n" +
                      "- All stadium unlock options\n" +
                      "- Iconic cricket grounds worldwide\n" +
                      "- Stadium details: name, location, capacity\n" +
                      "- Preview images and special features\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Non-consumable (one-time purchase)\n" +
                      "- Once purchased, owned permanently\n" +
                      "- Unlocks venue for match selection\n" +
                      "- Adds visual variety to gameplay\n\n" +
                      "**Use Case:** Display stadium shop catalog in your game UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of stadium packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stadiumPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("StadiumPacks"));
    }

    @Operation(
        summary = "Get user's purchased stadium packs",
        description = "**Returns all stadiums owned by a user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Free stadiums from premium subscriptions\n" +
                      "2. **Direct Purchases** → Stadiums bought through IAP\n\n" +
                      "**How It Works:**\n" +
                      "- Checks subscription for included stadiums\n" +
                      "- Adds directly purchased stadium packs\n" +
                      "- Returns complete list of owned venues\n\n" +
                      "**Use Case:** Determine which stadiums to unlock for match selection.\n\n" +
                      "**Integration:** Call after login to populate available venue list in match setup."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased stadium packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stadiumPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased stadiums.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("StadiumPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("StadiumPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
