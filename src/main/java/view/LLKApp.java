package view;

import control.LLKGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.events.EventBus;
import view.layouts.BottomBox;
import view.layouts.CardsGridPane;
import view.layouts.MainStackPane;
import view.layouts.TopBox;
import view.utils.CellButton;

public class LLKApp extends Application  {
    LLKGame game = new LLKGame();
    EventBus ebus = new EventBus();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lian Lian Kan");

        int nrow = game.getRows();
        int ncol = game.getColumns();

        MainStackPane mainPane = new MainStackPane(game, ebus);

        // new BackGroundMusic().play();

        int scene_width = ncol*CellButton.HEIGHT+CardsGridPane.hPadding*2;
        int scene_height = nrow*CellButton.WIDTH+TopBox.HEIGHT+CardsGridPane.vPadding*2+BottomBox.HEIGHT;
        Scene scene = new Scene(mainPane, scene_width, scene_height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
