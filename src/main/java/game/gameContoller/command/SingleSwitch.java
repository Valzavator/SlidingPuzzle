package game.gameContoller.command;

import game.board.GameBoard;
import game.button.ButtonPrototype;
import game.helpers.Player;

public class SingleSwitch extends Command {

    public SingleSwitch(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    public int switchButton(ButtonPrototype pressedButton) {
        int numOfColumns = gameBoard.getColumns();

        int indexOfPressedBtn = gameBoard.getIndexOf(pressedButton);
        int indexOfLockedBtn = gameBoard.getIndexOf(gameBoard.getButtonLocked());
        int indexDifference = indexOfPressedBtn - indexOfLockedBtn;

        boolean pressedBtnInFirstCol = gameBoard.getIndexOfCol(pressedButton) == 0;
        boolean pressedBtnInLastCol = gameBoard.getIndexOfCol(pressedButton) == numOfColumns - 1;
        boolean lockedBtnInFirstCol = gameBoard.getIndexOfCol(gameBoard.getButtonLocked()) == 0;
        boolean lockedBtnInLastCol = gameBoard.getIndexOfCol(gameBoard.getButtonLocked()) == numOfColumns - 1;

        if (Math.abs(indexDifference) != 1 &&
                Math.abs(indexDifference) != numOfColumns ||
                Math.abs(indexDifference) == 1 &&
                        (pressedBtnInFirstCol && lockedBtnInLastCol || pressedBtnInLastCol && lockedBtnInFirstCol)) {
            Player.play("/sounds/clickMouseBang.wav");
            System.out.println("Push blocked = " + indexOfPressedBtn + " cell");
            return 0;
        }

        if ((Math.abs(indexDifference) == 1 ||
                Math.abs(indexDifference) == numOfColumns) &&
                (Math.abs(indexDifference) != 1 ||
                        (!pressedBtnInLastCol || !lockedBtnInFirstCol) &&
                                (!pressedBtnInFirstCol || !lockedBtnInLastCol))) {
            Player.play("/sounds/clickMouse.wav");
            gameBoard.swapButtons(indexOfPressedBtn, indexOfLockedBtn);
            System.out.println("Change place = " + indexOfPressedBtn + " cell to " + indexOfLockedBtn + " cell");
        }

        activeButton = pressedButton;
        return 1;
    }
}
