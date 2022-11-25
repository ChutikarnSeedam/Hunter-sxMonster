package Project.resources;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;

import javax.imageio.ImageIO;

public class Textures {

	private static HashMap<String, BufferedImage> sprites;
	
//	Initialize HashMap name sprites to contains Image name and Image
	public static void init() {
		sprites = new HashMap<String, BufferedImage>();
		
		File folder = new File("resource");
		
		for(File file : folder.listFiles()) {
			try {
				sprites.put(file.getName().replaceAll(".png", ""), ImageIO.read(file));
			} catch (IOException e) {
			}
		}
	}
	
//	Return sprite which contains image name by using name to select
	public static BufferedImage getSprite(String name) {
		BufferedImage sprite = sprites.get(name);
		if(sprite != null) return sprite;
		else return sprites.get("error");
	}
}