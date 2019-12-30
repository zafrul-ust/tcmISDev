package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ForceBuyInputBean;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.beans.ItemInventoryForceBuyViewBean;
import com.tcmis.internal.hub.factory.ItemCountInventoryViewBeanFactory;
import com.tcmis.internal.hub.factory.ItemInventoryForceBuyViewBeanFactory;


/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class ForceBuyProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ForceBuyProcess(String client) {
		super(client);
	}

	public ForceBuyProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getSearchResult(ForceBuyInputBean inputBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ItemInventoryForceBuyViewBeanFactory factory = new
		ItemInventoryForceBuyViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		String s = null;
		s = inputBean.getInventoryGroup();
		if( s!= null && !s.equals("") )
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
					inputBean.getInventoryGroup());

		if (inputBean.getShowMinMaxOnly() != null &&
				inputBean.getShowMinMaxOnly().equals("Yes")) {
			searchCriteria.addCriterion("stockingMethod", SearchCriterion.NOT_EQUAL , "OOR");
		}

		if (inputBean.getSearchArgument() != null &&
				inputBean.getSearchArgument().trim().length() > 0) {
			String searchMode = inputBean.getSearchMode();
			String searchField = inputBean.getSearchField();
			String searchArgument = inputBean.getSearchArgument();
			String ignoreCase = "";
			if (!searchField.equalsIgnoreCase("itemId")) {
				ignoreCase = SearchCriterion.IGNORE_CASE;
			}
			if (searchMode.equalsIgnoreCase("is")) {
				searchCriteria.addCriterion(searchField, SearchCriterion.EQUALS,
						searchArgument.trim());
			}
			else if (searchMode.equalsIgnoreCase("contains")) {
				searchCriteria.addCriterion(searchField, SearchCriterion.LIKE,
						searchArgument.trim(), ignoreCase);
			}
			else if (searchMode.equalsIgnoreCase("startsWith")) {
				searchCriteria.addCriterion(searchField, SearchCriterion.STARTS_WITH,
						searchArgument.trim(), ignoreCase);
			}
			else if (searchMode.equalsIgnoreCase("endsWith")) {
				searchCriteria.addCriterion(searchField, SearchCriterion.ENDS_WITH,
						searchArgument.trim(), ignoreCase);
			}
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("inventoryGroup");
		sortCriteria.addCriterion("catPartNo");
		//		sortCriteria.addCriterion("partGroupNo");
		return factory.select(searchCriteria, sortCriteria);
	}


	public ExcelHandler createExcelFile(Collection bean, Locale locale)
	throws Exception {

		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<ItemInventoryForceBuyViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.inventorygroup","label.catalog","label.partnumber", "label.rpslrq","label.stockingmethod",
				"label.inventoryuom","label.onhand","label.inpurchasing", "label.item", "label.itemdescription"};
		//    , "label.replenishqty","label.needdatemm/dd/yyyy"};
		/*This array defines the type of the excel column.
    0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,0,pw.TYPE_NUMBER,pw.TYPE_NUMBER,0,pw.TYPE_PARAGRAPH};
		//    pw.TYPE_NUMBER,pw.TYPE_CALENDAR};
		/*This array defines the default width of the column when the Excel file opens.
    0 means the width will be default depending on the data type.*/
		int[] widths = {
				18,18,18,10,20,
				40,12,12,10,50};
		/*This array defines the horizontal alignment of the data in a cell.
    0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,0,0,
				0,0,0,pw.ALIGN_CENTER,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (ItemInventoryForceBuyViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getCatalogId());
			pw.addCell(member.getCatPartNo());
			pw.addCell(((member.getReorderPoint()!=null)?member.getReorderPoint():"")
					+"/"+((member.getStockingLevel()!=null)?member.getStockingLevel():"")
					+"/"+((member.getReorderQuantity()!=null)?member.getReorderQuantity():""));
			pw.addCell(member.getStockingMethod());
			pw.addCell(member.getItemPackaging());
			if (member.getPartOnHand()==null)
			{
				pw.addCell((BigDecimal)null);
			}
			else
			{
				pw.addCell(new BigDecimal(member.getPartOnHand().trim()));
			}
			if (member.getPartInPurchasing()==null)
			{
				pw.addCell((BigDecimal)null);
			}
			else
			{
				pw.addCell(new BigDecimal(member.getPartInPurchasing().trim()));
			}
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());

			//	  SimpleDateFormat date = new SimpleDateFormat(library.getString("java.dateformat"));
			//		SimpleDateFormat dateTime = new SimpleDateFormat(library.getString("java.datetimeformat"));
			//				pw.addCell(StringHandler.emptyIfNull(itemCountInventoryViewBean.getItemOnHand()) +
			//					" (" + StringHandler.emptyIfNull(itemCountInventoryViewBean.getLastCountDate()) + ")");
			//			pw.addCell((itemCountInventoryViewBean.getNeedDate()!=null)?date.format(itemCountInventoryViewBean.getNeedDate()):"");
		}
		return pw;
	}


	public Collection updateItemInvColl(Collection
			itemMgmtUpdateBeanCollection,
			PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ItemCountInventoryViewBeanFactory factory = new
		ItemCountInventoryViewBeanFactory(dbManager);
		Iterator mainIterator = itemMgmtUpdateBeanCollection.iterator();
		Collection messages = new Vector();


		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		String errorMessage;
		Collection results;
		Iterator resultsIter;

		while (mainIterator.hasNext()) {
			ItemCountInventoryViewBean updateBean = (ItemCountInventoryViewBean)
			mainIterator.next();

			//if (updateBean.getOkDoUpdate() != null && updateBean.getOkDoUpdate().length() > 0)
			if( permissionBean.hasInventoryGroupPermission("ForceBuy", updateBean.getInventoryGroup(),null,null))
			{
				if (updateBean.getReplenishQty() != null) {
					errorMessage = null;
					results = factory.forceBuyItemInventory(updateBean,personnelId.toString());
					resultsIter = results.iterator();
					if (resultsIter.hasNext()) {
						errorMessage = (String) resultsIter.next();
						if (log.isDebugEnabled()) {
							log.debug("p_force_buy_item_inventory    "+errorMessage);
						}
						if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK"))
						{
							messages.add(errorMessage);
						}
					}
				}
			}
		}
		return messages;
	}

}