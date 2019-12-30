package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Vector;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import com.tcmis.internal.catalog.beans.CatalogQueueProblemTypeBean;
import com.tcmis.internal.catalog.beans.ProblemQueueReasonInputBean;

import radian.tcmis.common.util.BeanHandler;
import radian.tcmis.common.util.SqlHandler;

public class ProblemQueueReasonProcess extends CatalogVendorProcess {

	public ProblemQueueReasonProcess(BigDecimal personnelId, String client) {
		super(personnelId, client);
	}

	public Collection<CatalogQueueProblemTypeBean> getProblemTypes(ProblemQueueReasonInputBean input) throws BaseException {
		String query = "select v.* from vv_queue_problem_type v, catalog_task_problem_type t" 
				+ " where t.task = " + SqlHandler.delimitString(input.getTask())
				+ " and t.status = 'A'"
				+ " and t.problem_type = v.problem_type"
				+ " order by v.display_order,v.problem_type_desc";
		
		return factory.setBean(new CatalogQueueProblemTypeBean()).selectQuery(query);
	}

	private void createProblemQueue(ProblemQueueReasonInputBean input, PersonnelBean user, Connection conn) throws BaseException {
		try {
			String whereClause = "";
			//insert record into catalog_queue_problem
			String problemInsertStmt = "insert into catalog_queue_problem "
					+ "(q_id, problem_type, comments, reported_by, insert_date"
					+ ", assigned_to, assigned_date, supplier_approved_by, supplier_approved_date, status)"
					+ " (select"
					+ " q_id"
					+ ", " + SqlHandler.delimitString(input.getProblemType()) + " problem_type"
					+ ", " + SqlHandler.delimitString(input.getComments()) + " comments"
					+ ", " + user.getPersonnelIdBigDecimal() + " reported_by"
					+ ", sysdate insert_date"
					+ ", assigned_to"
					+ ", assigned_date"
					+ ", supplier_approved_by"
					+ ", supplier_approved_date"
					+ ", status"
					+ " from catalog_queue where";

			if (input.hasCatalogAddRequestId())
				whereClause += " catalog_add_request_id = " + input.getCatalogAddRequestId() + " and task = '" + input.getTask() + "'";
			else
				whereClause += " q_id = " + input.getqId();
			//put it together
			problemInsertStmt += whereClause + ")";
			factory.deleteInsertUpdate(problemInsertStmt, conn);
		}catch (Exception e) {
			throw new BaseException(e);
		}
	} //end of method

	@SuppressWarnings("unchecked")
	private int updateCatalogQueue(ProblemQueueReasonInputBean input, String assignedTo, Connection conn) throws BaseException {
		int rowsUpdated = 0;
		Vector<ProblemQueueReasonInputBean> queueColl;
		
		if (input.hasCatalogAddRequestId()) {
			StringBuilder query = new StringBuilder("select q_id from catalog_queue");
			query.append(" where catalog_add_request_id = ").append(input.getCatalogAddRequestId());
			query.append(" and task = ").append(SqlHandler.delimitString(input.getTask()));
			query.append(" and status not in ('Closed', 'Approved', 'Reversed')");
			queueColl = (Vector<ProblemQueueReasonInputBean>) factory.setBean(new ProblemQueueReasonInputBean()).selectQuery(query.toString(), conn);
		} else {
			queueColl = new Vector<ProblemQueueReasonInputBean>();
			queueColl.add(input);
		}
		
		for (ProblemQueueReasonInputBean bean : queueColl) {
			//remove try-catch block since we already throw error
			StringBuilder queueUpdateStmt = new StringBuilder("update catalog_queue set");
			if (input.isReAssignToVendor()) {//when Master Data return the work to vendor
				if (StringHandler.isBlankString(assignedTo)) {
					queueUpdateStmt.append(" status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_OPEN));
					queueUpdateStmt.append(", assigned_to = null");
					queueUpdateStmt.append(", assigned_date = null");
				} else {
					queueUpdateStmt.append(" status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_ASSIGNED));
					queueUpdateStmt.append(", assigned_to = ").append(new BigDecimal(assignedTo));
					queueUpdateStmt.append(", assigned_date = sysdate");
				}
				queueUpdateStmt.append(", supplier = alt_supplier");
				queueUpdateStmt.append(", alt_supplier = null");
			} else if (input.isReAssignToCatalog()) {//when Master Data take the work back
				queueUpdateStmt.append(" status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_ASSIGNED));
				queueUpdateStmt.append(", assigned_to = ").append(new BigDecimal(assignedTo));
				queueUpdateStmt.append(", assigned_date = sysdate");
				queueUpdateStmt.append(", supplier = ").append(CatalogQueueBean.WESCO_SUPPLIER_ID);
				queueUpdateStmt.append(", alt_supplier = supplier");
			} else if (input.isSendEmailToCatalog()) {//when problem is reported to Catalog
				queueUpdateStmt.append(" status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_OPEN));
				queueUpdateStmt.append(", assigned_to = null");
				queueUpdateStmt.append(", assigned_date = null");
				queueUpdateStmt.append(", supplier = ").append(CatalogQueueBean.WESCO_SUPPLIER_ID);
				queueUpdateStmt.append(", alt_supplier = supplier");
			} else {//when problem is reported to assignee
				queueUpdateStmt.append(" status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_ASSIGNED));
			}
			queueUpdateStmt.append(", task_complete_date = NULL");
			queueUpdateStmt.append(", supplier_approved_date = NULL");
			queueUpdateStmt.append(", supplier_approved_by = NULL");
			
			queueUpdateStmt.append(" where status not in ('Closed', 'Approved')");
			queueUpdateStmt.append(" and task = ").append(SqlHandler.delimitString(input.getTask()));
			queueUpdateStmt.append(" and q_id = ").append(bean.getqId());

			rowsUpdated += factory.deleteInsertUpdate(queueUpdateStmt.toString(), conn);
			
			if (input.isSourcingTask()) {
				resetMaterialSourcingFlag(bean, conn);
			}
		}
		
		return rowsUpdated;
	} //end of method
	
	private void resetMaterialSourcingFlag(ProblemQueueReasonInputBean input, Connection conn) throws BaseException {
		String query = "update catalog_add_item_qc caiq"
				+ " set material_id_sourced = decode(caiq.material_id_sourced, 'Y', 'N', 'X', 'X', 'N')"
				+ " where (caiq.request_id, caiq.catalog_add_item_id) = ("
				+ "select cq.catalog_add_request_id, cq.catalog_add_item_id from catalog_queue cq"
				+ " where cq.q_id = " + input.getqId() + ")";
		
		factory.deleteInsertUpdate(query, conn);
	}

	private void notifyVendor(ProblemQueueReasonInputBean input, PersonnelBean user) throws BaseException{
		try {
			CatalogAddRequestVendorBean bean = new CatalogAddRequestVendorBean();
			CatalogAddVendorQueueProcess process = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getClient());
			BeanHandler.copyAttributes(input, bean);
			process.notifyVendor(bean);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
	} //end of method

	private void notifyMasterTeam(ProblemQueueReasonInputBean input, PersonnelBean user, Connection conn) throws BaseException{
		try {
			String query = "select catalog_add_request_id from catalog_queue where q_id = " + input.getqId();
			String requestId = factory.selectSingleValue(query,conn);

			String companyQuery = "select fx_company_name(carn.company_id) company_id, fx_facility_name(carn.eng_eval_facility_id, carn.company_id) facility_id"
					+ " from catalog_add_request_new carn"
					+ " where carn.request_id = " + requestId;
			
			@SuppressWarnings("unchecked")
			Optional<FacilityBean> companyFacility = factory.setBean(new FacilityBean()).selectQuery(companyQuery, conn).stream().findFirst();
			String companyName = companyFacility.map(cf -> cf.getCompanyId()).orElse("");
			String facilityName = companyFacility.map(cf -> cf.getFacilityId()).orElse("");
			
			String messageBody = "A problem was reported with request " + requestId + " for the task of " + input.getTask()
					+ ". The problem type is: " + input.getProblemType()+".\n\n"+input.getComments();
			
			String replyTo = (user==null||StringHandler.isBlankString(user.getEmail()))?"Catalog.Support@haasgroupintl.com":user.getEmail();
			
			MailProcess.sendEmail("Catalog.Support@haasgroupintl.com", null, replyTo, "Problem with " + companyName + " " + facilityName + " Request" + " #"+requestId,messageBody);
		} catch(Exception e) {
			throw new BaseException(e);
		}
	} //end of method

	public boolean updateProblemQueue(ProblemQueueReasonInputBean input, PersonnelBean user) throws BaseException {
		Connection conn = factory.getDbManager().getConnection();
		int rowsUpdated = 0;
		try {
			String mostRecentAssignedTo = getMostRecentAssignedTo(input.getqId(), conn);
			//insert record into catalog_queue_problem.
			createProblemQueue(input,user,conn);
			
			//update catalog_queue status
			rowsUpdated = updateCatalogQueue(input, input.isReAssignToVendor() ? mostRecentAssignedTo : user.getPersonnelIdBigDecimal().toPlainString(), conn);
			
			if (rowsUpdated > 0) {
				//send out email notification
				if (input.isSendEmailToCatalog()) {
					// send email to catalog team
					notifyMasterTeam(input, user, conn);
				} else {
					// send email to vendor
					if (input.hasCatalogAddRequestId()) {
						//get all work items and notify users
						StringBuilder query = new StringBuilder("select q_id from catalog_queue where catalog_add_request_id = ").append(input.getCatalogAddRequestId());
						query.append(" and task = ").append(SqlHandler.delimitString(input.getTask()));
						Iterator iter = factory.setBean(new ProblemQueueReasonInputBean()).selectQuery(query.toString(),conn).iterator();
						while (iter.hasNext()) {
							ProblemQueueReasonInputBean bean = (ProblemQueueReasonInputBean)iter.next();
							input.setqId(bean.getqId());
							notifyVendor(input, user);
						}
					} else
						notifyVendor(input, user);
				}
			}
		} finally {
			factory.getDbManager().returnConnection(conn);
		}
		
		return rowsUpdated > 0;
	}
	
	//method removed since not used
	public Collection<CatalogAddRequestVendorBean> getProblemHistory(ProblemQueueReasonInputBean input) throws BaseException {
		String query = "select cp.comments"
				+ ", fx_personnel_id_to_name(cp.reported_by) problem_reported_by_name"
				+ ", cp.problem_type"
				+ ", cp.insert_date problem_report_date"
				+ ", cp.status problem_status"
				+ " from catalog_queue_problem cp"
				+ " where q_id = " + input.getqId()
				+ " order by cp.insert_date desc";
		
		return (Collection<CatalogAddRequestVendorBean>)factory.setBean(new CatalogAddRequestVendorBean()).selectQuery(query);
	}
	
	public String getMostRecentAssignedTo(BigDecimal qId, Connection conn) throws BaseException {
		//get most recent assigned_to
		StringBuilder query = new StringBuilder("select assigned_to from catalog_queue_problem c where ");
		query.append(" q_id = ").append(qId);
		query.append(" and exists(select null from (");
		query.append("select q_id, max(insert_date) as insert_date from catalog_queue_problem where");
		query.append(" q_id = ").append(qId);
		query.append(" group by q_id) v");
		query.append(" where v.q_id = c.q_id");
		query.append(" and v.insert_date = c.insert_date)");
		
		return factory.selectSingleValue(query.toString(), conn);
	}
}
