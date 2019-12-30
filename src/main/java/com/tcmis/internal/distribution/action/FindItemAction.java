package com.tcmis.internal.distribution.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.process.FindItemProcess;


public class FindItemAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		FindItemProcess process = new FindItemProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
//		 copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
        Collection<ItemBean> results =  process.getBuyerColl(inputBean);
        
        // Write out the data
        response.setCharacterEncoding("utf-8");	  
        PrintWriter out = response.getWriter();
        // status needs to be in the third position or just id:name with no status
        // If we need to add different values, please leave the third one empty like id:name::something else 
    	for(ItemBean bean: results)
    		out.println(bean.getItemId()+":"+bean.getItemDesc());
		out.close();
		return noForward;	
	}

}


