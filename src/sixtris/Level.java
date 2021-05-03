/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

/**
 *
 * @author laurent
 */
public class Level {
	protected int index;
	protected int speed;

	public final int lastLevel = 3;

	public void next(){
		index++;
		if (index > lastLevel) index = lastLevel;
		speed = makeSpeed();
	}

	public void init(){
		index = 1;
		speed = makeSpeed();
	}

	protected int makeSpeed(){
		int baseSpeed = 400;
		int speedDecr = 37;

		return baseSpeed - (index-1)*speedDecr;
	}

	public int getSpeed(){
		return speed;
	}
	
	public int getIndex(){
		return index;
	}

	public Level(){
		
	}
}
