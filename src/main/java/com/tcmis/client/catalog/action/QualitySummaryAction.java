package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.QualitySummaryInputBean;
import com.tcmis.client.catalog.beans.QualitySummaryViewBean;
import com.tcmis.client.catalog.process.QualitySummaryProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import java.math.BigDecimal;
import java.util.Collection;

public class QualitySummaryAction extends TcmISBaseAction{
	
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws BaseException, Exception {

        if (!this.isLoggedIn(request)) {
            request.setAttribute("requestedPage", "qualitySummary");
            request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
            return (mapping.findForward("login"));
        }

        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
        BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
        
        QualitySummaryInputBean bean = new QualitySummaryInputBean();
        BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

        QualitySummaryProcess qualitySummaryProcess = new QualitySummaryProcess(this.getDbUser(request));
        request.setAttribute("qualitySummaryViewBean", qualitySummaryProcess.getSearchData(bean, personnelId));
        Collection c = qualitySummaryProcess.getQualifiedProducts(bean);
        request.setAttribute("qualityProductsRelationColl", qualitySummaryProcess.createRelationalObject(c));

        return mapping.findForward("success");
    }
}
