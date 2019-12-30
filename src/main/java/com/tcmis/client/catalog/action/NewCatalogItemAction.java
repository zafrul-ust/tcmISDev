package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.process.NewCatalogItemProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


/******************************************************************************
 * Controller for new catalog item
 * @version 1.0
 ******************************************************************************/
public class NewCatalogItemAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newcatalogitem");
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//	  main
		if( form == null )
			return (mapping.findForward("success"));
		String action = request.getParameter("uAction");
        String sourcePage = request.getParameter("sourcePage");

        if( action == null ) return mapping.findForward("success");
		//	  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		StringBuffer requestURL = request.getRequestURL();
		NewCatalogItemProcess process = new NewCatalogItemProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
		//
		CatAddHeaderViewBean inputBean = new CatAddHeaderViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (action.equals("new") ) {
			if (log.isDebugEnabled()) {
				log.debug("here in new:"+inputBean.getRequestId());
			}
			request.setAttribute("uAction","new");
			BigDecimal requestId = inputBean.getRequestId();
			if (requestId == null) {
				CatalogInputBean bean = new CatalogInputBean();
				bean.setCompanyId("HAAS");
				bean.setCatalogCompanyId("HAAS");
				bean.setCatalogId("Global");
				bean.setFacilityId("Catalog");
				bean.setInventoryGroupDefault(inputBean.getInventoryGroup());
				if (inputBean.getItemId() == null) {
					requestId = process.buildNewRequest(bean,personnelBean,inputBean);
				}else {
					//if new size
					if (inputBean.getStartingView() == null || inputBean.getStartingView().equals(new BigDecimal(1))) {
						bean.setItemId(inputBean.getItemId());
						requestId = process.buildNewSizeRequest(bean,personnelBean,inputBean);
					}else {
						//else converting global catalog item to distribution catalog item
						bean.setItemId(inputBean.getItemId());
						Object[] result = process.convertGlobalItemToDistributionCatalog(bean,personnelBean,inputBean);
						requestId = (BigDecimal)result[0];
						String errorMsg = (String)result[1];
						if (!"OK".equalsIgnoreCase(errorMsg)) {
							request.setAttribute("tcmISError", errorMsg);
						}
						//overriding this so it can be treated like submit
						request.setAttribute("uAction","submit");
                        request.setAttribute("sourcePage",sourcePage);                        
                    }
				}
			}
			if (requestId != null) {
				request.setAttribute("requestId",requestId);
				request.setAttribute("newCatalogItemData",process.getNewCatalogItemData(requestId,personnelBean));
			}
		} else if (action.equals("submit") ) {
			if (log.isDebugEnabled()) {
				log.debug("here in submit:"+inputBean.getRequestId());
			}
			request.setAttribute("uAction","submit");
			Collection tabBeans = BeanHandler.getBeans((DynaBean) form,"catalogAddItemBean", new CatalogAddItemBean(),getTcmISLocale(request));

			String errorMsg = process.submitData(inputBean,tabBeans,personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute("tcmISError", errorMsg);
			}

			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				request.setAttribute("requestId",requestId);
				request.setAttribute("newCatalogItemData",process.getNewCatalogItemData(requestId,personnelBean));
			}
		} else {/* set up search page data*/

		}
		return mapping.findForward("success");
	}
}
