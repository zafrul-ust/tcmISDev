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
import com.tcmis.client.catalog.beans.InventoryDetailInSupplyChainBean;
import com.tcmis.client.catalog.beans.InventoryDetailOnHandMaterialBean;
import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.client.catalog.beans.PkgInventoryDetailWebPrInventoryDetailBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.beans.QualityProductsBean;
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
		// TokenReactBean token = tokenVerify(request.getHeader("authorization"));
		// if (token.isValid()) {
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
		    PrCatalogScreenSearchBean pbean = (PrCatalogScreenSearchBean) BeanHandler.getJsonBeans(requestBody,
			    new PrCatalogScreenSearchBean());

		    pbean.setCompanyId(personnelBean.getCompanyId());
		    BigDecimal groupNo = new BigDecimal(1);
		    pbean.setPartGroupNo(groupNo);
		    pbean.setInventoryGroup("LM Marietta");
		    pbean.setFacilityId("Marietta");

		    Collection requestIdColl = catalogProcess.getRequestIdColl(pbean);
		    Collection specColl = catalogProcess.getSpecMenu(pbean);

		    Object partInvColl = catalogProcess.getInventoryMenu(pbean);
		    Object stockingReorder = catalogProcess.getStockingReorder(pbean);
		    Object imgLit = catalogProcess.getImgLit(pbean);
		    Object kitMsdsNumber = catalogProcess.getKitMsdsNumber(pbean);

		    // responseBody.put("specColl", specColl);
		    // responseBody.put("partInvColl", partInvColl);
		    // responseBody.put("stockingReorder", stockingReorder);
		    // responseBody.put("ImgLit", imgLit);
		    // responseBody.put("kitMsdsNumber", kitMsdsNumber);
		    // responseBody.put("requestIdColl", requestIdColl);

		    String catPartNo = pbean.getCatPartNo();
		    String catalogId = pbean.getCatalogId();
		    BigDecimal partGroupNo = pbean.getPartGroupNo();
		    String catalogCompanyId = pbean.getCompanyId();

		    String inventoryGroup = pbean.getInventoryGroup();
		    String facilityId = pbean.getFacilityId();

		    // Inventory
		    JSONArray itemDescription = new JSONArray();
		    JSONArray inventoryPartsForItem = new JSONArray();
		    JSONArray inventoryOnHandMaterial = new JSONArray();
		    JSONArray inventoryInSupplyChain = new JSONArray();

		    InventoryProcess inventoryProcess = new InventoryProcess(this.getDbUser(request));
		    Collection<PkgInventoryDetailWebPrInventoryDetailBean> inventoryColl = inventoryProcess
			    .getInventoryDetails(catPartNo, inventoryGroup, catalogId, partGroupNo.toString(),
				    catalogCompanyId);

		    if (inventoryColl.size() > 0) {
			inventoryColl.forEach(result -> {
			    if (result != null) {
				Collection<ItemBean> item = result.getItemDescription();
				Collection<InventoryDetailOnHandMaterialBean> onHands = result.getOnHandMaterial();
				Collection<InventoryDetailInSupplyChainBean> inSupplys = result.getInSupplyChain();
				Collection parts = result.getPartsForItem();
				if (item.size() > 0) {
				    item.forEach(itemResult -> {
					if (itemResult != null) {
					    try {
						itemDescription.put(BeanHandler.getJsonObject(itemResult));
					    } catch (BaseException e) {
					    }
					}
				    });
				}
				if (onHands.size() > 0) {
				    onHands.forEach(onHandResults -> {
					if (onHandResults != null) {
					    try {
						inventoryOnHandMaterial.put(BeanHandler.getJsonObject(onHandResults));
					    } catch (BaseException e) {
					    }
					}
				    });
				}
				if (inSupplys.size() > 0) {
				    inSupplys.forEach(inSupplyResults -> {
					if (inSupplyResults != null) {
					    try {
						inventoryInSupplyChain.put(BeanHandler.getJsonObject(inSupplyResults));
					    } catch (BaseException e) {
					    }
					}
				    });
				}
			    }
			});
		    }
		    responseBody.put("itemDescription", itemDescription);
		    responseBody.put("inventoryOnHandMaterialList", inventoryOnHandMaterial);
		    responseBody.put("inventoryInSupplyChainList", inventoryInSupplyChain);
		    // End Inventory

		    // Quality Summary
		    JSONArray qualitySummaryBean = new JSONArray();
		    JSONArray qualitySummaryResults = new JSONArray();

		    QualitySummaryInputBean qbean = new QualitySummaryInputBean();
		    qbean.setCatPartNo(catPartNo);
		    qbean.setCatalogCompanyId(catalogCompanyId);
		    qbean.setPartGroupNo(partGroupNo.toString());
		    qbean.setCatalogId(catalogId);
		    qbean.setFacilityId(facilityId);

		    QualitySummaryProcess qualitySummaryProcess = new QualitySummaryProcess(this.getDbUser(request));
		    QualitySummaryViewBean qualitySummaryViewBean = qualitySummaryProcess.getSearchData(qbean,
			    personnelId);
		    Collection<QualityProductsBean> c = qualitySummaryProcess.getQualifiedProducts(qbean);
		    Collection<QualityProductsBean> qualitySummaryColl = qualitySummaryProcess
			    .createRelationalObject(c);

		    if (qualitySummaryColl.size() > 0) {
			qualitySummaryColl.forEach(qualitySummaryCollResult -> {
			    if (qualitySummaryCollResult != null) {
				try {
				    qualitySummaryResults.put(BeanHandler.getJsonObject(qualitySummaryCollResult));
				} catch (BaseException e) {
				}
			    }
			});
		    }
		    if (qualitySummaryViewBean != null) {
			try {
			    qualitySummaryBean.put(BeanHandler.getJsonObject(qualitySummaryViewBean));
			} catch (BaseException e) {
			}
		    }
		    responseBody.put("qualitySummaryBean", qualitySummaryBean);
		    responseBody.put("qualitySummaryResults", qualitySummaryResults);
		    // End Quality Summary

		    // Approved Work Areas
		    JSONArray approvedWorkAreasResults = new JSONArray();
		    UseApprovalDetailViewBean awaBean = new UseApprovalDetailViewBean();
		    awaBean.setFacPartNo(catPartNo);
		    awaBean.setCatalogCompanyId(catalogCompanyId);
		    awaBean.setPartGroupNo(partGroupNo);
		    awaBean.setCatalogId(catalogId);
		    awaBean.setFacilityId(facilityId);
		    awaBean.setAllCatalog(false);

		    ApprovedWorkAreasProcess approvedWorkAreasProcess = new ApprovedWorkAreasProcess(
			    this.getDbUser(request));

		    Collection<UseApprovalDetailViewBean> approvedWorkAreasBeanCollection = approvedWorkAreasProcess
			    .getsearchResult(awaBean);
		    boolean useCodeRequired = approvedWorkAreasProcess.isUseCodeRequired(awaBean);
		    if (approvedWorkAreasBeanCollection.size() > 0) {
			approvedWorkAreasBeanCollection.forEach(approvedWorkAreaResult -> {
			    if (approvedWorkAreaResult != null) {
				try {
				    approvedWorkAreasResults.put(BeanHandler.getJsonObject(approvedWorkAreaResult));
				} catch (BaseException e) {
				}
			    }

			});
		    }
		    responseBody.put("approvedWorkAreasResults", approvedWorkAreasResults);
		    responseBody.put("useCodeRequired", useCodeRequired);
		    // End Approved Work Areas

		    // Lead time plots
		    ChartProcess process = new ChartProcess(getDbUser(request));
		    String inventoryGroupName = "Atlanta%20LM%20Marietta";
		    String x = pbean.getInventoryGroupName();
		    String issueGeneration = "";
		    String fileName = process.generateLeadtimeChart(inventoryGroup, catPartNo, partGroupNo.toString(),
			    inventoryGroupName, catalogId, issueGeneration, catalogCompanyId);
		    String map = process.getMap();
		    responseBody.put("fileName", fileName);
		    if (log.isDebugEnabled()) {
			log.debug("FILENAME:" + fileName);
		    }
		    // End Lead time plots

		    boolean editApprovalCode = personnelBean.getPermissionBean()
			    .hasFacilityPermission("EditUseCodeExpiration", bean.getFacilityId(), bean.getCompanyId());

		    String showDirectedCharge = "N";
		    if (!StringHandler.isBlankString(pbean.getApplicationId())
			    && !"My Work Areas".equals(pbean.getApplicationId())) {
			showDirectedCharge = catalogProcess.showDirectedCharge(pbean);
		    }
		    ok = true;
		    responseMsg = "Success!";

		} else {
		    responseMsg = "user has not permissions!";
		}

		responseBody.put("ok", ok);
		responseBody.put("message", responseMsg);
		responseCode = HttpServletResponse.SC_OK;
		// }
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
