package game.gameContoller.command;

import game.board.GameBoard;
import game.button.ButtonPrototype;
import game.helpers.Player;

public class MultiSwitch extends Command {

    public MultiSwitch(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    public int switchButton(ButtonPrototype pressedButton) {
        int clickCounter = 0;

        int indexOfPressedBtn = gameBoard.getIndexOf(pressedButton);
        int indexOfLockedBtn = gameBoard.getIndexOf(gameBoard.getButtonLocked());

        int colOfPressedBtn = gameBoard.getIndexOfCol(pressedButton);
        int rowOfPressedBtn = gameBoard.getIndexOfRow(pressedButton);
        int colOfLockedBtn = gameBoard.getIndexOfCol(gameBoard.getButtonLocked());
        int rowOfLockedBtn = gameBoard.getIndexOfRow(gameBoard.getButtonLocked());
        if (colOfPressedBtn != colOfLockedBtn && rowOfPressedBtn != rowOfLockedBtn) {
            Player.play("/sounds/clickMouseBang.wav");
            System.out.println("Push blocked = " + indexOfPressedBtn + " cell");
        } else {
            if (colOfPressedBtn == colOfLockedBtn) {
                if (rowOfLockedBtn < rowOfPressedBtn) {
                    for (int i = rowOfLockedBtn + 1; i <= rowOfPressedBtn; i++) {
                        clickCounter++;
                        int btnIndexForSwitch = indexOfLockedBtn + gameBoard.getColumns() * (i - rowOfLockedBtn);
                        swapButtons(btnIndexForSwitch, gameBoard.getIndexOf(gameBoard.getButtonLocked()));
                    }
                } else {
                    for (int i = rowOfLockedBtn - 1; i >= rowOfPressedBtn; i--) {
                        clickCounter++;
                        int btnIndexForSwitch = indexOfLockedBtn - gameBoard.getColumns() * (rowOfLockedBtn - i);
                        swapButtons(btnIndexForSwitch, gameBoard.getIndexOf(gameBoard.getButtonLocked()));
                    }
                }
            } else {
                if (colOfLockedBtn < colOfPressedBtn) {
                    for (int i = colOfLockedBtn + 1; i <= colOfPressedBtn; i++) {
                        clickCounter++;
                        int btnIndexForSwitch = indexOfLockedBtn + (i - colOfLockedBtn);
                        swapButtons(btnIndexForSwitch, gameBoard.getIndexOf(gameBoard.getButtonLocked()));
                    }
                } else {
                    for (int i = colOfLockedBtn - 1; i >= colOfPressedBtn; i--) {
                        clickCounter++;
                        int btnIndexForSwitch = indexOfLockedBtn - (colOfLockedBtn - i);
                        swapButtons(btnIndexForSwitch, gameBoard.getIndexOf(gameBoard.getButtonLocked()));
                    }
                }
            }
            Player.play("/sounds/clickMouse.wav");
            activeButton = gameBoard.getButton(indexOfLockedBtn);
        }
        return clickCounter;
    }

    private void swapButtons(int firstBtnIndex, int secondBtnIndex ) {
        System.out.println("Change place = " + firstBtnIndex + " cell to " + secondBtnIndex + " cell");
        gameBoard.swapButtons(firstBtnIndex, secondBtnIndex);
    }

}
