package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.HubCycleCountDisplayViewBean;
import com.tcmis.internal.hub.beans.HubCycleCountInputBean;

public class HubCycleCountProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;

	public HubCycleCountProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}
	
	public String getCountMonth(HubCycleCountInputBean input) throws BaseException 
	{		
		String countMonth = "";
		try {			
			StringBuilder query = new StringBuilder();
			query.append(" select count_month from hub_count_date where ");
			query.append(" hub = " + input.getHub());
			query.append(" and count_id = " + input.getCountId());
			query.append(" and count_status = 'Open' and count_type = 'Cycle Count'");
			
			countMonth = factory.selectSingleValue(query.toString());							
		}
		catch (Exception e) {
			log.error("Error getting countMonth for the selected hub", e);
			throw new BaseException(e);			  
		}
		return countMonth;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<HubCycleCountDisplayViewBean> getSearchResult(HubCycleCountInputBean input) throws BaseException 
	{
		Collection<HubCycleCountDisplayViewBean> coll = null;
		Connection conn = dbManager.getConnection();		
		String countMonth = "";
		try {
			if (input.getCountId() != null && !input.getCountId().equals(BigDecimal.ZERO)) {
				countMonth = getCountMonth(input);
				if (countMonth != null && !countMonth.equalsIgnoreCase("")) 
					input.setCountMonth(new BigDecimal(countMonth));
				
				factory.setBean(new HubCycleCountDisplayViewBean());				
				SearchCriteria criteria = new SearchCriteria();
				criteria.addCriterion("countId", SearchCriterion.EQUALS ,input.getCountId().toString());
				
				//and actual_quantity is null
				if (input.getShowUncountedOnly() != null && input.getShowUncountedOnly().equalsIgnoreCase("Y")) 
					criteria.addCriterion("actualQuantity", SearchCriterion.IS, "null");
				
				SortCriteria sort = new SortCriteria();
				if ("Bin".equals(input.getSortBy())) {
					sort.addCriterion("bin");
				} else if ("ReceiptId".equals(input.getSortBy())) {
					sort.addCriterion("receiptId");
				} else if ("ItemID".equals(input.getSortBy())) {
					sort.addCriterion("itemId");
				} else if ("Description".equals(input.getSortBy())) {
					sort.addCriterion("itemDesc");
				}
				coll = factory.select(criteria, sort, "HUB_CYCLE_COUNT_VIEW");
			}
		}
		catch (Exception e) {
			log.error("Error getting hub cycle count data for the selected hub", e);
			throw new BaseException(e);			  
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return coll;
	}
	
	/*
	 * procedure p_start_count ( v_inventory_group
	 * ig_count_definition.inventory_group%type, v_hub
	 * inventory_group_definition.hub%type, v_personnel_id number,
	 * a_collection char, v_error_message OUT VARCHAR2 )
	 */
	public HashMap<String, String> startCount(HubCycleCountInputBean input, PersonnelBean user) throws BaseException, Exception {

		HashMap<String, String> retMessages = new HashMap<String, String>();
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal userId = user.getPersonnelIdBigDecimal();

		try {			
			int countMonth = 0;
			if (input.getCountMonth() == null || input.getCountMonth().equals(BigDecimal.ZERO)) {		
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				countMonth = cal.get(Calendar.MONTH);
				countMonth = countMonth + 1;
			} else 
				countMonth = input.getCountMonth().intValue();
			
			if(!startNewCountForCurrMonthValid(input)) {
				countMonth = countMonth +1;
				input.setCountMonth(new BigDecimal(countMonth));
			}
			
			Vector outArgs = new Vector(2);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			
			// Call P_START_COUNT
			Collection inArgs = buildProcedureInput(input.getHub(),countMonth, userId);
			if (log.isDebugEnabled()) {
				log.debug("Start Count Procedure :" + inArgs + outArgs);
			}
			Vector vargs =  (Vector) procFactory.doProcedure("PKG_INVENTORY_COUNT.P_START_CYCLE_COUNT", inArgs, outArgs);

			String countId = "";
			if (vargs != null && vargs.size() > 0) {
				countId = (String) vargs.get(0);
				retMessages.put("COUNTID",countId);
				if (vargs.size() > 1 && (!StringHandler.isBlankString((String) vargs.get(1)))) {
					retMessages.put("ERROR",(String) vargs.get(1));
				}
			}
			//getErrorMessages(vargs, errorMessages);
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			retMessages.put("ERROR",errorMsg);
			log.error(errorMsg, e);
		}
		return retMessages;
	}
	
	public Collection save (HubCycleCountInputBean input, Collection<HubCycleCountInputBean> rows, PersonnelBean user) 
		throws BaseException, Exception 
	{	
		Connection conn = dbManager.getConnection();		
		Collection<String> errorMessages = new Vector<String>();
		
		try {
			for (HubCycleCountInputBean row : rows) {
				int rowsUpdated = 0;
				if (row.isRowCounted()) {	
					StringBuilder query = new StringBuilder();
					query.append("update inventory_count set actual_quantity = " + row.getActualQuantity());
					query.append(" where count_id = " + row.getCountId());
					query.append(" and receipt_id = " + row.getReceiptId() );
					
					rowsUpdated += factory.deleteInsertUpdate(query.toString(), conn);
					
					if (rowsUpdated == 0) {
						// There was an error, add a message
						errorMessages.add("" + library.format("error.noupdaterow", row.getReceiptId().toString(), row.getHub()));
					}
				}
			}
			conn.commit();
		} catch (Exception ex ) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, ex);
		} finally {
			// Finally, close the count(s)
			dbManager.returnConnection(conn);			
		}
		//errorMessages.addAll(closeCount(input, user));
		return errorMessages;
	}
	
	public int verifyAllReceiptsCounted(HubCycleCountInputBean input) throws BaseException, Exception {

		String numberOfRowsOpen = "";
		int rowsNotCounted = 0;		
//		If not all rows counted verify user still wants to close count, "## Receipts have not been counted. Close Count anyway?" 
//		Update HUB_COUNT_DATE if all rows counted or user still wants to close Count: 
//		Update HUB_COUNT_DATE set COUNT_STATUS = 'Closed' where HUB = ### and 
//		 COUNT_ID = ### 
		
		try {
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select count(*) from HUB_CYCLE_COUNT_VIEW where count_id = " + input.getCountId());
			strQuery.append(" and actual_quantity is null");
			
			numberOfRowsOpen = factory.selectSingleValue(strQuery.toString());
			if (numberOfRowsOpen != null) 
				rowsNotCounted = new Integer(numberOfRowsOpen).intValue();
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");			
			log.error(errorMsg, e);
		}
		return rowsNotCounted;
	}

	public Collection<String>  closeCount (HubCycleCountInputBean input, PersonnelBean user) throws BaseException, Exception {

		BigDecimal userId = user.getPersonnelIdBigDecimal();		
		Collection<String> errorMessages = new Vector<String>();
		
//		Update HUB_COUNT_DATE if all rows counted or user still wants to close Count: 
//		Update HUB_COUNT_DATE set COUNT_STATUS = 'Closed' where HUB = ### and 
//		 COUNT_ID = ### 
		
		try {
			StringBuilder strQuery = new StringBuilder();
			strQuery.append(" Update HUB_COUNT_DATE set COUNT_STATUS = 'Closed', ");
			strQuery.append(" COUNT_CLOSED_BY = " + userId);			
			strQuery.append(" where HUB = " + input.getHub());
			strQuery.append(" and COUNT_ID = " + input.getCountId());
			
			int numberOfRowsUpdated = factory.deleteInsertUpdate(strQuery.toString());
			if (log.isDebugEnabled())
				log.debug("Number of rows updated in CloseCount are - " + numberOfRowsUpdated);
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");			
			log.error(errorMsg, e);
		}
		return errorMessages;
	}

	public BigDecimal getCurrentCountId(HubCycleCountInputBean input) throws BaseException, Exception {
		String countId = "";
		Connection conn = dbManager.getConnection();
		BigDecimal currCountId = null;
		try {			
			StringBuilder query = new StringBuilder();
			query.append(" select count_id from hub_count_date where ");
			query.append(" hub = " + input.getHub());
			query.append(" and count_status = 'Open' and count_type = 'Cycle Count'");
		
			countId = factory.selectSingleValue(query.toString(), conn);
			
			if (countId != null && !countId.equalsIgnoreCase(""))
				currCountId = new BigDecimal(countId);
						
		} catch (Exception e) {
			log.error("Error getting hub cycle count Id for the selected hub", e);
			throw new BaseException(e);			  
		}
		finally {
			dbManager.returnConnection(conn);
		}
		
		return currCountId;
	}
	
	
	private Collection<String> getErrorMessages(Collection results, Collection<String> messages) {
		Iterator iterator = results.iterator();
		String result = null;
		while (iterator.hasNext()) {
			result = (String) iterator.next();
			if (!StringHandler.isBlankString(result)) {
				if (log.isDebugEnabled()) {
					log.debug("Result:" + result);
				}
				messages.add(result);
			}
		}
		return messages;
	}	
	
	public boolean startNewCountForCurrMonthValid(HubCycleCountInputBean input) throws BaseException, Exception {
		boolean blnCreate = false;

		try {
			int countMonth = 0;
			if (input.getCountMonth() == null || input.getCountMonth().equals(BigDecimal.ZERO)) {		
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				countMonth = cal.get(Calendar.MONTH);
				countMonth = countMonth + 1;
				input.setCountMonth(new BigDecimal(countMonth));
			} else 
				countMonth = input.getCountMonth().intValue();
			
			StringBuilder query = new StringBuilder();
			query.append(" select count(*) from hub_count_date where ");
			query.append(" hub = " + input.getHub());
			query.append(" and count_type = 'Cycle Count'");
			query.append(" and count_month = " + countMonth);
		
			String totalCountIds = factory.selectSingleValue(query.toString());
			if (totalCountIds != null && !totalCountIds.equalsIgnoreCase("")) {
				int totalnumber = new Integer(totalCountIds).intValue();
				if ( totalnumber > 0)
					blnCreate = false;
				else  
					blnCreate = true;				
			}		
		} catch (Exception e) {
			blnCreate = false;
			log.error("Error encountered while creating a new countid", e);
			throw new BaseException(e);			  
		}
		
		return blnCreate;		
	}
	
	public boolean isNextMonthCountStarted(HubCycleCountInputBean input)throws BaseException, Exception {
		boolean blnNextMnthCount = false;
		int currMonthCount = 0;
		try {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			currMonthCount = cal.get(Calendar.MONTH);
			currMonthCount = currMonthCount + 1;			
			
			StringBuilder query = new StringBuilder();
			query.append(" select count(*) from hub_count_date where ");
			query.append(" hub = " + input.getHub());
			query.append(" and count_type = 'Cycle Count'");
			query.append(" and count_month = " + (currMonthCount + 1));
		
			String totalCountIds = factory.selectSingleValue(query.toString());
			if (totalCountIds != null && !totalCountIds.equalsIgnoreCase("")) {
				int totalnumber = new Integer(totalCountIds).intValue();
				if ( totalnumber > 0)
					blnNextMnthCount = true;
				else  
					blnNextMnthCount = false;				
			}		
		} catch (Exception e) {
			blnNextMnthCount = false;
			log.error("Error encountered while creating a new countid", e);
			throw new BaseException(e);			  
		}
		
		return blnNextMnthCount;		
	}

}
