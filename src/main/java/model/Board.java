package model;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jingwu Xu on 2019-05-02
 */

public class Board {
  int n;
  int m;
  int c;
  Block[][] board;
  public static EmptyCell ec = new EmptyCell();

  public Board() {
    this(Level.A.getRows(), Level.A.getColumns(), Level.A.getComplexity());
  }

  public Board(int nrow, int mcol, int complexity) {
    this.n = nrow+2;
    this.m = mcol+2;
    this.c = complexity;
    this.board = new Block[n][m];
    this.initBoard();
  }

  public Iterable<Block> iterable() {
    Iterable<Block> iterable = () -> new Iterator<Block>() {
			private int row = 0;
			private int col = 0;

			@Override
			public boolean hasNext() {
			  // System.out.println(row + " " + col);
				return n > row && m > col;
			}

			@Override
			public Block next() {
				Block block = board[row][col];
				col += 1;
				if (m <= col) {
          row += 1;
          col = 0;
        }
				return block;
			}
		};
    return iterable;
  }
  private void initBoard() {
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        board[i][j] = new Block(i, j);

    // init four sides empty cells
    for (int j = 0; j < m; j++) {
      board[0][j].setCell(ec);
      board[n-1][j].setCell(ec);
    }
    for (int i = 0; i < n; i++) {
      board[i][0].setCell(ec);
      board[i][m-1].setCell(ec);
    }

    // init middle puzzle cells
    int[] cards = initCards();
    int k = 0;
    for (int i = 1; i < n-1; i++){
      for (int j = 1; j < m-1; j++) {
        CardCell cc = new CardCell(cards[k]);
        k += 1;
        board[i][j].setCell(cc);
      }
    }
  }

  private int[] initCards() {
    int tot_cnt = c;
    int tot_occ = (n-2)*(m-2)/2;
    int avg_occ = (n-2)*(m-2)/2/c;
    int f_low = decompose(avg_occ, avg_occ+1, tot_cnt, tot_occ);

    int[] cards = new int[tot_occ*2];

    int k = 0;
    for (int i = 0; i < c; i++) {
      f_low -= 1;
      int cnt = avg_occ;
      if (f_low < 0)
        cnt = avg_occ + 1;
      for (int j = 0; j < cnt*2; j++) {
        cards[k++] = i;
      }
    }

    return RandomizeArray(cards);
  }

  private int decompose(int x, int y, int cnt, int sum) {
    for (int k = 0; k<=cnt; k++) {
      if (x*k + (cnt-k)*y == sum)
        return k;
    }
    return 0;
  }

  private static int[] RandomizeArray(int[] array){
    Random rgen = new Random();  // Random number generator

		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}

		return array;
	}

	public void printBoard() {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++)
        System.out.format(" %2s ", board[i][j].toString());
      System.out.println();
    }
  }

  public Block getBlockAtPos(int row, int col) {
    if (row >= n || row < 0) return null;
    if (col >= m || col < 0) return null;
    return board[row][col];
  }

  public int getRow() {
    return n;
  }

  public int getCol() {
    return m;
  }

  public int getComplexity() {
    return c;
  }

  public void setBoardCellAtPos(Cell cell, int row, int col) {
    board[row][col].setCell(cell);
  }

  public static void main(String[] args) {
    Board board = new Board();
    System.out.println();
    int i = 0;
    for (Block b : board.iterable()) {
      if (i == board.m) {
        System.out.println();
        i = 0;
      }
      System.out.format("%3s", b);
      i+=1;
    }
  }
}
