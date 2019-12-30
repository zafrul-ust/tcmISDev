package com.tcmis.supplier.shipping.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.process.PackProcess;
import com.tcmis.supplier.shipping.process.ShipmentSelectionProcess;

/******************************************************************************
 * Controller for cabinet labels
 * 
 * @version 1.0
 ******************************************************************************/
public class PackAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{
		if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "packmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("pack"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

	    String uAction = request.getParameter("uAction");
		if ("showUploadList".equalsIgnoreCase(uAction)) {
			return (mapping.findForward("showupload"));
		}			
		String userAction = null;
		if (form == null
				|| (userAction = ((String) ((DynaBean) form).get("userAction"))) == null)
		{
			return (mapping.findForward("success"));
		}
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		DynaBean dynaBean = (DynaBean) form;
		SupplierShippingInputBean inputbean = new SupplierShippingInputBean();
		BeanHandler
				.copyAttributes(dynaBean, inputbean, getTcmISLocale(request));
		inputbean.setPersonnelId(personnelId);
		if (userAction.equals("display"))
		{
			ShipmentSelectionProcess process = new ShipmentSelectionProcess(
					this.getDbUser(request), getTcmISLocaleString(request));
			request.setAttribute("supplierLocationOvBeanCollection",
					process.getDLASearchDropDown(personnelId));
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		PackProcess process = new PackProcess(this.getDbUser(request),
				getTcmISLocaleString(request));
		if (userAction.equals("search"))
		{
			Object[] results = process.showResult(inputbean);
			request.setAttribute("suppPackingViewBeanCollection", results[0]);
			request.setAttribute("rowCountPart", results[1]);
			request.setAttribute("rowCountItem", results[2]);
			this.saveTcmISToken(request);
			request.setAttribute("carrierBeanColl",
					process.getValidCarriers(inputbean));
		}
		else
			if (userAction.equals("update"))
			{
				if (!this.isTcmISTokenValid(request, true))
				{
					BaseException be = new BaseException(
							"Duplicate form submission");
					be.setMessageKey("error.submit.duplicate");
					throw be;
				}
				this.saveTcmISToken(request);
				Collection beans = BeanHandler.getBeans(dynaBean,
						"supplierPackingViewBean",
						new SupplierPackingViewBean(), getTcmISLocale(request));
				String errorCode = process.update(beans);
				Object[] results = process.showResult(inputbean);
				request.setAttribute("suppPackingViewBeanCollection",
						results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);

				request.setAttribute("carrierBeanColl",
						process.getValidCarriers(inputbean));
				request.setAttribute("tcmISError", errorCode);
			}
			else
				if (userAction.equals("generateInspAsn"))
				{
					if (!this.isTcmISTokenValid(request, true))
					{
						BaseException be = new BaseException(
								"Duplicate form submission");
						be.setMessageKey("error.submit.duplicate");
						throw be;
					}
					this.saveTcmISToken(request);
					Collection beans = BeanHandler.getBeans(dynaBean,
							"supplierPackingViewBean",
							new SupplierPackingViewBean(),
							getTcmISLocale(request));
					String errorCode = process.generateOriginAsn(beans);
					Object[] results = process.showResult(inputbean);
					request.setAttribute("suppPackingViewBeanCollection",
							results[0]);
					request.setAttribute("rowCountPart", results[1]);
					request.setAttribute("rowCountItem", results[2]);

					request.setAttribute("carrierBeanColl",
							process.getValidCarriers(inputbean));
					request.setAttribute("tcmISError", errorCode);
				}
				else
					if (userAction.equals("createExcel"))
					{
						Vector v = (Vector) process.getSearchResult(inputbean);
						this.setExcel(response, "Pack");
						process.getExcelReportNoSplit(
								v,
								(java.util.Locale) request.getSession()
										.getAttribute("tcmISLocale")).write(
								response.getOutputStream());
						return noForward;
					}
					else
						if (userAction.equals("printTable"))
						{
							this.setExcelHeader(response);
							// TCMISDEV-264: Convert the Pack & Confirm Report
							StringBuffer reportURL = RequestUtils
									.createServerStringBuffer(
											request.getScheme(),
											request.getServerName(),
											request.getServerPort());
							StringBuilder reportRequest = new StringBuilder(
									reportURL.toString());
							reportRequest
									.append(IHaasConstants.RPT_HAAS_REPORT_URI);
							reportRequest.append("?pr_supplier_id=");
							reportRequest.append(inputbean.getSupplier());
							reportRequest.append("&pr_shipFrom_ids=");
							reportRequest.append(process
									.getShipFromLocationIds(inputbean).toString());
							reportRequest.append("&pr_filter_cond=");
							reportRequest.append(process
									.getFilterCondition(inputbean));
							reportRequest
									.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
							reportRequest.append("packConfirmReportDefinition");
							reportRequest.append("&locale=");
							reportRequest.append(request.getLocale());
							response.sendRedirect(response
									.encodeRedirectURL(reportRequest.toString()));
							return null;
						}
				      else if ("uploadSerialNumExcel".equalsIgnoreCase(userAction)) {
				    	  FileUploadBean fileBean = new FileUploadBean();
				    	  BeanHandler.copyAttributes(form, fileBean);
				        //check if a file was uploaded
				    	  if(fileBean.getTheFile() != null && !FileHandler.isValidUploadFile(fileBean.getTheFile())) {
				    	    	request.setAttribute("errorMessage", "Invalid file.");
				    	  } else if(fileBean.getTheFile() != null) {
				         	
				    		 Collection errorMessage = process.uploadSerialNumExcel(fileBean,inputbean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale"));
				    		  if (errorMessage == null)
				    		  {
				    			  request.setAttribute("result", "ok");
				    		  }
				    		  else
				    		  {
				    			  request.setAttribute("errorMessage", errorMessage);
				    		  }
				    	  }   
				    	  return (mapping.findForward("showupload"));
				      }

				      else if ("createSerialNumExcel".equalsIgnoreCase(userAction)) {
							Vector v = (Vector) process.getSerialNumsByDelTick(inputbean);
							this.setExcel(response, "Serial Numbers");
							process.getSerialNumExcel(
									v,
									(java.util.Locale) request.getSession()
											.getAttribute("tcmISLocale")).write(
									response.getOutputStream());
							return noForward;
							  }
				      else if ("createSerialNumExcelTemplate".equalsIgnoreCase(userAction)) {
							this.setExcel(response, "Serial Numbers");
							process.getSerialNumExcelTemplate(
									(java.util.Locale) request.getSession()
											.getAttribute("tcmISLocale")).write(
									response.getOutputStream());
							return noForward;
							  }
		
		return (mapping.findForward("success"));
	}
}
