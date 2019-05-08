package view.layouts;

import control.LLKGame;
import javafx.scene.layout.StackPane;
import view.events.EventBus;

/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class CentralStackPane extends StackPane {
  LLKGame game;
  EventBus ebus;
  public CentralStackPane (LLKGame game, EventBus ebus) {
    super();
    this.game = game;
    this.ebus = ebus;

    CardsGridPane cgp = new CardsGridPane(game, game.getRows(), game.getColumns());
    LinePane lp = new LinePane(game);
    lp.setPickOnBounds(false);
    getChildren().add(cgp);
    getChildren().add(lp);
  }

}
