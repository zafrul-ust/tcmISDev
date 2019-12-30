package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.hub.beans.VvHubBinsBean;
import com.tcmis.internal.hub.beans.VvHubRoomBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.hub.process.ReceivingProcess;

public class HubBinAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request,true)) {
			request.setAttribute("requestedPage", "showhubbin");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("binLabels") &&
                !personnelBean.getPermissionBean().hasUserPagePermission("binManagement") &&
                !personnelBean.getPermissionBean().hasUserPagePermission("receiving"))
		{
			return (mapping.findForward("nopermissions"));
		}

		String branchPlant=null ;
		request.setAttribute("fromBinManagement", request.getParameter("fromBinManagement"));
		
		String userAction = request.getParameter("userAction");
		if (userAction == null)
			userAction = "";
		
		if("uploadNewBins".equalsIgnoreCase(userAction) || "getUploadNewBinsTemplate".equalsIgnoreCase(userAction) || null==( (DynaBean)form).get("branchPlant") )
		{
			branchPlant = request.getParameter("branchPlant");
			request.setAttribute("branchPlant",request.getParameter("branchPlant"));
		}
		else
		{	
			branchPlant	= (String) ( (DynaBean)form).get("branchPlant");
			request.setAttribute("branchPlant",request.getParameter("branchPlant"));
		}
		if ("uploadNewBins".equalsIgnoreCase(userAction)) {
			FileUploadBean bean = new FileUploadBean();
		    BeanHandler.copyAttributes(form, bean);
		    //check if a file was uploaded
		    if(bean.getTheFile() != null && (!bean.getTheFile().getName().endsWith("xlsx") && !bean.getTheFile().getName().endsWith("xls"))) {
		    	request.setAttribute("errorMessage", "Invalid file.");
		    } else if(bean.getTheFile() != null){


		    	String errorMessage = null;
				BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
				errorMessage = binLabelsProcess.uploadNewBinsFile(bean,branchPlant);
				if (StringHandler.isBlankString(errorMessage))
				{
					request.setAttribute("errorMessage", null);
					request.setAttribute("result", "success");
				}
				else
					request.setAttribute("errorMessage", errorMessage);
				
			}    
			
			return (mapping.findForward("showupload"));
		}
		else if ("getUploadNewBinsTemplate".equalsIgnoreCase(userAction)) {
			try {
				this.setExcel(response, "uploadnewbinstemplate");
				BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
				binLabelsProcess.getUploadNewBinsTemplateExcel(personnelBean.getLocale()).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		if ("upReconcil".equalsIgnoreCase((String)((DynaBean)form).get("uAction"))) {	
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			binLabelsProcess.updateRecon(request.getParameter("receiptId"),request.getParameter("bin"));
		}
	    if (request.getParameter("branchPlant") == null) {
			return mapping.findForward("systemerrorpage");
		}
		else if (form != null && ( (DynaBean) form).get("submitAdd") != null &&
				( (String) ( (DynaBean) form).get("submitAdd")).length() > 0) {

			VvHubBinsBean bean = new VvHubBinsBean();
			BeanHandler.copyAttributes(form, bean);

			boolean addNewBin = false;
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			
			
			addNewBin = binLabelsProcess.insertNewBin(bean);
			if (addNewBin)
			{
				request.setAttribute("successMessage", "Yes");
			}
			else
			{
				request.setAttribute("errorMessage", "Yes");
			}

			return (mapping.findForward("showresults"));
		}
		else if ("showBins".equalsIgnoreCase(userAction)) {
			Collection c = null;
			if("recon".equalsIgnoreCase(request.getParameter("pageId")))
			{
				String itemId = request.getParameter("itemId");
				String rowNumber = request.getParameter("rowNumber");
				BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));	
				request.setAttribute("vvHubBinsBeanBeanCollection", binLabelsProcess.fillRecon(itemId,branchPlant));
	
				request.setAttribute("itemId",itemId);
				request.setAttribute("rowNumber",rowNumber);
			}
			else
			{
				String itemId = request.getParameter("itemId");
				String rowNumber = request.getParameter("rowNumber");
	
				VvDataProcess vvDataProcess = new VvDataProcess(this.
						getDbUser(request));
				c = vvDataProcess.getVvHubBins(branchPlant);
				request.setAttribute("vvHubBinsBeanBeanCollection",
					c);
	
				request.setAttribute("itemId",itemId);
				request.setAttribute("rowNumber",rowNumber);		
			}

			return mapping.findForward("showresults");
		} //end of method
		else if ("addNewBin".equalsIgnoreCase(userAction)) {
            String sourceHubName = (String) ( (DynaBean)form).get("sourceHubName");
            if	(!personnelBean.getPermissionBean().hasInventoryGroupPermission	("addNewBin",null,sourceHubName,null)) {
				request.setAttribute("tcmISError"	,getResourceBundleValue(request,"error.noaccesstopage"));
				 return (mapping.findForward("nopermissions"));
			}	
            	 
			request.setAttribute("branchPlant",branchPlant);
			request.setAttribute("sourceHubName",sourceHubName);
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			request.setAttribute("vvBinTypes", binLabelsProcess.getBinTypes());
			BinsToScanProcess process = new BinsToScanProcess(this.getDbUser(request));
			request.setAttribute("hubRoomOvBeanCollection", process.getDropDownData(((PersonnelBean) this
					.getSessionObject(request, "personnelBean")).getHubInventoryGroupOvBeanCollection()));
			return mapping.findForward("showresults");
		}
		else if ("showNewRoom".equalsIgnoreCase(userAction))
		{			
			request.setAttribute("branchPlant",request.getParameter("branchPlant"));
			return (mapping.findForward("shownewroom"));

		}
		else if ("addNewRoom".equalsIgnoreCase(userAction))                          
		{ 
			String sourceHubName = (String) ( (DynaBean)form).get("sourceHubName");
            request.setAttribute("sourceHubName",request.getParameter("sourceHubName"));

			if	(!personnelBean.getPermissionBean().hasInventoryGroupPermission	("addNewBin",null,sourceHubName,null)) {
				request.setAttribute("tcmISError"	,getResourceBundleValue(request,"error.noaccesstopage"));
				 return (mapping.findForward("nopermissions"));
			}

			VvHubRoomBean bean = new VvHubRoomBean();		
			BeanHandler.copyAttributes(form, bean);
			BinLabelsProcess binLabelsProcess = new BinLabelsProcess(this.getDbUser(request));
			Collection errorMsgs = binLabelsProcess.addNewRoom(bean);
			request.setAttribute("tcmISErrors", errorMsgs); 

			if(errorMsgs.isEmpty() )
			{
				request.setAttribute("closeNewRoomWin", "Y");
				request.setAttribute("roomBean", bean);
			}
			else
			{
				request.setAttribute("closeNewRoomWin", "N");				
			}

			return (mapping.findForward("shownewroom"));
		}
		else if (form != null && ( (DynaBean) form).get("addBinToItemBinCollection") != null &&
				( (String) ( (DynaBean) form).get("addBinToItemBinCollection")).length() > 0) {

			String itemId = request.getParameter("itemId");
			String rowNumber = request.getParameter("rowNumber");
			String vvHubBin = request.getParameter("vvHubBin");
			if (vvHubBin == null) {vvHubBin = "";}
			request.setAttribute("itemId", itemId);
			request.setAttribute("rowNumber", rowNumber);
			request.setAttribute("vvHubBin", vvHubBin);
			request.setAttribute("resultAddBin", "addBinToItemBinCollection");

			if (vvHubBin.trim().length() > 0) {
				if ((Collection)this.getSessionObject(request, "receivingViewBeanCollection") !=null)
				{
					ReceivingProcess process = new ReceivingProcess(this.getDbUser(request));
					Collection finalreceivingViewBeanCollection = new Vector();
					Collection receivingViewBeanCollection = new Vector( (Collection)this.
							getSessionObject(request, "receivingViewBeanCollection"));

					finalreceivingViewBeanCollection = process.addBinToItemBinCollection(
							receivingViewBeanCollection, itemId, vvHubBin);

					this.setSessionObject(request, finalreceivingViewBeanCollection,
					"receivingViewBeanCollection");
				}
			}
			return (mapping.findForward("showresults"));
		}
		else
		{
			return mapping.findForward("systemerrorpage");
		}
	}
} //end of class