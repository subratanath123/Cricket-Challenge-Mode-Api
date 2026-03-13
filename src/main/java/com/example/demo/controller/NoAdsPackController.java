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
        description = "Retrieves a complete list of all ad-removal pack options available for purchase. " +
                      "This endpoint returns different tiers of ad-removal options (e.g., basic ad removal, premium ad-free experience). " +
                      "Each pack contains details like: " +
                      "- Pack name and description " +
                      "- Price information " +
                      "- Scope of ad removal (specific ad types or all ads). " +
                      "No Ads packs are non-consumable purchases that persist permanently once bought. " +
                      "Use this endpoint to display ad-removal options in the game shop."
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
        summary = "Get purchased no-ads packs for a user",
        description = "Retrieves all ad-removal packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Ad-removal included with active subscriptions (premium users typically get ad-free experience) " +
                      "2. No-ads packs directly purchased by the user through in-app purchases. " +
                      "Use this to determine whether to show ads to the user during gameplay. " +
                      "If this endpoint returns any items, the user should have an ad-free experience. " +
                      "The response includes all ad-removal pack details for owned items."
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
