/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 5;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Gamespeed delay in ms */
	private static final int DELAY = 30;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		addMouseListeners();
		SetupGame();
		PlayGame();
	}

/**	Sets the game up. */
	private void SetupGame(){
		BuildBricks();
		BuildPaddle();	
	}	
	
/**	Builds the wall of Bricks. */
	private void BuildBricks(){		
		for (int i=0; i<NBRICK_ROWS; i++){
			for (int j=0; j<NBRICKS_PER_ROW; j++){
				
				//starting coordinates so that the wall is centered.
				double x = WIDTH/2 - BRICK_SEP*(NBRICKS_PER_ROW-1)/2 - BRICK_WIDTH*NBRICKS_PER_ROW/2 + j*(BRICK_WIDTH + BRICK_SEP);
				double y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT + BRICK_SEP);
				
				//constructor of the brick
				GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				
				//colorscheme of the bricks
				if (i==0 || i==1) brick.setColor(Color.RED);
				if (i==2 || i==3) brick.setColor(Color.ORANGE);
				if (i==4 || i==5) brick.setColor(Color.YELLOW);
				if (i==6 || i==7) brick.setColor(Color.GREEN);
				if (i==8 || i==9) brick.setColor(Color.CYAN);
				
				add(brick);
			}	
		}
	}
	
/** Builds the paddle */	
	private void BuildPaddle(){
		//starting coordinates so that the paddle starts centered.
		double x = WIDTH/2 - PADDLE_WIDTH/2;
		double y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		
		//constructor of the paddle.
		Paddle = new GRect (x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
		Paddle.setFilled(true);
		add(Paddle);		
	}
	
/**Builds the ball */
	private void BuildBall(){
		//starting coordinates of the ball, centered
		double x = WIDTH/2 - BALL_RADIUS;
		double y = HEIGHT/2 - BALL_RADIUS;
			
		//constructs the ball	
		Ball = new GOval (x,y,BALL_RADIUS*2,BALL_RADIUS*2);
		Ball.setFilled(true);		
		add(Ball);
	}	
	
/** Launches the ball(s) and plays the game */	
	private void PlayGame(){
		
		//Starting direction and velocity of the ball
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 3;
				
		//Build the first ball
		BuildBall();
		
		//Total number of bricks
		int TOTAL_BRICKS = NBRICKS_PER_ROW*NBRICK_ROWS;
		
		//number of tries for the player
		int lives = NTURNS;
		add(LifeCounter);
		
		//scoreboard
		int score = 0;		
		add(Score);
		
		//number of bounces the ball has made
		int bounces = 0;
		
		while (true){
			//updates the number of lives left in the life counter
			LifeCounter.setLabel("Lives: " + lives);
			
			//updates the score
			Score.setLabel("Score:   " + score);
			
			//moves the ball
			if(click !=0)Ball.move(vx,vy);
			pause(DELAY);
			
			//Makes the ball react to whatever it hits
			collider = getCollidingObject();			
			if (Ball.getX()<=0 || Ball.getX()>=WIDTH - 2*BALL_RADIUS){
				vx = -vx;
				bounceClip.play();
			}	
			if (Ball.getY()<=0){
				vy = -vy;
				bounceClip.play();
			}	
			if(collider != null && collider != Paddle && collider != LifeCounter && collider != Score && collider != clicker){	
				remove(collider);
				vy = -vy;
				TOTAL_BRICKS--;	
				bounceClip.play();
				if (Ball.getY() >= BRICK_Y_OFFSET && Ball.getY() < BRICK_Y_OFFSET + 2*(BRICK_HEIGHT + BRICK_SEP))score = score + 50;
				if (Ball.getY() >= BRICK_Y_OFFSET + 2*(BRICK_HEIGHT + BRICK_SEP) && Ball.getY() < BRICK_Y_OFFSET + 4*(BRICK_HEIGHT + BRICK_SEP))score = score + 25;
				if (Ball.getY() >= BRICK_Y_OFFSET + 4*(BRICK_HEIGHT + BRICK_SEP) && Ball.getY() < BRICK_Y_OFFSET + 6*(BRICK_HEIGHT + BRICK_SEP))score = score + 10;
				if (Ball.getY() >= BRICK_Y_OFFSET + 6*(BRICK_HEIGHT + BRICK_SEP) && Ball.getY() < BRICK_Y_OFFSET + 8*(BRICK_HEIGHT + BRICK_SEP))score = score + 5;
				if (Ball.getY() >= BRICK_Y_OFFSET + 8*(BRICK_HEIGHT + BRICK_SEP) && Ball.getY() < BRICK_Y_OFFSET + 10*(BRICK_HEIGHT + BRICK_SEP))score = score + 1;
			}
			if(collider == Paddle){
				if(vy >= 0){
					vy = -vy;
					bounceClip.play();
				}else{
					vx = -vx;
					bounceClip.play();
				}
				bounces++;
			}
			
			//victory conditions and label
			if(TOTAL_BRICKS == 0){
				vx = 0;
				vy = 0;
				if(lives == 3)score = score +5001;
				if(lives == 2)score = score +2501;
				if(lives == 1)score = score +1001;
				add(winnerlabel);
				break;
			}
			
			//failed attempt conditions
			if(Ball.getY()>=HEIGHT - 2*BALL_RADIUS){
				lives--;
				remove(Ball);
				if (lives > 0){					
					click = 0;
					BuildBall();
					vx = rgen.nextDouble(1.0, 3.0);
					if (rgen.nextBoolean(0.5)) vx = -vx;
				}			
			}
			
			//lose conditions and label
			if(lives == 0){
				LifeCounter.setLabel("Lives: " + lives);
				add(GameOver);
				break;			
			}
			
			//sends a message if user clicks a lot			
			if(click == 9) add(clicker);
			if(click == 19) remove(clicker);
			
			//speeds the ball up after the set number of bounces
			if(bounces == 7){
				vx = vx*2;
				bounces = 0;
			}
		}
	}	
	
/** Controls movement of the paddle */	
	public void mouseMoved(MouseEvent e){
		
		//coordinates of the paddle
		double x = e.getX() - PADDLE_WIDTH/2;
		double y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		
		//moves the paddle
		if (x >= 0 && x <= WIDTH - PADDLE_WIDTH) Paddle.setLocation(x,y);
	}

/** Sets the ball to be launched when the mouse is clicked */	
	public void mouseClicked(MouseEvent e){
		click++;		
	}
	
/** Tells the game whether the ball has hit something */	
	private GObject getCollidingObject(){
		if (getElementAt(Ball.getX(), Ball.getY()) != null){
			return getElementAt(Ball.getX(), Ball.getY());
			
		} else if (getElementAt(Ball.getX() + 2*BALL_RADIUS, Ball.getY()) != null){
			return getElementAt(Ball.getX() + 2*BALL_RADIUS, Ball.getY());
			
		} else if (getElementAt(Ball.getX() + 2*BALL_RADIUS, Ball.getY() + 2*BALL_RADIUS) != null){		
			return getElementAt(Ball.getX() + 2*BALL_RADIUS, Ball.getY() + 2*BALL_RADIUS);
			
		} else if (getElementAt(Ball.getX(), Ball.getY() + 2*BALL_RADIUS) != null){	
			return getElementAt(Ball.getX(), Ball.getY() + 2*BALL_RADIUS);
			
		} else {return null;}			
	}	
	
/** Labels the winner text */	
	private GLabel winnerlabel(){
		GLabel winnerlabel = new GLabel ("Winner winner Breakoutdinner!");
		winnerlabel.setFont("Calibri-24");
		winnerlabel.setLocation(WIDTH/2 - winnerlabel.getWidth()/2,HEIGHT/2 - winnerlabel.getHeight()/2);		
		return winnerlabel;
	}
	
/** Labels the game over text */	
	private GLabel GameOver(){
		GLabel GameOver = new GLabel ("Game Over!");
		GameOver.setFont("Calibri-24");
		GameOver.setLocation(WIDTH/2 - GameOver.getWidth()/2,HEIGHT/2 - GameOver.getHeight()/2);		
		return GameOver;
	}

/** Labels the life counter */	
	private GLabel LifeCounter(){
		GLabel LifeCounter = new GLabel (" ");
		LifeCounter.setFont("Calibri-24");
		LifeCounter.setLocation(1,LifeCounter.getHeight());
		return LifeCounter;
	}
	
/** Labels the score */
	private GLabel Score(){
		GLabel Score = new GLabel ("Score:     0");
		Score.setFont("Calibri-24");
		Score.setLocation(WIDTH - Score.getWidth() - 50,Score.getHeight());
		return Score;
	}
	
/** Label for the overenthusiastic clicker */	
	private GLabel clicker(){
		GLabel clicker = new GLabel("Easy, tiger!");
		clicker.setFont("Calibri-36");
		clicker.setLocation(WIDTH/2-clicker.getWidth()/2,HEIGHT - HEIGHT/4);
		return clicker;
	}
	
/* Private variables */
	private GRect Paddle;
	private	GOval Ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GObject collider;
	private GLabel winnerlabel = winnerlabel();
	private GLabel GameOver = GameOver();
	private GLabel LifeCounter = LifeCounter();	
	private GLabel clicker = clicker();
	private GLabel Score = Score();
	private int click;
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
}
