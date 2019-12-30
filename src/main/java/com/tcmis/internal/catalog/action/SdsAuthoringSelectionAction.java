package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.SdsAuthoringSelectionInputBean;
import com.tcmis.internal.catalog.beans.SdsAuthoringSelectionViewBean;
import com.tcmis.internal.catalog.process.SdsAuthoringSelectionProcess;

public class SdsAuthoringSelectionAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Locale locale = getTcmISLocale();
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		SdsAuthoringSelectionProcess process = new SdsAuthoringSelectionProcess(personnelBean.getPersonnelIdBigDecimal(), getDbUser());
		SdsAuthoringSelectionInputBean input = new SdsAuthoringSelectionInputBean();
		BeanHandler.copyAttributes((DynaBean)form, input, "datetimeformat", locale);
		if (input.isSelect()) {
			request.setAttribute("localeCollection", process.selectLocales(input));
			request.setAttribute("companyFacCatalogCollection", process.getCompanyFacCatalogCollection());
		}
		else if (input.isRequest()) {
			CatalogAddRequestProcess catAddReqProcess = new CatalogAddRequestProcess(getDbUser());
			CatalogInputBean catalogInput = new CatalogInputBean();
			BeanHandler.copyAttributes((DynaBean)form, catalogInput, locale);
			Collection<SdsAuthoringSelectionViewBean> languages = BeanHandler.getBeans((DynaBean) form, "localeDiv", new SdsAuthoringSelectionViewBean(), locale, "grab");
			request.setAttribute("generatedRequest", catAddReqProcess.sdsAuthoring(catalogInput, input.getRevisionDate(), personnelBean, languages));
		}
		return mapping.findForward("success");
	}

}
