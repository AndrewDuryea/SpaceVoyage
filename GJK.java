/**
 * Andrew Duryea
 * September 30, 2013
 * GJK.java
 *
 * An implementation of the Gilbert-Johnson-Keerthi algorithm for collision detection.
 *
 * sources:
 *	video: http://mollyrocket.com/849 (warning: the video is an hour long)
 *	example: http://www.codezealot.org/archives/88
 *	paper: http://www.medien.ifi.lmu.de/lehre/ss10/ps/Ausarbeitung_Beispiel.pdf
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import java.util.ArrayList;
import Graphics3D.Point3D;
import Graphics3D.Object3D;
import Graphics3D.Vector3D;

public class GJK {

    //collision stuff
	public static ArrayList<Point3D> simplex;
	public static Vector3D direction;

	public static Point3D getSupport(Object3D objectA, Object3D objectB){

		Vector3D localDirection = direction;

		Point3D A = objectA.points.get(0);
		double maxDot = localDirection.dot(new Vector3D(A));

		//System.out.println("this");
		//System.out.println("localDirection: " + localDirection);
		for(Point3D a : objectA.points){
			double dot = localDirection.dot(new Vector3D(a));
			//System.out.println(a + "; " + dot);
			if(dot > maxDot){
				maxDot = dot;
				A = a;
			}
		}
		//System.out.println("furthest: " + A);

		localDirection = localDirection.flipDirection();
		Point3D B = objectB.points.get(0);
		maxDot = localDirection.dot(new Vector3D(B));

		//System.out.println("\nother");
		//System.out.println("localDirection: " + localDirection);
		for(Point3D b : objectB.points){
			double dot = localDirection.dot(new Vector3D(b));
			//System.out.println(b + "; " + dot);
			if(dot > maxDot){
				maxDot = dot;
				B = b;
			}
		}
		//System.out.println("furthest: " + B);
		//System.out.println("\npoint: " + (A.X-B.X) + ", " + (A.Y-B.Y) + ", " + (A.Z-B.Z));

		return new Point3D(A.X-B.X, A.Y-B.Y, A.Z-B.Z);
	}

	public static boolean checkCollision(Object3D objectA, Object3D objectB){
		direction = (new Vector3D(objectA.points.get(0))).flipDirection();
		//direction = new Vector3D(1,0,0);
		simplex = new ArrayList<Point3D>();
		Point3D A = getSupport(objectA, objectB);

		simplex.add(A);

		direction = (new Vector3D(A)).flipDirection();

		//return false;

		//infinite loop?
		//while(true){
		for(int i = 0; i < 100; i++){
			A = getSupport(objectA, objectB);

			//System.out.println("dot: " + (new Vector3D(A)).dot(direction));

			if((new Vector3D(A)).dot(direction) <= 0){
				return false;
			}

			simplex.add(A);

			if(doSimplex()){
				return true;
			}
		}

		return true;
	}

	public static boolean doSimplex(){
			//System.out.println("here1 " + simplex.size());
		switch(simplex.size()){
			case 1:
				return false;
			case 2:
				return getClosestLine();
			case 3:
				return getClosestTriangle();
			case 4:
				return getClosestTetrahedron();
			default:
				System.out.println("\t\tHEELLLLPP!!!!");
				return false;
		}
	}

	public static boolean getClosestLine(){
		//System.out.println("here5");
		//we know simplex.size == 2, and points are added in order
		Point3D A = simplex.get(1);
		Point3D B = simplex.get(0);

		//Vector3D AO = (new Vector3D(A)).flipDirection();
		Vector3D AO = (new Vector3D(A)).flipDirection();
		Vector3D AB = new Vector3D(A,B);

		//System.out.println("BA.dot(AO) = " + BA.dot(AO));
		if(AB.dot(AO) > 0){
		//System.out.println("here3");
			direction = AB.cross(AO).cross(AB);
		}
		else{
		//System.out.println("here4");
			simplex.remove(0);
			direction = AO;
		}

		//a line can't contain the origin
		return false;
	}

	public static boolean getClosestTriangle(){
		//System.out.println("here6");
		//we know simplex.size == 3, and points are added in order
		Point3D A = simplex.get(2);
		Point3D B = simplex.get(1);
		Point3D C = simplex.get(0);

		Vector3D AO = (new Vector3D(A)).flipDirection();
		Vector3D AB = new Vector3D(A,B);
		Vector3D AC = new Vector3D(A,C);
		Vector3D ABC = AB.cross(AC);

		if((ABC.cross(AC)).dot(AO) > 0){
			if(AC.dot(AO) > 0){
				simplex.remove(1);
				direction = AC.cross(AO).cross(AC);
			}
			else{
				if(AB.dot(AO) > 0){
					simplex.remove(0);
					direction = AB.cross(AO).cross(AB);
				}
				else{
					simplex.remove(0);
					simplex.remove(1);
					direction = AO;
				}
			}
		}
		else{
			if((AB.cross(ABC)).dot(AO) > 0){
				if(AB.dot(AO) > 0){
					simplex.remove(0);
					direction = AB.cross(AO).cross(AB);
				}
				else{
					simplex.remove(0);
					simplex.remove(1);
					direction = AO;
				}
			}
			else{
				if(ABC.dot(AO) > 0){
					direction = ABC;
				}
				else{
					direction = ABC.flipDirection();
				}
			}
		}

		//a triangle can't contain the origin
		return false;
	}

	public static boolean getClosestTetrahedron(){
		//System.out.println("\nhere7");

		//we know simplex.size == 4, and points are added in order
		Point3D A = simplex.get(3);
		Point3D B = simplex.get(2);
		Point3D C = simplex.get(1);
		Point3D D = simplex.get(0);

		Vector3D AO = (new Vector3D(A)).flipDirection();
		Vector3D AB = new Vector3D(A,B);
		Vector3D AC = new Vector3D(A,C);
		Vector3D AD = new Vector3D(A,D);

		/*System.out.println("A: " + A);
		System.out.println("B: " + B);
		System.out.println("C: " + C);
		System.out.println("D: " + D);

		System.out.println("AO: " + AO);
		System.out.println("AB: " + AB);
		System.out.println("AC: " + AC);
		System.out.println("DA: " + AD);

		System.out.println("(AC.cross(AB)).dot(AO): " + (AC.cross(AB)).dot(AO));
		System.out.println("(AD.cross(AC)).dot(AO): " + (AD.cross(AC)).dot(AO));
		System.out.println("(AB.cross(AD)).dot(AO): " + (AB.cross(AD)).dot(AO));
*/
		if((AC.cross(AB)).dot(AO) > 0){
			simplex.remove(0);
			getClosestTriangle();
		} else if((AD.cross(AC)).dot(AO) > 0){
			simplex.remove(2);
			getClosestTriangle();
		} else if((AB.cross(AD)).dot(AO) > 0){
			simplex.remove(1);
			getClosestTriangle();
		} else
			return true;

		return false;
	}
}