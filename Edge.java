/**
 * Andrew Duryea
 * September 30, 2013
 * Edge.java
 *
 * Used for polygonFill() in Triangle3D.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Point3D;
import Graphics3D.Vector3D;

public class Edge
{
	double Ymax;
	double Ymin;
	double dX;	//the negative inverse slope, -(run/rise)
	double nextX;

	Vector3D dV = null; //the change in the vector across the edge
	Vector3D nextV = null; //the next value for the vector

	public Edge(Point3D P0, Point3D P1, Vector3D V0, Vector3D V1)
	{
		if(P0.Y > P1.Y){
			Ymax = (int)P0.Y + 0.5;
			Ymin = (int)P1.Y + 0.5;
			nextX = P0.X;

			if(V0 != null && V1 != null)
			{
				dV = V1.minus(V0).multiplyScaler(-1.0/(Ymin-Ymax));
				nextV = V0;
			}
		}
		else{
			Ymax = (int)P1.Y + 0.5;
			Ymin = (int)P0.Y + 0.5;
			nextX = P1.X;

			if(V0 != null && V1 != null)
			{
				dV = V0.minus(V1).multiplyScaler(-1.0/(Ymin-Ymax));
				nextV = V1;
			}
		}

		dX = -(((int)P1.X + 0.5) - ((int)P0.X + 0.5)) / (((int)P1.Y + 0.5) - ((int)P0.Y + 0.5));

		nextX += (dX/2);
	}

	public void incrementX()
	{
		nextX += dX;
	}

	public void incrementV()
	{
		if(nextV != null && dV != null)
		{
			nextV = nextV.add(dV);
		}
	}
}