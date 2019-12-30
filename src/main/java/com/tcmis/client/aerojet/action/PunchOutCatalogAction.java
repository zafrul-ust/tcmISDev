package com.tcmis.client.aerojet.action;

import java.math.BigDecimal;
import sun.misc.BASE64Encoder;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.aerojet.process.CxmlProcess;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

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
//		CxmlProcess process = new CxmlProcess(this.getDbUser(request));
//		String[] ids = process.getLoginIdfromSid(sid);
// get payloadId and logong Id.		
//		String payloadID = "";
//		String logonId = "";
//		if( ids == null || ids[0] == null || ids[0].length() ==0 )
//			return mapping.findForward("nopermissions");
//		String logonId = ids[0];
		PersonnelBean user = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		if (form == null) {
			try {
				user = new PersonnelBean();
				user.setCompanyId(this.getClient(request));
				user.setLogonId(logonId);
//				personnelBean.setClearTextPassword(request.getParameter("passwd"));
				LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
				user = loginProcess.loginWeb(user, false);
                user.setDbUser(this.getDbUser(request));
                this.setSessionObject(request, user, "personnelBean");
				Locale locale = null;
				locale = new Locale("en", "US");
				com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
				user.setLocale(locale);
			} catch (Exception ex) {
				return mapping.findForward("nopermissions");
			}

			orderTrackingProcess = new OrderTrackingProcess(this.
				 getDbUser(request), this.getTcmISLocaleString(request));
			personnelId = new BigDecimal(user.getPersonnelId());
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			// enable utc ocicatalog only features
			request.setAttribute("showcart", "true");
			request.setAttribute("showmenu", "true");
			request.setAttribute("ecommerceLogon", "Y");
// this should be something else??
			request.setAttribute("ecommerceSource", "AEROJETIP");
			return mapping.findForward("success");
		}

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		System.out.print("locale==" + request.getSession().getAttribute("tcmISLocale"));
		user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		orderTrackingProcess = new OrderTrackingProcess(this.
			 getDbUser(request), this.getTcmISLocaleString(request));
		personnelId = new BigDecimal(user.getPersonnelId());

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			CatalogInputBean bean = new CatalogInputBean();

			BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));

//	 for( String key: (java.util.Set<String>) request.getParameterMap().keySet() ) {
//		 System.out.println(key+":"+request.getParameter(key));
//	 }
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
			else if ("aerojetCheckOut".equals("aerojetCheckOut")) {
				/*
				java.util.Enumeration e = request.getParameterNames();
				while(e.hasMoreElements()) {
					String name = (String) e.nextElement();
					log.debug("Name:"+name+":value:"+request.getParameter(name));
				}
				 */
				System.out.println("ecommerceSource:"+request.getParameter("ecommerceSource" ) ) ;
				{
				Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
				ShoppingCartProcess shoppingCartProcess = new ShoppingCartProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				BigDecimal prNumber = shoppingCartProcess.buildRequest(bean,beans,personnelId);
				CxmlProcess cxml = new CxmlProcess(this.getDbUser(request));
				cxml.setResourceLib("aerojet");//"aerojet"
		// use sid , a ramdon generated id.
				cxml.setNewProc(true);
				String xmlString = cxml.getPunchoutOrderXML(payloadId);
				BASE64Encoder encoder = new BASE64Encoder();
				String xmlStringBase64Encoded = encoder.encode(xmlString.getBytes());
//				String posturl = "https://qa.tcmis.com/tcmIS/aerojet/purchorder.do";
//				String postStr = "<html><head></head><body><form  NAME=\"puchoutiproc\" action=\"" + posturl + "\" method=\"POST\">\n<input type=\"hidden\" name=\"cxml-urlencoded\" value=\"" + xmlString + "\"></form></body>";
				request.setAttribute("postBody", xmlString);
				request.setAttribute("postBodyBase64", xmlStringBase64Encoded);
				String browserPost = cxml.getBrowserPostFromPayloadId(payloadId);
//				String resposeCode = shoppingCartProcess.postHTMLShoppingCart(browserPost,xmlString);
//				log.debug("response code:"+resposeCode);
				request.setAttribute("browserPost", browserPost);
//				request.setAttribute("resposeCode", resposeCode);
				return mapping.findForward("callToServerCallback");
//				return this.noForward;//mapping.findForward("mrScreen");
				}
			}
//	 search frame
			else {
				request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
				request.setAttribute("myDefaultFacilityId",orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			}
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
