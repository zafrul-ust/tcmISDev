package com.tcmis.client.operations.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.operations.beans.ClientCabinetInventoryInputBean;
import com.tcmis.client.operations.beans.HubPreferredWarehouseViewBean;
import com.tcmis.client.operations.process.ClientCabinetInventoryProcess;
import com.tcmis.client.operations.process.ClientCabinetManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class ClientCabinetInventoryAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
	
		String forward = "success";      

		ClientCabinetInventoryInputBean inputBean = new ClientCabinetInventoryInputBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));
		ClientCabinetInventoryProcess process = new ClientCabinetInventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
	
		// Search
		if (null!=inputBean.getAction() &&	"search".equals(inputBean.getAction()))  {
			request.setAttribute("clientCabinetInvBeanColl", process.getSearchResult(inputBean));
			this.saveTcmISToken(request);
			return (mapping.findForward(forward));
		}
		//  Create Excel
		else if (null!=inputBean.getAction() && "createExcel".equals(inputBean.getAction())) {
			this.setExcel(response, "Cabinet_Inv_List");
			process.createExcelFile(inputBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}		

	else{
		Collection<HubPreferredWarehouseViewBean> hubPreferredWareHouseCollection = process.getHubPreferredWareHouseList();
		request.setAttribute("preferredHubCollection",hubPreferredWareHouseCollection );

        ClientCabinetManagementProcess clientCabmgtProcess = new ClientCabinetManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
		request.setAttribute("hubCabinetViewBeanCollection", clientCabmgtProcess.getAllLabelData( hubPreferredWareHouseCollection));
		
		this.saveTcmISToken(request);		
	}

	return (mapping.findForward(forward));
}
}
