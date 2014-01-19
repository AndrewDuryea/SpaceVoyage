/*
 * Andrew Duryea
 * November 3, 2013
 * Player.java
 *
 * A normal ship for the player. Average speed, acceleration, shields, and firepower
 */

 package Game;

public class NormalShip extends Player
{

    public NormalShip(Game g, GameState gs){
    	super(g, gs);

    	VELOCITY = 0.10;
        ACCELERATION = 0.02;
        DAMP = 0.8;

        MAX_SHIELDS = 100;
        shields = MAX_SHIELDS;

        SHOT_POWER = 1;

    	//create model
    	//(name, standardUp, Kdr, Kdg, Kdb, n, Ks)
    	model = g.g3D.addObject("ship", 1, 0.46, 0, 10, 0.75);
    	//gs.objects.add(model);

		//add points order is important
    	model.addPoint(-50,50,120);	//0
		model.addPoint(-50,-50,120);	//1
		model.addPoint(50,-50,120);	//2
		model.addPoint(50,50,120);	//3
		model.addPoint(-50,50,220);	//4
		model.addPoint(-50,-50,220);	//5
		model.addPoint(50,-50,220);	//6
		model.addPoint(50,50,220);	//7

		model.addPoint(0,0,60);	//8

    	model.addPoint(-25,50,270);	//9
		model.addPoint(-25,0,270);	//10
		model.addPoint(25,0,270);	//11
		model.addPoint(25,50,270);	//12

		//right engine points
    	model.addPoint(50,25,145);	//13
		model.addPoint(50,-25,145);	//14
		model.addPoint(100,-25,145);	//15
		model.addPoint(100,25,145);	//16

		model.addPoint(50,25,195);	//17
		model.addPoint(50,-25,195);	//18
		model.addPoint(100,-25,195);	//19
		model.addPoint(100,25,195);	//20

		model.addPoint(62,13,220);	//21
		model.addPoint(62,-13,220);	//22
		model.addPoint(88,-13,220);	//23
		model.addPoint(88,13,220);	//24

		model.addPoint(62,13,120);	//25
		model.addPoint(62,-13,120);	//26
		model.addPoint(88,-13,120);	//27
		model.addPoint(88,13,120);	//28

		//left engine points
    	model.addPoint(-100,25,145);	//29
		model.addPoint(-100,-25,145);	//30
		model.addPoint(-50,-25,145);	//31
		model.addPoint(-50,25,145);	//32

		model.addPoint(-100,25,195);	//33
		model.addPoint(-100,-25,195);	//34
		model.addPoint(-50,-25,195);	//35
		model.addPoint(-50,25,195);	//36

		model.addPoint(-88,13,220);	//37
		model.addPoint(-88,-13,220);	//38
		model.addPoint(-62,-13,220);	//39
		model.addPoint(-62,13,220);	//40

		model.addPoint(-88,13,120);	//41
		model.addPoint(-88,-13,120);	//42
		model.addPoint(-62,-13,120);	//43
		model.addPoint(-62,13,120);	//44

		//counter-clockwise for each face

		//body
		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

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

		//back end
		model.addTriangle(7,6,12);
		model.addTriangle(12,6,11);

		model.addTriangle(12,11,9);
		model.addTriangle(9,11,10);

		model.addTriangle(9,4,12);
		model.addTriangle(12,4,7);

		model.addTriangle(9,10,4);
		model.addTriangle(4,10,5);

		model.addTriangle(5,10,6);
		model.addTriangle(6,10,11);

		//right engine
		model.addTriangle(16,15,20);
		model.addTriangle(20,15,19);

		model.addTriangle(17,13,20);
		model.addTriangle(20,13,16);

		model.addTriangle(14,18,15);
		model.addTriangle(15,18,19);

		model.addTriangle(13,25,28);
		model.addTriangle(16,13,28);

		model.addTriangle(16,28,27);
		model.addTriangle(15,16,27);

		model.addTriangle(15,27,26);
		model.addTriangle(14,15,26);

		model.addTriangle(14,26,25);
		model.addTriangle(13,14,25);

		model.addTriangle(25,26,27);
		model.addTriangle(25,27,28);

		model.addTriangle(20,24,21);
		model.addTriangle(17,20,21);

		model.addTriangle(17,21,22);
		model.addTriangle(18,17,22);

		model.addTriangle(18,22,23);
		model.addTriangle(19,18,23);

		model.addTriangle(19,23,24);
		model.addTriangle(20,19,24);

		model.addTriangle(24,23,22);
		model.addTriangle(24,22,21);

		//left engine
		model.addTriangle(33,34,30);
		model.addTriangle(33,30,29);

		model.addTriangle(33,29,36);
		model.addTriangle(36,29,32);

		model.addTriangle(30,34,31);
		model.addTriangle(31,34,35);

		model.addTriangle(29,41,44);
		model.addTriangle(32,29,44);

		model.addTriangle(32,44,43);
		model.addTriangle(31,32,43);

		model.addTriangle(31,43,42);
		model.addTriangle(30,31,42);

		model.addTriangle(30,42,41);
		model.addTriangle(29,30,41);

		model.addTriangle(41,42,43);
		model.addTriangle(41,43,44);

		model.addTriangle(36,40,37);
		model.addTriangle(33,36,37);

		model.addTriangle(33,37,38);
		model.addTriangle(34,33,38);

		model.addTriangle(34,38,39);
		model.addTriangle(35,34,39);

		model.addTriangle(35,39,40);
		model.addTriangle(36,35,40);

		model.addTriangle(40,39,38);
		model.addTriangle(40,38,37);

		//rotate model, instead of recalculating points
		//model.rotateY(model.getCurrentYDegrees() + 180);
		//model.translate(0,0,90);
		//model.translate(300,0,60);
		model.translate(0,0,100);
		//model.scale(65);

		firingPoint = model.getPointAtIndex(8);

		description =
			"The Serenity is a standard transport ship. Due\n"+
			"to its many nooks and crannies, this ship is\n"+
			"preferred by smugglers to transport potentially\n"+
			"illegal goods. This ship has average speed,\n"+
			"firepower, and shields.";

	}

}