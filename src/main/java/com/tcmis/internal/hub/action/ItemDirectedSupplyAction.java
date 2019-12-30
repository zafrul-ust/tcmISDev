package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeViewBean;
import com.tcmis.internal.hub.beans.ItemDirectSupplyInputBean;
import com.tcmis.internal.hub.beans.ItemDirectSupplyViewBean;
import com.tcmis.internal.hub.process.ItemDirectedSupplyProcess;
import com.tcmis.internal.supply.beans.DBuyConsolidationFreqViewBean;

public class ItemDirectedSupplyAction extends TcmISBaseAction {
	   
	   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	                                      HttpServletRequest request, HttpServletResponse response)
	     throws BaseException, Exception {

			if (!this.isLoggedIn(request)) {
				request.setAttribute("requestedPage", "itemdirectsupplymain");
				request.setAttribute("requestedURLWithParameters", this
						.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
			}

	   	    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			String forward = "success";
			Vector errors = new Vector();
			PermissionBean permissionBean = personnelBean.getPermissionBean();
		    if ( !permissionBean.hasUserPagePermission("itemDirectSupply"))
		    {
		      return (mapping.findForward("nopermissions"));
		    }
		    
		    String userAction = null;
			if (form == null
					|| (userAction = ((String) ((DynaBean) form).get("uAction"))) == null) {
				return (mapping.findForward("success"));
			}
			
			ItemDirectedSupplyProcess process = new ItemDirectedSupplyProcess(this.getDbUser(request),getTcmISLocaleString(request));
			ItemDirectSupplyInputBean bean = new ItemDirectSupplyInputBean();
	        BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
	        
			if (userAction.equals("search")) {
				
	           this.saveTcmISToken(request);
	           request.setAttribute("ItemDirectSupplyBeanColl", process.getSearchData(bean));
			}
		    else if ( userAction.equals("createExcel")) {
				try {
			    	DynaBean dynaBean = (DynaBean) form;
			    	this.setExcel(response,"item_direct_supply");
					process.getExcelReport(bean, response.getOutputStream(), personnelBean.getLocale());
				}
				catch (Exception ex) {
				 ex.printStackTrace();
				 return mapping.findForward("genericerrorpage");
				}
				return noForward;
		    }
		    else if ( userAction.equals("delete")) {
		    	checkToken(request);

				// get the data from grid
				Collection<ItemDirectSupplyViewBean> beans = BeanHandler.getBeans((DynaBean) form, "itemDirectSupplyBean", new ItemDirectSupplyViewBean(), getTcmISLocale(request));

				String error = process.delete(beans, personnelBean);
				if(!StringHandler.isBlankString(error))
				
					errors.add(error);
				else
					errors = null;
				
				request.setAttribute("ItemDirectSupplyBeanColl", process.getSearchData(bean));
				request.setAttribute("tcmISErrors", errors);
				return (mapping.findForward("success"));
		    }
		    else if ( userAction.equals("new")){
		    	this.saveTcmISToken(request);
		    	if (!permissionBean.hasOpsEntityPermission("itemDirectSupply", bean.getOpsEntityId(), null))
			    {
			      return (mapping.findForward("nopermissions"));
			    }
		    	
		    	Collection allOpsHubIgOvBeanCollection = process.getAllOpsHubIgData(personnelBean);
    	    	request.setAttribute("allOpsHubIgOvBeanCollection", allOpsHubIgOvBeanCollection);
		    	return mapping.findForward("shownewitem");
		    }
		    else if ( userAction.equals("addnewitem")){
		    	checkToken(request);
		    	ItemDirectSupplyViewBean beans = new ItemDirectSupplyViewBean();
		        BeanHandler.copyAttributes(form, beans, getTcmISLocale(request));
		        
		        String errorMsgs = process.addnewitem(beans,personnelBean.getPersonnelIdBigDecimal());
				
				if(errorMsgs.equalsIgnoreCase("OK") )
				{
					request.setAttribute("closeNewItemWin", "Y");
					
				}
				else
				{
					request.setAttribute("tcmISErrors", errorMsgs);
					request.setAttribute("closeNewItemWin", "N");				
				}
		        return mapping.findForward("shownewitem"); 
		    }
		    

			return mapping.findForward(forward);
	  }
	}

