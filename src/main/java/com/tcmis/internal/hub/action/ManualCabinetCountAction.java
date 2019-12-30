package com.tcmis.internal.hub.action;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ManualCabinetCountInputBean;
import com.tcmis.internal.hub.beans.CabinetBinItemViewBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;

import com.tcmis.internal.hub.process.ManualCabinetCountProcess;

/******************************************************************************
 * Controller for ManualCabinetCount
 * @version 1.0
	******************************************************************************/

public class ManualCabinetCountAction extends TcmISBaseAction 
{
 
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception 
	{
	 if (!this.isLoggedIn(request)) 
	 {
		 request.setAttribute("requestedPage", "cabinetcountmain");
		 request.setAttribute("requestedURLWithParameters",
				this.getRequestedURLWithParameters(request));
		 return (mapping.findForward("login"));
	 }

	 ManualCabinetCountProcess process = new ManualCabinetCountProcess(this.getDbUser(request));

	 PersonnelBean personnelBean	= (PersonnelBean)this.getSessionObject(request, "personnelBean");
	 BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	 if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetCount"))
	 {
	 return (mapping.findForward("nopermissions"));
	 }
	 
	 ManualCabinetCountInputBean bean = new ManualCabinetCountInputBean();
	 BeanHandler.copyAttributes(form, bean);
	 if ( ( (DynaBean) form).get("action") != null &&
		( (String) ( (DynaBean) form).get("action")).equals("update")) 
	 {
		checkToken(request);
		
		if (!personnelBean.getPermissionBean().hasFacilityPermission("CabinetMgmt",bean.getHubName(),null))
		{
		      request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
		      request.setAttribute("cabinetsCollection", process.getSearchData(bean));
		      return (mapping.findForward("success"));
		}
		
		DynaBean dynaBean = (DynaBean) form;
		Collection beans = BeanHandler.getBeans(dynaBean,
				"cabinetBinItemBean", new CabinetItemInventoryCountBean());
		
		 String msg =  process.insertCabinetItemInventoryCounts( beans, personnelId );
		  Vector<CabinetBinItemViewBean> cabinetBinItemViewBeanCol = (Vector<CabinetBinItemViewBean>) process.getSearchData(bean);
		  request.setAttribute("cabinetsCollection", cabinetBinItemViewBeanCol);
		  
		  if(!msg.equalsIgnoreCase(""))
			 {		
				 request.setAttribute("tcmISError", msg);
			 }
			 else
			 {
				 request.setAttribute("updateSuccess", "Y");
			 }
		
		 return mapping.findForward("success");
	 }
	 else if ( ( (DynaBean) form).get("action") != null &&
		( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("createExcel")) 
	 {
			 try {
				   this.setExcel(response,"ManualCabinetCount");
			      process.getExcelReport(bean, personnelBean.getLocale()).write(response.getOutputStream());
			      }
			    catch (Exception ex) {
			     ex.printStackTrace();
			     return mapping.findForward("genericerrorpage");
			    }
			return noForward;
	 }
	 else 
	 if ( ( (DynaBean) form).get("action") != null &&
					( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("search")) 
	 {
		 Object[] results = process.showResult(bean);
		 this.saveTcmISToken(request);
		 request.setAttribute("cabinetsCollection", results[0]);
		 return mapping.findForward("success");
	 }
	 else 		 
	 {
		 request.setAttribute("hubCabinetViewBeanCollection",
		 process.getAllLabelData( (PersonnelBean)this.getSessionObject(request,
		 "personnelBean")));
		 return mapping.findForward("success");
	 }
	}
		
class compareBeans implements java.util.Comparator {
	public int compare(Object a,Object b){
		return ((CabinetBinItemViewBean)a).getCabinetRowspan()-
		((CabinetBinItemViewBean)b).getCabinetRowspan();
	}
}
}