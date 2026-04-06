package br.com.animator.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton configuration manager for loading game settings from config.ini.
 * Provides access to game flow configuration such as which screens are enabled/disabled.
 */
public class GameConfig {

    private static final String CONFIG_FILE = "src/main/resources/config/config.ini";
    private static final GameConfig INSTANCE = new GameConfig();

    private final Map<String, String> properties = new HashMap<>();

    /**
     * Private constructor to enforce singleton pattern.
     * Automatically loads configuration from config.ini on instantiation.
     */
    private GameConfig() {
        loadConfig();
    }

    /**
     * Loads configuration from config.ini file.
     * Sets sensible defaults if file is not found or properties are missing.
     * Properties are case-insensitive.
     */
    private void loadConfig() {
        Path configPath = Paths.get(CONFIG_FILE);

        // Set defaults
        properties.put("dev_logo_enabled", "true");
        properties.put("intro_screen_enabled", "true");
        properties.put("high_score_screen_enabled", "true");
        properties.put("render_mode", "native");

        // Try to load from file
        try {
            if (Files.exists(configPath)) {
                Files.lines(configPath)
                    .filter(line -> !line.trim().startsWith("#") && !line.trim().startsWith("[") && !line.trim().isEmpty())
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim().toLowerCase();
                            String value = parts[1].trim().toLowerCase();
                            properties.put(key, value);
                        }
                    });
                System.out.println("GameConfig: Loaded from " + configPath.toAbsolutePath());
            } else {
                System.out.println("GameConfig: config.ini not found at " + configPath.toAbsolutePath() + ", using defaults");
            }
        } catch (IOException e) {
            System.err.println("GameConfig: Failed to load config.ini: " + e.getMessage());
            System.err.println("GameConfig: Using default configuration");
        }
    }

    /**
     * Gets the singleton instance of GameConfig.
     * @return The GameConfig instance
     */
    public static GameConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Checks if the DEV_LOGO_SCREEN is enabled in the configuration.
     * @return true if dev logo screen should be displayed
     */
    public boolean isDevLogoEnabled() {
        return parseBoolean(properties.get("dev_logo_enabled"));
    }

    /**
     * Checks if the INTRO_SCREEN is enabled in the configuration.
     * @return true if intro screen should be displayed
     */
    public boolean isIntroScreenEnabled() {
        return parseBoolean(properties.get("intro_screen_enabled"));
    }

    /**
     * Checks if the HIGH_SCORE_SCREEN is enabled in the configuration.
     * @return true if high score screen should be displayed
     */
    public boolean isHighScoreScreenEnabled() {
        return parseBoolean(properties.get("high_score_screen_enabled"));
    }

    /**
     * Helper method to safely parse boolean values.
     * Considers "true", "yes", "1", "enabled" as true (case-insensitive).
     * All other values are considered false.
     * @param value The string value to parse
     * @return The parsed boolean value
     */
    private static boolean parseBoolean(String value) {
        if (value == null) {
            return false;
        }
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || 
               value.equalsIgnoreCase("1") || value.equalsIgnoreCase("enabled");
    }

    /**
     * Gets the render mode from configuration (native or opengl).
     * Defaults to "native" if not specified or invalid.
     * @return The render mode: "native" or "opengl"
     */
    public String getRenderMode() {
        String mode = properties.get("render_mode");
        if ("opengl".equalsIgnoreCase(mode)) {
            return "opengl";
        }
        return "native";
    }

    /**
     * Debug method to print all loaded properties.
     */
    public void printConfig() {
        System.out.println("=== GameConfig ===");
        properties.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println("==================");
    }
}
