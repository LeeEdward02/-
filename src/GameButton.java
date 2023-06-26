import javax.swing.*;

public class GameButton extends JButton {
    GameButton(){
        super();
        setHorizontalTextPosition(SwingConstants.CENTER);
    }

    public void setImage(String icon, String rolloverIcon, String pressedIcon){
        setHorizontalTextPosition(SwingConstants.CENTER);
        //setBorderPainted(false);
        setIcon(new ImageIcon(icon));
        setRolloverIcon(new ImageIcon(rolloverIcon));
        setPressedIcon(new ImageIcon(pressedIcon));
    }

}
