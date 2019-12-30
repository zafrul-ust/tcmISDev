package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogPartInventoryBean;
import com.tcmis.client.catalog.beans.FlowDownBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvContactTypeBean;
import com.tcmis.common.admin.beans.VvItemTypeBean;
import com.tcmis.common.admin.beans.VvPaymentTermsBean;
import com.tcmis.common.admin.factory.CompanyBeanFactory;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.factory.VvBuyOrderStatusBeanFactory;
import com.tcmis.common.admin.factory.VvCurrencyBeanFactory;
import com.tcmis.common.admin.factory.VvFreightOnBoardBeanFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.hub.factory.HubBeanFactory;
import com.tcmis.internal.supply.beans.AssociatePrViewBean;
import com.tcmis.internal.supply.beans.BuyPageInputBean;
import com.tcmis.internal.supply.beans.PoAddChargeAllocationAllBean;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.internal.supply.beans.PoLineItemListBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.beans.VvCategoryBean;
import com.tcmis.internal.supply.factory.AssociatePrViewBeanFactory;
import com.tcmis.internal.supply.factory.PoAddChargeAllocationAllBeanFactory;
import com.tcmis.internal.supply.factory.PoHeaderViewBeanFactory;
import com.tcmis.internal.supply.factory.PoLineDetailViewBeanFactory;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for receiving
 * 
 * @version 1.0
 *****************************************************************************/
public class PurchaseOrderProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	public static final String LINE_RESULT = "LINE_RESULT";
	public static final String INTER_COMPANY_MR = "INTER_COMPANY_MR";
	public static final String ERROR = "Error";
	private static final String PO_LINE_UPDATED = "PoLineUpdated";
	public static final String HEADER_RESULT = "HEADER_RESULT";
	public static final String PO_APPROVAL_REQUIRED = "PO_APPROVAL_REQUIRED";
	ResourceLibrary library; 
	
	public PurchaseOrderProcess(String client) {
		super(client);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
	}
	
	public PurchaseOrderProcess(String client, String locale) {
		super(client, locale);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
	}
	
	public boolean userHasAccess(PersonnelBean user) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		String query = "select count(*) from user_page where company_id = 'Radian' and page_id = 'purchaseOrderNew' and personnel_id = " + user.getPersonnelId();
		String count = factory.selectSingleValue(query);
		return Integer.parseInt(count) > 0;
	}

	public Collection getPoHeader(PurchaseOrderBean bean) {
		try {
			DbManager dbManager = new DbManager(getClient());
			PoHeaderViewBeanFactory factory = new PoHeaderViewBeanFactory(
					dbManager);
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, bean
					.getRadianPo().toPlainString());
			return factory.select(criteria);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Collection getPoLineDetails(PurchaseOrderBean bean) {
		try {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, bean
					.getRadianPo().toPlainString());
			//order by PO_LINE (poLine) ascending
			SortCriteria sortCriteria = new SortCriteria("poLine");    
			PoLineDetailViewBeanFactory poLineDetailViewBeanFactory = new PoLineDetailViewBeanFactory(
					new DbManager(getClient()));
			return poLineDetailViewBeanFactory.select(criteria, sortCriteria);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Collection buildsendLookahead(PurchaseOrderBean bean)
			throws BaseException {
		return buildsendLookahead(bean.getRadianPo(), bean.getItemId());
	}

	public Collection buildsendLookahead(BigDecimal radianPo, BigDecimal itemId)
			throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(
				getClient(), getLocale()), new CatalogPartInventoryBean());
		try {
			return factory
					.selectQuery("select distinct x.INVENTORY_GROUP,x.PART_GROUP_NO,x.CAT_PART_NO,x.LOOK_AHEAD_DAYS,x.COMPANY_ID,x.CATALOG_ID from customer.catalog_part_inventory x, buy_order y where y.PART_ID= x.CAT_PART_NO and x.COMPANY_ID = y.COMPANY_ID and y.RADIAN_PO="
							+ radianPo
							+ " and y.ITEM_ID="
							+ itemId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection buildItemNotes(PurchaseOrderBean bean, String companyId)
			throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(
				getClient(), getLocale()), new PoLineDetailViewBean());
		try {
			return factory
					.selectQuery("select ibc.ITEM_ID ,ibc.COMMENTS,ibc.TRANSACTION_DATE, to_char(ibc.TRANSACTION_DATE,'mm/dd/yyyy') TRANS_DATE, p.first_name ||' '|| p.last_name Full_name from item_buyer_comments ibc,personnel p  where item_id = "
							+ bean.getItemId()
							+ " and ibc.buyer_id = p.personnel_id and company_id = '"
							+ companyId + "' order by TRANSACTION_DATE desc");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection buildPartNotes(PurchaseOrderBean bean)
			throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(
				getClient(), getLocale()), new PoLineDetailViewBean());
		try {
			return factory
					.selectQuery("select COMPANY_ID, CATALOG_ID, CAT_PART_NO, CAT_PART_COMMENT  from ig_cat_part_comment_view where item_id="
							+ bean.getItemId()
							+ " and inventory_group='"
							+ bean.getInventoryGroup()
							+ "' and CAT_PART_COMMENT is not null");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection getSearchResult(BuyPageInputBean bean,
			boolean hasUpdatePermission, Collection hubInventoryGroupOvBeanColl)
			throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		AssociatePrViewBeanFactory factory = new AssociatePrViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getBranchPlant() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getBranchPlant());
		}

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(
				hubInventoryGroupOvBeanColl, 
				bean.getBranchPlant(),
				bean.getInventoryGroup());

		if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			if (iscollection) {
				//
			} else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
		}

		criteria.addCriterion("status", SearchCriterion.EQUALS, "Confirmed");
		criteria.addCriterion("poInJde", SearchCriterion.EQUALS, "y");
		criteria.addCriterion("radianPo", SearchCriterion.IS_NOT, null);

		if (bean.getBuyerId() != null && (bean.getBuyerId().floatValue() == -1)) {
			criteria.addCriterion("buyerId", SearchCriterion.IS, null);
		} else if (bean.getBuyerId() != null) {
			criteria.addCriterion("buyerId", SearchCriterion.EQUALS,
					"" + bean.getBuyerId() + "");
		}

		if (bean.getSupplyPath() != null
				&& !bean.getSupplyPath().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("supplyPath", SearchCriterion.EQUALS,
					bean.getSupplyPath());
		}

		// add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearchText() != null
				&& bean.getSearchText().trim().length() > 0) {
			// check search criterion
			String criterion = null;
			if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IN")) {
				criterion = SearchCriterion.IN;
			}

			// check what to search by
			if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("MFG_ID")) {
				criteria.addCriterion("mfgId", criterion, bean.getSearchText(),
						SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("RADIAN_PO")) {
				criteria.addCriterion("radianPo", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PART_ID")) {
				criteria.addCriterion("partId", criterion,
						bean.getSearchText(), SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
				criteria.addCriterion("itemId", criterion, bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("RAYTHEON_PO")) {
				criteria.addCriterion("raytheonPo", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("MR_NUMBER")) {
				criteria.addCriterion("mrNumber", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("ITEM_TYPE")) {
				criteria.addCriterion("itemType", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PR_NUMBER")) {
				criteria.addCriterion("prNumber", criterion,
						bean.getSearchText());
			}
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion(bean.getSort());

		Collection associatePrViewBeanCollection = factory.select(criteria,
				sortCriteria);
		Collection radianPoCollection = new Vector();
		Iterator mainIterator = associatePrViewBeanCollection.iterator();
		while (mainIterator.hasNext()) {
			AssociatePrViewBean associatePrViewBean = (AssociatePrViewBean) mainIterator.next();

			if (!radianPoCollection.contains(associatePrViewBean.getRadianPo())) {
				radianPoCollection.add(associatePrViewBean.getRadianPo());
			}
		}

		if (radianPoCollection.size() > 0) {
			return getPurchaseOrderDetails(radianPoCollection);
		} else {
			return null;
		}
	}

	public Collection getPurchaseOrderDetails(Collection radianPoCollection)
			throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PoHeaderViewBeanFactory factory = new PoHeaderViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("radianPo", SearchCriterion.EQUALS,
				radianPoCollection, SearchCriterion.IGNORE_CASE);

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("radianPo");

		return factory.select(criteria);
	}

	public Collection getCustomerSearch(BuyPageInputBean bean,
			boolean hasUpdatePermission, Collection hubInventoryGroupOvBeanColl)
			throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PoHeaderViewBeanFactory factory = new PoHeaderViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		String sortBy = null;

		if (bean.getBranchPlant() != null
				&& !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
					bean.getBranchPlant());
		}

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(
				this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(
				hubInventoryGroupOvBeanColl, bean.getBranchPlant(),
				bean.getInventoryGroup());

		if (bean.getInventoryGroup() != null
				&& !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			if (iscollection) {

			} else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
						bean.getInventoryGroup());
			}
		}

		if (bean.getBuyerId() != null && (bean.getBuyerId().floatValue() == -1)) {
			criteria.addCriterion("buyer", SearchCriterion.IS, null);
		} else if (bean.getBuyerId() != null) {
			criteria.addCriterion("buyer", SearchCriterion.EQUALS,
					"" + bean.getBuyerId() + "");
		}

		criteria.addCriterion("buyerCompanyId", SearchCriterion.EQUALS,
				getClient().toUpperCase());

		if (bean.getSupplyPath() != null
				&& !bean.getSupplyPath().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("supplyPath", SearchCriterion.EQUALS,
					bean.getSupplyPath());
		}

		// add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearchText() != null
				&& bean.getSearchText().trim().length() > 0) {
			// check search criterion
			String criterion = null;
			if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IN")) {
				criterion = SearchCriterion.IN;
			}

			// check what to search by
			if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("MFG_ID")) {
				criteria.addCriterion("mfgId", criterion, bean.getSearchText(),
						SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("RADIAN_PO")) {
				criteria.addCriterion("radianPo", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PART_ID")) {
				criteria.addCriterion("partId", criterion,
						bean.getSearchText(), SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
				criteria.addCriterion("itemId", criterion, bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("RAYTHEON_PO")) {
				criteria.addCriterion("customerPo", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("MR_NUMBER")) {
				criteria.addCriterion("mrNumber", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("ITEM_TYPE")) {
				criteria.addCriterion("itemType", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PR_NUMBER")) {
				criteria.addCriterion("prNumber", criterion,
						bean.getSearchText());
			}
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("radianPo");

		// return factory.customerSelect(criteria,sortCriteria);
		return null;
	}

	
	public HashMap updateSelected(Collection poLineDetailViewBeanInputCollection, BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		String errorMessage = "";
		Iterator mainIterator = poLineDetailViewBeanInputCollection.iterator();
		while (mainIterator.hasNext()) {
			PoLineDetailViewBean poLineDetailViewBean = (PoLineDetailViewBean) mainIterator
					.next();;

			String lineChangeStatus = poLineDetailViewBean
					.getLineChangeStatus();
			if (lineChangeStatus != null && lineChangeStatus.length() > 0) {
				log.info("UPDATING PO " + poLineDetailViewBean.getRadianPo()
						+ " Line " + poLineDetailViewBean.getPoLine()
						+ " Ammendment  " + poLineDetailViewBean.getAmendment()
						+ "");
				try {
					poLineDetailViewBean.setUnitPrice(new BigDecimal("0"));
					poLineDetailViewBean.setNeedDate(com.tcmis.common.util.DateHandler
									.getDateFromString("MM/dd/yyyy", "01/01/3000"));
					poLineDetailViewBean.setPromisedDate(com.tcmis.common.util.DateHandler
									.getDateFromString("MM/dd/yyyy", "01/01/3000"));
					poLineDetailViewBean.setAmmendmentcomments("Created from customer BO page");
					poLineDetailViewBean.setVendorShipDate(com.tcmis.common.util.DateHandler
									.getDateFromString("MM/dd/yyyy", "01/01/3000"));
					poLineDetailViewBean.setCurrencyId("USD");

					PoLineDetailViewBeanFactory poLineDetailViewBeanFactory = new PoLineDetailViewBeanFactory(
							dbManager);

					poLineDetailViewBeanFactory.updatePoLine(poLineDetailViewBean, personnelId, "");
					poLineDetailViewBeanFactory.updatePoLine(poLineDetailViewBean, personnelId, "confirm");
				} catch (Exception ex) {
					ex.printStackTrace();
					errorMessage += ex.getMessage();
				}
			}
		}

		HashMap result = new HashMap();
		result.put("ERRORMESSAGE", errorMessage);
		return result;
	}

	public Collection getCategory() {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvCategoryBean());
		try {
			return factory.selectQuery("select CATEGORY_ID,CATEGORY_DESC  from vv_category order by CATEGORY_DESC");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
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

	public Collection getOrderStatus() throws BaseException {
		return getOrderStatus("");
	}

	public Collection getOrderStatus(String lockStatus) throws BaseException  {		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		VvBuyOrderStatusBeanFactory factory = new VvBuyOrderStatusBeanFactory(dbManager);		
		SearchCriteria criteria = new SearchCriteria();
		if ("setonly".equalsIgnoreCase(lockStatus)) {
			criteria.addCriterion("buypageAssignable", SearchCriterion.EQUALS, "Y");			
		} else if (!StringHandler.isBlankString(lockStatus)) {
			criteria.addCriterion("lockStatus", SearchCriterion.EQUALS, lockStatus);		
		}
		SortCriteria sort = new SortCriteria("displaySort");		
		return factory.select(criteria,sort);	
   }

	public Collection nonAssignableStatuses() throws BaseException  {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		VvBuyOrderStatusBeanFactory factory = new VvBuyOrderStatusBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("buypageAssignable", SearchCriterion.EQUALS, "N");
		SortCriteria sort = new SortCriteria("displaySort");		
		return factory.select(criteria,sort);
	}

	public Collection getFob() throws BaseException  {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		VvFreightOnBoardBeanFactory factory = new VvFreightOnBoardBeanFactory(dbManager);
		SearchCriteria search = new SearchCriteria("status", SearchCriterion.EQUALS, "ACTIVE");
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("description");
		return factory.select(search,sort);
	}

	public Collection getAddChargeType() {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvItemTypeBean());
		try {
			return factory.selectQuery("select ITEM_TYPE,TYPE_DESC from vv_item_type where MA_ADD_CHARGE = 'y'");			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Collection getSecondarySupplierTypes() {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvItemTypeBean());
		try {
			return factory.selectQuery("select ITEM_TYPE, TYPE_DESC from vv_item_type where SECONDARY_SUPPLIER_ON_PO = 'Y'");			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;	
	}
	
	public Collection getPaymentTerms(boolean showInactive) {
		String query = "";
		if (showInactive) {
			query = "select * from vv_payment_terms where nvl(PREPAY_TERMS,'N') <> 'Y' order by PAYMENT_TERMS desc";
		} else {
			query = "select * from vv_payment_terms where STATUS = 'ACTIVE' and nvl(PREPAY_TERMS,'N') <> 'Y' order by PAYMENT_TERMS desc";
		}
		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvPaymentTermsBean());
		try {
			return factory.selectQuery(query);			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Collection getContactType() {		
		String query = "select * from vv_supplier_contact_type order by CONTACT_TYPE asc";
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvContactTypeBean());
		try {
			return factory.selectQuery(query);			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
    }

	public Collection getCurrencyType() throws BaseException  {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvCurrencyBeanFactory vvCurrencyBeanFactory = new
		VvCurrencyBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		return vvCurrencyBeanFactory.select(criteria,new SortCriteria("currencyDisplay"));
	}
	
	public String getPoLineForAddCharge(String radianPo, String poLine, String amendment) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PoAddChargeAllocationAllBeanFactory factory = new PoAddChargeAllocationAllBeanFactory(dbManager);
		SearchCriteria search = new SearchCriteria("radianPo", SearchCriterion.EQUALS, radianPo);
		search.addCriterion("poAddChargeLine", SearchCriterion.EQUALS, poLine);
		search.addCriterion("amendment", SearchCriterion.EQUALS,amendment);
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("poLine");
		Collection<PoAddChargeAllocationAllBean> poCol = factory.select(search,sort);
		StringBuffer poSbuf = new StringBuffer();
		for(PoAddChargeAllocationAllBean pobean : poCol){
			poSbuf.append(pobean.getPoLine());
			poSbuf.append(",");
		}
		String poSbufStr = StringHandler.stripLast(poSbuf.toString(), ",");
		return poSbufStr;
	}
	
	public ArrayList<String> getItemTypes(Collection<VvItemTypeBean> chargeTypeColl) {
		Iterator<VvItemTypeBean> it = chargeTypeColl.iterator();
		ArrayList<String> itemTypes = new ArrayList<String>();
		while (it.hasNext()) {
			VvItemTypeBean bean = (VvItemTypeBean) it.next();
			itemTypes.add(bean.getItemType());
		}
		return itemTypes;
	}
	
	public Collection getHubs() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		HubBeanFactory factory = new HubBeanFactory(dbManager);
		return factory.selectHubs();
	}
	
	public String getPoHubAndEntity(PurchaseOrderBean bean) throws BaseException {
		StringBuffer query = new StringBuffer("select hub_name from po_header_view where RADIAN_PO = ").append(bean.getRadianPo());	    
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try {
			return factory.selectSingleValue(query.toString());			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}
	
	public boolean isProblemWithBuy(PurchaseOrderBean bean) {
		boolean isProblemWithBuy = false;
		StringBuffer query = new StringBuffer("select status from buy_order where radian_po=").append(bean.getRadianPo());	    
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));		
		try {			
			Object[] c = factory.selectIntoObjectArray(query.toString());
			
			String problemWithBuy = "";
			if (((Vector)(c)[2]).size() > 0 && ((Vector)(c)[2]).get(0) != null && ((Object[])((Vector)(c)[2]).get(0))[0] != null)
				problemWithBuy = (String) ((Object[])((Vector)(c)[2]).get(0))[0].toString();		
			if (problemWithBuy.equalsIgnoreCase("ProblemWBuy")){
				isProblemWithBuy = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return isProblemWithBuy;
	}
	
	// This is the main save/confirm method. It is called on page submit when you click on the 
	// save or confirm link located on top of the grid. All the submitted header, po line information and the 9 grids information
	public HashMap updatePoDetails(PoHeaderViewBean poHeader, Collection<PoLineDetailViewBean> poLineDetailViewBeanCollection, 
			BigDecimal personnelId, String subAction ) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());		
		Connection conn = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		HashMap returnData = new HashMap();
		boolean success = true;
		String interCompMrNumber = "";
		String errorMessage = "";
		String radianPo = "";
		try {
			conn.setAutoCommit( false );
		    //boolean headerResult = updatePoHeader(poHeader, personnelId, conn);
		    HashMap headerResult = updatePoHeader(poHeader, personnelId, conn);
			boolean headerOutput = ((Boolean) headerResult.get(HEADER_RESULT)).booleanValue();
	        String headerError  = (String) headerResult.get(ERROR);	
		    if (!headerOutput)
		    	success = false;
		    radianPo = poHeader.getRadianPo().toString();
		    
		    if (!success) {
		    	returnData.put(INTER_COMPANY_MR, interCompMrNumber);
				returnData.put(LINE_RESULT, success);
				returnData.put(ERROR, headerError);
		    	return returnData;
		    }
		    
		    boolean lineResult = true;
		    conn.commit();
		    for(PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) 
		    {	
		    	//TODO: During the confirm process the SavepoLine procedure is called three times. 
		    	//First as a 'Save' action and then Second in the 'Confirm' action flow. 
		    	//In the confirm flow, the material types are called first to be confirmed and then the charge type items are called  
		    	//HashMap poLineUpdateResult  = updatePoLine( poHeader, poLineData, personnelId, subAction, conn );
		    	HashMap poLineUpdateResult  = updatePoLine( poHeader, poLineData, personnelId, "save", conn );
		    	lineResult= ((Boolean) poLineUpdateResult.get(LINE_RESULT)).booleanValue();
		        interCompMrNumber = (String) poLineUpdateResult.get(INTER_COMPANY_MR);
		        errorMessage = (String) poLineUpdateResult.get(ERROR);
		        log.debug("line " + poLineData.getPoLineNumber() + " and lineResult = " + lineResult + " interCompMrNumber="+ interCompMrNumber+ " errorMessage="+ errorMessage);
		        if (!lineResult )
		        	success = false;	
		        
		        int noOfSpecs = poLineData.getSpecBeanCol() != null ? poLineData.getSpecBeanCol().size() : 0;
		        int noOfFlowdowns = poLineData.getFlowdownBeanCol()!= null ? poLineData.getFlowdownBeanCol().size(): 0 ;
		        BigDecimal lineItemId= poLineData.getItemId();
				BigDecimal poLineNumber = poLineData.getPoLine();			
				int rowNumber = 0;
				if (poLineNumber != null)
					rowNumber = poLineNumber.intValue();
				BigDecimal ammendment = poLineData.getAmendment();
				String lineEverConfirmed = poLineData.getEverConfirmed();
				if (lineEverConfirmed.equalsIgnoreCase("N")) {
					ammendment = new BigDecimal(0);
				}
				int amendmentNumber = ammendment.intValue() * 1; 
				boolean poLineUpdated = false;
				//this flag is required to check if the save_po_line was called. Specs and flowdowns are saved only if save_po_line was called in the old code.
				if (poLineUpdateResult != null && poLineUpdateResult.get(PO_LINE_UPDATED) != null) {
					poLineUpdated =  ((Boolean)poLineUpdateResult.get(PO_LINE_UPDATED)).booleanValue();
				}
		        //save the specs and flowdowns for the complete poline details update.
		        if ( lineItemId != null && poLineUpdated && !"confirm".equalsIgnoreCase(subAction)) { 
					if (noOfSpecs > 0) {
						lineResult = updateSpecs( poLineData.getSpecBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn ); 
					} else
						lineResult = true;
					if (!lineResult )
				    	success = false;
					 
					if (noOfFlowdowns > 0) {
						lineResult = updateFlowdowns( poLineData.getFlowdownBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn ); 
					} else
						lineResult = true;
					if (!lineResult )
				    	success = false;
				}    
			}
		    
			for(PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) 
			{
				lineResult = updateLineAddCharges(poHeader, poLineData, "po", conn );
				if (!lineResult )
					success = false;			 
			}

			for(int i = 0; i < poLineDetailViewBeanCollection.size(); i++)
			{
				Iterator<PoLineDetailViewBean> itr = poLineDetailViewBeanCollection.iterator();
				PoLineDetailViewBean poLineData = null;
				while (itr.hasNext()) {
					poLineData = itr.next();  //gets the last element					
				}
				if (poLineDetailViewBeanCollection != null && poLineData != null) {					
					if (poLineData.getStatus().equalsIgnoreCase("Remove")) {
						HashMap poLineDeleteResult = deletePoLine(poHeader.getRadianPo(), poLineData, conn );
						lineResult = ((Boolean) poLineDeleteResult.get(LINE_RESULT)).booleanValue();
						poLineDetailViewBeanCollection.remove(poLineData);
					} else { 
						lineResult = true;
					}
					if (!lineResult )
						success = false;
				}
			}

			conn.commit();
			if ( !checkSetApprovalReqd(poHeader.getRadianPo(), subAction)) {
		    	returnData.put(PO_APPROVAL_REQUIRED, false);
				//if there's an error saving po line, don't proceed to confirm
				if ("confirm".equalsIgnoreCase( subAction ) && StringHandler.isBlankString(errorMessage)) {
					Vector inArgs = new Vector(); 
					inArgs.add(poHeader.getRadianPo());
					inArgs.add(personnelId);
					
					Vector outArgs = new Vector();
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					
					Vector errorMsg = (Vector)factory.doProcedure(conn, "pkg_expert_review.p_po_expert_review", inArgs, outArgs);				
					log.info("p_po_expert_review " + personnelId + " PO " + poHeader.getRadianPo() + " results: " + errorMsg.get(0) + "");
					errorMessage = ((String) errorMsg.get(0)).equalsIgnoreCase("ok") ? "" : (String) errorMsg.get(0);
					boolean needPoExpertReview = !StringHandler.isBlankString(errorMessage);
					
					boolean confirmationAttempted = false;
	            	for (PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) {
	            		/*Confirm all material lines first*/
						if (poLineData.getIsMaterialTypeItem().equalsIgnoreCase("Y")) {
							//if the po doesn't need Expert Review or cancelling line, proceed to confirm
							if (!needPoExpertReview || poLineData.getQuantity().compareTo(new BigDecimal("0")) == 0) {
								HashMap poLineUpdateResult = updatePoLine(poHeader, poLineData, personnelId, "confirm", conn);
								lineResult = ((Boolean) poLineUpdateResult.get(LINE_RESULT)).booleanValue();
								interCompMrNumber = (String) poLineUpdateResult.get(INTER_COMPANY_MR);
								if (!StringHandler.isBlankString(errorMessage))
									errorMessage += "\n" + (String) poLineUpdateResult.get(ERROR);
								else
									errorMessage = (String) poLineUpdateResult.get(ERROR);
								if (!lineResult)
									success = false;
								
								confirmationAttempted = true;
							}
						}
	            	}
	            	for(PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) 
	            	{	            		
	            		/*Confirm all additional charge lines later.*/
	            		if (!poLineData.getIsMaterialTypeItem().equalsIgnoreCase("Y"))
	            		{
	            			if (!needPoExpertReview || poLineData.getQuantity().compareTo(new BigDecimal("0")) == 0) {
								//if the po doesn't need Expert Review or cancelling line, proceed to confirm
		            			HashMap poLineUpdateResult  = updatePoLine( poHeader, poLineData, personnelId, "confirm", conn);
		            			lineResult = ((Boolean) poLineUpdateResult.get(LINE_RESULT)).booleanValue();
		            			errorMessage = (String) poLineUpdateResult.get(ERROR);
		            			if (!StringHandler.isBlankString(errorMessage))
									errorMessage += "\n" + (String) poLineUpdateResult.get(ERROR);
								else
									errorMessage = (String) poLineUpdateResult.get(ERROR);
		            			
		            			confirmationAttempted = true;
	            			}
				        }
	            		if (!lineResult)
							success = false;
	            	}
					conn.commit();
					//if PO needs Expert Review and no line has been canceled, set confirmation procedure's success flag to fail
					if (!confirmationAttempted)
						success = false;
					
					for(PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) {	
						lineResult = updateDeliveryDelayEmail( poHeader.getRadianPo(), poLineData,"confirm",conn );
					}
					conn.commit();
					
					allocatePO(poHeader.getRadianPo(), conn );
				    conn.commit();
				}
			}
			else {
				returnData.put(PO_APPROVAL_REQUIRED, true);
			}
	    
		    for(PoLineDetailViewBean poLineData : poLineDetailViewBeanCollection) 
        	{
		    	Collection<CatalogPartInventoryBean> lookaheadBeanCol = poLineData.getLookAheadBeanCol();
		    	if (lookaheadBeanCol != null && lookaheadBeanCol.size() > 0) {
		    		if (poLineData.getLookAheadChanged() != null && poLineData.getLookAheadChanged().equalsIgnoreCase("Y")) {
		    			updateLookAheads( lookaheadBeanCol, conn );
		    		}
		    	}
			}
			conn.commit();
		}
		catch ( Exception e )
		{
			success = false;
			e.printStackTrace();
			StringBuffer messageBody = new StringBuffer();
			messageBody.append("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");			
			MailProcess.sendEmail( MailProcess.DEFAULT_FROM_EMAIL, null, MailProcess.DEFAULT_FROM_EMAIL, "Error PO Page", messageBody.toString());
			log.debug("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");
			returnData.put(ERROR, messageBody.toString());
		}
		finally
		{
			conn.setAutoCommit(true);
			dbManager.returnConnection(conn);
		}	    
		returnData.put(INTER_COMPANY_MR, interCompMrNumber);
		returnData.put(LINE_RESULT, success);
		returnData.put(ERROR, errorMessage);
		return returnData;
	}

	//Use this method to save SDS. Header and Grid information is NOT saved in this method
	public HashMap updatePoSds(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData, 
			BigDecimal personnelId, String subAction ) throws Exception
	{
		return updateDeleteOnlyOnePoLine( poHeader, poLineData, personnelId, subAction );
	}

	//Use this method to save Specs. Header information is not saved in this method
	public HashMap updatePoSpecs(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData, BigDecimal personnelId, String subAction ) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());		
		Connection conn = dbManager.getConnection();
		HashMap result = new HashMap();	
		try {
			int noOfSpecs = poLineData.getSpecBeanCol() != null ? poLineData.getSpecBeanCol().size() : 0; 
			BigDecimal lineItemId= poLineData.getItemId(); 
			boolean specResult = false; 
			BigDecimal poLineNumber = poLineData.getPoLine();			
			int rowNumber = 0;
			if (poLineNumber != null)
				rowNumber = poLineNumber.intValue();
			BigDecimal ammendment = poLineData.getAmendment();
			String lineEverConfirmed = poLineData.getEverConfirmed(); //DataH.get( "LINE_EVER_CONFIRMED" ).toString().trim();
			if (lineEverConfirmed.equalsIgnoreCase("N")) {
				ammendment = new BigDecimal(0);
			}
			int amendmentNumber = ammendment.intValue() * 1; 
			if ( lineItemId != null ) { 
				result = updatePoLine(poHeader, poLineData, personnelId, subAction, conn);
				boolean poLineUpdated = false;
				boolean lineResult = false;
				//this flag is required to check if the save_po_line was called. Specs and flowdowns are saved only if save_po_line was called in the old code.
				if (result != null && result.get(PO_LINE_UPDATED) != null) {
					poLineUpdated =  ((Boolean)result.get(PO_LINE_UPDATED)).booleanValue();
					lineResult = ((Boolean)result.get(LINE_RESULT)).booleanValue();
				}
				if (poLineUpdated && noOfSpecs > 0 && !("confirm".equalsIgnoreCase(subAction))) {
					if (poLineData.getSpecChanged() != null && poLineData.getSpecChanged().equalsIgnoreCase("Y"))
						specResult = updateSpecs( poLineData.getSpecBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn );
					else
						specResult = true;
				} else if (lineResult && !poLineUpdated)
					specResult = true;
			}			
			result.put(LINE_RESULT, specResult);
			if (!specResult)
				result.put(ERROR,  library.getString("error.specdatasave"));  //Error on saving the Specs data. Please contact technical support
		} catch (Exception e) {
			log.error(e);
			result.put(ERROR, library.getString("error.specdatasave")); //"Error on saving the Specs data. Please contact technical support.");
		}
		return result;
	}
		
	//Use this method to save flowdowns. Header information is not saved in this method
	public HashMap updatePoFlowdowns(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData, BigDecimal personnelId, String subAction ) throws Exception
	{
		DbManager dbManager = new DbManager(getClient());		
		Connection conn = dbManager.getConnection();
		HashMap result = new HashMap();	
		try {
			int noOfFlowdowns = poLineData.getFlowdownBeanCol()!= null ? poLineData.getFlowdownBeanCol().size(): 0 ;
			boolean flowResult = false;
			BigDecimal poLineNumber = poLineData.getPoLine();			
			int rowNumber = 0;
			if (poLineNumber != null)
				rowNumber = poLineNumber.intValue();
			BigDecimal ammendment = poLineData.getAmendment();
			String lineEverConfirmed = poLineData.getEverConfirmed(); //DataH.get( "LINE_EVER_CONFIRMED" ).toString().trim();
			if (lineEverConfirmed.equalsIgnoreCase("N")) {
				ammendment = new BigDecimal(0);
			}
			int amendmentNumber = ammendment.intValue() * 1; 
				
			if (noOfFlowdowns > 0 && !("confirm".equalsIgnoreCase(subAction))) {
				result = updatePoLine(poHeader, poLineData, personnelId, subAction, conn);
				boolean poLineUpdated = false;
				boolean lineResult = false;
				//this flag is required to check if the save_po_line was called. Specs and flowdowns are saved only if save_po_line was called in the old code.
				if (result != null && result.get(PO_LINE_UPDATED) != null) {
					poLineUpdated =  ((Boolean)result.get(PO_LINE_UPDATED)).booleanValue();
					lineResult = ((Boolean)result.get(LINE_RESULT)).booleanValue();
				}
				if (poLineUpdated && poLineData.getFlowdownChanged() != null && poLineData.getFlowdownChanged().equalsIgnoreCase("Y"))
					flowResult = updateFlowdowns( poLineData.getFlowdownBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn );
				else if ((lineResult && !poLineUpdated) || (poLineData.getFlowdownChanged() != null && poLineData.getFlowdownChanged().equalsIgnoreCase("N")))
					flowResult = true;
			}
			result.put(LINE_RESULT, flowResult);
			
			if (!flowResult)
				result.put(ERROR, library.getString("error.flowdownsdatasave"));
		} catch (Exception e) {
			log.error(e);
			result.put(ERROR, library.getString("error.flowdownsdatasave"));//"Error on saving the Flowdowns data. Please try again later.");
		}
		return result;
	}
		
	//Use this method to DELETE one PO Line. Header information and Grids are NOT saved in this method.
	public HashMap updateDeleteOnlyOnePoLine(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData, 
			BigDecimal personnelId, String subAction ) throws Exception
	{

		DbManager dbManager = new DbManager(getClient());		
		Connection conn = dbManager.getConnection();
		HashMap returnData = new HashMap();
		boolean success = true;
		String interCompMrNumber = "";
		String radianPo = "";
		String errorMsg = "";
		try {
			
		    boolean lineResult = true;
		    
			//delete poline
			HashMap poLineDeleteResult = null;
			if ("Remove".equalsIgnoreCase(poLineData.getStatus())) {
				poLineDeleteResult = deletePoLine(poHeader.getRadianPo(), poLineData, conn );
				lineResult = ((Boolean) poLineDeleteResult.get(LINE_RESULT)).booleanValue();
			} else 
				lineResult = true;

			conn.commit();
			if (!lineResult ) {
				success = false;
				if (!StringHandler.isBlankString(errorMsg))
					errorMsg += "\n";
				errorMsg += (String) poLineDeleteResult.get(ERROR);
			}
		}
		catch ( Exception e )
		{
			success = false;
			e.printStackTrace();
			StringBuffer messageBody = new StringBuffer();
			messageBody.append("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");			
			MailProcess.sendEmail( MailProcess.DEFAULT_FROM_EMAIL, null, MailProcess.DEFAULT_FROM_EMAIL, "Price list upload failed", messageBody.toString());
			log.debug("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");
			errorMsg = library.format("error.polinedatasave", radianPo, poLineData.getPoLineNumber()); //"There was an error while saving the poline number = " + poLineData.getPoLineNumber() + " for radian PO = " + radianPo);			
		}
		finally
		{
			dbManager.returnConnection(conn);
		}
		if (success) //since some calling this method ignore LINE_RESULT flag and instead check for ERROR's emptiness to decide procedure's success
			errorMsg = "";
		returnData.put(ERROR, errorMsg);
		returnData.put(INTER_COMPANY_MR, interCompMrNumber);  
		returnData.put(LINE_RESULT, success);
		
		return returnData;
	}
	
	//Use this method to save one PO Line. Click on the 'Save Po Line' from the po line grid. 
	//Header and grid information for that line is also saved in this method
	public HashMap updatePoHeaderAndPoLine(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData, 
			BigDecimal personnelId, String subAction ) throws Exception
	{

		DbManager dbManager = new DbManager(getClient());		
		Connection conn = dbManager.getConnection();
		HashMap returnData = new HashMap();
		boolean success = true;
		String interCompMrNumber = "";
		String radianPo = "";
		try {
			boolean lineResult = true;
			HashMap headerResult = updatePoHeader(poHeader, personnelId, conn);
			boolean headerOutput = ((Boolean) headerResult.get(HEADER_RESULT)).booleanValue();
	        String headerError  = (String) headerResult.get(ERROR);	
		    if (!headerOutput)
		    	success = false;
		    radianPo = poHeader.getRadianPo().toString();
		    
		    conn.commit();

		    if (!success) {
		    	returnData.put(INTER_COMPANY_MR, interCompMrNumber);  
				returnData.put(LINE_RESULT, success);
				returnData.put(ERROR, headerError);
		    	return returnData;
		    }
		    
	    	HashMap poLineUpdateResult  = updatePoLine( poHeader, poLineData, personnelId, subAction, conn );
	    	lineResult= ((Boolean) poLineUpdateResult.get(LINE_RESULT)).booleanValue();
	        interCompMrNumber = (String) poLineUpdateResult.get(INTER_COMPANY_MR);	        
	        log.debug("line " + poLineData.getPoLineNumber() + " and lineResult = " + lineResult + " interCompMrNumber="+ interCompMrNumber);
	        
	        if (!lineResult )
		    	success = false;
	        
	        if (!success) {	        	
		    	returnData.put(INTER_COMPANY_MR, interCompMrNumber);  
				returnData.put(LINE_RESULT, success);
				returnData.put(ERROR, (String) poLineUpdateResult.get(ERROR));
		    	return returnData;
		    }
			
	        int noOfSpecs = poLineData.getSpecBeanCol() != null ? poLineData.getSpecBeanCol().size() : 0;
	        int noOfFlowdowns = poLineData.getFlowdownBeanCol()!= null ? poLineData.getFlowdownBeanCol().size(): 0 ;
	        BigDecimal lineItemId = poLineData.getItemId();
			BigDecimal poLineNumber = poLineData.getPoLine();			
			int rowNumber = 0;
			if (poLineNumber != null)
				rowNumber = poLineNumber.intValue();
			BigDecimal ammendment = poLineData.getAmendment();
			String lineEverConfirmed = poLineData.getEverConfirmed();
			if (lineEverConfirmed.equalsIgnoreCase("N")) {
				ammendment = new BigDecimal(0);
			}
			
			int amendmentNumber = ammendment.intValue() * 1; 
			boolean poLineUpdated = false;
			//this flag is required to check if the save_po_line was called. Specs and flowdowns are saved only if save_po_line was called in the old code.
			if (poLineUpdateResult != null && poLineUpdateResult.get(PO_LINE_UPDATED) != null) {
				poLineUpdated =  ((Boolean)poLineUpdateResult.get(PO_LINE_UPDATED)).booleanValue();
			}
			
			//save the specs and flowdowns for the complete poline details update.
	        if ( lineItemId != null && poLineUpdated && !"confirm".equalsIgnoreCase(subAction)) { 
				if (noOfSpecs > 0) {
					lineResult = updateSpecs( poLineData.getSpecBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn ); 
				} else
					lineResult = true;
				if (!lineResult )
			    	success = false;
				 
				if (noOfFlowdowns > 0) {
					lineResult = updateFlowdowns( poLineData.getFlowdownBeanCol(), poHeader.getRadianPo(), rowNumber, amendmentNumber, "po", conn ); 
				} else
					lineResult = true;
				if (!lineResult )
			    	success = false;
				
				log.debug("Specs and flowdowns saved for line " + poLineData.getPoLineNumber() + " and success = " + success );
	        }
	        
	        if (poLineUpdated || "Y".equalsIgnoreCase(poLineData.getChargeListChanged()))
	        	lineResult = updateLineAddCharges(poHeader, poLineData, "po", conn );
	        else
	        	lineResult = true;
			
			if (!lineResult )
		    	success = false;
						
			//delete poline
			if (poLineData.getStatus().equalsIgnoreCase("Remove")) {
				HashMap poLineDeleteResult = deletePoLine(poHeader.getRadianPo(), poLineData, conn );
				lineResult = ((Boolean) poLineDeleteResult.get(LINE_RESULT)).booleanValue();
			} else 
				lineResult = true;

			if (!lineResult )
		    	success = false;
			
			conn.commit();
			
    		if (poLineData.getLookAheadChanged() != null && poLineData.getLookAheadChanged().equalsIgnoreCase("Y")) { 
    			//updateLookAheads( lookaheadBeanCol, poLineData, conn );
    			updateLookAheads(poLineData.getLookAheadBeanCol(), conn );
    		}
			
			if (!lineResult )
				success = false;
			
			conn.commit();
			returnData.put(INTER_COMPANY_MR, interCompMrNumber);  
			returnData.put(LINE_RESULT, success);
		}
		catch ( Exception e )
		{
			success = false;
			e.printStackTrace();
			StringBuffer messageBody = new StringBuffer();
			messageBody.append("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");			
			MailProcess.sendEmail( MailProcess.DEFAULT_FROM_EMAIL, null, MailProcess.DEFAULT_FROM_EMAIL, "Price list upload failed", messageBody.toString());
			log.debug("Error PO Page \n" + "Error Msg:\n"+e.getMessage()+"\n  radian PO  "+ radianPo +" \n personnelID "+ personnelId + "\n");
			returnData.put(ERROR, library.format("error.polinedatasave", radianPo, poLineData.getPoLineNumber())); //There was an error while saving the poline number = " + poLineData.getPoLineNumber() + " for radian PO = " + radianPo);			
		}
		finally
		{	
			dbManager.returnConnection(conn);
		}
		return returnData;
	}
		
	public boolean updateLookAheads(Collection<CatalogPartInventoryBean> lookaheadBeanCol) throws Exception {
		return updateLookAheads(lookaheadBeanCol, null);
	}
	
	private boolean updateLookAheads(Collection<CatalogPartInventoryBean> lookaheadBeanCol, Connection conn) throws Exception{
		boolean updateResult = true;		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		DbManager dbManager = new DbManager(getClient());
		boolean newConnection = false;
		
		if (lookaheadBeanCol == null)
			return false;
		
		if (conn == null) {	
			newConnection = true;
			conn = dbManager.getConnection();
		}
		try
		{
			for (CatalogPartInventoryBean bean : lookaheadBeanCol) {
								
				String lookaheadCatalogId = bean.getCatalogId(); //flowData.get( "LOOKAHDCATALOGID" ).toString().trim();
				String lookaheadCatPartNo = bean.getCatPartNo(); //flowData.get( "LOOKAHDCATPARTNO" ).toString().trim();
				String lookaheadCompanyId = bean.getCompanyId(); //flowData.get( "LOOKAHDCOMPANYID" ).toString().trim();
				BigDecimal lookaheadDays = bean.getLookAheadDays(); //flowData.get( "LOOKAHDNODAYS" ).toString().trim();				
				String lookaheadInventoryGrp = bean.getInventoryGroup(); //flowData.get( "LOOKAHDINVGRP" ).toString().trim();
				BigDecimal lookaheadPartGrpNo = bean.getPartGroupNo(); //flowData.get( "LOOKAHDPARTGRPNO" ).toString().trim();
				
				//do not update if there is no change
				String changed = bean.getChanged();
				if (changed != null && changed.equalsIgnoreCase("N")) {
					updateResult = true;
					continue;
				}
				try {					
					StringBuilder updateQuery = new StringBuilder();
					if (lookaheadCatalogId != null && lookaheadCatPartNo != null && lookaheadCompanyId != null && lookaheadDays != null ){
						updateQuery.append("update customer.catalog_part_inventory ");
						updateQuery.append(" set LOOK_AHEAD_DAYS = ").append(lookaheadDays);
						updateQuery.append(" where ");
						updateQuery.append(" COMPANY_ID = ").append(SqlHandler.delimitString(lookaheadCompanyId));
						updateQuery.append(" and CATALOG_ID = ").append(SqlHandler.delimitString(lookaheadCatalogId));
						updateQuery.append(" and CAT_PART_NO = ").append(SqlHandler.delimitString(lookaheadCatPartNo));
						updateQuery.append(" and INVENTORY_GROUP = ").append(SqlHandler.delimitString(lookaheadInventoryGrp));
						updateQuery.append(" and PART_GROUP_NO = ").append(lookaheadPartGrpNo);
					}
				
					int rowsUpdated = factory.deleteInsertUpdate(updateQuery.toString(), conn);
					
					if (rowsUpdated > 0 ) {					
						Vector inArgs = new Vector();			
						inArgs.add(lookaheadCompanyId);
						inArgs.add(lookaheadCatalogId);
						inArgs.add(lookaheadCatPartNo);			
						inArgs.add(lookaheadInventoryGrp);
						//inArgs.add(lookaheadPartGrpNo);
						
						factory.doProcedure(conn, "p_cpi_rli_allocate", inArgs);
						log.debug("executed procedure for updating lookups - p_cpi_rli_allocate");					
					} else 
						updateResult = false;
					conn.commit();
				}
				catch (Exception ex) {
					updateResult = false;
					log.error("Error Calling p_cpi_rli_allocate in PO Page. Error Calling p_cpi_rli_allocate\nError Msg:\n" + ex.getMessage() + "\n  COMPANY_ID='" 
							+ lookaheadCompanyId + "' and CATALOG_ID='" + lookaheadCatalogId + "' and CAT_PART_NO ='" + lookaheadCatPartNo + "'");
				}
			}
		}
		
		finally 
		{
			if (newConnection)
				dbManager.returnConnection(conn);
		}
				
		return updateResult;
	}	  

	private boolean allocatePO(BigDecimal radianPo, Connection conn) {
		boolean result = false;
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));

		try
		{
			Vector inArgs = new Vector();			
			inArgs.add(radianPo);			
			
			factory.doProcedure(conn,  "p_po_allocate", inArgs );
			log.debug("p_po_allocate for po - " + radianPo);
			result = true;
		}
		catch ( Exception e )
		{
		  result=false;
		  //e.printStackTrace();
		  log.error("Error Calling p_po_allocate PO Page; Error p_po_allocate\nError Msg:\n" + e.getMessage() + "\n  radian PO  " + radianPo);
		}
		finally
		{
			
		}
		return result;
	  }

	private boolean updateDeliveryDelayEmail(BigDecimal radianPo, PoLineDetailViewBean poLineData,
			String action, Connection conn) {
		boolean result = false;
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try
		{
			BigDecimal lineQty = poLineData.getQuantity(); //DataH.get( "LINEQTY" ).toString().trim();
			BigDecimal lineItemId = poLineData.getItemId(); //DataH.get( "LINEITEMID" ).toString().trim();
			String lineStatus = poLineData.getStatus(); //DataH.get( "LINESTATUS" ).toString().trim();
			String lineChange = poLineData.getLineChange(); //DataH.get( "LINECHANGE" ).toString().trim();
			String originalLineStatus = poLineData.getCurrentLineStatus(); //DataH.get( "ORIGINALLINESTATUS" ).toString().trim();
			
			if ("No".equalsIgnoreCase(lineChange)
			 	&& (!"confirm".equalsIgnoreCase(action))) {
				result = true;
			} else if ("Yes".equalsIgnoreCase(lineChange)
					&& ("confirm".equalsIgnoreCase(action))
					&& (lineItemId == null || lineQty == null)) {
				if ("Remove".equalsIgnoreCase(lineStatus)) {
					result = true;
				} else {
					result = false;
				}
			} else if ("Yes".equalsIgnoreCase(lineChange)
					|| "Unconfirmed".equalsIgnoreCase(originalLineStatus)) {
				Vector inArgs = new Vector();
				inArgs.add(radianPo);
				inArgs.add(poLineData.getPoLineNumber());
				
				factory.doProcedure(conn, "pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE", inArgs);
				
				result = true;
			} else if ("No".equalsIgnoreCase(lineChange)) {
				result = true;
			}
		}
		catch ( Exception e )
		{
			result = false;
			log.error("Error Calling pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE in PO Page; Error Calling pkg_po.PR_DELIVERY_DELAY_NOTIFY_LINE\nError Msg:\n " + e.getMessage() + "\n  radian PO  " + radianPo);
		}
		finally
		{
			
		}
		return result;
	  }

	private HashMap deletePoLine(BigDecimal RadianPo, PoLineDetailViewBean poLineData,
			Connection conn) 
	{
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		boolean deleteResult = true;
		HashMap poLineDeleteResult = new HashMap();
		String errorMsg = "";
		try
		{
			String lineStatus = poLineData.getStatus(); //DataH.get( "LINESTATUS" ).toString().trim(); // TODO: check if this is the new status on page?
			
			if ( "Remove".equalsIgnoreCase( lineStatus ) )
			{
				Vector inArgs = new Vector();
				inArgs.add(RadianPo);
				inArgs.add(poLineData.getPoLine());
				inArgs.add(poLineData.getAmendment());
				
				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				Vector errMsg = (Vector) factory.doProcedure(conn, "pkg_po.DELETE_PO_LINE", inArgs, outArgs);
					
				if (errMsg.size() > 0 && errMsg.get(0) != null) {
					log.debug( "Error from pkg_po.DELETE_PO_LINE " + errMsg.get(0) );
					errorMsg = (String) errMsg.get(0);
					deleteResult = false;
				} else {
					deleteResult = true;
				}
				conn.commit();
			}
		}
		catch ( Exception e )
		{
			 deleteResult=false;
			 errorMsg = library.getString("error.db.procedure");
			 log.error("Error Calling pkg_po.DELETE_PO_LINE in PO Page;  Error Calling pkg_po.DELETE_PO_LINE\nError Msg:\n" + e.getMessage() 
					 + "\n  radianPo = " + RadianPo + "  rowNumber = " + poLineData.getPoLineNumber() + " amendment = " + poLineData.getAmendment());
		}
		poLineDeleteResult.put(LINE_RESULT, deleteResult);
		poLineDeleteResult.put(ERROR, errorMsg);
		
		return poLineDeleteResult;
	}

	private boolean updateLineAddCharges(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData,
			String whichPo, Connection conn) {
		boolean lineAddChrgResult = true;

		try
		{
			String lineStatus = poLineData.getStatus(); //DataH.get( "LINESTATUS" ).toString().trim();
			String lineChange = poLineData.getLineChange(); //DataH.get( "LINECHANGE" ).toString().trim();		  
			Collection addCharges = poLineData.getChargeListBeanCol(); //( Vector ) DataH.get( "CHARGELINEDETAILS" );
			
			int noOfAddCharges = 0;
			if (addCharges != null && addCharges.size() > 0) {
				noOfAddCharges = addCharges.size(); //DataH.get( "NOOFLINESINADDCHARGE" ).toString().trim();
			}
			String originalLineStatus = poLineData.getPoLineStatus(); //DataH.get( "ORIGINALLINESTATUS" ).toString().trim();
						
			if ((!"Remove".equalsIgnoreCase(lineStatus) 
				&& "Y".equalsIgnoreCase(lineChange))
				|| "Unconfirmed".equalsIgnoreCase(originalLineStatus)) 
			{
				if (!poLineData.getIsMaterialTypeItem().equalsIgnoreCase("Y")
						&& (noOfAddCharges > 0) ) 
				{			
					lineAddChrgResult = updateAddCharges(addCharges, poHeader.getRadianPo(), poLineData.getPoLine(), poLineData.getAmendment(), conn);
				}
			}
		}
		catch ( Exception e )
		{
		  lineAddChrgResult = false;
		}

		return lineAddChrgResult;
	}

	private boolean updateAddCharges(Collection<PoLineItemListBean> addCharges, BigDecimal radianPo,
			BigDecimal poLineNumber, BigDecimal amendment,
			Connection conn) {
		boolean addChargesResult = false;
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try
		{
			StringBuilder query = new StringBuilder();			
			query.append("delete from po_add_charge_alloc_draft where RADIAN_PO = ").append(radianPo).append(" and PO_ADD_CHARGE_LINE = ").append(poLineNumber);
			factory.deleteInsertUpdate(query.toString(), conn );

		    int addChargeDuplicates = 0;
		    
		    query = new StringBuilder();
		    query.append(" Select count(*) from po_add_charge_allocation where RADIAN_PO = ").append(radianPo);
		    query.append(" and PO_ADD_CHARGE_LINE = ").append(poLineNumber);
		    query.append(" and AMENDMENT = ").append(amendment);
		    
		    String countRows = factory.selectSingleValue(query.toString(),conn);
		    
		    if(!StringHandler.isBlankString(countRows)) {
		    	addChargeDuplicates = Integer.valueOf(countRows);
		    }
	
		    query = new StringBuilder();
		    query.append("Select count(*) from po_add_charge_alloc_history where RADIAN_PO = ").append(radianPo);
		    query.append(" and PO_ADD_CHARGE_LINE = ").append(poLineNumber);
		    query.append(" and AMENDMENT = ").append(amendment);
		    
		    countRows = factory.selectSingleValue(query.toString(),conn);
		    
		    if(!StringHandler.isBlankString(countRows)) {
		    	addChargeDuplicates = addChargeDuplicates + Integer.valueOf(countRows);
		    }
		   
		    if (addChargeDuplicates == 0)
		    {
		    	for (PoLineItemListBean chargeBean : addCharges)
		    	{
		    		boolean addChargeCheck = chargeBean.getSelectPo(); //addchargesData.get( "ADDCHARGECHECK" ).toString().trim();
		    		BigDecimal chargeLineNumber = chargeBean.getPoLine(); //addchargesData.get( "CHARGETOLINE" ).toString().trim();
		    		
		    		if (addChargeCheck) 
		    		{
		    			StringBuilder insert = new StringBuilder();
		    			
	    				insert.append("insert into po_add_charge_alloc_draft (RADIAN_PO,PO_LINE,PO_ADD_CHARGE_LINE,AMENDMENT) values \n");
	    				insert.append("(").append(radianPo).append(",").append(chargeLineNumber).append(",").append(poLineNumber).append( "," ).append(amendment).append(") \n");
		    			factory.deleteInsertUpdate(insert.toString(), conn);
		    		}
		    	}   
		    	addChargesResult = true;
		    	conn.commit();
		    }
		} catch ( Exception e ) {
		  addChargesResult = false;
		}
		return addChargesResult;
	  }

	private HashMap updatePoLine(PoHeaderViewBean poHeader, PoLineDetailViewBean poLineData,
			BigDecimal personnelId, String action, Connection conn) throws DbReturnConnectionException {
		if (StringHandler.isBlankString(poLineData.getCurrency())) {
			HashMap poLineUpdResult = new HashMap();
			poLineUpdResult.put(LINE_RESULT, false); 
			poLineUpdResult.put(INTER_COMPANY_MR,"");
			poLineUpdResult.put(ERROR, library.getString("errors.selectavalid").replace("{0}", library.getString("label.currency")));
			poLineUpdResult.put(PO_LINE_UPDATED, false);
			return poLineUpdResult;
		}
		
		boolean result=false;
		boolean specResult=true;
		boolean flowResult=true;		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		BigDecimal ammendment = new BigDecimal(0);
	    String interCompMrNumber = "";
	    HashMap poLineUpdResult = new HashMap();
	    String errorCode = "";
	    DbManager dbManager = new DbManager(getClient());
	    boolean newConnection = false;
	    boolean poLineUpdated = false; 
		try
		{
			if (conn == null) {	
				newConnection = true;
				conn = dbManager.getConnection();
			}
			BigDecimal lineUnitPrice = poLineData.getUnitPrice(); //DataH.get( "LINEUNITPRICE" ).toString().trim();
			BigDecimal lineQty = poLineData.getQuantity(); //DataH.get( "LINEQTY" ).toString().trim();
			  		  
			String lineNotes= poLineData.getLineNotes(); //DataH.get( "LINENOTES" ).toString().trim(); 
			if (lineNotes != null && !lineNotes.equalsIgnoreCase("")) {
				lineNotes = StringHandler.replace(lineNotes, "&#34;","\"" );
				lineNotes = StringHandler.replace(lineNotes,"&#40;","(" );
				lineNotes = StringHandler.replace(lineNotes,"&#41;",")" );
				lineNotes = StringHandler.replace(lineNotes,"<BR>"," \n " );
				lineNotes = StringHandler.replace(lineNotes,"'","''" );
			}

			//Date datePromised = poLineData.getDateConfirmed();		//DataH.get( "DATEPROMISED" ).toString().trim();
			Date datePromised = poLineData.getProjectedDate();		//DataH.get( "DATEPROMISED" ).toString().trim();
			String supplierPartNo = poLineData.getSupplierPn(); 	//DataH.get( "SUPPLIERPARTNO" ).toString().trim();
			Date promisedShipDate = poLineData.getPromisedDate();   //poLineData.getVendorShipDate();
			  
			//DataH.get( "PROJECTEDELIVDATE" ).toString().trim();
			Date dateNeeded = poLineData.getNeedDate(); //DataH.get( "DATENEEDED" ).toString().trim();
			BigDecimal lineItemId= poLineData.getItemId(); //DataH.get( "LINEITEMID" ).toString().trim();
			String dPas = poLineData.getDpas(); //DataH.get( "DPAS" ).toString().trim();			
			String lineStatus = poLineData.getStatus();//DataH.get( "LINESTATUS" ).toString().trim();
			String lineChangeStatus = poLineData.getLineChange();//DataH.get( "LINECHANGE" ).toString().trim();
			String originalLineStatus = poLineData.getPoLineStatus();//DataH.get( "ORIGINALLINESTATUS" ).toString().trim(); (using poline status as the original since this is retrieved from database
			ammendment = poLineData.getAmendment();//DataH.get( "AMMENDMENT" ).toString().trim();
			String ammendmentComments = poLineData.getAmmendmentcomments();//DataH.get( "AMMENDMENTCOMMENTS" ).toString().trim();
			ammendmentComments = StringHandler.replace(ammendmentComments, "'", "''"); //BothHelpObjs.changeSingleQuotetoTwoSingleQuote( ammendmentcomments );

			BigDecimal supplierQty = poLineData.getSupplierQty();//DataH.get( "SUPPLIERQTY" ).toString().trim();
			String supplierPkg = poLineData.getSupplierPkg(); //DataH.get( "SUPPLIERPKG" ).toString().trim();
			BigDecimal supplierUnitPrice = poLineData.getSupplierUnitPrice(); //DataH.get( "SUPPLIERUNITPRICE" ).toString().trim();			
			String checkForMSDS = poLineData.getMsdsRequestedDate(); //DataH.get( "CHECKFORMSDS" ).toString().trim();
			String shelfLife = poLineData.getShelfLife(); //DataH.get( "SHELFLIFE" ).toString().trim();
			String deliveryLineNotes = poLineData.getDeliveryComments(); //DataH.get( "DELIVERYLINENOTES" ).toString().trim();
			if ( deliveryLineNotes != null && deliveryLineNotes.length() > 0 ) {
				deliveryLineNotes = StringHandler.replace( deliveryLineNotes, "&#34;", "\"" );
				deliveryLineNotes = StringHandler.replace( deliveryLineNotes, "&#40;", "(" );
				deliveryLineNotes = StringHandler.replace( deliveryLineNotes, "&#41;", ")" );
				deliveryLineNotes = StringHandler.replace( deliveryLineNotes, "<BR>", " \n " );
				deliveryLineNotes = StringHandler.replace( deliveryLineNotes, "'", "''" );
			}
			String currencyId = poLineData.getCurrency(); //DataH.get( "CURRENCY_ID" ).toString().trim();
			String secondarySupplier = poLineData.getSupplier(); //DataH.get( "SECONDARY_SUPPLIER" ).toString().trim();
			String shipFromLocationId = poLineData.getShipFromLocationId(); //DataH.get( "SHIP_FROM_LOCATION_ID" ).toString().trim();
			String supplierSalesOrderNo = poLineData.getSupplierSalesOrderNo(); //DataH.get( "SUPPLIER_SALES_ORDER_NO" ).toString().trim();
			String lineEverConfirmed = poLineData.getEverConfirmed(); //DataH.get( "LINE_EVER_CONFIRMED" ).toString().trim();
			if (lineEverConfirmed.equalsIgnoreCase("N")) {
				ammendment = new BigDecimal(0);
			}
						
			//BigDecimal rowNumber= new BigDecimal(seqNumber);
			//rowNumber = rowNumber.multiply(new BigDecimal("1000"));
			BigDecimal poLineNumber = poLineData.getPoLine();			
			int rowNumber = 0;
			if (poLineNumber != null)
				rowNumber = poLineNumber.intValue();
			int amendmentNumber = ammendment.intValue() * 1; 
			
			if ("N".equalsIgnoreCase(lineChangeStatus) && (!"confirm".equalsIgnoreCase(action))) { 
				result = true; 
			} else if (("confirm".equalsIgnoreCase(action)) && "Remove".equalsIgnoreCase(lineStatus)) {
				log.info("Confirming Removed Line for Purchase Order: " + poHeader.getRadianPo() + " rowNumber:  " + rowNumber + "  amendmentNumber:  " + amendmentNumber);
				result = true;
			} else if ("Y".equalsIgnoreCase(lineChangeStatus) && ("confirm".equalsIgnoreCase(action)) && (lineItemId == null || lineQty == null )) {
				if ("Remove".equalsIgnoreCase(lineStatus)) {
					result = true;
				} else {
					result = false;
				}
			} else if ( "Y".equalsIgnoreCase( lineChangeStatus ) || "Unconfirmed".equalsIgnoreCase( originalLineStatus ) 
						||("Unconfirmed".equalsIgnoreCase( lineStatus ) && "confirm".equalsIgnoreCase(action))) {
				Vector inArgs = new Vector();			
				inArgs.add(poHeader.getRadianPo());
				inArgs.add(rowNumber);
				inArgs.add(ammendment);
				inArgs.add(lineItemId);
				inArgs.add(action.equalsIgnoreCase("confirm")?"Y":null);				
				inArgs.add(lineQty);
				inArgs.add(lineUnitPrice);
				inArgs.add(dateNeeded);
				inArgs.add(datePromised);
				inArgs.add(null);
				inArgs.add(null);
				inArgs.add(supplierPartNo);
				inArgs.add(dPas);
				inArgs.add(null); 
				inArgs.add(null);
				inArgs.add(lineNotes);
				if (action.equalsIgnoreCase("confirm"))
					inArgs.add(null);
				else 
					inArgs.add(ammendmentComments.equalsIgnoreCase("")?null:ammendmentComments);
				inArgs.add(personnelId);
				inArgs.add(supplierQty);
				inArgs.add(supplierPkg);
				inArgs.add(supplierUnitPrice);
				inArgs.add("N");//cocforflow not used anymore
				inArgs.add("N");//coaforflow not used anymore
				inArgs.add((checkForMSDS != null && checkForMSDS.equalsIgnoreCase("Yes"))?"Y":null); //add the checkforMsdsasked here.
				inArgs.add(shelfLife);
				inArgs.add(deliveryLineNotes);
				inArgs.add(promisedShipDate);
				inArgs.add(currencyId);
				inArgs.add(secondarySupplier != null && !secondarySupplier.trim().equalsIgnoreCase("") ? secondarySupplier:null);
							
				Vector outArgs = new Vector();			
			    outArgs.add(new Integer(java.sql.Types.VARCHAR));
			    outArgs.add(new Integer(java.sql.Types.VARCHAR));
			

				Vector optArgs = new Vector();			    
			    optArgs.add(shipFromLocationId);
			    optArgs.add(supplierSalesOrderNo);
			    optArgs.add(null);
			    
			    //invoke procedure to compare the excel data
				Vector procErrorMessage = (Vector)factory.doProcedure(conn, "pkg_po.SAVE_PO_LINE", inArgs, outArgs, optArgs);
				
				if(procErrorMessage.size() > 0 && procErrorMessage.get(0) != null) {
					result = false;
					errorCode = (String) procErrorMessage.get(0);
					log.info( "Error from pkg_po.SAVE_PO_LINE - Radian Po " + poHeader.getRadianPo() + " and line number " + rowNumber + " errorcode " + errorCode + " amendmentNumber " + amendmentNumber + " originalLineStatus " + originalLineStatus );
				} else {
			    	result = true;
			    	interCompMrNumber = (String) procErrorMessage.get(1);
			    }
				poLineUpdated = true;
			} else if ("N".equalsIgnoreCase(lineChangeStatus)) {
				result = true;
			}
		}
		catch ( Exception e )
		{
			result = false;
			String erroMessage = e.getMessage();
			log.info(erroMessage);
			log.info(erroMessage.indexOf("constraint") + "");
			if (erroMessage.indexOf("constraint") > 0) {
				String[] supportEmails = new String[]{"asepulveda@haastcm.com,mboze@haastcm.com,mblackburn@haastcm.com,dbertero@haastcm.com"};
				MailProcess.sendEmail( "deverror@tcmis.com", null ,"deverror@tcmis.com", "Error Calling pkg_po.SAVE_PO_LINE in PO Page", 
						"Error Calling pkg_po.SAVE_PO_LINE\nError Msg:\n" + e.getMessage() + "\n  radian PO  " + poHeader.getRadianPo() + "  rowNumber  " + poLineData.getPoLineNumber() 
						+ " ammendment  " + ammendment + " \n personnelID " + personnelId + "");
			}
			errorCode += "\n" + erroMessage;
		}
		finally
		{
			if (newConnection)
				dbManager.returnConnection(conn);
		}
	    poLineUpdResult.put(LINE_RESULT, new Boolean(result)); 
		if (interCompMrNumber == null) {
			interCompMrNumber = "";
		}
		poLineUpdResult.put(INTER_COMPANY_MR,interCompMrNumber);
		poLineUpdResult.put(ERROR, errorCode);
		poLineUpdResult.put(PO_LINE_UPDATED, poLineUpdated);
		return poLineUpdResult;       
	}
	
	public boolean checkSetApprovalReqd(BigDecimal radianPo, String subAction) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		String approvalReqd = factory.selectSingleValue(
				"select pkg_financial_approval_po.fx_is_financial_approval_reqd("+radianPo+") from dual");

		if ("confirm".equals(subAction) || "resubmit".equals(subAction)) {
			Vector approvalArgs = new Vector();
			approvalArgs.add(radianPo);
			approvalArgs.add(approvalReqd);
		
			factory.doProcedure("pkg_financial_approval_po.p_set_approval_reqd", approvalArgs);
		}
		
		return "Y".equals(approvalReqd);
	}
	
	public String isValidApprover(PoHeaderViewBean input, PersonnelBean user) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		String query = "select decode(count(c.po_approval_id), 0, 'N', 'Y') valid_approver"
				+ " from po_approval_chain c, po_approval a"
				+ " where a.po_approval_id = c.po_approval_id"
				+ " and a.radian_po = " + input.getRadianPo()
				+ " and c.approver_personnel_id = " + user.getPersonnelIdBigDecimal()
				+ " and c.action_taken = 'Pending'";
				
		return factory.selectSingleValue(query);
	}

	private boolean updateFlowdowns(Collection<FlowDownBean> flowdownBeanCol, BigDecimal radianPo , 
			int rowNumber, int amendmentNumber, String whichPo, Connection conn) 
	{
		boolean result = true;
		StringBuilder query = new StringBuilder();
		DbManager dbManager = new DbManager(getClient());		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try
		{
			query.append("delete from po_line_flow_down_draft where RADIAN_PO = ").append(radianPo).append(" and PO_LINE = ").append(rowNumber);
			factory.deleteInsertUpdate(query.toString(), conn);
			conn.commit();
			int cntInserted = 0;
			for(FlowDownBean flowdownBean : flowdownBeanCol) {				
				String flowCatalogId = flowdownBean.getCatalogId(); //flowData.get( "FLOWCATALOGID" ).toString().trim();
				String flowCompanyId = flowdownBean.getCompanyId(); //flowData.get( "FLOWCOMPANYID" ).toString().trim();
				boolean flowCompliance = StringHandler.isBlankString(flowdownBean.getOk())? false :new Boolean(flowdownBean.getOk()) ;//flowData.get( "FLOWCOMPLIANCE" ).toString().trim();
				String flowId = flowdownBean.getFlowDown(); //flowData.get( "FLOWID" ).toString().trim();
				String attachFlag = "";
				if ( flowCompliance) {			 
					StringBuilder insertFlows = new StringBuilder();
					insertFlows.append("insert into po_line_flow_down_draft (COMPANY_ID,CATALOG_ID,FLOW_DOWN,RADIAN_PO,PO_LINE,ATTACH,AMENDMENT) values \n");
					insertFlows.append("(").append(SqlHandler.delimitString(flowCompanyId)).append(",").append(SqlHandler.delimitString(flowCatalogId)).append(",").append(SqlHandler.delimitString(flowId)).append(",");
					insertFlows.append(radianPo).append(",").append(rowNumber).append(",").append(SqlHandler.delimitString(attachFlag)).append(",").append(amendmentNumber).append(") \n");
					cntInserted += factory.deleteInsertUpdate(insertFlows.toString(), conn);
					conn.commit();
					log.debug("cntInserted - " + cntInserted);
				}
			}
		}
		catch ( Exception e )
		{
			result = false;
		}
		return result;
	}
 
	private boolean updateSpecs(Collection<SpecBean> specBeanCol, BigDecimal radianPo,
			int rowNumber, int amendmentNumber, String whichPo, Connection conn) {
		boolean result = true;
		DbManager dbManager = new DbManager(getClient());		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		
		try {
			
			StringBuffer deleteQuery = new StringBuffer();
			deleteQuery.append("delete from po_line_spec_draft where RADIAN_PO = ").append(radianPo).append(" and PO_LINE = ").append(rowNumber) ; 
			factory.deleteInsertUpdate(deleteQuery.toString(), conn);
			
			for(SpecBean specBean : specBeanCol) 
        	{	
				String specLibrary=specBean.getSpecLibrary(); //specData.get( "SPECLIBRARY" ).toString().trim();
				String specId=specBean.getSpecId(); //specData.get( "SPECID" ).toString().trim();
				String specCoc = specBean.getSavedCocStr(); //specData.get( "SPECCOCCMPLIANCE" ).toString().trim();
				String specCoa = specBean.getSavedCoaStr(); //specData.get( "SPECCOACMPLIANCE" ).toString().trim();
				boolean blnSpecCoc = false; 
				if (specCoc != null)
					blnSpecCoc = new Boolean(specCoc).booleanValue();
				boolean blnSpecCoa = false;
				if (specCoa != null)
					blnSpecCoa = new Boolean(specCoa).booleanValue();
				String cocFlag = "";
				String coaFlag = "";
				String specDetail = specBean.getDetail(); //specData.get( "SPECDETAIL" ).toString().trim();
				boolean blnSpecCurrCocReq = false; 
				boolean blnSpecCurrCoaReq = false; 
				String specCurrentCocRequirement = specBean.getCurrentCocRequirementStr(); //specData.get( "SPECCERTCOCREQ" ).toString().trim();
				String specCurrentCoaRequirement = specBean.getCurrentCoaRequirementStr(); //specData.get( "SPECCERTCOAREQ" ).toString().trim();
				if (specCurrentCocRequirement != null && specCurrentCocRequirement.split(Pattern.quote("|")).length > 0 )
					specCurrentCocRequirement = specCurrentCocRequirement.split(Pattern.quote("|"))[1];
				else 
					specCurrentCocRequirement = specBean.getCurrentCocRequirementStr();
				if (specCurrentCoaRequirement != null && specCurrentCoaRequirement.split(Pattern.quote("|")).length > 0 )
					specCurrentCoaRequirement = specCurrentCoaRequirement.split(Pattern.quote("|"))[1];
				else 
					specCurrentCoaRequirement = specBean.getCurrentCoaRequirementStr();
				
				blnSpecCurrCocReq = new Boolean(specCurrentCocRequirement).booleanValue();
				blnSpecCurrCoaReq = new Boolean(specCurrentCoaRequirement).booleanValue();
				String currCocReqFlag = blnSpecCurrCocReq?"Y":"N";
				String currCoaReqFlag = blnSpecCurrCoaReq?"Y":"N";
				
				
				String specColor= specBean.getColor().toString(); //specData.get( "SPECCOLOR" ).toString().trim();

				if ("1".equalsIgnoreCase(specColor) || "2".equalsIgnoreCase(specColor)
						|| blnSpecCoc || blnSpecCoa) {
					if (blnSpecCoc) {
						cocFlag = "Y";
					}
					if (blnSpecCoa) {
						coaFlag = "Y";
					}

					/*Checking to see if for some reason this spec is already in the database with the same ammendment.
					Doing this to avoid confirmation error and manual fixinf of the POs. - Nawaz 11-08-08*/
					String count = "";
					int specExistsCount=0;
	
			 	  	StringBuffer countQuery = new StringBuffer(); 	  
					countQuery.append("select count(*) from po_line_spec_all where radian_po = ").append(radianPo.intValue());
					countQuery.append(" and PO_LINE = ").append(rowNumber);
					countQuery.append(" and AMENDMENT = ").append(amendmentNumber);
					countQuery.append(" and SPEC_ID = ").append(SqlHandler.delimitString(specBean.getSpecId()));
					countQuery.append(" and SPEC_LIBRARY = ").append(SqlHandler.delimitString(specBean.getSpecLibrary()));
					countQuery.append(" and DETAIL = ").append(SqlHandler.delimitString(specBean.getDetail()));
	
					count = factory.selectSingleValue(countQuery.toString());
				 	if(count == null || count.equalsIgnoreCase(""))
				 		specExistsCount = 0;
				 	else 
				 		specExistsCount = Integer.parseInt(count);
			 	
					if ( specExistsCount == 0)
					{
						StringBuffer insertSpecs = new StringBuffer();
						insertSpecs.append("insert into po_line_spec_draft (SPEC_ID,SPEC_LIBRARY,RADIAN_PO,PO_LINE,COC,COA,AMENDMENT,DETAIL,COA_REQ,COC_REQ,COLOR) values \n");
						insertSpecs.append("(").append(SqlHandler.delimitString(specId)).append( "," ).append(SqlHandler.delimitString(specLibrary)).append( "," ).append( radianPo ).append( "," ).append( rowNumber ).append( "," );
						insertSpecs.append(SqlHandler.delimitString(cocFlag)).append( "," ).append(SqlHandler.delimitString(coaFlag)).append( "," ).append( amendmentNumber ).append( ",").append(SqlHandler.delimitString(specDetail)).append(",");
						insertSpecs.append(SqlHandler.delimitString(currCoaReqFlag)).append(",").append(SqlHandler.delimitString(currCocReqFlag)).append(",").append(specColor).append(") \n");
						log.info(insertSpecs.toString());
				        try {
				        	factory.deleteInsertUpdate(insertSpecs.toString(), conn);
				        }
				        catch (Exception ex) {
				        	result = false;
				        	log.error(ex.getMessage());
				        }
					}
				}
        	}			

		} catch (Exception ex) {
			result = false;
			log.error(ex.getMessage());
		}		
		return result;
	}

	public HashMap updatePoHeader(PoHeaderViewBean poHeader, BigDecimal personnelId)  throws Exception {
		return updatePoHeader(poHeader, personnelId, null);
	}
	
	private HashMap updatePoHeader(PoHeaderViewBean poHeader, BigDecimal personnelId, Connection conn )  throws Exception
	{
		boolean result = false;
		DbManager dbManager = new DbManager(getClient());		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		boolean newConnection = false;
		HashMap resultOutcome = new HashMap();

		try {
			if (StringHandler.isBlankString(poHeader.getSupplier())) {
				resultOutcome.put(HEADER_RESULT, false);
		        resultOutcome.put(ERROR, library.getString("errors.selecta").replace("{0}", library.getString("label.supplier")));
		        return resultOutcome;
			}
			
			if (conn == null) {	
				newConnection = true;
				conn = dbManager.getConnection();
			}
			String customerpo = poHeader.getCustomerPo(); //DataH.get( "CUSTOMERPO" ).toString().trim();
			String carrierId = poHeader.getCarrier(); //DataH.get( "CARRIERID" ).toString().trim();
			String bpo = "";//DataH.get( "BPO" ).toString().trim();
			String hub = poHeader.getHubName();//DataH.get( "HUB" ).toString().trim();
			String supplierId = poHeader.getSupplier();//DataH.get( "SUPPLIERID" ).toString().trim();
			String shipToCompanyid = poHeader.getShipToCompanyId();//DataH.get( "SHIPTOCOMPANYID" ).toString().trim();
			String shipToFacilityId = poHeader.getShipToFacilityId();//DataH.get( "SHIPTOFACILITYID" ).toString().trim();
			String paymentTerms = poHeader.getPaymentTerms();//DataH.get( "PAYMENTTERMS" ).toString().trim();			
			String po = "";
			po = poHeader.getRadianPo().toString();//DataH.get( "PO" ).toString().trim();
			//String orderTakerId = poHeader.getSupplierContactId().toString(); //DataH.get( "ORDERTAKERID" ).toString().trim();
			String fob = poHeader.getFreightOnBoard(); //DataH.get( "FOB" ).toString().trim();
			if (fob != null && fob.equalsIgnoreCase("None")) {
				fob = null;
			}
			String shipToId = poHeader.getShipToLocationId();//DataH.get( "SHIPTO" ).toString().trim();
			if ( shipToId != null && shipToId.length() > 0 ) {
				shipToId = StringHandler.replace( shipToId,"%26","&" );
				shipToId = StringHandler.replace( shipToId,"%23","#" );
				shipToId = StringHandler.replace( shipToId,"%2F","\\" );
		    }
			//log.debug("shipToId - " + shipToId);
			//String faxDate = poHeader.getDateSent().toString(); //DataH.get( "FAXDATE" ).toString().trim();
			//String acceptedDate = poHeader.getDateAccepted().toString();//DataH.get( "ACCEPTEDDATE" ).toString().trim();
			//String buyerId = poHeader.getBuyerId().toString(); //DataH.get( "BUYERID" ).toString().trim();
			String criticalPo = poHeader.getCritical(); //DataH.get( "CRITICALPO" ).toString().trim();
			String suppRating = poHeader.getQualificationLevel(); //DataH.get( "SUPPRATING" ).toString().trim();
			String invenGrp = poHeader.getInventoryGroup(); //DataH.get( "INVENTORY_GROUP" ).toString().trim();
			String cosignedPo = poHeader.getConsignedPo(); //DataH.get( "CONSIGNED_PO" ).toString().trim();
				
			Vector inArgs = new Vector();
			BigDecimal bigponumber = new BigDecimal(po);
			inArgs.add(bigponumber);
			//BigDecimal bigbponumber = new BigDecimal(bpo);
			inArgs.add(null);
			inArgs.add(supplierId);
			inArgs.add(poHeader.getSupplierContactId());
			inArgs.add(poHeader.getBuyer());
			inArgs.add(hub);
			inArgs.add(shipToCompanyid);
			inArgs.add(shipToId);
			inArgs.add(fob);
			inArgs.add(paymentTerms);
			inArgs.add(carrierId);
			inArgs.add(poHeader.getDateSent());
			inArgs.add(poHeader.getDateAccepted());
			inArgs.add(null);
			inArgs.add(personnelId);
			inArgs.add(customerpo);
			inArgs.add(criticalPo);
			inArgs.add(invenGrp );
			if ( "y".equalsIgnoreCase(cosignedPo) ) {
				inArgs.add("y" );
			} else {
				inArgs.add("n" );
			}
			
			Vector outArgs = new Vector();			
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            
            Vector optInArgs = new Vector();	
			optInArgs.add(suppRating );
			optInArgs.add(shipToFacilityId );
			
            //invoke procedure to compare the excel data
			Vector procErrorMessage = (Vector)factory.doProcedure(conn, "pkg_po.PO_HEADER", inArgs, outArgs, optInArgs);
			String errorCode = "";

			if(procErrorMessage.size() > 0 && procErrorMessage.get(0) != null) {
				result = false;
				errorCode = (String) procErrorMessage.get(0);
				log.info( "Error from pkg_po.PO_HEADER " + errorCode + "  supplierid  " + supplierId + "  customerpo  " + customerpo );
			} else {
            	result = true;                
            }
	        log.debug("po header " + poHeader.getRadianPo() + " and result = " + result );
	        conn.commit();
	        resultOutcome.put(HEADER_RESULT, result);
	        resultOutcome.put(ERROR, errorCode);
		}
		catch ( Exception e ) {
			resultOutcome.put(HEADER_RESULT, false);
	        resultOutcome.put(ERROR, e.getMessage());
			log.error(e.getMessage());
		}
		finally {
			if (newConnection)
				dbManager.returnConnection(conn);
		}
		
		return resultOutcome;
	  }

	public boolean resendWBuyPo(PoHeaderViewBean poHeader) throws Exception {
		boolean success = false;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();

		try
		{
			Vector inArgs = new Vector();			
			inArgs.add(poHeader.getRadianPo());			
			
			factory.doProcedure(conn,  "PKG_PO.P_RESEND_WBUY_PO", inArgs );
			log.debug("PKG_PO.P_RESEND_WBUY_PO for Po - " + poHeader.getRadianPo());
			success = true;
		}
		catch ( Exception e )
		{
			success = false;
			log.error("Error Calling PKG_PO.P_RESEND_WBUY_PO PO Page; Error PKG_PO.P_RESEND_WBUY_PO\nError Msg:\n" + e.getMessage() + "\n  radian PO  " + poHeader.getRadianPo());
		}
		finally
		{
			dbManager.returnConnection(conn);
		} 
		return success;		
	}	
	
	public boolean isReadOnly(BigDecimal radianPo) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoHeaderViewBean());
		try {
			StringBuilder query = new StringBuilder();
			query.append("select PAYMENT_TERMS,OPS_ENTITY_ID,TRANSPORT,BRANCH_PLANT,RADIAN_PO,DBUY_LOCK_STATUS from po_header_view ");
			query.append(" where RADIAN_PO = '").append(radianPo).append("' ");
			
			Collection<PoHeaderViewBean> beanCol = factory.selectQuery(query.toString());
			for (PoHeaderViewBean bean : beanCol) {
				if ("Y".equalsIgnoreCase(bean.getDbuyLockStatus()))
					return true;
			}
		} catch (Exception e) {
			log.error("Error in getDBuyLockStatus - " + e.getMessage());
		}
		  
		return false;
	}

	public BigDecimal generateNewPo() throws Exception {
		BigDecimal radianPo = new BigDecimal("0");
		String bquery = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();
		try {
			bquery = "select po_seq.nextval RADIANPO from dual";
			String value = factory.selectSingleValue(bquery, conn);
			radianPo = new BigDecimal(value);	
		} catch (Exception e) {
			log.error("Exception occured while generating a new PO" + e.getMessage());
		} finally {
			dbManager.returnConnection(conn);
		}
		
		return radianPo;  
	}
	
	public String approveFinancialApprovalPendingPo(BigDecimal radianPo, BigDecimal personnelId ) throws Exception {
		String result = library.getString("label.approved");
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();
		try
		{
			Vector inArgs = new Vector();			
			inArgs.add(radianPo);
			inArgs.add(personnelId);
			
			factory.doProcedure(conn,  "PKG_FINANCIAL_APPROVAL_PO.p_approve_fa_pending_po", inArgs );
			log.debug("PKG_FINANCIAL_APPROVAL_PO.p_approve_fa_pending_po for Po - " + radianPo);
			
			String query = "select pkg_financial_approval_po.fx_get_po_approval_status(" + radianPo + ") from dual";
			
			String status = factory.selectSingleValue(query, conn);
			if ( ! status.equals("Approved")) {
				result = library.getString("msg.approvalrequired");
			}
		}
		catch ( Exception e )
		{
			log.error("Error Calling PKG_FINANCIAL_APPROVAL_PO.p_approve_fa_pending_po PO Page; \nError Msg:\n" + e.getMessage() + "\n  radian PO  " + radianPo);
			result =  "Error";		
		}
		finally
		{
			dbManager.returnConnection(conn);
		}
		return result;
	}
	
	public BigDecimal recalTotWithConversion(BigDecimal lineAmt, String currencyId, String thePO) throws Exception
	{	
		BigDecimal convertedAmt = new BigDecimal(0);
		if(!"USD".equalsIgnoreCase(currencyId))
		{	  
			BigDecimal conversionRate  = getConverionRate(currencyId,thePO);
			if(conversionRate != null)
				convertedAmt = conversionRate.multiply(lineAmt);
		} else {
			convertedAmt = lineAmt;
		}
		return convertedAmt;
	}
	
	public BigDecimal getConverionRate(String currentCurrency, String thePO) throws Exception
	{
		BigDecimal conversionRateBDec  = null;
		Date datePOCreated  = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		try
      	{      		
      		StringBuilder query = new StringBuilder("select DATE_CREATED from po where radian_po = ").append(thePO);
      		datePOCreated = factory.selectSingleDateValue(query.toString(), conn);
      		 
      		query = new StringBuilder("select nvl(fx_conversion_rate(").append(SqlHandler.delimitString(currentCurrency));
      		query.append(",'USD',to_date('").append(sdf.format(datePOCreated)).append("', 'MM/DD/RRRR')").append("),0) CONVERSION from dual");
      		String conversionRate = factory.selectSingleValue(query.toString(), conn);
      		if (conversionRate != null ) {
      			conversionRateBDec = new BigDecimal(conversionRate);	
      		}
      		   		
      		if(conversionRateBDec == null || conversionRateBDec.equals(BigDecimal.ZERO))
      		{	 
          		query = new StringBuilder("select nvl(fx_conversion_rate(").append(SqlHandler.delimitString(currentCurrency));
          		query.append(",'USD',sysdate").append("),1) CONVERSION from dual");
          		conversionRate = factory.selectSingleValue(query.toString(), conn);
          		if (conversionRate != null ) {
          			conversionRateBDec = new BigDecimal(conversionRate);	
          		}
          	}
      	}
      	catch ( Exception e )
      	{
      		//e.printStackTrace();
      	}
	    finally
	    {
	    	dbManager.returnConnection(conn);
	    }
  		return conversionRateBDec;
	}

	public String getGhsCompliant(BigDecimal itemId) throws Exception {
		String ghsComplaint = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();
		try {
			String query = " select fx_item_ghs_compliant("+itemId+") GHS_COMPLIANT from dual";
			ghsComplaint = factory.selectSingleValue(query, conn);
		}
		catch (Exception e) {
			ghsComplaint = "";
		}
		finally
	    {
	    	dbManager.returnConnection(conn);
	    }
		
		return ghsComplaint;
	}
	
	//check if the PO is already in table
	public boolean isSpecialCharge(BigDecimal radianPo) {
		if (radianPo != null) {
			StringBuilder query = new StringBuilder("select count(*) from special_charge_po_lookup");
			query.append(" where radian_po = ").append(radianPo.toPlainString());
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
			String count = "";
			try {
				count = factory.selectSingleValue(query.toString());
			} catch (BaseException e) {
				log.error(e.getMessage(), e);
				return false;
			}
			if (!"0".equalsIgnoreCase(count))
				return true;
		}
		
		return false;
	}
	
	//add given PO to special_charge_po_lookup table and return an error message (if any)
	public String addPoToSpecialCharge(PoHeaderViewBean poHeader, BigDecimal personnelId) {
		String emptyElements = "";
		if (poHeader.getRadianPo() == null)
			emptyElements += library.getString("label.po");
		if (StringHandler.isBlankString(poHeader.getSupplier())) {
			if (!StringHandler.isBlankString(emptyElements))
				emptyElements += ", ";
			emptyElements += library.getString("label.supplier");
		}
		if (!StringHandler.isBlankString(emptyElements))
			return library.getLabel("label.validvalues") + emptyElements;
		
		StringBuilder query = new StringBuilder("insert into special_charge_po_lookup (radian_po, supplier, inserted_by)");
		query.append("values (").append(poHeader.getRadianPo());
		query.append(", ").append(SqlHandler.delimitString(poHeader.getSupplier()));
		query.append(", ").append(personnelId).append(")");
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try {
			factory.deleteInsertUpdate(query.toString());
		} catch (BaseException e) {
			log.error(e.getMessage(), e);
			return library.getString("error.db.update");
		}
		
		return "";
	}
	
	public String getPoType(BigDecimal radianPo) {
		String poType = "Purchaser";
		
		if (radianPo != null) {
			StringBuilder query = new StringBuilder("select min(supply_path) from buy_order");
			query.append(" where radian_po = ").append(radianPo);
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
			try {
				String temp = factory.selectSingleValue(query.toString());
				if (!StringHandler.isBlankString(temp))
					poType = temp;
			} catch (BaseException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return poType;
	}
	
	public BigDecimal getPoLineTotal(String currencyTo, String list) {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = null;
		try {
			connection = dbManager.getConnection();
			return getPoLineTotal(currencyTo, list, connection);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				dbManager.returnConnection(connection);
			} catch (DbReturnConnectionException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public BigDecimal getPoLineTotal(String currencyTo, String list, Connection connection) {
		log.info("Input string: " + list);
		BigDecimal result = new BigDecimal(0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		HashMap<String, String> conversionRates = new HashMap<String, String>();
		try {
			String[] linesData = list.split(";");
			for (String lineData : linesData) {
				String[] splittedStr = lineData.split(",");
				if (splittedStr.length < 2) {
					log.error("Incorrect format: " + lineData + ". Expecting '${lineTotal}, ${lineCurrencyId}, ${lineDateToUse}'.");
					return null;
				}
				String curLineTotal = splittedStr[0];
				String curLineCurrency = splittedStr[1];
				String curLineDateToUse = splittedStr.length == 3 ? splittedStr[2] : "";
				
				String curConversionRate = "";
				if (conversionRates.containsKey(curLineCurrency + curLineDateToUse)) {
					curConversionRate = conversionRates.get(curLineCurrency + curLineDateToUse);
				} else if (curLineCurrency.equalsIgnoreCase(currencyTo)) {
					curConversionRate = "1";
					conversionRates.put(curLineCurrency + curLineDateToUse, curConversionRate);
				} else {
					//the date is stored in English, so needs to use en_US Locale during conversion
					String dateStrToUse = StringHandler.isBlankString(curLineDateToUse) ? "sysdate" : "to_date(" + SqlHandler.delimitString(sdf.format(DateHandler.getDateFromString(curLineDateToUse, defaultLocale))) + ", 'MM/DD/RRRR')";
					StringBuilder query = new StringBuilder("select nvl(fx_conversion_rate(");
					query.append(SqlHandler.delimitString(curLineCurrency));
					query.append(",").append(SqlHandler.delimitString(currencyTo));
					query.append(",").append(dateStrToUse).append("),0) CONVERSION from dual");
					GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
					curConversionRate = factory.selectSingleValue(query.toString(), connection);
					if ("0".equalsIgnoreCase(curConversionRate)) {
						log.error("Can't get conversion rate from " + curLineCurrency + " to " + currencyTo + " for date " + dateStrToUse);
						return null;
					}
					conversionRates.put(curLineCurrency + curLineDateToUse, curConversionRate);
				}
				result = result.add(new BigDecimal(curLineTotal).multiply(new BigDecimal(curConversionRate)));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
		return result;
	}
	
	public String getOpsEntityHomeCurrency(String opsEntity) {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = null;
		try {
			connection = dbManager.getConnection();
			return getOpsEntityHomeCurrency(opsEntity, connection);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "";
		} finally {
			try {
				dbManager.returnConnection(connection);
			} catch (DbReturnConnectionException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public String getOpsEntityHomeCurrency(String opsEntity, Connection connection) {
		String currencyId = "";
		
		if (!StringHandler.isBlankString(opsEntity)) {
			StringBuilder query = new StringBuilder("select home_currency_id from operating_entity");
			query.append(" where ops_entity_id = ").append(SqlHandler.delimitString(opsEntity));
			GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
			try {
				currencyId = factory.selectSingleValue(query.toString(), connection);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return currencyId;
	}
	
}
