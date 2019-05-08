package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public abstract class Cell {
  public abstract boolean walkable(Direction d);

  public abstract String toString();

  public abstract boolean equals(Object o);

  public String label() {
    return toString();
  }
}
