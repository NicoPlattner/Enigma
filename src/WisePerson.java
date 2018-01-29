import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class WisePerson extends GameCharacter {

    //state 0: haven't done riddle yet
    //state 1: riddle done
    private int state = 0;
    private Random random = new Random();
    private int numberOfText = 2 + random.nextInt(15);
    private String direction;
    private String firstTurn;
    private int[][] knowledgeOfLab = new int[19][19];

    //picture:
    ImageHandler imageHandler = new ImageHandler();
    //change for different image:
    private BufferedImage image = imageHandler.loadImage("imgs/characters/wise_person.png");

    private Text[][] texts = {
            {
                    // texts[0, x] returns to text x in playstate
            },
            {
                    // 1, 0:
                    new Text("Adventurer listen to me!", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1, 1)}),
                    // 1, 1:
                    new Text("To further be successful in your travels you will need my help.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1, 2)}),
                    // 1, 2:
                    new Text("You shall only receive it if you can answer my riddle.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1, 3)}),
                    // 1, 3:
                    new Text("Are you ready?", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 1, 4), new TextOption(KeyEvent.VK_A, "I can't", 0, 13)}),
                    // 1 ,4:
                    new Text("Very well, now listen closely:", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", numberOfText, 0)}),
            },
            {
                    // 2, 0:
                    new Text("Tall I am young,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 2, 1)}),
                    // 2, 1:
                    new Text("Short I am old,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 2, 2)}),
                    // 2, 2:
                    new Text("While with life I glow, Wind is my foe.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 2, 3)}),
                    // 2, 3:
                    new Text("What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Book", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Tree", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Flag", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Candle", "solveRiddle")}),
            },
            {
                    // 3, 0:
                    new Text("Who makes it, has no need of it,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 3, 1)}),
                    // 3, 1:
                    new Text("Who buys it, has no use for it.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 3, 2)}),
                    // 3, 2:
                    new Text("Who uses it can neither see nor feel it.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 3, 3)}),
                    // 3, 3:
                    new Text("What is it?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Coffin", "solveRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Gift", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "John Cena", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Window", "failRiddle")}),
            },
            {
                    // 4, 0:
                    new Text("It has an eye but can not see,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 4, 1)}),
                    // 4, 1:
                    new Text("Is small but powerful indeed.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 4, 2)}),
                    // 4, 2:
                    new Text("What is it?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Pirate", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Bat", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Hurricane", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Needle", "solveRiddle")}),
            },
            {
                    // 5, 0:
                    new Text("If you have me, you want to share me,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 5, 1)}),
                    // 5, 1:
                    new Text("If you share me, you haven't got me.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 5, 2)}),
                    // 5, 2:
                    new Text("What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Story", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Problem", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Secret", "solveRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Meal", "failRiddle")}),
            },
            {
                    // 6, 0:
                    new Text("What name has the creature,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 6, 1)}),
                    // 6, 1:
                    new Text("that has an armour that makes no sound,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 6, 2)}),
                    // 6, 2:
                    new Text("that drinks it's whole life and if it leaves it's element it dies.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 6, 3)}),
                    // 6, 3:
                    new Text("What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Drunk", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Well", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Knight", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Fish", "solveRiddle")}),
            },
            {
                    // 7, 0:
                    new Text("32 whites on a red path, they stomp they malm and wait once again.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 7, 1)}),
                    // 7, 1:
                    new Text("What are they?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Teeth", "solveRiddle"),
                            new TextOption(KeyEvent.VK_2, "Stones", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "Golems", "failRiddle")}),
            },
            {
                    // 8, 0:
                    new Text("it determines your birth, it determines your death,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 8, 1)}),
                    // 8, 1:
                    new Text("it follows you your whole life and exists before and after.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 8, 2)}),
                    // 8, 2:
                    new Text("it is the reason for all changes all around.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 8, 3)}),
                    // 8, 3:
                    new Text("What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Existance", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "Time", "solveRiddle"),
                            new TextOption(KeyEvent.VK_3, "well...", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "Feelings", "failRiddle")}),
            },
            {
                    // 9, 0:
                    new Text("Which creature walks at birth with four,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 9, 1)}),
                    // 9, 1:
                    new Text("Then with two and in the end with three legs?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Mutant", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Table", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Human", "solveRiddle")}),
            },
            {
                    // 10, 0:
                    new Text("I stay with you all the day but not at night. What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Your Legs", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Friend", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "Your Shadow", "solveRiddle"),
                            new TextOption(KeyEvent.VK_4, "Your eyes", "failRiddle")}),
            },
            {
                    // 11, 0:
                    new Text("I gurgle but never speak, run but never walk,", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 11, 1)}),
                    // 11, 1:
                    new Text("have a bed but never sleep. What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Runner", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "Fire", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Vampire", "failRiddle"),
                            new TextOption(KeyEvent.VK_4, "A River", "solveRiddle")}),
            },
            {
                    // 12, 0:
                    new Text("I am not alive but I can grow.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 12, 1)}),
                    // 12, 1:
                    new Text("I do not have lungs but I need air to survive.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 12, 2)}),
                    // 12, 2:
                    new Text("What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Fire", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Fish", "solveRiddle"),
                            new TextOption(KeyEvent.VK_3, "Rocks", "failRiddle")}),
            },
            {
                    // 13, 0:
                    new Text("I have an eye but cannot see.", new TextOption[]{new TextOption(KeyEvent.VK_ENTER, "Continue", 13, 1)}),
                    // 13, 1:
                    new Text("I am fast but I have no limbs. What am I?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Cyclops", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Bat", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Hurricane", "solveRiddle")}),
            },
            {
                    // 14, 0:
                    new Text("It weighs next to nothing but no one can hold it for long. What is it?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Money", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "A Thought", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "Breath", "solveRiddle"),
                            new TextOption(KeyEvent.VK_4, "A Speech", "failRiddle")}),
            },
            {
                    // 15, 0:
                    new Text("If your aunt’s brother is not your uncle, who is he?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Your Mother", "failRiddle"),
                            new TextOption(KeyEvent.VK_2, "Your Father", "solveRiddle"),
                            new TextOption(KeyEvent.VK_3, "Adopted", "failRiddle")}),
            },
            {
                    // 16, 0:
                    new Text("What can you catch but never throw?", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "A Cold", "solveRiddle"),
                            new TextOption(KeyEvent.VK_2, "Light", "failRiddle"),
                            new TextOption(KeyEvent.VK_3, "A Thought", "failRiddle")}),
            },
    };


    /*************************************************
     Helper
     ************************************************/

    // Maze Solving Algorithm (Depth First Search)
    public void solveLabyrinth(int[][] labyrinth, int xPosOfStart, int yPosOfStart) {
        knowledgeOfLab = getShortestPath(knowledgeOfLab, labyrinth, xPosOfStart, yPosOfStart);

        if (knowledgeOfLab[xPosOfStart + 1][yPosOfStart] == 1) {
            direction = "East";
        }
        if (knowledgeOfLab[xPosOfStart][yPosOfStart + 1] == 1) {
            direction = "South";
        }
        if (knowledgeOfLab[xPosOfStart - 1][yPosOfStart] == 1) {
            direction = "West";
        }
        if (knowledgeOfLab[xPosOfStart][yPosOfStart - 1] == 1) {
            direction = "North";
        }

        int deltaX = 0;
        int deltaY = 0;

        switch (direction) {
            case "East":
                deltaX = 1;
                break;
            case "South":
                deltaY = 1;
                break;
            case "West":
                deltaX = -1;
                break;
            case "North":
                deltaY = -1;
                break;
        }

        int tempX = xPosOfStart;
        int tempY = yPosOfStart;

        while (knowledgeOfLab[tempX + deltaX][tempY + deltaY] == 1) {
            tempX += deltaX;
            tempY += deltaY;
        }

        int normalX = deltaX != 0 ? 0 : 1;
        int normalY = deltaY != 0 ? 0 : 1;

        if (normalX != 0) {
            if (knowledgeOfLab[tempX + normalX][tempY] == 1) {
                firstTurn = "East";
            } else if (knowledgeOfLab[tempX - normalX][tempY] == 1) {
                firstTurn = "West";
            }
        } else if (normalY != 0) {
            if (knowledgeOfLab[tempX][tempY + normalY] == 1) {
                firstTurn = "South";
            } else if (knowledgeOfLab[tempX][tempY - normalY] == 1) {
                firstTurn = "North";
            }
        }
    }


    private int[][] getShortestPath(int[][] shortestPath, int[][] labyrinth, int currentX, int currentY) {
        shortestPath[currentX][currentY] = 1;

        if (shortestPath[17][17] == 1) {
            return shortestPath;
        }

        if (shortestPath[currentX + 1][currentY] == 0 && labyrinth[currentX + 1][currentY] != 1) {
            shortestPath = getShortestPath(shortestPath, labyrinth, currentX + 1, currentY);

            if (shortestPath[17][17] == 1) {
                return shortestPath;
            }
        }
        if (shortestPath[currentX][currentY + 1] == 0 && labyrinth[currentX][currentY + 1] != 1) {
            shortestPath = getShortestPath(shortestPath, labyrinth, currentX, currentY + 1);

            if (shortestPath[17][17] == 1) {
                return shortestPath;
            }
        }
        if (shortestPath[currentX - 1][currentY] == 0 && labyrinth[currentX - 1][currentY] != 1) {
            shortestPath = getShortestPath(shortestPath, labyrinth, currentX - 1, currentY);

            if (shortestPath[17][17] == 1) {
                return shortestPath;
            }
        }
        if (shortestPath[currentX][currentY - 1] == 0 && labyrinth[currentX][currentY - 1] != 1) {
            shortestPath = getShortestPath(shortestPath, labyrinth, currentX, currentY - 1);

            if (shortestPath[17][17] == 1) {
                return shortestPath;
            }
        }

        if (shortestPath[17][17] == 1) {
            return shortestPath;
        }

        shortestPath[currentX][currentY] = 0;
        return shortestPath;
    }


    /*************************************************
     get/set
     ************************************************/

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public String getFirstTurn() {
        return firstTurn;
    }

    public int getState() {
        return state;
    }

    // State 0: haven't talked
    // State 1: failed riddle
    // State 2: succeeded riddle
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public Text getText(int nextLocation, int nextText) {
        if (state == 0) {
            return texts[nextLocation][nextText];
        } else if (state == 1) {
            return new Text(new String[]{
                    "You already talked to me!",
                    "You only have one try!",
                    "Go to the next wise person",
                    "How are you my friend?"
            });
        } else {
            return new Text(new String[]{
                    ("I told you, go " + getDirection() + " and then " + firstTurn + "."),
                    ("The exit is to the " + getDirection() + " and then to the " + firstTurn + "."),
                    ("What are you still doing here? Go " + getDirection() + " and then " + firstTurn + "."),
                    ("If you're lost try going " + getDirection() + " and then " + firstTurn + ".")
            });
        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}