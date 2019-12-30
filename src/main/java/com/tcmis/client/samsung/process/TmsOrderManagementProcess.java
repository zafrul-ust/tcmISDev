package com.tcmis.client.samsung.process;

import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.samsung.beans.TmsOrderManagementBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process used by TmsOrderManagementProcess
 * @version 1.0
 *****************************************************************************/

public class TmsOrderManagementProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;
	protected Connection connection = null;
	protected Vector validSearchField = new Vector();

	public TmsOrderManagementProcess(String client, String locale) {
		super(client, locale);
		init(new TmsOrderManagementBean());
	}

	private void init(TmsOrderManagementBean bean) {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager, bean);
			validSearchField.addElement("x.customer_requisition_number");
			validSearchField.addElement("x.pr_number");
			validSearchField.addElement("x.fac_part_no");
			validSearchField.addElement("x.application");
			validSearchField.addElement("x.allocate_by_mfg_lot");
			validSearchField.addElement("x.notes");
		}
		catch (Exception ex) {
			log.error("Error initializing TmsOrderManagementProcess", ex);
		}
	}

	public String cancelReservation(TmsOrderManagementBean inputBean, PersonnelBean personnelBean) throws Exception {
		String result = "";
		try {
			StringBuilder query = new StringBuilder("update request_line_item set release_date = null,cancel_status = 'canceled',cancel_authorizer = ").append(personnelBean.getPersonnelId());
			query.append(",request_line_status = 'Cancelled',category_status = 'Cancelled',last_updated = sysdate, last_updated_by = ").append(personnelBean.getPersonnelId());
			query.append(" where pr_number = ").append(inputBean.getPrNumber());
			if (inputBean.isCancelLineReservation())
				query.append(" and line_item = ").append(inputBean.getLineItem());
			query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}  //end of method

	public String updateData(TmsOrderManagementBean inputBean, Collection<TmsOrderManagementBean> data, PersonnelBean personnelBean) throws Exception {
		String result = "";
		connection = dbManager.getConnection();
		try {
			Vector mrToReprocessColl = new Vector();
			for (TmsOrderManagementBean bean : data) {
				if (bean.isUpdateRow()) {
					//first save mr line
					String tmpVal = saveMrLine(bean,personnelBean);
					if (StringHandler.isBlankString(tmpVal)) {
						//keep track of MR for reprocess
						if (!mrToReprocessColl.contains(bean.getPrNumber()))
							mrToReprocessColl.add(bean.getPrNumber());
					}else {
						if (!StringHandler.isBlankString(result))
							result += "\n";
						result += tmpVal;
					}
				}
			}
			//reprocess
			if (inputBean.isUpdateAndReprocess()) {
				for (int i = 0; i < mrToReprocessColl.size(); i++) {
					if (!StringHandler.isBlankString(result))
						result += "\n";
					result += reprocessMr((BigDecimal)mrToReprocessColl.elementAt(i),personnelBean);
				}
			}
		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	private String saveMrLine(TmsOrderManagementBean bean, PersonnelBean personnelBean) {
		String result = "";
		try {
			StringBuilder query = new StringBuilder("update request_line_item set fac_part_no = ").append(SqlHandler.delimitString(bean.getCatPartNo()));
			query.append(", quantity = ").append(bean.getQuantity());
			query.append(", required_datetime = ").append(DateHandler.getOracleToDateFunction(bean.getRequiredDatetime()));
			if (bean.getAllocateAfter() != null)
				query.append(", allocate_after = ").append(DateHandler.getOracleToDateFunction(bean.getAllocateAfter()));
			query.append(", application = ").append(SqlHandler.delimitString(bean.getApplication()));
			query.append(", delivery_point = ").append(SqlHandler.delimitString(bean.getApplication()));
			query.append(", allocate_by_mfg_lot = ").append(SqlHandler.delimitString(bean.getAllocateByMfgLot()));
			query.append(", last_updated = sysdate, last_updated_by = ").append(personnelBean.getPersonnelId());
			query.append(" where pr_number = ").append(bean.getPrNumber()).append(" and line_item = ").append(bean.getLineItem());
			factory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = "Error occurred while trying to update PR Number: "+bean.getPrNumber()+" and line: "+bean.getLineItem();
		}
		return result;
	} //end of method

	private String reprocessMr(BigDecimal prNumber, PersonnelBean personnelBean) {
		String result = "";
		try {
			OrderFulfillmentProcess orderFulfillmentProcess = new OrderFulfillmentProcess(this.getClient(),false);
			orderFulfillmentProcess.setFactoryConnection(factory,connection);
			StringBuilder query = new StringBuilder("select line_item from request_line_item where pr_number = ").append(prNumber);
			query.append(" order by line_item");
			Iterator iter = factory.selectQuery(query.toString(),connection).iterator();
			Hashtable emsg = new Hashtable();
			boolean hasError = false;
			while (iter.hasNext()) {
				TmsOrderManagementBean bean = (TmsOrderManagementBean)iter.next();
				StringBuilder internalNote = new StringBuilder("");
				if (orderFulfillmentProcess.validateMrData(prNumber, new BigDecimal(bean.getLineItem()), emsg, internalNote)) {
					orderFulfillmentProcess.updateMrLineStatus(prNumber, new BigDecimal(bean.getLineItem()), "In Progress", "Open",internalNote.toString(),personnelBean.getPersonnelIdBigDecimal());
				} else {
					orderFulfillmentProcess.updateMrLineStatus(prNumber, new BigDecimal(bean.getLineItem()), "Pending Use Approval", "Open",internalNote.toString(),personnelBean.getPersonnelIdBigDecimal());
					hasError = true;
				}
			}
			if (hasError)
				result = "There are still errors on MR: "+prNumber+" please look at Notes column for more details.";
			else {
				MaterialRequestInputBean materialRequestInputBean = new MaterialRequestInputBean();
				MaterialRequestProcess materialRequestProcess = new MaterialRequestProcess(this.getClient(), this.getLocale());
				materialRequestProcess.setFactoryConnection(factory, connection);
				materialRequestInputBean.setPrNumber(prNumber);
				materialRequestProcess.releaseMrToHaas(materialRequestInputBean, personnelBean);
			}
			//setting factory to initial bean because it was switch to another bean from above methods
			factory.setBeanObject(new TmsOrderManagementBean());
		}catch (Exception e) {
			log.error(e);
			result = "Error occurred while trying to reprocess PR Number: "+prNumber;
		}
		return result;
	} //end of method

	public Collection getFacilityApplicationDropDown(TmsOrderManagementBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			StringBuilder query = new StringBuilder("select application,application_desc from fac_loc_app where company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId())).append(" and application <> 'All' and application like 'RC%'");
			query.append(" order by application_desc");
			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	public Collection getFacilityDropDown(TmsOrderManagementBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			StringBuilder query = new StringBuilder("select company_id,facility_id,facility_name from");
			if ("TCM_OPS".equals(this.getClient()))
				query.append(" customer.facility");
			else
				query.append(" facility");
			query.append(" where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" order by facility_name");
			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	public Collection getSearchResult(TmsOrderManagementBean inputBean) throws Exception {
		Collection results = new Vector();
		try {
			StringBuilder whereClause = new StringBuilder("");
			if (inputBean.getOrderFromDate() != null) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" pr.request_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getOrderFromDate()));
			}
			if (inputBean.getOrderToDate() != null) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" pr.request_date <= ").append(DateHandler.getOracleToDateFunction(inputBean.getOrderToDate()));
			}

			if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
				String mode = inputBean.getSearchMode();
				String field = inputBean.getSearchField();
				if (!validSearchField.contains(inputBean.getSearchField()))
					field = "x.customer_requisition_number";
				if (mode.equals("is")) {
					if (!StringHandler.isBlankString(whereClause.toString()))
						whereClause.append(" and");
					whereClause.append(" ").append(field).append(" = ").append(SqlHandler.delimitString(inputBean.getSearchArgument()));
				}

				if (mode.equals("contains")) {
					if (!StringHandler.isBlankString(whereClause.toString()))
						whereClause.append(" and");
					whereClause.append(" lower(").append(field).append(") like lower('%").append(SqlHandler.validQuery(inputBean.getSearchArgument())).append("%')");
				}
			}

			if (inputBean.getOnlyOpenRequest()) {
				if (!StringHandler.isBlankString(whereClause.toString()))
					whereClause.append(" and");
				whereClause.append(" x.category_status = 'Open'");
			}

			StringBuilder query = new StringBuilder("select rli.company_id,rli.facility_id,fx_facility_name(rli.facility_id,rli.company_id) facility_name,rli.customer_requisition_number,rli.pr_number,rli.line_item");
			query.append(",rli.quantity,rli.fac_part_no cat_part_no,rli.example_packaging packaging,rli.allocate_by_mfg_lot,rli.internal_note,rli.request_line_status,application,");
			query.append("rli.required_datetime,rli.last_updated,rli.last_updated_by,fx_personnel_id_to_name(rli.last_updated_by) last_updated_by_name");
			query.append(",pr.request_date,fx_application_desc(rli.facility_id,rli.application,rli.company_id) application_desc,rli.notes,rli.allocate_after");
			query.append(" from request_line_item rli, purchase_request pr where rli.company_id = pr.company_id and rli.pr_number = pr.pr_number");
			query.append(" and rli.company_id = '").append(inputBean.getCompanyId()).append("' and rli.facility_id = '").append(inputBean.getFacilityId()).append("' and rli.pr_number in (");
			query.append("select pr.pr_number from purchase_request pr, request_line_item x where pr.company_id = '").append(inputBean.getCompanyId()).append("' and pr.facility_id = '").append(inputBean.getFacilityId()).append("'");
			query.append(" and pr.company_id = x.company_id and pr.pr_number = x.pr_number");
			query.append(" and x.customer_requisition_number is not null");
			if (!StringHandler.isBlankString(whereClause.toString()))
				query.append(" and").append(whereClause);
			query.append(")");
			query.append(" order by customer_requisition_number,pr_number,to_number(line_item)");
			results = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return results;
	}  //end of method

	public ExcelHandler createExcelReport(TmsOrderManagementBean inputBean) throws NoDataException, BaseException, Exception {
		Vector<TmsOrderManagementBean> data = (Vector)getSearchResult(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputBean.getFacilityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.orderedbetween")+":"+StringHandler.emptyIfNull(inputBean.getOrderFromDate())+" "+StringHandler.emptyIfNull(inputBean.getOrderToDate()),1,3);
		pw.addRow();
		String tmpVal = "";
		if (!StringHandler.isBlankString(inputBean.getSearchArgument()))
			tmpVal = inputBean.getSearchFieldName() + " " + inputBean.getSearchModeName() + " "+ inputBean.getSearchArgument();
		pw.addTdRegionBold(library.getString("label.search ")+tmpVal,1,3);
		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.reservationnumber",
				"label.mrnumber",
				"label.line",
				"label.status",
				"label.partnumber",
				"label.packaging",
				"label.quantity",
				"label.dateneeded",
				"label.deliverto",
				"label.lotnumber",
				"label.notes",
				"label.customernotes",
				"label.dateordered",
				"label.lastupdated",
				"label.lastupdatedby",
				"label.datetoallocate"
				};
		/*This array defines the type of the excel column.
 		  0 means default depending on the data type. */
		int[] types = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		/*This array defines the default width of the column when the Excel file opens.
 		  0 means the width will be default depending on the data type.*/
		int[] widths = {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		/*This array defines the horizontal alignment of the data in a cell.
 		  0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(TmsOrderManagementBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getCustomerRequisitionNumber());
			pw.addCell(bean.getPrNumber());
			pw.addCell(bean.getLineItem());
			if ("Pending Use Approval".equals(bean.getRequestLineStatus()))
				pw.addCell("ERROR");
			else
				pw.addCell(bean.getRequestLineStatus());
			pw.addCell(bean.getCatPartNo());
			pw.addCell(bean.getPackaging());
			pw.addCell(bean.getQuantity());
			pw.addCell(bean.getRequestDate());
			pw.addCell(bean.getApplicationDesc());
			pw.addCell(bean.getAllocateByMfgLot());
			pw.addCell(bean.getInternalNote());
			pw.addCell(bean.getNotes());
			pw.addCell(bean.getSubmittedDate());
			pw.addCell(bean.getLastUpdated());
			pw.addCell(bean.getLastUpdatedByName());
			pw.addCell(bean.getAllocateAfter());
		}
		return pw;
	}  //end of method

} //end of class
