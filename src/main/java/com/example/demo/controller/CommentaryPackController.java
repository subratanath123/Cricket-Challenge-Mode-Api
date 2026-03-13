package com.example.demo.controller;

import com.example.demo.dto.subscription.NonConsumableProducts;
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

import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Commentary Packs", description = "API endpoints for managing commentary packs. Commentary packs are non-consumable in-game items that unlock different commentary voice options and styles to enhance the audio experience during cricket matches.")
public class CommentaryPackController {


    @Operation(
        summary = "Get all available commentary packs",
        description = "Retrieves a complete list of all commentary packs available in the game store. " +
                      "This endpoint returns all commentary voice pack options with different commentators, languages, and styles. " +
                      "Each pack contains details like commentator names, languages, price, and preview audio URLs. " +
                      "Commentary packs are non-consumable and persist once purchased. " +
                      "Use this endpoint to display the commentary options catalog in the game shop."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of commentary packs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/commentaryPacks")
    public List<NonConsumableProducts> get() {

        return new ArrayList<>(nonConsumableProducts
                .get("CommentaryPacks"));
    }

    @Operation(
        summary = "Get purchased commentary packs for a user",
        description = "Retrieves all commentary packs that have been purchased by a specific user. " +
                      "This endpoint combines two sources: " +
                      "1. Free commentary packs included with active subscriptions (bonus commentaries from premium subscriptions) " +
                      "2. Commentary packs directly purchased by the user through in-app purchases. " +
                      "Use this to determine which commentary voices should be available/unlocked for the user in-game. " +
                      "The response includes all pack details for owned commentary options only."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased commentary packs"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/commentaryPacks/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased commentary packs.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {
        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("CommentaryPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("CommentaryPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
