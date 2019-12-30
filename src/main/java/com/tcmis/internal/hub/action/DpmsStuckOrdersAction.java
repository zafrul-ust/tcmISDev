package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.internal.hub.beans.PolchemDpmsStuckOrderBean;
import com.tcmis.internal.hub.beans.PolchemDpmsStuckOrderInputBean;
import com.tcmis.internal.hub.process.PolchemDpmsStuckOrderProcess;


public class DpmsStuckOrdersAction
        extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

    	String forward = "success";      
    	request.setAttribute("startSearchTime", new Date().getTime());
       
        PolchemDpmsStuckOrderInputBean polchemDpmsStuckOrderInputBean = new PolchemDpmsStuckOrderInputBean();
        BeanHandler.copyAttributes(form, polchemDpmsStuckOrderInputBean,getTcmISLocale(request));

        PolchemDpmsStuckOrderProcess process = new PolchemDpmsStuckOrderProcess(this.getDbUser(request),getTcmISLocaleString(request));         

		// Search
		if (polchemDpmsStuckOrderInputBean.getAction() == null ) {
			request.setAttribute("polchemDpmsStuckOrderBeanCollection", process.getSearchResult());
			this.saveTcmISToken(request);  
		}
         //  Create Excel
        else if (polchemDpmsStuckOrderInputBean.getAction().equals("createExcel")) {
              this.setExcel(response, "newGenerate");
              process.getExcelReport(polchemDpmsStuckOrderInputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
              return noForward;
        }

          request.setAttribute("endSearchTime", new Date().getTime() ); 
          return (mapping.findForward(forward));
    }
}
