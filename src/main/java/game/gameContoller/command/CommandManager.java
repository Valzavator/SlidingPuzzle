package game.gameContoller.command;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoHistoryCommands;
    private Stack<Command> redoHistoryCommands;

    public CommandManager() {
        undoHistoryCommands = new Stack<>();
        redoHistoryCommands = new Stack<>();
    }

    public void addCommand(Command command) {
        undoHistoryCommands.add(command);
    }

    public int undo() {
        return undoAction(undoHistoryCommands, redoHistoryCommands);
    }

    public int redo() {
        return undoAction(redoHistoryCommands, undoHistoryCommands);
    }

    private int undoAction(Stack<Command> getContainer,
                               Stack<Command> putContainer) {
        if (getContainer.empty())
            return 0;
        Command command = getContainer.pop();
        int switchCounter = command.undo();
        putContainer.push(command);
        return switchCounter;
    }

}
