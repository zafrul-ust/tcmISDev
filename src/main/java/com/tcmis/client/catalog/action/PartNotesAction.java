package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.catalog.beans.CatPartCommentBean;
import com.tcmis.client.catalog.beans.PartCommentsInputBean;
import com.tcmis.client.catalog.process.PartNumberCommentsProcess;

import java.util.Vector;
/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
     ******************************************************************************/
public class PartNotesAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "partnotesmain");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
    if ( form == null ) return (mapping.findForward("success"));
    
//  populate fields
    PartNumberCommentsProcess notesProcess = new PartNumberCommentsProcess(this.getDbUser(request));     
    CatPartCommentBean inputBean = new CatPartCommentBean();
//	notesProcess.getsearchResult(inputBean.getCatalogId(),inputBean.getCatPartNo());

    String action = (String) ( (DynaBean) form).get("uAction");
    BeanHandler.copyAttributes(form, inputBean);  
    
    if ( "search".equals(action) ) {
        request.setAttribute("partNotesColl",
    	notesProcess.getsearchResult(inputBean.getCatPartNo(),inputBean.getCatalogId()));
    	return (mapping.findForward("success"));
    }
    
    if ( "update".equals(action) ) {
        Collection<CatPartCommentBean> beans  = BeanHandler.getBeans((DynaBean)form, "CatPartCommentBean",inputBean);      
        for(CatPartCommentBean bean:beans) {
        	if( bean.getOk() != null ) {
        		if( bean.getOk().equals("new") ) {
        			if( bean.getComments().trim().length() !=0 ) {
        				notesProcess.insertComment(bean);
        			}
        		}
        		else {
        			notesProcess.updateComment(bean);
        		}
        	}
        }
        request.setAttribute("partNotesColl",
        		notesProcess.getsearchResult(inputBean.getCatPartNo(),inputBean.getCatalogId()));
        
    }
    
    if ( "delete".equals(action) ) {
        Collection<CatPartCommentBean> beans  = BeanHandler.getBeans((DynaBean)form, "CatPartCommentBean",inputBean);      
        for(CatPartCommentBean bean:beans) {
        	if( bean.getOk() != null && !bean.getOk().equals("new")) {
        		notesProcess.deleteComment(bean.getCommentId().toString());
        	}
        }
        request.setAttribute("partNotesColl",
        		notesProcess.getsearchResult(inputBean.getCatPartNo(),inputBean.getCatalogId()));
        
    }
    
    return (mapping.findForward("success"));        
  }
}
