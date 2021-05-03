/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;


/**
 *
 * @author laurent
 */
public class Plateau extends javax.swing.JPanel {
	private boolean inited = false;

	protected Theme theme;
	protected Config config;

	private int[][] cases;
	private int[][] oldCases;

	public final int ecart = 4;
	private Dimension size;
	
	private boolean showPause = false;
	private boolean showLoose = false;





	public Dimension getSize(){
		return (Dimension)size.clone();
	}



	public void setSize( Dimension size ){
		this.size.width = size.width;
		this.size.height = size.height;

		if (this.size.width / 2 == 0) this.size.width--;
		if (this.size.width < 3) this.size.width = 3;

		if (ecart > this.size.height) this.size.height = ecart;
		if (inited) init();
	}



	void setCase( int left, int top, int caseType ){
		if (
			(left < 0)
			|| (top < -ecart)
			|| (left > size.width-1)
			|| (top > size.height-1)
		){

		}else{
			/** @todo Contôle d'erreur à affectuer */
		//	if (0 > caseType) caseType = 0;
		//	if (10 < caseType) caseType = 9;
			cases[left][top+ecart] = caseType;
		}
	}

	
	private void setOldCase( int left, int top, int caseType ){
		if (
			(left < 0)
			|| (top < -ecart)
			|| (left > size.width-1)
			|| (top > size.height-1)
		){

		}else{
			/** @todo Contôle d'erreur à affectuer */
		//	if (0 > caseType) caseType = 0;
		//	if (10 < caseType) caseType = 9;
			oldCases[left][top+ecart] = caseType;
		}		
	}


	int getCase( int left, int top ){
		if (
			(left < 0)
			|| (top < -ecart)
			|| (left > size.width-1)
			|| (top > size.height-1)
		){
			return -1;
		}else{
			return cases[left][top+ecart];
		}
	}


	boolean caseChanged( int left, int top ){
		if (
			(left < 0)
			|| (top < -ecart)
			|| (left > size.width-1)
			|| (top > size.height-1)
		){
			return false;
		}else{
			return cases[left][top+ecart] != oldCases[left][top+ecart];
		}
	}



	void init(){
		int i,j;
		Rectangle r;
		Dimension d = new Dimension();

		if (config != null){
			size.width = config.plateauSize.width;
			size.height = config.plateauSize.height;
		}

		cases = new int[size.width][size.height+ecart];
		oldCases = new int[size.width][size.height+ecart];
		for( i=0;i<size.width;i++ ){
			for( j=0;j<size.height+ecart;j++ ){
				cases[i][j] = 0;
				oldCases[i][j] = 0;
			}
		}		

		d.height = size.height * theme.getStepHeight() + theme.getCaseZone().height;
		d.width = (size.width+1) * theme.getStepWidth() + theme.getCaseZone().width;

		this.setPreferredSize(d);
		r = this.getBounds();
		r.width = d.width;
		r.height = d.height;
		this.setBounds(r);
		inited = true;
		repaint();
	}


	public void setTheme( Theme theme ){
		this.theme = theme;
	}



	public void setConfig( Config config ){
		this.config = config;
	}


	public Plateau(){
		super();
		size = new Dimension(13,23);
	}

	
	
	/**
	 * Affiche l'image de pause ou non
	 * 
	 * @param pause 
	 */
	public void showPause( boolean pause ){
		showPause = pause;
		repaint();
	}
	
	
	
	/**
	 * Affiche Partie perdue! ou non
	 * 
	 * @param loose 
	 */
	public void showLoose( boolean loose ){
		showLoose = loose;
		repaint();
	}



	protected int getCasePX(int x ){
		return ((x+1) * theme.stepWidth);
	}


	protected int getCasePY(int x, int y){
		return ((y-ecart) * theme.stepHeight) + ( (Math.abs(x) % 2 - 1) * theme.stepCol);
	}

	
	public void refresh(){
		int s,i,j,left,top;
		Point topLeft, bottomRight, topLeftCase, bottomRightCase;
		Rectangle margin = new Rectangle();

		if (theme != null && cases != null) {

			topLeft = new Point(99999999, 99999999);
			bottomRight = new Point(0, 0);

			topLeftCase = new Point(99999999, 99999999);
			bottomRightCase = new Point(0, 0);			

			margin.x = Math.min(theme.getCaseZone().x, theme.getShadowZone().x);
			margin.y = Math.min(theme.getCaseZone().y, theme.getShadowZone().y);
			margin.width = Math.max(theme.getCaseZone().width, theme.getShadowZone().width);
			margin.height = Math.max(theme.getCaseZone().height, theme.getShadowZone().height);

			for(i=-1;i<=size.width;i++){
				for(j=-1;j<=size.height;j++){
					if (caseChanged(i,j)){
						left = getCasePX(i);
						top = getCasePY(i,j+ecart);
						if (topLeft.x > left+margin.x) topLeft.x = left+margin.x;
						if (topLeft.y > top+margin.y) topLeft.y = top+margin.y;
						if (bottomRight.x < left+margin.width) bottomRight.x = left+margin.width;
						if (bottomRight.y < top+margin.height) bottomRight.y = top+margin.height;

						setOldCase(i,j,getCase(i,j));
					}
				}
			}

			if (topLeft.x < bottomRight.x && topLeft.y < bottomRight.y){
				this.repaint(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y );	
			//	Graphics2D g = (Graphics2D)getGraphics();
			//	g.setColor(Color.YELLOW);
			//	g.fillRect(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
			}
		}

	}


	@Override
	public void paint(Graphics gg) {
		int s,i,j,left,top;

		if (theme != null && cases != null) {
			Graphics2D g = (Graphics2D) gg;

			g.setBackground(theme.backgroundColor);
			g.setColor(theme.backgroundColor);
			g.fillRect(0, 0, (int)getBounds().getWidth(), (int)getBounds().getHeight());

			/* dessin du fond */
			if (null != theme.background){
				g.drawImage(
								theme.background,
								(int)(((int)getBounds().getWidth() - theme.background.getWidth()) / 2),
								(int)(((int)getBounds().getHeight() - theme.background.getHeight()) / 2),
								theme.backgroundColor,
								null
				);
			}

			/* dessin des ombres et des cases */
			for(s=0;s<2;s++){
				for(i=-1;i<=size.width;i++){
					for(j=-1;j<=size.height;j++){
						left = getCasePX(i);
						top = getCasePY(i,j+ecart);
						if (0 == s){
							theme.drawShadow(g, left, top, getCase(i, j));
						}else{
							theme.drawCase(g, left, top, getCase(i, j));
						}
					}
				}
			}
			
			
			/* Dessin de la pause (ou non) */
			if (showPause){
				if (null != theme.pause){
					g.drawImage(
									theme.pause,
									(int)(((int)getBounds().getWidth() - theme.pause.getWidth()) / 2),
									(int)(((int)getBounds().getHeight() - theme.pause.getHeight()) / 2),
									null,
									null
					);
				}
			}
			
			/* Dessin de la partie perdue (ou non) */
			if (showLoose){
				if (null != theme.loose){
					g.drawImage(
									theme.loose,
									(int)(((int)getBounds().getWidth() - theme.loose.getWidth()) / 2),
									(int)(((int)getBounds().getHeight() - theme.loose.getHeight()) / 2),
									null,
									null
					);
				}
			}			
		}
	}

}
