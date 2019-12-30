package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.catalog.beans.SpecBean;

/******************************************************************************
 * Process for spec lookup
 * @version 1.0
 *****************************************************************************/
public class SpecLookupProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public SpecLookupProcess(String client) {
		super(client);
	}

	public SpecLookupProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection getSpecBeanCollection(PersonnelBean personnelBean, String searchArgument) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SpecBean());
        String searchLogic = doSearchLogic(searchArgument);
        StringBuilder query = new StringBuilder("select a.*,b.spec_library_desc from (");
        query.append("select distinct spec_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line,spec_library,host,itar from spec where nvl(itar,'N') = 'N'");
        if (!StringHandler.isBlankString(searchArgument)) {
			query.append(" and (").append(searchLogic).append(")");
		}
        if (personnelBean.hasItarAccess()) {
            query.append(" union all ");
            query.append("select distinct spec_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line,spec_library,host,itar from spec where nvl(itar,'N') = 'Y'");
            if (!StringHandler.isBlankString(searchArgument)) {
			    query.append(" and (").append(searchLogic).append(")");
		    }
        }
        query.append(") a, spec_library b where a.spec_library = b.spec_library");
        query.append(" order by a.spec_name,a.spec_title,b.spec_library_desc");
        return factory.selectQuery(query.toString());
    } //end of method

    //boolean search logic
    public String doSearchLogic(String search) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower(spec_id||' '||spec_name||' '||spec_title) like lower('%" + SqlHandler.validQuery(search) + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower(spec_id||' '||spec_name||' '||spec_title) like lower('%" + SqlHandler.validQuery(likes.elementAt(0).toString().trim()) + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower(spec_id||' '||spec_name||' '||spec_title) " + lk + " lower('%" + SqlHandler.validQuery(searchS) + "%') ";
		}

		return result;
	}

    public ExcelHandler getExcelReport(Collection<SpecBean> data, Locale locale) throws NoDataException, BaseException, Exception {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.specid", "label.specname", "label.spectitle", "label.specversion", "label.specamendment", "label.viewonline", "label.speclibrary"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 0, 0, 0, 0, 0, 0 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			for (SpecBean member : data) {
	
				pw.addRow();
                pw.addCell(member.getSpecId());
                pw.addCell(member.getSpecName());
                pw.addCell(member.getSpecTitle());
                pw.addCell(member.getSpecVersion());
                pw.addCell(member.getSpecAmendment());
                pw.addCell(member.getOnLine());
                pw.addCell(member.getSpecLibraryDesc());
            }
			return pw;
	}
}
