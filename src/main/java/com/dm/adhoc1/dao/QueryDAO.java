package com.dm.adhoc1.dao;


import com.dm.adhoc1.bean.Query;
import com.dm.adhoc1.connection.Conn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import com.dm.adhoc1.service.PrivacyFilter;

public class QueryDAO
{
	Connection con;

	public  ResultSet rs;
	
	public  ResultSet getRs() {
		return rs;
	}

	public void saveQuery(Query q, String qname){

		Statement st= null;
		try {
			System.out.println("Saving");
			con=new Conn().connect("MyQueryDB");
			st = con.createStatement();

			int s=st.executeUpdate("INSERT INTO myQueryTable values ('"+qname+"', '"+q.getQueryString()+"' , '"+q.getDatabase()+"' )");
			System.out.println("Inserted"+s);
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

	public List<String[]> executeQuery(Query q)
	{
		Statement st;
		List<String[]> table = new ArrayList<>();
		try
		{
			System.out.println(q.getQueryString());
			con = new Conn().connect(q.getDatabase());
			st = con.createStatement();
			rs = st.executeQuery(q.getQueryString());

			int nCol = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
			    String[] row = new String[nCol];
			    for(int iCol = 1; iCol <= nCol; iCol++ ){
			            Object obj = rs.getObject( iCol );
			            row[iCol-1] = (obj == null) ?null:obj.toString();
			    }
			    table.add(row);
			}

			// print result
			for( String[] row: table ){
			    for( String s: row ){
			        System.out.print( " " + s );
			    }
			    System.out.println();
			}
			rs = st.executeQuery(q.getQueryString());


			//PrivacyFilter pf= new PrivacyFilter();
			//pf.start(rs);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return table;
	}
}

