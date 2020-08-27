package com.dm.adhoc1.service;



import java.sql.Connection;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.dm.adhoc1.bean.ForeignKeyConstraint;
import com.dm.adhoc1.connection.Conn;
import com.dm.adhoc1.dao.JoinDAO;

public class JoinService
{
	static Connection con;
	static ArrayList<String> tables;
	static String database;
	static HashMap<String, ArrayList<ForeignKeyConstraint>> fklist;
	static int count = 0;
    static ArrayList<ForeignKeyConstraint> finalJoins = new ArrayList<ForeignKeyConstraint>();

    public static void getJoin(String src, String dest){
        count=0;
        System.out.println("\n Hey get join");
        boolean[] visited =new boolean[tables.size()];
        traceMap(new ForeignKeyConstraint("","",src,""),new ForeignKeyConstraint("","",dest,""),visited,new ArrayList<ForeignKeyConstraint>());
    }
	

	
    static void traceMap(ForeignKeyConstraint src, ForeignKeyConstraint dest, boolean[]visited, ArrayList<ForeignKeyConstraint>joins){
        int u = tables.indexOf(src.refTable);
        visited[u] = true;
        System.out.println(src.refTable+" "+dest.refTable);
        if (src.refTable.equals(dest.refTable))
        {
            count++;
            finalJoins.addAll(joins);
            System.out.println("Printing joins"+count);
            System.out.println(joins);
            visited[u] = false;
            return ;
        }
        for (ForeignKeyConstraint cnst : fklist.get(src.refTable))
        {
            int i = tables.indexOf(cnst.refTable);
            if (!visited[i] && count==0)
            {
                joins.add(cnst);
                traceMap(cnst, dest, visited, joins);
                System.out.println("Removing "+cnst);
                joins.remove(cnst);
            }
        }
        visited[u] = false;
    }
	
			
		public static String returnJoin(String db, LinkedHashSet<String> tbnames)
		{  
			fklist = new HashMap<String, ArrayList<ForeignKeyConstraint> >();
			JoinDAO joinDAO= new JoinDAO();
			database = db;
			String join = "";
			String from = "";
			tables = new ArrayList<String>();
			try
			{
				Conn c = new Conn();
				con = c.connect("information_schema");
				tables = joinDAO.get_tables(db);
				fklist = joinDAO.drawGraph(db);
				System.out.println(fklist);
				Iterator<String> i = tbnames.iterator();
				String root = i.next();
				while(i.hasNext())
				{
					String temp = i.next();
					if (!temp.equals(root))
							getJoin(root,temp);
				}
				
				Iterator<ForeignKeyConstraint> j = finalJoins.iterator();
				while(j.hasNext())
				{
					ForeignKeyConstraint x = j.next();
					tbnames.add(x.myTable);
					tbnames.add(x.refTable);
				}
				
				from = from + " FROM ";
				
				i = tbnames.iterator();
				while (i.hasNext())
				{
					from = from + i.next();
					if (i.hasNext())
						from = from + ", ";
				}
				
				
				j = finalJoins.iterator();
				while(j.hasNext())
				{
					ForeignKeyConstraint x = j.next();
					join = join + x.myTable + "." + x.myKey + " = " + x.refTable + "." + x.refKey;
					if(j.hasNext())
						join = join + " AND ";
				}
				System.out.println(join);
				
				if(!join.equals(""))
				{
					join = from + " WHERE " + join;
				}
				else
				{
					join = from;
				}
				System.out.println(join);
				con.close();
			}
			catch(Exception e){}
			finalJoins = new ArrayList<ForeignKeyConstraint>();
			return join;
	
	}
}
