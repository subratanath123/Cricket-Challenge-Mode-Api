package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
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
import java.util.Collection;
import java.util.List;

import static com.example.demo.dto.util.CacheUtils.*;
import static com.example.demo.dto.util.ResourceUtils.*;

@RestController
@Tag(name = "Purchase", description = "Universal purchase API endpoints for processing in-app purchases. Handles transactions for all product types including consumables (coins, diamonds), non-consumables (equipment, kits), and subscriptions. This is the main transaction endpoint that records purchases to user accounts.")
public class PurchaseController {

    @Operation(
        summary = "Purchase a subscription product",
        description = "Records a subscription purchase for a specific user. " +
                      "This endpoint specifically handles subscription-type products (monthly, yearly plans). " +
                      "When a subscription is purchased: " +
                      "1. The subscription is added to the user's subscription purchase history " +
                      "2. The user gains access to subscription benefits including free products " +
                      "3. Subscription metadata (expiry date, renewal info) is stored. " +
                      "Use this endpoint when processing subscription IAP completions from app stores. " +
                      "Returns 'purchased' on success."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription purchased successfully, returns 'purchased'"),
        @ApiResponse(responseCode = "400", description = "Invalid email or product ID"),
        @ApiResponse(responseCode = "404", description = "Product ID not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/purchase/subscription/{id}")
    public String purchaseConsumableProducts(
        @Parameter(
            description = "User's email address to record the purchase against. " +
                         "This identifies which user account should receive the subscription.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email,
        @Parameter(
            description = "Subscription product ID to purchase. " +
                         "This should match an ID from the /subscriptionPacks endpoint.",
            required = true,
            example = "monthly_premium_sub"
        )
        @PathVariable String id
    ) {

        List<ConsumableProducts> purchaseHistoryOrDefault = subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>());

        purchaseHistoryOrDefault
                .add(
                        subscriptionProducts
                                .get("Subscription")
                                .stream()
                                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                                .findFirst()
                                .orElse(null)
                );

        subscriptionPurchaseHistory.put(email, purchaseHistoryOrDefault);

        return "purchased";
    }


    @Operation(
        summary = "Universal purchase endpoint for any product",
        description = "Universal purchase endpoint that handles any type of product purchase (consumable, non-consumable, or subscription). " +
                      "This endpoint automatically detects the product type and routes the purchase accordingly: " +
                      "1. First checks consumable products (coins, diamonds) " +
                      "2. Then checks non-consumable products (equipment, kits, stadiums, etc.) " +
                      "3. Finally checks subscription products " +
                      "4. Records the purchase to the appropriate user history based on product type. " +
                      "Use this as a universal purchase handler when you don't want to call specific endpoints. " +
                      "Common use case: App store purchase callback can call this single endpoint with the product ID. " +
                      "Returns 'purchased' on success, 'failed' if product ID not found in any category."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase processed. Returns 'purchased' if successful, 'failed' if product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid email or product ID format"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/purchase/{id}")
    public String purchaseProducts(
        @Parameter(
            description = "User's email address to record the purchase against. " +
                         "This identifies which user account should receive the purchased item.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email,
        @Parameter(
            description = "Product ID to purchase. Can be any product type (consumable, non-consumable, subscription). " +
                         "The endpoint will automatically detect the product category and process accordingly.",
            required = true,
            example = "bat_pack_001"
        )
        @PathVariable String id
    ) {
        List<ConsumableProducts> purchaseHistoryOrDefault = consumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        ConsumableProducts matchedConsumableProduct = consumableProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedConsumableProduct != null) {
            purchaseHistoryOrDefault.add(matchedConsumableProduct);
            consumablePurchaseHistory.put(email, purchaseHistoryOrDefault);
            return "purchased";
        }

        List<NonConsumableProducts> nonConsumablePurchaseHistoryOrDefault = nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        NonConsumableProducts matchedNonConsumableProduct = nonConsumableProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(nonConsumableProducts -> nonConsumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedNonConsumableProduct != null) {
            nonConsumablePurchaseHistoryOrDefault.add(matchedNonConsumableProduct);
            nonConsumablePurchaseHistory.put(email, nonConsumablePurchaseHistoryOrDefault);
            return "purchased";
        }

        List<ConsumableProducts> subscriptionPurchaseHistoryOrDefault = subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>());

        ConsumableProducts matchedSubscriptionProduct = subscriptionProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedSubscriptionProduct != null) {
            subscriptionPurchaseHistoryOrDefault.add(matchedSubscriptionProduct);
            subscriptionPurchaseHistory.put(email, subscriptionPurchaseHistoryOrDefault);
            return "purchased";
        }


        return "failed";
    }

}
