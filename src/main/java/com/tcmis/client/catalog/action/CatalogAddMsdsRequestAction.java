package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.CatalogAddHmrbProcess;
import com.tcmis.client.catalog.process.CatalogAddMsdsRequestProcess;
import com.tcmis.client.catalog.beans.*;

/******************************************************************************
 * Controller for new catalog add MSDS request
 * @version 1.0
 ******************************************************************************/
public class CatalogAddMsdsRequestAction extends TcmISBaseAction {
    private static final String CAT_ADD_HEADER_VIEW_BEAN		            = "catAddHeaderViewBean";
    private static final String TCMIS_ERROR								    = "tcmISError";
    private static final String HAS_HMRB_TAB								= "hasHmrbTab";
    private static final String FORWARD_EDIT_NEW_MSDS					    = "editNewMsds";
    private static final String SHOW_REPLACES_MSDS							= "showReplacesMsds";
    private static final String FORWARD_SUCCESS 							= "success";
    private static final String FORWARD_RELOAD_MSDS_DATA					= "reloadMsdsData";

    private String forward = FORWARD_SUCCESS;

    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

        if (!this.isLoggedIn(request)) {
            request.setAttribute("requestedPage", "catalogaddmsdsrequest");
            request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
            return (mapping.findForward("login"));
        }

        if( form == null ) return (mapping.findForward("success"));

        String action = request.getParameter("uAction");
        if( action == null ) return mapping.findForward("success");
        //	  result
        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
        StringBuffer requestURL = request.getRequestURL();
        CatalogAddMsdsRequestProcess process = new CatalogAddMsdsRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
        //
        CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        //
        CatAddHeaderViewBean inputBean = new CatAddHeaderViewBean();
        BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        forward = FORWARD_SUCCESS;
        if (action.equals("view") ) {
            saveTcmISToken(request);
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
        } else if (action.equals("submit") ) {
            //If the page is being updated I check for a valid token.
            //checkToken will aslo save token for you to avoid duplicate form submissions.
            checkToken(request);
            //first save data
            Collection beans = BeanHandler.getBeans((DynaBean)form,"msdsDataDiv",new CatalogAddItemBean(),this.getTcmISLocale(request));
            String errorMsg = process.saveRequestData(inputBean,beans);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }else {
                CatalogAddRequestProcess process2 = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
                //this is because this page has two starting view variables, request starting view and starting_view (which is line starting view)
                inputBean.setStartingView(new BigDecimal(request.getParameter("requestStartingView")));
                errorMsg = process2.submitRequestData(inputBean,personnelBean);
                if (!"OK".equalsIgnoreCase(errorMsg)) {
                    request.setAttribute(TCMIS_ERROR, errorMsg);
                }
            }
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("save") ) {
            //If the page is being updated I check for a valid token.
            //checkToken will aslo save token for you to avoid duplicate form submissions.
            checkToken(request);
            Collection beans = BeanHandler.getBeans((DynaBean)form,"msdsDataDiv",new CatalogAddItemBean(),this.getTcmISLocale(request));
            String errorMsg = process.saveRequestData(inputBean,beans);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
            request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
        } else if (action.equals("deleteRequest") ) {
            CatalogAddRequestProcess process2 = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
            String errorMsg = process2.deleteRequest(inputBean);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
                CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
                request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
                request.setAttribute(HAS_HMRB_TAB,inputBean.getHasHmrbTab());
                request.setAttribute(SHOW_REPLACES_MSDS,inputBean.getShowReplacesMsds());
            }else {
                //if deleting is successfull I don't need any data other than request_id so I can close the tab
                inputBean.setSizeUnitViewColl(new Vector(0));
                request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
                request.setAttribute(HAS_HMRB_TAB,"N");
                request.setAttribute(SHOW_REPLACES_MSDS,"N");
            }
        } else if (action.equals("resubmitRequest") ) {
            CatalogAddRequestProcess process2 = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
            String errorMsg = process2.copyRequest(inputBean,personnelBean);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
                CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
                request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
                request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
                request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            }else {
                //if deleting is successfull I don't need any data other than request_id so I can close the tab
                inputBean.setSizeUnitViewColl(new Vector(0));
                request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,inputBean);
                request.setAttribute(HAS_HMRB_TAB,"N");
                request.setAttribute(SHOW_REPLACES_MSDS,"N");
            }
        } else if (action.equals("deleteLine") ) {
            BigDecimal partId = new BigDecimal(request.getParameter("partId"));
            process.deleteLine(inputBean.getRequestId(),new BigDecimal(inputBean.getLineItem()),partId);

            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            if("Y".equalsIgnoreCase((String)request.getParameter("delCont")))
                request.setAttribute("closeTransitWinflag","N");
            else
                request.setAttribute("closeTransitWinflag","Y");
            forward = FORWARD_RELOAD_MSDS_DATA;
        } else if (action.equals("reloadMsdsData") ) {
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            if("Y".equalsIgnoreCase((String)request.getParameter("addCont")))
                request.setAttribute("closeTransitWinflag","N");
            else
                request.setAttribute("closeTransitWinflag","Y");
            forward = FORWARD_RELOAD_MSDS_DATA;
        } else if (action.equals("approve") ) {
            //If the page is being updated I check for a valid token.
            //checkToken will aslo save token for you to avoid duplicate form submissions.
            checkToken(request);
            //first save data
            Collection<CatalogAddItemBean> beans = BeanHandler.getBeans((DynaBean)form,"msdsDataDiv",new CatalogAddItemBean(),this.getTcmISLocale(request));
            String errorMsg = process.saveRequestData(inputBean,beans);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }else {
                //approval roles
                CatalogAddRequestProcess process2 = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
                Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
                errorMsg = process2.approvalRequest(inputBean, approvalRoleBeans, personnelBean);
                if (!"OK".equalsIgnoreCase(errorMsg) && !"".equals(errorMsg)) {
                    request.setAttribute(TCMIS_ERROR, errorMsg);
                }
            }
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
        } else if (action.equals("reject") ) {
            //If the page is being updated I check for a valid token.
            //checkToken will aslo save token for you to avoid duplicate form submissions.
            checkToken(request);
            Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
            CatalogAddRequestProcess process2 = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
            String errorMsg = process2.rejectRequest(inputBean, approvalRoleBeans, personnelBean);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }
            CatAddHeaderViewBean resultBean = process.getMsdsData(inputBean.getRequestId(),personnelBean);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN,resultBean);
            request.setAttribute(HAS_HMRB_TAB,personnelBean.isFeatureReleased("HmrbTab",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
            request.setAttribute(SHOW_REPLACES_MSDS,personnelBean.isFeatureReleased("ShowReplacesMsds",resultBean.getEngEvalFacilityId(),resultBean.getCompanyId())?"Y":"N");
        } else if (action.equals("newMaterial") ) {
            BigDecimal partId = process.buildNewMaterial(inputBean.getRequestId());
            getMaterialPopupData(request,personnelBean,process,inputBean.getRequestId(),partId);
        } else if (action.equals("addMaterial") ) {
            BigDecimal partId = process.buildAddMaterial(inputBean.getRequestId(),inputBean.getCustMsdsNo(),inputBean.getMaterialId());
            getMaterialPopupData(request,personnelBean,process,inputBean.getRequestId(),partId);
        } else if (action.equals("editNewMaterial") ) {
            BigDecimal partId = new BigDecimal(request.getParameter("partId"));
            getMaterialPopupData(request,personnelBean,process,inputBean.getRequestId(),partId);
        } else if (action.equals("submitEditNewMaterial") ) {
            CatalogAddItemBean caiBean = new CatalogAddItemBean();
            BeanHandler.copyAttributes(form, caiBean, getTcmISLocale(request));
            String errorMsg = process.saveMaterialData(inputBean,caiBean);
            if (!"OK".equalsIgnoreCase(errorMsg)) {
                request.setAttribute(TCMIS_ERROR, errorMsg);
            }
            getMaterialPopupData(request,personnelBean,process,caiBean.getRequestId(),caiBean.getPartId());
        } else if (action.equals("submitNewComponent") ) {
            CatalogAddItemBean caiBean = new CatalogAddItemBean();
            BeanHandler.copyAttributes(form, caiBean, getTcmISLocale(request));
            process.buildNewComponent(personnelBean,inputBean,caiBean);
            getMaterialPopupData(request,personnelBean,process,caiBean.getRequestId(),caiBean.getPartId());
        }
        return mapping.findForward(forward);
    }

    void getMaterialPopupData(HttpServletRequest request, PersonnelBean personnelBean, CatalogAddMsdsRequestProcess process, BigDecimal requestId, BigDecimal partId) {
        try {
            CatAddHeaderViewBean resultBean = process.getNewCatalogItemData(requestId, partId);
            request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, resultBean);
            request.setAttribute(HAS_HMRB_TAB, personnelBean.isFeatureReleased("HmrbTab", resultBean.getEngEvalFacilityId(), resultBean.getCompanyId()) ? "Y" : "N");
            request.setAttribute(SHOW_REPLACES_MSDS, personnelBean.isFeatureReleased("ShowReplacesMsds", resultBean.getEngEvalFacilityId(), resultBean.getCompanyId()) ? "Y" : "N");
            forward = FORWARD_EDIT_NEW_MSDS;
        }catch (Exception e) {e.printStackTrace();}
    }

}