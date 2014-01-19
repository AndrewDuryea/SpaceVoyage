/**
 * Andrew Duryea
 * September 6, 2013
 * Graphics3D.java
 *
 * A standalone class for 3D graphics. With any luck, this class should be the back end of a display.
 *
 * September 9, 2013: added a few helper methods, got rid of old commented code
 */

package Graphics3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import Graphics3D.Point3D;
import Graphics3D.Vector3D;
import Graphics3D.Object3D;
import Graphics3D.Canvas3D;
import Graphics3D.PointLight;
import Graphics3D.Camera;
import Game.Game;

public class Graphics3D
{
	public static Game game;

	public Canvas3D canvas;

	public static boolean showDebugStrings = false;

	//the reference point
	//Point3D R = new Point3D(7,-7,77);
	public static Point3D R = new Point3D(0,0,50);

	//to label or not to label?
	public static boolean labelPoints = false;

	//show surface normals if true
	public static boolean showNormals = false;

	//show face labels
	public static boolean labelFaces = false;

	//a universal pointList
	public static ArrayList<Point3D> pointList = new ArrayList<Point3D>();

	//the y axis
	public static Vector3D standardUp = new Vector3D(0,1,0);

	public static boolean bWireframe = true;
	public static boolean facetShading = false;
	public static boolean gouradShading = false;
	public static boolean phongShading = false;

	public static boolean project = true;

	//the default veiwing distance
	public static double viewpoint = 500;

	//the view vector
	public static Vector3D view = new Vector3D(0,0,-viewpoint);

	public static ArrayList<Object3D> objects = new ArrayList<Object3D>();

	public static boolean bAmbient = true, bDiffuse = true, bSpecular = true;

	//the selected light
	public static PointLight currentLight;
	public static double Iar = 0.2, Iag = 0.2, Iab = 0.2; //the ambient intensity
	public static ArrayList<PointLight> pointLightList = new ArrayList<PointLight>();

	//all cameras in a scene
	public static ArrayList<Camera> cameras = new ArrayList<Camera>();

	//the current camera being looked through
	public static Camera currentCamera;

	/*Graphics3D methods*/
	public Graphics3D(Game g)
	{
		this.game = g;

		canvas = new Canvas3D();
		canvas.setLayout(new FlowLayout());
		canvas.setBackground(Color.BLACK);

		//default camera and light
		addCamera("camera1", 0, -25, 100, 200);

		addPointLight(170,140,140,140, 100,50,-110);
	}

	public Object3D addObject(String name, double Kdr, double Kdg, double Kdb, double n, double Ks)
	{
		Object3D o = new Object3D(name, standardUp, Kdr, Kdg, Kdb, n, Ks);
		//objects.add(o);
		return o;
	}

	public Point3D addPoint(double x, double y, double z)
	{
		return new Point3D(x, y, z);
	}

	public Camera addCamera(String name, double x, double y, double z, double viewDist)
	{
		currentCamera = new Camera(name, new Vector3D(x,y,z), viewDist);
		cameras.add(currentCamera);
		return currentCamera;
	}

	public void setCurrentCameraView(double x, double y, double z){
		currentCamera.setViewVector(x, y, z);
	}

	public void setCurrentCameraDistance(double dist){
		currentCamera.setViewDistance(dist);
	}

	public PointLight addPointLight(double distance, double Ipr, double Ipg, double Ipb , double x, double y, double z)
	{
		currentLight = new PointLight(distance, Ipr, Ipg, Ipb, new Vector3D(x, y, z));
		pointLightList.add(currentLight);
		return currentLight;
	}

	public void setCurrentLightDirection(double x, double y, double z){
		currentLight.setDirection(x,y,z);
	}

	public void showDebugStrings(boolean b){
		showDebugStrings = b;
	}

	public void setBackgroundColor(Color c){
		canvas.setBackground(c);
	}

	public void render(){
		canvas.repaint();
	}

//here lies graphics stuff======================================================================

}