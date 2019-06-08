package game.board;

import game.button.ButtonPrototype;
import game.button.ButtonCache;
import game.button.ImageButton;
import game.enumeration.Difficulty;
import game.helpers.ImageProcessing;
import javafx.scene.image.Image;

public class ImageBoard extends GameBoard {
    private Image boardImage;
    private Image[][] boardImages;

    ImageBoard(Image boardImage, Difficulty difficulty) {
        super(difficulty);
        this.boardImage = boardImage;
        this.boardImages = ImageProcessing.cutImage(boardImage, difficulty.getRows(), difficulty.getColumns());
    }

    @Override
    ButtonPrototype createBtn(int position) {
        ImageButton imgBtn = (ImageButton) ButtonCache.get("ImageButton");
        imgBtn.setImage(boardImages[position / difficulty.getRows()][position % difficulty.getColumns()]);
        imgBtn.setGameIndex(position);
        return imgBtn;
    }

    public void changeImage(Image boardImage) {
        this.boardImage = boardImage;
        this.boardImages = ImageProcessing.cutImage(boardImage, difficulty.getRows(), difficulty.getColumns());
        for (ButtonPrototype button : buttons) {
            if (button != buttonLocked) {
                int position = button.getGameIndex();
                ((ImageButton) button).setImage(
                        boardImages[position / difficulty.getRows()][position % difficulty.getColumns()]
                );
            }
        }
    }

    public Image getBoardImage() {
        return boardImage;
    }
}
