/*	
 * 	Author: Xiaoning Guo
 * 	NetID: xguo24 
 * 	Project: 4
 * 	Lab: T,R / 11:05-12:20
 * 	
 * 	I did not collaborate with anyone on this assignment.
 * 
 * 	This is the class that stores information about the state of objects. 
 */
public class GameState {
	private int lives;
	
	private double barX;
	private boolean barLeft, barRight;
	private int barLength;
	private double barSpeed;
	
	private double ballX;
	private double ballRadius;
	private boolean ballReflectX; //Default is false for regular delta x
	private boolean ballReflectY;
	private double ballY;
	private double ballAngle;
	private double ballSpeed;
	private double ballFlightTime; //Amount of time the ball has flown. Purpose of beginning and terminating the trajectory curve.
	private double deltaTime; //Time increment for dx/dt
	
	private Images background;
	private Images bar;
	private Images ball;
	
	
	
	public GameState(){
		reset();
	}
	
	public void reset(){
		barRight = false;
		barLeft = false;
		ballReflectX = false;
		ballReflectY = false;
		ballRadius = 10;
		
		lives = 3;
		deltaTime = 0.03;
	}
	public double getBarX() {
		return barX;
	}
	public void setBarX(double barX) {
		this.barX = barX;
	}
	public void shiftBarX(double shift){
		barX+=shift;
	}
	
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	public boolean isBarRight() {
		return barRight;
	}
	public void setBarRight(boolean barRight) {
		this.barRight = barRight;
	}
	public boolean isBarLeft() {
		return barLeft;
	}
	public void setBarLeft(boolean barLeft) {
		this.barLeft = barLeft;
	}

	public int getBarLength() {
		return barLength;
	}

	public void setBarLength(int barLength) {
		this.barLength = barLength;
	}

	public double getBarSpeed() {
		return barSpeed;
	}

	public void setBarSpeed(double barSpeed) {
		this.barSpeed = barSpeed;
	}

	public double getBallSpeed() {
		return ballSpeed;
	}

	public void setBallSpeed(double ballSpeed) {
		this.ballSpeed = ballSpeed;
	}

	public Images getBackground() {
		return background;
	}

	public void setBackground(Images background) {
		this.background = background;
	}

	public Images getBar() {
		return bar;
	}

	public void setBar(Images bar) {
		this.bar = bar;
	}

	public double getBallX() {
		return ballX;
	}

	public void setBallX(double ballX) {
		this.ballX = ballX;
	}

	public double getBallY() {
		return ballY;
	}

	public void setBallY(double ballY) {
		this.ballY = ballY;
	}

	public Images getBall() {
		return ball;
	}

	public void setBall(Images ball) {
		this.ball = ball;
	}

	public double getBallAngle() {
		return ballAngle;
	}

	public void setBallAngle(double ballAngle) {
		this.ballAngle = ballAngle;
	}

	public double getBallFlightTime() {
		return ballFlightTime;
	}

	public void setBallFlightTime(double ballTime) {
		this.ballFlightTime = ballTime;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}

	public boolean isBallReflectX() {
		return ballReflectX;
	}

	public void setBallReflectX(boolean ballReflectX) {
		this.ballReflectX = ballReflectX;
	}

	public boolean isBallReflectY() {
		return ballReflectY;
	}

	public void setBallReflectY(boolean ballReflectY) {
		this.ballReflectY = ballReflectY;
	}

	public double getBallRadius() {
		return ballRadius;
	}

	public void setBallRadius(double ballRadius) {
		this.ballRadius = ballRadius;
	}
	
}
