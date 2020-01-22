package com.tcmis.client.api.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.process.EcommerceCheckoutProcess;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/**
 * ***************************************************************************
 * Controller for EcommerceCheckoutAction
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class EcommerceCheckoutAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (form != null) {
			String uAction = request.getParameter("uAction");
			PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			if ("checkOut".equals(uAction)) {
                CatalogInputBean bean = new CatalogInputBean();
                BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
                bean.setPayloadId(personnelBean.getCustomerReturnId());
                bean.setPayloadTimestamp(personnelBean.getPayloadTimestamp());
                bean.setEcommerceLanguage(personnelBean.getEcommerceLanguage());
				Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
				EcommerceCheckoutProcess process = new EcommerceCheckoutProcess(this.getDbUser(request));
                process.sendShoppingCart(bean,beans);

                return mapping.findForward("callToServerCallback");
            }
		}
		return mapping.findForward("success");
	}
}
