package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.MsdsSearchInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for SearchMsdsProcess
 * @version 1.0
 *****************************************************************************/
public class MsdsSearchProcess extends GenericProcess {

	Log log = LogFactory.getLog(this.getClass());

	public MsdsSearchProcess(String client,String locale) {
		super(client,locale);
	}
	
	public Collection getMsdsCompanyColl() throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsSearchInputBean()); 
		 String query = "select distinct msds_search_company_id, company_name from MSDS_SEARCH_COMPANY_VIEW order by company_name";
	    return factory.selectQuery(query);
	}

    public Collection searchMsds(MsdsSearchInputBean bean) throws BaseException {
        
           	   factory.setBean(new MsdsSearchDataTblBean());
        	   StringBuilder query = new StringBuilder("select  *  from table (pkg_msds_search.fx_msds_search(");
               if (!StringHandler.isBlankString(bean.getMsdsSearchCompanyId())) {
                   query.append("'").append(bean.getMsdsSearchCompanyId()).append("'");
               }else {
                   query.append("''");
               }
               query.append(",''");
               if (!StringHandler.isBlankString(bean.getSearchText())) {
                   query.append(",").append(this.getSqlString(bean.getSearchText()));
               }else {
                   query.append(",''");
               }
               query.append("))");
               
               return factory.selectQuery(query.toString());
     			
	}

    public ExcelHandler getMsdsExcelReport(MsdsSearchInputBean inputbean) throws NoDataException, BaseException, Exception {
		Vector<MsdsSearchDataTblBean> data = (Vector)this.searchMsds(inputbean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	 write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.kitmsds",
                "label.kitdesc",
                "label.msds",
				"label.description",
				"label.manufacturer",
				"label.tradename"};
		/*This array defines the type of the excel column.
            0 means default depending on the data type. */
		int[] types = {
				0,pw.TYPE_PARAGRAPH,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
            0 means the width will be default depending on the data type.*/
		int[] widths = {0,10,0,10,10,10};
		/*This array defines the horizontal alignment of the data in a cell.
            0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(MsdsSearchDataTblBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getCustomerMixtureNumber());
            pw.addCell(bean.getMixtureDesc());
            pw.addCell(bean.getCustomerMsdsNumber());
			pw.addCell(bean.getMaterialDesc());
			pw.addCell(bean.getMfgDesc());
			pw.addCell(bean.getTradeName());
		}
		return pw;
	}


} //end of class

