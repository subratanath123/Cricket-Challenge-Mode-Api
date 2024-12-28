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

import static com.example.demo.controller.GameAssetController.teamListForKits;
import static com.example.demo.dto.util.CacheUtils.nonConsumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.nonConsumableProducts;

@RestController
public class KitsPackController {

    @GetMapping("/kits/teams")
    public List<CardItem> getAvailableTeamsForKits() {
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
