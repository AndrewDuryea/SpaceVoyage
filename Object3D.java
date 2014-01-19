/**
 * Andrew Duryea
 * September 30, 2013
 * Object3D.java
 *
 * A 3D object, a collection of points and faces.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Graphics3D;
import Graphics3D.Point3D;
import Graphics3D.Vector3D;
import Graphics3D.Triangle3D;
import Graphics3D.Matrix;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Object3D
{
	ArrayList<Point3D> originalPoints = new ArrayList<Point3D>();
	ArrayList<Point3D> points = new ArrayList<Point3D>();
	ArrayList<Triangle3D> triangleList = new ArrayList<Triangle3D>();

	Vector3D up;

	double currentScale = 100;

	double currentXTheta = 0;
	double currentYTheta = 0;
	double currentZTheta = 0;

	public boolean alwaysWireframe = false;
	boolean labelPoints = false;
	boolean labelFaces = false;
	boolean showNormals = false;

	public String name;

	//lighting stuff
	public double Kdr, Kdg, Kdb; //diffuse reflectivity

	public double alpha = 1;

	double n; // shinyness
	double Ks; //specular constant

	Object3D(String name, Vector3D up, double Kdr, double Kdg, double Kdb, double n, double Ks)
	{
		this.name = name;
		this.up = up;

		this.Kdr = Kdr;
		this.Kdg = Kdg;
		this.Kdb = Kdb;

		this.n = n;
		this.Ks = Ks;
	}

	public void addPoint(double X, double Y, double Z)
	{
		points.add(new Point3D(X, Y, Z, points.size()));
		originalPoints.add(new Point3D(X, Y, Z));
	}

	public void addPoint(double X, double Y, double Z, String name)
	{
		points.add(new Point3D(X, Y, Z, name));
		originalPoints.add(new Point3D(X, Y, Z));
	}

	public void printPoints()
	{
		for(Point3D p : points)
		{
			System.out.println(p);
		}
	}

	public void reset()
	{
		for(int i = 0; i < points.size(); i++)
		{
			points.get(i).setPoint(originalPoints.get(i).X,
									originalPoints.get(i).Y,
									originalPoints.get(i).Z);
		}

		currentScale = 100;
		currentXTheta = currentYTheta = currentZTheta = 0;
	}

	public void addTriangle(int P0, int P1, int P2)
	{
		Triangle3D t = new Triangle3D(points.get(P0),points.get(P1),points.get(P2),this);
		triangleList.add(t);
		points.get(P0).addFace(t);
		points.get(P1).addFace(t);
		points.get(P2).addFace(t);
	}

	public void printTriangles()
	{
		for(Triangle3D t : triangleList)
		{
			System.out.println(t);
		}
	}

	public Point3D getCenter()
	{
		double x = 0;
		double y = 0;
		double z = 0;
		int i = 0;

		for(i = 0; i < points.size(); i++)
		{
			x += points.get(i).X;
			y += points.get(i).Y;
			z += points.get(i).Z;
		}

		return new Point3D(x/i, y/i, z/i);
	}

	public void drawObject(Graphics g, double xOffset, double yOffset)
	{
		//sort the faces
		Collections.sort(triangleList, new Comparator<Triangle3D>(){
			public int compare(Triangle3D a, Triangle3D b){
				return (int)(b.getCenter().Z - a.getCenter().Z);
			}
		});

		for(int i = 0; i < triangleList.size(); i++)
		{
			triangleList.get(i).drawTriangle(g, i, xOffset, yOffset);
		}
	}

	public void translate(Point3D p){
		translate(p.X, p.Y, p.Z);
	}

	//translate the object, passing the offsets
	public void translateDyn(double x, double y, double z)
	{
		//translate, move to the destination based on the reference point above
		Matrix m = translateMatrix(x, y, z);

		for(Point3D p : points)
		{
			Point3D point = p.multiply(m);

			p.X = point.X;
			p.Y = point.Y;
			p.Z = point.Z;
		}
	}

	//translate the object, passing the destination coordinates
	public void translate(double x, double y, double z)
	{
		//reference point
		Point3D center = this.getCenter();

		//translate, move to the destination based on the reference point above
		Matrix m = translateMatrix(x-center.X, y-center.Y, z-center.Z);

		for(Point3D p : points)
		{
			Point3D point = p.multiply(m);

			p.X = point.X;
			p.Y = point.Y;
			p.Z = point.Z;
		}
	}

	public Matrix translateMatrix(double x, double y, double z)
	{
		Matrix ret = new Matrix(4,4);

		//set diagonal to 1
		ret.setValue(0,0,1);
		ret.setValue(1,1,1);
		ret.setValue(2,2,1);
		ret.setValue(3,3,1);

		//set translation
		ret.setValue(3,0,x);
		ret.setValue(3,1,y);
		ret.setValue(3,2,z);

		return ret;
	}

	public void scale(double scaleFactor)
	{
		scaleXYZ(scaleFactor/currentScale, scaleFactor/currentScale, scaleFactor/currentScale);
		currentScale = scaleFactor;
	}

	public void scaleXYZ(double xScale, double yScale, double zScale)
	{
		Matrix m;

		//reference point
		Point3D center = this.getCenter();

		//translate, move to the origin based on the reference point above
		Matrix trans1 = translateMatrix(0-center.X, 0-center.Y, 0-center.Z);

		//scale
		Matrix scale = scaleMatrix(xScale, yScale, zScale);

		//translate, move the reference point back
		Matrix trans2 = translateMatrix(center.X-0, center.Y-0, center.Z-0);

		Matrix composite;

		composite = trans1.multiply(scale);
		composite = composite.multiply(trans2);

		for(Point3D p : points)
		{
			Point3D point = p.multiply(composite);

			p.X = point.X;
			p.Y = point.Y;
			p.Z = point.Z;
		}
	}

	public Matrix scaleMatrix(double xScale, double yScale, double zScale)
	{
		Matrix ret = new Matrix(4,4);

		//set diagonal to scale value
		ret.setValue(0,0,xScale);
		ret.setValue(1,1,yScale);
		ret.setValue(2,2,zScale);
		ret.setValue(3,3,1);

		return ret;
	}

	public void rotateX(double theta)
	{
		theta = theta * (Math.PI / 180);

		Matrix m = new Matrix(4,4);

		//rotate about the x-axis
		m.setValue(0,0,1);
		m.setValue(1,1,Math.cos(theta-currentXTheta));
		m.setValue(1,2,Math.sin(theta-currentXTheta));
		m.setValue(2,1,-Math.sin(theta-currentXTheta));
		m.setValue(2,2,Math.cos(theta-currentXTheta));
		m.setValue(3,3,1);

		rotate(m);

		currentXTheta = theta % (4 * Math.PI);
	}

	public void rotateY(double theta)
	{
		theta = theta * (Math.PI / 180);

		Matrix m = new Matrix(4,4);

		//rotate about the y-axis
		m.setValue(0,0,Math.cos(theta-currentYTheta));
		m.setValue(0,2,-Math.sin(theta-currentYTheta));
		m.setValue(1,1,1);
		m.setValue(2,0,Math.sin(theta-currentYTheta));
		m.setValue(2,2,Math.cos(theta-currentYTheta));
		m.setValue(3,3,1);

		rotate(m);

		currentYTheta = theta % (4 * Math.PI);
	}

	public void rotateZ(double theta)
	{
		theta = theta * (Math.PI / 180);

		Matrix m = new Matrix(4,4);

		//rotate about the z-axis
		m.setValue(0,0,Math.cos(theta-currentZTheta));
		m.setValue(0,1,Math.sin(theta-currentZTheta));
		m.setValue(1,0,-Math.sin(theta-currentZTheta));
		m.setValue(1,1,Math.cos(theta-currentZTheta));
		m.setValue(2,2,1);
		m.setValue(3,3,1);

		rotate(m);

		currentZTheta = theta % (4 * Math.PI);
	}

	//make composite matrix of rotations
	/*public void rotate(double Xtheta, double Ytheta, double Ztheta)
	{
		Xtheta = Xtheta * (Math.PI / 180);
		Ytheta = Ytheta * (Math.PI / 180);
		Ztheta = Ztheta * (Math.PI / 180);

		Matrix m = new Matrix(4,4);

		//rotate about the x-axis
		m.setValue(0,0,1);
		m.setValue(1,1,Math.cos(theta-currentXTheta));
		m.setValue(1,2,Math.sin(theta-currentXTheta));
		m.setValue(2,1,-Math.sin(theta-currentXTheta));
		m.setValue(2,2,Math.cos(theta-currentXTheta));
		m.setValue(3,3,1);

		//rotate about the y-axis
		m.setValue(0,0,Math.cos(theta-currentYTheta));
		m.setValue(0,2,-Math.sin(theta-currentYTheta));
		m.setValue(1,1,1);
		m.setValue(2,0,Math.sin(theta-currentYTheta));
		m.setValue(2,2,Math.cos(theta-currentYTheta));
		m.setValue(3,3,1);

		//rotate about the z-axis
		m.setValue(0,0,Math.cos(theta-currentZTheta));
		m.setValue(0,1,Math.sin(theta-currentZTheta));
		m.setValue(1,0,-Math.sin(theta-currentZTheta));
		m.setValue(1,1,Math.cos(theta-currentZTheta));
		m.setValue(2,2,1);
		m.setValue(3,3,1);
	}*/

	public void rotate(Matrix rotate)
	{
		Matrix m;

		//reference point
		Point3D center = this.getCenter();

		//translate, move to the origin based on the reference point above
		Matrix trans1 = translateMatrix(0-center.X, 0-center.Y, 0-center.Z);

		//translate, move the reference point back
		Matrix trans2 = translateMatrix(center.X-0, center.Y-0, center.Z-0);

		Matrix composite;

		composite = trans1.multiply(rotate);
		composite = composite.multiply(trans2);

		for(Point3D p : points)
		{
			Point3D point = p.multiply(composite);

			p.X = point.X;
			p.Y = point.Y;
			p.Z = point.Z;
		}
	}

	//only one direction can be rotated at a time, the other parametes should be zero
	public Matrix rotateMatrix(double xTheta, double yTheta, double zTheta)
	{
		Matrix ret = new Matrix(4,4);

		//rotate about the x-axis
		if(xTheta != 0 && yTheta == 0 && zTheta == 0)
		{
			ret.setValue(0,0,1);
			ret.setValue(1,1,Math.cos(xTheta));
			ret.setValue(1,2,Math.sin(xTheta));
			ret.setValue(2,1,-Math.sin(xTheta));
			ret.setValue(2,2,Math.cos(xTheta));
			ret.setValue(3,3,1);
		}
		//rotate about the y-axis
		if(xTheta == 0 && yTheta != 0 && zTheta == 0)
		{
			ret.setValue(0,0,Math.cos(yTheta));
			ret.setValue(0,2,-Math.sin(yTheta));
			ret.setValue(1,1,1);
			ret.setValue(2,0,Math.sin(yTheta));
			ret.setValue(2,2,Math.cos(yTheta));
			ret.setValue(3,3,1);
		}
		//rotate about the z-axis
		if(xTheta == 0 && yTheta == 0 && zTheta != 0)
		{
			ret.setValue(0,0,Math.cos(zTheta));
			ret.setValue(0,1,Math.sin(zTheta));
			ret.setValue(1,0,-Math.sin(zTheta));
			ret.setValue(1,1,Math.cos(zTheta));
			ret.setValue(2,2,1);
			ret.setValue(3,3,1);
		}

		return ret;
	}

	//rotate dynamically (offset the current rotation)
	public void rotateDyn(double x, double y, double z){
		this.rotateX(getCurrentXDegrees() + x);
		this.rotateY(getCurrentYDegrees() + y);
		this.rotateZ(getCurrentZDegrees() + z);
	}

	public double getCurrentXDegrees(){
		return currentXTheta * 180 / Math.PI;
	}

	public double getCurrentYDegrees(){
		return currentYTheta * 180 / Math.PI;
	}

	public double getCurrentZDegrees(){
		return currentZTheta * 180 / Math.PI;
	}

	public void setColor(double r, double g, double b){
		Kdr = r;
		Kdg = g;
		Kdb = b;
	}

	public void invertColor(){
		Kdr = 1-Kdr;
		Kdg = 1-Kdg;
		Kdb = 1-Kdb;
	}

	public Point3D getPointAtIndex(int i){
		return points.get(i);
	}

	public ArrayList<Triangle3D> getFaces(){
		return triangleList;
	}
}
