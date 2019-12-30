package com.tcmis.internal.hub.process;


import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.TlLtlCarrierIgViewBean;

/******************************************************************************
 * Process for TruckloadCarrierSearchProcess
 * @version 1.0
 *****************************************************************************/
public class TruckloadCarrierSearchProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public TruckloadCarrierSearchProcess(String client, String locale) {
		super(client,locale);
	}

	public Collection getSearchData(TlLtlCarrierIgViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new TlLtlCarrierIgViewBean());
		StringBuilder query = new StringBuilder();
		if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
			query.append(" where inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
		}
		if (!StringHandler.isBlankString(inputBean.getSearchText())) {
			if (query.length() > 0) {
				query.append(" and ");
			}else {
				query.append(" where ");
			}
			query.append("lower(carrier_code||carrier_name||transportation_mode) like lower('%").append(SqlHandler.validQuery(inputBean.getSearchText())).append("%')");
		}
		query.insert(0, "select * from tl_ltl_carrier_ig_view");
		query.append(" order by carrier_name,carrier_code,transportation_mode");
		return genericSqlFactory.selectQuery(query.toString());
	} //end of method

	public ExcelHandler getExcelReport(TlLtlCarrierIgViewBean bean, Locale locale) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<TlLtlCarrierIgViewBean> data = this.getSearchData(bean);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();

		//	  write column headers
		pw.addRow();
		pw.addCellKeyBold("label.search");
		pw.addCell(bean.getSearchText());

		//blank row
		pw.addRow();

		//result table
		//write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.carrier","label.account", "label.transportationmode"};

		/*This array defines the type of the excel column.
   0 means default depending on the data type. */
		int[] types = {0,0,0};
		/*This array defines the default width of the column when the Excel file opens.
   0 means the width will be default depending on the data type.*/
		int[] widths = {0,0,0};
		/*This array defines the horizontal alignment of the data in a cell.
   0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		//now write data
		for (TlLtlCarrierIgViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getCarrierName());
			pw.addCell(member.getCarrierCode());
			pw.addCell(member.getTransportationMode());
		}
		return pw;
	} //end of method

}
