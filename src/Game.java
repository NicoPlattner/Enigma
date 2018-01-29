import javax.swing.*;
import java.awt.*;

public class Game {

    public static JFrame frame = new JFrame("Text-Adventure");
    public static JPanel panel = new GamePanel();

    public Game() {
        frame.getContentPane().setBackground(Color.BLACK);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(false);
        frame.setResizable(false);

        frame.validate();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new Game();
    }
}

