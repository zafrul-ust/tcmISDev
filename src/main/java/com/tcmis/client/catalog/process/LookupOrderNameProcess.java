package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SqlHandler;

public class LookupOrderNameProcess extends GenericProcess {
	private String TABLE = "PURCHASE_REQUEST";
	private int returnSize = 5;
	
	public LookupOrderNameProcess(String client) {
		super(client);
	}
	
	public LookupOrderNameProcess(String client, String locale) {
		super(client,locale);
	}
	
	public Collection<String> getSimilarOrderNames(AutocompleteInputBean inputBean) throws BaseException {
		Vector<String> availableOrderNames = new Vector<String>();		
		int uniqueNum = 1;
		
		try {
			String userInput = inputBean.getSearchText();
		    String possibleName = userInput;
		    String delimitedCompanyId = SqlHandler.delimitString(inputBean.getCompanyId());
		    StringBuilder query;
			while (true) {
				query = new StringBuilder("select count(*) from ");
				query.append(TABLE);
				query.append(" where lower(order_name) = lower(").append(SqlHandler.delimitString(possibleName.trim())).append(")");
				query.append(" and company_id = ").append(delimitedCompanyId);
				String count = factory.selectSingleValue(query.toString());
				if (count.equals("0")) {//add unique id to list
					availableOrderNames.add(possibleName.trim());
					if (availableOrderNames.size() >= returnSize)
						break;
				}

				possibleName = userInput + uniqueNum;
				uniqueNum++;
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		
		return availableOrderNames;
	}
	
	public void setReturnSize(int size) {
		returnSize = size;
	}
}
