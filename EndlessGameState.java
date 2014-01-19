/*
 * Andrew Duryea
 * October 30, 2013
 * EndlessGameState.java
 *
 * A game where asteroids are endlessly generated.
 */

package Game;

public class EndlessGameState extends GameState{

	//double dist = 0;
	long time = 0;

	double vel;

	static double MAX_ASTER = 3;

    public EndlessGameState(Game g) {
    	super(g);
    	//introString = "ENDLESS";
		displayMessage("ENDLESS");
    }

    public void addPlayer(Player player){
    	super.addPlayer(player);
    	vel = Player.VELOCITY;
    }

	public void update(long delta){
		super.update(delta);

		time += delta;

		if((time/1000) > 10){ //approx 10 sec
			time = 0;

			vel += 0.1;
			MAX_ASTER += 1;
		}

		if(asteroidList.size() < MAX_ASTER){
			makeAsteroid();
		}
	}

    public void makeAsteroid(){
    	Asteroid a = new Asteroid(this, 0, true);
    	a.velocity[2] = vel;

    	asteroidList.add(new Asteroid(this, 0, true));
    }
}