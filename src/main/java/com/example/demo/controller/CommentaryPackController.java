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
@Tag(name = "Commentary Packs", description = "API endpoints for managing commentary packs. Commentary packs are non-consumable in-game items that unlock different commentary voice options and styles to enhance the audio experience during cricket matches.")
public class CommentaryPackController {


    @Operation(
        summary = "Get all available commentary packs",
        description = "**Returns complete catalog of commentary voice options.**\n\n" +
                      "**What You Get:**\n" +
                      "- All commentary packs with different commentators\n" +
                      "- Various languages and commentary styles\n" +
                      "- Preview audio URLs and descriptions\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Non-consumable (one-time purchase)\n" +
                      "- Once purchased, owned permanently\n" +
                      "- Unlocks additional commentary voices\n" +
                      "- Enhances audio experience during matches\n\n" +
                      "**Use Case:** Display commentary options shop in your game UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of commentary packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/commentaryPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("CommentaryPacks"));
    }

    @Operation(
        summary = "Get user's purchased commentary packs",
        description = "**Returns all commentary packs owned by a user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Free commentary from premium subscriptions\n" +
                      "2. **Direct Purchases** → Commentary packs bought through IAP\n\n" +
                      "**How It Works:**\n" +
                      "- Checks subscription for included commentary\n" +
                      "- Adds directly purchased commentary packs\n" +
                      "- Returns complete list of owned commentary options\n\n" +
                      "**Use Case:** Determine which commentary voices to unlock in the game.\n\n" +
                      "**Integration:** Call after login to enable owned commentary options in settings."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased commentary packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/commentaryPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased commentary packs.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("CommentaryPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("CommentaryPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
