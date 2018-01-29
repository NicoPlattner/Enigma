import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Orc extends GameCharacter {

    private int state = 0;
    private int health = 20;
    private Random random = new Random();
    private int numberOfText = 2 + random.nextInt(15);
    private int defense = 3;

    //picture:
    ImageHandler imageHandler = new ImageHandler();
    //change for different image:
    private BufferedImage image = imageHandler.loadImage("imgs/characters/orc.png");

    private Text[][] texts = {
            {
                    // texts[0, x] returns to text x in playstate
            },
            {
                    // 1, 0:
                    new Text("Puny human you shall regret crossing my path!", new TextOption[]{
                            new TextOption(KeyEvent.VK_1, "Stab", "stab"),
                            new TextOption(KeyEvent.VK_2, "Shoot", "shoot")}),
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
    };


    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getState() {
        return super.getState();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void adjustHealth(int delta) {
        health += delta;
    }

    @Override
    public void adjustdefense(int delta) {
        defense += delta;

        if (defense < 0){
            defense = 0;
        }
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
            return new Text("error: Nico f....d up");
        }
    }
}
