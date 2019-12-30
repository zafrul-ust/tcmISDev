package com.tcmis.internal.distribution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.PartSearchInputBean;
import com.tcmis.internal.distribution.beans.PartSearchBean;
import com.tcmis.internal.distribution.process.PriceListProcess;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class SupplierPLItemSearchAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//		login
		if (!this.checkLoggedIn( "supplierplitemsearch" ) ) return mapping.findForward("login");

		//main
		//if( form == null )
		//	return (mapping.findForward("success"));
		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");

		//result
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	    PriceListProcess process = new PriceListProcess(this);

	    PartSearchInputBean ib = new PartSearchInputBean();
		BeanHandler.copyAttributes(form, ib, getTcmISLocale(request));

		if (uAction.equals("search")) {
			String query = 
"select * from table (pkg_sales_search.FX_PART_DESC_SPEC_SEARCH( {0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13} )) order by alternate_name,spec_list_id";
	    	request.setAttribute("partSearchCollection",
	    	    	process.getSearchResult(query,
	    	    			new PartSearchBean(),
							ib.getPartNumber() , ib.getSearchPartNumberMode(),
							null,null,
							ib.getText() , ib.getSearchTextMode() ,
							ib.getAlternate() , ib.getSearchAlternateMode() ,
                            ib.getPartDesc() , ib.getSearchPartDescMode() ,
                            ib.getCompanyId() , ib.getPriceGroupId() ,
                            ib.getCatalogCompanyId(),ib.getCatalogId()
	    	    		    ));
			return (mapping.findForward("success"));
		}
		else if (uAction.equals("createExcel") ) {


			return noForward;

		}
		//search    
		else {/* set up search page data*/
			request.setAttribute("priceGroupCatalogOv", process.getPriceGroupCatalogOv(ib));
		}
		return mapping.findForward("success");
	}
}
