import java.awt.image.BufferedImage;

public class Item {

    ImageHandler imageHandler = new ImageHandler();

    private String name;
    private BufferedImage image;


    public Item(String name, String imageSource, String effect, int amount){
        this.name= name;

        image = imageHandler.loadImage(imageSource);
    }


    public BufferedImage getImage() {
        return image;
    }
}
