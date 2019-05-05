package view.layouts;

import control.LLKGame;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import view.utils.CellButton;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CardsGridPane extends GridPane {
  public static final int hPadding = 120;
  public static final int vPadding = 10;
  public static CellButton[][] btns;

  public CardsGridPane(LLKGame game, int nrow, int ncol) {
    super();
    setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));
    btns = new CellButton[nrow][ncol];
    for (int i = 0; i < nrow; i++) {
        for (int j = 0; j < ncol; j++) {
            CellButton btn = new CellButton(game, i, j);
            btns[i][j] = btn;
            game.getBlockAtPos(i, j).addObserver(btn);
            add(btn, j, i);
        }
    }
  }


}

