package Engine;

import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// contains a bunch of helpful methods for loading image files into the game
public class ImageLoader {

    // loads an image and sets its transparent color to the one defined in the Config class
    public static BufferedImage load(String imageFileName) {
        return load(imageFileName, Config.TRANSPARENT_COLOR);
    }

    // loads an image and allows the transparent color to be specified
    public static BufferedImage load(String imageFileName, Color transparentColor) {
        Path filePath = Paths.get(Config.RESOURCES_PATH, imageFileName);  // Using Path to build the file path
        if (!filePath.toFile().exists()) {
            System.err.println("Error: The image file does not exist at " + filePath);
            return null;  // Return null to handle it gracefully, or throw an exception if needed
        }
        
        try {
            BufferedImage initialImage = ImageIO.read(filePath.toFile());
            return ImageUtils.transformColorToTransparency(initialImage, transparentColor);
        } catch (IOException e) {
            System.err.println("Unable to load image from file: " + filePath);
            e.printStackTrace();
            return null;  // Return null on error instead of throwing an exception
        }
    }

    // loads a piece of an image from an image file and sets its transparent color to the one defined in the Config class
    public static BufferedImage loadSubImage(String imageFileName, int x, int y, int width, int height) {
        return loadSubImage(imageFileName, Config.TRANSPARENT_COLOR, x, y, width, height);
    }

    // loads a piece of an image from an image file and allows the transparent color to be specified
    public static BufferedImage loadSubImage(String imageFileName, Color transparentColor, int x, int y, int width, int height) {
        Path filePath = Paths.get(Config.RESOURCES_PATH, imageFileName);
        if (!filePath.toFile().exists()) {
            System.err.println("Error: The image file does not exist at " + filePath);
            return null;  // Return null to handle it gracefully, or throw an exception if needed
        }

        try {
            BufferedImage initialImage = ImageIO.read(filePath.toFile());
            BufferedImage transparentImage = ImageUtils.transformColorToTransparency(initialImage, transparentColor);
            return transparentImage.getSubimage(x, y, width, height);
        } catch (IOException e) {
            System.err.println("Unable to load subimage from file: " + filePath);
            e.printStackTrace();
            return null;  // Return null on error instead of throwing an exception
        }
    }
}
