package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.CatalogAddHmrbProcess;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.common.beans.ShoppingCartBean;

/******************************************************************************
 * Controller for new catalog add request
 * @version 1.0
 ******************************************************************************/
public class CatalogAddRequestAction extends TcmISBaseAction {
	private static final String FORWARD_EDIT_NEW_ITEM					= "editNewItem";
	private static final String CATALOG_ADD_EDIT_NEW_ITEM_DATA		= "catalogAddEditNewItemData";
	private static final String SUBMITTED_DATA_COLL						= "submittedDataColl";
	private static final String LINE_ITEM									= "lineItem";
	private static final String CAT_ADD_HEADER_VIEW_BEAN		      = "catAddHeaderViewBean";
	private static final String TCMIS_ERROR								= "tcmISError";
	private static final String RELOAD_QPL_DATA							= "reloadQplData";
	private static final String RELOAD_SPEC_DATA							= "reloadSpecData";
	private static final String ADD_SPEC									= "addSpec";
	private static final String SPEC_DATA									= "specData";
	private static final String RELOAD_FLOW_DOWN_DATA				   = "reloadFlowdownData";
	private static final String ITEM_ALREADY_IN_QPL					   = "itemAlreadyInQpl";
	private static final String ITEM_ID_IN_QPL							= "itemIdInQpl";
	private static final String RELOAD_USE_APPROVAL_DATA           = "reloadUseApprovalData";
	private static final String CATALOG_FACILITY_COLLECTION			= "catalogFacilityCollection";
    private static final String HAS_HMRB_TAB								= "hasHmrbTab";
    private static final String RELOAD_HMRB_DATA							= "reloadHmrbData";
    private static final String ADD_HMRB									= "addHmrb";
    private static final String HMRB_DATA									= "hmrbData";
    private static final String CALL_TO_SERVER_CALLBACK					= "callToServerCallback";
    private static final String RELOAD_STORAGE_DATA									= "reloadStorageData";
    private static final String HAS_CATALOG_ADD_STORAGE_TAB				= "hasCatalogAddStorageTab";
    private static final String ATTACHED_FILE_LIST				= "attachedFileList";
    private static final String SHOW_REPLACES_MSDS									= "showReplacesMsds";

    public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "catalogaddrequest");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

	  	if( form == null ) return (mapping.findForward("success"));

		String action = request.getParameter("uAction");
		if( action == null ) return mapping.findForward("success");
		//	  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		StringBuffer requestURL = request.getRequestURL();
		CatalogAddRequestProcess process = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
		//
		CatAddHeaderViewBean inputBean = new CatAddHeaderViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		String forward = "success";
		
		if (action.equals("view") ) {
			saveTcmISToken(request);
			process.getRequestInfo(inputBean,personnelBean,true);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			CatalogFacilityBean tmpBean = new CatalogFacilityBean();
			tmpBean.setCompanyId(inputBean.getCompanyId());
			tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			tmpBean.setCatalogId(inputBean.getCatalogId());
			tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
			request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())?"Y":"N");
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,personnelBean.isFeatureReleased("CatalogAddStorageTab",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())?"Y":"N");
        } else if (action.equals("submit") ) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String hasHmrbTab = inputBean.getHasHmrbTab();
            String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
            String showReplacesMsds = inputBean.getShowReplacesMsds();
            String errorMsg = process.submitRequestData(inputBean,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
            if("SEAGATE".equalsIgnoreCase(inputBean.getCompanyId()))
            	request.setAttribute("seaGatePopUp",inputBean.getSeagateURL());
			process.getRequestInfo(inputBean,personnelBean,true);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			CatalogFacilityBean tmpBean = new CatalogFacilityBean();
			tmpBean.setCompanyId(inputBean.getCompanyId());
			tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			tmpBean.setCatalogId(inputBean.getCatalogId());
			tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
			request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
            //put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
            inputBean.setHasHmrbTab(hasHmrbTab);
            inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
            inputBean.setShowReplacesMsds(showReplacesMsds);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("save") ) {
            //If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String hasHmrbTab = inputBean.getHasHmrbTab();
            String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
            String showReplacesMsds = inputBean.getShowReplacesMsds();
            String errorMsg = process.saveRequestData(inputBean,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			process.getRequestInfo(inputBean,personnelBean,true);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			CatalogFacilityBean tmpBean = new CatalogFacilityBean();
			tmpBean.setCompanyId(inputBean.getCompanyId());
			tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			tmpBean.setCatalogId(inputBean.getCatalogId());
			tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
			request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
			//put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
            inputBean.setHasHmrbTab(hasHmrbTab);
            inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
            inputBean.setShowReplacesMsds(showReplacesMsds);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("deleteRequest") ) {
            //If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String errorMsg = process.deleteRequest(inputBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
                String hasHmrbTab = inputBean.getHasHmrbTab();
                String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
                String showReplacesMsds = inputBean.getShowReplacesMsds();
                request.setAttribute(TCMIS_ERROR, errorMsg);
				process.getRequestInfo(inputBean,personnelBean,true);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogFacilityBean tmpBean = new CatalogFacilityBean();
				tmpBean.setCompanyId(inputBean.getCompanyId());
				tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
				tmpBean.setCatalogId(inputBean.getCatalogId());
				tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
				request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
				//put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
                inputBean.setHasHmrbTab(hasHmrbTab);
                inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
                inputBean.setShowReplacesMsds(showReplacesMsds);
                request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
                request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
                request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            }else {
				//if deleting is successfull I don't need any data other than request_id so I can close the tab
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
				request.setAttribute(CATALOG_FACILITY_COLLECTION,new Vector(0));
				request.setAttribute(HAS_HMRB_TAB,"N");
			    request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, "N");
                request.setAttribute(SHOW_REPLACES_MSDS,"N");
            }
        } else if (action.equals("resubmitRequest") ) {
            //If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String errorMsg = process.copyRequest(inputBean,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
                String hasHmrbTab = inputBean.getHasHmrbTab();
                String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
                String showReplacesMsds = inputBean.getShowReplacesMsds();
                request.setAttribute(TCMIS_ERROR, errorMsg);
				process.getRequestInfo(inputBean,personnelBean,true);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogFacilityBean tmpBean = new CatalogFacilityBean();
				tmpBean.setCompanyId(inputBean.getCompanyId());
				tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
				tmpBean.setCatalogId(inputBean.getCatalogId());
				tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
				request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
				//put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
                inputBean.setHasHmrbTab(hasHmrbTab);
                inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
                inputBean.setShowReplacesMsds(showReplacesMsds);
                request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
                request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
                request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            }else {
				//if deleting is successfull I don't need any data other than request_id so I can close the tab
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
				request.setAttribute(CATALOG_FACILITY_COLLECTION,new Vector(0));
				request.setAttribute(HAS_HMRB_TAB,"N");
			    request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, "N");
                request.setAttribute(SHOW_REPLACES_MSDS,"N");
            }
        } else if (action.equals("approve") ) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String hasHmrbTab = inputBean.getHasHmrbTab();
            String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
            String showReplacesMsds = inputBean.getShowReplacesMsds();
            Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
            String errorMsg = process.approvalRequest(inputBean, approvalRoleBeans, personnelBean);
            if (!"OK".equalsIgnoreCase(errorMsg) && !"".equals(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }
			process.getRequestInfo(inputBean,personnelBean,true);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			CatalogFacilityBean tmpBean = new CatalogFacilityBean();
			tmpBean.setCompanyId(inputBean.getCompanyId());
			tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			tmpBean.setCatalogId(inputBean.getCatalogId());
			tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
			request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
			//put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
            inputBean.setHasHmrbTab(hasHmrbTab);
            inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
            inputBean.setShowReplacesMsds(showReplacesMsds);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("reject") ) {
			//If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);
            String hasHmrbTab = inputBean.getHasHmrbTab();
            String catalogAddStorageTab = inputBean.getHasCatalogAddStorageTab();
            String showReplacesMsds = inputBean.getShowReplacesMsds();
            Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
			String errorMsg = process.rejectRequest(inputBean, approvalRoleBeans, personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			process.getRequestInfo(inputBean,personnelBean,true);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			CatalogFacilityBean tmpBean = new CatalogFacilityBean();
			tmpBean.setCompanyId(inputBean.getCompanyId());
			tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			tmpBean.setCatalogId(inputBean.getCatalogId());
			tmpBean.setFacilityId(inputBean.getEngEvalFacilityId());
			request.setAttribute(CATALOG_FACILITY_COLLECTION,catalogProcess.getCatalogFacility(tmpBean));
			//put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
            inputBean.setHasHmrbTab(hasHmrbTab);
            inputBean.setHasCatalogAddStorageTab(catalogAddStorageTab);
            inputBean.setShowReplacesMsds(showReplacesMsds);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("addSuggestedSupplier") ) {
			BigDecimal lineItem = new BigDecimal(request.getParameter(LINE_ITEM));
			request.setAttribute("suggestedSupplierData",process.getSuggestedSupplier(inputBean.getRequestId(),lineItem));
			forward = "addSuggestedSupplier";
		} else if (action.equals("submitAddSuggesstedSupplier") ) {
			CatalogAddItemBean bean = new CatalogAddItemBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setRequestId(inputBean.getRequestId());
			String errorMsg = process.submitAddSuggestedSupplier(bean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
				request.setAttribute("suggestedSupplierData",inputBean);
			}
			forward = "addSuggestedSupplier";
		} else if (action.equals("editNewItem") ) {
			BigDecimal lineItem = new BigDecimal(request.getParameter(LINE_ITEM));
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,process.getNewCatalogItemData(inputBean.getRequestId(),lineItem,personnelBean));
			request.setAttribute(SUBMITTED_DATA_COLL,new Vector(0));
			request.setAttribute(LINE_ITEM,lineItem);
			request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			request.setAttribute(ITEM_ID_IN_QPL,"");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
		    request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = FORWARD_EDIT_NEW_ITEM;
		} else if (action.equals("submitEditNewItem") ) {
			Collection tabBeans = BeanHandler.getBeans((DynaBean) form,"catalogAddItemBean", new CatalogAddItemBean(),getTcmISLocale(request));
			String errorMsg = process.submitEditNewItemData(inputBean,tabBeans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			BigDecimal lineItem = new BigDecimal(request.getParameter(LINE_ITEM));

			CatAddHeaderViewBean tmpBean = new CatAddHeaderViewBean();
			tmpBean.setRequestId(inputBean.getRequestId());
			tmpBean.setCatalogAddItemColl(new Vector(0));
			tmpBean.setSizeUnitViewColl(new Vector(0));
			tmpBean.setNetWtSizeUnitColl(new Vector(0));
			tmpBean.setShippingWeightUnitColl(new Vector(0));
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,tmpBean);
			request.setAttribute(SUBMITTED_DATA_COLL,process.getSubmitCatalogItemData(inputBean.getRequestId(),lineItem));
			request.setAttribute(LINE_ITEM,lineItem);
			request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			request.setAttribute(ITEM_ID_IN_QPL,"");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
		    request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = FORWARD_EDIT_NEW_ITEM;
		} else if (action.equals("newMaterial") ) {
			HashMap results = process.buildNewMaterial(inputBean.getRequestId(),request.getParameter("lineItem"));
			BigDecimal lineItem = (BigDecimal)results.get("lineItem");
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,process.getNewCatalogItemData(inputBean.getRequestId(),lineItem,personnelBean,(Vector<BigDecimal>)results.get("partIdColl")));
			request.setAttribute(SUBMITTED_DATA_COLL,new Vector(0));
			request.setAttribute(LINE_ITEM,lineItem);
			request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			request.setAttribute(ITEM_ID_IN_QPL,"");
            CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())?"Y":"N");
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB,"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())?"Y":"N");

            forward = FORWARD_EDIT_NEW_ITEM;
		} else if (action.equals("newSizePackaging") ) {
			String addToLineItem = request.getParameter("lineItem");
			HashMap results = process.buildNewSizePackaging(inputBean.getRequestId(),inputBean.getItemId(),new BigDecimal(request.getParameter("materialId")),request.getParameter("customerMixtureNumber"),addToLineItem,request.getParameter("insertCustMixNo"));
			BigDecimal lineItem = (BigDecimal)results.get("lineItem");
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,process.getNewCatalogItemData(inputBean.getRequestId(),lineItem,personnelBean, (Vector)results.get("partIdColl")));
			request.setAttribute(SUBMITTED_DATA_COLL,new Vector(0));
			request.setAttribute(LINE_ITEM,lineItem);
			request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			request.setAttribute(ITEM_ID_IN_QPL,"");
			forward = FORWARD_EDIT_NEW_ITEM;
        } else if (action.equals("addItem") ) {
            inputBean.setStartingView(new BigDecimal(CatalogAddRequestProcess.MODIFY_QPL));
			BigDecimal lineItem = process.buildAddItem(inputBean,personnelBean);
            CatAddHeaderViewBean tmpBean = new CatAddHeaderViewBean();
			tmpBean.setRequestId(inputBean.getRequestId());
			tmpBean.setCatalogAddItemColl(new Vector(0));
			tmpBean.setSizeUnitViewColl(new Vector(0));
			tmpBean.setNetWtSizeUnitColl(new Vector(0));
			tmpBean.setShippingWeightUnitColl(new Vector(0));
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,tmpBean);
			//request.setAttribute(SUBMITTED_DATA_COLL,process.getSubmitCatalogItemData(inputBean.getRequestId(),lineItem));
			request.setAttribute(SUBMITTED_DATA_COLL,new Vector(0));
			request.setAttribute(LINE_ITEM,lineItem);
			if (lineItem == null) {
				request.setAttribute(ITEM_ALREADY_IN_QPL,"Y");
			}else {
				request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			}
			request.setAttribute(ITEM_ID_IN_QPL,request.getParameter("itemId"));
			forward = FORWARD_EDIT_NEW_ITEM;
			
		}else if (action.equals("addMsds") ) {
			HashMap results = process.buildNewSizePackagingMsds(inputBean.getRequestId(),inputBean.getItemId(),(request.getParameter("materialId") == null?null:new BigDecimal(request.getParameter("materialId"))),request.getParameter("customerMixtureNumber"),new Boolean(request.getParameter("insertCustMixtNo")),request.getParameter("lineItem"));
			BigDecimal lineItem = (BigDecimal)results.get("lineItem");
			request.setAttribute(CATALOG_ADD_EDIT_NEW_ITEM_DATA,process.getNewCatalogItemData(inputBean.getRequestId(),lineItem,personnelBean,(Vector<BigDecimal>)results.get("partIdColl")));
			request.setAttribute(SUBMITTED_DATA_COLL,process.getSubmitCatalogItemData(inputBean.getRequestId(),lineItem));
			request.setAttribute(LINE_ITEM, lineItem);
			request.setAttribute(ITEM_ALREADY_IN_QPL,"N");
			request.setAttribute(ITEM_ID_IN_QPL,"");
			forward = FORWARD_EDIT_NEW_ITEM;
		} 
          else if (action.equals("deleteLine")) {
			String tmpTimeTempSensitive = inputBean.getTimeTempSensitive();
			String tmpRoomTempOutTime = inputBean.getRoomTempOutTime();
			BigDecimal lineItem = new BigDecimal(request.getParameter(LINE_ITEM));
			String partId = request.getParameter("partId");
			Collection<CatalogAddItemBean> deleteCatAddItemBeans = BeanHandler.getBeans((DynaBean) form,"catalogAddItemBean", new CatalogAddItemBean(),getTcmISLocale(request));
			if(!StringHandler.isBlankString(partId))
				 process.deleteLines(inputBean.getRequestId(),lineItem,new BigDecimal(partId),deleteCatAddItemBeans);
			else if(!deleteCatAddItemBeans.isEmpty())
			{
				 process.deleteLines(inputBean.getRequestId(),lineItem,null,deleteCatAddItemBeans);
				 return noForward;
			}
			else
				 process.deleteLines(inputBean.getRequestId(),lineItem,null,null);

			//reload data
			process.getRequestInfo(inputBean,personnelBean,false);
			//do this because user changes shelf life source and it is not yet saved into database
			if (!StringHandler.isBlankString(tmpTimeTempSensitive)) {
				inputBean.setTimeTempSensitive(tmpTimeTempSensitive);
			}
			if (!StringHandler.isBlankString(tmpRoomTempOutTime)) {
				inputBean.setRoomTempOutTime(tmpRoomTempOutTime);
			}
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals("rejectLine")) {
			String tmpTimeTempSensitive = inputBean.getTimeTempSensitive();
			String tmpRoomTempOutTime = inputBean.getRoomTempOutTime();
			BigDecimal lineItem = new BigDecimal(request.getParameter(LINE_ITEM));
            process.rejectLine(inputBean.getRequestId(),lineItem,personnelBean,request.getParameter("comments"));
			//reload data
			process.getRequestInfo(inputBean,personnelBean,false);
			//do this because user changes shelf life source and it is not yet saved into database
			if (!StringHandler.isBlankString(tmpTimeTempSensitive)) {
				inputBean.setTimeTempSensitive(tmpTimeTempSensitive);
			}
			if (!StringHandler.isBlankString(tmpRoomTempOutTime)) {
				inputBean.setRoomTempOutTime(tmpRoomTempOutTime);
			}
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals("showObsolete")) {
			String tmpTimeTempSensitive = inputBean.getTimeTempSensitive();
			String tmpRoomTempOutTime = inputBean.getRoomTempOutTime();
            process.setShowObsolete(true);
			process.getRequestInfo(inputBean,personnelBean,false);
			//do this because user changes shelf life source and it is not yet saved into database
			if (!StringHandler.isBlankString(tmpTimeTempSensitive)) {
				inputBean.setTimeTempSensitive(tmpTimeTempSensitive);
			}
			if (!StringHandler.isBlankString(tmpRoomTempOutTime)) {
				inputBean.setRoomTempOutTime(tmpRoomTempOutTime);
			}
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals("saveQplData")) {
            String tmpTimeTempSensitive = inputBean.getTimeTempSensitiveYes();
			String tmpRoomTempOutTime = inputBean.getRoomTempOutTimeYes();
            Collection<CatPartQplViewBean> beans = BeanHandler.getBeans((DynaBean)form,"qplDataDiv",new CatPartQplViewBean(),this.getTcmISLocale(request));
			
			String errorMsg = process.saveQplData(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			process.getRequestInfo(inputBean,personnelBean,false);
            //do this because user changes shelf life source and it is not yet saved into database
			if (!StringHandler.isBlankString(tmpTimeTempSensitive)) {
				inputBean.setTimeTempSensitive(tmpTimeTempSensitive);
			}
			if (!StringHandler.isBlankString(tmpRoomTempOutTime)) {
				inputBean.setRoomTempOutTime(tmpRoomTempOutTime);
			}
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			String closeTransitWinflag = (String) ( (DynaBean)form).get("closeTransitWinflag");
			request.setAttribute("closeTransitWinflag",closeTransitWinflag);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals("fadeOutFromQpl")) {
            inputBean.setStartingView(new BigDecimal(CatalogAddRequestProcess.FADEOUT_FROM_QPL));
            inputBean.setLineItem(null);
            inputBean.setCustomerMixtureNumber(null);
            process.buildAddItem(inputBean,personnelBean);
            //reload data
			process.getRequestInfo(inputBean,personnelBean,false);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, inputBean.getHasCatalogAddStorageTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals(RELOAD_QPL_DATA)) {
			String tmpTimeTempSensitive = inputBean.getTimeTempSensitive();
			String tmpRoomTempOutTime = inputBean.getRoomTempOutTime();
            String hasHmrbTab = inputBean.getHasHmrbTab();
            String showReplacesMsds = inputBean.getShowReplacesMsds();
            //reload data
			process.getRequestInfo(inputBean,personnelBean,true);
			//do this because user changes shelf life source and it is not yet saved into database
			if (!StringHandler.isBlankString(tmpTimeTempSensitive)) {
				inputBean.setTimeTempSensitive(tmpTimeTempSensitive);
			}
			if (!StringHandler.isBlankString(tmpRoomTempOutTime)) {
				inputBean.setRoomTempOutTime(tmpRoomTempOutTime);
			}
            //put data from request back to inputbean because it was reset by getRequestInfo with needHeaderInfo = true
            inputBean.setHasHmrbTab(hasHmrbTab);
            inputBean.setShowReplacesMsds(showReplacesMsds);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
            String refreshQplDataCloseTransWin = request.getParameter("refreshQplDataCloseTransWin");
            if(!StringHandler.isBlankString(refreshQplDataCloseTransWin) && "N".equalsIgnoreCase(refreshQplDataCloseTransWin))
            	request.setAttribute("closeTransitWinflag","N");
            else
            	request.setAttribute("closeTransitWinflag","Y");
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(HAS_CATALOG_ADD_STORAGE_TAB, "N");
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            forward = RELOAD_QPL_DATA;
		} else if (action.equals("addUseApproval") ) {
			request.setAttribute("useApprovalData", process.getUseApproval(inputBean));
			forward = "addUseApproval";
		} else if (action.equals("submitUseApproval") ) {
			CatalogAddUserGroupBean bean = new CatalogAddUserGroupBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setRequestId(inputBean.getRequestId());
			String errorMsg = process.submitUseApproval(bean,inputBean.getCalledFrom());
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			bean.setWorkAreaColl(new Vector(0));
			bean.setUserGroupColl(new Vector(0));
			bean.setInventoryGroupColl(new Vector(0));
			request.setAttribute("useApprovalData",bean);
			
			String storageAction = request.getParameter("storageAction");
			if(storageAction != null && storageAction.equalsIgnoreCase("add"))
			{
				String application = request.getParameter("application");
				errorMsg = process.saveSingleStorageRecord(application, inputBean.getRequestId(), personnelBean.getCompanyId());
					if (!"OK".equalsIgnoreCase(errorMsg)) {
						request.setAttribute(TCMIS_ERROR, errorMsg);
					}
			}
			
			forward = "addUseApproval";
		} else if (action.equals("saveUseApprovalData")) {
			Collection<CatalogAddUserGroupBean> beans = BeanHandler.getBeans((DynaBean)form,"useApprovalDataDiv",new CatalogAddUserGroupBean(),this.getTcmISLocale(request));

			String errorMsg = process.saveUseApprovalData(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			//reload data
			process.reloadUseApprovalData(inputBean,personnelBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			
			String closeTransitWinflag = (String) ( (DynaBean)form).get("closeTransitWinflag");
			request.setAttribute("closeTransitWinflag",closeTransitWinflag);
			forward = RELOAD_USE_APPROVAL_DATA;
		} else if (action.equals("deleteUseApprovalLine")) {
			process.deleteUseApprovalLine(inputBean);
			//reload data
			process.reloadUseApprovalData(inputBean,personnelBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
			
			String storageAction = request.getParameter("storageAction");
			if(storageAction != null && storageAction.equalsIgnoreCase("delete"))
			{
				String application = request.getParameter("application");
				String errorMsg = process.deleteStorageData(inputBean.getRequestId().toPlainString(), application, personnelBean.getCompanyId());
					if (!"OK".equalsIgnoreCase(errorMsg)) {
						request.setAttribute(TCMIS_ERROR, errorMsg);
					}
			}
			
			forward = RELOAD_USE_APPROVAL_DATA;
		} else if (action.equals("reloadUseApproval")) {
			//reload data
			process.reloadUseApprovalData(inputBean,personnelBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			request.setAttribute("closeTransitWinflag","Y");
			forward = RELOAD_USE_APPROVAL_DATA;
		}else if (action.equals(ADD_SPEC) ) {
			CatalogAddSpecBean bean = new CatalogAddSpecBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setSpecName(StringUtils.join(StringUtils.split(bean.getSpecName(), " "), ' '));
			bean.setSpecVersion(StringUtils.join(StringUtils.split(bean.getSpecVersion(), " "), ' '));
			bean.setSpecAmendment(StringUtils.join(StringUtils.split(bean.getSpecAmendment(), " "), ' '));
			request.setAttribute(SPEC_DATA,process.getSpecInfo(bean,false));
			forward = ADD_SPEC;
		}else if (action.equals("addStandardMfgCert") ) {
			CatalogAddSpecBean bean = new CatalogAddSpecBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			request.setAttribute(SPEC_DATA,process.getSpecInfo(bean,true));
			forward = ADD_SPEC;
		} else if (action.equals("addNoSpec")) {
			process.addNoSpec(inputBean);
			//reload data
			process.reloadSpecData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_SPEC_DATA;
		} else if (action.equals("deleteSpec")) {
			CatalogAddSpecBean bean = new CatalogAddSpecBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setSpecName(StringUtils.join(StringUtils.split(bean.getSpecName(), " "), ' '));
			bean.setSpecVersion(StringUtils.join(StringUtils.split(bean.getSpecVersion(), " "), ' '));
			bean.setSpecAmendment(StringUtils.join(StringUtils.split(bean.getSpecAmendment(), " "), ' '));
			process.deleteSpec(bean);
			//reload data
			process.reloadSpecData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_SPEC_DATA;
        } else if (action.equals("saveSpecData")) {
			Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specDataDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));

			String errorMsg = process.saveSpecData(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			//reload data
			process.reloadSpecData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);

			String closeTransitWinflag = (String) ( (DynaBean)form).get("closeTransitWinflag");
			request.setAttribute("closeTransitWinflag",closeTransitWinflag);
			forward = RELOAD_SPEC_DATA;
        } else if (action.equals("submitSpec") ) {
			CatalogAddSpecBean bean = new CatalogAddSpecBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			bean.setSpecName(StringUtils.join(StringUtils.split(bean.getSpecName(), " "), ' '));
			bean.setSpecVersion(StringUtils.join(StringUtils.split(bean.getSpecVersion(), " "), ' '));
			bean.setSpecAmendment(StringUtils.join(StringUtils.split(bean.getSpecAmendment(), " "), ' '));
			String errorMsg = process.submitSpec(personnelBean,bean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			request.setAttribute(SPEC_DATA,bean);
			forward = ADD_SPEC;
		} else if (action.equals("reloadSpec")) {
			//reload data
			process.reloadSpecData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_SPEC_DATA;
		}else if (action.equals("addFlowdown") ) {
			CatalogAddFlowDownBean bean = new CatalogAddFlowDownBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			process.submitFlowdown(bean);
			//reload data
			process.reloadFlowdownData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_FLOW_DOWN_DATA;
		} else if (action.equals("deleteFlowdown")) {
			CatalogAddFlowDownBean bean = new CatalogAddFlowDownBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			process.deleteFlowdown(bean);
			//reload data
			process.reloadFlowdownData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_FLOW_DOWN_DATA;
		} else if (action.equals("reloadFlowdown")) {
			//reload data
			process.reloadFlowdownData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_FLOW_DOWN_DATA;

            
        }else if (action.equals(ADD_HMRB) ) {
			CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            request.setAttribute(HMRB_DATA,hmrbProcess.getHmrbInfo(bean));
			forward = ADD_HMRB;
        }else if (action.equals("editHmrb") ) {
			CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            bean.setUAction(action);
            request.setAttribute(HMRB_DATA,hmrbProcess.getHmrbInfo(bean));
			forward = ADD_HMRB;
        }else if (action.equals("copyHmrb") ) {
			CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            bean.setUAction(action);
            hmrbProcess.copyHmrb(bean);
            request.setAttribute(HMRB_DATA,hmrbProcess.getHmrbInfo(bean));
			forward = ADD_HMRB;
        }else if (action.equals("viewHmrb") ) {
			CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            bean.setUAction(action);
            request.setAttribute(HMRB_DATA,hmrbProcess.getHmrbInfo(bean));
			forward = ADD_HMRB;
        } else if (action.equals("submitHmrb") ) {
			CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            String errorMsg = hmrbProcess.submitHmrb(bean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
            bean.setMaxQtyUsePerShiftUnitColl(new Vector(0));
            request.setAttribute(HMRB_DATA,bean);
			forward = ADD_HMRB;
		} else if (action.equals("deleteHmrbLine")) {
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            hmrbProcess.deleteHmrb(inputBean);
            //reload data
			process.reloadHmrbData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_HMRB_DATA;
		} else if (action.equals("reloadHmrb")) {
			//reload data
			process.reloadHmrbData(inputBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			forward = RELOAD_HMRB_DATA;
		} else if (action.equals("setUpdateUseCodeExpiration")) {
            CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getDbUser(request),getTcmISLocaleString(request));
            hmrbProcess.setUpdateUseCodeExpiration(inputBean,personnelBean);
			forward = CALL_TO_SERVER_CALLBACK;
		}
		else if(action.equals("storageCheckExistingWorkAreas"))
		{
			String existingWorkAreas = request.getParameter("existingWorkAreas");
			String appDesc = request.getParameter("applicationDesc");
			String requestId = request.getParameter("requestId");
			Collection<CatalogAddStorageBean> results =  process.getWorkArea(requestId, appDesc,existingWorkAreas,personnelBean.getCompanyId());       
	        // Write out the data
	      	PrintWriter out = response.getWriter();
	        // status needs to be in the third position or just id:name with no statue
	        // If we need to add different values, please leave the third one empty like id:name::something else 
	      	for(CatalogAddStorageBean bean: results)
	    		out.println(bean.getApplication()+"]>^~<["+bean.getApplicationDesc());   		
	      	out.close();   	
			return noForward;
		}
		else if(action.equals("saveStorageData"))
		{
			Collection<CatalogAddStorageBean> beans = BeanHandler.getBeans((DynaBean)form,"storageDataDiv",new CatalogAddStorageBean(),this.getTcmISLocale(request));

			String errorMsg = process.saveStorageData(inputBean,beans);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			//reload data
			process.reloadStorageData(inputBean,personnelBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			
			String closeTransitWinflag = (String) ( (DynaBean)form).get("closeTransitWinflag");
			request.setAttribute("closeTransitWinflag",closeTransitWinflag);
			forward = RELOAD_STORAGE_DATA;
		}
		else if(action.equals("deleteStorageData"))
		{
			String errorMsg = process.deleteStorageData(request.getParameter("requestId"), request.getParameter("application"), personnelBean.getCompanyId());
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			//reload data
			process.reloadStorageData(inputBean,personnelBean);
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
			
			String closeTransitWinflag = (String) ( (DynaBean)form).get("closeTransitWinflag");
			request.setAttribute("closeTransitWinflag",closeTransitWinflag);
			forward = CALL_TO_SERVER_CALLBACK;
		}
		else if(action.equals("findAttachedFiles"))
		{
			EngEvalProcess eProcess = new EngEvalProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			String results = eProcess.getUserUploadedMsdsForRequest(new BigDecimal(request.getParameter("requestId")));
			
			if(results.length() > 0) {
				String[] splits = results.split("\n");
				request.setAttribute("fileList",splits);
			}
			
			forward = ATTACHED_FILE_LIST;
		}
		// @dbauer - added the msdsRequest function
		else if (action.equals("msdsRequest")) {
			AutocompleteInputBean autocompleteBean = new AutocompleteInputBean(request);
			BeanHandler.copyAttributes(form, autocompleteBean, getTcmISLocale(request));
			
			Vector<CustomerMaterialNumberBean> msdsColl =  (Vector<CustomerMaterialNumberBean>)process.getMSDSColl(autocompleteBean);
	    	
	    	// @dbauer - JSON addition
	    	JSONObject results = new JSONObject();
	    	
	    	JSONArray resultsArray = new JSONArray();
			for (CustomerMaterialNumberBean bean : msdsColl) {
				JSONObject docJSON = new JSONObject();
				docJSON.put("customerMsdsNumber", bean.getCustomerMsdsNumber());
				resultsArray.put(docJSON);
			}
			results.put("CustomerMSDSNumberResults", resultsArray);
			
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
	    	
	    	return noForward;
		}

        return mapping.findForward(forward);
	}
}