package com.tcmis.internal.distribution.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.distribution.beans.AddSpecsBean;
import com.tcmis.internal.distribution.process.AddSpecsProcess;

public class AddSpecsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "addspecsmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}	

		//Main
		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/* if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders",null,null,null))
         {           

             return (mapping.findForward("nopermissions"));
         }	*/

		//Process search
		AddSpecsProcess process = new AddSpecsProcess(this.getDbUser(request),getTcmISLocaleString(request));		

		AddSpecsBean inputBean = new AddSpecsBean();

		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
		String s = request.getParameter("fromReceiptSpec");
		request.setAttribute("fromReceiptSpec", request.getParameter("fromReceiptSpec"));



		// Search
		if ( uAction.equals("search")) {

			request.setAttribute("specsBeanCollection", process.getSearchResult(inputBean, personnelBean));
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		else if (uAction.equals("newspecs"))
		{
			request.setAttribute("specDetailTypeCol", process.getSpecDetails("type"));
			request.setAttribute("specDetailClassCol", process.getSpecDetails("class"));
			request.setAttribute("specDetailFormCol", process.getSpecDetails("form"));
			request.setAttribute("specDetailGroupCol", process.getSpecDetails("group"));
			request.setAttribute("specDetailGradeCol", process.getSpecDetails("grade"));
			request.setAttribute("specDetailStyleCol", process.getSpecDetails("style"));
			request.setAttribute("specDetailFinishCol", process.getSpecDetails("finish"));
			request.setAttribute("specDetailSizeCol", process.getSpecDetails("size"));
			request.setAttribute("specDetailColorCol", process.getSpecDetails("color"));
			//request.setAttribute("specDetailMethodCol", process.getSpecDetails("method"));
			//request.setAttribute("specDetailConditionCol", process.getSpecDetails("condition"));
			//request.setAttribute("specDetailDashCol", process.getSpecDetails("dash"));
			//request.setAttribute("specDetailNoteCol", process.getSpecDetails("note"));

			return (mapping.findForward("shownewspecs"));
		}
		else if ( "addNewSpec".equals(uAction) || "newSpecsfromOldSpec".equals(uAction) )
		{
			
			String fileErrorMessage  = "";
            String extension = "";

            request.setAttribute("specDetailTypeCol", process.getSpecDetails("type"));
			request.setAttribute("specDetailClassCol", process.getSpecDetails("class"));
			request.setAttribute("specDetailFormCol", process.getSpecDetails("form"));
			request.setAttribute("specDetailGroupCol", process.getSpecDetails("group"));
			request.setAttribute("specDetailGradeCol", process.getSpecDetails("grade"));
			request.setAttribute("specDetailStyleCol", process.getSpecDetails("style"));
			request.setAttribute("specDetailFinishCol", process.getSpecDetails("finish"));
			request.setAttribute("specDetailSizeCol", process.getSpecDetails("size"));
			request.setAttribute("specDetailColorCol", process.getSpecDetails("color"));
			//request.setAttribute("specDetailMethodCol", process.getSpecDetails("method"));
			//request.setAttribute("specDetailConditionCol", process.getSpecDetails("condition"));
			//request.setAttribute("specDetailDashCol", process.getSpecDetails("dash"));
			//request.setAttribute("specDetailNoteCol", process.getSpecDetails("note"));
			
			if(inputBean.getTheFile() != null && !FileHandler.isValidUploadFile(inputBean.getTheFile()) ) {
				request.setAttribute("tcmISErrors", "File type not allowed.");
				request.setAttribute("closeNewSpecWin", "N");
	    	}	
			else {
			
	            if(inputBean.getTheFile() != null)
				{
	               extension = FilenameUtils.getExtension(inputBean.getTheFile().getName()); 
				   fileErrorMessage = process.uploadFile(inputBean);
				}
	
	            inputBean.setContent(""+inputBean.getSpecName()+extension+"");
	            Object[] results = process.createNewSpec(inputBean);
				Collection errorMsgs = (Collection) results[0];
				Boolean validFlag = (Boolean) results[1];
	
				if(errorMsgs.isEmpty() && validFlag.booleanValue())
				{
					request.setAttribute("closeNewSpecWin", "Y");
				}
				else
				{
					request.setAttribute("closeNewSpecWin", "N");
				}
	
				if (fileErrorMessage.length() == 0)
				{
					request.setAttribute("tcmISErrors", errorMsgs);
				}
				else
				{
					request.setAttribute("tcmISErrors", fileErrorMessage);
				}
				request.setAttribute("specId", results[2]);
				request.setAttribute("tcmISErrors", errorMsgs);
			
			}
			request.setAttribute("specName", inputBean.getSpecName());
			
			return (mapping.findForward("shownewspecs"));
		}
		else if ( "getNewSpecId".equals(uAction)) 
		{   
			String specLibrary = request.getParameter("specLibrary");
			String specName = request.getParameter("specName");
			String specVersion = request.getParameter("specVersion");
			String specAdmentment = request.getParameter("specAdmentment");
			
			Object[] results = process.getSpecId(specLibrary, specName, specVersion, specAdmentment);
	           
	       	String errorMessage = null;
	       	if (results[0] != null)
			{
	       		request.setAttribute("specId", results[0]);
			} else
			{
				errorMessage = results[1].toString();
				request.setAttribute("tcmISError", results[1].toString());
			}

			return (mapping.findForward("getjsonspecid"));
		} 
		else if ( "getSpecDetails".equals(uAction)) 
		{   
			String specLibrary = request.getParameter("specLibrary");
			String specId = request.getParameter("specId");
			String specDetail = request.getParameter("specDetail");
			
			Collection details = process.getSpecDetails(specId.replaceAll("&amp;", "&"), specLibrary.replaceAll("&amp;", "&"), specDetail.replaceAll("&amp;", "&"));
	           
	       	request.setAttribute("detailColl", details);

			return (mapping.findForward("getspecdetails"));
		} 
		else {
			return mapping.findForward("success");
		}
	}

}
