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
@Tag(name = "Batting Packs", description = "API endpoints for managing batting equipment packs. Batting packs are non-consumable in-game items that provide players with different batting equipment options (bats, gloves, etc.) to enhance their cricket gameplay experience.")
public class BattingPackController {

    @Operation(
        summary = "Get all available batting packs",
        description = "Retrieves a complete list of all batting packs available in the game store. " +
                      "This endpoint returns all batting equipment packs regardless of purchase status. " +
                      "Each pack contains details like pack name, price, image URL, and metadata. " +
                      "Use this endpoint to display the batting packs catalog in the game shop."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of batting packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/battingPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("BattingPacks"));
    }

    @Operation(
        summary = "Get purchased batting packs for a user",
        description = "Retrieves all batting packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Free batting packs included with active subscriptions (checks if subscription includes free products) " +
                      "2. Batting packs directly purchased by the user through in-app purchases. " +
                      "Use this to unlock batting equipment in the game based on user ownership. " +
                      "The response includes all pack details for owned items only."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased batting packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/battingPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased items.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("BattingPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.isDefault() || consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("BattingPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }

}
