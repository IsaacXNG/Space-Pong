import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*	
 * 	Author: Xiaoning Guo
 * 	NetID: xguo24 
 * 	Project: 4
 * 	Lab: T,R / 11:05-12:20
 * 	
 * 	I did not collaborate with anyone on this assignment.
 * 
 * 	This is the class that makes loading images easier.
 */
public class Images {
	
	BufferedImage img = null;
	
	public Images(String location){
		try {
		    img = ImageIO.read(new File("Pictures/"+location));
		} catch (IOException e) {
			System.out.println("No picture found.");
		}
	}
	
	public BufferedImage getImage(){
		return img;
	}
}
