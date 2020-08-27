package com.dm.adhoc1.bean;

public class ForeignKeyConstraint {
	 public	String myTable;
	    public String myKey;
	    public String refTable;
	    public String refKey;
	    public ForeignKeyConstraint(String a,String b,String c,String d){
	        myTable=a;
	        myKey=b;
	        refTable=c;
	        refKey=d;
	    }
	    public String getMyTable() {
	        return myTable;
	    }

	    public String getMyKey() {
	        return myKey;
	    }

	    public String getRefTable() {
	        return refTable;
	    }

	    public String getRefKey() {
	        return refKey;
	    }

	    @Override
	    public String toString() {

	        return "myTable"+myTable+" "+"myKeyColm"+myKey+" "+"refTable"+refTable+" "+"refKeyColm"+refKey+" ";
	    }

}
