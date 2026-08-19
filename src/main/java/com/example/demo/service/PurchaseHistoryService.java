package com.example.demo.service;

import com.example.demo.domain.UserPurchaseHistory;
import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import com.example.demo.repository.UserPurchaseHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PurchaseHistoryService {

    private final UserPurchaseHistoryRepository repository;

    public PurchaseHistoryService(UserPurchaseHistoryRepository repository) {
        this.repository = repository;
    }

    public List<ConsumableProducts> getConsumablePurchases(String email) {
        return new ArrayList<>(history(email).getConsumablePurchases());
    }

    public List<NonConsumableProducts> getNonConsumablePurchases(String email) {
        return new ArrayList<>(history(email).getNonConsumablePurchases());
    }

    public List<ConsumableProducts> getSubscriptionPurchases(String email) {
        return new ArrayList<>(history(email).getSubscriptionPurchases());
    }

    public List<ConsumableProducts> getActiveSubscriptions(String email) {
        Date now = new Date();
        return getSubscriptionPurchases(email).stream()
                .filter(Objects::nonNull)
                .filter(subscription -> subscription.getExpiryDate() != null && subscription.getExpiryDate().after(now))
                .collect(Collectors.toList());
    }

    public void addConsumablePurchase(String email, ConsumableProducts product) {
        if (product == null) {
            return;
        }
        UserPurchaseHistory history = history(email);
        history.getConsumablePurchases().add(product);
        repository.save(history);
    }

    public void addNonConsumablePurchase(String email, NonConsumableProducts product) {
        if (product == null) {
            return;
        }
        UserPurchaseHistory history = history(email);
        history.getNonConsumablePurchases().add(product);
        repository.save(history);
    }

    public void addSubscriptionPurchase(String email, ConsumableProducts product) {
        if (product == null) {
            return;
        }
        UserPurchaseHistory history = history(email);
        history.getSubscriptionPurchases().add(copyWithExpiry(product));
        repository.save(history);
    }

    private ConsumableProducts copyWithExpiry(ConsumableProducts template) {
        Date expiryDate = Date.from(Instant.now().plus(365, ChronoUnit.DAYS));
        ConsumableProducts purchasedSubscription = new ConsumableProducts(
                template.getId(),
                template.getCategory(),
                template.getPackName(),
                template.getMetaInfo(),
                template.getPrice(),
                expiryDate,
                template.getInformation(),
                template.getObjectType(),
                template.getImageUrl(),
                template.getFreeProducts(),
                template.getNextUrl(),
                template.getPurchaseId(),
                template.getPayload(),
                template.isDefault()
        );
        purchasedSubscription.setAlertInformation(template.getAlertInformation());
        purchasedSubscription.setFlipInformation(template.getFlipInformation());
        return purchasedSubscription;
    }

    private UserPurchaseHistory history(String email) {
        return repository.findById(email).orElseGet(() -> new UserPurchaseHistory(email));
    }
}
