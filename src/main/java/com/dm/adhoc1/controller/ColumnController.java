package com.dm.adhoc1.controller;


import java.util.ArrayList;
import java.util.LinkedHashSet;




import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dm.adhoc1.bean.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.dm.adhoc1.dao.ColumnDAO;
import com.dm.adhoc1.dao.QueryDAO;
import com.dm.adhoc1.service.QueryService;


@Path("/ColumnController")
public class ColumnController
{
	@GET
	@Path("/columns/{dbname}/{tbname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColumns(	@PathParam("dbname") String dbname,
								@PathParam("tbname") String tbname)
	{
		Database db = new Database();
		db.setName(dbname);
		
		Table tb = new Table();
		tb.setName(tbname);
		
		
		GenericEntity<List<Columns>> clList;
		clList  = new GenericEntity<List<Columns>>(new ColumnDAO().showColumns(db, tb)) { };
		return Response.ok(clList).build();
	}
	
	@POST
	@Path("/setcolumns")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setColumns(String selectedCols)
	{
		System.out.println("Hello");
		JSONArray jArray = (JSONArray) new JSONTokener(selectedCols).nextValue();
		
		QueryElements qe;
		LinkedHashSet<String> tables = new LinkedHashSet<String>();
		LinkedHashSet<String> columns = new LinkedHashSet<String>();
		LinkedHashSet<String> dbPlusTables = new LinkedHashSet<String>();
		ArrayList<Boolean> show_colms=new ArrayList<>();
		ArrayList<String>whrStr=new ArrayList<>();
		ArrayList<String>orderBy=new ArrayList<>();
		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject jObject = jArray.getJSONObject(i);
			System.out.println("jobject in cc"+jObject);
			tables.add(jObject.getString("tbname"));
			columns.add(jObject.getString("tbname") + "." + jObject.getString("clname"));
			dbPlusTables.add(jObject.getString("dbname") + "/" + jObject.getString("tbname"));
			show_colms.add(jObject.getBoolean("show_me"));
			whrStr.add(jObject.getString("whr"));
			if(jObject.getBoolean("ord"))
				orderBy.add(jObject.getString("tbname") + "." + jObject.getString("clname"));
		}
		qe = new QueryElements(jArray.getJSONObject(0).getString("dbname"), tables, columns, dbPlusTables,show_colms,whrStr,orderBy);
		String query=new QueryService().generateQuery(qe);
		List<String[]> result=new QueryDAO().executeQuery(new Query(jArray.getJSONObject(0).getString("dbname"), query));
		QueryRS out=new QueryRS(query,result);
		GenericEntity<QueryRS> output;
		output= new GenericEntity<QueryRS>(out){};
		//output  = new GenericEntity<List<String[]>>(new QueryDAO().executeQuery(new Query(jArray.getJSONObject(0).getString("dbname"), new QueryService().generateQuery(qe)))) { };

		return Response.ok(output).build();
		
	   
	}
	
}