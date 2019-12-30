package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Locale;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.CompanyFacInvoiceDateBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailInputBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailViewBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailViewTotalsBean;
import com.tcmis.internal.hub.factory.FacilityInvoicePeriodViewBeanFactory;
import com.tcmis.internal.hub.factory.MonthlyInventoryDetailViewBeanFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.internal.hub.factory.CompanyFacInvoiceDateOvBeanFactory;
import com.tcmis.common.util.ExcelHandler;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class MonthlyInventoryDetailProcess
	extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public MonthlyInventoryDetailProcess(String client) {
	super(client);
  }
  
  public MonthlyInventoryDetailProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getSearchResult(MonthlyInventoryDetailInputBean bean,Collection hubInventoryGroupOvBeanColl) throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	MonthlyInventoryDetailViewBeanFactory factory =
		new MonthlyInventoryDetailViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();
	//add inventory group to criteria if not "All"
	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
	 bean.getHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

	 if (bean.getInvoiceDate() != null &&
		bean.getInvoiceDate().trim().length() > 0) {
	  criteria.addCriterion("invoicePeriod",
							SearchCriterion.EQUALS,
							bean.getInvoiceDate());
	 }

	 if (bean.getCompanyId() != null &&
		!bean.getCompanyId().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("directedCompanyId",
		 SearchCriterion.EQUALS,
		 bean.getCompanyId());
	 }

	 if (bean.getFacilityId() != null &&
		!bean.getFacilityId().equalsIgnoreCase("ALL")) {
		criteria.addCriterion("directedFacilityId",
		 SearchCriterion.EQUALS,
		 bean.getFacilityId());
	 }

	return factory.select(criteria,bean,iscollection);
  }

	public Collection getCompanyFacilityFlatData(BigDecimal personnelId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from company_fac_invoice_date_vw");
		query.append(" where personnel_id = ").append(personnelId);
		query.append(" order by company_name, facility_name, end_date, invoice_group, invoice_period");
		
		return new GenericSqlFactory(new DbManager(getClient(),getLocale()), new CompanyFacInvoiceDateBean()).selectQuery(query.toString());
	}
	
	public Collection getCompanyFacilityData(BigDecimal personnelId) throws
	 BaseException {
	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("personnelId",SearchCriterion.EQUALS,
														 "" + personnelId);

	DbManager dbManager = new DbManager(getClient(),getLocale());
	CompanyFacInvoiceDateOvBeanFactory factory = new CompanyFacInvoiceDateOvBeanFactory(dbManager);
	return factory.selectObject(criteria);
 }

  public Collection getFacilities() throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	FacilityInvoicePeriodViewBeanFactory factory =
		new FacilityInvoicePeriodViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	return factory.selectForFacilityCustomer(criteria);
  }

  public Collection getInvoicePeriods(MonthlyInventoryDetailInputBean bean,Collection hubInventoryGroupOvBeanColl) throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	FacilityInvoicePeriodViewBeanFactory factory =
		new FacilityInvoicePeriodViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();
	//add inventory group to criteria if not "All"
	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
	 bean.getHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

	if (bean.getFacilityId() != null &&
		!bean.getFacilityId().equalsIgnoreCase("ALL") &&
		bean.getFacilityId().length() > 0) {
	  criteria.addCriterion("facilityId",
							SearchCriterion.EQUALS,
							bean.getFacilityId());
	}

	return factory.selectForHub(criteria,bean,iscollection);
  }

  public Collection getCustomerInvoicePeriods(MonthlyInventoryDetailInputBean
											  bean,Collection hubInventoryGroupOvBeanColl) throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient());
	FacilityInvoicePeriodViewBeanFactory factory =
		new FacilityInvoicePeriodViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();
	//add inventory group to criteria if not "All"
	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
	 bean.getHub(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

	 if (iscollection) {

	 }
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
	 }
	}

	if (bean.getFacilityId() != null &&
		!bean.getFacilityId().equalsIgnoreCase("ALL") &&
		bean.getFacilityId().length() > 0) {
	  criteria.addCriterion("facilityId",
							SearchCriterion.EQUALS,
							bean.getFacilityId());
	}

	return factory.selectForCustomer(criteria,bean,iscollection);
  }

  public ExcelHandler writeExcelFile(Collection searchCollection, Locale locale) throws
	  BaseException, Exception {
  if(log.isDebugEnabled()) {
   log.debug("locale:" + locale);
   log.debug("country:" + locale.getCountry());
   log.debug("language:" + locale.getLanguage());
  }
  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

  ExcelHandler pw = new ExcelHandler(library);
  
  pw.addTable();

	pw.addRow();
	pw.addThRegion("label.item",2,1);
	pw.addThRegion("label.partnumber",2,1);
	pw.addThRegion("label.productname",2,1);
	pw.addThRegion("label.packaging",2,1);
	pw.addThRegion("label.countuom",2,1);
	pw.addThRegion("label.currency",2,1);
	pw.addThRegion("label.startinventory",1,4);
	pw.addThRegion("label.startinventoryvalue",1,4);
	pw.addThRegion("label.received",1,4);
	pw.addThRegion("label.receivedvalue",1,4);
	pw.addThRegion("label.endinventory",1,4);
	pw.addThRegion("label.endinventoryvalue",1,4);
	pw.addThRegion("label.usage",1,4);
	pw.addThRegion("label.usagevalue",1,4);
	

	pw.addRow();
	pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.consigned");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");
    
	 String[] headerkeys = {
		      "label.item","label.partnumber","label.productname","label.pkg","label.countuom","label.currency",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total",
		      "label.haas","label.consigned","label.customer","label.total"};

	 int[] types = {
        0,0,ExcelHandler.TYPE_PARAGRAPH,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        0,0,0,0};
	 int[] widths = {
             0,18,0,40,0,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0,
             0,11,10,0};
	 int[] horizAligns = {
        0,0,0,0,0,
        0,0,0,0,0,
        0,0,0,0,0};

 pw.setColumnHeader(headerkeys,types, widths, horizAligns);
 pw.applyColumnWidth();

 int i;
 for(i=6;i<38;i++)
 pw.setColumnDigit(i, 2);
 
 
 
	DecimalFormat valuesFormat = new DecimalFormat("###0.00##");
	valuesFormat.setMinimumFractionDigits(2);

	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
	  pw.addRow();

	  MonthlyInventoryDetailViewBean monthlyInventoryDetailSnapColl = (
		  MonthlyInventoryDetailViewBean) i11.next(); ;
	  pw.addCell(monthlyInventoryDetailSnapColl.getItemId());
	  pw.addCell(monthlyInventoryDetailSnapColl.getCatPartNo());
	  pw.addCell(monthlyInventoryDetailSnapColl.getItemDesc());
	  pw.addCell(monthlyInventoryDetailSnapColl.getPackaging());
	  pw.addCellBold(monthlyInventoryDetailSnapColl.getCountUom());
	  pw.addCell(monthlyInventoryDetailSnapColl.getCurrencyId());
	  
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiConsignedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiInventory());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiConsignedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecConsignedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getQuantityReceived());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecConsignedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiConsignedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiInventory());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiConsignedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageConsignedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getNetUsageQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageConsignedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getNetUsageValue());
/*	  pw.addCell(
			  com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiConsignedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getBiInventory().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiConsignedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getBiInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecConsignedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getQuantityReceived().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecConsignedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getRecInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiConsignedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getEiInventory().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiConsignedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getEiInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageConsignedQuantity().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getNetUsageQuantity().
								   setScale(2, 3)) );
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageConsignedValue().setScale(2, 3)));
	  pw.addCell(
			   com.tcmis.common.util.StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getNetUsageValue().
								   setScale(2, 3)) );
	  */
	}

	writetotals(pw,searchCollection, "hubpage");
	//pw.write(new FileOutputStream(filePath));
	return pw;
  }

  public void writeCoustomerExcelFile(Collection searchCollection,
									  String filePath,Locale locale) throws
	  BaseException, Exception {
   if(log.isDebugEnabled()) {
    log.debug("locale:" + locale);
    log.debug("country:" + locale.getCountry());
    log.debug("language:" + locale.getLanguage());
  }
  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

  ExcelHandler pw = new ExcelHandler(library);
  
  pw.addTable();
  pw.addRow();
	pw.addThRegion("label.storagearea",2,1);
	pw.addThRegion("label.item",2,1);
	pw.addThRegion("label.partnumber",2,1);
	pw.addThRegion("label.productname",2,1);
	pw.addThRegion("label.packaging",2,1);
	pw.addThRegion("label.countuom",2,1);
	pw.addThRegion("label.currency",2,1);
	pw.addThRegion("label.startinventory",1,3);
	pw.addThRegion("label.startinventoryvalue",1,3);
	pw.addThRegion("label.received",1,3);
	pw.addThRegion("label.receivedvalue",1,3);
	pw.addThRegion("label.endinventory",1,3);
	pw.addThRegion("label.endinventoryvalue",1,3);
	pw.addThRegion("label.usage",1,3);
	pw.addThRegion("label.usagevalue",1,3);

	pw.addRow();
	pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

  pw.addCellKeyBold("label.haas");
	pw.addCellKeyBold("label.customer");
	pw.addCellKeyBold("label.total");

	
	String[] headerkeys = {
			  "label.storagearea","label.item","label.partnumber","label.productname","label.pkg","label.countuom","label.currency",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total",
		      "label.haas","label.customer","label.total"};

	 int[] types = {
      0,0,0,ExcelHandler.TYPE_PARAGRAPH,0,0,0,
      0,0,0,
      0,0,0,
      0,0,0,
      0,0,0,
      0,0,0,
      0,0,0,
      0,0,0,
      0,0,0};
	 int[] widths = {
           0,0,15,0,38,0,0,
           0,11,0,
           0,11,0,
           0,11,0,
           0,11,0,
           0,11,0,
           0,11,0,
           0,11,0,
           0,11,0};
	 int[] horizAligns = {
      0,0,0,0,0,
      0,0,0,0,0,
      0,0,0,0,0};

pw.setColumnHeader(headerkeys,types, widths, horizAligns);
pw.applyColumnWidth();

int i;
for(i=7;i<31;i++)
pw.setColumnDigit(i, 2);
	
	
	

	DecimalFormat valuesFormat = new DecimalFormat("####.00##");
	valuesFormat.setMinimumFractionDigits(2);

	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
	  pw.addRow();

	  MonthlyInventoryDetailViewBean monthlyInventoryDetailSnapColl = (
		  MonthlyInventoryDetailViewBean) i11.next(); ;

	  pw.addCell(monthlyInventoryDetailSnapColl.getInventoryGroup());
	  pw.addCell(monthlyInventoryDetailSnapColl.getItemId());
	  pw.addCell(monthlyInventoryDetailSnapColl.getCatPartNo());
	  pw.addCell(monthlyInventoryDetailSnapColl.getItemDesc());
	  pw.addCell(monthlyInventoryDetailSnapColl.getPackaging());
	  pw.addCell( monthlyInventoryDetailSnapColl.getCountUom());
	  pw.addCell(StringHandler.emptyIfNull(monthlyInventoryDetailSnapColl.getCurrencyId()));
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiInventory());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getBiInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getQuantityReceived());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getRecInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiInventory());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getEiInventoryValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageHaasOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageCustomerOwnedQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getNetUsageQuantity());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageHaasOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getUsageCustomerOwnedValue());
	  pw.addCell(monthlyInventoryDetailSnapColl.getNetUsageValue());  
/*	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getBiInventory().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getBiCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getBiInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getQuantityReceived().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getRecCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getRecInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getEiInventory().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getEiCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getEiInventoryValue().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageHaasOwnedQuantity().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageCustomerOwnedQuantity().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getNetUsageQuantity().
								   setScale(2, 3)) );
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageHaasOwnedValue().setScale(2, 3)));
	  pw.addCell(
			   StringHandler.emptyIfZero(monthlyInventoryDetailSnapColl.
		  getUsageCustomerOwnedValue().setScale(2, 3)));
	  pw.addCellBold(
			   valuesFormat.format(monthlyInventoryDetailSnapColl.getNetUsageValue().
								   setScale(2, 3)) );    */
	}

	writetotals(pw,searchCollection, "danapage");
	pw.write(new FileOutputStream(filePath));
  }

  private boolean validateInput(MonthlyInventoryDetailInputBean bean) {
	/*
		if (bean == null ||
		 bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
			bean.getPersonnelId() == 0 ||
		 bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
			bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
		  return false;
		}
		else {
		  return true;
		}
	 */
	return true;
  }

  private ExcelHandler writetotals(ExcelHandler pw,Collection searchCollection,String indicator) {
	Collection currencyColl = new Vector();
	Hashtable monthlyInventoryDetailTotals = new Hashtable();
	//MonthlyInventoryDetailViewTotalsBean monthlyInventoryDetailViewTotalsBean;
	DecimalFormat valuesFormat = new DecimalFormat("###0.00##");
	valuesFormat.setMinimumFractionDigits(2);

	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
			   MonthlyInventoryDetailViewBean monthlyInventoryDetailSnapColl = (
					   MonthlyInventoryDetailViewBean) i11.next(); ;

			 String currencyId = monthlyInventoryDetailSnapColl.getCurrencyId() == null ? "" : monthlyInventoryDetailSnapColl.getCurrencyId();

			 if (!currencyColl.contains(currencyId)) {
			   MonthlyInventoryDetailViewTotalsBean monthlyInventoryDetailViewTotalsBean2 = new
				   MonthlyInventoryDetailViewTotalsBean();
			   monthlyInventoryDetailViewTotalsBean2.setCurrencyId(currencyId);

			   monthlyInventoryDetailTotals.put(currencyId,
												monthlyInventoryDetailViewTotalsBean2);
			   currencyColl.add(currencyId);
			 }

			 MonthlyInventoryDetailViewTotalsBean monthlyInventoryDetailViewTotalsBean = (
				   MonthlyInventoryDetailViewTotalsBean) monthlyInventoryDetailTotals.
				   get(currencyId);


			 monthlyInventoryDetailViewTotalsBean.setBiInventoryValue(
				 monthlyInventoryDetailSnapColl.getBiInventoryValue());
			 monthlyInventoryDetailViewTotalsBean.setBiHaasOwnedValue(
				 monthlyInventoryDetailSnapColl.getBiHaasOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setBiConsignedValue(
				 monthlyInventoryDetailSnapColl.getBiConsignedValue());
			 monthlyInventoryDetailViewTotalsBean.setBiCustomerOwnedValue(
				 monthlyInventoryDetailSnapColl.getBiCustomerOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setRecInventoryValue(
				 monthlyInventoryDetailSnapColl.getRecInventoryValue());
			 monthlyInventoryDetailViewTotalsBean.setRecHaasOwnedValue(
				 monthlyInventoryDetailSnapColl.getRecHaasOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setRecConsignedValue(
				 monthlyInventoryDetailSnapColl.getRecConsignedValue());
			 monthlyInventoryDetailViewTotalsBean.setRecCustomerOwnedValue(
				 monthlyInventoryDetailSnapColl.getRecCustomerOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setEiInventoryValue(
				 monthlyInventoryDetailSnapColl.getEiInventoryValue());
			 monthlyInventoryDetailViewTotalsBean.setEiHaasOwnedValue(
				 monthlyInventoryDetailSnapColl.getEiHaasOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setEiConsignedValue(
				 monthlyInventoryDetailSnapColl.getEiConsignedValue());
			 monthlyInventoryDetailViewTotalsBean.setEiCustomerOwnedValue(
				 monthlyInventoryDetailSnapColl.getEiCustomerOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setNetUsageValue(
				 monthlyInventoryDetailSnapColl.getNetUsageValue());
			 monthlyInventoryDetailViewTotalsBean.setUsageHaasOwnedValue(
				 monthlyInventoryDetailSnapColl.getUsageHaasOwnedValue());
			 monthlyInventoryDetailViewTotalsBean.setUsageConsignedValue(
				 monthlyInventoryDetailSnapColl.getUsageConsignedValue());
			 monthlyInventoryDetailViewTotalsBean.setUsageCustomerOwnedValue(
				 monthlyInventoryDetailSnapColl.getUsageCustomerOwnedValue());
		   }

		   boolean hubpage = false;
		   if ("hubpage".equalsIgnoreCase(indicator))
		   {
			 hubpage = true;
		   }
		   try {
			 Enumeration E;
			 for (E = monthlyInventoryDetailTotals.keys(); E.hasMoreElements(); ) {
			   String key = (String) E.nextElement();
			   MonthlyInventoryDetailViewTotalsBean monthlyInventoryDetailViewTotalsBean = (
				   MonthlyInventoryDetailViewTotalsBean)
				   monthlyInventoryDetailTotals.get(key);

		       pw.addRow();
			   if (hubpage)
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
		       }
			   else
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }

			   pw.addCellKeyBold("label.total");
			   pw.addCell(
							monthlyInventoryDetailViewTotalsBean.getCurrencyId() );

			   if (hubpage)
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   else
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getBiHaasOwnedValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getBiHaasOwnedValue().setScale(2, 3))); */
			   if (hubpage)
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getBiConsignedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getBiCustomerOwnedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getBiInventoryValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getBiConsignedValue().setScale(2, 3)));
			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getBiCustomerOwnedValue().setScale(2, 3)));
			   pw.addCellBold(
							valuesFormat.format(monthlyInventoryDetailViewTotalsBean.
												getBiInventoryValue().
												setScale(2, 3)) ); */

			   if (hubpage)
			  {
				pw.addTdEmpty();
				pw.addTdEmpty();
				pw.addTdEmpty();
				pw.addTdEmpty();
			  }
			  else
			  {
				pw.addTdEmpty();
				pw.addTdEmpty();
				pw.addTdEmpty();
			  }
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getRecHaasOwnedValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getRecHaasOwnedValue().setScale(2, 3))); */
			   if (hubpage)
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getRecConsignedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getRecCustomerOwnedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getRecInventoryValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getRecConsignedValue().setScale(2, 3)));
			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getRecCustomerOwnedValue().setScale(2, 3)));
			   pw.addCellBold(
							valuesFormat.format(monthlyInventoryDetailViewTotalsBean.
												getRecInventoryValue().
												setScale(2, 3)) );  */
			   if (hubpage)
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   else
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getEiHaasOwnedValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getEiHaasOwnedValue().setScale(2, 3)));  */
			   if (hubpage)
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getEiConsignedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getEiCustomerOwnedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getEiInventoryValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getEiConsignedValue().setScale(2, 3)));
			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getEiCustomerOwnedValue().setScale(2, 3)));
			   pw.addCellBold(
							valuesFormat.format(monthlyInventoryDetailViewTotalsBean.
												getEiInventoryValue().
												setScale(2, 3)) );  */
			   if (hubpage)
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   else
			   {
				 pw.addTdEmpty();
				 pw.addTdEmpty();
				 pw.addTdEmpty();
			   }
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getUsageHaasOwnedValue()); 
	/*		   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getUsageHaasOwnedValue().setScale(2, 3)));  */
			   if (hubpage)
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getUsageConsignedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getUsageCustomerOwnedValue());
			   pw.addCell(monthlyInventoryDetailViewTotalsBean.getNetUsageValue());
/*			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getUsageConsignedValue().setScale(2, 3)));
			   pw.addCell(
							StringHandler.emptyIfZero(
				   monthlyInventoryDetailViewTotalsBean.
				   getUsageCustomerOwnedValue().setScale(2, 3)));
			   pw.addCellBold(
							valuesFormat.format(monthlyInventoryDetailViewTotalsBean.
												getNetUsageValue().
												setScale(2, 3)) );  */
			   
		   }}
  catch (Exception e) {

	  }
  return pw;
	 }
  }
