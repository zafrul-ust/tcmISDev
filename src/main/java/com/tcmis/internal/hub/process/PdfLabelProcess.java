package com.tcmis.internal.hub.process;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import java.io.File;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.internal.hub.erw.SeagateLabelView;

public class PdfLabelProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public PdfLabelProcess(String client) {
	super(client);
    }

 public String buildSeagateLabels(Collection seagateLabelDataCollection
	) throws Exception {
	ResourceLibrary resource = new ResourceLibrary("label");
	File dir = new File(resource.getString("label.serverpath"));
	File file = File.createTempFile("seagatelabel", ".pdf", dir);

	try {
	 ACJEngine en = new ACJEngine();
	 en.setDebugMode(true);
	 en.setX11GfxAvailibility(false);
	 en.setTargetOutputDevice(ACJConstants.PDF);

	 ACJOutputProcessor eClient = new ACJOutputProcessor();
	 String fontMapPath = resource.getString("label.font.path");
	 String templatPath = resource.getString("label.template.path");
	 String templateName = resource.getString("label.template.seagate");
	 String writeFilePath = resource.getString("label.serverpath");
	 //en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
	 eClient.setPathForFontMapFile(fontMapPath);

	 en.readTemplate(templatPath + templateName);
	 //TemplateManager tm = en.getTemplateManager();
	 try {
		AppDataHandler ds = new AppDataHandler();
		RegisterTable[] rt = getSeagateData(seagateLabelDataCollection);
		for (int i = 0; i < rt.length; i++) {
		 Vector v1 = rt[i].getData();
		 Vector v2 = rt[i].getMethods();
		 ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
			rt[i].getWhere());
		}
		en.setDataSource(ds);
	 }
	 catch (Exception e) {
		e.printStackTrace();
		throw e;
	 }

	 eClient.setReportData(en.generateReport());
	 eClient.setPDFProperty("FileName", "" + writeFilePath + file.getName());
	 eClient.generatePDF();
	}
	catch (Exception ex1) {
	 ex1.printStackTrace();
	 throw ex1;
	}

	return resource.getString("label.urlpath") +
	 file.getName();
 }

 public RegisterTable[] getSeagateData(Collection labelData) throws Exception {
	boolean result = true;
	RegisterTable[] rt = new RegisterTable[1];
	try {
	 rt[0] = new RegisterTable(SeagateLabelView.getVector(labelData), "BARCODE",
		SeagateLabelView.getFieldVector(), null);
	}
	catch (Exception e1) {
	 e1.printStackTrace();
	 throw e1;
	}
	return rt;
 }
}