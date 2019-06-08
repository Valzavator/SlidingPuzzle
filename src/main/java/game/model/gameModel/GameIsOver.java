package game.model.gameModel;

import game.button.ButtonPrototype;

public class GameIsOver extends GameState {
    GameIsOver(GameModel context) {
        super(context);
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean switchButton(ButtonPrototype pressedButton) {
        return false;
    }

    @Override
    public boolean undoSwitchButton() {
        return false;
    }

    @Override
    public boolean redoSwitchButton() {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public void finish() {
        gameModel.getTimer().stop();
        gameModel.setRedoCommandCounter(0);
        gameModel.setUndoCommandCounter(0);
    }
}
