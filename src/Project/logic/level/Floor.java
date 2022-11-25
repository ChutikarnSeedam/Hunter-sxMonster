package Project.logic.level;


import java.util.ArrayList;
import java.util.List;

import Project.logic.entities.Monster;
import Project.logic.entities.Player;

public class Floor {

	private Tile[][] floor;
	
	private int startPosX;
	private int startPosY;
	
	private List<Monster> monsters;

	public Floor(String[] levelData, int startPosX, int startPosY, Monster... monsters) {
		floor = new Tile[levelData.length][];
		
		for(int y = 0; y < levelData.length; y++) {
			
			floor[y] = new Tile[levelData[y].length()];
//	Replace Every Character of Template Map to image
			for(int x = 0; x < levelData[y].length(); x++) {
				switch(levelData[y].charAt(x)) {
//				Secret
				case 'Z':
					floor[y][x] = new Tile("secret-sword", x, y);
					break;
				case '*':
					floor[y][x] = new Tile("secret-wall", x, y);
					break;
					
//				Village
				case '0':
					floor[y][x] = new Tile("dungeon_door", x, y);
					break;
				case '2':
					floor[y][x] = new Tile("floor1-1", x, y);
					break;
				case '3':
					floor[y][x] = new Tile("floor1-2", x, y);
					break;
				case '4':
					floor[y][x] = new Tile("floor1-3", x, y);
					break;
				case '5':
					floor[y][x] = new Tile("floor1-4", x, y);
					break;
				case '6':
					floor[y][x] = new Tile("wall1-1", x, y);
					break;
				case '7':
					floor[y][x] = new Tile("wall1-2", x, y);
					break;
				case '8':
					floor[y][x] = new Tile("wall1-3", x, y);
					break;
				
//				Level 1		
				case '#':
					floor[y][x] = new Tile("wall", x, y);
					break;
				case 'a':
					floor[y][x] = new Tile("floor2-1", x, y);
					break;
				case 'c':
					floor[y][x] = new Tile("chest2", x, y);
					break;
				case '^':
					floor[y][x] = new Tile("stairs", x, y);
					break;
				case 'T':
					floor[y][x] = new Tile("torch-1", x, y);
					break;
					
//				Level 2
				case 'd':
					floor[y][x] = new Tile("floor3-1", x, y);
					break;
				case 'e':
					floor[y][x] = new Tile("floor3-2", x, y);
					break;
				case 'f':
					floor[y][x] = new Tile("chest3", x, y);
					break;	
					
//				Level 3
				case 'g':
					floor[y][x] = new Tile("floor4", x, y);
					break;
				case 'h':
					floor[y][x] = new Tile("torch-2", x, y);
					break;
				case 'i':
					floor[y][x] = new Tile("wall3", x, y);
					break;
				}
			}
		}
//	Set Start Position of Player per Map		
		this.startPosX = startPosX;
		this.startPosY = startPosY;

//	Set All monster which in each Map		
 		this.monsters = new ArrayList<Monster>();
		for(Monster one : monsters) {
			this.monsters.add(one);
		}
	}
	
	public Floor(Floor copy) {
		floor = new Tile[copy.getSizeY()][];
		for(int y = 0; y < copy.getSizeY(); y++) {
			floor[y] = new Tile[copy.getSizeX()];
			for(int x = 0; x < copy.getSizeX(); x++) {
				floor[y][x] = new Tile(copy.getTileAt(x, y).getName(), x, y);
			}
		}
		
		this.startPosX = copy.getStartPosX();
		this.startPosY = copy.getStartPosY();
		
		this.monsters = copy.getMonstersList();		
	}

//	Return current floor width
	public int getSizeX() {
		return floor[0].length;
	}
	
//	Return current floor height	
	public int getSizeY() {
		return floor.length;
	}

// 	Return Position of each floor in Map
	public Tile getTileAt(int x, int y) {
		return floor[y][x];
	}

// 	Return Start Position X of Player
	public int getStartPosX() {
		return startPosX;
	}

// 	Return Start Position Y of Player
	public int getStartPosY() {
		return startPosY;
	}

// 	Return All monster in each map
	public Monster[] getMonsters() {
		Monster[] other = new Monster[monsters.size()];
		other = monsters.toArray(other);
		return other;
	}

//	Return All monster list
	private List<Monster> getMonstersList(){
		return this.monsters;
	}

//	Return Monster if Monster is at position X, Y
	public Monster getMonsterAt(int x, int y) {
		for(Monster monster : monsters) {
			if(monster == null)
				return null;
			
			if(monster.getPosX() == x && monster.getPosY() == y)
				return monster;
		}
		return null;
	}

//	Change floor which have Object to normal floor	
	public boolean removeCollectible(int x, int y) {
		switch(floor[y][x].getName()) {
		case "chest2":
			floor[y][x] = new Tile("floor2-1", x, y);
			return true;
		case "chest3":
			floor[y][x] = new Tile("floor3-1", x, y);
			return true;
		}
		return false;
	}
	
//	Remove Monster at Position X, Y and if that Monster is Boss Call GameCleared	
	public boolean killMonster(Player player, int x, int y) {
		for(int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).getPosX() == x && monsters.get(i).getPosY() == y) {
				if(monsters.get(i).getName() == "boss") {
					monsters.remove(i);
					player.setGameCleared();
				}
				else {
					monsters.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
//	Check if monster at Position X, Y		
	public boolean thereIsMonsterHere(int x, int y) {
		for(int i=0;i<monsters.size();i++) {
			if(monsters.get(i).getPosX() == x && monsters.get(i).getPosY() == y)
				return true;
		}
		return false;
	}
}