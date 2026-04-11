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
     * Returns an image from the cache or loads it from resources if not present.
     * The returned image is optimized for the current graphics configuration.
     * 
     * @param path The resource path to the image file.
     * @return A compatible BufferedImage or null if the resource could not be loaded.
     */
    public static BufferedImage getImage(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }

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