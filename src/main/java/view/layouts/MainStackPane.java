package view.layouts;

import control.LLKGame;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import view.events.EventBus;

/**
 * Created by Jingwu Xu on 2019-05-06
 */
public class MainStackPane extends StackPane {
  LLKGame game;
  EventBus ebus;

  public MainStackPane (LLKGame game, EventBus ebus) {
    super();

    this.game = game;
    this.ebus = ebus;

    CentralStackPane stackPane = new CentralStackPane(game, ebus);
    TopBox topbox = new TopBox(game, ebus);
    BottomBox botbox = new BottomBox();
    MainGamePane mbp = new MainGamePane(stackPane, topbox, botbox);

    CoverPane cp = new CoverPane(this, game, ebus);
    ebus.addObserver(cp);

    getChildren().add(mbp);
    // getChildren().add(cp);

    setBackground(new Background(new BackgroundImage(
        new Image("file:src/main/resources/pngs/map.png"),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
  }
}
