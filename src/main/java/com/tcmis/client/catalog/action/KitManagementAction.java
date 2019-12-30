package com.tcmis.client.catalog.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import com.tcmis.client.catalog.beans.KitManagementBean;
import com.tcmis.client.catalog.process.KitManagementProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;


public class KitManagementAction extends TcmISBaseAction {
	private void doSearch(KitManagementProcess process, KitManagementBean input, PersonnelBean user) throws BaseException {
		Collection<KitManagementBean> results = process.getSearchResult(input, user);
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
		request.setAttribute("searchInput", input);
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String next = "success";

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "updatemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {

			// If you need access to who is logged in
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			/* Need to check if the user has permissions to view this page. */
			//if (user.getPermissionBean().hasUserPagePermission("openPos")) 
			{
				// copy date from dyna form to the input bean
				KitManagementBean input = new KitManagementBean(form, getTcmISLocale());
				KitManagementProcess process = new KitManagementProcess(getDbUser(), getTcmISLocale());
				// Search
				if (input.isSearch()) {
					// Pass the result collection in request
					doSearch(process, input, user);
				}
				//  Update
				else if (input.isUpdate()) {

					/*Need to check if the user has permissions to update this data. if they do not have the permission
					we show a error message.*/
					if (!user.getPermissionBean().hasFacilityPermission("KitManagement", input.getFacilityId(), user.getCompanyId())) {
						request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
						// repopulate the search results
						doSearch(process, input, user);
					}
					else {

						// If the page is being updated I check for a valid token.
						//checkToken will aslo save token for you to avoid duplicate form submissions.
						checkToken(request);

						// get the data from grid, selecting ONLY those beans whose okDoUpdate box has been checked
						Collection<KitManagementBean> beans = BeanHandler.getBeans((DynaBean) form, "kitManagementBean", new KitManagementBean(), getTcmISLocale(), "okDoUpdate");

						// Do the update and set any errors into the request
						request.setAttribute("tcmISErrors", process.update(beans, user));

						// After update the data, we do the re-search to refresh the window
						doSearch(process, input, user);
					}

				}
				else if (input.isCreateExcel()) {
					setExcel(response, "KitManagementExcel");
					process.getExcelReport(process.getSearchResult(input, user), getTcmISLocale()).write(response.getOutputStream());
					return noForward;
				}
				/*else if("mfgSearch".equalsIgnoreCase(input.getuAction()))
				{
			        PrintWriter out = response.getWriter();
			        // status needs to be in the third position or just id:name with no statue
			        // If we need to add different values, please leave the third one empty like id:name::something else 
			        Vector<KitManagementBean> mfgResults =  (Vector<KitManagementBean>)process.getMfg(input);
			    	for(KitManagementBean bean: mfgResults)
			    		out.println(bean.getMixtureMfg());   		
					out.close();
					return noForward;	
				}*/
				else if("getRevHist".equalsIgnoreCase(input.getuAction()))
				{
			        Vector<KitManagementBean> revHistResults =  (Vector<KitManagementBean>)process.getKitRevs(input);
					JSONObject results = new JSONObject();
					JSONArray resultsArray = new JSONArray();
					SimpleDateFormat reportFormat = new SimpleDateFormat("MM/dd/yyyy");
					for (KitManagementBean bean : revHistResults) {
						JSONObject docJSON = new JSONObject();
						docJSON.put("mixtureRevisionDateDisplay", bean.getMixtureRevisionDateDisplay());
						docJSON.put("mixtureRevisionDate", reportFormat.format(bean.getMixtureRevisionDate()));
						docJSON.put("dataSource", bean.getDataSource());
						resultsArray.put(docJSON);
					}
					results.put("mixtureRevisionDateResults", resultsArray);
					// Write out the data
					PrintWriter out = response.getWriter();
					out.write(results.toString(3));
					out.close();
					return noForward;
				}
				else if("revHist".equalsIgnoreCase(input.getuAction()))
				{
			        Vector<KitManagementBean> revHistResults =  (Vector<KitManagementBean>)process.getRevHist(input);
					request.setAttribute("searchResults", revHistResults);
					// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
					request.setAttribute("searchInput", input);
					next = "revHist";	
				}
				else if("editHist".equalsIgnoreCase(input.getuAction()))
				{
			        Vector<KitManagementBean> revHistResults =  (Vector<KitManagementBean>)process.getEditHist(input);
					request.setAttribute("searchResults", revHistResults);
					// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
					request.setAttribute("searchInput", input);
					next = "editHist";	
				}
				else{
					OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		            request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(user.getPersonnelIdBigDecimal()));
					if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
						user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(),user.getCompanyId()));
					} 
				}
			}
			/*else {
				next = "nopermissions";
			}*/
		}
		return (mapping.findForward(next));
	}
}

