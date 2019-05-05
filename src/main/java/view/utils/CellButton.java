package view.utils;

import control.LLKGame;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CellButton extends Button implements Observer {
  public static final int WIDTH = 40;
  public static final int HEIGHT = 40;
  int row;
  int col;
  String label;
  LLKGame game;

  public CellButton(LLKGame game, int row, int col) {
    super(game.getBlockAtPos(row, col).toString());
    this.label = game.getBlockAtPos(row, col).toString();
    this.row = row;
    this.col = col;
    this.game = game;

    if (label.equals("E")) {
      setDisable(true);
      setVisible(false);
    }

    setMinWidth(WIDTH);
    setLayoutX(WIDTH*row);
    setMinHeight(HEIGHT);
    setLayoutY(HEIGHT*col);
    setClickable();
  }

  private void setClickable() {
    setOnAction(event -> {
      System.out.println("clicked " + row + " " + col);
        game.setSelected(row, col);
    });
  }

  @Override
  public void update(Observable o, Object arg) {
    label = arg.toString();
    if (label.equals("E")) {
      FadeTransition fadeout = new FadeTransition();
      fadeout.setNode(this);
      fadeout.setDuration(new Duration(1200));
      fadeout.setFromValue(1);
      fadeout.setToValue(0);
      fadeout.play();
      setDisable(true);
    }
  }
}
