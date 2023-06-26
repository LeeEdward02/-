import javax.swing.*;
import java.io.Serial;

public class DifficultySelectionPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    public DifficultySelectionPanel(){
        JFrame selectionMenu = new JFrame();

        selectionMenu.setSize(400,400);
        selectionMenu.setLocationRelativeTo(null);
        selectionMenu.setLayout(null);

        JPanel imgPanel=(JPanel) selectionMenu.getContentPane();
        imgPanel.setOpaque(false);
        ImageIcon img = new ImageIcon("src/image/难度选择背景.png");
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        selectionMenu.getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));

        GameButton easyButton = new GameButton();
        easyButton.setSize(160,50);
        easyButton.setLocation(120,60);
        easyButton.setImage("src/image/难度选择 组件/简单1.png",
                "src/image/难度选择 组件/简单2.png",
                "src/image/难度选择 组件/简单3.png");
        selectionMenu.add(easyButton);
        easyButton.addActionListener(event -> {
            new MineSweeperMainPanel(GameBoard.EASY);
            selectionMenu.dispose();
        });

        GameButton intermediateButton = new GameButton();
        intermediateButton.setSize(160,50);
        intermediateButton.setLocation(120,140);
        intermediateButton.setImage("src/image/难度选择 组件/普通1.png",
                "src/image/难度选择 组件/普通2.png",
                "src/image/难度选择 组件/普通3.png");
        selectionMenu.add(intermediateButton);
        intermediateButton.addActionListener(event -> {
            new MineSweeperMainPanel(GameBoard.INTERMEDIATE);
            selectionMenu.dispose();
        });

        GameButton difficultButton = new GameButton();
        difficultButton.setSize(160,50);
        difficultButton.setLocation(120,220);
        difficultButton.setImage("src/image/难度选择 组件/困难1.png",
                "src/image/难度选择 组件/困难2.png",
                "src/image/难度选择 组件/困难3.png");
        selectionMenu.add(difficultButton);
        difficultButton.addActionListener(event -> {
            new MineSweeperMainPanel(GameBoard.DIFFICULT);
            selectionMenu.dispose();
        });

        selectionMenu.setTitle("难度选择");
        selectionMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectionMenu.setResizable(false);
        selectionMenu.setVisible(true);
    }
}
