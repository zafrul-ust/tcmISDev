package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.UserOpsEntityHubIgOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.InventoryTransferDetailViewBean;
import com.tcmis.internal.hub.beans.TransferRequestInputBean;
import com.tcmis.internal.hub.beans.TransferableInventoryViewBean;
import com.tcmis.internal.hub.factory.InventoryTransferDetailViewBeanFactory;
import com.tcmis.internal.hub.factory.TransferableInventoryViewBeanFactory;

/******************************************************************************
 * Process for allocation analysis
 * 
 * @version 1.0
 *****************************************************************************/
public class TransferRequestProcess extends BaseProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public TransferRequestProcess(String client) {
		super(client);
	}

	public TransferRequestProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getSearchData(TransferRequestInputBean bean, PersonnelBean personnelBean) throws BaseException {
		// log.debug("bean:" + bean);
		Collection c = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		TransferableInventoryViewBeanFactory factory = new TransferableInventoryViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (!StringHandler.isBlankString(bean.getHub())) criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
		if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
			if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0) invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
			if (!StringHandler.isBlankString(bean.getHub())) invQuery += " and hub = '" + bean.getHub() + "' ";
			if (!StringHandler.isBlankString(bean.getOpsEntityId())) invQuery += " and ops_entity_id = '" + bean.getOpsEntityId() + "' ";

			criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

		if (!StringHandler.isBlankString(bean.getDestInventoryGroup())) {
			criteria.addCriterion("destInventoryGroup", SearchCriterion.EQUALS, bean.getDestInventoryGroup());
		}

		if (bean.getIncludeNoInventory() != null) {
			criteria = criteria;
		}
		else {
			criteria.addCriterion("onHand", SearchCriterion.NOT_EQUAL, "0");
		}

		if (!StringHandler.isBlankString(bean.getSearchArgument())) {
			criteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());
		}

		return factory.select(criteria);
	}

	public Object[] transfer(Collection transferRequestInputBeanCollection, PersonnelBean personnelBean) throws BaseException {
		Collection c = new Vector();
		Collection cc = new Vector();
		Collection ccc = new Vector();
		String error = null;
		if (transferRequestInputBeanCollection != null) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Iterator iterator = transferRequestInputBeanCollection.iterator();
			while (iterator.hasNext()) {
				TransferableInventoryViewBean bean = (TransferableInventoryViewBean) iterator.next();
				if (bean.getOk() != null && bean.getTransferQuantity() != null && bean.getTransferQuantity().compareTo(new BigDecimal("0")) > 0) {

					try {
						Collection inArgs = new Vector(6);
						if (bean.getInventoryGroup() != null) {
							inArgs.add(bean.getInventoryGroup());
						}
						else {
							inArgs.add("");
						}
						if (bean.getDestInventoryGroup() != null) {
							inArgs.add(bean.getDestInventoryGroup());
						}
						else {
							inArgs.add("");
						}
						if (bean.getItemId() != null) {
							inArgs.add(bean.getItemId());
						}
						else {
							inArgs.add("");
						}
						if (bean.getTransferQuantity() != null) {
							inArgs.add(bean.getTransferQuantity());
						}
						else {
							inArgs.add("");
						}
						// always passes null
						inArgs.add("");

						if (bean.getTransferDate() != null) {
							inArgs.add(new Timestamp(bean.getTransferDate().getTime()));
						}
						else {
							inArgs.add(new Timestamp(new Date().getTime()));
						}

						if (bean.getComments() != null) {
							inArgs.add(bean.getComments());
						}
						else {
							inArgs.add("");
						}

						Collection outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.NUMERIC));
						outArgs.add(new Integer(java.sql.Types.VARCHAR));

						Collection optionalIn = new Vector();
						optionalIn.add("n");

						if (bean.getCompanyId() != null) {
							optionalIn.add(bean.getCompanyId());
						}
						else {
							optionalIn.add("");
						}
						if (bean.getCatalogCompanyId() != null) {
							optionalIn.add(bean.getCatalogCompanyId());
						}
						else {
							optionalIn.add("");
						}
						if (bean.getCatalogId() != null) {
							optionalIn.add(bean.getCatalogId());
						}
						else {
							optionalIn.add("");
						}
						if (bean.getCatPartNo() != null) {
							optionalIn.add(bean.getCatPartNo());
						}
						else {
							optionalIn.add("");
						}
						if (bean.getPartGroupNo() != null) {
							optionalIn.add(bean.getPartGroupNo());
						}
						else {
							optionalIn.add("");
						}
						// default values
						optionalIn.add("N");
						optionalIn.add("N");
						optionalIn.add("OOR");
						optionalIn.add("N");
						if (bean.getShippingReference() != null) {
							optionalIn.add(bean.getShippingReference());
						}
						else {
							optionalIn.add("");
						}
                        optionalIn.add(personnelBean.getPersonnelId());

                        if (log.isDebugEnabled()) {
							log.debug("p_inventory_transfer_request_m in:" + inArgs + optionalIn);
						}

						Collection resultCollection = procFactory.doProcedure("p_inventory_transfer_request_m", inArgs, outArgs, optionalIn);

						Iterator it = resultCollection.iterator();
						int count = 0;
						BigDecimal result = null;

						while (it.hasNext()) {
							result = (BigDecimal) it.next();
							error = (String) it.next();
							if (error != null && error.length() != 0) cc.add(error);
							if (result != null && (error == null || error.length() == 0)) {
								bean.setTransferRequestId(result);
								ccc.add(bean);
							}
							else if (error == null || error.length() == 0) {
								c.add(bean);
							}
						}
						if (log.isDebugEnabled()) {
							log.debug("Result:" + result);
							log.debug("Error:" + error);
						}
					}
					catch (Exception e) {
						log.error("Error:" + e.getMessage(), e);
						c.add(bean);
					}
				}
			}
		}
		Object[] objs = { c, cc, ccc };
		return objs;
	}

	public Collection getTransferTransactions(InventoryTransferDetailViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		InventoryTransferDetailViewBeanFactory factory = new InventoryTransferDetailViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("transferRequestId", SearchCriterion.EQUALS, bean.getTransferRequestId().toString());
		return factory.selectOrdered(criteria);
	}

	public String getSourceInventoryGroup(TransferRequestInputBean bean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();

		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select pkg_consigned_item.fx_get_source_ig('").append(bean.getDestInventoryGroup()).append("') from dual");

		return factory.selectSingleValue(query.toString());
	}

	public Collection getTransferToDropdown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "-1");

		UserOpsEntityHubIgOvBeanFactory factory = new UserOpsEntityHubIgOvBeanFactory(dbManager);

		return factory.selectObject(criteria);
	}

	public ExcelHandler getExcelReport(Collection<TransferableInventoryViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.item", "label.description", "label.packaging", "label.customerpartnumber", "label.frominventorygroup", "label.quantityavailable", "label.allocatable", "label.datesent", "label.comments" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { pw.TYPE_NUMBER, 0, 0, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 8, 15, 40, 12, 12, 12, 12, 0, 11 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);

		for (TransferableInventoryViewBean member : data) {

			pw.addRow();
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getDistCustomerPartList());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getOnHand());
			pw.addCell(member.getAllocatable());
			pw.addCell(dateFormat.format(calendar.getTime()));
			pw.addCell(member.getComments());

		}
		return pw;
	}
}
