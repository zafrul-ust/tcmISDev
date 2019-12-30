package com.tcmis.internal.hub.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CMSPricelistBean;
import com.tcmis.internal.hub.beans.CMSPricelistInputBean;
import com.tcmis.internal.hub.beans.UserPriceGroupBean;
import com.tcmis.internal.hub.process.CMSPricelistProcess;

/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class CMSPricelistAction extends TcmISBaseAction {
	private void doSearch(CMSPricelistProcess process, Collection<UserPriceGroupBean> priceGroups, CMSPricelistInputBean input, PersonnelBean user) throws BaseException {
		Collection<CMSPricelistBean> results = process.getSearchResult(input);
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// stick the input form back in to the request for use by the
		// tcmis:setHiddenFields tag, after grabbing the default currency and
		// catalog_id
		for (UserPriceGroupBean priceGroup : priceGroups) {
			if (priceGroup.getCompanyId().equals(input.getCompanyId()) && priceGroup.getPriceGroupId().equals(input.getPriceGroupId())) {
				input.setDefaultCurrencyId(priceGroup.getCurrencyId());
				request.setAttribute("canEditThisPricegroup", priceGroup.isPriceGroupEditable());
			}
		}
		if (!results.isEmpty()) {
			input.setCatalogId(results.iterator().next().getCatalogId());
		}
		else {
			input.setCatalogId(input.getPriceGroupId());
		}
		request.setAttribute("searchInput", input);
	}

	private boolean hasPricegroupEditPermission(String priceGroupId, Collection<UserPriceGroupBean> priceGroups) {
		for (UserPriceGroupBean priceGroup : priceGroups) {
			if (priceGroupId.equals(priceGroup.getPriceGroupId())) {
				return priceGroup.isPriceGroupEditable();
			}
		}
		return false;
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		String next = "showSearch";

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "CMSPricelist");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {
			CMSPricelistProcess process = new CMSPricelistProcess(getDbUser(), getTcmISLocale());
			// Grab the user
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			/* Need to check if the user has permissions to view this page. */
			if (user.getPermissionBean().hasUserPagePermission("CMSPricelist")) {
				// Get the users pricegroups
				Collection<UserPriceGroupBean> priceGroups = process.getUserPriceGroups(user);
				request.setAttribute("priceGroups", priceGroups);

				// copy data from dyna form to the input bean
				CMSPricelistInputBean input = new CMSPricelistInputBean(form, getTcmISLocale());

				if (input.isShowAddPart()) {
					Collection<CatalogBean> results = process.getCatalogs(input);
					request.setAttribute("companyCatalogs", results);
					next = "showAddPartSearch";
				}
				else if (input.isSearchAddPart()) {
					Collection<CMSPricelistBean> results = process.getPartSearchResult(input);
					input.setTotalLines(results.size());
					// Stick the results into the session
					request.setAttribute("searchResults", results);
					next = "showAddPartResults";
				}
				else if (input.isShowHistory()) {
					input.setShowExpired(true);
					input.setSearchMode("is");
					input.setSearchField("catPartNo");
					Collection<CMSPricelistBean> results = process.getSearchResult(input);
					input.setTotalLines(results.size());
					// Stick the results into the session
					request.setAttribute("searchResults", results);
					next = "showHistory";
				}
				else if (input.isUpdate()) {
					/*
					 * Need to check if the user has permissions to update this
					 * pricegroup. if they do not have the permission we show a
					 * error message.
					 */
					if (!hasPricegroupEditPermission(input.getPriceGroupId(), priceGroups)) {
						request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
					}
					else {

						// get the data from grid, selecting ONLY those
						// beans
						// whose okDoUpdate box has been checked
						Collection<CMSPricelistBean> updatedPrices = BeanHandler.getBeans((DynaBean) form, "CMSPricelistBean", new CMSPricelistBean(), getTcmISLocale(),
								"okDoUpdate");

						// Do the update and set any errors into the request
						request.setAttribute("tcmISErrors", process.updatePrices(input, updatedPrices, user));
					}
					// repopulate the search results
					doSearch(process, priceGroups, input, user);
					next = "showResults";
				}
				else if (input.isSearch()) {
					// populate the search results
					doSearch(process, priceGroups, input, user);
					next = "showResults";
				}
				else if (input.isGetCSV()) {
					try {
						String fileName = input.getCompanyId() + "-" + input.getPriceGroupId();
						if (input.hasSearchArgument()) {
							fileName += "-" + input.getSearchArgument();
						}
						fileName += ".csv";
						
						response.setContentType("text/csv");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						// Write out the data
						Collection<CMSPricelistBean> results = process.getSearchResult(input);
						PrintWriter out = response.getWriter();
						out.write("Part No, Description, Start Date, End Date, Currency ID, Catalog Price, Audit Note (REQUIRED)\n");
						for (CMSPricelistBean row : results) {
							out.write("\"" + row.getCatPartNo().replace("\"", "\"\"") + "\","); // CSV escape for quotes is to double them
							out.write("\"" + row.getItemDesc().replace("\"", "\"\"") + "\","); 
							out.write("\"" + dateFormat.format(row.getStartDate()) + "\",");
							out.write("\"" + dateFormat.format(row.getEndDate()) + "\",");
							out.write("\"" + row.getCurrencyId().replace("\"", "\"\"") + "\","); 
							out.write("\"" + row.getCatalogPrice() + "\","); 
							out.write("\"\"\n");
						}
						out.close();
						return noForward;
					}
					catch (Exception e) {
						log.error("Error producing CSV", e);
					}
				}
			}
			else {
				next = "nopermissions";
			}
		}
		return (mapping.findForward(next));
	}
}
