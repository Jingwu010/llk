package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class Block {
  int row;
  int col;
  Cell cell;

  public Block(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public void setCell(Cell cell) {
    this.cell = cell;
  }

  public String toString() {
    return cell.toString();
  }

  public boolean getWalkable(Direction d) {
    return cell.walkable(d);
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Block)) return false;
    Block that = (Block) obj;
    return row == that.row && col == that.col;
  }

  public Cell getCell() {
    return cell;
  }
}
