/*
 * Andrew Duryea
 * October 16, 2013
 * GameOverState.java
 *
 * This state is pushed whenever the player's sheilds <= 0.
 */

package Game;

public class GameOverState extends GameEndState{

    public GameOverState(Game g){
    	super(g);

    	this.titleString = "Game Over";

    	//menu.add(new MenuItem("restart", Game.WIDTH,Game.HEIGHT,120,30));
    	//menu.add(new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));

		int index = addMenu();

    	addMenuItem(index, new MenuItem("restart", Game.WIDTH,Game.HEIGHT,120,30));
    	addMenuItem(index, new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));
    }

    public void submit(){
    	switch(selectedIndex){
    		case 0: //restart, make a new gameState
    			if(game.stateStack.get(game.stateStack.size() - 2).getClass().equals(LevelGameState.class)){
    				int levelNum = ((LevelGameState) game.stateStack.get(game.stateStack.size() - 2)).levelNum;

    				LevelGameState gs = new LevelGameState(game, levelNum);

    				Player player;

    				if(((LevelGameState) game.stateStack.get(game.stateStack.size() - 2)).p.getClass().equals(FastShip.class)){
						player = new FastShip(game, gs);
    				}
    				else if(((LevelGameState) game.stateStack.get(game.stateStack.size() - 2)).p.getClass().equals(SlowShip.class)){
						player = new SlowShip(game, gs);
    				}
    				else{
						player = new NormalShip(game, gs);
    				}

					gs.addPlayer(player);

	    			Game.playSound(Game.SOUND_M_CONFIRM);
	    			game.popX(2);
	    			game.pushState(gs);
	    			//game.pushState(new LevelGameState(game, levelNum));
	    			//((LevelGameState) game.stateStack.get(game.stateStack.size() - 1)).addPlayer(player);
	    			//game.pushState(new LevelGameState(game, player, levelNum));
    			}
    			else if(game.stateStack.get(game.stateStack.size() - 2).getClass().equals(EndlessGameState.class)){

    				EndlessGameState gs = new EndlessGameState(game);

    				Player player;

    				if(((LevelGameState) game.stateStack.get(game.stateStack.size() - 2)).p.getClass().equals(FastShip.class)){
						player = new FastShip(game, gs);
    				}
    				else if(((LevelGameState) game.stateStack.get(game.stateStack.size() - 2)).p.getClass().equals(SlowShip.class)){
						player = new SlowShip(game, gs);
    				}
    				else{
						player = new NormalShip(game, gs);
    				}

					gs.addPlayer(player);

	    			Game.playSound(Game.SOUND_M_CONFIRM);
	    			game.popX(2);
	    			game.pushState(gs);

	    			//game.pushState(new EndlessGameState(game));
	    			//((EndlessGameState) game.stateStack.get(game.stateStack.size() - 1)).addPlayer(player);
	    			//game.pushState(new EndlessGameState(game, player));
    			}

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