/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sixtris;

import java.awt.Dimension;

/**
 *
 * @author laurent
 */
public class Config {
	protected String theme = "Default";
	protected Dimension plateauSize;

	protected int lastLevel = 1100;
	protected int baseSpeed = 1400;
	protected int speedStep = 110;
	protected int levelChange = 11;

	private String sep = System.getProperty("file.separator");
	private String path;
	private IniFile file;





	/**
	 * Constructeur
	 * @param path 
	 */
	public Config( String path ){
		this.path = path;
	}



	/**
	 * 
	 * 
	 * @return 
	 */
	public String getTheme(){
		return theme;
	}

	public void load(){
		plateauSize = new Dimension( 13, 23 );
		Integer i;
		try{
			file = new IniFile( path+"config.ini" );

			if (file.isset("theme")){
				theme = file.get("theme");
			}

			if (file.isset("plateau", "width")){
				i = new Integer( file.get("plateau", "width") );
				plateauSize.width = i.intValue();
			}

			if (file.isset("plateau", "height")){
				i = new Integer( file.get("plateau", "height") );
				plateauSize.height = i.intValue();
			}
			
			if (file.isset("game", "lastLevel")){
				i = new Integer( file.get("game", "lastLevel") );
				lastLevel = i.intValue();
			}
			
			if (file.isset("game", "baseSpeed")){
				i = new Integer( file.get("game", "baseSpeed") );
				baseSpeed = i.intValue();
			}
			
			if (file.isset("game", "speedStep")){
				i = new Integer( file.get("game", "speedStep") );
				speedStep = i.intValue();
			}
			
			if (file.isset("game", "levelChange")){
				i = new Integer( file.get("game", "levelChange") );
				levelChange = i.intValue();
			}			
						
		}catch( Exception e ){
			System.out.println( e.getLocalizedMessage() );
		}
	}

}
