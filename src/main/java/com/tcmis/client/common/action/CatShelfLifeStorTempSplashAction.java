package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.beans.CatalogFacilityBean;
import com.tcmis.client.catalog.process.*;
import com.tcmis.client.common.process.ItemCatalogProcess;
import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.pnnl.process.CxmlProcess;
import com.tcmis.client.seagate.beans.MrNeedingApprovalViewBean;
import com.tcmis.client.seagate.process.AribaRepairProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

import sun.misc.BASE64Encoder;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CatShelfLifeStorTempSplashAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String uAction = request.getParameter("uAction");
		if (uAction == null) {
			uAction = "init";
		}
		CatalogProcess catalogProcess = null;
		if (uAction.equals("search")) 
		{
			try {
				CatalogInputBean bean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
				
				catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>) catalogProcess.getSearchResult(bean);
				Object[] results = catalogProcess.createRelationalObjects(StringHandler.isBlankString(bean.getIncludeObsoleteParts()) == false ? true:false,c);
				
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);			
				request.setAttribute("qualityIdLabelColumnHeader", results[3]);
				request.setAttribute("catPartAttrColumnHeader", results[4]);
				request.setAttribute("facilityOrAllShelflife", bean.isFacilityOrAllShelflife());
			} catch (Exception ex) {
				log.error("Error CatShelfLifeStorTempSplash page:" + ex.getMessage());
				return mapping.findForward("genericerrorpage");
			}
		}
		else if (uAction.equals("createXSL")) 
		{
			this.setExcel(response, "CatShelfLifeStorTemp");
			try {
				CatalogInputBean bean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
				catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				catalogProcess.getShelfLifeStorTempSplashExcelReport(bean).write(response.getOutputStream());
				return noForward;
			} catch (Exception ex) {
				log.error("Error CatShelfLifeStorTempSplash page:" + ex.getMessage());
				return mapping.findForward("genericerrorpage");
			}
		}
		else
		{
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
            request.setAttribute("workAreaFacilityBeanCollection", orderTrackingProcess.getShelfLifeStorTempSplashAreaFacility());
		}
	    return mapping.findForward("success");
	}
}
