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

import static com.example.demo.dto.util.CacheUtils.getActiveSubscriptions;
import static com.example.demo.dto.util.CacheUtils.*;
import static com.example.demo.dto.util.ResourceUtils.consumableProducts;

@RestController
@Tag(name = "Coins Packs", description = "API endpoints for managing in-game coin packs. Coins are consumable virtual currency used for various in-game purchases and transactions. Players can buy coins in different pack sizes.")
public class CoinsPackController {

    @Operation(
        summary = "Get all available coin packs",
        description = "**Returns complete catalog of coin bundles.**\n\n" +
                      "**What You Get:**\n" +
                      "- All coin pack options with varying quantities\n" +
                      "- Different bundle sizes and price points\n" +
                      "- Special offers and bonus coins\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Consumable (can buy multiple times)\n" +
                      "- Coins are standard in-game currency\n" +
                      "- Used for purchases and transactions\n\n" +
                      "**Use Case:** Display coin shop bundles in your game UI."
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
        summary = "Get user's purchased coin packs",
        description = "**Returns coin pack purchase history for a user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Bonus coins from active subscriptions\n" +
                      "2. **Direct Purchases** → Coin packs bought through IAP\n\n" +
                      "**Important Note:**\n" +
                      "- This returns **purchase history**, not current coin balance\n" +
                      "- Each entry = one pack purchase transaction\n" +
                      "- Coins from these packs should be added to user's wallet\n\n" +
                      "**Use Case:** Track purchase history or verify completed transactions.\n\n" +
                      "💡 **Tip:** You need a separate wallet/balance system to track actual coin count."
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
                .filter(product -> getActiveSubscriptions(email)
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
