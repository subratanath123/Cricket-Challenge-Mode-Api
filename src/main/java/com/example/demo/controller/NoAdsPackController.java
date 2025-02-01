package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.CacheUtils.consumablePurchaseHistory;
import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.consumableProducts;

@RestController
public class NoAdsPackController {

    @GetMapping("/noAdsPacks")
    public List<ConsumableProducts> get() {

        return new ArrayList<>(consumableProducts
                .get("NoAdsPacks"));
    }

    @GetMapping("/noAdsPacks/purchased")
    public List<ConsumableProducts> purchase(@RequestParam String email) {
        List<ConsumableProducts> purchasedProducts = new ArrayList<>(consumableProducts.get("NoAdsPacks"))
                .stream()
                .filter(product -> subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                        .stream()
                        .anyMatch(consumableProducts -> consumableProducts.getFreeProducts().contains(product.getId())))
                .collect(Collectors.toList());

        purchasedProducts.addAll(consumablePurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(nonConsumableProducts -> nonConsumableProducts.getCategory().equals("NoAdsPacks"))
                .collect(Collectors.toList()));

        return  purchasedProducts;
    }
}
