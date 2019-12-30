package com.tcmis.client.openCustomer.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.openCustomer.process.MrAllocationReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;

public class MrAllocationReportAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mrallocationreport");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");

		MrAllocationReportProcess mrAllocationReportProcess = new MrAllocationReportProcess(this.getDbUser(request),getTcmISLocaleString(request));

		String mrNumber = request.getParameter("mrNumber");
		String lineItem = request.getParameter("lineItem");
		String uAction = request.getParameter("uAction");
		if ("result".equalsIgnoreCase(uAction)) {
			request.setAttribute("startSearchTime", new Date().getTime() );

			Collection c = mrAllocationReportProcess.getMrAllocationReport(mrNumber,lineItem);
			request.setAttribute("pkgOrderTrackWebPrOrderTrackDetailBeanCollection", c);

			request.setAttribute("endSearchTime", new Date().getTime() );
			return (mapping.findForward("success"));
		} else if ("getMSDSFileNames".equalsIgnoreCase(uAction)) {
			String refType = request.getParameter("refType");
			String refNumber = request.getParameter("refNumber");

			Collection<ReceiptDocumentViewBean> vv = mrAllocationReportProcess.getMsdsColl(refType,refNumber,mrNumber,lineItem);

			String fileNames = "";
			if( vv!=null && vv.size()!=0) {
				for(ReceiptDocumentViewBean bb: vv) {
					String filePath = bb.getDocumentUrl();
					
					
					
					if (bb.getDocumentUrl() != null && (bb.getDocumentUrl().startsWith("https://www.tcmis.com/msds/") || bb.getDocumentUrl().startsWith("https://www.tcmis.com/MSDS/")))
					{
						filePath = filePath.substring(27,filePath.length());
					}
					else if (bb.getDocumentUrl() != null && (filePath.startsWith("http://www.tcmis.com/msds/") || filePath.startsWith("http://www.tcmis.com/MSDS/")))
					{
						filePath = filePath.substring(26,filePath.length());
					}
					// The following codes are just for testing purpose.
					else if (bb.getDocumentUrl() != null && (filePath.startsWith("http://qa.tcmis.com/msds/") || filePath.startsWith("http://qa.tcmis.com/MSDS/")))
					{
						filePath = filePath.substring(25,filePath.length());
					}
					// This is for the general situation created for China project
					else if(bb.getDocumentUrl() != null){
						ResourceLibrary resource = new ResourceLibrary("label");
		                String hostUrl = resource.getString("label.hosturl");
		                
		                if(filePath.startsWith(hostUrl+"/msds/") || filePath.startsWith(hostUrl+"/MSDS/")) {
			                int trimLength = hostUrl.length() + 6;
			                
			                filePath = filePath.substring(trimLength,filePath.length());
		                }
					}

					if(fileNames.length() == 0)
						fileNames = filePath;
					else
						fileNames += ","+filePath;
				}
			}

			request.setAttribute("type", "msds");
			request.setAttribute("fileNames", fileNames);
			return (mapping.findForward("getFileNames"));
		} else if ("getCertFileNames".equalsIgnoreCase(uAction)) {
			ActionForward forward = mapping.findForward("getFileNames");
			String receiptId = request.getParameter("receiptId");
			Collection<ReceiptDocumentViewBean> vv = mrAllocationReportProcess.getCertsDocs(new BigDecimal(receiptId));

			BigDecimal documentId = null;
			String reqType = "certs";
			String fileNames = "";
			if( vv!=null && vv.size()!=0) {
				for(ReceiptDocumentViewBean bb: vv) {
					String filePath = bb.getDocumentUrl();
					if (bb.getDocumentUrl() != null) {
						if (bb.getDocumentUrl().startsWith("https://www.tcmis.com/MSDS/"))
						{
							filePath = filePath.substring(40,filePath.length());
						}
						else if (filePath.startsWith("http://www.tcmis.com/MSDS/"))
						{
							filePath = filePath.substring(39,filePath.length());
						}
						// The following codes are just for testing purpose.
						else if (filePath.startsWith("http://qa.tcmis.com/MSDS/"))
						{
							filePath = filePath.substring(28,filePath.length());
						}
						else if (filePath.contains("receipt_documents")){
							documentId = bb.getDocumentId();
							request.setAttribute("receiptId", receiptId);
							request.setAttribute("documentId", documentId);
							reqType = "receipts";
						}
					}

					if(fileNames.length() == 0)
						fileNames = filePath;
					else
						fileNames += ","+filePath;
				}
			}

			request.setAttribute("type", reqType);
			request.setAttribute("fileNames", fileNames);
			return forward;
		} else if ("requestMSDS".equalsIgnoreCase(uAction)) {
			String itemId = request.getParameter("itemId");
			String csrEmail = request.getParameter("csrEmail");
			mrAllocationReportProcess.sendRequest(csrEmail, personnelBean, itemId, getTcmISLocaleString(request));

			request.setAttribute("requestSent","Y");
			return (mapping.findForward("getFileNames"));
		}


		return mapping.findForward("success");
	} //end of method
} //end of class
