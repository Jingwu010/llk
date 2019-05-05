package view.layouts;

import control.LLKGame;
import javafx.scene.layout.StackPane;

/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class CentralStackPane extends StackPane {
  LLKGame game;
  public CentralStackPane (LLKGame game) {
    super();
    this.game = game;

    CardsGridPane cgp = new CardsGridPane(game, game.getRows(), game.getColumns());
    LinePane lp = new LinePane(game);
    lp.setPickOnBounds(false);
    getChildren().add(cgp);
    getChildren().add(lp);
  }

}
