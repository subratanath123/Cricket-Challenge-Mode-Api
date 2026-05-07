package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ResourceUtils {
    private static final String TEST_DATA_BASE_URL = "https://cwapi.flyhr.net/IpJson/";

    public static final Map<String, List<NonConsumableProducts>> nonConsumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> consumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionProducts = new HashMap<>();

    static {
        List<NonConsumableProducts> nonConsuambleKitList = new ArrayList<>(
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "JerseyPacksBan.json",
                        "testData/JerseyPacksBan.json"));
        nonConsuambleKitList.addAll(asList(
                new NonConsumableProducts("11114", "PAK", "Exclusive Default Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/4?email=#EMAIL#", "com.pack1", null, true),
                new NonConsumableProducts("4", "PAK", "Exclusive 1 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/4?email=#EMAIL#", "com.pack1", null, false),
                new NonConsumableProducts("5", "PAK", "Exclusive 2 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/5?email=#EMAIL#", "com.pack2", null, false),
                new NonConsumableProducts("6", "PAK", "Exclusive 3 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/6?email=#EMAIL#", "com.pack3", null, false),
                new NonConsumableProducts("711111", "SA", "Exclusive Default Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/7?email=#EMAIL#", null, true),
                new NonConsumableProducts("7", "SA", "Exclusive 1 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/7?email=#EMAIL#", null, false),
                new NonConsumableProducts("8", "SA", "Exclusive 2 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/8?email=#EMAIL#", null, false),
                new NonConsumableProducts("9", "SA", "Exclusive 3 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/9?email=#EMAIL#", null, false)
        ));

        nonConsumableProducts.put("Kits", nonConsuambleKitList);

        List<NonConsumableProducts> staudiumPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "StadiumsPack.json",
                        "testData/StadiumsPack.json");

        nonConsumableProducts.put("StadiumPacks", staudiumPackList);

        List<NonConsumableProducts> commentaryPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "Commentary.json",
                        "testData/Commentary.json");

        nonConsumableProducts.put("CommentaryPacks", commentaryPackList);

        List<NonConsumableProducts> tournamentPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "TournamentsPack.json",
                        "testData/TournamentsPack.json");

        nonConsumableProducts.put("TournamentPacks", tournamentPackList);

        List<NonConsumableProducts> battingPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "BatsPack.json",
                        "testData/BatsPack.json");

        nonConsumableProducts.put("BattingPacks", battingPackList);

        List<NonConsumableProducts> bowlingPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "Ballspack.json",
                        "testData/BallsPack.json");

        nonConsumableProducts.put("BowlingPacks", bowlingPackList);



        ////Subscription packlist
        List<ConsumableProducts> subscriptionPackList =
                TestDataResourceLoader.loadConsumableProducts(
                        TEST_DATA_BASE_URL + "SubscriptionsPack.json",
                        "testData/SubscriptionsPack.json");
        subscriptionProducts.put("Subscription", subscriptionPackList);


        List<ConsumableProducts> diamondPackList =
                TestDataResourceLoader.loadConsumableProducts(
                        TEST_DATA_BASE_URL + "DiamondsPack.json",
                        "testData/DiamondsPack.json");

        consumableProducts.put("DiamondPacks", diamondPackList);

        List<ConsumableProducts> coinPackList =
                TestDataResourceLoader.loadConsumableProducts(
                        TEST_DATA_BASE_URL + "CoinsPack.json",
                        "testData/CoinsPack.json");

        consumableProducts.put("CoinsPacks", coinPackList);

        List<NonConsumableProducts> noAdsPackList =
                TestDataResourceLoader.loadNonConsumableProducts(
                        TEST_DATA_BASE_URL + "NoAds.json",
                        "testData/NoAds.json");

        nonConsumableProducts.put("NoAdsPacks", noAdsPackList);

    }
}
