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
import com.tcmis.client.catalog.beans.FacLocAppPartCommentBean;
import com.tcmis.client.catalog.beans.FacLocAppPartCommentsViewBean;
import com.tcmis.client.catalog.beans.PartCommentsInputBean;
import com.tcmis.client.catalog.process.WorkAreaCommentsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class WorkAreaCommentsAction
extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "workareacommentsmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) return mapping.findForward("success");

		//if form is not null we have to perform some action
		if (request.getParameter("catPartNo") == null ||
				request.getParameter("partGroupNo") == null) {
			return mapping.findForward("systemerrorpage");
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		//copy date from dyna form to the data form
		FacLocAppPartCommentBean bean = new FacLocAppPartCommentBean();
		BeanHandler.copyAttributes(form, bean);
		WorkAreaCommentsProcess workAreaCommentsProcess = new WorkAreaCommentsProcess(this.getDbUser(request),this.getTcmISLocale(request));
		
		if ( ( (DynaBean) form).get("submitUpdate") != null &&
				( (String) ( (DynaBean) form).get("submitUpdate")).length() > 0) {

			Vector<FacLocAppPartCommentBean> v =
				(Vector<FacLocAppPartCommentBean>)	BeanHandler.getBeans((DynaBean)form, "bean", new FacLocAppPartCommentBean() );
			for (FacLocAppPartCommentBean cbean:v) {
				if( cbean.getDeleteCheckBox() == null && !"changed".equalsIgnoreCase(cbean.getUpdateStatus() ) )
						continue;
				if ( cbean.getDeleteCheckBox() != null && !cbean.getDeleteCheckBox().equalsIgnoreCase("New" ) ) {
					System.out.println("delete"+cbean.getComments());
					workAreaCommentsProcess.deleteComment(cbean.getDeleteCheckBox());
				}
				else {
					cbean.setFacilityId(bean.getFacilityId());
					cbean.setApplication(bean.getApplicationId());
					cbean.setCatalogId(bean.getCatalogId());
					cbean.setCatPartNo(bean.getCatPartNo());
					cbean.setPartGroupNo(bean.getPartGroupNo());
					cbean.setEnteredBy(personnelId);
					cbean.setComments(cbean.getComments());
					if (cbean.getCommentId()== null) {
						System.out.println("new"+cbean.getComments());
						workAreaCommentsProcess.insertComment(cbean);
					}
					else if ("changed".equalsIgnoreCase(cbean.getUpdateStatus())) {
						System.out.println("update"+cbean.getComments());
						workAreaCommentsProcess.updateComment(cbean);
					}
				}
			}
		}
		Vector workAreaCommentsBean = (Vector) workAreaCommentsProcess.getsearchResult(
				bean.getCatPartNo(), bean.getPartGroupNo().toString(), bean.getCatalogId(),
				bean.getFacilityId(), bean.getApplicationId());
		workAreaCommentsBean.add(new FacLocAppPartCommentsViewBean());
		request.setAttribute("workAreaCommentsBeanCollection", workAreaCommentsBean);
		return mapping.findForward("success");
	}
}