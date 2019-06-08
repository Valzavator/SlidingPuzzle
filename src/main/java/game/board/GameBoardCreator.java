package game.board;

import game.enumeration.BoardType;
import game.enumeration.Difficulty;
import game.helpers.RandomImage;

public class GameBoardCreator {
    public GameBoard createGameBoard(BoardType boardType, Difficulty difficulty) {
        if (boardType == BoardType.ImageBoard)
            return new ImageBoard(RandomImage.getRandomImage(), difficulty);
        else if (boardType == BoardType.TextBoard)
            return new TextBoard(difficulty);
        else throw new IllegalArgumentException("Invalid game board type!");
    }
}
