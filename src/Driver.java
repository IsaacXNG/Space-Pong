import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
/*	
 * 	Author: Xiaoning Guo
 * 	NetID: xguo24 
 * 	Project: 4
 * 	Lab: T,R / 11:05-12:20
 * 	
 * 	I did not collaborate with anyone on this assignment.
 * 
 * 	This is the driver class that has the main method that starts the GUI.
 *  The dimensions are fixed for best window size. 
 */
public class Driver {
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 500;
		
	public static void main(String args[]){
		
		GameFrame newGame = new GameFrame("Space Pong");
		newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		newGame.setBounds((int)(screenSize.getWidth()/2) - WIDTH/2, (int)(screenSize.getHeight()/2) - HEIGHT/2, WIDTH, HEIGHT);
		newGame.setResizable(false);
		newGame.setVisible(true);
	
	}

}
