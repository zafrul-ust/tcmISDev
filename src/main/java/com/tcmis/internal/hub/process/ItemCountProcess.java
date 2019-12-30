package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IgCountDefinitionBean;
import com.tcmis.internal.hub.beans.IgItemSearchViewBean;
import com.tcmis.internal.hub.beans.ItemCountDisplayViewBean;
import com.tcmis.internal.hub.beans.ItemCountInputBean;
import com.tcmis.internal.hub.factory.IgItemSearchViewBeanFactory;

/*******************************************************************
 * 
 * @version 1.0
 * 
 * 
 ********************************************************************/

public class ItemCountProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;

	public ItemCountProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

	public String addItemToCount(ItemCountInputBean input) throws BaseException, Exception {

		Collection inArgs = buildProcedureInput(input.getAddItemInventoryGroup(), input.getItemId());
		
		String errorMsg = null;
		Connection conn = null;

		try {
			conn = dbManager.getConnection();
			errorMsg = getProcError(conn,inArgs, null, "p_ig_count_item_insert");
			if(!StringHandler.isBlankString(errorMsg))
				return errorMsg;
			
			String mdItem = null;
			this.factory = new GenericSqlFactory(dbManager);
			mdItem = factory.selectSingleValue(new StringBuilder("select item_id from item_conversion where original_item_id = ").append(input.getItemId()).append(" and inventory_group = '").append(input.getAddItemInventoryGroup()).append("'").toString(),conn);
			
			if(!StringHandler.isBlankString(mdItem))
			{
				BigDecimal maItemAddedByUser = input.getItemId();
				String ig = input.getAddItemInventoryGroup();
				factory.setBean(new ItemCountInputBean());
				
				factory.deleteInsertUpdate(new StringBuilder("update ig_count_item set md_item_id = ").append(mdItem).append(" where item_id = ").append(maItemAddedByUser).toString(),conn);
				
				insertUpdateDisburseItem(ig, mdItem, mdItem,conn);
												
				Collection<ItemCountInputBean> maItems = factory.selectQuery(new StringBuilder("select original_item_id item_id from item_conversion where item_id = ").append(mdItem).append(" and inventory_group = '").append(ig).append("'").toString(),conn);
				
				for(ItemCountInputBean i : maItems)
				{	
					if(!maItemAddedByUser.equals(i.getItemId()))
						insertUpdateDisburseItem(ig, i.getItemId().toPlainString(), mdItem,conn);
				}
			}
			
			return errorMsg;	
		}
		catch (Exception e) {
			errorMsg = library.getString("error.db.update");
			log.error(errorMsg, e);
			return errorMsg;
		}
		finally
		{
			dbManager.returnConnection(conn);
		}
	}

	public Collection<IgCountDefinitionBean> getInventoryGroupsWithActiveCounts(ItemCountInputBean input) throws BaseException {

		factory.setBean(new IgCountDefinitionBean());

		SearchCriteria criteria = new SearchCriteria();
		String nestedQuery = "select distinct inventory_group from inventory_group_definition";
		SearchCriteria nestedCriteria = new SearchCriteria("hub", SearchCriterion.EQUALS, input.getHub());
		nestedCriteria.addCriterion("issueGeneration",  SearchCriterion.EQUALS, "Item Counting");
		criteria.addNestedQuery("inventoryGroup", nestedQuery, nestedCriteria);


		SortCriteria sort = new SortCriteria();
		sort.addCriterion("inventoryGroup");

		return factory.select(criteria, sort, "IG_COUNT_DEFINITION");
	}

	@SuppressWarnings("unchecked")
	public Collection<ItemCountDisplayViewBean> getSearchResult(ItemCountInputBean input) throws BaseException {

		factory.setBean(new ItemCountDisplayViewBean());
	
		if(input.isProcessDisbursement())
		{	
			StringBuilder disbQuery = new StringBuilder("SELECT icdv.md_item_id, icdv.item_id, icdv.*, case when icdv.md_item_id = icdv.item_id then 2 else 1 end order_by FROM tcm_ops.ITEM_COUNT_DISPLAY_VIEW icdv WHERE HUB = '");
			disbQuery.append(input.getHub()).append("'");
			disbQuery.append(" AND INVENTORY_GROUP = '").append(input.getInventoryGroup()[0]).append("'").append(" ORDER BY  icdv.md_item_id, order_by");
			return factory.selectQuery(disbQuery.toString());
		}
		
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterionWithMode("hub", "is", input.getHub());
		if (input.hasInventoryGroup()) {
			criteria.addCriterionArray("inventoryGroup", SearchCriterion.IN, input.getInventoryGroup());
		}

		SortCriteria sort = new SortCriteria();
		
		if ("Bin".equals(input.getSortBy())) {
			sort.addCriterion("lastBin");
			sort.addCriterion("itemId");
		}
		else {
			sort.addCriterion("inventoryGroup");
			if ("ItemID".equals(input.getSortBy())) {
				sort.addCriterion("itemId");
			}
			else if ("Description".equals(input.getSortBy())) {
				sort.addCriterion("description");
			}
			else if ("PartNumber".equals(input.getSortBy())) {
				sort.addCriterion("catPartNo");
			}
		}

		return factory.select(criteria, sort, input.isCollection() ? "ITEM_COUNT_DISPLAY_GROUP_VIEW" : "ITEM_COUNT_DISPLAY_VIEW");
	}

	@SuppressWarnings("unchecked")
	public Collection<IgItemSearchViewBean> getAddItemSearchResult(ItemCountInputBean input) throws BaseException {

		IgItemSearchViewBeanFactory factory = new IgItemSearchViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (input.hasInventoryGroup()) {
			criteria.addCriterionArray("inventoryGroup", SearchCriterion.IN, input.getInventoryGroup());
		}
		else if (input.hasHub()) {
			String nestedQuery = "select distinct inventory_group from inventory_group_definition";
			SearchCriteria nestedCriteria = new SearchCriteria("hub", SearchCriterion.EQUALS, input.getHub());
			nestedCriteria.addCriterion("issueGeneration",  SearchCriterion.EQUALS, "Item Counting");
			criteria.addNestedQuery("inventoryGroup", nestedQuery, nestedCriteria);
		}

		if (input.hasSearchText()) {
			criteria.addCriterion("searchString", SearchCriterion.LIKE, input.getSearchText());
		}

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("inventoryGroup");
		sort.addCriterion("itemId");
		sort.addCriterion("itemDesc");

		return factory.select(criteria, sort);
	}

	public ExcelHandler getExcelReport(ItemCountInputBean input) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		ExcelHandler excel = new ExcelHandler(library);

		/* Column headers for  Excel. */
		Vector<String> hk = new Vector<String>();
		hk.add("label.partnumber");
		hk.add("label.inventorygroup");
		hk.add("label.item");
		hk.add("label.description");
		if(!input.isProcessDisbursement())
		{
			hk.add("label.inpurchasing");
			hk.add("inventory.label.inventoryuom");
		}
		hk.add("label.quantityonhand");
		hk.add("label.countuom");
		hk.add("label.bin");
		hk.add("label.actualcount");
		
		String[] headerkeys = new String[hk.size()];
		headerkeys = hk.toArray(headerkeys);
		
		int[] types = new int[hk.size()];
		int typesCount = 0;
		types[typesCount++]= 0;
		types[typesCount++]= 0;
		types[typesCount++]= 0;
		types[typesCount++]= excel.TYPE_PARAGRAPH;
		if(!input.isProcessDisbursement())
		{
			types[typesCount++]=excel.TYPE_NUMBER;
			types[typesCount++]=0;
		}
		types[typesCount++]=excel.TYPE_NUMBER;
		types[typesCount++]=0;
		types[typesCount++]=0;
		types[typesCount++]= excel.TYPE_NUMBER;

		int[] widths = new int[hk.size()];
		
		widths[0] = 15;
		widths[1] = 20;
		
		for(int i = 2;i < widths.length;++i)
			widths[i]=0;
		

		// Cell horizontal alignment
		int[] horizAligns = null;

		// Create the table
		excel.addTable();

		// Create the column header row
		excel.addRow();
		excel.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write rows for the data
		for (ItemCountDisplayViewBean bean : getSearchResult(input)) {
			excel.addRow();
			excel.addCell(bean.getCatPartNo());
			excel.addCell(bean.getInventoryGroupName());
			excel.addCell(bean.getItemId());
			excel.addCell(bean.getDescription());		
			if(!input.isProcessDisbursement())
			{
				excel.addCell(bean.getInPurchasing());
				excel.addCell(bean.getPackaging());
			}
			excel.addCell(bean.getInventoryQuantity());
			excel.addCell(bean.getUom());
			excel.addCell(bean.getLastBin());
			excel.addCell(bean.getInventoryQuantity());
		}
		return excel;
	}

	/*
	 * procedure p_start_count ( v_inventory_group
	 * ig_count_definition.inventory_group%type, v_hub
	 * inventory_group_definition.hub%type, v_personnel_id number,
	 * a_collection char, v_error_message OUT VARCHAR2 )
	 */
	public Collection<String> startCount(ItemCountInputBean input, PersonnelBean user) throws BaseException, Exception {

		Collection<String> errorMessages = new Vector<String>();
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal userId = user.getPersonnelIdBigDecimal();

		try {
			Vector outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			if (input.hasInventoryGroup()) {
				for (String invGroup : input.getInventoryGroup()) {
					doStartCount(procFactory, invGroup, input, userId, outArgs, errorMessages);
				}
			}
			else {
				doStartCount(procFactory, "All", input, userId, outArgs, errorMessages);
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}

		return errorMessages;
	}

	private void doStartCount(GenericProcedureFactory procFactory, String inventoryGroup, ItemCountInputBean input, BigDecimal userId, Vector outArgs, Collection<String> errorMessages) throws BaseException, Exception {
		// Call P_START_COUNT
		Collection inArgs = buildProcedureInput(inventoryGroup, input.getHub(), userId, input.isCollection() ? "Y" : "");

		if (log.isDebugEnabled()) {
			log.debug("Start Count Procedure :" + inArgs + outArgs);
		}

		getErrorMessages(procFactory.doProcedure("P_START_COUNT", inArgs, outArgs), errorMessages);
	}

	private void doCloseCount(GenericProcedureFactory procFactory, String inventoryGroup, ItemCountInputBean input, BigDecimal userId, Vector outArgs, Collection<String> errorMessages) throws BaseException, Exception {
		Collection inArgs = buildProcedureInput(inventoryGroup, input.getHub(), userId, input.isCollection() ? "Y" : "");

		if (log.isDebugEnabled()) {
			log.debug("END Count Procedure :" + inArgs + outArgs);
		}

		getErrorMessages(procFactory.doProcedure("P_END_COUNT", inArgs, outArgs), errorMessages);
	}

	private void doSetDateCounted(GenericProcedureFactory procFactory, String inventoryGroup, ItemCountInputBean input, BigDecimal userId, Vector outArgs, Collection<String> errorMessages) throws BaseException, Exception {
		Collection inArgs = buildProcedureInput(inventoryGroup, input.getHub(), userId, input.isCollection() ? "Y" : "", input.getCountDate());

		if (log.isDebugEnabled()) {
			log.debug("P_SET_DATE_COUNTED Procedure :" + inArgs + outArgs);
		}

		getErrorMessages(procFactory.doProcedure("P_SET_DATE_COUNTED", inArgs, outArgs), errorMessages);
	}

	public Collection<String> closeCount(ItemCountInputBean input, PersonnelBean user) throws BaseException, Exception {

		Collection<String> errorMessages = new Vector<String>();
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal userId = user.getPersonnelIdBigDecimal();

		try {
			Vector outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (input.hasInventoryGroup()) {
				for (String invGroup : input.getInventoryGroup()) {
					doCloseCount(procFactory, invGroup, input, userId, outArgs, errorMessages);
				}
			}
			else {
				doCloseCount(procFactory, "All", input, userId, outArgs, errorMessages);
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}

		return errorMessages;
	}

	public Collection update(ItemCountInputBean input, Collection<ItemCountInputBean> rows, PersonnelBean user) throws BaseException, Exception {

		Collection<String> errorMessages = new Vector<String>();
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		BigDecimal userId = user.getPersonnelIdBigDecimal();

		// First, call P_SET_DATE_COUNTED
		// passed
		try {
			Vector outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (input.hasInventoryGroup()) {
				for (String inventoryGroup : input.getInventoryGroup()) {
					doSetDateCounted(procFactory, inventoryGroup, input, userId, outArgs, errorMessages);
				}
			}
			else {
				doSetDateCounted(procFactory, "All", input, userId, outArgs, errorMessages);
			}
		}
		catch (Exception e) {
			String errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}

		// If we didn't get an error then For each row that is checked
		// OR issueOnReceipt, call P_IG_COUNT_UPDATE
		if (errorMessages.isEmpty()) {
			StringBuilder prNums = new StringBuilder();

			for (ItemCountInputBean row : rows) {
				if (row.isRowCounted() || row.isIssueOnReceipt()) {

					if (row.isIssueOnReceipt()) {
						row.setCountedQuantity(new BigDecimal("0"));
					}
					
					Collection inArgs = buildProcedureInput(row.getCountId(), row.getItemId(), row.getCompanyId(), row.getCatalogId(), row.getCatPartNo(), row.getPartGroupNo(), user.getPersonnelIdBigDecimal(), row.getCountedQuantity(),
							row.getCountType());

					// 3 args returned, PR_NUMBER, LINE_ITEM
					// & RETURN
					Collection outArgs = new Vector(3);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					outArgs.add(new Integer(java.sql.Types.INTEGER));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					Collection optionalArgs = new Vector(3);
					optionalArgs.add(null);
					optionalArgs.add(null);
					optionalArgs.add(input.hasPoNumber() ? input.getPoNumber() : null);
					
					if (log.isDebugEnabled()) {
						log.debug("Calling P_IG_COUNT_UPDATE Procedure :" + inArgs + outArgs + optionalArgs);
					}

					Object[] results = procFactory.doProcedure("P_IG_COUNT_UPDATE", inArgs, outArgs, optionalArgs).toArray();
					if (log.isDebugEnabled()) {
						log.debug("P_IG_COUNT_UPDATE results :" + results[0] + ", " + results[1] + ", " + results[2]);
					}

					if (!"OK".equals(results[0])) {
						// There was an error, add a
						// message
						errorMessages.add("" + results[0]);
					}

					// Collect any returned PR numbers
					if (results[1] != null) {
						if (prNums.length() > 0) {
							prNums.append(",");
						}
						prNums.append(results[1]);
					}
				}
			}
			// Finally, close the count(s)
			errorMessages.addAll(closeCount(input, user));

			// If PRs were generated, tell the user
			if (prNums.length() > 0) {
				ResourceLibrary messages = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
				ResourceLibrary resources = new ResourceLibrary("tcmISWebResource");
				//TCMISPROD-1228
				String hosturl = resources.getString("hosturl");
				if( hosturl.charAt(hosturl.length()-1) == '/' ) {
					hosturl = hosturl.substring(0, hosturl.length()-1);
				}
				BulkMailProcess bulkMailProcess = new BulkMailProcess(getClient());
				String confirmationURL = resources.getString("cabupconfirmurl");
				// debug on QA.
				//				System.out.println("hosturl+confirmationURL:"+(hosturl+confirmationURL));
				bulkMailProcess.sendBulkEmail(user.getPersonnelIdBigDecimal(), messages.getString("letter.itemcounttitle"), messages.format("letter.itemcount", hosturl+confirmationURL, "UserAction=Search&mrslist=", prNums.toString()), true);
			}

		}

		return errorMessages;
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
	
	public String isProcessDisbursement(String inventoryGroup) throws Exception {
	
		String isProcessDisbursement = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject()).getString("error.db.select");
		GenericSqlFactory genericSqlFactory = null;
		try
		{
			genericSqlFactory = new GenericSqlFactory(dbManager);
			return genericSqlFactory.selectSingleValue(new StringBuilder("select process_disbursement from inventory_group_definition where inventory_group = '").append(inventoryGroup).append("'").toString());
		}catch(Exception e)
		{
			
			log.info("Error selecting process_disbursement from inventory_group_definition");
		}
		finally
		{
			genericSqlFactory = null;
		}
		
		return isProcessDisbursement;
		
	}
	
	private String insertUpdateDisburseItem(String ig, String itemId, String mdItemId,Connection conn) throws Exception
	{
		Collection inArgs = buildProcedureInput(ig, itemId);
		String errorMsg = getProcError(conn,inArgs, null, "p_ig_count_item_insert");
		if(!StringHandler.isBlankString(errorMsg))
			return errorMsg;
		factory.deleteInsertUpdate(new StringBuilder("update ig_count_item set md_item_id = ").append(mdItemId).append(" where item_id = ").append(itemId).toString(),conn);		
		return null;
	}
}
