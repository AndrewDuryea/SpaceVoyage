/**
 * Andrew Duryea
 * September 30, 2013
 * Camera.java
 *
 * A 3D camera.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Object3D;
import Graphics3D.Vector3D;
import Graphics3D.Matrix;
import Graphics3D.Triangle3D;
import Graphics3D.Graphics3D;

public class Camera extends Object3D
{
	public Vector3D viewVec;
	public double viewDist;
	public Matrix composite;

	private double L;
	private Vector3D n;
	private double p;

	Camera(String name, Vector3D viewVec, double viewDist)
	{
		super(name, Graphics3D.standardUp,0,0,0,0,0);
		this.viewVec = viewVec;
		this.viewDist = viewDist;

		calculateVector();
		calculateMatrix();
	}

	public void setViewVector(double x, double y, double z)
	{
		viewVec.end.X = x;
		viewVec.end.Y = y;
		viewVec.end.Z = z;

		calculateVector();
		calculateMatrix();
	}

	public void setViewDistance(double d)
	{
		viewDist = d;

		calculateVector();
		calculateMatrix();
	}

	public void calculateVector()
	{
		L = viewVec.magnitude();
		n = new Vector3D(viewVec.end.X * (viewDist / L),
			viewVec.end.Y * (viewDist / L),
			viewVec.end.Z * (viewDist / L));
		p = Math.sqrt(n.end.Y * n.end.Y + n.end.Z * n.end.Z);
	}

	public void calculateMatrix()
	{
		Matrix Tn = translateMatrix(n.end.X, n.end.Y, n.end.Z);

		Matrix Rx = new Matrix(4,4);
		Rx.setValue(0,0,1);
		Rx.setValue(1,1,n.end.Z/p);
		Rx.setValue(1,2,n.end.Y/p);
		Rx.setValue(2,1,-n.end.Y/p);
		Rx.setValue(2,2,n.end.Z/p);
		Rx.setValue(3,3,1);

		Matrix Ry = new Matrix(4,4);
		Ry.setValue(0,0,p/viewDist);
		Ry.setValue(0,2,n.end.X/viewDist);
		Ry.setValue(1,1,1);
		Ry.setValue(2,0,-n.end.X/viewDist);
		Ry.setValue(2,2,p/viewDist);
		Ry.setValue(3,3,1);

		composite = Tn.multiply(Rx).multiply(Ry);
	}

	public Triangle3D orient(Vector3D U, Triangle3D t)
	{
		double ux = U.end.X*(p/viewDist) - (n.end.X/(viewDist*p))*(U.end.Y*n.end.Y+U.end.Z*n.end.Z);
		double uy = (1/p)*((U.end.Y*n.end.Z)-(U.end.Z*n.end.Y));

		double w = Math.sqrt(ux*ux + uy*uy);

		Matrix Tr = translateMatrix(-Graphics3D.R.X, -Graphics3D.R.Y, -Graphics3D.R.Z);

		Matrix Rz = new Matrix(4,4);
		Rz.setValue(0,0,uy/w);
		Rz.setValue(0,1,ux/w);
		Rz.setValue(1,0,-ux/w);
		Rz.setValue(1,1,uy/w);
		Rz.setValue(2,2,1);
		Rz.setValue(3,3,1);

		Matrix c = Tr.multiply(composite).multiply(Rz);

		Point3D P0 = t.P0.multiply(c);
		Point3D P1 = t.P1.multiply(c);
		Point3D P2 = t.P2.multiply(c);

		return new Triangle3D(P0,P1,P2, t.object);
	}

	public Vector3D orient(Vector3D U, Vector3D V)
	{
		double ux = U.end.X*(p/viewDist) - (n.end.X/(viewDist*p))*(U.end.Y*n.end.Y+U.end.Z*n.end.Z);
		double uy = (1/p)*((U.end.Y*n.end.Z)-(U.end.Z*n.end.Y));

		double w = Math.sqrt(ux*ux + uy*uy);

		Matrix Tr = translateMatrix(-Graphics3D.R.X, -Graphics3D.R.Y, -Graphics3D.R.Z);

		Matrix Rz = new Matrix(4,4);
		Rz.setValue(0,0,uy/w);
		Rz.setValue(0,1,ux/w);
		Rz.setValue(1,0,-ux/w);
		Rz.setValue(1,1,uy/w);
		Rz.setValue(2,2,1);
		Rz.setValue(3,3,1);

		Matrix c = Tr.multiply(composite).multiply(Rz);

		return new Vector3D(V.end.multiply(c));
	}

	public Point3D orient(Vector3D U, Point3D P)
	{
		double ux = U.end.X*(p/viewDist) - (n.end.X/(viewDist*p))*(U.end.Y*n.end.Y+U.end.Z*n.end.Z);
		double uy = (1/p)*((U.end.Y*n.end.Z)-(U.end.Z*n.end.Y));

		double w = Math.sqrt(ux*ux + uy*uy);

		Matrix Tr = translateMatrix(-Graphics3D.R.X, -Graphics3D.R.Y, -Graphics3D.R.Z);

		Matrix Rz = new Matrix(4,4);
		Rz.setValue(0,0,uy/w);
		Rz.setValue(0,1,ux/w);
		Rz.setValue(1,0,-ux/w);
		Rz.setValue(1,1,uy/w);
		Rz.setValue(2,2,1);
		Rz.setValue(3,3,1);

		Matrix c = Tr.multiply(composite).multiply(Rz);

		return P.multiply(c);
	}

	public String toString()
	{
		return "("+viewVec.end.X+", "+viewVec.end.Y+", "+viewVec.end.Z+")\n"+
			"dist: "+viewDist;
	}
}