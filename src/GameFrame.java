import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
/*	
 * 	Author: Xiaoning Guo
 * 	NetID: xguo24 
 * 	Project: 4
 * 	Lab: T,R / 11:05-12:20
 * 	
 * 	I did not collaborate with anyone on this assignment.
 * 
 * 	This is the class that generates the GUI components and handles the keyboard events.
 */
public class GameFrame extends JFrame implements KeyListener{
	
	GameState gs;
	
	JMenuBar menuBar;
	JMenu menuFile;
	JMenuItem menuItemRestartGame;
	
	JPanel canvasPanel;
	
	Canvas canvas;
	
	public GameFrame(String title) {
		super(title);
		setIconImage(new Images("moon-and-stars.png").getImage());
		setLayout(new BorderLayout());
		gs = new GameState();
		
		initCanvas();
		initMenuBar();
		initMenuBarEvents();
		
		initKeyEvents();
	}
	
	public void initCanvas(){
		canvas = new Canvas(gs);
		canvasPanel = new JPanel();
		canvasPanel.setLayout(new BorderLayout());	
		canvasPanel.add(canvas, BorderLayout.CENTER);
		add(canvasPanel, BorderLayout.CENTER);
		
	}
	
	public void initMenuBar(){
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemRestartGame = new JMenuItem("Restart Game");
		menuFile.add(menuItemRestartGame);
	}
	
	public void initMenuBarEvents(){
		menuItemRestartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gs.reset();
				canvas.reset(gs);
			}
		});
	}
	
	public void initKeyEvents(){
		addKeyListener(this);
	}
	
	public void keyTyped(KeyEvent e) {
	
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			gs.setBarLeft(true);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			gs.setBarRight(true);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			canvas.skip();
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			gs.setBarLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			gs.setBarRight(false);
		}
	}
	
}
