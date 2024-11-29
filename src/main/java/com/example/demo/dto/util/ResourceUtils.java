package com.example.demo.dto.util;

import com.example.demo.dto.subscription.ConsumableProducts;
import com.example.demo.dto.subscription.NonConsumableProducts;

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
        List<NonConsumableProducts> nonConsuambleKitList = asList(
                new NonConsumableProducts("1", "BAN", "Exclusive 1 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/1?email=#EMAIL#", "com.pack0"),
                new NonConsumableProducts("2", "BAN", "Exclusive 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/2?email=#EMAIL#"),
                new NonConsumableProducts("3", "BAN", "Exclusive 3 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/3?email=#EMAIL#"),

                new NonConsumableProducts("4", "PAK", "Exclusive 1 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/4?email=#EMAIL#", "com.pack1"),
                new NonConsumableProducts("5", "PAK", "Exclusive 2 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/5?email=#EMAIL#", "com.pack2"),
                new NonConsumableProducts("6", "PAK", "Exclusive 3 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/6?email=#EMAIL#", "com.pack3"),

                new NonConsumableProducts("7", "SA", "Exclusive 1 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/7?email=#EMAIL#"),
                new NonConsumableProducts("8", "SA", "Exclusive 2 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/8?email=#EMAIL#"),
                new NonConsumableProducts("9", "SA", "Exclusive 3 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "Kits", "/purchase/nonconsumable/Kits/9?email=#EMAIL#")
        );

        nonConsumableProducts.put("Kits", nonConsuambleKitList);


        List<NonConsumableProducts> staudiumPackList = asList(
                new NonConsumableProducts("1", "StadiumPacks", "Stadium 1 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/1?email=#EMAIL#"),
                new NonConsumableProducts("2", "StadiumPacks", "Stadium 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/2?email=#EMAIL#"),
                new NonConsumableProducts("3", "StadiumPacks", "Stadium 3 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "StadiumPacks", "/purchase/nonconsumable/StadiumPacks/3?email=#EMAIL#", "com.pack5")
        );

        nonConsumableProducts.put("StadiumPacks", staudiumPackList);


        List<NonConsumableProducts> tournamentPackList = asList(
                new NonConsumableProducts("1", "TournamentPacks", "Tournament 1 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/1?email=#EMAIL#"),
                new NonConsumableProducts("2", "TournamentPacks", "Tournament 2 Pack", "12 $", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/2?email=#EMAIL#", "com.pack6"),
                new NonConsumableProducts("3", "TournamentPacks", "Tournament 3 Pack", "12 Coins", "Buy", "https://i.imgur.com/zwokeqa.png", "TournamentPacks", "/purchase/nonconsumable/TournamentPacks/3?email=#EMAIL#")
        );

        nonConsumableProducts.put("TournamentPacks", tournamentPackList);


        List<NonConsumableProducts> battingPackList = asList(
                new NonConsumableProducts("1", "BattingPacks", "Batting 1 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/1?email=#EMAIL#"),
                new NonConsumableProducts("2", "BattingPacks", "Batting 2 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/2?email=#EMAIL#"),
                new NonConsumableProducts("3", "BattingPacks", "Batting 3 Pack", "12 Diamonds", "Buy", "https://i.imgur.com/zwokeqa.png", "BattingPacks", "/purchase/nonconsumable/BattingPacks/3?email=#EMAIL#")
        );

        nonConsumableProducts.put("BattingPacks", battingPackList);


        ////Subscription packlist
        List<ConsumableProducts> subscriptionPackList = asList(
                new ConsumableProducts("11", "Premium Subscription", "Subscription", "50 $", Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),
                        "Get Limited Time Offer",
                        "Subscription",
                        "https://i.imgur.com/Z0qwQbu.png",
                        asList("1", "2", "3"),
                        "com.pack8")
        );


        subscriptionProducts.put("Subscription", subscriptionPackList);


        List<ConsumableProducts> diamondPackList = asList(
                new ConsumableProducts("1", "DiamondPacks", "Diamond 1 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy", "ConsumableProducts" ,"https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/1?email=#EMAIL#"),
                new ConsumableProducts("2", "DiamondPacks", "Diamond 2 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy","ConsumableProducts" , "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/2?email=#EMAIL#"),
                new ConsumableProducts("3", "DiamondPacks", "Diamond 3 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy","ConsumableProducts" , "https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/DiamondPacks/3?email=#EMAIL#")
        );

        consumableProducts.put("DiamondPacks", diamondPackList);

        List<ConsumableProducts> coinPackList = asList(
                new ConsumableProducts("1", "CoinsPacks", "Coin 1 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy", "ConsumableProducts" ,"https://i.imgur.com/zwokeqa.png", null, "/purchase/nonconsumable/CoinsPacks/1?email=#EMAIL#", "com.pack4"),
                new ConsumableProducts("2", "CoinsPacks", "Coin 2 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy", "ConsumableProducts" ,"https://i.imgur.com/zwokeqa.png", null, "CoinsPacks", "/purchase/nonconsumable/CoinsPacks/2?email=#EMAIL#"),
                new ConsumableProducts("3", "CoinsPacks", "Coin 3 Pack", "12 $",  Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),"Buy", "ConsumableProducts" ,"https://i.imgur.com/zwokeqa.png", null, "CoinsPacks", "/purchase/nonconsumable/CoinsPacks/3?email=#EMAIL#")
        );

        consumableProducts.put("CoinsPacks", coinPackList);


    }

}
