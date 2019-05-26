package control;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

import static control.LLKGame.packBlockInfo;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by Jingwu Xu on 2019-05-03
 */
public class LLKGameTest1 implements Observer {
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
  public void testSetSelected1() {
    int row1 = 3, col1 = 1;
    int row2 = 3, col2 = 6;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);

    assertEquals((jsonObj.getJSONArray("DATA")).length(), 2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 0);
  }

  @Test
  public void testSetSelected2() {
    int row1 = 3, col1 = 1;
    int row2 = 1, col2 = 4;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 1);
  }

  @Test
  public void testSetSelected3() {
    int row1 = 2, col1 = 3;
    int row2 = 4, col2 = 2;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 2);
  }

  @Test
  public void testSetSelected4() {
    int row1 = 2, col1 = 5;
    int row2 = 4, col2 = 2;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 2);
  }

  @Test
  public void testSetSelected5() {
    int row1 = 2, col1 = 2;
    int row2 = 3, col2 = 6;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 1);
  }

  @Test
  public void testSetSelected6() {
    int row1 = 1, col1 = 4;
    int row2 = 3, col2 = 6;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) == 1);
  }

  @Test
  public void testSetSelected7() {
    int row1 = 4, col1 = 3;
    int row2 = 4, col2 = 5;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) != 1);
  }

  @Test
  public void testSetSelected8() {
    System.out.println();
    EmptyCell ec = new EmptyCell();
    game.board.setBoardCellAtPos(ec, 3, 6);

    int row1 = 1, col1 = 4;
    int row2 = 2, col2 = 6;
    CardCell cc = select(row1, col1, row2, col2);
    JSONObject jsonObj = new JSONObject(updatedMessage);
    deSelect(row1, col1, row2, col2, cc);

    // Common Assertion Check
    setSelectedCommonCheck(jsonObj, row1, col1, row2, col2);
    assertTrue(getPathTurnings(jsonObj.getJSONArray("DATA")) != 1);
    // System.out.println(jsonObj.toString());

    CardCell cc0 = new CardCell(0);
    game.board.setBoardCellAtPos(cc0, 3, 6);
  }

  @Test
  public void testFalseSetSelected() {
    int row1 = 2, col1 = 2;
    int row2 = 2, col2 = 3;
    CardCell cc = select(row1, col1, row2, col2);
    deSelect(row1, col1, row2, col2, cc);

    // False Path Check
    assertNull(updatedMessage);
  }

  private void setSelectedCommonCheck(JSONObject jsonObj, int row1, int col1, int row2, int col2) {
    // Check Message Received
    assertNotNull(updatedMessage);
    // Check Correct IDENTIFIER
    assertEquals(jsonObj.get("IDENTIFIER"), "PATH");
    // Check Valid Path Length
    assertTrue((jsonObj.getJSONArray("DATA")).length() <= 4);
    // Check Valid Path Start and End
    assertTrue(checkStartAndEnd(jsonObj.getJSONArray("DATA"), row1, col1, row2, col2));
    // Check Valid Turnings -- less than 3 turnings
    int turnings = getPathTurnings(jsonObj.getJSONArray("DATA"));
    assertTrue(turnings < 3);
    System.out.println("Path has " + turnings + " turnings");
  }

  private CardCell select(int row1, int col1, int row2, int col2) {
    // game.getBlockAtPos(row1,col1).addObserver(this);
    // game.getBlockAtPos(row2,col2).addObserver(this);
    CardCell cc = (CardCell)  game.getBlockAtPos(row1, col1).getCell();
    game.setSelectedBlock(row1, col1);
    game.setSelectedBlock(row2, col2);

    game.mbus.setMessageType(MessageType.DELETE);
    game.mbus.sendMessage(packBlockInfo(game.board.getBlockAtPos(row1, col1)));
    game.mbus.sendMessage(packBlockInfo(game.board.getBlockAtPos(row2, col2)));
    return cc;
  }

  private void deSelect(int row1, int col1, int row2, int col2, CardCell cc) {
    game.board.setBoardCellAtPos(cc, row1, col1);
    game.board.setBoardCellAtPos(cc, row2, col2);
  }

  private int getPathTurnings(JSONArray jsonArray) {
    int turnings = -1;
    Direction d = null;
    Block prev = null;
    for (int i=0; i<jsonArray.length(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      if (prev == null) {
        prev = getBlock(jsonObject);
      } else {
        Direction dir = null;
        Block curr = getBlock(jsonObject);
        int drow = curr.getRow() - prev.getRow();
        int dcol = curr.getCol() - prev.getCol();
        if (!((drow==0) ^ (dcol==0))) {
          String errormsg = String.format("(%2d, %2d) -> (%2d, %2d)\n", prev.getRow(), prev.getCol(), curr.getRow(), curr.getCol());
          throw new AssertionError("In Valid Path Direction at " + errormsg);
        }
        if (drow == 0) {
          if (dcol > 0) dir = Direction.RIGHT;
          else dir = Direction.LEFT;
        } else {
          if (drow > 0) dir = Direction.TOP;
          else dir = Direction.BOTTOM;
        }
        // String msg = String.format("(%2d, %2d) -> (%2d, %2d)\n", prev.getRow(), prev.getCol(), curr.getRow(), curr.getCol());
        // System.out.println(msg + " " + dir);
        if (dir != d) {
          turnings += 1;
          d = dir;
          prev = curr;
        }

        if (turnings > 2)
          throw new AssertionError("More than 2 turnings in PATH");
      }
    }
    return turnings;
  }

  private boolean checkStartAndEnd(JSONArray jsonArray, int row1, int col1, int row2, int col2) {
    Block end = getBlock(jsonArray.getJSONObject(0));
    Block start = getBlock(jsonArray.getJSONObject(jsonArray.length()-1));
    // System.out.println(start);
    // System.out.println(end);
    return start.getRow() == row1 && start.getCol() == col1 &&
            end.getRow() == row2 && end.getCol() == col2;
  }
  private Block getBlock(JSONObject jsonObj) {
    int row = jsonObj.getInt("row");
    int col = jsonObj.getInt("col");
    // System.out.println(jsonObj);
    // System.out.println(row);
    // System.out.println(col);
    return game.board.getBlockAtPos(row, col);
  }
  @Override
  public void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    if (!jsonObj.getString("IDENTIFIER").equals("PATH"))
      return;
    updatedMessage = arg.toString();
  }
}
