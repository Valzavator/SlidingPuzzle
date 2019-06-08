package game.helpers.stylesManager;

import game.button.ButtonPrototype;
import game.button.ImageButton;

public class StylesManager implements IHighlight {

    public void setNormalStyle(ButtonPrototype button) {
        checkButton(button);
        final String STYLE_NORMAL = "-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;";
        button.setStyle(STYLE_NORMAL);
    }

    public void resetStyle(ButtonPrototype button) {
        checkButton(button);
        button.setStyle(null);
    }

    @Override
    public void highlightButton(ButtonPrototype button) {
        checkButton(button);
        final String STYLE_HIGHLIGHTED_IMAGE = "-fx-scale-x: 0.90;" +
                "-fx-scale-y: 0.95;" +
                "-fx-background-color: lightgreen;";
        final String STYLE_HIGHLIGHTED_TEXT = "-fx-scale-x: 0.98;" +
                "-fx-scale-y: 0.98;" +
                "-fx-background-color: lightgreen;";
        if (button instanceof ImageButton)
            button.setStyle(STYLE_HIGHLIGHTED_IMAGE);
        else
            button.setStyle(STYLE_HIGHLIGHTED_TEXT);
    }

    private void checkButton(ButtonPrototype button) {
        if (button == null)
            throw new IllegalArgumentException("Invalid button in StyleManager!!!");
    }
}
