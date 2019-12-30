package com.tcmis.internal.catalog.process;

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
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.catalog.beans.ChemSynonymViewBean;
import com.tcmis.internal.catalog.beans.ChemicalBean;
import com.tcmis.internal.catalog.factory.ChemSynonymViewBeanFactory;
import com.tcmis.internal.catalog.factory.ChemicalBeanFactory;

/******************************************************************************
 * Process used by CasNumberSearchAction
 * @version 1.0
 *****************************************************************************/

public class CaseNumberSearchProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private DbManager dbManager;

	public CaseNumberSearchProcess(String client) {
		super(client);
		dbManager = new DbManager(getClient());
	}

	public Collection getChemSynonymViewBeanCollection(String searchField, String searchMode, String searchArgument, boolean tradeSecret) throws BaseException, Exception {
		ChemSynonymViewBeanFactory factory = new ChemSynonymViewBeanFactory(dbManager);
		String arg = getSqlString(searchArgument);
		Collection c = factory.select(searchField, searchMode,arg.substring(1, arg.length() - 1), tradeSecret);
		if (log.isDebugEnabled()) {
			log.debug("ChemSynonymViewBean collection size: [" + c.size() + "]; ");
		}

		return c;
	}

	public int insertNewChemical(ChemicalBean chemicalBean) throws BaseException, Exception {
		ChemicalBeanFactory factory = new ChemicalBeanFactory(dbManager);
		if (chemicalBean.isTradeSecret()) {
			int newId = dbManager.getOracleSequence("trade_secret_seq");
			chemicalBean.setCasNumber("TS" + (newId < 10000 ? "0" : "") + newId);
			chemicalBean.setCommonName(chemicalBean.getPreferredName());
			chemicalBean.setPreferredName("Trade Secret (" + chemicalBean.getPreferredName() + ")");
		}
		int result = factory.insert(chemicalBean);
		if (log.isDebugEnabled()) {
			log.debug("new Integer( result ).toString() = [" + new Integer(result).toString() + "]; ");
		}

		return result;
	}

	public ExcelHandler getExcelReport(Collection<ChemSynonymViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();

		String[] headerkeys = { "label.casnumber", "label.ecnumber", "label.preferredname" };

		int[] types = { 0, 0, pw.TYPE_PARAGRAPH };

		int[] widths = { 15, 15, 60 };

		int[] horizAligns = { 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
	//	pw.setColumnDigit(6, 2);
	//	pw.setColumnDigit(7, 2);

		for (ChemSynonymViewBean member : data) {

			pw.addRow();
			pw.addCell(member.getCasNumber());
			pw.addCell(member.getEcNumber());
			pw.addCell(member.getPreferredName());

		}
		return pw;
	}

}
