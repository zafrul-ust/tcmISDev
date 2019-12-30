package com.tcmis.internal.hub.process;

import java.sql.Connection;
import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PickingGroupBean;
import com.tcmis.internal.hub.beans.PickingGroupMgmtInputBean;
import com.tcmis.internal.hub.beans.PickingStateViewBean;
import com.tcmis.internal.hub.beans.PickingStatusInputBean;

public class PickingGroupMgmtProcess extends GenericProcess {

	public PickingGroupMgmtProcess(String client) {
		super(client);
	}
	
	public PickingGroupMgmtProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	public Collection<PickingStateViewBean> getPickingStateColl() throws BaseException {
		String query = "select * from vv_picking_state";
		
		Collection<PickingStateViewBean> pickingStateColl = factory.setBean(new PickingStateViewBean()).selectQuery(query);
		return pickingStateColl;
	}
	
	public Collection<PickingGroupBean> getHubPickingGroupColl(PickingGroupMgmtInputBean input) throws BaseException {
		String query = "select picking_group_id, picking_group_name, hub from picking_group";
		if ( ! StringHandler.isBlankString(input.getSourceHub())) {
			query += " where hub = " + SqlHandler.delimitString(input.getSourceHub())
					+ " order by picking_group_name";
		}
		else {
			query += " order by hub, picking_group_name";
		}
		Collection<PickingGroupBean> pickingGroups = factory.setBean(new PickingGroupBean()).
			selectQuery(query);
		
		return pickingGroups;
	}

	public void updatePickingGroups(PickingGroupMgmtInputBean input, PersonnelBean user, Collection<PickingGroupBean> pickingGroupColl) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			for (PickingGroupBean bean : pickingGroupColl) {
				String stmt = "";
				if (bean.getPickingGroupId() != null) {
					String active = bean.isActive()?"'A'":"'I'";
					stmt = "update picking_group set picking_group_name = "+SqlHandler.delimitString(bean.getPickingGroupName())
							+ ", status = " + active 
							+ ", last_updated_by = " + user.getPersonnelId()
							+ " where picking_group_id = "+bean.getPickingGroupId()
							+ " and (status <> " + active
							+ " or picking_group_name <> " + SqlHandler.delimitString(bean.getPickingGroupName())
							+ ")";
				}
				else if ( ! bean.getPickingGroupName().isEmpty()) {
					stmt = "insert into picking_group (company_id, hub, picking_group_name, last_updated_by) values ("
							+ SqlHandler.delimitString(input.getCompanyId()) + ", "
							+ SqlHandler.delimitString(input.getSourceHub()) + ", "
							+ SqlHandler.delimitString(bean.getPickingGroupName()) + ", "
							+ user.getPersonnelId() + ")";
				}
				else {
					continue;
				}
				
				factory.deleteInsertUpdate(stmt);
			}
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public Collection<PickingGroupBean> search(PickingGroupMgmtInputBean input) throws BaseException {
		String query = "select pg.*, fx_personnel_id_to_name(pg.last_updated_by) last_updated_by_name "
				+ "from picking_group pg";
		String where = " where";
		if ( ! StringHandler.isBlankString(input.getSourceHub())) {
			query += where + " hub = " + input.getSourceHub();
			where = " and";
		}
		if (input.getPickingGroupId() != null) {
			query += where + " pg.picking_group_id = " + input.getPickingGroupId();
			where = " and";
		}
		if (input.isActiveOnly()) {
			query += where + " pg.status = 'A'";
			where = " and";
		}
		else {
			query += where + " pg.status <> 'D'";
		}
		query += " order by picking_group_name";
		
		Collection<PickingGroupBean> pickingGroupColl = factory.setBean(new PickingGroupBean()).selectQuery(query);
		return pickingGroupColl;
	}
	
	public ExcelHandler getExcelReport(PickingGroupMgmtInputBean bean,PersonnelBean personnelBean, Locale locale) throws
	BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PickingGroupBean> data = search(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		write column headers
		pw.addRow();

		String[] headerkeys = {
				"label.pickinggroup","label.name","label.company","label.hub","label.pickingstate",
				"label.lastupdated","label.lastupdatedby","label.status"};

		int[] widths = {0,0,0,0,0,0,0,0};
		int[] types = {0,0,0,0,0,pw.TYPE_DATE,0,0};
		int[] aligns = {0,0,0,0,0,0,0,0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (PickingGroupBean member : data) {
			pw.addRow();
			pw.addCell(member.getPickingGroupId());
			pw.addCell(member.getPickingGroupName());
			pw.addCell(member.getCompanyId());
			pw.addCell(member.getHub());
			pw.addCell(member.getPickingStateDesc());
			pw.addCell(member.getLastUpdated());
			pw.addCell(member.getLastUpdatedBy());
			pw.addCell(member.getStatus().equals("A")?library.getString("label.active"):library.getString("label.inactive"));
		}

		return pw;
	}
}
