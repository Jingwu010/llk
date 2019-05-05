package view.layouts;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class MainBorderPane extends BorderPane {
  public MainBorderPane(Node center, Node top, Node bot) {
    this(center, top, new Text(), bot, new Text());
  }

  public MainBorderPane(Node center, Node top, Node right, Node bottom, Node left) {
    super(center, top, right, bottom, left);
  }
}
