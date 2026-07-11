package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceUtils {
    private static final String TEST_DATA_BASE_URL = "https://cwapi.flyhr.net/Fileserver/Cricket%20World/Json/";

    private static final String[] JERSEY_PACK_FILES = {
            "JerseyPacksAFG.json",
            "JerseyPacksAUS.json",
            "JerseyPacksBan.json",
            "JerseyPacksENG.json",
            "JerseyPacksIND.json",
            "JerseyPacksIRE.json",
            "JerseyPacksNEP.json",
            "JerseyPacksNET.json",
            "JerseyPacksNMB.json",
            "JerseyPacksNZ.json",
            "JerseyPacksOMAN.json",
            "JerseyPacksPAK.json",
            "JerseyPacksPNG.json",
            "JerseyPacksSA.json",
            "JerseyPacksSCO.json",
            "JerseyPacksSRI.json",
            "JerseyPacksUAE.json",
            "JerseyPacksWI.json",
            "JerseyPacksZIM.json"
    };

    public static final Map<String, List<NonConsumableProducts>> nonConsumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> consumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionProducts = new HashMap<>();

    static {
        nonConsumableProducts.put("Kits", loadAllJerseyPacks());

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

    private static List<NonConsumableProducts> loadAllJerseyPacks() {
        List<NonConsumableProducts> kits = new ArrayList<>();

        for (String fileName : JERSEY_PACK_FILES) {
            String url = TEST_DATA_BASE_URL + fileName;
            kits.addAll(TestDataResourceLoader.loadNonConsumableProducts(url));
        }

        return kits;
    }
}
