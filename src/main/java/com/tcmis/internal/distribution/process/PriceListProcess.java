package com.tcmis.internal.distribution.process;

import com.tcmis.internal.distribution.beans.*;

import com.tcmis.internal.distribution.factory.PriceGroupCatalogOvBeanFactory;
import com.tcmis.internal.distribution.factory.PriceListOvBeanFactory;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.UserEntityAdminViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import java.math.BigDecimal;
import java.util.*;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class PriceListProcess
  		extends GenericProcess {

   public PriceListProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
  }
   public String ModifyPriceList(PriceListOvBean bean,String qtys,String prices) throws BaseException, Exception {
//	   setting business logic...
/*
PROCEDURE P_MODIFY_PRICE_LIST(a_company_id IN customer.catalog_part_pricing_orig.company_id%TYPE,
                                     a_catalog_id IN customer.catalog_part_pricing_orig.catalog_id%TYPE,
                                     a_cat_part_no IN customer.catalog_part_pricing_orig.cat_part_no%TYPE,
                                     a_part_group_no IN customer.catalog_part_pricing_orig.part_group_no%TYPE,
                                     a_price_group_id IN customer.catalog_part_pricing_orig.price_group_id%TYPE,
                                     a_new_start_date IN customer.catalog_part_pricing_orig.start_date%TYPE,
                                     a_old_start_date IN customer.catalog_part_pricing_orig.start_date%TYPE,
                                     a_end_date IN customer.catalog_part_pricing_orig.end_date%TYPE,
                                     a_price_break_qty IN varchar2,
                                     a_catalog_price IN varchar2,
                                     a_baseline_price IN customer.catalog_part_pricing_orig.baseline_price%TYPE,
                                     a_currency_id IN customer.catalog_part_pricing_orig.currency_id%TYPE,
                                     a_catalog_company_id IN customer.catalog_part_pricing_orig.catalog_company_id%TYPE,
                                     a_error_message OUT VARCHAR2);
                                     
*/	   // I don't have update routine now.
       String pkgCall = "PKG_PRICE_LIST.P_MODIFY_PRICE_LIST";
       return getProcError(
			   buildProcedureInput(bean.getCompanyId(),
					   			   bean.getCatalogId(),
					               bean.getCatPartNo(),
					               bean.getPartGroupNo(),
					               bean.getPriceGroupId(),
					               bean.getStartDate(),
					               bean.getOldStartDate(),
					               bean.getEndDate(),
					               qtys,
					               prices,
					               bean.getBaselinePrice(),
					               bean.getCurrencyId(),
					               bean.getCatalogCompanyId()
					               )
			   ,null,pkgCall);
   }
   public String DeletePriceList(PriceListOvBean bean) throws BaseException, Exception {
//	   setting business logic...
/*
PROCEDURE P_DELETE_PRICE_LIST(a_company_id IN customer.catalog_part_pricing_orig.company_id%TYPE,
                                     a_catalog_id IN customer.catalog_part_pricing_orig.catalog_id%TYPE,
                                     a_cat_part_no IN customer.catalog_part_pricing_orig.cat_part_no%TYPE,
                                     a_part_group_no IN customer.catalog_part_pricing_orig.part_group_no%TYPE,
                                     a_price_group_id IN customer.catalog_part_pricing_orig.price_group_id%TYPE,
                                     a_start_date IN customer.catalog_part_pricing_orig.start_date%TYPE,
                                     a_error_message OUT VARCHAR2) IS
*/	   // I don't have update routine now.
       String pkgCall = "PKG_PRICE_LIST.P_DELETE_PRICE_LIST";
       return getProcError(
			   buildProcedureInput(bean.getCompanyId(),
					   			   bean.getCatalogId(),
					               bean.getCatPartNo(),
					               bean.getPartGroupNo(),
					               bean.getPriceGroupId(),
					               bean.getOldStartDate()
					               )
			   ,null,pkgCall);
   }
   public String newPriceGroup(String companyId,String name,String desc,String ops,String currencyId,Date bd)throws BaseException, Exception{
//	   setting business logic...
/*
PROCEDURE P_CREATE_PRICE_GROUP(a_company_id IN customer.price_group.company_id%TYPE,
                               a_price_group_name IN customer.price_group.price_group_name%TYPE,
                               a_price_group_desc IN customer.price_group.price_group_desc%TYPE,
                               a_currency_id IN customer.price_group.currency_id%TYPE,
                               a_ops_company_id IN customer.price_group.ops_company_id%TYPE,
                               a_ops_entity_id IN customer.price_group.ops_entity_id%TYPE,
                               a_baseline_price_reset customer.price_group.baseline_price_reset_date%TYPE,
                               a_price_group_id IN OUT customer.price_group.price_group_id%TYPE,
                               a_error_message OUT VARCHAR2) IS
*/	   // I don't have update routine now.
       String pkgCall = "PKG_PRICE_LIST.P_CREATE_PRICE_GROUP";
       Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) ); // price group id
		outArgs.add( new Integer(java.sql.Types.VARCHAR) ); // error 

       return getProcOut(
			   buildProcedureInput(companyId,name,desc,currencyId,companyId,ops,bd)
			   ,outArgs,pkgCall);
   }
   public String RemovePriceList(PriceListOvBean bean) throws BaseException, Exception {
//	   setting business logic...
/*
 * 
CUSTOMER.PKG_PRICE_LIST.P_DELETE_PRICE_LIST(a_company_id IN customer.catalog_part_pricing_orig.company_id%TYPE,
                                     a_catalog_id IN customer.catalog_part_pricing_orig.catalog_id%TYPE,
                                     a_cat_part_no IN customer.catalog_part_pricing_orig.cat_part_no%TYPE,
                                     a_part_group_no IN customer.catalog_part_pricing_orig.part_group_no%TYPE,
                                     a_price_group_id IN customer.catalog_part_pricing_orig.price_group_id%TYPE,
                                     a_start_date IN customer.catalog_part_pricing_orig.start_date%TYPE,
                                     a_error_message OUT VARCHAR2);
*/	   // I don't have update routine now.
       String pkgCall = "PKG_PRICE_LIST.P_DELETE_PRICE_LIST";
       return getProcError(
			   buildProcedureInput(bean.getCompanyId(),
					               bean.getCatalogId(),
					               bean.getCatPartNo(),
					               bean.getPartGroupNo(),
					               bean.getPriceGroupId(),
					               bean.getOldStartDate()
					               )
			   ,null,pkgCall);
   }

   public Collection getPriceList(PriceListInputBean bean) throws BaseException {
	   PriceListOvBeanFactory factory = new PriceListOvBeanFactory(dbManager);
	   SearchCriteria sc= new SearchCriteria();
	   SortCriteria sort= new SortCriteria();
	   sort.addCriterion("priceGroupId");
	   sort.addCriterion("partName");
	   sort.addCriterion("specListId");
	   sort.addCriterion("startDate");
//	   bean.setCompanyId("MILLER");
//	   if( !isBlank(bean.getCompanyId())) 
//		   sc.addCriterion("companyId",SearchCriterion.EQUALS,bean.getCompanyId());
	   if( !isBlank(bean.getOpsEntityId())) 
		   sc.addCriterion("opsEntityId",SearchCriterion.EQUALS,bean.getOpsEntityId());
	   if( !isBlank(bean.getPriceGroupId())) 
		   sc.addCriterion("priceGroupId",SearchCriterion.EQUALS,bean.getPriceGroupId());
	   sc.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() );
	   
	   if( !"Y".equals(bean.getShowExpired()) ) {
			 GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
			 int year  = calendar.get(GregorianCalendar.YEAR);
			 int month  = calendar.get(GregorianCalendar.MONTH);
			 int date  = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			 calendar.clear();
//			 System.out.println(calendar);
			 calendar.set(year, month, date);
//			 calendar.add(GregorianCalendar.DATE, 1);
//		   sc.addCriterion("endDate",SearchCriterion.FROM_DATE,calendar.getTime());
	       sc.addCriterion("endDate", SearchCriterion.GREATER_THAN, DateHandler.getOracleToDateFunction(calendar.getTime()));
	   }
//	   else
//	       sc.addCriterion("endDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, "sysdate ");

//	   return factory.selectObject("select value(p) from Price_list_ov p where company_id = 'MILLER' and cat_part_no in ('401674', '401691', '401744')");
	   return factory.selectObject(sc,sort);
	  	   
	  }
   public Collection getCatalogItem(PriceListInputBean bean) throws BaseException {
	   PriceListOvBeanFactory factory = new PriceListOvBeanFactory(dbManager);
	   SearchCriteria sc= new SearchCriteria();
	   bean.setCompanyId("MILLER");
	   if( !isBlank(bean.getCompanyId())) 
		   sc.addCriterion("companyId",SearchCriterion.EQUALS,bean.getCompanyId());
//	   if( !isBlank(bean.getOpsEntityId())) 
//		   sc.addCriterion("opsEntityId",SearchCriterion.EQUALS,bean.getOpsEntityId());
//	   if( !isBlank(bean.getPriceGroupId())) 
//		   sc.addCriterion("priceGroupId",SearchCriterion.EQUALS,bean.getPriceGroupId());
	   sc.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() );
//	   if( !"Y".equals(bean.getShowExpired()) ) 
//		   sc.addCriterion("endDate",SearchCriterion.FROM_DATE,new Date());
//	   else
//		   sc.addCriterion("endDate",SearchCriterion.FROM_DATE,new Date());
//	   return factory.selectObject("select value(p) from Price_list_ov p where company_id = 'MILLER' and cat_part_no in ('401674', '401691', '401744')");
	   return factory.selectObject(sc);
	  	   
	  }

   public Collection getPriceGroupCatalogOv(PartSearchInputBean ib) throws
	BaseException {
		SearchCriteria s = new SearchCriteria("priceGroupId",SearchCriterion.EQUALS,ib.getPriceGroupId());
		s.addCriterion("opsEntityId", SearchCriterion.EQUALS, ib.getOpsEntityId());
		return new PriceGroupCatalogOvBeanFactory(dbManager).selectObject(s);
	}
	protected String getProcOut(Collection inArgs,Collection outArgs,String procname) {
		boolean hasError = false;
		String errorMsg = "";
		Collection c = null;
		
		if( inArgs == null ) inArgs = new Vector();
		if( outArgs == null ) {
			outArgs = new Vector(); 
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		}
		
		try {
			c = dbManager.doProcedure(procname, inArgs, outArgs);
		} catch(Exception ex){
			hasError = true;
		}
		String procResult = "";
		if( c != null ) {
			Iterator it = c.iterator();
			if(it.hasNext()) procResult =  (String)it.next(); // price group id
			System.out.println("Out 1:"+ procResult);
			if(it.hasNext()) procResult =  (String)it.next(); // error
			System.out.println("Out 2:"+ procResult);
			if( !isBlank(procResult) && !"OK".equalsIgnoreCase(procResult)) {
				hasError = true;
				log.error(procname+" returned:" + procResult);
			}
		}
		if( hasError ) {
			ResourceLibrary library = new ResourceLibrary(
					"com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errorMsg = library.getString("generic.error");
		}
		return errorMsg;
	}
 
public ExcelHandler getExcelReport(PriceListInputBean ib,Collection<PriceListOvBean> data, Locale locale)
	throws Exception
	{
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		  ExcelHandler pw = new ExcelHandler(library);
		  
		  pw.addTable();
		  pw.addRow();
		  pw.addCellKeyBold("label.operatingentity");
		  pw.addCell(ib.getOpsEntityId());
		  pw.addCellKeyBold("label.pricelist");
		  pw.addCell(ib.getPriceGroupId());
		  pw.addCellKeyBold("label.part");
		  pw.addCell(ib.getSearchArgument());
		  pw.addRow();
		  
//		  write column headers
		  pw.addRow();
		  /*Pass the header keys for the Excel.*/
//none distribution change 
//		    "label.catpartno"
//		  	"label.description"
		  
		    String[] headerkeys = {
		      "label.pricelist","label.catalogcompany","label.catalog", "label.catalogitem","label.specification",
		      "label.description","label.startDate","label.baseline","label.currencyid","label.quantity",
		      "label.unitprice"};
		    /*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		    int[] types = {
		      0,0,0,0,0,
		      0,pw.TYPE_CALENDAR,pw.TYPE_NUMBER,0,pw.TYPE_NUMBER,
		      pw.TYPE_NUMBER};
		    /*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		    int[] widths = {
		      0,0,0,0,16,
		      40,0,0,0,0,
		      0};
		    /*This array defines the horizontal alignment of the data in a cell.
		    0 means excel defaults the horizontal alignemnt by the data type.*/
//		    int[] horizAligns = {0};
		      
		    pw.applyColumnHeader(headerkeys, types, widths);//, horizAligns);

//		  now write data
//		  DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//		  DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		  BigDecimal one = new BigDecimal("1");
		  for(PriceListOvBean bean:data)
		  {
//			  String startDate = (bean.getDateOfReceipt()==null)?"":shortDateFormat.format(bean.getDateOfReceipt() );
//			  String productionDate = (bean.getTransactionDate()==null)?"":longDateFormat.format(bean.getTransactionDate() );
//			  BigDecimal zero = new BigDecimal(0);
//			  String startPageOrder = (bean.getStartPageOrder()==null)?"":bean.getStartPageOrder().toString();
			  pw.addRow();
			  pw.addCell(bean.getPriceGroupName());
			  pw.addCell(bean.getCatalogCompanyName());
			  pw.addCell(bean.getCatalogName());
			  pw.addCell(bean.getPartName());
			  pw.addCell(bean.getSpecListId()+bean.getSpecList());
			  pw.addCell(bean.getPartDesc());
			  pw.addCell(bean.getStartDate());
			  pw.addCell(bean.getBaselinePrice());
			  pw.addCell(bean.getCurrencyId());
			  pw.addCell( one );
			  pw.addCell(bean.getCatalogPrice());
			  for(CppPriceBreakObjBean bean2:(Collection<CppPriceBreakObjBean>) bean.getPriceBreakCollection()) {
				  pw.addRow();
				  pw.addCell(bean.getPriceGroupName());
				  pw.addCell(bean.getCatalogCompanyName());
				  pw.addCell(bean.getCatalogName());
				  pw.addCell(bean.getPartName());
				  pw.addCell(bean.getSpecListId()+bean.getSpecList());
				  pw.addCell(bean.getPartDesc());
				  pw.addCell(bean.getStartDate());
				  pw.addCell(bean.getBaselinePrice());
				  pw.addCell(bean.getCurrencyId());
				  pw.addCell(bean2.getBreakQuantity());
				  pw.addCell(bean2.getCatalogPrice());
			  }
		  }
		  return pw;
	}
 
}
