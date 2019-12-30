package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.internal.catalog.process.CatalogSpecQcProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.beans.CatalogAddSpecBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;

/******************************************************************************
 * Controller for catalog spec qc
 * 
 * @version 1.0
 ******************************************************************************/
public class CatalogSpecQcAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, CatalogSpecQcProcess process, CatalogAddSpecBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Object[] data = process.getRequestData(inputBean,personnelBean);
        request.setAttribute("catAddHeaderViewBean", data[CatalogSpecQcProcess.HEADER_DATA]);
		request.setAttribute("catAddSpecRequestLines", data[CatalogSpecQcProcess.SPEC_DATA]);
		request.setAttribute("specLibraryColl", data[CatalogSpecQcProcess.SPEC_LIBRARIES]);
		saveTcmISToken(request);
	}
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "catalogspecqc");
			//This is to save any parameters passed in the URL, so that they
			//can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

        if( form == null ) return (mapping.findForward("success"));

        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission
        we need to not show them the page.*/
        if (!personnelBean.getPermissionBean().hasUserPagePermission("catalogAddProcess"))
            return (mapping.findForward("nopermissions"));

		String forward = "success";
		String action = (String) ((DynaBean) form).get("uAction");

		StringBuffer requestURL = request.getRequestURL();
		CatalogSpecQcProcess process = new CatalogSpecQcProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));

        CatalogAddSpecBean inputBean = new CatalogAddSpecBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		//collapse all consecutive spaces into one, like a web browser would do
		inputBean.setSpecName(StringUtils.join(StringUtils.split(inputBean.getSpecName(), " "), ' '));
        inputBean.setSpecTitle(StringUtils.join(StringUtils.split(inputBean.getSpecTitle(), " "), ' '));
        inputBean.setSpecVersion(StringUtils.join(StringUtils.split(inputBean.getSpecVersion(), " "), ' '));
		inputBean.setSpecAmendment(StringUtils.join(StringUtils.split(inputBean.getSpecAmendment(), " "), ' '));
        
		if ("save".equalsIgnoreCase(action)){
			Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.saveData(inputBean,beans);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
		}else if ("addExistingSpec".equalsIgnoreCase(action)) {
            Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.addExistingSpec(inputBean,beans);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else if ("addNewSpec".equalsIgnoreCase(action)) {
            Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.addNewSpec(personnelBean,inputBean,beans);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else if("approve".equals(action)){
        	Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.saveData(inputBean,beans);
        	process.approveSpec(inputBean, personnelBean);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else if("reject".equals(action)){
        	Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.saveData(inputBean,beans);
        	process.rejectSpec(inputBean, personnelBean);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else if ("revert".equalsIgnoreCase(action)) {
            Collection<CatalogAddSpecBean> beans = BeanHandler.getBeans((DynaBean)form,"specRequestDiv",new CatalogAddSpecBean(),this.getTcmISLocale(request));
            process.saveData(inputBean,beans);
            process.revertRequest(inputBean);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else if ("deleteSpec".equalsIgnoreCase(action)){
            process.deleteSpec(inputBean);
            //reload data
            doSearch(request, process, inputBean, personnelBean);
        }else {
            //load initial data
        	doSearch(request, process, inputBean, personnelBean);
        }

		return mapping.findForward(forward);
	}
}