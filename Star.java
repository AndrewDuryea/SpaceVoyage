/*
 * Andrew Duryea
 * September 18, 2013
 * Star.java
 *
 * The stars, they move and stuff.
 */

package Game;

import java.lang.Math;
import java.util.Random;
import Graphics3D.Point3D;
import Game.Background;

public class Star
{
	Point3D model;

	public double X;
	public double Y;

	private double stepX;
	private double stepY;

	Background back;

	double MAX_VEL;
	double MIN_VEL;

	double velocity;

    public Star(Background b, double VELOCITY, boolean moveZ){
    	this.back = b;

    	//System.out.println("here1");
    	X = Y = 0;
    	//System.out.println("here2 " + X + ", " + Y);

/*    	model = back.game.g3D.addObject("star", 1, 1, 1, 0, 0.75);

		//points
		model.addPoint(-3,3,10);	//0
		model.addPoint(-3,-3,10);	//1
		model.addPoint(3,-3,10);	//2
		model.addPoint(3,3,10);		//3

		//faces
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);
*/
		MAX_VEL = (5 * VELOCITY);
		MIN_VEL = (VELOCITY);

    	Random r = new Random();

    	//velocity = 0 - (r.nextInt((int)VELOCITY) + 5);
    	velocity = 0 - ((r.nextDouble() * (MAX_VEL - MIN_VEL)) + MIN_VEL);

    	//int x = r.nextInt(Game.WIDTH + 1) - Game.WIDTH / 2;
    	//int y = r.nextInt(Game.HEIGHT + 1) - Game.HEIGHT / 2;
		//model.translate(x,y,GameState.DISTANCE);


		int x = 0;
		int y = 0;
		//random width, x
		if(r.nextInt() % 2 == 0){
			//pos height
			if(r.nextInt() % 2 == 0){
				x = r.nextInt(Game.WIDTH + 1) - Game.WIDTH / 2;
				y = Game.HEIGHT;
			}
			//neg height
			else{
				x = r.nextInt(Game.WIDTH + 1) - Game.WIDTH / 2;
				y = -Game.HEIGHT;
			}
		}
		//random height, y
		else{
			//pos width
			if(r.nextInt() % 2 == 0){
				x = Game.WIDTH;
				y = r.nextInt(Game.HEIGHT + 1) - Game.HEIGHT / 2;
			}
			//neg width
			else{
				x = -Game.WIDTH;
				y = r.nextInt(Game.HEIGHT + 1) - Game.HEIGHT / 2;
			}
		}

		if(moveZ){
			int z = r.nextInt(back.distance);
			model = b.game.g3D.addPoint(x,y,z);
		}
		else{
			model = b.game.g3D.addPoint(x,y,back.distance);
		}

    	//System.out.println("here3 " + x + ", " + y);

    	//magnitude because Vectors
    	double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

    	stepX = x / magnitude;
    	stepY = y / magnitude;
    	//System.out.println("here4 " + magnitude + " " + stepX + ", " + stepY);
    }

    public void update(long delta){
    	//System.out.println("here2");
    	//X += (stepX * velocity);
    	//Y += (stepY * velocity);

    	//model.translateDyn(0,0,velocity);
    	model.Z += velocity*delta;
    }
}