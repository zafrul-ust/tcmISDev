package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetMixtureManagementViewBean;
import com.tcmis.client.het.beans.HetMixtureSearchViewBean;
import com.tcmis.client.het.beans.MixtureManagementInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;

public class MixtureManagementProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the Mixture Management
	 * 
	 * @version 1.0
	 * *****************************************************************
	 */

	Log log = LogFactory.getLog(this.getClass());

	public MixtureManagementProcess(String client, Locale locale) {
		super(client, locale);
	}

	public MixtureManagementProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetMixtureManagementViewBean> getSearchResult(MixtureManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new HetMixtureManagementViewBean());
		SearchCriteria criteria = new SearchCriteria("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		if (input.hasSearchArgument()) {
			criteria.addCriterionWithMode(input.getSearchField(), input.getSearchMode(), input.getSearchArgument());
		}

		SortCriteria sort = new SortCriteria("companyId");
		sort.addCriterion("facilityId");
		sort.addCriterion("mixtureId");
		sort.addCriterion("msdsNo");
		sort.addCriterion("materialId");

		return factory.select(criteria, sort, "HET_MIXTURE_MANAGEMENT_VIEW");	}

	@SuppressWarnings("unchecked")
	public Collection<HetMixtureSearchViewBean> getMSDSMfgColl(MixtureManagementInputBean input) throws BaseException {

		factory.setBean(new HetMixtureSearchViewBean());
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());

		if (input.getSearchArgument() != null && input.getSearchArgument().length() > 0) {
			criteria.addCriterionWithMode(input.getSearchField(), input.getSearchMode(), input.getSearchArgument());
		}

		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("customerMsdsNumber");
		return factory.select(criteria, sort, "HET_MIXTURE_SEARCH_VIEW");
	}

	public String updateMethod(Collection<HetMixtureManagementViewBean> beans, PersonnelBean user) throws BaseException {
		String errMsg = "";
		String preRecord = "";
		BigDecimal preMixtureId = null;
		for (HetMixtureManagementViewBean bean : beans) {
			StringBuilder query = null;
			if ("changed".equals(bean.getStatus())) {
				try {
					query = new StringBuilder();
					query.append("update HET_MIXTURE set mixture_name = '").append(bean.getMixtureName());
					query.append("' where company_id = '").append(bean.getCompanyId());
					query.append("' and facility_id = '").append(bean.getFacilityId());
					query.append("' and mixture_id = ").append(bean.getMixtureId()).append("");
					factory.deleteInsertUpdate(query.toString());

					
					query = new StringBuilder();
					query.append("update HET_MIXTURE_MSDS  set separable = '").append(bean.isSeparable() ? "Y" : "N");
					query.append("' where  mixture_id = ").append(bean.getMixtureId());
					query.append(" and msds_no = '").append(bean.getMsdsNo()).append("'");
					factory.deleteInsertUpdate(query.toString());

				}
				catch (Exception e) {
					e.printStackTrace();
					errMsg += "error updating mixture_name:" + bean.getMixtureName() + "\n";
				}
			}
			else if ("newMixture".equals(bean.getStatus())) {
				try {
					BigDecimal mixtureId = new BigDecimal(dbManager.getOracleSequence("HET_MISC_SEQ"));
					query = new StringBuilder();
					query.append("insert into HET_MIXTURE (company_id, facility_id, mixture_id, mixture_name) values ('");
					query.append(bean.getCompanyId()).append("', '").append(bean.getFacilityId()).append("', ");
					query.append(mixtureId).append(", ").append(SqlHandler.delimitString(bean.getMixtureName())).append(")");

					factory.deleteInsertUpdate(query.toString());

					preMixtureId = mixtureId;
					query = new StringBuilder();
					String separable = bean.isSeparable() ? "Y" : "N";
					query.append("insert into HET_MIXTURE_MSDS (mixture_id, msds_no, separable) values (");
					query.append(mixtureId).append(", '").append(bean.getMsdsNo()).append("', '");
					query.append(separable).append("')");

					factory.deleteInsertUpdate(query.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
					errMsg += "error adding mixture_name:" + bean.getMixtureName() + "\n";
				}
			}
			else if ("newMsds".equals(bean.getStatus())) {
				BigDecimal mixtureId = bean.getMixtureId();
				if (bean.getMixtureId() == null)
					mixtureId = preMixtureId;
				try {
					query = new StringBuilder();
					String separable = bean.isSeparable() ? "Y" : "N";
					query.append("insert into HET_MIXTURE_MSDS (mixture_id, msds_no, separable) values (");
					query.append(mixtureId).append(", '").append(bean.getMsdsNo()).append("', '");
					query.append(separable).append("')");

					factory.deleteInsertUpdate(query.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
					errMsg += "error adding mixture_name:" + bean.getMixtureName() + "\n";
				}
			}
			else if ("deleteByMsds".equals(bean.getStatus()) && bean.getOriginalMixtureId() != null) {
				try {
					deleteMsds(bean.getMixtureId(), bean.getMsdsNo());
				}
				catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting mixture_name:" + bean.getMixtureName() + "\n";
				}
			}
			else if ("deleteByMixture".equals(bean.getStatus()) && bean.getOriginalMixtureId() != null) {
				try {
					deleteMsds(bean.getMixtureId(), bean.getMsdsNo());
				}
				catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting mixture_name:" + bean.getMixtureName() + "\n";
				}

				String currentRecord = bean.getCompanyId() + bean.getFacilityId() + bean.getMixtureId();
				if (!currentRecord.equals(preRecord)) {
					try {
						//	log.debug("currentRecord:"+currentRecord+"    preRecord:"+preRecord);
						query = new StringBuilder();
						query.append("delete from HET_MIXTURE ");
						query.append("where company_id = '").append(bean.getCompanyId());
						query.append("' and facility_id = '").append(bean.getFacilityId());
						query.append("' and mixture_id = ").append(bean.getMixtureId()).append("");

						factory.deleteInsertUpdate(query.toString());
					}
					catch (Exception e) {
						e.printStackTrace();
						errMsg += "error deleting mixture_name:" + bean.getMixtureName() + "\n";
					}
					preRecord = currentRecord;
				}
			}

		}
		return errMsg;
	}

	private void deleteMsds(BigDecimal mixtureId, String msdsNo) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);

		StringBuilder query = new StringBuilder();

		if (msdsNo != null) {
			query.append("delete from HET_MIXTURE_MSDS  where mixture_id = ").append(mixtureId);
			query.append(" and msds_no = '").append(msdsNo).append("'");
		}

		genericSqlFactory.deleteInsertUpdate(query.toString());

	}

	public ExcelHandler getExcelReport(Collection<HetMixtureManagementViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.mixturename", "label.allowseperateusage", "label.msds", "label.manufacturer", "label.materialdescription" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 30, 22, 12, 20, 30 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);*/

		for (HetMixtureManagementViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getMixtureName());
			pw.addCell(member.isSeparable() ? "Y" : "N");
			pw.addCell(member.getMsdsNo());
			pw.addCell(member.getMfgDesc());
			pw.addCell(member.getMaterialDesc());
		}
		return pw;
	}
}
