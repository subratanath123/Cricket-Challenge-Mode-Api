package com.example.demo.dto.util;

import com.example.demo.dto.gift.*;
import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ResourceUtils {

    public static final Map<String, List<NonConsumableProducts>> nonConsumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> consumableProducts = new HashMap<>();
    public static final Map<String, List<ConsumableProducts>> subscriptionProducts = new HashMap<>();

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        // Kits pack
        KitsPackGift kitsPackGift1 = new KitsPackGift
                .Builder()
                .setJerseyWithShoeTextureUrl("https://i.imgur.com/c3c58YT.png")
                .setJerseyWithPadHelmetGlovesTextureUrl("https://i.imgur.com/3Dn1WP3.png")
                .setTeam("BAN")
                .build();

        KitsPackGift kitsPackGift2 = new KitsPackGift
                .Builder()
                .setJerseyWithShoeTextureUrl("https://i.imgur.com/7tCXKZv.png")
                .setJerseyWithPadHelmetGlovesTextureUrl("https://i.imgur.com/2rXDDHn.png")
                .setTeam("BAN")
                .build();

        String kitsPackGift1Json = null;
        String kitsPackGift2Json = null;

        try {
            kitsPackGift1Json = objectMapper.writeValueAsString(kitsPackGift1);
            kitsPackGift2Json = objectMapper.writeValueAsString(kitsPackGift2);
        } catch (JsonProcessingException e) {
        }

        List<NonConsumableProducts> nonConsuambleKitList = asList(
                new NonConsumableProducts("111", "BAN", "Exclusive 1 Pack", "12 $", "Buy", "https://i.imgur.com/5qGmuny.png", "Kits", "/purchase/nonconsumable/Kits/1?email=#EMAIL#", "com.pack0", kitsPackGift1Json, false),
                new NonConsumableProducts("222", "BAN", "Exclusive 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/U4AxZin.png", "Kits", "/purchase/nonconsumable/Kits/2?email=#EMAIL#", kitsPackGift2Json, false),
                new NonConsumableProducts("333", "BAN", "Exclusive 3 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/U4AxZin.png", "Kits", "/purchase/nonconsumable/Kits/3?email=#EMAIL#", kitsPackGift2Json, false),

                new NonConsumableProducts("4", "PAK", "Exclusive 1 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/4?email=#EMAIL#", "com.pack1", null, false),
                new NonConsumableProducts("5", "PAK", "Exclusive 2 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/5?email=#EMAIL#", "com.pack2", null, false),
                new NonConsumableProducts("6", "PAK", "Exclusive 3 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/6?email=#EMAIL#", "com.pack3", null, false),

                new NonConsumableProducts("7", "SA", "Exclusive 1 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/7?email=#EMAIL#", null, false),
                new NonConsumableProducts("8", "SA", "Exclusive 2 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/8?email=#EMAIL#", null, false),
                new NonConsumableProducts("9", "SA", "Exclusive 3 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/9?email=#EMAIL#", null, false)
        );

        nonConsumableProducts.put("Kits", nonConsuambleKitList);


        List<NonConsumableProducts> staudiumPackList = asList(
                new NonConsumableProducts("10", "DUBAI", "Dubai Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/10?email=#EMAIL#", null, false),
                new NonConsumableProducts("11", "HAML", "Hamilton Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/11?email=#EMAIL#", null, false),
                new NonConsumableProducts("12", "MUMBAI", "Mumbai Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/12?email=#EMAIL#", "com.pack4", null, false),
                new NonConsumableProducts("122", "TRNTB", "Trent-Bridge Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/122?email=#EMAIL#", "com.pack4", null, false)
        );

        nonConsumableProducts.put("StadiumPacks", staudiumPackList);

        List<NonConsumableProducts> commentaryPackList = asList(
                new NonConsumableProducts("180", "BANGLA", "Bangla Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "CommentaryPacks", "/purchase/nonconsumable/CommentaryPacks/180?email=#EMAIL#", null, false),
                new NonConsumableProducts("181", "TAMIL", "Tamil Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "CommentaryPacks", "/purchase/nonconsumable/CommentaryPacks/181?email=#EMAIL#", null, false)
        );

        nonConsumableProducts.put("CommentaryPacks", commentaryPackList);

        List<NonConsumableProducts> tournamentPackList = asList(
                new NonConsumableProducts("13", "World Cup", "World Cup Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/13?email=#EMAIL#", null, false),
                new NonConsumableProducts("14", "Champions Trophy", "Champions Trophy Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/14?email=#EMAIL#", "com.pack5", null, false),
                new NonConsumableProducts("15", "M15", "M15 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/15?email=#EMAIL#", null, false),
                new NonConsumableProducts("16", "Legends Cup Journey", "Legends Cup Journey Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/16?email=#EMAIL#", null, false)
        );

        nonConsumableProducts.put("TournamentPacks", tournamentPackList);

//        BattingGiftPack
        BattingGift battingGift = new BattingGift
                .Builder()
                .setDefensePower(100)
                .setShotPower(100)
                .setTextureUrl("https://i.imgur.com/wyLVR8W.png")
                .build();

        BattingGift defaultBattingGift = new BattingGift
                .Builder()
                .setDefensePower(0)
                .setShotPower(0)
                .setTextureUrl("https://i.imgur.com/S3OSle0.png")
                .build();

        String battingGiftJson = null;
        String defaultBattingGiftJson = null;
        try {
            battingGiftJson = objectMapper.writeValueAsString(battingGift);
            defaultBattingGiftJson = objectMapper.writeValueAsString(defaultBattingGift);
        } catch (JsonProcessingException e) {
        }

        List<NonConsumableProducts> battingPackList = asList(
                new NonConsumableProducts("116", "BattingPacks", "Default Batting Pack", "0 Diamonds", "Use", "https://i.imgur.com/40vivT3.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/116?email=#EMAIL#", defaultBattingGiftJson, true),
                new NonConsumableProducts("16", "BattingPacks", "Batting 1 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/dut11kl.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/16?email=#EMAIL#", battingGiftJson, false),
                new NonConsumableProducts("17", "BattingPacks", "Batting 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/dut11kl.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/17?email=#EMAIL#", battingGiftJson, false),
                new NonConsumableProducts("18", "BattingPacks", "Batting 3 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/dut11kl.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/18?email=#EMAIL#", battingGiftJson, false)
        );

        nonConsumableProducts.put("BattingPacks", battingPackList);

//        BowlingGift
        BowlingGift bowlingGift1 = new BowlingGift
                .Builder()
                .setSpeed(80)
                .setSwing(80)
                .setTextureUrl("https://i.imgur.com/cT7LCsl.png")
                .build();

        BowlingGift bowlingGift2 = new BowlingGift
                .Builder()
                .setSpeed(60)
                .setSwing(60)
                .setTextureUrl("https://i.imgur.com/uKiKfzz.png")
                .build();

        String bowling1GiftJson = null;
        String bowling2GiftJson = null;

        try {
            bowling1GiftJson = objectMapper.writeValueAsString(bowlingGift1);
            bowling2GiftJson = objectMapper.writeValueAsString(bowlingGift2);
        } catch (JsonProcessingException e) {
        }

        List<NonConsumableProducts> bowlingPackList = asList(
                new NonConsumableProducts("1116", "BowlingPacks", "Bowling 1 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/9Eqpc36.png", "BowlingPacks", "/purchase/nonconsumable/BattingPacks/1116?email=#EMAIL#", bowling1GiftJson, false),
                new NonConsumableProducts("1117", "BowlingPacks", "Bowling 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/IrjM5UD.png", "BowlingPacks", "/purchase/nonconsumable/BattingPacks/1117?email=#EMAIL#", bowling2GiftJson, false),
                new NonConsumableProducts("1118", "BowlingPacks", "Bowling 3 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/9Eqpc36.png", "BowlingPacks", "/purchase/nonconsumable/BattingPacks/1118?email=#EMAIL#", bowling1GiftJson, false)
        );

        nonConsumableProducts.put("BowlingPacks", bowlingPackList);



        ////Subscription packlist
        List<ConsumableProducts> subscriptionPackList = asList(
                new ConsumableProducts("19",
                        "Premium Subscription",
                        "Premium Subscription. Get Unlimited Offers",
                        "Subscription",
                        "50 $",
                        Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),
                        "Get Limited Time Offer",
                        "Subscription",
                        "https://i.imgur.com/Z0qwQbu.png",
                        asList("1", "2", "3", "4", "5", "7", "8", "9", "10", "11", "12", "13", "1116", "1117",
                                "1118", "16", "17", "18", "13", "14", "15", "16", "111", "222", "333"),
                        "/purchase/subscription/19?email=#EMAIL#",
                        "com.pack100",
                        null, false)
        );


        subscriptionProducts.put("Subscription", subscriptionPackList);


//        CoinGiftPack
        DiamondGift diamondGift = new DiamondGift
                .Builder()
                .setGift(1000)
                .build();

        String diamondGiftJson = null;
        try {
            diamondGiftJson = objectMapper.writeValueAsString(diamondGift);
        } catch (JsonProcessingException e) {
        }

        List<ConsumableProducts> diamondPackList = asList(
                new ConsumableProducts("21", "DiamondPacks", "Diamond 1 Pack", "Diamond 1 Pack","12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/21?email=#EMAIL#", "com.pack26", diamondGiftJson, false),
                new ConsumableProducts("22", "DiamondPacks", "Diamond 2 Pack", "Diamond 2 Pack", "12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/22?email=#EMAIL#", "com.pack27", diamondGiftJson, false),
                new ConsumableProducts("23", "DiamondPacks", "Diamond 3 Pack", "Diamond 3 Pack", "12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/23?email=#EMAIL#", "com.pack28", diamondGiftJson, false)
        );

        consumableProducts.put("DiamondPacks", diamondPackList);

//        CoinGiftPack
        CoinGift coinGift = new CoinGift
                .Builder()
                .setGift(1000)
                .build();

        String coinGiftJson = null;
        try {
            coinGiftJson = objectMapper.writeValueAsString(coinGift);
        } catch (JsonProcessingException e) {
        }

        List<ConsumableProducts> coinPackList = asList(
                new ConsumableProducts("24", "CoinsPacks", "Coin 1 Pack","Coin 1 Pack", "12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/CoinsPacks/24?email=#EMAIL#", "com.pack6", coinGiftJson, false),
                new ConsumableProducts("25", "CoinsPacks", "Coin 2 Pack","Coin 2 Pack", "12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/CoinsPacks/25?email=#EMAIL#", "com.pack7", coinGiftJson, false),
                new ConsumableProducts("26", "CoinsPacks", "Coin 3 Pack","Coin 3 Pack", "12 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)), "Buy", "ConsumableProducts", "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/CoinsPacks/26?email=#EMAIL#", "com.pack8", coinGiftJson, false)
        );

        consumableProducts.put("CoinsPacks", coinPackList);

        List<NonConsumableProducts> noAdsPackList = asList(
             new NonConsumableProducts("126", "NoAdsPacks", "No Ads 1 Pack", "12 $", "Buy", "https://i.imgur.com/9Eqpc36.png", "NoAdsPacks", "/purchase/NoAdsPacks/BattingPacks/126?email=#EMAIL#",  "shadja.asdasdn.asda" ,null, false)
        );

        nonConsumableProducts.put("NoAdsPacks", noAdsPackList);

    }

}
