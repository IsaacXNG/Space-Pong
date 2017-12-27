Author: Xiaoning Guo
NetID: xguo24 
Project: 4
Lab: T,R / 11:05-12:20

DISCLAIMER: Most of the pictures are taken from Google images. Links to the sources are found in this folder.

NOTE: STAGE 4 IS NOT A MISTAKE. 

Instructions:	To play, simply click on the Jar file and execute as Java Binary SE. 
		Or import the project into Eclipse and run the Driver class.

		To test each level, go to line 101 in the Canvas class and change the StageNumber to the level you want to play.
		To win any level, go to line 271 in the Canvas class and change the time to 1.

Classes: 	Canvas - Paints all the graphics and handles most of the input commands
		Driver - Creates an instance of the game
		GameFrame - Generates GUI components and handles keyboard events
		GameState - Stores the state of objects in the game	
		Images - Helper class for storing and generating images
		SpecialEvents - Defines special objects in the game

How to play: 	The first three slides to the game tell you how to play.
		
About the game:	The object of the game is to prevent the ball from falling below the bar. Use the arrow keys 
		to control the bar and rebound the ball. You have a total of three chances or lives to beat the game.
		You move onto the next round if the timer runs down to 0. If you die, you lose a life and the round is reset.
		
		Stage 1: Normal round.
		Stage 2: Slow bar.
		Stage 3: Short bar.
		Stage 4: Reversed bar.
		Stage 5: Super fast ball.
		Stage 6: Semi-invisible ball. Incredibly difficult level.

How to score points: Rebouncing the ball yields between 40-60 points each. Winning the entire game adds 1000x the number of 
		     lives remaining.

Bonus features: Persistent Hiscore UI 
		Sick photoshop (hand-drawn ball, bar, instruction slides)
		Flashing press to continue prompt
		File menu with Restart Game button
	
