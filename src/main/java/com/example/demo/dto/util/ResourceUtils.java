package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ResourceUtils {

    public static final Map<String, List<NonConsumableProducts>> nonConsumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> consumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionProducts = new HashMap<>();

    static {
        List<NonConsumableProducts> nonConsuambleKitList = new ArrayList<>(
                TestDataResourceLoader.loadNonConsumableProducts("testData/JerseyPacksBan.json"));
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
                TestDataResourceLoader.loadNonConsumableProducts("testData/StadiumsPack.json");

        nonConsumableProducts.put("StadiumPacks", staudiumPackList);

        List<NonConsumableProducts> commentaryPackList =
                TestDataResourceLoader.loadNonConsumableProducts("testData/Commentary.json");

        nonConsumableProducts.put("CommentaryPacks", commentaryPackList);

        List<NonConsumableProducts> tournamentPackList = asList(
                new NonConsumableProducts("1123345", "T-20 World Cup", "T-20 World Cup", "12 Coins", "Buy", "https://i.imgur.com/mf71noQ.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/13?email=#EMAIL#", null, true),
                new NonConsumableProducts("13", "World Cup", "World Cup Pack", "12 Coins", "Buy", "https://i.imgur.com/MzAL53H.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/13?email=#EMAIL#", null, false),
                new NonConsumableProducts("14", "Champions Trophy", "Champions Trophy Pack", "12 $", "Buy", "https://i.imgur.com/xGM1KQy.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/14?email=#EMAIL#", "com.pack5", null, false),
                new NonConsumableProducts("15", "M15", "M15 Pack", "12 Coins", "Buy", "https://i.imgur.com/pkOsFaW.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/15?email=#EMAIL#", null, false),
                new NonConsumableProducts("16", "Legends Cup Journey", "Legends Cup Journey Pack", "12 Coins", "Buy", "https://i.imgur.com/iKXRRD6.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/16?email=#EMAIL#", null, false)
        );

        nonConsumableProducts.put("TournamentPacks", tournamentPackList);

        List<NonConsumableProducts> battingPackList =
                TestDataResourceLoader.loadNonConsumableProducts("testData/BatsPack.json");

        nonConsumableProducts.put("BattingPacks", battingPackList);

        List<NonConsumableProducts> bowlingPackList =
                TestDataResourceLoader.loadNonConsumableProducts("testData/BallsPack.json");

        nonConsumableProducts.put("BowlingPacks", bowlingPackList);



        ////Subscription packlist
        List<ConsumableProducts> subscriptionPackList =
                TestDataResourceLoader.loadConsumableProducts("testData/SubscriptionsPack.json");
        subscriptionProducts.put("Subscription", subscriptionPackList);


        List<ConsumableProducts> diamondPackList =
                TestDataResourceLoader.loadConsumableProducts("testData/DiamondsPack.json");

        consumableProducts.put("DiamondPacks", diamondPackList);

        List<ConsumableProducts> coinPackList =
                TestDataResourceLoader.loadConsumableProducts("testData/CoinsPack.json");

        consumableProducts.put("CoinsPacks", coinPackList);

        List<NonConsumableProducts> noAdsPackList =
                TestDataResourceLoader.loadNonConsumableProducts("testData/NoAds.json");

        nonConsumableProducts.put("NoAdsPacks", noAdsPackList);

    }
}
