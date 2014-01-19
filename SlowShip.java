/*
 * Andrew Duryea
 * November 3, 2013
 * SlowShip.java
 *
 * A slow ship for the player. Above average firepower and shields; below average speed and acceleration
 */

package Game;

public class SlowShip extends Player{

    public SlowShip(Game g, GameState gs) {
    	super(g, gs);

    	VELOCITY = 0.05;
        ACCELERATION = 0.01;
        DAMP = 0.8;

        MAX_SHIELDS = 150;
        shields = MAX_SHIELDS;

        SHOT_POWER = 2;

    	//create model
    	//(name, standardUp, Kdr, Kdg, Kdb, n, Ks)
    	model = g.g3D.addObject("ship", 0.3, 0.8, 0.32, 100, 0);
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

		model.addPoint(50,0,70);	//8
		model.addPoint(-50,0,70);	//9

		model.addPoint(100,0,120);	//10
		model.addPoint(100,0,240);	//11

		model.addPoint(-100,0,120);	//12
		model.addPoint(-100,0,240);	//13

		//right engine points
		//x 60,10
		//y 25,-25
		model.addPoint(46,25,220);	//14
		model.addPoint(24,25,220);	//15
		model.addPoint(10,11,220);	//16
		model.addPoint(10,-11,220);	//17
		model.addPoint(24,-25,220);	//18
		model.addPoint(46,-25,220);	//19
		model.addPoint(60,-11,220);	//20
		model.addPoint(60,11,220);	//21

		model.addPoint(46,25,245);	//22
		model.addPoint(24,25,245);	//23
		model.addPoint(10,11,245);	//24
		model.addPoint(10,-11,245);	//25
		model.addPoint(24,-25,245);	//26
		model.addPoint(46,-25,245);	//27
		model.addPoint(60,-11,245);	//28
		model.addPoint(60,11,245);	//29

		model.addPoint(35,0,235);	//30

		//left engine points
		//x -60,-10
		//y 25,-25
		model.addPoint(-24,25,220);	//31
		model.addPoint(-46,25,220);	//32
		model.addPoint(-60,11,220);	//33
		model.addPoint(-60,-11,220);	//34
		model.addPoint(-46,-25,220);	//35
		model.addPoint(-24,-25,220);	//36
		model.addPoint(-10,-11,220);	//37
		model.addPoint(-10,11,220);	//38

		model.addPoint(-24,25,245);	//39
		model.addPoint(-46,25,245);	//40
		model.addPoint(-60,11,245);	//41
		model.addPoint(-60,-11,245);	//42
		model.addPoint(-46,-25,245);	//43
		model.addPoint(-24,-25,245);	//44
		model.addPoint(-10,-11,245);	//45
		model.addPoint(-10,11,245);	//46

		model.addPoint(-35,0,235);	//47

		//firing point
		model.addPoint(0,0,70); //48

		//counter-clockwise for each face

		//body
		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(7,11,6);
		model.addTriangle(4,5,13);

		//left side
		model.addTriangle(3,10,7);
		model.addTriangle(7,10,11);

		model.addTriangle(10,2,6);
		model.addTriangle(10,6,11);

		//right side
		model.addTriangle(4,12,0);
		model.addTriangle(4,13,12);

		model.addTriangle(13,5,12);
		model.addTriangle(12,5,1);

		//front
		model.addTriangle(0,8,3);
		model.addTriangle(0,9,8);

		model.addTriangle(9,2,8);
		model.addTriangle(9,1,2);

		//corner bits
		model.addTriangle(0,12,9);
		model.addTriangle(12,1,9);

		model.addTriangle(3,8,10);
		model.addTriangle(8,2,10);

		//right engine
		model.addTriangle(14,22,23);
		model.addTriangle(15,14,23);
		model.addTriangle(15,23,24);
		model.addTriangle(16,15,24);
		model.addTriangle(16,24,25);
		model.addTriangle(17,16,25);
		model.addTriangle(17,25,26);
		model.addTriangle(18,17,26);
		model.addTriangle(27,18,26);
		model.addTriangle(19,18,27);
		model.addTriangle(28,19,27);
		model.addTriangle(20,19,28);
		model.addTriangle(29,20,28);
		model.addTriangle(21,20,29);
		model.addTriangle(22,21,29);
		model.addTriangle(14,21,22);

		model.addTriangle(30,23,22);
		model.addTriangle(30,24,23);
		model.addTriangle(30,25,24);
		model.addTriangle(30,26,25);
		model.addTriangle(30,27,26);
		model.addTriangle(30,28,27);
		model.addTriangle(30,29,28);
		model.addTriangle(30,22,29);

		//left engine
		model.addTriangle(31,39,40);
		model.addTriangle(32,31,40);
		model.addTriangle(32,40,41);
		model.addTriangle(33,32,41);
		model.addTriangle(33,41,42);
		model.addTriangle(34,33,42);
		model.addTriangle(34,42,43);
		model.addTriangle(35,34,43);
		model.addTriangle(44,35,43);
		model.addTriangle(36,35,44);
		model.addTriangle(45,36,44);
		model.addTriangle(37,36,45);
		model.addTriangle(46,37,45);
		model.addTriangle(38,37,46);
		model.addTriangle(39,38,46);
		model.addTriangle(31,38,39);

		model.addTriangle(47,40,39);
		model.addTriangle(47,41,40);
		model.addTriangle(47,42,41);
		model.addTriangle(47,43,42);
		model.addTriangle(47,44,43);
		model.addTriangle(47,45,44);
		model.addTriangle(47,46,45);
		model.addTriangle(47,39,46);

		//rotate model, instead of recalculating points
		//model.rotateY(model.getCurrentYDegrees() + 180);
		//model.translate(0,0,90);
		//model.translate(300,0,60);
		//model.translate(0,0,100);
		//model.scale(65);

		firingPoint = model.getPointAtIndex(48);

		description =
			"The Ptolemaios is a slow, but strong, ship.\n"+
			"The Ptolemaios is often used to support smaller\n"+
			"ships and as a mobile fortress. This ship has\n"+
			"above average firepower and shields, and below\n"+
			"average speed.";
    }


}