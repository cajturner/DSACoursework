package com.calumturner;

import java.util.Arrays;
import java.util.Iterator;

public class HashDictionary implements Dictionary {
	private final String AVAILABLE="%%%%%";
	private String[] table = new String[7];
	private int numElements;
	private float loadF;
	private HashCode hashCode;
	private int totOps =0;
	private int totProb=0;
	public HashDictionary(HashCode sH, float lF) {
		loadF= lF;
		hashCode= sH;
		numElements=0;
	}
	
	public HashDictionary()throws DictionaryException{
		throw new DictionaryException("Invalid HashDictionary Definition");
	}
	
	@Override
	public void insert(String value) {
		totOps++;
		if(isOverLoaded(numElements+1))increaseTable();
		int hCode = hashCode.giveCode(value);//compress hashCode.
		int c = compressKey(hCode);
		System.out.println(""+c);
		if(table[c]==null){
			table[c]=value; //If no value has had that key insert.
			numElements++;
			return;
		}
		int doubleCode= compressDoubleHash(hCode); // creates a new hashcode
		for(int j=0; j<numElements-1; j++){
			totProb++;
			int dCompress = doubleHash(compressKey(hCode),doubleCode,j); //double hashes
			if(table[dCompress]==null){ //checks if new element is empty
				table[dCompress]=value;
				numElements++;
			}
		}	
	}
	@Override
	public void remove(String key) throws DictionaryException {
		totOps++;
		int hCode = hashCode.giveCode(key);
		int compressKey =compressKey(hCode);
		
		if(table[compressKey]==null)throw new DictionaryException(key +" was not found, so not removed.");
		else if(table[compressKey]==key){
			table[compressKey]=AVAILABLE;
			numElements--;
			return;
		}else{			
			int dCode = compressDoubleHash(hCode);
			for(int j=0; j<numElements-1; j++){
				totProb++;
				int dCompress = doubleHash(compressKey(hCode),dCode,j);
				if(table[dCompress]==null) throw new DictionaryException(key +" was not found, so not removed.");
				if(table[dCompress]==key){
					table[dCompress]=AVAILABLE;
					numElements--;
				}
			}
			throw new DictionaryException(key +" was not found, so not removed.");	
		}
		
		
	}
	@Override
	public boolean find(String key) {
		totOps++;
		int hCode = hashCode.giveCode(key);
		int compressKey = compressKey(hCode);
		
		if(table[compressKey]==key)return true;
		if(table[compressKey]==null)return false;
		
		int dCode = compressDoubleHash(hCode);
		for(int j=0; j<numElements-1; j++){
			totProb++;
			int dCompress = doubleHash(compressKey(hCode),dCode,j);
			if(table[dCompress]==null)return false;
			if(table[dCompress]==key)return true;
		}
		return false;
	}
	
	@Override
	public Iterator<String> elements() {
		return Arrays.asList(table).iterator();
	}
	
	public float averNumProbes() {
		return (float)totProb/totOps;
	}
	
	private void increaseTable(){
		int tableSize = table.length*2; //Start looking for the next prime number double the size of the old size.
		tableSize++;//Make it odd
		while(!isPrime(tableSize))//Recur through odd numbers till a prime number is located.
			tableSize+=2;
		String newTable[] = table;
		table= new String[tableSize-1]; //Set table to new prime number size 0-(prime number-1).
		numElements=0;
	
		for(int i=0;i<table.length;i++){ //Reinsert all the odd values into the new sized hash table
			if(table[i]==null || table[i]==AVAILABLE)continue;
			insert(newTable[i]);			
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
		return (5*hashKey+33)%(table.length);//MAD compression
	}
	
	private int compressDoubleHash(int hashKey){
		int i= ((table.length-1)-(hashKey%(table.length-1)));
		return i;
	}
	
	private int doubleHash(int hCode,int doubleCode, int j){
		return ((hCode+(j*doubleCode))%numElements);
	}
	
	
	

}
