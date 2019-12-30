  package com.tcmis.internal.distribution.action;

  import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.distribution.process.SpecListProcess;

  /******************************************************************************
   * Controller for CustomerAddressSearch
   * @version 1.0
   ******************************************************************************/
  public class SpecListAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

//  login
	   	if (!this.isLoggedIn(request)) {
				request.setAttribute("requestedPage", "speclist");
				request.setAttribute("requestedURLWithParameters",
						this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
		}
		
//  main
	  	if( form == null )
	      	return (mapping.findForward("success"));
	  	String uAction = (String) ( (DynaBean)form).get("uAction");

//  result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		SpecListProcess process = new SpecListProcess(this);
	  	
		BigDecimal itemId = new BigDecimal( ((DynaBean)form).get("itemId").toString());
	
	    if (uAction == null || uAction.equals("search")) {
	    	String catPartNo = ((DynaBean)form).get("catPartNo").toString();
	    	
	    	Collection specListColl = process.getSpecListColl(itemId,catPartNo);		
			request.setAttribute("specListColl", specListColl);
			return mapping.findForward("success");
	    }
	    else if ("showDetails".equals(uAction)) {
	    	String radianPo = ((DynaBean)form).get("radianPo").toString();
	    	String poLine = ((DynaBean)form).get("poLine").toString();
	    	Collection poLineSpecColl = process.getPoLineSpecColl(radianPo,poLine);
	    	Collection specListColl = process.getSpecListColl(itemId,"");
	    	request.setAttribute("poLineSpecColl", poLineSpecColl);
			request.setAttribute("specListColl", specListColl);
			return mapping.findForward("showDetails");
	    }
	    else {
 //     	request.setAttribute("setUpCollection", process.getSetUpCollection(new BigDecimal(personnelBean.getPersonnelId())));
	    }
	    return mapping.findForward("success");
    }
  }