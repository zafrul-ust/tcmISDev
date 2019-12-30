package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.CabinetManagementBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class MSDSsUsedAtLocationsProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public MSDSsUsedAtLocationsProcess(String client) {
		super(client);
	}

	public MSDSsUsedAtLocationsProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<CabinetManagementBean> getSearchResults(WorkAreaSearchTemplateInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetManagementBean());

        StringBuilder areaIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getAreaId())) {
            areaIdString.append(bean.getAreaId().replace("|",","));
        }
        StringBuilder buildingIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getBuildingId())) {
            buildingIdString.append(bean.getBuildingId().replace("|",","));
        }
        StringBuilder deptIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getDeptId())) {
            String[] deptIdArray =  bean.getDeptId().split("\\|");
            for (int i = 0; i < deptIdArray.length; i++) {
                if (deptIdArray[i].length() > 0) {
                    //don't add the commas at the end if this is the last element
                    if (i == deptIdArray.length -1) {
                        deptIdString.append(getSqlString(deptIdArray[i]));
                    } else {
                        deptIdString.append(getSqlString(deptIdArray[i])).append(",");
                    }
                }
            }
        }
        StringBuilder cabinetIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getApplicationId())) {
            cabinetIdString.append(bean.getApplicationId().replace("|",","));
        }
        StringBuilder countTypeString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getCountTypeArray())) {
            String[] countTypeArray =  bean.getCountTypeArray().split("\\|");
            for (int i = 0; i < countTypeArray.length; i++) {
                if (countTypeArray[i].length() > 0) {
                    //don't add the commas at the end if this is the last element
                    if (i == countTypeArray.length -1) {
                        countTypeString.append(getSqlString(countTypeArray[i]));
                    } else {
                        countTypeString.append(getSqlString(countTypeArray[i])).append(",");
                    }
                }
            }
        }

        StringBuilder query = new StringBuilder("select * from table (PKG_PART_HISTORY.fx_cabinet_part_history('");
		query.append(bean.getCompanyId()).append("', '");
		query.append(StringHandler.emptyIfNull(bean.getFacilityId())).append("','");
        query.append(StringHandler.emptyIfNull(cabinetIdString)).append("','");
		query.append(StringHandler.emptyIfNull(bean.getReportingEntityId())).append("','");
        query.append(StringHandler.emptyIfNull(areaIdString)).append("','");
        query.append(StringHandler.emptyIfNull(buildingIdString)).append("',");
        query.append((StringHandler.isBlankString(deptIdString.toString())?"''":deptIdString)).append(",'");//add getSqlString back in for multi select see Cabinet Management Definition Process
        query.append(StringHandler.emptyIfNull(bean.getItemOrPart())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriterion())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriteria())).append("',");
        query.append((StringHandler.isBlankString(countTypeString.toString())?"''":countTypeString));//add getSqlString back in for multi select see Cabinet Management Definition Process
		query.append("))");
		return factory.selectQuery(query.toString());
	}

	public ExcelHandler getExcelReport(Collection<CabinetManagementBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {"label.facility","label.area","label.building","label.workarea","label.department","label.counttype",
		"label.activationdate","label.inactivationdate","label.catalog","label.partnumber",
		"label.item","label.containersize","label.averagerp","label.maxsl","label.msdsnumber",
		"label.description","label.unit","label.averageamount","label.maxamount"
		};
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0, 0, 
		pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0, 0,
		0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0,
		pw.TYPE_PARAGRAPH, 0,  pw.TYPE_NUMBER,  pw.TYPE_NUMBER};

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 0, 0, 0, 0, 0, 
				0, 0, 0, 0,
				0, 0, 0, 0, 0, 
				20, 0, 0, 0
				};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 
			    0, 0, 0, 0,
				0, 0, 0, 0, 0, 
				20, 0, 0, 0
				};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);

		for (CabinetManagementBean member : data) {

			pw.addRow();
			pw.addCell(member.getFacilityName());
			pw.addCell(member.getAreaName());
			pw.addCell(member.getBuildingName());
			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getDeptName());
			String countType = member.getCountType();
			if(!StringHandler.isBlankString(countType))
				if(countType.equalsIgnoreCase("Counted"))
					pw.addCell(library.getString("label.counted"));	
				else if(countType.equalsIgnoreCase("Not Counted"))
					pw.addCell(library.getString("label.notcounted"));
				else if(countType.equalsIgnoreCase("Nonmanaged"))
					pw.addCell(library.getString("label.nonmanaged"));
				else
					pw.addCell("");
			else
				pw.addCell("");
			pw.addCell(member.getActivationDate());
			pw.addCell(member.getInactivationDate());
			pw.addCell(member.getCatalogDesc());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getContainerSize());
			pw.addCell(member.getReorderPoint());
			pw.addCell(member.getStockingLevel());
			pw.addCell(member.getMsdsId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getSizeUnit());
			pw.addCell(member.getAvgAmount());
			pw.addCell(member.getMaxAmount());
		}
		return pw;
	}
}
