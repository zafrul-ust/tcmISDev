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
import com.tcmis.client.catalog.beans.QualitySummaryInputBean;
import com.tcmis.client.catalog.beans.QualitySummaryViewBean;
import com.tcmis.client.catalog.beans.UseApprovalDetailViewBean;
import com.tcmis.client.catalog.process.ApprovedWorkAreasProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.InventoryProcess;
import com.tcmis.client.catalog.process.QualitySummaryProcess;
import com.tcmis.client.report.process.ChartProcess;
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
			Object imgLit = catalogProcess.getImgLit(pbean);
			Object kitMsdsNumber = catalogProcess.getKitMsdsNumber(pbean);
			Collection requestIdColl = catalogProcess.getRequestIdColl(pbean);

			responseBody.put("specColl", specColl);
			responseBody.put("partInvColl", partInvColl);
			responseBody.put("stockingReorder", stockingReorder);
			responseBody.put("ImgLit", imgLit);
			responseBody.put("kitMsdsNumber", kitMsdsNumber);
			responseBody.put("requestIdColl", requestIdColl);

			String catPartNo = pbean.getCatPartNo();
			String inventoryGroup = pbean.getInventoryGroup();
			String catalogId = pbean.getCatalogId();
			BigDecimal partGroupNo = pbean.getPartGroupNo();
			String catalogCompanyId = pbean.getCatalogCompanyId();
			String facilityId = pbean.getFacilityId();

			/**
			 * lista de view Approbal = requestIdColl.requestId
			 */

			// Inventory

			InventoryProcess inventoryProcess = new InventoryProcess(this.getDbUser(request));
			Collection inventoryColl = inventoryProcess.getInventoryDetails(catPartNo, inventoryGroup,
				catalogId, partGroupNo.toString(), catalogCompanyId);
			responseBody.put("inventoryColl", inventoryColl);

			// End Inventory

			// Quality Summary

			QualitySummaryInputBean qbean = new QualitySummaryInputBean();
			qbean.setCatPartNo(catPartNo);
			qbean.setCatalogCompanyId(catalogCompanyId);
			qbean.setPartGroupNo(partGroupNo.toString());
			qbean.setCatalogId(catalogId);
			qbean.setFacilityId(facilityId);

			QualitySummaryProcess qualitySummaryProcess = new QualitySummaryProcess(
				this.getDbUser(request));
			QualitySummaryViewBean qualitySummaryViewBean = qualitySummaryProcess.getSearchData(qbean,
				personnelId);
			Collection c = qualitySummaryProcess.getQualifiedProducts(qbean);
			responseBody.put("qualityProductsRelationColl",
				qualitySummaryProcess.createRelationalObject(c));
			responseBody.put("qualitySummaryViewBean", qualitySummaryViewBean);

			// End Quality Summary

			// Approved Work Areas

			UseApprovalDetailViewBean awaBean = new UseApprovalDetailViewBean();
			awaBean.setFacPartNo(catPartNo);
			awaBean.setCatalogCompanyId(catalogCompanyId);
			awaBean.setPartGroupNo(partGroupNo);
			awaBean.setCatalogId(catalogId);
			awaBean.setFacilityId(facilityId);
			awaBean.setAllCatalog(false);

			ApprovedWorkAreasProcess approvedWorkAreasProcess = new ApprovedWorkAreasProcess(
				this.getDbUser(request));

			Collection approvedWorkAreasBeanCollection = approvedWorkAreasProcess.getsearchResult(awaBean);
			boolean useCodeRequired = approvedWorkAreasProcess.isUseCodeRequired(awaBean);

			responseBody.put("approvedWorkAreasBeanCollection", approvedWorkAreasBeanCollection);
			responseBody.put("useCodeRequired", useCodeRequired);

			// End Approved Work Areas

			// Lead time plots

			ChartProcess process = new ChartProcess(getDbUser(request));

			String inventoryGroupName = "Atlanta%20LM%20Marietta";
			String x = pbean.getInventoryGroupName();

			String issueGeneration = "";

			String fileName = process.generateLeadtimeChart(inventoryGroup, catPartNo,
				partGroupNo.toString(), inventoryGroupName, catalogId, issueGeneration,
				catalogCompanyId);
			String map = process.getMap();
			responseBody.put("fileName", fileName);
			responseBody.put("chartType", "Lead time for " + catPartNo + " in " + inventoryGroupName);

			if (log.isDebugEnabled()) {
			    log.debug("FILENAME:" + fileName);
			}

			// End Lead time plots

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
