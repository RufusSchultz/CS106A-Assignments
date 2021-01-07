/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

/** This is the HangmanLexicon constructor. */
public class HangmanLexicon {
	
	ArrayList<String> WordList = new ArrayList<String>();{
	
	try{
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true){
				String line = rd.readLine();
				WordList.add(line);
				if(line == null)break;
			}
			rd.close();
	}catch (IOException ex){throw new ErrorException(ex);}
	}
		
		
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return WordList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return WordList.get(index);
	}
}
