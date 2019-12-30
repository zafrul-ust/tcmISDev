package com.tcmis.internal.supply.action;



import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.supply.process.PoShelfLifeQuantityCheckProcess;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PoShelfLifeQuantityCheckAction extends TcmISBaseAction {
   
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
	   PoShelfLifeQuantityCheckProcess process = new PoShelfLifeQuantityCheckProcess(getDbUser(request), getTcmISLocale());
	   String po = request.getParameter("po").toString();
	   String shelfLife = request.getParameter("shelfLife").toString();
	   String itemId  = request.getParameter("itemId").toString();
		if(request.getParameter("uAction") != null && request.getParameter("uAction").toString().equalsIgnoreCase("Type1"))
			request.setAttribute("searchResults", process.getType1(po,shelfLife,itemId));
		else if(request.getParameter("uAction") != null && request.getParameter("uAction").toString().equalsIgnoreCase("Type2"))
			request.setAttribute("searchResults", process.getType2(po,shelfLife,itemId));
	    return mapping.findForward("success");
	}
}

