package com.tcmis.internal.invoice.action;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.NoRequestException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.common.db.*;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
import com.tcmis.internal.invoice.process.AjaxProcess;

/******************************************************************************
 *
 * @version 1.0
     ******************************************************************************/
public class AjaxAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

        //if (!this.isLoggedIn(request)) {
        //  return (mapping.findForward("login"));
        //}
        //if(!this.hasPermission(request, "Invoice", null)) {
        //  BaseException be = new BaseException("No permission");
        //  be.setMessageKey("error.permission.invalid");
        //  throw be;
        //}

    String client = (String)request.getParameter("client");
    System.out.println("CLIENT:" + client);
    //request.setAttribute("invoicePeriod", "10");
    AjaxProcess process = new AjaxProcess(getDbUser(request));
    PrintWriter writer = response.getWriter();
    writer.write(process.getMaxInvoicePeriod(client));
    writer.close();
/*
    try {
      InvoiceInputBean bean = new InvoiceInputBean();
      BeanHandler.copyAttributes(form, bean);

      if ("ARVIN_MERITOR".equalsIgnoreCase(bean.getClient())) {
        ArvinMeritorInvoiceProcess process = new ArvinMeritorInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("BAE".equalsIgnoreCase(bean.getClient())) {
        BaeInvoiceProcess process = new BaeInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("BOEING_DECATUR".equalsIgnoreCase(bean.getClient())) {
        //BoeingDecaturInvoiceProcess process = new BoeingDecaturInvoiceProcess(
        //    getDbUser(request));
        //type = "EXCEL";
        //wb = process.getInvoice(bean);
      }
      else if ("BOEING_OFFSITE".equalsIgnoreCase(bean.getClient())) {
        BoeingOffsiteInvoiceProcess process = new BoeingOffsiteInvoiceProcess(
            getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("CAL".equalsIgnoreCase(bean.getClient())) {
        CalInvoiceProcess process = new CalInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("DANA".equalsIgnoreCase(bean.getClient())) {
        DanaInvoiceProcess process = new DanaInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("DOE".equalsIgnoreCase(bean.getClient())) {
        SlacInvoiceProcess process = new SlacInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("DRS".equalsIgnoreCase(bean.getClient())) {
        CalInvoiceProcess process = new CalInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("GOLETAEW".equalsIgnoreCase(bean.getClient())) {
        GoletaewInvoiceProcess process = new GoletaewInvoiceProcess(getDbUser(
            request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("KELLY".equalsIgnoreCase(bean.getClient())) {
        KellyInvoiceProcess process = new KellyInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("L3".equalsIgnoreCase(bean.getClient())) {
        L3InvoiceProcess process = new L3InvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("SYRACUSE".equalsIgnoreCase(bean.getClient())) {
        SyracuseInvoiceProcess process = new SyracuseInvoiceProcess(getDbUser(
            request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("MILLER".equalsIgnoreCase(bean.getClient())) {
        MillerInvoiceProcess process = new MillerInvoiceProcess(getDbUser(
            request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("FSS".equalsIgnoreCase(bean.getClient())) {
        CalInvoiceProcess process = new CalInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("RAC".equalsIgnoreCase(bean.getClient())) {
        RacInvoiceProcess process = new RacInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("SAUER_DANFOSS".equalsIgnoreCase(bean.getClient())) {
        SauerInvoiceProcess process = new SauerInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("SWA".equalsIgnoreCase(bean.getClient())) {
        CalInvoiceProcess process = new CalInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("UTC_HSD".equalsIgnoreCase(bean.getClient())) {
        bean.setDivision("HSD");
        UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("UTC_PWA".equalsIgnoreCase(bean.getClient())) {
        bean.setDivision("PWA");
        UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else if ("UTC_SAC".equalsIgnoreCase(bean.getClient())) {
        bean.setDivision("SAC");
        UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
        process.getWordInvoice(bean, response.getWriter());
      }
      else {
        response.setHeader("Content-Disposition", "");
        response.setContentType("text/html");
        BaseException be = new BaseException("Invalid client");
        be.setMessageKey("error.client.invalid");
        throw be;
      }

      log.debug("done");
    }
    catch (BeanCopyException bce) {
      response.setHeader("Content-Disposition", "");
      response.setContentType("text/html");
      //error copying bean
      //the only reason to fail is if non numeric invoice period was entered
      log.fatal("BeanCopyException:" + bce.getMessage());
      BaseException bex = new BaseException(bce);
      bex.setMessageKey("error.invoiceperiod.invalid");
      throw bex;
    }
    catch (NoRequestException nrex) {
      response.setHeader("Content-Disposition", "");
      response.setContentType("text/html");
      //no data to create report
      log.info("NoRequestException:" + nrex.getMessage());
      BaseException bex = new BaseException(nrex);
      bex.setMessageKey("error.norequest");
      throw bex;
    }
    catch (NoDataException ndex) {
      response.setHeader("Content-Disposition", "");
      response.setContentType("text/html");
      //no data to create report
      log.info("NoDataException:" + ndex.getMessage());
      BaseException bex = new BaseException(ndex);
      bex.setMessageKey("error.nodata");
      throw bex;
    }
    catch (Exception e) {
      response.setHeader("Content-Disposition", "");
      response.setContentType("text/html");
      log.fatal("Exception:" + e.getMessage());
      e.printStackTrace(System.out);
      throw e;
    }
*/
    return mapping.findForward("default");
  }
}