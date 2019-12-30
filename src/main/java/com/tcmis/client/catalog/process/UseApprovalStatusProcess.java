package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import com.tcmis.common.util.ResourceLibrary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusInputBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusViewBean;
import com.tcmis.client.catalog.factory.FacAppUserGrpOvBeanFactory;
import com.tcmis.client.catalog.factory.FacLocAppBeanFactory;
import com.tcmis.client.catalog.factory.OverLimitUseApproverViewBeanFactory;
import com.tcmis.client.catalog.factory.UseApprovalStatusViewBeanFactory;
import com.tcmis.client.catalog.factory.VvUseApprovalOrderQtyRuleBeanFactory;
import com.tcmis.client.catalog.factory.AppManagedUseApprovalBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ExcelHandler;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class UseApprovalStatusProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public UseApprovalStatusProcess(String client) {
	super(client);
 }

 public Collection getVvOrderQtyRule() throws NoDataException, BaseException,
	Exception {
	DbManager dbManager = new DbManager(getClient());
	VvUseApprovalOrderQtyRuleBeanFactory vvUseApprovalOrderQtyRuleBeanFactory = new
	 VvUseApprovalOrderQtyRuleBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();
	return vvUseApprovalOrderQtyRuleBeanFactory.select(criteria);
 }

 public Collection getSearchResult(UseApprovalStatusInputBean bean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	UseApprovalStatusViewBeanFactory factory = new
	 UseApprovalStatusViewBeanFactory(dbManager);

	return factory.select(bean);
 }

 public Collection getFacilityData() throws BaseException {

	DbManager dbManager = new DbManager(getClient());
	SearchCriteria criteria = new SearchCriteria();

	FacAppUserGrpOvBeanFactory factory = new FacAppUserGrpOvBeanFactory(dbManager);
	return factory.selectObject(criteria);
 }

 public Collection copyAttributes(Collection
	useApprovalStatusViewBeanInputCollection, Vector savedBeanVector) {
	Collection useApprovalStatusViewBeanCollection = new Vector();
	Iterator iterator = useApprovalStatusViewBeanInputCollection.iterator();
	int count = 0;

	while (iterator.hasNext()) {
	 UseApprovalStatusViewBean inputBean = (UseApprovalStatusViewBean) iterator.
		next();
	 UseApprovalStatusViewBean savedBean = (UseApprovalStatusViewBean)
		savedBeanVector.get(count);

	 String facPartNo = com.tcmis.common.util.StringHandler.emptyIfNull(inputBean.
		getFacPartNo());

	 if (facPartNo.equalsIgnoreCase(savedBean.getFacPartNo())) {
		if (inputBean.getOk() != null) {
		 String rowid = (String) inputBean.getOk();
		 savedBean.setOk(rowid);
		 log.info("rowid     " + rowid + "");
		}
		else {
		 savedBean.setOk(null);
		}

		if ("Y".equalsIgnoreCase(inputBean.getUseApprovalChanged()) ||
		 "Y".equalsIgnoreCase(inputBean.getAutomatedFeedChanged())) {
		 savedBean.setMwLimitQuantityPeriod1(inputBean.getMwLimitQuantityPeriod1());
		 savedBean.setMwLimitQuantityPeriod2(inputBean.getMwLimitQuantityPeriod2());
		 savedBean.setMwDaysPeriod1(inputBean.getMwDaysPeriod1());
		 savedBean.setMwDaysPeriod2(inputBean.getMwDaysPeriod2());
		 savedBean.setMwOrderQuantity(inputBean.getMwOrderQuantity());
		 if ( (savedBean.getMwOrderQuantityRule() != null &&
			savedBean.getMwOrderQuantityRule().trim().length() > 0) &&
			(inputBean.getMwOrderQuantityRule() == null ||
			inputBean.getMwOrderQuantityRule().length() ==0)) {
		 }
		 else
		 {
			savedBean.setMwOrderQuantityRule(inputBean.getMwOrderQuantityRule());
		 }
		 savedBean.setMwEstimateOrderQuantityPrd(inputBean.
			getMwEstimateOrderQuantityPrd());
		 savedBean.setMwProcessDesc(inputBean.getMwProcessDesc(),true);
		 savedBean.setCustomerDeliverTo(inputBean.getCustomerDeliverTo());
		 savedBean.setDockLocationId(inputBean.getDockLocationId());
		 savedBean.setDockDeliveryPoint(inputBean.getDockDeliveryPoint());
		 savedBean.setDeliveryPointBarcode(inputBean.getDeliveryPointBarcode());
		 savedBean.setBarcodeRequester(inputBean.getBarcodeRequester());
		 savedBean.setRowNumber(inputBean.getRowNumber());
		 savedBean.setUseApprovalChanged(inputBean.getUseApprovalChanged());
		 savedBean.setAutomatedFeedChanged(inputBean.getAutomatedFeedChanged());
		}
	 }
	 useApprovalStatusViewBeanCollection.add(savedBean);
	 count++;
	}

	return useApprovalStatusViewBeanCollection;
 }

 public String updateSelected(UseApprovalStatusInputBean inputBean,Collection useApprovalStatusViewBeanCollection,
	BigDecimal personnelId) throws BaseException {
	DbManager dbManager = new DbManager(getClient());
	UseApprovalStatusViewBeanFactory factory = new
	 UseApprovalStatusViewBeanFactory(dbManager);
	String errorMessage = "";

	if (inputBean.getUpdateAllRows() != null &&
	 inputBean.getUpdateAllRows().length() > 0) {
	 AppManagedUseApprovalBeanFactory appManagedfactory = new AppManagedUseApprovalBeanFactory(dbManager);
	 appManagedfactory.populateManagedWorkArea(inputBean,personnelId);
	}

	Iterator mainIterator = useApprovalStatusViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 UseApprovalStatusViewBean currentUseApprovalStatusViewBean = (
		UseApprovalStatusViewBean) mainIterator.next(); ;

	 String currentOk = currentUseApprovalStatusViewBean.getOk();
	 String useApprovalChanged = currentUseApprovalStatusViewBean.
		getUseApprovalChanged();
	 String automatedFeedChanged = currentUseApprovalStatusViewBean.
		getAutomatedFeedChanged();

	 //log.info("updateSelected currentOk " + currentOk + " mw  "+currentUseApprovalStatusViewBean.getMwApprovalId()+" "+useApprovalChanged+"");

	 if ( (currentOk != null && currentOk.length() > 0) ||
		(currentUseApprovalStatusViewBean.getMwApprovalId() != null)) {
		Collection result = null;

		if (useApprovalChanged != null && useApprovalChanged.length() > 0) {
		 //log.info("Calling p_app_managed_use_approval   currentOk " + currentOk + "");
		 try {
			factory.updateUseApproval(currentUseApprovalStatusViewBean,personnelId);
		 }
		 catch (Exception ex) {
			errorMessage += "error Calling p_app_managed_use_approval <BR>";
		 }
		 if (result != null) {
			Iterator resultIterator = result.iterator();
			int resultCount = 0;
			while (resultIterator.hasNext()) {
			 if (resultCount == 0) {
				String errorMessageChild = (String) resultIterator.next(); ;
				if (errorMessageChild != null && errorMessageChild.length() > 0) {
				 errorMessage += "<BR>";
				}
			 }
			 resultCount++;
			}
		 }
		}
		if (automatedFeedChanged != null && automatedFeedChanged.length() > 0) {

		 //log.info("Calling p_barcode_data   currentOk " + currentOk + "");
		 try {
			result = factory.updateAutomatedFeedInformation(
			 currentUseApprovalStatusViewBean, personnelId);
		 }
		 catch (Exception ex1) {
			errorMessage += "error Calling p_barcode_data <BR>";
		 }
		}
	 }
	}
	return errorMessage;
 }

 public Collection getApplicationProperties(FacLocAppBean facLocAppBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	FacLocAppBeanFactory factory = new FacLocAppBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();
	if (facLocAppBean.getFacilityId() != null &&
	 !facLocAppBean.getFacilityId().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("facilityId", SearchCriterion.EQUALS,
		facLocAppBean.getFacilityId());
	}

	if (facLocAppBean.getApplication() != null &&
	 !facLocAppBean.getApplication().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("application", SearchCriterion.EQUALS,
		facLocAppBean.getApplication());
	}

	return factory.select(criteria,null);
 }

 public Collection getUseApprovers(FacLocAppBean facLocAppBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	OverLimitUseApproverViewBeanFactory factory = new
	 OverLimitUseApproverViewBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();
	if (facLocAppBean.getFacilityId() != null &&
	 !facLocAppBean.getFacilityId().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("facilityId", SearchCriterion.EQUALS,
		facLocAppBean.getFacilityId());
	}

	if (facLocAppBean.getApplication() != null &&
	 !facLocAppBean.getApplication().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("application", SearchCriterion.EQUALS,
		facLocAppBean.getApplication());
	}

	return factory.select(criteria);
 }

 public int updateUseApprovalLimitsOption(FacLocAppBean facLocAppBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	FacLocAppBeanFactory factory = new FacLocAppBeanFactory(dbManager);
	return factory.updateUseApprovalLimitsOption(facLocAppBean);
 }

 public int updateManagedUseApproval(FacLocAppBean facLocAppBean,BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	FacLocAppBeanFactory factory = new FacLocAppBeanFactory(dbManager);
	return factory.updateManagedUseApproval(facLocAppBean,personnelId);
 }

 public ExcelHandler writeExcelFile(Collection searchCollection,java.util.Locale locale) throws
	BaseException, Exception {
	
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
	  
  //write column headers
	  pw.addTable();

//	  write column headers
	  pw.addRow();

	pw.addCellKeyBold("label.catalog");
	pw.addCellKeyBold("label.partnumber");
	pw.addCellKeyBold("label.partdescription");
	pw.addCellKeyBold("useapprovalstatus.label.approvedstatus");
	pw.addCellBold(library.getString("useapprovalstatus.label.limit1")+" "+library.getString("label.qty"));
	pw.addCellKeyBold("label.period");
	pw.addCellBold(library.getString("useapprovalstatus.label.limit2")+" "+library.getString("label.qty"));
	pw.addCellKeyBold("label.period");
	pw.addCellKeyBold("useapprovalstatus.label.processdesc");
	pw.addCellKeyBold("label.active");
	pw.addCellBold(library.getString("useapprovalstatus.label.limit1")+" "+library.getString("label.qty"));
	pw.addCellKeyBold("label.period");
	pw.addCellBold(library.getString("useapprovalstatus.label.limit2")+" "+library.getString("label.qty"));
	pw.addCellKeyBold("label.period");
	pw.addCellKeyBold("useapprovalstatus.label.processdesc");
	pw.addCellKeyBold("label.orderqty");
	pw.addCellKeyBold("useapprovalstatus.label.packaging");
	pw.addCellKeyBold("useapprovalstatus.label.estimatedcovereage");
	pw.addCellKeyBold("label.orderqtytype");
	pw.addCellKeyBold("useapprovalstatus.label.customershiptocode");
	pw.addCellKeyBold("useapprovalstatus.label.tcmisdock");
	pw.addCellKeyBold("useapprovalstatus.label.customerdeliverto");
	pw.addCellKeyBold("useapprovalstatus.label.tcmisdeliverto");
	pw.addCellKeyBold("useapprovalstatus.label.requestor");
	

//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
		pw.addRow();

	UseApprovalStatusViewBean useApprovalStatusViewBean = (
		UseApprovalStatusViewBean) i11.next(); ;
	pw.addCell(useApprovalStatusViewBean.getCatalogId());
	pw.addCell(useApprovalStatusViewBean.getFacPartNo());
	pw.addCell(useApprovalStatusViewBean.getPartDescription());
	pw.addCell(useApprovalStatusViewBean.getApprovalStatus());
	pw.addCell(useApprovalStatusViewBean.getLimitQuantityPeriod1());
	
	if (useApprovalStatusViewBean.getDaysPeriod1() !=null) 
		{pw.addCell(useApprovalStatusViewBean.getDaysPeriod1()+" days");}
	else
		pw.addTdEmpty();
	pw.addCell(useApprovalStatusViewBean.getLimitQuantityPeriod2());
	
	if (useApprovalStatusViewBean.getDaysPeriod2() !=null) 
		{pw.addCell(useApprovalStatusViewBean.getDaysPeriod2()+" days");}
	else
		pw.addTdEmpty();
	pw.addCell(useApprovalStatusViewBean.getProcessDesc());
	if (useApprovalStatusViewBean.getMwApprovalId() !=null) 
		{pw.addCell("Yes");}
	else
		pw.addTdEmpty();
	pw.addCell(useApprovalStatusViewBean.getMwLimitQuantityPeriod1());
	if (useApprovalStatusViewBean.getMwDaysPeriod1() !=null) 
		{pw.addCell(useApprovalStatusViewBean.getMwDaysPeriod1()+" days");}
	else
		pw.addTdEmpty();
	pw.addCell(useApprovalStatusViewBean.getMwLimitQuantityPeriod2());
	if (useApprovalStatusViewBean.getMwDaysPeriod2() !=null) 
		{pw.addCell(useApprovalStatusViewBean.getMwDaysPeriod2()+" days");}
	else
		pw.addTdEmpty();
	pw.addCell(useApprovalStatusViewBean.getMwProcessDesc());
	pw.addCell(useApprovalStatusViewBean.getMwOrderQuantity());
	pw.addCell(useApprovalStatusViewBean.getPackaging());
	pw.addCell(useApprovalStatusViewBean.getMwEstimateOrderQuantityPrd());
	pw.addCell(useApprovalStatusViewBean.getMwOrderQuantityRule());
	pw.addCell(useApprovalStatusViewBean.getCustomerDeliverTo());
	pw.addCell(useApprovalStatusViewBean.getDockLocationId());
	pw.addCell(useApprovalStatusViewBean.getDeliveryPointBarcode());
	pw.addCell(useApprovalStatusViewBean.getDockDeliveryPoint());
	pw.addCell(useApprovalStatusViewBean.getBarcodeRequesterName());
	
 }
	return pw;
 }
}