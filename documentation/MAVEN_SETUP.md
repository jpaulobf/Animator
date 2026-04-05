# Animator Engine

A reusable, production-ready **Java2D game engine framework** designed to eliminate boilerplate code and provide a solid foundation for building 2D games in Java.

## Features

✅ **Game Loop & Timing**
- High-precision nanosecond-based timing (supports 30/60/90/120/240 FPS)
- FPS/UPS tracking with circular buffer statistics
- Adaptive delta-time with edge case handling

✅ **State Machine**
- Centralized game state management with configuration-driven transitions
- Optional screen flow (Dev Logo, Intro, High Score screens)
- Configurable via `config.ini`

✅ **Window Management**
- Fullscreen/windowed mode support with enum-based resolution management
- Thread-safe dimension handling with `CountDownLatch` synchronization
- Multiple aspect ratios (4:3, 16:9, 16:10)

✅ **Input Handling**
- Keyboard and mouse input abstraction
- Alt+Tab prevention (optional)

✅ **Game UI Framework**
- Scalable menu system with highlighting
- Options screens (Graphics, Sound, Game Options, Score Presentation)
- Built-in enumerators (GameDifficulty, ScreenMode, DeepColor)

✅ **Configuration System**
- Singleton-based `GameConfig` with dynamic property loading
- External `config.ini` for enabling/disabling UI screens
- Sensible defaults for missing configuration

✅ **Architecture Patterns**
- Factory Pattern (CoreGameFactory)
- Strategy Pattern (State as behavior)
- Template Method (AbstractGame base class)
- Singleton (GameConfig)

## Project Structure

```
animator-engine/
├── pom.xml                                 # Maven configuration
├── README.md                               # This file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/game/animator/
│   │   │       ├── Launcher.java          # Entry point
│   │   │       ├── engine/
│   │   │       │   └── GameEngine.java    # Core game loop
│   │   │       ├── game/
│   │   │       │   ├── Game.java          # Main controller
│   │   │       │   ├── core/
│   │   │       │   │   ├── IGame.java     # Core interface
│   │   │       │   │   └── AbstractGame.java
│   │   │       │   ├── factory/
│   │   │       │   │   └── CoreGameFactory.java
│   │   │       │   ├── state/
│   │   │       │   │   ├── GameStateMachine.java
│   │   │       │   │   └── GameStates.java
│   │   │       │   ├── gameData/
│   │   │       │   │   ├── GameGraphics.java
│   │   │       │   │   ├── GameOptions.java
│   │   │       │   │   ├── GameScore.java
│   │   │       │   │   ├── GameSoundOptions.java
│   │   │       │   │   └── enumerators/
│   │   │       │   ├── gameUI/
│   │   │       │   │   ├── CoreGameLogic.java
│   │   │       │   │   ├── menu/
│   │   │       │   │   ├── options/
│   │   │       │   │   ├── advertise/
│   │   │       │   │   ├── intro/
│   │   │       │   │   ├── loading/
│   │   │       │   │   └── score/
│   │   │       ├── input/
│   │   │       ├── window/
│   │   │       │   └── Window.java
│   │   │       ├── exceptions/
│   │   │       └── util/
│   │   │           ├── GameConfig.java    # Configuration loader
│   │   │           ├── GlobalProperties.java
│   │   │           └── ImageUtil.java
│   │   └── resources/
│   │       └── config/
│   │           └── config.ini             # Game flow configuration
│   └── test/
│       └── java/                          # Unit tests (future)
└── bin/
    └── res/
        └── images/                        # Game assets

```

## Build Instructions

### Prerequisites
- Java 14 or higher
- Maven 3.6 or higher

### Build the JAR

```bash
cd animator-engine
mvn clean package
```

This will create:
- `target/animator-engine-1.0.0.jar` — Main JAR
- `target/animator-engine-1.0.0-sources.jar` — Source code
- `target/animator-engine-1.0.0-javadoc.jar` — Documentation

### Install Locally

To use this as a dependency in other projects:

```bash
mvn clean install
```

This installs the JAR to your local Maven repository (`~/.m2/repository/`).

## Using as a Dependency

In your game project's `pom.xml`:

```xml
<dependency>
    <groupId>br.com.game</groupId>
    <artifactId>animator-engine</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

### config.ini

Located at `src/main/resources/config/config.ini`, controls which screens are shown in the game flow:

```ini
# Enable/disable DEV_LOGO_SCREEN (shown at startup)
dev_logo_enabled=true

# Enable/disable INTRO_SCREEN (shown after logo)
intro_screen_enabled=true

# Enable/disable HIGH_SCORE_SCREEN (shown after intro)
high_score_screen_enabled=true
```

Accepted values: `true`, `yes`, `1`, `enabled` (case-insensitive)

## Creating Your Game

To create a game using this engine:

### 1. Extend `AbstractGame`

```java
import br.com.game.animator.game.core.AbstractGame;

public class MyGame extends AbstractGame {
    
    @Override
    public void handleInput(Game game, int keyCode, boolean isAltDown) {
        // Your game input logic
    }
    
    @Override
    public void updateGame() {
        // Your game update logic
    }
    
    @Override
    public void drawGame(Graphics2D g2d) {
        // Your game rendering logic
    }
}
```

### 2. Implement UI Screens

Implement the marker interface `CoreGameLogic` for your custom screens:

```java
public class MyGameScreen implements CoreGameLogic {
    
    @Override
    public void draw(Graphics2D g2d) { /* rendering */ }
    
    @Override
    public void handleInput(Game game, int keyCode, boolean isAltDown) { /* input */ }
    
    @Override
    public boolean finished() { return false; }
}
```

### 3. Extend the Factory

Modify `CoreGameFactory.createCoreGameLogic()` to instantiate your custom screens based on game state.

### 4. Launch Your Game

```java
public class YourLauncher {
    public static void main(String[] args) {
        new Launcher();
    }
}
```

## Game Flow Diagram

```
DEV_LOGO_SCREEN (config: dev_logo_enabled)
        ↓
SUB_INTRO_SCREEN (mandatory - stores game state)
        ↓
INTRO_SCREEN (config: intro_screen_enabled)
        ↓
HIGH_SCORE_SCREEN (config: high_score_screen_enabled)
        ↓
MAIN_MENU_SCREEN (menu.GameMainMenu)
        ↓
OPTIONS_SCREEN (game.gameUI.options.*)
        ↓
GAME_RUNNING (your AbstractGame implementation)
        ↓
EXIT_CONFIRMATION
        ↓
GAME_OVER
```

## Key Classes

### GameEngine
- Core game loop with nanosecond precision
- Tracks FPS/UPS statistics in O(1) time using circular buffers
- Targets multiple FPS settings via switch expressions

### GameStateMachine
- Centralized state transitions with Map-based configuration
- Respects optional screens from `GameConfig`
- Supports dynamic state groups

### GameConfig (Singleton)
- Loads configuration from `config.ini`
- Provides `isDevLogoEnabled()`, `isIntroScreenEnabled()`, `isHighScoreScreenEnabled()`

### Window
- Handles Java2D buffer strategy and fullscreen toggling
- Thread-safe dimension access via volatile fields + `dimensionLock`

### CoreGameFactory
- Single factory method creates appropriate screen based on game state
- Extensible for custom game screens

## Performance

- **FPS Options**: 30, 60, 90, 120, 240 FPS
- **Statistics**: Circular buffer (O(1) addition, accurate averages)
- **Delta Time**: Capped at 100ms to prevent large jumps
- **Thread Model**: Single-threaded game loop on dedicated engine thread, input/rendering on EDT

## Future Enhancements

- [ ] Sound engine (Java Clip/SourceDataLine wrapper)
- [ ] Asset loader (textures, fonts, sounds)
- [ ] Particle system
- [ ] Collision detection API
- [ ] Unit test suite
- [ ] Example demo game
- [ ] Spring Boot auto-configuration (optional integration)

## Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Ensure all tests pass
5. Submit a pull request

## License

[Your License - e.g., MIT, Apache 2.0]

## Author

João Paulo Faria

## Changelog

### 1.0.0 (Initial Release)
- Game loop with nanosecond precision timing
- State machine with configuration-driven transitions
- Window management with fullscreen support
- Input abstraction layer
- Comprehensive UI framework
- Configuration system with external config.ini
- Built-in game data structures (Graphics, Options, Score, Sound)
- Complete documentation and JavaDoc

---

**Ready to build your next game with Animator Engine!** 🎮✨
