package view.layouts;

import control.LLKGame;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.utils.TimeProgressBar;


/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class TopBox extends VBox {
  LLKGame game;
  public static final int HEIGHT = 100;
  public TopBox(LLKGame game) {
    super();
    this.game = game;
    setPrefHeight(HEIGHT);

    TimeProgressBar tpb = new TimeProgressBar();
    getChildren().add(tpb);
    Thread thread = new Thread(tpb);
    thread.setDaemon(true);
    thread.start();

    Button hintBtn = new Button("hint");
    hintBtn.setOnAction((event -> {
      game.hintAvailablePath();
    }));
    getChildren().add(hintBtn);

    Button shuffleBtn = new Button("shuffle");
    shuffleBtn.setOnAction((event -> {
      game.shuffleBoard();
    }));
    getChildren().add(shuffleBtn);
  }
}
