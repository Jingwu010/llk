package view.utils;

import control.LLKGame;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CellButton extends Button {
  public static final int WIDTH = 50;
  public static final int HEIGHT = 50;
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
    setStyle("-fx-background-radius: 0");

    if (!this.label.equals("E")) {
      DropShadow e = new DropShadow();
      e.setColor(Color.rgb(252, 164, 179, 0.8));
      e.setWidth(5);
      e.setHeight(3);
      e.setOffsetX(3);
      e.setOffsetY(5);
      e.setRadius(0);
      setEffect(e);
    }
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
    // setText(label);
    if (this.label.equals("E")) return;

    int id = Integer.valueOf(this.label) + 1;
    setGraphic(BackGroundIcons.getImageView(id));
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
