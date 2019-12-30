package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.MsdsAuthorBean;
import com.tcmis.internal.catalog.process.MsdsAuthorSearchProcess;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;

/******************************************************************************
 * Controller for New Revision Date page
 * @version 1.0
 ******************************************************************************/

public class MsdsAuthorSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MsdsAuthorSearchProcess process = new MsdsAuthorSearchProcess(this.getDbUser(request));	    
		
//		 copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
        Collection<MsdsAuthorBean> results =  process.getMsdsAuthorBeanCollection(inputBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	  
        PrintWriter out = response.getWriter();
        // status needs to be in the third position or just id:name with no status
        // If we need to add different values, please leave the third one empty like id:name::something else 
    	for(MsdsAuthorBean bean: results)
    		out.println(bean.getMsdsAuthorId()+"::"+bean.getMsdsAuthorDesc()+"::"+bean.getCountryAbbrev()+"::"+bean.getNotes());   		
		out.close();
		return noForward;	
	}
}
