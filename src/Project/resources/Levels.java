package Project.resources;


import Project.logic.entities.Monster;
import Project.logic.level.Floor;

public class Levels {
	
	public static final Floor BASE_LEVEL = new Floor(new String[] {
//			Max height = 10
//			Max Width = 3	
			"7###7",
			"7#0#7",
			"73237",
			"74237",
			"73247",
			"77277",
			"73237",
			"73277",
			"65226",
			"67766"
		}, 2, 8);
	public static final Floor LEVEL_1 = new Floor(new String[] {
//			Max height = 14
//			Max Width = 30			
			"################################",
			"#aaa^aaaaa#caaTaaaaaaa#aaaaaaaa#",
			"Taaaaaa##T##aaaaaaTaaaaaaaTaaaa#",
			"#####aaaaa##################aaa#",
			"#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#",
			"####T###T###T###T###T###aaaa#T##",
			"#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#",
			"#aaaaaaaaaaaaaaaaa#aaaaaaaaaaaa#",
			"####T###T###T###aaa###T###T###a#",
			"#aaaaaaaaaaaaaaaaaaaaaaaaa#aaaa#",
			"#aaaaaaaaaaaaaaaaaaaaaaaaa#aaaa#",
			"####T####aaaaaaT###T###T###T##################",
			"#aaaaaaTaaaaaaaaaaaaaaaaaaaaaac#aaaaaaaaaaaaZ#",
			"#aaaaaa#aaaaaaaaaaaaaaaaaaaaaaa*aaaaaaaaaaaaa#",
			"#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#aaaaaaaaaaaaa#",
			"####T###T###T###T###T###T###T#################"
	}, 23, 13,
		new Monster(Monster.Type.ICEKING, 3, 1),
		new Monster(Monster.Type.ICEKING, 6, 3),
		new Monster(Monster.Type.ICEKING, 3, 13),
		new Monster(Monster.Type.ICEKING, 25, 5),
		new Monster(Monster.Type.PENGUIN, 13, 6),
		new Monster(Monster.Type.PENGUIN, 15, 14),
		new Monster(Monster.Type.PENGUIN, 12, 6),
		new Monster(Monster.Type.PENGUIN, 10, 6),
		new Monster(Monster.Type.PENGUIN, 19, 6),
		new Monster(Monster.Type.PENGUIN, 17, 1));
		
	public static final Floor LEVEL_2 = new Floor(new String[] {
//			Max height = 14
//			Max Width = 31			
			"####T###T###T###T###T###T###T###",
			"#fddeeeeeeeeeeeeeeeeeeeeeeedddd#",
			"#########dd#####################",
			"####Tdddddddddd#Tdddddddddddddf#",
			"##########Tdddd##dddd###T###T###",
			"#deeeeeeeeeeeeeeeeeeeeeeeeeeeed#",
			"###T##############Tddddd########",
			"###deeeeeeeeeeeeeeeeeeeeeeeeeed#",
			"######dddddd###T########T###T###",
			"#feeeeeeeeeeeeeeeeeeeed#########",
			"######T#####T######dddddd#######",
			"#ddeeeeeeeeed###dddddddd##ddddf#",
			"#dddd##dddddd##TddeeeeddT#dddddT",
			"#dddd##dddddd###dddddddf##ddddd#",
			"#^ddd##dddddddddddddddddddddd###",
			"####T###T###T###T###T###T###T###"
	}, 30, 1,
		new Monster(Monster.Type.BUTLER, 10, 3),
		new Monster(Monster.Type.BUTLER, 15, 7),
		new Monster(Monster.Type.BUTLER, 5, 7),
		new Monster(Monster.Type.BUTLER, 9, 11),
		new Monster(Monster.Type.BUTLER, 5, 13),
		new Monster(Monster.Type.BUTLER, 13, 14),
		new Monster(Monster.Type.BUTLER, 20, 7),
		new Monster(Monster.Type.BUTLER, 25, 7));
	
	public static final Floor LEVEL_3 = new Floor(new String[] {
//			Max height = 10
//			Max Width = 39
			"iiihiiiiiiihiiiiiiihiiihiiihiiihiiihiiii",
			"igggggggggggggihgggggggggggggggigggggggi",
			"iggggiiiiiggggiggggggggggggggggigggggggi",
			"igggghiiihggggigggggggggggggggghgggggggi",
			"iggggiiiiiggggiiihiiihiiihgggggigggggggi",
			"iggggggggggggggggggggggggigggggiiiigiiii",
			"iiihiiihiiihiiigggggiiiiiigggggggggggggi",
			"iggggggggggggggggggggggggggggggggggggggi",
			"igggggiiigggggiiiiggggghiiihiiihiiihiiii",
			"iggggghiiggggghiihgggggggggggggggggggggi",
			"igggggiiigggggiiiigggggggggggggggggggggi",
			"ihiiiiiiiiihiiiiiiiiihiiihiiihiiihiiihii"
	}, 35, 9,
		new Monster(Monster.Type.PENGUIN, 2, 9),
		new Monster(Monster.Type.PENGUIN, 4, 8),
		new Monster(Monster.Type.PENGUIN, 10, 3),
		new Monster(Monster.Type.PENGUIN, 20, 9),
		new Monster(Monster.Type.PENGUIN, 19, 7),
		new Monster(Monster.Type.PENGUIN, 27, 6),
		new Monster(Monster.Type.ICEKING, 10, 4),	
		new Monster(Monster.Type.ICEKING, 2, 7),
		new Monster(Monster.Type.ICEKING, 3, 1),	
		new Monster(Monster.Type.ICEKING, 38, 2),
		new Monster(Monster.Type.BUTLER, 25, 10),
		new Monster(Monster.Type.BUTLER, 15, 7),
		new Monster(Monster.Type.BUTLER, 5, 7),
		new Monster(Monster.Type.BUTLER, 36, 3),
		new Monster(Monster.Type.BUTLER, 5, 10),
		new Monster(Monster.Type.BUTLER, 13, 10),
		new Monster(Monster.Type.BOSS, 1, 1)
	);
}