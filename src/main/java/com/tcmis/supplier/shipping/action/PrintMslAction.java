package com.tcmis.supplier.shipping.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingViewBean;
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;
import com.tcmis.supplier.shipping.process.LabelProcess;
import com.tcmis.common.admin.process.ZplDataProcess;

public class PrintMslAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	  request.setAttribute("requestedPage", "printunitboxlabels");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}

	 LabelInputBean labelInputBean = new LabelInputBean();
	 labelInputBean.setPaperSize("31");
	 Collection suppPackViewBeanCollection = new Vector();
	 BeanHandler.copyAttributes(form, labelInputBean,getTcmISLocale(request));

  // Cast form to DynaBean
	DynaBean dynaForm = (DynaBean) form;
	List labelBeans = (List) dynaForm.get("supplierPackingViewBean");
	Iterator iterator = labelBeans.iterator();
  Vector boxIdV = new Vector();
  Vector palletIdV = new Vector();
  Vector mrLineV = new Vector();

  while (iterator.hasNext()) {
   org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
    commons.beanutils.LazyDynaBean) iterator.next();

   SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
   BeanHandler.copyAttributes(lazyBean, supplierPackingViewBean,getTcmISLocale(request));
   if (supplierPackingViewBean.getOk() !=null && supplierPackingViewBean.getOk().length() > 0)
   {
     //log.info(""+labelInputBean.getUserAction()+"   "+supplierPackingViewBean.getPrNumber()+"   "+supplierPackingViewBean.getLineItem()+"");
     if (labelInputBean.getUserAction().equalsIgnoreCase("printCaseMSL"))
     {
       String boxId = supplierPackingViewBean.getBoxId();
       if (boxId != null && !boxIdV.contains(boxId))
      {
       boxIdV.add(boxId);
       supplierPackingViewBean.setPackingGroupId(null);
       suppPackViewBeanCollection.add(supplierPackingViewBean);
      }
     }
     else if (labelInputBean.getUserAction().equalsIgnoreCase("printPalletMSL"))
     {
      String palletId = supplierPackingViewBean.getPalletId();
      if (palletId != null && !palletIdV.contains(palletId))
      {
       palletIdV.add(palletId);
       suppPackViewBeanCollection.add(supplierPackingViewBean);
      }
     }
     else if (labelInputBean.getUserAction().equalsIgnoreCase("printPlacardLabels"))
     {
      String palletId = supplierPackingViewBean.getPalletId();
      if (palletId != null && !palletIdV.contains(palletId))
      {
       palletIdV.add(palletId);
       suppPackViewBeanCollection.add(supplierPackingViewBean);
      }
     }
     else if (labelInputBean.getUserAction().equalsIgnoreCase("printPackingList"))
     {
      //printing one dd250s for each MR-Line
      String mrLine = supplierPackingViewBean.getPrNumber()+"-"+supplierPackingViewBean.getLineItem();
      if (mrLine != null && !mrLineV.contains(mrLine))
      {
       mrLineV.add(mrLine);
       suppPackViewBeanCollection.add(supplierPackingViewBean);
      }
     }
     else
     {       
       suppPackViewBeanCollection.add(supplierPackingViewBean);
     }
   }
  }

	//log.info("labelReceipts  "+labelInputBean.getLabelReceipts()+" printerPath  "+labelInputBean.getPrinterPath()+"");
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	LabelProcess labelProcess = new LabelProcess(this.getDbUser(request));

	if (suppPackViewBeanCollection.size() > 0) {
	 Collection locationLabelPrinterCollection = new Vector();
	 if (personnelBean.getPrinterLocation() != null &&
		personnelBean.getPrinterLocation().length() > 0 &&
		!"811".equalsIgnoreCase(labelInputBean.getPaperSize())) {
	    ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
    
    locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
		if (locationLabelPrinterCollection!=null && locationLabelPrinterCollection.size() > 1)
		{		
		 request.setAttribute("sourcePage", "printmsl");		 
		 request.setAttribute("locationLabelPrinterCollection",locationLabelPrinterCollection);
		 return mapping.findForward("printerchoice");
		}
	 }
	 else
	 {
		//Build PDF Labels here
		return mapping.findForward("pagenotavailable");
	 }
	 
	 String labelUrl ="";
	 if (labelInputBean.getUserAction().equalsIgnoreCase("printTable"))
	 {
		 labelUrl = labelProcess.buildPrintTable(personnelBean,labelInputBean,locationLabelPrinterCollection,suppPackViewBeanCollection);
	 }
   else if (labelInputBean.getUserAction().equalsIgnoreCase("printCaseMSL"))
	 {        
     Object[] results = labelProcess.buildPrintCaseMSL(personnelBean,labelInputBean,locationLabelPrinterCollection,suppPackViewBeanCollection);
     labelUrl = ""+results[1]+"";
   }
   else if (labelInputBean.getUserAction().equalsIgnoreCase("printPalletMSL"))
	 {
		 Object[] results = labelProcess.printPalletMSL(personnelBean,labelInputBean,locationLabelPrinterCollection,suppPackViewBeanCollection);
     labelUrl = ""+results[1]+"";
   }
   else if (labelInputBean.getUserAction().equalsIgnoreCase("printPlacardLabels"))
	 {
		 Object[] results = labelProcess.buildPrintPlacardLabels(personnelBean,labelInputBean,locationLabelPrinterCollection,suppPackViewBeanCollection);
     labelUrl = ""+results[1]+"";
   }
	 else if (labelInputBean.getUserAction().equalsIgnoreCase("printPackingList"))
	 {
		 Object[] results = labelProcess.buildPrintPackingList(suppPackViewBeanCollection);
         labelUrl = ""+results[1]+"";
     }
	 
	 if (labelUrl.length() > 0)
	 {
			 //this.setSessionObject(request, labelUrl, "filePath");
       request.setAttribute("filePath", labelUrl);
       return mapping.findForward("viewfile");
	 }
	 else
	 {
		return mapping.findForward("systemerrorpage");
	 }
	}
	else {
	 return mapping.findForward("systemerrorpage");
	}
 }
} //end of class