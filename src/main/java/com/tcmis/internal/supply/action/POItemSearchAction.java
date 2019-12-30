package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.POItemSearchInputBean;
import com.tcmis.internal.supply.process.POItemSearchProcess;

/******************************************************************************
 * Controller for Purchase Order Item Search page
 * @version 1.0
 ******************************************************************************/

public class POItemSearchAction extends TcmISBaseAction
{

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception
			{
		if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "poitemsearchmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		request.setAttribute("valueElementId",request.getParameter("valueElementId"));
		request.setAttribute("displayElementId",request.getParameter("displayElementId"));
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		/*
     	if (! permissionBean.hasFacilityPermission("POSupplierContact", null, null))
     	{
        	return mapping.findForward("nopermissions");
     	}
		 */

		request.setAttribute("companyId", request.getParameter("companyId"));
		String shipToId = request.getParameter("shipToId");
		//log.debug("shipToId = [" + shipToId + "]; ");
		if(shipToId==null)shipToId="";
		String shipToIdFixed = shipToId.replace("#", "~"  );
		// String shipToIdFixed = findReplace( shipToId, "#", "%23" );
		//log.debug("shipToIdFixed = [" + shipToIdFixed + "]; ");
		request.setAttribute("shipToId", shipToIdFixed );

		request.setAttribute("mode", request.getParameter("mode"));
		request.setAttribute("validBPO", request.getParameter("validBPO"));
		request.setAttribute("inventoryGroup", request.getParameter("inventoryGroup"));
		String action = request.getParameter("uAction");
		if (form == null)
		{
			return (mapping.findForward("success"));
		}
		POItemSearchInputBean inputBean = new POItemSearchInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		/*		java.util.Enumeration<String> e = request.getParameterNames();
 			java.util.Vector<String> v = new java.util.Vector();
			while(e.hasMoreElements())
				v.add(e.nextElement());
			java.util.Collections.sort(v);
			for(String ss:v) {
				System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
			}
		 */
		POItemSearchProcess process = new POItemSearchProcess(this.getDbUser(request));

		if ("search".equals(action) )
		{
			Collection pOItemBeanCollection = process.getPOItemBeanCollection(inputBean);

			request.setAttribute("pOItemBeanCollection", pOItemBeanCollection);
			this.saveTcmISToken(request);
		}

		return (mapping.findForward("success"));
			}

	public static String findReplace(String source, String toReplace, String replacement)
	{
		if(source.indexOf(toReplace) > -1)
		{
			StringBuilder sb = new StringBuilder();
			for(int ix = -1; (ix = source.indexOf(toReplace)) >= 0;)
			{
				//sb.append(source.substring(0, ix)).append(replacement);
				sb.append(source.substring(0,ix));
				sb.append(replacement);
				source = source.substring(ix + toReplace.length());
			}

			if(source.length() > 0)
				sb.append(source);
			return sb.toString();
		} else
		{
			return source;
		}
	}

}
