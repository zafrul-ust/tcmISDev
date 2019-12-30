package com.tcmis.internal.hub.action;
//package com.tcmis.internal.hub.action;

  import java.math.BigDecimal;
  import java.util.Collection;
import java.util.Vector;

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

  import com.tcmis.internal.hub.beans.ProductionBatchViewBean;
import com.tcmis.internal.hub.process.ProductionBatchViewProcess;
import com.tcmis.internal.hub.process.HubInventoryGroupVesselOvProcess;
import com.tcmis.internal.hub.beans.BatchPicklistViewBean;
import com.tcmis.internal.hub.process.BatchPicklistViewProcess;
  /******************************************************************************
   * Controller for BlendingBatchInfo
   * @version 1.0
   ******************************************************************************/
  public class BlendingBatchInfoAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
        BaseException, Exception {

    //  login
    if (!this.isLoggedIn(request)) {
          request.setAttribute("requestedPage", "blendingbatchinfomain");
          request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
          return (mapping.findForward("login"));
      }
    //  main
    if ( ( (DynaBean) form).get("action") != null &&
            ( (String) ( (DynaBean) form).get("action")).equals("init")) {
        request.setAttribute("batchId", request.getParameter("batchId"));
        return mapping.findForward("success");
    }

    DynaBean dynaBean = (DynaBean) form;

    String action = (String) dynaBean.get("_action");
    if( action == null ) return mapping.findForward("error");

    BigDecimal pid = new BigDecimal(((PersonnelBean)this.getSessionObject(request,"personnelBean")).getPersonnelId());
    ProductionBatchViewProcess process = new ProductionBatchViewProcess(this.getDbUser(request));
    ProductionBatchViewBean bean = new ProductionBatchViewBean();
    //  get the input bean
    BeanHandler.copyAttributes(dynaBean, bean);

    //update inventory
    if ( action.equals("updateInventory")) {

        BatchPicklistViewBean pickBean = new BatchPicklistViewBean();
        BatchPicklistViewProcess pickproc = new BatchPicklistViewProcess(this.getDbUser(request));

        Collection beans1 = BeanHandler.getBeans(dynaBean, "BatchPicklistViewBean",new BatchPicklistViewBean());
        BatchPicklistViewBean anybean = pickproc.update(beans1,pid);
        bean.setBatchId(anybean.getBatchId());
        request.setAttribute("batchInfoBean",process.getSearchResult(bean).toArray()[0]);

        return mapping.findForward("success");
    }

    //  release Inventory
    if ( action.equals("releaseInventory")) {
        process.releaseInventory(bean,pid);
        request.setAttribute("batchId",bean.getBatchId());
    }
    //show Inventory
    else if ( action.equals("showInventory")) {
        boolean queryQcDate = false;
        if( bean.getLotApprovalDate() != null ) {
                queryQcDate = true;
        }
        new BatchPicklistViewProcess(this.getDbUser(request)).showInventory( bean.getBatchId(),request,queryQcDate);
    }

    //search area
    //drop down set up
    new HubInventoryGroupVesselOvProcess(this.getDbUser(request)).setHubBean(request,pid);

    if ( action.equals("display") ) {
        if( bean.getBatchId() != null ) {
            request.setAttribute("batchId",bean.getBatchId());
            bean = (ProductionBatchViewBean) process.getSearchResult(bean).toArray()[0];
        }
        if( bean.getProductionDate() == null )
            bean.setProductionDate(new java.util.Date());
        String errMsg = (String) request.getSession().getAttribute("approveLotErrMsg");

        if( errMsg != null && !errMsg.equals("OK")) {
            log.info("db ERROR [[procedure p_batch_approve]]:" + errMsg );

            org.apache.struts.action.ActionErrors actionerrors = new org.apache.struts.action.ActionErrors();
            actionerrors.add(org.apache.struts.action.ActionErrors.GLOBAL_MESSAGE, new org.apache.struts.action.ActionMessage("error.db.procedure","p_batch_approve"));
            this.saveErrors(request,actionerrors);
        }
        request.getSession().removeAttribute("approveLotErrMsg");
        request.setAttribute("batchInfoBean",bean);
    }
    //start batch
    else if ( action.equals("startup")) {
        BigDecimal batchId = new BigDecimal( (Integer) process.startBatch(bean,pid).toArray()[0] );
        bean.setBatchId(batchId);
        request.setAttribute("batchInfoBean",process.getSearchResult(bean).toArray()[0]);
    }
    //update batch
    else if ( action.equals("update")) {
        process.updateBatch(bean,pid);
        request.setAttribute("batchInfoBean",process.getSearchResult(bean).toArray()[0]);
    }
    //approveLot
    else if ( action.equals("approveLot")) {
        Vector v = (Vector) process.approveLot(bean,pid);
        request.setAttribute("batchId",bean.getBatchId());
        request.getSession().setAttribute("approveLotErrMsg",v.get(0));
    }
    //commit Inventory
    else if ( action.equals("commitInventory")) {
        Collection coll = process.batchPickList(bean,pid);
        String errMsg = (String) coll.toArray()[1];
    /// saveError method is in Action
        if( errMsg != null  && !errMsg.equals("OK")) {
            org.apache.struts.action.ActionErrors actionerrors = new org.apache.struts.action.ActionErrors();
            actionerrors.add(org.apache.struts.action.ActionErrors.GLOBAL_MESSAGE, new org.apache.struts.action.ActionMessage(errMsg));
            this.saveErrors(request,actionerrors);
        }
        request.setAttribute("batchInfoBean",process.getSearchResult(bean).toArray()[0]);

    }
    return mapping.findForward("success");
    }
  }