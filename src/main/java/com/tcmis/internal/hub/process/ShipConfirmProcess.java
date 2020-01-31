package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;
import com.tcmis.internal.hub.beans.ShipConfirmViewBean;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentCreationStageBean;
import com.tcmis.internal.hub.beans.VvIncotermBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.beans.VvMotIncotermBean;
import com.tcmis.internal.hub.factory.HubBeanFactory;
import com.tcmis.internal.hub.factory.ShipConfirmViewBeanFactory;
import com.tcmis.internal.hub.factory.ShipmentBeanFactory;
import com.tcmis.internal.hub.factory.ShipmentCreationStageBeanFactory;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for ship confirm
 * 
 * @version 1.0
 *****************************************************************************/
public class ShipConfirmProcess extends GenericProcess {
	Log						log			= LogFactory.getLog(this.getClass());

	private ResourceLibrary	resource	= new ResourceLibrary("label");
	private String			hostUrl		= resource.getString("label.hosturl");

	public ShipConfirmProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getHubs() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		HubBeanFactory factory = new HubBeanFactory(dbManager);
		return factory.selectHubs();
	}

	public Object[] search(PersonnelBean personnelBean, ShipConfirmInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ShipConfirmViewBeanFactory factory = new ShipConfirmViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (!StringHandler.isBlankString(bean.getHub()))
			criteria.addCriterion("sourceHub", SearchCriterion.EQUALS, bean.getHub());
		if (!StringHandler.isBlankString(bean.getOpsEntityId()))
			if (!StringHandler.isBlankString(bean.getInventoryGroup()))
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			else {
				String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
				if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
					invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
				if (!StringHandler.isBlankString(bean.getOpsEntityId()))
					invQuery += " and ops_entity_id = '" + bean.getOpsEntityId() + "' ";
				criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
			}

		if (!isBlank(bean.getCustomerId()))
			criteria.addCriterion("customerId", SearchCriterion.EQUALS, bean.getCustomerId().toString());

		if (!isBlank(bean.getCustomerServiceRepId()))
			criteria.addCriterion("customerServiceRepId", SearchCriterion.EQUALS, bean.getCustomerServiceRepId().toString());

		if (!isBlank(bean.getSearchArgument()))
			criteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());

		SortCriteria sortCriteria = new SortCriteria();
		if (!StringHandler.isBlankString(bean.getSortBy())) {
			if ("sd".equalsIgnoreCase(bean.getSortBy())) {
				// SHIP_TO_LOCATION_ID,DELIVERY_POINT
				sortCriteria.addCriterion("shipToLocationId");
				sortCriteria.addCriterion("deliveryPoint");
			}
			else if ("sfa".equalsIgnoreCase(bean.getSortBy())) {
				// SHIP_TO_LOCATION_ID,FACILITY_ID,APPLICATION
				sortCriteria.addCriterion("shipToLocationId");
				sortCriteria.addCriterion("facilityId");
				sortCriteria.addCriterion("application");
			}
			else if ("plb".equalsIgnoreCase(bean.getSortBy())) {
				// PR_NUMBER,LINE_ITEM,BATCH
				sortCriteria.addCriterion("prNumber");
				sortCriteria.addCriterion("lineItem");
				sortCriteria.addCriterion("batch");
			}
			else if ("fpl".equalsIgnoreCase(bean.getSortBy())) {
				// FACILITY_ID,PR_NUMBER,LINE_ITEM
				sortCriteria.addCriterion("facilityId");
				sortCriteria.addCriterion("prNumber");
				sortCriteria.addCriterion("lineItem");
			}
		}

		Collection<ShipConfirmViewBean> results = factory.select(criteria, sortCriteria);

		boolean showProForma = false;
		Vector v1 = new Vector();
		for (ShipConfirmViewBean bean1 : results) {
			if (!v1.contains(bean1.getShipmentId()) && bean1.getShipmentId() != null)
				v1.add(bean1.getShipmentId());

			if (bean1.isProformaRequired())
				showProForma = true;
		}

		Collections.sort(v1);

		Vector v2 = new Vector();
		for (ShipConfirmViewBean bean2 : results) {
			if (!v2.contains(bean2.getPrNumber()) && bean2.getShipmentId() != null)
				v2.add(bean2.getPrNumber());
		}

		Collections.sort(v2);

		Object[] objs = {results, v1, v2, showProForma ? "Y" : "N"};
		return objs;
	}

	public ExcelHandler getExcelReport(PersonnelBean personnelBean, ShipConfirmInputBean bean, Locale locale) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		Object[] results = this.search(personnelBean, bean);
		Collection data = (Collection) results[0];
		Iterator iterator = data.iterator();

		String containOrderEntry = "N";
		while (iterator.hasNext()) {
			ShipConfirmViewBean shipConfirmViewBean = (ShipConfirmViewBean) iterator.next();
			if ("Y".equals(shipConfirmViewBean.getDistribution())) {
				containOrderEntry = "Y";
				break;
			}
		}

		pw.addTable();

		// write column headers
		pw.addRow();

		if ("Y".equals(containOrderEntry)) {
			String[] headerkeys = {"label.shipmentid", "label.inventorygroup", "label.customer", "label.materialrequestorigin", "label.shipto", "label.paymentterms",
					"label.hazardous", "label.hazardcategory", "label.mrline", "label.csr", "label.picklistid", "label.part", "label.quantity", "label.datepicked", "label.picker",
					"label.qcdate", "label.qcedby", "label.billtonote", "label.shiptonote", "label.orderinternalnote", "label.orderexternalnote", "label.internallinenote",
					"label.externallinenote"};
			int[] types = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_DATE, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0, 0, 0};

			int[] widths = {12, 15, 15, 10, 20, 10, 11, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			pw.applyColumnHeader(headerkeys, types, widths, null);

		}
		else {
			String[] headerkeys = {"label.shipmentid", "label.inventorygroup", "label.company", "label.facility", "label.workarea", "label.dock", "label.hazardous",
					"label.hazardcategory", "label.mrline", "label.csr", "label.picklistid", "label.part", "label.quantity", "label.datepicked", "label.picker", "label.qcdate",
					"label.qcedby", "label.billtonote", "label.shiptonote", "label.orderinternalnote", "label.orderexternalnote", "label.internallinenote",
					"label.externallinenote"};

			int[] types = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_DATE, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0, 0, 0};

			int[] widths = {12, 12, 15, 12, 15, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			pw.applyColumnHeader(headerkeys, types, widths, null);
		}

		/*
		 * pw.addRow(); pw.addCellKeyBold("label.shipmentid");
		 * pw.addCellKeyBold("label.company");
		 * pw.addCellKeyBold("label.facility");
		 * pw.addCellKeyBold("label.workarea");
		 * pw.addCellKeyBold("label.deliverypoint");
		 * pw.addCellKeyBold("label.dock");
		 * pw.addCellKeyBold("label.hazardcategory");
		 * pw.addCellKeyBold("label.mrline");
		 * pw.addCellKeyBold("label.picklistid");
		 * pw.addCellKeyBold("label.part"); pw.addCellKeyBold("label.quantity");
		 * pw.addCellKeyBold("label.datepicked");
		 * pw.addCellKeyBold("label.picker");
		 */
		// now write data
		// DateFormat dateFormat = new
		// SimpleDateFormat(library.getString("java.dateformat"));
		iterator = data.iterator();

		while (iterator.hasNext()) {
			ShipConfirmViewBean shipConfirmViewBean = (ShipConfirmViewBean) iterator.next();
			pw.addRow();
			pw.addCell(shipConfirmViewBean.getShipmentId());
			pw.addCell(shipConfirmViewBean.getInventoryGroup());
			if ("Y".equals(containOrderEntry))
				pw.addCell(shipConfirmViewBean.getCustomerName());
			else
				pw.addCell(shipConfirmViewBean.getCompanyName());
			if ("Y".equals(containOrderEntry)) {
				pw.addCell(shipConfirmViewBean.getMaterialRequestOrigin());
				pw.addCell(shipConfirmViewBean.getShipToAddressLine1() + " " + shipConfirmViewBean.getShipToAddressLine1() + " " + shipConfirmViewBean.getShipToAddressLine2() + " "
						+ shipConfirmViewBean.getShipToAddressLine3() + " " + shipConfirmViewBean.getShipToAddressLine4() + " " + shipConfirmViewBean.getShipToAddressLine5());
				pw.addCell(shipConfirmViewBean.getPaymentTerms());
			}
			else {
				pw.addCell(shipConfirmViewBean.getFacilityId());
				pw.addCell(shipConfirmViewBean.getApplicationDesc());
				pw.addCell(shipConfirmViewBean.getShipToLocationId());
			}
			pw.addCell(shipConfirmViewBean.getHazardous());
			pw.addCell(shipConfirmViewBean.getHazardCategory());
			pw.addCell(shipConfirmViewBean.getPrNumber() + "-" + shipConfirmViewBean.getLineItem());
			pw.addCell(shipConfirmViewBean.getCsrName());
			pw.addCell(shipConfirmViewBean.getBatch());
			pw.addCell(shipConfirmViewBean.getCatPartNo());
			pw.addCell(shipConfirmViewBean.getQuantity());
			pw.addCell(shipConfirmViewBean.getDatePicked());
			pw.addCell(shipConfirmViewBean.getIssuerName());
			pw.addCell(shipConfirmViewBean.getIssueQcDate());
			pw.addCell(shipConfirmViewBean.getIssueQcUserName());
			pw.addCell(shipConfirmViewBean.getCustomerNote());
			pw.addCell(shipConfirmViewBean.getShiptoNote());
			pw.addCell(shipConfirmViewBean.getPrInternalNote());
			pw.addCell(""); // TODO add Order External Note here
			pw.addCell(shipConfirmViewBean.getLineInternalNote());
			pw.addCell(""); // TODO add External Line Note here

		}
		return pw;
	}

	public void createShipment(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Connection connection = dbManager.getConnection();
			try {
				GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
				ShipmentCreationStageBeanFactory factory = new ShipmentCreationStageBeanFactory(dbManager);
				connection.setAutoCommit(false);
				Iterator it = shipConfirmInputBeanCollection.iterator();
				Date dateInserted = new Date();
				String action = "CREATE";
				while (it.hasNext()) {
					ShipConfirmInputBean inputBean = (ShipConfirmInputBean) it.next();
					ShipmentCreationStageBean bean = new ShipmentCreationStageBean();
					setupBean(inputBean, bean);
					bean.setDateInserted(dateInserted);
					bean.setAction(action);
					factory.insert(bean, connection);
				}
				Collection inArgs = new Vector(0);
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				Collection resultColl = procFactory.doProcedure(connection, "P_CREATE_SHIPMENT", inArgs, outArgs);
				if (log.isDebugEnabled()) {
					Iterator resIt = resultColl.iterator();
					while (resIt.hasNext()) {
						log.debug("res:" + resIt.next());
					}
				}
			}
			finally {
				if (connection != null) {
					connection.commit();
					connection.setAutoCommit(true);
					dbManager.returnConnection(connection);
				}
			}
		}
		udpateShippintReference(shipConfirmInputBeanCollection);
	}

	public void udpateShippintReference(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Connection connection = dbManager.getConnection();
			try {
				Iterator it = shipConfirmInputBeanCollection.iterator();
				while (it.hasNext()) {
					ShipConfirmInputBean inputBean = (ShipConfirmInputBean) it.next();
					if ("0".equals(inputBean.getLineItem())) {
						String query = "update inventory_transfer_request set SHIPPING_REFERENCE = '" + inputBean.getShippingReference() + "' where transfer_request_id = "
								+ inputBean.getPrNumber();
						this.factory.deleteInsertUpdate(query, connection);
					}
					else {
						String query = "update request_line_item set SHIPPING_REFERENCE = '" + inputBean.getShippingReference() + "' where company_id = '"
								+ inputBean.getCompanyId() + "' and PR_NUMBER = " + inputBean.getPrNumber() + " and LINE_ITEM = " + inputBean.getLineItem();
						this.factory.deleteInsertUpdate(query, connection);
					}
				}
			}
			finally {
				if (connection != null) {
					dbManager.returnConnection(connection);
				}
			}
		}
	}

	public void updateShipment(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Connection connection = dbManager.getConnection();
			try {
				GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
				ShipmentCreationStageBeanFactory factory = new ShipmentCreationStageBeanFactory(dbManager);
				connection.setAutoCommit(false);
				Iterator it = shipConfirmInputBeanCollection.iterator();
				Date dateInserted = new Date();
				String action = "UPDATE";
				while (it.hasNext()) {
					ShipConfirmInputBean inputBean = (ShipConfirmInputBean) it.next();
					ShipmentCreationStageBean bean = new ShipmentCreationStageBean();
					setupBean(inputBean, bean);
					bean.setDateInserted(dateInserted);
					bean.setAction(action);
					factory.insert(bean, connection);
				}
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				procFactory.doProcedure(connection, "P_CREATE_SHIPMENT", outArgs);
			}
			finally {
				if (connection != null) {
					connection.commit();
					connection.setAutoCommit(true);
					dbManager.returnConnection(connection);
				}
			}
		}
		udpateShippintReference(shipConfirmInputBeanCollection);
	}

	public void confirmShipment(PersonnelBean personnelBean, ShipConfirmInputBean inputBean, Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		PermissionBean perbean = personnelBean.getPermissionBean();
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			if (personnelBean.isFromTablet() || perbean.hasInventoryGroupPermission("shipConfirm", inputBean.getInventoryGroup(), null, null)) {
				if (!personnelBean.isFromTablet()) {
					this.createShipment(shipConfirmInputBeanCollection);
				}
				DbManager dbManager = new DbManager(getClient(), getLocale());
				GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
				ShipmentBeanFactory factory = new ShipmentBeanFactory(dbManager);
				SearchCriteria criteria = new SearchCriteria();
				Vector v = new Vector(shipConfirmInputBeanCollection);
				ShipConfirmInputBean shipConfirmInputBean = (ShipConfirmInputBean) v.elementAt(0);
				if (inputBean.getDeliveredDate() == null) {
					shipConfirmInputBean.setDeliveredDate(new Date());
				}
				else {
					shipConfirmInputBean.setDeliveredDate(inputBean.getDeliveredDate());
				}
				criteria.addCriterion("shipConfirmDate", SearchCriterion.IS, "null");
				criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getBranchPlant());
				Collection c = factory.selectDistinct(criteria);
				Iterator it = c.iterator();
				while (it.hasNext()) {
					ShipmentBean bean = (ShipmentBean) it.next();
					this.callShipConfirmProcedure(dbManager, bean.getShipmentId(), bean.getCarrierCode(), bean.getTrackingNumber(), shipConfirmInputBean.getDeliveredDate(),
							new BigDecimal("" + personnelBean.getPersonnelId() + ""), shipConfirmInputBean.getFreightCharge(), "", "");
				}
				this.sendNotification(c);
			}
		}
	}

	public String confirmNotAutoShipment(PersonnelBean personnelBean, ShipConfirmInputBean inputBean, Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		String listOfShipmentId = "";
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Iterator it = shipConfirmInputBeanCollection.iterator();
			while (it.hasNext()) {
				ShipConfirmInputBean bean = (ShipConfirmInputBean) it.next();
				if (listOfShipmentId != null && listOfShipmentId.length() == 0) {
					listOfShipmentId += "" + bean.getShipmentId() + "";
				}
				else {
					listOfShipmentId += "," + bean.getShipmentId() + "";
				}
				// log.debug("getShipConfirmDate "+bean.getShipConfirmDate());
				if (bean.getShipConfirmDate() == null) {
					this.callShipConfirmProcedure(dbManager, bean.getShipmentId(), bean.getCarrierCode(), bean.getTrackingNumber(), inputBean.getDeliveredDate(),
							new BigDecimal("" + personnelBean.getPersonnelId() + ""), bean.getFreightCharge(), bean.getModeOfTransport(), bean.getIncoterm());
					// this.sendNotAutoConfirmNotification(shipConfirmInputBeanCollection);
				}
			}
			// This one has been moved out for logical reason. On production
			// already.
			this.sendNotAutoConfirmNotification(shipConfirmInputBeanCollection);
		}
		return listOfShipmentId;
	}

	private void callShipConfirmProcedure(DbManager dbManager, BigDecimal shipmentId, String carrierCode, String trackingNumber, Date deliveredDate, BigDecimal personnelId,
			BigDecimal freightCharge, String modeOfTransport, String incoterm) throws BaseException, Exception {
		Collection inArgs = new Vector(5);
		Collection outArgs = new Vector(1);
		Collection optArgs = new Vector(4);
		if (shipmentId != null) {
			inArgs.add(shipmentId);
		}
		else {
			inArgs.add("");
		}
		if (carrierCode != null) {
			inArgs.add(carrierCode);
		}
		else {
			inArgs.add("");
		}
		if (trackingNumber != null) {
			inArgs.add(trackingNumber);
		}
		else {
			inArgs.add("");
		}
		inArgs.add(new Timestamp(deliveredDate.getTime()));
		inArgs.add(personnelId);
		if (freightCharge != null) {
			inArgs.add("USD");
			inArgs.add(freightCharge);
		}
		else {
			inArgs.add("");
			inArgs.add("");
		}
		// log.debug("p_ship_confirm "+inArgs);
		inArgs.add("");
		inArgs.add("");
		inArgs.add("");
		inArgs.add("");
		inArgs.add("N");

		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		optArgs.add(null);
		optArgs.add(null);
		if (modeOfTransport != null)
			optArgs.add(modeOfTransport);
		else
			optArgs.add("");

		if (incoterm != null)
			optArgs.add(incoterm);
		else
			optArgs.add("");

		Vector v = (Vector) dbManager.doProcedure("p_ship_confirm", inArgs, outArgs, optArgs);
		if (v.get(0) != null) {
			log.info(v.get(0));
		}
		if (v.get(1) != null) {
			log.info(v.get(1));
			throw new BaseException(v.get(1).toString());
		}
	}

	private void setupBean(ShipConfirmInputBean inputBean, ShipmentCreationStageBean bean) {
		bean.setPrNumber(inputBean.getPrNumber());
		bean.setLineItem(inputBean.getLineItem());
		bean.setShipmentId(inputBean.getShipmentId());
		bean.setPicklistId(inputBean.getPicklistId());
	}

	public void sendNotification(Collection shipmentBeanCollection) throws BaseException {
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		try {
			Hashtable emailAddress = new Hashtable();
			Iterator it = shipmentBeanCollection.iterator();
			while (it.hasNext()) {
				ShipmentBean bean = (ShipmentBean) it.next();
				StringBuilder shipmentIdStr = new StringBuilder(bean.getShipmentId().toString());
				
				/* retrieve the email address of every personnel 
				 * who is part of the 'DeliveryNotification' group for this shipment's ship to location 
				 */
				Iterator iterator = factory.selectShipmentNotificationEmail(bean.getShipmentId()).iterator();
				while (iterator.hasNext()) {
					PersonnelBean personnelBean = (PersonnelBean) iterator.next();
					if (!emailAddress.containsKey(personnelBean.getEmail())) {					
						emailAddress.put(personnelBean.getEmail(), shipmentIdStr);
					}
					else {
						StringBuilder s = (StringBuilder) emailAddress.get(personnelBean.getEmail());
						s.append(",").append(bean.getShipmentId().toString());
					}
				}
				
				/* if feature release 'DeliveryNotificationToPRContact' is set
				 * retrieve email address of this shipment's purchase request contact
				 */
				if(isSendPRDeliveryNotification(bean.getShipmentId())) {
					String contact = getPurchaseRequestContact(bean.getShipmentId());
					
					if(!StringHandler.isBlankString(contact))
						emailAddress.put(contact, shipmentIdStr);
				}
			}
			Enumeration myenum = emailAddress.keys();
			while (myenum.hasMoreElements()) {
				String key = myenum.nextElement().toString();
				String shipmentId = emailAddress.get(key).toString();
				String subject = new StringBuilder("Delivery Notification for shipment: ").append(shipmentId).toString();
				String message = new StringBuilder("Delivery Notification for shipment: ").append(shipmentId).append("\n\n").append("Click on link below to print Packing List\n\n")
						.append(hostUrl).append("/tcmIS/hub/printpackinglist.do?shipmentIds=").append(shipmentId).toString();
				MailProcess.sendEmail(key, "", MailProcess.DEFAULT_FROM_EMAIL, subject, message);
				if (log.isDebugEnabled()) {
					log.debug("sending delivery notification to:" + key);
				}
			}
		}
		catch (Exception e) {
			log.error("Error sending shipment notification:" + e.getMessage(), e);
		}
	}

	public void sendNotAutoConfirmNotification(Collection shipConfirmInputBeanCollection) throws BaseException {
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		
		try {
			Hashtable emailAddress = new Hashtable();
			Iterator it = shipConfirmInputBeanCollection.iterator();
			while (it.hasNext()) {
				ShipConfirmInputBean bean = (ShipConfirmInputBean) it.next();
				StringBuilder shipmentIdStr = new StringBuilder(bean.getShipmentId().toString());
				
				/* retrieve the email address of every personnel 
				 * who is part of the 'DeliveryNotification' group for this shipment's ship to location 
				 */
				Iterator iterator = factory.selectShipmentNotificationEmail(bean.getShipmentId()).iterator();
				while (iterator.hasNext()) {
					PersonnelBean personnelBean = (PersonnelBean) iterator.next();
					if (!emailAddress.containsKey(personnelBean.getEmail())) {						
						emailAddress.put(personnelBean.getEmail(), shipmentIdStr);
					}
					else {
						StringBuilder s = (StringBuilder) emailAddress.get(personnelBean.getEmail());
						s.append("," + bean.getShipmentId().toString());
					}
				}
				
				/* if feature release 'DeliveryNotificationToPRContact' is set
				 * retrieve email address of this shipment's purchase request contact
				 */
				if(isSendPRDeliveryNotification(bean.getShipmentId())) {
					String contact = getPurchaseRequestContact(bean.getShipmentId());
					
					if(!StringHandler.isBlankString(contact))
						emailAddress.put(contact, shipmentIdStr);
				}
			}
			Enumeration myenum = emailAddress.keys();
			while (myenum.hasMoreElements()) {
				String key = myenum.nextElement().toString();
				String shipmentId = emailAddress.get(key).toString();
				String subject = new StringBuilder("Delivery Notification for shipment: ").append(shipmentId).toString();
				String message = new StringBuilder("Delivery Notification for shipment: ").append(shipmentId).append("\n\n").append("Click on link below to print Packing List\n\n")
						.append(hostUrl).append("/tcmIS/hub/printpackinglist.do?shipmentIds=").append(shipmentId).toString();
				MailProcess.sendEmail(key, "", MailProcess.DEFAULT_FROM_EMAIL, subject, message);
				if (log.isDebugEnabled()) {
					log.debug("sending delivery notification to:" + key);
				}
			}
		}
		catch (Exception e) {
			log.error("Error sending shipment notification:" + e.getMessage(), e);
		}
	}

	public Collection getShipment(ShipConfirmInputBean bean, String confirmedShipments, PersonnelBean user) throws BaseException, Exception {
		return getShipment(bean, confirmedShipments, user, true);
	}

	public Collection getShipment(ShipConfirmInputBean bean, String confirmedShipments, PersonnelBean user, boolean proFormaPrintedOnly) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ShipmentBeanFactory factory = new ShipmentBeanFactory(dbManager);
		// SearchCriteria criteria = new SearchCriteria();
		// criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
		// bean.getBranchPlant());
		// criteria.addCriterion("shipConfirmDate", SearchCriterion.IS, "null");
		String count = "";
		if (!StringHandler.isBlankString(confirmedShipments)) {
			StringBuilder countQuery = new StringBuilder("select count(*)");
			countQuery.append(" from invoice_group_facility igf, issue i, purchase_request pr, invoice_group ig");
			countQuery.append(" where igf.company_id = pr.company_id and igf.facility_id = pr.facility_id");
			countQuery.append(" and igf.account_sys_id = pr.account_sys_id");
			countQuery.append(" and i.pr_number = pr.pr_number and i.shipment_id in (").append(confirmedShipments);
			countQuery.append(") and igf.invoice_group = ig.invoice_group and ig.invoice_at_shipping = 'Y'");
			count = this.factory.selectSingleValue(countQuery.toString());
		}
		if (!StringHandler.isBlankString(count) && count.equals("0")) {
			confirmedShipments = "";
		}

		// return factory.selectWithCarrier(criteria, new
		// SortCriteria("shipmentId"), confirmedShipments);
		if (confirmedShipments == null)
			confirmedShipments = "";

		StringBuilder shipmentQuery = new StringBuilder();
		shipmentQuery.append("select * from table (pkg_shipment.fx_get_ship_tx_data(");
		shipmentQuery.append(SqlHandler.delimitString(bean.getBranchPlant())).append(",");
		shipmentQuery.append(SqlHandler.delimitString(confirmedShipments)).append("))");
		shipmentQuery.append(" where inventory_group ");
		if (bean.getInventoryGroup() != null && !StringHandler.isBlankString(bean.getInventoryGroup())) {
			shipmentQuery.append(" = ").append(SqlHandler.delimitString(bean.getInventoryGroup()));
		}
		else {
			shipmentQuery.append(" in (select inventory_group from user_group_member_ig where ");
			shipmentQuery.append("personnel_id = ").append(user.getPersonnelId());
			shipmentQuery.append(" and user_group_id = 'shipConfirm')");
		}
		if (proFormaPrintedOnly)
			shipmentQuery.append("and ((pro_forma_required = 'Y' and last_pro_forma_print_date is not null and proposed_export_date <= sysdate-1) or pro_forma_required != 'Y')");
		else
			shipmentQuery.append("and pro_forma_required = 'Y'");

		return factory.selectWithCarrier(shipmentQuery.toString(), "");
	}

	public String generateInvoice(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		String listOfShipmentId = "";
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Iterator it = shipConfirmInputBeanCollection.iterator();
			while (it.hasNext()) {
				ShipConfirmInputBean bean = (ShipConfirmInputBean) it.next();
				String invoiceBy = bean.getInvoiceBy();
				String invoiceAtShipping = bean.getInvoiceAtShipping();
				if (bean.getShipConfirmDate() != null) {
					if ((invoiceAtShipping != null && invoiceAtShipping.equalsIgnoreCase("Y")) && (invoiceBy != null && invoiceBy.equalsIgnoreCase("Order"))) {
						if (listOfShipmentId != null && listOfShipmentId.length() == 0) {
							listOfShipmentId += "" + bean.getShipmentId() + "";
						}
						else {
							listOfShipmentId += "," + bean.getShipmentId() + "";
						}
					}
				}
			}
		}
		return listOfShipmentId;
	}

	/*
	 * public void generateInvoice(ShipConfirmInputBean bean) throws
	 * BaseException, Exception { Vector outArgs = new Vector(); outArgs.add(
	 * new Integer(java.sql.Types.VARCHAR) ); Collection inArgs =
	 * buildProcedureInput(bean.getShipmentId());
	 * dbManager.doProcedure("P_INVOICE_SHIPMENT", inArgs,outArgs); //return
	 * outArgs; }
	 */

	/*
	 * This is to release the page quickly, can be removed after moving to new
	 * code
	 */
	public Vector bolPrintData(Collection pickedMrs) {
		Vector printData = new Vector();
		Vector prnumbers = new Vector();
		Vector linenumber = new Vector();
		Vector picklistid = new Vector();
		Vector pickingUnitId = new Vector();
		Vector boxCount = new Vector();
		Hashtable FResult = new Hashtable();

		Iterator mainIterator = pickedMrs.iterator();
		while (mainIterator.hasNext()) {
			ShipConfirmInputBean shipConfirmInputBean = (ShipConfirmInputBean) mainIterator.next();
			prnumbers.addElement("" + shipConfirmInputBean.getPrNumber() + "");
			linenumber.addElement("" + shipConfirmInputBean.getLineItem() + "");
			// log.debug("getPicklistId "+shipConfirmInputBean.getPicklistId());
			picklistid.addElement("" + shipConfirmInputBean.getPicklistId() + "");
			pickingUnitId.addElement(!StringHandler.isBlankString(shipConfirmInputBean.getPickingUnitId()) ? shipConfirmInputBean.getPickingUnitId() : "");
			boxCount.addElement(!StringHandler.isBlankString(shipConfirmInputBean.getBoxCount()) ? shipConfirmInputBean.getBoxCount() : "1"); // use
																																				// a
																																				// default
																																				// quantity
																																				// of
																																				// 1
		}

		FResult.put("mr_number", prnumbers);
		FResult.put("line_number", linenumber);
		FResult.put("picklist_number", picklistid);
		FResult.put("picking_unit", pickingUnitId);
		FResult.put("lbl_qty", boxCount);
		printData.add(FResult);
		return printData;
	}

	public Collection getPackingList(String hub) throws BaseException, Exception {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ShipmentBean());

		String query = "select BRANCH_PLANT, CARRIER_CODE, COMPANY_ID, DATE_DELIVERED, INVENTORY_GROUP,"
				+ "SHIP_CONFIRM_DATE, SHIP_TO_LOCATION_ID, SHIPMENT_ID, TRACKING_NUMBER  from shipment " + "where BRANCH_PLANT='" + hub
				+ "' and SHIP_CONFIRM_DATE is null order by SHIPMENT_ID";

		return factory.selectQuery(query);

	}

	public Collection getModeOfTransportData() throws BaseException {
		StringBuffer query = new StringBuffer("select * from VV_MODE_OF_TRANSPORT");
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvMotIncotermBean());
		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection getIncotermForModeOfTransport() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), locale.toString());
		Connection conn = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		Collection<VvMotIncotermBean> modeOfTransportCol = getModeOfTransportData();

		for (VvMotIncotermBean modeOfTransportbean : modeOfTransportCol) {
			factory.setBeanObject(new VvIncotermBean());
			modeOfTransportbean.setVvIncotermList(factory.selectQuery(
					"select mot.mode_of_transport, inc.incoterm, inc.incoterm_short_desc from vv_incoterm inc, vv_incoterm_mode_of_transport mot where inc.incoterm = mot.incoterm "
							+ " and mot.mode_of_transport = '" + modeOfTransportbean.getModeOfTransport() + "'",
					conn));
		}

		return modeOfTransportCol;
	}

	public String generateCmsInvoice(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		String listOfShipmentId = "";
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Iterator it = shipConfirmInputBeanCollection.iterator();
			while (it.hasNext()) {
				ShipConfirmInputBean bean = (ShipConfirmInputBean) it.next();
				String invoiceBy = bean.getInvoiceBy();
				String invoiceAtShipping = bean.getInvoiceAtShipping();
				if (bean.getShipConfirmDate() != null) {
					if ((invoiceAtShipping != null && invoiceAtShipping.equalsIgnoreCase("Y")) && (invoiceBy != null && invoiceBy.equalsIgnoreCase("Shipment"))) {
						if (listOfShipmentId != null && listOfShipmentId.length() == 0) {
							listOfShipmentId += "" + bean.getShipmentId() + "";
						}
						else {
							listOfShipmentId += "," + bean.getShipmentId() + "";
						}
					}
				}
			}
		}
		return listOfShipmentId;
	}

	public String generateProForma(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		String listOfShipmentId = "";
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Iterator it = shipConfirmInputBeanCollection.iterator();
			while (it.hasNext()) {
				ShipConfirmInputBean inputBean = (ShipConfirmInputBean) it.next();

				if (inputBean.getProFormaRequired() != null && inputBean.getProFormaRequired().equalsIgnoreCase("Y")) {
					if (listOfShipmentId != null && listOfShipmentId.length() == 0) {
						listOfShipmentId += "" + inputBean.getShipmentId() + "";
					}
					else {
						listOfShipmentId += "," + inputBean.getShipmentId() + "";
					}
				}
			}
		}
		return listOfShipmentId;
	}

	public void udpateProposedExportDate(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Connection connection = dbManager.getConnection();
			try {
				Iterator it = shipConfirmInputBeanCollection.iterator();
				ShipConfirmInputBean inputBean;
				String query;

				while (it.hasNext()) {
					inputBean = (ShipConfirmInputBean) it.next();
					if (inputBean.getProposedExportDate() != null) {
						query = "UPDATE shipment SET proposed_export_date = " + DateHandler.getOracleToDateFunction(inputBean.getProposedExportDate()) + " WHERE shipment_id = "
								+ inputBean.getShipmentId();
						this.factory.deleteInsertUpdate(query, connection);
					}
				}
			}
			finally {
				if (connection != null) {
					dbManager.returnConnection(connection);
				}
			}
		}
	}

	public void udpateShipmentBy(Collection shipConfirmInputBeanCollection) throws BaseException, Exception {
		if (shipConfirmInputBeanCollection != null && shipConfirmInputBeanCollection.size() > 0) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			Connection connection = dbManager.getConnection();
			try {
				Iterator it = shipConfirmInputBeanCollection.iterator();
				boolean noData = true;

				ShipConfirmInputBean inputBean;
				StringBuilder query;

				while (it.hasNext()) {
					inputBean = (ShipConfirmInputBean) it.next();
					query = new StringBuilder("UPDATE shipment SET");

					if (!StringHandler.isBlankString(inputBean.getCarrierCode())) {
						query.append(" carrier_code = ").append(SqlHandler.delimitString(inputBean.getCarrierCode()));
						noData = false;
					}

					if (!StringHandler.isBlankString(inputBean.getTrackingNumber())) {
						if (!noData)
							query.append(",");
						query.append(" tracking_number = ").append(SqlHandler.delimitString(inputBean.getTrackingNumber()));
						noData = false;
					}

					if (!noData) {
						query.append(" WHERE shipment_id = " + inputBean.getShipmentId());
						this.factory.deleteInsertUpdate(query.toString(), connection);
					}
				}
			}
			finally {
				if (connection != null) {
					dbManager.returnConnection(connection);
				}
			}
		}
	}
	
	public String getPurchaseRequestContact(BigDecimal shipmentId) throws BaseException, Exception {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ShipmentBean());

		String query = "SELECT pr.contact_info " + 
						 "FROM customer.purchase_request pr, tcm_ops.issue i  " + 
						"WHERE i.shipment_id = " + shipmentId.intValue() + 
						  "AND pr.pr_number = i.pr_number";

		return factory.selectSingleValue(query);

	}
	
	public boolean isSendPRDeliveryNotification(BigDecimal shipmentId) throws BaseException, Exception {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ShipmentBean());

		String query = "SELECT scope " + 
						 "FROM customer.feature_release fr, tcm_ops.shipment s " + 
						"WHERE fr.feature = 'PRDeliveryNotification' " + 
						  "AND s.shipment_id = " + shipmentId.intValue() + 
						  "AND fr.company_id = s.company_id " + 
						  "AND fr.scope in ('ALL', (SELECT pr.facility_id " + 
						  							 "FROM customer.purchase_request pr " + 
						  							"WHERE pr.pr_number = (SELECT i.pr_number " + 
						  													"FROM issue i " + 
						  													"WHERE i.shipment_id = s.shipment_id)))" +
						  "AND fr.active = 'Y'"; 

		Collection results = factory.selectQuery(query);
		
		if(results != null && results.size() >= 1)
			return true;
		
		return false;
	}
}
