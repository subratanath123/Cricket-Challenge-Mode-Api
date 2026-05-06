package com.example.demo.dto.util;

import com.example.demo.dto.kits.CardItem;
import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Loads catalog rows from JSON under {@code classpath:testData/} (sourced from {@code src/testData} at build time).
 */
public final class TestDataResourceLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private TestDataResourceLoader() {
    }

    public static List<NonConsumableProducts> loadNonConsumableProducts(String classpathRelativePath) {
        try (InputStream in = open(classpathRelativePath)) {
            List<NonConsumableJsonRow> rows = MAPPER.readValue(in, new TypeReference<>() {
            });
            return rows.stream().map(TestDataResourceLoader::toNonConsumable).toList();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test data: " + classpathRelativePath, e);
        }
    }

    public static List<ConsumableProducts> loadConsumableProducts(String classpathRelativePath) {
        try (InputStream in = open(classpathRelativePath)) {
            List<ConsumableJsonRow> rows = MAPPER.readValue(in, new TypeReference<>() {
            });
            return rows.stream().map(TestDataResourceLoader::toConsumable).toList();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test data: " + classpathRelativePath, e);
        }
    }

    public static List<CardItem> loadCardItems(String classpathRelativePath) {
        try (InputStream in = open(classpathRelativePath)) {
            List<CardItemJsonRow> rows = MAPPER.readValue(in, new TypeReference<>() {
            });
            return rows.stream()
                    .map(r -> new CardItem(r.title, r.imageUrl, r.description, r.actionName, r.objectType, r.nextUrl))
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test data: " + classpathRelativePath, e);
        }
    }

    private static InputStream open(String classpathRelativePath) {
        String path = classpathRelativePath.startsWith("/")
                ? classpathRelativePath.substring(1)
                : classpathRelativePath;
        InputStream in = TestDataResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if (in == null) {
            throw new IllegalStateException("Classpath resource not found: " + path);
        }
        return in;
    }

    private static NonConsumableProducts toNonConsumable(NonConsumableJsonRow r) {
        NonConsumableProducts p = new NonConsumableProducts(
                r.id,
                r.metaInfo,
                r.packName,
                r.price,
                r.information,
                r.imageUrl,
                r.category,
                r.nextUrl,
                r.purchaseId,
                r.payload,
                r.defaultProduct
        );
        if (r.alertInformation != null) {
            p.setAlertInformation(r.alertInformation);
        }
        if (r.flipInformation != null) {
            p.setFlipInformation(r.flipInformation);
        }
        if (r.objectType != null) {
            p.setObjectType(r.objectType);
        }
        return p;
    }

    private static ConsumableProducts toConsumable(ConsumableJsonRow r) {
        Date expiry = Date.from(Instant.parse(r.expiryDate));
        ConsumableProducts p = new ConsumableProducts(
                r.id,
                r.category,
                r.packName,
                r.metaInfo,
                r.price,
                expiry,
                r.information,
                r.objectType,
                r.imageUrl,
                r.freeProducts,
                r.nextUrl,
                r.purchaseId,
                r.payload,
                r.defaultProduct
        );
        if (r.alertInformation != null) {
            p.setAlertInformation(r.alertInformation);
        }
        if (r.flipInformation != null) {
            p.setFlipInformation(r.flipInformation);
        }
        return p;
    }

    @SuppressWarnings("unused")
    private static class NonConsumableJsonRow {
        public String id;
        public String metaInfo;
        public String category;
        public String packName;
        public String imageUrl;
        public String price;
        public String information;
        public String alertInformation;
        public String flipInformation;
        public String nextUrl;
        public String objectType;
        public String purchaseId;
        public String payload;
        @JsonProperty("default")
        public boolean defaultProduct;
    }

    @SuppressWarnings("unused")
    private static class ConsumableJsonRow {
        public String id;
        public String category;
        public String packName;
        public String metaInfo;
        public String price;
        public String expiryDate;
        public String information;
        public String alertInformation;
        public String flipInformation;
        public String imageUrl;
        public String objectType;
        public List<String> freeProducts;
        public String purchaseId;
        public String nextUrl;
        public String payload;
        @JsonProperty("default")
        public boolean defaultProduct;
    }

    @SuppressWarnings("unused")
    private static class CardItemJsonRow {
        public String title;
        public String actionName;
        public String imageUrl;
        public String description;
        public String objectType;
        public String nextUrl;
    }
}
