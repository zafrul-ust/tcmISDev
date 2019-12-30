package com.tcmis.supplier.shipping.process;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.supplier.shipping.erw.PackConfirmData;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class PackConfirmPrintTableProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public PackConfirmPrintTableProcess(String client) {
	super(client);
    }

 public String buildPdf(Collection supplierPackingViewBeanCollection) throws Exception {
	ResourceLibrary resource = new ResourceLibrary("report");
	File dir = new File(resource.getString("report.serverpath"));
	File file = File.createTempFile("packconfirm", ".pdf", dir);
  
	try {
	 ACJEngine en = new ACJEngine();
	 en.setDebugMode(true);
	 en.setX11GfxAvailibility(false);
	 en.setTargetOutputDevice(ACJConstants.PDF);

	 ACJOutputProcessor eClient = new ACJOutputProcessor();
	 String fontMapPath = resource.getString("report.font.path");
	 String templatPath = resource.getString("report.template.path");
	 String templateName = resource.getString("packconfirm.template");
	 String writeFilePath = resource.getString("report.serverpath");
	 //en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
	 eClient.setPathForFontMapFile(fontMapPath);

	 en.readTemplate(templatPath + templateName);

	 AppDataHandler ds = new AppDataHandler();
	 try {
		RegisterTable[] rt = getData(supplierPackingViewBeanCollection);
		for (int i = 0; i < rt.length; i++) {
		 ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
			rt[i].getWhere());
		}
	 }
	 catch (Exception e) {
		e.printStackTrace();
		throw e;
	 }

	 TemplateManager tmp = en.getTemplateManager();
	 Iterator iterator = supplierPackingViewBeanCollection.iterator();
	 while (iterator.hasNext()) {
		 SupplierPackingViewBean bean = (SupplierPackingViewBean) iterator.next();
		 tmp.setLabel("SUPPLIERLOCATION",bean.getShipFromLocationName());
		 break;
	 }

	 en.setDataSource(ds);
	 eClient.setReportData(en.generateReport());
	 eClient.setPDFProperty("FileName", "" + writeFilePath + file.getName());
	 eClient.generatePDF();
	}
	catch (Exception ex1) {
	 ex1.printStackTrace();
	 throw ex1;
	}

	return resource.getString("report.hosturl") +
	 resource.getString("report.urlpath") +
	 file.getName();
 }

 public RegisterTable[] getData(Collection supplierPackingViewBeanCollection) throws Exception {
	boolean result = true;
	RegisterTable[] rt = new RegisterTable[1];
	try {
	 rt[0] = new RegisterTable(PackConfirmData.getVector(supplierPackingViewBeanCollection), "PACKCONFIRMDATA",PackConfirmData.getFieldVector(), null);
	}
	catch (Exception e1) {
	 e1.printStackTrace();
	 throw e1;
	}
	return rt;
 }

}