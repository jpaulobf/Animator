package br.com.animator.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * ImageManager handles the caching of original images to avoid redundant disk I/O.
 * It also ensures images are stored in a format compatible with the graphics hardware.
 */
public class ImageManager {

    private static final Map<String, BufferedImage> imageCache = new HashMap<>();
    private static final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();

    /**
     * Loads an image from resources and stores it in the cache with the given key.
     * The returned image is optimized for the current graphics configuration.
     * 
     * @param key The key to identify the image (e.g., "ClassName.imageName").
     * @param path The resource path to the image file.
     */
    public static void load(String key, String path) {
        if (path == null || path.isEmpty() || key == null) {
            return;
        }

        try {
            var resource = ImageManager.class.getResource(path);
            if (resource == null) {
                System.err.println("ImageManager: Resource not found at " + path);
                return;
            }

            BufferedImage bi = ImageIO.read(resource);
            if (bi != null) {
                BufferedImage compatible = gc.createCompatibleImage(
                        bi.getWidth(), 
                        bi.getHeight(), 
                        bi.getColorModel().getTransparency());

                Graphics2D g2d = compatible.createGraphics();
                g2d.drawImage(bi, 0, 0, null);
                g2d.dispose();

                imageCache.put(key, compatible);
            }
        } catch (Exception e) {
            System.err.println("ImageManager: Error loading image " + key + " from " + path + ": " + e.getMessage());
        }
    }

    /**
     * Returns a pre-loaded image from the cache.
     * 
     * @param key The key used during loading.
     * @return The BufferedImage or null if not found.
     */
    public static BufferedImage get(String key) {
        return imageCache.get(key);
    }

    /**
     * Deprecated: Use load() and get() instead.
     */
    @Deprecated
    public static BufferedImage getImage(String path) {
        return imageCache.computeIfAbsent(path, p -> {
            try {
                BufferedImage bi = ImageIO.read(ImageManager.class.getResource(p));
                if (bi == null) return null;

                // Create a compatible image for hardware acceleration
                BufferedImage compatible = gc.createCompatibleImage(
                        bi.getWidth(), 
                        bi.getHeight(), 
                        bi.getColorModel().getTransparency());

                Graphics2D g2d = compatible.createGraphics();
                g2d.drawImage(bi, 0, 0, null);
                g2d.dispose();

                return compatible;
            } catch (Exception e) {
                System.out.println("ImageManager: Error loading image at " + p + ": " + e.getMessage());
                return null;
            }
        });
    }

    public static void clearCache() {
        imageCache.clear();
    }
}