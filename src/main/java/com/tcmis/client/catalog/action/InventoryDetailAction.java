package com.tcmis.client.catalog.action;

import java.util.Collection;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.process.InventoryProcess;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.beans.InventoryInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class InventoryDetailAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		/*
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showinventorydetail");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		*/

		if (request.getParameter("catPartNo") == null) {
			return mapping.findForward("systemerrorpage");
		} else {
		   PrCatalogScreenSearchBean bean = new PrCatalogScreenSearchBean();
			String catPartNo = request.getParameter("catPartNo");
			String inventoryGroup = request.getParameter("inventoryGroup");
			String catalogId = request.getParameter("catalogId");
			String partGroupNo = request.getParameter("partGroupNo");
			String catalogCompanyId = request.getParameter("catalogCompanyId");
			bean.setCatPartNo(catPartNo);
			bean.setInventoryGroup(inventoryGroup);
			bean.setCatalogId(catalogId);
			BigDecimal pgn = new BigDecimal(0);
			if(!StringHandler.isBlankString(partGroupNo)) {
				pgn =new BigDecimal(partGroupNo);
			}
			bean.setPartGroupNo(pgn);
			bean.setCatalogCompanyId(catalogCompanyId);
			//call the process here
			InventoryProcess inventoryProcess = new InventoryProcess(this.getDbUser(request));
			if ("createExcel".equalsIgnoreCase(request.getParameter("uAction"))) {
				PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
        		this.setExcel(response,"Inventory Detail");
        		inventoryProcess.getDetailExcelReport(bean, personnelBean, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        		return noForward;
			}else {
				Collection c = inventoryProcess.getInventoryDetails(bean.getCatPartNo(),bean.getInventoryGroup(),bean.getCatalogId(),bean.getPartGroupNo().toString(),bean.getCatalogCompanyId());
				request.setAttribute("pkgInventoryDetailWebPrInventoryDetailBeanCollection", c);
			}
			return mapping.findForward("inventorydetail");
		}
	} //end of method
} //end of class
