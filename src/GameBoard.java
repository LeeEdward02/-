import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serial;

// 这个类是游戏的核心
public class GameBoard extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    // 设定了“棋盘”中有多少个Cell对象
    public static int ROWS;
    public static int COLS;
    // 每一个Cell对象的尺寸大小，依次大小再去计算“棋盘”的大小
    public final int CELL_SIZE = 45;
    public int CANVAS_WIDTH;
    public int CANVAS_HEIGHT;
    private final Cell[][] cells;
    private MineMap mineMap;
    private int numMines;
    private int numFlags = 0;
    private boolean isOver;

    // 下面三个静态变量是游戏的难度编码
    public static final int EASY = 1;
    public static final int INTERMEDIATE = 2;
    public static final int DIFFICULT = 3;

    public GameBoard(int difficultyCode){
        switch (difficultyCode) {
            case EASY -> {
                this.numMines = 10;
                ROWS = 9;
                COLS = 9;
            }
            case INTERMEDIATE -> {
                this.numMines = 40;
                ROWS = 16;
                COLS = 16;
            }
            case DIFFICULT -> {
                this.numMines = 99;
                ROWS = 16;
                COLS = 30;
            }
        }
        CANVAS_WIDTH = CELL_SIZE * COLS;
        CANVAS_HEIGHT = CELL_SIZE * ROWS;
        cells = new Cell[ROWS][COLS];

        super.setLayout(new GridLayout(ROWS, COLS, 1, 1));
        for (int row = 0; row < ROWS; ++row){
            for (int col = 0; col < COLS; ++col){
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        // 为所有的Cell单元对象创建一个共享的鼠标事件监听器
        CellMouseListener listener = new CellMouseListener();
        // 通过下面的循环，将每个Cell对象的鼠标事件监听器对象设为listener
        for (int row = 0; row < ROWS; ++row){
            for (int col = 0; col <COLS; ++col){
                cells[row][col].addMouseListener(listener);
            }
        }
        super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }

    // 初始化一个新的游戏
    public void newGame(int difficultyCode){
        // 通过MineMap获得新游戏中的地雷数据的布局
        MineMap mineMap = new MineMap();
        switch (difficultyCode) {
            case EASY -> mineMap.newMineMap(GameBoard.EASY);
            case INTERMEDIATE -> mineMap.newMineMap(GameBoard.INTERMEDIATE);
            case DIFFICULT -> mineMap.newMineMap(GameBoard.DIFFICULT);
        }
        this.mineMap = mineMap;
        this.isOver = false;
        this.numFlags = 0;
        // 根据mineMap中的数据初始化每个Cell单元对象
        for (int row = 0; row < ROWS; ++row){
            for (int col = 0; col < COLS; ++col){
                cells[row][col].newGame(mineMap.isMined[row][col]);
            }
        }
    }

    public void resetGame(){
        this.isOver = false;
        this.numFlags = 0;
        for (int row = 0; row < ROWS; ++row){
            for (int col = 0; col < COLS; ++col){
                cells[row][col].newGame(mineMap.isMined[row][col]);
            }
        }
    }

    // 获得[srcRow, srcCol]Cell单元对象周围的8个邻居的地雷总数
    private int getSurroundingMines(int srcRow, int srcCol){
        int numMines = 0;
        for (int row = srcRow - 1; row <= srcRow + 1; ++row){
            for (int col = srcCol - 1; col <= srcCol + 1; ++col){
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS)
                    if (cells[row][col].isMined) numMines++;
            }
        }
        return numMines;
    }

    // 对[srcRow, srcCol]Cell单元对象执行挖雷操作
    // 如果该单元格对象中的标记的雷的数量为0，那么就自动递归对其周围8个邻居执行挖雷操作
    private void revealCell(int srcRow, int srcCol){
        int numMines = getSurroundingMines(srcRow, srcCol);
        cells[srcRow][srcCol].setText(String.valueOf(numMines));
        cells[srcRow][srcCol].setHorizontalTextPosition(SwingConstants.CENTER);
        cells[srcRow][srcCol].rightClickable = false;
        cells[srcRow][srcCol].leftClickable = false;
        cells[srcRow][srcCol].isRevealed = true;
        cells[srcRow][srcCol].paint();
        if (numMines == 0){
            for (int row = srcRow - 1; row <= srcRow + 1; ++row){
                for (int col = srcCol - 1; col <= srcCol + 1; ++col){
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS)
                        if (!cells[row][col].isRevealed) revealCell(row, col);
                }
            }
        }
    }

    // 用来判断玩家是否已经赢得此次游戏
    public boolean hasWon(){
        boolean isWin = true;
        for (int row = 0; row < ROWS; ++row){
            for (int col = 0; col < COLS; ++col){
                if ((cells[row][col].isMined) && (!cells[row][col].isRevealed)){
                    isWin = false;
                    break;
                }
            }
        }
        if (numMines != numFlags) isWin = false;
        return isWin;
    }

    // 定义一个内部类，该类的作用为鼠标事件监听器
    private class CellMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            // 获得触发此次鼠标事件的Cell对象
            Cell sourceCell = (Cell)e.getSource();
            // 获得鼠标事件的类型，MouseEvent.BUTTON1为单击鼠标左键
            if (e.getButton() == MouseEvent.BUTTON1 && sourceCell.leftClickable && !isOver) {
                // 如果当前Cell对象里面有地雷，则游戏结束；否则对该Cell对象执行挖雷操作
                if (sourceCell.isMined) {
                    Dialog dialog = new Dialog();
                    dialog.setTitle("游戏结束");
                    dialog.setText("<html><body>你踩中了地雷，游戏结束！<br>总耗时" + MineSweeperMainPanel.usedTime + "秒。<body></html>");
                    dialog.setFontType(Font.BOLD);
                    dialog.setSize(300,180);
                    dialog.showDialog();
                    isOver = true;
                    sourceCell.setIcon(new ImageIcon("src/image/游戏按键/雷.png"));
                } else {
                    revealCell(sourceCell.row, sourceCell.col);
                }
            }
            // 获得鼠标事件的类型，MouseEvent.BUTTON3为单击鼠标右键
            if (e.getButton() == MouseEvent.BUTTON3 && sourceCell.rightClickable && !isOver){
                if (sourceCell.isFlagged){
                    sourceCell.isFlagged = false;
                    sourceCell.isRevealed = false;
                    sourceCell.leftClickable = true;
                    numFlags--;
                    sourceCell.setIcon(new ImageIcon("src/image/游戏按键/上面写数字的.png"));
                    MineSweeperMainPanel.statusBar.setText("总计有10个地雷，你已经标记了" + numFlags + "个区域");
                } else {
                    sourceCell.isFlagged = true;
                    sourceCell.leftClickable = false;
                    numFlags++;
                    sourceCell.setIcon(new ImageIcon("src/image/游戏按键/旗子.png"));
                    if (sourceCell.isMined) sourceCell.isRevealed = true;
                    MineSweeperMainPanel.statusBar.setText("总计有10个地雷，你已经标记了" + numFlags + "个区域");
                }
                // 如果该Cell对象上插了旗子，那么就去掉旗子；否则将该Cell对象打上旗子的标记。
                // 当对Cell单元格对象执行了挖雷操作之后判断玩家是否赢得该游戏
                if (hasWon()){
                    Dialog dialog = new Dialog();
                    dialog.setTitle("游戏胜利");
                    dialog.setText("<html><body>你标记了所有地雷，游戏胜利！<br>总耗时" + MineSweeperMainPanel.usedTime + "秒。<body></html>");
                    dialog.setFontType(Font.BOLD);
                    dialog.setSize(350, 180);
                    dialog.showDialog();
                    isOver = true;
                }
            }
        if (isOver) MineSweeperMainPanel.time.stop();
        }
    }
}
