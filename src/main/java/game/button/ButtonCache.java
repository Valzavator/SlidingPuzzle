package game.button;

import game.helpers.RandomImage;
import game.helpers.stylesManager.StylesManager;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;


public class ButtonCache {
    private static StylesManager stylesManager = new StylesManager();
    private static Map<String, ButtonPrototype> cache = new HashMap<>();

    static {
        loadCache();
    }

    public static ButtonPrototype get(String key) {
        return cache.get(key).clone();
    }

    private static void loadCache() {

        ImageButton imageButton = new ImageButton();
        stylesManager.setNormalStyle(imageButton);
        cache.put("ImageButton", imageButton);

        TextButton textButton = new TextButton();
        textButton.setFont(Font.font("Verdana", 20));
        cache.put("TextButton", textButton);

        ImageButton buttonLocked = new ImageButton();
        buttonLocked.changeButtonFunctionality();
        stylesManager.setNormalStyle(buttonLocked);
        buttonLocked.setImage(RandomImage.getImageLocked());
        cache.put("ButtonLocked", buttonLocked);
    }
}
