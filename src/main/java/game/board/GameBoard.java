package game.board;

import game.button.ButtonPrototype;
import game.button.ButtonCache;
import game.enumeration.Difficulty;

import java.util.ArrayList;
import java.util.Collections;

public abstract class GameBoard {
    ArrayList<ButtonPrototype> buttons;
    ButtonPrototype buttonLocked;
    Difficulty difficulty;

    GameBoard(Difficulty difficulty) {
        this.buttons = new ArrayList<>();
        this.difficulty = difficulty;
    }

    public int getIndexOfCol(ButtonPrototype button) {
        int indexInList = this.buttons.indexOf(button);
        return indexInList != -1 ? indexInList % this.difficulty.getColumns() : -1;
    }

    public int getIndexOfRow(ButtonPrototype button) {
        int indexInList = this.buttons.indexOf(button);
        return indexInList != -1 ? indexInList / this.difficulty.getRows() : -1;
    }

    public ButtonPrototype getButtonLocked() {
        return buttonLocked;
    }

    public int size() {
        return this.buttons.size();
    }

    public int getColumns() {
        return this.difficulty.getColumns();
    }

    public int getRows() {
        return this.difficulty.getRows();
    }

    public ButtonPrototype getButton(int index) {
        return this.buttons.get(index);
    }

    public int getIndexOf(ButtonPrototype button) {
        return this.buttons.indexOf(button);
    }

    public void swapButtons(int a, int b) {
        Collections.swap(this.buttons, a, b);
    }

    public void init() {
        buttons.clear();
        int countPosition = 0;
        for (int i = 0; i < difficulty.getRows(); i++) {
            for (int j = 0; j < difficulty.getColumns(); j++) {
                ButtonPrototype newBtn = createBtn(countPosition);
                if (countPosition < difficulty.getNumberOfCells() - 1) {
                    this.buttons.add(newBtn);
                }
                countPosition++;
            }
        }
        this.addButtonLocked();
        this.shuffleGameBoard();
    }

    abstract ButtonPrototype createBtn(int position);

    private void addButtonLocked() {
        this.buttonLocked = ButtonCache.get("ButtonLocked");
        this.buttonLocked.setGameIndex(this.difficulty.getNumberOfCells() - 1);
        this.buttons.add(this.buttonLocked);
    }

    public void shuffleGameBoard() {
        Collections.shuffle(this.buttons);
        if (!isSolvable()) {
            if (getIndexOf(buttonLocked) <= 1) {
                swapButtons(difficulty.getNumberOfCells() - 2, difficulty.getNumberOfCells() - 1);
            } else {
                swapButtons(0, 1);
            }
        }
    }

    private boolean isSolvable() {
        int inv = 0;
        for (int i = 0; i < difficulty.getNumberOfCells(); i++)
            if (buttons.get(i) != buttonLocked) {
                for (int j = 0; j < i; j++)
                    if (buttons.get(j).getGameIndex() > buttons.get(i).getGameIndex() &&
                            buttons.get(j).getGameIndex() != difficulty.getNumberOfCells() - 1) {
                        ++inv;
                    }
            }
        if (difficulty == Difficulty.Hard)
            inv += 1 + this.getIndexOfRow(this.buttonLocked);
        return inv % 2 == 0;
    }

}
