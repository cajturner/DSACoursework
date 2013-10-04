package com.calumturner;
import java.io.File;
import java.io.*;
import java.util.Iterator;
 
public class spell {
   
          
    public static void main(String[] args) throws java.io.IOException{
         if (args.length != 2 ) {
            System.out.println("Usage: spell dictionaryFile.txt inputFile.txt ");
            System.exit(0);
         }
         BufferedInputStream dict,file;
         
         try{
           
            dict  = new BufferedInputStream(new FileInputStream(args[0]));
            file  = new BufferedInputStream(new FileInputStream(args[1]));
	    // To read from specific files, comment the 2 lines above and 
            // uncomment 2 lines below 
            //dict  = new BufferedInputStream(new FileInputStream("C:\\dictionary.txt"));
	    //file  = new BufferedInputStream(new FileInputStream("C:\\checkText.txt"));
	   
         }
         catch (IOException e){ // catch exceptions caused by file input/output errors
            System.out.println("Check your file name");
            System.exit(0);
        }  
    }
}
