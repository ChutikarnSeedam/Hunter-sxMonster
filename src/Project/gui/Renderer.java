package Project.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Project.logic.entities.EntityTile;
import Project.logic.entities.Monster;
import Project.logic.entities.Player;
import Project.logic.level.Floor;
import Project.logic.level.Tile;
import Project.logic.text.MessageBox;
import Project.resources.Textures;

public class Renderer {

	private int zoomLevel;

	public static final Rectangle inventory = new Rectangle(150, 50, 700, 500);
	public static BufferedImage sprite = null;
	public static final Rectangle inventorySlot1 = new Rectangle(510, 150, 330, 60);
	public static final Rectangle inventorySlot2 = new Rectangle(510, 220, 330, 60);
	public static final Rectangle inventorySlot3 = new Rectangle(510, 290, 330, 60);

	public static final Rectangle weaponSlot = new Rectangle(160, 150, 330, 60);
	public static final Rectangle armorSlot = new Rectangle(160, 220, 330, 60);

	public static final Rectangle messageBox = new Rectangle(200, 480, 600, 50);
	
	public Renderer() {
		this.zoomLevel = 2;
	}
	
//	Render player at position on middle of the screen
	public void renderPlayer(EntityTile playerData, Graphics graphics) {
		BufferedImage sprite = Textures.getSprite("player");
		
		int drawPosX = (Window.WIDTH/2)-(sprite.getWidth()/2)*zoomLevel;
		int drawPosY = (Window.HEIGHT/2)-(sprite.getHeight()/2)*zoomLevel;
		
		graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
	}

//	Render object in map by get data from floorData by using Player position X, Y
	public void renderLevel(Floor floorData, Player player, Graphics graphics) {
		for(int y = 0; y < floorData.getSizeY(); y++) {
			for(int x = 0; x < floorData.getSizeX(); x++) {
				BufferedImage sprite = Textures.getSprite(floorData.getTileAt(x, y).getName());
				
				if(floorData.getTileAt(x, y).getName() == "wall" && y == floorData.getSizeY()-1)
					sprite = Textures.getSprite("wall2");
				else if(floorData.getTileAt(x, y).getName() == "wall" && (floorData.getTileAt(x, y+1).getName() != "wall2"))
					sprite = Textures.getSprite("wall2");
				else if(floorData.getTileAt(x, y).getName() == "wall3" && y == floorData.getSizeY()-1){
					sprite = Textures.getSprite("wall3");
				}
				int drawPosX = calculateOffsetX(sprite, floorData.getTileAt(x, y), player);
				int drawPosY = calculateOffsetY(sprite, floorData.getTileAt(x, y), player);
				graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
			}
		}
	}
	
//	Render monster in map by get data from monsters by using Player position X, Y
	public void renderMonsters(Monster[] monsters, Player player, Graphics graphics) {
		if(monsters == null) return;
		
		for(Monster monster : monsters) {
			BufferedImage sprite = Textures.getSprite(monster.getName());
			int drawPosX = calculateOffsetX(sprite, monster, player) - monster.getMotionOffsetX()*zoomLevel;
			int drawPosY = calculateOffsetY(sprite, monster, player) - monster.getMotionOffsetY()*zoomLevel;
			if(monster.getHealth() > 0)
				graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
		}
	}
	
//	Calculate position of every object by using object width, player position X, window width
	private int calculateOffsetX(BufferedImage sprite, Tile tile, Player player) {
		return tile.getPosX()*sprite.getWidth()*zoomLevel + ((Window.WIDTH/2)-player.getPosX()*sprite.getWidth()*zoomLevel-(sprite.getWidth()/2)*zoomLevel)+player.getMotionOffsetX()*zoomLevel;
	}

//	Calculate position of every object by using object height, player position Y, window height
	private int calculateOffsetY(BufferedImage sprite, Tile tile, Player player) {
		return tile.getPosY()*sprite.getHeight()*zoomLevel + ((Window.HEIGHT/2)-player.getPosY()*sprite.getHeight()*zoomLevel-(sprite.getHeight()/2)*zoomLevel)+player.getMotionOffsetY()*zoomLevel;
	}
	
//	Renders the UI : Status Bar, Inventory Screen
	public void renderUI(Player player, Floor floorData, Graphics2D graphics, Point mousePosition) {
		if(player.getGameCleared() == false) {
			sprite = Textures.getSprite("sign_board");
			graphics.drawImage(sprite, 0, 0, null);		
			graphics.setFont(new Font("Dialog", Font.PLAIN, 16));
			graphics.drawString("Stat", 5, 40);
			graphics.setFont(new Font("Dialog", Font.PLAIN, 12));
			graphics.drawString("HP: "+player.getHealth()+"/"+player.getMaxHealth(), 10, 60);
			graphics.drawString("STR: "+player.getStrength(), 10, 80);
			graphics.drawString("DEF: "+player.getDefence(), 10, 100);
			graphics.drawString("Floors: "+player.getFloorsCleared(), 10, 120);
			
			for(int y = 0; y < floorData.getSizeY(); y++) {
				for(int x = 0; x < floorData.getSizeX(); x++) {
					if(floorData.getTileAt(x, y).isCollectible()) {
						int drawPosX = floorData.getTileAt(x, y).getPosX()*32*zoomLevel + ((Window.WIDTH/2)-player.getPosX()*32*zoomLevel-(32/2)*zoomLevel);
						int drawPosY = floorData.getTileAt(x, y).getPosY()*32*zoomLevel + ((Window.HEIGHT/2)-player.getPosY()*32*zoomLevel-(32/2)*zoomLevel);
						
						if((player.getPosX() == x-1 && player.getPosY() == y) || (player.getPosX() == x+1 && player.getPosY() == y) || (player.getPosX() == x && player.getPosY() == y-1) || (player.getPosX() == x && player.getPosY() == y+1)) {
							BufferedImage sprite = Textures.getSprite("E");
							graphics.drawImage(sprite, drawPosX+8*zoomLevel+player.getMotionOffsetX()*zoomLevel, drawPosY-8*zoomLevel+player.getMotionOffsetY()*zoomLevel, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
						}
					}
				}
			}
		}
		else {
			BufferedImage sprite = Textures.getSprite("lose_game");
			graphics.drawImage(sprite, 0, 0, null);
		}
		
		if(player.isInventoryOpen()) {
//	Inventory
			graphics.setColor(Color.BLACK);
			graphics.fillRoundRect(inventory.x, inventory.y, inventory.width, inventory.height, 10, 10);
			graphics.setColor(Color.WHITE);
			graphics.drawRoundRect(inventory.x, inventory.y, inventory.width, inventory.height, 10, 10);
			
			graphics.setFont(new Font("Dialog", Font.PLAIN, 40));
			graphics.drawString("- Inventory -", 160, 90);
			graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
			graphics.drawString("HP: "+player.getHealth()+"/"+player.getMaxHealth()+"     STR: "+player.getStrength()+"   DEF: "+player.getDefence(), 160, 120);
			
//	Inventory slots
//		Empty Inventory slots : 1
			if(inventorySlot1.contains(mousePosition))
				graphics.setStroke(new BasicStroke(3));
			
			graphics.drawRoundRect(inventorySlot1.x, inventorySlot1.y, inventorySlot1.width, inventorySlot1.height, 10, 10);
			graphics.drawRoundRect(inventorySlot1.x, inventorySlot1.y, 60, inventorySlot1.height, 10, 10);
			
			graphics.setStroke(new BasicStroke(1));
			
//		Empty Inventory slots : 2			
			if(inventorySlot2.contains(mousePosition))
				graphics.setStroke(new BasicStroke(3));
			
			graphics.drawRoundRect(inventorySlot2.x, inventorySlot2.y, inventorySlot2.width, inventorySlot2.height, 10, 10);
			graphics.drawRoundRect(inventorySlot2.x, inventorySlot2.y, 60, inventorySlot2.height, 10, 10);
			
			graphics.setStroke(new BasicStroke(1));
			
//		Empty Inventory slots : 3				
			if(inventorySlot3.contains(mousePosition))
				graphics.setStroke(new BasicStroke(3));
			
			graphics.drawRoundRect(inventorySlot3.x, inventorySlot3.y, inventorySlot3.width, inventorySlot3.height, 10, 10);
			graphics.drawRoundRect(inventorySlot3.x, inventorySlot3.y, 60, inventorySlot3.height, 10, 10);
			
			graphics.setStroke(new BasicStroke(1));
			
			graphics.drawRoundRect(weaponSlot.x, weaponSlot.y, weaponSlot.width, weaponSlot.height, 10, 10);
			graphics.drawRoundRect(weaponSlot.x, weaponSlot.y, 60, weaponSlot.height, 10, 10);

//	Render Weapon in Inventory Screen
			if(player.getWeapon() != null) {
				BufferedImage sprite = Textures.getSprite(player.getWeapon().getName());
				graphics.drawImage(sprite, weaponSlot.x+7, weaponSlot.y+7, sprite.getWidth()*3, sprite.getHeight()*3, null);
				graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
				graphics.drawString(player.getWeapon().getDisplayName(), weaponSlot.x+65, weaponSlot.y+22);
				graphics.setFont(new Font("Dialog", Font.PLAIN, 15));
				graphics.drawString(player.getWeapon().getDescription(), weaponSlot.x+65, weaponSlot.y+48);
			}
			
			graphics.drawRoundRect(armorSlot.x, armorSlot.y, armorSlot.width, armorSlot.height, 10, 10);
			graphics.drawRoundRect(armorSlot.x, armorSlot.y, 60, armorSlot.height, 10, 10);

//	Render Armor in Inventory Screen
			if(player.getArmor() != null) {
				BufferedImage sprite = Textures.getSprite(player.getArmor().getName());
				graphics.drawImage(sprite, armorSlot.x+7, armorSlot.y+7, sprite.getWidth()*3, sprite.getHeight()*3, null);
				graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
				graphics.drawString(player.getArmor().getDisplayName(), armorSlot.x+65, armorSlot.y+22);
				graphics.setFont(new Font("Dialog", Font.PLAIN, 15));
				graphics.drawString(player.getArmor().getDescription(), armorSlot.x+65, armorSlot.y+48);
			}
			
//	Render All Item in Inventory Screen : Inventory size = 3
			for(int i = 0; i < Player.INVENTORY_SIZE; i++) {
				if(player.getInventoryItem(i) != null) {
					BufferedImage sprite = Textures.getSprite(player.getInventoryItem(i).getName());
					graphics.drawImage(sprite, inventorySlot1.x+7, 157+i*70, sprite.getWidth()*3, sprite.getHeight()*3, null);
					
					graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
					graphics.drawString(player.getInventoryItem(i).getDisplayName(), inventorySlot1.x+65, 170+i*70);
					graphics.setFont(new Font("Dialog", Font.PLAIN, 15));
					graphics.drawString(player.getInventoryItem(i).getDescription(), inventorySlot1.x+65, 195+i*70);
				}
			}
		}
		
//	Render Death screen
		if(player.getHealth() <= 0) {
			BufferedImage sprite = Textures.getSprite("lose_game");
			graphics.drawImage(sprite, 0, 0, null);
		}
		
		if(player.getGameCleared() == true) {
			BufferedImage sprite = Textures.getSprite("clear_game");
			graphics.drawImage(sprite, 0, 0, null);
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
			graphics.drawString("Press ESC to Close...", 400, 400);
		}
	}
	
//	Renders the message box at the bottom of the screen
	public void renderMessageBox(MessageBox message, Graphics graphics) {
		if(message.getMessage() == null || message.getTime() <= 0)
			return;
		
		graphics.setColor(Color.BLACK);
		graphics.fillRoundRect(messageBox.x, messageBox.y, messageBox.width, messageBox.height, 10, 10);
		graphics.setColor(Color.WHITE);
		graphics.drawRoundRect(messageBox.x, messageBox.y, messageBox.width, messageBox.height, 10, 10);

		graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
		
// Set Text Position to Center
		try {
			int textPosX = messageBox.x + (messageBox.width - graphics.getFontMetrics().stringWidth(message.getMessage())) / 2;
			int textPosY = messageBox.y + ((messageBox.height - graphics.getFontMetrics().getHeight()) / 2) + graphics.getFontMetrics().getAscent();		
			graphics.drawString(message.getMessage(), textPosX, textPosY);
		} catch(NullPointerException e) {
			return;
		}
	}
	
// Render Light Effect
	public void renderLight(Floor floorData, Player player, Graphics2D graphics) {
		for(int y = 0; y < floorData.getSizeY(); y++) {
			for(int x = 0; x < floorData.getSizeX(); x++) {
				BufferedImage sprite = null;
				try {
					if(floorData.getTileAt(x, y).getName() == "torch-1" || 
							floorData.getTileAt(x, y).getName() == "torch-2")
						continue;
					else if(floorData.getTileAt(x-1, y).getName() == "torch-1" || 
							floorData.getTileAt(x-1, y).getName() == "torch-2")
						sprite = Textures.getSprite("dark_right");
					
					else if(floorData.getTileAt(x+1, y).getName() == "torch-1" || 
							floorData.getTileAt(x+1, y).getName() == "torch-2")
						sprite = Textures.getSprite("dark_left");
					
					else if(floorData.getTileAt(x, y-1).getName() == "torch-1" || 
							floorData.getTileAt(x, y-1).getName() == "torch-2")
						sprite = Textures.getSprite("dark_down");
					
					else if(floorData.getTileAt(x, y+1).getName() == "torch-1" || 
							floorData.getTileAt(x, y+1).getName() == "torch-2")
						sprite = Textures.getSprite("dark_up");
					
					else if(floorData.getTileAt(x-1, y-1).getName() == "torch-1" || 
							floorData.getTileAt(x-1, y-1).getName() == "torch-2")
						sprite = Textures.getSprite("dark_down_right");
					
					else if(floorData.getTileAt(x+1, y+1).getName() == "torch-1" || 
							floorData.getTileAt(x+1, y+1).getName() == "torch-2") 
						sprite = Textures.getSprite("dark_up_left");
					
					else if(floorData.getTileAt(x+1, y-1).getName() == "torch-1" || 
							floorData.getTileAt(x+1, y-1).getName() == "torch-2")
						sprite = Textures.getSprite("dark_down_left");
					
					else if(floorData.getTileAt(x-1, y+1).getName() == "torch-1" || 
							floorData.getTileAt(x-1, y+1).getName() == "torch-2")
						sprite = Textures.getSprite("dark_up_right");
					
					else
						sprite = Textures.getSprite("dark");
				} catch(ArrayIndexOutOfBoundsException e) {
					sprite = Textures.getSprite("dark");
				}
				
				int drawPosX = calculateOffsetX(sprite, floorData.getTileAt(x, y), player);
				int drawPosY = calculateOffsetY(sprite, floorData.getTileAt(x, y), player);
				AlphaComposite original = (AlphaComposite) graphics.getComposite();
				
//	Set Brightness depends on Floor which player have cleared
				switch(player.getFloorsCleared()) {
					case 1:
						original = (AlphaComposite) graphics.getComposite();
						graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
						graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
						graphics.setComposite(original);
						break;
					case 2:
						original = (AlphaComposite) graphics.getComposite();
						graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
						graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
						graphics.setComposite(original);
						break;
					case 3:
						original = (AlphaComposite) graphics.getComposite();
						graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
						graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
						graphics.setComposite(original);
						break;
					case 4:
						original = (AlphaComposite) graphics.getComposite();
						graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
						graphics.drawImage(sprite, drawPosX, drawPosY, sprite.getWidth()*zoomLevel, sprite.getHeight()*zoomLevel, null);
						graphics.setComposite(original);
						break;
				}
				
			}
		}
		
	}
	
//	Render Title Screen
	public void renderTitleScreen(Graphics graphics) {
		BufferedImage sprite = Textures.getSprite("temp");
		graphics.drawImage(sprite, 0, 0, null);
		graphics.setColor(Color.WHITE);
		graphics.fillRoundRect(50, 50, Window.WIDTH-150, Window.HEIGHT-150, 10, 10);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Dialog", Font.PLAIN, 40));
		graphics.drawString("Hunter'sx Monster", 100, 100);
		graphics.setFont(new Font("Dialog", Font.PLAIN, 20));
		graphics.drawString("     The story of a young adventurer with a mission from an old man ", 100, 150);
		graphics.drawString("to find valuable treasures from a sacred cave ", 100, 180);
		graphics.drawString("known as Lives of many strange creatures ", 100, 210);
		graphics.drawString("But with the confidence and arrogance of a young man. ", 100, 240);
		graphics.drawString("Legendary stories of animals", 100, 270);
		graphics.drawString("the strangeness had not diminished his fear one bit.", 100, 300);
		graphics.drawString("Can he conquer the most dangerous cave or not!!", 100, 330);
		graphics.drawString("Press any button...", 200, 400);
	}
}