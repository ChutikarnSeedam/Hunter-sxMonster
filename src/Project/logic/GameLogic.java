package Project.logic;

import java.util.Random;

import javax.swing.Timer;

import Project.gui.Renderer;
import Project.logic.entities.EntityTile;
import Project.logic.entities.Monster;
import Project.logic.entities.Player;
import Project.logic.items.Item;
import Project.logic.level.Floor;
import Project.logic.level.Tile;
import Project.logic.level.Tower;
import Project.logic.text.MessageBox;
import Project.resources.Items;
import Project.resources.Textures;

public class GameLogic {

	private static Timer timer;
	
	private static Random randomizer;
	
	private static Player player;
	private static Tower tower;
	private static Floor currentFloor;
	private static Monster[] activeMonsters;
	private static MessageBox messageBox;
	
	private static boolean onTitleScreen;

/**	Load Every Texture to Game then Called init of GameLogic 
 * and set Delay to 20 millisecond between event listener 
 * then Called GameLoop to declare function with any input that assigned
 */

	public static void startGame() {
		Textures.init();
		
		init();
		
		timer = new Timer(20, new GameLoop());
		timer.start();
	}
	
/**	Declare currentFloor by getting data from Tower which random between Map 1 and Map 2
 * Declare player by getting data from Player which Initial name to get image and Position X and Y
 * Declare messageBox to Called Function Render in Class MessageBox
 */
	private static void init() {
		randomizer = new Random();

		tower = new Tower(randomizer);
		currentFloor = tower.getFloor(0);
		player = new Player("player", 2, 8);
		activeMonsters = currentFloor.getMonsters();
		messageBox = new MessageBox();
		
		onTitleScreen = true;
	}
	
//	Called at beginning of GameLoop to get status of monster

	public static void genericLoop() {
		player.decreaseMotionOffset();
		for(Monster monster : activeMonsters) {
			monster.decreaseMotionOffset();
		}
	}
	
//	Changes player position X, Y in Game

	public static void movePlayer(int dirX, int dirY) {
		onTitleScreen = false;
		
		if(player.isInventoryOpen())
			return;
		
		if(checkIfPlayerDied())
			return;
		
		if(detectMonsterToFight(dirX, dirY)) {
			checkIfPlayerDied();
			return;
		}
		
		switch(getTileInFrontOfEntity(player, dirX, dirY).getName()) {
//	Player Can Walk
			case "secret-wall":
			case "floor1-1":
			case "floor1-2":
			case "floor1-3":
			case "floor1-4":
			case "floor2-1":
			case "floor3-1":
			case "floor3-2":
			case "floor4":
				player.setPosition(player.getPosX()+dirX, player.getPosY()+dirY, true);
				break;
				
//	Player Can't Walk			
			case "wall":
			case "wall1-1":
			case "wall1-2":
			case "wall3":
			case "torch-1":
			case "torch-2":
				messageBox.addMessage("You ran into a wall!", 30);
				break;
				
//	If Player walk through Change currentFloor, plater, activeMonster due to tower which contain monster, object etc.			
			case "dungeon_door":
			case "stairs":
				currentFloor = tower.getNextFloor();
				player.setPosition(currentFloor.getStartPosX(), currentFloor.getStartPosY(), false);
				activeMonsters = currentFloor.getMonsters();
				messageBox.addMessage("You went into a new floor!", 30);
				player.addFloorCleared();
				break;

//	Player walk through secret-sword player can get it	
			case "secret-sword":
				pickupItem(player.getPosX()+1, player.getPosY());
				pickupItem(player.getPosX()-1, player.getPosY());
				pickupItem(player.getPosX(), player.getPosY()+1);
				pickupItem(player.getPosX(), player.getPosY()-1);
				break;
		}
		
		moveMonsters();
		
		checkIfPlayerDied();
	}

// 	Gets tile in current floor in one block around of player
	private static Tile getTileInFrontOfEntity(EntityTile entity, int dirX, int dirY) {
		return currentFloor.getTileAt(entity.getPosX()+dirX, entity.getPosY()+dirY);
	}
	
	public static void openPlayerInventory() {
		if(player.getHealth() > 0)
			player.setInventoryOpen(!player.isInventoryOpen());
	}
	
//	Action When User Press E 
	public static void handleInteration() {
		pickupItem(player.getPosX()+1, player.getPosY());
		pickupItem(player.getPosX()-1, player.getPosY());
		pickupItem(player.getPosX(), player.getPosY()+1);
		pickupItem(player.getPosX(), player.getPosY()-1);
	}

//	get item and remove from map
	private static void pickupItem(int itemPosX, int itemPosY) {
		switch(currentFloor.getTileAt(itemPosX, itemPosY).getName()) {
			case "secret-sword":
				if(player.giveItem(Items.SECRET_SWORD)) {
					player.equipWeapon(Items.SECRET_SWORD);
					currentFloor.removeCollectible(itemPosX, itemPosY);
					messageBox.addMessage("You picked up a SECRET SWORD!", 20);
				}
				else {
					messageBox.addMessage("Your inventory is full!", 20);
				}
				break;
				
			case "chest3":
			case "chest2":
				switch(randomizer.nextInt(8)) {
					case 1:
						if(player.giveItem(Items.DEF_POTION)) {
							messageBox.addMessage("You picked up a green potion!", 20);
						}
						else {
							messageBox.addMessage("Your inventory is full!", 20);
						}
						break;
						
					case 2:
						if(player.giveItem(Items.HP_POTION)) {
							messageBox.addMessage("You picked up a red potion!", 20);
						}
						else {
							messageBox.addMessage("Your inventory is full!", 20);
						}
						
						break;
						
					case 3:
						player.equipWeapon(Items.AXE);
						messageBox.addMessage("You found an AXE!", 20);
						break;
						
					case 4:
						player.equipArmor(Items.MEDIEVAL_ARMOR);
						messageBox.addMessage("You found a MEDIEVAL ARMOR!", 20);
						break;
						
					case 5:
						player.equipWeapon(Items.GREAT_SWORD);
						messageBox.addMessage("You found a GREAT SWORD!", 20);
						break;
						
					case 6:
						player.equipArmor(Items.MISTERIOUS_ARMOR);
						messageBox.addMessage("You found a MISTERIOUS ARMOR!", 20);
						break;
				}
				currentFloor.removeCollectible(itemPosX, itemPosY);
				break;
		}
	}

//	set Cursor left click action to used item when Inventory Screen is visible
	public static void handleLeftClick(int mouseX, int mouseY) {
		if(player.isInventoryOpen()) {
			if(Renderer.inventorySlot1.contains(mouseX, mouseY)) {
				usePlayerItem(0);
			}
			else if(Renderer.inventorySlot2.contains(mouseX, mouseY)) {
				usePlayerItem(1);
			}
			else if(Renderer.inventorySlot3.contains(mouseX, mouseY)) {
				usePlayerItem(2);
			}
		}
		
		if(player.getHealth() <= 0) {
			init();
		}
	}

//	Used item in Inventory
	private static void usePlayerItem(int index) {
		Item item = player.getInventoryItem(index);
		
		if(item == null) return;
		
		if(item == Items.HP_POTION) {
			player.heal(10);
			messageBox.addMessage("You drank a red potion and you recovered health!", 30);
		}
		else if(item == Items.DEF_POTION) {
			player.addDefenceBuff();
			messageBox.addMessage("You drank a defence potion! Defence temporary increased!", 30);
		}
		player.removeItem(index);
	}

/**	Important Algorithm!!!
 * 1. if there is monster in map
 * 2. if monster is chase or not with player
 * 3. random 0-3 to get how to move monster
 * 3.1 if case = 0
 * 		3.1.1 if monster is right next to player : monster attack
 * 		3.1.2 if entity at right position of monster stay is floor monster move x+1
 * 3.2 if case = 1
 * 		3.2.1 if monster is left next to player : monster attack
 * 		3.2.2 if entity at left position of monster stay is floor monster move x-1
 * 3.3 if case = 2
 * 		3.3.1 if monster is above next to player : monster attack
 * 		3.3.2 if entity at above position of monster stay is floor monster move y+1
 * 3.4 if case = 3
 *  	3.4.1 if monster is below next to player : monster attack
 * 		3.4.2 if entity at below position of monster stay is floor monster move y-1
 */
	
	private static void moveMonsters() {
		for(Monster monster : activeMonsters) {
			if(monster.getHealth() <= 0)
				continue;
			
			if(!monster.shouldChasePlayer()) {
				switch(randomizer.nextInt(4)) {
//	Monster stay at right next to player
				case 0:
					if(currentFloor.thereIsMonsterHere(monster.getPosX()+1, monster.getPosY())) {
						return;
					}
					else if(monster.getPosX()+1 == player.getPosX() && monster.getPosY() == player.getPosY()) {
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
						player.damage(monster.getStrength() - player.getDefence()/3);
						break;
					}	
					if(getTileInFrontOfEntity(monster, 1, 0).getName() == "floor2-1" || 
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor4") {
						monster.setPosition(monster.getPosX()+1, monster.getPosY(), true);
						break;
					}
//	Monster stay at left next to player
				case 1:
					if(currentFloor.thereIsMonsterHere(monster.getPosX()-1, monster.getPosY())) {
						return;
					}
					else if(monster.getPosX()-1 == player.getPosX() && monster.getPosY() == player.getPosY()) {
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
						player.damage(monster.getStrength() - player.getDefence()/3);
						break;
					}	
					if(getTileInFrontOfEntity(monster, -1, 0).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor4") {
						monster.setPosition(monster.getPosX()-1, monster.getPosY(), true);
						break;
					}
//	Monster stay at above next to player
				case 2:
					if(currentFloor.thereIsMonsterHere(monster.getPosX(), monster.getPosY()+1)) {
						return;
					}
					else if(monster.getPosX() == player.getPosX() && monster.getPosY()+1 == player.getPosY()) {
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
						player.damage(monster.getStrength() - player.getDefence()/3);
						break;
					}	
					if(getTileInFrontOfEntity(monster, 0, 1).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor4") {
						monster.setPosition(monster.getPosX(), monster.getPosY()+1, true);
						break;
					}
//	Monster stay at below next to player
				case 3:
					if(currentFloor.thereIsMonsterHere(monster.getPosX(), monster.getPosY()-1)) {
						return;
					}
					else if(monster.getPosX() == player.getPosX() && monster.getPosY()-1 == player.getPosY()) {
						player.damage(monster.getStrength() - player.getDefence()/3);
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
						break;
					}	
					if(getTileInFrontOfEntity(monster, 0, -1).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor4") {
						monster.setPosition(monster.getPosX(), monster.getPosY()-1, true);
						break;
					}
				}
			} else {
//	Monster stay at right next to player
				if(player.getPosX() > monster.getPosX()) {
					if(monster.getPosX()+1 == player.getPosX() && monster.getPosY() == player.getPosY()) {
						player.damage(monster.getStrength() - player.getDefence()/3);
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
					}
					else if(getTileInFrontOfEntity(monster, 1, 0).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 1, 0).getName() == "floor4") {
						monster.setPosition(monster.getPosX()+1, monster.getPosY(), true);
					}
				}
//	Monster stay at left next to player
				else if(player.getPosX() < monster.getPosX()) {
					if(monster.getPosX()-1 == player.getPosX() && monster.getPosY() == player.getPosY()) {
						player.damage(monster.getStrength() - player.getDefence()/3);
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
					}
					else if(getTileInFrontOfEntity(monster, -1, 0).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, -1, 0).getName() == "floor4") {
						monster.setPosition(monster.getPosX()-1, monster.getPosY(), true);
					}
				}
//	Monster stay at above next to player
				else if(player.getPosY() > monster.getPosY()) {
					if(monster.getPosX() == player.getPosX() && monster.getPosY()+1 == player.getPosY()) {
						player.damage(monster.getStrength() - player.getDefence()/3);
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
					}
					else if(getTileInFrontOfEntity(monster, 0, 1).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 0, 1).getName() == "floor4") {
						monster.setPosition(monster.getPosX(), monster.getPosY()+1, true);
					}
				}
//	Monster stay at below next to player
				else if(player.getPosY() < monster.getPosY()) {
					if(monster.getPosX() == player.getPosX() && monster.getPosY()-1 == player.getPosY()) {
						player.damage(monster.getStrength() - player.getDefence()/3);
						messageBox.addMessage("Monster deal "+monster.getStrength()+" to you. You have "+player.getHealth()+" left.", 30);
					}
					else if(getTileInFrontOfEntity(monster, 0, -1).getName() == "floor2-1" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor3-1" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor3-2" ||
							getTileInFrontOfEntity(monster, 0, -1).getName() == "floor4") {
						monster.setPosition(monster.getPosX(), monster.getPosY()-1, true);
					}
				}
			}
		}
	}
	
//	Checks if there is monster around the player, work with direction where player wants to move (X, Y)
	
	private static boolean detectMonsterToFight(int dirX, int dirY) {
		if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY) != null) {
			
			Monster fight = currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY);
			fight.damage(player.getStrength() - fight.getDefence()/3);
//	Monster is dead			
			if(fight.getHealth() <= 0) { 
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "GUNTHER") {
					messageBox.addMessage("You've killed GUNTHER!!!", 30);
				}
				
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "PEPPERMINT_BUTLER") {
					messageBox.addMessage("You've killed PEPPERMINT BUTLER!!!", 30);
				}
				
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "ICEKING") {
					messageBox.addMessage("You've killed ICEKING!!!", 30);
				}
				currentFloor.killMonster(player, fight.getPosX(), fight.getPosY());
			}
//	Monster is still alive after attack
			else if(fight.getHealth() > 0){ 
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "GUNTHER") {
					messageBox.addMessage("You've deal " + (player.getStrength() - fight.getDefence()/3) + "/" + 
										 fight.getMaxHealth() + " damage to GUNTHER!!!", 30);
				}
				
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "PEPPERMINT_BUTLER") {
					messageBox.addMessage("You've deal " + (player.getStrength() - fight.getDefence()/3) +  "/" + 
										fight.getMaxHealth() + " damage to PEPPERMINT BUTLER!!!", 30);
				}
				
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "ICEKING") {
					messageBox.addMessage("You've deal " + (player.getStrength() - fight.getDefence()/3) +  "/" + 
										fight.getMaxHealth() + " damage to ICEKING!!!", 30);
				}
				
				if(currentFloor.getMonsterAt(player.getPosX()+dirX, player.getPosY()+dirY).getName() == "boss") {
					messageBox.addMessage("You've deal " + (player.getStrength() - fight.getDefence()/3) +  "/" + 
										fight.getMaxHealth() + " damage to BOSS!!!", 30);
				}
				
				player.damage(fight.getStrength() - player.getDefence()/3);
				
			}
//	Has attacked a monster on its left			
			if(dirX > 0) 
				player.setMotionOffset(-16, 0);
//	Has attacked a monster on its right			
			else if(dirX < 0) 
				player.setMotionOffset(16, 0);
//	Has attacked a monster above
			else if(dirY > 0) 
				player.setMotionOffset(0, -16);
//	Has attacked a monster below
			else if(dirY < 0) 
				player.setMotionOffset(0, 16);
			
			return true;
		}
		return false;
	}
	
//	If Plater died return true
	private static boolean checkIfPlayerDied() {
		if(player.getHealth() <= 0) {
			return true;
		}
		return false;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static Floor getCurrentFloor() {
		return currentFloor;
	}
	
	public static Monster[] getMonsters() {
		return activeMonsters;
	}
	
	public static MessageBox getMessageBox() {
		return messageBox;
	}
	
	public static boolean isOnTitleScreen() {
		return onTitleScreen;
	}
}