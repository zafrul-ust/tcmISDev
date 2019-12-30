package com.tcmis.internal.print.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.process.ShippingLabelProcess;

public class ShippingLabelAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "printshippinglabels");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		String forward = "success";
		
		if (form != null) {
			try {			
				LabelInputBean labelInputBean = new LabelInputBean();
				BeanHandler.copyAttributes(form, labelInputBean);
				PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
				
				boolean validParam = true;
				StringBuilder missingParam = new StringBuilder();
				
				// parameter check
				if(labelInputBean.getPrNumbers() == null){
					validParam = false;
					
					missingParam.append(getResourceBundleValue(request, 
							"error.required".replace("{0}", getResourceBundleValue(request,"label.prnumber"))));
					missingParam.append("\n");
				}
				
				if(labelInputBean.getLineItems() == null){
					validParam = false;
					
					missingParam.append(getResourceBundleValue(request, 
							"error.required".replace("{0}", getResourceBundleValue(request,"label.lineitem"))));
					missingParam.append("\n");
				}
				
				
				if(labelInputBean.getPaperSize() == null){
					labelInputBean.setPaperSize("34");
				}
				
				// end parameter check
				
				if(!validParam){
					request.setAttribute("tcmISError", missingParam.toString());
					forward = "error";
				}
				else {
					ShippingLabelProcess labelProcess = new ShippingLabelProcess();		
					
					//*** GET DATA ***
					Collection labelDataCollection = labelProcess.getLabelData(labelInputBean.getPrNumbers(), labelInputBean.getLineItems());
					
					if(labelDataCollection.isEmpty()) {
						request.setAttribute("tcmISError", getResourceBundleValue(request, "error.label.nodata"));
						forward = "error";
					}
					else {
						String printerLocation = !StringHandler.isBlankString(personnelBean.getPrinterLocation()) ?
								personnelBean.getPrinterLocation() : labelInputBean.getPrinterLocation();
			
						if (!StringHandler.isBlankString(printerLocation))
						{
							Collection locationLabelPrinterCollection = labelProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
							
							if(locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1){
								request.setAttribute("prNumbers", labelInputBean.getPrNumbers());
								request.setAttribute("lineItems", labelInputBean.getLineItems());
								request.setAttribute("paperSize", labelInputBean.getPaperSize());
								request.setAttribute("sourcePage", "printshippinglabels");
						
								request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
								return (mapping.findForward("printerchoice"));
							}
							else if(locationLabelPrinterCollection.size() < 1){
								request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noprinter") + " " + printerLocation);
								return (mapping.findForward("error"));
							}
							
							//*** BUILD ZPL ***
							String labelUrl = labelProcess.buildShippingLabels(labelDataCollection, locationLabelPrinterCollection);
		
							setSessionObject(request, labelUrl, "filePath");
							request.setAttribute("doexcelpopup", "Yes");
							forward = "viewfile";			
						}
						else {
							request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noprinterlocation"));
							return (mapping.findForward("error"));
						}
					}
				}
			}
			catch (BaseException be) {
				processExceptions(request, mapping, be);
			}
			catch (Exception e) {
				processExceptions(request, mapping, new BaseException(e));
			}
		}
		
		return (mapping.findForward(forward));
	}

}
