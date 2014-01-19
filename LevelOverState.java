/*
 * Andrew Duryea
 * October 29, 2013
 * levelOverState.java
 *
 * Shows the final score for a level, a child of menuState.
 */

package Game;

public class LevelOverState extends GameEndState{

    public LevelOverState(Game g){
    	super(g);

		this.titleString = "Level Complete!";

    	//menu.add(new MenuItem("continue", Game.WIDTH,Game.HEIGHT,120,30));
    	//menu.add(new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));

		int index = addMenu();

    	addMenuItem(index, new MenuItem("continue", Game.WIDTH,Game.HEIGHT,120,30));
    	addMenuItem(index, new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));
    }

    public void submit(){
    	switch(selectedIndex){
    		case 0: //continue, make next level
    			game.popState();
    			((LevelGameState) game.stateStack.get(game.stateStack.size() - 1)).loadNextLevel();
    			break;
    		case 1: //quit, pop back to menu
    			Game.playSound(Game.SOUND_M_CONFIRM);
    			game.popX(2);
    			break;
    		default:
    			break;
    	}
    }

}