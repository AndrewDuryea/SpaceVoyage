/**
 * Andrew Duryea
 * September 30, 2013
 * Triangle.java
 *
 * This class is a simple triangle in 3D
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Graphics3D;
import Graphics3D.Point3D;
import Graphics3D.Vector3D;
import Graphics3D.Object3D;
import Graphics3D.Edge;
import java.awt.*;
import java.util.ArrayList;

public class Triangle3D
{
	public Point3D P0;
	public Point3D P1;
	public Point3D P2;

	public Object3D object; //the object this triangle belongs to

	Triangle3D(Point3D P0, Point3D P1, Point3D P2)
	{
		this.P0 = P0;
		this.P1 = P1;
		this.P2 = P2;
	}

	Triangle3D(Point3D P0, Point3D P1, Point3D P2, Object3D object)
	{
		this.P0 = P0;
		this.P1 = P1;
		this.P2 = P2;
		this.object = object;
	}

	public Point3D getCenter()
	{
		return new Point3D(
			(this.P0.X+this.P1.X+this.P2.X)/3,
			(this.P0.Y+this.P1.Y+this.P2.Y)/3,
			(this.P0.Z+this.P1.Z+this.P2.Z)/3
		);
	}

	public Vector3D getNormal()
	{
		Vector3D P = new Vector3D(this.P0, this.P1);
		Vector3D Q = new Vector3D(this.P0, this.P2);
		return Q.cross(P);
	}

	public String toString()
	{
		return "P0: "+P0+"\nP1: "+P1+"\nP2: "+P2;
	}

	public Triangle3D perspectiveProject()
	{
		return new Triangle3D(P0.perspectiveProject(Graphics3D.viewpoint), P1.perspectiveProject(Graphics3D.viewpoint), P2.perspectiveProject(Graphics3D.viewpoint), this.object);
	}

	public boolean isShown()
	{
		Vector3D point = new Vector3D(this.getCenter());

		//System.out.println(Graphics3D.currentCamera.viewVec.dot(this.perspectiveProject().getNormal()) - point.dot((this.perspectiveProject().getNormal())));
		//the first two ifs below seem to work the same, but the last one does not work
		//if(view.dot(this.perspectiveProject().getNormal()) <= 0)
		if(Graphics3D.currentCamera.viewVec.dot(this.perspectiveProject().getNormal()) - point.dot((this.perspectiveProject().getNormal())) <= 0)
		//if(view.dot(this.getNormal()) <= 0)
			return false;

		return true;
	}

	public void drawTriangle(Graphics g, int i, double xOffset, double yOffset)
	{
		Triangle3D oriented = Graphics3D.currentCamera.orient(object.up, this);

		if(!Graphics3D.bWireframe && !oriented.isShown() &&!object.alwaysWireframe)
			return;

		Triangle3D ps = oriented.perspectiveProject();

		if(!Graphics3D.bWireframe &&!object.alwaysWireframe) //Thread here?
			ps.triangleFill(g, xOffset, yOffset, this.getNormal(), this.getCenter());
		else
		{
			g.setColor(Color.YELLOW);

			//p0->p1
			g.drawLine((int)(ps.P0.X + xOffset),
				(int)(-ps.P0.Y + yOffset),
				(int)(ps.P1.X + xOffset),
				(int)(-ps.P1.Y + yOffset));
			//p1->p2
			g.drawLine((int)(ps.P1.X + xOffset),
				(int)(-ps.P1.Y + yOffset),
				(int)(ps.P2.X + xOffset),
				(int)(-ps.P2.Y + yOffset));
			//p2->p0
			g.drawLine((int)(ps.P2.X + xOffset),
				(int)(-ps.P2.Y + yOffset),
				(int)(ps.P0.X + xOffset),
				(int)(-ps.P0.Y + yOffset));
		}

		if(object.labelPoints)
		{
			g.setColor(Color.yellow);

			//label points
        	g.drawString(this.P0.pointID,(int)(ps.P0.X + xOffset),(int)(-ps.P0.Y + yOffset));
        	g.drawString(this.P1.pointID,(int)(ps.P1.X + xOffset),(int)(-ps.P1.Y + yOffset));
        	g.drawString(this.P2.pointID,(int)(ps.P2.X + xOffset),(int)(-ps.P2.Y + yOffset));

			g.setColor(Color.black);
		}

		if(object.labelFaces)
		{
			//label faces
			g.setColor(Color.blue);

			Point3D psPoint = oriented.getCenter().perspectiveProject(Graphics3D.viewpoint);
    		g.drawString("F"+i,(int)(psPoint.X + xOffset),(int)(-psPoint.Y + yOffset));

			g.setColor(Color.black);
		}

		if(object.showNormals)
		{
			//show surface normals
			Point3D center = oriented.getCenter().perspectiveProject(Graphics3D.viewpoint);
			Point3D end = oriented.getNormal().unitVector().end;
			end = end.perspectiveProject(Graphics3D.viewpoint);
			end.X = end.X * 50;
			end.Y = end.Y * 50;

			g.setColor(Color.red);

			g.drawLine((int)(center.X + xOffset),(int)(-center.Y + yOffset),
				(int)(end.X+center.X + xOffset),
				(int)(-end.Y-center.Y + yOffset));
			g.drawString(""+i,
				(int)(end.X+center.X + xOffset),
				(int)(-end.Y-center.Y + yOffset));

			g.setColor(Color.black);
		}
	}

	//dhould only be called for projected triangles
	public void triangleFill(Graphics g, double xOffset, double yOffset, Vector3D facetNormal, Point3D facetPoint)
	{
		ArrayList<Edge> edgeList = new ArrayList<Edge>();

		Vector3D V1=null, V2=null;

		//the intensities at each vertex
		Vector3D I0 = null, I1 = null, I2 = null;

		//if facet shading, then V1 is the intensity vector for the face and V2 is null
		if(Graphics3D.facetShading)
		{
			V1 = facetNormal.start.getIntensityVector(facetNormal.unitVector(), object, facetPoint);
		}

		//if gourad shading, then V1 is the left hand intensity, and V2 is the right hand intensity
		else if(Graphics3D.gouradShading)
		{
			I0 = P0.getNormal();
			I0 = I0.start.getIntensityVector(I0, object, P0);

			I1 = P1.getNormal();
			I1 = I1.start.getIntensityVector(I1, object, P1);

			I2 = P2.getNormal();
			I2 = I2.start.getIntensityVector(I2, object, P2);
		}

		//if phong shading, then V1 is the left hand normal, and V2 is the right hand normal
		else if(Graphics3D.phongShading)
		{
			I0 = P0.getNormal();

			I1 = P1.getNormal();

			I2 = P2.getNormal();
		}

		if(P0.Y != P1.Y)
			edgeList.add(new Edge(P0, P1, I0, I1));
		if(P1.Y != P2.Y)
			edgeList.add(new Edge(P1, P2, I1, I2));
		if(P2.Y != P0.Y)
			edgeList.add(new Edge(P2, P0, I2, I0));


		//sort edges on Ymax using bubble sort
		Edge temp;

		for(int i = 0; i < edgeList.size(); i++)
		{
			for(int j = 0; j < edgeList.size()-1; j++)
			{
				if(edgeList.get(j).Ymax < edgeList.get(j+1).Ymax)
				{
					//swap edges
					temp = edgeList.get(j+1);
					edgeList.set(j+1, edgeList.get(j));
					edgeList.set(j, temp);
				}
				else if(edgeList.get(j).Ymax == edgeList.get(j+1).Ymax &&
					edgeList.get(j).Ymin < edgeList.get(j+1).Ymin)
				{
					//swap edges
					temp = edgeList.get(j+1);
					edgeList.set(j+1, edgeList.get(j));
					edgeList.set(j, temp);
				}
			}
		}

		int left = 0;
		int right = 1;
		double Ymin = edgeList.get(0).Ymin;

		for(Edge e : edgeList)
		{
			if(e.Ymin < Ymin)
				Ymin = e.Ymin;
		}

		ArrayList<Edge> current = new ArrayList<Edge>();

		for(int y = (int)edgeList.get(0).Ymax; y > Ymin; y--)
		{
			current.clear();
			for(Edge e : edgeList)
			{
				if(e.Ymax > y && e.Ymin < y)
				{
					current.add(e);
				}
			}


			if(current.size() > 1)
			{
				if(current.get(0).nextX > current.get(1).nextX)
				{
					//swap edges
					temp = current.get(1);
					current.set(1, current.get(0));
					current.set(0, temp);
				}

				if(Graphics3D.gouradShading || Graphics3D.phongShading)
				{
					V1 = current.get(0).nextV;
					V2 = current.get(1).nextV;
				}

				//Thread here?
				shadeLine(g,(int)(current.get(0).nextX + xOffset), (int)(-y + yOffset), (int)(current.get(1).nextX + xOffset), V1, V2);

				current.get(0).incrementX();
				current.get(1).incrementX();

				current.get(0).incrementV();
				current.get(1).incrementV();
			}
		}
	}

	//facet for now
	public void shadeLine(Graphics g, int x1, int y1, int x2, Vector3D V1, Vector3D V2)
	{
		Vector3D interval, intensity;
		Vector3D V = V1;
		//if facet shading, then V1 is the intensity vector for the face and V2 is null
		if(Graphics3D.facetShading)
		{
			g.setColor(new Color((int)(255*V1.end.X),(int)(255*V1.end.Y),(int)(255*V1.end.Z), (int)(255 * object.alpha)));
			for(int x = x1; x <= x2; x++)
			{
				g.drawLine(x,y1,x,y1);
			}
			g.setColor(Color.black);
			return;
		}

		//if gourad shading, then V1 is the left hand intensity, and V2 is the right hand intensity
		else if(Graphics3D.gouradShading && (x2-x1) > 0)
		{
			interval = V2.minus(V1).multiplyScaler(1.0/(x2-x1));
			for(int x = x1; x <= x2; x++)
			{
				V.end.X = (V.end.X > 1) ? 1 : V.end.X;
				V.end.Y = (V.end.Y > 1) ? 1 : V.end.Y;
				V.end.Z = (V.end.Z > 1) ? 1 : V.end.Z;

				V.end.X = (V.end.X < 0) ? 0 : V.end.X;
				V.end.Y = (V.end.Y < 0) ? 0 : V.end.Y;
				V.end.Z = (V.end.Z < 0) ? 0 : V.end.Z;

				g.setColor(new Color((int)(255*V.end.X),(int)(255*V.end.Y),(int)(255*V.end.Z), (int)(255 * object.alpha)));
				g.drawLine(x,y1,x,y1);

				V = V.add(interval);
			}
			g.setColor(Color.black);

			return;
		}

		//if phong shading, then V1 is the left hand normal, and V2 is the right hand normal
		else if(Graphics3D.phongShading && (x2-x1) > 0)
		{
			//interpolate V1 -> V2
			//run through shader
			interval = V2.minus(V1).multiplyScaler(1.0/(x2-x1));
			for(int x = x1; x <= x2; x++)
			{
				intensity = V.start.getIntensityVector(V, object, V.end);

				g.setColor(new Color((int)(255*intensity.end.X),(int)(255*intensity.end.Y),(int)(255*intensity.end.Z), (int)(255 * object.alpha)));
				g.drawLine(x,y1,x,y1);

				V = V.add(interval);
			}
			g.setColor(Color.black);
			return;
		}
	}
}