/*
 * SixtrisApp.java
 */

package sixtris;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;


/**
 * The main class of the application.
 */
public class SixtrisApp extends SingleFrameApplication {	
	public Game game;
	public Theme theme;
	public Config config;
	public Listener listener;
	
	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override protected void startup() {
		String lastDir, sep=System.getProperty("file.separator"), path = System.getProperty("user.dir");
		lastDir = path.substring( path.lastIndexOf(sep) + sep.length() );
		if ( lastDir.equals("dist") ){
			path = path.substring( 0, path.lastIndexOf(System.getProperty("file.separator")) );
		}
		path += sep;

		config = new Config( path );
		listener = new Listener();
		theme = new Theme();
		SixtrisView mainView = new SixtrisView(this);
		game = new Game( mainView.getPlateau(), mainView.getPreview(), listener.getTimer() );
		game.setConfig( config );
		
		mainView.init();
		config.load();
		theme.load( config.theme );
		listener.init(config);
		show( mainView );
		mainView.getPlateau().init();
		mainView.getPreview().init();
	}

	/**
	 * This method is to initialize the specified window by injecting resources.
	 * Windows shown in our application come fully initialized from the GUI
	 * builder, so this additional configuration is not needed.
	 */
	@Override protected void configureWindow(java.awt.Window root) {
		root.addKeyListener( listener );
	}

	/**
	 * A convenient static getter for the application instance.
	 * @return the instance of SixtrisApp
	 */
	public static SixtrisApp getApplication() {
		return Application.getInstance(SixtrisApp.class);
	}

	/**
	 * Main method launching the application.
	 */
	public static void main(String[] args) {
		launch(SixtrisApp.class, args);
	}
}
