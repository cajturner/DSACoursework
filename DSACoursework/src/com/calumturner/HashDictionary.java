package com.calumturner;

import java.util.Iterator;

public class HashDictionary implements Dictionary {
	private String[] table = new String[6];
	private int numElements;
	private float loadF;
	private StringHashCode hashCode;
	public HashDictionary(StringHashCode sH, float lF) {
		loadF= lF;
		hashCode= sH;
		numElements=0;
	}
	
	public HashDictionary()throws DictionaryException{}
	
	@Override
	public void insert(String key) throws DictionaryException {
		if(isOverLoaded(numElements+1))increaseTable();
		int hCode = compressKey(hashCode.giveCode(key));//compress hashCode
		
		
	}
	@Override
	public void remove(String key) throws DictionaryException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean find(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator elements() {
		// TODO Auto-generated method stub
		return null;
	}
	public String averNumProbes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void increaseTable(){
		int tableSize = table.length*2; //Start looking for the next prime number double the size of the old size.
		if(tableSize%2==0)tableSize++;//If even make odd.
		while(!isPrime(tableSize))//Recur through odd numbers till a prime number is located.
			tableSize+=2;
		String newTable[] = table;
		table= new String[tableSize-1]; //Set table to new prime number size 0-(prime number-1).
	
		for(int i=0;i<table.length;i++){ //Reinsert all the odd values into the new sized hash table
			try {
				insert(newTable[i]);
			} catch (DictionaryException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	private boolean isPrime(int num){
		//If num is even, not prime.
		if(num%2==0)return false;
		//Check odd numbers returning if not prime.
		for(int i = 3; i*i<=num; i+=2)
			if(num%i==0)return false;
		
		return true;
	}
	
	private boolean isOverLoaded(int numE){
		if((float)numE/table.length>loadF)return true;
		return false;
		
	}
	
	private int compressKey(int hashKey){
		return (5*hashKey+33)%(table.length-1);//MAD compression
	}
	
	

}
