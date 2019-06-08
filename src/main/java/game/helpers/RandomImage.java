package game.helpers;

import javafx.scene.image.Image;

import java.util.Random;

public class RandomImage {
    private static Image imageLocked;
    private static String previousImage;

    public static Image getRandomImage() {
        Random rnd = new Random();
        String newImage;
        do {
            newImage = String.format("/images/%d.png", rnd.nextInt(7) + 1);
        } while (newImage.equals(previousImage));
        previousImage = newImage;
        return ImageProcessing.loadImage(newImage);
    }

    public static Image getImageLocked() {
        if (imageLocked == null)
            imageLocked = ImageProcessing.loadImage("/images/locked/locked1.png");
        return imageLocked;
    }

}
