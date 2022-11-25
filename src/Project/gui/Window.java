package Project.gui;


import javax.swing.JFrame;

public class Window {

	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	
	private static JFrame window;
	private static GameScreen screen;
	
// Create Windows which Title is "Hunter'sx Monster" and call GameScreen to load every object and logic
	public static void create() {
		window = new JFrame("Hunter'sx Monster");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(20, 20, WIDTH, HEIGHT);
		window.setResizable(false);
		
		screen = new GameScreen();
		window.add(screen);
	}
	
// Set windows to be visible when everything is ready
	public static void setVisible() {
		if(window!=null) window.setVisible(true);
	}
}