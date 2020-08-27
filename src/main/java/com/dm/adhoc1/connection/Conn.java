package com.dm.adhoc1.connection;

import java.sql.Connection;

import java.sql.DriverManager;

public class Conn
{
	private Connection con;
	
	public Connection connect(String database)
	{
		con = null;
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, "root", "");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}  
		return con;
	}
}

