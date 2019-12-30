package com.tcmis.internal.accountspayable.process;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.PersonnelUserGroupViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.accountspayable.beans.VoucherReportInputBean;
import com.tcmis.internal.accountspayable.beans.VoucherReportViewBean;
import com.tcmis.internal.accountspayable.factory.VoucherReportViewBeanFactory;
import com.tcmis.internal.accountspayable.factory.VvVoucherStatusBeanFactory;

/******************************************************************************
 * Process for dbuy misatch
 * @version 1.0
 *****************************************************************************/
public class VoucherReportProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  private final long MILLISECONDS_PER_DAY = 86400000;

  public VoucherReportProcess(String client) {
    super(client);
  }
  
  public VoucherReportProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getAPPersonnel() throws BaseException {
     Collection personnelBeans = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     PersonnelUserGroupViewBeanFactory pugvFactory = new PersonnelUserGroupViewBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("userGroupId",SearchCriterion.EQUALS,"Acountspayable");
        personnelBeans = pugvFactory.selectDistinctIdName(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getAPPersonnel: " + be2);
     } finally {
        dbManager = null;
        pugvFactory = null;
     }
     return personnelBeans;
  }

  public Collection getVoucherStatuses() throws BaseException {
     Collection statusBeans = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     VvVoucherStatusBeanFactory vvvsFactory = new VvVoucherStatusBeanFactory(dbManager);
     try {
        statusBeans = vvvsFactory.selectOrdered();
     } catch (BaseException be2) {
        log.error("Base Exception in getVoucherStatuses: " + be2);
     } finally {
        dbManager = null;
        vvvsFactory = null;
     }
     return statusBeans;
  }

  public Collection getSupplierInvoices(VoucherReportInputBean inputBean) throws BaseException {
     Collection invoiceBeans = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     VoucherReportViewBeanFactory voucherReportFactory = new VoucherReportViewBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        if (inputBean.getOpsEntityId() != null && inputBean.getOpsEntityId().trim().length() > 0 ) {
            searchCriteria.addCriterion("opsEntityId",SearchCriterion.EQUALS,inputBean.getOpsEntityId());
        }
        
        if (inputBean.getBranchPlant() != null && inputBean.getBranchPlant().trim().length() > 0 ) {
            searchCriteria.addCriterion("branchPlant",SearchCriterion.EQUALS,inputBean.getBranchPlant());
        }
        
        if (inputBean.getInventoryGroup() != null && inputBean.getInventoryGroup().trim().length() > 0 ) {
            searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,inputBean.getInventoryGroup());
        }

        if (inputBean.getSupplierInvoiceId() != null && inputBean.getSupplierInvoiceId().trim().length() > 0 ) {
           searchCriteria.addCriterion("supplierInvoiceId",SearchCriterion.EQUALS,inputBean.getSupplierInvoiceId());
        }

        if (inputBean.getStatus() != null && !inputBean.getStatus().equalsIgnoreCase("All")) {
           searchCriteria.addCriterion("status",SearchCriterion.EQUALS,inputBean.getStatus());
        }
        
        if (inputBean.getSupplier() != null && inputBean.getSupplier().trim().length() > 0 ) {
            searchCriteria.addCriterion("supplier",SearchCriterion.EQUALS,inputBean.getSupplier().trim(),SearchCriterion.IGNORE_CASE);
        }
        else if (inputBean.getSupplierName() != null && inputBean.getSupplierName().trim().length() > 0 ) {
           searchCriteria.addCriterion("supplierName",SearchCriterion.LIKE,inputBean.getSupplierName().trim(),SearchCriterion.IGNORE_CASE);
        }

        if (inputBean.getVoucherAge() != null && inputBean.getVoucherAge().intValue() > 0) {
           Date now = new Date();
           Date then = new Date( now.getTime() - (inputBean.getVoucherAge().intValue() * MILLISECONDS_PER_DAY) );
           searchCriteria.addCriterion("invoiceDate",SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatOracleDate(then));
        }

        if (inputBean.getBuyerId() != null) {
           searchCriteria.addCriterion("buyerId",SearchCriterion.EQUALS,inputBean.getBuyerId().toString());
        }

        if (inputBean.getShowOnlyWithReceipts() != null && inputBean.getShowOnlyWithReceipts().equalsIgnoreCase("Yes")) {
           searchCriteria.addCriterion("availableReceipts",SearchCriterion.EQUALS,"Y");           
        }
        
        if (inputBean.getShowOnlyToBeQced() != null && inputBean.getShowOnlyToBeQced().equalsIgnoreCase("Yes")) {
           searchCriteria.addCriterion("qcDate",SearchCriterion.IS,null);                      
           
           if (inputBean.getApprovedDateBegin() != null) {
              searchCriteria.addCriterion("approvedDate",SearchCriterion.GREATER_THAN,DateHandler.formatOracleDate(inputBean.getApprovedDateBegin()));
           }
           if (inputBean.getApprovedDateEnd() != null) {
              Date endOfDay = new Date( inputBean.getApprovedDateEnd().getTime() + MILLISECONDS_PER_DAY );              
              searchCriteria.addCriterion("approvedDate",SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatOracleDate(endOfDay));
           }           
        }        
        
     	SortCriteria sortCriteria = null;
        if (inputBean.getSortBy() != null) {
           sortCriteria =new SortCriteria();
	   sortCriteria.addCriterion(inputBean.getSortBy());
        }

        invoiceBeans = voucherReportFactory.select(searchCriteria,sortCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getSupplierInvoices: " + be2);
     } finally {
        dbManager = null;
        voucherReportFactory = null;
     }

     return invoiceBeans;
  }
      public ExcelHandler getExcelReport(Collection reportCollection, Locale locale) throws
            BaseException, Exception {
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        Iterator iterator = reportCollection.iterator();
        ExcelHandler pw = new ExcelHandler(library);
        pw.addTable();

       String[] headerkeys = { "label.po","label.buyer","label.email","label.phone","label.supplier",
                "label.invoice","label.invoicedate","label.supplierterms","label.amount","label.status",
                "label.approvedby","label.ApprovedDate","label.qcedby","label.qcdate","label.availablereceipts",
                "label.apuser","label.comments"
        };
        int[] widths = {0,20,21,12,22,
                        0,0,0,10,10,
                        25,0,9,0,10,
                        25,0} ;

        int [] types = {0,0,0,0,0,
                        0,pw.TYPE_CALENDAR,0,0,0,
                        0,pw.TYPE_DATE,0,pw.TYPE_DATE,0,
                        0, ExcelHandler.TYPE_PARAGRAPH} ;

        int[] aligns = {0,0,0,0,0,
                        0,0,0,0,0,
                        0,0,0,0,pw.ALIGN_CENTER,
                        0,0} ;

        pw.applyColumnHeader(headerkeys, types, widths, aligns);
        while (iterator.hasNext()) {
            VoucherReportViewBean voucherBean = (VoucherReportViewBean) iterator.next();
            pw.addRow();
            pw.addCell(voucherBean.getRadianPo());
            pw.addCell(voucherBean.getBuyerName());
            pw.addCell(voucherBean.getBuyerEmail());
            pw.addCell(voucherBean.getBuyerPhone());
            pw.addCell(voucherBean.getSupplierName());
            pw.addCell(voucherBean.getSupplierInvoiceId());
            pw.addCell(voucherBean.getInvoiceDate());
            pw.addCell(voucherBean.getSupplierTerms());
            pw.addCell(voucherBean.getNetInvoiceAmount());
            pw.addCell(voucherBean.getStatus());
            pw.addCell(voucherBean.getApApproverName());
            pw.addCell(voucherBean.getApprovedDate());
            pw.addCell(voucherBean.getQcUser());
            pw.addCell(voucherBean.getQcDate());
            pw.addCell(voucherBean.getAvailableReceipts());
            pw.addCell(voucherBean.getApUserName());
            pw.addCell(voucherBean.getApNote());

        }
        return pw;
    }
}
