/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.awt.Dimension;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author laurent
 */
public class Game {
	protected Plateau plateau;
	protected Preview preview;
	protected Block block;
	protected boolean started = false;
	protected int lines;
	protected int blocks;
	protected int level;
	protected int score;
	protected int lastLevel = 34;
	protected int baseSpeed = 400;
	protected int speedStep = 10;
	protected int levelChange = 1;
	private Timer timer;
	private EventListenerList listeners;
	private boolean pause=false;
	protected Config config;



	/**
	 * Retourne le nombre de lignes complétées
	 * 
	 * @return nombre de lignes complétées
	 */
	public int getLines(){
		return lines;
	}

	
	
	/**
	 * Retourne le nombre de blocs chargés dans la partie
	 * 
	 * @return nombre de blocs
	 */
	public int getBlocks(){
		return blocks;
	}

	
	
	/**
	 * Retourne le score courant
	 * 
	 * @return score
	 */
	public int getScore(){
		return score;
	}



	/**
	 * Retorune le délai de chute courant
	 * 
	 * @return délai de chute
	 */
	public int getSpeed(){
		int newSpeed = baseSpeed - (level-1)*speedStep;
		if (newSpeed < 60) return 60;
		return newSpeed;
	}



	/**
	 * Charge une configuration
	 * 
	 * @param config Configuration du jeu
	 */
	public void setConfig( Config config ){
		this.config = config;
	}


	/**
	 * Constructeur
	 * 
	 * @param pl plateau de jeu
	 * @param pr plateau de prévisualisation
	 * @param timer Timer du jeu
	 */
	public Game( Plateau pl, Preview pr, Timer timer ){
		plateau = pl;
		preview = pr;
		listeners = new EventListenerList();		
		this.timer = timer;
	//	preview = pr;
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(ActionListener.class, listener);
	}

	public void removeActionListener(ActionListener listener) {
		listeners.remove(ActionListener.class, listener);
	}

	
	
	/**
	 * Passe au niveau suivant
	 */
	public void nextLevel(){
		level++;
		if (level > lastLevel) level = lastLevel;
		timer.setDelay(getSpeed());
		timer.setInitialDelay(getSpeed());
	}

	
	
	/**
	 * Débute la partie
	 */
	public void start(){
		blocks = 0;
		level = 0;
		lines = 0;
		if (pause) pause();
		plateau.showLoose(false);
		started = true;
		if (config != null){
			lastLevel = config.lastLevel;
			baseSpeed = config.baseSpeed;
			speedStep = config.speedStep;
			levelChange = config.levelChange;
		}
		plateau.init();
		nextLevel();
		preview.setBlock( Block.factory() );
		nextBlock();
	}



	/**
	 * Met le jeu en pause si il n'est pas en pause, et vice-versa
	 */
	public void pause(){
		pause = ! pause;
		plateau.showPause(pause);
	}



	/**
	 * Retourne true si le jeu est en pause, sinon false
	 * @return 
	 */
	public boolean paused(){
		return pause;
	}



	/**
	 * Passe au bloc suivant
	 */
	public void nextBlock(){
		if (started && ! pause){
			blocks++;
			
			block = preview.block;
			preview.setBlock( Block.factory() );
			if (! block.add(plateau) ) loose();
			plateau.refresh();
			timer.start();
			ActionEvent event = null;
			for(ActionListener listener : listeners.getListeners(ActionListener.class)) {
				if(event == null)
					event = new ActionEvent(this,0, "test");
				listener.actionPerformed(event);
			}
		}
	}

	
	
	/**
	 * Déplacement à gauche de n cases
	 * 
	 * @param cases nombre de cases de déplacement
	 */
	void moveLeft( int cases ){
		if (started && ! pause){
			for( int i=0;i<cases;i++){
				if (! block.moveLeft() ) break;
			}
			plateau.refresh();
		}
	}

	
	
	/**
	 * Déplacement à droite de n cases
	 * 
	 * @param cases nombre de cases de déplacement
	 */
	void moveRight( int cases ){
		if (started && ! pause){
			for( int i=0;i<cases;i++){
				if (! block.moveRight() ) break;
			}
			plateau.refresh();
		}
	}

	
	
	/**
	 * Rotation à gauche
	 */
	void rotateLeft(){
		if (started && ! pause){
			if ( block.rotateLeft() )
				plateau.refresh();
		}
	}

	
	
	/**
	 * Rotation à droite
	 */
	void rotateRight(){
		if (started && ! pause){
			if ( block.rotateRight() )
				plateau.refresh();
		}
	}



	/**
	 * Fait descendre une pièce de 1 case ou bien jusqu'en bas (toFloor)
	 * 
	 * @param toFloor Fait descendre la pièce jusqu'en bas du plateau (true) ou no  (false)
	 */
	void fall( boolean toFloor ){
		boolean lastFall=true;
		if (started && ! pause){
			if (toFloor){
				score += Math.round( calcScore() / 10 );
				System.out.println( Math.round( calcScore() / 10 ) );
				while( lastFall ){
					lastFall = block.moveDown();
				}
			}else{
				lastFall = block.moveDown();
			}

			if (! lastFall ){
				timer.stop();
				if (0 == removeCompleteLines()){
					plateau.refresh();
				}
				nextBlock();
			}else{
				plateau.refresh();
			}
		}
	}

	
	
	/**
	 * Fin de la partie
	 */
	void stop(){
		if (started){
			started = false;
		}
	}

	
	
	/**
	 * Partie perdue
	 */
	void loose(){
		stop();
		plateau.showLoose(true);
	}

	private int removeCompleteLines(){
		int h,w,h2,w2,width,height;
		boolean complete;
		byte completeLines;

		width = plateau.getSize().width;
		height = plateau.getSize().height;
		completeLines = 0;
		for( h=height-1; h>-plateau.ecart-1; h-- ){
			complete = true;
			for( w=0;w<width;w++ ){
				if (0 == plateau.getCase(w, h)){
					complete = false;
					break;
				}
			}
			if (complete){
				completeLines++;
				lines++;
				for( h2=h;h2>-plateau.ecart;h2-- ){
					for( w2=0; w2<width;w2++){
						plateau.setCase(w2, h2, plateau.getCase(w2, h2-1));
					}
				}
				for( w2=0; w2<width;w2++){
					plateau.setCase(w2, 0, 0);
				}
				h++;
			}
		}
		if (completeLines > 0){
			if (lines > 0 && 0 == lines % levelChange ){
				nextLevel();
				if (completeLines > 3)
					score += calcScore() * plateau.getSize().width / plateau.getSize().height * completeLines * 4;
				else
					score += calcScore() * plateau.getSize().width / plateau.getSize().height * completeLines * 2;

				int ii = lines % levelChange;
			}
			plateau.refresh();
		}
		return completeLines;
	}

	

	/**
	 * Calcule une graine de score à partir de la vitsse de chute d'une pièce.
	 * 
	 * @return int
	 */
	private int calcScore(){
		double x1 = 2200, x2=200, x3=0.3, x4=13;
		int delay = timer.getDelay();
		return (int)Math.round((x1-(Math.log(delay)*x2+delay*x3))/x4);
	}

}
