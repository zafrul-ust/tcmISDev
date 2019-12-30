package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.beans.CatPartCommentBean;
import com.tcmis.client.catalog.beans.PartCommentsInputBean;
import com.tcmis.client.catalog.process.PartNumberCommentsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class PartNumberCommentsAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showworkareacomments");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	//if form is not null we have to perform some action
	if (form != null) {
	 //copy date from dyna form to the data form
	 PartCommentsInputBean bean = new PartCommentsInputBean();
	 BeanHandler.copyAttributes(form, bean);

	 /*if (!this.isTokenValid(request, true) &&
		( ( (DynaBean) form).get("submitUpdate") != null &&
		 ( (String) ( (DynaBean) form).get("submitUpdate")).length() > 0)) {
			BaseException be = new BaseException("Duplicate form submission");
			be.setMessageKey("error.submit.duplicate");
			this.saveToken(request);
			throw be;
			 }
			 this.saveToken(request);*/

	 if ( ( (DynaBean) form).get("submitUpdate") != null &&
		( (String) ( (DynaBean) form).get("submitUpdate")).length() > 0) {

		PartNumberCommentsProcess partNumberCommentsProcess = new
		 PartNumberCommentsProcess(this.getDbUser(request));

		String[] commentIdArray = bean.getCommentId();
		String[] updateStatusArray = bean.getUpdateStatus();
		String[] commentsArray = bean.getComments();
		String[] deleteCheckBoxArray = bean.getDeleteCheckBox();
		Vector deleteCheckBox = new Vector(2);
		for (int j = 0; j < deleteCheckBoxArray.length; j++) {
		 deleteCheckBox.add(deleteCheckBoxArray[j]);
		 //log.debug("id Delete " + j + ":" + deleteCheckBoxArray[j]);
		 if (!"New".equalsIgnoreCase(deleteCheckBoxArray[j])) {
			partNumberCommentsProcess.deleteComment(deleteCheckBoxArray[j]);
		 }
		}

		//Vector v = new Vector(commentIdArray.length);
		for (int i = 0; i < commentIdArray.length; i++) {
		 if (!deleteCheckBox.contains(commentIdArray[i])) {
			//log.debug("id " + i + ":" + commentIdArray[i]);
			CatPartCommentBean catPartCommentBean = new CatPartCommentBean();

			catPartCommentBean.setCatalogId(bean.getCatalogId());
			catPartCommentBean.setCatPartNo(bean.getCatPartNo());
			catPartCommentBean.setEnteredBy(personnelId);
			catPartCommentBean.setComments(commentsArray[i]);
			catPartCommentBean.setCompanyId(this.getDbUser(request).toUpperCase());
			if ("New".equalsIgnoreCase(commentIdArray[i])) {
			 partNumberCommentsProcess.insertComment(catPartCommentBean);
			}
			else if ("changed".equalsIgnoreCase(updateStatusArray[i])) {
			 catPartCommentBean.setCommentId(new BigDecimal(commentIdArray[i]));
			 partNumberCommentsProcess.updateComment(catPartCommentBean);
			}
			//v.add(facLocAppPartCommentBean);
		 }
		}

		Collection workAreaCommentsBean = partNumberCommentsProcess.getsearchResult(
		 bean.getCatPartNo(), bean.getCatalogId());
		request.setAttribute("catPartCommentBeanCollection", workAreaCommentsBean);
		return mapping.findForward("success");
	 }
	 else {
		if (request.getParameter("catPartNo") == null ||
		 request.getParameter("partGroupNo") == null) {
		 return mapping.findForward("systemerrorpage");
		}
		else {
		 String catPartNo = request.getParameter("catPartNo");
		 String catalogId = request.getParameter("catalogId");
		 String selectedFacilityId = request.getParameter("facilityId");

		 //call the process here
		 PartNumberCommentsProcess partNumberCommentsProcess = new
			PartNumberCommentsProcess(this.getDbUser(request));
		 Collection partNumberCommentsBean = partNumberCommentsProcess.
			getsearchResult(catPartNo, catalogId);
		 request.setAttribute("catPartCommentBeanCollection",
			partNumberCommentsBean);
		 request.setAttribute("catPartNo", catPartNo);
		 //request.setAttribute("partGroupNo", partGroupNo);
		 request.setAttribute("catalogId", catalogId);
		 request.setAttribute("facilityId", selectedFacilityId);
		 //request.setAttribute("applicationId", selectedWorkArea);
		 //this.saveToken(request);
		 return mapping.findForward("success");
		}
	 }
	}
	return mapping.findForward("success");
 }
}