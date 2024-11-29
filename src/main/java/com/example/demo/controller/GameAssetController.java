package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.dto.util.ResourceUtils.*;


@RestController
public class GameAssetController {

    @GetMapping("/assets")
    public Set<String> purchaseConsumableProducts() {

        Set<String> imageAssets = nonConsumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(nonConsumableProducts -> nonConsumableProducts.getImageUrl())
                .collect(Collectors.toSet());

        imageAssets.addAll(consumableProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(consumableProducts -> consumableProducts.getImageUrl())
                .collect(Collectors.toSet()));

        imageAssets.addAll(subscriptionProducts.values()
                .stream()
                .flatMap(Collection::stream)
                .map(nonConsumableProducts -> nonConsumableProducts.getImageUrl())
                .collect(Collectors.toSet()));

        return imageAssets;
    }


}
