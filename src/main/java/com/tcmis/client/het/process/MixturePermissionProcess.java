package com.tcmis.client.het.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.FxMixtureIdMgmtSearchBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

public class MixturePermissionProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the Mixture Permissions
	 * 
	 * @version 1.0
	 * *****************************************************************
	 */

	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public MixturePermissionProcess(String client, Locale locale) {
		super(client, locale);
	}

	public MixturePermissionProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<FxMixtureIdMgmtSearchBean> getSearchResult(PermitManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new FxMixtureIdMgmtSearchBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.FX_MIXTURE_ID_MGMT_SEARCH ('");
		query.append(user.getCompanyId()).append("','");
		query.append(input.getFacilityId()).append("',");
		query.append(StringHandler.nullIfEmpty(input.getAreaId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getBuildingId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getWorkArea()));

		query.append(")) order by mixture_Name, area_name, building_name, application_desc");

		return factory.selectQuery(query.toString());
	}

	public String update(Collection<FxMixtureIdMgmtSearchBean> beans, PersonnelBean user) throws BaseException, Exception {
		String errMsg = "";
		String preRecord = "";
		for (FxMixtureIdMgmtSearchBean bean : beans) {
			StringBuilder query = new StringBuilder();
			if("changed".equals(bean.getStatus())) {
				try {
					query.append("update HET_MIXTURE_AREA_BLDG_APP set area_id = ").append(getSqlString(bean.getAreaId()));
					query.append(", building_id = ").append(getSqlString(bean.getBuildingId()));
					query.append(", application_id = ").append(getSqlString(bean.getApplicationId()));
					query.append(" where mixture_id = ").append(bean.getMixtureId());
					query.append(" and area_id = ").append(bean.getOriginalAreaId());
					query.append(" and building_id = ").append(bean.getOriginalBuildingId());
					query.append(" and application_id = ").append(bean.getOriginalApplicationId()).append("");

					factory.deleteInsertUpdate(query.toString());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error updating mixture_id:" + bean.getMixtureId();
				}
			}  else if("new".equals(bean.getStatus()) && bean.getAreaId() != null) {
				try {
					query = new StringBuilder();
					query.append("insert into HET_MIXTURE_AREA_BLDG_APP (mixture_id, area_id, building_id, application_id) values (");
					query.append(bean.getMixtureId()).append(", ").append(bean.getAreaId()).append(", ").append(bean.getBuildingId()).append(", ").append(bean.getApplicationId()).append(")");

					factory.deleteInsertUpdate(query.toString());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error adding mixture_name:" + bean.getMixtureName()+ "\n";
				}
			} else if(bean.getStatus().length() > 6 && "delete".equals(bean.getStatus().substring(0, 6)) && bean.getAreaId() != null) {
				try {
					query = new StringBuilder();
					query.append("delete from HET_MIXTURE_AREA_BLDG_APP ");
					query.append("where mixture_id = ").append(bean.getMixtureId());
					query.append(" and area_id = ").append(bean.getOriginalAreaId());
					query.append(" and building_id = ").append(bean.getOriginalBuildingId());
					query.append(" and application_id = ").append(bean.getOriginalApplicationId()).append("");

					factory.deleteInsertUpdate(query.toString());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting mixture_name:" + bean.getMixtureName()+ "\n";
				}
			}

		}
		return errMsg;
	}

	public ExcelHandler getExcelReport(Collection<FxMixtureIdMgmtSearchBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.mixturename", "label.area", "label.building", "label.workarea" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 30, 15, 15 , 35  };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);*/

		for (FxMixtureIdMgmtSearchBean member : data) {
			pw.addRow();
			pw.addCell(member.getMixtureName());
			if(member.getAreaId() == null)
				pw.addCell(library.getString("label.none"));
			else
				pw.addCell(member.getAreaName());

			if(member.getBuildingId() == null)
				pw.addCell(library.getString("label.none"));
			else if(member.getBuildingId().intValue() == -1)
				pw.addCell(library.getString("label.all"));
			else
				pw.addCell(member.getBuildingName());

			if(member.getApplicationId() == null)
				pw.addCell(library.getString("label.none"));
			else if(member.getApplicationId().intValue() == -1)
				pw.addCell(library.getString("label.all"));
			else
				pw.addCell(member.getApplicationDesc());
		}
		return pw;
	}
}
