/**
 * Andrew Duryea
 * September 30, 2013
 * Vector3D.java
 *
 * This class is a vector in 3D. It has a start and end point, but really,
 *  only the end point is interesting because the start is always (0,0,0).
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Point3D;

public class Vector3D
{
	public Point3D start = new Point3D(0,0,0);
	public Point3D end;

	//find the vector of two points
	public Vector3D(Point3D start, Point3D end)
	{
		this.end = new Point3D(end.X - start.X, end.Y-start.Y, end.Z-start.Z);
	}

	//for when we know where the vector ends
	Vector3D(double X, double Y, double Z)
	{
		this.end = new Point3D(X,Y,Z);
	}

	//for when we know where the vector ends
	Vector3D(Point3D end)
	{
		this.end = end;
	}

	public Vector3D add(Vector3D Q)
	{
		return new Vector3D(this.end.X+Q.end.X, this.end.Y+Q.end.Y, this.end.Z+Q.end.Z);
	}

	public Vector3D minus(Vector3D Q)
	{
		return new Vector3D(this.end.X-Q.end.X, this.end.Y-Q.end.Y, this.end.Z-Q.end.Z);
	}

	public Vector3D addScaler(double c)
	{
		return new Vector3D(this.end.X+c, this.end.Y+c, this.end.Z+c);
	}

	public Vector3D divideScaler(double c)
	{
		return new Vector3D(this.end.X/c, this.end.Y/c, this.end.Z/c);
	}

	public Vector3D multiplyScaler(double c)
	{
		return new Vector3D(this.end.X*c, this.end.Y*c, this.end.Z*c);
	}

	public Vector3D flipDirection()
	{
		return new Vector3D(0-this.end.X,0-this.end.Y,0-this.end.Z);
	}

	//the cross product of two vectors, this*Q
	public Vector3D cross(Vector3D Q)
	{
		//this	x y z
		//Q		x y z
		return new Vector3D(
			this.end.Y*Q.end.Z-this.end.Z*Q.end.Y,
			-(this.end.X*Q.end.Z-this.end.Z*Q.end.X),
			this.end.X*Q.end.Y-this.end.Y*Q.end.X
		);
	}

	//the dot product of two vectors, this.Q
	public double dot(Vector3D Q)
	{
		return this.end.X*Q.end.X+this.end.Y*Q.end.Y+this.end.Z*Q.end.Z;
	}

	public double magnitude()
	{
		return Math.sqrt(end.X*end.X + end.Y*end.Y + end.Z*end.Z);
	}

	public Vector3D unitVector()
	{
		return new Vector3D(end.X/this.magnitude(),end.Y/this.magnitude(),end.Z/this.magnitude());
	}

	public Vector3D perspectiveProject(double viewpoint)
	{
		return new Vector3D(end.perspectiveProject(viewpoint));
	}

	public boolean equals(double x, double y, double z){
		if(end.X == x && end.Y == y && end.Z == z)
			return true;
		else
			return false;
	}

	public boolean equals(Vector3D v){
		if(this.end.X == v.end.X && this.end.Y == v.end.Y && this.end.Z == v.end.Z)
			return true;
		else
			return false;
	}

	public String toString()
	{
		return "<" + end.X + ", " + end.Y + ", " + end.Z + ">";
	}

}