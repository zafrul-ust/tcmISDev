package com.tcmis.supplier.invoice.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.admin.beans.UserGroupMemberSupplierBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.supplier.invoice.beans.*;
import com.tcmis.supplier.invoice.factory.*;
import com.tcmis.common.util.ExcelHandler;
/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class VendorPaymentProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public VendorPaymentProcess(String client) {
    super(client);
  }

  public Collection getSearchData(PersonnelBean personnelBean, SupplierPaymentInputBean inputBean) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    SupplierPaymentHeaderViewBeanFactory factory = new
        SupplierPaymentHeaderViewBeanFactory(dbManager);
    Collection resultCollection = null;
    SearchCriteria criteria = new SearchCriteria();
//criteria.addCriterion("vendorCode", SearchCriterion.EQUALS, "3MAERTCM001");
    Collection c = personnelBean.getPermissionBean().getUserGroupMemberSupplierBeanCollection();
    if(c != null && c.size() > 0) {
      Iterator iterator = c.iterator();
      int i = 0;
      while(iterator.hasNext()) {
        UserGroupMemberSupplierBean userGroupMemberSupplierBean = (UserGroupMemberSupplierBean)iterator.next();
        if("vendorpayment".equalsIgnoreCase(userGroupMemberSupplierBean.getUserGroupId())) {
          if (i == 0) {
            criteria.addCriterion("supplier", SearchCriterion.EQUALS,
                                  userGroupMemberSupplierBean.getSupplier());
          }
          else {
            criteria.addValueToCriterion("supplier",
                                         userGroupMemberSupplierBean.
                                         getSupplier());
          }
          i++;
        }
      }
      //since there might be data in usergroupmembersupplier but no vendorpayment records I have to do this
      if(i > 0) {
        if (!StringHandler.isBlankString(inputBean.getPaymentNum())) {
          criteria.addCriterion("paymentNum", SearchCriterion.EQUALS,
                                inputBean.getPaymentNum());
        }
        if (!StringHandler.isBlankString(inputBean.getCheckNum())) {
          criteria.addCriterion("checkNum", SearchCriterion.EQUALS,
                                inputBean.getCheckNum());
        }
        if (inputBean.getCheckDateFrom() != null) {
          criteria.addCriterion("checkDate",
                                SearchCriterion.GREATER_THAN_OR_EQUAL_TO,
                                DateHandler.formatOracleDate(inputBean.
              getCheckDateFrom()));
        }
        if (inputBean.getCheckDateTo() != null) {
          criteria.addCriterion("checkDate",
                                SearchCriterion.LESS_THAN_OR_EQUAL_TO,
                                DateHandler.formatOracleDate(inputBean.
              getCheckDateTo()));
        }
        resultCollection = factory.select(criteria, new SortCriteria());
      }
    }
    return resultCollection;
  }

  public Collection getDetailData(PersonnelBean personnelBean, String paymentNumber) throws BaseException, Exception {
    Collection resultCollection = null;
    SearchCriteria criteria = new SearchCriteria();
    DbManager dbManager = new DbManager(getClient());
    SupplierPaymentHeaderViewBeanFactory factory = new SupplierPaymentHeaderViewBeanFactory(dbManager);
    Collection c = personnelBean.getPermissionBean().getUserGroupMemberSupplierBeanCollection();
    if(c != null && c.size() > 0) {
      Iterator iterator = c.iterator();
      int i = 0;
      while(iterator.hasNext()) {
        UserGroupMemberSupplierBean userGroupMemberSupplierBean = (UserGroupMemberSupplierBean)iterator.next();
        if("vendorpayment".equalsIgnoreCase(userGroupMemberSupplierBean.getUserGroupId())) {
          if (i == 0) {
            criteria.addCriterion("supplier", SearchCriterion.EQUALS,
                                  userGroupMemberSupplierBean.getSupplier());
          }
          else {
            criteria.addValueToCriterion("supplier",
                                         userGroupMemberSupplierBean.
                                         getSupplier());
          }
          i++;
        }
      }
      //since there might be data in usergroupmembersupplier but no vendorpayment records I have to do this
      if(i > 0) {
        criteria.addCriterion("paymentNum", 
                              SearchCriterion.EQUALS,
                              paymentNumber);
        resultCollection = factory.selectDetail(criteria, new SortCriteria());
      }
    }
    return resultCollection;
  }

  public ExcelHandler  getExcelReportHeader(PersonnelBean personnelBean, SupplierPaymentInputBean inputBean, Locale locale) throws
      NoDataException, BaseException, Exception {
    /*if(log.isDebugEnabled()) {
      log.debug("locale:" + locale);
      log.debug("country:" + locale.getCountry());
      log.debug("language:" + locale.getLanguage());
    }*/
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

    Collection data = this.getSearchData(personnelBean, inputBean);
//log.debug("data:" + data);
    Iterator iterator = data.iterator();
    ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  pw.addRow();
    pw.addCellKeyBold("label.searchcriteria");
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.paymentnumber");
    pw.addCellBold(inputBean.getPaymentNum());
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.checknumber");
    pw.addCellBold(inputBean.getPaymentNum());
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.checkdate");
    String dateStr="";
    if(inputBean.getCheckDateFrom() != null) {
        dateStr = DateHandler.formatDate(inputBean.getCheckDateFrom(), "MM/dd/yyyy", locale);
    }
    pw.addCellBold(library.getString("label.from")+":"+dateStr);
    dateStr = "";
    if(inputBean.getCheckDateTo() != null) {
        dateStr = DateHandler.formatDate(inputBean.getCheckDateTo(), "MM/dd/yyyy", locale);
    }
    pw.addCellBold(library.getString("label.to")+":"+dateStr);

    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    

    //write column headers
    pw.addRow();
    pw.addCellKeyBold("label.company");
    pw.addCellKeyBold("label.paymentnumber");
    pw.addCellKeyBold("label.checknumber");
    pw.addCellKeyBold("label.date");
    pw.addCellKeyBold("label.paymentmethod");
    pw.addCellKeyBold("label.paymentamount");
    pw.addCellKeyBold("label.comment");
    pw.addCellKeyBold("label.contactname");
    pw.addCellKeyBold("label.contactphone");
    pw.addCellKeyBold("label.contactemail");
    
    //now write data
    while(iterator.hasNext()) {
      SupplierPaymentHeaderViewBean bean = (SupplierPaymentHeaderViewBean)iterator.next();

      pw.addRow();

      pw.addCell(bean.getCompany());
      pw.addCell(bean.getPaymentNum());
      pw.addCell(bean.getCheckNum());
      dateStr = "";
      if(bean.getCheckDate() != null) {
          dateStr = DateHandler.formatDate(bean.getCheckDate(), "MM/dd/yyyy", locale);
      }
      pw.addCell(dateStr);
      pw.addCell(bean.getPaymentCode());
      pw.addCell(bean.getAmtNet());
      pw.addCell(bean.getDocDesc());
      pw.addCell(bean.getApContactName());
      pw.addCell(bean.getApContactPhone());
      pw.addCell(bean.getApContactEmail());

      
    }
    return pw;
  }

  public ExcelHandler getExcelReportDetail(PersonnelBean personnelBean, SupplierPaymentInputBean inputBean, Locale locale) throws
      NoDataException, BaseException, Exception {
    /*if(log.isDebugEnabled()) {
      log.debug("locale:" + locale);
      log.debug("country:" + locale.getCountry());
      log.debug("language:" + locale.getLanguage());
    }*/
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

    Collection data = this.getDetailData(personnelBean, inputBean.getPaymentNum());
//log.debug("data:" + data);
    Iterator iterator = data.iterator();
    ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  
    while(iterator.hasNext()) {
      SupplierPaymentHeaderViewBean bean = (SupplierPaymentHeaderViewBean)
          iterator.next();
      pw.addRow();
      pw.addCellKeyBold("label.vendorname");
      pw.addCellBold(bean.getPayeeName());
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      
    pw.addRow();
    pw.addCellKeyBold("label.paymentnumber");
    pw.addCellBold(bean.getPaymentNum());
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.paymentdate");
    
    String dateStr="";
    if(bean.getCheckDate() != null) {
      dateStr = DateHandler.formatDate(bean.getCheckDate(), "MM/dd/yyyy", locale);
    }
    pw.addCellBold(dateStr);
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.paymentamount");
    pw.addCellBold(bean.getAmtNet());
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
    pw.addRow();
    pw.addCellKeyBold("label.comment");
    pw.addCellBold(bean.getDocDesc());
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    pw.addTdEmpty();
    
      pw.addRow();
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      pw.addTdEmpty();
      
      //write column headers
      pw.addRow();
      pw.addCellKeyBold("label.invoice");
      pw.addCellKeyBold("label.invoicedate");
      pw.addCellKeyBold("label.invoiceamount");
      pw.addCellKeyBold("label.discountamount");
      pw.addCellKeyBold("label.amountpaid");
      pw.addCellKeyBold("label.comment");
      
      //now write data
      Iterator it = bean.getSupplierPaymentDetailViewBeanCollection().iterator();
      while (it.hasNext()) {
        SupplierPaymentDetailViewBean detailBean = (SupplierPaymentDetailViewBean)
            it.next();
        pw.addRow();
        pw.addCell(detailBean.getInvoiceNum());
        dateStr = "";
        if(detailBean.getInvoiceDate() != null) {
            dateStr = DateHandler.formatDate(detailBean.getInvoiceDate(), "MM/dd/yyyy", locale);
          }
        pw.addCell(dateStr);
        pw.addCell(detailBean.getInvoiceAmt());
        pw.addCell(detailBean.getAmtDiscTaken());
        pw.addCell(detailBean.getAmtApplied());
        pw.addCell(detailBean.getLineDesc());
      }
    }
    return pw;
  }
}