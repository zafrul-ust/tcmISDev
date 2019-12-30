package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.client.report.beans.FacAreaBlgFloorRmStgView;

/******************************************************************************
 * Controller for Simple Call to Server
 * @version 1.0
     ******************************************************************************/
public class SimpleCallToServerAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws BaseException, Exception {


        GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(this.getDbUser(request)));
        SearchCriteria criteria = new SearchCriteria();
        SortCriteria sort = new SortCriteria();
        if("workAreaStockTransfer".equals(request.getParameter("calledFrom"))) {
            String companyId = request.getParameter("companyId");
            String facilityId = request.getParameter("facilityId");
            criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
            criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
            if ("Y".equals(request.getParameter("facilityChanged"))) {
                //get inventory data
                sort.addCriterion("inventoryGroupName");
                genericSqlFactory.setBeanObject(new InventoryGroupDefinitionBean());
                request.setAttribute("inventoryGroupCollection", genericSqlFactory.select(criteria, sort, "facility_ig_desc_view"));
            }
            //get work area data
            String areaId = request.getParameter("areaId");
            String buildingId = request.getParameter("buildingId");
            String floorId = request.getParameter("floorId");
            String status = request.getParameter("status");
            if(!StringHandler.isBlankString(areaId)) {
                areaId = areaId.replaceAll("\\|", ",");
                criteria.addCriterion("areaId", SearchCriterion.IN, areaId);
            }
            if(!StringHandler.isBlankString(buildingId)) {
                buildingId = buildingId.replaceAll("\\|", ",");
                criteria.addCriterion("buildingId", SearchCriterion.IN, buildingId);
            }
            if(!StringHandler.isBlankString(floorId))
                criteria.addCriterion("floorId", SearchCriterion.EQUALS, floorId);
            if (!StringHandler.isBlankString(status))
                criteria.addCriterion("status", SearchCriterion.EQUALS, status);
            sort = new SortCriteria();
            sort.addCriterion("storageAreaDesc");
            genericSqlFactory.setBeanObject(new FacAreaBlgFloorRmStgView());
            request.setAttribute("workAreaCollection", genericSqlFactory.select(criteria, sort, "fac_area_blg_floor_rm_wa_view"));
        }
        return mapping.findForward("success");
    }
}