package Project.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	private static boolean[] keys;
	
	private static int delay;
		
	public KeyboardInput() {
//	Set size array of boolean to store every keyboard button
		keys = new boolean[1000]; 
//	Set Delay timing for every Keyboard pressed to activated function which each key in 96 millisecond  
		delay = 96;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;
	}
	
//	Check if any is pressing on keyboard and activated that key when delay is less than 0 or equal
	public static boolean isKeyDown(int key) {
		if(keys[key] == true && delay <= 0) {
			delay = 96;
			return true;
		}
		else {
			delay--;
			return false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}