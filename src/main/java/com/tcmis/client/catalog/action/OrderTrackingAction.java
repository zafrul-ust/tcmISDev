package com.tcmis.client.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.client.catalog.beans.ApplicationObjBean;
import com.tcmis.client.catalog.beans.OrderTrackingInputBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackPrOrderTrackBean;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;


/******************************************************************************
 * Controller for order tracking
 * @version 1.0
 ******************************************************************************/
public class OrderTrackingAction extends TcmISBaseAction {

  @SuppressWarnings({ "rawtypes", "unchecked" })
public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "ordertrackingmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    
    if (!personnelBean.getPermissionBean().hasUserPagePermission("orderTracking"))
    {
      return (mapping.findForward("nopermissions"));
    }
    
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));
    
    //if form is not null we have to perform some action
    if (form != null) {
       this.saveTcmISToken(request);
      //copy date from dyna form to the data form
      OrderTrackingInputBean bean = new OrderTrackingInputBean();
      BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
      
      String action = (String) ((DynaBean) form).get("action");

      //Since facilityId value from dropdown is actually the string 'facility_group_id.facility_id', so to use it in featureReleased tag, separate the values
      String[] facilityIdSubStrs = StringHandler.isBlankString(bean.getFacilityId()) ? new String[0] : bean.getFacilityId().split("\\.");
      String featureReleaseFacilityId = facilityIdSubStrs.length > 0 ? facilityIdSubStrs[facilityIdSubStrs.length - 1] : "";
      //check what button was pressed and determine where to go
      if ("search".equalsIgnoreCase(action)) {
        bean.setPersonnelId(personnelBean.getPersonnelId());
        // check search parameters
        if ("Y".equalsIgnoreCase(bean.getNeedMyApproval())) {
            request.setAttribute("pkgOrderTrackPrOrderTrackBeanCollection", orderTrackingProcess.getsearchResult(bean, personnelBean));
        } else {
        	Collection c = orderTrackingProcess.getsearchResult(bean, personnelBean);
        	request.setAttribute("pkgOrderTrackPrOrderTrackBeanCollection", c );
        	Object[] results = orderTrackingProcess.getExtraInfo(c);
        	request.setAttribute("qualityIdLabelColumnHeader", results[0]);
        	request.setAttribute("catPartAttrColumnHeader", results[1]);
         }
        request.setAttribute("featureReleaseFacilityId", featureReleaseFacilityId);
        request.setAttribute("companyModule", ModuleUtils.getInstance().getModuleConfig(request).getPrefix()); 
        return (mapping.findForward("showresults"));
      } else if ("createExcel".equalsIgnoreCase(action)) {
        this.setExcel(response,"OrderTracking");
        bean.setPersonnelId(personnelBean.getPersonnelId());
        bean.setButtonCreateExcel("createExcel");
        bean.setMaxData(new BigDecimal(1000000));
          orderTrackingProcess.getExcelReport(bean, personnelBean,ModuleUtils.getInstance().getModuleConfig(request).getPrefix(), featureReleaseFacilityId).write(response.getOutputStream());	      
	        log.debug("CREATE EXCEL COMPLETED");
	      return noForward;
      } else if ("getApplicationColl".equalsIgnoreCase(action)) {
          
    	  Vector<ApplicationObjBean> applicationColl =  (Vector<ApplicationObjBean>)orderTrackingProcess.getApplicationColl(bean.getCompanyId(), bean.getFacilityId(), personnelBean.getPersonnelId());
		    	
		  JSONObject results = new JSONObject();
		    	
		  JSONArray resultsArray = new JSONArray();
		  for (ApplicationObjBean applicationBean : applicationColl) {
				JSONObject docJSON = new JSONObject();
				docJSON.put("application", applicationBean.getApplication());
				docJSON.put("applicationDesc", applicationBean.getApplicationDesc());
				resultsArray.put(docJSON);
		  }
		  results.put("applicationColl", resultsArray);
				
				// Write out the data
		  PrintWriter out = response.getWriter();
		  out.write(results.toString(3));
		  out.close();
		    	
		  return noForward;
      } else if ("editOrderName".equalsIgnoreCase(action)) {
    	  PkgOrderTrackPrOrderTrackBean resultBean = new PkgOrderTrackPrOrderTrackBean();
    	  BeanHandler.copyAttributes(form, resultBean, getTcmISLocale(request));
    	  PrintWriter out = response.getWriter();
    	  out.write(orderTrackingProcess.editOrderName(resultBean));
    	  out.close();
    	  return noForward;
      } else { 
        //log initial data for dropdown
        //default delivery days
        request.setAttribute("deliveredSinceDays","30");
        request.setAttribute("releasedSinceDays","30");
        request.setAttribute("cancelledSinceDays","30");
          //get user name
          request.setAttribute("requestorId",personnelId.toString());
          request.setAttribute("requestorName",personnelBean.getLastName()+", "+personnelBean.getFirstName());
          Collection tmpCol = personnelBean.getMyWorkareaFacilityOvBeanCollection();
          
      
          if (tmpCol == null || tmpCol.size() == 0) {
        	  if("Radian".equals(personnelBean.getCompanyId()))
        		  personnelBean.setMyWorkareaFacilityOvBeanCollection(orderTrackingProcess.getMyFacAppDropdownDataTest(personnelId.intValue()));
        	  else
        		  personnelBean.setMyWorkareaFacilityOvBeanCollection(orderTrackingProcess.getMyFacAppDropdownData(personnelId.intValue()));
          }
        
			 tmpCol = personnelBean.getMyCompanyDefaultFacilityIdCollection();
		 	 if (tmpCol == null || tmpCol.size() == 0) {
		 		personnelBean.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(personnelBean.getPersonnelId()));
		 	 }
			 //make sure that user has at least one work area
		    tmpCol = personnelBean.getMyWorkareaFacilityOvBeanCollection();
		    
		    if (tmpCol == null || tmpCol.size() == 0) {
            return (mapping.findForward("nopermissions"));
          }else {
		      return (mapping.findForward("success"));
		    }
      }
    }
    return mapping.findForward("success");
  }
}