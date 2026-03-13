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
@Tag(name = "Diamonds Packs", description = "API endpoints for managing diamond packs. Diamonds are premium consumable virtual currency used for exclusive in-game purchases and high-value transactions. Players can buy diamonds in different pack sizes.")
public class DiamondsPackController {

    @Operation(
        summary = "Get all available diamond packs",
        description = "Retrieves a complete list of all diamond packs available for purchase in the game store. " +
                      "This endpoint returns all diamond bundle options with varying quantities and prices. " +
                      "Each pack contains details like the number of diamonds, price, image URL, and special bonuses. " +
                      "Diamonds are premium consumable items typically used for exclusive or high-value purchases. " +
                      "They offer better value or access to premium content compared to regular coins. " +
                      "Use this endpoint to display the premium currency shop in the game."
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
        summary = "Get purchased diamond packs for a user",
        description = "Retrieves all diamond packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Free diamonds included with active subscriptions (bonus diamonds from subscription perks) " +
                      "2. Diamond packs directly purchased by the user through in-app purchases. " +
                      "Note: This returns purchase history, not the current diamond balance. " +
                      "Each entry represents a pack purchase transaction. " +
                      "Use this for showing purchase history, verifying completed transactions, or tracking premium currency acquisition."
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
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
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
