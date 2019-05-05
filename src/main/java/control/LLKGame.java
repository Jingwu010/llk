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

  List<Block> availablePath;

  int num_shuffles = 5;

  int num_hints = 5+1;

  public LLKGame() {
    this(Level.A);
  }

  public LLKGame(Level lv) {
    newGame(lv);
    availablePath = new ArrayList<>();

    updateConnectablePath();
  }

  public void next() {
    lv = lv.next();
    newGame(lv);
  }

  public Block getBlockAtPos(int row, int col) {
    return board.getBlockAtPos(row, col);
  }

  public int getRows() {
    return board.getRow();
  }

  public int getColumns() {
    return board.getCol();
  }

  public void setSelectedBlock(int row, int col) {
    if (selected == null) {
      selected = board.getBlockAtPos(row, col);
      return;
    }
    Block clicked = board.getBlockAtPos(row, col);
    List<Block> path = new ArrayList<>();
    if (checkConnectable(selected, clicked, path)) {

      setChanged();
      notifyObservers(packPathJsonString(path));

      board.getBlockAtPos(selected.getRow(), selected.getCol()).setCell(Board.ec);
      board.getBlockAtPos(clicked.getRow(), clicked.getCol()).setCell(Board.ec);
      // System.out.println("A MATCH!");
      selected = null;

      postMoveSteps();
    } else {
      // System.out.println("MISMATCH!");
      selected = clicked;
    }
  }

  public void hintAvailablePath() {
    if (num_hints < 0) return;
    num_hints -= 1;

    updateConnectablePath();

    setChanged();
    notifyObservers(packPathJsonString(availablePath));
  }

  private void postMoveSteps() {
    updateConnectablePath();
  }

  private void updateConnectablePath() {
    // System.out.println("findAnyConnectable() " + findAnyConnectable());
    // System.out.println("availablePath" + packPathJsonString(availablePath));
    while (!findAnyConnectable() && num_shuffles > 0) {
      // System.out.println("shuffle board");
      shuffleBoard();
    }
  }

  public boolean shuffleBoard() {
    if (num_shuffles < 0) return false;
    num_shuffles -= 1;
    List<Block> bList = new ArrayList<>();
    for (int i=0; i<board.getRow(); i++)
      for (int j=0; j<board.getCol(); j++) {
        Block curr = board.getBlockAtPos(i, j);
        if (!(curr.getCell() instanceof CardCell)) continue;
        bList.add(curr);
      }

    Random rgen = new Random();
    for (int i=0; i<bList.size(); i++) {
      int randomPosition = rgen.nextInt(bList.size());
      Cell temp = bList.get(i).getCell();
      bList.get(i).setCell(bList.get(randomPosition).getCell());
      bList.get(randomPosition).setCell(temp);
    }
    return true;
  }

  private boolean findAnyConnectable() {
    // System.out.println("findAnyConnectable");
    if (checkPathValid(availablePath)) return true;
    availablePath.clear();
    for (int i1=0; i1<board.getRow(); i1++)
      for (int j1=0; j1<board.getCol(); j1++) {
        Block block1 = board.getBlockAtPos(i1, j1);
        if (block1.getCell() instanceof EmptyCell) continue;
        // System.out.println();
        for (int i2=0; i2<board.getRow(); i2++)
          for (int j2=0; j2<board.getCol(); j2++) {
            if (i1==i2 && j1==j2) continue;
            Block block2 = board.getBlockAtPos(i2, j2);
            // System.out.println("Checking " + block1 + " " + block1.getRow() + ":" + block1.getCol() + "   " + block1.getCell().getClass());
            // System.out.println("Checking " + block2 + " " + block2.getRow() + ":" + block2.getCol() + "   " + block2.getCell().getClass());
            // System.out.print(block1.getCell().equals(block2.getCell()));
            // System.out.print("   ");
            // System.out.print(block1.getCell().toString().equals(block2.getCell().toString()));
            // System.out.print("\n");
            if (block1.getCell().equals(block2.getCell())) {
              List<Block> path = new ArrayList<>();
              if (checkConnectable(block1, block2, path)) {
                availablePath.addAll(path);
                return true;
              }
            }
          }
      }
    return false;
  }

  private boolean checkPathValid(List<Block> path) {
    // The path we find is already valid before
    // all we need to check is whether two blocks have been cleared
    if (path.isEmpty()) return false;

    Block end = path.get(0);
    Block start = path.get(path.size()-1);
    // System.out.println("Checking two blocks "+ start + " AND " + end);
    if (!start.getCell().equals(end.getCell())) return false;
    // Maybe wrong here
    if (start.getCell() instanceof EmptyCell) return false;

    return true;
  }

  private void newGame(Level lv) {
    this.board = new Board(lv.getRows(), lv.getColumns(), lv.getComplexity());
  }

  private static String packPathJsonString(List<Block> path) {
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

        if (nextRow >= board.getRow() || nextRow < 0) break;
        if (nextCol >= board.getCol() || nextCol < 0) break;

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
