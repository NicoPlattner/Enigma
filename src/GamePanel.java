import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {

    /*  for easier use of pixelart and the possiblity to scale
        the game for different monitors/resolutions the smaller assets are used
        and the whole image is then scaled to fit the desired resolution  */
    private int scale = 4;

    // game state manager
    private GameStateManager gsm = new GameStateManager();


    /*************************************************
     init
     ************************************************/

    public GamePanel() {
        super();

        setPreferredSize(new Dimension(192 * scale, 160 * scale));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }


    /*************************************************
     Output
     ************************************************/

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(scale, scale);

        /* Functions are handled by the GameStateManager which loads the function from the current GameState*/
        gsm.draw(g2d);
    }


    /*************************************************
     Inputs
     ************************************************/

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        repaint();
        gsm.keyReleased(e.getKeyCode());
    }
}
