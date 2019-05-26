package view;

import control.LLKGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Level;
import view.events.EventBus;
import view.layouts.BottomBox;
import view.layouts.CardsGridPane;
import view.layouts.MainStackPane;
import view.layouts.TopBox;
import view.utils.BackGroundMusic;
import view.utils.CellButton;

public class LLKApp extends Application  {
    LLKGame game = new LLKGame(Level.T1);
    EventBus ebus = new EventBus();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.currentThread().setName("LLK JAVAFX THREAD");
        primaryStage.setTitle("Lian Lian Kan");

        Thread gameThread = new Thread(game);
        gameThread.setDaemon(true);
        gameThread.start();

        Thread ebusThread = new Thread(ebus);
        ebusThread.setDaemon(true);
        ebusThread.start();

        int nrow = game.getRows();
        int ncol = game.getColumns();

        MainStackPane mainPane = new MainStackPane(game, ebus);

        new BackGroundMusic().play();

        int scene_width = ncol*CellButton.HEIGHT+CardsGridPane.hPadding*2;
        int scene_height = nrow*CellButton.WIDTH+TopBox.HEIGHT+CardsGridPane.vPadding*2+BottomBox.HEIGHT;
        Scene scene = new Scene(mainPane, scene_width, scene_height);
        primaryStage.setScene(scene);
        primaryStage.show();
        mainPane.requestFocus();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
