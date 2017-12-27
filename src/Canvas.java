import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
/*	
 * 	Author: Xiaoning Guo
 * 	NetID: xguo24 
 * 	Project: 4
 * 	Lab: T,R / 11:05-12:20
 * 	
 * 	I did not collaborate with anyone on this assignment.
 * 
 * 	This is the class that actually draws all the graphics.
 */
public class Canvas extends JComponent{
	private final double GRAVITY = 9.8;
	
	private DecimalFormat formatterScore = new DecimalFormat("000000");
	private DecimalFormat formatterTime = new DecimalFormat("00");
	private Random random = new Random();
	private Timer timer;
	private GameState gs;
	private Images heart1, heart2, heart3;
	private Images flameLeft, flameRight; //Jet flames
	
	private int stageNumber;
	private int time;
	private int score;
	
	File hiscoreFile = new File("Hiscore.txt");
	
	private boolean isIntroPhase, isRetryPhase, isVictoryPhase;
	private Images intro, retry, victory;
	
	private Images loser;     
 	
	private int internalTimer; //Purpose of timing the fade in fade out press space to continue
	private boolean continueVisibility; //Blinking continue prompt
	private Images continueScreen; //Image for the blinking continue prompt
	private Images playAgain; //Image for the blinking play again prompt
	
	private Images instructions1, instructions2, instructions3; //Images for the tutorial
	private int instructNum; //Index for the tutorial slide
	
	private boolean skip; //Moves onto the next phase. This boolean acts as a button.
	
	public Canvas(GameState gs){
		timer = new Timer(10, new RunAnimation());
		timer.start();
		reset(gs);
	}
	
	public void reset(GameState gs){
		this.gs = gs;
		
		heart1 = new Images("Heart_Alive.png");
		heart2 = new Images("Heart_Alive.png");
		heart3 = new Images("Heart_Alive.png");
		flameLeft = new Images("FireLeft.png");
		flameRight = new Images("FireRight.png");
		
		intro = new Images("Intro_Stage1.png");
		instructions1 = new Images("Instructions1.png");
		instructions2 = new Images("Instructions2.png");
		instructions3 = new Images("Instructions3.png");
		instructNum = 1;

		isIntroPhase = true; 
		isRetryPhase = false;
		isVictoryPhase = false;
		
		loser = new Images("Loser.png");
		retry = new Images("Retry.png");
		victory = new Images("Victory.png");
		
		internalTimer = 0;
		continueVisibility = true;
		continueScreen = new Images("Continue.png");

		playAgain = new Images("PlayAgain.png");
	
		score = 0;
		
		skip = false;
		stageNumber = 1;
		stageSelection();
	}
	
	public void paintComponent(Graphics g){
		//Draws the background
		g.drawImage(gs.getBackground().getImage(),0,0,null);
		
		//Draws lives  
		g.drawImage(heart1.getImage(), 6, 6, null);
		g.drawImage(heart2.getImage(), 33, 6, null);
		g.drawImage(heart3.getImage(), 60, 6, null);
		
		//Draws bar
		g.drawImage(gs.getBar().getImage(),(int)gs.getBarX(),400,null);
		//Draws the right bar flame. Stage 4 has a reversed effect.
		if(gs.isBarLeft() && !isIntroPhase && !isRetryPhase && !isVictoryPhase){
			if(stageNumber == 4){ 
				g.drawImage(flameLeft.getImage(), (int)gs.getBarX()-57, 400, null);
			}else{
				g.drawImage(flameRight.getImage(), (int)gs.getBarX()+gs.getBarLength() - 5, 400, null);
			}
		}
		//Draws the left bar flame. Stage 4 has a reversed effect.
		if(gs.isBarRight()&& !isIntroPhase && !isRetryPhase && !isVictoryPhase){
			if(stageNumber == 4){
				g.drawImage(flameRight.getImage(), (int)gs.getBarX()+gs.getBarLength() - 5, 400, null);
			}else{
				g.drawImage(flameLeft.getImage(), (int)gs.getBarX()-57, 400, null);
			}
		}
			
		//Draws the ball
		if(stageNumber == 6){ //Stage 6 has invisible ball mechanics
			if(gs.getBallY()<420 && gs.getBallY()>150){
			}else{
				g.drawImage(gs.getBall().getImage(),(int)(gs.getBallX()- gs.getBallRadius()), getHeight() - (int)gs.getBallY() - (int)gs.getBallRadius(),null);
			}
			
		}else{
			g.drawImage(gs.getBall().getImage(),(int)(gs.getBallX()- gs.getBallRadius()), getHeight() - (int)gs.getBallY() - (int)gs.getBallRadius(),null);
		}
		
		//Draws score
		g.setColor(Color.WHITE);
		g.drawString("Score: "+formatterScore.format(score), 6, 43);
		g.drawString("Time: " + formatterTime.format(time), 6, 58+15);
		
		if(score > readHighScore()){
			writeHighScore();
		}
		
		g.drawString("Highscore: " + formatterScore.format(readHighScore()), 483, 20);
		
		//Draws stage number
		g.drawString("Stage: " + stageNumber, 6, 58);
		
		//If you lose all 3 lives
		if(gs.getLives()<=0){
			isRetryPhase = false;
			g.drawImage(loser.getImage(), 0, 0, null);
			heart1 = new Images("Heart_Dead.png");
			if(skip){
				skip = false;
				gs.reset();
				reset(gs);
			}
		}
		
		//If you win the game
		if(isVictoryPhase){
			g.drawImage(victory.getImage(), 0, 0, null);
			if(skip){
				isVictoryPhase = false;
				stageNumber = 1;
				stageSelection();
				gs.reset();
				reset(gs);
				skip = false;
			}
		}
		
		//Instructions for the game. Only occurs at the beginning of the game.
		if(isIntroPhase && stageNumber==1){
			if(instructNum == 1){
				g.drawImage(instructions1.getImage(), 0, 0, null);
			}else if(instructNum == 2){
				g.drawImage(instructions2.getImage(), 0, 0, null);
				skip = false;
			}else if(instructNum == 3){ 
				g.drawImage(instructions3.getImage(), 0, 0, null);
				skip = false;
			}else if(instructNum == 4){
				g.drawImage(intro.getImage(), 0, 0, null);
			}
			else{
				if(skip){
					skip = false;
					isIntroPhase = false;
			}
			}
			
		}
		//Each stage has a prompt and introduction
		else if(isIntroPhase){
			g.drawImage(intro.getImage(), 0, 0, null);	
			if(skip){
				skip = false;
				isIntroPhase = false;
			}
		}
		
		//Try again
		if(isRetryPhase){
			g.drawImage(retry.getImage(), 0, 0, null);	
			if(skip){
				stageSelection();
				isRetryPhase = false;
				skip = false;
			}
		
		}
		
		//Blinks the prompt to continue
		if(continueVisibility){
			if(isVictoryPhase || gs.getLives()<=0){
				g.drawImage(playAgain.getImage(), 0, 0, null);
			}else{
				g.drawImage(continueScreen.getImage(), 0, 0, null);
			}
			
		}
		
	}

	public class RunAnimation implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//Blinks the prompt to continue
			if(isIntroPhase || isRetryPhase || isVictoryPhase || gs.getLives()<=0){
				continueVisibility = true;
				internalTimer++;
				if(internalTimer>=70){
					internalTimer=0;
				}
				if(internalTimer>=35){
					continueVisibility = false;
				}
				
			}
			else{//Game Phase
				continueVisibility = false;
				countDown();//Count down timer
				moveBar();//Moves the bar
				moveBall();	//Moves the ball
				checkLoss();//Condition for losing a life
				checkWin();//Condition for winning
				skip = false;
			}
			repaint();	
		}
	
	}
	
	public void stageSelection(){
		
		gs.setBall(new Images("Ball.png"));
		gs.setBallFlightTime(0);
		generateRandomBallSettings();
		gs.setBarX(600/2 - gs.getBarLength()/2); //Set the bar to the middle of the screen
		time = 30;
		
		//Changes the heart values after every death
		if(gs.getLives()==2)
			heart3 = new Images("Heart_Dead.png");
		if(gs.getLives()==1)
			heart2 = new Images("Heart_Dead.png");
		
		if(stageNumber == 1){
			gs.setBackground(new Images("Background1.png"));
			gs.setBar(new Images("Bar.png"));
			gs.setBarLength(100);
			gs.setBarSpeed(3.5);
			intro = new Images("Intro_Stage1.png");
			gs.setBallSpeed(120);
		}else if(stageNumber == 2){
			gs.setBackground(new Images("Background2.png"));
			gs.setBar(new Images("Bar_Green.png"));
			gs.setBarLength(100);
			gs.setBarSpeed(2);
			intro = new Images("Intro_Stage2.png");
			gs.setDeltaTime(0.025);
			gs.setBallSpeed(120);
		}else if(stageNumber == 3){
			gs.setBackground(new Images("Background3.png"));
			gs.setBar(new Images("Bar_Short.png"));
			gs.setBarSpeed(3.5);
			gs.setBarLength(80);
			intro = new Images("Intro_Stage3.png");
			gs.setDeltaTime(0.03);
			gs.setBallSpeed(140);
		}else if(stageNumber == 4){
			gs.setBackground(new Images("Background4.png"));
			gs.setBar(new Images("Bar_Red.png"));
			gs.setBarLength(100);
			gs.setBarSpeed(-3.5);
			intro = new Images("Intro_Stage4.png");
			gs.setBallSpeed(140);
		}else if(stageNumber == 5){
			gs.setBackground(new Images("Background5.png"));
			gs.setBar(new Images("Bar_Short.png"));
			gs.setBarSpeed(3.5);
			gs.setBarLength(80);
			intro = new Images("Intro_Stage5.png");
			gs.setBallSpeed(120);
			gs.setDeltaTime(0.05);
		}else if(stageNumber == 6){
			gs.setBackground(new Images("Background6.png"));
			gs.setBar(new Images("Bar_Short.png"));
			gs.setBarSpeed(4);
			gs.setBarLength(80);
			gs.setBall(new Images("Ball_Black.png"));
			intro = new Images("Intro_Stage6.png");
			gs.setBallSpeed(120);
			gs.setDeltaTime(0.0375);
		}
		
	}
	
	public void moveBar(){
		if(gs.isBarLeft())
			gs.shiftBarX(-gs.getBarSpeed());
		if(gs.isBarRight())
			gs.shiftBarX(gs.getBarSpeed());
		
		//Prevents the bar from moving out of the screen
		if(gs.getBarX() < 0){
			gs.setBarX(0);
		}
		if(gs.getBarX() > 600 - gs.getBarLength()){
			gs.setBarX(600 - gs.getBarLength());
		}
	}
	
	public void moveBall(){
	
		//Increment the flight time by a sliver of time
		gs.setBallFlightTime(gs.getBallFlightTime()+gs.getDeltaTime());
		
		//Parametric equations
		int dx = (int)(gs.getBallSpeed()*Math.cos(gs.getBallAngle()*Math.PI/180));
		int dy = (int)(gs.getBallSpeed()*Math.sin(gs.getBallAngle()*Math.PI/180));
		
		if(gs.isBallReflectX()){
			dx = -dx;
		}
		if(gs.isBallReflectY()){
			dy = -dy;
		}
		
		//Gravity is always downwards so we need to post-add it to dy to avoid negation.
		dy = (int) (dy - (GRAVITY)*gs.getBallFlightTime());
		
		//Reflect X after horizontal bounces
		if(gs.getBallX()>600-gs.getBallRadius()){
			gs.setBallReflectX(true);
		}
		if(gs.getBallX() < 0+gs.getBallRadius()){
			gs.setBallReflectX(false);
		}
		
		//Reflect Y after vertical bounces
		if(gs.getBallY()>440){
			gs.setBallReflectY(false);
		}
		//Checks to see if the bar is underneath the ball during bounce
		if(gs.getBallY()<50 && gs.getBallY()>40 && gs.getBallX()> gs.getBarX()-5 && gs.getBallX() < gs.getBarX()+gs.getBarLength()+5){
			gs.setBallFlightTime(0);
			gs.setBallReflectY(true);
			score+=10;
		}
		
		//Changes the ball position by speed x time
		gs.setBallX(gs.getBallX() + dx*gs.getDeltaTime());
		gs.setBallY(gs.getBallY() + dy*gs.getDeltaTime());
	}
	
	public void countDown(){
		internalTimer++;
		if(internalTimer>=100){
			internalTimer=0;
			time--;
		}
	}
	
	public void checkLoss(){
		if(gs.getBallY()<=-10){
			isRetryPhase = true;
			gs.setLives(gs.getLives()-1);
		}
	}
	
	public void checkWin(){
		if(time<=0 && stageNumber == 6){
			score+= gs.getLives()*1000;
			isVictoryPhase = true;    
		}else if(time<=0){
			isIntroPhase = true; 
			stageNumber++;
			stageSelection();
		}
	}
	
	//Each ball should have unique starting points and angles
	public void generateRandomBallSettings(){
		gs.setBallFlightTime(0);
		int x = random.nextInt(100)+250;
		int y = random.nextInt(100)+300;
		int a = random.nextInt(10)+50;
		int b = random.nextInt(2);
		
		if(b==0)
			gs.setBallReflectX(false);
		else
			gs.setBallReflectX(true);
		
		gs.setBallReflectY(true);

		gs.setBallX(x);
		gs.setBallY(y);
		gs.setBallAngle(-a);
	}
	
	public void writeHighScore(){
		try{
			PrintWriter output = new PrintWriter(hiscoreFile);
			output.println(score);
			output.close();
		}catch(IOException e){
			System.out.println("Something occured when outputting into file mike.txt");
		}
	}
	
	public double readHighScore(){
		try {
			Scanner in = new Scanner(hiscoreFile);
			double temp;
			temp = in.nextDouble();
			in.close();
			return temp;
			
		} catch (FileNotFoundException e) {
			System.out.println("This file cannot be found.");
		}
		return 0;

	}
	
	
	//A way for the JFrame keylistener to interact with the canvas timer
	public void skip(){
		instructNum++;
		skip = true;
	}

}
