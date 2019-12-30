package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.beans.ReplacementPartSearchInputBean;
import com.tcmis.internal.distribution.beans.CustomerPartManagementInputBean;

/******************************************************************************
 * Controller for Replacement Part Search page
 * @version 1.0
 ******************************************************************************/

public class PartSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "partsearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		String userAction = request.getParameter("uAction");
		if (form == null || userAction == null)
		{    	 
			return (mapping.findForward("success"));
		}

		ReplacementPartSearchInputBean inputBean = new ReplacementPartSearchInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		GenericProcess process = new GenericProcess(this.getDbUser(request));
		if (userAction.equals("search") ) {
			saveTcmISToken(request);
			String query = "select company_id catalog_company_id,facility_id catalog_id,to_number(substr(fac_part_no,0,6)) item_id,part_description,fac_part_no cat_part_no, spec_id spec_list from  fac_item where facility_id = 'Global' and ((lower(part_description) like lower('%"+inputBean.getSearchArgument()+"%')) or (lower(fac_part_no) like lower('%"+inputBean.getSearchArgument()+"%'))) order by part_description";
			Collection dataColl = process.getSearchResult(query, new CustomerPartManagementInputBean());
			request.setAttribute("customerPartManagementCollection", dataColl);
			request.setAttribute("dataRowSpanHashMap",getSearchDataRowSpan(dataColl));
		}

		return (mapping.findForward("success"));
    }
	
	public HashMap getSearchDataRowSpan(Collection<CustomerPartManagementInputBean> dataColl) throws Exception {
		HashMap m1 = new HashMap();
		Integer i1 = null;
		BigDecimal lastItemId = new BigDecimal("0");
		//looping thru line data
		//Iterator iter = dataColl.iterator();
		/*while(iter.hasNext()) {
			CustomerPartManagementInputBean tmpBean = (CustomerPartManagementInputBean) iter.next();
		*/
		for(CustomerPartManagementInputBean tmpBean : dataColl)
		{
			BigDecimal currentItemId = tmpBean.getItemId();
			if (currentItemId.equals(lastItemId)) {
				currentItemId = lastItemId;
			}

			if (m1.get(currentItemId) == null) {
				i1 = new Integer(0);
				m1.put(currentItemId, i1);
			}
			i1 = (Integer) m1.get(currentItemId);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(currentItemId, i1);

			lastItemId = currentItemId;
		}
		return m1;
	}
  
}
