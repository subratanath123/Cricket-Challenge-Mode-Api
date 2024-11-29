package com.example.demo.controller;

import com.example.demo.dto.kits.CardItem;
import com.example.demo.dto.subscription.NonConsumableProducts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
public class KitsPackController {

    @GetMapping("/kits/teams")
    public List<CardItem> getAvailableTeamsForKits() {

        List<CardItem> teamListForKits = Arrays.asList(
                new CardItem("PAK", "https://i.imgur.com/Z0qwQbu.png", "Buy Pakistan Team Jersey & Kits", "Details", "Parent", "/kits/teams/PAK"),
                new CardItem("IND", "https://i.imgur.com/Z0qwQbu.png", "Buy India Team Jersey & Kits", "Details", "Parent", "/kits/teams/IND"),
                new CardItem("BAN", "https://i.imgur.com/Z0qwQbu.png", "Buy Bangladesh Team Jersey & Kits", "Details", "Parent", "/kits/teams/BAN"),
                new CardItem("ZIM", "https://i.imgur.com/Z0qwQbu.png", "Buy Zimbabwe Team Jersey & Kits", "Details", "Parent", "/kits/teams/ZIM"),
                new CardItem("AUS", "https://i.imgur.com/Z0qwQbu.png", "Buy Australia Team Jersey & Kits", "Details", "Parent", "/kits/teams/AUS"),
                new CardItem("SA", "https://i.imgur.com/Z0qwQbu.png", "Buy South Africa Team Jersey & Kits", "Details", "Parent", "/kits/teams/SA"),
                new CardItem("SRI", "https://i.imgur.com/Z0qwQbu.png", "Buy Sri-Lanka Team Jersey & Kits", "Details", "Parent", "/kits/teams/SRI"),
                new CardItem("NZ", "https://i.imgur.com/Z0qwQbu.png", "Buy New Zealand Team Jersey & Kits", "Details", "Parent", "/kits/teams/NZ"),
                new CardItem("WI", "https://i.imgur.com/Z0qwQbu.png", "Buy West Indies Team Jersey & Kits", "Details", "Parent", "/kits/teams/WI")
        );

        return teamListForKits;
    }

    @GetMapping("/kits/teams/{team}")
    public List<NonConsumableProducts> getTeamsKits(@PathVariable String team) {

        return nonConsumableProducts
                .get("Kits")
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getMetaInfo().equals(team))
                .collect(Collectors.toList());
    }

    @GetMapping("/kits/teams/purchased")
    public List<NonConsumableProducts> purchase(@RequestParam String email) {

        List<NonConsumableProducts> purchasedProducts = new ArrayList<>(nonConsumableProducts.get("Kits"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());


        purchasedProducts.addAll(nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("Kits"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }


}
