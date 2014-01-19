/*
 * Andrew Duryea
 * November 3, 2013
 * FastShip.java
 *
 * A fast ship for the player. Above average speed and acceleration; below average shields; average firepower
 */

package Game;

public class FastShip extends Player{

    public FastShip(Game g, GameState gs) {
    	super(g, gs);

    	VELOCITY = 0.2;
        ACCELERATION = 0.04;
        DAMP = 0.9;

        MAX_SHIELDS = 75;
        shields = MAX_SHIELDS;

        SHOT_POWER = 1;

    	//create model
    	//(name, standardUp, Kdr, Kdg, Kdb, n, Ks)
    	model = g.g3D.addObject("ship", 0, 0.46, 1, 10, 0.75);
    	//gs.objects.add(model);
		//add points, order is important
    	model.addPoint(-50,25,120);	//0
		model.addPoint(-50,-50,120);	//1
		model.addPoint(50,-50,120);	//2
		model.addPoint(50,25,120);	//3

		model.addPoint(-50,50,220);	//4
		model.addPoint(-50,-50,220);	//5
		model.addPoint(50,-50,220);	//6
		model.addPoint(50,50,220);	//7

		model.addPoint(0,-50,60);	//8

		model.addPoint(125,0,240);	//9
		model.addPoint(-125,0,240);	//10

		//engine points
		model.addPoint(-20,40,220);	//11
		model.addPoint(20,40,220);	//12
		model.addPoint(40,20,220);	//13
		model.addPoint(40,-20,220);	//14
		model.addPoint(20,-40,220);	//15
		model.addPoint(-20,-40,220);	//16
		model.addPoint(-40,-20,220);	//17
		model.addPoint(-40,20,220);	//18

		model.addPoint(-20,40,245);	//19
		model.addPoint(20,40,245);	//20
		model.addPoint(40,20,245);	//21
		model.addPoint(40,-20,245);	//22
		model.addPoint(20,-40,245);	//23
		model.addPoint(-20,-40,245);	//24
		model.addPoint(-40,-20,245);	//20
		model.addPoint(-40,20,245);	//26

		model.addPoint(0,0,225);	//27

		//counter-clockwise for each face

		//body
		//model.addTriangle(0,1,3);
		//model.addTriangle(3,1,2);

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

		//cone
		model.addTriangle(0,1,8);
		model.addTriangle(3,8,2);
		model.addTriangle(0,8,3);
		model.addTriangle(8,1,2);

		//wings
		model.addTriangle(9,6,7);
		model.addTriangle(9,7,2);
		model.addTriangle(9,2,6);

		model.addTriangle(10,4,5);
		model.addTriangle(10,1,4);
		model.addTriangle(10,5,1);

		//engine
		model.addTriangle(11,20,19);
		model.addTriangle(11,12,20);
		model.addTriangle(12,21,20);
		model.addTriangle(12,13,21);
		model.addTriangle(13,22,21);
		model.addTriangle(13,14,22);
		model.addTriangle(14,23,22);
		model.addTriangle(14,15,23);
		model.addTriangle(15,24,23);
		model.addTriangle(15,16,24);
		model.addTriangle(16,25,24);
		model.addTriangle(16,17,25);
		model.addTriangle(17,26,25);
		model.addTriangle(17,18,26);
		model.addTriangle(18,19,26);
		model.addTriangle(18,11,19);

		model.addTriangle(27,19,20);
		model.addTriangle(27,20,21);
		model.addTriangle(27,21,22);
		model.addTriangle(27,22,23);
		model.addTriangle(27,23,24);
		model.addTriangle(27,24,25);
		model.addTriangle(27,25,26);
		model.addTriangle(27,26,19);

		//rotate model, instead of recalculating points
		//model.rotateY(model.getCurrentYDegrees() + 180);
		//model.translate(0,0,90);
		//model.translate(300,0,60);
		model.translate(0,0,100);
		//model.scale(65);

		firingPoint = model.getPointAtIndex(8);

		description =
			"The Interceptor is a remarkably fast ship.\n"+
			"The ship is so fast that its navigation must be\n"+
			"assisted by a complex artificial intellegence\n"+
			"system. This ship has above average speed,\n"+
			"but below average shields.";
    }


}