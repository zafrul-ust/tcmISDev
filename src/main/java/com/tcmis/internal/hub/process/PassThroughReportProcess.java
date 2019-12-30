package com.tcmis.internal.hub.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Locale;
import java.math.BigDecimal;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SortCriterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.PassThroughReportInputBean;
import com.tcmis.internal.hub.beans.IgBilledIssuesViewBean;
import com.tcmis.internal.hub.factory.IgBilledIssuesViewBeanFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.util.ExcelHandler;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class PassThroughReportProcess
	extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PassThroughReportProcess(String client) {
	super(client);
  }
  
  public PassThroughReportProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getSearchResult(PassThroughReportInputBean bean,Collection hubInventoryGroupOvBeanColl) throws
	  BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	IgBilledIssuesViewBeanFactory factory =
		new IgBilledIssuesViewBeanFactory(dbManager);
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
    
	 if (bean.getDateDelivered() != null) {
//	 if (bean.getDateDelivered() != null && bean.getDateDelivered().length() == 10) {
	  criteria.addCriterion("dateDelivered",
			                SearchCriterion.FROM_DATE,
							bean.getDateDelivered());
	 }
	 
	 criteria.addCriterion("billingMethod", SearchCriterion.EQUALS,bean.getBillingMethod());

	 return factory.select(criteria,bean,iscollection);
  }

  public ExcelHandler createExcelFile(Collection searchCollection, Locale locale) throws
	  BaseException, Exception {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  ExcelHandler pw = new ExcelHandler(library);
    pw.addTable();
    pw.addRow();
    String[] headerkeys =       {"label.facility","label.workarea","label.partnumber","label.description","label.itemid",
	  "label.qty","label.pkg","label.unitprice","label.extprice","label.mrnumber",
	  "label.customerpo","label.releaseno","passthroughreport.label.usedate","monthlyinventorydetail.label.invoicedate"};

    int[] widths = {18,16,16,0,9,
                    9,35,11,10,9,
                    14,10,0,0};
    int [] types = {0,0,0, ExcelHandler.TYPE_PARAGRAPH,0,
                    0,0,0,0,0,
                    0,0, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR};
     int [] aligns = {0,0,0,0,0,
                    0,0,0,0,0,
                    0,0,0,0};
    pw.applyColumnHeader(headerkeys, types, widths, aligns);


	pw.addRow();

	
	//print rows
	Iterator i11 = searchCollection.iterator();
	while (i11.hasNext()) {
	  pw.addRow();
	  IgBilledIssuesViewBean igBilledIssuesViewBean = (IgBilledIssuesViewBean) i11.next(); ;

	  pw.addCell(igBilledIssuesViewBean.getFacilityId());
	  pw.addCell(igBilledIssuesViewBean.getApplication());
	  pw.addCell(igBilledIssuesViewBean.getFacPartNo());
		pw.addCell(igBilledIssuesViewBean.getPartDescription());
	  pw.addCell(igBilledIssuesViewBean.getItemId());
	  
//	  pw.addCell(com.tcmis.common.util.StringHandler.emptyIfZero(igBilledIssuesViewBean.getQuantity().setScale(2, 3)));
	  pw.addCell(igBilledIssuesViewBean.getQuantity());
  	pw.addCell(igBilledIssuesViewBean.getPackaging());
  	
//  	pw.addCell(com.tcmis.common.util.StringHandler.emptyIfZero(igBilledIssuesViewBean.getUnitPrice().setScale(2, 3)));
 	pw.addCell(igBilledIssuesViewBean.getUnitPrice());
//  	pw.addCell(com.tcmis.common.util.StringHandler.emptyIfZero(igBilledIssuesViewBean.getExtPrice().setScale(2, 3)));
  	pw.addCell(igBilledIssuesViewBean.getExtPrice());
  	pw.addCell(igBilledIssuesViewBean.getPrNumber() + "-" + igBilledIssuesViewBean.getLineItem());
		pw.addCell(igBilledIssuesViewBean.getCustomerPo());
		pw.addCell(igBilledIssuesViewBean.getReleaseNo());
		pw.addCell( igBilledIssuesViewBean.getDateDelivered());
		pw.addCell(igBilledIssuesViewBean.getInvoiceDate());
	 
	}
	return pw;
  }
 }
