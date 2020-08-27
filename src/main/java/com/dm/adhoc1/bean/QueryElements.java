package com.dm.adhoc1.bean;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class QueryElements
{
	private String database;
	private LinkedHashSet<String> tables;
	private LinkedHashSet<String> columns;
	private LinkedHashSet<String> dbtb;
	private ArrayList<Boolean>show_colm;
	private ArrayList<String>whereStr;
	private ArrayList<String>orderBy;



	public QueryElements(String database, LinkedHashSet<String> tables, LinkedHashSet<String> columns, LinkedHashSet<String> dbtb, ArrayList<Boolean>show_colm, ArrayList<String> whr, ArrayList<String>orderBy)
	{
		super();
		this.orderBy=orderBy;
		this.whereStr=whr;
		this.database = database;
		this.tables = tables;
		this.columns = columns;
		this.dbtb = dbtb;
		this.show_colm=show_colm;
	}

	public ArrayList<String> getWhereStr() {
		return whereStr;
	}
	public ArrayList<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(ArrayList<String> orderBy) {
		this.orderBy = orderBy;
	}
	public void setWhereStr(ArrayList<String> whereStr) {
		this.whereStr = whereStr;
	}

	public ArrayList<Boolean> getShow_colm() {
		return show_colm;
	}

	public void setShow_colm(ArrayList<Boolean> show_colm) {
		this.show_colm = show_colm;
	}
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public LinkedHashSet<String> getTables() {
		return tables;
	}

	public void setTables(LinkedHashSet<String> tables) {
		this.tables = tables;
	}

	public LinkedHashSet<String> getColumns() {
		return columns;
	}

	public void setColumns(LinkedHashSet<String> columns) {
		this.columns = columns;
	}

	public LinkedHashSet<String> getDbtb() {
		return dbtb;
	}

	public void setDbtb(LinkedHashSet<String> dbtb) {
		this.dbtb = dbtb;
	}

	@Override
	public String toString()
	{
		return "Query Elements [database=" + database + ", tables=" + tables + ", columns=" + columns + ", db.Tables="
				+ dbtb + ", show_colm="+show_colm+"]";
	}
	
	
}

