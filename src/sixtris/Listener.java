/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sixtris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author laurent
 */
public class Listener implements KeyListener, ActionListener {	
	protected Timer timer;

	public Listener(){
		timer = new Timer( 1, this );
	}

	public Timer getTimer(){
		return timer;
	}


	public void init( Config config ){
		/** @todo compléter l'affectation des touches ! ! */
	}

	
	
	/**
	 * Méthode déclanchée sur appui d'une touche clavier
	 * @param evt 
	 */
  public void keyPressed(KeyEvent evt){
		int keyCode = evt.getKeyCode();
		switch( keyCode ){
			case 38: // Flèche haut ou 5 pavé numérique
				SixtrisApp.getApplication().game.rotateLeft();
			break;
			case 101: // Flèche haut ou 5 pavé numérique
				SixtrisApp.getApplication().game.rotateRight();
			break;				
			case 40: // Flèche bas
				SixtrisApp.getApplication().game.fall(false);
			break;			
			case 32: // Espace
				SixtrisApp.getApplication().game.fall(true);
			break;
			case 37: // Flèche gauche
				SixtrisApp.getApplication().game.moveLeft(1);
			break;
			case 39: // Flèche droite
				SixtrisApp.getApplication().game.moveRight(1);
			break;
			case 100: // 4 pavé numérique
				SixtrisApp.getApplication().game.moveLeft(4);
			break;
			case 102: // 6 pavé numérique
				SixtrisApp.getApplication().game.moveRight(4);
			break;
			case 80: // p
				SixtrisApp.getApplication().game.pause();
			break;
			case 19: // pause
				SixtrisApp.getApplication().game.pause();
			break;
		}
		
	}

  public void keyReleased(KeyEvent evt){}  

  public void keyTyped(KeyEvent evt) {}

	/**
	 * Action en provenance du Timer
	 * @param e 
	 */
	public void actionPerformed( ActionEvent e ){
		SixtrisApp.getApplication().game.fall(false);
	}
}