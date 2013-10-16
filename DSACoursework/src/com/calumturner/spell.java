package com.calumturner;
import java.io.*;
import java.util.Iterator;
 
public class spell {
   
	private static HashDictionary hDict;
    private static HashDictionary wordsDict;
          
    public static void main(String[] args) throws java.io.IOException{
//         if (args.length != 2 ) {
//            System.out.println("Usage: spell dictionaryFile.txt inputFile.txt ");
//            System.exit(0);
//         }
    		BufferedInputStream dict,file;
         
         try{
           
         //   dict  = new BufferedInputStream(new FileInputStream(args[0]));
         //   file  = new BufferedInputStream(new FileInputStream(args[1]));
	    // To read from specific files, comment the 2 lines above and 
            // uncomment 2 lines below 
            dict  = new BufferedInputStream(new FileInputStream("C:\\d1.txt"));
            file  = new BufferedInputStream(new FileInputStream("C:\\checkText.txt"));
            
         // Load all the words from dictionary into dictionary data structure 
            StringHashCode sHCode = new StringHashCode();
             hDict = new HashDictionary(sHCode,(float) 0.9);
             wordsDict = new HashDictionary(sHCode, (float)0.9);
			
            FileWordRead reader = new FileWordRead(dict);
            while(reader.hasNextWord()){
            	hDict.insert(reader.nextWord());
            }
            reader = new FileWordRead(file);
            while(reader.hasNextWord()){
            	String word = reader.nextWord();
            	System.out.println(word);
            	if(!hDict.find(word)){
            		letterSub(word);
            		letterOmi(word);
            		letterIns(word);
            	}
            }
           
			
          // Load in each word from tested file and check the words
         
         }
         catch (IOException e){ // catch exceptions caused by file input/output errors
            System.out.println("Check your file name");
            System.exit(0);
        } 
         
    }
         private static void letterSub(String word){
        	 StringBuffer s ;
        	 for(int i=0;i<word.length();i++){
        		 s= new StringBuffer(word);
        		 for(int j=0;j<26;j++){
        			 s.setCharAt(i, (char)(97+j));
        			 if(hDict.find(s.toString())){
        				 wordsDict.insert(s.toString());
        				 System.out.println(s.toString());
        				 
        			 }
        			 
        		 }
        	 }
        	 
         }
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
         private static void letterIns(String word){
        	StringBuffer s;
        	for(int i=0;i<=word.length();i++){
        		for(int j=0;j<26;j++){
        			s= new StringBuffer(word);
        			s.insert(i, (char)(97+j));
        			if(hDict.find(s.toString())){
       				 	wordsDict.insert(s.toString());
       				 	System.out.println(s.toString());
       				 
       			 	}
        		}
        	}
         }
         private static void letterRev(String word){
        	 if(word.length()==1)return;
        	 StringBuffer s;
        	 for(int i=0;i<word.length();i++){
        		 
        	 }
        	 
        	 
        	 
         }
    
}
