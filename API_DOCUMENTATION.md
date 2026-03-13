# Howzat Cricket Game API Documentation

## Overview

The Howzat API is a comprehensive REST API that powers the backend for the Howzat Cricket Game. It manages in-game purchases, user inventories, challenge modes, and shop resources.

## Getting Started

### Running the API

```bash
./gradlew bootRun
```

The API will start on `http://localhost:8080`

### Accessing Swagger Documentation

Once the API is running, access the interactive Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

Or view the OpenAPI specification at:

```
http://localhost:8080/api-docs
```

## Core Concepts

### Product Types

The API manages three types of products:

1. **Consumable Products** - Can be purchased multiple times (Coins, Diamonds)
2. **Non-Consumable Products** - One-time purchases that persist forever (Equipment, Stadiums, Kits, etc.)
3. **Subscription Products** - Time-based recurring benefits with expiry dates

### User Identification

Users are identified by their **email address** throughout the API. Pass the email as a query parameter:

```
GET /battingPacks/purchased?email=player@example.com
```

## API Architecture

### Product Categories

| Category | Type | Description | Endpoint |
|----------|------|-------------|----------|
| Batting Packs | Non-Consumable | Batting equipment | `/battingPacks` |
| Bowling Packs | Non-Consumable | Bowling equipment | `/bowlingPacks` |
| Stadium Packs | Non-Consumable | Cricket venues | `/stadiumPacks` |
| Kits Packs | Non-Consumable | Team jerseys | `/kits/teams` |
| Tournament Packs | Non-Consumable | Tournament modes | `/tournamentPacks` |
| Commentary Packs | Non-Consumable | Commentary voices | `/commentaryPacks` |
| Coins Packs | Consumable | In-game currency | `/coinsPacks` |
| Diamond Packs | Consumable | Premium currency | `/diamondPacks` |
| Subscription Packs | Subscription | Recurring benefits | `/subscriptionPacks` |
| No Ads Packs | Non-Consumable | Ad removal | `/noAdsPacks` |

### Common Endpoint Patterns

Each product category follows this pattern:

1. **Get All Products**: `GET /{category}` - Returns all available products in the category
2. **Get Purchased**: `GET /{category}/purchased?email={userEmail}` - Returns user's purchased products

**Exception: Kits** have a hierarchical structure:
- `GET /kits/teams` - List of available teams
- `GET /kits/teams/{teamCode}` - Kits for specific team (e.g., `IND`, `PAK`)
- `GET /kits/teams/purchased?email={userEmail}` - User's purchased kits

## Purchase Flow

### Standard Purchase Flow

1. **Client displays products** from catalog endpoints (e.g., `/battingPacks`)
2. **User initiates purchase** through app store (Google Play / Apple App Store)
3. **App store processes payment** and returns success
4. **Client calls purchase API** to record the purchase:
   ```
   GET /purchase/{productId}?email=user@example.com
   ```
5. **API records purchase** in user's history
6. **Client refreshes inventory** by calling the purchased endpoint

### Subscription Purchase Flow

Subscriptions have a dedicated endpoint:

```
GET /purchase/subscription/{subscriptionId}?email=user@example.com
```

### Universal vs Specific Purchase

- **Universal**: `GET /purchase/{productId}?email={email}` - Works for any product type
- **Subscription-specific**: `GET /purchase/subscription/{id}?email={email}` - Only for subscriptions

## Key Endpoints

### Shop Resources

#### Get Available Pack Categories
```
GET /availablePacks
```
Returns high-level shop categories for building navigation UI.

#### Get IAP Product Configuration
```
GET /iapPacks
```
Returns all products configured for In-App Purchase with store SKUs. Use this to initialize IAP SDK.

#### Get All Game Assets
```
GET /assets
```
Returns all image URLs and metadata for preloading assets when game starts.

### Challenge Mode

#### Get Available Levels
```
GET /levelList
```
Returns: `["Level 1", "Level 2", "Level 3", ...]`

#### Get Challenges for a Level
```
GET /challengeList?level=Level 1&myTeam=IND
```
Returns challenges filtered by level. Each challenge includes:
- Challenge ID and title
- Role (BATTING or BOWLING)
- Opponent team
- Target objectives
- Rewards (XPs, coins, diamonds, stars)

#### Get User Progress
```
GET /progress?level=Level 1&myTeam=BAN
```
Returns mock progress data (currently returns hardcoded sample data).

#### Save Challenge Completion
```
GET /progress/update?level=1&challengeId=105&summary=Won by 6 wickets
```
Records challenge completion (currently a mock endpoint).

## Understanding Subscriptions

### How Subscriptions Work

Subscriptions have a special `freeProducts` field containing an array of product IDs:

```json
{
  "id": "monthly_premium",
  "packName": "Premium Monthly",
  "price": "$9.99",
  "expiryDate": "2026-04-13T00:00:00.000+00:00",
  "freeProducts": ["bat_pack_001", "stadium_pack_002", "kit_ind_001"]
}
```

### Automatic Unlocking

When you call any `/{category}/purchased?email={email}` endpoint, the API automatically returns:

1. **Direct purchases** - Items the user bought individually
2. **Subscription benefits** - Items included in their active subscription's `freeProducts` array

### Checking Active Subscriptions

```
GET /subscriptionPacks/purchased?email=user@example.com
```

Check the `expiryDate` field to determine if subscription is still active.

## Response Models

### ConsumableProducts

```json
{
  "id": "coin_pack_100",
  "category": "CoinsPacks",
  "packName": "100 Coins Pack",
  "price": "$0.99",
  "imageUrl": "https://example.com/coin_pack.png",
  "information": "Detailed description",
  "freeProducts": [],
  "purchaseId": "com.game.coins.100",
  "expiryDate": null,
  "isDefault": false
}
```

### NonConsumableProducts

```json
{
  "id": "bat_pack_premium_001",
  "category": "BattingPacks",
  "packName": "Premium Bat Pack",
  "price": "$4.99",
  "imageUrl": "https://example.com/bat_pack.png",
  "information": "Unlock premium batting equipment",
  "metaInfo": "Additional metadata",
  "purchaseId": "com.game.bat.premium",
  "isDefault": false
}
```

### IapProducts (for IAP initialization)

```json
{
  "id": "bat_pack_premium_001",
  "price": "$4.99",
  "purchaseId": "com.game.bat.premium",
  "type": "NonConsumable"
}
```

## Integration Guide

### Game Initialization Sequence

```javascript
// 1. Preload assets
GET /assets
// Download all images in background

// 2. Initialize IAP SDK
GET /iapPacks
// Configure app store with purchaseId values

// 3. Load shop categories
GET /availablePacks
// Build main shop navigation

// 4. Load user's inventory
GET /battingPacks/purchased?email=user@example.com
GET /bowlingPacks/purchased?email=user@example.com
GET /subscriptionPacks/purchased?email=user@example.com
// Unlock owned items in game
```

### Displaying Shop Categories

```javascript
// 1. Get categories
GET /availablePacks

// 2. When user selects "Batting Packs":
GET /battingPacks
// Display all available batting packs

// 3. Check user ownership to show "Owned" badge:
GET /battingPacks/purchased?email=user@example.com
```

### Processing a Purchase

```javascript
// 1. User clicks "Buy" on a product
const productId = "bat_pack_premium_001";

// 2. Initiate app store purchase
await appStore.purchase(productId);

// 3. On success, record in backend
GET /purchase/{productId}?email=user@example.com

// 4. Refresh inventory
GET /battingPacks/purchased?email=user@example.com
// Update UI to show newly owned item
```

### Kits Special Handling

```javascript
// 1. Get list of teams
GET /kits/teams
// Returns: [{title: "PAK", imageUrl: "...", nextUrl: "/kits/teams/PAK"}, ...]

// 2. User selects Pakistan
GET /kits/teams/PAK
// Returns all Pakistan jersey options

// 3. Check owned kits across all teams
GET /kits/teams/purchased?email=user@example.com
```

## Important Notes

### ⚠️ Data Persistence

**This is a demo API using in-memory storage.** All data is stored in static maps:

```java
public class CacheUtils {
    public static Map<String, List<ConsumableProducts>> consumablePurchaseHistory = new HashMap<>();
    public static Map<String, List<NonConsumableProducts>> nonConsumablePurchaseHistory = new HashMap<>();
    public static Map<String, List<ConsumableProducts>> subscriptionPurchaseHistory = new HashMap<>();
}
```

**All purchase data will be lost when the server restarts.**

For production:
1. Replace static maps with database (MongoDB, PostgreSQL, etc.)
2. Implement proper authentication (OAuth2, JWT)
3. Add validation and error handling
4. Implement subscription expiry checks
5. Add purchase receipt verification

### No Authentication

Currently, the API has no authentication. Email is used for identification but not verified. For production:

1. Add JWT or OAuth2 authentication
2. Validate user identity before processing purchases
3. Verify app store purchase receipts
4. Implement rate limiting

### Response Format

All endpoints return JSON. Standard HTTP status codes:
- `200` - Success
- `400` - Bad request (invalid parameters)
- `404` - Resource not found
- `500` - Internal server error

### Error Handling

Purchase endpoints return strings:
- `"purchased"` - Success
- `"failed"` - Product not found

In production, use proper error response objects:

```json
{
  "success": false,
  "error": "PRODUCT_NOT_FOUND",
  "message": "Product with ID 'xyz' does not exist"
}
```

## Example Use Cases

### Use Case 1: Building Shop UI

```
1. GET /availablePacks
   -> Display category tiles

2. User taps "Diamond Packs"
   GET /diamondPacks
   -> Display diamond bundle options

3. GET /diamondPacks/purchased?email=player@example.com
   -> Mark owned bundles in UI
```

### Use Case 2: Checking Subscription Benefits

```
1. GET /subscriptionPacks/purchased?email=player@example.com
   -> Returns user's subscriptions

2. Check expiryDate field:
   if (expiryDate > now) {
     subscription is active
   }

3. Extract freeProducts array:
   ["bat_pack_001", "stadium_pack_002"]

4. When loading /battingPacks/purchased:
   -> API automatically includes bat_pack_001 if it's in freeProducts
```

### Use Case 3: Challenge Mode Integration

```
1. GET /levelList
   -> Display level selection screen

2. User selects "Level 2"
   GET /challengeList?level=Level 2&myTeam=IND
   -> Display challenges for Level 2

3. User completes challenge 105
   GET /progress/update?level=2&challengeId=105&summary=Won by 5 wickets
   -> Record completion

4. Load progress
   GET /progress?level=Level 2&myTeam=IND
   -> Show which challenges are completed
```

## Testing with Swagger UI

1. Start the application
2. Open http://localhost:8080/swagger-ui.html
3. Each endpoint shows:
   - Description and purpose
   - Parameters with examples
   - Response schema
   - "Try it out" button for testing

Example test flow:
```
1. Try /battingPacks
   -> See all available batting packs

2. Try /purchase/bat_pack_001?email=test@example.com
   -> Records a purchase

3. Try /battingPacks/purchased?email=test@example.com
   -> See your purchased pack appear in results
```

## Development Tips

### Adding a New Product Category

1. Add products to ResourceUtils data structure
2. Create a new controller (copy existing pattern)
3. Add Swagger annotations for documentation
4. Add category to PackEnum
5. Update /availablePacks endpoint

### Modifying Purchase Logic

Edit PurchaseController.java - the universal `/purchase/{id}` endpoint handles all product types.

### Adding Database Persistence

Replace static maps in CacheUtils.java with repository classes:

```java
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserEmail(String email);
}
```

## Support

For questions about the API implementation, refer to:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/api-docs
- Source code comments and annotations

---

**Last Updated**: March 2026  
**API Version**: 1.0.0
