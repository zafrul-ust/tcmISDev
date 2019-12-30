package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.NewChemHeaderViewBean;
import com.tcmis.internal.hub.beans.NewItemInputBean;
import com.tcmis.internal.hub.process.NewItemProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * Controller for NewItemAction
 * @version 1.0
     ******************************************************************************/
public class NewItemAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "newitemform");
      request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
        
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    //SupplierPermissionProcess supplierPermissionProcess = new SupplierPermissionProcess(this.getDbUser(request));
    //if form is not null we have to perform some action
    if (form != null) {
       this.saveTcmISToken(request);
      //copy date from dyna form to the data form
      NewItemInputBean bean = new NewItemInputBean();
      BeanHandler.copyAttributes(form, bean);

	    request.setAttribute("catalogCompanyId",bean.getCatalogCompanyId());
	    request.setAttribute("catalogId",bean.getCatalogId());
	    request.setAttribute("catPartNo",bean.getCatPartNo());
	    request.setAttribute("receiptId",bean.getReceiptId());
	    request.setAttribute("inventoryGroup",bean.getInventoryGroup());
      //check what button was pressed and determine where to go
			if (((DynaBean) form).get("action") != null && "submit".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //log.debug("HERE IN SUBMIT");
	      if (!this.isTcmISTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          this.saveTcmISToken(request);
          throw be;
        }
        DynaBean dynaBean = (DynaBean) form;
	      Collection beans = BeanHandler.getBeans(dynaBean,"newItemInputBean", new NewItemInputBean());
				NewItemProcess process = new NewItemProcess(this.getDbUser(request));
				//copy user data
				request.setAttribute("newChemHeaderViewBean",process.copyHeaderInfo(bean));
				request.setAttribute("newChemDetailViewBeanCollection",process.copyDetailInfo(beans));
				request.setAttribute("sizeUnitNetWtRequiredCollection",process.getSizeUnitNetWtRequired());
				request.setAttribute("sizeUnitConversionBeanCollection",process.getFromUnit());
	      //create new cat add
				bean.setRequestorId(personnelId.toString());
				String result = process.createNewChem(bean,beans);
				if ("OK".equals(result)) {
					request.setAttribute("submitted","Yes");
				}else if ("ERROR".equals(result)){
					request.setAttribute("submitted","Error");
				}else {
					request.setAttribute("submitted","Message");
					request.setAttribute("messageToUser",result);
				}
				return (mapping.findForward("success"));
      }else {
        //log initial data for dropdown
				//log.debug("HERE IN LOAD INITIAL DATA ");
				NewItemProcess process = new NewItemProcess(this.getDbUser(request));
				NewChemHeaderViewBean newChemHeaderViewBean = process.getHeaderInfo(bean);
				Collection detailData = process.getDetailInfo(newChemHeaderViewBean);
				if (detailData.size() > 0) {
					request.setAttribute("newChemHeaderViewBean",newChemHeaderViewBean);
					request.setAttribute("newChemDetailViewBeanCollection",detailData);
					request.setAttribute("sizeUnitNetWtRequiredCollection",process.getSizeUnitNetWtRequired());
					request.setAttribute("sizeUnitConversionBeanCollection",process.getFromUnit());
					request.setAttribute("submitted","No");
					return (mapping.findForward("success"));
				}else {
					return (mapping.findForward("genericerrorpage"));
				}
      }
    }
    return (mapping.findForward("success"));
  }
}
