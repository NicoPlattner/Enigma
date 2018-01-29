import java.awt.*;
import java.util.Stack;

public class GameStateManager {

    public Stack<GameState> gameStates;

    /* A Stack is a structure that gives access to its top element
       This is useful in our case because we can add "layers" to our project
       We can have a default state (Menustate) that is always at the bottom of our stack
       and still there if we close the other states then we can create for example
       a playingstate that is active now at the top, the menustate remains frozen in the meanwhile
       since we don't close it, and onto the playingstate we could add a pausestate and while
       the pausestate is active the game would be frozen and we could continue it just by
       closing our pausestate */

    public GameStateManager() {
        gameStates = new Stack<GameState>();
        gameStates.push(new MenuState(this));
    }

    public void changestate(int newstate) {
        /* The GSM can change the GameStates here
           If new GameStates are added they have to be added here as well.
           0 - MenuState (default state)
           1 - SampleState (the ability to access the SampleState can be
               removed here when it's not needed anymore)*/

        if (newstate == 0) {
            gameStates.pop();
        }
        if (newstate == 1) {
            gameStates.push(new PlayState(this));
        }
    }

    public void draw(Graphics2D g) {
        gameStates.peek().draw(g);
    }

    public void keyPressed(int k) {
        gameStates.peek().keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.peek().keyReleased(k);
    }
}