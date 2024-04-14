import javax.swing.*;
import java.awt.*;

public class ChessApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chesssssssss");
        frame.setSize(1100, 762);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(new ChessPanel());

        frame.setBackground(new Color(143, 119, 53));

    }
}
