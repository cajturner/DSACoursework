package com.calumturner;

import java.util.Iterator;

public class HashDictionary implements Dictionary, Iterable<String> {
	private final String AVAILABLE="%%%%%";	//Variable to indicate slot can be used, and to continue searching if searching.
	private String[] table = new String[7];	//Array of Strings to store data
	private int numElements;				//Number of elements in the Dictionary
	private float loadF;					//Load Factor of the Dictionary
	private HashCode hashCode;				
	private int totOps =0;					//Number of operations
	private int totProb=0;					//Number of Probes
	
	/**
	 * HashDicitionary constructor.
	 * Initialises LoadF, hashCode, numberElemnts.
	 * @param sH. A instance of HashCode.
	 * @param lF. The load factor of this Dictionary.
	 */
	public HashDictionary(HashCode sH, float lF) {
		loadF= lF;
		hashCode= sH;
		numElements=0;
	}
	
	/**
	 * If a instance of HashDictionary is created throw an exception.
	 * @throws DictionaryException. Invalid HashDictionary definition.
	 */
	public HashDictionary()throws DictionaryException{
		throw new DictionaryException("Invalid HashDictionary Definition");
	}
	
	/**
	 * Inserts a word into the Dictionary.
	 * Increments totOps.Checks if value is already in dictionary and returns if true.
	 * Checks adding value will not overload the dictionary.
	 * Creates a hash code for value and probes the dictionary.
	 * @param String key.	
	 */
	@Override
	public void insert(String key) {
		
		totOps++;
		if(find(key))return;		
		if(isOverLoaded(numElements+1))increaseTable();//Increase table size if table will be overloaded.
		
		int hCode = hashCode.giveCode(key);
		int compKey = compressKey(hCode);
		
		totProb++;
		if(table[compKey]==null||table[compKey].equals(AVAILABLE)){
			numElements++; 					
			table[compKey]=key; 
			return;
		}
		int doubleCode= compressDoubleHash(hCode);
		for(int j=1; j<table.length-1; j++){
			totProb++;
			int dCompress = doubleHash(compKey,doubleCode,j);
			if(table[dCompress]==null||table[dCompress].equals(AVAILABLE)){
				numElements++;
				table[dCompress]=key;
				return;
			}
			
		}	
	}
	
	/**
	 * Removes a word into the Dictionary.
	 * Increments totOps.If key's location if null throw DictionaryException.
	 * If key if found remove it and set location to AVAILABLE, decrement numElements.
	 * If for loops ends, all locations have been probed so key was not found and 
	 * throw an exception.
	 * @param String key.	
	 */
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
				int dCompress = doubleHash(compressKey,dCode,j);
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
	/**
	 * Searches the dictionary for String key, if location probed is null return false(Not found).
	 * If location probed value is equal to key return true.
	 * @param String key.	
	 * @return Boolean. True if key was found, otherwise false.
	 */
	@Override
	public boolean find(String key) {
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
	
	/**
	 * Returns TableIterator
	 * @return Iterator<String> TableIterator.
	 */
	@Override
	public Iterator<String> iterator() {
		return new TableIterator();
	}
		
	/**
	 * Returns the average number of probes per operation.
	 * @return float totProbs/totOps
	 */
	public float averNumProbes() {
		return (float)totProb/totOps;
	}
	
	/**
	 * Increase the size of the dictionary
	 * Find the first prime number double the size of the current table size.
	 * Reinsert all the old values into the table.
	 * 
	 */
	private void increaseTable() {
		int tableSize = (table.length*2)+1; //Start looking for the next prime number double the size of the old size. Add 1 to make it odd
		while(!isPrime(tableSize))//Recur through odd numbers till a prime number is located.
			tableSize+=2;
		String newTable[] = table;
		table = new String[tableSize]; //Set table to new prime number size 0-(prime number-1).
		numElements=0;
		for(int i=0;i<newTable.length;i++){ //Reinsert all the odd values into the new sized hash table
			if(newTable[i]==null || newTable[i]==AVAILABLE)continue;
				insert(newTable[i]);	
		}
		
	}
	
	/**
	 * Checks if num is prime.
	 * @param int num. Number to be checked.
	 * @return Boolean. True if num is prime.
	 */
	private boolean isPrime(int num){
		//If num is even, not prime.
		if(num%2==0)return false;
		//Check odd numbers returning if not prime.
		for(int i = 3; i*i<=num; i+=2)
			if(num%i==0)return false;
		
		return true;
	}
	
	/**
	 * Checks if numE/dictionary size is greater than the load factor
	 * @param int numE. number of potential elements in the table
	 * @return Boolean. True if numE/dictionary size is greater than load factor.
	 */
	private boolean isOverLoaded(int numE){
		if((float)numE/table.length>loadF)return true;
		return false;
		
	}
	
	/**
	 * Compresses hashKey using MAD compression
	 * @param hashKey. int to be compressed
	 * @return compressed int
	 */
	private int compressKey(int hashKey){
		int comp =(((17*hashKey)+40)%table.length);
		if(comp<0)comp=comp*-1;
		return comp;
	}
	
	/**
	 * Used to compress the has key a second time.	
	 * @param hashKey. int to be compressed
	 * @return hashKey compressed.
	 */
	private int compressDoubleHash(int hashKey){
		return  (5-(hashKey%5));
	}
	
	/**
	 * Creates a double hashCode
	 * @param hCode, the original compress hash code
	 * @param doubleCode, the compressed second hash code
	 * @param j, 
	 * @return new compressed hash code
	 */
	private int doubleHash(int hCode,int doubleCode, int j){
		return ((hCode+(j*doubleCode))%table.length);
	}

	/**
	 * Iterator class implementing Iterator<String>
	 */
	private class TableIterator implements Iterator<String>{
		private int currentPos=-1;
		
		/**
		 * Steps through the array and returns true if a value is found.
		 * @return Boolean. True if there is another value in the array
		 */
		@Override
		public boolean hasNext() {
			int nextPos = currentPos+1;
			while(nextPos<=table.length-1){
				if(table[nextPos]!=null&&!(table[nextPos].equals(AVAILABLE)))return true;
				nextPos++;
			}
			return false;
		}

		/**
		 * Returns the next element in the array
		 * @return String of the next element in the array, null if no more elements are found.
		 */
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

		/**
		 * Removes the current element from the array.
		 */
		@Override
		public void remove() {
			if(currentPos==-1){
				System.out.println("Cannot remove until you call next() at least once.");
				return;
			}
			if(table[currentPos]==null)return;
			table[currentPos]=AVAILABLE;
			numElements--;
			return;
		}
		
	}
	
	
	

}
