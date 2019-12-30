package com.tcmis.internal.supply.action;

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
import com.tcmis.internal.supply.beans.ItemBuyerCommentsBean;
import com.tcmis.internal.supply.process.ItemNotesProcess;

/******************************************************************************
 * Controller for UpdateReceiptNotes
 * @version 1.0
     ******************************************************************************/
public class ItemNotesAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "itemnotes");
      request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
    //populate fields
    ItemNotesProcess notesProcess = new ItemNotesProcess(this.getDbUser(request));     
    ItemBuyerCommentsBean inputBean = new ItemBuyerCommentsBean();
    BeanHandler.copyAttributes(form, inputBean);      

    //If the search button was pressed the getSubmitSearch() value will be not null
    if (((DynaBean) form).get("addNotes") != null && 
          ((String)((DynaBean) form).get("addNotes")).length() > 0) {                              
      //access to who is logged in
      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
                
      //Pass the success receipt msg in request
      notesProcess.addNotes(inputBean,personnelBean);            
      request.setAttribute("notesMessage",inputBean.getItemId()+"");
      
    } else {
      // first check for a valid item id
      if (inputBean.getItemId()==null) {
         return mapping.findForward("systemerrorpage");
      }
       
      //Pass the result item id in request
      request.setAttribute("itemNotesColl",notesProcess.getNotes(inputBean));
    }

    return (mapping.findForward("showresults"));        
  }
}
