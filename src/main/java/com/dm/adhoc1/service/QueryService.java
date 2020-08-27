
package com.dm.adhoc1.service;



import java.util.*;

import com.dm.adhoc1.bean.QueryElements;

public class QueryService
{	
	private String query;
	
	public String generateQuery(QueryElements qe)
	{
		query = "SELECT ";
		ArrayList<Boolean> sc = qe.getShow_colm();
		Iterator<String> i=qe.getColumns().iterator();
		int cnt=0;
		for(int j=0;j<sc.size();j++) {
			if(sc.get(j))
			 	cnt++;
		}
		int j=0;
		while (i.hasNext() && cnt>0)
		{
			if(sc.get(j)) {
				query = query + i.next();
				cnt--;
			}
			if (cnt>0)
				query = query + ", ";
		}
		String where="";
		ArrayList<String>whrStr=qe.getWhereStr();
		for(int k=0;k<whrStr.size();k++){
			where=where+whrStr.get(k);
			System.out.println("WHERE "+where);
		}

		
		String join = JoinService.returnJoin(qe.getDatabase(),qe.getTables());
		System.out.println("Join "+join);
		if(!join.equals(""))
		{
			query = query + join;

		}

		if(query.contains("WHERE")){
			if(!where.equals(""))
				query=query+" and "+where;
		}
		else{
			if(!where.equals(""))
				query=query+ " WHERE "+where;
		}
		ArrayList<String>orderBy=qe.getOrderBy();
		String orderByStr=" ORDER BY ";
		for(int k=0;k<orderBy.size();k++){
			if(k!=orderBy.size()-1)
				orderByStr=orderByStr+orderBy.get(k)+" , ";
			else
				orderByStr=orderByStr+orderBy.get(k);
		}
		System.out.println("order by "+orderByStr);
		if(!orderByStr.equals(" ORDER BY "))
			query=query+orderByStr;
		System.out.println(query);
		return query;
	}
}
