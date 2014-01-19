/*
 * Andrew Duryea
 * October 7, 2013
 * Reticle.java
 *
 * The reticule.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import Graphics3D.Graphics3D;
import Graphics3D.Point3D;
import Graphics3D.Vector3D;
import Graphics3D.Triangle3D;
import Graphics3D.Object3D;
import Game.GameState;

public class Reticle
{

	Point3D firingPoint;

	GameState gameState;

    public Reticle(GameState gs, Point3D fp){
    	this.gameState = gs;
    	this.firingPoint = fp;
    }

    public void render(Graphics g, int width, int height){
		Point3D fp = new Point3D(firingPoint);
		fp.Z += 3100;

		g.setColor(Color.WHITE);

		synchronized(gameState.asteroidList){
			//use an iterator to avoid concurrent modification exception
	    	Iterator itr = gameState.asteroidList.iterator();
	    	while(itr.hasNext()){
	    		Asteroid a = (Asteroid) itr.next();

	    		if(intersects(firingPoint, fp, a.model)){
	    			g.setColor(Color.RED);
	    			break;
	    		}
	    	}
		}

		fp.Z -= 2600;

		fp = Graphics3D.currentCamera.orient(Graphics3D.standardUp, fp).perspectiveProject(Graphics3D.viewpoint);

		//fp.drawPoint(g, width, height);
		g.drawLine((int)(fp.X + width + 10), (int)(-fp.Y + height + 10), (int)(fp.X + width - 10), (int)(-fp.Y + height - 10));
		g.drawLine((int)(fp.X + width - 10), (int)(-fp.Y + height + 10), (int)(fp.X + width + 10), (int)(-fp.Y + height - 10));
    }

    public boolean intersects(Point3D p0, Point3D p1, Object3D o){
    	/*Input: a 3D segment S from point P0 to point P1
           a 3D convex polyhedron OMEGA with n faces F0,...,Fn-1 and
                Vi = a vertex for each face Fi
                ni = an outward normal vector for each face Fi

		    if (P0 == P1) then S is a single point, so {
		        test for point inclusion of P0 in OMEGA; and
		        return the test result (TRUE or FALSE);
		    }

		    Initialize:
		        tE = 0 for the maximum entering segment parameter;
		        tL = 1 for the minimum leaving segment parameter;
		        dS = P1 - P0 is the segment direction vector;

		    for (each face Fi of OMEGA; i=0,n-1)
		    {
		        N = - dot product of (P0 - Vi) and ni;
		        D = dot product of dS and ni;
		        if (D == 0) then S is parallel to the face Fi {
		            if (N < 0) then P0 is outside the face Fi
		                 return FALSE since S cannot intersect OMEGA;
		            else S cannot enter or leave OMEGA across face Fi {
		                 ignore face Fi and
		                 continue to process the next face;
		            }
		        }

		        Put t = N / D;
		        if (D < 0) then segment S is entering OMEGA across face Fi {
		            New tE = max of current tE and this t
		            if (tE > tL) then segment S enters OMEGA after leaving
		                 return FALSE since S cannot intersect OMEGA
		        }
		        else (D > 0) then segment S is leaving OMEGA across face Fi {
		            New tL = min of current tL and this t
		            if (tL < tE) then segment S leaves OMEGA before entering
		                 return FALSE since S cannot intersect OMEGA
		        }
		    }

		    Output: [Note: to get here, one must have tE <= tL]
		    there is a valid intersection of S with OMEGA
		        from the entering point: P(tE) = P0 + tE * dS
		        to the leaving point:    P(tL) = P0 + tL * dS
		    return TRUE*/

		double tE = 0;
		double tL = 1;
		Vector3D direction = new Vector3D(p0, p1);

		for(Triangle3D face : o.getFaces()){
			double N = 0 - (new Vector3D(face.P0, p0)).dot(face.getNormal());
			double D = direction.dot(face.getNormal());

			if(D == 0){
				if(N < 0)
					return false;
				else
					continue;
			}

			double t = N / D;

			if(D < 0){
				tE = (tE > t) ? tE : t;
				if(tE > tL)
					return false;
			}
			else{
				tL = (tL > t) ? t : tL;
				if(tL < tE)
					return false;
			}
		}

		return true;
    }
}