package control;

import model.CardCell;
import model.EmptyCell;
import model.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jingwu Xu on 2019-05-05
 */
class LLKGameTest3 implements Observer {
  static LLKGame game;
  String updatedMessage = null;

  @BeforeAll
  public static void setUp() {
    game = new LLKGame(Level.T);
    EmptyCell ec = new EmptyCell();
    game.board.setBoardCellAtPos(ec, 2, 4);
    game.board.setBoardCellAtPos(ec, 3, 2);
    game.board.setBoardCellAtPos(ec, 3, 3);
    game.board.setBoardCellAtPos(ec, 3, 4);
    game.board.setBoardCellAtPos(ec, 3, 5);
    game.board.setBoardCellAtPos(ec, 4, 4);
    CardCell cc = new CardCell(1);
    game.board.setBoardCellAtPos(cc, 2, 3);
    game.board.setBoardCellAtPos(cc, 2, 5);
    game.board.setBoardCellAtPos(cc, 4, 2);
    game.board.setBoardCellAtPos(cc, 5, 4);
    game.board.printBoard();
  }

  @BeforeEach
  public void init() {
    game.mbus.addObserver(this);
    game.selected = null;
    updatedMessage = null;
  }

  @Test
  public void testShuffleBoard() {
    int cnt0 = getCellCount(0);
    int cnt1 = getCellCount(1);
    game.shuffleBoard();
    assertEquals(cnt0, getCellCount(0));
    assertEquals(cnt1, getCellCount(1));
    EmptyCell ec = new EmptyCell();
    assertEquals(ec.toString(), game.board.getBlockAtPos(2, 4).getCell().toString());
    assertEquals(ec.toString(), game.board.getBlockAtPos(3, 2).getCell().toString());
    assertEquals(ec.toString(), game.board.getBlockAtPos(3, 3).getCell().toString());
    assertEquals(ec.toString(), game.board.getBlockAtPos(3, 4).getCell().toString());
    assertEquals(ec.toString(), game.board.getBlockAtPos(3, 5).getCell().toString());
    assertEquals(ec.toString(), game.board.getBlockAtPos(4, 4).getCell().toString());
  }

  public int getCellCount(int type) {
    int cnt = 0;
    for (int i=0; i<game.board.getRow(); i++)
      for (int j=0; j<game.board.getCol(); j++) {
        if (game.getBlockAtPos(i,j).getCell().toString().equals(String.valueOf(type)))
          cnt += 1;
      }
    return cnt;
  }

  @Override
  public void update(Observable o, Object arg) {
    return;
  }
}
