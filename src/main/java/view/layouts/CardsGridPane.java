package view.layouts;

import control.LLKGame;
import control.MessageIdentifier;
import control.MessageType;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;
import view.utils.CellButton;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CardsGridPane extends GridPane implements Observer {
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
            add(btn, j, i);
        }
    }

    game.mbus.addObserver(this);
  }


  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    if (!jsonObj.getString("IDENTIFIER").equals(MessageIdentifier.BLOCK.toString()))
      return;
    int row = jsonObj.getInt("row");
    int col = jsonObj.getInt("col");
    switch (MessageType.toType(jsonObj.getString("TYPE"))) {
      case POST:
        btns[row][col].updateLabel(jsonObj.getString("label"));
        break;
      case DELETE:
        btns[row][col].fadeOut();
        break;
      case PUT:
        btns[row][col].newButton(jsonObj.getString("label"));
        break;
      default:
        break;

    }


    // btns[row][col].fadeOut();
  }
}

