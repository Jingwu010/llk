package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class EmptyCell extends Cell {

  public boolean walkable(Direction d) {
    return true;
  }

  public String toString() {
    return "E";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CardCell)) return false;
    CardCell that = (CardCell) o;
    return toString().equals(that.toString());
  }
}
