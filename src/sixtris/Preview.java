/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;

/**
 *
 * @author laurent
 */
public class Preview extends javax.swing.JPanel {

	protected final int blockSize = 4;
	protected Theme theme;
	protected Block block;
	protected Point[] cases;


	public void setBlock( Block block ){
		this.block = block;
		this.block.drawPreview(this);
		repaint();
	}

	public Block getBlock(){
		return block;
	}

	public void setTheme( Theme theme ){
		this.theme = theme;
	}	

	public void setCase( int x, int y, int i ){
		cases[i].x = x;
		cases[i].y = y;
	}

	public void init(){
		Rectangle r;
		Dimension d = new Dimension();

		cases = new Point[blockSize];
		for( int i=0;i<blockSize;i++){
			cases[i] = new Point();
		}

		d.height = blockSize * theme.getStepHeight() + theme.getCaseZone().height;
		d.width = (blockSize+1) * theme.getStepWidth() + theme.getCaseZone().width;

		this.setPreferredSize(d);
		r = this.getBounds();
		r.width = d.width;
		r.height = d.height;
		this.setBounds(r);
	}

	protected int getCasePX(int x ){
		return ((x+1) * theme.stepWidth);
	}


	protected int getCasePY(int x, int y){
		return ((y) * theme.stepHeight) + ( (Math.abs(x) % 2 - 1) * theme.stepCol);
	}

	@Override
	public void paint(Graphics gg) {
		if (theme != null) {
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

			if (block != null){
				int left, top;
				Point topLeft = new Point(999999,999999);
				Point bottomRight = new Point(0,0);
				Point pos = new Point(0,0);

				for(int i=0;i<cases.length;i++){
					left = getCasePX(cases[i].x);
					top = getCasePY(cases[i].x,cases[i].y);

					if (topLeft.x > left) topLeft.x = left;
					if (topLeft.y > top) topLeft.y = top;

					if (bottomRight.x < left+theme.getCaseZone().width) bottomRight.x = left+theme.getCaseZone().width;
					if (bottomRight.y < top+theme.getCaseZone().height) bottomRight.y = top+theme.getCaseZone().height;
				}

				pos.x = (int)(getBounds().getWidth() / 2) - (int)((bottomRight.x - topLeft.x) / 2);
				pos.y = (int)(getBounds().getHeight() / 2) - (int)((bottomRight.y - topLeft.y) / 2);

				for(int s=0;s<2;s++){
					for(int i=0;i<cases.length;i++){
						left = pos.x - topLeft.x + getCasePX(cases[i].x);
						top = pos.y - topLeft.y + getCasePY(cases[i].x,cases[i].y);
						if (0 == s){
							theme.drawShadow(g, left, top, block.getType());
						}else{
							theme.drawCase(g, left, top, block.getType());
						}
					}
				}
			}
		}
	}
}
