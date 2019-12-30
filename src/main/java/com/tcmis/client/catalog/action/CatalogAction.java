package com.tcmis.client.catalog.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.ClientLabelBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class CatalogAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "showcatalog");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		if (!user.getPermissionBean().hasUserPagePermission("catalog")) {
			return (mapping.findForward("nopermissions"));
		}

		if (form == null) {
			this.saveTcmISToken(request);
		}

		BigDecimal personnelId = new BigDecimal(user.getPersonnelId());

		OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request));
		// BigDecimal personnelId = new BigDecimal(bean.getPersonnelId());
		request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));

		// if form is not null we have to perform some action
		if (form != null) {
			// copy date from dyna form to the data form
			CatalogInputBean bean = new CatalogInputBean();
			BeanHandler.copyAttributes(form, bean);

			CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request));

			/*
			 * if (!this.isTcmISTokenValid(request,true)) { BaseException be =
			 * new BaseException("Duplicate form submission");
			 * be.setMessageKey("error.submit.duplicate");
			 * this.saveTcmISToken(request); throw be; }
			 * this.saveTcmISToken(request);
			 */

			if (((DynaBean) form).get("submitSearch") != null && ((String) ((DynaBean) form).get("submitSearch")).length() > 0) {

				this.setSessionObject(request, new Vector(), "clientLabelBeanCollection");

				Collection c = catalogProcess.getSearchResult(bean, user);
				request.setAttribute("prCatalogScreenSearchBeanCollection", catalogProcess.createRelationalObject(c));
				request.setAttribute("showProp65Warning", catalogProcess.getProp65Flag(bean.getFacilityId()));
			}
			else if (((DynaBean) form).get("buttonPrint") != null && ((String) ((DynaBean) form).get("buttonPrint")).length() > 0) {
				String[] catPartNoArray = bean.getCatPartNo();
				String[] partDescriptionArray = bean.getPartDescription();
				String[] shelfLifeArray = bean.getShelfLife();
				String[] printCheckBoxArray = bean.getPrintCheckBox();
				String[] inventoryGroupArray = bean.getInventoryGroup();
				Vector printCheckBox = new Vector(2);
				for (int j = 0; j < printCheckBoxArray.length; j++) {
					printCheckBox.add(printCheckBoxArray[j]);
					if (log.isDebugEnabled()) {
						log.debug("id " + j + ":" + printCheckBoxArray[j]);
					}
				}

				Vector v = new Vector(catPartNoArray.length);
				for (int i = 0; i < catPartNoArray.length; i++) {
					if (printCheckBox.contains(catPartNoArray[i])) {
						ClientLabelBean clientLabelBean = new ClientLabelBean();
						clientLabelBean.setCatPartNo(catPartNoArray[i]);
						clientLabelBean.setPartDescription(partDescriptionArray[i]);
						clientLabelBean.setShelfLife(shelfLifeArray[i]);
						clientLabelBean.setInventoryGroup(inventoryGroupArray[i]);
						v.add(clientLabelBean);
					}
				}
				this.setSessionObject(request, v, "clientLabelBeanCollection");

				ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
				this.setSessionObject(request, resource.getString("hosturl") + resource.getString("callabelurl"), "filePath");
				request.setAttribute("doexcelpopup", "Yes");

				Collection c = catalogProcess.getSearchResult(bean, user);
				request.setAttribute("prCatalogScreenSearchBeanCollection", catalogProcess.createRelationalObject(c));
			}
			else if (((DynaBean) form).get("buttonCreateExcel") != null && ((String) ((DynaBean) form).get("buttonCreateExcel")).length() > 0) {

				this.setSessionObject(request, new Vector(), "clientLabelBeanCollection");
				Collection c = catalogProcess.getSearchResult(bean, user);
				c = catalogProcess.createRelationalObject(c);
				ResourceLibrary resource = new ResourceLibrary("report");

				File dir = new File(resource.getString("excel.report.serverpath"));
				File file = File.createTempFile("excel", ".xls", dir);
				catalogProcess.writeExcelFile(c, file.getAbsolutePath(), bean, user.getLocale());
				this.setSessionObject(request, resource.getString("report.hosturl") + resource.getString("excel.report.urlpath") + file.getName(), "filePath");

				request.setAttribute("doexcelpopup", "Yes");
				request.setAttribute("prCatalogScreenSearchBeanCollection", c);
			}
			else if (((DynaBean) form).get("createExcel") != null && ((String) ((DynaBean) form).get("createExcel")).length() > 0) {

				this.setSessionObject(request, new Vector(), "clientLabelBeanCollection");
				Collection c = catalogProcess.getSearchResult(bean, user);
				c = catalogProcess.createRelationalObject(c);
				ResourceLibrary resource = new ResourceLibrary("report");

				File dir = new File(resource.getString("excel.report.serverpath"));
				File file = File.createTempFile("excel", ".xls", dir);
				catalogProcess.writeExcelFile(c, file.getAbsolutePath(), bean, user.getLocale());
				request.setAttribute("filePath", resource.getString("report.hosturl") + resource.getString("excel.report.urlpath") + file.getName());

				return mapping.findForward("viewfile");
			}
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
