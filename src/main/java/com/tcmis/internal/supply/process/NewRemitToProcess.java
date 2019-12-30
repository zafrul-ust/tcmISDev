package com.tcmis.internal.supply.process;

//import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.NewRemitToInputBean;
import com.tcmis.internal.supply.beans.SupplierBillingLocationViewBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class NewRemitToProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	
	public NewRemitToProcess(String client) 
	{
		super(client);
	}  
	
	public NewRemitToProcess(String client,String locale) {
	    super(client,locale);
    }

	public Collection <SupplierBillingLocationViewBean> getSupplierBillingLocationViewBeanCollection(NewRemitToInputBean bean) throws BaseException, Exception 
	{
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		
		GenericSqlFactory supplierBillingLocFactory = new GenericSqlFactory(dbManager,new SupplierBillingLocationViewBean());
		
		Collection <SupplierBillingLocationViewBean> c = null;
		
		SearchCriteria searchCriteria = new SearchCriteria();
		
		String status = "Open";
		
		searchCriteria.addCriterion("status", SearchCriterion.EQUALS,status);		
			  			                          
		if(!StringHandler.isBlankString(bean.getSearchArgument()))
		{
			searchCriteria.addCriterion("supplierName", SearchCriterion.LIKE, bean.getSearchArgument(), SearchCriterion.IGNORE_CASE);
		}		
		
		c = supplierBillingLocFactory.select(searchCriteria, null, TABLE_NAME);

		return c;
	}



	public ExcelHandler getExcelReport(NewRemitToInputBean bean, Locale locale)
	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(	"com.tcmis.common.resources.CommonResources", locale);
		Collection<SupplierBillingLocationViewBean> data = getSupplierBillingLocationViewBeanCollection(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = 
		{		"label.supplier", "label.address", "label.invoicereference", 
				"label.requestor","label.requestoremail","label.requestdate"
		};
		int[] widths = {24,46,12,20,20,12};
		int[] types =
		{ 
				pw.TYPE_STRING,pw.TYPE_STRING,0,0,0,0				
		};	

		int[] aligns = { 0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (SupplierBillingLocationViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getSupplierName());
			pw.addCell(StringHandler.emptyIfNull(member.getAddressLine1())+" "+(StringHandler.isBlankString(member.getAddressLine2())?"":member.getAddressLine2())+" "+
			(StringHandler.isBlankString(member.getAddressLine3())?"":member.getAddressLine3())+" "+
			(StringHandler.emptyIfNull(member.getCity()))+" "+
			(StringHandler.isBlankString(member.getStateAbbrev() )?"":member.getStateAbbrev())+" "+
			(StringHandler.isBlankString(member.getZip())?"":member.getZip())+" "+
			(StringHandler.isBlankString(member.getCountryAbbrev())?"":member.getCountryAbbrev()));
			pw.addCell(member.getSupplierInvoiceIds());
			pw.addCell(member.getRequester());
			pw.addCell(member.getRequesterEmail());
			pw.addCell(member.getRequestDate());
				
		}
		return pw;

	}
	
	
	
	public Collection update(Collection <SupplierBillingLocationViewBean> suppolierViewBeanCollection,PersonnelBean personnelBean) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();
        String statusOrSapCode = null;
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		if((suppolierViewBeanCollection!=null) && (!suppolierViewBeanCollection.isEmpty()))
		{
			for(SupplierBillingLocationViewBean supplierBean : suppolierViewBeanCollection) {
				/*
				 * Check to see if the user has the permissions to do the
				 * update.
				 */
				if (!permissionBean.hasFacilityPermission("newRemitTo",null,null))
					continue;	
				try {
					if((!StringHandler.isBlankString(supplierBean.getOk()) && !StringHandler.isBlankString(supplierBean.getBillingLocationId())) &&
							(!StringHandler.isBlankString(supplierBean.getSupplier()) && 
							(!StringHandler.isBlankString(supplierBean.getSapVendorCode())  ||  
							!StringHandler.isBlankString(supplierBean.getStatusCol())))) {
						inArgs = new Vector(4);
						if(!StringHandler.isBlankString(supplierBean.getSapVendorCode()) && StringHandler.isBlankString(supplierBean.getStatusCol()))
						{
							statusOrSapCode = supplierBean.getSapVendorCode();
						}
						else if( !StringHandler.isBlankString(supplierBean.getStatusCol()) && StringHandler.isBlankString(supplierBean.getSapVendorCode()))
						{
							statusOrSapCode = "Y";
						}
						inArgs.add(supplierBean.getSupplier());						
						inArgs.add(supplierBean.getBillingLocationId());
						inArgs.add(statusOrSapCode);
						inArgs.add(personnelId);
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						if(log.isDebugEnabled()) {
							log.debug("New Remit To Proc:" + inArgs);
						}			   
						Vector error = (Vector) factory.doProcedure("pkg_contract_setup.p_approve_remit_to", inArgs, outArgs);
						if(error.size()>0 && error.get(0) != null)
						{
							errorCode = (String) error.get(0);
							log.info("Error in Procedure For New Remit To  Section: "+supplierBean.getSupplier()+"-"+supplierBean.getBillingLocationId()+" Error Code "+errorCode+" ");
							errorMessages.add(errorCode);
						}			     
                        else
                        {
                            sendNotificationMail(supplierBean,"Approve");
                        }
                    }
				} catch (Exception e) {
					errorMsg = library.getString("error.db.update");
					errorMessages.add(errorMsg);
				}
			}
			factory = null;
			dbManager = null;
		} 

		return errorMessages;
	}

  private void sendNotificationMail(SupplierBillingLocationViewBean bean,
                                  String action) {
    try {
      String subject = null;
      String message = null;
      if (!StringHandler.isBlankString(bean.getSupplierInvoiceIds()))
      {
       if ("Approve".equalsIgnoreCase(action)) {
        subject = "New Remit To address for supplier " + bean.getSupplierName() + ", Entered by " + bean.getRequesterName() + " - is approved";
        message = "New Remit To address for supplier " + bean.getSupplierName() + ", Entered by " + bean.getRequesterName() + " - is approved \n\n" +
                  "The below invoices can be approved now.\n\n" + bean.getSupplierInvoiceIds();
       }
       else if ("Reject".equalsIgnoreCase(action)) {
        subject = "New Remit To address for supplier " + bean.getSupplierName() + ", Entered by " + bean.getRequesterName() + " - is rejected";
        message = "New Remit To address for supplier " + bean.getSupplierName() + ", Entered by " + bean.getRequesterName() + " - is rejected \n\n" +
                  "The below invoices can not be approved.\n\n" + bean.getSupplierInvoiceIds();
       }

      String[] to = new String[1];
      //to[0] = "sscsupport.haas@iqbackoffice.com";
      to[0] = "haasap@iqbackoffice.com";
      /*String[] cc = new String[1];
      cc[0] = "nshaik@haastcm.com";*/

      MailProcess.sendEmail(to, null, subject, message, true);
     }
    }
    catch(Exception e) {
      log.error("Error sending email.", e);
    }
  }

    public Collection reject(SupplierBillingLocationViewBean bean, PersonnelBean personnelBean) throws
	BaseException, Exception {
//		Collection inArgs = null;
//		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();
        String statusOrSapCode = null;
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
        Date todayDate = new Date();
        if (!permissionBean.hasFacilityPermission("newRemitTo",null,null)) {
			errorMsg = library.getString("nopermissions.title");
			errorMessages.add(errorMsg);
			return errorMessages;	
		}
		try {
		
			String query = "Update SUPPLIER_BILLING_LOCATION_VIEW set status = 'Rejected',comments='Rejected by "+personnelBean.getLastName()+
                    ", "+personnelBean.getFirstName()+" on  "+todayDate+"' where billing_location_id = '" + bean.getBillingLocationId() + "' and supplier = '"+bean.getSupplier()+"'";
			log.info(query);
            genericSqlFactory.deleteInsertUpdate(query);
            sendNotificationMail(bean,"Reject");

        } catch (Exception e) {
			errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
		}

		return errorMessages;
	}

	
	 private static final String TABLE_NAME ="SUPPLIER_BILLING_LOCATION_VIEW";	
}




