package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CacheUtils {

    public static final Map<String, List<ConsumableProducts>> consumablePurchaseHistory = new HashMap<>();
    public static final Map<String, List<NonConsumableProducts>> nonConsumablePurchaseHistory = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionPurchaseHistory = new HashMap<>();

    public static List<ConsumableProducts> getActiveSubscriptions(String email) {
        Date now = new Date();
        return subscriptionPurchaseHistory.getOrDefault(email, new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .filter(subscription -> subscription.getExpiryDate() != null && subscription.getExpiryDate().after(now))
                .collect(Collectors.toList());
    }

}
