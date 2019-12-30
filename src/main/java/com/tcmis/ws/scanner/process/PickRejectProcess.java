package com.tcmis.ws.scanner.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.PickReceipt;

public class PickRejectProcess extends BaseScannerPickProcess {
	public PickRejectProcess(String client) {
		super(client);
	}

	public JSONArray rejectPick(Pick pick, JSONArray errors) throws BaseException {
		int originalNumberOfErrors = errors.length();
		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			IssueBean firstIssue = null;
			for (IssueBean issue : getIssues(pick, conn)) {
				rejectIssue(pick, issue, conn, errors);
				if (firstIssue == null) {
					firstIssue = issue;
				}
			}

			if (errors.length() == originalNumberOfErrors) {
				finishRejection(pick, firstIssue, conn, errors);
			}

			if (errors.length() > originalNumberOfErrors) {
				log.debug("*** ROLLING BACK CHANGES FOR REJECT PICK ***");
				conn.rollback();
			}
			else {
				conn.commit();
			}
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in PickRejectProcess", e1);
			}
			throw new BaseException("Error saving pick", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing PickRejectProcess", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	private Collection<IssueBean> getIssues(Pick pick, Connection conn) throws BaseException {
		factory.setBean(new IssueBean());
		@SuppressWarnings("unchecked")
		Collection<IssueBean> beans = factory
				.selectQuery("select i.* from picking_unit_issue p, issue i where p.picking_unit_id = " + pick.getPickingUnitId() + " and I.ISSUE_ID = p.issue_id", conn);
		return beans;
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}

	private boolean responseIsOK(Vector results) {
		String response = "" + results.firstElement();
		return StringHandler.isBlankString(response) || "0".equals(response);
	}

	private JSONObject rejectPickingUnit(Pick pick, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder("update PICKING_UNIT set ");
		sql.append(" LAST_UPDATED_BY = ").append(pick.getPickPersonnelId());
		sql.append(", PICKING_STATE = ").append("'REJECTED'");
		if (pick.hasPickDate()) {
			sql.append(", PICK_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getPickDate()));
		}
		else {
			sql.append(", PICK_DATE = ").append("sysdate");
		}
		sql.append(" where PICKING_UNIT_ID = ").append(pick.getPickingUnitId());
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			return getError(e, "Error updating picking_unit: ", pick.getId(), sql.toString());
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void rejectIssue(Pick pick, IssueBean issue, Connection conn, JSONArray errors) throws BaseException {
		Collection inArgs = null;
		Collection outArgs = null;
		try {
			inArgs = new Vector();
			inArgs.add(issue.getPicklistId());
			inArgs.add(issue.getCompanyId());
			inArgs.add(issue.getPrNumber());
			inArgs.add(issue.getLineItem());
			inArgs.add(getTimestamp(issue.getNeedDate()));
			inArgs.add(issue.getSourceHub());
			inArgs.add(issue.getReceiptId());
			inArgs.add(getTimestamp(new Date()));
			inArgs.add(0);
			inArgs.add(pick.getPickPersonnelId());

			outArgs = new Vector(2);
			outArgs.add(new Integer(java.sql.Types.NUMERIC));
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			Vector results = (Vector) factory.doProcedure(conn, "P_ENTER_PICK", inArgs, outArgs);
			if (!responseIsOK(results)) {
				errors.put(getError(null, "Error calling: P_ENTER_PICK:", pick.getId(), "inArgs: " + inArgs));
			}
		}
		catch (Exception e) {
			errors.put(getError(e, "Error calling: P_ENTER_PICK:", pick.getId(), "inArgs: " + inArgs));
		}
	}

	private void finishRejection(Pick pick, IssueBean issue, Connection conn, JSONArray errors) throws BaseException {
		Collection inArgs = new Vector(5);
		boolean allocateSuccessful = true;
		try {
			inArgs.add(issue.getCompanyId());
			inArgs.add(issue.getPrNumber());
			inArgs.add(issue.getLineItem());
			inArgs.add("Y");
			inArgs.add(getTimestamp(issue.getNeedDate()));
			factory.doProcedure(conn, "P_LINE_ITEM_ALLOCATE", inArgs);
		}
		catch (Exception e) {
			errors.put(getError(e, "Error calling: P_LINE_ITEM_ALLOCATE: ", pick.getId(), "inArgs: " + inArgs));
			allocateSuccessful = false;
		}

		if (allocateSuccessful) {
			inArgs = new Vector(2);
			try {
				inArgs.add(issue.getPicklistId());
				inArgs.add(pick.getPickPersonnelId());
				factory.doProcedure(conn, "P_QC_PICKLIST", inArgs);
				
				JSONObject currentError = rejectPickingUnit(pick, conn);
				if (currentError != null) {
					errors.put(currentError);
				}

			}
			catch (Exception e) {
				errors.put(getError(e, "Error calling: P_QC_PICKLIST: ", pick.getId(), "inArgs: " + inArgs));
			}
		}
	}
}
