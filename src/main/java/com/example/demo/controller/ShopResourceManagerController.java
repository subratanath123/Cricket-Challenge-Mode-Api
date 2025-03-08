package com.example.demo.controller;

import com.example.demo.dto.shop.Packs;
import com.example.demo.dto.subscription.IapProducts;
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
public class ShopResourceManagerController {

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
