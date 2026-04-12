#  Changelog

All notable changes to the **Animator Engine** will be documented in this file.

## [1.3.0] - 2026-04-12
### Added
- **ImageManager**: Centralized hardware-accelerated image caching to prevent redundant disk I/O and optimize rendering.
- **Bootstrap Loading**: Implemented a two-stage loading process (`loadBootstrap` and `loadAllResources`) to allow immediate UI feedback.
- **Background Resource Loading**: Integrated a dedicated `ResourceLoaderThread` in `AbstractGame` to load heavy assets without freezing the main game loop.
- **Unified Resource API**: Refactored `ImageUtil` and `OggAudio` to act as clean interfaces for resource retrieval using unique keys, hiding internal Manager complexity.
### Improved
- **Architecture**: Decoupled asset registration (configuration) from asset usage (game logic).

## [1.2.0] - 2026-04-11
### Added
- **Dynamic FPS Control**: Implementation of runtime FPS switching within the `GameEngine`.
- **Fast Forward Feature**: Added a built-in shortcut (**TAB**) to switch between target FPS (e.g., 60) and Unlimited mode.
- **Unified Game Loop**: Re-engineered the core loop to use a single branch-predicted path, optimizing performance when switching between fixed and variable timing.
### Fixed
- **Keyboard Input Focus**: Disabled default AWT focus traversal keys to ensure the **TAB** key is correctly captured by the game engine.

## [1.1.0] - 2026-04-06

### Added
- **LWJGL Integration**: Added support for LWJGL (OpenAL and STB) to handle high-performance audio.
- **OGG Support**: Implementation of `OggAudio` class to support `.ogg` files for Music and SFX.
- **Advanced Audio Controls**: Added methods for granular volume control (up/down by 10%), looping, and muting.
- **Native Libraries**: Configured `pom.xml` to automatically include LWJGL natives for Windows.

### Fixed
- Fixed `UnsupportedAudioFileException` when trying to load OGG files using the standard Java Sound API.
- Improved audio latency by switching from software mixing to hardware-accelerated OpenAL.