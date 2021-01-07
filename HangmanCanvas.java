/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {
	
	//used to construct the hangman image
	String Letters = "" ;

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		ConstructScaffold();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		
		double x = (getWidth()/2 - BEAM_LENGTH);
		double y = (getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH*2));
		
		GLabel labelWord = new GLabel(word, x, y);	
		labelWord.setFont("Calibri-36");
		if (getElementAt(x,y) != null){remove(getElementAt(x,y));}
		add(labelWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	
	public void noteIncorrectGuess(String Letter) {
		
		String GuessedLetters = "Guessed Letters: ";
		Letters = Letters + Letter;
		
		double x = (getWidth()/2 - BEAM_LENGTH);
		double y = (getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)+ SCAFFOLD_HEIGHT + ROPE_LENGTH*1.5);

		
		GLabel guessedLetter = new GLabel(GuessedLetters + Letters);
		guessedLetter.setFont("Calibri-24");
		guessedLetter.setLocation(x, y);
		if (getElementAt(x,y) != null){remove(getElementAt(x,y));}
		
		add(guessedLetter);
			
		if (Letters.length() == 1){ConstructHead();}
		if (Letters.length() == 2){ConstructBody();}
		if (Letters.length() == 3){ConstructLeftArm();}
		if (Letters.length() == 4){ConstructRightArm();}
		if (Letters.length() == 5){ConstructLeftLeg();}
		if (Letters.length() == 6){ConstructRightLeg();}
		if (Letters.length() == 7){ConstructLeftFoot();}
		if (Letters.length() == 8){ConstructRightFoot();
								   ConstructDeadFace();}
	}

	public void Congratulations(){
		ConstructHead();
		ConstructHappyFace();
	}
	
/** constructs the scaffold */	
	private void ConstructScaffold(){
		GLine Scaffold = new GLine((getWidth()/2 - BEAM_LENGTH),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)), (getWidth()/2 - BEAM_LENGTH),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)+ SCAFFOLD_HEIGHT));
		GLine Beam = new GLine ((getWidth()/2 - BEAM_LENGTH),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)),(getWidth()/2),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)));
		GLine Rope = new GLine ((getWidth()/2),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2 + ROPE_LENGTH)),(getWidth()/2),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2)));
		add (Scaffold);
		add (Beam);
		add (Rope);
	}

/** constructs the head */	
	private void ConstructHead(){
		GOval Head = new GOval ((getWidth()/2 - HEAD_RADIUS),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS*2)),(HEAD_RADIUS*2),(HEAD_RADIUS*2));
		add (Head);
	}

/** constructs the body */	
	private void ConstructBody(){
		GRect Body = new GRect ((getWidth()/2 - BODY_LENGTH/6), (getHeight()/2 - BODY_LENGTH/2), (BODY_LENGTH/3), BODY_LENGTH);
		Body.setFillColor(rgen.nextColor());
		Body.setFilled(true);
		add (Body);
	}
	
/** constructs the left arm */	
	private void ConstructLeftArm(){
		GRect UpperLeftArm = new GRect ((getWidth()/2 - BODY_LENGTH/6 - UPPER_ARM_LENGTH/4), (getHeight()/2 - BODY_LENGTH/2 + ARM_OFFSET_FROM_HEAD/4), (UPPER_ARM_LENGTH/4), UPPER_ARM_LENGTH);
		GRect LowerLeftArm = new GRect ((getWidth()/2 - BODY_LENGTH/6 - UPPER_ARM_LENGTH/4), (getHeight()/2 - BODY_LENGTH/2 + ARM_OFFSET_FROM_HEAD/4 + UPPER_ARM_LENGTH),(UPPER_ARM_LENGTH/4), LOWER_ARM_LENGTH);
		UpperLeftArm.setFilled(true);
		UpperLeftArm.setFillColor(rgen.nextColor());
		add (UpperLeftArm);
		add (LowerLeftArm);
	}

/** constructs the right arm */
	private void ConstructRightArm(){
		GRect UpperRightArm = new GRect ((getWidth()/2 + BODY_LENGTH/6), (getHeight()/2 - BODY_LENGTH/2 + ARM_OFFSET_FROM_HEAD/4), (UPPER_ARM_LENGTH/4), UPPER_ARM_LENGTH);
		GRect LowerRightArm = new GRect ((getWidth()/2 + BODY_LENGTH/6), (getHeight()/2 - BODY_LENGTH/2 + ARM_OFFSET_FROM_HEAD/4 + UPPER_ARM_LENGTH),(UPPER_ARM_LENGTH/4), LOWER_ARM_LENGTH);
		UpperRightArm.setFilled(true);
		UpperRightArm.setFillColor(rgen.nextColor());
		add (UpperRightArm);
		add (LowerRightArm);
	}

/** constructs the left leg */
	private void ConstructLeftLeg(){
		GRect LeftLeg = new GRect ((getWidth()/2 - BODY_LENGTH/6), (getHeight()/2 + BODY_LENGTH/2 + HIP_WIDTH),(BODY_LENGTH/6), (LEG_LENGTH - HIP_WIDTH - FOOT_LENGTH));
		GRect LeftHip = new GRect ((getWidth()/2 - BODY_LENGTH/6), (getHeight()/2 + BODY_LENGTH/2),(BODY_LENGTH/6),HIP_WIDTH);
		LeftHip.setFilled(true);
		LeftHip.setFillColor(rgen.nextColor());
		add (LeftHip);
		add (LeftLeg);
	}
	
/** constructs the right leg */
	private void ConstructRightLeg(){
		GRect RightHip = new GRect ((getWidth()/2), (getHeight()/2 + BODY_LENGTH/2),(BODY_LENGTH/6),HIP_WIDTH);
		GRect RightLeg = new GRect ((getWidth()/2), (getHeight()/2 + BODY_LENGTH/2 + HIP_WIDTH),(BODY_LENGTH/6), (LEG_LENGTH - HIP_WIDTH - FOOT_LENGTH));
		RightHip.setFilled(true);
		RightHip.setFillColor(rgen.nextColor());
		add (RightHip);
		add (RightLeg);
	}
	
/** constructs the left foot */
	private void ConstructLeftFoot(){
		GRect LeftFoot = new GRect ((getWidth()/2 - FOOT_LENGTH), (getHeight()/2 + BODY_LENGTH/2 + LEG_LENGTH - FOOT_LENGTH), FOOT_LENGTH, (FOOT_LENGTH/2));
		LeftFoot.setFilled(true);
		LeftFoot.setFillColor(rgen.nextColor());
		add (LeftFoot);
	}
	
/** constructs the right foot */
	private void ConstructRightFoot(){
		GRect RightFoot = new GRect ((getWidth()/2), (getHeight()/2 + BODY_LENGTH/2 + LEG_LENGTH - FOOT_LENGTH), FOOT_LENGTH, (FOOT_LENGTH/2));
		RightFoot.setFilled(true);
		RightFoot.setFillColor(rgen.nextColor());
		add (RightFoot);
	}
	
/** constructs the dead face when the player loses */
	private void ConstructDeadFace(){
		GOval Nose = new GOval ((getWidth()/2 - HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)),(HEAD_RADIUS*2/6),(HEAD_RADIUS*2/6));
		Nose.setFillColor(Color.RED);
		Nose.setFilled(true);
		add(Nose);
		
		GLabel LeftEye = new GLabel("X");
		LeftEye.setFont("Calibri-28");
		LeftEye.setLocation((getWidth()/2 - LeftEye.getWidth() - HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)));
		add(LeftEye);
		
		GLabel RightEye = new GLabel("X");
		RightEye.setFont("Calibri-28");
		RightEye.setLocation((getWidth()/2 + HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)));
		add(RightEye);
		
		GLine Mouth = new GLine((getWidth()/2 - HEAD_RADIUS/3),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/2)),(getWidth()/2 + HEAD_RADIUS/3),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/2)));
		add(Mouth);
		
		GArc Tongue = new GArc ((getWidth()/2),((getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/1.5))), (HEAD_RADIUS/4), (HEAD_RADIUS/3), 0, -180);
		Tongue.setFillColor(Color.RED);
		Tongue.setFilled(true);
		add(Tongue);
	}
	
	/** constructs the happy face when the player wins */
	private void ConstructHappyFace(){
		GOval Nose = new GOval ((getWidth()/2 - HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)),(HEAD_RADIUS*2/6),(HEAD_RADIUS*2/6));
		Nose.setFillColor(Color.RED);
		Nose.setFilled(true);
		add(Nose);
		
		GLabel LeftEye = new GLabel("O");
		LeftEye.setFont("Calibri-28");
		LeftEye.setLocation((getWidth()/2 - LeftEye.getWidth() - HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)));
		add(LeftEye);
		
		GLabel RightEye = new GLabel("O");
		RightEye.setFont("Calibri-28");
		RightEye.setLocation((getWidth()/2 + HEAD_RADIUS/6),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS)));
		add(RightEye);
		
		GLine UpperLip = new GLine((getWidth()/2 - HEAD_RADIUS/3),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/2)),(getWidth()/2 + HEAD_RADIUS/3),(getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/2)));
		add(UpperLip);
		
		GArc LowerLip = new GArc ((getWidth()/2 - HEAD_RADIUS/3),((getHeight()/2 - (BODY_LENGTH/2 + HEAD_RADIUS/2 + HEAD_RADIUS/4))), (UpperLip.getWidth()), (HEAD_RADIUS/2), 0, -180);
		add(LowerLip);
	}
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();


}
