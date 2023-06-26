import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class StartPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    public StartPanel(){
        JFrame startFrame = new JFrame();

        startFrame.setSize(800,550);
        startFrame.setLocationRelativeTo(null);
        startFrame.setLayout(null);

        JPanel imgPanel=(JPanel) startFrame.getContentPane();
        imgPanel.setOpaque(false);
        ImageIcon img = new ImageIcon("src/image/封面.png");
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        startFrame.getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));

        GameButton startButton = new GameButton();
        startButton.setSize(150,60);
        startButton.setLocation(190,300);
        startButton.setImage("src/image/主菜单组件/开始1.png",
                "src/image/主菜单组件/开始2.png",
                "src/image/主菜单组件/开始3.png");
        startFrame.add(startButton);
        startButton.addActionListener(event -> new DifficultySelectionPanel());

        GameButton introductionButton = new GameButton();
        introductionButton.setSize(150,60);
        introductionButton.setLocation(460,300);
        introductionButton.setImage("src/image/主菜单组件/简介1.png",
                "src/image/主菜单组件/简介2.png",
                "src/image/主菜单组件/简介3.png");
        startFrame.add(introductionButton);
        introductionButton.addActionListener(event -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("简介");
            dialog.setText("<html><body><blockquote>微软扫雷是一款扫雷类型的视频游戏，由Curt Johnson创造，" +
                    "最初是为IBM的OS/2开发的，后来由Robert Donner移植到微软的Windows上，" +
                    "当时两人都是微软的员工。在1990年首次作为微软娱乐包的一部分正式发布，在1992" +
                    "年首次包含在Windows 3.1的标准安装中，取代了Windows 3.0中的Reversi。" +
                    "在Windows Vista之前的所有后续Windows版本中，微软扫雷都没有进行重大改动。" +
                    "在Windows 8 及以后的版本中，该游戏没有包含在新的Windows安装中，但" +
                    "微软工作室在微软商店中发布了由Arkadi-um开发的更新版本。</blockquote><body></html>");
            dialog.setSize(800, 300);
            dialog.setFontSize(20);
            dialog.setFontType(Font.BOLD);
            dialog.showDialog();
        });

        GameButton helpButton = new GameButton();
        helpButton.setSize(150,60);
        helpButton.setLocation(190,390);
        helpButton.setImage("src/image/主菜单组件/帮助1.png",
                "src/image/主菜单组件/帮助2.png",
                "src/image/主菜单组件/帮助3.png");
        startFrame.add(helpButton);
        helpButton.addActionListener(event -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("帮助");
            dialog.setText("<html><body><blockquote>左键挖开当前的方块，右键用旗子标记标记地雷。<br>" +
                    "注意！在进行第一次点击时，您也有可能触雷！<br>" +
                    "挖开方块后每个数字代表这个数字周围的九个格里有几颗雷。<br>" +
                    "要标记您认为可能有地雷的方块，请右键单击它。<br>" +
                    "当您标记了所有的地雷，且标记数量等于地雷数量时，游戏胜利。<br>" +
                    "如果您挖到了地雷，游戏失败。</blockquote><body></html>");
            dialog.setSize(800, 300);
            dialog.setFontSize(20);
            dialog.setFontType(Font.BOLD);
            dialog.showDialog();
        });

        GameButton exitButton = new GameButton();
        exitButton.setSize(150,60);
        exitButton.setLocation(460,390);
        exitButton.setImage("src/image/主菜单组件/退出1.png",
                "src/image/主菜单组件/退出2.png",
                "src/image/主菜单组件/退出3.png");
        startFrame.add(exitButton);
        exitButton.addActionListener(event -> System.exit(0));

        startFrame.setTitle("扫雷");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setResizable(false);
        startFrame.setVisible(true);
    }
}
