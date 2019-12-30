package com.tcmis.client.catalog.action;

import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.catalog.process.SearchMsdsProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.process.MsdsViewerProcess;

import java.util.Collection;

/******************************************************************************
 * Controller for Search for MSDS page
 * @version 1.0
 ******************************************************************************/

public class SearchMsdsAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "searchmsdsmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		String userAction = request.getParameter("uAction");
		if (form == null || userAction == null) {
			return (mapping.findForward("success"));
		}

        CatalogInputBean bean = new CatalogInputBean();
		BeanHandler.copyAttributes(form, bean);
        SearchMsdsProcess searchMsdsProcess = new SearchMsdsProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        if ( "search".equals(userAction)) {
            bean.setSourcePage(request.getParameter("calledFrom"));
            Collection coll = searchMsdsProcess.searchMsds(bean);
            request.setAttribute("itemCatalogBeanCollection", coll);
            if(coll != null && coll.size() > 0) {
                MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
                request.setAttribute("showFacilityUseCode",process.showFacilityUseCode(bean.getFacilityId()));
                CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),getTcmISLocaleString(request));
                request.setAttribute("showMSDS",catalogProcess.isFeatureReleased("ALL","ShowKitMaterialMsds"));
                request.setAttribute("showCASNumber",catalogProcess.isFeatureReleased("ALL","ShowCASNumber"));
            }else {
                request.setAttribute("showFacilityUseCode","N");
                request.setAttribute("showMSDS","N");
                request.setAttribute("showCASNumber","N");
            }

        }else if ("createXSL".equals(userAction) || "createExcel".equals(userAction)) {
			try {
				this.setExcel(response,"Search MSDS");
                PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
                searchMsdsProcess.getMsdsExcelReport(bean,personnelBean).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}

		return (mapping.findForward("success"));
    }
  
}

