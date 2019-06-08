package game.button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends ButtonPrototype {

    public ImageButton() {}

    public ImageButton(Image image, int countPosition) {
        super(countPosition);
        this.setImage(image);
    }

    public void setImage(Image image) {
        this.setGraphic(new ImageView(image));
    }

    @Override
    protected void setProperties(ButtonPrototype button) {
        super.setProperties(button);
        this.setGraphic(button.getGraphic());
    }
}
