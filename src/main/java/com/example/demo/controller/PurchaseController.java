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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.example.demo.dto.util.CacheUtils.*;
import static com.example.demo.dto.util.ResourceUtils.*;

@RestController
@Tag(name = "Purchase", description = "Universal purchase API endpoints for processing in-app purchases. Handles transactions for all product types including consumables (coins, diamonds), non-consumables (equipment, kits), and subscriptions. This is the main transaction endpoint that records purchases to user accounts.")
public class PurchaseController {

    @Operation(
        summary = "Purchase a subscription",
        description = "**Records a subscription purchase for a user.**\n\n" +
                      "**What This Does:**\n" +
                      "1. Adds subscription to user's purchase history\n" +
                      "2. User gains access to subscription benefits\n" +
                      "3. Free products auto-unlock (from `freeProducts` array)\n" +
                      "4. Subscription expiry tracking begins\n\n" +
                      "**When to Call:**\n" +
                      "After successful in-app purchase (IAP) completion from app store.\n\n" +
                      "**Response:** Returns `\"purchased\"` on success.\n\n" +
                      "**Integration Flow:**\n" +
                      "```\n" +
                      "1. User taps subscribe in app\n" +
                      "2. App store processes payment → Success\n" +
                      "3. Call this endpoint with subscription ID\n" +
                      "4. Subscription benefits activate immediately\n" +
                      "```"
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

        ConsumableProducts matchedSubscriptionProduct = subscriptionProducts
                .get("Subscription")
                .stream()
                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedSubscriptionProduct == null) {
            return "failed";
        }

        purchaseHistoryOrDefault.add(createPurchasedSubscription(matchedSubscriptionProduct));

        subscriptionPurchaseHistory.put(email, purchaseHistoryOrDefault);

        return "purchased";
    }


    @Operation(
        summary = "Universal purchase endpoint",
        description = "**Handles ANY product purchase - consumable, non-consumable, or subscription.**\n\n" +
                      "**How It Works:**\n" +
                      "1. Searches consumable products (coins, diamonds)\n" +
                      "2. If not found → searches non-consumable (equipment, stadiums, kits)\n" +
                      "3. If not found → searches subscriptions\n" +
                      "4. Records purchase in appropriate history\n\n" +
                      "**Returns:**\n" +
                      "- `\"purchased\"` → Success (product found and recorded)\n" +
                      "- `\"failed\"` → Product ID not found in any category\n\n" +
                      "**Use Case:** Single purchase handler for all product types.\n\n" +
                      "**Integration Flow:**\n" +
                      "```\n" +
                      "1. User selects any product in shop\n" +
                      "2. App store completes payment → Success\n" +
                      "3. Call: /purchase/{productId}?email=user@example.com\n" +
                      "4. Check response: 'purchased' or 'failed'\n" +
                      "5. Update UI to show owned item\n" +
                      "```\n\n" +
                      "💡 **Tip:** Use this instead of category-specific endpoints for simpler integration."
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
            subscriptionPurchaseHistoryOrDefault.add(createPurchasedSubscription(matchedSubscriptionProduct));
            subscriptionPurchaseHistory.put(email, subscriptionPurchaseHistoryOrDefault);
            return "purchased";
        }


        return "failed";
    }

    private ConsumableProducts createPurchasedSubscription(ConsumableProducts template) {
        Date expiryDate = Date.from(Instant.now().plus(365, ChronoUnit.DAYS));
        ConsumableProducts purchasedSubscription = new ConsumableProducts(
                template.getId(),
                template.getCategory(),
                template.getPackName(),
                template.getMetaInfo(),
                template.getPrice(),
                expiryDate,
                template.getInformation(),
                template.getObjectType(),
                template.getImageUrl(),
                template.getFreeProducts(),
                template.getNextUrl(),
                template.getPurchaseId(),
                template.getPayload(),
                template.isDefault()
        );
        purchasedSubscription.setAlertInformation(template.getAlertInformation());
        purchasedSubscription.setFlipInformation(template.getFlipInformation());
        return purchasedSubscription;
    }

}
