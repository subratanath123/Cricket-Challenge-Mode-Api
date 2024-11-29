package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtils {

    public static final Map<String, List<ConsumableProducts>> consumablePurchaseHistory = new HashMap<>();
    public static final Map<String, List<NonConsumableProducts>> nonConsumablePurchaseHistory = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionPurchaseHistory = new HashMap<>();

}
