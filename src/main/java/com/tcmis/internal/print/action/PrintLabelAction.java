package com.tcmis.internal.print.action;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.DeliveryDocumentViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import com.tcmis.internal.print.process.HubLabelProcess;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PrintLabelAction extends TcmISBaseAction
{
  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws BaseException, Exception
  {
    String result = "OK";
    PrintLabelInputBean inputBean = new PrintLabelInputBean();
    BeanHandler.copyAttributes(form, inputBean);

    LabelInputBean labelInputBean = new LabelInputBean();
    BeanHandler.copyAttributes(form, labelInputBean);
    if (log.isDebugEnabled()) {
      log.debug("got bean:" + inputBean);
      log.debug("got bean:" + labelInputBean);
    }
    try {
      if ((inputBean.getLabelType().equalsIgnoreCase("UPS")) && (!StringHandler.isBlankString(labelInputBean.getPackingGroupId())))
      {
        com.tcmis.client.ups.process.ShippingProcess process = new com.tcmis.client.ups.process.ShippingProcess(getDbUser(request));
        process.getUpsLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("Fedex")) && (!StringHandler.isBlankString(labelInputBean.getPackingGroupId())))
      {
        com.tcmis.client.fedex.process.ShippingProcess process = new com.tcmis.client.fedex.process.ShippingProcess(getDbUser(request));
        process.getFedexLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("UPSHAZMAT")) || ((inputBean.getLabelType().equalsIgnoreCase("UPSHAZMATUNKNOWN")) && (!StringHandler.isBlankString(labelInputBean.getPackingGroupId()))))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildPackingGroupInstruction(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("TCNPACKINGLIST")) && (!StringHandler.isBlankString(labelInputBean.getPalletId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildTcnPackingList(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("UNIT")) && (!StringHandler.isBlankString(labelInputBean.getBoxId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildUnitLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("EXTERNAL")) && (!StringHandler.isBlankString(labelInputBean.getBoxId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildExternalLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("PROJECTCODE")) && (!StringHandler.isBlankString(labelInputBean.getBoxId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildProjectCodeLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("FLASHPOINT")) && (!StringHandler.isBlankString(labelInputBean.getBoxId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildFlashPointLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("CASEMSL")) && ((!StringHandler.isBlankString(labelInputBean.getBoxId())) || (!StringHandler.isBlankString(labelInputBean.getPackingGroupId()))))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildCaseMslLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("PALLETMSL")) && ((!StringHandler.isBlankString(labelInputBean.getPalletId())) || (!StringHandler.isBlankString(labelInputBean.getConsolidationNumber()))))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildPalletMslLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("COMMERCIALLABEL")) && (!StringHandler.isBlankString(labelInputBean.getPalletId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildCommercialLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("TRACKINGLABEL")) && (!StringHandler.isBlankString(labelInputBean.getPalletId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildTrackingLabel(labelInputBean);
      }
      else if ((inputBean.getLabelType().equalsIgnoreCase("DD1348")) && (!StringHandler.isBlankString(labelInputBean.getPackingGroupId())))
      {
        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.buildPrintPackingList(labelInputBean);
      }
      else if (inputBean.getLabelType().equalsIgnoreCase("DELIVERYDOCS")) {
        DynaBean dynaBean = (DynaBean)form;
        Collection beans = BeanHandler.getBeans(dynaBean, "delyDocViewBean", new DeliveryDocumentViewBean());
        DeliveryDocumentViewBean bean = null;

        HubLabelProcess labelProcess = new HubLabelProcess(getDbUser(request));
        labelProcess.printDeliveryDocuments(beans, labelInputBean);
      }
      else {
    	log.error("Error printing label - Invalid label type:" + inputBean.getLabelType());
        throw new BaseException("Invalid label type:" + inputBean.getLabelType());
      }
    }
    catch (Exception e) {
    	log.error("Error printing label:" + e.getMessage(), e);	
    }
    PrintWriter writer = response.getWriter();
    writer.write(result);
    writer.close();

    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        session.invalidate();
      }
      catch (Exception e)
      {
      }
    }
    return noForward;
  }
}