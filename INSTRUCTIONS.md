# Animator Engine - Detailed Instructions

This document provides technical details about the features, architecture, and configuration of the Animator Engine.

## Key Features

### Core Game Loop
- Professional game engine with configurable frame rates (default 60 FPS).
- **Fast Forward Mode**: Toggle unlimited FPS instantly by holding the TAB key.
- Fixed timestep support for physics consistency.
- Input tracking for keyboard, mouse, and joystick.

### Graphics & Rendering
- Hardware-accelerated Swing rendering via BufferStrategy.
- **ImageManager**: Centralized hardware-accelerated image caching.
- Support for Fullscreen, Windowed modes, and multiple color depths (16/24/32 bits).
- **Bootstrap Loading**: Two-stage non-blocking resource loading for immediate UI feedback.

### Audio System
- **OGG Support**: High-performance audio via LWJGL (OpenAL and STB).
- Granular volume control, looping, and muting.
- Decoupled API hiding internal complexity.

### UI & State Management
- Built-in Finite State Machine (FSM) for screen transitions (Intro -> Menu -> Gameplay).
- Standardized UI components for Menus, Options, and Scoring.

## Architecture

### Design Patterns

| Pattern | Application |
|---------|-------------|
| Factory | `CoreGameFactory` for decoupling component creation. |
| State Machine | `GameStateMachine` for managing game flow. |
| Manager/Cache | `ImageManager` for asset optimization. |
| Singleton | `GameConfig` for centralized settings. |
| Template Method | `AbstractGame` defining the loop structure. |

### Layered Architecture

1. **Game Implementation**: Your custom logic extending `AbstractGame`.
2. **Framework Layer**: `AbstractGame` and `IGame` interfaces.
3. **Resource Layer**: `ImageManager`, `AudioManager`, and `LoadResources`.
4. **Engine Layer**: `GameEngine` core loop and timing.
5. **Hardware Interface**: Window management, Input processing (AWT/LWJGL).

## Core Components

### GameEngine
Manages the lifecycle of the game loop, timing, UPS (Updates Per Second), and FPS (Frames Per Second). It handles the switch etween fixed and variable timing.

### AbstractGame
The base class you must extend. It provides hooks for:
- `handleInput`: Processing user actions.
- `updateGame`: Logic updates.
- `drawGame`: Drawing to the screen.

### GameConfig
A singleton that loads properties from `config.ini` and holds runtime settings for graphics and audio.

## Configuration (config.ini)

The engine looks for a `config.ini` in the resources folder to toggle specific screens:

```ini
# Game flow configuration
dev_logo_enabled=true
intro_screen_enabled=true
high_score_screen_enabled=true
```

## Performance Notes

### Optimization Tips
- Use the `ImageManager` to load assets; it ensures images are compatible with the current `GraphicsConfiguration`.
- Avoid object allocation inside the `updateGame` or `drawGame` methods to prevent GC spikes.
- Enable Fullscreen mode for the most stable frame rates.

### Hardware Acceleration
The engine uses `sun.java2d.opengl=true` (when applicable) and hardware-accelerated VolatileImages internally through the wing framework.

## Contributing

1. Consistently use English for code and comments.
2. Follow the Factory pattern when adding new UI logic.
3. Document public APIs using Javadoc.
4. Ensure `mvn clean package` passes before submitting changes.

## Project Structure

```
animator-engine/
├── src/main/java/br/com/animator/
│   ├── engine/             # Core loop (GameEngine)
│   ├── game/               # Main logic and resources
│   │   ├── core/           # Base classes (AbstractGame)
│   │   ├── factory/        # Component Factory
│   │   ├── ui/             # Built-in screens (Menu, Options)
│   │   └── state/          # FSM (GameStateMachine)
│   ├── window/             # JFrame/Display management
│   └── input/              # Mouse/Keyboard/Joystick
├── src/main/resources/
│   ├── config/             # config.ini
│   └── images/             # Engine assets
└── pom.xml                 # Maven configuration
```

---

## Game Development Workflow

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

## Usage Examples

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

## Creating New Games

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