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
@Tag(name = "Diamonds Packs", description = "API endpoints for managing diamond packs. Diamonds are premium consumable virtual currency used for exclusive in-game purchases and high-value transactions. Players can buy diamonds in different pack sizes.")
public class DiamondsPackController {

    @Operation(
        summary = "Get all available diamond packs",
        description = "**Returns complete catalog of premium diamond bundles.**\n\n" +
                      "**What You Get:**\n" +
                      "- All diamond pack options with varying quantities\n" +
                      "- Premium bundle sizes and pricing\n" +
                      "- Exclusive bonus diamonds and special deals\n\n" +
                      "**Product Information:**\n" +
                      "- Category: Consumable (can buy multiple times)\n" +
                      "- Diamonds are **premium currency**\n" +
                      "- Used for exclusive/high-value purchases\n" +
                      "- Better value than regular coins\n\n" +
                      "**Use Case:** Display premium diamond shop in your game UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of diamond packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/diamondPacks")
    public List<ConsumableProducts> get() {

        return new ArrayList<>(consumableProducts
                .get("DiamondPacks"));
    }

    @Operation(
        summary = "Get user's purchased diamond packs",
        description = "**Returns diamond pack purchase history for a user.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Bonus diamonds from active subscriptions\n" +
                      "2. **Direct Purchases** → Diamond packs bought through IAP\n\n" +
                      "**Important Note:**\n" +
                      "- This returns **purchase history**, not current diamond balance\n" +
                      "- Each entry = one pack purchase transaction\n" +
                      "- Diamonds from these packs should be added to user's wallet\n\n" +
                      "**Use Cases:**\n" +
                      "- Track premium currency purchase history\n" +
                      "- Verify completed transactions\n" +
                      "- Audit premium purchases\n\n" +
                      "💡 **Tip:** Implement a separate wallet system to track actual diamond balance."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased diamond packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/diamondPacks/purchased")
    public List<ConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific diamond pack purchases.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<ConsumableProducts> purchasedProducts = new ArrayList<>(consumableProducts.get("DiamondPacks"))
                .stream()
                .filter(product -> getActiveSubscriptions(email)
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(consumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("DiamondPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }

}
