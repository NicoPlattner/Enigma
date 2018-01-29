import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageHandler {

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(getClass().getResourceAsStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}

