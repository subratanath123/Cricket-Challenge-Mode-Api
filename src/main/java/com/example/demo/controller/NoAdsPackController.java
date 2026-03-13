package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
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

import static com.example.demo.dto.util.CacheUtils.*;
import static com.example.demo.dto.util.ResourceUtils.consumableProducts;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "No Ads Packs", description = "API endpoints for managing ad-removal packs. No Ads packs are non-consumable in-app purchases that permanently remove advertisements from the game, providing an uninterrupted gaming experience.")
public class NoAdsPackController {

    @Operation(
        summary = "Get all available no-ads packs",
        description = "**Returns ad-removal pack options.**\n\n" +
                      "**What You Get:**\n" +
                      "- Different tiers of ad removal options\n" +
                      "- Basic to premium ad-free experiences\n" +
                      "- Scope details (which ads are removed)\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Non-consumable (one-time purchase)\n" +
                      "- Once purchased, owned permanently\n" +
                      "- Removes advertisements from gameplay\n" +
                      "- Provides uninterrupted gaming experience\n\n" +
                      "**Use Case:** Display ad-removal options in shop."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of no-ads packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/noAdsPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("NoAdsPacks"));
    }

    @Operation(
        summary = "Check if user has ad-removal",
        description = "**Returns user's ad-removal purchases.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Ad-free with premium subscriptions\n" +
                      "2. **Direct Purchases** → No-ads packs bought through IAP\n\n" +
                      "**How to Use:**\n" +
                      "```\n" +
                      "if (response.length > 0) {\n" +
                      "  // User has ad-free access\n" +
                      "  hideAllAds();\n" +
                      "} else {\n" +
                      "  // Show ads\n" +
                      "  displayAds();\n" +
                      "}\n" +
                      "```\n\n" +
                      "**Use Case:** Determine whether to show ads during gameplay.\n\n" +
                      "**Integration:** Call on app startup to configure ad display settings."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased no-ads packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/noAdsPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to determine if the user has purchased ad removal.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("NoAdsPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());

        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("NoAdsPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
