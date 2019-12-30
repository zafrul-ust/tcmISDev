package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.distribution.beans.CatalogItemSpecListBean;
import com.tcmis.internal.distribution.process.SpecListProcess;

/******************************************************************************
 * Controller for CustomerAddressSearch
 * @version 1.0
 ******************************************************************************/
public class SpecListJDEAction
    extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

//login
	   	/*if (!this.isLoggedIn(request)) {
				request.setAttribute("requestedPage", "speclist");
				request.setAttribute("requestedURLWithParameters",
						this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
		}*/
		
//main
	  	if( form == null )
	      	return (mapping.findForward("success"));
	  	String uAction = (String) ( (DynaBean)form).get("uAction");

//result
	  	SpecListProcess process = new SpecListProcess(this);
	  	
		BigDecimal itemId = new BigDecimal( ((DynaBean)form).get("itemId").toString());
		BigDecimal sequenceNum = new BigDecimal(((DynaBean)form).get("jdeSequenceNumber").toString());
		BigDecimal lineNumber = null;
	    Object jdeOrderLineObj =  ((DynaBean)form).get("jdeOrderLine");
	    if(jdeOrderLineObj != null &&  !StringHandler.isBlankString(jdeOrderLineObj.toString()))
	    	lineNumber =  new BigDecimal(((DynaBean)form).get("jdeOrderLine").toString());
	    
	    if ("selectSpecs".equals(uAction)) {
	    	BigDecimal remainingShelfLifePercent = new BigDecimal(((DynaBean)form).get("shelfLifePercent").toString());
	    	Vector<SpecBean> beans = (Vector<SpecBean>)BeanHandler.getBeans((DynaBean) form, "catalogItemSpecListBean", new SpecBean(), getTcmISLocale(), "ok");
	    	beans.firstElement().setRemainingShelfLifePercent(remainingShelfLifePercent);
	    	request.setAttribute("tcmISErrors", process.setSelecedSpecListJDE(beans,sequenceNum,lineNumber));
			request.setAttribute("closeAttempt", true);
	    }
	    else
	        process.checkInsertDefaultSpec(itemId,sequenceNum,lineNumber);
	    Vector<CatalogItemSpecListBean> specListColl = (Vector<CatalogItemSpecListBean>)process.getSpecListJDEColl(itemId,sequenceNum, lineNumber);
		request.setAttribute("specListColl", specListColl);
		if(specListColl.size() > 0)
			request.setAttribute("shelfLifePercent", specListColl.firstElement().getRemainingShelfLifePercent());

	    return mapping.findForward("success");
  }
}