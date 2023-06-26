import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.io.Serial;

public class Cell extends GameButton{
    // 为Cell单元格定义若干颜色和字体常量
    // 这些常量将随着Cell单元格的状态变化而被使用
    @Serial
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    public static final Color FG_NOT_REVEALED = Color.RED;    // flag, mines
    public static final Color FG_REVEALED = Color.YELLOW;     // number of mines
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 15);

    // 定义Cell对象的属性
    int row, col;   // row和col值用来表示单元格在最终“棋盘”上的位置定位
    boolean isRevealed; // 标记是否已经被挖出
    boolean isMined;    // 标记是否是地雷
    boolean isFlagged;  // 标记是否被玩家插上了一个小红旗
    boolean rightClickable; // 标记一个单元格是否可以被右键点击
    boolean leftClickable;  // 标记一个单元格是否可以被左键点击

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        super.setFont(FONT_NUMBERS);
    }

    public void newGame(boolean isMined){
        this.isRevealed = false;
        this.isFlagged = false;
        this.isMined = isMined;
        this.rightClickable = true;
        this.leftClickable = true;
        super.setEnabled(true);
        super.setText("");
        paint();
    }

    public void paint(){
        super.setForeground(isRevealed? FG_REVEALED: FG_NOT_REVEALED);
        setIcon(isRevealed? new ImageIcon("src/image/游戏按键/已经点过的.png"): new ImageIcon("src/image/游戏按键/上面写数字的.png"));
    }
}
