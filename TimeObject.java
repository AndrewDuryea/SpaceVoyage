/*
 * Andrew Duryea
 * October 29, 2013
 * TimeObject.java
 *
 * A convienent abstract class. Once the time is reached, timeRun should be called from the Timeline class.
 */

package Game;

public abstract class TimeObject
{
	double dist;

    public abstract void timeRun(GameState gs);
}