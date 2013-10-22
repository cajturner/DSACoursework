package com.calumturner;
import java.io.*;
import java.util.Iterator;
 
public class spell {
   
	private static HashDictionary hDict;
    private static HashDictionary wordsDict;

    public static void main(String[] args) throws java.io.IOException{
    	long startTime= System.currentTimeMillis();
    	//If input arguments is not 2 print out error and exit.
        if (args.length != 2 ) {
        	System.out.println("Usage: spell dictionaryFile.txt inputFile.txt ");
            System.exit(0);
        }
        
    	BufferedInputStream dict,file;
         
        try{
        	//Initialise input streams and Dictionaries
        	dict  = new BufferedInputStream(new FileInputStream(args[0]));
            file  = new BufferedInputStream(new FileInputStream(args[1]));
            StringHashCode sHCode = new StringHashCode();
            hDict = new HashDictionary(sHCode,(float) 0.9);
            wordsDict = new HashDictionary(sHCode, (float)0.9);
            
            // Load all the words from dictionary into dictionary data structure 
            FileWordRead reader = new FileWordRead(dict);
            while(reader.hasNextWord()){
            	hDict.insert(reader.nextWord());
            }
            
            //Read word for word from text file
            reader = new FileWordRead(file);
            while(reader.hasNextWord()){
            	String word = reader.nextWord();
            	//Check if the words not in the dictionary and try to change it to match dictionary words
            	if(!hDict.find(word)){
            		letterSub(word);
            		letterOmi(word);
            		letterIns(word);
            		letterRev(word);
            	}
            }
         
         }
         catch (IOException e){ // Catch exceptions caused by file input/output errors
            System.out.println("Check your file name");
            e.printStackTrace();
            System.exit(0);
        } 
        //Iterates through all the elements in the words dictionary and prints them out
        Iterator<String> it =wordsDict.iterator();
        System.out.println("Suggested words:");
        while(it.hasNext()){
        	System.out.println(it.next());
        }
        System.out.println("---------------");
        long endTime = System.currentTimeMillis();
        System.out.println(args[0]+" -> "+args[1]+". Running time: "+(endTime-startTime));  
    }
    	/**
    	 * Goes over all the characters in the String word and tries to replace them with 
    	 * any other character, if a new word is found, insert it to the word dictionary.
    	 * @param String word. A word not in the dictionary.
    	 *      
    	 */
         private static void letterSub(String word){
        	 StringBuffer s;
        	 for(int i=0;i<word.length();i++){
        		 s= new StringBuffer(word);
        		 for(int j=0;j<26;j++){
        			 s.setCharAt(i, (char)(97+j));
        			 if(hDict.find(s.toString())) wordsDict.insert(s.toString()); 	 
        		 }
        	 } 
         }
         /**
          * Removes one character from the String word and checks if the new word is in the
          *	dictionary, if so add to word dictionary.
          * @param String word. A word not in the dictionary.
          */
         private static void letterOmi(String word){
        	 if(word.length()==1)return;
        	 char[] wordChars = word.toCharArray();
        	 String newWord="";
        	 for(int i =0;i<word.length();i++){
        		 wordChars= word.toCharArray();
        		 for(int j=i; j<word.length()-1;j++){
        			 wordChars[j]=wordChars[j+1];
        		 }
        		 newWord= new String(wordChars);
        		 newWord = newWord.substring(0, newWord.length()-1);
        		 if(hDict.find(newWord))wordsDict.insert(newWord);
        	 }
         }
         /**
          * Inserts all in succession before, between and after each character for String word. If any
          * new words match to the dictionary. Add new word to word dictionary.
          * @param String word. A word not in the dictionary. 
          */
         private static void letterIns(String word){
        	StringBuffer s;
        	for(int i=0;i<=word.length();i++){
        		for(int j=0;j<26;j++){
        			s= new StringBuffer(word);
        			s.insert(i, (char)(97+j));
        			if(hDict.find(s.toString()))wordsDict.insert(s.toString()); 
        		}
        	}
         }
         
         /** 
          * Swaps pairs of characters within String word. If any new words match
          * to the dictionary. Add new word to word dictionary.
          * 
          * @param String word. A word not in the dictionary.
          */
         private static void letterRev(String word){
        	 if(word.length()==1)return;
        	 StringBuffer s;
        	 for(int i=0;i<word.length()-1;i++){
        		 s= new StringBuffer(word);
        		 char temp = s.charAt(i);
        		 s.setCharAt(i, s.charAt(i+1));
        		 s.setCharAt(i+1, temp);
        		 if(hDict.find(s.toString()))wordsDict.insert(s.toString());  	
        	 }	 
         }
    
}
