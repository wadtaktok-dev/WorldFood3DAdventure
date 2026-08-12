# 🌍 WorldFood 3D Adventure

<p align="center">
  <strong>Travel the World • Discover Food • Master the Puzzle</strong>
</p>

<p align="center">
  A premium Android Match-3 travel adventure built with Kotlin and Jetpack Compose.
</p>

---

## 🎮 About the Game

**WorldFood 3D Adventure** combines world exploration, international food culture, and Match-3 puzzle gameplay in one Android adventure.

Travel across a stylized world map, unlock countries, complete handcrafted puzzle levels, collect stars and coins, earn passport stamps, discover international dishes, and experience country-themed music and sound effects.

The current Chapter 1 contains:

- 🌍 7 countries
- 🧩 105 handcrafted Match-3 levels
- ⭐ Star-based progression
- 🪙 Coins and rewards
- ❤️ Lives
- 📈 XP and player levels
- 🛂 Passport progression
- 🎁 Milestone rewards
- 🏆 Achievements
- 🎵 Country-themed audio
- 📳 Haptic feedback
- 🚀 Special tiles and boosters

---

## 🌍 Countries & Levels

| Country | Levels | Unlock Requirement |
|---------|-------:|-------------------:|
| 🇩🇪 Germany | 15 | 0 Stars |
| 🇮🇹 Italy | 15 | 30 Stars |
| 🇫🇷 France | 15 | 60 Stars |
| 🇪🇸 Spain | 15 | 90 Stars |
| 🇯🇵 Japan | 15 | 120 Stars |
| 🇲🇽 Mexico | 15 | 145 Stars |
| 🇸🇩 Sudan | 15 | 185 Stars |

### Total

**105 handcrafted levels**

Sudan Level 15 completes **Chapter 1** and awards the special **World Explorer** achievement.

---

## 🧩 Match-3 Gameplay

The game uses a custom Match-3 engine developed specifically for WorldFood 3D Adventure.

Core gameplay systems include:

- Adjacent tile swapping
- Match detection
- Invalid-swap detection
- Automatic match resolution
- Cascades
- Chain reactions
- Dead-board detection
- Automatic reshuffling
- Move limits
- Score objectives
- Level-specific goals
- Deterministic board validation
- Optimized board generation

---

## 💥 Special Tiles

Larger and specially shaped matches can create powerful special tiles.

Available special tiles include:

- ➡️ Row Clear
- ⬇️ Column Clear
- 💣 Bomb
- 🌈 Color Bomb

Special tiles can interact with cascades and trigger chain reactions.

---

## 🚀 Boosters

The current booster system includes:

- 🔨 Hammer
- 🔀 Shuffle
- ➕ Extra Moves

Booster inventory is persisted with the player's game progress.

---

## 🏆 Player Progression

WorldFood 3D Adventure includes a complete progression system.

Players can collect and progress through:

- ⭐ Stars
- 🪙 Coins
- ❤️ Lives
- 📈 XP
- 🎚️ Player Levels
- 🌍 Country Unlocks
- 🛂 Passport Stamps
- 🎁 Milestone Rewards
- 🏅 Achievements

Gameplay progress is stored locally using Android DataStore.

---

## 🛂 Passport System

The Passport screen tracks the player's journey around the world.

As countries are completed, the player's passport progression is updated with completed destinations and country achievements.

The passport system is directly connected to the game's progression state.

---

## 🎁 Rewards

The Rewards system reflects actual player progression.

Rewards can be connected to:

- Level completion
- Star milestones
- Country completion
- Global progression
- Achievements
- Chapter milestones

---

## 👤 Player Profile

The Profile screen displays player statistics and progression information.

Current profile information includes:

- Player Level
- XP
- Stars
- Coins
- Lives
- World Progress
- Country Progress
- Gameplay Statistics

---

## 🎵 Audio System

WorldFood 3D Adventure includes a dedicated audio system for music, gameplay effects, and UI feedback.

### Background Music

Current music assets include:

- `world_map.ogg`
- `germany_theme.ogg`
- `italy_theme.ogg`
- `france_theme.ogg`
- `japan_theme.ogg`
- `mexico_theme.ogg`

### Sound Effects

The project contains gameplay and UI sound effects for events such as:

- Button clicks
- Tile selection
- Valid swaps
- Invalid swaps
- Small matches
- Large matches
- Combos
- Cascades
- Coin collection
- Star rewards
- XP rewards
- Level unlocks
- Country unlocks
- Victory
- Defeat
- Country-specific victory events

Audio resources are stored in:

```text
app/src/main/res/raw/
```

Music and sound effects can be controlled through the in-game Settings screen.

---

## 📳 Haptic Feedback

The game supports Android haptic feedback.

Safety protections include:

- `android.permission.VIBRATE`
- Device vibration capability checks
- `SecurityException` protection
- Safe fallback behavior
- Centralized haptic management

---

## 🛠️ Technology Stack

WorldFood 3D Adventure is built with modern Android development technologies.

### Android

- Kotlin
- Jetpack Compose
- Material 3
- Android Studio
- Android ViewModel
- Coroutines
- StateFlow
- DataStore

### Firebase

The project integrates selected Firebase services:

- Firebase Authentication
- Firebase Analytics
- Firebase App Check
- Play Integrity Provider

### Game Technology

Custom systems include:

- Match-3 Engine
- Board Generator
- Match Detector
- Move Finder
- Cascade Processor
- Level Registry
- Progression Manager
- Audio Manager
- Haptic Manager
- Global System Manager

---

## 🏗️ Architecture

The project follows a state-driven Android architecture with separation between UI, game logic, persistence, and global systems.

```text
WorldFood3DAdventure
│
├── MainActivity
│
├── Navigation
│
├── GlobalSystemManager
│
├── ProgressionManager
│
│
├── UI
│   ├── Globe / World
│   ├── Country Selection
│   ├── Level Selection
│   ├── Match-3 Gameplay
│   ├── Passport
│   ├── Rewards
│   ├── Profile
│   └── Settings
│
├── Match-3 Engine
│   ├── Match3Engine
│   ├── BoardGenerator
│   ├── MatchDetector
│   ├── MoveFinder
│   └── CascadeProcessor
│
├── Data Layer
│   ├── GameProgressRepository
│   ├── DataStore
│   └── Firebase Services
│
├── Audio
│   ├── AudioManager
│   ├── SoundRepository
│   └── Music / SFX
│
└── Haptics
    └── HapticManager
```

The gameplay engine is separated from the Compose UI so that important game logic can be tested independently.

---

## ⚡ Performance

Performance optimization is an important part of the project.

Implemented optimizations include:

- Constructive Match-3 board generation
- Localized valid-move detection
- Background-thread board preparation
- Bounded board-generation retries
- Bounded cascade processing
- Lifecycle-aware globe rendering
- Background audio initialization
- Navigation debounce protection
- Reduced unnecessary rendering work
- Optimized Match-3 launch path

A previous Match-3 level-start bottleneck was traced to expensive random board generation and full-board move scans.

The generation system was redesigned to use constructive board generation and localized move validation.

Measured level-launch times were reduced from multi-second delays to approximately sub-second loading on tested devices.

---

## 🔐 Security

Security practices currently include:

- No hardcoded passwords
- No committed signing passwords
- Release signing configuration separated from source control
- `keystore.properties` excluded from Git
- Firebase App Check support
- Play Integrity support
- Safe haptic handling
- Release minification
- Resource shrinking

### Security Vulnerabilities

Please do **not** publicly disclose security vulnerabilities through GitHub Issues.

See:

[SECURITY.md](SECURITY.md)

for responsible vulnerability reporting instructions.

---

## 🔒 Privacy

WorldFood 3D Adventure does not require players to provide a name, email address, contacts, or similar directly identifying profile information for normal gameplay.

Gameplay information such as:

- Level progress
- Stars
- Coins
- Lives
- Settings
- Player progression

is stored locally where applicable.

Firebase services may process technical information necessary for analytics, authentication, integrity verification, and service operation.

A dedicated Privacy Policy will accompany the public release.

---

## 🚫 Advertising & Purchases

The current release does not include:

- ❌ Advertising
- ❌ In-app purchases
- ❌ Paid subscriptions

The focus of the current release is the core WorldFood 3D Adventure gameplay experience.

---

## ✅ Testing

The project has an automated test suite covering important gameplay and progression systems.

Current Phase 13 validation:

```text
Automated Tests: 252 PASS
Failed Tests:     0
Skipped Tests:    0

assembleDebug:    PASS
lintDebug:        PASS
bundleRelease:    PASS
```

Testing covers systems including:

- Match detection
- Special tiles
- Board integrity
- Move detection
- Progression
- Level configuration
- Settings persistence
- Game-state behavior

---

## 📱 Android Configuration

```text
Application ID:
com.mahmodhota.worldfood3dadventure

Namespace:
com.mahmodhota.worldfood3dadventure

Minimum SDK:
26

Target SDK:
37

Compile SDK:
37
```

---

## 📦 Release Status

Current release candidate:

```text
Version Code:
10001

Version Name:
1.0.0-rc1
```

Recommended public release version:

```text
1.0.0
```

Release builds use:

- Release signing
- R8 minification
- Resource shrinking
- Non-debuggable release configuration
- Android App Bundle generation

Current release bundle location:

```text
app/build/outputs/bundle/release/app-release.aab
```

---

## 📸 Screenshots

Official screenshots will be added as part of the public-release preparation.

Planned showcase screenshots include:

1. 🌍 World / Globe
2. 🗺️ Country Selection
3. 🧩 Match-3 Gameplay
4. 💥 Special Tiles
5. 🛂 Passport
6. 🎁 Rewards
7. 👤 Player Profile
8. 🏆 Chapter 1 Finale

---

## 🗺️ Development Roadmap

Possible future development includes:

- 🌍 Additional countries
- 📖 Chapter 2
- 🍽️ Additional international foods
- 🧩 New Match-3 mechanics
- 🚀 Additional boosters
- 🏆 More achievements
- 🌐 Expanded language support
- 🎨 Enhanced country-specific visual themes
- 🎵 Expanded music package
- ♿ Additional accessibility features
- 📱 Additional device optimization

---

## 🤝 Contributing

WorldFood 3D Adventure is currently under active development.

Contribution guidelines will be provided in:

`CONTRIBUTING.md`

Please open an issue before proposing major gameplay, architectural, or structural changes.

---

## 🐛 Bug Reports

Normal gameplay and UI bugs may be reported through GitHub Issues.

Please include:

- Android version
- Device model
- Game version
- Country and level
- Steps to reproduce
- Expected behavior
- Actual behavior
- Screenshot or screen recording when possible

### Security Issues

Security vulnerabilities should **not** be submitted as public bug reports.

Please follow:

[SECURITY.md](SECURITY.md)

---

## 📄 License

Licensing information will be provided before the final public release.

Unless explicitly stated otherwise, source code, artwork, audio, branding, game assets, and other project materials should **not** be assumed to be available for unrestricted reuse.

---

## 👨‍💻 Developer

**WorldFood 3D Adventure**

Developed by:

**Mahmod Hota Dev WorldBooks Studio**

Built with:

**Kotlin • Jetpack Compose • Firebase • Custom Match-3 Engine**

---

## 🌟 Project Status

```text
PROJECT
WorldFood 3D Adventure

CHAPTER
Chapter 1

COUNTRIES
7

LEVELS
105

AUTOMATED TESTS
252 PASS

DEBUG BUILD
PASS

LINT
PASS

RELEASE BUNDLE
PASS

STATUS
Release Candidate
```

---

<p align="center">
  <strong>🌍 Explore • 🍽️ Discover • 🧩 Match • 🏆 Conquer the World</strong>
</p>

<p align="center">
  <strong>WorldFood 3D Adventure</strong>
</p>
