package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;

/******************************************************************************
 * Process for logistics
 * @version 1.0
 *****************************************************************************/
public class InventoryLookupProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

  public InventoryLookupProcess(String client,Locale locale) {
    super(client,locale);
  }

  public Object[] getSearchResult(LogisticsInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LogisticsViewBean());
		
		SearchCriteria searchCriteria = new SearchCriteria();
	    searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
	    if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
	        searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	    }
	    if( !"Yes".equals(inputBean.getShowIssuedReceipts()) ) 
	    	searchCriteria.addCriterion("quantity", SearchCriterion.NOT_EQUAL, "0");

/*	    if( inputBean.getNoDaysOld() != null  ) {
	    	searchCriteria.addCriterion("receiptExpireDate", SearchCriterion.LESS_THAN, "sysdate + " +inputBean.getNoDaysOld());
//	    	searchCriteria.addCriterion("receiptExpireDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, "sysdate");
	    }   */
	    searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());

	    searchCriteria.addCriterionWithMode(inputBean.getSearchField2(), inputBean.getSearchMode2(), inputBean.getSearchArgument2());
		
		if( !isBlank(inputBean.getDorfrom()) )
			searchCriteria.addCriterion(inputBean.getSearchField3(), SearchCriterion.FROM_DATE, inputBean.getDorfrom() );
	    if( !isBlank(inputBean.getDorto()) )
	    	searchCriteria.addCriterion(inputBean.getSearchField3(), SearchCriterion.TO_DATE, inputBean.getDorto() );
	    
	    if(!isBlank(inputBean.getCountId())) {
		    String receiptQuery = " select distinct receipt_id from inventory_count where count_id = " + inputBean.getCountId();
			searchCriteria.addCriterion("receiptId", SearchCriterion.NOT_IN, receiptQuery);
	    }
		
	SortCriteria sortCriteria = new SortCriteria();
//	s = inputBean.getSortBy();
//	String[] sorts = s.split(",");
//	for(String cr:sorts) 
//		sortCriteria.addCriterion(cr.replaceAll("[|]"," "));

	Vector<LogisticsViewBean> v = (Vector<LogisticsViewBean>)factory.select(searchCriteria, sortCriteria,"LOGISTICS_VIEW");
	return getRowSpan(v);//getBins(v); 
 }

  public Object[] getRowSpan(Vector<LogisticsViewBean> bv) {
//		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		Vector<Integer> m1 = new Vector();
		Integer i1 = null;
		String partNum = null;
		String prePart = "";
		boolean flag = false; // no rowspan.
		int current = 0 ;
		int count = 0 ;
		for (LogisticsViewBean pbean:bv) {
			partNum = pbean.getReceiptId().toString();

			if( partNum.equals(prePart)) {
				Integer receiptCount = m1.get(current);
				receiptCount++;
				m1.set(current, receiptCount);
				m1.add(receiptCount);
				flag = true;
			}
			else {
				m1.add(new Integer(1));
				current = count;
			}
			count++;
			prePart = partNum;
  		}
		Object[] objs = {bv,m1,flag};
		return objs;
	}
  
    public Collection doAddReceipts(PersonnelBean personnelBean, LogisticsInputBean inputBean, Collection<LogisticsViewBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
				
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		for (LogisticsViewBean bean : beans) {
			if ( !StringHandler.isBlankString(bean.getOk())  && "true".equals(bean.getOk())) {
				
//				if ((!personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation",null,null,null)) ||
//						(!personnelBean.getPermissionBean().hasInventoryGroupPermission("inventoryReconciliation",null,null,null)) )
//					continue;
				try {
					 inArgs = new Vector(4);
			         inArgs.add(inputBean.getCountId());
			         inArgs.add(bean.getReceiptId());
			         inArgs.add(bean.getQtyShipped());
			         inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
			         factory.doProcedure("pkg_inventory_count.p_add_receipt", inArgs);
          
				} catch (Exception e) {
					errorMsg = library.getString("label.erroraddingreceipt") + bean.getReceiptId()+ "";
					errorMessages.add(errorMsg);
				}
			}
		}

		return (errorMessages.size()>0?errorMessages:null);
	}

	public ExcelHandler getExcelReport(Vector<LogisticsViewBean> beans, Locale locale) throws
	  NoDataException, BaseException, Exception {
	
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	
		ExcelHandler pw = new ExcelHandler(library);
	
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
		"label.itemid","label.description","label.invengroup", "label.receiptid","label.lot",
		"label.expdate","label.dor","label.bin", "label.status", "label.quantity"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type. */
		int[] types = {
				0,pw.TYPE_PARAGRAPH,0,0,0,
				pw.TYPE_CALENDAR,pw.TYPE_CALENDAR, 0,0,0,
				};
		
		int[] widths = {
				0,0,0,0,0,
				0,0, 0,0,0,
				};
		
		pw.applyColumnHeader(headerkeys, types, widths, null );
		
		// now write data
		//pw.setColumnDigit(10, 4);
		
		String indefinite = library.getString("label.indefinite");
		for (LogisticsViewBean member : beans) {
		
		  pw.addRow();
		
		  pw.addCell(member.getItemId());
		  pw.addCell(member.getItemDesc());
		  pw.addCell(member.getInventoryGroupName());
		  pw.addCell(member.getReceiptId());
		  pw.addCell(member.getMfgLot());
//		  pw.addCell((member.getExpireDate().getYear()==1100)?indefinite:member.getExpireDate());
		  pw.addCell(member.getExpireDate());
		  pw.addCell(member.getDateOfReceipt());
		  pw.addCell(member.getBin());
		  pw.addCell(member.getLotStatus());
		  pw.addCell(member.getQuantity());
		 
		}
		return pw;
	}


}
