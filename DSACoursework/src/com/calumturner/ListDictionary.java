package com.calumturner;

import java.util.Iterator;
import java.util.LinkedList;

public class ListDictionary implements Dictionary{
	private LinkedList<String> ll;
	public ListDictionary(){
		ll = new LinkedList<String>();
	}

	@Override
	public void insert(String key) {
		if(!find(key))ll.add(key);//If value is not in dict add it
	}

	@Override
	public void remove(String key) throws DictionaryException  {
		if(!ll.remove(key))throw new DictionaryException(key+ " was not found in the linked list, so was not removed.");
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
	public Iterator<String> iterator() {
		return	ll.iterator();	
		
	}

}
