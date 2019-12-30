package com.tcmis.client.operations.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.operations.beans.CustDropShipReceivingViewBean;
import com.tcmis.client.operations.process.DropshipReceivingProcess;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;
import com.tcmis.client.operations.beans.DropShipReceivingInputBean;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class DropshipReceivingAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "dropshipreceivingmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    if (form == null) {
      //this.saveTcmISToken(request);
    }

    //populate drop downs
    ShipmentHistoryProcess igdprocess = new ShipmentHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
    if (this.getSessionObject(request, "hubPrefViewOvBeanCollection") == null) {

      VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
      this.setSessionObject(request, vvDataProcess.getActiveVvLotStatus(), "vvLotStatusBeanCollection");

      request.setAttribute("searchWhat", "PO");
    }

    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      DropShipReceivingInputBean inputBean = new DropShipReceivingInputBean();
      inputBean.setDoTrim(true);
      BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

      PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
      BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

      //check what button was pressed and determine where to go
      if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        //log.debug("Here in Search");

        this.saveTcmISToken(request);
        //check if it's chemical or non-chemical
        DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));

        //get search result
        Collection c = process.getResult(inputBean, personnelBean.getPermissionBean().hasLocationPermission("DropshipReceiving", null, inputBean.getFacilityId(), inputBean.getCompanyId()),personnelBean);
        //add result and pass it to the jsp page
        request.setAttribute("companyId",inputBean.getCompanyId());
        request.setAttribute("facilityId",inputBean.getFacilityId());
        this.setSessionObject(request, c, "dropshipReceivingViewBeanCollection");
        request.setAttribute("receivingViewRelationBeanCollection", process.createRelationalObject(c));
        return (mapping.findForward("showresults"));
      }
      else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));
          this.setExcel(response, "Dropship");
          process.getExcelReport(inputBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale"),personnelBean).write(response.getOutputStream());
          return noForward;
      }
      else if (((DynaBean) form).get("action") != null && "receive".equalsIgnoreCase(((String) ((DynaBean) form).get("action"))) ||
                  (inputBean.getDuplicatePkgLine() != null && inputBean.getDuplicatePkgLine().length() > 0) ||
                  (inputBean.getDuplicateKitLine() != null && inputBean.getDuplicateKitLine().length() > 0)) {
        //log.debug("Here in receive");
        if (!this.isTcmISTokenValid(request, true)) {
          BaseException be = new BaseException("Duplicate form submission");
          be.setMessageKey("error.submit.duplicate");
          throw be;
        }
        this.saveTcmISToken(request);
        Vector savedBeanVector = new Vector((Collection)this.getSessionObject(request, "dropshipReceivingViewBeanCollection"));
        // Cast form to DynaBean
        DynaBean dynaForm = (DynaBean) form;
        List receivingViewBeans = (List) dynaForm.get("receivingViewBean");
        Iterator iterator = receivingViewBeans.iterator();
        //int count = 0;
        Collection receivingViewBeanInputCollection = new Vector();
        DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        while (iterator.hasNext()) {
          org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
/*          
          String expirationDate = com.tcmis.common.util.StringHandler.emptyIfNull(lazyBean.get("expirationDate"));
          if ("Indefinite".equalsIgnoreCase(expirationDate)) {
            expirationDate = "01/01/3000";
          }
          if (expirationDate.length() > 0 && expirationDate.length() == 10) {
            lazyBean.set("expirationDate", expirationDate);
          }  
          */
          
          CustDropShipReceivingViewBean receivingViewBean = new CustDropShipReceivingViewBean();
          BeanHandler.copyAttributes(lazyBean, receivingViewBean, getTcmISLocale(request));
//          receivingViewBean = process.processIndefinite(receivingViewBean);
          receivingViewBeanInputCollection.add(receivingViewBean);
        }
        receivingViewBeanInputCollection = process.copyAttributes(inputBean, receivingViewBeanInputCollection, savedBeanVector);
        if ((inputBean.getDuplicateLine() != null && inputBean.getDuplicateLine().length() > 0) || (inputBean.getDuplicateKitLine() != null && inputBean.getDuplicateKitLine().length() > 0) ||
            (inputBean.getDuplicatePkgLine() != null && inputBean.getDuplicatePkgLine().length() > 0)) {
          request.setAttribute("duplicateLine", "Yes");
        }
        //now I can pass the collection of true data beans to the process
        Collection c = new Vector();
        c = process.createRelationalObject(receivingViewBeanInputCollection);
        HashMap receivedReceiptResult = new HashMap();
        receivedReceiptResult = process.receiveSelected(inputBean, c, personnelId);
        Collection finalReceivedReceiptsCollection = (Collection) receivedReceiptResult.get("RECEIVEDRECEIPTSCOLLECTION");
        String errorMessage = (String) receivedReceiptResult.get("ERRORMESSAGE");
        String receivedReceipts = (String) receivedReceiptResult.get("RECEIVEDRECEIPTS");
        Collection finalNewBeanCollection = process.flattenRelationship(inputBean, receivingViewBeanInputCollection, finalReceivedReceiptsCollection);
        //log.info("Final collectionSize here " +finalReceivedReceiptsCollection.size() + " newBeanVector  "+receivingViewBeanInputCollection.size()+"  finalNewBeanVector "+finalNewBeanCollection.size()+"");
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("receivedReceipts", receivedReceipts);
        request.setAttribute("companyId",inputBean.getCompanyId());
        request.setAttribute("facilityId",inputBean.getFacilityId());
        this.setSessionObject(request, finalNewBeanCollection, "dropshipReceivingViewBeanCollection");
        request.removeAttribute("receivingViewRelationBeanCollection");
        request.setAttribute("receivingViewRelationBeanCollection", finalReceivedReceiptsCollection);
        return (mapping.findForward("showresults"));

        /*
             } else if (inputBean.getSubmitCreateReport() != null && inputBean.getSubmitCreateReport().trim().length() > 0) {
                   ResourceLibrary resource = new ResourceLibrary("report");
                   DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));
                   Collection c = new Vector((Collection)this.getSessionObject(request, "dropshipReceivingViewBeanCollection"));
                   File dir = new File(resource.getString("excel.report.serverpath"));
                   File file = File.createTempFile("excel", ".xls", dir);
                   process.writeExcelFile(inputBean, process.createRelationalObject(c), file.getAbsolutePath());
                   this.setSessionObject(request, resource.getString("report.hosturl") + resource.getString("excel.report.urlpath") + file.getName(), "filePath");
                   request.setAttribute("doexcelpopup", "Yes");
                   request.setAttribute("receivingViewRelationBeanCollection", process.createRelationalObject(c));
                   if (inputBean.getCategory().equalsIgnoreCase("chemicals")) {
          return (mapping.findForward("showchemical"));
                   } else {
          return (mapping.findForward("shownonchemical"));
                   }
         */
      } else {
        //facilities - dock drop down
        DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));
        request.setAttribute("myFacilityDockCollection", process.getMyFacilityDockDropdownData(personnelId.intValue()));
        return mapping.findForward("success");
      }
      //this.setSessionObject(request, null, "dropshipReceivingViewBeanCollection");

    } else {
      this.setSessionObject(request, null, "dropshipReceivingViewBeanCollection");
      return mapping.findForward("success");
    }
  }
}