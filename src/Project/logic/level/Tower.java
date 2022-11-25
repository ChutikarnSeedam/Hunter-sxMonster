package Project.logic.level;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Project.resources.Levels;

public class Tower {

	private List<Floor> floors;
	private int floorAt;
	
//	Get tower data from class Project.resources.Levels
	public Tower(Random randomizer) {
		this.floors = new ArrayList<Floor>();
		List<Floor> temporaryTower = new ArrayList<>();
		this.floorAt = 0;
/**	Set First floors to BASE_LEVEL
	then Add LEVEL_1, LEVEL_2 to temporaryTower to store Temporary Data before add to floors by random between amount of Map
*/
		this.floors.add(Levels.BASE_LEVEL);
		temporaryTower.add(Levels.LEVEL_1);
		temporaryTower.add(Levels.LEVEL_2);

		while(!temporaryTower.isEmpty()) {
			int choice = temporaryTower.size() == 1 ? 0 : randomizer.nextInt(temporaryTower.size()-1)+1;
			this.floors.add(temporaryTower.get(choice));
			temporaryTower.remove(choice);
		}
//	If temporaryTower is Empty then Add Last Stage to floors
		if(temporaryTower.isEmpty()) {
			this.floors.add(Levels.LEVEL_3);
		}
	}
	
//	Return floors Data by using index to select which floor
	public Floor getFloor(int index) {
		return floors.get(index);
	}
	
//	Gets the amount of floors
	public int getTowerHeight() {
		return floors.size();
	}
	
// 	Gets the index of current floors where player stay
	public int getFloorAt() {
		return floorAt;
	}
	
//	Gets the level next the current one
	public Floor getNextFloor() {
		floorAt++;
		
		if(floorAt == floors.size())
			floorAt--;
		
		return floors.get(floorAt);
	}
	
// 	Gets the level before the current one*/
	public Floor getPreviousFloor() {
		if(floorAt != 0)
			floorAt--;
		
		return floors.get(floorAt);
	}
}