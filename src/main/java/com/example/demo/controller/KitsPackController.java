package com.example.demo.controller;

import com.example.demo.dto.kits.CardItem;
import com.example.demo.dto.subscription.NonConsumableProducts;
import com.example.demo.service.PurchaseHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.controller.GameAssetController.teamListForKits;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
@Tag(name = "Kits Packs", description = "API endpoints for managing team kits and jerseys. Kits are non-consumable in-game items that allow players to customize their team's appearance with authentic cricket team jerseys and equipment.")
public class KitsPackController {

    private final PurchaseHistoryService purchaseHistoryService;

    public KitsPackController(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @Operation(
        summary = "Get list of teams with available kits",
        description = "**Returns list of cricket teams that have jerseys available.**\n\n" +
                      "**What You Get:**\n" +
                      "- Team codes (3-letter country codes like IND, PAK, AUS)\n" +
                      "- Team logos and representative images\n" +
                      "- Marketing descriptions\n" +
                      "- Navigation URLs to view team-specific kits\n\n" +
                      "**Available Teams:**\n" +
                      "Pakistan, India, Bangladesh, Zimbabwe, Australia, South Africa, Sri Lanka, New Zealand, West Indies\n\n" +
                      "**Use Case:** Display team selection screen before showing kit options.\n\n" +
                      "**Integration Flow:**\n" +
                      "```\n" +
                      "1. Call this endpoint → Get team list\n" +
                      "2. User selects a team → Get team code\n" +
                      "3. Call /kits/teams/{teamCode} → Show kits for that team\n" +
                      "```"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of teams"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams")
    public List<CardItem> getAvailableTeamsForKits() {
        return teamListForKits;
    }

    @Operation(
        summary = "Get kits for a specific team",
        description = "**Returns all jersey/kit options for a specific cricket team.**\n\n" +
                      "**What You Get:**\n" +
                      "- All kit designs for the selected team\n" +
                      "- Home jerseys, away jerseys, special editions\n" +
                      "- Pricing information\n" +
                      "- Preview images showing kit appearance\n\n" +
                      "**Team Codes:** IND, PAK, AUS, BAN, SA, SRI, NZ, WI, ZIM, etc.\n\n" +
                      "**Use Case:** Display purchasable kit options after user selects a team.\n\n" +
                      "**Example:**\n" +
                      "```\n" +
                      "GET /kits/teams/IND  → Returns all India team kits\n" +
                      "GET /kits/teams/PAK  → Returns all Pakistan team kits\n" +
                      "```"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved kits for the team"),
        @ApiResponse(responseCode = "404", description = "Team not found or no kits available"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams/{team}")
    public List<NonConsumableProducts> getTeamsKits(
        @Parameter(
            description = "Team code (3-letter country code) to fetch kits for. " +
                         "Examples: PAK (Pakistan), IND (India), AUS (Australia), BAN (Bangladesh), etc.",
            required = true,
            example = "IND"
        )
        @PathVariable String team
    ) {

        return nonConsumableProducts
                .get("Kits")
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getMetaInfo().equals(team))
                .collect(Collectors.toList());
    }

    @Operation(
        summary = "Get user's purchased kits (all teams)",
        description = "**Returns all team kits owned by a user across ALL teams.**\n\n" +
                      "**Sources Combined:**\n" +
                      "1. **Subscription Benefits** → Free kits from premium subscriptions\n" +
                      "2. **Direct Purchases** → Kits bought through IAP\n\n" +
                      "**What You Get:**\n" +
                      "- All owned kits from all teams (IND, PAK, AUS, etc.)\n" +
                      "- Not filtered by team - complete collection\n" +
                      "- Kit details including team identification\n\n" +
                      "**Use Case:** Unlock all owned team jerseys in the game.\n\n" +
                      "**Integration:** Call after login to enable owned kits for all teams."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved purchased kits"),
        @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kits/teams/purchased")
    public List<NonConsumableProducts> purchase(
        @Parameter(
            description = "User's email address to identify their purchase history. " +
                         "This is used as the unique identifier to fetch user-specific purchased kits.",
            required = true,
            example = "player@example.com"
        )
        @RequestParam String email
    ) {

        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("Kits"))
                .stream()
                .filter(product -> purchaseHistoryService.getActiveSubscriptions(email)
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(purchaseHistoryService.getNonConsumablePurchases(email)
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("Kits"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }


}
