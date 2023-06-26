import javax.swing.*;
import java.awt.*;

public class Dialog extends JDialog {
    private String title;
    private String text;
    private int width;
    private int height;
    private int fontType;
    private int fontSize;

    public Dialog(){
        this.title = "new dialog";
        this.text = "this is a new dialog";
        this.width = 400;
        this.height = 100;
        this.fontType = Font.PLAIN;
        this.fontSize = 20;
    }


    public void showDialog(){
        super.setTitle(title);
        super.setSize(width, height);
        setLocationRelativeTo(null); // 将弹窗置于屏幕中央
        setResizable(false);

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("宋体", fontType, fontSize));
        container.add(label, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setText(String text){
        this.text = text;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setFontType(int fontType){
        this.fontType = fontType;
    }

    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }

}
