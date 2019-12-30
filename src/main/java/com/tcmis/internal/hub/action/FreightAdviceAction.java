package com.tcmis.internal.hub.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
import com.tcmis.internal.hub.beans.FreightAdviceInputBean;
import com.tcmis.internal.hub.beans.FreightAdviceViewBean;
import com.tcmis.internal.hub.process.FreightAdviceProcess;

/******************************************************************************
 * Controller for FreightAdvice
 * @version 1.0
 ******************************************************************************/

public class FreightAdviceAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "freightadvicemain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("freightAdvice"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		if (form != null) {
			FreightAdviceProcess process = new FreightAdviceProcess(this.getDbUser(request),getTcmISLocaleString(request));
			FreightAdviceInputBean inputBean = new FreightAdviceInputBean();
			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

				BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

			if ( ( (DynaBean) form).get("action") != null &&
					( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("cancel")) {
				log.debug("cancel..");
				if (!this.isTcmISTokenValid(request, true)) {
					BaseException be = new BaseException("Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}
				this.saveTcmISToken(request);
				Collection c = new Vector();
				DynaBean dynaBean = (DynaBean) form;
				List freightAdviceInputBeans = (List) dynaBean.get("freightAdviceInputBean");
				//log.debug("cabinetManagementInputBeans:" + cabinetManagementInputBeans);
				if (freightAdviceInputBeans != null) {
					Iterator iterator = freightAdviceInputBeans.iterator();
					//int count = 0;
					while (iterator.hasNext()) {
						FreightAdviceInputBean freightAdviceInputBean = new
						FreightAdviceInputBean();
						org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
								commons.beanutils.LazyDynaBean) iterator.next();
						//log.debug(lazyBean.get("packingGroupId"));
						BeanHandler.copyAttributes(lazyBean, freightAdviceInputBean, getTcmISLocale(request));
						//log.debug("bean:" + inputBean);
						c.add(freightAdviceInputBean);
					}
				}
				process.cancel(c);
				request.setAttribute("freightAdviceViewBeanCollection",  process.getSearchData(inputBean));
			}
			else if ( ( (DynaBean) form).get("submitSearch") != null &&
					( (String) ( (DynaBean) form).get("submitSearch")).length() > 0
					|| (
							( (DynaBean) form).get("action") != null &&
							( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("search")	)
			) {
				this.saveTcmISToken(request);
				log.debug("searching..");

				request.setAttribute("freightAdviceViewBeanCollection",  process.getSearchData(inputBean));
			}
			else if ( ( (DynaBean) form).get("action") != null &&
					( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("createExcel") ) {
				try {
					this.setExcel(response,"FreightAdvice");
					process.getExcelReport(inputBean,personnelBean.getLocale()).write(response.getOutputStream());
				}
				catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			}
			else if ( ( (DynaBean) form).get("action") != null &&
					( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("changefreightadvice") ) {
				request.setAttribute("freightAdviceViewBeanCollection",  process.getSearchData(inputBean));
				request.setAttribute("refresh", "");
				return (mapping.findForward("changefreightadvice"));
			}
			else if ( ( (DynaBean) form).get("changefreightadviceok") != null && ( (String) ( (DynaBean) form).get("changefreightadviceok")).length() > 0) {
				String errorMsg = "";
				//DynaBean dynaBean = (DynaBean) form;
				//Collection beans = BeanHandler.getBeans(dynaBean,"freightAdviceInputBean", new FreightAdviceViewBean(), getTcmISLocale(request));
				log.debug("change");
				FreightAdviceViewBean bean = new FreightAdviceViewBean();
				BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
				errorMsg = process.changeFreightAdvice(bean,personnelId);
				request.setAttribute("refresh", "refresh");
				if (!"OK".equalsIgnoreCase(errorMsg)) {
					request.setAttribute("messageToUser", errorMsg);
				}else {
					request.setAttribute("messageToUser", "");
				}
				if (log.isDebugEnabled()) {
					log.debug("change done:"+errorMsg+"*");
				}
				return (mapping.findForward("changefreightadvice"));
			}
			else {
				log.debug("get status collection..");
				request.setAttribute("vvFreightAdviceStatusBeanCollection", process.getShipStatusCollection());
			}
		}
		return (mapping.findForward("success"));
	}
}

