package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.catalog.beans.ItemSequenceGeneratorBean;
import com.tcmis.internal.catalog.process.ItemSequenceGeneratorProcess;

public class ItemSequenceGeneratorAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "approvedworkareas");
			request.setAttribute("requestedURLWithParameters",
			this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		ActionForward next = mapping.findForward("success");
		ItemSequenceGeneratorBean sequenceBean = new ItemSequenceGeneratorBean(form,this.getTcmISLocale());
		
		if (sequenceBean.isGenerate()) {
			ItemSequenceGeneratorProcess process = new ItemSequenceGeneratorProcess(this.getDbUser(request));
			Collection<String> idColl = process.generateItems(sequenceBean);
			JSONArray idArray = new JSONArray(idColl);
			
			PrintWriter out = response.getWriter();
			out.write(idArray.toString(3));
			out.close();
		}
		
		return next;
	}
}
