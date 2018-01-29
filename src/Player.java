import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player {

    private int xPosition;
    private int yPosition;
    private int speed = 1;
    private int health = 30;
    private BufferedImage[] player_pics = new BufferedImage[4];
    private int current_view_of_player = 0;
    private int player_annoyed;
    private LinkedList<Item> inventory = new LinkedList<>();

    public Player(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        ImageHandler imageHandler = new ImageHandler();
        player_pics[0] = imageHandler.loadImage("imgs/characters/player_front.png");
        player_pics[1] = imageHandler.loadImage("imgs/characters/player_left.png");
        player_pics[2] = imageHandler.loadImage("imgs/characters/player_back.png");
        player_pics[3] = imageHandler.loadImage("imgs/characters/player_right.png");
    }


    /*************************************************
     get/set
     ************************************************/

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void move(String direction) {
        switch (direction) {
            case "north":
                yPosition -= speed;
                current_view_of_player = 2;
                break;
            case "west":
                xPosition -= speed;
                current_view_of_player = 1;
                break;
            case "south":
                yPosition += speed;
                current_view_of_player = 0;
                break;
            case "east":
                xPosition += speed;
                current_view_of_player = 3;
                break;
        }
    }

    public BufferedImage getPlayerImage() {
        return player_pics[current_view_of_player];
    }

    public void setCurrent_view_of_player(String direction) {
        switch (direction) {
            case "north":
                current_view_of_player = 2;
                break;
            case "west":
                current_view_of_player = 1;
                break;
            case "south":
                current_view_of_player = 0;
                break;
            case "east":
                current_view_of_player = 3;
                break;
        }
    }

    public void increase_annoyance() {
        if (player_annoyed < 2) {
            player_annoyed++;
        }
    }

    public int getPlayer_annoyed() {
        return player_annoyed;
    }

    public void setAnnoyanceToZero() {
        player_annoyed = 0;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealthby(int amount) {
        health += amount;
    }

    public void addItem(Item item){
        inventory.push(item);
    }

    public LinkedList<Item> getInventory() {
        return inventory;
    }
}
