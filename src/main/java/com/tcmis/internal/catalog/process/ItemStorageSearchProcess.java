package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Iterator;

import com.tcmis.internal.catalog.beans.ItemStorageBean;
import com.tcmis.internal.catalog.factory.IItemStorageDataMapper;
import com.tcmis.internal.catalog.factory.ItemStorageDataMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process used by ItemStorageSearchProcess
 * @version 1.0
 *****************************************************************************/

public class ItemStorageSearchProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
    IItemStorageDataMapper dataMapper;

	public ItemStorageSearchProcess(String client) {
		super(client);
        dataMapper = new ItemStorageDataMapper(new DbManager(getClient(), getLocale()));
	}

	public Collection getSearchData(ItemStorageBean inputBean) throws BaseException, Exception {
		return dataMapper.getSearchData(inputBean);
	}


	public ExcelHandler createExcelReport(ItemStorageBean inputBean) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		String[] headerkeys = { "label.inventorygroup","label.itemid","label.materialid","label.materialdesc","label.packaging" };
		int[] types = { 0, 0, 0, 0, 0};
		int[] widths = { 20, 20, 20, 20, 20 };
		int[] horizAligns = { 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
        Iterator iter = getSearchData(inputBean).iterator();
		while(iter.hasNext()) {
            ItemStorageBean itemStorageBean = (ItemStorageBean)iter.next();
			pw.addRow();
			pw.addCell(itemStorageBean.getInventoryGroupName());
			pw.addCell(itemStorageBean.getItemId());
			pw.addCell(itemStorageBean.getMaterialId());
            pw.addCell(itemStorageBean.getMaterialDesc());
			pw.addCell(itemStorageBean.getPackaging());
		}
		return pw;
	}

}
