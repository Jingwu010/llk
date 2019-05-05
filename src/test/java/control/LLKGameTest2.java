package control;

import model.Block;
import model.CardCell;
import model.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jingwu Xu on 2019-05-04
 */
class LLKGameTest2 implements Observer {
  static LLKGame game;
  String updatedMessage = null;

  @BeforeEach
  public void init() {
    game = new LLKGame(Level.T);
    int k = 0;
    for (int i=1; i<game.getRows()-1; i++)
      for (int j=1; j<game.getColumns()-1; j++) {
        CardCell cc = new CardCell(k++);
        game.board.setBoardCellAtPos(cc, i, j);
      }
    game.addObserver(this);
    updatedMessage = null;
  }

  @Test
  void testHintAvailablePath1() {
    game.hintAvailablePath();
    assertTrue(game.num_shuffles == 0);
    assertTrue(game.availablePath.isEmpty());
  }

  @Test
  void testHintAvailablePath2() {
    game.board.setBoardCellAtPos(new CardCell(0), 1, 6);
    game.hintAvailablePath();
    System.out.println(game.num_shuffles);
    assertTrue(game.num_shuffles == 5);
    assertFalse(game.availablePath.isEmpty());
    assertTrue(game.availablePath.size()<=4);
  }

  @Test
  void testHintAvailablePath3() {
    game.board.setBoardCellAtPos(new CardCell(0), 1, 6);
    game.hintAvailablePath();

    game.setSelectedBlock(1,1);
    game.setSelectedBlock(1,6);
    assertTrue(game.num_shuffles == 0);
    assertTrue(game.availablePath.isEmpty());
  }

  @Test
  void testHintAvailablePath4() {
    game.board.setBoardCellAtPos(new CardCell(0), 1, 6);
    game.board.setBoardCellAtPos(new CardCell(1), 2, 1);
    game.hintAvailablePath();

    game.setSelectedBlock(1,1);
    game.setSelectedBlock(1,6);
    game.board.printBoard();
    assertTrue(game.num_shuffles == 5);
    assertFalse(game.availablePath.isEmpty());
    assertTrue(game.availablePath.size()<=4);
  }

  @Test
  void testHintAvailablePath5() {
    game.board.setBoardCellAtPos(new CardCell(0), 1, 6);
    game.board.setBoardCellAtPos(new CardCell(1), 1, 5);
    game.hintAvailablePath();

    List<Block> apath = game.availablePath;
    game.setSelectedBlock(1,2);
    game.setSelectedBlock(1,5);
    game.board.printBoard();
    assertTrue(game.num_shuffles == 5);
    assertFalse(game.availablePath.isEmpty());
    assertTrue(game.availablePath.size()<=4);
    assertEquals(apath, game.availablePath);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o instanceof LLKGame)
      updatedMessage = arg.toString();
  }
}
