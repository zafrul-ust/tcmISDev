package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

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
import com.tcmis.internal.catalog.beans.CatalogItemDetailQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcViewBean;
import com.tcmis.internal.catalog.process.ItemMatcherProcess;

public class ItemMatcherAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "itemmatcher");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			if (user != null && (user.getPermissionBean().hasFacilityPermission("catalogAddItemQc", null,null) ||
					user.getPermissionBean().hasUserPagePermission("catAddVendorQueue"))) {
				try {
					Locale locale = getTcmISLocale();
		
		            // Get the user/page input in a usable form as an input bean
		            CatalogItemDetailQcInputBean input = new CatalogItemDetailQcInputBean(form, locale, "datetimeformat");
		            Collection<CatalogItemQcViewBean> components = BeanHandler.getBeans((DynaBean) form, "part", "datetimeformat", new CatalogItemQcViewBean(), locale);
		            ItemMatcherProcess process = new ItemMatcherProcess(getDbUser(),locale);
		
		            // Stick the bean back in the session for the hidden field tag
		            request.setAttribute("inputBean", input);
		            
		            if (input.isMatch()) {
		            	request.setAttribute("componentCollection", components);
		            	request.setAttribute("itemMatchCollection", process.findMatches(input, components));
		            }
		            else if (input.isSelect()) {
		            	process.updateItemId(input, false);
		            	request.setAttribute("unload", true);
		            }
		            else if (input.isNewItem()) {
		            	input.setItemId(new BigDecimal(process.createNewItem()));
		            	process.updateItemId(input, true);
		            	request.setAttribute("unload", true);
		            	
		            	PrintWriter out = response.getWriter();
						out.write(input.getItemId().toString());
						out.close();
		            }
				}
				catch (BaseException be) {
                    processExceptions(request, mapping, be);
                }
                catch (Exception e) {
                    request.setAttribute("tcmisError", e.getMessage());
                }
			}
			else {
                next = mapping.findForward("nopermissions");
            }
		}
		
		return next;
	}
}
