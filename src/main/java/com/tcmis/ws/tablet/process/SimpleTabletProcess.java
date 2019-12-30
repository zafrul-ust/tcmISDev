package com.tcmis.ws.tablet.process;

import java.util.Collection;

import org.json.JSONArray;

import com.tcmis.common.admin.beans.PageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvCarrierBean;
import com.tcmis.common.admin.beans.VvReceiptDocumentTypeBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.factory.ReceivingViewBeanFactory;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.beans.VerifyExistsBean;
import com.tcmis.ws.tablet.beans.VvCarrierParentBean;
import com.tcmis.ws.tablet.beans.VvReceivingPriorityBean;
import com.tcmis.ws.tablet.beans.VvReceivingStatusBean;

public class SimpleTabletProcess extends GenericProcess {

	public SimpleTabletProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public JSONArray getTabletMenu(PersonnelBean user) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new PageBean());

		StringBuilder query = new StringBuilder("select p.* from page p, user_page up where p.page_id = up.page_id and up.personnel_id = ");
		query.append(user.getPersonnelId()).append(" and up.company_id = '").append(user.getCompanyId()).append("'and p.page_id in (select distinct PAGE_ID from PAGE_MENU where menu_id = 'tabletMenu')");
		
		Collection<PageBean> beans = factory.selectQuery(query.toString());
		for (PageBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}

	
	public JSONArray getLotNumbersForItem(TabletInputBean input) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptBean());

		StringBuilder query = new StringBuilder("select distinct MFG_LOT from receipt where  date_of_receipt < sysdate - 365 AND lot_status = 'Available' AND item_id = " + input.getItemId());

		if (input.hasHub()) {
			query.append(" AND HUB = '" + input.getHub() + "'");
		}
		if (input.hasInventoryGroup()) {
			query.append(" AND INVENTORY_GROUP = '" + input.getInventoryGroup() + "'");
		}

		Collection<ReceiptBean> beans = factory.selectQuery(query.toString());
		for (ReceiptBean bean : beans) {
			results.put(bean.getMfgLot());
		}

		return results;
	}
	
	public JSONArray getLotNumbersForItemComponents(TabletInputBean input) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptComponentBean());

		StringBuilder query = new StringBuilder("select distinct rc.part_id, rc.MFG_LOT from receipt r, receipt_component rc where r.date_of_receipt < sysdate - 365 AND r.lot_status = 'Available' AND r.item_id = ");
		query.append(input.getItemId());
		query.append(" AND rc.receipt_id = r.receipt_id");

		if (input.hasHub()) {
			query.append(" AND r.HUB = '" + input.getHub() + "'");
		}
		if (input.hasInventoryGroup()) {
			query.append(" AND r.INVENTORY_GROUP = '" + input.getInventoryGroup() + "'");
		}
		query.append(" order by receipt_id, part_id");

		Collection<ReceiptComponentBean> beans = factory.selectQuery(query.toString());
		for (ReceiptComponentBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}
	
	public String getBinStatus(TabletInputBean input) throws BaseException {
		return factory.selectSingleValue("select status from vv_hub_bins where branch_plant = '" + input.getHubId() +"' and lower(bin) = lower('" + input.getBin() + "') order by status");
	}
	
	public String getUserDefaultHub(PersonnelBean user) throws BaseException {
		if (user != null) {
			return factory.selectSingleValue("select default_hub from user_company_view where company_id = 'Radian' AND personnel_id = "
					+ user.getPersonnelId());
		}
		return "";
	}
	
	public JSONArray verifyExists(TabletInputBean input, PersonnelBean user) throws BaseException {
		JSONArray results = new JSONArray();

		StringBuilder query = new StringBuilder();
		if (input.hasPO()) {
			query.append("select radian_po transaction_id, inventory_group from PO where RADIAN_PO = " + input.getPo());
		}
		else if (input.hasRma()) {
			query.append("select crr.customer_rma_id transaction_id, rli.inventory_group from customer_return_request crr, request_line_item rli where rli.pr_number = crr.pr_number and rli.line_item = crr.line_item AND crr.return_material = 'Y' AND crr.rma_status = 'Approved' AND crr.correct_item_shipped = 'Y' and crr.customer_rma_id = " + input.getRma());
		}
		else if (input.hasReceiptId()) {
			query.append("select receipt_id transaction_id, inventory_group from RECEIPT where RECEIPT_ID = " + input.getReceiptId());
		}
		else if (input.hasTransferRequest()) {
			query.append("select transfer_request_id transaction_id, destination_inventory_group inventory_group from INVENTORY_TRANSFER_REQUEST where TRANSFER_REQUEST_ID = " + input.getTransferRequest());
		}
		else if (input.hasCitrPo()) {
			query.append("select doc_num transaction_id, inventory_group from CUSTOMER_INVENTORY_TO_RECEIVE where lower(po_number) = lower('" + input.getCitrPo() + "')");
		}
	
		factory.setBean(new VerifyExistsBean());
		
		Collection<VerifyExistsBean> beans = factory.selectQuery(query.toString());
		
		for (VerifyExistsBean bean : beans) {
			bean.setPermissionToReceive(user.getPermissionBean().hasInventoryGroupPermission("Receiving", bean.getInventoryGroup(), null, null));
			if (bean.isPermissionToReceive()) {
				bean.setInventoryGroupName(factory.selectSingleValue("select inventory_group_name from inventory_group_definition where inventory_group = '" + bean.getInventoryGroup() + "'"));
			}
			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}

	public boolean revertedExistForHub(TabletInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select 'Y' from DUAL where exists(");
		query.append("select * from RECEIPT where HUB = " + input.getHub());
		query.append(" and RECEIVING_STATUS = 'Re-Image')");		
		String response = factory.selectSingleValue(query.toString());
		return "Y".equals(response.trim());
	}
}
