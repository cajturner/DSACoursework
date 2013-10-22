package com.calumturner;

public class StringHashCode implements HashCode {
	static int CONSTANT = 33;
	
	/**
	 * Returns polynomial accumulation of Object o interpreted as a String.
	 * @return 
	 */
	@Override
	public int giveCode(Object o) {
		
		if(o==null)return -1;						//if the input object is null return -1;
		return HornersRule((String.valueOf((o))));
		
	}
	
	/**
	 * Returns int of polynomial accumulation of String s.
	 * @param s, String to be used.
	 * @return int of polynomial accumulation of s
	 */
	private int HornersRule(String s){				// Polynomial accumulation
		int hCode=s.charAt(s.length()-1);	
		int i = s.length()-2;					
		while(i>=0){
			hCode=(hCode*CONSTANT) +s.charAt(i);	// p=p*a+s[i] Horner's rule
			i--;
		}
		return hCode;
	}
	
	

}
