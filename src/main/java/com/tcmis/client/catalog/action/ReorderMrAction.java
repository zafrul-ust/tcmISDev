package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.ReorderMrInputBean;
import com.tcmis.client.catalog.process.ReorderMrProcess;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class ReorderMrAction extends TcmISBaseAction {
	private void getMrData(HttpServletRequest request, ReorderMrInputBean inputBean, ReorderMrProcess process) throws Exception {
		Collection<ShoppingCartBean> beanColl = process.getMrData(inputBean);
		request.setAttribute("shoppingCartBeanCollection", beanColl);
		inputBean.setTotalLines(beanColl.size());
		//when loaded for the first time, only prNumber is available
		if (!beanColl.isEmpty() && "Y".equalsIgnoreCase(inputBean.getIsFirstLoad())) {
			ShoppingCartBean firstBean = beanColl.iterator().next();
			inputBean.setFacilityId(firstBean.getFacilityId());
			inputBean.setApplicationId(firstBean.getOriginalApplication());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "reordermr");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		
		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = user.getPersonnelIdBigDecimal();
		
		ActionForward next = mapping.findForward("success");
		if (form != null) {
			ReorderMrInputBean inputBean = new ReorderMrInputBean(form, getLocale());
			String dbUser = getDbUser(request);
			String localeString = getTcmISLocaleString(request);
			ReorderMrProcess process = new ReorderMrProcess(dbUser, localeString);
			if (inputBean.isSearch()) {
				getMrData(request, inputBean, process);
			} else if (inputBean.isCheckOut()) {
				CatalogInputBean catalogBean = new CatalogInputBean();
				BeanHandler.copyAttributes(inputBean, catalogBean);
				Collection<ShoppingCartBean> cartBeanColl = BeanHandler.getBeans((DynaBean)form, "shoppingCartBean", new ShoppingCartBean(), this.getTcmISLocale(request), "okDoUpdate");
				BigDecimal prNumber;
				try {
					prNumber = new ShoppingCartProcess(dbUser, localeString).buildRequest(catalogBean, cartBeanColl, personnelId);
				} catch (Exception e) {
					log.debug(e);
					prNumber = new BigDecimal("-1");
				}
			    PrintWriter out = response.getWriter();
				out.write(prNumber.toPlainString());
				out.close();
				next = noForward;
			} else
	            request.setAttribute("myWorkareaFacilityViewBeanCollection", process.getDropdownCollection(personnelId));
			
			request.setAttribute("searchInput", inputBean);
		}

		return next;
	}
}
