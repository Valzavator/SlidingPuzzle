package game.model.gameModel;

import game.button.ButtonPrototype;

public abstract class GameState {
    GameModel gameModel;

    GameState(GameModel context) {
        this.gameModel = context;
    }

    public abstract boolean start();
    public abstract boolean switchButton(ButtonPrototype pressedButton);
    public abstract boolean undoSwitchButton();
    public abstract boolean redoSwitchButton();
    public abstract boolean pause();
    public abstract void finish();
}
