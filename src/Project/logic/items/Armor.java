package Project.logic.items;

public class Armor extends Item {

	private int defence;
	
	public Armor(String name, String displayName, int defence) {
		super(name, displayName, "Protection: "+defence);
		this.defence = defence;
	}

	public int getDefence() {
		return defence;
	}
}