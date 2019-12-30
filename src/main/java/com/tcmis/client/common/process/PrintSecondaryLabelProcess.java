package com.tcmis.client.common.process;

import com.tcmis.client.common.beans.MaterialBean;
import com.tcmis.client.common.beans.SecLblIconsInLabelViewBean;
import com.tcmis.client.common.beans.SecondaryLabelDataBean;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process for receiving qc
 * 
 * @version 1.0
 *****************************************************************************/
public class PrintSecondaryLabelProcess extends BaseProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public PrintSecondaryLabelProcess(String client, String locale) {
		super(client, locale);
	}

	public String buildSecondaryLabelsZpl(Collection secondaryLabelDataColl, Collection iconColl, MaterialBean materialDataBean, String labelQty) throws Exception {
		ResourceLibrary resource = new ResourceLibrary("label");
		File dir = new File(resource.getString("label.serverpath"));
		File file = File.createTempFile("labeltxt", ".txt", dir);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));
		String lineFeed = "";
		lineFeed += (char) (13);
		lineFeed += (char) (10);
		String templatePath = resource.getString("label.template.path");
		String printerResolution = "";

		try {
			BufferedReader buf = new BufferedReader(new FileReader("" + templatePath + "Supp_Warning_200_LM_4x4" + printerResolution + ".txt"));
			String line = null; // not declared within while loop
			while ((line = buf.readLine()) != null) {
				if (!line.startsWith("^FX**")) { // No need to send comments to the printer
					pw.print(line + lineFeed);
				}
			}
			buf.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			StringBuilder receiptDocLabel = new StringBuilder();
			receiptDocLabel.append("^XA").append(lineFeed);
			receiptDocLabel.append("^XF").append("00003").append("^FS").append(lineFeed);
			for (SecLblIconsInLabelViewBean iconBean : (Collection<SecLblIconsInLabelViewBean>)iconColl) {
				receiptDocLabel.append(iconBean.getIconZpl()).append(lineFeed);
			}
			receiptDocLabel.append("^").append("FN10");
			receiptDocLabel.append("^FD").append("" + materialDataBean.getCustomerMsdsNo() + "").append("^FS").append(lineFeed);
			receiptDocLabel.append("^").append("FN11");
			receiptDocLabel.append("^FD").append("" + materialDataBean.getManufacturer() + "").append("^FS").append(lineFeed);
			receiptDocLabel.append("^").append("FN12");
			//receiptDocLabel.append("^FD").append("" + materialDataBean.getTradeName() + "").append("^FS").append(lineFeed);        
			
			// limit each line to 50 chars   
			receiptDocLabel.append("^FD");
			String[] tradeNameTxtArray = materialDataBean.getTradeName().split(" ");
            int count = materialDataBean.getTradeName().length() + 1;
            for(int i=0; i<tradeNameTxtArray.length; i++){
            	if(count >= 50 || count + tradeNameTxtArray[i].length() + 1 >= 50){
            		if(i != 0)
            			receiptDocLabel.append("\\&");
            		receiptDocLabel.append(lineFeed);
            		count = 0;
            	}
            	receiptDocLabel.append(tradeNameTxtArray[i] + " ");
            	count += tradeNameTxtArray[i].length() + 1;
            }
            receiptDocLabel.append("^FS").append(lineFeed);
			receiptDocLabel.append("^").append("FN13^FD");

			Collection secondaryLabelTypeColl = new Vector();
			for  (SecondaryLabelDataBean secondaryLblDataBean : (Collection<SecondaryLabelDataBean>)secondaryLabelDataColl) {
				/*if (!secondaryLabelTypeColl.contains(secondaryLblDataBean.getTypeId())) {
					receiptDocLabel.append("\\&").append(lineFeed).append(secondaryLblDataBean.getTypeName()).append("\\&").append(lineFeed);
					secondaryLabelTypeColl.add(secondaryLblDataBean.getTypeId());
				}*/
				// again limit each line to 50 chars 
				String[] commentTxtArray = secondaryLblDataBean.getCommentAltTxt().split(" ");
				count = commentTxtArray.length;
				for(int i=0; i<commentTxtArray.length; i++){
					if(i == 0){
						receiptDocLabel.append(secondaryLblDataBean.getCommentId()).append(": ");
						count = secondaryLblDataBean.getCommentId().length() + 2;
					}
					
	            	if(count >= 50 || count + commentTxtArray[i].length() + 1 >= 50){
	            		if(i != 0)
	            			receiptDocLabel.append("\\&");
	            		receiptDocLabel.append(lineFeed);
	            		count = 0;
	            	}
	            	receiptDocLabel.append(commentTxtArray[i] + " ");
	            	count += commentTxtArray[i].length() + 1;
	            }
				receiptDocLabel.append("\\&").append(lineFeed);
				//receiptDocLabel.append(secondaryLblDataBean.getCommentId()).append(": ").append(secondaryLblDataBean.getCommentAltTxt()).append("\\&").append(lineFeed);
			}
			receiptDocLabel.append("^FS").append(lineFeed);

			count = 5;
			for (SecLblIconsInLabelViewBean iconBean : (Collection<SecLblIconsInLabelViewBean>)iconColl) {
				receiptDocLabel.append("^").append("FN").append("" + count + "");
				receiptDocLabel.append("^FD").append("" + iconBean.getIconName() + "").append("^FS").append(lineFeed);
				count++;
			}

			if (labelQty != null && labelQty.length() > 0) {
				receiptDocLabel.append("^PQ" + labelQty + "").append(lineFeed);
			}
			else {
				receiptDocLabel.append("^PQ1").append(lineFeed);
			}
			receiptDocLabel.append("^XZ").append(lineFeed);
			pw.print(receiptDocLabel);
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}

		pw.close();

		String labelUrl = resource.getString("label.hosturl") + resource.getString("label.urlpath") + file.getName();
		log.info("Secondary label URL: " + labelUrl);
		return labelUrl;
	}
}