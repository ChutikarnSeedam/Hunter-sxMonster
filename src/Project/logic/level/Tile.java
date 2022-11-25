package Project.logic.level;

public class Tile {

	private String name;
	
	protected int worldPosX;
	protected int worldPosY;
	
	private boolean collectible;
	
//	Constructor of Class Tile which contains name of object, Position X and Y
	public Tile(String name, int posX, int posY) {
		this.name = name;
		this.worldPosX = posX;
		this.worldPosY = posY;
		
		if(name == "chest2" || name == "chest3")
			this.collectible = true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPosX() {
		return worldPosX;
	}
	
	public int getPosY() {
		return worldPosY;
	}
	
	public boolean isCollectible() {
		return collectible;
	}
}