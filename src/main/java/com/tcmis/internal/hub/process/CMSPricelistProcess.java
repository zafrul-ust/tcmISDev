package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.client.catalog.beans.CatalogBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.CMSPricelistBean;
import com.tcmis.internal.hub.beans.CMSPricelistInputBean;
import com.tcmis.internal.hub.beans.CatalogPartItemGroupBean;
import com.tcmis.internal.hub.beans.UserPriceGroupBean;

public class CMSPricelistProcess extends GenericProcess {
	private Log log = LogFactory.getLog(this.getClass());

	public CMSPricelistProcess(String client) {
		super(client);
	}

	public CMSPricelistProcess(String client, Locale locale) {
		super(client, locale);
	}

	private CatalogPartItemGroupBean getCPIG(CMSPricelistInputBean searchTerms, CMSPricelistBean currentPrice) throws BaseException {
		String sql = "SELECT * FROM catalog_part_item_group" + " WHERE catalog_id = " + SqlHandler.delimitString(searchTerms.getPriceGroupId()) + " AND company_id = "
				+ SqlHandler.delimitString(searchTerms.getCompanyId()) + " AND cat_part_no = " + SqlHandler.delimitString(currentPrice.getCatPartNo());

		factory.setBean(new CatalogPartItemGroupBean());
		Collection<CatalogPartItemGroupBean> results = factory.selectQuery(sql);
		if (results != null && !results.isEmpty()) {
			return results.iterator().next();
		}
		return null;
	}

	private CMSPricelistBean getCurrentPrice(CMSPricelistInputBean searchTerms, CMSPricelistBean currentPrice) throws BaseException {
		String sql = "SELECT cpp.* FROM catalog_part_pricing cpp";
		sql += " WHERE cpp.price_group_id = " + SqlHandler.delimitString(searchTerms.getPriceGroupId());
		sql += " AND cpp.company_id = " + SqlHandler.delimitString(searchTerms.getCompanyId());
		sql += " AND cpp.cat_part_no = " + SqlHandler.delimitString(currentPrice.getCatPartNo());
		sql += " AND cpp.end_date >= " + DateHandler.getOracleToDateFunction(currentPrice.getStartDate());

		factory.setBean(new CMSPricelistBean());
		Collection<CMSPricelistBean> results = factory.selectQuery(sql);
		if (results != null && !results.isEmpty()) {
			return results.iterator().next();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection<CatalogBean> getCatalogs(CMSPricelistInputBean searchTerms) throws BaseException {
		String sql = "SELECT * FROM catalog WHERE company_id = " + SqlHandler.delimitString(searchTerms.getCompanyId());
		factory.setBean(new CatalogBean());
		return factory.selectQuery(sql);
	}

	@SuppressWarnings("unchecked")
	public Collection<CMSPricelistBean> getPartSearchResult(CMSPricelistInputBean input) throws BaseException {
		String sql = "SELECT cpig.catalog_id, cpig.cat_part_no, '' || cpig.item_id AS item_id, i.item_desc, cpig.part_group_no";
		sql += " FROM catalog_part_item_group cpig, item i";
		sql += " WHERE i.item_id = cpig.item_id";
		sql += " AND cpig.company_id = " + SqlHandler.delimitString(input.getCompanyId());
		sql += " AND cpig.catalog_id = " + SqlHandler.delimitString(input.getCatalogId());
		sql += " AND cpig.status = 'A'";

		if (input.hasSearchArgument()) {
			String search = SqlHandler.delimitString("%" + input.getSearchArgument() + "%");
			sql += " AND (cpig.cat_part_no LIKE " + search;
			if (StringUtils.isNumeric(input.getSearchArgument())) {
				sql += " OR i.item_id LIKE  " + search;
			}
			sql += " OR i.item_desc LIKE " + search + ")";
		}

		sql += " ORDER BY cat_part_no, item_id";

		factory.setBean(new CMSPricelistBean());
		return factory.selectQuery(sql);
	}

	@SuppressWarnings("unchecked")
	public Collection<CMSPricelistBean> getSearchResult(CMSPricelistInputBean input) throws BaseException {
		String sql = "SELECT CUSTOMER.fx_cat_part_no_item_list (catalog_id, cat_part_no, part_group_no, 'N', ',', company_id) AS item_id,";
		sql += " CUSTOMER.FX_CAT_PART_DESC (company_id, catalog_id, cat_part_no) AS item_desc,";
		sql += " cpp.*, FX_PERSONNEL_ID_TO_NAME(cpp.last_updated_by) AS last_updated_name, FX_PERSONNEL_ID_TO_NAME(cpp.inserted_by) AS inserted_name";
		sql += " FROM catalog_part_pricing cpp";
		sql += " WHERE cpp.price_group_id = " + SqlHandler.delimitString(input.getPriceGroupId());
		sql += " AND cpp.company_id = " + SqlHandler.delimitString(input.getCompanyId());
		if (!input.isShowExpired()) {
			sql += " AND cpp.end_date >= SYSDATE";
		}

		if (input.hasSearchArgument()) {
			sql += " AND " + input.getSearchSQL();
		}

		sql += " ORDER BY cat_part_no ASC, item_id ASC, start_date DESC";

		factory.setBean(new CMSPricelistBean());
		return factory.selectQuery(sql);
	}

	@SuppressWarnings("unchecked")
	public Collection<UserPriceGroupBean> getUserPriceGroups(PersonnelBean user) throws BaseException {
		factory.setBean(new UserPriceGroupBean());
		String sql = "SELECT pg.company_id, pg.price_group_id, upg.price_group_editable, NVL (c.company_short_name, c.company_name) AS company_name, pg.price_group_name, pg.currency_id";
		sql += " FROM customer.user_price_group upg, company c, price_group pg";
		sql += " WHERE c.company_id = upg.company_id";
		sql += " AND pg.price_group_id = upg.price_group_id";
		sql += " AND upg.company_id = pg.company_id";
		sql += " AND upg.personnel_id = " + user.getPersonnelId();
		sql += " UNION";
		sql += " SELECT pg.company_id, pg.price_group_id, upg.price_group_editable, NVL (c.company_short_name, c.company_name) AS company_name, pg.price_group_name, pg.currency_id";
		sql += " FROM customer.user_price_group upg, company c, price_group pg";
		sql += " WHERE c.company_id = upg.company_id";
		sql += " AND upg.company_id = pg.company_id";
		sql += " AND upg.price_group_id = 'All'";
		sql += " AND upg.personnel_id = " + user.getPersonnelId();
		sql += " AND not exists (select null from customer.user_price_group upg2 WHERE upg2.company_id = pg.company_id AND upg2.personnel_id = upg.personnel_id AND upg2.price_group_id = pg.price_group_id)";
		return factory.selectQuery(sql);
	}

	private void insertPrice(CMSPricelistBean inputPrice, PersonnelBean user) throws BaseException {
		String sql = "INSERT INTO customer.catalog_part_pricing_orig ";
		sql += " (company_id, catalog_id, cat_part_no, part_group_no, price_group_id, start_date, end_date,";
		sql += " loading_comments, catalog_price, currency_id, inserted_by, insert_date, last_updated_by, last_updated)";
		sql += " VALUES (";
		sql += SqlHandler.delimitString(inputPrice.getCompanyId()) + ", ";
		sql += SqlHandler.delimitString(inputPrice.getCatalogId()) + ", ";
		sql += SqlHandler.delimitString(inputPrice.getCatPartNo()) + ", ";
		sql += inputPrice.getPartGroupNo() + ", ";
		sql += SqlHandler.delimitString(inputPrice.getPriceGroupId()) + ", ";
		sql += "trunc(sysdate), ";
		sql += DateHandler.getOracleToDateFunction(inputPrice.getEndDate()) + ", ";
		sql += SqlHandler.delimitString(inputPrice.getLoadingComments()) + ", ";
		sql += inputPrice.getCatalogPrice() + ", ";
		sql += SqlHandler.delimitString(inputPrice.getCurrencyId()) + ", ";
		sql += user.getPersonnelId() + ", ";
		sql += "sysdate, ";
		sql += user.getPersonnelId() + ", ";
		sql += "sysdate";
		sql += ")";
		factory.deleteInsertUpdate(sql);
	}

	public Collection<String> updatePrices(CMSPricelistInputBean searchTerms, Collection<CMSPricelistBean> inputPrices, PersonnelBean user) throws BaseException {
		Vector<String> errorMessages = new Vector<String>();

		for (CMSPricelistBean inputPrice : inputPrices) {
			if (inputPrice.isUpdate()) {
				try {
					CMSPricelistBean insertPrice = new CMSPricelistBean();
					CMSPricelistBean oldPrice = getCurrentPrice(searchTerms, inputPrice);

					if (oldPrice != null) {
						updateExistingPrice(oldPrice, inputPrice, user);
						// Copy the itemId, etc. from the old price to the new
						// price
						insertPrice = oldPrice;
					}
					else {
						insertPrice.setCompanyId(searchTerms.getCompanyId());
						insertPrice.setCatPartNo(inputPrice.getCatPartNo());
						insertPrice.setCatalogId(inputPrice.getCatalogId());
						insertPrice.setPartGroupNo(inputPrice.getPartGroupNo());
						insertPrice.setItemId(inputPrice.getItemId());
					}

					if (!inputPrice.isUpdateEndDateOnly() && !inputPrice.isPriceFromToday()) {
						// if (insertPrice.hasLoadingComments() &&
						// insertPrice.getLoadingComments().equals(inputPrice.getLoadingComments()))
						// {
						// // Same comments as from previous, don't save them
						// // with the new price
						// insertPrice.setLoadingComments(null);
						// }
						// else {
						insertPrice.setLoadingComments(inputPrice.getLoadingComments());
						// }
						insertPrice.setPriceGroupId(searchTerms.getPriceGroupId());
						insertPrice.setCatalogPrice(inputPrice.getCatalogPrice());
						insertPrice.setCurrencyId(inputPrice.getCurrencyId());
						insertPrice.setStartDate(inputPrice.getStartDate());
						insertPrice.setOriginalStartDate(inputPrice.getOriginalStartDate());
						insertPrice.setEndDate(inputPrice.getEndDate());
						insertPrice(insertPrice, user);
					}
				}
				catch (Exception e) {
					String errorMsg = "Error updating Part " + inputPrice.getCatPartNo() + " : " + e.getMessage();
					errorMessages.add(errorMsg);
				}
			}
			else {
				// Otherwise they just updated the comment
				updateExistingPriceComment(searchTerms, inputPrice, user);
			}

		}

		return errorMessages;
	}

	private void updateExistingPriceComment(CMSPricelistInputBean searchTerms, CMSPricelistBean inputPrice, PersonnelBean user) throws BaseException {
		String sql = "UPDATE customer.catalog_part_pricing_orig";
		sql += " SET last_updated_by = " + user.getPersonnelId();
		sql += ", last_updated = SYSDATE";
		sql += ", loading_comments = " + SqlHandler.delimitString(inputPrice.getLoadingComments());
		sql += " WHERE price_group_id = " + SqlHandler.delimitString(searchTerms.getPriceGroupId());
		sql += " AND company_id = " + SqlHandler.delimitString(searchTerms.getCompanyId());
		sql += " AND catalog_id = " + SqlHandler.delimitString(inputPrice.getCatalogId());
		sql += " AND cat_part_no = " + SqlHandler.delimitString(inputPrice.getCatPartNo());
		sql += " AND end_date >= " + DateHandler.getOracleToDateFunction(inputPrice.getStartDate());
		factory.deleteInsertUpdate(sql);
	}

	private void updateExistingPrice(CMSPricelistBean oldPrice, CMSPricelistBean inputPrice, PersonnelBean user) throws BaseException {
		String sql = "UPDATE customer.catalog_part_pricing_orig";
		sql += " SET last_updated_by = " + user.getPersonnelId();
		sql += ", last_updated = SYSDATE";
		if (inputPrice.isUpdateEndDateOnly()) {
			sql += ", end_date = trunc(" + DateHandler.getOracleToDateFunction(inputPrice.getEndDate()) + ")";
			sql += ", loading_comments = " + SqlHandler.delimitString(inputPrice.getLoadingComments());
		}
		else if (inputPrice.isPriceFromToday()) { // Double edit of a price in
													// one day, don't need to
													// expire anything
			sql += ", catalog_price = " + inputPrice.getCatalogPrice();
			sql += ", currency_id = " + SqlHandler.delimitString(inputPrice.getCurrencyId());
		}
		else {
			// Set the old price to end yesterday
			sql += ", end_date = trunc(sysdate - 1)";
		}
		sql += " WHERE price_group_id = " + SqlHandler.delimitString(oldPrice.getPriceGroupId());
		sql += " AND company_id = " + SqlHandler.delimitString(oldPrice.getCompanyId());
		sql += " AND catalog_id = " + SqlHandler.delimitString(oldPrice.getCatalogId());
		sql += " AND cat_part_no = " + SqlHandler.delimitString(oldPrice.getCatPartNo());
		sql += " AND end_date >= " + DateHandler.getOracleToDateFunction(inputPrice.getStartDate());
		factory.deleteInsertUpdate(sql);
	}

	public JSONObject validateCatPartNos(CMSPricelistInputBean searchTerms) throws JSONException {
		JSONObject results = new JSONObject();
		for (String catPartNo : searchTerms.getAllCatPartNos()) {
			String sql = "SELECT 'Y' FROM dual WHERE EXISTS (";
			sql += "SELECT null FROM catalog_part_item_group WHERE company_id = " + SqlHandler.delimitString(searchTerms.getCompanyId());
			sql += " AND catalog_id = " + SqlHandler.delimitString(searchTerms.getCatalogId());
			sql += " AND cat_part_no = " + SqlHandler.delimitString(catPartNo);
			sql += ")";
			try {
				String validation = factory.selectSingleValue(sql);
				results.put(catPartNo, "Y".equals(validation) ? true : false);
			}
			catch (BaseException e) {
				e.printStackTrace();
				results.put(catPartNo, false);
			}
		}
		return results;
	}
}