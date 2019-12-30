package com.tcmis.internal.supply.process;

//import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.supply.beans.SupplierReturnsInputBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class SupplierReturnsProcess  extends BaseProcess
{

	public SupplierReturnsProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getLogisticsViewBeanCollection(PersonnelBean personnelBean, SupplierReturnsInputBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LogisticsViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();

		if(!StringHandler.isBlankString(inputBean.getOpsEntityId()))
			searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if(!StringHandler.isBlankString(inputBean.getHub()))
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
		if(!StringHandler.isBlankString(inputBean.getInventoryGroup()))
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
			if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
				invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

		if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
			searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
		}
		
		searchCriteria.addCriterion("quantity",SearchCriterion.GREATER_THAN,"0");
	//	searchCriteria.addCriterion("",SearchCriterion.CUSTOM,"(owner_company_id in ('RADIAN', 'HAAS') or owner_company_id is null)");

		return factory.select(searchCriteria, null,"LOGISTICS_VIEW");
	}



	public ExcelHandler getExcelReport(PersonnelBean personnelBean, SupplierReturnsInputBean bean, Locale locale)
	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<LogisticsViewBean> data = getLogisticsViewBeanCollection(personnelBean, bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys =
		{		"label.operatingentity","label.hub","label.inventorygroup","label.itemid","label.shortDesc",
				"label.haaspo","label.receiptid","label.qtyavailable","label.lotstatus"
		};
		int[] widths = {12,12,26,12,12,
				26,12,12,12};
		int[] types =
		{
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,0,pw.TYPE_STRING,
				0,0,0,pw.TYPE_STRING,
		};

		int[] aligns = { 0,0,0,0,0,0,0,0,0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (LogisticsViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getItemId());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getPoLine());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getLotStatus());
		}
		return pw;

	}


	public Collection update(PersonnelBean personnelBean,Collection<LogisticsViewBean> updateBeanCollection) throws
	BaseException, Exception {
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";

		//Collection resultCollection = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		if((updateBeanCollection!=null) && (!updateBeanCollection.isEmpty()))
		{
			for(LogisticsViewBean updateBean : updateBeanCollection) {
				if(!StringHandler.isBlankString(updateBean.getOk()) &&
						( isBlank(updateBean.getQtyReturned()) || isBlank(updateBean.getReturnDate()) || isBlank(updateBean.getSupplierRma()))) {
					errorMsg = library.getString("label.validvalues")+ library.getString("label.receiptid")+updateBean.getReceiptId() + "";
					errorMessages.add(errorMsg);
					break;
				}
				try {
					if(!StringHandler.isBlankString(updateBean.getOk()) && !StringHandler.isBlankString(updateBean.getQtyReturned().toString())) {

						inArgs = new Vector(9);
						inArgs.add(updateBean.getReceiptId());
						inArgs.add(updateBean.getQtyReturned());
						inArgs.add(personnelId);
						inArgs.add(new Timestamp(updateBean.getReturnDate().getTime()));
						inArgs.add(StringHandler.emptyIfNull(updateBean.getComments()));
						inArgs.add("");
						inArgs.add("");
						inArgs.add(updateBean.getSupplierRma());
						inArgs.add("N");

						//					outArgs = new Vector(1);
						//					outArgs.add(new Integer(java.sql.Types.VARCHAR));
						if(log.isDebugEnabled()) {
							log.debug("p_return_to_vendor_detail"+inArgs);
						}
						factory.doProcedure("p_return_to_vendor_detail", inArgs);
					}
				} catch (Exception e) {
					e.printStackTrace();
					errorMsg = "Error updating Receipt Id: "+ updateBean.getReceiptId() + "";
					errorMessages.add(errorMsg);
				}
			}

		}
		return errorMessages;
	}

}




