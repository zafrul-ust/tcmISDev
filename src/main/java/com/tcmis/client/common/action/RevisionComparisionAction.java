package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.RevisionComparisionProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;



public class RevisionComparisionAction extends TcmISBaseAction {
	 public ActionForward executeAction(ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws
				BaseException, Exception {
		 
		 BigDecimal materialId = new BigDecimal(request.getParameter("materialId").toString());
		 
		 RevisionComparisionProcess rcProcess = new RevisionComparisionProcess(this.getDbUser(request),getTcmISLocaleString(request));
		 
		 if (materialId != null) {
	    		//Collection versionColl = rcProcess.getVersions(materialId);
			 	Vector propertyColl =  rcProcess.getProperties();
			 	request.setAttribute("propertyColl",propertyColl);
                Vector versionColl = new Vector();
                request.setAttribute("propertyVersionMap",rcProcess.getSearchDataRowSpan(versionColl,materialId));
                request.setAttribute("versionColl",versionColl);
            }
		 
		 return mapping.findForward("success");
	 }
	 
	
}
