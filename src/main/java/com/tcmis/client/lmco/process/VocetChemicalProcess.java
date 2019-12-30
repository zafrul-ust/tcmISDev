package com.tcmis.client.lmco.process;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.lmco.beans.VocetCategoryBean;
import com.tcmis.client.lmco.beans.VocetChemicalSearchViewBean;
import com.tcmis.client.lmco.beans.VocetSourceBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;

/**
 * ******************************************************************
 * Process for the Vocet Chemical
 * 
 * @version 1.0
 * *****************************************************************
 */

public class VocetChemicalProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	public VocetChemicalProcess(String client, Locale locale) {
		super(client, locale);
	}

	public VocetChemicalProcess(String client) {
		super(client);
	}
	
	public Collection<VocetCategoryBean> getVocetCategoryColl() throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		
		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("facilityId,vocetCategoryDesc");
				
		return factory.setBean(new VocetCategoryBean()).select(criteria, sort, "vocet_category ");
	}
	
	public Collection<VocetSourceBean> getVocetSourceColl() throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		
		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("facilityId,vocetSourceDesc");
				
		return factory.setBean(new VocetSourceBean()).select(criteria, sort, "vocet_source");
	}

    public Collection<VocetChemicalSearchViewBean> getSearchResult(VocetChemicalSearchViewBean input,PersonnelBean personnelBean) throws BaseException {
        StringBuilder query = new StringBuilder("select * from table (pkg_vocet_chemical.fx_vocet_search('");
        query.append(StringHandler.emptyIfNull(personnelBean.getCompanyId()));
        query.append("','");
        query.append(StringHandler.emptyIfNull(input.getFacilityId()));
		query.append("','");
        query.append(StringHandler.emptyIfNull(input.getSearchField()));
		query.append("','");
        query.append(StringHandler.emptyIfNull(input.getMatchType()));
		query.append("'");
        if ("in csv list".equalsIgnoreCase(input.getMatchType())) {
            //if number field
            if ("material_id".equals(input.getSearchField())) {
                query.append(",'").append(SqlHandler.validQuery(input.getSearchText())).append("'");
            }else {
                //string fields
                String[] tmpString = input.getSearchText().split(",");
                StringBuilder inList = new StringBuilder("");
                for (int i = 0; i < tmpString.length; i++) {
                    if (i == 0) {
                        inList.append("").append(SqlHandler.delimitString(tmpString[i]));
                    }else {
                        inList.append(",").append(SqlHandler.delimitString(tmpString[i]));
                    }
                }
                query.append(",").append(SqlHandler.delimitString(inList.toString()));
            }
        }else {
            query.append(",").append(SqlHandler.delimitString(input.getSearchText()));
        }
		query.append(",'");
		query.append(StringHandler.emptyIfNull(input.getVocetCategoryId()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(input.getVocetSourceId()));
		query.append("',");
        if (input.getMaxData() != null) {
            query.append(input.getMaxData());
        }else {
            query.append("''");
        }
        if (input.getUpdatedBy() != null) {
            query.append(",").append(input.getUpdatedBy());
        }else {
            query.append(",''");
        }

        query.append(",'").append(StringHandler.emptyIfNull(input.getEntryType())).append("',").append(input.getUploadSeqId());
        
        String startDateString = null;
        if(null!=input.getEntryStartDate())
        	startDateString = "TO_DATE('" + dateFormatter.format( input.getEntryStartDate()) + "', 'MM/DD/RRRR hh24:mi:ss')";
        
        String endDateString = null;
        if(null!=input.getEntryEndDate())
        	endDateString = "TO_DATE('" + dateFormatter.format( input.getEntryEndDate()) + "', 'MM/DD/RRRR hh24:mi:ss')";
        
        query.append(",").append(startDateString).append(",").append(endDateString).append("))");

		factory.setBeanObject(new VocetChemicalSearchViewBean());
		return factory.selectQuery(query.toString());

	}


    public String update(Collection<VocetChemicalSearchViewBean> inputLines, VocetChemicalSearchViewBean input, PersonnelBean personnelBean) throws BaseException {
		String errorMsg = null;
		Connection connection = dbManager.getConnection();
		Collection inArgs = null;
		try {
			for (VocetChemicalSearchViewBean inputBean : inputLines) {
				if("Y".equals(inputBean.getChanged())) {

					inArgs = new Vector(7);
					inArgs.add(inputBean.getCompanyId());
					inArgs.add(inputBean.getFacilityId());
					inArgs.add(inputBean.getCasNumber());
					inArgs.add(inputBean.getVocetSourceId());
					inArgs.add(inputBean.getVocetCategoryId());
					inArgs.add(inputBean.getShortTermEsl());
					inArgs.add(inputBean.getLongTermEsl());
					inArgs.add(personnelBean.getPersonnelId());
					inArgs.add("M");
					
					Vector outArgs = new Vector();

					Vector error = (Vector) factory.doProcedure(connection,"pkg_vocet_chemical.p_upsert ", inArgs, outArgs);

				}
			}
		}
		catch (Exception e) {
			errorMsg = "Error upsert msds ";
		}
		finally {
			dbManager.returnConnection(connection);
		}

		return errorMsg;
	}
	
	public ExcelHandler getExcelReport(Collection<VocetChemicalSearchViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.casno", "label.chemicalname", "label.source", "label.category", "label.shorttermesl", "label.longetermesl", "label.enteredby", "label.entrydate", "label.entrytype", "cyclecountresults.label.uploadid"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0};

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (VocetChemicalSearchViewBean member : data) {
			String tmpEntryType = member.getEntryType();
            if ("I".equals(member.getEntryType())) {
                tmpEntryType = library.getString("label.import");
            }
            if ("M".equals(member.getEntryType())) {
                tmpEntryType = library.getString("label.manual");
            }
			
			pw.addRow();
			pw.addCell(member.getCasNumber());
			pw.addCell(member.getPreferredName());
            pw.addCell(member.getVocetSourceId());
            pw.addCell(member.getVocetCategoryId());
			pw.addCell(member.getShortTermEsl());
			pw.addCell(member.getLongTermEsl());
			pw.addCell(member.getUpdatedByName());
			pw.addCell(member.getUpdatedOn());
			pw.addCell(tmpEntryType);
			pw.addCell(member.getUploadSeqId());
		}
		return pw;
	}

}