package Project.logic.entities;

import Project.logic.items.Armor;
import Project.logic.items.Item;
import Project.logic.items.Weapon;
import Project.resources.Items;

public class Player extends EntityTile {

	public static final int INVENTORY_SIZE = 3;
	
	private Item[] inventory;
	private boolean inventoryOpen;
	
	private Weapon weaponEquipped;
	private Armor armorEquipped;

	private int floors;
	
	private int strengthBuff;
	private int defenceBuff;
	private boolean Cleared;
	public static int posX = 0;
	public static int posY = 0;
	
	public Player(String name, int posX, int posY) {
		super(name, posX, posY, 20);
		this.inventory = new Item[INVENTORY_SIZE];
		this.inventoryOpen = false;
		this.weaponEquipped = Items.RUSTY_SWORD;
		this.armorEquipped = Items.LEATHER_ARMOR;
		this.strength = 1;
		this.defence = 0;
		this.floors = 0;
		this.strengthBuff = 0;
		this.defenceBuff = 0;
		this.Cleared = false;
	}
	
	@Override
	public void setPosition(int dirX, int dirY, boolean animated) {
		super.setPosition(dirX, dirY, animated);
		if(animated) {
			this.strengthBuff--;
			this.defenceBuff--;
		}
	}
	
//	Add Item to Inventory slot where there is empty slot
	public boolean giveItem(Item item) {
		for(int i=0;i<INVENTORY_SIZE;i++) {
			if(this.inventory[i] == null) {
				this.inventory[i] = item;
				return true;
			}
		}
		return false;
	}
	
//	Remove Item to Inventory slot where Player used Item
	public void removeItem(int index) {
		try {
			this.inventory[index] = null;
		} catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

//	return Inventory data which have variable from class Item
	public Item getInventoryItem(int index) {
		try {
			return inventory[index];
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}
	
	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

	public void addFloorCleared() {
		this.floors++;
	}
	
	public void subtractFloorCleared() {
		this.floors--;
	}
	
	public int getFloorsCleared() {
		return floors;
	}
	
	public void increaseHealth(int amount) {
		this.maxHealth += amount;
		this.health += amount;
	}
	
	public void addStrengthBuff() {
		this.strengthBuff = 50;
	}
	
	public void setGameCleared() {
		this.Cleared = true;
	}
	
	public boolean getGameCleared() {
		return Cleared;
	}
	
	@Override
	public int getStrength() {
		int str = super.getStrength();
		if(this.weaponEquipped != null) str += this.weaponEquipped.getDamage();
		return this.strengthBuff > 0 ? str + 5 : str;
	}
	
	public void addDefenceBuff() {
		this.defenceBuff = 50;
	}
	
	@Override
	public int getDefence() {
		int def = super.getDefence();
		if(this.armorEquipped != null) def += this.armorEquipped.getDefence();
		return this.defenceBuff > 0 ? def + 5 : def;
	}

//	Replace New Weapons in place of the Old one
	public void equipWeapon(Weapon weapon) {
		this.weaponEquipped = new Weapon(weapon.getName(), weapon.getDisplayName(), weapon.getDamage());
	}

//	Replace New Armor in place of the Old one
	public void equipArmor(Armor armor) {
		this.armorEquipped = new Armor(armor.getName(), armor.getDisplayName(), armor.getDefence());
	}

//	Return data of weaponEquipped by get variable from Class Weapon
	public Weapon getWeapon() {
		return this.weaponEquipped;
	}
	
//	Return data of armorEquipped by get variable from Class Weapon
	public Armor getArmor() {
		return this.armorEquipped;
	}
}