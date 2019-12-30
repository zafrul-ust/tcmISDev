package com.tcmis.client.catalog.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.commons.beanutils.DynaBean;
import com.tcmis.client.catalog.beans.ClientInventoryCommentsViewBean;
import com.tcmis.client.catalog.process.CatalogCommentProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for CasNumber Search page
 * @version 1.0
 ******************************************************************************/

public class EditCommentAction extends TcmISBaseAction{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "editcomment");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

// there can be at most one comment per.
		
		String uAction = (String) ( (DynaBean) form).get("uAction");

		ClientInventoryCommentsViewBean clientCommentsBean = new ClientInventoryCommentsViewBean();
		BeanHandler.copyAttributes(form, clientCommentsBean, getTcmISLocale(request));

		CatalogCommentProcess process = new CatalogCommentProcess(this.getDbUser(request),this.getTcmISLocale(request));				
		String comment = "";

		if( ! "addcomment".equalsIgnoreCase(uAction) ) {
			if( request.getParameter("comments") == null || request.getParameter("comments").trim().length() == 0 ) {
				Vector<ClientInventoryCommentsViewBean> v = 
					(Vector<ClientInventoryCommentsViewBean>)process.search(clientCommentsBean,uAction );
				if( v.size() == 1)
					comment = v.get(0).getComments();
			}
			request.setAttribute("comments", comment);
		}
		else
		{
			process.deleteComments(clientCommentsBean);
			if( clientCommentsBean.getComments().trim().length() != 0 ) 
				process.insertNewComments( clientCommentsBean);						
			if( request.getParameter("callback").length() != 0 ) {
				request.setAttribute("callback","true" );
			}
		}		
		return (mapping.findForward("success"));
	}
}
