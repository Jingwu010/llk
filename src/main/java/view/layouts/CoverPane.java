package view.layouts;

import control.LLKGame;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.json.JSONObject;
import view.events.EventBus;
import view.events.EventType;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-05
 */
public class CoverPane extends Pane implements Observer {
  LLKGame game;
  EventBus ebus;
  boolean visible = false;

  public CoverPane(LLKGame game, EventBus ebus) {
    super();

    this.game = game;
    this.ebus = ebus;
    setVisible(visible);
    setStyle("-fx-background-color: rgba(224, 224, 235, 0.5)");
  }

  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    switch (EventType.toType(jsonObj.getString("TYPE"))) {
      case PAUSE:
        addPauseStage();
        break;
      case TIMEOUT:
        addTIMEOUTStage();
        break;
    }
    visible = !visible;
    setVisible(visible);
  }

  private void flipCoverPane() {
    visible = !visible;
    setVisible(visible);
  }

  private void addPauseStage() {
    Button btn = new Button();
    btn.setText("RESUME");
    btn.setOnAction(event -> {
      flipCoverPane();
    });
    getChildren().add(btn);
  }

  private void addTIMEOUTStage() {
    // [TODO] This is called by view timer thread, which is not the javafx main thread
    // [TODO] updaing javafx component from non-javafx thread is not allowed/suggested
    Button btn = new Button();
    btn.setText("RESTART");
    btn.setOnAction(event -> {
      game.restart();
      flipCoverPane();
    });
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
          getChildren().add(btn);
      }
    });
  }
}


