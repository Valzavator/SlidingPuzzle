package game.board;

import game.button.ButtonPrototype;
import game.button.ButtonCache;
import game.button.TextButton;
import game.enumeration.Difficulty;

public class TextBoard extends GameBoard {

    TextBoard(Difficulty difficulty) {
        super(difficulty);
    }

    @Override
    ButtonPrototype createBtn(int position) {
        TextButton textBtn = (TextButton) ButtonCache.get("TextButton");
        textBtn.setString(Integer.toString(position + 1));
        textBtn.setGameIndex(position);
        return textBtn;
    }
}
