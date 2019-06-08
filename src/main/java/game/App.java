package game;

import java.io.IOException;

import game.controller.HighScoresController;
import game.controller.MainController;
import game.controller.SettingsDialogController;
import game.model.highScores.HighScoreUnit;
import game.model.highScores.HighScoresModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initRootLayout();
        initPrimaryStage();
        this.primaryStage.show();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
            BorderPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            MainController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPrimaryStage() {
        primaryStage.setTitle("Sliding puzzle");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public void showSettingsDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SettingsDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("Settings");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SettingsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHighScores() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/HighScores.fxml"));
            AnchorPane page = loader.load();

            Stage highScores = new Stage();
            highScores.setResizable(false);
            highScores.setTitle("High-scores");
            highScores.initModality(Modality.WINDOW_MODAL);
            highScores.initOwner(primaryStage);
            Scene scene = new Scene(page);
            highScores.setScene(scene);

            HighScoresController controller = loader.getController();
            controller.setHighScoresStage(highScores);

            highScores.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}