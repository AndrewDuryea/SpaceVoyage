/*
 * Andrew Duryea
 * October 29, 2013
 * Timeline.java
 *
 * The timeline, fires TimeObjects at the appropriate times.
 */

package Game;

import java.util.ArrayList;

import Game.TimeObject;
import Game.GameState;

public class Timeline
{
	GameState gameState;

	ArrayList<TimeObject> events;

	public double currentDist; //the current distance traveled by the player
	int currentIndex; //the current index in events

	long time = 0;

    public Timeline(GameState gs){
    	this.gameState = gs;

    	events = new ArrayList<TimeObject>();

    	currentDist = 0;
    	currentIndex = 0;
    }

	public void update(long delta){
	/*	if(events.size() <= 0)
			return;
*/
		//time += delta;
		currentDist += Player.VELOCITY * delta;
		//System.out.println((time/1000.0) + " : " + currentDist);

		while(currentIndex < events.size() &&
				currentDist >= events.get(currentIndex).dist){
			events.get(currentIndex).timeRun(gameState);
			currentIndex++;
		}
	}

	public void addObject(TimeObject t){
		events.add(t);
	}
}