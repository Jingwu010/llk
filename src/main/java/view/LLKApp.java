package view;

import control.LLKGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.layouts.*;
import view.utils.BackGroundMusic;
import view.utils.CellButton;

public class LLKApp extends Application  {
    LLKGame game = new LLKGame();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lian Lian Kan");

        int nrow = game.getRows();
        int ncol = game.getColumns();

        CentralStackPane stackPane = new CentralStackPane(game);
        TopBox topbox = new TopBox(game);
        BottomBox botbox = new BottomBox();
        MainBorderPane mbp = new MainBorderPane(stackPane, topbox, botbox);

        new BackGroundMusic().play();

        int scene_width = ncol*CellButton.HEIGHT+CardsGridPane.hPadding*2;
        int scene_height = nrow*CellButton.WIDTH+TopBox.HEIGHT+CardsGridPane.vPadding*2;
        Scene scene = new Scene(mbp, scene_width, scene_height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
