package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.ItemCatalogViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.ItemLookupInputBean;

/******************************************************************************
 * Process for ItemLookupProcess
 * 
 * @version 1.0
 *****************************************************************************/
public class ItemLookupProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public ItemLookupProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<ItemCatalogViewBean> search(ItemLookupInputBean input, PersonnelBean user) {

		Collection<ItemCatalogViewBean> results = null;
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ItemCatalogViewBean());

			StringBuilder query = new StringBuilder("select * from table (PKG_INVENTORY_DETAIL_WEB.FX_ITEM_LOOKUP('");
			query.append(user.getPersonnelId()).append("','");
			query.append(StringHandler.emptyIfNull(input.getOpsEntityId())).append("','");
			query.append(StringHandler.emptyIfNull(input.getHub())).append("','");
			query.append(StringHandler.emptyIfNull(input.getInventoryGroup())).append("','");
			if (input.isRestrictedToApprovedForGroup()) {
				query.append("Y");
			}
			query.append("','");
			if (input.isRestrictedToInCatalog()) {
				query.append("Y");
			}
			query.append("',");
			query.append(SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(input.getSearchText())));

			query.append("))");

			results = factory.selectQuery(query.toString());
			if (results != null) {
				if (results.size() > 0) {
					calculatingNumberOfKitsPerItem(results);
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			results = new Vector(0);
		}
		return results;
	}

	private void calculatingNumberOfKitsPerItem(Collection data) {
		try {
			if (data.size() > 1) {
				Object[] dataArray = data.toArray();
				int count = 1;
				int firstMatchedItemIndex = 0;
				// go ahead and get the first row and declare it
				// as our current
				ItemCatalogViewBean firstBean = (ItemCatalogViewBean) dataArray[0];
				BigDecimal currentItem = firstBean.getItemId();
				for (int i = 1; i < dataArray.length; i++) {
					ItemCatalogViewBean bean = (ItemCatalogViewBean) dataArray[i];
					if (currentItem.equals(bean.getItemId())) {
						count++;
						// setting number of kits per
						// item to -1 because it's kit
						bean.setNumberOfKitsPerItem(new BigDecimal(-1));
					}
					else {
						ItemCatalogViewBean matchBean = (ItemCatalogViewBean) dataArray[firstMatchedItemIndex];
						matchBean.setNumberOfKitsPerItem(new BigDecimal(count));
						// reset count
						count = 1;
						// reset first matched
						firstMatchedItemIndex = i;
					}
					currentItem = bean.getItemId();
				}
				// taking care of the last record
				ItemCatalogViewBean lastBean = (ItemCatalogViewBean) dataArray[dataArray.length - 1];
				// this the last record is not a kit
				if (lastBean.getNumberOfKitsPerItem() == null) {
					lastBean.setNumberOfKitsPerItem(new BigDecimal(1));
				}
				else {
					// otherwise it's a kit
					ItemCatalogViewBean matchBean = (ItemCatalogViewBean) dataArray[firstMatchedItemIndex];
					matchBean.setNumberOfKitsPerItem(new BigDecimal(count));
				}
			}
			else {
				// since only one record then no need to merge
				// cell
				Object[] arrayObj = data.toArray();
				ItemCatalogViewBean firstBean = (ItemCatalogViewBean) arrayObj[0];
				firstBean.setNumberOfKitsPerItem(new BigDecimal(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExcelHandler getItemExcelReport(ItemLookupInputBean input, PersonnelBean user) throws NoDataException, BaseException, Exception {

		Collection<ItemCatalogViewBean> data =  search(input, user);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler excel = new ExcelHandler(library);

		excel.addTable();

		// write column headers
		excel.addRow();

		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.item", "inventory.label.componentdescription", "inventory.label.packaging", "label.grade", "label.manufacturer", "label.country",
				"label.mfgpartno", "catalog.label.shelflife" };
		/*
		 * This array defines the type of the excel column. 0 means
		 * default depending on the data type.
		 */
		int[] types = { 0, excel.TYPE_PARAGRAPH, excel.TYPE_PARAGRAPH, 0, excel.TYPE_PARAGRAPH, 0, 0, excel.TYPE_PARAGRAPH };
		/*
		 * This array defines the default width of the column when the
		 * Excel file opens. 0 means the width will be default depending
		 * on the data type.
		 */
		int[] widths = { 0, 0, 0, 10, 0, 10, 10, 0 };
		/*
		 * This array defines the horizontal alignment of the data in a
		 * cell. 0 means excel defaults the horizontal alignemnt by the
		 * data type.
		 */
		int[] horizAligns = null;
		excel.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		// DateFormat longDateFormat = new
		// SimpleDateFormat("MM/dd/yyyy HH:mm");
		// DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		// DateFormat shortDateFormat =
		// DateFormat.getDateInstance(DateFormat.SHORT);

		for (ItemCatalogViewBean bean : data) {
			excel.addRow();
			excel.addCell(bean.getItemId());
			excel.addCell(bean.getMaterialDesc());
			excel.addCell(bean.getPackaging());
			excel.addCell(bean.getGrade());
			excel.addCell(bean.getMfgDesc());
			excel.addCell(bean.getCountryName());
			excel.addCell(bean.getMfgPartNo());
			excel.addCell(bean.getShelfLife());
		}
		return excel;
	}

} // end of class

/*
 * //test query =
 * "select tcm_ops.fx_sales_catalog_search('366', 'contains') from dual";
 * Object[] myData = factory.selectCursorIntoObjectArray(query); Collection
 * reportData = (Collection)myData[GenericSqlFactory.DATA_INDEX]; Iterator iter
 * = reportData.iterator(); while(iter.hasNext()) { Object[] rowData =
 * (Object[])iter.next(); for (int i = 0; i < rowData.length; i++) {
 * log.debug(StringHandler.emptyIfNull(rowData[i])); } }
 */