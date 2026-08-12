# Contributing to WorldFood 3D Adventure

Thank you for your interest in contributing to **WorldFood 3D Adventure**.

WorldFood 3D Adventure is a premium Android Match-3 travel adventure developed with Kotlin, Jetpack Compose, Firebase, and a custom Match-3 engine.

The project is currently maintained as a **proprietary game project**.

The source code may be publicly visible for portfolio, development, educational review, testing, and collaboration purposes. Public visibility of the repository does **not** automatically make the project open-source software.

Please read these guidelines before opening an issue, suggesting a feature, or submitting a pull request.

---

## 🌍 About WorldFood 3D Adventure

WorldFood 3D Adventure combines:

- 🌍 World exploration
- 🍽️ International food themes
- 🧩 Match-3 puzzle gameplay
- ⭐ Star-based progression
- 🪙 Coins and rewards
- ❤️ Lives
- 📈 XP and player levels
- 🛂 Passport progression
- 🎁 Milestone rewards
- 🏆 Achievements
- 🎵 Country-themed music
- 🔊 Gameplay sound effects
- 📳 Haptic feedback

The current Chapter 1 contains **7 countries and 105 handcrafted levels**.

---

# 🤝 Ways to Contribute

There are several ways to help improve WorldFood 3D Adventure.

You can contribute by:

- Reporting gameplay bugs
- Reporting crashes
- Reporting performance problems
- Reporting device compatibility problems
- Suggesting gameplay improvements
- Suggesting UI/UX improvements
- Suggesting accessibility improvements
- Reporting documentation problems
- Suggesting new countries
- Suggesting new foods
- Suggesting new Match-3 mechanics
- Suggesting new boosters
- Suggesting achievements
- Suggesting audio improvements
- Testing the game on different Android devices
- Improving documentation
- Proposing carefully scoped code improvements

---

# 🐛 Bug Reports

Before submitting a bug report:

1. Check whether the problem has already been reported.
2. Make sure you are testing the latest available version.
3. Try to reproduce the problem more than once.
4. Restart the application and confirm whether the problem remains.
5. If possible, test whether the problem happens consistently.

A useful bug report should include as much of the following information as possible.

### Device Information

```text
Device:
Android Version:
Screen Size / Resolution:
App Version:
```

### Game Information

```text
Country:
Level:
Game Screen:
```

### Problem

Describe exactly what happened.

### Steps to Reproduce

Example:

```text
1. Launch WorldFood 3D Adventure.
2. Open Germany.
3. Select Level 10.
4. Start the level.
5. Swap two tiles.
6. Observe the problem.
```

### Expected Behavior

Explain what you expected to happen.

### Actual Behavior

Explain what actually happened.

### Additional Information

When possible, include:

- Screenshot
- Screen recording
- Logcat output
- Crash message
- Performance measurements
- Device model
- Android version

---

# 🔐 Never Include Secrets in Bug Reports

Never publish sensitive credentials or private configuration.

Do **not** include:

- Passwords
- API keys
- Firebase private credentials
- Signing passwords
- Keystore passwords
- Keystore files
- Access tokens
- Private authentication tokens
- Personal information

If you accidentally publish a secret, revoke or rotate it immediately.

---

# 💡 Feature Requests

Feature suggestions are welcome.

A useful feature request should explain:

1. What feature you are proposing.
2. What problem it solves.
3. How the player would use it.
4. Why it improves WorldFood 3D Adventure.
5. Which part of the game it affects.

Possible categories include:

- Gameplay
- Match-3 mechanics
- Countries
- Food
- World exploration
- Passport
- Rewards
- Profile
- Progression
- Boosters
- Achievements
- UI/UX
- Accessibility
- Audio
- Performance
- Localization
- Android compatibility

---

# 🌍 Country Suggestions

WorldFood 3D Adventure is designed around international travel and food.

When suggesting a new country, consider including:

```text
Country:
Suggested Foods:
Suggested Level Theme:
Suggested Music Style:
Suggested Visual Theme:
Possible Achievement:
Possible Passport Reward:
```

Country suggestions should be respectful and avoid stereotypes.

The goal is to celebrate international food and travel through a positive game experience.

---

# 🍽️ Food Suggestions

Food suggestions should preferably be:

- Recognizable
- Visually distinctive
- Suitable for Match-3 tiles
- Relevant to the selected country or region
- Respectful of the food's cultural origin

When suggesting food content, you may include:

```text
Food Name:
Country / Region:
Short Description:
Visual Characteristics:
Possible Tile Design:
```

---

# 🛠️ Development Stack

WorldFood 3D Adventure is primarily developed using:

### Android

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Android ViewModel
- Coroutines
- StateFlow
- DataStore

### Services

- Firebase Authentication
- Firebase Analytics
- Firebase App Check
- Play Integrity

### Custom Game Systems

- Match-3 Engine
- Board Generator
- Match Detector
- Move Finder
- Cascade Processor
- Progression Manager
- Level Registry
- Audio Manager
- Sound Repository
- Haptic Manager
- Global System Manager

---

# 🏗️ Project Architecture

The project separates UI, game logic, progression, persistence, audio, and platform systems.

A simplified architecture looks like this:

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
├── UI
│   ├── World / Globe
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
├── Data
│   ├── GameProgressRepository
│   ├── DataStore
│   └── Firebase Services
│
├── Audio
│   ├── AudioManager
│   └── SoundRepository
│
└── Haptics
    └── HapticManager
```

Contributions should respect the existing separation of responsibilities whenever possible.

---

# 💻 Code Guidelines

Code contributions should follow existing Kotlin and Android conventions.

Please:

- Keep functions focused.
- Keep code readable.
- Use descriptive names.
- Avoid unnecessary duplication.
- Preserve the existing architecture.
- Prefer immutable state where appropriate.
- Keep gameplay logic separate from UI logic.
- Avoid unnecessary global mutable state.
- Handle nullable values safely.
- Handle Android lifecycle changes correctly.
- Use coroutines responsibly.
- Cancel background work when appropriate.
- Avoid blocking the Android main thread.
- Preserve accessibility semantics.
- Add comments only where they provide useful context.

---

# ⚡ Performance Requirements

Performance is important for WorldFood 3D Adventure, particularly on lower-powered Android devices and emulators.

Contributions should avoid:

- Heavy computation on the main thread
- Unnecessary Compose recomposition
- Unbounded loops
- Excessive object creation inside animation loops
- Repeated full-board scans when localized checks are sufficient
- Excessive GPU effects
- Unnecessary large gradients
- Excessive particle effects
- Repeated disk reads during gameplay
- Repeated network requests during gameplay
- Blocking audio initialization
- Unbounded board-generation attempts

Expensive work should be moved away from the UI thread when appropriate.

---

# 🌐 Globe and Rendering Performance

The world/globe experience is one of the visually intensive parts of the application.

Changes to globe rendering should preserve:

- Lifecycle-aware animation
- Safe background behavior
- Reasonable GPU usage
- Reasonable CPU usage
- Stable frame pacing
- Tablet compatibility
- Phone compatibility

Animations should stop or reduce unnecessary work when the relevant screen is no longer active.

---

# 🧩 Match-3 Engine Guidelines

Changes to the Match-3 engine require special care.

The engine should preserve the following guarantees:

- Only valid adjacent swaps are accepted.
- Invalid diagonal swaps are rejected.
- Generated boards should not begin with unintended automatic matches.
- Generated boards should provide at least one valid move.
- Dead boards can be detected.
- Dead boards can be reshuffled safely.
- Cascades remain bounded.
- Special tiles behave correctly.
- Chain reactions remain safe.
- Player input does not corrupt board resolution.
- Booster inventories cannot become negative.
- Game state remains internally consistent.

---

# 🎲 Board Generation

Board generation should remain efficient and bounded.

Avoid returning to retry-heavy random generation strategies that repeatedly generate complete boards and perform expensive full-board validation.

Where appropriate, prefer:

- Constructive generation
- Local validation
- Bounded retries
- Deterministic safeguards
- Efficient valid-move detection

Changes to board generation should be tested carefully.

---

# 💥 Special Tiles

Changes involving special tiles should preserve expected behavior for:

- Row Clear
- Column Clear
- Bomb
- Color Bomb
- Cascades
- Chain reactions

Special-tile interactions should not create infinite loops or corrupt board state.

---

# 🚀 Boosters

Current booster concepts include:

- Hammer
- Shuffle
- Extra Moves

Changes involving boosters should verify:

- Correct inventory deduction
- No negative inventory
- Correct board state
- Correct move state
- Correct progression persistence
- Safe interaction with special tiles
- Safe interaction with cascades

---

# 🏆 Progression

Changes involving progression should preserve:

- Stars
- Coins
- Lives
- XP
- Player Level
- Country Unlocks
- Passport Progress
- Rewards
- Achievements

Progression changes should not unintentionally reset existing player data.

When changing persistence structures, backward compatibility should be considered.

---

# 💾 Persistence

Player progress is stored locally using Android DataStore where applicable.

Persistence-related changes should:

- Avoid unnecessary disk operations
- Preserve existing player progress
- Handle missing values safely
- Provide sensible defaults
- Avoid corrupting saved state
- Avoid blocking the main thread

---

# 🎵 Audio Guidelines

Audio changes should preserve:

- Background music
- Sound effects
- Music settings
- Sound-effect settings
- Country themes
- Victory sounds
- Lifecycle behavior

Audio initialization should not block the main thread.

Background/foreground transitions should be tested after significant audio changes.

---

# 📳 Haptic Guidelines

Haptic changes should preserve safe Android behavior.

The project should continue to handle:

- Vibration capability checks
- Missing vibration hardware
- Android permission requirements
- SecurityException protection
- Safe fallback behavior

A haptic failure should never crash the game.

---

# ♿ Accessibility

Accessibility improvements are encouraged.

When changing the UI, consider:

- Content descriptions
- Touch-target sizes
- Text readability
- Contrast
- Screen-reader semantics
- Meaningful button labels
- Clear game-state communication
- Tablet layouts
- Phone layouts

Do not rely exclusively on color to communicate important gameplay information where practical.

---

# 📱 Device Compatibility

Changes should ideally be tested on more than one screen size.

Important targets include:

- Android phones
- Large Android phones
- Android tablets
- Different aspect ratios
- Portrait layouts used by the game
- Supported Android API levels

---

# 🧪 Testing

Changes to gameplay systems should include or update tests where appropriate.

Important test areas include:

- Match detection
- Board generation
- Move detection
- Special tiles
- Cascades
- Boosters
- Progression
- Level definitions
- Persistence
- Settings
- Country unlocking

---

# ✅ Local Validation

Before submitting a pull request, run the relevant validation commands.

On macOS or Linux:

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew lintDebug
```

For release-sensitive changes:

```bash
./gradlew bundleRelease
```

On Windows:

```powershell
gradlew.bat testDebugUnitTest
gradlew.bat assembleDebug
gradlew.bat lintDebug
```

For release-sensitive changes:

```powershell
gradlew.bat bundleRelease
```

---

# 📋 Pull Request Checklist

Before submitting a pull request, confirm:

- [ ] The project builds successfully.
- [ ] Relevant tests pass.
- [ ] Lint has been checked.
- [ ] The change has a clear purpose.
- [ ] Unrelated code has not been modified unnecessarily.
- [ ] No credentials are included.
- [ ] No signing files are included.
- [ ] No private data is included.
- [ ] Performance has been considered.
- [ ] UI changes were tested on an appropriate screen size.
- [ ] Gameplay changes preserve Match-3 correctness.
- [ ] Documentation was updated when necessary.

---

# 🔀 Pull Request Description

A good pull request should explain:

### What changed?

Describe the implementation.

### Why was it changed?

Explain the problem or improvement.

### What systems are affected?

For example:

```text
Match-3 Engine
Progression
Audio
World Map
Passport
Rewards
Profile
Settings
Firebase
Performance
```

### Testing

Include the validation results.

Example:

```text
testDebugUnitTest: PASS
assembleDebug: PASS
lintDebug: PASS
```

For release-sensitive changes:

```text
bundleRelease: PASS
```

### Screenshots

For visual changes, include before/after screenshots when appropriate.

---

# 📝 Commit Messages

Use short and descriptive commit messages.

Good examples:

```text
Fix level launch performance
Improve globe lifecycle handling
Optimize Match-3 move detection
Add progression regression tests
Improve passport accessibility
Fix country unlock state
Update rewards screen layout
Improve tablet rendering performance
```

Avoid vague messages such as:

```text
update
changes
stuff
fix
new code
final
test
```

---

# 🌿 Branches

For larger contributions, use a separate branch.

Examples:

```text
feature/new-country
feature/new-booster
fix/level-launch
fix/audio-lifecycle
performance/board-generation
docs/update-readme
```

Keep each branch focused on one logical change whenever possible.

---

# 🔐 Security Issues

Security vulnerabilities must **not** be disclosed publicly through normal GitHub Issues.

Examples include:

- Exposed credentials
- Authentication vulnerabilities
- Firebase security problems
- Integrity bypasses
- Sensitive information exposure
- Signing-related security problems

Please follow the project's security policy:

[SECURITY.md](SECURITY.md)

---

# 🚨 Sensitive Files

Never commit:

```text
keystore.properties
*.jks
*.keystore
local.properties
private API keys
password files
service credentials
access tokens
private certificates
```

Always inspect your Git changes before committing.

---

# 📦 Generated Files

Avoid committing unnecessary generated files such as:

```text
build/
app/build/
.gradle/
local build output
temporary IDE files
```

The repository's `.gitignore` should be respected.

---

# 📚 Documentation

Documentation contributions are welcome.

Documentation should be:

- Clear
- Accurate
- Easy to understand
- Consistent with current behavior
- Written in professional language

Important project documentation includes:

```text
README.md
SECURITY.md
LICENSE
CONTRIBUTING.md
```

---

# 🗣️ Communication

Project discussions should remain:

- Respectful
- Constructive
- Relevant
- Technical when appropriate
- Welcoming to different experience levels

Disagreement about implementation is acceptable.

Personal attacks, harassment, spam, abusive behavior, or intentionally harmful contributions are not acceptable.

---

# ⚖️ Licensing

WorldFood 3D Adventure is currently maintained as proprietary software.

Public access to the repository does not automatically grant permission to:

- Redistribute the complete project
- Republish the application
- Sell the application
- Rebrand the game
- Publish modified versions
- Use proprietary game assets commercially
- Publish the game on another app store
- Claim the project as your own work

Please read:

[LICENSE](LICENSE)

before using or contributing to project materials.

---

# ©️ Project Assets

Game-specific assets may include:

- Artwork
- UI designs
- Food graphics
- Logos
- Branding
- Music
- Sound effects
- Screenshots
- Game concepts
- Documentation

These materials may have separate ownership or third-party licensing requirements.

Do not assume that an asset can be reused simply because it is visible in the repository.

---

# 👨‍💻 Project Owner

**WorldFood 3D Adventure**

Maintained by:

**Mahmod Hota**

Project:

```text
WorldFood 3D Adventure
```

Technology:

```text
Kotlin
Jetpack Compose
Firebase
Custom Match-3 Engine
Android
```

---

# ❤️ Thank You

Thank you for taking the time to help improve **WorldFood 3D Adventure**.

Bug reports, testing, constructive feedback, documentation improvements, and carefully designed contributions can all help make the project better.

**Explore the world. Discover food. Master the puzzle.**
