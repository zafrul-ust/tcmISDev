package com.tcmis.ws.tablet.process;

import java.io.File;
import java.util.Collection;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.InboundTransItemBean;
import com.tcmis.ws.tablet.beans.InboundTransLineBean;
import com.tcmis.ws.tablet.beans.MsdsRevisionBean;
import com.tcmis.ws.tablet.beans.PoLineFlowDownBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class InboundTransProcess extends GenericProcess {
	private static String	relativeItemImageDirectory;
	static {
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		relativeItemImageDirectory = library.getString("relativeItemImageDirectory");
		if (!relativeItemImageDirectory.endsWith(File.separator)) {
			relativeItemImageDirectory += File.separator;
		}
	}

	public InboundTransProcess(String client) {
		super(client);
	}

	public InboundTransProcess(String client, Locale locale) {
		super(client, locale);
	}

	public JSONArray getInboundTransLineDetail(TabletInputBean input, String dateFormat) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundTransLineBean());

		@SuppressWarnings("unchecked")
		Collection<InboundTransLineBean> beans = factory.selectQuery("select * from table (PKG_HUB_AUTOMATION.FX_GET_INBOUND_TRANS_LINES(" + input.getInboundShipmentDetailId() + "," + input.getItemId() + "))");
		for (InboundTransLineBean bean : beans) {
			JSONObject lineJSON = BeanHandler.getJsonObject(bean, dateFormat);
			lineJSON.put("flowdowns", getInboundTransFlowDowns(bean, dateFormat));
			results.put(lineJSON);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getInboundTransFlowDowns(InboundTransLineBean line, String dateFormat) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new PoLineFlowDownBean());

		// First get the specific flowdowns for the PO
		String query = "SELECT l.*, F.FLOW_DOWN_DESC, F.FLOW_DOWN_TYPE, F.REVISION_DATE, f.content, F.CURRENT_VERSION FROM po_line_flow_down l, vv_flow_down f WHERE l.radian_po = ";
		query += line.getRadianPo();
		query += " AND l.po_line = ";
		query += line.getLineItem();
		query += " AND l.flow_down = f.flow_down AND l.catalog_id = f.catalog_id AND l.company_id = f.company_id";
		Collection<PoLineFlowDownBean> beans = factory.selectQuery(query);
		if (line.hasInventoryGroup()) {
			// The add the Vendor Qualification flowdowns for the IG
			query = "SELECT qual_stmnt flow_down_desc, 'Vendor Qualification' flow_down_type FROM ig_vendor_qual_stmnt WHERE inventory_group = '";
			query += line.getInventoryGroup() + "'";
			beans.addAll(factory.selectQuery(query));
		}

		for (PoLineFlowDownBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, dateFormat));
		}

		return results;
	}

	public JSONArray getInboundItemsForTransaction(TabletInputBean input) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundTransItemBean());

		@SuppressWarnings("unchecked")
		Collection<InboundTransItemBean> beans = factory.selectQuery("select * from table (PKG_HUB_AUTOMATION.FX_GET_INBOUND_TRANS_ITEMS(" + input.getInboundShipmentDetailId() + ")) order by component_id");
		String receivingStatus = factory.selectSingleValue("select distinct receiving_status from receipt where inbound_shipment_detail_id = " + input.getInboundShipmentDetailId());
		String itemReceiveStatus = (StringHandler.isBlankString(receivingStatus) ? "New" : "In Progress");

		factory.setBean(new MsdsRevisionBean());

		for (InboundTransItemBean bean : beans) {
			bean.setItemStatus(itemReceiveStatus);
			bean.setImageUrl(bean.getImageFileName());
			String query = "select * from (select icv.DESCRIPTION, TO_DATE(icv.REVISION_DATE, 'DD/MON/YYYY' ) AS REVISION_DATE, msds_locale  from item_component_view icv where item_id = ";
			query += bean.getItemId();
			query += " union select icmlv.DESCRIPTION, TO_DATE(icmlv.REVISION_DATE, 'DD/MON/YYYY' ) AS REVISION_DATE, locale_code msds_locale from item_component_msds_loc_view icmlv where item_id = ";
			query += bean.getItemId();
			query += ") where msds_locale = '" + getLocale() + "'";

			bean.setMsdsVersions(factory.selectQuery(query));

			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}

	public String getQualityControli18nLabel(TabletInputBean input) throws BaseException {
		return factory.selectSingleValue("select * from table (PKG_HUB_AUTOMATION.fx_quality_control_i18n_label(" + input.getItemId() + ",'" + input.getInventoryGroup() + "'))");
	}

	public String getReceiveInfoItemNote(TabletInputBean input) throws BaseException {
		return factory.selectSingleValue("select notes from receive_info where JACKET_DISPLAY = 'Y' and item_id = " + input.getItemId() + " and HUB= '" + input.getHubId() + "'");
	}
}
