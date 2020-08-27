package com.dm.adhoc1.bean;



public class Columns
{
	private long table_id;
	private String name;
	
	public long getTable_id()
	{
		return table_id;
	}
	public void setTable_id(long table_id)
	{
		this.table_id = table_id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return "Column [table_id=" + table_id + ", name=" + name + "]";
	}
}


