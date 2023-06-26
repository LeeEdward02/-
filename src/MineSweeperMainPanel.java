import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class MineSweeperMainPanel extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private GameBoard board; // 游戏内容面板
    private long startTime = System.currentTimeMillis(); // 游戏开始时间
    protected static JLabel timeBar = null; // 下方计时状态栏
    protected static JLabel statusBar = null; // 下方进度状态栏
    protected static Timer time = null; // 计时器
    protected static long usedTime = 0; // 游戏耗时

    public MineSweeperMainPanel(int difficultCode){
        // 设置界面容器
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        // 根据不同的游戏难度设置游戏界面
        switch (difficultCode) {
            case GameBoard.EASY -> board = new GameBoard(GameBoard.EASY);
            case GameBoard.INTERMEDIATE -> board = new GameBoard(GameBoard.INTERMEDIATE);
            case GameBoard.DIFFICULT -> board = new GameBoard(GameBoard.DIFFICULT);
        }
        cp.add(board, BorderLayout.CENTER);

        // 设置菜单选择栏并且为其绑定监听器
        JMenuBar file = new JMenuBar();
        JMenu newFile = new JMenu("文件");
        JMenuItem newGame = new JMenuItem("新游戏");
        JMenuItem resetGame = new JMenuItem("重置当前游戏");
        JMenuItem exit = new JMenuItem("退出游戏");
        file.add(newFile);
        newFile.add(newGame);
        newFile.add(resetGame);
        newFile.add(exit);
        newGame.addActionListener(event -> {
            // 重置计时器
            startTime = System.currentTimeMillis();
            usedTime = 0;
            time.start();
            switch (difficultCode) {
                case GameBoard.EASY -> board.newGame(GameBoard.EASY);
                case GameBoard.INTERMEDIATE -> board.newGame(GameBoard.INTERMEDIATE);
                case GameBoard.DIFFICULT -> board.newGame(GameBoard.DIFFICULT);
            }
        });
        resetGame.addActionListener(event -> {
            // 重置计时器
            startTime = System.currentTimeMillis();
            usedTime = 0;
            time.start();
            board.resetGame();
        });
        exit.addActionListener(event -> dispose());
        cp.add(file, BorderLayout.NORTH);

        Container bar = new Container();
        bar.setLayout(new BorderLayout());

        // 设置下方的状态条
        timeBar = new JLabel("游戏已用时：0秒");
        timeBar.setHorizontalAlignment(0);//设置文字居中对齐
        timeBar.setFont(new Font("宋体",Font.BOLD,20));
        // 设定计时器
        ActionListener task = new UpdateTask();
        time = new Timer(1000, task);
        time.start();
        // 加入到bar面板中
        bar.add(timeBar, BorderLayout.NORTH);

        // 设置游戏进度状态条
        statusBar = new JLabel();
        switch (difficultCode) {
            case GameBoard.EASY -> statusBar.setText("总计有10个地雷，你已经标记了0个区域");
            case GameBoard.INTERMEDIATE -> statusBar.setText("总计有40个地雷，你已经标记了0个区域");
            case GameBoard.DIFFICULT -> statusBar.setText("总计有99个地雷，你已经标记了0个区域");
        }
        statusBar.setHorizontalAlignment(0);//设置文字居中对齐
        statusBar.setFont(new Font("宋体",Font.BOLD,20));
        // 加入到bar面板中
        bar.add(statusBar, BorderLayout.SOUTH);

        // 将bar面板放置在游戏面板下方
        cp.add(bar, BorderLayout.SOUTH);

        switch (difficultCode) {
            case GameBoard.EASY -> board.newGame(GameBoard.EASY);
            case GameBoard.INTERMEDIATE -> board.newGame(GameBoard.INTERMEDIATE);
            case GameBoard.DIFFICULT -> board.newGame(GameBoard.DIFFICULT);
        }
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("扫雷");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class UpdateTask implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long endTime = System.currentTimeMillis();
            usedTime = (endTime - startTime) / 1000;
            timeBar.setText("游戏已用时：" + usedTime + "秒");
        }
    }
}
