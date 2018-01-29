/*
_ to indicate member variables is easier to read

* */

import java.awt.*;
import java.awt.image.BufferedImage;

public class FieldOfVision {
    //left upper corner
    private int startX;
    private int startY;

    private int sizeX;
    private int sizeY;

    private ImageHandler imageHandler = new ImageHandler();
    private int fields[][];

    private BufferedImage border_left = imageHandler.loadImage("imgs/border_left.png");
    private BufferedImage border_right = imageHandler.loadImage("imgs/border_right.png");
    private BufferedImage border_up = imageHandler.loadImage("imgs/border_up.png");
    private BufferedImage border_down = imageHandler.loadImage("imgs/border_down.png");


    public FieldOfVision() {
        this(19, 19);
    }

    public FieldOfVision(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.fields = new int[this.sizeX][this.sizeY];
    }

    public int getStartX() {

        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    //player position min the  middle of the fields
    // left upper corner
    // copy data from labyrinth to local cache
    public void FillVision(Player player, int[][] labyrinth) {


        for (int i = player.getxPosition() - 1; i <= player.getxPosition() + 1; i++) {
            for (int j = player.getyPosition() - 1; j <= player.getyPosition() + 1; j++) {
                fields[i][j] = 1;
            }
        }
    }

    public void Draw(Graphics2D g, int wallsize, int labyrinthXPos, int labyrinthYPos, Color colorOfUnknown) {
        g.setColor(colorOfUnknown);

        for (int ix = 0; ix < fields.length; ix++) {
            for (int iy = 0; iy < fields[0].length; iy++) {
                if (this.fields[ix][iy] == 0) {
                    g.fillRect(labyrinthXPos + ix * wallsize, labyrinthYPos + iy * wallsize, 4, 4);

                    if (ix == 0) {
                        g.drawImage(border_left, labyrinthXPos + ix * wallsize - 1, labyrinthYPos + iy * wallsize, null);
                    } else if (fields[ix - 1][iy] == 1) {
                        g.drawImage(border_left, labyrinthXPos + ix * wallsize - 1, labyrinthYPos + iy * wallsize, null);
                    }

                    if (ix == fields.length - 1) {
                        g.drawImage(border_right, labyrinthXPos + ix * wallsize + 3, labyrinthYPos + iy * wallsize, null);
                    } else if (fields[ix + 1][iy] == 1) {
                        g.drawImage(border_right, labyrinthXPos + ix * wallsize + 3, labyrinthYPos + iy * wallsize, null);
                    }

                    if (iy == fields.length - 1) {
                        g.drawImage(border_down, labyrinthXPos + ix * wallsize, labyrinthYPos + iy * wallsize + 3, null);
                    } else if (fields[ix][iy + 1] == 1) {
                        g.drawImage(border_down, labyrinthXPos + ix * wallsize, labyrinthYPos + iy * wallsize + 3, null);
                    }

                    if (iy == 0) {
                        g.drawImage(border_up, labyrinthXPos + ix * wallsize, labyrinthYPos + iy * wallsize - 1, null);
                    } else if (fields[ix][iy - 1] == 1) {
                        g.drawImage(border_up, labyrinthXPos + ix * wallsize, labyrinthYPos + iy * wallsize - 1, null);
                    }
                }
            }
        }
    }
}
