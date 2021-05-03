/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author laurent
 */
public class Theme {

	/**
	 * Nom du thème (correspond au dossier)
	 */
	private String name = "Default";
	private String sep = System.getProperty("file.separator");
	private String path;
	private IniFile file;

	protected Color backgroundColor = Color.WHITE;

	protected BufferedImage background;
	protected BufferedImage border;
	protected BufferedImage shadow;
	protected BufferedImage pause;
	protected BufferedImage loose;

	/**
	 * Buffers des images
	 * 0 à 9 = cases
	 *
	 */
	protected BufferedImage[] casesImages = new BufferedImage[10];

	protected int shadowTop = -10;
	protected int shadowLeft = -10;

	protected int borderTop = 0;
	protected int borderLeft = 0;

	protected int caseTop = 0;
	protected int caseLeft = 0;	

	protected Rectangle shadowZone;
	protected Rectangle caseZone;
	protected Rectangle borderZone;

	/**
	 * Taille du pas en largeur
	 */
	protected int stepWidth = 20;

	/**
	 * Hauteur du pas en descente
	 */
	protected int stepHeight = 24;

	/**
	 * Différence de pas entre les cases paires et impaires en largeur !
	 */
	protected int stepCol = 12;


	public Color getBackgroundColor(){
		return backgroundColor;
	}
	
	

	/**
	 * Retourne l'image de fond du jeu
	 * @return BufferedImage
	 */
	public BufferedImage getBackground(){
		return background;
	}



	/**
	 * Retourne la case de bordure
	 * @return BufferedImage
	 */
	public BufferedImage getBorder(){
		return border;
	}



	/**
	 * Retourne l'ombre d'une case
	 * @return BufferedImage
	 */
	public BufferedImage getShadow(){
		return shadow;
	}


	public BufferedImage getPause(){
		return pause;
	}
	
	
	public BufferedImage getLoose(){
		return loose;
	}
	
	
	public Rectangle getShadowZone(){
		return shadowZone;
	}
	
	
	public Rectangle getCaseZone(){
		return caseZone;
	}

	
	public Rectangle getBorderZone(){
		return borderZone;
	}



	/**
	 *	 
	 *
	 * @return int
	 */
	public int getShadowLeft(){
		return shadowLeft;
	}



	/**
	 *	 
	 *
	 * @return int
	 */
	public int getShadowTop(){
		return shadowTop;
	}



	/**
	 *	 
	 *
	 * @return int
	 */
	public int getStepWidth(){
		return stepWidth;
	}



	/**
	 *	 
	 *
	 * @return int
	 */
	public int getStepHeight(){
		return stepHeight;
	}



	/**
	 *	 
	 *
	 * @return int
	 */
	public int getStepCol(){
		return stepCol;
	}
	
	
	/**
	 * Retourne une image de case, en fonction du numéro
	 *
	 * @param index
	 * @return BufferedImage
	 */
	public BufferedImage getCaseImage( int index ){
		return casesImages[index-1];
	}



	/**
	 * Charge un thème en mémoire
	 */
	public void load( String themeName ){
		String fileName, p;
		name = themeName;

		p = path + name + sep;

		try {
			file = new IniFile( p + "theme.ini" );

			if (file.isset("background","image")){
				fileName = p + file.get("background","image");
				background = ImageIO.read(new File(fileName));				
			}

			if (file.isset("background","color")){
				backgroundColor = Color.decode( "0x"+file.get("background","color").substring(1) );
			}


			if (file.isset("border","image")){
				fileName = p + file.get("border","image");
				border = ImageIO.read(new File(fileName));
			}

			if (file.isset("border","left")){
				borderLeft = Integer.decode( file.get("border","left") ).intValue();
			}
			
			if (file.isset("border","top")){
				borderTop = Integer.decode( file.get("border","top") ).intValue();
			}
			
			
			if (file.isset("shadow","image")){
				fileName = p + file.get("shadow","image");
				shadow = ImageIO.read(new File(fileName));
			}
			
			if (file.isset("pause","image")){
				fileName = p + file.get("pause","image");
				pause = ImageIO.read(new File(fileName));
			}
			
			if (file.isset("loose","image")){
				fileName = p + file.get("loose","image");
				loose = ImageIO.read(new File(fileName));
			}			

			if (file.isset("shadow","left")){
				shadowLeft = Integer.decode( file.get("shadow","left") ).intValue();
			}

			if (file.isset("shadow","top")){
				shadowTop = Integer.decode( file.get("shadow","top") ).intValue();
			}


			if (file.isset("step","width")){
				stepWidth = Integer.decode( file.get("step","width") ).intValue();
			}

			if (file.isset("step","height")){
				stepHeight = Integer.decode( file.get("step","height") ).intValue();
			}

			if (file.isset("step","col")){
				stepCol = Integer.decode( file.get("step","col") ).intValue();
			}


			for( int i=0;i<10;i++){
				if (file.isset( "cases", "image"+new Integer(i+1).toString() )){
					fileName = p + file.get( "cases", "image"+new Integer(i+1).toString() );
					casesImages[i] = ImageIO.read(new File(fileName));
				}
			}

			if (file.isset("cases","left")){
				caseLeft = Integer.decode( file.get("cases","left") ).intValue();
			}

			if (file.isset("cases","top")){
				caseTop = Integer.decode( file.get("cases","top") ).intValue();
			}			

			shadowZone = new Rectangle( shadowLeft, shadowTop, shadow.getWidth(), shadow.getHeight() );
			caseZone = new Rectangle( caseLeft, caseTop, casesImages[0].getWidth(), casesImages[0].getHeight() );
			borderZone = new Rectangle( borderLeft, borderTop, border.getWidth(), border.getHeight() );			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}


	public void drawShadow( Graphics2D g, int x, int y, int index ){
		if (0 < index){ // pas d'ombre pour les bordures ! !
			g.drawImage(shadow, x + shadowZone.x, y + shadowZone.y, null);
		}
	}

	public void drawCase( Graphics2D g, int x, int y, int index ){
		if ( 0 < index ){
			g.drawImage(getCaseImage(index), x + caseZone.x, y + caseZone.y, null);
		}else if(-1 == index){
			g.drawImage(border, x + borderZone.x, y + borderZone.y, null);
		}
	}



	/**
	 * Constructeur
	 *
	 * @param themeName 
	 */
	public Theme(){
		String lastDir;

		path = System.getProperty("user.dir");
		lastDir = path.substring( path.lastIndexOf(sep) + sep.length() );

		if ( lastDir.equals("dist") ){
			path = path.substring( 0, path.lastIndexOf(sep) );
		}
		path += sep + "themes" + sep;
	}

}
