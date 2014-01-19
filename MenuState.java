/*
 * Andrew Duryea
 * October 13, 2013
 * MenuState.java
 *
 * The super class of a menu, contains the actual list of MenuItems.
 *
 * October 30, 2013: changed menu to be a 2 dimensional arrayList of menu items, which apparently is possibly, to allow for sublists
 */

package Game;

import Game.Game;
import Game.State;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public abstract class MenuState extends State implements MouseListener, MouseMotionListener{

	ArrayList<ArrayList<MenuItem>> menus;
	public int menuIndex  = 0;
	public int selectedIndex = -1;
	Font font;
	FontMetrics fm;

	boolean drawMenu = true;

	double mouseX = 0;
	double mouseY = 0;

    public MenuState(Game g) {
    	super(g);
    	menus = new ArrayList<ArrayList<MenuItem>>();

    	font = new Font("Monospaced", Font.BOLD, 30);
    }

	public void show(){
		super.show();

		selectedIndex = -1;

		//add to canvas so that origin is in the upper left of the canvas
		game.g3D.canvas.addMouseListener(this);
		game.g3D.canvas.addMouseMotionListener(this);
	}

	public void hide(){
		super.hide();

		game.g3D.canvas.removeMouseListener(this);
		game.g3D.canvas.removeMouseMotionListener(this);
	}

    public abstract void update(long delta);

    //public abstract void render(Graphics g, double width, double height);

    public void render(Graphics g, int width, int height)
    {
    	g.setFont(font);
    	fm = g.getFontMetrics();

		double offset = 0;

    	//draw menu items
		for(int i = 0; i < menus.get(menuIndex).size(); i++){
			menus.get(menuIndex).get(i).width = fm.stringWidth(menus.get(menuIndex).get(i).text);
			//menus.get(menuIndex).get(i).height = fm.getHeight();

			if(menus.get(menuIndex).get(i).centered)
				offset = fm.stringWidth(menus.get(menuIndex).get(i).text) / 2;

			if(drawMenu && (menus.get(menuIndex).get(i).contains(mouseX, mouseY, offset) || selectedIndex == i)){
				if(selectedIndex != i){
    				Game.playSound(Game.SOUND_M_MOVE);
					selectedIndex = i;
				}
				g.setColor(Color.YELLOW);
				//g.drawRect((int)(menus.get(menuIndex).get(i).x - offset),(int)menus.get(menuIndex).get(i).y,(int)menus.get(menuIndex).get(i).width,(int)menus.get(menuIndex).get(i).height);
				g.drawLine((int)(menus.get(menuIndex).get(i).x - offset - 10),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2)),(int)(menus.get(menuIndex).get(i).x - offset - 30),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2) + 15));
				g.drawLine((int)(menus.get(menuIndex).get(i).x - offset - 10),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2)),(int)(menus.get(menuIndex).get(i).x - offset - 30),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2) - 15));
				g.drawLine((int)(menus.get(menuIndex).get(i).x - offset - 30),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2) + 15),(int)(menus.get(menuIndex).get(i).x - offset - 30),(int)(menus.get(menuIndex).get(i).y + (menus.get(menuIndex).get(i).height/2) - 15));
			}
			else{
				g.setColor(Color.WHITE);
			}

			g.drawString(menus.get(menuIndex).get(i).text, (int)(menus.get(menuIndex).get(i).x - offset), (int)(menus.get(menuIndex).get(i).y + menus.get(menuIndex).get(i).height - 3));
			//g.drawRect((int)menus.get(menuIndex).get(i).x,(int)menus.get(menuIndex).get(i).y,(int)menus.get(menuIndex).get(i).width,(int)menus.get(menuIndex).get(i).height);
		}
    }

    //the method for doing something when a menuItem is activated
    public abstract void submit();

	//keyboard events
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
    	if(!drawMenu)
    		return;

    	//System.out.print(selectedIndex + " " + menus.get(menuIndex).size() + " : ");
    	if(e.getKeyCode() == KeyEvent.VK_UP){
    		selectedIndex = (selectedIndex - 1 < 0) ? (menus.get(menuIndex).size() - 1) : (selectedIndex - 1);
    		Game.playSound(Game.SOUND_M_MOVE);
    	}
    	if(e.getKeyCode() == KeyEvent.VK_DOWN){
    		selectedIndex = (selectedIndex + 1) % menus.get(menuIndex).size();
    		Game.playSound(Game.SOUND_M_MOVE);
    	}
    	if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
    		//System.out.println("\t" + this.getClass());
    		submit();
    	}
    	//System.out.println(selectedIndex);
    }
    public void keyReleased(KeyEvent e){}

	//normal mouse events
    public void mouseClicked(MouseEvent e){
    	if(!drawMenu)
    		return;

    	if(selectedIndex >= 0){
    		double offset = 0;

    		if(menus.get(menuIndex).get(selectedIndex).centered)
				offset = fm.stringWidth(menus.get(menuIndex).get(selectedIndex).text) / 2;

	    	if(menus.get(menuIndex).get(selectedIndex).contains(e.getX(), e.getY(), offset)){
    			//System.out.println("\t" + this.getClass());
	    		submit();
	    	}
    	}
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

	//mouse motion events
	public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
    }

    public int addMenu(){
    	menus.add(new ArrayList<MenuItem>());

    	return menus.size() - 1;
    }

	public void addMenuItem(int index, MenuItem m){
		if(index < menus.size() && menus.get(index) != null)
			menus.get(index).add(m);
	}
}