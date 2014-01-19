/*
 * Andrew Duryea
 * October 29, 2013
 * Station.java
 *
 * A space station, when the player collides with this, the level is over.
 */

package Game;

import Game.GameState;
import Graphics3D.Object3D;

public class Station extends Asteroid
{
	//always in the center of the screen, and always moving with -Player.VELOCITY
	public Station(GameState gs, double dist){
		super(gs, 0, false);

		this.gameState = gs;

    	this.dist = dist;

    	//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	this.model = gameState.game.g3D.addObject("station", 0.3, 0.3, 0.3, 10, 0.75);

		model.addPoint(-50,50,20);	//1
		model.addPoint(50,50,20);	//2
		model.addPoint(120,50,90);	//3
		model.addPoint(120,50,190);	//4
		model.addPoint(50,50,260);	//5
		model.addPoint(-50,50,260);	//6
		model.addPoint(-120,50,190);//7
		model.addPoint(-120,50,90);	//8

		model.addPoint(-50,-50,20);	//9
		model.addPoint(50,-50,20);	//10
		model.addPoint(120,-50,90);	//11
		model.addPoint(120,-50,190);//12
		model.addPoint(50,-50,260);	//13
		model.addPoint(-50,-50,260);//14
		model.addPoint(-120,-50,190);//15
		model.addPoint(-120,-50,90);//16

		//extra points so that transforms line up
		model.addPoint(0,150,140);	//17, center

		model.addPoint(0,-100,90);	//18
		model.addPoint(50,-100,140);	//19
		model.addPoint(0,-100,190);	//20
		model.addPoint(-50,-100,140);	//21

		model.addPoint(0,-250,140);	//22, center

		model.addTriangle(0,8,9);
		model.addTriangle(0,9,1);
		model.addTriangle(1,9,10);
		model.addTriangle(1,10,2);
		model.addTriangle(2,10,11);
		model.addTriangle(2,11,3);
		model.addTriangle(3,11,12);
		model.addTriangle(3,12,4);
		model.addTriangle(4,12,13);
		model.addTriangle(4,13,5);
		model.addTriangle(5,13,14);
		model.addTriangle(5,14,6);
		model.addTriangle(6,14,15);
		model.addTriangle(6,15,7);
		model.addTriangle(7,15,8);
		model.addTriangle(7,8,0);

		model.addTriangle(16,0,1);
		model.addTriangle(16,1,2);
		model.addTriangle(16,2,3);
		model.addTriangle(16,3,4);
		model.addTriangle(16,4,5);
		model.addTriangle(16,5,6);
		model.addTriangle(16,6,7);
		model.addTriangle(16,7,0);

		model.addTriangle(8,17,9);
		model.addTriangle(9,17,10);
		model.addTriangle(17,18,10);
		model.addTriangle(10,18,11);
		model.addTriangle(18,19,11);
		model.addTriangle(19,12,11);
		model.addTriangle(19,13,12);
		model.addTriangle(14,13,19);
		model.addTriangle(20,14,19);
		model.addTriangle(15,14,20);
		model.addTriangle(15,20,17);
		model.addTriangle(8,15,17);

		model.addTriangle(21,18,17);
		model.addTriangle(21,19,18);
		model.addTriangle(21,20,19);
		model.addTriangle(21,17,20);

		model.translate(0, 0, GameState.DISTANCE);

		model.scale(200);

		//model.alwaysWireframe = true;

		velocity[0] = 0;
		velocity[1] = 0;
		velocity[2] = 0 - (2 * Player.VELOCITY);
	}

    public void update(long delta){
    	//System.out.println(velocity[0] + " " + velocity[1] + " " + velocity[2]);
    	//System.out.println(model.getCenter().toString());

    	model.translateDyn(velocity[0]*delta,velocity[1]*delta,velocity[2]*delta);
    }

	//just adds this asteroid to the list
    public void timeRun(GameState g){
    	super.timeRun(g);
    	g.displayMessage("Prepare for Docking");
    }
}