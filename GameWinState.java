/*
 * Andrew Duryea
 * October 29, 2013
 * GameWinState.java
 *
 * An abstract class representing a state where the game is not being played, ex. game over, level over, etc.
 */

package Game;

public class GameWinState extends GameEndState{
    public GameWinState(Game g){
    	super(g);

    	this.titleString = "Congratulations, You Win!";

    	//menu.add(new MenuItem("quit", Game.WIDTH,Game.HEIGHT,120,30));

		int index = addMenu();

    	addMenuItem(index, new MenuItem("quit", Game.WIDTH,Game.HEIGHT,120,30));
    }

    public void submit(){
    	switch(selectedIndex){
    		case 0: //quit, pop back to menu
    			Game.playSound(Game.SOUND_M_CONFIRM);
    			game.popX(2);
    			break;
    		default:
    			break;
    	}
    }
}