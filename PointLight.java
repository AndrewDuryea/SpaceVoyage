/**
 * Andrew Duryea
 * September 30, 2013
 * PointLight.java
 *
 * And I said: "Let there be PointLight!"
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import Graphics3D.Vector3D;

public class PointLight
{
	public double distance;
	float Ipr, Ipg, Ipb;
	double ambientIntensity;
	Vector3D direction;

	public PointLight(double distance, double Ipr, double Ipg, double Ipb , Vector3D direction)
	{
		this.distance = distance;
		this.Ipr = (float)Ipr;
		this.Ipg = (float)Ipg;
		this.Ipb = (float)Ipb;
		this.direction = direction;
	}

	public PointLight(PointLight p, Vector3D direction)
	{
		this.distance = p.distance;
		this.Ipr = p.Ipr;
		this.Ipg = p.Ipg;
		this.Ipb = p.Ipb;
		this.direction = direction;
	}

	public void setDirection(double x, double y, double z)
	{
		direction.end.X = x;
		direction.end.Y = y;
		direction.end.Z = z;
	}
}