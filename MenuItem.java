/*
 * Andrew Duryea
 * October 12, 2013
 * MenuItem.java
 *
 * A menu item, contains the text of the menu item, and its bounding box.
 * Whether or not a point is contained by this items bounding box is checked
 * through the contains method.
 */

package Game;

public class MenuItem
{

	String text = "";

	//bounding box
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean centered = true;

    public MenuItem(String text, double x, double y, double width, double height){
    	this.text = text;
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }

    public MenuItem(String text, double x, double y, double width, double height, boolean centered){
    	this(text, x, y, width, height);
    	this.centered = centered;
    }

    //returns true if the point is contained in the bounding box
    public boolean contains(double xPos, double yPos, double offset){

    	//System.out.println(xPos + " : " + x + ", " + width);
    	//System.out.println(yPos + " : " + y + ", " + height);

    	if(xPos < (x - offset) || xPos > (x - offset + width))
    		return false;

    	if(yPos < y || yPos > (y + height))
    		return false;

    	return true;
    }
}