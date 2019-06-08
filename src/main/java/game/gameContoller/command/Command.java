package game.gameContoller.command;

import game.board.GameBoard;
import game.button.ButtonPrototype;
import game.model.gameModel.GameState;

public abstract class Command {
    // executor
    GameBoard gameBoard;
    ButtonPrototype activeButton;

    public Command() {}
    public Command(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setGameBoard(GameBoard gabeBoard) {
        this.gameBoard = gabeBoard;
    }

    public abstract int switchButton(ButtonPrototype button);

    public int undo() {
        return activeButton != null ? switchButton(activeButton) : 0;
    }
}
