import java.util.Random;

public class MineMap {
    private int numMines;
    private int rows;
    private int cols;
    boolean[][] isMined = new boolean[GameBoard.ROWS][GameBoard.COLS];
    public MineMap(){}
    public void newMineMap(int difficultyCode){
        switch (difficultyCode) {
            case GameBoard.EASY -> {
                this.numMines = 10;
                this.rows = 9;
                this.cols = 9;
            }
            case GameBoard.INTERMEDIATE -> {
                this.numMines = 40;
                this.rows = 16;
                this.cols = 16;
            }
            case GameBoard.DIFFICULT -> {
                this.numMines = 99;
                this.rows = 16;
                this.cols = 30;
            }
        }

        Random random = new Random();
        for (int i = 0; i < numMines; i++){
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (isMined[x][y]) i--;
            else isMined[x][y] = true;
        }
    }
}
