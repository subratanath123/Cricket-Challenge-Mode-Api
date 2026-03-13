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
        description = "Retrieves a complete list of all subscription plan options available for purchase. " +
                      "This endpoint returns different subscription tiers (e.g., monthly, yearly, premium, VIP). " +
                      "Each subscription includes details like: " +
                      "- Plan name and duration " +
                      "- Pricing information " +
                      "- List of free products/perks included (freeProducts array) " +
                      "- Expiry date and renewal information " +
                      "- Special benefits and features. " +
                      "Subscriptions are recurring purchases that provide ongoing benefits. " +
                      "The 'freeProducts' field lists IDs of items automatically unlocked with the subscription. " +
                      "Use this endpoint to display subscription options in the game shop."
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
        summary = "Get active subscriptions for a user",
        description = "Retrieves all subscription purchases for a specific user. " +
                      "This endpoint returns the user's subscription history and active subscriptions. " +
                      "Each subscription entry includes: " +
                      "- Subscription plan details " +
                      "- Purchase/activation date " +
                      "- Expiry date (check this to determine if subscription is still active) " +
                      "- List of free products included (freeProducts). " +
                      "Use this endpoint to: " +
                      "1. Check if user has an active subscription (verify expiry date) " +
                      "2. Determine which free products the user should have access to " +
                      "3. Display subscription status in user profile. " +
                      "Note: You need to check the expiryDate field to determine if subscription is currently active."
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
