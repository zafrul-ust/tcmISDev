package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvBuyOrderStatusBean;
import com.tcmis.common.admin.factory.CompanyBeanFactory;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.BoPoChangeReasonBean;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.beans.BuyPageViewBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceFlatBean;
import com.tcmis.internal.supply.factory.BuyPageViewBeanFactory;
import com.tcmis.internal.supply.factory.DbuyContractPriceOvBeanFactory;
import com.tcmis.internal.supply.factory.PoBeanFactory;
import com.tcmis.internal.supply.factory.PoLineSpecDraftBeanFactory;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for buy orders
 * 
 * @version 1.0
 *****************************************************************************/
public class BuyOrdersProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public BuyOrdersProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getReason(BuyOrdersInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new BoPoChangeReasonBean());
		String query = "select * from VV_BO_PO_CHANGE_REASON order by display_order,reason_description";
		return factory.selectQuery(query);
	}

	public Collection getDbuyFlatView(DbuyContractPriceFlatBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DbuyContractPriceFlatBean());
		String query = "SELECT * FROM tcm_ops.preferred_supplier_price_view WHERE";
		query += " ops_entity_id = " + GenericProcess.getSqlString(bean.getOpsEntityId());
		query += " AND item_id = " + GenericProcess.getSqlString(bean.getItemId());
		query += " AND supply_path = " + GenericProcess.getSqlString(bean.getSupplyPath());
		query += " AND NVL(buyer_company_id, 'Radian') = 'Radian'";
		query += " AND status IN ('DBUY','PRICING')";
		query += " AND (inventory_group = " + GenericProcess.getSqlString(bean.getInventoryGroup());
		query += " OR inventory_group = 'All')";
		query += " AND ((ship_to_company_id = " + GenericProcess.getSqlString(bean.getShipToCompanyId());
		query += " AND ship_to_location_id = " + GenericProcess.getSqlString(bean.getShipToLocationId());
		query += " ) OR (ship_to_company_id = 'All' AND ship_to_location_id = " + GenericProcess.getSqlString(bean.getShipToLocationId());
		query += " ) OR (ship_to_company_id = 'All' AND ship_to_location_id = 'All')) ";
		query += " order by break_unit_price asc";

		return factory.selectQuery(query);
	}

	public Collection getPoSupplier(BuyOrdersInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new BuyOrdersInputBean());
		String query = "select supplier last_supplier,supplier_name last_supplier_name from table (pkg_supplier.FX_IG_ITEM_SUPPLIER (" + "'" + bean.getInventoryGroup() + "',"
				+ bean.getItemId() + ",'" + bean.getShipToCompanyId() + "','" + bean.getShipToLocationId() + "'))";
		return factory.selectQuery(query);
	}

	public Collection getVvBuyOrderStatus(Collection vvBuyOrderStatus) throws BaseException {
		Collection result = new Vector(vvBuyOrderStatus.size());
		Iterator iter = vvBuyOrderStatus.iterator();
		while (iter.hasNext()) {
			VvBuyOrderStatusBean bean = (VvBuyOrderStatusBean) iter.next();
			if ("Y".equalsIgnoreCase(bean.getBuypageAssignable())) {
				result.add(bean);
			}
		}
		return result;
	}

	public Collection getSearchData(PersonnelBean personnelBean, BuyOrdersInputBean bean, Collection hubIgBuyerCollection) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		BuyPageViewBeanFactory factory = new BuyPageViewBeanFactory(dbManager);
		bean.setLocale(getLocale());
		return factory.select(personnelBean, bean, hubIgBuyerCollection);
	}

	public Collection getSearchDataOpenBuy(PersonnelBean personnelBean, BuyOrdersInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		BuyPageViewBeanFactory factory = new BuyPageViewBeanFactory(dbManager);
		return factory.selectOpenBuy(personnelBean, bean);
	}

	public Collection getBuyerDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		return factory.selectBuyOrdersBuyer();
	}

	public Collection getActiveBuyerDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		return factory.selectActiveBuyOrdersBuyer();
	}

	public Collection getCompanyDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CompanyBeanFactory factory = new CompanyBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("customer", SearchCriterion.EQUALS, "Y");
		criteria.addCriterion("companyId", SearchCriterion.NOT_EQUAL, "ALL");
		return factory.selectCustomer(criteria);
	}

	protected String getBuyerCompanyId(BuyOrdersInputBean row) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new BoPoChangeReasonBean());
		String query = "SELECT tcm_ops.fx_dbuy_buyer_company_id(" + row.getItemId() 
				+ ", " + SqlHandler.delimitString(row.getInventoryGroup()) 
				+ ", " + SqlHandler.delimitString(row.getShipToCompanyId()) 
				+ ", " + SqlHandler.delimitString(row.getShipToLocationId()) 
				+ ", " + SqlHandler.delimitString(row.getOpsEntityId()) 
				+ ") FROM   DUAL";
		String result = factory.selectSingleValue(query);
		if (StringUtils.isBlank(result)) {
			throw new BaseException("There is no dbuy_contract data for itemId " + row.getItemId() + " & inventoryGroup '" + row.getInventoryGroup() + "' to convert to Customer Purchase. Please contact TCMIS Support.");
		}
		return result;
	}

	public void update(Collection buyOrdersInputBeanCollection, PersonnelBean personnelBean) throws BaseException {
		if (buyOrdersInputBeanCollection != null) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Iterator iterator = buyOrdersInputBeanCollection.iterator();
			while (iterator.hasNext()) {
				BuyOrdersInputBean bean = (BuyOrdersInputBean) iterator.next();
				if (bean.isSupplyPathCustomer() || isBuyerIdDifferent(bean.getOldBuyerId(), bean.getBuyerId())
						|| !StringHandler.emptyIfNull(bean.getOldComments()).equalsIgnoreCase(StringHandler.emptyIfNull(bean.getComments()))
						|| !StringHandler.emptyIfNull(bean.getOldStatus()).equalsIgnoreCase(StringHandler.emptyIfNull(bean.getStatus()))
						|| !StringHandler.emptyIfNull(bean.getOldPoSupplierName()).equalsIgnoreCase(StringHandler.emptyIfNull(bean.getPoSupplierName()))) {
					Collection inArgs = new Vector(9);
					if (bean.getPrNumber() == null) {
						inArgs.add("");
					}
					else {
						inArgs.add(bean.getPrNumber());
					}
					if (bean.getComments() == null) {
						inArgs.add("");
					}
					else {
						inArgs.add(bean.getComments());
					}
					if (bean.isSupplyPathCustomer()) {
						inArgs.add("New"); // Change status to New for all conversion to customer buy
						inArgs.add(getBuyerCompanyId(bean)); // Inventory group's customer company_id
						inArgs.add(""); // No longer a Wesco buy, remove the wesco buyer ID
						inArgs.add(bean.getSupplyPath()); 
					}
					else {
						if (bean.getStatus() == null) {
							inArgs.add("");
						}
						else {
							inArgs.add(bean.getStatus());
						}						
						if (personnelBean.getCompanyId() == null) {
							inArgs.add("");
						}
						else {
							inArgs.add(personnelBean.getCompanyId());
						}
						if (bean.getBuyerId() == null) {
							inArgs.add("");
						}
						else {
							inArgs.add(bean.getBuyerId());
						}
						// changing Dbuy or WBuy to regular buy
						if ("Dbuy".equalsIgnoreCase(bean.getSupplyPath()) || "Wbuy".equalsIgnoreCase(bean.getSupplyPath())) {
							if (bean.getStatus() != null) {
								if (bean.getStatus().indexOf("DBuy") >= 0 || bean.getStatus().indexOf("WBuy") >= 0) {
									inArgs.add("");
								}
								else {
									inArgs.add("Purchaser");
								}
							}
							else {
								inArgs.add("");
							}
						}
						else {
							inArgs.add("");
						}
					}
					if (bean.getPoSupplierId() == null) {
						inArgs.add(null);
					}
					else {
						inArgs.add(bean.getPoSupplierId());
					}
					if (bean.getReasonId() == null || (new BigDecimal((-1))).equals(bean.getReasonId())) {
						inArgs.add("");
					}
					else {
						inArgs.add(bean.getReasonId());
					}
					inArgs.add(personnelBean.getPersonnelId());
					log.debug(inArgs + "Buyer before " + bean.getOldBuyerId() + "Personnel " + personnelBean.getFirstName() + "  ID  " + personnelBean.getPersonnelId());
					procFactory.doProcedure("PKG_BUY_ORDER.P_UPDATE_BUY_ORDER", inArgs);

					if (((StringHandler.emptyIfNull(bean.getOldStatus())).equalsIgnoreCase("WBuyReview")
							&& (StringHandler.emptyIfNull(bean.getStatus()).equalsIgnoreCase("InProgressWBuy")))
							|| ((StringHandler.emptyIfNull(bean.getOldStatus())).equalsIgnoreCase("XBuyReview")
									&& (StringHandler.emptyIfNull(bean.getStatus()).equalsIgnoreCase("InProgressXBuy")))) {
						Collection inArgs2 = new Vector(3);
						if (bean.getRadianPo() != null) {
							inArgs2.add(bean.getRadianPo());
							inArgs2.add(bean.getMrNumber());
							inArgs2.add(bean.getMrLineItem());
							log.info("p_notify_airgas_at_po_create  " + inArgs2);
							procFactory.doProcedure("p_notify_airgas_at_po_create", inArgs2);
						}
					}
				}
			}
		}
	}

	private boolean isBuyerIdDifferent(BigDecimal b1, BigDecimal b2) {
		boolean flag = true;
		if ((b1 == null && b2 == null) || ((b1 != null && b2 != null) && b1.compareTo(b2) == 0)) {
			flag = false;
		}
		return flag;
	}

	public String[] createPo(Collection buyOrdersInputBeanCollection, PersonnelBean personnelBean, Locale locale) throws BaseException {
		String[] result = new String[2];
		String errorMessage = "";
		String poNumber = null;
		// first update data
		update(buyOrdersInputBeanCollection, personnelBean);
		if (buyOrdersInputBeanCollection != null) {
			StringBuilder sb = new StringBuilder("");
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Iterator iterator = buyOrdersInputBeanCollection.iterator();
			String companyId = null;
			// BigDecimal itemId = null;
			String inventoryGroup = null;
			String customerPo = null;
			String selectedSupplierForPo = "";
			// i'm keep this if i need to call manuallyCreatePo
			BuyOrdersInputBean inputBean = new BuyOrdersInputBean();
			boolean firstTime = true;
			while (iterator.hasNext()) {
				BuyOrdersInputBean bean = (BuyOrdersInputBean) iterator.next();
				if (bean.getRowNumber() != null) {
					if (companyId == null) {
						companyId = bean.getCompanyId();
					}
					/*
					 * if(itemId == null) { itemId = bean.getItemId(); }
					 */
					if (inventoryGroup == null) {
						inventoryGroup = bean.getInventoryGroup();
					}
					if (customerPo == null) {
						customerPo = bean.getCustomerPoNumber();
					}
					if (StringHandler.isBlankString(selectedSupplierForPo)) {
						if (!"No Supplier".equalsIgnoreCase(bean.getSelectedSupplierForPo()) && !StringHandler.isBlankString(bean.getSelectedSupplierForPo())) {
							selectedSupplierForPo = bean.getSelectedSupplierForPo();
						}
					}

					if (companyId.equalsIgnoreCase("SWA") && !customerPo.equalsIgnoreCase(bean.getCustomerPoNumber())) {
						ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
						errorMessage += library.getString("buyorders.label.swasamepo");
					}
					if (!inventoryGroup.equalsIgnoreCase(bean.getInventoryGroup())) {
						ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
						errorMessage += library.getString("buyorders.label.createpoalert");
					}
					sb.append(bean.getPrNumber()).append(",");

					// copy bean for manuallyCreatePo
					if (firstTime) {
						BeanHandler.copyAttributes(bean, inputBean);
						firstTime = false;
					}
					else {
						// sum up the orderQty
						inputBean.setOrderQuantity(inputBean.getOrderQuantity().add(bean.getOrderQuantity()));
						// set the need date to the
						// earliest of the dates
						try {
							if (inputBean.getNeedDate().getTime() > bean.getNeedDate().getTime()) {
								inputBean.setNeedDate(bean.getNeedDate());
							}
						}
						catch (Exception e) {

						}
					}
				}
			}

			if (!StringHandler.isBlankString(sb.toString())) {
				String prNumbers = StringHandler.stripLast(sb.toString(), ",");

				Collection inArgs = new Vector(4);
				inArgs.add(prNumbers == null ? "" : prNumbers);
				inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
				inArgs.add(selectedSupplierForPo);
				inArgs.add(inventoryGroup);

				Collection outArgs = new Vector(2);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				try {
					if (log.isDebugEnabled()) {
						log.debug("CREATE_PO  " + inArgs);
					}
					Collection c = procFactory.doProcedure("CREATE_PO", inArgs, outArgs);
					Iterator it = c.iterator();
					for (int i = 0; it.hasNext(); i++) {
						if (i == 0) {
							poNumber = (String) it.next();
						}
						else if (i == 1) {
							errorMessage = (String) it.next();
							// it.next();
						}
						else { // in case something
								// changes I don't want
								// to end up in an
								// endless loop
							it.next();
						}
					}
					// manually create po if procedure
					// return nothing
					if (StringHandler.isBlankString(poNumber)) {
						ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
						errorMessage += library.getString("generic.error");
						// the procedure will handle
						// this now
						// poNumber =
						// manuallyCreatePo(inputBean,personnelBean,prNumbers);
					}
				}
				catch (Exception e) {
					log.error("Error calling CREATE_PO with:" + inArgs.toString());
					MailProcess.sendEmail("deverror@haastcm.com", "", MailProcess.DEFAULT_FROM_EMAIL, "Error calling CREATE_PO",
							"Parameters:" + inArgs.toString() + "\n" + e.getMessage());
					throw new BaseException(e);
				}
			}
		}
		//
		log.info("poNumber  " + poNumber + " errorMessage " + errorMessage + "");
		result[0] = poNumber;
		result[1] = errorMessage;
		return result;
	}

	public String manuallyCreatePo(BuyOrdersInputBean inputBean, PersonnelBean personnelBean, String prNumbers) throws BaseException {
		BigDecimal poNumber = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PoBeanFactory factory = new PoBeanFactory(dbManager);
		// get po next sequence
		poNumber = factory.getPoNextSeq();
		if (poNumber != null) {
			// create po
			factory.createPo("insert into po (RADIAN_PO,BUYER,PAYMENT_TERMS,INVENTORY_GROUP) values (" + poNumber.toString() + "," + personnelBean.getPersonnelId() + ",'Net 45','"
					+ inputBean.getInventoryGroup() + "')");
			// update buy_order
			factory.createPo("update buy_order set BUYER_COMPANY_ID = '" + personnelBean.getCompanyId() + "',RADIAN_PO = " + poNumber.toString() + ",BUYER_ID="
					+ personnelBean.getPersonnelId() + ",DATE_CHANGED=sysdate where item_id =" + inputBean.getItemId().toString() + " and pr_number in (" + prNumbers
					+ ") and radian_po is null and frozen = 'N'");
			// create po_header
			createPoHeader(poNumber, inputBean, personnelBean);
			// save po_line
			savePoLine(poNumber, inputBean, personnelBean);
			// save initial SPEC
			PoLineSpecDraftBeanFactory specDraftFactory = new PoLineSpecDraftBeanFactory(dbManager);
			specDraftFactory.saveInitialSpec(inputBean, poNumber);
		}
		return poNumber.toString();
	}

	public void createPoHeader(BigDecimal poNumber, BuyOrdersInputBean inputBean, PersonnelBean personnelBean) {
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = new Vector(19);
			Collection optInArgs = new Vector(2);
			Collection outArgs = new Vector(1);
			outArgs.add(java.sql.Types.VARCHAR);

			inArgs.add(poNumber);
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add(personnelBean.getPersonnelId());
			inArgs.add(inputBean.getBranchPlant());
			inArgs.add(inputBean.getShipToCompanyId());
			inArgs.add(inputBean.getShipToLocationId());
			inArgs.add("");
			inArgs.add("Net 45");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(inputBean.getCustomerPoNumber())) {
				inArgs.add(inputBean.getCustomerPoNumber());
			}
			else {
				inArgs.add("");
			}
			if (!StringHandler.isBlankString(inputBean.getCritical())) {
				inArgs.add(inputBean.getCritical());
			}
			else {
				inArgs.add("");
			}
			inArgs.add(inputBean.getInventoryGroup());
			inArgs.add("n");

			optInArgs.add("");
			if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
				optInArgs.add(inputBean.getFacilityId());
			}
			else {
				optInArgs.add("");
			}

			procFactory.doProcedure("pkg_po.PO_HEADER", inArgs, outArgs, optInArgs);
		}
		catch (Exception e) {

		}
	}

	public void savePoLine(BigDecimal poNumber, BuyOrdersInputBean inputBean, PersonnelBean personnelBean) {
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Collection inArgs = new Vector(29);
			Collection outArgs = new Vector(1);
			outArgs.add(java.sql.Types.VARCHAR);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			inArgs.add(poNumber);
			inArgs.add(new BigDecimal(1000));
			inArgs.add(new BigDecimal(0));
			inArgs.add(inputBean.getItemId());
			inArgs.add("");
			inArgs.add(inputBean.getOrderQuantity());
			inArgs.add("");
			inArgs.add(new Timestamp(inputBean.getNeedDate().getTime()));
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("N");
			inArgs.add("N");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
			if (!StringHandler.isBlankString(inputBean.getHomeCurrencyId())) {
				inArgs.add(inputBean.getHomeCurrencyId());
			}
			else {
				inArgs.add("");
			}
			inArgs.add("");

			procFactory.doProcedure("pkg_po.SAVE_PO_LINE", inArgs, outArgs);
		}
		catch (Exception e) {

		}
	}

	public ExcelHandler getExcelReport(PersonnelBean personnelBean, BuyOrdersInputBean bean, Collection hubIgBuyerCollection, Locale locale)
			throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<BuyPageViewBean> data = getSearchData(personnelBean, bean, hubIgBuyerCollection);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		pw.addCellKeyBold("label.hub");
		pw.addCell(bean.getHubName());
		pw.addCell("");
		pw.addCellKeyBold("label.company");
		pw.addCell(bean.getCompanyName());
		pw.addCell("");
		pw.addCellKeyBold("label.status");
		pw.addCell(bean.getStatusDesc());

		pw.addRow();
		pw.addCellKeyBold("label.inventory");
		pw.addCell(bean.getInventoryGroupName());
		pw.addCell("");
		pw.addCellKeyBold("label.buyer");
		pw.addCell(bean.getBuyerName());
		pw.addCell("");
		pw.addCellKeyBold("label.supplypath");
		pw.addCell(bean.getSupplyPathDesc());

		pw.addRow();
		pw.addCellKeyBold("label.facility");
		pw.addCell(bean.getFacilityName());
		pw.addCell("");
		pw.addCellKeyBold("label.search");
		pw.addCell(bean.getSearchWhatDesc() + " " + bean.getSearchTypeDesc() + " " + bean.getSearchText());
		pw.addCell("");
		pw.addCellKeyBold("label.sortby");
		pw.addCell(bean.getSortByDesc());

		if ("YES".equals(bean.getBoForUnconfirmedPo())) {
			pw.addRow();
			pw.addThRegion("buyorders.label.onlyunconfirmed", 1, 4);
		}
		if ("YES".equals(bean.getBoWithNoPo())) {
			pw.addRow();
			pw.addThRegion("buyorders.label.notassigned", 1, 4);
		}
		if ("YES".equals(bean.getBoForConfirmedPo())) {
			pw.addRow();
			pw.addThRegion("buyorders.label.onlyconfirmed", 1, 4);
		}
		if ("YES".equals(bean.getConfirmedButNoAssociation())) {
			pw.addRow();
			pw.addThRegion("buyorders.label.associationnotconfirmed", 1, 4);
		}

		// blank row
		pw.addRow();

		// result table
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = {"label.prnumber", "label.prcreated", "label.shipto", "label.inventorygroup", "label.partnumber", "label.item", "label.itemdescription", "label.type",
				"label.buyquantity", "label.uom", "label.needed", "label.custpromisedate", "label.buyer", "label.status", "label.buyordernotes", "label.po", "label.purchasingnote",
				"label.manufacturer", "label.lastsupplier", "label.company", "label.facility", "label.mrline", "label.mrquantity", "label.requestor", "label.csr", "label.clientpo",
				"label.externallinenote", "label.internallinenote", "label.shiptonote", "label.mm", "label.reorderpoint", "label.inventory", "label.openpoquantity"};
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = {0, pw.TYPE_DATE, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_NUMBER, pw.TYPE_PARAGRAPH, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				pw.TYPE_NUMBER, 0, 0, 0, 0, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER};
		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = {10, 0, 20, 20, 20, 10, 0, 0, 0, 0, 0, 0, 18, 0, 25, 0, 25, 36, 26, 18, 20, 8, 0, 30, 15, 15, 20, 20, 20, 0, 0, 0, 0};
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = {pw.ALIGN_CENTER, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (BuyPageViewBean member : data) {
			i++;
			pw.addRow();
			pw.addCell(member.getPrNumber());
			pw.addCell(member.getDateIssued());
			pw.addCell(member.getShipToLocationId());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getPartId());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getItemType());
			pw.addCell(member.getOrderQuantity());
			pw.addCell(member.getSizeUnit());
			pw.addCell(member.getNeedDate());
			pw.addCell(member.getPromiseDate());
			pw.addCell(member.getBuyerName());
			pw.addCell(member.getStatus());
			pw.addCell(member.getComments());
			pw.addCell(StringHandler.emptyIfZero(member.getRadianPo()));
			pw.addCell(member.getLinePurchasingNote());
			pw.addCell(member.getMfgId());
			pw.addCell(member.getLastSupplierName());
			pw.addCell(member.getCompanyId());
			pw.addCell(member.getFacility());
			if (member.getMrNumber() == null) {
				pw.addCell("");
				pw.addCell("");
				pw.addCell("");
				pw.addCell("");
				pw.addCell("");
			}
			else {
				pw.addCell(member.getMrNumber() + "-" + member.getMrLineItem());
				pw.addCell(member.getMrQuantity());
				pw.addCell(member.getRequestorFirstName() + " " + member.getRequestorLastName() + " " + member.getPhone() + " " + member.getEmail());
				pw.addCell(member.getCsrName());
				pw.addCell(member.getRaytheonPo());
			}
			pw.addCell(member.getNotes());
			pw.addCell(member.getLineInternalNote());
			pw.addCell(member.getShiptoNote());
			pw.addCell(member.getStockingLevel());
			pw.addCell(member.getReorderPoint());
			pw.addCell(member.getAvailableQuantity());
			pw.addCell(member.getOpenPoQuantity());
		}
		return pw;
	} // end of method

} // end of class