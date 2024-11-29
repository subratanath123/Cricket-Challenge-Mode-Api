package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.demo.dto.util.CacheUtils.*;
import static com.example.demo.dto.util.ResourceUtils.*;

@RestController
public class PurchaseController {

    @GetMapping("/purchase/subscription/{id}")
    public String purchaseConsumableProducts(@RequestParam String email,
                                             @PathVariable String id) {

        List<ConsumableProducts> purchaseHistoryOrDefault = subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>());

        purchaseHistoryOrDefault
                .add(
                        subscriptionProducts
                                .get("Subscription")
                                .stream()
                                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                                .findFirst()
                                .orElse(null)
                );

        subscriptionPurchaseHistory.put(email, purchaseHistoryOrDefault);

        return "purchased";
    }


    //this is for all kind of purchase. Not specific to consumable or non consumable
    @GetMapping("/purchase/{id}")
    public String purchaseProducts(@RequestParam String email,
                                   @PathVariable String id) {
        List<ConsumableProducts> purchaseHistoryOrDefault = consumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        ConsumableProducts matchedConsumableProduct = consumableProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedConsumableProduct != null) {
            purchaseHistoryOrDefault.add(matchedConsumableProduct);
            consumablePurchaseHistory.put(email, purchaseHistoryOrDefault);
            return "purchased";
        }

        List<NonConsumableProducts> nonConsumablePurchaseHistoryOrDefault = nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        NonConsumableProducts matchedNonConsumableProduct = nonConsumableProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(nonConsumableProducts -> nonConsumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedNonConsumableProduct != null) {
            nonConsumablePurchaseHistoryOrDefault.add(matchedNonConsumableProduct);
            nonConsumablePurchaseHistory.put(email, nonConsumablePurchaseHistoryOrDefault);
            return "purchased";
        }

        List<ConsumableProducts> subscriptionPurchaseHistoryOrDefault = subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>());

        ConsumableProducts matchedSubscriptionProduct = subscriptionProducts
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (matchedSubscriptionProduct != null) {
            subscriptionPurchaseHistoryOrDefault.add(matchedSubscriptionProduct);
            subscriptionPurchaseHistory.put(email, subscriptionPurchaseHistoryOrDefault);
            return "purchased";
        }


        return "failed";
    }


    @GetMapping("/purchase/consumable/{category}/{id}")
    public String purchaseConsumableProducts(@RequestParam String email,
                                             @PathVariable String id,
                                             @PathVariable String category) {

        List<ConsumableProducts> purchaseHistoryOrDefault = consumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        purchaseHistoryOrDefault
                .add(
                        consumableProducts
                                .get(category)
                                .stream()
                                .filter(consumableProducts -> consumableProducts.getId().equals(id))
                                .findFirst()
                                .orElse(null)
                );

        consumablePurchaseHistory.put(email, purchaseHistoryOrDefault);

        return "purchased";
    }

    @GetMapping("/purchase/nonconsumable/{category}/{id}")
    public String purchaseNonConsumableProducts(@RequestParam String email,
                                                @PathVariable String id,
                                                @PathVariable String category) {

        List<NonConsumableProducts> purchaseHistoryOrDefault = nonConsumablePurchaseHistory.getOrDefault(email, new ArrayList<>());

        purchaseHistoryOrDefault
                .add(
                        nonConsumableProducts
                                .get(category)
                                .stream()
                                .filter(nonConsumableProducts -> nonConsumableProducts.getId().equals(id))
                                .findFirst()
                                .orElse(null)
                );

        nonConsumablePurchaseHistory.put(email, purchaseHistoryOrDefault);

        return "purchased";
    }

}
