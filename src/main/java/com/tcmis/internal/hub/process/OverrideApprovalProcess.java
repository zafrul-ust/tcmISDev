package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.internal.hub.beans.OverrideApprovalBean;

public class OverrideApprovalProcess extends GenericProcess {

	public OverrideApprovalProcess(String client) {
		super(client);
	}

	public OverrideApprovalBean view(BigDecimal pickingUnitId, BigDecimal rid,String pickingState) throws BaseException {
		String query = "select packer_override, packer_override_approver, packer_override_date, "
				+ "fx_personnel_id_to_name(packer_override_approver) packer_override_approver_name, "
				+ "label_override, label_override_approver, label_override_date, "
				+ "fx_personnel_id_to_name(label_override_approver) label_override_approver_name, "
				+ "qty_override, qty_override_approver, qty_override_date, "
				+ "fx_personnel_id_to_name(qty_override_approver) qty_override_approver_name, "
				+ "override_note, picking_unit_id from picking_unit, vv_picking_state vv "
				+ "where picking_unit_id = " + pickingUnitId;
		
		OverrideApprovalBean overrideApproval = ((Vector<OverrideApprovalBean>)factory.setBean(new OverrideApprovalBean()).selectQuery(query)).get(0);
		
		String ridQuery = "select receipt_id from receipt where "
				+ " item_id = (select item_id from receipt where receipt_id = " +getSqlString(rid) +")"
				+ " and inventory_group = (select inventory_group from receipt where receipt_id = " + getSqlString(rid) +")"
				+ " and receipt_id != " + getSqlString(rid) 
				+ " and quantity_received - total_quantity_issued + total_quantity_returned > 0";
		
		overrideApproval.setValidRids((Vector)factory.selectIntoObjectArray(ridQuery)[2]);
		
//		allow_rid_override
//		allow_packer_qty_override		
		overrideApproval.setAllowRidOverride(factory.selectSingleValue("select allow_rid_override from tcm_ops.vv_picking_state where picking_state = " + this.getSqlString(pickingState))); 
		overrideApproval.setAllowPackerQtyOverride(factory.selectSingleValue("select allow_packer_qty_override from tcm_ops.vv_picking_state where picking_state = " + this.getSqlString(pickingState)));
		return overrideApproval;
	}
	
	public void update(OverrideApprovalBean input, PersonnelBean user, HttpServletRequest request) throws BaseException {
		Vector<String> errorMsgs = new Vector<String>();
		Connection conn = dbManager.getConnection();
		OverrideApprovalBean currentState = null;
		String finalGenericMsg = "";
		try {
			currentState = view(input.getPickingUnitId(), input.getRid(), input.getPickingState());
			StringBuilder stmt = new StringBuilder("update picking_unit set ");
			StringBuilder setStmt = new StringBuilder();

			if (!currentState.isPackerOverride() && input.isPackerOverride()) {
				setStmt.append("packer_override = 'Y'").append(", packer_override_approver = ")
						.append(user.getPersonnelId()).append(", packer_override_date = sysdate");
			}
			if (!currentState.isLabelOverride() && input.isLabelOverride()) {
				if (setStmt.length() > 0) {
					setStmt.append(", ");
				}
				setStmt.append("label_override = 'Y'").append(", label_override_approver = ")
						.append(user.getPersonnelId()).append(", label_override_date = sysdate");
			}
			if (!currentState.isQtyOverride() && input.isQtyOverride()) {
				if (setStmt.length() > 0) {
					setStmt.append(", ");
				}
				setStmt.append("qty_override = 'Y'").append(", qty_override_approver = ").append(user.getPersonnelId())
						.append(", qty_override_date = sysdate");
			}
			// TCMISDEV 4409 and 4419
			if (input.isChangeRid()) {
				Vector inArgs = new Vector();
				inArgs.add(input.getRid());
				inArgs.add(input.getToRid());
				inArgs.add(input.getPickingUnitId());
				Vector<String> temp = new Vector<String>();
				String tempMsg = this.getProcError(inArgs, null, "PKG_PU_RECEIPT_UPDATE.p_update_receipt", temp);
				if (!temp.get(0).isEmpty() && !"OK".equalsIgnoreCase(temp.get(0)))
					errorMsgs.add(temp.get(0));
				else if (!tempMsg.isEmpty())
					finalGenericMsg = tempMsg;
				if (setStmt.length() > 0) {
					setStmt.append(", ");
				}
				setStmt.append("rid_override = 'Y'").append(", rid_override_approver = ").append(user.getPersonnelId())
						.append(", rid_override_date = sysdate");
			}
			if (input.isPackQtyOverride()) {
				if (new BigDecimal(0).compareTo(input.getOverrideQty()) == 0) {
					Vector inArgs = new Vector();
					inArgs.add(input.getPickingUnitId());
					inArgs.add(input.getIssueId());
					inArgs.add(user.getPersonnelId());
					inArgs.add(input.getOverrideNote());
					Vector<String> temp = new Vector<String>();
					String tempMsg = this.getProcError(inArgs, null, "pkg_reject_picklist.p_reject_picklist", temp);
					if (!temp.get(0).isEmpty() && !"OK".equalsIgnoreCase(temp.get(0)))
						errorMsgs.add(temp.get(0));
					else if (!tempMsg.isEmpty())
						finalGenericMsg = tempMsg;
				} else {
					String updateRid = "update tcm_ops.ISSUE set quantity = " + getSqlString(input.getOverrideQty())
						+ " where issue_id in ( select issue_id from picking_unit_issue where picking_unit_id = "
						+ input.getPickingUnitId() + ")"
							// + " where pr_number = " + factory.selectSingleValue("select pr_number from
							// picking_unit where picking_unit_id = "+ input.getPickingUnitId() )
							// + " and line_item = " + getSqlString(factory.selectSingleValue("select
							// line_item from picking_unit where picking_unit_id = "+
							// input.getPickingUnitId()))
							+ " and receipt_id = " + getSqlString(input.getRid());
					factory.deleteInsertUpdate(updateRid, conn);
					// System.out.println(updateRid);
					updateRid = "update tcm_ops.PICKING_UNIT_RCPT_VALIDATION set qc_quantity = "
							+ getSqlString(input.getOverrideQty()) + " where picking_unit_id = "
							+ getSqlString(input.getPickingUnitId()) + " and receipt_id = " + getSqlString(input.getRid());
					// System.out.println(updateRid);
					factory.deleteInsertUpdate(updateRid, conn);
					// According to Ron, call these two procedure afterwards
					Vector inArgs = new Vector();
					inArgs.add(input.getRid());
					String tempMsg = this.getProcError(inArgs, new Vector(), "p_receipt_allocate");
					if (!tempMsg.isEmpty())
						finalGenericMsg = tempMsg;
				}
				// for picking_unit table //PACKER_QTY_OVERRIDE_APPROVER
				if (setStmt.length() > 0) {
					setStmt.append(", ");
				}
				setStmt.append("packer_qty_override = 'Y'").append(", packer_qty_override_approver = ")
						.append(user.getPersonnelId()).append(", packer_qty_override_date = sysdate");
			}
			if (setStmt.length() > 0) {
				setStmt.append(", override_note = to_char(sysdate) || ' - ' || rtrim(")
						.append(SqlHandler.delimitString(input.getOverrideNote() + "; ")).append(" || override_note)");
				stmt.append(setStmt);
				stmt.append(" where picking_unit_id = ").append(input.getPickingUnitId());

				factory.deleteInsertUpdate(stmt.toString(), conn);
			}
		} catch (Exception e) {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			finalGenericMsg = library.getString("generic.error");
		} finally {
			dbManager.returnConnection(conn);
		}
		
		if (!finalGenericMsg.isEmpty())
			errorMsgs.add(finalGenericMsg);
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("overrideApprovalBean", currentState);
			request.setAttribute("tcmISErrors", errorMsgs);
		}
	}
}
