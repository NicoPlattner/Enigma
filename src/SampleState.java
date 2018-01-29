import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class SampleState extends GameState {

    //Gameobjects:
    private int playerX = 1;
    private int playerY = 1;

    //Textvariables
    private int currentText;
    private LinkedList<TextOption> options;

    //Drawvariables
    private Font textFont = new Font("Power Clear", Font.PLAIN, 10);

    // Labyrinth x - y inverted for some reason
    Labyrinth lab = new Labyrinth(9, 9);
    private int[][] labyrinth = lab.getLabyrinth();

    /*Texts:
    * In this system are Texts, Textoptions and Actions
    * A Text is displayed in the Top of the window and can have no or multiple Textoptions to choose from
    * A Textoption is displayed on the left and leads to a new Text or an Action
    * An Action can change or check Gamevariables e.g player position, health
    *
    * In this example Text 0 says "You wake up in a cave"
    * It has the option "Continue" which is selected by pressing Enter and it leads to Text 1 as labelled in the list
    * So a new Text is added by new Text() where first the Text has to be entered in "",
    * then Textoptions have to be added by new Textoption[]{} in the curly brackets Textoptions can be added
    * by new Textoption() with Keyevent.VK_... the key can be selected, than a text has to be assigned and then
    * it can lead to an other text in this list by entering the corresponding number
    *
    * Multiple Textoptions need to be seperated with a , e.g Text 8
    *
    * If no number is given it will show the standardTextoptions (walk, talk,...) based on the players position and other attributes
    *
    * Texts that are either part of a Dialog or the story should lead to another text since not much text fits in the
    * window
    * The end of a dialog or Storysegment or also generic phrases like you hit the wall should show the standardoptions
    * and the player will be able to continue his journey*/

    private Text[] texts = {
            // 0:
            new Text("You wake up in a cave", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1)}),
            // 1:
            new Text("You look around but you can't see anything", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 2)}),
            // 2:
            new Text("What do you do?"),
            // 3:
            new Text("You hit the wall"),
            // 4:
            new Text("You dare a step"),
            // 5:
            new Text("What has roots as nobody sees,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 6)}),
            // 6:
            new Text("Is taller than trees,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 7)}),
            // 7:
            new Text("Up, up it goes,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 8)}),
            // 8:
            new Text("And yet never grows?", new TextOption[]{
                    new TextOption(KeyEvent.VK_1, "A Dwarf", 9),
                    new TextOption(KeyEvent.VK_2, "A Mountain", 10),
                    new TextOption(KeyEvent.VK_3, "A Mind", 9),
                    new TextOption(KeyEvent.VK_4, "A Prime Number", 9)}),
            // 9:
            new Text("You fool!"),
            // 10:
            new Text("Very wise, here is your reward.")
    };

    /* StandardTextoptions:
    * The standardtextoptions will mainly be based on the players position
    * and are not all available at the same time
    * if the player is near an enemy it will say attack but it might also be possible
    * to show only items that the player has in his inventory
    * The selection which options the player currently has is made in getStandardOptions */

    private TextOption[] standardTextOptions = {
            new TextOption(KeyEvent.VK_W, "Go North", "north"),
            new TextOption(KeyEvent.VK_A, "Go West", "west"),
            new TextOption(KeyEvent.VK_D, "Go East", "east"),
            new TextOption(KeyEvent.VK_S, "Go South", "south"),
            new TextOption(KeyEvent.VK_ENTER, "Talk", "talk")

            // note: in this example talk wouldn't need an action but could
            // instead just lead to text 5 (new TextOption(KeyEvent.VK_ENTER, "Talk", 5))
            // however if more wise men are added
            // it will need to be checked which one are you talking to
            // also: it it should only be possible to try a riddle once
            // which will need to be checked
    };


    /*************************************************
     init
     ************************************************/

    public SampleState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        currentText = 0;
        options = new LinkedList<>();
        options.add(new TextOption(KeyEvent.VK_ENTER, "Continue", 1));
    }


    /*************************************************
     Helper
     ************************************************/

    private void getStandardOptions(LinkedList<TextOption> options) {
        options.clear();
        switch (labyrinth[playerX][playerY]) {
            case 0:
                options.add(standardTextOptions[0]);
                options.add(standardTextOptions[1]);
                options.add(standardTextOptions[2]);
                options.add(standardTextOptions[3]);
                break;
            case 1:
                options.add(standardTextOptions[0]);
                options.add(standardTextOptions[1]);
                options.add(standardTextOptions[2]);
                options.add(standardTextOptions[3]);
                options.add(standardTextOptions[4]);
                break;
        }
    }

    private void doAction(String action) {
        switch (action) {
            case "north":
                if (labyrinth[playerX][playerY - 1] != 1) {
                    currentText = 4;
                    playerY--;
                } else {
                    currentText = 3;
                }
                getStandardOptions(options);
                break;
            case "west":
                if (labyrinth[playerX - 1][playerY] != 1) {
                    currentText = 4;
                    playerX--;
                } else {
                    currentText = 3;
                }
                getStandardOptions(options);
                break;
            case "east":
                if (labyrinth[playerX + 1][playerY] != 1) {
                    currentText = 4;
                    playerX++;
                } else {
                    currentText = 3;
                }
                getStandardOptions(options);
                break;
            case "south":
                if (labyrinth[playerX][playerY + 1] != 1) {
                    currentText = 4;
                    playerY++;
                } else {
                    currentText = 3;
                }
                getStandardOptions(options);
                break;
            case "talk":
                currentText = 5;

                options.clear();

                options.addAll(Arrays.asList(texts[5].getOptions()));
                break;
        }
    }


    /*************************************************
     Output
     ************************************************/

    public void draw(Graphics2D g) {
        //Background:
        g.fillRect(0, 0, 192, 160);

        //Text:
        g.setFont(textFont);
        g.setColor(Color.white);
        g.drawString(texts[currentText].getText(), 15, 22);

        //Textframe:
        g.setColor(Color.gray);
        g.drawRect(10, 10, 172, 30);

        //Options:
        g.setColor(Color.white);
        int optionYPosition = 62;

        for (TextOption option : options) {
            g.drawString(option.text, 15, optionYPosition);
            optionYPosition += 15;
        }

        //Optionsframe:
        g.setColor(Color.gray);
        g.drawRect(10, 50, 70, 100);

        //Labyrinth:
        int labyrinthXPos = 90;
        int labyrinthYPos = 50;

        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[0].length; j++) {
                if (labyrinth[i][j] == 1) {
                    g.setColor(Color.gray);
                    g.fillRect(labyrinthXPos + i * 5, labyrinthYPos + j * 5, 5, 5);

                }
                if (labyrinth[i][j] == 3) {
                    g.setColor(Color.yellow);
                    g.fillOval(labyrinthXPos + i * 5 + 1, labyrinthYPos + i * 5 + 1, 3, 3);
                }
            }
        }

        //Player:
        g.setColor(Color.red);
        g.fillOval(labyrinthXPos + playerX * 5 + 1, labyrinthYPos + playerY * 5 + 1, 3, 3);
    }


    /*************************************************
     Inputs
     ************************************************/

    public void keyPressed(int k) {
    }

    public void keyReleased(int k) {
        boolean replaceOptions = false;
        LinkedList<TextOption> tempoptions = new LinkedList<>();

        for (TextOption option : options) {
            if (option.key == k) {

                if (option.next != 0) {
                    currentText = option.next;

                    if (texts[option.next].getOptions() != null) {
                        Collections.addAll(tempoptions, texts[option.next].getOptions());
                    } else {
                        getStandardOptions(tempoptions);
                    }
                    replaceOptions = true;
                } else {
                    doAction(option.action);
                    break;
                }
            }
        }
        if (replaceOptions) {
            options.clear();
            options = tempoptions;
        }
    }
}
