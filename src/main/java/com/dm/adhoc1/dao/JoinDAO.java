package com.dm.adhoc1.dao;


import com.dm.adhoc1.bean.ForeignKeyConstraint;
import com.dm.adhoc1.connection.Conn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class JoinDAO
{
	Connection con;
	ArrayList<String> tables;
	String database;
	HashMap<String, ArrayList<ForeignKeyConstraint>> fklist;

	public ArrayList<String> get_tables(String database)
	{   
		Conn c = new Conn();
		con = c.connect("information_schema");
		this.database = database;
		tables = new ArrayList<String>();
		fklist = new HashMap<String, ArrayList<ForeignKeyConstraint> >();
		try 
		{
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM Tables WHERE TABLE_SCHEMA = '" + database + "'");
			while(rs.next())  
			{
				tables.add(rs.getString(1) + "");
				ArrayList<ForeignKeyConstraint> temp1 = new ArrayList<ForeignKeyConstraint>(); 
				fklist.put(rs.getString(1) + "", temp1);
			}
		}
		catch(Exception e){}
		return tables;
	}

	public HashMap<String, ArrayList<ForeignKeyConstraint>> drawGraph(String database)
	{
		get_tables(database);
		Conn c = new Conn();
		con = c.connect("information_schema");
		this.database = database;
		try
		{
			System.out.println(tables);
			Statement stmt = con.createStatement(); 		
			for(String tbname : tables)
			{
				String dbPlusTable = database + "/" + tbname;
				ResultSet rs = stmt.executeQuery("SELECT t.REF_NAME, t.FOR_NAME, f.REF_COL_NAME, f.FOR_COL_NAME FROM INNODB_SYS_FOREIGN t, INNODB_SYS_FOREIGN_COLS f WHERE t.id = f.id AND t.FOR_NAME = '" + dbPlusTable + "'");

				while(rs.next())  
				{
					String str = rs.getString(1);
					String[] arrOfStr = str.split("/", 2);
					if(! arrOfStr[1].equals(tbname))
					{
						ForeignKeyConstraint cond = new ForeignKeyConstraint(tbname, rs.getString(4), arrOfStr[1], rs.getString(3));
						fklist.get(tbname).add(cond);
					}
				}

				rs = stmt.executeQuery("SELECT t.REF_NAME, t.FOR_NAME, f.REF_COL_NAME, f.FOR_COL_NAME FROM INNODB_SYS_FOREIGN t, INNODB_SYS_FOREIGN_COLS f WHERE t.id = f.id AND t.REF_NAME = '" + dbPlusTable + "'");

				while(rs.next())  
				{   
					String str = rs.getString(2);
					String[] arrOfStr = str.split("/", 2);

					if(! arrOfStr[1].equals(tbname))
					{

						ForeignKeyConstraint cond = new ForeignKeyConstraint(tbname, rs.getString(3), arrOfStr[1], rs.getString(4));
						fklist.get(tbname).add(cond);
					}
				}
			}
		}
		catch(Exception e){}
		return fklist;
	}
}
