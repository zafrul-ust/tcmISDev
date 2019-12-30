package com.tcmis.client.seagate.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Encoder;

import com.tcmis.client.catalog.beans.CatalogCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.seagate.process.CxmlProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class PunchOutCatalogAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String uAction = request.getParameter("uAction");
		BigDecimal personnelId = null;

		OrderTrackingProcess orderTrackingProcess = null;
		String payloadId=request.getParameter("payloadId"); // to retrieve payloadID and logon id.payloadId	
		String logonId=request.getParameter("logonId");

		PersonnelBean user = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		if (form == null) {
			try {
				CxmlProcess process = new CxmlProcess(this.getDbUser(request));
				if(Integer.parseInt(process.getPayloadIdCount(payloadId,logonId)) > 0)
				{
					user = new PersonnelBean();
					user.setCompanyId(this.getClient(request));
					user.setLogonId(logonId);				
					LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
					user = loginProcess.loginWeb(user, false);
	                user.setDbUser(this.getDbUser(request));
	                this.setSessionObject(request, user, "personnelBean");
					Locale locale = null;
					locale = new Locale("en", "US");
					com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
					user.setLocale(locale);
				}
				else
					return mapping.findForward("nopermissions");
			} catch (Exception ex) {
				return mapping.findForward("nopermissions");
			}
			user.setCustomerReturnId(payloadId);
			orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			personnelId = new BigDecimal(user.getPersonnelId());
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			// enable utc ocicatalog only features
			user.setEcommerceLogon("Y");
			user.setEcommerceSource("SEAGATEIP");
			return mapping.findForward("application");
		}
		
		if(StringHandler.isBlankString(payloadId))
			payloadId = user.getCustomerReturnId();

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		personnelId = new BigDecimal(user.getPersonnelId());

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			CatalogInputBean bean = new CatalogInputBean();
			BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
			bean.setPayloadId(user.getCustomerReturnId());
			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));

			if (uAction != null && uAction.equals("showCart")) {
				Collection<CatalogCartBean> beans = BeanHandler.getBeans((DynaBean) form, "CatalogCartBean", new CatalogCartBean(), this.getLocale(request));
				request.setAttribute("CatalogCartBeanCollection", beans);
				return mapping.findForward("showcart");
			} else if ("search".equals(uAction)) {
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>) catalogProcess.getSearchResult(bean, user);
				Object[] results = catalogProcess.createRelationalObjects(c);
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);
				this.saveTcmISToken(request);
			} else if ("createXSL".equals(uAction)) {
				this.setExcel(response, "OCICatalogSearch");
				try {
					catalogProcess.getPartExcelReport(bean, user).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			} else if ("loaddata".equals(uAction)) {
				PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
				BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));

				request.setAttribute("specColl", catalogProcess.getSpecMenu(pbean));
				request.setAttribute("partInvColl", catalogProcess.getInventoryMenu(pbean));
				request.setAttribute("stockingReorder", catalogProcess.getStockingReorder(pbean));
				request.setAttribute("ImgLit", catalogProcess.getImgLit(pbean));

				return mapping.findForward("success");
			}
			else if ("seagateCheckOut".equals(uAction)) {
				log.debug("seagateCheckOut");
				log.debug("ecommerceSource:"+request.getParameter("ecommerceSource" ) ) ;
				
				CxmlProcess cxml = new CxmlProcess(this.getDbUser(request));
				cxml.setResourceLib("seagate");
				
				Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
				ShoppingCartProcess shoppingCartProcess = new ShoppingCartProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				BigDecimal prNumber = shoppingCartProcess.buildRequest(bean, beans, personnelId);
				
				String errorMsg = cxml.submitMR(user, prNumber, request.getRequestURL());				
				if (errorMsg == null || errorMsg.trim().equalsIgnoreCase("") || !errorMsg.equalsIgnoreCase("OK")) {
					log.debug("Exception occured while trying to submit the MR. Contact Haas Group International for further details.");
				}
				
				String xmlString = cxml.getPunchoutOrderMessage(payloadId, prNumber);
				request.setAttribute("postBodyUrlUtf8", xmlString);
				String browserPost = cxml.getBrowserPostFromPayloadId(payloadId);
				//String resposeCode = shoppingCartProcess.postHTMLShoppingCart(browserPost,xmlString);
				//log.debug("response code:"+resposeCode);
				request.setAttribute("browserPost", browserPost);
				//request.setAttribute("resposeCode", resposeCode);
				return mapping.findForward("callToServerCallback");
			} else if ("cancelSeagateCheckOut".equalsIgnoreCase(uAction)) {
				log.debug("cancelSeagateCheckOut");
				log.debug("ecommerceSource:"+request.getParameter("ecommerceSource" ) ) ;
				
				CxmlProcess cxml = new CxmlProcess(this.getDbUser(request));
				cxml.setResourceLib("seagate");
				String xmlString = "";
				request.setAttribute("postBodyUrlUtf8", xmlString);
				String browserPost = cxml.getBrowserPostFromPayloadId(payloadId);
				request.setAttribute("browserPost", browserPost);
				return mapping.findForward("callToServerCallback");
			}
			//search frame
			else {
				request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
				request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			}
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
