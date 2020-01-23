package com.tcmis.internal.react.actions.ordermanagement;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.react.actions.TcmisReactAction;
import com.tcmis.internal.react.beans.TokenReactBean;

public class CatalogPartDetailAction extends TcmisReactAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
	    String jsonString) throws BaseException {
	String requestMethod = request.getMethod();
	JSONObject responseBody = new JSONObject();
	boolean ok = false;
	int responseCode = HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED;
	String responseMsg = "Error";

	if (requestMethod.equalsIgnoreCase("POST")) {
	    try {
		TokenReactBean token = tokenVerify(request.getHeader("authorization"));
		if (token.isValid()) {
		    JSONObject requestBody = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString)
			    : new JSONObject();
		    PersonnelBean personnelBean = this.getSessionPersonnelBean(request);
		    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		    JSONArray searchResults = new JSONArray();

		    if (personnelBean.getPermissionBean().hasUserPagePermission("catalogReport")
			    && personnelBean.getPermissionBean().hasUserPagePermission("newcatalog")) {

			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),
				this.getTcmISLocaleString(request));
			CatalogInputBean bean = (CatalogInputBean) BeanHandler.getJsonBeans(requestBody,
				new CatalogInputBean());
			PrCatalogScreenSearchBean pbean = (PrCatalogScreenSearchBean) BeanHandler
				.getJsonBeans(requestBody, new PrCatalogScreenSearchBean());

			Collection specColl = catalogProcess.getSpecMenu(pbean);
			Object partInvColl = catalogProcess.getInventoryMenu(pbean);
			Object stockingReorder = catalogProcess.getStockingReorder(pbean);
			Object ImgLit = catalogProcess.getImgLit(pbean);
			Object kitMsdsNumber = catalogProcess.getKitMsdsNumber(pbean);
			Collection requestIdColl = catalogProcess.getRequestIdColl(pbean);

			responseBody.put("specColl", specColl);
			responseBody.put("partInvColl", partInvColl);
			responseBody.put("stockingReorder", stockingReorder);
			responseBody.put("ImgLit", ImgLit);
			responseBody.put("kitMsdsNumber", kitMsdsNumber);
			responseBody.put("requestIdColl", requestIdColl);

			boolean editApprovalCode = personnelBean.getPermissionBean().hasFacilityPermission(
				"EditUseCodeExpiration", bean.getFacilityId(), bean.getCompanyId());

			String showDirectedCharge = "N";
			if (!StringHandler.isBlankString(pbean.getApplicationId())
				&& !"My Work Areas".equals(pbean.getApplicationId())) {
			    showDirectedCharge = catalogProcess.showDirectedCharge(pbean);
			}
			request.setAttribute("showDirectedCharge", showDirectedCharge);
			ok = true;
			responseMsg = "Success!";

		    } else {
			responseMsg = "user has not permissions!";
		    }

		    responseBody.put("ok", ok);
		    responseBody.put("message", responseMsg);
		    responseCode = HttpServletResponse.SC_OK;
		}
	    } catch (JSONException e) {
		responseCode = HttpServletResponse.SC_BAD_REQUEST;
		responseMsg = "Request body is not valid JSON";
	    } catch (JWTCreationException e) {
		responseCode = HttpServletResponse.SC_FORBIDDEN;
		responseMsg = e.getMessage();
	    } catch (Exception e) {
		responseCode = HttpServletResponse.SC_BAD_REQUEST;
		responseMsg = e.getMessage();
	    }

	} else {
	    responseCode = HttpServletResponse.SC_BAD_REQUEST;
	    responseMsg = "Bad Request";
	}

	try {
	    if (responseCode == HttpServletResponse.SC_OK) {
		PrintWriter out = response.getWriter();
		out.write(responseBody.toString(2));
		out.close();
	    } else {
		response.sendError(responseCode, responseMsg);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return noForward;

    }

}
