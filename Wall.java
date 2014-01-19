/*
 * Andrew Duryea
 * September 27, 2013
 * Wall.java
 *
 * Just something to collide with.
 */

package Game;

import Graphics3D.Object3D;
import Game.Game;
import java.util.ArrayList;

public class Wall
{
	public Object3D model;

    public Wall() {
/*
    	//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = g.g3D.addObject("wall", 1, 0, 1, 10, 0.75);
    	//gs.objects.add(model);

    	//add points order is important
    	model.addPoint(190,50,120);	//0
		model.addPoint(190,-50,120);	//1
		model.addPoint(200,-50,120);	//2
		model.addPoint(200,50,120);	//3
		model.addPoint(190,50,220);	//4
		model.addPoint(190,-50,220);	//5
		model.addPoint(200,-50,220);	//6
		model.addPoint(200,50,220);	//7

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

		model.translate(0,0,60);*/
    }

    public void setModel(Object3D model){
    	this.model = model;
    }
}