package com.tcmis.client.report.process;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.report.beans.AdHocTemplateBean;
import com.tcmis.client.report.beans.MaterialTransferHistoryInputBean;
import com.tcmis.client.report.beans.WorkAreaTransferBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;

/******************************************************************************
 * Process to build a web page for user to view Material Transfer History.
 * @version 1.0
 *****************************************************************************/
public class MaterialTransferHistoryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	public MaterialTransferHistoryProcess(String client) {
		super(client);
	}

	public MaterialTransferHistoryProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getSearchResult(MaterialTransferHistoryInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new WorkAreaTransferBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_WORK_AREA_TRANSFER.FX_WORK_AREA_TRANSFER('");
		query.append(StringHandler.emptyIfNull(inputBean.getFacilityId())).append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getFromApplicationId())).append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getToApplicationId())).append("',");
		
		query.append(null!=inputBean.getTransferredFromDate()?"TO_DATE('" + dateFormatter.format(inputBean.getTransferredFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''").append(",");
		query.append(null!=inputBean.getTransferredToDate()?"TO_DATE('" + dateFormatter.format(inputBean.getTransferredToDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''").append(",'");
		query.append(StringHandler.emptyIfNull(inputBean.getSearchBy())+"','");
		query.append(StringHandler.emptyIfNull(inputBean.getSearchType())+"','");
		query.append(StringHandler.emptyIfNull(inputBean.getSearchText())+"'))");
        query.append(" order by to_pr_number desc");

        return factory.selectQuery(query.toString());
		
	} //end of method

	public ExcelHandler getExcelReport(MaterialTransferHistoryInputBean inputBean, Collection<WorkAreaTransferBean> data, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	/*	CatalogProcess catalogProcess = new CatalogProcess(getClient(), getLocale());
		//String showChargesFeatures ="Y";
		boolean showChargesFeatures = "Y".equals(catalogProcess.isFeatureReleased(inputBean.getFacilityId(), "DeliveriesCostData"));
		*/
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { 
				"label.transferreddate","label.mrline","label.workarea", "label.catalog", "label.partno",
				"label.partdesc", "label.item", "label.itemdesc", "label.qty", "label.frommrline",
				"label.fromworkarea"};
		/*This array defines the type of the excel column.
					0 means default depending on the data type. */
		int[] types = { pw.TYPE_CALENDAR, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0 };
					
		/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 0, 0, 0, 0,
						 20, 0, 20, 0, 0,
						 20 };
		/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0,
							  0, 0, 0, 0, 0,
							  0 };
	
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		for (WorkAreaTransferBean member : data) {
            pw.addRow();
            pw.addCell(member.getTransferDate());
            pw.addCell(member.getToPrNumber()+"-"+member.getToLineItem());
            pw.addCell(member.getToApplicationDesc());
            pw.addCell(member.getCatalogDesc());
            pw.addCell(member.getFacPartNo());
            pw.addCell(member.getPartDescription());
            pw.addCell(member.getItemId());
            pw.addCell(member.getItemDesc());
            pw.addCell(member.getQuantity());
            pw.addCell(member.getFromPrNumber()+"-"+member.getFromLineItem());
            pw.addCell(member.getFromApplicationDesc());
		}

		return pw;
	}
	

} //end of class
