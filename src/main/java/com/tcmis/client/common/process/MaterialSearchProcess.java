package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
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
import com.tcmis.client.common.beans.MaterialSearchViewBean;

/******************************************************************************
 * Process used by MaterialSearchAction
 * @version 1.0
 *****************************************************************************/

public class MaterialSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public MaterialSearchProcess(String client)
	{
		super(client);
	}
/*	
	public Collection getMaterialBeanCollection(String manufacturer, String searchArgument) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		Collection c = factory.select(manufacturer, searchArgument );
		//log.debug("MaterialBean collection size: [" + c.size() + "]; ");

		return c;
	}
*/
	public Collection getMaterialBeanCollection(String companyId, String facilityId, String catalogId, String catalogCompanyId, String searchField, String searchMode, String searchArgument, String application) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		Collection<MaterialSearchViewBean> c = null;
		
		StringBuilder query= new StringBuilder("select * from table (PKG_STOCK_MGMT.FX_MATERIAL_SEARCH('");
		query.append(catalogCompanyId).append("', '").append(catalogId).append("', '");
		query.append(searchField).append("', '").append(searchMode).append("', '");;
		query.append(searchArgument).append("', '").append(companyId).append("','"+facilityId+"','"+application+"','Y'))");
		query.append(" order by MATERIAL_DESC asc");

		c = factory.selectQuery(query.toString());

		return c;
	}
/*	
	public BigDecimal getNextMaterialId() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		BigDecimal materialId = factory.getNextMaterialId();
		// log.debug("materialId.toString() = [" + materialId.toString() + "]; ");

		return materialId;
	}


	public int insertNewMaterial(MaterialBean materialBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		int result = factory.insert(materialBean );
		if (log.isDebugEnabled()) {
			log.debug("new Integer( result ).toString() = [" + new Integer( result ).toString() + "]; ");
		}

		return result;
	}
*/	

	public ExcelHandler getExcelReport(Collection<MaterialSearchViewBean> data, Locale locale) throws
      NoDataException, BaseException, Exception {
    
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
	
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
		  "label.materialid","label.msds","label.materialdescription","label.tradename",
		  "label.viewonline","label.manufacturer"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type. 
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = {
		  0,0,0,0,
		  0,0};
		
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = {
		  10,10,30,30,
		  0,30};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
		  0,0,0,0,
		  0,0};
		  
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		
		for (MaterialSearchViewBean member : data) {
		  
	      pw.addRow();
	      pw.addCell(member.getMaterialId());
	      pw.addCell(member.getMsdsNumber());
	      pw.addCell(member.getMaterialDesc());
	      pw.addCell(member.getTradeName());
	      pw.addCell(member.getMsdsOnLine());
	      pw.addCell(member.getMfgDesc());
	    }
	    return pw;
    }

}
