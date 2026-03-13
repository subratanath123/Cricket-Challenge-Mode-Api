# Howzat API - Quick Reference Guide

## Access Points

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **Base URL**: http://localhost:8080

## Quick Start

### 1. View All Available Products
```bash
# Get shop categories
GET /availablePacks

# Get specific product types
GET /battingPacks
GET /bowlingPacks
GET /coinsPacks
GET /diamondPacks
GET /stadiumPacks
GET /tournamentPacks
GET /commentaryPacks
GET /subscriptionPacks
GET /noAdsPacks
```

### 2. Get User's Purchased Items
```bash
GET /battingPacks/purchased?email=player@example.com
GET /bowlingPacks/purchased?email=player@example.com
GET /coinsPacks/purchased?email=player@example.com
# ... (same pattern for all categories)
```

### 3. Make a Purchase
```bash
# Universal purchase endpoint (works for any product)
GET /purchase/{productId}?email=player@example.com

# Subscription-specific
GET /purchase/subscription/{subscriptionId}?email=player@example.com
```

## API Endpoint Summary

### Shop Management

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/availablePacks` | GET | List of pack categories | None |
| `/iapPacks` | GET | IAP product configurations | None |
| `/assets` | GET | All game asset URLs | None |

### Product Endpoints (Pattern for all categories)

Each category follows this pattern:

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/{category}` | GET | All products in category | None |
| `/{category}/purchased` | GET | User's purchased items | `email` (required) |

**Categories**: `battingPacks`, `bowlingPacks`, `stadiumPacks`, `tournamentPacks`, `commentaryPacks`, `coinsPacks`, `diamondPacks`, `subscriptionPacks`, `noAdsPacks`

### Kits (Special Hierarchical Structure)

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/kits/teams` | GET | List of available teams | None |
| `/kits/teams/{teamCode}` | GET | Kits for specific team | `teamCode` (path param) |
| `/kits/teams/purchased` | GET | User's purchased kits | `email` (required) |

### Purchase Endpoints

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/purchase/{id}` | GET | Universal purchase | `id` (path), `email` (query) |
| `/purchase/subscription/{id}` | GET | Subscription purchase | `id` (path), `email` (query) |

### Challenge Mode

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/levelList` | GET | All available levels | None |
| `/challengeList` | GET | Challenges for a level | `level` (required), `myTeam` (optional) |
| `/progress` | GET | User's progress | `level` (optional), `myTeam` (optional) |
| `/progress/update` | GET | Record completion | `level` (optional), `challengeId` (required), `summary` (required) |

## Example Requests

### Get All Batting Packs
```bash
curl http://localhost:8080/battingPacks
```

### Get User's Purchased Batting Packs
```bash
curl "http://localhost:8080/battingPacks/purchased?email=player@example.com"
```

### Purchase a Product
```bash
curl "http://localhost:8080/purchase/bat_pack_001?email=player@example.com"
```

### Get Challenge List
```bash
curl "http://localhost:8080/challengeList?level=Level%201&myTeam=IND"
```

### Get Kits for India Team
```bash
curl http://localhost:8080/kits/teams/IND
```

## Response Models

### ConsumableProducts (Coins, Diamonds, Subscriptions)
```json
{
  "id": "string",
  "category": "string",
  "packName": "string",
  "price": "string",
  "imageUrl": "string",
  "information": "string",
  "freeProducts": ["string"],
  "purchaseId": "string",
  "expiryDate": "datetime",
  "isDefault": boolean
}
```

### NonConsumableProducts (Equipment, Stadiums, etc.)
```json
{
  "id": "string",
  "category": "string",
  "packName": "string",
  "price": "string",
  "imageUrl": "string",
  "information": "string",
  "metaInfo": "string",
  "purchaseId": "string",
  "isDefault": boolean
}
```

### IapProducts (IAP Configuration)
```json
{
  "id": "string",
  "price": "string",
  "purchaseId": "string",
  "type": "Consumable|NonConsumable|Subscription"
}
```

## Important Notes

### 🔴 Data Persistence
- Uses **in-memory storage** (data lost on restart)
- For production, implement database persistence

### 🔴 Authentication
- Currently uses **email-only** identification
- No authentication tokens required
- For production, implement proper auth (JWT, OAuth2)

### 🔴 Subscription Logic
- Subscriptions have `freeProducts` array
- `*/purchased` endpoints automatically include:
  - Direct user purchases
  - Items from active subscriptions
- Check `expiryDate` to verify if subscription is active

## Testing with cURL

### Complete Test Flow
```bash
# 1. Get available products
curl http://localhost:8080/battingPacks

# 2. Purchase a product
curl "http://localhost:8080/purchase/bat_pack_001?email=test@example.com"

# 3. Verify purchase
curl "http://localhost:8080/battingPacks/purchased?email=test@example.com"

# 4. Get IAP configuration
curl http://localhost:8080/iapPacks

# 5. Get all assets
curl http://localhost:8080/assets
```

## Error Responses

### Purchase Responses
- `"purchased"` - Success
- `"failed"` - Product not found

### HTTP Status Codes
- `200` - Success
- `400` - Bad request
- `404` - Not found
- `500` - Server error

## Common Integration Patterns

### Game Startup
```
1. GET /assets          → Preload images
2. GET /iapPacks        → Initialize IAP SDK
3. GET /availablePacks  → Build shop UI
```

### Loading User Inventory
```
For each category:
  GET /{category}/purchased?email=user@example.com
  
Unlock items in game based on response
```

### Purchase Flow
```
1. User selects product
2. App store processes payment
3. On success: GET /purchase/{productId}?email=user@example.com
4. Refresh: GET /{category}/purchased?email=user@example.com
```

## Useful Team Codes

For kits and challenges:
- `IND` - India
- `PAK` - Pakistan
- `AUS` - Australia
- `BAN` - Bangladesh
- `SA` - South Africa
- `SRI` - Sri Lanka
- `NZ` - New Zealand
- `WI` - West Indies
- `ZIM` - Zimbabwe
- `AFG` - Afghanistan
- `IRE` - Ireland
- `ENG` - England

## Support

For detailed documentation, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Full Documentation**: See `API_DOCUMENTATION.md`

---

**Last Updated**: March 2026
