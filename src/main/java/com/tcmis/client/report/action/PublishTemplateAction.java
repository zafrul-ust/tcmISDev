package com.tcmis.client.report.action;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.report.process.*;
import com.tcmis.client.report.beans.PublishTemplateInputBean;
import com.tcmis.client.report.beans.AdHocTemplateBean;

/**
 * Controller for Publish or Unpublish template.
 */

public class PublishTemplateAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PublishTemplateInputBean inputBean = new PublishTemplateInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		PublishTemplateProcess process = new PublishTemplateProcess(this.getDbUser(request),getLocale(request));
		if ("submitPublishTemplate".equalsIgnoreCase(inputBean.getCalledFrom())) {
			if ("publish".equalsIgnoreCase(inputBean.getAction())) {
				request.setAttribute("tcmISError",process.publishTemplate(inputBean,personnelBean));
				if ("!Ok".equalsIgnoreCase(inputBean.getSuccessFlag())) {
					request.setAttribute("userGroupColl",process.getUserPublishUserGroups(personnelBean,inputBean.getTemplateId()));
				}else {
					request.setAttribute("userGroupColl",new Vector(0));
				}
			}else {
				request.setAttribute("tcmISError",process.unpublishTemplate(inputBean,personnelBean));
			 	if ("!Ok".equalsIgnoreCase(inputBean.getSuccessFlag())) {
					AdHocTemplateBean bean = process.getTemplate(inputBean.getTemplateId());
					request.setAttribute("owner",bean.getOwner());
					if ("PERSONNEL_ID".equalsIgnoreCase(bean.getOwner())) {
						request.setAttribute("userColl",process.getTemplatePublishToUsers(bean.getTemplateId()));
					}else if ("USER_GROUP_ID".equalsIgnoreCase(bean.getOwner())) {
						request.setAttribute("userGroupDesc",process.getUserGroupDescForTemplate(bean.getUserGroupId()));
					}
				}else {
					request.setAttribute("owner","");
					request.setAttribute("userColl",new Vector(0));
					request.setAttribute("userGroupDesc","");
				}
			}
		}else {
			if ("publish".equalsIgnoreCase(inputBean.getAction())) {
				request.setAttribute("userGroupColl",process.getUserPublishUserGroups(personnelBean,inputBean.getTemplateId()));
			}else {
				AdHocTemplateBean bean = process.getTemplate(inputBean.getTemplateId());
				request.setAttribute("owner",bean.getOwner());
				if ("PERSONNEL_ID".equalsIgnoreCase(bean.getOwner())) {
					request.setAttribute("userColl",process.getTemplatePublishToUsers(bean.getTemplateId()));
				}else if ("USER_GROUP_ID".equalsIgnoreCase(bean.getOwner())) {
				 	request.setAttribute("userGroupDesc",process.getUserGroupDescForTemplate(bean.getUserGroupId()));
				}
			}

		}
		request.setAttribute("successFlag",inputBean.getSuccessFlag());

		return (mapping.findForward("showinput"));
	}
}
