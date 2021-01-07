/*
 * File: Hangman.java
 * ------------------
 * This program will play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	
/** Number of guesses player gets */
	private static final int Guesses = 8;
	
/** Displayed symbol where the letter isn't guessed */	
	private static final String blank = "-";

// adds the graphics window.
	
	public void init(){
		canvas = new HangmanCanvas();
		add(canvas);
	}

    public void run() {
    	
// Sets the game up.
    	
    	canvas.reset();
		println("Welcome to Hangman!");
		setupSolution();
		int tries = Guesses;
		
		
		//Constructs the word the player gets as feedback.
		String Word = "";
    	for (int i = 0; i < Solution.length(); i++){
    		Word = Word + blank;
    	}
// Runs the game.		
		while(true){
			if (tries != 0){
				//Asks for input and displays the word the player sees and the guesses he has left.
				println("The word now looks like this: " + Word);				
				println("You have " + tries + " guesses left.");
				String Letter = readLine("Your guess: ");
								
				//makes sure the user enters only 1 letter
				while (Letter.length() != 1){Letter = readLine("Please enter only one letter: ");}
								
				//converts the letter to uppercase if necessary
				if (Character.isLowerCase(Letter.charAt(0))){Letter = Letter.toUpperCase();}
				
				char letter = Letter.charAt(0);
				
				//will be used to check whether the guessed letter is part of the solution.
				String OldWord = Word;
				
				//Checks wether the entered letter is part of the solution and modifies the word the user sees if applicable.
				Word = "";
		    	for (int i = 0; i < Solution.length(); i++){
		    		if (Solution.charAt(i) == (letter)){Word += letter;
		    		}else{Word += OldWord.charAt(i);}
		    	
		    	//updates the word displayed on the canvas	
		    	canvas.displayWord(Word);	
		    	}
				
				//win event
				if (Word.equals(Solution)){
					println("You guessed the word: " + Solution);
					println("You win.");
					canvas.Congratulations();
					break;
				}
				
				//no such letter in the solution event
				if (Word.equals(OldWord)){
					println("There are no " + Letter + "'s in the word.");
					tries--;
					canvas.noteIncorrectGuess(Letter);
				}	
				
			}else{
				//lose event
				println("You're completely hung.");
				println("The word was: " + Solution);
				println("You lose.");
				break;
			}
		}		
	}
    

 /** Selects the word that needs to be guesssed */   
    private String setupSolution(){
    	int i = rgen.nextInt(0,WordPool.getWordCount()-1);
    	String str = WordPool.getWord(i);
    	return str;
    }
 
//	Private instance variables.
	
    private HangmanLexicon WordPool = new HangmanLexicon();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String Solution = setupSolution();
	private HangmanCanvas canvas;
	public char letter;


	

}
