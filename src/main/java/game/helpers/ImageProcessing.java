package game.helpers;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageProcessing {

    public static Image[][] cutImage(Image image, int rows, int cols) {
        return imageProcessing(image, rows, cols);
    }

    public static Image[][] cutImage(String filePath, int rows, int cols) {
        return imageProcessing(loadImage(filePath), rows, cols);
    }

    private static Image[][] imageProcessing(Image image, int rows, int cols) {
        Image[][] images = new Image[rows][cols];
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                images[i][j] = new WritableImage(reader,
                        j * width / cols, i * height / rows, width / cols, height / rows);
            }
        }
        return images;
    }

    public static Image loadImage(String filePath) {
        Image img = null;
        try {
            img = new Image(ImageProcessing.class.getClass().getResourceAsStream(filePath));
        } catch (Exception ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, filePath);
        }
        return img;
    }
}
