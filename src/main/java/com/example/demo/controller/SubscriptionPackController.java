package com.example.demo.controller;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.demo.dto.util.CacheUtils.subscriptionPurchaseHistory;
import static com.example.demo.dto.util.ResourceUtils.subscriptionProducts;
import static java.util.Arrays.asList;

@RestController
public class SubscriptionPackController {

    @GetMapping("/subscriptionPacks")
    public List<ConsumableProducts> get() {

        return new ArrayList<>(subscriptionProducts.get("Subscription"));
    }

    @GetMapping("/subscriptionPacks/purchased")
    public List<ConsumableProducts> purchase(@RequestParam String email) {
        return new ArrayList<>(subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>()));
    }

}
