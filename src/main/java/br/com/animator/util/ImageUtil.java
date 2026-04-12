package br.com.animator.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import br.com.animator.config.GlobalProperties;

/**
 * ImageUtil is a utility class for loading, copying, rotating, and manipulating
 * images in various ways, such as converting to grayscale or applying a red
 * filter. It also provides methods for scaling images according to the screen
 * size and aspect ratio.
 */
public class ImageUtil {

	private GraphicsEnvironment ge = null;
	private GraphicsConfiguration gc = null;
	private ColorConvertOp colorConvertOpGray = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	private BufferedImage imageGray = null;
	private static final int SCALE_X_16 = 1920;
	private static final int SCALE_Y_9 = 1080;
	private static final int SCALE_X_16a = 1728;
	private static final int SCALE_Y_10 = 1080;
	private static final int SCALE_X_4 = 1440;
	private static final int SCALE_Y_3 = 1080;
	private int currentCanvasWidth;
	private int currentCanvasHeight;
	private int currentAspectRatio;

	/**
	 * Default constructor. Initializes graphics environment and configuration.
	 */
	public ImageUtil() {
		this.ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.gc = this.ge.getDefaultScreenDevice().getDefaultConfiguration();
	}

	/**
	 * Constructor with canvas dimensions and aspect ratio.
	 * @param canvasWidth The canvas width
	 * @param canvasHeight The canvas height
	 * @param currentAspectRatio The current aspect ratio
	 */
	public ImageUtil(Integer canvasWidth, Integer canvasHeight, Integer currentAspectRatio) {
		this();
		this.currentCanvasWidth = canvasWidth;
		this.currentCanvasHeight = canvasHeight;
		this.currentAspectRatio = currentAspectRatio;
	}

	/**
	 * Loads an image specified in the parameter.
	 * 
	 * @param key The key used in LoadResources
	 * @return
	 */
	public BufferedImage loadImage(String key) {
		return ImageManager.get(key);
	}

	/**
	 * Retrieves an image from the manager using its key.
	 * 
	 * @param key The key used in LoadResources
	 * @return The BufferedImage
	 */
	public static BufferedImage getImage(String key) {
		return ImageManager.get(key);
	}

	/**
	 * Loads an image specified in the parameter.
	 * 
	 * @param key The key used in LoadResources
	 * @return
	 */
	public BufferedImage loadScaledImage(String key) {
		return loadScaledImage(ImageManager.get(key));
	}

	/**
	 * Loads and scales an image based on canvas dimensions and aspect ratio.
	 * 
	 * @param bi The BufferedImage to scale
	 * @return The scaled BufferedImage
	 */
	public BufferedImage loadScaledImage(BufferedImage bi) {

		if (bi == null) {
			return null;
		}

		int[] dims = calculateScaledDimensions(bi.getWidth(), bi.getHeight());
		int finalWidth = dims[0];
		int finalHeight = dims[1];

		BufferedImage copy = this.gc.createCompatibleImage(finalWidth, finalHeight, bi.getTransparency());

		Graphics2D g2d = (Graphics2D) copy.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.drawImage(bi, 0, 0, finalWidth, finalHeight, 0, 0, bi.getWidth(), bi.getHeight(), null);
		g2d.dispose();
		return (copy);
	}

	/**
	 * Calculates the target dimensions for scaling based on the current aspect ratio and canvas size.
	 */
	private int[] calculateScaledDimensions(int originalWidth, int originalHeight) {
		int finalWidth;
		int finalHeight;

		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			finalWidth = (int) ((float) originalWidth / SCALE_X_4 * this.currentCanvasWidth);
			finalHeight = (int) ((float) originalHeight / SCALE_Y_3 * this.currentCanvasHeight);
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_9) {
			finalWidth = (int) ((float) originalWidth / SCALE_X_16 * this.currentCanvasWidth);
			finalHeight = (int) ((float) originalHeight / SCALE_Y_9 * this.currentCanvasHeight);
		} else {
			finalWidth = (int) ((float) originalWidth / SCALE_X_16a * this.currentCanvasWidth);
			finalHeight = (int) ((float) originalHeight / SCALE_Y_10 * this.currentCanvasHeight);
		}

		return new int[] { finalWidth, finalHeight };
	}

	/**
	 * Copies an image specified in the parameter.
	 * 
	 * @param source The source BufferedImage to copy
	 * @return A copy of the source image
	 */
	public BufferedImage copyImage(BufferedImage source) {
		try {
			int transparency = source.getColorModel().getTransparency();
			BufferedImage copy = this.gc.createCompatibleImage(source.getWidth(), source.getHeight(), transparency);

			Graphics2D g2d = (Graphics2D) copy.getGraphics();
			g2d.drawImage(source, 0, 0, null);
			g2d.dispose();
			return (copy);

		} catch (Exception e) {
			System.out.println("Error copying image: " + e.getMessage());
		}
		return (null);
	}

	/**
	 * Copies a portion of an image specified in the parameter.
	 * 
	 * @param source The source BufferedImage to copy
	 * @param posicaoX Starting X position
	 * @param posicaoY Starting Y position
	 * @param largura Width of the region
	 * @param altura Height of the region
	 * @return A copy of the specified region
	 */
	public BufferedImage copyImage(BufferedImage source, int posicaoX, int posicaoY, int largura, int altura) {
		try {
			int transparency = source.getColorModel().getTransparency();
			BufferedImage copy = this.gc.createCompatibleImage(source.getWidth(), source.getHeight(), transparency);

			Graphics2D g2d = (Graphics2D) copy.getGraphics();
			g2d.drawImage(source,
					0,
					0,
					largura,
					altura,
					posicaoX,
					posicaoY,
					posicaoX + largura,
					posicaoY + altura,
					null);
			g2d.dispose();
			return (copy);
		} catch (Exception e) {
			System.out.println("Error copying image: " + e.getMessage());
		}
		return (null);
	}

	/**
	 * Rotates an image specified in the parameter.
	 * 
	 * @param src
	 * @param angle
	 * @return
	 */
	public BufferedImage getRotatedImage(BufferedImage src, int angle) {
		if (src == null) {
			return null;
		}

		int transparency = src.getColorModel().getTransparency();
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), transparency);
		Graphics2D g2d = dest.createGraphics();

		AffineTransform origAT = g2d.getTransform();

		AffineTransform rot = new AffineTransform();
		rot.rotate(Math.toRadians(angle), src.getWidth() / 2, src.getHeight() / 2);
		g2d.transform(rot);
		g2d.drawImage(src, 0, 0, null);

		g2d.setTransform(origAT);
		g2d.dispose();
		return (dest);
	}

	/**
	 * Gets a grayscale image from the source image specified in the parameter.
	 * 
	 * @param src
	 * @return
	 */
	public BufferedImage getGrayscaleImage(BufferedImage src) {
		if (src == null) {
			return null;
		}

		int transparency = src.getColorModel().getTransparency();
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), transparency);
		Graphics2D g2d = dest.createGraphics();

		dest = this.colorConvertOpGray.filter(src, dest);
		g2d.dispose();

		return (dest);
	}

	/**
	 * Revert a grayscale image to color, using the source image specified in the
	 * parameter.
	 * 
	 * @param src
	 * @return
	 */
	public BufferedImage revertImageInGrayscale(BufferedImage src) {
		if (src != null) {
			BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D temp = (Graphics2D) image.getGraphics();
			temp.drawImage(src, 0, 0, null);
			temp.dispose();
			return (image);
		}
		return (null);
	}

	/**
	 * Revert a grayscale image to color, using the source image specified in the
	 * parameter.
	 * 
	 * @param src
	 * @return
	 */
	public BufferedImage revertImageInColor(BufferedImage src) {
		if (src != null) {
			BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D temp = (Graphics2D) image.getGraphics();
			temp.drawImage(src, 0, 0, null);
			temp.dispose();
			return (image);
		}
		return (null);
	}

	/**
	 * Draws a grayscale image on the Graphics2D object, using the source image
	 * specified in the parameter.
	 * 
	 * @param graphics2D The Graphics2D context to draw on
	 * @param src The source BufferedImage
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param width The width to draw
	 * @param height The height to draw
	 */
	public void drawImageInGrayscale(Graphics2D graphics2D, BufferedImage src, int x, int y, int width, int height) {
		if (src != null) {
			if (this.imageGray == null || this.imageGray.getWidth() != (width + x)) {
				this.imageGray = new BufferedImage(width + x, height + y, BufferedImage.TYPE_BYTE_GRAY);
			}
			Graphics2D temp = (Graphics2D) this.imageGray.getGraphics();
			temp.setClip(0, 0, width + x, height + y);
			temp.drawImage(src, 0, 0, null);
			temp.dispose();
			graphics2D.drawImage(this.imageGray, 0, 0, width + x, height + y, null);
		}
	}

	/**
	 * Draws an image with red color filter on the Graphics2D object.
	 * @param graphics2D The Graphics2D context to draw on
	 * @param src The source BufferedImage to draw
	 */
	public void drawImageInRed(Graphics2D graphics2D, BufferedImage src) {
		ImageFilter filter = new GetRedFilter();
		FilteredImageSource filteredSrc = new FilteredImageSource(src.getSource(), filter);
		Image image = Toolkit.getDefaultToolkit().createImage(filteredSrc);
		graphics2D.drawImage(image, 0, 0, null);
	}

	/**
	 * Gets an image with red color filter applied.
	 * @param src The source BufferedImage
	 * @return The image with red filter applied
	 */
	public BufferedImage getImageInRed(BufferedImage src) {
		BufferedImage buffer = new BufferedImage(src.getWidth(), src.getHeight(),
				src.getColorModel().getTransparency());
		buffer.getGraphics().drawImage(
				Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), new GetRedFilter())),
				0, 0, null);
		return (buffer);
	}

	/**
	 * @see https://docs.oracle.com/javase/7/docs/api/java/awt/image/RGBImageFilter.html
	 * @author https://stackoverflow.com/users/1095801/sergio-lopes
	 */
	private class GetRedFilter extends RGBImageFilter {
		public GetRedFilter() {
			canFilterIndexColorModel = true;
		}

		public int filterRGB(int x, int y, int rgb) {
			if (x == -1) {
			}
			return rgb & 0xffff0000;
		}
	}

	/**
	 * @param g2d
	 * @param image
	 * @param imageFinalPosX
	 * @param imageFinalPosY
	 * @param zoomScale
	 * @return
	 */
	public Integer drawZoomInImage(Graphics2D g2d,
			BufferedImage image,
			int imageFinalPosX,
			int imageFinalPosY,
			Integer zoomScale) {

		int px = imageFinalPosX;
		int py = imageFinalPosY;
		int pw = image.getWidth();
		int ph = image.getHeight();

		if (zoomScale > 1) {
			zoomScale--;
		}

		int zoomInPw = (int) ((float) pw / (float) zoomScale);
		int zoomInPh = (int) ((float) ph / (float) zoomScale);
		int zoomInPx = px + (int) (((float) pw / 2) - ((float) zoomInPw / 2));
		int zoomInPy = py + (int) (((float) ph / 2) - ((float) zoomInPh / 2));

		g2d.drawImage(image,
				zoomInPx,
				zoomInPy,
				zoomInPw,
				zoomInPh,
				null);

		return (zoomScale);
	}

	/**
	 * Gets the scaled width of the image according to the screen size.
	 * 
	 * @param value
	 * @return
	 */
	public int getScaledWidth(int value) {
		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			return (int) ((float) value / SCALE_X_4 * this.currentCanvasWidth);
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_9) {
			return (int) ((float) value / SCALE_X_16 * this.currentCanvasWidth);
		} else {
			return (int) ((float) value / SCALE_X_16a * this.currentCanvasWidth);
		}
	}

	/**
	 * Gets the scaled width of the image according to the screen size, using the
	 * informed value for 4x3 aspect ratio.
	 * 
	 * @param value
	 * @return
	 */
	public int getScaledWidthForPosition(int value) {
		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			value = value - 240;
			return (int) ((float) value / SCALE_X_4 * this.currentCanvasWidth);
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_9) {
			return (int) ((float) value / SCALE_X_16 * this.currentCanvasWidth);
		} else {
			value = value - 96;
			return (int) ((float) value / SCALE_X_16a * this.currentCanvasWidth);
		}
	}

	/**
	 * Gets the scaled width of the image according to the screen size, using the
	 * informed value for 4x3 aspect ratio.
	 * 
	 * @param value
	 * @return
	 */
	public int getScaledWidthScaledWithInformed4x3(int value, int diffFor4X3) {
		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			value = value - diffFor4X3;
			return (int) ((float) value / SCALE_X_4 * this.currentCanvasWidth);
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_9) {
			return (int) ((float) value / SCALE_X_16 * this.currentCanvasWidth);
		} else {
			value = value - 96;
			return (int) ((float) value / SCALE_X_16a * this.currentCanvasWidth);
		}
	}

	/**
	 * Gets the scaled height of the image according to the screen size.
	 * 
	 * @param value
	 * @return
	 */
	public int getScaledHeight(int value) {
		if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_4_3) {
			return (int) ((float) value / SCALE_Y_3 * this.currentCanvasHeight);
		} else if (this.currentAspectRatio == GlobalProperties.ASPECT_RATIO_16_9) {
			return (int) ((float) value / SCALE_Y_9 * this.currentCanvasHeight);
		} else {
			return (int) ((float) value / SCALE_Y_10 * this.currentCanvasHeight);
		}
	}

	/**
	 * Updates the canvas properties.
	 * 
	 * @param pwidth
	 * @param pheight
	 */
	public void updateCanvasProperties(Integer pwidth, Integer pheight, Integer aspectRatio) {
		this.currentCanvasWidth = pwidth;
		this.currentCanvasHeight = pheight;
		this.currentAspectRatio = aspectRatio;
	}
}