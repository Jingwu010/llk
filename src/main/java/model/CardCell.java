package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public class CardCell extends Cell{
  int type;
  // int count;

  public CardCell(int type) {
    this.type = type;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CardCell)) return false;
    CardCell that = (CardCell) o;
    return type == that.type;
  }

  public boolean walkable(Direction d) {
    return false;
  }

  public String toString() {
    return String.valueOf(type);
  }
}
