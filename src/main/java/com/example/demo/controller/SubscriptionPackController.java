package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.subscriptionProducts;
import static java.util.Arrays.asList;

@RestController
@Tag(name = "Subscription Packs", description = "API endpoints for managing subscription plans. Subscriptions provide recurring benefits including free products, exclusive content, ad-free experience, and premium features. Subscriptions are time-based with expiry dates and renewal options.")
public class SubscriptionPackController {

    @Operation(
        summary = "Get all available subscription plans",
        description = "**Returns complete list of subscription options.**\n\n" +
                      "**What You Get:**\n" +
                      "- All subscription tiers (monthly, yearly, VIP, etc.)\n" +
                      "- Pricing and duration information\n" +
                      "- List of included free products (`freeProducts` array)\n" +
                      "- Expiry and renewal details\n" +
                      "- Special benefits and features\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Subscription (time-based recurring)\n" +
                      "- Auto-unlocks items in `freeProducts` array\n" +
                      "- Provides ongoing benefits while active\n" +
                      "- Check `expiryDate` to verify if still active\n\n" +
                      "**Use Case:** Display subscription options in shop."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of subscription plans"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/subscriptionPacks")
    public List<ConsumableProducts> get() {

        return new ArrayList<>(subscriptionProducts.get("Subscription"));
    }

    @Operation(
        summary = "Get user's active subscriptions",
        description = "**Returns all subscription purchases for a user.**\n\n" +
                      "**What You Get:**\n" +
                      "- All subscription purchase records\n" +
                      "- Purchase/activation dates\n" +
                      "- Expiry dates (check to see if still active)\n" +
                      "- List of free products included\n\n" +
                      "**How to Check if Active:**\n" +
                      "```\n" +
                      "if (subscription.expiryDate > currentDate) {\n" +
                      "  // Subscription is active\n" +
                      "  unlockFreeProducts(subscription.freeProducts);\n" +
                      "}\n" +
                      "```\n\n" +
                      "**Use Cases:**\n" +
                      "1. Check if user has active subscription\n" +
                      "2. Determine which free products to unlock\n" +
                      "3. Display subscription status in profile\n\n" +
                      "⚠️ **Important:** Always check `expiryDate` field to verify subscription is still active!"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user subscriptions"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/subscriptionPacks/purchased")
    public List<ConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their subscription history. " +
                         "This is used as the unique identifier to fetch user-specific subscriptions.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        return new ArrayList<>(subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>()));
    }

}
