package com.calumturner;

import java.util.Arrays;
import java.util.Iterator;

public class HashDictionary implements Dictionary, Iterable<String> {
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
		if(find(value))return;//Vaule is already in the dictionary
		if(isOverLoaded(numElements+1))increaseTable();
		int hCode = hashCode.giveCode(value);//compress hashCode.
		
		int c = compressKey(hCode);
		totProb++;
		if(table[c]==null){
			table[c]=value; //If no value has had that key insert.
			numElements++;
			return;
		}
		if(table[c].equals(AVAILABLE)){
			table[c]=value;
			return;
		}
		int doubleCode= compressDoubleHash(hCode); // creates a new hashcode
		for(int j=1; j<table.length-1; j++){
			totProb++;
			int dCompress = doubleHash(compressKey(hCode),doubleCode,j); //double hashes
			if(table[dCompress]==null||table[dCompress].equals(AVAILABLE)){ //checks if new element is empty
				if(table[dCompress]==null){
					numElements++;
				}
				table[dCompress]=value;
				
				return;
			}
			
		}	
	}
	@Override
	public void remove(String key) throws DictionaryException {
		totOps++;
		int hCode = hashCode.giveCode(key);
		int compressKey =compressKey(hCode);
		totProb++;		
		if(table[compressKey]==null)throw new DictionaryException(key +" was not found, so not removed.");
		if(table[compressKey].equals(key)){
			table[compressKey]=AVAILABLE;
			numElements--;
			return;
		}else{
			int dCode = compressDoubleHash(hCode);
			for(int j=1; j<table.length-1; j++){
				totProb++;
				int dCompress = doubleHash(compressKey(hCode),dCode,j);
				if(table[dCompress]==null) throw new DictionaryException(key +" was not found, so not removed.");
				if(table[dCompress].equals(key)){
					table[dCompress]=AVAILABLE;
					numElements--;
					return;
				}
			}
			throw new DictionaryException(key +" was not found, so not removed.");	
		}
		
		
	}
	@Override
	public boolean find(String key) {//not working
		totOps++;
		int hCode = hashCode.giveCode(key);
		int compressKey = compressKey(hCode);
		totProb++;
		if(table[compressKey]==null){return false;}
		if(table[compressKey].equals(key)){return true;}
		
		
		int dCode = compressDoubleHash(hCode);
		for(int j=1; j<numElements-1; j++){
			totProb++;
			int dCompress = doubleHash(compressKey(hCode),dCode,j);
			if(table[dCompress]==null)return false;
			if(table[dCompress].equals(key))return true;
		}
		return false;
	}
	
	@Override
	public Iterator<String> iterator() {
		
		return new TableIterator();
	}
				
	
	
	
	public float averNumProbes() {
		return (float)totProb/totOps;
	}
	
	private void increaseTable() {
		int tableSize = (table.length*2)+1; //Start looking for the next prime number double the size of the old size. Add 1 to make it odd
		while(!isPrime(tableSize))//Recur through odd numbers till a prime number is located.
			tableSize+=2;
		String newTable[] = table;
		table= new String[tableSize]; //Set table to new prime number size 0-(prime number-1).
		numElements=0;
		for(int i=0;i<newTable.length;i++){ //Reinsert all the odd values into the new sized hash table
			if(newTable[i]==null || newTable[i]==AVAILABLE)continue;
			
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
		int comp =(((17*hashKey)+40)%table.length);
		if(comp<0)comp=comp*-1;
		return comp;//MAD compression
	}
	
	private int compressDoubleHash(int hashKey){
		int i= (5-(hashKey%5));
		return i;
	}
	
	private int doubleHash(int hCode,int doubleCode, int j){
		return ((hCode+(j*doubleCode))%table.length);
	}

	
	private class TableIterator implements Iterator<String>{
		private int currentPos=-1;
		@Override
		public boolean hasNext() {
			int nextPos = currentPos+1;
			while(nextPos<=table.length-1){
				if(table[nextPos]!=null&&!(table[nextPos].equals(AVAILABLE)))return true;
				nextPos++;
			}
			return false;
		}

		@Override
		public String next() {
			int nextPos = currentPos+1;
			while(nextPos<=table.length-1){
				if(table[nextPos]!=null&&!(table[nextPos].equals(AVAILABLE))){
					currentPos=nextPos;
					return table[currentPos];
				}
				nextPos++;
			}
			return null;
		}

		@Override
		public void remove() {
			if(table[currentPos]==null)return;
			table[currentPos]=AVAILABLE;
			return;
			
		}
		
	}
	
	
	

}
