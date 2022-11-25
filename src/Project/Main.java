package Project;

import Project.gui.Window;
import Project.logic.GameLogic;

public class Main {

	public static void main(String[] args) {
		Window.create();
		GameLogic.startGame();
		Window.setVisible();
	}
}