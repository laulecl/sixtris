/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.awt.Point;
import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author laurent
 */
public class Block {
	protected int type;
	protected Dimension size = new Dimension();
	protected byte firstTop;
	protected Point position = new Point();
	protected byte rotation = 0; // 0 à 5
	protected Plateau plateau;

	protected Point[] cases;


	public boolean add( Plateau plateau ){
		this.plateau = plateau;

		position.x = (int)(plateau.getSize().width/2);
		position.y = firstTop;
		return draw(true);
	}


	public int getType(){
		return type;
	}

	protected Point transform( int o, int r ){
		Point p = new Point();
		boolean ok = false;
		if (o == 0 && r == 0){ p.x =  0; p.y =  0; ok=true; }

		if (o == 0 && r == 1){ p.x =  0; p.y = -1; ok=true; }
		if (o == 1 && r == 1){ p.x =  1; p.y = -1; ok=true; }
		if (o == 2 && r == 1){ p.x =  1; p.y =  0; ok=true; }
		if (o == 3 && r == 1){ p.x =  0; p.y =  1; ok=true; }
		if (o == 4 && r == 1){ p.x = -1; p.y =  0; ok=true; }
		if (o == 5 && r == 1){ p.x = -1; p.y = -1; ok=true; }

		if (o == 0 && r == 2){ p.x =  0; p.y = -2; ok=true; }
		if (o == 1 && r == 2){ p.x =  1; p.y = -2; ok=true; }
		if (o == 2 && r == 2){ p.x =  2; p.y = -1; ok=true; }
		if (o == 3 && r == 2){ p.x =  2; p.y =  0; ok=true; }
		if (o == 4 && r == 2){ p.x =  2; p.y =  1; ok=true; }
		if (o == 5 && r == 2){ p.x =  1; p.y =  1; ok=true; }
		if (o == 6 && r == 2){ p.x =  0; p.y =  2; ok=true; }
		if (o == 7 && r == 2){ p.x = -1; p.y =  1; ok=true; }
		if (o == 8 && r == 2){ p.x = -2; p.y =  1; ok=true; }
		if (o == 9 && r == 2){ p.x = -2; p.y =  0; ok=true; }
		if (o ==10 && r == 2){ p.x = -2; p.y = -1; ok=true; }
		if (o ==11 && r == 2){ p.x = -1; p.y = -2; ok=true; }		

		if (! ok) System.out.println("Le point ne peut pas être transformé");
		return p;
	}



	protected boolean draw( boolean draw){
		int t = 0, o, r, dy=0;
		Point np;
		if (draw) t = type;
		boolean goodPos = true;

		if (draw){
			for(int i=0;i<4;i++){
				o = cases[i].x;
				r = cases[i].y;
				if (0 == r) o = 0; else o = (o+rotation*r) % (6*r);
				np = transform( o, r );

				if ( (position.x + np.x) % 2 == 1 && Math.abs(np.x) % 2 == 0 ) dy = 0; else dy = 1;

				if (0 != plateau.getCase( position.x + np.x, position.y + np.y + dy)) goodPos = false;
			}
		}
		if ( (! draw) || (draw && goodPos) ){
			for(int i=0;i<cases.length;i++){
				o = cases[i].x;
				r = cases[i].y;
				if (0 == r) o = 0; else o = (o+rotation*r) % (6*r);
				np = transform( o, r );
				
				if ( (position.x + np.x) % 2 == 1 && np.x % 2 == 0 ) dy = 0; else dy = 1;
				plateau.setCase( position.x + np.x, position.y + np.y + dy, t );
			}
		}
		return goodPos;
	}



	protected void drawPreview( Preview preview ){
		int o, r, dy=0;
		Point np;
		for(int i=0;i<cases.length;i++){
			o = cases[i].x;
			r = cases[i].y;
			if (0 == r) o = 0; else o = o % (6*r);
			np = transform( o, r );

			if ( (np.x) % 2 == 1 && np.x % 2 == 0 ) dy = 0; else dy = 1;
			preview.setCase( np.x, np.y + dy, i );
		}
	}



	public void make(){
		cases = new Point[4];
		switch(type){
			case 1:
				firstTop = -3;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(3,1);
				cases[3] = new Point(6,2);
			break;			
			case 2:
				firstTop = -3;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(3,1);
				cases[3] = new Point(7,2);
			break;
			case 3:
				firstTop = -3;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(3,1);
				cases[3] = new Point(5,2);
			break;				
			case 4:
				firstTop = -3;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(4,1);
				cases[3] = new Point(7,2);
			break;
			case 5:
				firstTop = -3;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(2,1);
				cases[3] = new Point(5,2);
			break;
			case 6:
				firstTop = -2;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(3,1);
				cases[3] = new Point(4,1);
			break;
			case 7:
				firstTop = -2;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(2,1);
				cases[3] = new Point(3,1);
			break;
			case 8:
				firstTop = -2;
				cases[0] = new Point(0,1);
				cases[1] = new Point(0,0);
				cases[2] = new Point(2,1);
				cases[3] = new Point(4,1);
			break;				
			case 9:
				firstTop = -2;
				cases[0] = new Point(0,0);
				cases[1] = new Point(2,1);
				cases[2] = new Point(3,1);
				cases[3] = new Point(4,1);
			break;
			case 10:
				firstTop = -2;
				cases[0] = new Point(3,1);
				cases[1] = new Point(4,1);
				cases[2] = new Point(5,1);
				cases[3] = new Point(0,1);
			break;
		}
	}

	public boolean moveLeft(){
		return move(4);
	}

	public boolean moveRight(){
		return move(6);
	}

	public boolean moveDown(){
		return move(2);
	}

	public boolean moveUp(){
		return move(8);
	}

	public boolean rotateLeft(){
		return move(5);
	}
	
	public boolean rotateRight(){
		return move(1);
	}

	protected boolean move( int movement ){
		byte nx=0, ny=0, nr=0, lastRot=rotation;
		boolean goodMove = true;

		switch(movement){
			case 8: ny--; break; // En haut
			case 2: ny++; break; // En bas
			case 4: nx--; break; // à gauche
			case 6: nx++; break; // à droite
			case 5: nr--; break; // rotation à gauche
			case 1: nr++; break; // rotation à droite
		}

		draw(false);

		position.x += nx;
		position.y += ny;
		rotation += nr;
		if (6 == rotation) rotation = 0;
		if (-1 == rotation) rotation = 5;

		if (! draw(true)){
			position.x -= nx;
			position.y -= ny;
			rotation = lastRot;
			draw(true);
			goodMove = false;
		}
		return goodMove;
	}



	public static Block factory(){
		Random random = new Random();
		int blockType = random.nextInt(10) + 1;
		//int blockType = 10;
		return new Block(blockType);
	}



	public Block( int blockType ){
		type = blockType;
		make();
	}
}
