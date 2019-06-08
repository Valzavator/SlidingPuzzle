package game.controller;

import game.enumeration.BoardType;
import game.enumeration.Difficulty;
import game.model.SettingsModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class SettingsDialogController {
    private Stage dialogStage;

    private SettingsModel settingsModel;

    private Difficulty difficulty;
    private BoardType boardType;
    private boolean highlightButtons;
    private boolean multiSwitch;
    private boolean playSound;
    private boolean hardmode;

    @FXML
    ToggleGroup TGDifficulty;

    @FXML
    RadioButton isEasy;

    @FXML
    RadioButton isHard;

    @FXML
    ToggleGroup TGGameBoard;

    @FXML
    RadioButton isTextBoard;

    @FXML
    RadioButton isImageBoard;

    @FXML
    CheckBox CBHighlightButtons;

    @FXML
    CheckBox CBPlaySound;

    @FXML
    CheckBox CBHardmode;

    @FXML
    CheckBox CBMultiSwitch;

    public SettingsDialogController() {
        settingsModel = SettingsModel.getInstance();
        difficulty = settingsModel.getDifficulty();
        boardType = settingsModel.getBoardType();
        highlightButtons = settingsModel.getHighlightButtons();
        multiSwitch = settingsModel.getMultiSwitch();
        playSound = settingsModel.getPlaySounds();
        hardmode = settingsModel.getHardmode();
    }

    @FXML
    private void initialize() {
        isImageBoard.setUserData(BoardType.ImageBoard);
        isTextBoard.setUserData(BoardType.TextBoard);
        isEasy.setUserData(Difficulty.Easy);
        isHard.setUserData(Difficulty.Hard);

        TGGameBoard.selectToggle(boardType == BoardType.ImageBoard ? isImageBoard : isTextBoard);
        TGDifficulty.selectToggle(difficulty == Difficulty.Easy ? isEasy : isHard);

        TGDifficulty.selectedToggleProperty().addListener((ov, oldToggle, newToggle) ->
                difficulty = (Difficulty) newToggle.getUserData());
        TGGameBoard.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            boardType = (BoardType) newToggle.getUserData();
            CBHardmode.setDisable(boardType != BoardType.ImageBoard);
        });
        CBHighlightButtons.setSelected(highlightButtons);
        CBPlaySound.setSelected(playSound);
        CBHardmode.setSelected(hardmode);
        CBMultiSwitch.setSelected(multiSwitch);
        CBHighlightButtons.selectedProperty().addListener((ov, oldVal, newVal) ->
                highlightButtons = newVal);
        CBPlaySound.selectedProperty().addListener((ov, oldVal, newVal) ->
                playSound = newVal);
        CBHardmode.selectedProperty().addListener((ov, oldVal, newVal) ->
                hardmode = newVal);
        CBMultiSwitch.selectedProperty().addListener((ov, oldVal, newVal) ->
                multiSwitch = newVal);
        CBHardmode.setDisable(boardType != BoardType.ImageBoard);
    }

    @FXML
    private void OKButtonAction() {
        boolean isModify = settingsModel.getDifficulty() != difficulty ||
                settingsModel.getBoardType() != boardType ||
                settingsModel.getHighlightButtons() != highlightButtons ||
                settingsModel.getPlaySounds() != playSound ||
                settingsModel.getHardmode() != hardmode ||
                settingsModel.getMultiSwitch() != multiSwitch;
        if (isModify) {
            settingsModel.setDifficulty(difficulty);
            settingsModel.setBoardType(boardType);
            settingsModel.setHighlightButtons(highlightButtons);
            settingsModel.setMultiSwitch(multiSwitch);
            settingsModel.setPlaySounds(playSound);
            settingsModel.setHardmode(hardmode);
            settingsModel.saveToFile();
            settingsModel.notifyObservers();
        }
        dialogStage.close();
    }

    @FXML
    private void CancelButtonAction() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
