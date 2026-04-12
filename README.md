# 🎮 Animator Engine - Java2D Game Framework

> A **production-ready, Maven-based Java2D game framework** designed for creating full-featured 2D games with minimal boilerplate. Built with clean architecture patterns, extensive configuration options, and comprehensive tooling.

## 📋 Table of Contents

1. [Overview](#overview)
2. [Key Features](#key-features)
3. [Architecture](#architecture)
4. [Quick Start](#quick-start)
5. [Project Structure](#project-structure)
6. [Core Components](#core-components)
7. [Configuration](#configuration)
8. [Game Development Workflow](#game-development-workflow)
9. [Usage Examples](#usage-examples)
10. [Creating New Games](#creating-new-games)
11. [Performance Notes](#performance-notes)
12. [Contributing](#contributing)

---

## 🎯 Overview

**Animator Engine** is a comprehensive Java2D game framework that abstracts common game development tasks while maintaining flexibility and control. It's designed to be:

- **Framework**: Reusable base for multiple games
- **Configurable**: Runtime properties without code changes
- **Extensible**: Clean interfaces for custom implementations
- **Production-Ready**: Professional architecture patterns included
- **Maven-Based**: Easy dependency management and distribution

### Use Cases

✅ Create 2D puzzle games
✅ Build platformers
✅ Develop action/arcade games
✅ Create turn-based strategy games
✅ Prototype new game ideas quickly
✅ Build educational games

---

## ✨ Key Features

### 🎮 Core Game Loop
- **Fast Forward Mode**: Toggle unlimited FPS instantly by holding the **TAB** key for rapid testing or gameplay acceleration.
- Professional game engine with configurable frame rates
- Fixed timestep physics support
- Input handling (keyboard + mouse)
- Anti-aliasing and rendering optimization

### 🎨 Graphics & Rendering
- Hardware-accelerated Swing rendering
- Double-buffering for flicker-free graphics
- Fullscreen and windowed modes
- Multiple color depth support (16-bit, 24-bit, 32-bit)
- **Hardware-Accelerated Image Caching**: Integrated `ImageManager` for optimized asset storage.
- **Bootstrap & Background Loading**: Non-blocking resource loading system with support for splash screens and progress tracking.
- Rendering optimization with automatic `GraphicsConfiguration` compatibility.

### 🎵 Audio System
- **Hardware-accelerated OGG support** via LWJGL (OpenAL & STB)
- Real-time volume control (0% to 100%)
- Mute/unmute and Looping functionality
- High-performance decoding for Music and SFX

### 🎮 Input Handling
- Configurable keyboard input
- Mouse support (position, buttons, clicks)
- Key state tracking
- Alt+Tab blocking (optional)

### 🎯 Game State Management
- **Centralized Asset Management**: `LoadResources` class for easy registration of all game assets (Images, SFX, Music).
- **Decoupled Architecture**: Clean separation between resource loading (Managers) and resource usage (Utils).
- Finite State Machine pattern
- Professional state transitions
- Intro screens, menus, gameplay, pause, game over
- Extensible state interface

### 📱 UI Components
- Main menu with customizable options
- Options/settings screen (graphics, sound, gameplay)
- Graphics settings (resolution, color depth, fullscreen)
- Sound settings (volume control, muting)
- Score display and high score tracking
- Pause menu functionality

### ⚙️ Configuration System
- Runtime editable `config.ini`
- Boolean flags for feature toggles
- Easy customization without recompilation
- Persistent settings saved to disk

### 🏭 Factory Pattern
- CoreGameFactory for creating game components
- Decoupled from specific implementations
- Easy to extend and customize

---

## 🏗️ Architecture

### Design Patterns Used

| Pattern | Where | Purpose |
|---------|-------|----------|
| **Factory** | `CoreGameFactory` | Create game components |
| **State Machine** | `GameStateMachine` | Manage game states |
| **Manager/Cache** | `ImageManager` & `AudioManager` | Asset caching and hardware optimization |
| **Bootstrap** | `LoadResources` | Two-stage resource initialization |
| **Strategy** | `Game` implementations | Different game behaviors |
| **Singleton** | `GameConfig` | Single configuration instance |
| **Template Method** | `AbstractGame` | Standard game structure |
| **Observer** | Mouse/Keyboard handlers | Input event handling |

### Layered Architecture

```
┌─────────────────────────────────────┐
│     Game Implementation             │  Your custom game logic
├─────────────────────────────────────┤
│     Game Framework (AbstractGame)   │  Base class with game loop
├─────────────────────────────────────┤
│     Resource Management Layer       │  ImageManager, AudioManager, LoadResources
├─────────────────────────────────────┤
│     Game Engine (GameEngine)        │  Core loop, timing, rendering
├─────────────────────────────────────┤
│     Window Management (Window)      │  Fullscreen, resolution, modes
├─────────────────────────────────────┤
│     Input Handling (Keyboard/Mouse) │  User input processing
├─────────────────────────────────────┤
│     UI Components & State Machine   │  Menus, options, screens
├─────────────────────────────────────┤
│     Configuration System            │  Runtime settings
└─────────────────────────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites

```bash
# Required
Java 14 or higher
OpenGL compatible drivers (for OpenAL)
Maven 3.6 or higher

# Verify installation
java -version
mvn --version
```

### Build the Framework

```bash
# Clean, compile, and package
mvn clean package

# Install locally for use in other projects
mvn install
```

### Create Your First Game

**See document: [TEMPLATE_NEW_GAME.md](TEMPLATE_NEW_GAME.md)**

Quick summary:

1. Create Maven project with dependency on `animator-engine`
2. Extend `AbstractGame` class
3. Implement `handleInput()`, `updateGame()`, `drawGame()`
4. Create Launcher extending `Launcher` class
5. Build and run!

---

## 📁 Project Structure

```
animator-engine/
├── src/
│   └── br/com/game/animator/
│       ├── Launcher.java                    # Entry point
│       ├── engine/
│       │   └── GameEngine.java             # Core game loop
│       ├── game/
│       │   ├── Game.java                   # Game interface
│       │   ├── LoadResources.java          # Resource registration
│       │   ├── core/
│       │   │   ├── AbstractGame.java       # Base implementation
│       │   │   └── IGame.java              # Core interface
│       │   ├── factory/
│       │   │   └── CoreGameFactory.java    # Object factory
│       │   ├── gameData/
│       │   │   ├── GameGraphics.java       # Graphics configuration
│       │   │   ├── GameOptions.java        # Game options
│       │   │   ├── GameScore.java          # Score tracking
│       │   │   ├── GameSoundOptions.java   # Audio settings
│       │   │   └── enumerators/
│       │   │       ├── DeepColor.java      # Color depths
│       │   │       ├── GameDifficulty.java # Difficulty levels
│       │   │       └── ScreenMode.java     # Display modes
│       │   ├── gameUI/
│       │   │   ├── CoreGameLogic.java      # UI logic marker
│       │   │   ├── advertise/              # Developer ads
│       │   │   ├── intro/                  # Intro screens
│       │   │   ├── loading/                # Loading screen
│       │   │   ├── menu/                   # Main menu
│       │   │   ├── options/                # Options screens
│       │   │   └── score/                  # Score display
│       │   └── state/
│       │       ├── GameStateMachine.java   # State management
│       │       └── GameStates.java         # State enumeration
│       ├── input/
│       │   ├── InputHandler.java           # Input interface
│       │   └── MouseHandler.java           # Mouse processing
│       ├── util/
│       │   ├── AltTabStopper.java          # Alt+Tab blocker
│       │   ├── GlobalProperties.java       # Global settings
│       │   ├── ImageManager.java           # Internal image cache
│       │   ├── ImageUtil.java              # Image utilities
│       │   └── ValueComparator.java        # Comparator utility
│       ├── window/
│       │   └── Window.java                 # Window management
│       └── exceptions/
│           └── FullScreenNotSupportedException.java
├── src/res/
│   ├── config/
│   │   └── config.ini                      # Configuration file
│   └── images/                             # Game resources
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

---

## 🔩 Core Components

### GameEngine
The heart of the framework. Manages:
- Game loop with configurable FPS (default: 60)
- Rendering cycle (clear → update → draw → display)
- Frame timing and delta time
- Window management
- Input processing

```java
public class GameEngine {
    public GameEngine(Game game, Window window) { }
    public void startGameEngine() { }  // Start the loop
    public void stopGameEngine() { }   // Stop the loop
    public void setTargetFPS(int fps) { } // Dynamic FPS control
}
```

### AbstractGame
Base class for all games. Provides:
- Standard game loop structure (handleInput, updateGame, drawGame)
- Lifecycle methods (init, cleanup)
- State machine integration
- Configuration access

```java
public abstract class AbstractGame implements IGame {
    public abstract void handleInput(Game game, int keyCode, boolean isAltDown);
    public abstract void updateGame();
    public abstract void drawGame(Graphics2D g);
}
```

### GameStateMachine
Manages game flow:
- Loading → Intro → Menu → Gameplay → Pause → GameOver → Menu
- Customizable state transitions
- State-specific rendering and input handling

### Window
Handles display:
- Fullscreen/windowed modes
- Resolution management (800x600, 1024x768, etc.)
- Color depth selection (16-bit, 24-bit, 32-bit)
- Per-monitor DPI awareness

### CoreGameFactory
Creates game components:
- Graphics objects
- Sound objects
- UI screens
- Game state machine

### GameConfig
Singleton configuration holder:
- Graphics settings
- Sound settings
- Game options
- Score tracking

---

## ⚙️ Configuration

### config.ini Format

```ini
# Developer Features
dev_logo_enabled=true          # Show developer intro
intro_screen_enabled=true      # Show animated intro
high_score_screen_enabled=true # Show high scores
```

### Setting at Runtime

```java
GameConfig config = GameConfig.getInstance();
config.setDevLogoEnabled(false);        // Disable dev logo
config.setIntroScreenEnabled(true);     // Enable intro
config.setHighScoreScreenEnabled(true); // Enable scores
```

### Graphics Configuration

```java
GameGraphics graphics = config.getGameGraphics();
graphics.setResolution(1024, 768);
graphics.setColorDepth(DeepColor.BIT_32);
graphics.setScreenMode(ScreenMode.FULLSCREEN);
```

---

## 🎮 Game Development Workflow

### Step 1: Extend AbstractGame
```java
public class MyGame extends AbstractGame {
    @Override
    public void handleInput(Game game, int keyCode, boolean isAltDown) {
        // Process keyboard input
    }
    
    @Override
    public void updateGame() {
        // Update game logic (called ~60x per second)
    }
    
    @Override
    public void drawGame(Graphics2D g) {
        // Render graphics
    }
}
```

### Step 2: Create Launcher
```java
public class MyGameLauncher extends Launcher {
    public static void main(String[] args) {
        new MyGameLauncher();
    }
}
```

### Step 3: Configure pom.xml
```xml
<dependency>
    <groupId>br.com.game</groupId>
    <artifactId>animator-engine</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step 4: Build & Run
```bash
mvn clean package
java -jar target/my-game-1.0.0.jar
```

---

## 💡 Usage Examples

### Example 1: Simple Input Handling
```java
@Override
public void handleInput(Game game, int keyCode, boolean isAltDown) {
    if (keyCode == KeyEvent.VK_LEFT) {
        playerX -= 5;
    } else if (keyCode == KeyEvent.VK_RIGHT) {
        playerX += 5;
    }
}
```

### Example 2: Game State Updates
```java
@Override
public void updateGame() {
    // Update positions
    playerY += velocity;
    
    // Check collisions
    if (checkCollision(playerX, playerY)) {
        // Handle collision
    }
    
    // Update score
    score++;
}
```

### Example 3: Graphics Rendering
```java
@Override
public void drawGame(Graphics2D g) {
    // Clear background
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, screenWidth, screenHeight);
    
    // Draw player
    g.setColor(Color.WHITE);
    g.fillRect(playerX, playerY, 32, 32);
    
    // Draw score
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Score: " + score, 10, 30);
}
```

### Example 4: Sound Effects
```java
public void playSound() {
    GameSoundOptions soundOptions = GameConfig.getInstance()
        .getGameSoundOptions();
    if (!soundOptions.isMuted()) {
        // Play sound effect
    }
}
```

---

## 🎮 Creating New Games

**Complete guide: [TEMPLATE_NEW_GAME.md](TEMPLATE_NEW_GAME.md)**

Minimal 10-step process:
1. Setup Maven project
2. Add animator-engine dependency
3. Create game class extending AbstractGame
4. Create launcher extending Launcher
5. Implement game logic (3 methods)
6. Add resources (images, sounds, config)
7. Build with Maven
8. Test locally
9. Package as JAR
10. Share or publish

---

## ⚡ Performance Notes

### Benchmarks
- **Frame Rate**: 60 FPS target (configurable)
- **Memory**: ~50-100 MB per game instance
- **Startup Time**: ~2-3 seconds
- **Input Latency**: <16ms keyboard, <32ms mouse

### Optimization Tips
- Use object pooling for frequently created objects
- Cache Graphics2D renders when possible
- Enable fullscreen for better performance
- Use 16-bit color depth for older systems
- Profile with JProfiler or YourKit

### Troubleshooting Performance
- If FPS drops, reduce screen resolution
- Check for excessive object creation in update loop
- Use `java -Xmx512m` to allocate more memory
- Profile CPU with Java Flight Recorder

---

## 🤝 Contributing

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable names (Portuguese or English consistently)
- Document public methods with JavaDoc
- Keep methods under 50 lines when possible

### Testing
- Unit tests in `src/test/java`
- Run tests: `mvn test`
- Target: >80% code coverage

### Submitting Changes
1. Create feature branch: `git checkout -b feature/my-feature`
2. Commit changes: `git commit -am 'Add feature'`
3. Push to branch: `git push origin feature/my-feature`
4. Create Pull Request
5. Wait for review and CI/CD to pass

---

## 📚 Documentation Index

| Document | Purpose |
|----------|----------|
| [START_HERE.md](START_HERE.md) | Quick orientation guide |
| [MAVEN_SETUP.md](MAVEN_SETUP.md) | Maven configuration details |
| [MAVEN_QUICKSTART.md](MAVEN_QUICKSTART.md) | 5-step quick start |
| [TEMPLATE_NEW_GAME.md](TEMPLATE_NEW_GAME.md) | Create new game guide |
| [NEXT_STEPS.md](NEXT_STEPS.md) | Recommended next actions |
| [PROJECT_OVERVIEW.md](PROJECT_OVERVIEW.md) | Architecture overview |
| [SETUP_VERIFICATION.md](SETUP_VERIFICATION.md) | Verification checklist |
| [DOCUMENTATION_INDEX.md](DOCUMENTATION_INDEX.md) | Complete documentation index |
| [CHANGELOG.md](CHANGELOG.md) | Version history |

---

## 📞 Support

### Common Questions

**Q: Can I use this for commercial games?**
A: Yes! The framework is designed for production use.

**Q: How do I add new game screens?**
A: Extend `CoreGameLogic` interface and implement in `gameUI` package.

**Q: Can I compile to native executable?**
A: Yes, use GraalVM native-image or jpackage (Java 14+).

**Q: What JDK versions are supported?**
A: Java 14+. Java 17 LTS, Java 21 LTS recommended.

---

## 📄 License

This project is provided as-is for educational and commercial game development.

---

## 🚀 Version History

See [CHANGELOG.md](CHANGELOG.md) for complete version history and improvements.

---

**Last Updated:** April 2026
**Framework Version:** 1.0.0
**Status:** ✅ Production Ready
