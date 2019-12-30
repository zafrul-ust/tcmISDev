package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;
import com.tcmis.common.util.ResourceLibrary;
import org.apache.commons.logging.Log;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SearchCriterion;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.DistributedCountUsageInputBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewWorkAreaBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewItemBean;
import com.tcmis.internal.hub.factory.DistributedCountUsageViewBeanFactory;
//import com.tcmis.internal.hub.factory.HubIgUndistrCountViewBeanFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.admin.process.BulkMailProcess;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class DistributedCountProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public DistributedCountProcess(String client) {
	super(client);
 }
 
 public DistributedCountProcess(String client,String locale) {
	    super(client,locale);
}

 /*public Collection getUndistributedCounts(DistributedCountUsageInputBean bean,
	Collection hubInventoryGroupOvBeanColl) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	HubIgUndistrCountViewBeanFactory factory = new
	 HubIgUndistrCountViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(
	 hubInventoryGroupOvBeanColl,
	 bean.getSourceHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

	if (bean.getCountId() != null)
	{
	 criteria.addCriterion("countId", SearchCriterion.EQUALS,
		 ""+bean.getCountId()+"");
	}

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("startDate");

	return factory.select(criteria,sortCriteria,bean,iscollection);
 }*/

 public Collection getsearchResult(DistributedCountUsageInputBean bean,
	Collection hubInventoryGroupOvBeanColl) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	SearchCriteria criteria = new SearchCriteria();

	if (bean.getSourceHub() != null &&
	 bean.getSourceHub().length() > 0) {
	 criteria.addCriterion("hub", SearchCriterion.EQUALS,
		 bean.getSourceHub());
	}

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(
	 hubInventoryGroupOvBeanColl,
	 bean.getSourceHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

	if (bean.getCountId() != null)
	{
	 criteria.addCriterion("countId", SearchCriterion.EQUALS,
		 ""+bean.getCountId()+"");
	}

	if (bean.getSearchWhat() != null && bean.getSearchText() != null &&
	 bean.getSearchText().trim().length() > 0) {
	 String criterion = null;
	 if (bean.getSearchType() != null &&
		bean.getSearchType().equalsIgnoreCase("IS")) {
		criterion = SearchCriterion.EQUALS;
	 }
	 else if (bean.getSearchType() != null &&
		bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
		criterion = SearchCriterion.LIKE;
	 }

	 String searchWhat = bean.getSearchWhat();
	 criteria.addCriterion(searchWhat, criterion, bean.getSearchText(),
		 SearchCriterion.IGNORE_CASE);
	}

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("inventoryGroup");
	sortCriteria.addCriterion("catPartNo");
	sortCriteria.addCriterion("itemId");
	sortCriteria.addCriterion("facilityId");
	sortCriteria.addCriterion("application");

	DistributedCountUsageViewBeanFactory factory = new
	 DistributedCountUsageViewBeanFactory(dbManager);

	return factory.select(criteria, sortCriteria, bean, iscollection);
 }

 public Collection copyAttributes(Collection
	distributedCountUsageViewBeanInputCollection, Vector savedBeanVector) {
	Collection distributedCountUsageViewBeanCollection = new Vector();
	Iterator iterator = distributedCountUsageViewBeanInputCollection.iterator();
	int count = 0;

	while (iterator.hasNext()) {
	 DistributedCountUsageViewBean inputBean = (DistributedCountUsageViewBean) iterator.
		next();
	 DistributedCountUsageViewBean savedBean = (DistributedCountUsageViewBean)
		savedBeanVector.get(count);

	 String catPartNo = com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.getCatPartNo());

	 if (catPartNo.equalsIgnoreCase(savedBean.getCatPartNo())) {
		if (inputBean.getOk() != null) {
		 String rowid = (String) inputBean.getOk();
		 savedBean.setOk(rowid);
		 //log.info("rowid     " + rowid + "");
		}
		else {
		 savedBean.setOk(null);
		}
		//log.info("getUomDistributedUsage     " + inputBean.getUomDistributedUsage() + "");
		if (inputBean.getUomDistributedUsage() !=null)
		{
		 savedBean.setUomDistributedUsage(inputBean.getUomDistributedUsage());
		 savedBean.setUsageChanged(inputBean.getUsageChanged());
		}
		}
	 distributedCountUsageViewBeanCollection.add(savedBean);
	 count++;
	}

	return distributedCountUsageViewBeanCollection;
 }

 public String updateCount(DistributedCountUsageInputBean inputBean,Collection
	distributedCountUsageViewBeanCollection,
	BigDecimal personnelId) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	DistributedCountUsageViewBeanFactory factory = new
	 DistributedCountUsageViewBeanFactory(dbManager);
	String errorMessage = "";
	String mrCreatedList = "";
	BulkMailProcess bulkMailProcess = new
		BulkMailProcess(this.getClient());
	Iterator mainIterator = distributedCountUsageViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 DistributedCountUsageViewBean currentDistributedCountUsageViewBean = (
		DistributedCountUsageViewBean)
		mainIterator.next(); ;

	 String currentCatPartNo = currentDistributedCountUsageViewBean.getCatPartNo();
	 Collection itemBeanCollection = currentDistributedCountUsageViewBean.
		getItemCollection();

	 Iterator itemIterator = itemBeanCollection.iterator();
	 while (itemIterator.hasNext()) {
		Collection processResult = null;
		DistributedCountUsageViewItemBean distributedCountUsageViewItemBean = (
		 DistributedCountUsageViewItemBean) itemIterator.next(); ;
		Collection workAreaCollection = distributedCountUsageViewItemBean.
		 getWorkAreaCollection();

		String currentOk = distributedCountUsageViewItemBean.getOk();
		//log.info("Here in Item loop  "+currentOk+"");
		Iterator workAreaIterator = workAreaCollection.iterator();
		if (currentOk != null && currentOk.length() > 0) {

		 while (workAreaIterator.hasNext()) {
			Collection updateResult = null;
			DistributedCountUsageViewWorkAreaBean
			 distributedCountUsageViewWorkAreaBean = (
			 DistributedCountUsageViewWorkAreaBean) workAreaIterator.next(); ;

			String usageChanged = distributedCountUsageViewWorkAreaBean.
			 getUsageChanged();
			if (usageChanged != null && usageChanged.equalsIgnoreCase("Y")) {
			 try {
				updateResult = factory.updateCountDistribution(
				 currentDistributedCountUsageViewBean,
				 distributedCountUsageViewWorkAreaBean, personnelId);
			 }
			 catch (Exception ex) {
			 }
			}

			if (updateResult != null) {
			 Iterator resultIterator = updateResult.iterator();
			 int resultCount = 0;
			 while (resultIterator.hasNext()) {
        if (resultCount == 0) {
				 resultIterator.next();
				}
				if (resultCount == 1) {
				 String errorCode = (String) resultIterator.next(); ;
				 if (errorCode != null)
					errorMessage += errorCode;
				}
				resultCount++;
			 }
			}
		 }
		 //Process Count
		 if (inputBean.getSubmitUpdate() !=null &&inputBean.getSubmitUpdate().length() > 0)
		 {
			try {
			 processResult = factory.processCountDistribution(
				currentDistributedCountUsageViewBean, distributedCountUsageViewItemBean,
				personnelId);
			}
			catch (Exception ex1) {
			}

			if (processResult != null) {
			 Iterator resultIterator = processResult.iterator();
			 int resultCount = 0;
			 while (resultIterator.hasNext()) {
				if (resultCount == 0) {
				 Integer prNumber = (Integer) resultIterator.next(); ;
				 //log.info("prNumber  " + prNumber + "");
				 mrCreatedList += "" + prNumber + "" + ",";
				}
				else if (resultCount == 1) {
				 String errorCode = (String) resultIterator.next(); ;
				 if (errorCode != null)
					errorMessage += errorCode;
				}
				resultCount++;
			 }
			}
		 }
		}
	 }
	}

	//log.info("mrCreatedList   " + mrCreatedList + "   errorMessage   " + errorMessage + "");
	/*if (inputBean.getSubmitUpdate() != null &&
	 inputBean.getSubmitUpdate().length() > 0) {
	 if (errorMessage != null && errorMessage.length() > 0) {
		//send error email
		bulkMailProcess.sendBulkEmail(personnelId,
		 "Error in processing distribution",
		 "An error occured while processing distributions for Hub " +
		 inputBean.getSourceHub() + " and InventoryGroup " +
		 inputBean.getInventoryGroup() + ".\n" +
		 "\nPlease try again.\nTech Center has been informed," +
		 " you will be contacted if needed." +
		 "\nCall the Tech Center if you have any questions.", false);
	 }
	 else {
		//Send sucess email
		ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
		bulkMailProcess.sendBulkEmail(personnelId,
		 "Item Count distribution processing results",
		 "The item count distribution has been processed, please go to the URL below to look at the results.\n\n\n" +
		 resource.getString("cabupconfirmurl") +
		 "UserAction=Search&mrslist=" + mrCreatedList + "", false);
	 }
	}
	else if (errorMessage != null && errorMessage.length() > 0) {
	 //send error email to nawaz
	 if (errorMessage.length() > 0) {
		bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
		 "Error from updating distribution",
		 "Error from updating distribution for Hub " + inputBean.getSourceHub() +
		 " and InventoryGroup " + inputBean.getInventoryGroup() + "\n\n" +
		 errorMessage +
		 "\n\nPersonnel ID:   " + personnelId + "", false);
	 }
	}*/

	//if mrCreatedList >0 or errorMessage >0 send opropritate emails
	return errorMessage;
 }

 public Collection createRelationalObject(Collection
	distributedCountUsageViewBeanCollection) {

	Collection finaldistributedCountUsageViewBeanCollection = new Vector();
	String nextPartNumber = "";
	String nextItem = "";
	String nextInventoryGroup = "";
	String rowChecked="";
	int samePartNumberCount = 0;
	int sameItemIdCount = 0;

	Vector collectionVector = new Vector(
	 distributedCountUsageViewBeanCollection);
	Vector itemIdV1 = new Vector();
	Vector workAreaV1 = new Vector();
	for (int loop = 0; loop < collectionVector.size(); loop++) {

	 DistributedCountUsageViewBean currentDistributedCountUsageViewBean = (
		DistributedCountUsageViewBean) collectionVector.elementAt(loop);
	 String currentPartNumber = currentDistributedCountUsageViewBean.getCatPartNo();
	 String currentItem = "" + currentDistributedCountUsageViewBean.getItemId() + "";
	 String currentInventoryGroup = currentDistributedCountUsageViewBean.getInventoryGroup();
	 String currentOk = currentDistributedCountUsageViewBean.getOk();

	 if (! ( (loop + 1) == collectionVector.size())) {
		DistributedCountUsageViewBean nextDistributedCountUsageViewBean = (
		 DistributedCountUsageViewBean) collectionVector.elementAt(loop + 1);

		nextPartNumber = nextDistributedCountUsageViewBean.getCatPartNo();
		nextItem = "" + nextDistributedCountUsageViewBean.getItemId() + "";
		nextInventoryGroup = nextDistributedCountUsageViewBean.getInventoryGroup();
	 }
	 else {
		nextPartNumber = "";
		nextItem = "";
		nextInventoryGroup = "";
	 }

	 boolean samePartNumber = false;
	 boolean sameItemId = false;

	 if (currentPartNumber.equalsIgnoreCase(nextPartNumber) && currentInventoryGroup.equalsIgnoreCase(nextInventoryGroup)) {
		samePartNumber = true;
		samePartNumberCount++;
		if (nextItem.equalsIgnoreCase(currentItem)) {
		 sameItemId = true;
		 if (sameItemIdCount ==0 && currentOk!=null)
		 {
			rowChecked = "Y";
		 }
		 sameItemIdCount++;
		}
	 }
   else if (sameItemIdCount ==0 && currentOk!=null)
	 {
			rowChecked = "Y";
	 }
   //log.info("Here realtion loop  "+currentOk+"");

	 DistributedCountUsageViewWorkAreaBean distributedCountUsageViewWorkAreaBean = new
		DistributedCountUsageViewWorkAreaBean();
	 distributedCountUsageViewWorkAreaBean.setItemId(
		currentDistributedCountUsageViewBean.getItemId());
	 distributedCountUsageViewWorkAreaBean.setFacilityId(
		currentDistributedCountUsageViewBean.getFacilityId());
	 distributedCountUsageViewWorkAreaBean.setApplication(
		currentDistributedCountUsageViewBean.getApplication());
	 distributedCountUsageViewWorkAreaBean.setDistributedUsage(
		currentDistributedCountUsageViewBean.getDistributedUsage());
	 distributedCountUsageViewWorkAreaBean.setUomDistributedUsage(
		currentDistributedCountUsageViewBean.getUomDistributedUsage());
	 distributedCountUsageViewWorkAreaBean.setUsageChanged(
		currentDistributedCountUsageViewBean.getUsageChanged());
	 distributedCountUsageViewWorkAreaBean.setCatPartNo(
		currentDistributedCountUsageViewBean.getCatPartNo());

	 workAreaV1.add(distributedCountUsageViewWorkAreaBean);

	 if (sameItemId) {

	 }
	 else {
		DistributedCountUsageViewItemBean distributedCountUsageViewItemBean = new
		 DistributedCountUsageViewItemBean();
		distributedCountUsageViewItemBean.setWorkAreaCollection( (Vector) workAreaV1.
		 clone());
		workAreaV1 = new Vector();

		distributedCountUsageViewItemBean.setItemId(currentDistributedCountUsageViewBean.
		 getItemId());
		distributedCountUsageViewItemBean.setUsage(
		 currentDistributedCountUsageViewBean.getUsage());
		distributedCountUsageViewItemBean.setUom(currentDistributedCountUsageViewBean.getUom());
		distributedCountUsageViewItemBean.setOk(rowChecked);
		itemIdV1.add(distributedCountUsageViewItemBean);
		sameItemIdCount =0;
		rowChecked="";
	 }

	 if (samePartNumber) {

	 }
	 else {
		currentDistributedCountUsageViewBean.setItemCollection( (Vector) itemIdV1.clone());
		itemIdV1 = new Vector();
		currentDistributedCountUsageViewBean.setRowSpan(new BigDecimal(
		 samePartNumberCount + 1));

		finaldistributedCountUsageViewBeanCollection.add(
		 currentDistributedCountUsageViewBean);
		samePartNumberCount = 0;
	 }
	}

	//log.debug("Final collectionSize here " + finaldistributedCountUsageViewBeanCollection.size() + "");
	return finaldistributedCountUsageViewBeanCollection;
 }

 /*public void writeExcelFile(Collection searchCollection, String filePath,
	CatalogInputBean bean) throws BaseException, Exception {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
	boolean allCatalog = false;
	if (bean.getFacilityOrAllCatalog() != null &&
	 bean.getFacilityOrAllCatalog().equalsIgnoreCase("All Catalogs")) {
	 allCatalog = true;
	}

	pw.println("<table border=\"1\">");
	pw.println("<TR>");
	pw.println("<TD><B>Facility:</B></TD>");
	pw.println("<TD>" + bean.getFacilityId() + "</TD>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("<TD><B>Work Area:</B></TD>");
	pw.println("<TD>" + bean.getApplicationId() + "</TD>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("<TD><B>Search:</B></TD>");
	pw.println("<TD>" + bean.getSearchText() + "</TD>");
	pw.println("</TR>");
	pw.println("</table>");
	pw.println("<BR><BR>");
	//write column headers
	pw.println("<table border=\"1\">");
	pw.println("<TR>");
	pw.println("<TH>Catalog</TH>");
	pw.println("<TH>Part</TH>");
	pw.println("<TH>Description</TH>");
	pw.println("<TH>Type</TH>");
	pw.println("<TH>Price</TH>");
	pw.println("<TH>Shelf Life @ Storage Temp</TH>");
	pw.println("<TH>Part UOM</TH>");
	pw.println("<TH>Qty UOM per Item</TH>");
	pw.println("<TH>Status</TH>");
	pw.println("<TH>Item</TH>");
	pw.println("<TH>Component Description</TH>");
	pw.println("<TH>Packaging</TH>");
	pw.println("<TH>Grade</TH>");
	pw.println("<TH>Manufacturer</TH>");
	pw.println("<TH>Mfg Part No.</TH>");
	pw.println("</TR>");

	DecimalFormat valuesFormat = new DecimalFormat("####.00##");
	valuesFormat.setMinimumFractionDigits(2);

	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
	 pw.println("<TR>");

	 DistributedCountUsageViewBean prCatalogScreenSearchBean = (
		DistributedCountUsageViewBean) i11.next(); ;

	 int mainRowSpan = prCatalogScreenSearchBean.getRowSpan().intValue();
	 Collection itemCollection = prCatalogScreenSearchBean.getItemCollection();

	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		prCatalogScreenSearchBean.getCatalogId() + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		prCatalogScreenSearchBean.getCatPartNo() + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		prCatalogScreenSearchBean.getPartDescription() + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		prCatalogScreenSearchBean.getStockingMethod() + "</TD>");

	 BigDecimal finalPrice = new BigDecimal("0");
	 BigDecimal minCatalogPrice = prCatalogScreenSearchBean.getMinCatalogPrice();
	 BigDecimal maxCatalogPrice = prCatalogScreenSearchBean.getMaxCatalogPrice();

	 if (allCatalog) {
		if (minCatalogPrice != null && maxCatalogPrice != null) {
		 finalPrice = maxCatalogPrice;
		}
		else if (minCatalogPrice != null) {
		 finalPrice = minCatalogPrice;
		}
		else if (maxCatalogPrice != null) {
		 finalPrice = maxCatalogPrice;
		}
	 }
	 else {
		finalPrice = minCatalogPrice;
	 }

	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">");
	 if (finalPrice == null || finalPrice.setScale(2, 3).floatValue() == 0) {

	 }
	 else {
		pw.println(valuesFormat.format(finalPrice) + " " +
		 prCatalogScreenSearchBean.getCurrencyId());
	 }
	 pw.println("</TD>");

	 String storageTemp = prCatalogScreenSearchBean.getStorageTemp();
	 String fnialShelfLife = "";
	 if (storageTemp != null && storageTemp.length() > 0) {
		if (!"Indefinite".equalsIgnoreCase(prCatalogScreenSearchBean.getShelfLife())) {
		 if (prCatalogScreenSearchBean.getShelfLifeBasis() != null) {
			fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() +
			 prCatalogScreenSearchBean.getShelfLifeBasis() + " @ " + storageTemp;
		 }
		 else {
			fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() + " @ " +
			 storageTemp;
		 }
		}
		else {
		 fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() + " @ " +
			storageTemp;
		}
	 }
	 else {
		fnialShelfLife = "&nbsp;";
	 }

	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" + fnialShelfLife + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.
		getUnitOfSale()) + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		com.tcmis.common.util.StringHandler.emptyIfZero(com.tcmis.common.util.
		NumberHandler.zeroBigDecimalIfNull(prCatalogScreenSearchBean.
		getUnitOfSaleQuantityPerEach())) + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.
		getApprovalStatus()) + "</TD>");

	 int itemCount = 0;
	 Iterator i11Item = itemCollection.iterator();
	 while (i11Item.hasNext()) {
		itemCount++;
		DistributedCountUsageViewItemBean prCatalogScreenSearchItemBean = (
		 DistributedCountUsageViewItemBean) i11Item.next(); ;

		if (itemCount > 1 && itemCollection.size() > 1) {
		 pw.println("<TR>");
		}

		Collection componenetCollection = prCatalogScreenSearchItemBean.
		 getComponentCollection();

		int componenetSize = componenetCollection.size();

		pw.println("<TD ROWSPAN=" + componenetSize + ">" +
		 prCatalogScreenSearchItemBean.getItemId() + "</TD>");

		int componenetCount = 0;
		Iterator i11Allocation = componenetCollection.iterator();
		while (i11Allocation.hasNext()) {
		 componenetCount++;
		 DistributedCountUsageViewWorkAreaBean prCatalogScreenSearchComponentBean = (
			DistributedCountUsageViewWorkAreaBean) i11Allocation.next(); ;

		 if (componenetCount > 1 && componenetSize > 1) {
			pw.println("<TR>");
		 }

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.
			getMaterialDesc()) + "</TD>");

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.
			getPackaging()) + "</TD>");

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.
			getGrade()) + "</TD>");

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.
			getMfgDesc()) + "</TD>");

		 pw.println("<TD>" +
			com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.
			getMfgPartNo()) + "</TD>");

		 if (componenetCount > 1 || componenetSize == 1) {
			pw.println("</TR>");
		 }
		}

		if (itemCount > 1) {
		 pw.println("</TR>");
		}
	 }
	}
	pw.println("</html>");
	pw.close();
 }*/
}