package com.calumturner;

import java.util.Iterator;
import java.util.LinkedList;

public class ListDictionary implements Dictionary {

	private LinkedList<String> ll;

	/**
	 * ListDictionary constructor Initialises ll to a new Link List type String.
	 */
	public ListDictionary() {
		ll = new LinkedList<String>();
	}

	/**
	 * Inserts key into the linked list if it is not already present.
	 * 
	 * @param String
	 *            key. A String to be added to the the linked list.
	 */
	@Override
	public void insert(String key) {
		if (!find(key))
			ll.add(key);
		
	}

	/**
	 * Removes key from linked list, if ll.remove returns false throw an key not
	 * found exception.
	 * 
	 * @param String
	 *            key. A String to be removed from the linked list.
	 */
	@Override
	public void remove(String key) throws DictionaryException {
		if (!ll.remove(key))
			throw new DictionaryException(key
					+ " was not found in the linked list, so was not removed.");
	}

	/**
	 * Searches linked list for key and returns true if found, or false if not.
	 * 
	 * @param String
	 *            key. A String to be searched for in the linked list.
	 * @return Boolean. Returns true if key is in the linked list.
	 */
	@Override
	public boolean find(String key) {
		return ll.contains(key);
	}

	/**
	 * Returns a String Iterator of the linked list.
	 * 
	 * @return Iterator<String>. Returns a String Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		return ll.iterator();

	}

}
