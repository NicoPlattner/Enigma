import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class PlayState extends GameState {

    //Gameobjects:
    private Player player;
    private Labyrinth lab = new Labyrinth(9, 9);
    private int[][] labyrinth = lab.getLabyrinth();
    private GameCharacter[][] gameCharacters = lab.getGameCharacters();
    private FieldOfVision fieldOfVision = new FieldOfVision();//AZ T1
    private boolean gameover = false;
    private boolean gamewon = false;

    //Textvariables
    private Text currentText = new Text("You wake up in a cave", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1)});
    private LinkedList<TextOption> options;
    private String temptext = "";
    private boolean correctbuttonpress = true;

    //Drawvariables
    private Font font;
    private Font biggerfont;
    private BufferedImage gui_background;
    private BufferedImage current_button;
    private BufferedImage health_icon;
    private BufferedImage enemy_health_icon;
    private BufferedImage endscreen;
    private BufferedImage door;
    private boolean inCombat = false;

    //Colors:
    private Color darkgreen = Color.decode("#063619");
    private Color beige = Color.decode("#827c70");
    private Color darkblue = Color.decode("#051155");
    private Color red = Color.decode("#fe3b1e");
    private Color lightpurple = Color.decode("#e61cf7");

    //Compassneedle
    private BufferedImage needle;


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

    // !!! IMPORTANT !!! only add texts to the end of the array and don't delete or change texts

    private Text[] texts = {
            // 0:
            new Text("You wake up in a cave", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1)}),
            // 1:
            new Text("You look around but you can't see anything", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 2)}),
            // 2:
            new Text("What do you do?"),
            // 3:
            new Text("You shall not have the right way from me."),
            // 4:
            new Text(new String[]{
                    "You dare a step",
                    "You make a big leap forward",
                    "A little slow but you`re getting there",
                    "Another step closer to the end",
                    "Without fear you advance in the labyrinth"
            }),
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
            new Text(new String[]{
                    "You mistakenly bumped against the wall",
                    "You took a wrong turn and met the wall",
                    "Wrong move now you shall meet the wall"
            }),
            // 10:
            new Text("Very wise, here is your reward"),
            // 11:
            new Text(new String[]{
                    "You mistook your imagination with reality and crashed once again.",
                    "Once again you hit the wall. Be more careful.",
                    "Whoops the wall is sure not the right way"
            }),
            // 12:
            new Text(new String[]{
                    "You really love walls don't you? Well they don't love you back. You took 1 damage.",
                    "You ran at maximum speed against the wall. Well done. You took 1 damage.",
                    "You slipped and hit your head against the wall. You took 1 damage.",
                    "You are at the wrong time on the wrong wall. You took 1 damage.",
                    "The wall you face seems to menacing. You took 1 damage out of fear."
            }),
            // 13:
            new Text("Well maybe next time."),
            // 14:
            new Text(new String[]{
                    "You deal 3 damage",
                    "You hit him"
            }),
            // 15:
            new Text("He hits you back", new TextOption[]{
                    new TextOption(KeyEvent.VK_1, "Stab", "stab"),
                    new TextOption(KeyEvent.VK_2, "Shoot", "shoot")})
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
    };


    /*************************************************
     init
     ************************************************/

    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        player = new Player(1, 1);
        player.addItem(new Item("Armor", "imgs/items/armor1.png", "setDefense", 5));
        player.addItem(new Item("Sword", "imgs/items/sword1.png", "setAttack", 5));
        player.addItem(new Item("Armor", "imgs/items/armor3.png", "setDefense", 5));


        ImageHandler imgHandler = new ImageHandler();
        gui_background = imgHandler.loadImage("imgs/gui_background.jpg");
        needle = imgHandler.loadImage("imgs/needle_south.png");
        health_icon = imgHandler.loadImage("imgs/health_icon.png");
        enemy_health_icon = imgHandler.loadImage("imgs/enemy_health_icon.png");
        endscreen = imgHandler.loadImage("imgs/end_screen.png");
        door = imgHandler.loadImage("imgs/door.png");
        options = new LinkedList<>();
        options.add(new TextOption(KeyEvent.VK_ENTER, "Continue", 1));


        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("pkmndp.ttf"));
            font = font.deriveFont(10F);
            biggerfont = font.deriveFont(15F);

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (Exception e) {
            System.out.println("Error when loading Font");
        }
    }


    /*************************************************
     Helper
     ************************************************/

    private void getStandardOptions(LinkedList<TextOption> options) {
        options.clear();

        options.add(standardTextOptions[0]);
        options.add(standardTextOptions[1]);
        options.add(standardTextOptions[2]);
        options.add(standardTextOptions[3]);

        if (gameCharacters[player.getxPosition()][player.getyPosition()] != null) {
            options.add(standardTextOptions[4]);
        }

    }

    private void doAction(String action) {
        ImageHandler imgHandler = new ImageHandler();

        switch (action) {
            case "north":
                if (labyrinth[player.getxPosition()][player.getyPosition() - 1] != 1) {
                    currentText = texts[4];
                    player.move("north");
                    player.setAnnoyanceToZero();
                } else {
                    walkIntoWall();
                    player.setCurrent_view_of_player("north");
                }
                needle = imgHandler.loadImage("imgs/needle_north.png");
                getStandardOptions(options);

                if (gameCharacters[player.getxPosition()][player.getyPosition()] != null) {
                    if (gameCharacters[player.getxPosition()][player.getyPosition()].getClass() == Orc.class) {
                        doAction("talk");
                    }
                }

                if (player.getxPosition() == 17 && player.getyPosition() == 17) {
                    gamewon = true;
                }
                break;
            case "west":
                if (labyrinth[player.getxPosition() - 1][player.getyPosition()] != 1) {
                    currentText = texts[4];
                    player.move("west");
                    player.setAnnoyanceToZero();
                } else {
                    walkIntoWall();
                    player.setCurrent_view_of_player("west");
                }
                needle = imgHandler.loadImage("imgs/needle_east.png");
                getStandardOptions(options);

                if (gameCharacters[player.getxPosition()][player.getyPosition()] != null) {
                    if (gameCharacters[player.getxPosition()][player.getyPosition()].getClass() == Orc.class) {
                        doAction("talk");
                    }
                }

                if (player.getxPosition() == 17 && player.getyPosition() == 17) {
                    gamewon = true;
                }
                break;
            case "east":
                if (labyrinth[player.getxPosition() + 1][player.getyPosition()] != 1) {
                    currentText = texts[4];
                    player.move("east");
                    player.setAnnoyanceToZero();
                } else {
                    walkIntoWall();
                    player.setCurrent_view_of_player("east");
                }
                needle = imgHandler.loadImage("imgs/needle_west.png");
                getStandardOptions(options);

                if (gameCharacters[player.getxPosition()][player.getyPosition()] != null) {
                    if (gameCharacters[player.getxPosition()][player.getyPosition()].getClass() == Orc.class) {
                        doAction("talk");
                    }
                }

                if (player.getxPosition() == 17 && player.getyPosition() == 17) {
                    gamewon = true;
                }
                break;
            case "south":
                if (labyrinth[player.getxPosition()][player.getyPosition() + 1] != 1) {
                    currentText = texts[4];
                    player.move("south");
                    player.setAnnoyanceToZero();
                } else {
                    walkIntoWall();
                    player.setCurrent_view_of_player("south");
                }

                needle = imgHandler.loadImage("imgs/needle_south.png");
                getStandardOptions(options);

                if (gameCharacters[player.getxPosition()][player.getyPosition()] != null) {
                    if (gameCharacters[player.getxPosition()][player.getyPosition()].getClass() == Orc.class) {
                        doAction("talk");
                    }
                }

                if (player.getxPosition() == 17 && player.getyPosition() == 17) {
                    gamewon = true;
                }
                break;
            case "talk":
                currentText = gameCharacters[player.getxPosition()][player.getyPosition()].getText(1, 0);

                options.clear();
                if (gameCharacters[player.getxPosition()][player.getyPosition()].getText(1, 0).getOptions() != null) {
                    options.addAll(Arrays.asList(gameCharacters[player.getxPosition()][player.getyPosition()].getText(1, 0).getOptions()));
                } else {
                    getStandardOptions(options);
                }

                if (gameCharacters[player.getxPosition()][player.getyPosition()].getClass() == Orc.class) {
                    inCombat = true;
                }

                break;
            case "solveRiddle":
                WisePerson wisePerson = (WisePerson) gameCharacters[player.getxPosition()][player.getyPosition()];
                wisePerson.solveLabyrinth(labyrinth, player.getxPosition(), player.getyPosition());
                gameCharacters[player.getxPosition()][player.getyPosition()] = wisePerson;

                currentText = new Text("I sense you have to go " + wisePerson.getDirection() + " and then " + wisePerson.getFirstTurn() + ".");

                player.changeHealthby(6);
                if (player.getHealth() > 30) {
                    player.changeHealthby(30 - player.getHealth());
                }

                gameCharacters[player.getxPosition()][player.getyPosition()].setState(2);
                getStandardOptions(options);
                break;
            case "failRiddle":
                currentText = texts[3];

                gameCharacters[player.getxPosition()][player.getyPosition()].setState(1);
                getStandardOptions(options);
                break;
            case "stab":
                int strength = 5 - gameCharacters[player.getxPosition()][player.getyPosition()].getDefense();
                gameCharacters[player.getxPosition()][player.getyPosition()].adjustHealth(-strength);

                if (gameCharacters[player.getxPosition()][player.getyPosition()].getHealth() <= 0) {
                    gameCharacters[player.getxPosition()][player.getyPosition()] = null;

                    currentText = new Text("You defeated the Orc!");
                    inCombat = false;

                    getStandardOptions(options);
                } else {
                    if (gameCharacters[player.getxPosition()][player.getyPosition()].getDefense() <= 0) {
                        currentText = new Text(new String[]{
                                "Fearlessly you attack him, dealing " + strength + " damage",
                                "You found his weak spot and deal " + strength + " damage",
                                "You almost missed but manage to deal " + strength + " damage"
                        });
                    } else {
                        currentText = new Text(new String[]{
                                "Fearlessly you attack him, dealing " + strength + " damage and lower his defense",
                                "You found his weak spot and deal " + strength + " damage and lower his defense",
                                "You almost missed but manage to deal " + strength + " damage and lower his defense"
                        });
                    }

                    gameCharacters[player.getxPosition()][player.getyPosition()].adjustdefense(-1);
                    options.clear();
                    options.add(new TextOption(KeyEvent.VK_ENTER, "Continue", "enemyattack"));
                }
                break;
            case "shoot":
                int attack = 6 - gameCharacters[player.getxPosition()][player.getyPosition()].getDefense();
                gameCharacters[player.getxPosition()][player.getyPosition()].adjustHealth(-attack);

                if (gameCharacters[player.getxPosition()][player.getyPosition()].getHealth() <= 0) {
                    gameCharacters[player.getxPosition()][player.getyPosition()] = null;

                    currentText = new Text("You defeated the Orc!");
                    inCombat = false;

                    getStandardOptions(options);
                } else {
                    currentText = new Text(new String[]{
                            "Shooting your bow you deal " + attack + " damage",
                            "You were always a great archer. You hit him and he takes " + attack + " damage",
                            "Not your best shot but you manage to inflict " + attack + " damage"
                    });

                    options.clear();
                    options.add(new TextOption(KeyEvent.VK_ENTER, "Continue", "enemyattack"));
                }
                break;
            case "enemyattack":
                Random random = new Random();
                int damage = random.nextInt(3) + 1;

                currentText = new Text(new String[]{
                        "He hits you with his dagger, dealing " + damage + " damage",
                        "With all his power he attacks you, dealing " + damage + " damage",
                        "He got angry and strikes back, dealing " + damage + " damage",
                        "Furiosly he takes his dagger and hits you. You take " + damage + " damage"
                });

                player.changeHealthby(-damage);

                if (player.getHealth() <= 0) {
                    gameover = true;
                }

                options.clear();
                options.add(new TextOption(KeyEvent.VK_1, "Stab", "stab"));
                options.add(new TextOption(KeyEvent.VK_2, "Shoot", "shoot"));
                break;
        }
    }

    private void doAction(String action, int parameter) {
        switch (action) {
            case "changeState":

                break;
        }
    }

    private void walkIntoWall() {
        switch (player.getPlayer_annoyed()) {
            case 0:
                currentText = texts[9];
                break;
            case 1:
                currentText = texts[11];
                break;
            case 2:
                player.changeHealthby(-1);
                currentText = texts[12];
                break;
        }
        if (player.getHealth() <= 0) {
            gameover = true;
        }

        player.increase_annoyance();
    }


    /*************************************************
     Output
     ************************************************/

    public void draw(Graphics2D g) {
        //Background:
        g.fillRect(0, 0, 192, 160);
        g.drawImage(gui_background, 0, 0, null);

        //Text:
        g.setFont(font);
        g.setColor(Color.black);

        if (!gameover && correctbuttonpress) {
            temptext = currentText.getText();
        }
        drawParagraph(g, temptext, 170);

        //Options:
        g.setColor(Color.black);
        int optionYPosition = 58;
        int optionXPosition = 24;

        for (TextOption option : options) {
            //Key-Icons:
            ImageHandler imgHandler = new ImageHandler();
            current_button = imgHandler.loadImage("imgs/buttons/" + KeyEvent.getKeyText(option.key) + "_button.png");
            g.drawImage(current_button, optionXPosition - current_button.getWidth(), optionYPosition - 9, null);

            //Option:
            g.drawString(option.text, optionXPosition + 7, optionYPosition);
            optionYPosition += 14;
        }

        //Labyrinth:
        int labyrinthXPos = 109;
        int labyrinthYPos = 76;
        int wallsize = 4;

        g.setColor(beige);
        g.fillRect(labyrinthXPos, labyrinthYPos, wallsize * labyrinth.length, wallsize * labyrinth[0].length);

        g.setColor(darkgreen);
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[0].length; j++) {
                if (labyrinth[i][j] == 1) {
                    g.fillRect(labyrinthXPos + i * wallsize, labyrinthYPos + j * wallsize, 4, 4);
                }
            }
        }

        //Game Characters:
        for (int i = 0; i < gameCharacters.length; i++) {
            for (int j = 0; j < gameCharacters[0].length; j++) {
                if (gameCharacters[i][j] != null) {
                    g.drawImage(gameCharacters[i][j].getImage(), labyrinthXPos + i * wallsize, labyrinthYPos + j * wallsize, null);
                }
            }
        }

        g.drawImage(door, labyrinthXPos + 17 * 4, labyrinthYPos + 17 * 4, null);
        g.drawImage(needle, 157, 41, null);

        //Player:
        g.drawImage(player.getPlayerImage(), labyrinthXPos + player.getxPosition() * wallsize, labyrinthYPos + player.getyPosition() * wallsize, null);
        g.setColor(red);
        g.fillRect(120, 45, (int) (player.getHealth() * 1.2F), 5);
        g.fillRect(120, 46, (int) (player.getHealth() * 1.2F) + 1, 3);
        g.drawImage(health_icon, 0, 0, null);
        fieldOfVision.FillVision(player, labyrinth);// AT T1
        fieldOfVision.Draw(g, wallsize, labyrinthXPos, labyrinthYPos, darkblue);// AZ T1

        //Enemy Health:
        if (inCombat) {
            g.setColor(lightpurple);
            g.fillRect(120, 60, (int) (gameCharacters[player.getxPosition()][player.getyPosition()].getHealth() * 1.82F), 5);
            g.fillRect(120, 61, (int) (gameCharacters[player.getxPosition()][player.getyPosition()].getHealth() * 1.82F) + 1, 3);
            g.drawImage(enemy_health_icon, 0, 0, null);
        }

        if (gameover) {
            g.drawImage(endscreen, 0, 0, null);
            g.setColor(Color.black);
            g.setFont(biggerfont);
            g.drawString("You lost!", 73, 64);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Press Enter to Return to the Menu", 30, 125);
        }
        if (gamewon) {
            g.drawImage(endscreen, 0, 0, null);
            g.setColor(Color.black);
            g.setFont(biggerfont);
            g.drawString("You won!", 73, 64);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Press Enter to Return to the Menu", 30, 125);
        }
    }

    void drawParagraph(Graphics2D g, String paragraph, float width) {
        AttributedString string = new AttributedString(paragraph);
        string.addAttribute(TextAttribute.FONT, font);
        LineBreakMeasurer linebreaker = new LineBreakMeasurer(string.getIterator(), g.getFontRenderContext());

        int y = 0;
        while (linebreaker.getPosition() < paragraph.length()) {
            TextLayout textLayout = linebreaker.nextLayout(width);


            textLayout.draw(g, 12, 19 + y * 1.2F);
            y += textLayout.getAscent();

            y += textLayout.getDescent() + textLayout.getLeading();
        }
    }


    /*************************************************
     Inputs
     ************************************************/

    public void keyPressed(int k) {
    }

    public void keyReleased(int k) {
        boolean replaceOptions = false;
        LinkedList<TextOption> tempoptions = new LinkedList<>();

        if (!gameover && !gamewon) {
            int count = 0;
            int optionssize = options.size();

            for (TextOption option : options) {
                if (option.key == k) {

                    if (option.nextLocation != 0) {
                        if (option.next >= 0) {
                            currentText = gameCharacters[player.getxPosition()][player.getyPosition()].getText(option.nextLocation, option.next);

                            Collections.addAll(tempoptions, gameCharacters[player.getxPosition()][player.getyPosition()].getText(option.nextLocation, option.next).getOptions());
                            replaceOptions = true;
                        } else {
                            doAction(option.action, option.getParameter());
                            break;
                        }
                    } else {
                        if (option.action == "") {
                            currentText = texts[option.next];

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
                } else {
                    count++;
                }
            }

            if (count >= optionssize) {
                correctbuttonpress = false;
            } else {
                correctbuttonpress = true;
            }

            if (replaceOptions) {
                options.clear();
                options = tempoptions;
            }
        } else if (k == KeyEvent.VK_ENTER) {
            gsm.gameStates.pop();
        }
    }
}