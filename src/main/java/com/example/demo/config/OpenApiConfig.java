package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI howzatOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Howzat Game API Support");
        contact.setEmail("support@howzat.game");

        License license = new License()
                .name("Proprietary")
                .url("https://howzat.game/license");

        Info info = new Info()
                .title("Howzat Cricket Game API")
                .version("1.0.0")
                .contact(contact)
                .description("Comprehensive REST API for the Howzat Cricket Game. " +
                        "This API provides complete backend functionality for managing in-game purchases, " +
                        "user inventories, challenge modes, and shop resources.\n\n" +
                        
                        "## API Overview\n\n" +
                        
                        "### Product Types\n" +
                        "The API handles three main product types:\n" +
                        "- **Consumable Products**: Items that can be purchased multiple times (Coins, Diamonds)\n" +
                        "- **Non-Consumable Products**: One-time purchases that persist permanently (Equipment, Stadiums, Kits, Tournaments, Commentary)\n" +
                        "- **Subscription Products**: Time-based recurring purchases that provide ongoing benefits\n\n" +
                        
                        "### Key Features\n" +
                        "1. **Shop Management**: Browse and retrieve product catalogs across multiple categories\n" +
                        "2. **Purchase System**: Universal and category-specific purchase endpoints\n" +
                        "3. **User Inventory**: Track purchased items per user (identified by email)\n" +
                        "4. **Subscription Benefits**: Automatic unlocking of free products for subscribers\n" +
                        "5. **Challenge Mode**: Structured single-player challenges with rewards\n" +
                        "6. **Asset Management**: Centralized asset URLs for client preloading\n" +
                        "7. **IAP Integration**: In-App Purchase configuration for app stores\n\n" +
                        
                        "### Authentication\n" +
                        "Currently, the API uses email-based identification for user-specific operations. " +
                        "No authentication token is required (suitable for demo/development).\n\n" +
                        
                        "### Product Categories\n" +
                        "- **Batting Packs**: Batting equipment and accessories\n" +
                        "- **Bowling Packs**: Bowling equipment and ball options\n" +
                        "- **Stadium Packs**: Unlockable cricket venues\n" +
                        "- **Kits Packs**: Team jerseys organized by country\n" +
                        "- **Tournament Packs**: Special tournament modes and leagues\n" +
                        "- **Commentary Packs**: Additional commentary voice options\n" +
                        "- **Coins Packs**: In-game currency bundles\n" +
                        "- **Diamond Packs**: Premium currency bundles\n" +
                        "- **Subscription Packs**: Monthly/yearly subscription plans\n" +
                        "- **No Ads Packs**: Ad-removal options\n\n" +
                        
                        "### How Purchases Work\n" +
                        "1. Client calls product list endpoint (e.g., `/battingPacks`)\n" +
                        "2. User selects a product and initiates purchase via app store\n" +
                        "3. After successful IAP, client calls purchase endpoint with product ID and user email\n" +
                        "4. API records purchase in user's history\n" +
                        "5. Client can retrieve purchased items via `/*/purchased` endpoints\n\n" +
                        
                        "### Subscription Benefits\n" +
                        "Subscriptions include a `freeProducts` array listing product IDs automatically unlocked. " +
                        "When checking purchased items, the API automatically includes:\n" +
                        "- Items directly purchased by the user\n" +
                        "- Items included in active subscriptions\n\n" +
                        
                        "### Data Persistence\n" +
                        "**NOTE**: This is a demo API using in-memory storage. All purchase data is stored in " +
                        "static maps and will be lost on server restart. For production, implement database persistence.\n\n" +
                        
                        "### Typical Integration Flow\n" +
                        "1. **Game Startup**: Call `/assets` and `/iapPacks` to initialize\n" +
                        "2. **Shop UI**: Call `/availablePacks` for categories, then specific endpoints for products\n" +
                        "3. **User Inventory**: Call `/{category}/purchased?email=user@example.com` to unlock owned items\n" +
                        "4. **Purchase**: Use `/purchase/{id}?email=user@example.com` after IAP completion\n" +
                        "5. **Challenge Mode**: Use `/levelList`, `/challengeList`, `/progress` endpoints")
                .license(license);

        // Do not set OpenAPI `servers` here. A fixed list with localhost first makes Swagger UI
        // default all "Try it out" requests to localhost even when opened on Render or another host.
        // Omitting servers lets Swagger use the same origin as the page (e.g. Render deployment URL).
        return new OpenAPI().info(info);
    }
}
