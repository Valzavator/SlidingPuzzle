package game.gameContoller;

import game.board.GameBoard;
import game.button.ButtonPrototype;
import game.button.ImageButton;
import game.gameContoller.command.*;
import game.helpers.stylesManager.IHighlight;
import game.helpers.Player;
import game.helpers.stylesManager.StylesManager;

// PROXY for StylesManager
public class GameController implements IHighlight {

    // real subject
    private StylesManager stylesManager;
    private CommandCreator commandCreator;

    private GameBoard gameBoard;
    private boolean highlightButtons;
    private boolean multiSwitch;

    private CommandManager commandManager;

    public GameController(GameBoard gameBoard, boolean highlightButtons, boolean multiSwitch) {
        this.gameBoard = gameBoard;
        this.highlightButtons = highlightButtons;
        this.multiSwitch = multiSwitch;
        this.commandCreator = new CommandCreator();
        this.commandManager = new CommandManager();
        this.highlightAllButtons();
    }

    public void setHighlightButtons(boolean highlightButtons) {
        this.highlightButtons = highlightButtons;
    }

    @Override
    public void highlightButton(ButtonPrototype button) {
        if (stylesManager == null)
            stylesManager = new StylesManager();
        if (highlightButtons)
            if (gameBoard.getIndexOf(button) == button.getGameIndex() &&
                    button.getGameIndex() != gameBoard.getButtonLocked().getGameIndex()) {
                stylesManager.highlightButton(button);
            } else if (button instanceof ImageButton)
                stylesManager.setNormalStyle(button);
            else
                stylesManager.resetStyle(button);
    }

    public void highlightAllButtons() {
        if (highlightButtons)
            for (int i = 0; i < gameBoard.size(); i++) {
                this.highlightButton(gameBoard.getButton(i));
            }
    }

    public int switchButton(ButtonPrototype pressedButton) {
        Command command = commandCreator.createCommand(multiSwitch, gameBoard);
        int switchCounter = command.switchButton(pressedButton);
        if (switchCounter > 0) {
            commandManager.addCommand(command);
            highlightAllButtons();
        }
        return switchCounter;
    }

    public int undoSwitchButton() {
        int switchCounter = commandManager.undo();
        if (switchCounter > 0)
            highlightAllButtons();
        return switchCounter;
    }

    public int redoSwitchButton() {
        int switchCounter = commandManager.redo();
        if (switchCounter > 0)
            highlightAllButtons();
        return switchCounter;
    }

    public boolean checkSolution() {
        for (int i = 0; i < gameBoard.size(); i++) {
            if (!gameBoard.getButton(i).compareGameIndex(i))
                return false;
        }
        finishGame();
        return true;
    }

    private void finishGame() {
        Player.play("/sounds/finishGame.wav");
        System.out.println("GAME FINISH!!!");
    }

    public void reset() {
        Player.play("/sounds/restart.wav");
        commandManager = new CommandManager();
    }
}
