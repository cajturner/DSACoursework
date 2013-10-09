package com.calumturner;

import java.util.Iterator;

public class HashDictionary implements Dictionary {

	private float loadF;
	StringHashCode hashCode;
	public HashDictionary(StringHashCode sH, float lF) {
		loadF= lF;
		hashCode= hashCode;
	}
	
	public HashDictionary()throws DictionaryException{}
	
	@Override
	public void insert(String key) throws DictionaryException {
		int hCode = hashCode.giveCode(key);
		
		
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
	
	private 
	
	

}
