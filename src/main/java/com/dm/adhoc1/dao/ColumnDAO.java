package com.dm.adhoc1.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dm.adhoc1.bean.Database;
import com.dm.adhoc1.bean.Table;

import com.dm.adhoc1.bean.Columns;
import com.dm.adhoc1.connection.Conn;

public class ColumnDAO
{
	Connection con;
	String database = "information_schema";
	
	public List<Columns> showColumns(Database db, Table tb)
	{
		Statement st;
		ResultSet rs;
		String query;
		Columns cl;
		List<Columns> clList = new ArrayList<Columns>();
		try
		{
			query = "SELECT c.NAME FROM INNODB_SYS_TABLES t, INNODB_SYS_COLUMNS c WHERE t.NAME = '"+ db.getName() + "/" +tb.getName() + "' and t.TABLE_ID=c.TABLE_ID";
			
		     
			con = new Conn().connect(database);
			st = con.createStatement();
			rs = st.executeQuery(query);
		
			while(rs.next())
			{
				cl = new Columns();
				cl.setName(rs.getString(1));
				
				clList.add(cl);
			}
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return clList;
	}
}
