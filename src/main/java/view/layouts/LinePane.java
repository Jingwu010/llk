package view.layouts;

import control.LLKGame;
import control.MessageIdentifier;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import view.utils.CellButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class LinePane extends Pane implements Observer {
  LLKGame game;
  public LinePane(LLKGame game) {
    super();
    this.game = game;

    this.game.mbus.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    if (!jsonObj.getString("IDENTIFIER").equals(MessageIdentifier.PATH.toString()))
      return;
    getChildren().clear();
    handlePathUpdate(jsonObj);
  }

  private void handlePathUpdate(JSONObject jsonObj) {
    List<CellButton> btns = new ArrayList<>();
    JSONArray jsonArray = jsonObj.getJSONArray("DATA");
    for (int i=0; i<jsonArray.length(); i++) {
      JSONObject json = jsonArray.getJSONObject(i);
      btns.add(CardsGridPane.btns[json.getInt("row")][json.getInt("col")]);
    }
    drawPath(btns);
  }

  private void drawPath(List<CellButton> btns) {
    CellButton prevBtn = null;
    for (CellButton currBtn : btns) {
      if (prevBtn == null) {
        prevBtn = currBtn;
        continue;
      }
      double startX = prevBtn.getLayoutX()+prevBtn.getMinWidth()/2;
      double startY = prevBtn.getLayoutY()+prevBtn.getMinHeight()/2;
      double endX = currBtn.getLayoutX()+currBtn.getMinWidth()/2;
      double endY = currBtn.getLayoutY()+currBtn.getMinHeight()/2;

      drawLine(startX, startY, endX, endY);

      prevBtn = currBtn;
    }
  }

  private void drawLine(double startX, double startY, double endX, double endY) {
    Line line = new Line(startX, startY, endX, endY);
    line.setStrokeWidth(3);
    line.setStroke(Color.rgb(0,0,0,1));

    addTransition(line);

    getChildren().add(line);
  }

  private void addTransition(Node node) {
    FadeTransition fadein = new FadeTransition();
    fadein.setNode(node);
    fadein.setDuration(new Duration(300));
    fadein.setFromValue(0);
    fadein.setToValue(1);
    fadein.play();

    FadeTransition fadeout = new FadeTransition();
    fadeout.setNode(node);
    fadeout.setDuration(new Duration(1000));
    fadeout.setFromValue(1);
    fadeout.setToValue(0);
    fadeout.play();
  }
}
