package com.tcmis.internal.supply.process;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.supply.beans.POExpeditingInputBean;
import com.tcmis.internal.supply.beans.PoExpediteViewBean;
import com.tcmis.internal.supply.beans.PoLineExpediteDateBean;
import com.tcmis.internal.supply.factory.PoExpediteViewBeanFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

/**
 * ******************************************************************
 * Process for the PoExpediting Section
 * 
 * @version 1.0
 * *****************************************************************         
 */

/**
* Change History ----- 03/13/09 - Shahzad Butt - Modifed getSearchResult() and 
* 								  getExcelReport() methods
*                      04/14/09 - Shahzad Butt - Changed  update method to add person id
*                      of the person updating the record and also added condition to check
*                      if ok is not zero. Also added new column to the spreadsheet and renamed
*                      last revised date to last updated date and moved the comment column
*                      and it's values before  last updated date. 
*/
public class PoExpeditingProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PoExpeditingProcess(String client, String locale) {
		super(client, locale);
	}

	public PoExpeditingProcess(String client) {
		super(client);
	}

	public Collection<PoExpediteViewBean> getSearchResult(
			POExpeditingInputBean bean,PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PoExpediteViewBeanFactory factory = new PoExpediteViewBeanFactory(
				dbManager);

		SearchCriteria criteria = new SearchCriteria();
		if (bean.getHubIdArray() != null && bean.getHubIdArray().length > 0) {
			boolean initFlag = true;
			for (int i = 0; i < bean.getHubIdArray().length; i++) {
				if (bean.getHubIdArray()[i].length() > 0) {
					if (initFlag) {
						if (!bean.getHubIdArray()[i].toString().equalsIgnoreCase("All"))
							criteria.addCriterion("branchPlant",SearchCriterion.EQUALS,
							 bean.getHubIdArray()[i].toString());
					} else {
						criteria.addValueToCriterion("branchPlant", bean
								.getHubIdArray()[i].toString());
					}
					initFlag = false;
				}
			}
		}
		if ("Y".equals(bean.getCreditHold())) {
			criteria.addCriterion("creditHold", SearchCriterion.EQUALS, "Y");
		}
		if (!StringHandler.isBlankString(bean.getBuyerId())) {
			criteria.addCriterion("buyerId", SearchCriterion.EQUALS, bean.getBuyerId());
		}
		if (!StringHandler.isBlankString(bean.getSupplier())) {
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean.getSupplier());
		}
		if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("All")) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
					bean.getInventoryGroup());
		}
        else
        {
    	String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
//    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
//    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
    	criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
        }

        /* Searching for FROM date */
		if (bean.getShipFromDate() != null) {
			criteria.addCriterion("vendorShipDate", SearchCriterion.FROM_DATE,
					bean.getShipFromDate());
		}
		/* Searching for TO date */
		if (bean.getShipToDate() != null) {
			criteria.addCriterion("vendorShipDate", SearchCriterion.TO_DATE, bean
					.getShipToDate());
		}

		/* Searching by age greater than */
		if (!StringHandler.isBlankString(bean.getExpediteAge())) {
			criteria.addCriterion("expediteAge", SearchCriterion.GREATER_THAN, bean
					.getExpediteAge());
		}
		/* Searching for order FROM date */
		if (bean.getOrderFromDate() != null) {
			criteria.addCriterion("orderDate", SearchCriterion.FROM_DATE,
					bean.getOrderFromDate());
		}
		/* Searching for order TO date */
		if (bean.getOrderToDate() != null) {
			criteria.addCriterion("orderDate", SearchCriterion.TO_DATE, bean
					.getOrderToDate());
		}	
		
		/* Searching for order FROM date */
		if (bean.getRevisedPromisedFromDate() != null) {
			criteria.addCriterion("revisedPromisedDate", SearchCriterion.FROM_DATE,
					bean.getRevisedPromisedFromDate());
		}
		/* Searching for order TO date */
		if (bean.getRevisedPromisedToDate() != null) {
			criteria.addCriterion("revisedPromisedDate", SearchCriterion.TO_DATE, bean.getRevisedPromisedToDate());
		}
			
		/* Searching by Supply path  */
		if ((bean.getSupplyPath() != null) && (!"ALL".equalsIgnoreCase(bean.getSupplyPath()))) {
			criteria.addCriterion("supplyPath", SearchCriterion.EQUALS, bean.getSupplyPath());
		}

		String s = null;
		s = bean.getSearchArgument();
		if (s != null && !s.equals("")) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if (mode.equals("is"))
				criteria.addCriterion(field, SearchCriterion.EQUALS, s);
			if (mode.equals("like"))
				criteria.addCriterion(field, SearchCriterion.LIKE, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("startsWith"))
				criteria.addCriterion(field, SearchCriterion.STARTS_WITH, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("endsWith"))
				criteria.addCriterion(field, SearchCriterion.ENDS_WITH, s,
						SearchCriterion.IGNORE_CASE);
		}
		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion(bean.getSortBy());
		Collection<PoExpediteViewBean> c = factory.select(criteria, scriteria);
		return c;
	}

	public ExcelHandler getExcelReport(POExpeditingInputBean bean,PersonnelBean personnelBean, Locale locale)
			throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<PoExpediteViewBean> data = getSearchResult(bean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();
		
		String[] headerkeys = 
				  {"label.haaspoline",  
				  "label.supplier","label.partnumber",
				  "label.itemid","label.itemdescription","label.buyer", 
		          "label.supplypath","label.dateconfirmed",
		          "label.needdate","label.quantityopen","label.qtyalloctomrs",
		          "label.qtyreceived", "label.unitprice", 
		          "label.inventorygroup","label.promised.ship.date","label.oriprojecteddelivery","label.revprojecteddelivery",
		          "label.comments","label.credithold","label.laastUpdatedDate","label.lastUpdatedBy","label.expediteage","label.daysLate",
		          "label.company","label.customerpo","label.shipto","label.carrier","label.hub","label.supplierdateaccepted"
		          };
		int[] widths = {16, 13, 15, 15, 0, 12, 0,  12, 18, 0, 16, 10, 0, 0,14, 14,16, 18, 0,18, 16, 16,16, 12,14,26,14,12,12  };
		int[] types =
		        { 
				pw.TYPE_NUMBER,
				pw.TYPE_STRING, pw.TYPE_STRING,
				pw.TYPE_NUMBER,ExcelHandler.TYPE_PARAGRAPH, pw.TYPE_STRING, 
				pw.TYPE_STRING, pw.TYPE_CALENDAR, 
				pw.TYPE_CALENDAR, pw.TYPE_NUMBER,
				pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_STRING,
				pw.TYPE_STRING, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR,pw.TYPE_CALENDAR,
				ExcelHandler.TYPE_PARAGRAPH, pw.TYPE_STRING,pw.TYPE_CALENDAR,pw.TYPE_NUMBER, pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,
				pw.TYPE_STRING,ExcelHandler.TYPE_PARAGRAPH,	pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_CALENDAR
				};	
		
		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0,0,0,0, 0,0, 0,0,0,0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

	//TO_DO change member.getExpediteAge to getlastupdatedBy.
		pw.setColumnDigit(12, 2);
		
		for (PoExpediteViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getRadianPo() + "-" + member.getPoLine());
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getPartNumber());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getBuyerName());		
			pw.addCell(member.getSupplyPath());			
			pw.addCell(member.getOrderDate());
			pw.addCell(member.getNeedDate());
			pw.addCell(member.getQuantityOpen());
			pw.addCell(member.getMrAlloc());
			pw.addCell(member.getQtyReceived());
			pw.addCell(member.getCurrencyId()+" "+member.getUnitPrice());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getVendorShipDate());
			pw.addCell(member.getPromisedDate());
			pw.addCell(member.getRevisedPromisedDate());
			pw.addCell(member.getExpediteComments());
            if (member.getCreditHold() == null)
            {
               pw.addCell("");
            }
            else
            {
			pw.addCell(member.getCreditHold());
            }
			pw.addCell(member.getLastRevised());
			pw.addCell(member.getLastRevisedBy());
			pw.addCell(member.getExpediteAge());
            if (member.getDaysLate() != null && member.getDaysLate().intValue() <0)
            {
                pw.addCell("");
            }
            else
            {
                pw.addCell(member.getDaysLate());
            }
            pw.addCell(member.getCompanyId());
			pw.addCell(member.getCustomerPo());
			pw.addCell(member.getShipToAddress());
			pw.addCell(member.getCarrier());
			pw.addCell(member.getHubName());
			pw.addCell(member.getSupplierDateAccepted());
			
		}
		return pw;

	}

	public Collection getBuyerDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		return factory.selectBuyOrdersBuyer();
	}

	public Collection update(Collection<PoLineExpediteDateBean> beans,
			PersonnelBean personnelBean) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
				
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		for (PoLineExpediteDateBean bean : beans) {
			if ( "true".equals(bean.getOk()) ) {
				
				/*
				 * Check to see if the user has the permissions to do the
				 * update.
				 */
				if (!permissionBean.hasInventoryGroupPermission("BuyOrder",
						bean.getInventoryGroup(), null, null))
					continue;
				try {
					if(	bean.getComments().trim().length()<=2000)
					{								
					 String creditHold = "";
					 if( "true".equals(bean.getCreditHold()) )
						 creditHold = "Y";
					 inArgs = new Vector(5);
			         inArgs.add(bean.getRadianPo());
			         inArgs.add(bean.getPoLine());
			         inArgs.add(new Timestamp(bean.getRevisedPromisedDate().getTime()));
			         inArgs.add(bean.getComments());
			         inArgs.add(personnelId);
			         inArgs.add(creditHold);
			         outArgs = new Vector(1);
			         outArgs.add(new Integer(java.sql.Types.VARCHAR));
                     if(log.isDebugEnabled()) {
            		     log.debug("P_PO_LINE_EXPEDITE_NOTES:" + inArgs);
		             }
                     Vector error = (Vector) factory.doProcedure("P_PO_LINE_EXPEDITE_NOTES ", inArgs, outArgs);
                     
                     if(error.size()>0 && error.get(0) != null)
                     {
                    	 errorCode = (String) error.get(0);
                    	 log.info("Error in Procedure P_PO_LINE_EXPEDITE_NOTES: "+bean.getRadianPo()+"-"+bean.getPoLine()+" Error Code "+errorCode+" ");
                    	 errorMessages.add(errorCode);
                    	 
                     }
					}
					else
					{
						errorMsg = "Error updating PO: Comment should be less than 2000 characters. ";
						errorMessages.add(errorMsg);
					}
				} catch (Exception e) {
					errorMsg = "Error updating PO: "+ bean.getRadianPo()+ "-" + bean.getPoLine() + "";
					errorMessages.add(errorMsg);
				}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size()>0?errorMessages:null);
	}
	
	
	public Collection<PoExpediteViewBean> getNewPoExpediteSearchResult(
			POExpeditingInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PoExpediteViewBeanFactory factory = new PoExpediteViewBeanFactory(
				dbManager);

		SearchCriteria criteria = new SearchCriteria();

		if (null!=bean.getRadianPo()) {
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, bean.getRadianPo().toString());
		}
		if (null!=bean.getPoLine()) {
			criteria.addCriterion("poLine", SearchCriterion.EQUALS, bean.getPoLine().toString());
		}
				
		Collection<PoExpediteViewBean> c = factory.select(criteria, null);
		return c;
	}
	
	
	public Collection uploadFile(FileUploadBean inputBean,
			PersonnelBean personnelBean) throws BaseException,IOException {
		DbManager dbManager = null;
		Connection conn = null;
		GenericProcedureFactory procFactory = null;
		GenericSqlFactory sqlFactory = null;

		try{
			if (inputBean.getTheFile() != null) {
					if (log.isDebugEnabled()) {
						log.debug(inputBean.getTheFile().getName());
					}
				Vector errorMessages = new Vector();
				Collection inArgs = null;
				Collection outArgs = null;
				String errorMsg = "";
				String errorCode = null;
				dbManager = new DbManager(this.getClient(), getLocale());
				conn = dbManager.getConnection();
				procFactory = new GenericProcedureFactory(dbManager);
				sqlFactory = new GenericSqlFactory(dbManager);
				BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
				
				Vector<Vector<String>> sv = ExcelHandlerXlsx.read(inputBean.getTheFile().getCanonicalPath());
				int row = 0;
						
				PermissionBean permissionBean = personnelBean.getPermissionBean();
				for(Vector<String> v : sv) {
					row++;
					if( row == 1 ) continue;// line 1 is header.
					if( v == null  )  continue;
						/*
						 * Check to see if the user has the permissions to do the
						 * update.
						 */
						String radianPo = v.get(0);
						String comments = v.get(4);
						String poLine = v.get(1);
						if (!permissionBean.hasInventoryGroupPermission("BuyOrder",
								sqlFactory.selectSingleValue(new StringBuilder("select inventory_group from po where radian_po = ").append(radianPo).toString(), conn), null, null))
						{
							errorMsg = "You do not have permissions to update PO "+radianPo+"-"+poLine;
							errorMessages.add(errorMsg);
							continue;
						}
						try {
							if( comments.trim().length()<=2000)
							{								
							 String creditHold = "N";
							 if( "Y".equalsIgnoreCase((v.get(3))))
								 creditHold = "Y";
							 inArgs = new Vector(5);
					         inArgs.add(radianPo);
					         inArgs.add(poLine);
					         inArgs.add(ExcelHandler.getDate((int)Double.parseDouble(v.get(2))));
					         inArgs.add(comments);
					         inArgs.add(personnelId);
					         inArgs.add(creditHold);
					         outArgs = new Vector(1);
					         outArgs.add(new Integer(java.sql.Types.VARCHAR));
		                     if(log.isDebugEnabled()) {
		            		     log.debug("P_PO_LINE_EXPEDITE_NOTES:" + inArgs);
				             }
		                     Vector error = (Vector) procFactory.doProcedure(conn,"P_PO_LINE_EXPEDITE_NOTES ", inArgs, outArgs);
		                     
		                     if(error.size()>0 && error.get(0) != null)
		                     {
		                    	 errorCode = (String) error.get(0);
		                    	 log.info("Error in Procedure P_PO_LINE_EXPEDITE_NOTES: "+radianPo+"-"+poLine+" Error Code "+errorCode+" ");
		                    	 errorMessages.add(errorCode);
		                    	 
		                     }
							}
							else
							{
								errorMsg = "Error updating PO: Comment should be less than 2000 characters.";
								errorMessages.add(errorMsg);
							}
						} catch (Exception e) {
							errorMsg = "Error updating PO: "+ radianPo+ "-" + poLine + "";
							errorMessages.add(errorMsg);
						}
				}
				
				return (errorMessages.size()>0?errorMessages:null);
			}
		}
		finally
		{
			   if(dbManager != null)
				   dbManager.returnConnection(conn);		
			   	procFactory = null;
			   	sqlFactory = null;
				dbManager = null;
		}
		return null;
	}

  
  
  private static final String Zero ="0";
}