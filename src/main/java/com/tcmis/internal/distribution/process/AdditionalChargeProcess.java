package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.AdditionalChargeInputBean;
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;

public class AdditionalChargeProcess extends GenericProcess {
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;

	public AdditionalChargeProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

	public Collection getSearchResults(AdditionalChargeInputBean input) throws BaseException {
				
		String query = "select * from table (pkg_catalog_item_add_charge.fx_catalog_item_add_charge({0},{1})) ";
			if(!StringHandler.isBlankString(input.getSearchArgument()))
			{
			  query = query+ "where lower(" +input.getSearchField()+") ";
			  if(input.getSearchMode().equalsIgnoreCase("LIKE")){  
				  query = query+ "LIKE lower(''%"+SqlHandler.escSingleQteFuncall(input.getSearchArgument())+"%'')";
			  }
			  if(input.getSearchMode().equalsIgnoreCase("IS")){  
				  query = query+ "= '"+SqlHandler.escSingleQteFuncall(input.getSearchArgument())+"'";
			  }
		    }
	
			query = query+  " order by item_description";
		// H, L or B for both
		String chargeType = input.isHeaderOnly() ? "H" : input.isLineOnly() ? "L" : "B";
		return getSearchResult(query, new CatalogItemAddChargeBean(), input.getOpsEntityId(), chargeType);
	}

	public ExcelHandler getItemExcelReport(AdditionalChargeInputBean inputbean) throws NoDataException, BaseException, Exception {

		Collection<BaseDataBean> data = getSearchResults(inputbean);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler excel = new ExcelHandler(library);

		/* Column headers for  Excel. */
		String[] headerkeys = { "label.item", "label.description", "label.headerCharge", "label.linecharge", "label.defaultprice", "label.currency", "label.laastUpdatedDate", "label.lastUpdatedBy" };

		//Column types, 0 means default depending on the data type.
		int[] types = { 0, excel.TYPE_PARAGRAPH, 0, 0, 0, 0, excel.TYPE_DATE, 0 };

		//Column width for Excel, 0 means the width will be default depending on the data type.
		int[] widths = { 0, 0, 0, 0, 0, 0 };

		// Cell horizontal alignment
		int[] horizAligns = null;

		// Create the table
		excel.addTable();

		// Create the column header row
		excel.addRow();
		excel.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write rows for the data
		for (BaseDataBean basebean : data) {
			CatalogItemAddChargeBean bean = (CatalogItemAddChargeBean) basebean;
			excel.addRow();
			excel.addCell(bean.getItemId());
			excel.addCell(bean.getItemDescription());
			excel.addCell(bean.getHeaderCharge());
			excel.addCell(bean.getLineCharge());
			excel.addCell(bean.getDefaultPrice());
			excel.addCell(bean.getDefaultCurrencyId());
			excel.addCell(bean.getLastUpdated());
			excel.addCell(bean.getUpdateByName());
		}
		return excel;
	}

	public Collection update(AdditionalChargeInputBean input, Collection<CatalogItemAddChargeBean> rows, PersonnelBean user) throws BaseException, Exception {

		Vector errorMessages = new Vector();
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		String errorMsg = "";

		// Call the
		// pkg_catalog_item_add_charge.fx_catalog_item_add_charge
		// procedure
		try {
			for (CatalogItemAddChargeBean row : rows) {
				Collection inArgs = buildProcedureInput(row.getOpsEntityId(), row.getItemId(), row.getHeaderCharge(), row.getLineCharge(), row.getDefaultPrice(), row.getDefaultCurrencyId(), user.getPersonnelIdBigDecimal());
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("pkg_catalog_item_add_charge.p_catalog_item_add_charge Procedure :" + inArgs + outArgs);
				}

				Collection results = procFactory.doProcedure("pkg_catalog_item_add_charge.p_catalog_item_add_charge", inArgs, outArgs);
				getErrorMessages(results, errorMessages);
			}

		}
		catch (Exception e) {
			errorMsg = library.getString("error.db.update");
			errorMessages.add(errorMsg);
			log.error(errorMsg, e);
		}

		return errorMessages;
	}

	private Vector getErrorMessages(Collection results, Vector messages) {
		Iterator iterator = results.iterator();
		String result = null;
		while (iterator.hasNext()) {
			result = (String) iterator.next();
			if (!StringHandler.isBlankString(result)) {
				if (log.isDebugEnabled()) {
					log.debug("Result:" + result);
				}
				messages.add(result);
			}
		}
		return messages;
	}

}
