package com.calumturner;

public class StringHashCode implements HashCode {
	static int CONSTANT = 33;
	
	
	@Override
	public int giveCode(Object o) {
		// Polynomial accumulation
		
		if(o==null)return -1;//if the input object is null return -1;
		
		return HornersRule((String.valueOf((o))));
		
	}
	
	private int HornersRule(String s){
		int hCode=s.charAt(s.length()-1);		//assign hCode to the value of the last char of the string
		int i = s.length()-2;					
		while(i>=0){
			hCode=(hCode*CONSTANT) +s.charAt(i);	// p=p*a+s[i] Horner's rule
			i--;
		}
		return hCode;
	}
	
	

}
