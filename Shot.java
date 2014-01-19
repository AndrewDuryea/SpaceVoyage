/*
 * Andrew Duryea
 * September 16, 2013
 * Shot.java
 *
 * A shot fired from the player.
 */

package Game;

import Graphics3D.Object3D;
import Graphics3D.Point3D;
import Graphics3D.GJK;
import java.util.*;

public class Shot
{
	Object3D model;

	GameState gameState;

	//should only have a positive z velocity
	double velocity[] = {0,0,2};

	boolean destroy = false;

	int power;

    public Shot(GameState gs, int power, Point3D firingPoint){
    	this.gameState = gs;

		this.power = power;

    	//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = gs.game.g3D.addObject("shot", 0, 1, 0, 0, 0);
    	//gs.objects.add(model);

    	//points
    	model.addPoint(-2,2,10);	//0
		model.addPoint(-2,-2,10);	//1
		model.addPoint(2,-2,10);	//2
		model.addPoint(2,2,10);		//3
		model.addPoint(-2,2,50);	//4
		model.addPoint(-2,-2,50);	//2
		model.addPoint(2,-2,50);	//6
		model.addPoint(2,2,50);		//7

		//counter-clockwise for each face

		//body
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);

		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(4,5,0);
		model.addTriangle(0,5,1);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		model.translate(firingPoint);
    }

    public void update(long delta){
    	synchronized(gameState.asteroidList){
			//use an iterator to avoid concurrent modification exception
	    	Iterator itr = gameState.asteroidList.iterator();
	    	while(itr.hasNext()){
	    		Asteroid a = (Asteroid) itr.next();

		    	if(!a.getClass().equals(Station.class) && GJK.checkCollision(this.model, a.model)){
		    		//destroy Asteroid and this shot
		    		a.health -= this.power;

		    		if(a.health <= 0){
			    		GameState.score += a.score;

			    		itr.remove();
	    				Game.playSound(Game.SOUND_EXPLODE);
		    		}
		    		else
		    			Game.playSound(Game.SOUND_ASTEROID_HIT);

		    		destroy = true;

		    		break;
		    	}
			}
		}
    	model.translateDyn(velocity[0]*delta,velocity[1]*delta,velocity[2]*delta);
    }
}