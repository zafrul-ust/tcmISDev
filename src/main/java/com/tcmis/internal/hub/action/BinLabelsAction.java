package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.BinLabelsInputBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.VvHubBinsBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.supply.beans.PoLineExpediteDateBean;

/******************************************************************************
 * Controller for viewing and generating bin labels
 * 
 * @version 1.0
 ******************************************************************************/


/**
 * Change History
 * --------------
 * 03/05/09 - Shahzad Butt - Added Update functionality, commented the unnecessary code 
 * 							 from update. Permission needs to be added for update.
 *
 */

public class BinLabelsAction extends TcmISBaseAction {


	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws BaseException, Exception {

		if (!this.isLoggedIn(request,true)) {
			request.setAttribute("requestedPage", "binlabelsmain");
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("binManagement"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		BinLabelsInputBean inputBean = new BinLabelsInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

        request.setAttribute("sourceHubName",inputBean.getSourceHubName());
        request.setAttribute("branchPlant",inputBean.getBranchPlant());
        BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));


		BinsToScanProcess process = new BinsToScanProcess(this.getDbUser(request));
		request.setAttribute("hubRoomOvBeanCollection", process.getDropDownData(((PersonnelBean) this
					.getSessionObject(request, "personnelBean")).getHubInventoryGroupOvBeanCollection()));

		Collection vvBinTypes = binLabelsProcess.getBinTypes();
		request.setAttribute("vvBinTypes", vvBinTypes);

		// if action is search
		if (inputBean.isSearch()) {
			this.saveTcmISToken(request);
			
			Collection c = binLabelsProcess.getSearchResult(inputBean);
			request.setAttribute("binlabelsBeanCollection", c);
		}
		else if (inputBean.isUpdate()) {
			if	(!personnelBean.getPermissionBean().hasInventoryGroupPermission	("addNewBin",null,inputBean.getSourceHubName(),null)) {
				request.setAttribute("tcmISError"	,getResourceBundleValue(request,"error.noaccesstopage"));
				Collection c = binLabelsProcess.getSearchResult(inputBean);
				request.setAttribute("binlabelsBeanCollection", c);
				return	(mapping.findForward("success")); 
				}

			checkToken(request);

			VvHubBinsBean bean = new VvHubBinsBean();
			Collection<VvHubBinsBean> beans = BeanHandler.getBeans((DynaBean) form, "binlabelsViewBean", bean,getTcmISLocale(request));

			Collection errorMsgs = binLabelsProcess.update(beans);
			Collection c = binLabelsProcess.getSearchResult(inputBean);
			request.setAttribute("binlabelsBeanCollection", c);
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));
		}
		else if (inputBean.isGenerateLabels()) {
			LabelInputBean labelInputBean = new LabelInputBean();

			BeanHandler.copyAttributes(form, labelInputBean);
			ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
			Collection locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean, labelInputBean);
			if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1) {
				request.setAttribute("paperSize", labelInputBean.getPaperSize());
				request.setAttribute("sourcePage", "binlabelsresults");
				request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
				if (labelInputBean.isDoPrintLabelsNow()) {
					request.setAttribute("printLabelsNow", labelInputBean.getPrintLabelsNow());
				}
				return mapping.findForward("printerchoice");
			}
			Collection<VvHubBinsBean> beans = BeanHandler.getBeans((DynaBean) form, "binlabelsViewBean", new VvHubBinsBean(),getTcmISLocale(request));
			String url = binLabelsProcess.generateContainerLabel(beans, locationLabelPrinterCollection, personnelBean.getPersonnelIdBigDecimal(), inputBean.getSourceHubName());
			response.sendRedirect(response.encodeRedirectURL(url));
		}
		return mapping.findForward("success");
	}
} // end of action class