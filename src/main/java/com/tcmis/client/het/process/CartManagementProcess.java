package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.het.beans.CartManagementInputBean;
import com.tcmis.client.het.beans.FxItemSearchBean;
import com.tcmis.client.het.beans.HetCartBean;
import com.tcmis.client.het.beans.HetCartInputBean;
import com.tcmis.client.het.beans.HetCartItemBean;
import com.tcmis.client.het.beans.HetCartViewBean;
import com.tcmis.client.het.factory.HetCartBeanFactory;
import com.tcmis.client.het.factory.HetCartItemBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

public class CartManagementProcess extends GenericProcess {

	public CartManagementProcess(String dbUser, Locale tcmISLocale) {
		super(dbUser, tcmISLocale);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetCartViewBean> getSearchData(CartManagementInputBean input, PersonnelBean user) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, user.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		criteria.addCriterion("applicationId", SearchCriterion.EQUALS, input.getWorkArea() + "");

		if (input.hasSearchArgument()) {
			criteria.addCriterion(input.getSearchField(), input.getSearchCriterion(), input.getSearchArgument());
		}

		factory.setBeanObject(new HetCartViewBean());
		SortCriteria sort = new SortCriteria("cartName");
		sort.addCriterion("cartStatus");
		sort.addCriterion("sortOrder");
		sort.addCriterion("catPartNo");
		return factory.select(criteria, sort, "HET_CART_VIEW");
	}

	@SuppressWarnings("unchecked")
	public Collection<FxItemSearchBean> getCartItemSearchData(CartManagementInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FxItemSearchBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_item_search('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',").append(input.getWorkArea()).append(",");
		if (input.hasSearchArgument()) {
			query.append("'").append(input.getSearchField()).append("','").append(input.getSearchMode()).append("','").append(input.getSearchArgument()).append("'");
		}
		else {
			query.append("'','',''");
		}
		query.append(")) order by cat_part_no, item_id, kit_id, part_id");

		return factory.selectQuery(query.toString());
	}

	private HetCartBean createCartBean(CartManagementInputBean input, HetCartInputBean cart, PersonnelBean user) {
		HetCartBean cartBean = new HetCartBean();
		cartBean.setCompanyId(user.getCompanyId());
		cartBean.setApplicationId(input.getWorkArea());
		cartBean.setCartId(cart.getCartId());
		cartBean.setCartName(cart.getCartName());
		cartBean.setCartStatus(cart.getCartStatus());
		cartBean.setCheckedOut(cart.getCheckedOut());
		cartBean.setEmployee(cart.getEmployee());
		return cartBean;
	}

	private HetCartItemBean createCartItemBean(CartManagementInputBean input, HetCartInputBean cart, PersonnelBean user) {
		HetCartItemBean cartItemBean = new HetCartItemBean(user.getCompanyId(), cart.getCartId(), cart.getItemId(), cart.getContainerId());
		cartItemBean.setSortOrder(cart.getSortOrder());
		return cartItemBean;
	}

	public String updateCarts(CartManagementInputBean input, Collection<HetCartInputBean> carts, PersonnelBean user) throws BaseException {
		String errMsg = null;
		HetCartBeanFactory cartFactory = new HetCartBeanFactory(dbManager);
		HetCartItemBeanFactory cartItemFactory = new HetCartItemBeanFactory(dbManager);
		try {
			BigDecimal lastCartId = null;
			Vector<BigDecimal> handledCarts = new Vector<BigDecimal>();
			for (HetCartInputBean inputCart : carts) {
				if (inputCart.isNewCart()) {
					HetCartBean cart = createCartBean(input, inputCart, user);
					cartFactory.insert(cart);
					lastCartId = cart.getCartId();
				}
				else if (inputCart.isCartUpdated() && !handledCarts.contains(inputCart.getCartId())) {
					cartFactory.update(createCartBean(input, inputCart, user));
					handledCarts.add(inputCart.getCartId());
				}

				if (inputCart.getCartId() == null) {
					inputCart.setCartId(lastCartId);
				}
				else {
					lastCartId = inputCart.getCartId();
				}

				if (inputCart.isNewCartItem()) {
					cartItemFactory.insert(createCartItemBean(input, inputCart, user));
					//cartFactory.markLastLogged(user.getCompanyId(), inputCart.getCartId());
				}
				else if (inputCart.isCartItemDeleted()) {
					cartItemFactory.delete(createCartItemBean(input, inputCart, user));
					if (!cartItemFactory.cartHasItems(lastCartId)) {
						cartFactory.delete(createCartBean(input, inputCart, user));
					}
				}
				else if (inputCart.isSortOrderChanged()) {
					cartItemFactory.update(createCartItemBean(input, inputCart, user));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;

	}

}
