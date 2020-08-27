package com.dm.adhoc1.controller;

import com.dm.adhoc1.bean.Query;
import com.dm.adhoc1.dao.QueryDAO;
import com.dm.adhoc1.service.QueryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/QueryController")
public class QueryController {

    @POST
    @Path("/saveQuery")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveQuery(String qry)
    {
        System.out.println(qry);
        System.out.println("Hello B");
        JSONObject jObject=(JSONObject) new JSONTokener(qry).nextValue();
       //JSONObject jObject=new JSONObject(qry);
        System.out.println(jObject);
        //System.out.println(jObject.getString("dbname"));
        String dbname=jObject.getString("dbname");
        String qname=jObject.getString("qname");
        String qstring=jObject.getString("qstr");
       QueryDAO qd=new QueryDAO();
       Query q1=new Query(dbname,qstring);
       // System.out.println("hello "+(String)jObject.getString("dbname")+" "+(String)jObject.getString("qStr")+ " "+ (String)jObject.getString("qname"));
        qd.saveQuery( q1 ,qname);

        return Response.ok().build();
    }
}
