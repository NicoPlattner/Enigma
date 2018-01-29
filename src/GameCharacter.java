import java.awt.image.BufferedImage;

public abstract class GameCharacter {

    private int state;
    private int health;
    private int defense;
    Text text;
    BufferedImage image;

    public Text getText(int currentLocation, int currentText) {
        return text;
    }


    public BufferedImage getImage() {
        return image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void adjustHealth(int delta) {
        this.health += delta;
    }

    public void adjustdefense(int delta){
        defense += delta;

        if (defense<0){
            defense = 0;
        }
    }
}
