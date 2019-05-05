package model;

/**
 * Created by Jingwu Xu on 2019-05-02
 */
public enum Direction {
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP(-1, 0),
    BOTTOM(1, 0);

    public int drow;
    public int dcol;

    Direction(int drow, int dcol) {
        this.drow = drow;
        this.dcol = dcol;
    }

    public Direction opposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case TOP:
                return BOTTOM;
            case BOTTOM:
                return TOP;
        }
        return TOP;
    }

    public Direction side() {
        switch (this) {
            case LEFT:
            case RIGHT:
                return TOP;
            case TOP:
            case BOTTOM:
                return LEFT;
        }
        return TOP;
    }
}
