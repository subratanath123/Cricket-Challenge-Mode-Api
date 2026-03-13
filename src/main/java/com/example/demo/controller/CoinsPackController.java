package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
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

@RestController
@Tag(name = "Coins Packs", description = "API endpoints for managing in-game coin packs. Coins are consumable virtual currency used for various in-game purchases and transactions. Players can buy coins in different pack sizes.")
public class CoinsPackController {

    @Operation(
        summary = "Get all available coins packs",
        description = "Retrieves a complete list of all coins packs available for purchase in the game store. " +
                      "This endpoint returns all coin bundle options with varying quantities and prices. " +
                      "Each pack contains details like the number of coins, price, image URL, and special offers. " +
                      "Coins are consumable items that can be spent in-game. " +
                      "Use this endpoint to display the coins shop in the game."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of coin packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/coinsPacks")
    public List<ConsumableProducts> get() {

        return new ArrayList<>(consumableProducts
                .get("CoinsPacks"));
    }

    @Operation(
        summary = "Get purchased coins packs for a user",
        description = "Retrieves all coins packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Free coins included with active subscriptions (bonus coins from subscription perks) " +
                      "2. Coins packs directly purchased by the user through in-app purchases. " +
                      "Note: This returns purchase history, not the current coin balance. " +
                      "Each entry represents a pack purchase transaction. " +
                      "Use this for showing purchase history or verifying completed transactions."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased coin packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/coinsPacks/purchased")
    public List<ConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific coin pack purchases.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<ConsumableProducts> purchasedProducts = new ArrayList<>(consumableProducts.get("CoinsPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(consumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("CoinsPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }

}
