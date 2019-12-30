package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.LessThanNWksInvViewBean;
import com.tcmis.internal.hub.beans.NWeekInventoryInputBean;
import com.tcmis.internal.hub.factory.LessThanNWksInvViewBeanFactory;

/**
 * ******************************************************************
 * Process for the NWeekInventoryProcess Section
 * 
 * @version 1.0
 * *****************************************************************         
 */


public class NWeekInventoryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public NWeekInventoryProcess(String client, String locale) {
		super(client, locale);
	}

	public NWeekInventoryProcess(String client) {
		super(client);
	}

	public Collection<LessThanNWksInvViewBean> getSearchResult(	NWeekInventoryInputBean inputBean, PersonnelBean personnelBean) throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());		
		LessThanNWksInvViewBeanFactory nWeekInvFactory = new LessThanNWksInvViewBeanFactory(dbManager);
		Collection<LessThanNWksInvViewBean> c = nWeekInvFactory.selectUsingProc(inputBean, personnelBean);
		return c;
	}

	public ExcelHandler getExcelReport(NWeekInventoryInputBean bean, PersonnelBean personnelBean, Locale locale)	throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary(	"com.tcmis.common.resources.CommonResources", locale);
		
		Collection<LessThanNWksInvViewBean> data = getSearchResult(bean,personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		
		pw.addTable();
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		pw.addThRegion("label.operatingentity",2,1);
		pw.addThRegion("label.hub",2,1);
		pw.addThRegion("label.inventorygroup",2,1);
		pw.addThRegion("label.itemid",2,1);
		pw.addThRegion("label.partnumber",2,1);
		pw.addThRegion("label.description",2,1);
		pw.addThRegion("label.onhand",2,1);
		pw.addThRegion("label.currencyid",2,1);
		pw.addThRegion("label.averagecost",2,1);
		pw.addThRegion("label.daystostockoutusingbasis",1,4);
		
		String[] headerkeys = 
				  { "label.operatingentity","label.hub","label.inventorygroup",
					"label.itemid","label.partnumber","label.description",
					"label.onhand","label.currencyid","label.averagecost","label.0-90days",
					"label.91-180days","label.181-270days","label.271-360days"   	
		          };
		int[] widths = {12,8,12,12,12,25,12,10,12,10,10,10,10 };
		int[] types =
		        { 
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_PARAGRAPH,
				pw.TYPE_STRING,pw.TYPE_STRING,pw.TYPE_STRING,0,
				0,0,0
				};	
		
		pw.applyColumnHeader(headerkeys, types, widths, null);	
		
		pw.setColumnDigit(8, 4);
		for (LessThanNWksInvViewBean member : data) {
			pw.addRow();			
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getItemId());
			pw.addCell(member.getPartNo());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getOnHand());
			if(member.getAverageCost() == null) 
				pw.addCell("");
			else
				pw.addCell(member.getCurrencyId());
			pw.addCell(member.getAverageCost());
			pw.addCell(member.getDaysToStockoutQtr1());
			pw.addCell(member.getDaysToStockoutQtr2());
			pw.addCell(member.getDaysToStockoutQtr3());
			pw.addCell(member.getDaysToStockoutQtr4());
		}
		return pw;

	}

  private static final String TABLE_NAME ="LESS_THAN_N_WKS_INV_VIEW";
}