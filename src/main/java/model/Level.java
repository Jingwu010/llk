package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
class LevelDetail {
  int row;
  int col;
  int complexity;
  String desc;

  LevelDetail(int row, int col, int complexity, String desc) {
    this.row = row;
    this.col = col;
    this.complexity = complexity;
    this.desc = desc;
  }
}

public enum Level {
  T(new LevelDetail(6, 6, 1,"Test")),
  T1(new LevelDetail(6, 6, 1,"Test")),
  T2(new LevelDetail(6, 6, 2,"Test")),
  A(new LevelDetail(10, 14, 20,"Beginner")),
  B(new LevelDetail(10, 14, 24,"Easy")),
  C(new LevelDetail(10, 14, 32,"Normal")),
  D(new LevelDetail(10, 14, 32,"Hard"));

  private LevelDetail ld;

  Level(LevelDetail ld) {
    this.ld = ld;
  }

  public LevelDetail ld() {
    return ld;
  }

  public int getRows() {
    return ld.row;
  }

  public int getColumns() {
    return ld.col;
  }

  public int getComplexity() {
    return ld.complexity;
  }

  public Level next() {
    switch (this) {
      case T1:
        return T2;
      case T2:
        return T1;
      case A:
        return B;
      case B:
        return C;
      case C:
        return D;
      case D:
        return A;
      default:
        return T;
    }
  }

  public static void main(String[] agrs) {
    Level lv = Level.T;
    lv = lv.next();
    System.out.println(lv);
  }
}
