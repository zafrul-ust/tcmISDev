package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CatalogRQCEditShelfLifeBean;
import com.tcmis.internal.hub.process.CatalogRQCEditShelfLifeProcess;

public class CatalogRQCEditShelfLifeAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String next = "success";
		
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receivingqcchecklist");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {
			Locale locale = getTcmISLocale();
			PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			CatalogRQCEditShelfLifeBean input = new CatalogRQCEditShelfLifeBean(form, locale, "datetimeformat");
            CatalogRQCEditShelfLifeProcess process = new CatalogRQCEditShelfLifeProcess(getDbUser(),locale);
            
            if (input.isUpdate()) {
            	Collection<CatalogRQCEditShelfLifeBean> itemParts = BeanHandler.getBeans((DynaBean) form, "part", "datetimeformat", new CatalogRQCEditShelfLifeBean(), locale);
            	process.updateShelfLifeStorageTemp(input, user, itemParts);
            	request.setAttribute("updateComplete", true);
            }
        	
            process.setFormInfo(request, input);
		}
		
		return mapping.findForward(next);
	}
}
