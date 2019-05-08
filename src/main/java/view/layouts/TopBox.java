package view.layouts;

import control.LLKGame;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.events.EventType;
import view.events.EventBus;
import view.utils.TimeProgressBar;


/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class TopBox extends VBox {
  LLKGame game;
  EventBus ebus;

  public static final int HEIGHT = 150;
  public TopBox(LLKGame game, EventBus ebus) {
    super();
    this.game = game;
    this.ebus = ebus;
    setPrefHeight(HEIGHT);

    TimeProgressBar tpb = new TimeProgressBar(game, ebus);
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

    Button pauseBtn = new Button("pause");
    pauseBtn.setOnAction((event -> {
      System.out.println("sendMessage event!");
      ebus.setEventType(EventType.PAUSE);
      ebus.fireEvent();
    }));

    getChildren().add(pauseBtn);

    Button nextBtn = new Button("next");
    nextBtn.setOnAction((event -> {
      game.nextNewGame();
    }));

    getChildren().add(nextBtn);
  }
}
