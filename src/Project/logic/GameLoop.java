package Project.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import Project.gui.KeyboardInput;

public class GameLoop implements ActionListener {
//	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			GameLogic.genericLoop();
//	Move Player UP
			if(KeyboardInput.isKeyDown(KeyEvent.VK_W)) {
				GameLogic.movePlayer(0, -1); 
			}
//	Move Player LEFT
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_A)) {
				GameLogic.movePlayer(-1, 0);
			}
//	Move Player DOWN
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_S)) {
				GameLogic.movePlayer(0, 1); 
			}
//	Move Player RIGHT			
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_D)) {
				GameLogic.movePlayer(1, 0); 
			}
//	Open Inventory
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_I)) {
				GameLogic.openPlayerInventory();
			}
//	Interact with Object
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_E)) {
				GameLogic.handleInteration();
			}
//	Close Game		
			else if(KeyboardInput.isKeyDown(KeyEvent.VK_ESCAPE)) {
				System.exit(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
