package view.layouts;

import javafx.scene.layout.VBox;
import view.utils.TimeProgressBar;

/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class TopBox extends VBox {
  public static final int HEIGHT = 100;
  public TopBox() {
    super();
    setPrefHeight(HEIGHT);

    TimeProgressBar tpb = new TimeProgressBar();
    getChildren().add(tpb);
    Thread thread = new Thread(tpb);
    thread.setDaemon(true);
    thread.start();
  }
}
