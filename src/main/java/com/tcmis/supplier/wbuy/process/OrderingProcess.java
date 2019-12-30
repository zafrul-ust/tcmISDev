/******************************************************
 * OrderingProcess.java
 *
 * Common support functions for Purchase Actions
 ******************************************************
 */

package com.tcmis.supplier.wbuy.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.factory.ItemBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.supplier.wbuy.beans.OrderListInputBean;
import com.tcmis.supplier.wbuy.beans.PoHeaderViewBean;
import com.tcmis.supplier.wbuy.beans.PoLineDetailViewBean;
import com.tcmis.supplier.wbuy.beans.WbuyStatusViewBean;
import com.tcmis.supplier.wbuy.factory.InternalFlowdownsBeanFactory;
import com.tcmis.supplier.wbuy.factory.InternalSpecsBeanFactory;
import com.tcmis.supplier.wbuy.factory.PoHeaderViewBeanFactory;
import com.tcmis.supplier.wbuy.factory.PoLineDetailViewBeanFactory;
import com.tcmis.supplier.wbuy.factory.ProblemRejectionWbuyViewBeanFactory;
import com.tcmis.supplier.wbuy.factory.WbuyStatusViewBeanFactory;

public class OrderingProcess extends BaseProcess {

	public static final String STATUS_NEW = "NEW";
	public static final String STATUS_ACK = "ACKNOWLEDGED";
	public static final String STATUS_PROB = "PROBLEM";
	public static final String STATUS_CONF = "CONFIRMED";
	public static final String STATUS_RES = "RESOLVED";
	public static final String STATUS_REJ = "REJECTED";

	public static final String NB_STAT_NEW = "StatNew"; // Nav Bean
	public static final String NB_STAT_ACK = "StatAck"; // Nav Bean
	public static final String NB_STAT_PROB = "StatProb"; // Nav Bean
	public static final String NB_STAT_CONF = "StatConf"; // Nav Bean
	public static final String NB_STAT_RES = "StatRes"; // Nav Bean
	public static final String NB_STAT_REJ = "StatRej"; // Nav Bean

	Log log = LogFactory.getLog(this.getClass());

	public OrderingProcess(String client) {
		super(client);
	}

	/*
	 * @return A Collection of WbuyStatusViewBeans (orders for this supplier)
	 */
	public Collection selectOrders(PersonnelBean user, String days, String[] stats) throws BaseException {
		log.info("executing selectOrders");
		DbManager dbManager = new DbManager(this.getClient());
		WbuyStatusViewBeanFactory wbuyStatusFactory = new WbuyStatusViewBeanFactory(dbManager);

		String statlist = "";
		int counter = 0;
		for (int i = 0; i < stats.length; i++) {
			if (stats[i] != null && stats[i].length() > 0) {
				if (counter != 0) {
					statlist += ", ";
				}
				statlist += "'" + stats[i] + "'";
				counter++;
			}
		}

		String po_conditions = "";
		if (statlist != null && statlist.length() > 0) {
			po_conditions = "dbuy_status in (" + statlist + ") and ";
		}
		po_conditions += "supplier in (select supplier from customer.user_supplier where personnel_id =" + user.getPersonnelId() + ")";
		if (days != null && days.trim().length() > 0) {
			po_conditions += " and days_since_last_status <= " + days;
		}
		if (log.isDebugEnabled()) {
			log.debug("select orders condition: " + po_conditions);
		}
		Collection poBeans = wbuyStatusFactory.select(po_conditions);
		dbManager = null;
		wbuyStatusFactory = null;

		return poBeans;
	}

	public Collection selectOrdersUsingSupplier(PersonnelBean user, String days, String[] stats, OrderListInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(this.getClient());
		WbuyStatusViewBeanFactory wbuyStatusFactory = new WbuyStatusViewBeanFactory(dbManager);

		String statlist = "";
		int statusCounter = 0;
		for (int i = 0; i < stats.length; i++) {
			if (stats[i] != null && stats[i].length() > 0) {
				if (statusCounter != 0) {
					statlist += ", ";
				}
				statlist += "'" + stats[i] + "'";
				statusCounter++;
			}
		}

		String po_conditions = "";
		if (statlist != null && statlist.length() > 0) {
			po_conditions = "dbuy_status in (" + statlist + ") and ";
		}

		String supplierlist = "";
		int beanCounter = 0;
		for (int i = 0; i < bean.getSupplierIdArray().length; i++) {
			if (bean.getSupplierIdArray()[i] != null && bean.getSupplierIdArray()[i].length() > 0) {
				if (beanCounter != 0) {
					supplierlist += ", ";
				}
				supplierlist += "'" + bean.getSupplierIdArray()[i] + "'";
				beanCounter++;
			}
		}

		if (supplierlist != null && supplierlist.length() > 0) {
			po_conditions += "supplier in (" + supplierlist + ") and ";
		}
		po_conditions += "supplier in (select supplier from customer.user_supplier where personnel_id =" + user.getPersonnelId() + ")";
		if (days != null && days.trim().length() > 0) {
			po_conditions += " and days_since_last_status <= " + days;
		}
		if (log.isDebugEnabled()) {
			log.debug("select orders condition: " + po_conditions);
		}
		Collection poBeans = wbuyStatusFactory.select(po_conditions);
		dbManager = null;
		wbuyStatusFactory = null;

		return poBeans;
	}

	/*
	 * @return A Collection Bean of one order for this supplier
	 */
	public Collection selectOrder(PersonnelBean user, String ponum) throws BaseException {
		log.info("executing selectOrder");
		Collection poBeans = searchOrder(user, ponum);
		return poBeans;
	}

	/*
	 * @return A PoHeaderView Bean
	 */
	public PoHeaderViewBean getPOHeader(PersonnelBean user, String radianpo) throws BaseException {
		PoHeaderViewBean poHeaderBean = null;
		DbManager dbManager = new DbManager(this.getClient());
		PoHeaderViewBeanFactory poHeaderFactory = new PoHeaderViewBeanFactory(dbManager);

		String condition = "radian_po=" + radianpo + " ";
		condition += "and supplier in (select supplier from customer.user_group_member_supplier where personnel_id = " + user.getPersonnelId() + ")";
		try {
			Collection c = poHeaderFactory.select(condition);
			if (c != null && !c.isEmpty()) {
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					poHeaderBean = (PoHeaderViewBean) iter.next();
				}
			}
		}
		catch (BaseException be) {
			log.error("Base Exception in query po header: " + be);
		}
		finally {
			dbManager = null;
			poHeaderFactory = null;
		}
		return poHeaderBean;
	}

	/*
	 * @return A WbuyStatusView Bean
	 */
	public WbuyStatusViewBean getWbuyPo(PersonnelBean user, String radianpo) throws BaseException {
		WbuyStatusViewBean wbuyPo = null;
		Collection c = searchOrder(user, radianpo);
		if (c != null && !c.isEmpty()) {
			Iterator iter = c.iterator();
			if (iter.hasNext()) {
				wbuyPo = (WbuyStatusViewBean) iter.next();
			}
		}
		return wbuyPo;
	}

	/*
	 * @return A Collection of PoLineDetailView beans
	 */
	public Collection getPOLines(String radianpo) throws BaseException {
		Collection lineBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		PoLineDetailViewBeanFactory poLinesFactory = new PoLineDetailViewBeanFactory(dbManager);
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("radianPo", SearchCriterion.EQUALS, radianpo);
			lineBeans = poLinesFactory.select(searchCriteria);
		}
		catch (BaseException be2) {
			log.error("Base Exception in getPOLines: " + be2);
		}
		finally {
			dbManager = null;
			poLinesFactory = null;
		}
		return lineBeans;
	}

	/*
	 * @return A Collection of PoLineDetailViewBeans (materials only)
	 */
	public Collection getMaterialLines(String radianpo) throws BaseException {
		Collection materialBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		PoLineDetailViewBeanFactory poLinesFactory = new PoLineDetailViewBeanFactory(dbManager);
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("radianPo", SearchCriterion.EQUALS, radianpo);
			searchCriteria.addCriterion("itemType", SearchCriterion.EQUALS, "MA");
			searchCriteria.addValueToCriterion("itemType", "OB");
			searchCriteria.addValueToCriterion("itemType", "MP");
			searchCriteria.addValueToCriterion("itemType", "BG");
			searchCriteria.addValueToCriterion("itemType", "MV");
			materialBeans = poLinesFactory.select(searchCriteria);
		}
		catch (BaseException be2) {
			log.error("Base Exception in getMaterialLines: " + be2);
		}
		finally {
			dbManager = null;
			poLinesFactory = null;
		}
		return materialBeans;
	}

	/*
	 * @return A Collection of PoLineDetailViewBeans (additional charges only)
	 */
	public Collection getAddChargeLines(String radianpo) throws BaseException {
		Collection addchargeBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		PoLineDetailViewBeanFactory poLinesFactory = new PoLineDetailViewBeanFactory(dbManager);
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("radianPo", SearchCriterion.EQUALS, radianpo);
			searchCriteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL, "MA");
			searchCriteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL, "OB");
			searchCriteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL, "MP");
			searchCriteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL, "BG");
			searchCriteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL, "MV");
			addchargeBeans = poLinesFactory.select(searchCriteria);
		}
		catch (BaseException be2) {
			log.error("Base Exception in getAddChargeLines: " + be2);
		}
		finally {
			dbManager = null;
			poLinesFactory = null;
		}
		return addchargeBeans;
	}

	public Collection getAllLineData(PoHeaderViewBean poHeaderBean, Collection lineBeans) throws BaseException {
		Collection specBeans = new Vector();
		Collection flowdownBeans = new Vector();
		String specSQL = null;
		String flowdownSQL = null;
		DbManager dbManager;
		dbManager = new DbManager(this.getClient());

		InternalSpecsBeanFactory specsFactory = new InternalSpecsBeanFactory(dbManager);
		InternalFlowdownsBeanFactory flowdownsFactory = new InternalFlowdownsBeanFactory(dbManager);

		if (poHeaderBean != null && lineBeans != null) {
			Iterator iter = lineBeans.iterator();
			while (iter.hasNext()) {
				PoLineDetailViewBean lineBean = (PoLineDetailViewBean) iter.next();
				// specs
				specSQL = getSpecSQL(poHeaderBean, lineBean);
				// flowdowns
				flowdownSQL = getFlowdownSQL(poHeaderBean, lineBean);

				if (log.isDebugEnabled()) {
					log.debug("getting specs view");
				}
				if (specSQL != null) {
					specSQL = "SELECT distinct spec_id,spec_library,detail,saved_coc,saved_coa, spec_id_display FROM ( " + specSQL + " ) WHERE (saved_coc = 'Y' or saved_coa = 'Y')";
					try {
						specBeans = specsFactory.select(specSQL);
						lineBean.setSpecs(specBeans);
					}
					catch (BaseException e) {
						log.error("Exception getting data for specs: " + e);
					}
				}
				log.debug("gettting flowdowns");
				if (flowdownSQL != null) {
					flowdownSQL = "SELECT distinct company_id, catalog_id, flow_down, flow_down_desc, revision_date, flow_down_type FROM ( " + flowdownSQL + " ) WHERE OK = 'Y'";
					try {
						flowdownBeans = flowdownsFactory.select(flowdownSQL);
						lineBean.setFlowdowns(flowdownBeans);
					}
					catch (BaseException e) {
						log.error("Exception getting data for flowdown: " + e);
					}
				}
			}
		}

		return lineBeans;
	}

	/*
	 * return a Collection of ItemBean
	 */
	public Collection listCharges(String invGrp) {
		Collection chargeList = null;
		try {
			DbManager dbManager = new DbManager(this.getClient());
			ItemBeanFactory itemFactory = new ItemBeanFactory(dbManager);

			String chkAckQry = "select item.item_id,item.item_desc from item, vv_item_type it where item.item_type = it.item_type and it.ma_add_charge = 'y' and it.cost = 'y' and it.reimbursable = 'y'order by item.item_id";
			chargeList = itemFactory.select(chkAckQry);

		}
		catch (BaseException be) {
			log.error("Base Exception in OrderingProcess::listCharges: " + be);
		}
		catch (Exception e) {
			log.error("unknown exception in listCharges:: " + e);
		}
		return chargeList;
	}

	/*
	 * @return A Collection of ProblemRejectionWbuyViewBeans
	 */
	public Collection getProbsRejects(PersonnelBean user, String problem, String reject) {
		log.debug("getting Problem/rejects list");
		Collection probs = null;
		try {
			DbManager dbManager = new DbManager(this.getClient());
			
			String po_conditions = "supplier in (select supplier from customer.user_supplier where personnel_id =" + user.getPersonnelId() + ")";
			if (problem != null && reject == null) {
				po_conditions += " and dbuy_status='PROBLEM'";
			}
			else if (problem == null && reject != null) {
				po_conditions += " and dbuy_status='REJECTED'";
			}
			ProblemRejectionWbuyViewBeanFactory prwvbFactory = new ProblemRejectionWbuyViewBeanFactory(dbManager);
			probs = prwvbFactory.select(po_conditions);
		}
		catch (BaseException be) {
			log.error("Base Exception in OrderingProcess::getProbsRejects: " + be);
		}
		catch (Exception e) {
			log.error("unknown exception in getProbRejects:: " + e);
		}

		return probs;
	}

	public boolean findProblemsOrRejections(Collection col) {
		boolean found = false;
		WbuyStatusViewBean statusBean = null;
		String tmpStatus = null;
		Iterator iter = col.iterator();
		try {
			while (iter.hasNext() && !found) {
				statusBean = (WbuyStatusViewBean) iter.next();
				tmpStatus = BeanHandler.getField(statusBean, "dbuy_status");
				found = tmpStatus.equalsIgnoreCase(STATUS_PROB) || tmpStatus.equalsIgnoreCase(STATUS_REJ);
			}
		}
		catch (BaseException be) {
			log.error("Base Exception getting data for field dbuy_status");
		}
		return found;
	}

	/*
	 * Add a line to the PO
	 */
	public String saveLine(String po, int line, long itemID, int quantity, String price, Date needDate, Date shipDate, Date dockDate, String mfgPartNo, String suppPartNo, String dpaRating, String lineNote, int userID, String cofc, String cofa,
			int shelfLife, String delivNote, String currency, String supplier, String confirm, String shipFromLocationId) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
		DbManager dbManager = new DbManager(this.getClient());
		// first convert java dates strings to timestamps (for oracle)
		Timestamp ship = null;
		Timestamp dock = null;
		Timestamp need = null;

		if (shipDate != null)
			ship = new Timestamp(shipDate.getTime());
		if (dockDate != null)
			dock = new Timestamp(dockDate.getTime());
		if (needDate != null)
			need = new Timestamp(needDate.getTime());
		// convert needed numbers from Strings
		DecimalFormat dblFmt = new DecimalFormat("####.000###");
		String fmtPrice = dblFmt.format(Double.parseDouble(price));
		double unitPrice = Double.parseDouble(fmtPrice);
		log.info("call pkg_po.save_po_line:" + po + "," + line + "," + itemID + "," + confirm + "," + quantity + "," + unitPrice + "," + need + "," + dock + ",null," + mfgPartNo + "," + suppPartNo + "," + dpaRating + ",null,null," + lineNote + ",null," + userID + ",null,null,null," + cofc + "," + cofa
			+ ",null," + shelfLife + "," + delivNote + "," + ship + "," + currency + "," + supplier + "," + shipFromLocationId);

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_po.save_po_line(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, po);

			cs.setInt(2, line);

			cs.setInt(3, 0); // ammendment

			cs.setLong(4, itemID);

			if (confirm != null) {
				cs.setString(5, confirm);

			}
			else {
				cs.setNull(5, Types.VARCHAR);
			}
			cs.setInt(6, quantity);

			cs.setDouble(7, unitPrice);

			if (need != null) {
				cs.setTimestamp(8, need);
			}
			else {
				cs.setNull(8, Types.VARCHAR);
			}
			if (dock != null) {
				cs.setTimestamp(9, dock); // promised Date
			}
			else {
				cs.setNull(9, Types.VARCHAR);
			}

			cs.setNull(10, Types.INTEGER); // allowed price variance

			if (mfgPartNo != null) {
				cs.setString(11, mfgPartNo);
			}
			else {
				cs.setNull(11, Types.VARCHAR);
			}
			if (suppPartNo != null) {
				cs.setString(12, suppPartNo);
			}
			else {
				cs.setNull(12, Types.VARCHAR);
			}
			if (dpaRating != null) {
				cs.setString(13, dpaRating);
			}
			else {
				cs.setNull(13, Types.VARCHAR);
			}
			cs.setNull(14, Types.VARCHAR); // quality flow downs

			cs.setNull(15, Types.VARCHAR); // packaging flow downs

			if (lineNote != null) {
				cs.setString(16, lineNote);
			}
			else {
				cs.setNull(16, Types.VARCHAR);
			}
			cs.setNull(17, Types.VARCHAR); // ammendment remarks

			cs.setInt(18, userID);

			cs.setNull(19, Types.INTEGER); // supplier qty

			cs.setNull(20, Types.VARCHAR); // supplier pkg

			cs.setNull(21, Types.FLOAT); // supplier unit price

			if (cofc != null) {
				cs.setString(22, cofc); // c of c
			}
			else {
				cs.setNull(22, Types.VARCHAR);
			}
			if (cofa != null) {
				cs.setString(23, cofa); // c of a
			}
			else {
				cs.setNull(23, Types.VARCHAR);
			}
			cs.setNull(24, Types.VARCHAR); // msds requested date

			if (shelfLife > 0) {
				cs.setInt(25, shelfLife);
			}
			else {
				cs.setNull(25, Types.INTEGER);
			}
			if (delivNote != null) {
				cs.setString(26, delivNote);
			}
			else {
				cs.setNull(26, Types.VARCHAR);
			}
			if (ship != null) {
				cs.setTimestamp(27, ship);
			}
			else {
				cs.setNull(27, Types.VARCHAR);
			}
			if (currency != null) {
				cs.setString(28, currency);
			}
			else {
				cs.setNull(28, Types.VARCHAR);
			}
			if (supplier != null) {
				cs.setString(29, supplier);
			}
			else {
				cs.setNull(29, Types.VARCHAR);
			}

			cs.registerOutParameter(30, OracleTypes.VARCHAR);
			cs.registerOutParameter(31, OracleTypes.VARCHAR);
			if (shipFromLocationId != null) {
				cs.setString(32, shipFromLocationId);
			}
			else {
				cs.setNull(32, Types.VARCHAR);
			}
			cs.execute();

			Object o = cs.getObject(30);
			if (o != null) {
				result = o.toString();
			}

		}
		catch (SQLException sqle) {
			bulkMailProcess.sendBulkEmail(new BigDecimal("86405"), "Error Calling pkg_po.SAVE_PO_LINE in WBUY PO Page- " + po + " line " + line + " amendment 0 ITEM " + itemID + "", "Error Calling pkg_po.SAVE_PO_LINE\nError Msg:\n"
					+ sqle.getMessage() + "\n  PO Page- " + po + " line " + line + " ITEM " + itemID + "\n\nPersonnel ID:  " + userID + "", false);
			result = sqle.toString();
		}
		finally {
			dbManager.returnConnection(connect1);
		}
		return result;
	}

	public String removeLine(String po, int line, int amendment) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());

		DbManager dbManager = new DbManager(this.getClient());
		connect1 = dbManager.getConnection();
		try {
			log.debug("call pkg_po.DELETE_PO_LINE: " + po + "," + line + "," + amendment);
			cs = connect1.prepareCall("{ call pkg_po.DELETE_PO_LINE (?,?,?,?) }");
			BigDecimal bigponumber = new BigDecimal(po);
			cs.setBigDecimal(1, bigponumber);
			cs.setInt(2, line);
			cs.setInt(3, amendment);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();

			Object o = cs.getObject(4);
			if (o != null)
				result = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in removeLine: " + sqle);
			bulkMailProcess.sendBulkEmail(new BigDecimal("86405"), "Error Calling  pkg_po.DELETE_PO_LINE in WBUY PO Page- " + po + " line " + line + " amendment " + amendment + "", "Error Calling  pkg_po.DELETE_PO_LINE\nError Msg:\n"
					+ sqle.getMessage() + "\n  PO Page- " + po + " line " + line + " amendment " + amendment + "", false);
			result = sqle.toString();
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return result;
	}

	public String addCharges(String po, int line, int add_line, int amendment) throws BaseException {
		String result = null;
		BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
		String insertCmd = "insert into po_add_charge_alloc_draft values (" + po + "," + line + "," + add_line + "," + amendment + " )";
		try {
			doUpdate(insertCmd);
		}
		catch (Exception sqle) {
			log.error("Exception in addCharges: " + sqle);
			bulkMailProcess.sendBulkEmail(new BigDecimal("86405"), "Error in addCharges WBUY PO Page- " + po + " line " + line + " amendment " + amendment + "", "Error in addCharges WBUY PO\nError Msg:\n" + sqle.getMessage() + "\n  PO Page- "
					+ po + " line " + line + " amendment " + amendment + "", false);

			result = sqle.toString();
		}
		return result;
	}

	public Collection updateMaterialsBean(Collection materials, String row, String field, Object value) {

		PoLineDetailViewBean line = null;
		Iterator iter = materials.iterator();
		while (iter.hasNext()) {
			line = (PoLineDetailViewBean) iter.next();
			if (line.getPoLine() != null) {

			}

		}
		return materials;
	}

	/**
	 * Get the SQL string needed to search for the specs for this line on this PO
	 */
	private String getSpecSQL(PoHeaderViewBean header, PoLineDetailViewBean line) throws BaseException {
		String resultQuery = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}");

			String shipToLoc = header.getShipToLocationId();
			String shiptocompanyid = header.getShipToCompanyId();

			String invengrp = header.getInventoryGroup();
			BigDecimal itemID = line.getItemId();
			BigDecimal radianpo = header.getRadianPo();
			BigDecimal radianbpo = header.getBo();
			BigDecimal lineID = line.getPoLine();
			BigDecimal amendment = line.getAmendment();
			log.debug("call pkg_po.Po_spec: " + shipToLoc + "," + shiptocompanyid + "," + itemID + "," + radianpo + "|" + radianbpo + "," + lineID + "," + amendment + "," + invengrp);

			cs.setString(1, shipToLoc); // Ship To Loc
			if (shiptocompanyid.length() > 0) { // Ship To Company ID
				cs.setString(2, shiptocompanyid);
			}
			else {
				cs.setNull(2, OracleTypes.VARCHAR);
			}
			if (itemID.longValue() > 0) { // Item ID
				cs.setBigDecimal(3, itemID);
			}
			else {
				cs.setNull(3, OracleTypes.INTEGER);
			}
			if (radianpo.intValue() > 0) { // Radian PO
				cs.setBigDecimal(4, radianpo);
			}
			else if (radianbpo.intValue() > 0) {
				cs.setBigDecimal(4, radianbpo);
			}
			else {
				cs.setNull(4, OracleTypes.INTEGER);
			}
			if (lineID.longValue() > 0) { // Line ID
				cs.setBigDecimal(5, lineID);
			}
			else {
				cs.setNull(5, OracleTypes.INTEGER);
			}
			if (amendment.longValue() >= 0) { // amendment
				cs.setBigDecimal(6, amendment);
			}
			else {
				cs.setNull(6, OracleTypes.INTEGER);
			}
			if (invengrp != null && invengrp.length() > 0) { // Inventory Group
				cs.setString(7, invengrp);
			}
			else {
				cs.setNull(7, OracleTypes.VARCHAR);
			}

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(8);
			if (o != null)
				resultQuery = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in getSpecSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return resultQuery;
	}

	/*
	 * Get the SQL query that we will need to look up the flowdowns for this line of this PO
	 */
	private String getFlowdownSQL(PoHeaderViewBean header, PoLineDetailViewBean line) throws BaseException {
		String resultQuery = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}");

			String shipToLoc = header.getShipToLocationId();
			String shiptocompanyid = header.getShipToCompanyId();

			BigDecimal itemID = line.getItemId();
			BigDecimal radianpo = header.getRadianPo();
			BigDecimal radianbpo = header.getBo();
			BigDecimal lineID = line.getPoLine();
			BigDecimal amendment = line.getAmendment();
			log.debug("call pkg_po.Po_flowdown: " + shipToLoc + "," + shiptocompanyid + "," + itemID + "," + radianpo + "|" + radianbpo + "," + lineID + "," + amendment);

			cs.setString(1, shipToLoc); // Ship To Loc
			if (shiptocompanyid.length() > 0) { // Ship To Company ID
				cs.setString(2, shiptocompanyid);
			}
			else {
				cs.setNull(2, OracleTypes.VARCHAR);
			}
			if (itemID.longValue() > 0) { // Item ID
				cs.setBigDecimal(3, itemID);
			}
			else {
				cs.setNull(3, OracleTypes.INTEGER);
			}
			if (radianpo.intValue() > 0) { // Radian PO
				cs.setBigDecimal(4, radianpo);
			}
			else if (radianbpo.intValue() > 0) {
				cs.setBigDecimal(4, radianbpo);
			}
			else {
				cs.setNull(4, OracleTypes.INTEGER);
			}
			if (lineID.longValue() > 0) { // Line ID
				cs.setBigDecimal(5, lineID);
			}
			else {
				cs.setNull(5, OracleTypes.INTEGER);
			}
			if (amendment.longValue() >= 0) { // amendment
				cs.setBigDecimal(6, amendment);
			}
			else {
				cs.setNull(6, OracleTypes.INTEGER);
			}

			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(7);
			if (o != null)
				resultQuery = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in getFlowdownSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return resultQuery;
	}

	/*
	 * @return NULL if PO acknowledge call ran OK, or the error that occurred
	 */
	public String acknowledgePO(BigDecimal radianpo, BigDecimal personnelId) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());
		
		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_dbuy.acknowledgement_po_for_wbuy(?,?,?)}");
			log.debug("call pkg_dbuy.acknowledgement_po_for_wbuy: " + radianpo + "," + personnelId);
			cs.setBigDecimal(1, radianpo);
			cs.setBigDecimal(2, personnelId);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.execute();
			Object o = cs.getObject(3);
			if (o != null)
				result = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in acknowledgePO: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}
		return result;
	}

	/*
	 * @return NULL if PO sent call ran OK, or the error that occurred
	 */
	public String sentPO(PoHeaderViewBean poHeaderBean, int personnelID) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_dbuy.sent_po_for_wbuy(?,?,?)}");
			log.debug("call pkg_dbuy.sent_po_for_wbuy: " + poHeaderBean.getRadianPo() + "," + personnelID);
			cs.setBigDecimal(1, poHeaderBean.getRadianPo());
			cs.setInt(2, personnelID);

			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(3);
			if (o != null)
				result = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in sentPO: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return result;
	}

	/*
	 * @return NULL if PO reject call ran OK, or the error that occurred
	 */
	public String rejectPO(PoHeaderViewBean poHeaderBean, int personnelID, String msg) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_dbuy.reject_po_for_wbuy(?,?,?,?)}");
			log.debug("call pkg_dbuy.reject_po_for_wbuy: " + poHeaderBean.getRadianPo() + "," + personnelID + "," + msg);
			cs.setBigDecimal(1, poHeaderBean.getRadianPo());
			cs.setInt(2, personnelID);
			cs.setString(3, msg);

			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(4);
			if (o != null)
				result = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in rejectPO: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return result;
	}

	/*
	 * @return NULL if PO problem call ran OK, or the error that occurred
	 */
	public String problemPO(PoHeaderViewBean poHeaderBean, int personnelID, String reason) throws BaseException {
		String result = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_dbuy.problem_po_for_wbuy(?,?,?,?)}");
			log.debug("call pkg_dbuy.problem_po_for_wbuy: " + poHeaderBean.getRadianPo() + "," + personnelID + "," + reason);
			cs.setBigDecimal(1, poHeaderBean.getRadianPo());
			cs.setInt(2, personnelID);
			cs.setString(3, reason);

			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(4);
			if (o != null)
				result = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in problemPO: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}

		return result;
	}

	/*
	 * @return NULL if confirmedStatus, or the error
	 */
	public String getConfirmedStatus(String po) throws BaseException {
		String status = null;
		DbManager dbManager = new DbManager(this.getClient());
		WbuyStatusViewBeanFactory wbuyStatusFactory = new WbuyStatusViewBeanFactory(dbManager);
		String po_conditions = "radian_po=" + po + "";
		Collection poBeans = wbuyStatusFactory.select(po_conditions);
		if (poBeans != null) {
			try {
				Iterator iterator = poBeans.iterator();
				while (iterator.hasNext()) {
					WbuyStatusViewBean inputBean = (WbuyStatusViewBean) iterator.next();
					status = inputBean.getDbuyStatus();
				}
			}
			catch (Exception e1) {
				log.error(e1);
			}
		}
		
		if (status != null && (status.equalsIgnoreCase(OrderingProcess.STATUS_CONF) || status.equalsIgnoreCase(OrderingProcess.STATUS_PROB) || status.equalsIgnoreCase(OrderingProcess.STATUS_REJ))) {
			return status;
		}
		else {
			return null;
		}
	}

	/*
	 * @return The comments the user put in, which are in wbuy_status_view
	 */
	public String getUserComments(String po) throws BaseException {
		String comments = null;
		ResultSet rs = null;
		DbManager dbManager = new DbManager(this.getClient());
		WbuyStatusViewBeanFactory wbuyStatusFactory = new WbuyStatusViewBeanFactory(dbManager);
		String po_conditions = "radian_po=" + po + "";
		Collection poBeans = wbuyStatusFactory.select(po_conditions);
		if (poBeans != null) {
			try {
				Iterator iterator = poBeans.iterator();
				while (iterator.hasNext()) {
					WbuyStatusViewBean inputBean = (WbuyStatusViewBean) iterator.next();
					comments = inputBean.getComments();
				}
			}
			catch (Exception e1) {
				log.error(e1);
			}
		}

		return comments;
	}

	/*
	 * Sort the Collection based on the column specified by sortColumn
	 */
	public Collection sortOrders(Collection orders, String sortColumn) throws BaseException {
		BeanSorter beanSorter = new BeanSorter(new WbuyStatusViewBean());
		String sortFieldGetter = getFieldGetterName(sortColumn);
		return beanSorter.sort(orders, sortFieldGetter);
	}

	private Collection searchOrder(PersonnelBean user, String ponum) throws BaseException {
		Collection poBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		WbuyStatusViewBeanFactory wbuyStatusFactory = new WbuyStatusViewBeanFactory(dbManager);
		String po_conditions = "radian_po='" + ponum + "' and ";
		po_conditions += "supplier in (select supplier from customer.user_supplier where personnel_id =" + user.getPersonnelId() + ")";

		poBeans = wbuyStatusFactory.select(po_conditions);
		dbManager = null;
		wbuyStatusFactory = null;

		return poBeans;
	}

	private void doUpdate(String query) throws BaseException {
		Connection conn = null;
		DbManager dbManager = null;
		try {
			dbManager = new DbManager(this.getClient());
			conn = dbManager.getConnection();
			Statement stmt = conn.createStatement();
			log.debug("Order Process Update Query: " + query);
			stmt.executeUpdate(query);
		}
		catch (DbConnectionException dbce) {
			log.error("Db Connection Exception executing internal order process Update: " + dbce);
		}
		catch (SQLException sqle) {
			log.error("SQL Exception executing internal order process Update: " + sqle + "\nQuery: " + query);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	/*
	 * create the name of the getter for the given database field name
	 */
	private String getFieldGetterName(String col) {
		String fieldName = BeanHandler.getFieldHandlerName(col);
		String getterName = "get" + fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
		return getterName;
	}

	public ExcelHandler getExcelReport(Collection bean, Locale locale) throws NoDataException, BaseException {
		if (log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		  write column headers
		pw.addRow();
		String[] headerkeys = { "label.po.num", "label.company", "label.shipto", "label.date.created", "label.critical", "label.status", "label.current.status", "label.first.viewed", "label.confirmed", "label.promised.ship.date",
				"label.est.dock.date", "label.comments" };
		int[] widths = { 0, 12, 0, 0, 0, 14, 0, 0, 12, 0, 0, 0 };
		int[] types = { 0, 0, 0, ExcelHandler.TYPE_CALENDAR, 0, 0, 0, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_CALENDAR, ExcelHandler.TYPE_PARAGRAPH };
		int[] aligns = { 0, 0, 0, 0, ExcelHandler.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		Iterator iterator = bean.iterator();
		//	   now write data
		while (iterator.hasNext()) {
			WbuyStatusViewBean member = (WbuyStatusViewBean) iterator.next();
			pw.addRow();
			pw.addCell(member.getRadianPo());
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getShipToLocationId());
			pw.addCell(member.getDateCreated());
			pw.addCell(member.getCritical());
			pw.addCell(member.getDbuyStatus());
			pw.addCell(member.getDaysSinceLastStatus());
			pw.addCell(member.getDateAcknowledgement());
			pw.addCell(member.getDateConfirmed());
			pw.addCell(member.getVendorShipDate());
			pw.addCell(member.getPromisedDate());
			pw.addCell(member.getComments());
		}
		return pw;

	}
}
