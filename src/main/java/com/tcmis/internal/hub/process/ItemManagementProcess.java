package com.tcmis.internal.hub.process;

import com.tcmis.client.operations.factory.ItemManagementIgOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.beans.ItemManagementInputBean;
import com.tcmis.internal.hub.factory.ItemCountInventoryViewBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class ItemManagementProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public ItemManagementProcess(String client) {
	super(client);
 }
 
 public ItemManagementProcess(String client,String locale) {
	    super(client,locale);
 }

 public Collection getItemMgmtIgOvBeanCollection(BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());

	ItemManagementIgOvBeanFactory factory = new ItemManagementIgOvBeanFactory(dbManager);
	SearchCriteria searchCriteria = new SearchCriteria();

    if (getClient().equalsIgnoreCase("TCM_OPS"))
    {
	 searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS,"Radian");
	}
	
	searchCriteria.addCriterion("personnelId", SearchCriterion.EQUALS,
	 personnelId.toString());
	Collection itemManagementIgOvBeanCollection = factory.selectObject(searchCriteria);
	return itemManagementIgOvBeanCollection;
 }

 public Collection getSearchResult(ItemManagementInputBean inputBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	ItemCountInventoryViewBeanFactory factory = new
	 ItemCountInventoryViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
    String s = null;
    s = inputBean.getInventoryGroup();
    if( s!= null && !s.equals("") )
    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
    				inputBean.getInventoryGroup());
//    s = inputBean.getHub();
    	
	if (inputBean.getShowActiveOnly() != null &&
	 inputBean.getShowActiveOnly().equals("Yes")) {
	 searchCriteria.addCriterion("icmrcStatus", SearchCriterion.EQUALS, "A");
	}

	if (inputBean.getShowMinMaxOnly() != null &&
	 inputBean.getShowMinMaxOnly().equals("Yes")) {
	 searchCriteria.addCriterion("stockingMethod", SearchCriterion.EQUALS, "MM");
	}

	if (inputBean.getSearchArgument() != null &&
	 inputBean.getSearchArgument().trim().length() > 0) {
	 String searchMode = inputBean.getSearchMode();
	 String searchField = inputBean.getSearchField();
	 String searchArgument = inputBean.getSearchArgument();
	 String ignoreCase = "";
	 if (searchField.equalsIgnoreCase("itemId")) {
		ignoreCase = SearchCriterion.IGNORE_CASE;
	 }
	 if (searchMode.equalsIgnoreCase("is")) {
		searchCriteria.addCriterion(searchField, SearchCriterion.EQUALS,
		 searchArgument.trim());
	 }
	 else if (searchMode.equalsIgnoreCase("contains")) {
		searchCriteria.addCriterion(searchField, SearchCriterion.LIKE,
		 searchArgument.trim(), ignoreCase);
	 }
	 else if (searchMode.equalsIgnoreCase("startsWith")) {
		searchCriteria.addCriterion(searchField, SearchCriterion.STARTS_WITH,
		 searchArgument.trim(), ignoreCase);
	 }
	 else if (searchMode.equalsIgnoreCase("endsWith")) {
		searchCriteria.addCriterion(searchField, SearchCriterion.ENDS_WITH,
		 searchArgument.trim(), ignoreCase);
	 }
	}

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("inventoryGroup");
	sortCriteria.addCriterion("catPartNo");
	sortCriteria.addCriterion("partGroupNo");
	return factory.select(searchCriteria, sortCriteria);
 }


 public ExcelHandler createExcelFile(ItemManagementInputBean bean, 	Locale locale) 
 	throws Exception {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	Collection data = this.getSearchResult(bean);
	//log.debug("data:" + data);
	Iterator iterator = data.iterator();
	ExcelHandler pw = new ExcelHandler(library);
	  
	pw.addTable();
	//write column headers
	
	pw.addRow();
   String[] headerkeys = {
    "label.inventorygroup","label.catalog","label.partnumber","label.setpointsrpslrq","label.stockingmethod",
    "label.uom","label.onhand","label.inpurchasing","label.item","label.itemdescription"};
   
   int [] widths = {
           13,0,17,17,0,
           35,0,13,0,0
           } ;  
   int [] types = {
           0,0,0,0,0,
           0,0,0,0,ExcelHandler.TYPE_PARAGRAPH
           } ;    
   int [] aligns = {
           0,0,0,0,0,
           0,0,0,0,0
           } ;    
     
    pw.applyColumnHeader(headerkeys, types, widths, aligns);

	//now write data
	while (iterator.hasNext()) {
	 ItemCountInventoryViewBean itemCountInventoryViewBean = (
		ItemCountInventoryViewBean) iterator.next();
	 pw.addRow();
	 pw.addCell(itemCountInventoryViewBean.getInventoryGroup());
	 pw.addCell(itemCountInventoryViewBean.getCatalogId());
	 pw.addCell(itemCountInventoryViewBean.getCatPartNo());
	 pw.addCell(
		StringHandler.emptyIfNull(itemCountInventoryViewBean.getReorderPoint()) +
		"/"
		+ StringHandler.emptyIfNull(itemCountInventoryViewBean.getStockingLevel()) +
		"/"
		+ StringHandler.emptyIfNull(itemCountInventoryViewBean.getReorderQuantity())
	 );
	 pw.addCell(itemCountInventoryViewBean.getStockingMethod());
	 pw.addCell(itemCountInventoryViewBean.getItemPackaging());
	 if (itemCountInventoryViewBean.getPartOnHand() != null)
	     pw.addCell(new BigDecimal(itemCountInventoryViewBean.getPartOnHand().trim()));
	 else
		 pw.addCell((BigDecimal)null);
	 if (itemCountInventoryViewBean.getItemInPurchasing() != null)
	     pw.addCell(new BigDecimal(itemCountInventoryViewBean.getItemInPurchasing().trim()));
     else
	     pw.addCell((BigDecimal)null);	 	 
	 pw.addCell(itemCountInventoryViewBean.getItemId());
	 pw.addCell(itemCountInventoryViewBean.getItemDesc());
	}
return pw;
}

 public Collection updateItemInvColl(Collection
	itemMgmtUpdateBeanCollection,
	BigDecimal personnelId) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	ItemCountInventoryViewBeanFactory factory = new
	 ItemCountInventoryViewBeanFactory(dbManager);
	Iterator mainIterator = itemMgmtUpdateBeanCollection.iterator();
	Collection allResults = new Vector(2);
	Collection errors = new Vector();
	Collection messages = new Vector();

	String errorMessage;
	Collection results;
	Iterator resultsIter;

	while (mainIterator.hasNext()) {
	 ItemCountInventoryViewBean updateBean = (ItemCountInventoryViewBean)
		mainIterator.next();
	 if (updateBean.getOkDoUpdate() != null && updateBean.getOkDoUpdate().length() > 0) {
		if (updateBean.getIcmrcStatus() !=null && !updateBean.getIcmrcStatus().equals(updateBean.getOldIcmrcStatus())) {
		 errorMessage = null;
		 factory.updateItemIcmrcStatus(updateBean);
		}

		if (updateBean.getReplenishQty() != null) {
		 errorMessage = null;
		 results = factory.forceBuyItemInventory(updateBean,personnelId.toString());
		 resultsIter = results.iterator();
		 if (resultsIter.hasNext()) {
			errorMessage = (String) resultsIter.next();
			messages.add(errorMessage);
		 }
		}
	 }
	}
	allResults.add(messages);
	return allResults;
 }
}