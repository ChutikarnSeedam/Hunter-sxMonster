package Project.resources;


import Project.logic.items.Armor;
import Project.logic.items.Item;
import Project.logic.items.Weapon;

public class Items {

	public static final Item HP_POTION = new Item("hp_potion", "Health potion", "Restores 10 HP");
	public static final Item DEF_POTION = new Item("def_potion", "Mysterious potion", "Its effect is unknown");
	
	public static final Weapon SECRET_SWORD = new Weapon("secret-sword", "secret-sword", 1000);
	public static final Weapon RUSTY_SWORD = new Weapon("rusty_sword", "rusty Sword", 5);
	public static final Weapon AXE = new Weapon("axe", "Axe", 7);
	public static final Weapon GREAT_SWORD = new Weapon("great_sword", "Great Sword", 9);
	
	public static final Armor LEATHER_ARMOR = new Armor("leather_armor", "leather Armor", 4);
	public static final Armor MEDIEVAL_ARMOR = new Armor("medieval_armor", "Medieval Armor", 7);
	public static final Armor MISTERIOUS_ARMOR = new Armor("misterious_armor", "Misterious Armor", 9);
}