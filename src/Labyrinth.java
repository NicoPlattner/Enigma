import java.util.LinkedList;
import java.util.Random;

public class Labyrinth {

    private class Wall {
        int XPosition, YPosition;

        private Wall(int XPosition, int YPosition) {
            this.XPosition = XPosition;
            this.YPosition = YPosition;
        }
    }

    private int[][] labyrinth;
    private GameCharacter[][] gameCharacters;
    private Random randomgenerator = new Random();
    private int seed = randomgenerator.nextInt();

    // unassigned = 0
    int empty_space = 1;
    int wall = 2;


    /*************************************************
     init
     ************************************************/

    public Labyrinth(int width, int height) {
        labyrinth = new int[width * 2 + 1][height * 2 + 1];
        gameCharacters = new GameCharacter[width * 2 + 1][height * 2 + 1];

        if (seed < 0) {
            seed *= -1;
        }
        createLabyrinth();
        changeToOldNumbers();
        fillLabyrinth();
    }


    private void createLabyrinth() {
        /*
        Maze Generation using the Randomized Prim's Algorithm:

        Explanation on Wikipedia: https://en.wikipedia.org/wiki/Maze_generation_algorithm
        1.  Start with a grid full of walls.
        2.  Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
        3.  While there are walls in the list:
            1.  Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
                1.  Make the wall a passage and mark the unvisited cell as part of the maze.
                2.  Add the neighboring walls of the cell to the wall list.
            2.  Remove the wall from the list.


        Labyrinth at the beginning of the algorithm:
            e ... edge (wall)
            . ... empty space(stays empty)
            x ... either edge or space

            eeeeeeeee
            e.x.x.x.x
            exexexexe
            e.x.x.x.x
            exexexexe

            (to see the labyrinth at the beginning just delete the while loop under create other walls
            or comment it out)
        */

        LinkedList<Wall> wallList = new LinkedList<>();

        //create fixed walls
        for (int i = 0; i < labyrinth.length; i++) {
            labyrinth[i][0] = wall;
            labyrinth[i][labyrinth[0].length - 1] = wall;
        }

        for (int i = 0; i < labyrinth[0].length; i++) {
            labyrinth[0][i] = wall;
            labyrinth[labyrinth.length - 1][i] = wall;
        }

        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[0].length; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    labyrinth[i][j] = wall;
                }
            }
        }

        //create other walls
        labyrinth[1][1] = empty_space;
        wallList.add(new Wall(2, 1));
        wallList.add(new Wall(1, 2));

        int count = 2;
        while (!wallList.isEmpty()) {
            Wall currentwall = wallList.get(seed % wallList.size());
            int currentx = currentwall.XPosition;
            int currenty = currentwall.YPosition;

            // checks if 2 adjacent opposing cells aren't equal
            // Note: 2 must be walls at fixed positions and are equal
            // and one is an empty space otherwise the wall wouldn't have been added to the list
            // so this checks if opposite of the empty space is another empty space or a not yet assigned cell
            if ((labyrinth[currentx - 1][currenty] == labyrinth[currentx + 1][currenty]) != (labyrinth[currentx][currenty - 1] == labyrinth[currentx][currenty + 1])) {
                labyrinth[currentx][currenty] = empty_space;
                if (labyrinth[currentx - 1][currenty] == 0) {
                    expandTo("left", currentx - 1, currenty, wallList);
                } else if (labyrinth[currentx + 1][currenty] == 0) {
                    expandTo("right", currentx + 1, currenty, wallList);
                } else if (labyrinth[currentx][currenty - 1] == 0) {
                    expandTo("up", currentx, currenty - 1, wallList);
                } else if (labyrinth[currentx][currenty + 1] == 0) {
                    expandTo("down", currentx, currenty + 1, wallList);
                }
            }
            wallList.remove(currentwall);
            count = count * 2 + 1;
        }
    }

    private void expandTo(String direction, int xposition, int yposition, LinkedList<Wall> wallList) {
        labyrinth[xposition][yposition] = empty_space;

        if (direction != "right" && xposition > 2) {
            wallList.add(new Wall(xposition - 1, yposition));
        }
        if (direction != "left" && xposition < labyrinth.length - 2) {
            wallList.add(new Wall(xposition + 1, yposition));
        }
        if (direction != "up" && yposition < labyrinth[0].length - 2) {
            wallList.add(new Wall(xposition, yposition + 1));
        }
        if (direction != "down" && yposition > 2) {
            wallList.add(new Wall(xposition, yposition - 1));
        }
    }

    private void changeToOldNumbers() {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[0].length; j++) {
                labyrinth[i][j]--;
            }
        }
    }

    private void fillLabyrinth() {
        Random random = new Random();
        int count = 0;

        while (count <= 13) {
            int randomX = random.nextInt(labyrinth.length / 2);
            int randomY = random.nextInt(labyrinth[0].length / 2);
            int randomCharacter = random.nextInt(2) + 2;

            if (labyrinth[randomX * 2 + 1][randomY * 2 + 1] == 0 && (randomX != 0 && randomY != 0)  && (randomX != 8 && randomY != 8)) {
                switch (randomCharacter){
                    case 2:
                        gameCharacters[randomX * 2 + 1][randomY * 2 + 1] = new WisePerson();
                        break;
                    case 3:
                        gameCharacters[randomX * 2 + 1][randomY * 2 + 1] = new Orc();
                        break;
                }
                count++;
            }
        }
    }


    /*************************************************
     get/set
     ************************************************/

    public int[][] getLabyrinth() {
        return labyrinth;
    }

    public GameCharacter[][] getGameCharacters() {
        return gameCharacters;
    }
}
