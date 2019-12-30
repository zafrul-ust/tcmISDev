package com.tcmis.internal.hub.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.process.LabelProcess;


public class LabelRollsAction extends TcmISBaseAction {
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
			String next = "success";
			if (!isLoggedIn(request)) {
				request.setAttribute("requestedPage", "labelrolls");
				request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
				next = "login";
			}
			else
			{
				PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
				LabelInputBean input = new LabelInputBean();
			    BeanHandler.copyAttributes(form, input);
		        if (!StringHandler.isBlankString(input.getUserAction())) {
		        	if(!StringHandler.isBlankString(input.getPrinterLocation()))
		        		doPrint(input,user);
		        	else
		        	{	
			        	ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
			        	if(!input.hasPaperSize())
			        		input.setPaperSize("31");
			        	Vector <LocationLabelPrinterBean> locationLabelPrinterCollection = (Vector <LocationLabelPrinterBean>)zplDataProcess.getLocationLabelPrinter(user, input);
			        	
			        	if(locationLabelPrinterCollection == null || locationLabelPrinterCollection.size() == 0){
			        		request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noprinterlocation"));
							return (mapping.findForward("error"));
			        	}
			        	else if (locationLabelPrinterCollection != null && locationLabelPrinterCollection.size() > 1)
			        	{
							request.setAttribute("paperSize", input.getPaperSize());
							request.setAttribute("sourcePage", "labelrolls");
							request.setAttribute("locationLabelPrinterCollection", locationLabelPrinterCollection);
							next = "printerchoice";
			        	}
			        	else
			        	{
			        		input.setPrinterPath(locationLabelPrinterCollection.firstElement().getPrinterPath());
			        		input.setLabelReceipts("");
			        		doPrint(input,user);
							request.setAttribute("doexcelpopup", "Yes");
							next = "viewfile";
			        	}
			        }
		
		       	}

			}
			
			return (mapping.findForward(next));
		}
		
		private void doPrint(LabelInputBean bean, PersonnelBean user) throws BaseException, Exception
		{
			ResourceLibrary resource = new ResourceLibrary("label");
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
			File tempDir = new File(resource.getString("label.serverpath"));
			File jnlp = File.createTempFile(bean.getLabelType() + "jnlp", ".jnlp", tempDir);
			File testFile = File.createTempFile(bean.getLabelType() + "Label", ".txt", tempDir);
			PrintWriter pw = new PrintWriter(new FileOutputStream(testFile.getAbsolutePath()));
			StringBuilder zpl = new StringBuilder();
			String lineFeed = "" + (char) (13) + (char) (10);
			
			LabelProcess labelProcess = new LabelProcess(this.getDbUser(request));

			try {
				if(bean.getLabelType().equalsIgnoreCase("receipt"))
				{
					Vector<LabelInputBean> results = (Vector<LabelInputBean>)labelProcess.getReceiptOrDocSequences("RECEIPT_SEQ", bean.getLabelQuantity(), user);
					
					if(bean.isPaperSize11()){
						for(LabelInputBean seq:results)
						{
							zpl.append("^XA").append(lineFeed);
							zpl.append("^BY2").append(lineFeed);
							zpl.append("^LH0,0").append(lineFeed);	
							zpl.append("^FO10,35^A0N,30^BCN,40^FD"+seq.getLabelSequenceNum()+"^FS").append(lineFeed);
							zpl.append("^FO125,15^A0N,20,15^FR^FDRCPT^FS").append(lineFeed);
							zpl.append("^PQ1").append(lineFeed);
							zpl.append("^XZ").append(lineFeed);
						}
					}
					else{ // template for default label size 31
						for(LabelInputBean seq:results)
						{
							zpl.append("^XA").append(lineFeed);
							zpl.append("^BY2").append(lineFeed);
							zpl.append("^LH0,0").append(lineFeed);	
							zpl.append("^FO190,40^A0N,90^BCN,60^FD"+seq.getLabelSequenceNum()+"^FS").append(lineFeed);
							zpl.append("^FO10,20^A0N,200,75^FR^FDRCPT^FS").append(lineFeed);
							zpl.append("^PQ1").append(lineFeed);
							zpl.append("^XZ").append(lineFeed);
						}
					}
				}
				else if(bean.getLabelType().equalsIgnoreCase("pdoc"))
				{			
					Vector<String> results = (Vector<String>)labelProcess.getPDocSequences(bean.getLabelQuantity());
					String theDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					for(String seq:results)
					{
						
						int padding = 8 - seq.length();
						StringBuilder docIdStrFinal = new StringBuilder(""); 
						for(int j = 0; j < padding;j++)
							docIdStrFinal.append("0");
						docIdStrFinal.append(seq);
						zpl.append("^XA").append(lineFeed);
						zpl.append("^BY2").append(lineFeed);
						zpl.append("^LH0,0").append(lineFeed);
						zpl.append("^FO250,40^AE^BCN,70^FDPDOC"+docIdStrFinal+"^FS").append(lineFeed);
						zpl.append("^GB200,0,200^FS").append(lineFeed);
						zpl.append("^FO10,20^A0N,200,75^FR^FDPDOC^FS").append(lineFeed);
						zpl.append("^FO300,160^A0N,40,40^FR^FD").append(theDate).append("^FS").append(lineFeed);
						zpl.append("^PQ1").append(lineFeed);
						zpl.append("^XZ").append(lineFeed);
					}
				}
				else
				{			
					Vector<LabelInputBean> results = (Vector<LabelInputBean>)labelProcess.getReceiptOrDocSequences("INBOUND_SHIPMENT_DOC_ID_SEQ", bean.getLabelQuantity(), user);
					
					for(LabelInputBean seq:results)
					{
						String docIdStr= seq.getLabelSequenceNum();
						int padding = 8 - docIdStr.length();
						StringBuilder docIdStrFinal = new StringBuilder(""); 
						for(int j = 0; j < padding;j++)
							docIdStrFinal.append("0");
						docIdStrFinal.append(docIdStr);
						zpl.append("^XA").append(lineFeed);
						zpl.append("^BY2").append(lineFeed);
						zpl.append("^LH0,0").append(lineFeed);
						zpl.append("^FO250,40^AE^BCN,70^FDRDOC"+docIdStrFinal+"^FS").append(lineFeed);
						zpl.append("^GB200,0,200^FS").append(lineFeed);
						zpl.append("^FO10,20^A0N,200,75^FR^FDRDOC^FS").append(lineFeed);
						zpl.append("^PQ1").append(lineFeed);
						zpl.append("^XZ").append(lineFeed);
					}
				}
				pw.println(zpl.toString());
			}
			finally {
				pw.close();
			}
			
			com.tcmis.common.util.ZplHandler.writeJnlpFileToDisk(jnlp.getAbsolutePath(), testFile.getName(), bean.getPrinterPath(), zpl.toString());
			setSessionObject(request, resource.getString("label.hosturl") + resource.getString("label.urlpath") + jnlp.getName(), "filePath");		
		}
}
