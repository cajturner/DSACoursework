package com.calumturner;

import java.util.Iterator;
import java.util.LinkedList;

public class ListDictionary implements Dictionary{
	private LinkedList<String> ll;
	public ListDictionary(){
		ll = new LinkedList<String>();
	}

	@Override
	public void insert(String key) throws DictionaryException {
		ll.add(key);
	}

	@Override
	public void remove(String key) throws DictionaryException {
		ll.remove(key);
	}

	@Override
	public boolean find(String key) {
		int i=0;
		while(i<ll.size()){
			if(ll.get(0)==key)return true;			
		}
		return false;
	}

	@Override
	public Iterator<String> elements() {
		return	ll.iterator();	
		
	}

}
