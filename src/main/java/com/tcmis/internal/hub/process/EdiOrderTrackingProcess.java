package com.tcmis.internal.hub.process;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.EdiOrderTrackingBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process used by EdiOrderTrackingProcess
 * @version 1.0
 *****************************************************************************/

public class EdiOrderTrackingProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public EdiOrderTrackingProcess(String client, String locale) {
		super(client, locale);
	}

	public String updateData (Collection<EdiOrderTrackingBean> data) throws Exception {
		String result = "";
		for (EdiOrderTrackingBean bean : data) {
			log.debug(bean.toString());
		}
		return result;
	}  //end of method

	public String cancelCatalogAddRequest (PersonnelBean personnelBean, EdiOrderTrackingBean inputBean) throws Exception {
		String result = "";
		try {
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("update catalog_add_request_new set");
			query.append(" eval_rejected_comment = 'Cancel per user on EDI Order Error Tracking',eval_rejected_by = ").append(personnelBean.getPersonnelId());
			query.append(", request_status = 7, approval_group_seq = 0");
			query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and request_id = ").append(inputBean.getCatalogAddRequestId());
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
			result = "Unable to cancel this request: "+inputBean.getCatalogAddRequestId();
			throw e;
		}
		return result;
	}  //end of method

	private boolean partExistedInCatalog(EdiOrderTrackingBean inputBean, GenericSqlFactory factory, Connection connection) {
		boolean result = false;
		try {
			StringBuilder query = new StringBuilder("select count(*) from catalog_part_item_group where company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
			query.append(" and catalog_id = '").append(inputBean.getCatalogId()).append("' and cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
			query.append(" and part_group_no = ").append(inputBean.getPartGroupNo()).append(" and status = 'A'");
			String tmpVal = factory.selectSingleValue(query.toString(),connection);
			if (!"0".equals(tmpVal))
				result = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}  //end of method

	public String updateEdiData (PersonnelBean personnelBean, EdiOrderTrackingBean inputBean) throws Exception {
		String result = "";
		DbManager dbManager = new DbManager(getClient());
		Connection connection = dbManager.getConnection();
		try {
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			boolean existingPart = partExistedInCatalog(inputBean,factory,connection);
			StringBuilder query;
			//update catalog add request accordingly
			if (inputBean.getCatalogAddRequestId() != null) {
				//since user selected a part that existed then go ahead and cancel this catalog add request
				query = new StringBuilder("update catalog_add_request_new set cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
				query.append(",part_group_no = ").append(inputBean.getPartGroupNo());
				if (existingPart) {
					query.append(",eval_rejected_comment = 'Part Exists in Catalog',eval_rejected_by = ").append(personnelBean.getPersonnelId());
					query.append(", request_status = 7, approval_group_seq = 0");
				}
				query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and request_id = ").append(inputBean.getCatalogAddRequestId());
				factory.deleteInsertUpdate(query.toString(),connection);
			}
			//update customer_po_pre_stage
			query = new StringBuilder("update customer_po_stage set cat_part_no = '").append(inputBean.getCatPartNo()).append("'");
			query.append(",quantity = ").append(inputBean.getQuantity()).append(",uom = '").append(inputBean.getOrderPartUom()).append("'");
			query.append(",shipto_city = '").append(inputBean.getShiptoCity()).append("'");
			query.append(",status = 'NEW'");
			query.append(" where customer_po_no = '").append(inputBean.getCustomerPoNo()).append("' and customer_po_line_no = '").append(inputBean.getCustomerPoLineNo()).append("'");
			query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			factory.deleteInsertUpdate(query.toString(),connection);
			/*call procedure to reset status so it will pick up on next run
			Vector cin = new Vector();
			cin.add(inputBean.getCompanyId());
			factory.doProcedure(connection,"pkg_dbuy_fix_pre_stage.p_fix_pre_stage_errors", cin);
			Iterator i11 = outputs.iterator();
			while (i11.hasNext()) {
				result  = (String) i11.next();
			}*/

		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	public Collection getShiptoCity(PersonnelBean personnelBean, EdiOrderTrackingBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			results = getCompanyFacilityData(personnelBean,inputBean.getCompanyId());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	private Collection getCompanyFacilityData(PersonnelBean bean, String companyId) throws Exception {
		Collection results = new Vector();
		try {
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new EdiOrderTrackingBean());
			StringBuilder query = new StringBuilder("select distinct c.company_id,c.company_name,ufv.facility_id,ufv.facility_name");
			query.append(" from company c, user_facility_view ufv, edi_shipto_mapping esm ");
			query.append(" where c.company_id = ufv.company_id and ufv.personnel_id = ").append(bean.getPersonnelId());
			if (!StringHandler.isBlankString(companyId))
				query.append(" and ufv.company_id = '").append(companyId).append("'");
			query.append(" and ufv.company_id = esm.company_id and ufv.facility_id = esm.default_facility_id and esm.enable_order_tracking = 'Y'");
			query.append(" order by c.company_name,ufv.facility_name");
			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	public Vector buildCompanyFacilityObj (PersonnelBean personnelBean) throws Exception {
		Vector results = new Vector();
		Vector<EdiOrderTrackingBean> data = (Vector) getCompanyFacilityData(personnelBean,"");
		String currentCompanyId = "";
		for (EdiOrderTrackingBean bean : data) {
			if (currentCompanyId.equals(bean.getCompanyId())) {
				EdiOrderTrackingBean companyBean = (EdiOrderTrackingBean) results.lastElement();
				Vector<EdiOrderTrackingBean> facilityList = (Vector) companyBean.getFacilityList();
				EdiOrderTrackingBean facilityBean = new EdiOrderTrackingBean();
				facilityBean.setFacilityId(bean.getFacilityId());
				facilityBean.setFacilityName(bean.getFacilityName());
				facilityList.add(facilityBean);
			} else {
				EdiOrderTrackingBean companyBean = new EdiOrderTrackingBean();
				companyBean.setCompanyId(bean.getCompanyId());
				companyBean.setCompanyName(bean.getCompanyName());
				Collection facilityList = new Vector();
				EdiOrderTrackingBean facilityBean = new EdiOrderTrackingBean();
				facilityBean.setFacilityId(bean.getFacilityId());
				facilityBean.setFacilityName(bean.getFacilityName());
				facilityList.add(facilityBean);
				companyBean.setFacilityList(facilityList);
				results.add(companyBean);
			}
			currentCompanyId = bean.getCompanyId();
		}
		return results;
	}  	//end of method


	public Collection getSearchResult(EdiOrderTrackingBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			SearchCriteria criteria = new SearchCriteria();
			if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
				criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
			}

			if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
				criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());
			}

			if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
				String mode = inputBean.getSearchMode();
				String field = inputBean.getSearchField();
				if (mode.equals("is")) {
					criteria.addCriterion(field, SearchCriterion.EQUALS, inputBean.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				}

				if (mode.equals("contains")) {
					criteria.addCriterion(field, SearchCriterion.LIKE, inputBean.getSearchArgument(), SearchCriterion.IGNORE_CASE);

				}
			}

			if (inputBean.isDisplayCatalogAddOnly())
				criteria.addCriterion("catalogAddRequestId", SearchCriterion.IS_NOT, null);

			if (inputBean.isDisplayEdiErrorOnly())
				criteria.addCriterion("catalogAddRequestId", SearchCriterion.IS, null);

			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("facilityName");
			sortCriteria.addCriterion("customerPoNo");
			sortCriteria.addCriterion("customerPoLineNo");
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new EdiOrderTrackingBean());
			results = factory.select(criteria,sortCriteria,"edi_order_error_tracking_view");
		}catch (Exception e) {
			log.error(e);
			throw e;
		}

		return results;
	}  //end of method

	public ExcelHandler createExcelReport(EdiOrderTrackingBean inputBean) throws NoDataException, BaseException, Exception {
		Vector<EdiOrderTrackingBean> data = (Vector)getSearchResult(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.company")+":"+inputBean.getCompanyName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputBean.getFacilityName(),1,3);
		pw.addRow();
		String tmpVal = "";
		if (!StringHandler.isBlankString(inputBean.getSearchArgument()))
			tmpVal = inputBean.getSearchFieldName() + " " + inputBean.getSearchModeName() + " "+ inputBean.getSearchArgument();
		pw.addTdRegionBold(library.getString("label.search")+tmpVal,1,3);

		if (inputBean.isDisplayCatalogAddOnly()) {
			pw.addRow();
			pw.addTdRegionBold(library.getString("label.onlydisplaycatalogadd"), 1, 3);
		}
		if (inputBean.isDisplayEdiErrorOnly()) {
			pw.addRow();
			pw.addTdRegionBold(library.getString("label.isOnlyDisplayEdiError"), 1, 3);
		}
		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.ordernumber",
				"label.orderline",
				"label.partnumber",
				"label.description",
				"label.quantity",
				"label.uom",
				"label.company",
				"label.facility",
				"label.shiptocity",
				"label.status",
				"label.statusdetail",
				"label.catalogaddrequestid"
				};
		/*This array defines the type of the excel column.
 		  0 means default depending on the data type. */
		int[] types = {
				0,0,0,pw.TYPE_PARAGRAPH,0,0,0,0,0,0,pw.TYPE_PARAGRAPH,0};
		/*This array defines the default width of the column when the Excel file opens.
 		  0 means the width will be default depending on the data type.*/
		int[] widths = {20,10,20,50,20,20,20,20,20,20,50,20};
		/*This array defines the horizontal alignment of the data in a cell.
 		  0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(EdiOrderTrackingBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getCustomerPoNo());
			pw.addCell(bean.getCustomerPoLineNo());
			pw.addCell(bean.getCatPartNo());
			pw.addCell(bean.getPartShortDesc());
			pw.addCell(bean.getQuantity());
			pw.addCell(bean.getOrderPartUom());
			pw.addCell(bean.getCompanyName());
			pw.addCell(bean.getFacilityName());
			pw.addCell(bean.getShiptoCity());
			pw.addCell(bean.getStatus());
			pw.addCell(bean.getErrorDetail());
			pw.addCell(bean.getCatalogAddRequestId());
		}
		return pw;
	}  //end of method

} //end of class
