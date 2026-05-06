package com.example.demo.controller;

import com.example.demo.dto.asset.GameAsset;
import com.example.demo.dto.kits.CardItem;
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

import static com.example.demo.dto.util.ResourceUtils.*;
import static com.example.demo.dto.util.TestDataResourceLoader.loadCardItems;


@RestController
@Tag(name = "Game Assets", description = "API endpoints for managing game asset resources. This provides consolidated asset information including images and metadata for all in-game purchasable items. Used for preloading and caching visual assets in the game client.")
public class GameAssetController {

    public static List<CardItem> teamListForKits = loadCardItems("testData/JerseyIcon.json");

    @Operation(
        summary = "Get all game assets for preloading",
        description = "**Returns consolidated asset information for ALL in-game purchasable items.**\n\n" +
                      "**Assets Included From:**\n" +
                      "1. Non-consumable products (batting, bowling, stadiums, kits, tournaments, commentary)\n" +
                      "2. Consumable products (coins, diamonds)\n" +
                      "3. Subscription products\n" +
                      "4. Team kit parent categories\n\n" +
                      "**Each Asset Contains:**\n" +
                      "- `id`: Unique product identifier\n" +
                      "- `imageUrl`: CDN URL for the image\n" +
                      "- `category`: Product category for organization\n\n" +
                      "**Use Case:** Preload all visual assets when game starts.\n\n" +
                      "**Integration:**\n" +
                      "```\n" +
                      "1. Call this endpoint on app startup\n" +
                      "2. Download all images in background\n" +
                      "3. Cache for quick loading in shop/inventory\n" +
                      "```\n\n" +
                      "**Returns:** Set (no duplicates)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all game assets"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/assets")
    public Set<GameAsset> purchaseConsumableProducts() {

        Set<GameAsset> imageAssets = nonConsumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(product ->  new GameAsset.Builder().setId(product.getId()).setImageUrl(product.getImageUrl()).setCategory(product.getCategory()).build())
                .collect(Collectors.toSet());

        imageAssets.addAll(consumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(product ->  new GameAsset.Builder().setId(product.getId()).setImageUrl(product.getImageUrl()).setCategory(product.getCategory()).build())
                .collect(Collectors.toSet()));

        imageAssets.addAll(subscriptionProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(product ->  new GameAsset.Builder().setId(product.getId()).setImageUrl(product.getImageUrl()).setCategory(product.getCategory()).build())
                .collect(Collectors.toSet()));

        imageAssets.addAll(teamListForKits
                .stream()
                .map(parent ->  new GameAsset.Builder().setId(parent.getTitle()).setImageUrl(parent.getImageUrl()).setCategory(parent.getObjectType()).build())
                .collect(Collectors.toSet()));

        return imageAssets;
    }


}
