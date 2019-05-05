package control;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class LLKGame extends Observable{
  Level lv;

  Board board;

  Block selected = null;

  public LLKGame() {
    this(Level.A);
  }

  public LLKGame(Level lv) {
    newGame(lv);
  }

  private void newGame(Level lv) {
    this.board = new Board(lv.getRows(), lv.getColumns(), lv.getComplexity());
  }

  public void next() {
    lv = lv.next();
    newGame(lv);
  }

  public Board getBoard() {
    return board;
  }

  public Level getLv() {
    return lv;
  }

  public Block getBlockAtPos(int row, int col) {
    return board.getBlockAtPos(row, col);
  }

  public int getRows() {
    return board.getN();
  }

  public int getColumns() {
    return board.getM();
  }

  public void setSelected(int row, int col) {
    if (selected == null) {
      selected = board.getBlockAtPos(row, col);
      return;
    }
    Block clicked = board.getBlockAtPos(row, col);
    List<Block> path = new ArrayList<>();
    if (checkConnectable(selected, clicked, path)) {

      setChanged();
      notifyObservers(packPathJsonString(path));

      board.getBlockAtPos(selected.getRow(), selected.getCol()).fill(Board.ec);
      board.getBlockAtPos(clicked.getRow(), clicked.getCol()).fill(Board.ec);
      // System.out.println("A MATCH!");
      selected = null;
    } else {
      // System.out.println("MISMATCH!");
      selected = clicked;
    }
  }

  /**
   * {
   *   "IDENTIFIER" : "PATH"
   *   "DATA" : [
   *      {
   *        "ROW"   :
   *        "COL"   :
   *        "LABEL" :
   *      }, ...
   *   ]
   * }
   * @param path
   * @return
   */
  private String packPathJsonString(List<Block> path) {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("IDENTIFIER", "PATH");
    JSONArray jsonArr = new JSONArray();
    for (Block block : path) {
      JSONObject jobj = new JSONObject();
      jobj.put("row", block.getRow());
      jobj.put("col", block.getCol());
      jobj.put("label", block.getCell().toString());
      jsonArr.put(jobj);
    }
    jsonObj.put("DATA", jsonArr);
    System.out.println(jsonObj.toString());
    return jsonObj.toString();
  }


  private boolean checkConnectable(Block b1, Block b2, List<Block> path) {
    if (b1.equals(b2))
      return false;
    if(!b1.getCell().equals(b2.getCell()))
      return false;
    List<Direction> dirs = new ArrayList<>();
    if (b1.getCol() > b2.getCol())
      dirs.add(Direction.LEFT);
    else if (b1.getCol() < b2.getCol())
      dirs.add(Direction.RIGHT);

    if (b1.getRow() > b2.getRow())
      dirs.add(Direction.TOP);
    else if (b1.getRow() < b2.getRow())
      dirs.add(Direction.BOTTOM);

    // System.out.println("DIRS " + dirs);
    if (dirs.size() == 1) {
      dirs.add(dirs.get(0).side());
      dirs.add(dirs.get(0).side().opposite());
      List<Block> tmpPath = new ArrayList<>();
      if (findPath(b1, b2, dirs, tmpPath)){
        path.addAll(tmpPath);
        return true;
      }
    }
    else {
      for (Direction d : Direction.values()) {
        dirs.add(0, d);
        List<Block> tmpPath = new ArrayList<>();
        if (findPath(b1, b2, dirs, tmpPath)){
          path.addAll(tmpPath);
          return true;
        }
        dirs.remove(0);
      }
    }
    return false;
  }

  private boolean findPath(Block start, Block end, List<Direction> dirs, List<Block> path) {
    return findPath(start, end, dirs, path, null);
  }

  private boolean findPath(Block start, Block end, List<Direction> dirs, List<Block> path, Direction d) {
    // [NOTE] 180 turn around is not allowed
    if (dirs.size() == 0) return false;

    boolean flag = false;
    List<Direction> tmpList = new ArrayList<>();
    tmpList.addAll(dirs);
    // System.out.format("(%2d,%2d) %s \n", start.getRow(), start.getCol(), dirs.toString());
    for (int k = 0; k < dirs.size(); k++) {
      // choose a direction from limited possibilities
      Direction dir = dirs.get(k);

      if (d == null) d = dir;
      // 180 turn around is not allowed
      if (d == dir.opposite()) continue;

      tmpList.remove(k);

      for (int i = 1; ; i++) {
        // walk as many blocks as possible
        int nextRow = start.getRow() + dir.drow*i;
        int nextCol = start.getCol() + dir.dcol*i;

        if (nextRow >= board.getN() || nextRow < 0) break;
        if (nextCol >= board.getM() || nextCol < 0) break;

        Block nextBlock = board.getBlockAtPos(nextRow, nextCol);
        if (nextBlock.equals(end)) {
          // System.out.println("find equals");
          // System.out.println(end);
          // System.out.println(nextBlock);
          path.add(end);
          path.add(start);
          return true;
        }
        // if next block is walkable
        if (nextBlock.getWalkable(dir)) {
          // System.out.format("(%2d,%2d) -> (%2d, %2d)\n", start.getRow(), start.getCol(), nextRow, nextCol);
          flag = flag || findPath(nextBlock, end, tmpList, path, dir);
        } else break;

      }
      tmpList.add(k, dirs.get(k));
    }
    if (flag) path.add(start);
    return flag;
  }

  public static void main(String[] args) {
    LLKGame game = new LLKGame(Level.T);
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

    // game.getBlockAtPos(3,1).addObserver(this);
    // game.getBlockAtPos(3,4).addObserver(this);

    Block clicked = game.getBlockAtPos(1, 4);
    Block selected = game.getBlockAtPos(3, 6);
    List<Block> path = new ArrayList<>();
    game.checkConnectable(clicked, selected, path);
    System.out.println(path);

  }
}
