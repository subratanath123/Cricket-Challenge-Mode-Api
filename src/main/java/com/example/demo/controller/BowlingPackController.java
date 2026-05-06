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

import static com.example.demo.dto.util.CacheUtils.getActiveSubscriptions;
import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Bowling Packs", description = "API endpoints for managing bowling equipment packs. Bowling packs are non-consumable in-game items that provide players with different bowling equipment and ball options to enhance their cricket gameplay experience.")
public class BowlingPackController {

    @Operation(
        summary = "Get all available bowling packs",
        description = "**Returns complete catalog of bowling equipment packs.**\n\n" +
                      "**What You Get:**\n" +
                      "- All bowling packs available for purchase\n" +
                      "- Includes both free and paid options\n" +
                      "- Pack details: name, price, image URL, metadata\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Non-consumable (one-time purchase)\n" +
                      "- Once purchased, owned permanently\n" +
                      "- Unlocks bowling equipment and ball options\n\n" +
                      "**Use Case:** Display bowling equipment shop catalog in your game UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of bowling packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bowlingPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("BowlingPacks"));
    }

    @Operation(
        summary = "Get user's purchased bowling packs",
        description = "**Returns all bowling packs owned by a specific user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Free packs from active subscriptions\n" +
                      "2. **Direct Purchases** → Packs bought through in-app purchases\n\n" +
                      "**How It Works:**\n" +
                      "- Checks user's subscription for free products\n" +
                      "- Includes items from subscription's `freeProducts` array\n" +
                      "- Adds directly purchased bowling packs\n" +
                      "- Returns combined list of all owned items\n\n" +
                      "**Use Case:** Unlock bowling equipment in the game based on user ownership.\n\n" +
                      "**Integration:** Call this after user login to unlock owned bowling equipment."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased bowling packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bowlingPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased items.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("BowlingPacks"))
                .stream()
                .filter(product -> getActiveSubscriptions(email)
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.isDefault() || consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("BowlingPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }

}
