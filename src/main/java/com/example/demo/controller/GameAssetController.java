package com.example.demo.controller;

import com.example.demo.dto.asset.GameAsset;
import com.example.demo.dto.kits.CardItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.ResourceUtils.*;


@RestController
public class GameAssetController {

    public static List<CardItem> teamListForKits = Arrays.asList(
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
