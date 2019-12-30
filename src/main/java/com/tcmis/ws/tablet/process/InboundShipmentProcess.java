package com.tcmis.ws.tablet.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;

import com.tcmis.common.admin.beans.PageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvCarrierBean;
import com.tcmis.common.admin.beans.VvReceiptDocumentTypeBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.factory.ReceivingViewBeanFactory;
import com.tcmis.internal.msds.beans.MsdsInputBean;
import com.tcmis.ws.tablet.beans.InboundShipmentBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDetailBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDocumentBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDocumentInputBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.beans.VvCarrierParentBean;
import com.tcmis.ws.tablet.beans.VvReceivingPriorityBean;

public class InboundShipmentProcess extends GenericProcess {

	public InboundShipmentProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public JSONArray getInboundShipment(InboundShipmentBean input, String dateFormat) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipmentBean());

		SearchCriteria criteria = new SearchCriteria();
		if (input.hasInboundShipmentId()) {
			criteria.addCriterion("inboundShipmentId", SearchCriterion.EQUALS, "" + input.getInboundShipmentId());
		}
		else {
			criteria.addCriterion("trackingNumber", SearchCriterion.EQUALS, "" + input.getTrackingNumber());
			criteria.addCriterion("carrierParentShortName", SearchCriterion.EQUALS, "" + input.getCarrierParentShortName());
			criteria.addCriterion("shipmentStatus", SearchCriterion.NOT_EQUAL, "Received");
			if (input.hasHub()) {
				criteria.addCriterion("hub", SearchCriterion.EQUALS, input.getHub());
			}
	        criteria.addCriterion("arrivalScanDate", SearchCriterion.CUSTOM, " > (sysdate - 5)");

		}

		Collection<InboundShipmentBean> beans = factory.select(criteria, null, "INBOUND_SHIPMENT");
		for (InboundShipmentBean bean : beans) {
			factory.setBean(new InboundShipmentDocumentBean());
			SearchCriteria docCriteria = new SearchCriteria("inboundShipmentId", SearchCriterion.EQUALS, "" + bean.getInboundShipmentId());
			bean.setDocuments(factory.select(docCriteria, null, "INBOUND_SHIPMENT_DOCUMENT"));
			results.put(BeanHandler.getJsonObject(bean, dateFormat));
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getInboundShipmentDetails(InboundShipmentBean input, String dateFormat) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipmentDetailBean());

		SearchCriteria criteria = new SearchCriteria("inboundShipmentId", SearchCriterion.EQUALS, "" + input.getInboundShipmentId());

		Collection<InboundShipmentDetailBean> beans = factory.select(criteria, null, "INBOUND_SHIPMENT_DETAIL");
		for (InboundShipmentDetailBean bean : beans) {
			StringBuilder query = new StringBuilder();
			if (bean.hasRadianPo()) {
				query.append("select inventory_group from PO where RADIAN_PO = " + bean.getRadianPo());
			}
			else if (bean.hasCustomerRmaId()) {
				query.append("select rli.inventory_group from customer_return_request crr, request_line_item rli where rli.pr_number = crr.pr_number and rli.line_item = crr.line_item AND crr.return_material = 'Y' AND crr.rma_status = 'Approved' AND crr.correct_item_shipped = 'Y' and crr.customer_rma_id = " + bean.getCustomerRmaId());
			}
			else if (bean.hasTransferRequestId()) {
				query.append("select destination_inventory_group inventory_group from INVENTORY_TRANSFER_REQUEST where TRANSFER_REQUEST_ID = " + bean.getTransferRequestId());
			} 
			else if (bean.hasDocNum()) 
			{	
				query.append("select inventory_group from CUSTOMER_INVENTORY_TO_RECEIVE where doc_num = " + bean.getDocNum());
			}
			bean.setInventoryGroup(factory.selectSingleValue(query.toString()));
			
			results.put(BeanHandler.getJsonObject(bean, dateFormat));
		}

		return results;
	}

//	PROCEDURE P_UPSERT_INBOUND_SHIPMENT
//	(
//		A_INBOUND_SHIPMENT_ID			IN	INBOUND_SHIPMENT.INBOUND_SHIPMENT_ID%TYPE,
//		A_HUB							IN	INBOUND_SHIPMENT.HUB%TYPE,
//		A_TRACKING_NUMBER				IN	INBOUND_SHIPMENT.TRACKING_NUMBER%TYPE,
//		A_CARRIER_PARENT_SHORT_NAME		IN	INBOUND_SHIPMENT.CARRIER_PARENT_SHORT_NAME%TYPE,
//		A_ESTIMATED_DELIVERY_DATE		IN	INBOUND_SHIPMENT.ESTIMATED_DELIVERY_DATE%TYPE,
//		A_DATE_OF_RECEIPT				IN	INBOUND_SHIPMENT.DATE_OF_RECEIPT%TYPE,
//		A_COUNT_AND_CONDITION_NOTES		IN	INBOUND_SHIPMENT.COUNT_AND_CONDITION_NOTES%TYPE,
//		A_SHIPMENT_STATUS				IN	INBOUND_SHIPMENT.SHIPMENT_STATUS%TYPE,
//		A_ARRIVAL_SCAN_USER				IN	INBOUND_SHIPMENT.ARRIVAL_SCAN_USER%TYPE,
//		A_ARRIVAL_SCAN_DATE				IN	INBOUND_SHIPMENT.ARRIVAL_SCAN_DATE%TYPE,
//		A_UPDATE_USER					IN	INBOUND_SHIPMENT.UPDATE_USER%TYPE,
//	  	R_INBOUND_SHIPMENT_ID			OUT	INBOUND_SHIPMENT.INBOUND_SHIPMENT_ID%TYPE,
//		R_ERROR_MSG						OUT	VARCHAR2
//	)
	@SuppressWarnings("unchecked")
	public Collection saveInboundShipment(InboundShipmentBean bean, PersonnelBean user) throws BaseException, ParseException {
		Collection in = new Vector(9);
		Collection out = new Vector(2);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", user.getLocale());
		SimpleDateFormat timeStampFormat = new SimpleDateFormat(library.getString("java.tabletdatetimeformat"));

		in.add(bean.getInboundShipmentId());
		in.add(bean.getHub());
		in.add(bean.getTrackingNumber());
		in.add(bean.getCarrierParentShortName());
		in.add(null);
		in.add(bean.getDateOfReceipt());
		in.add(bean.getCountAndConditionNotes());
		in.add(bean.getShipmentStatus());
		in.add(bean.hasInboundShipmentId() ? null : user.getPersonnelIdBigDecimal());
		in.add(getTimestamp(timeStampFormat, bean.getArrivalScanTimestamp()));
		in.add(bean.hasInboundShipmentId() ? user.getPersonnelIdBigDecimal() : null);
		out.add(new Integer(java.sql.Types.VARCHAR));
		out.add(new Integer(java.sql.Types.VARCHAR));
		out = dbManager.doProcedure("pkg_hub_automation.p_upsert_inbound_shipment", in, out);

		return out;
	}

	public void saveInboundShipmentDetails(Collection<InboundShipmentDetailBean> details, String shipmentId, PersonnelBean user) throws BaseException {
		for (InboundShipmentDetailBean detail : details) {
			if (shipmentId != null) {
				detail.setInboundShipmentId(new BigDecimal(shipmentId));
			}
			saveInboundShipmentDetail(detail, user);
		}
	}

	public Collection saveInboundShipmentDetail(InboundShipmentDetailBean bean, PersonnelBean user) throws BaseException {
		Collection in = new Vector(7);
		Collection out = new Vector(2);

		in.add(bean.getInboundShipmentId());
		in.add(bean.getInboundShipmentDetailId());
		in.add(bean.getRadianPo());
		in.add(bean.getTransferRequestId());
		in.add(bean.getCustomerRmaId());
		in.add(bean.getDocNum());
		in.add(bean.getReceivingPriority());
		out.add(new Integer(java.sql.Types.INTEGER));
		out.add(new Integer(java.sql.Types.VARCHAR));
		out = dbManager.doProcedure("pkg_hub_automation.p_upsert_inbnd_shpmnt_detail", in, out);

		return out;
	}

	public void saveInboundShipmentDocuments(Collection<InboundShipmentDocumentInputBean> documents, String shipmentId, PersonnelBean user) throws BaseException {
		for (InboundShipmentDocumentInputBean document : documents) {
			String docId = "" + document.getInboundShipmentDocumentId();
			factory.deleteInsertUpdate("Insert into INBOUND_SHIPMENT_DOCUMENT (INBOUND_SHIPMENT_ID, INBOUND_SHIPMENT_DOCUMENT_ID, INITIAL_SCAN_USER) values (" + shipmentId + "," + docId + "," + user.getPersonnelId() + ")");
		}
	}

	public boolean inboundShipmentDocumentExists(String docId) throws BaseException {
		String count = factory.selectSingleValue("select count(*) from INBOUND_SHIPMENT_DOCUMENT where INBOUND_SHIPMENT_DOCUMENT_ID = '" + docId + "'");
		return !"0".equals(count);
	}
	
	private Timestamp getTimestamp(SimpleDateFormat formatter, String date) throws BaseException {
		if (StringHandler.isBlankString(date)) {
			return null;
		}
		try {
			return getTimestamp(formatter.parse(date));
		} catch (Exception e) {
			throw new BaseException("Error parsing date with format " + formatter.toPattern() + " : " + e.getMessage(), e);
		}		
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}
}
