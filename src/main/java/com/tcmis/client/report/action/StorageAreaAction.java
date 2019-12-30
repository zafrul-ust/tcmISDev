package com.tcmis.client.report.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.report.beans.FacAreaBlgFloorRmStgView;
import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Controller for Storage Area
 * @version 1.0
     ******************************************************************************/
public class StorageAreaAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
	    
	  if (!this.isLoggedIn(request)) {
	      request.setAttribute("requestedPage", "storagearea");
	      request.setAttribute("requestedURLWithParameters",
	                           this.getRequestedURLWithParameters(request));
	      return (mapping.findForward("login"));
	  }
	  request.setAttribute("startSearchTime", new Date().getTime());
      String companyId = request.getParameter("companyId");
      String facilityId = request.getParameter("facilityId");
	  String areaId = request.getParameter("areaId");
	  String buildingId = request.getParameter("buildingId");
	  String floorId = request.getParameter("floorId");
      String roomId = request.getParameter("roomId");
      String reportingEntityId = request.getParameter("reportingEntityId");
      String deptId = request.getParameter("deptId");
      String reportType = request.getParameter("reportType");
      String status = request.getParameter("status");
      String flammabilityControlZone = request.getParameter("flammabilityControlZoneId");
      String vocZone = request.getParameter("vocZoneId");
      
      GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(this.getDbUser(request)), new FacAreaBlgFloorRmStgView());
	  SearchCriteria criteria = new SearchCriteria();
      if(!StringHandler.isBlankString(companyId))
        criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);

      if(!StringHandler.isBlankString(facilityId))
		  criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);

      if(!StringHandler.isBlankString(areaId)){
		  areaId = areaId.replaceAll("\\|", ",");
		  criteria.addCriterion("areaId", SearchCriterion.IN, areaId);
	  }

      if(!StringHandler.isBlankString(buildingId)){
		  buildingId = buildingId.replaceAll("\\|", ",");
		  criteria.addCriterion("buildingId", SearchCriterion.IN, buildingId);
	  }
	  if(!StringHandler.isBlankString(floorId))
		  criteria.addCriterion("floorId", SearchCriterion.EQUALS, floorId);

      if(!StringHandler.isBlankString(roomId))
                criteria.addCriterion("roomId", SearchCriterion.EQUALS, roomId);

      if(!StringHandler.isBlankString(reportingEntityId))
          criteria.addCriterion("reportingEntityId", SearchCriterion.EQUALS, reportingEntityId);

      if(!StringHandler.isBlankString(deptId)){
          deptId = deptId.replaceAll("\\|", ",");
          criteria.addCriterion("deptId", SearchCriterion.IN, deptId);
      }

      if(!StringHandler.isBlankString(flammabilityControlZone)){
    	  flammabilityControlZone = flammabilityControlZone.replaceAll("\\|", ",");
		  criteria.addCriterion("flammabilityControlZoneId", SearchCriterion.IN, flammabilityControlZone);
	  }

      if(!StringHandler.isBlankString(vocZone)){
    	  vocZone = vocZone.replaceAll("\\|", ",");
		  criteria.addCriterion("vocZoneId", SearchCriterion.IN, vocZone);
	  }
	  
      SortCriteria sort = new SortCriteria();
      sort.addCriterion("storageAreaDesc");
      if(reportType.equalsIgnoreCase("AdHocInventory")) {
    	  criteria.addCriterion("reportInventory", SearchCriterion.EQUALS, "Y");
    	  request.setAttribute("storageAreaCollection", genericSqlFactory.select(criteria, sort, "fac_area_blg_floor_rm_stg_view"));
      }else{
    	  if (reportType.equalsIgnoreCase("AdHocUsage"))
    		  criteria.addCriterion("reportUsage", SearchCriterion.EQUALS, "Y");
    	      	  
          if (!StringHandler.isBlankString(status))
            criteria.addCriterion("status", SearchCriterion.EQUALS, status);

          request.setAttribute("storageAreaCollection", genericSqlFactory.select(criteria, sort, "fac_area_blg_floor_rm_wa_view"));
      }
      //fac_loc_app
	  request.setAttribute("endSearchTime", new Date().getTime());
	  return mapping.findForward("wastkmgmtcallback");
  }
}