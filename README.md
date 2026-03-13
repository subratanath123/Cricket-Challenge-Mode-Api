# 🏏 Howzat Cricket Game API - Enhanced with Swagger Documentation

Complete REST API for the Howzat Cricket Game with comprehensive Swagger/OpenAPI documentation.

## 🚀 Quick Start

### 1. Start the API Server
```bash
./gradlew bootRun
```

### 2. Access Documentation
Open in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

### 3. Test an Endpoint
Click any endpoint → "Try it out" → "Execute"

That's it! The Swagger UI provides interactive documentation and testing for all 30 API endpoints.

## 📚 Documentation Files

| File | Purpose | When to Use |
|------|---------|-------------|
| **[Swagger UI](http://localhost:8080/swagger-ui/index.html)** | Interactive API explorer | Testing & understanding endpoints |
| **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** | Condensed endpoint reference | Quick lookups & curl examples |
| **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** | Complete integration guide | Full implementation details |
| **[SWAGGER_UPDATE_SUMMARY.md](SWAGGER_UPDATE_SUMMARY.md)** | What was changed | Understanding the documentation |

## 🎯 What's Documented

### Every endpoint includes:
- ✅ **Summary** - What it does in one line
- ✅ **Detailed Description** - How it works, when to use it, special behaviors
- ✅ **Parameters** - Every parameter explained with examples
- ✅ **Response Schemas** - All fields documented with types and examples
- ✅ **Status Codes** - What each response code means
- ✅ **Try It Out** - Interactive testing directly in browser

### 30 Endpoints Across 14 Categories:
- 🏏 Batting Packs (2)
- 🎳 Bowling Packs (2)
- 💰 Coins Packs (2)
- 💎 Diamond Packs (2)
- 🏟️ Stadium Packs (2)
- 👕 Kits Packs (3)
- 🏆 Tournament Packs (2)
- 🎙️ Commentary Packs (2)
- 📅 Subscription Packs (2)
- 🚫 No Ads Packs (2)
- 🛒 Shop Resources (2)
- 💳 Purchase (2)
- 🎮 Challenge Mode (4)
- 🖼️ Game Assets (1)

## 🔥 Key Features

### For Game Developers
- **No Postman Needed** - Test everything in your browser
- **Clear Explanations** - Every endpoint explains its purpose and usage
- **Real Examples** - See actual response structures
- **Integration Patterns** - Copy-paste ready code examples

### API Capabilities
- 🛍️ **Shop Management** - Browse products across 10 categories
- 💳 **Purchase System** - Universal purchase handling
- 👤 **User Inventory** - Track owned items per user
- 📦 **Subscription Benefits** - Auto-unlock free products
- 🎮 **Challenge Mode** - Structured gameplay challenges
- 🖼️ **Asset Management** - Centralized resource URLs
- 📱 **IAP Integration** - App store product configuration

## 📖 Example Usage

### View All Batting Packs
```bash
curl http://localhost:8080/battingPacks
```

### Get User's Purchases
```bash
curl "http://localhost:8080/battingPacks/purchased?email=player@example.com"
```

### Make a Purchase
```bash
curl "http://localhost:8080/purchase/bat_pack_001?email=player@example.com"
```

### Test in Browser
1. Go to Swagger UI
2. Click on "Batting Packs" → "GET /battingPacks"
3. Click "Try it out" → "Execute"
4. See the response instantly

## 🏗️ API Architecture

### Product Types
- **Consumable** - Coins, Diamonds (can buy multiple times)
- **Non-Consumable** - Equipment, Stadiums (one-time purchase)
- **Subscription** - Monthly/yearly plans with benefits

### Common Patterns
```
List Products    → GET /{category}
User Purchases   → GET /{category}/purchased?email={email}
Make Purchase    → GET /purchase/{productId}?email={email}
```

### Special Endpoints
```
Shop Categories  → GET /availablePacks
IAP Config       → GET /iapPacks
All Assets       → GET /assets
Challenge List   → GET /challengeList?level=Level 1
```

## 🎓 How Subscriptions Work

Subscriptions include a `freeProducts` array:
```json
{
  "id": "monthly_premium",
  "freeProducts": ["bat_pack_001", "stadium_pack_002"]
}
```

When you call any `/*/purchased` endpoint, the API automatically includes:
1. Items purchased directly by the user
2. Items from the `freeProducts` array of active subscriptions

No extra work needed - it's automatic!

## ⚠️ Important Notes

### Data Persistence
- Currently uses **in-memory storage**
- All data lost on server restart
- For production: implement database persistence

### Authentication
- Uses **email-only** for user identification
- No authentication tokens required
- For production: add JWT/OAuth2

### Response Format
- All endpoints return JSON
- Purchase endpoints return: `"purchased"` or `"failed"`
- Standard HTTP status codes (200, 400, 404, 500)

## 🧪 Testing Workflow

### Complete Test Flow
```bash
# 1. Start server
./gradlew bootRun

# 2. Get products
curl http://localhost:8080/battingPacks

# 3. Make a purchase
curl "http://localhost:8080/purchase/bat_pack_001?email=test@example.com"

# 4. Verify purchase
curl "http://localhost:8080/battingPacks/purchased?email=test@example.com"
```

Or simply use the Swagger UI "Try it out" feature!

## 📱 Game Integration Flow

### On Game Startup
```
1. GET /assets           → Preload all images
2. GET /iapPacks         → Initialize in-app purchase SDK
3. GET /availablePacks   → Build shop navigation
```

### Loading User Inventory
```
For each category:
  GET /{category}/purchased?email={userEmail}
  
Unlock owned items in game
```

### Processing Purchase
```
1. User taps "Buy" on product
2. App store processes payment
3. On success: GET /purchase/{productId}?email={userEmail}
4. Refresh: GET /{category}/purchased?email={userEmail}
5. Update game UI
```

## 🎮 Challenge Mode

### Get Challenges
```bash
# List all levels
curl http://localhost:8080/levelList

# Get challenges for Level 1
curl "http://localhost:8080/challengeList?level=Level%201&myTeam=IND"

# Track progress
curl "http://localhost:8080/progress?level=Level%201"

# Record completion
curl "http://localhost:8080/progress/update?level=1&challengeId=105&summary=Won"
```

## 🌍 Team Codes

Use these codes for kits and challenges:
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

## 🛠️ Technical Stack

- **Framework**: Spring Boot 3.3.1
- **Documentation**: SpringDoc OpenAPI 3.0
- **API Spec**: OpenAPI 3.0.1
- **Java**: 17
- **Build Tool**: Gradle 8.8

## 📊 Verification

Check everything is working:
```bash
# Build the project
./gradlew build

# Start the server
./gradlew bootRun

# Verify Swagger UI (open in browser)
http://localhost:8080/swagger-ui/index.html

# Check OpenAPI spec
curl http://localhost:8080/api-docs
```

## 💡 Tips for Game Developers

### Understanding an Endpoint
1. Find it in Swagger UI
2. Read the description (tells you everything)
3. Look at the response schema
4. Check the examples

### Testing an Endpoint
1. Click "Try it out"
2. Fill in parameters (examples provided)
3. Click "Execute"
4. See real response

### Debugging Issues
1. Check parameter requirements (required vs optional)
2. Verify email format is correct
3. Ensure product IDs exist
4. Check response status codes

### Best Practices
- Always check subscription status (expiryDate)
- Use `/availablePacks` first to understand categories
- Call `/iapPacks` on game startup for IAP init
- Load user inventory after login
- Refresh purchased items after every purchase

## 🆘 Need Help?

### Documentation Resources
- **Swagger UI** - Interactive docs: http://localhost:8080/swagger-ui/index.html
- **Quick Reference** - Fast lookups: [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Full Guide** - Complete details: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- **Change Summary** - What's new: [SWAGGER_UPDATE_SUMMARY.md](SWAGGER_UPDATE_SUMMARY.md)

### Common Questions

**Q: Where do I see all endpoints?**  
A: Open Swagger UI - they're organized by category

**Q: How do I test an endpoint?**  
A: Click on it → "Try it out" → Fill parameters → "Execute"

**Q: What does each field in the response mean?**  
A: Click "Schema" next to the response - every field is documented

**Q: How do subscriptions unlock items?**  
A: Read the "Subscription Packs" section in API_DOCUMENTATION.md

**Q: Can I use this in production?**  
A: Yes, but add authentication and database persistence first

## 📈 What's Next

### For Production Deployment
1. ✅ Add database persistence (replace in-memory maps)
2. ✅ Implement authentication (JWT/OAuth2)
3. ✅ Add receipt verification for app store purchases
4. ✅ Implement subscription expiry checks
5. ✅ Add rate limiting
6. ✅ Set up monitoring and logging
7. ✅ Deploy to production server

### For Feature Enhancement
1. Add user profiles endpoint
2. Implement leaderboards
3. Add friend system
4. Create achievements API
5. Add match history

---

## 🎉 Ready to Go!

Everything is documented and ready to use. Start the server and open Swagger UI to explore:

```bash
./gradlew bootRun
```

Then open: **http://localhost:8080/swagger-ui/index.html**

**Happy coding! 🏏**

---

*Last Updated: March 2026*  
*API Version: 1.0.0*  
*Documentation: Complete ✅*
