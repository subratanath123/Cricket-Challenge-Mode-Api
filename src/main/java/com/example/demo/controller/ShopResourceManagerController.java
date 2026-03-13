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
        summary = "Get list of available pack categories",
        description = "Retrieves a high-level list of all product pack categories available in the game shop. " +
                      "This endpoint provides the main navigation structure for the shop UI. " +
                      "Each pack category includes: " +
                      "- Category identifier (enum name) " +
                      "- Display name (human-readable category name). " +
                      "Available categories: " +
                      "- DiamondPacks: Premium currency " +
                      "- CoinsPacks: Standard currency " +
                      "- StadiumPacks: Stadium/venue unlocks " +
                      "- BatPacks: Batting equipment " +
                      "- KitsPacks: Team jerseys and kits " +
                      "- TournamentPacks: Tournament unlocks " +
                      "- CommentaryPacks: Commentary voices " +
                      "- SubscriptionPacks: Subscription plans " +
                      "- NoAdsPacks: Ad removal options. " +
                      "Use this endpoint to build the main shop navigation/menu."
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
        summary = "Get In-App Purchase (IAP) product configurations",
        description = "Retrieves all products configured for In-App Purchase integration with app stores (Google Play, Apple App Store). " +
                      "This endpoint aggregates IAP-enabled products from all categories and provides the necessary information for IAP initialization. " +
                      "Each IAP product includes: " +
                      "- id: Internal product identifier " +
                      "- price: Display price (may be overridden by store) " +
                      "- purchaseId: Store-specific product ID (SKU for Google Play, Product ID for Apple) " +
                      "- type: Product type ('Consumable', 'NonConsumable', or 'Subscription'). " +
                      "Only products with valid purchaseId values are included. " +
                      "Use this endpoint to: " +
                      "1. Initialize IAP SDK with product list " +
                      "2. Query store prices for products " +
                      "3. Map store purchase callbacks to internal product IDs. " +
                      "Returns a Set to ensure no duplicate entries."
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
