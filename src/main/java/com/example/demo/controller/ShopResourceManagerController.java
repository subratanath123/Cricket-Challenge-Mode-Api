package com.example.demo.controller;

import com.example.demo.dto.shop.Packs;
import com.example.demo.dto.subscription.IapProducts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.dto.shop.PackEnum.*;
import static com.example.demo.dto.util.ResourceUtils.*;

@RestController
@Tag(name = "Shop Resource Manager", description = "API endpoints for managing shop catalog metadata and In-App Purchase (IAP) product configurations. Provides high-level shop organization and IAP integration information.")
public class ShopResourceManagerController {

    @Operation(
        summary = "Get shop pack categories",
        description = "**Returns high-level list of all product pack categories.**\n\n" +
                      "**Available Categories:**\n" +
                      "- Diamond Packs (Premium currency)\n" +
                      "- Coin Packs (Standard currency)\n" +
                      "- Stadium Packs (Venue unlocks)\n" +
                      "- Bat Packs (Batting equipment)\n" +
                      "- Kits Packs (Team jerseys)\n" +
                      "- Tournament Packs (Tournament modes)\n" +
                      "- Commentary Packs (Voice options)\n" +
                      "- Subscription Packs (Plans)\n" +
                      "- NoAds Packs (Ad removal)\n\n" +
                      "**Each Category Includes:**\n" +
                      "- Category identifier (enum name)\n" +
                      "- Display name (human-readable)\n\n" +
                      "**Use Case:** Build main shop navigation/menu UI."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved pack categories"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/availablePacks")
    public List<Packs> get() {

        return Arrays.asList(
                new Packs(DiamondPacks.name(), "Diamond Packs"),
                new Packs(CoinsPacks.name(), "Coin Packs"),
                new Packs(StadiumPacks.name(), "Stadium Packs"),
                new Packs(BatPacks.name(), "Bat Packs"),
                new Packs(KitsPacks.name(), "Kits Packs"),
                new Packs(TournamentPacks.name(), "Tournament Packs"),
                new Packs(CommentaryPacks.name(), "Commentary Packs"),
                new Packs(SubscriptionPacks.name(), "Subscription Packs"),
                new Packs(NoAdsPacks.name(), "NoAds Packs")
        );
    }

    @Operation(
        summary = "Get IAP product configurations",
        description = "**Returns all products configured for In-App Purchase (IAP) integration.**\n\n" +
                      "**What You Get:**\n" +
                      "All IAP-enabled products with store-specific information:\n" +
                      "- `id`: Internal product identifier\n" +
                      "- `price`: Display price (may be overridden by store)\n" +
                      "- `purchaseId`: Store SKU (Google Play/Apple App Store)\n" +
                      "- `type`: 'Consumable', 'NonConsumable', or 'Subscription'\n\n" +
                      "**Sources:** Products from all categories that have valid `purchaseId` values\n\n" +
                      "**Use Cases:**\n" +
                      "1. Initialize IAP SDK with product list\n" +
                      "2. Query store prices for products\n" +
                      "3. Map store purchase callbacks to internal IDs\n\n" +
                      "**Integration:**\n" +
                      "```\n" +
                      "1. Call this endpoint on app startup\n" +
                      "2. Extract purchaseId values\n" +
                      "3. Initialize IAP SDK with these SKUs\n" +
                      "4. Use type field to configure IAP correctly\n" +
                      "```\n\n" +
                      "**Returns:** Set (no duplicates)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved IAP product configurations"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/iapPacks")
    public Set<IapProducts> getIapPacks() {

        Set<IapProducts> iapPacks = nonConsumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getPurchaseId() != null && !product.getPurchaseId().trim().isEmpty())
                .map(nonConsumableProducts -> new IapProducts(nonConsumableProducts.getId(), nonConsumableProducts.getPrice(), nonConsumableProducts.getPurchaseId(), "NonConsumable"))
                .collect(Collectors.toSet());

        iapPacks.addAll(consumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getPurchaseId() != null && !product.getPurchaseId().trim().isEmpty())
                .map(consumableProducts -> new IapProducts(consumableProducts.getId(), consumableProducts.getPrice(), consumableProducts.getPurchaseId(),"Consumable"))
                .collect(Collectors.toSet()));

        iapPacks.addAll(subscriptionProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getPurchaseId() != null && !product.getPurchaseId().trim().isEmpty())
                .map(nonConsumableProducts -> new IapProducts(nonConsumableProducts.getId(), nonConsumableProducts.getPrice(), nonConsumableProducts.getPurchaseId(), "Consumable"))
                .collect(Collectors.toSet()));

        return iapPacks;
    }

}
