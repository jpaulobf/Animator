#  Changelog

All notable changes to the **Animator Engine** will be documented in this file.

## [1.1.0] - 2026-04-06

### Added
- **LWJGL Integration**: Added support for LWJGL (OpenAL and STB) to handle high-performance audio.
- **OGG Support**: Implementation of `OggAudio` class to support `.ogg` files for Music and SFX.
- **Advanced Audio Controls**: Added methods for granular volume control (up/down by 10%), looping, and muting.
- **Native Libraries**: Configured `pom.xml` to automatically include LWJGL natives for Windows.

### Fixed
- Fixed `UnsupportedAudioFileException` when trying to load OGG files using the standard Java Sound API.
- Improved audio latency by switching from software mixing to hardware-accelerated OpenAL.