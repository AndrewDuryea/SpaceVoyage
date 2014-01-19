/*
 * Andrew Duryea
 * September 16, 2013
 * Player.java
 *
 * The player and the model for the player.
 *
 * November 3, 2013: moved ship specific parts into ship specific classes: NormalShip, FastShip, SlowShip
 */

package Game;

import java.lang.Math;
import Graphics3D.Object3D;
import Graphics3D.Point3D;
import Graphics3D.GJK;
import Game.Game;
import java.util.*;

public class Player
{
	Object3D model;

	Game game;
	GameState gameState;

	//x, y, z
	double velocity[] = {0,0,0};
	double acceleration[] = {0,0,0};

	static double VELOCITY;
	static double ACCELERATION;
	static double DAMP;

	public int MAX_SHIELDS;
	public int SHOT_POWER;

	public int shields;

	private int invincible = 0;

	public Point3D firingPoint;

	public String description;

    public Player(Game g, GameState gs)
    {
    	this.game = g;
    	this.gameState = gs;
    }

    public void update(long delta){

		if(invincible == 1){
			model.invertColor();
		}

		if(invincible > 0){
			invincible--;
		}

		//velocity[0] = velocity[1] = 0;

		//double accel[] = {0,0};

		if(velocity[0] < VELOCITY && gameState.rightPressed){
    		//System.out.println("here1");
    		velocity[0] += ACCELERATION*delta;
	    }
		if(velocity[0] > -VELOCITY && gameState.leftPressed){
    		//System.out.println("here2");
    		velocity[0] += -ACCELERATION*delta;
    	}

		if(velocity[1] < VELOCITY && gameState.upPressed){
    		velocity[1] += ACCELERATION*delta;
    	}
		if(velocity[1] > -VELOCITY && gameState.downPressed){
    		velocity[1] += -ACCELERATION*delta;
    	}

    	/*velocity[0] *= DAMP;
    	velocity[1] *= DAMP;*/

    	if(!gameState.leftPressed && !gameState.rightPressed){
    		velocity[0] = 0;
    	}

    	if(!gameState.upPressed && !gameState.downPressed){
    		velocity[1] = 0;
    	}

		//System.out.println(velocity[0] + " " + velocity[1] + " " + velocity[2]);

		double tempVel[] = {velocity[0]*delta,velocity[1]*delta,velocity[2]*delta};

    	model.translateDyn(tempVel[0], tempVel[1], tempVel[2]);

		//System.out.println(gameState.border.size());
    	//check collisions with borders
    	for(Wall w : gameState.border){
	    	if(GJK.checkCollision(this.model, w.model)){
	    		//System.out.println("true " + w.model.name);
	    		//reverse the movement
	    		model.translateDyn(0-tempVel[0],0-tempVel[1],0-tempVel[2]);
	    		break;
	    	}
	    	else{
	    		//System.out.println("false " + w.model.name);
	    	}
    	}

		synchronized(gameState.asteroidList){
			//use an iterator to avoid concurrent modification exception
	    	Iterator itr = gameState.asteroidList.iterator();
	    	while(itr.hasNext()){
	    		Asteroid a = (Asteroid) itr.next();

				//if the asteroid is a station, and there is a collision
				if(gameState.getClass().equals(LevelGameState.class) && a.getClass().equals(Station.class) && GJK.checkCollision(this.model, a.model)){
					//push levelOverState
					//game.pushState(game.stateStack.get(game.stateStack.size()-1), new LevelOverState(game));
					((LevelGameState) gameState).levelEnd();
				}

	    		if(invincible == 0){
			    	if(!a.getClass().equals(Station.class) && GJK.checkCollision(this.model, a.model)){
			    		//damage
			    		shields -= a.damage;

			    		if(shields < 0)
			    			shields = 0;

			    		invincible = 62;
			    		model.invertColor();

						itr.remove();

    					Game.playSound(Game.SOUND_DAMAGE);
			    		break;
			    	}
	    		}
			}
		}

    	/*for(Asteroid a : gameState.asteroidList){
    		if(invincible == 0){
		    	if(GJK.checkCollision(this.model, a.model)){
		    		//damage
		    		shields -= 10;
		    		invincible = 62;
		    		model.invertColor();
		    		break;
		    	}
    		}
    	}*/

    	//System.out.println(model.getCenter() + "\n");
    	Game.P_X = model.getCenter().X;
    	Game.P_Y = model.getCenter().Y;
    	Game.P_Z = model.getCenter().Z;
    }

    public double getXVelocity(){
    	return velocity[0];
    }

    public double getYVelocity(){
    	return velocity[1];
    }

    public double getZVelocity(){
    	return velocity[2];
    }

    public void setVelocity(double x, double y, double z){
    	velocity[0] = x;
    	velocity[1] = y;
    	velocity[2] = z;
    }

	public int getShields(){
		return shields;
	}

	public void setShields(int s){
		shields = s;
	}

	public void incShields(int s){
		shields += s;
	}

	public void decShields(int s){
		shields -= s;
		if(shields < 0)
			shields = 0;
	}

	public void resetModel(){
		model.reset();
		//System.out.print("From Player.java: " + model.getCurrentYDegrees());
		//model.rotateY(model.getCurrentYDegrees() + 180);
		model.rotateY(180);
		//System.out.println(model.getCurrentYDegrees());
		model.translate(0,0,100);
		model.scale(65);
	}
}