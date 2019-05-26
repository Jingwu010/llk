package view.layouts;

import control.LLKGame;
import control.MessageIdentifier;
import control.MessageType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.JSONObject;
import view.events.EventBus;
import view.events.EventType;
import view.utils.TimeProgressBar;

import java.util.Observable;
import java.util.Observer;


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
    setAlignment(Pos.TOP_CENTER);

    TimeProgressBar tpb = new TimeProgressBar(game, ebus);
    tpb.prefWidthProperty().bind(widthProperty());

    Thread thread = new Thread(tpb);
    thread.setDaemon(true);
    thread.start();

    TopHBox buttonHBox = new TopHBox();
    TopButton hintBtn = new TopButton(game, "hint", 5);
    hintBtn.setOnAction((event -> {
      game.hintAvailablePath();
    }));
    buttonHBox.addChild(hintBtn);

    TopButton shuffleBtn = new TopButton(game, "shuffle", 5);
    shuffleBtn.setOnAction((event -> {
      game.shuffleBoard();
    }));
    buttonHBox.addChild(shuffleBtn);

    TopButton pauseBtn = new TopButton(game, "pause");
    pauseBtn.setOnAction((event -> {
      // System.out.println(Thread.currentThread().getName() + " pauses game");
      ebus.setEventType(EventType.PAUSE);
      ebus.fireEvent();
    }));
    buttonHBox.addChild(pauseBtn);

    TopButton nextBtn = new TopButton(game, "next");
    nextBtn.setOnAction((event -> {
      game.nextNewGame();
    }));
    buttonHBox.addChild(nextBtn);

    this.getChildren().add(tpb);
    this.getChildren().add(buttonHBox);
  }
}

class TopHBox extends HBox {
    public TopHBox() {
      super();

      setPadding(new Insets(15, 12, 15, 12));
    }

    public void addChild(Node node) {
      getChildren().add(node);
      HBox.setHgrow(node, Priority.ALWAYS);
    }
}

class TopButton extends Button implements Observer {
  private String text;
  private LLKGame game;

  public TopButton(LLKGame game, String text) {
    this(game, text, -1);
  }

  public TopButton(LLKGame game, String text, int number) {
    super();
    this.game = game;
    this.text = text;

    changeNumber(number);

    setFont(new Font(16));
    setMaxSize(300, 200);
    setStyle("-fx-background-color: transparent");
    setTextFill(Color.WHITE);
    setFont(Font.font("Helvetica", FontWeight.BOLD, 24));

    game.mbus.addObserver(this);
  }

  private String addNumber(int number){
    if (number == -1)
      return "";
    else
      return ":  " + number;
  }

  public void changeNumber(int number) {
    String displayText = this.text + addNumber(number);
    setText(displayText.toUpperCase());
    if (number != 0)
      setCursor(Cursor.HAND);
    else
      setCursor(Cursor.NONE);
  }

  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    String header = jsonObj.getString("HEADER");
    if (!jsonObj.getString("IDENTIFIER").equals(MessageIdentifier.BUTTON.toString()))
      return;
    MessageType mtype = MessageType.toType(jsonObj.getString("TYPE"));
    switch (mtype) {
      case POST:
        break;
      case PUT:
        String text = jsonObj.getString("title");
        int number = jsonObj.getInt("number");
        if (text.equals(this.text))
          changeNumber(number);
        break;
    }
  }
}
