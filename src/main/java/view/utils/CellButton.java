package view.utils;

import control.LLKGame;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CellButton extends Button {
  public static final int WIDTH = 40;
  public static final int HEIGHT = 40;
  int row;
  int col;
  String label;
  LLKGame game;

  public CellButton(LLKGame game, int row, int col) {
    super();

    this.row = row;
    this.col = col;
    this.game = game;

    this.label = game.getBlockAtPos(row, col).toString();
    newButton(this.label);

    setMinWidth(WIDTH);
    setLayoutX(WIDTH*row);
    setMinHeight(HEIGHT);
    setLayoutY(HEIGHT*col);
    setClickable();
  }

  public void newButton(String label) {
    setDisable(false);
    setVisible(true);
    setOpacity(1.0);
    if (label.equals("E")) {
      setDisable(true);
      setVisible(false);
      setOpacity(0.0);
    }
    updateLabel(label);
  }

  public void updateLabel(String label) {
    this.label = label;
    setText(label);
  }

  public void fadeOut() {
    FadeTransition fadeout = new FadeTransition();
    fadeout.setNode(this);
    fadeout.setDuration(new Duration(1200));
    fadeout.setFromValue(1);
    fadeout.setToValue(0);
    fadeout.play();
    setDisable(true);
  }

  private void setClickable() {
    setOnAction(event -> {
        game.setSelectedBlock(row, col);
    });
  }
}
