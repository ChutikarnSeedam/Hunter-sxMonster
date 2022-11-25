package Project.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import Project.logic.GameLogic;

@SuppressWarnings("serial")
public class GameScreen extends JPanel {

	private Renderer renderer;

	public GameScreen() {
		super();
		this.setFocusable(true);
		this.addKeyListener(new KeyboardInput());
		this.addMouseListener(new Mouse());
		
		this.renderer = new Renderer();
		
	}
	
//	Call important Render function
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

		if(GameLogic.isOnTitleScreen()) {
			renderer.renderTitleScreen(graphics);
		} 
		else {
			renderer.renderLevel(GameLogic.getCurrentFloor(), GameLogic.getPlayer(), graphics);
			renderer.renderPlayer(GameLogic.getPlayer(), graphics);
			renderer.renderMonsters(GameLogic.getMonsters(), GameLogic.getPlayer(), graphics);
			renderer.renderLight(GameLogic.getCurrentFloor(), GameLogic.getPlayer(), (Graphics2D) graphics);
			renderer.renderUI(GameLogic.getPlayer(), GameLogic.getCurrentFloor(), (Graphics2D) graphics, this.getMouseLocation());
			renderer.renderMessageBox(GameLogic.getMessageBox(), graphics);
		}
		
		repaint();
			
	}
	
//	Get Where Cursor Mouse is. Used when click any object or components that needs Cursor interact
	public Point getMouseLocation() {
		if(getMousePosition() != null)
			return getMousePosition();
		else
			return new Point(-1, -1);
	}
}