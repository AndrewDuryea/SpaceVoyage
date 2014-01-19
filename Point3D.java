/**
 * Andrew Duryea
 * September 30, 2013
 * Point3D.java
 *
 * A point in 3D, can also be drawn.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Graphics3D;
import Graphics3D.Point3D;
import Graphics3D.Triangle3D;
import Graphics3D.Vector3D;
import Graphics3D.Matrix;
import Graphics3D.PointLight;
import java.awt.*;
import java.util.ArrayList;

//this class is a simple point in 3D
public class Point3D
{
	public double X;
	public double Y;
	public double Z;

	public String pointID;

	//a list of all the faces that have this point as a vertex
	public ArrayList<Triangle3D> faceList = new ArrayList<Triangle3D>();

	public Point3D(double X, double Y, double Z)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.pointID = ""+-1; //this point is unlisted, ie not part of a triangle or temporary
	}

	public Point3D(double X, double Y, double Z, int pointID)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.pointID = "P" + pointID; //this point is listed
	}

	public Point3D(double X, double Y, double Z, String name)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.pointID = name; //this is a named point
	}

	public Point3D(double X, double Y, double Z, ArrayList<Triangle3D> faceList)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.faceList = faceList;
	}

	public Point3D(Point3D p){
		this.X = p.X;
		this.Y = p.Y;
		this.Z = p.Z;
		this.pointID = ""+-1; //this point is unlisted, ie not part of a triangle or temporary
	}

	public void setPoint(double X, double Y, double Z)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}

	public void addFace(Triangle3D t)
	{
		faceList.add(t);
	}

	//get the sum of the normal of the faces in faceList
	public Vector3D getNormal()
	{
		Vector3D ret = new Vector3D(0,0,0);
		for(Triangle3D t : faceList)
		{
			ret = ret.add(t.getNormal().unitVector());
		}
		return ret.unitVector();
	}

	public Point3D perspectiveProject(double view)
	{
		double Xps;
		double Yps;
		double Zps;

		if(Graphics3D.project)
		{
			Xps = (view * X) / (view + Z);
			Yps = (view * Y) / (view + Z);
			Zps = (Z) / (view + Z);
		}
		else
		{
			Xps = X;
			Yps = Y;
			Zps = Z;
		}
		return new Point3D(Xps, Yps, Zps, this.faceList);
	}

	public void drawPoint(Graphics g, int xOffset, int yOffset){
		Point3D p = Graphics3D.currentCamera.orient(Graphics3D.standardUp, this).perspectiveProject(Graphics3D.viewpoint);

		g.drawLine((int)(p.X + xOffset), (int)(-p.Y + yOffset), (int)(p.X + xOffset), (int)(-p.Y + yOffset));
	}

	public Point3D multiply(Matrix m)
	{
		Matrix point = new Matrix(1,4);

		//create a matrix from the point
		point.setValue(0,0,this.X);
		point.setValue(0,1,this.Y);
		point.setValue(0,2,this.Z);
		point.setValue(0,3,1);

		//pull the point through the composite matrix
		point = point.multiply(m);

		return new Point3D(point.getValue(0,0), point.getValue(0,1), point.getValue(0,2), this.faceList);
	}

	public String toString()
	{
		return "(" + this.X + ", " + this.Y + ", " + this.Z + ")";
	}

	//lightStuff
	public Vector3D getIntensityVector(Vector3D normal, Object3D o, Point3D point)
	{
		//the reflection vector
		Vector3D Ref = null;

		double cphi;
		double ctheta;

		double dist;

		double Ir, Ig, Ib;
		Ir = Ig = Ib = 0;

		if(Graphics3D.bAmbient)
		{
			//ambient diffuse
			Ir = Graphics3D.Iar * o.Kdr;
			Ig = Graphics3D.Iag * o.Kdg;
			Ib = Graphics3D.Iab * o.Kdb;
		}

		for(PointLight p : Graphics3D.pointLightList)
		{
			//emmitted diffuse
			cphi = normal.unitVector().dot(p.direction.unitVector());
			dist = p.distance;

			//specular
			if(cphi > 0) //case 1
			{
				if(Graphics3D.bDiffuse)
				{
					Ir += p.Ipr * o.Kdr * cphi / dist;
					Ig += p.Ipg * o.Kdg * cphi / dist;
					Ib += p.Ipb * o.Kdb * cphi / dist;
				}
				if(!Graphics3D.gouradShading && Graphics3D.bSpecular)
				{
					Ref = normal.unitVector().minus(p.direction.unitVector().divideScaler(2*cphi));

					Ir += p.Ipr / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
					Ig += p.Ipg / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
					Ib += p.Ipb / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
				}
			}
			else //case 3
			{
				if(!Graphics3D.gouradShading && Graphics3D.bSpecular)
				{
					Ref = p.direction.unitVector().flipDirection();

					Ir += p.Ipr / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
					Ig += p.Ipg / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
					Ib += p.Ipb / dist * o.Ks * Math.pow((Ref.unitVector().dot(Graphics3D.currentCamera.viewVec.unitVector())), o.n);
				}
			}
		}

		Ir = (Ir > 1) ? 1 : Ir;
		Ig = (Ig > 1) ? 1 : Ig;
		Ib = (Ib > 1) ? 1 : Ib;

		Ir = (Ir < 0) ? 0 : Ir;
		Ig = (Ig < 0) ? 0 : Ig;
		Ib = (Ib < 0) ? 0 : Ib;

		return new Vector3D(Ir, Ig, Ib);
	}
}