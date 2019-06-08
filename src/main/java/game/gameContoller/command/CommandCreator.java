package game.gameContoller.command;


import game.board.GameBoard;

public class CommandCreator {
    public Command createCommand(boolean multiSwitch, GameBoard gameBoard) {
        if (multiSwitch)
            return new MultiSwitch(gameBoard);
        else
            return new SingleSwitch(gameBoard);
    }
}
