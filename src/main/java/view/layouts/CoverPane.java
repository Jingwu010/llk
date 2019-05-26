package view.layouts;

import control.LLKGame;
import control.MessageBus;
import control.MessageIdentifier;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.json.JSONObject;
import view.events.EventBus;
import view.events.EventType;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-05
 */
public class CoverPane extends StackPane implements Observer {
  LLKGame game;
  EventBus ebus;
  MainStackPane msp;
  boolean visible = false;

  public CoverPane(MainStackPane msp, LLKGame game, EventBus ebus) {
    super();

    this.msp = msp;
    this.game = game;
    this.ebus = ebus;

    this.game.mbus.addObserver(this);

    setVisible(true);
    setAlignment(Pos.CENTER);
    setStyle("-fx-background-color: rgba(224, 224, 235, 0.75)");
  }

  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    String header = jsonObj.getString("HEADER");
    switch (header) {
      case MessageBus.HEADER:
        if (!jsonObj.getString("IDENTIFIER").equals(MessageIdentifier.NEXT.toString()))
          return;
        addNextGametage();
        break;
      case EventBus.HEADER:
        switch (EventType.toType(jsonObj.getString("TYPE"))) {
          case PAUSE:
            addPauseStage();
            break;
          case TIMEOUT:
            addTIMEOUTStage();
            break;
        }
        break;
    }
    flipCoverPane();
  }

  private void flipCoverPane() {
    visible = !visible;
    CoverPane cp = this;
    if (visible)
      Platform.runLater(new Runnable() {
      @Override
      public void run() {
        msp.getChildren().add(cp);
        }
      });
    else
      Platform.runLater(new Runnable() {
      @Override
      public void run() {
        msp.getChildren().remove(cp);
        }
      });
  }

  private void addPauseStage() {
    Button btn = new Button();
    btn.setText("RESUME");
    btn.setOnAction(event -> {
      ebus.setEventType(EventType.PAUSE);
      ebus.fireEvent();
    });
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
          getChildren().add(btn);
      }
    });
  }

  private void addTIMEOUTStage() {
    // [TODO] This is called by view timer thread, which is not the javafx main thread
    // [TODO] updaing javafx component from non-javafx thread is not allowed/suggested
    Button btn = new Button();
    btn.setText("RESTART");
    btn.setOnAction(event -> {
      game.restart();
      ebus.setEventType(EventType.PAUSE);
      ebus.fireEvent();
    });
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
          getChildren().add(btn);
      }
    });
  }

  private void addNextGametage() {
    Button btn = new Button();
    btn.setText("NEXT GAME");
    btn.setOnAction(event -> {
      game.nextNewGame();
      ebus.setEventType(EventType.PAUSE);
      ebus.fireEvent();
    });
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
          getChildren().add(btn);
      }
    });
  }
}


