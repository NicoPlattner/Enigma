import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class MenuState extends GameState {


    /*************************************************
     init
     ************************************************/

    private Font font;

    private BufferedImage background;
    private BufferedImage enter_button;
    private BufferedImage esc_button;

    private String[] options = {
            "Start",
            "Exit"
    };

    // for alternative controls
    private int currentOption = 0;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        ImageHandler imghandler = new ImageHandler();
        background = imghandler.loadImage("imgs/titlescreen.jpg");
        enter_button = imghandler.loadImage("imgs/buttons/Eingabe_button.png");
        esc_button = imghandler.loadImage("imgs/buttons/Escape_button.png");

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/pkmndp.ttf"));
            font = font.deriveFont(16F);
        } catch (Exception e){
            System.out.println("Error when loading Font");
        }
    }

    /*************************************************
     draw
     ************************************************/

    public void draw(Graphics2D g) {
        //Background:
        g.fillRect(0, 0, 192, 160);
        g.drawImage(background, 0, 0, null);

        //Options:
        g.setFont(font);
        g.setColor(Color.white);

        int count = 0;


        for (String option : options) {
            /* for alternative controls
            if (count == currentOption) {
                g.setColor(selectedTextColor);
            } else {
                g.setColor(Color.white);
            }
            */
            g.drawString(option, 92, 64 + count * 20);
            count++;
        }
        g.drawImage(enter_button, 67, 55, null);
        g.drawImage(esc_button, 67, 75, null);
    }

    /*************************************************
     Inputs
     ************************************************/

    public void keyPressed(int k) {
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.changestate(1);
        }
        if (k == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }


        /* Controls using up and down keys to select option and space to confirm option (commented out because it feels weird)
        if (k == KeyEvent.VK_DOWN) {
            currentOption++;
            if (currentOption >= options.length) {
                currentOption = 0;
            }
        }
        if (k == KeyEvent.VK_UP) {
            currentOption--;
            if (currentOption < 0) {
                currentOption = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_SPACE) {
            if (options[currentOption] == "Start") {
                gsm.changestate(1);
            }
            if (options[currentOption] == "Exit") {
                System.exit(0);
            }
        }
        */
    }
}
