package com.tcmis.client.peiprojects.process;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.client.peiprojects.erw.PeiProjectData;
import com.tcmis.client.peiprojects.erw.PeiProjectDocumentsData;
import com.tcmis.client.peiprojects.erw.PeiProjectSavingsData;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.erw.RegisterTable;

public class PrintPdfProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public PrintPdfProcess(String client) {
	super(client);
    }

 public String buildProjectPdf(Collection peiProjectViewBeanCollection,Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection
	) throws Exception {
	ResourceLibrary resource = new ResourceLibrary("report");
	File dir = new File(resource.getString("report.serverpath"));
	File file = File.createTempFile("peiproject", ".pdf", dir);

	try {
	 ACJEngine en = new ACJEngine();
	 en.setDebugMode(true);
	 en.setX11GfxAvailibility(false);
	 en.setTargetOutputDevice(ACJConstants.PDF);

	 ACJOutputProcessor eClient = new ACJOutputProcessor();
	 String fontMapPath = resource.getString("report.font.path");
	 String templatPath = resource.getString("report.template.path");
	 String templateName = resource.getString("report.template.name");
	 String writeFilePath = resource.getString("report.serverpath");
	 //en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
	 eClient.setPathForFontMapFile(fontMapPath);

	 en.readTemplate(templatPath + templateName);
	 /*TemplateManager tm = en.getTemplateManager();
	 tm.setLabel( "CUSTOMER",getClient());*/

	 AppDataHandler ds = new AppDataHandler();
	 try {
		RegisterTable[] rt = getProjectData(peiProjectViewBeanCollection,vvKeywordCollection,vvStatusCollection,vvCategoryCollection,vvPriorityCollection);
		for (int i = 0; i < rt.length; i++) {
		 ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
			rt[i].getWhere());
		}
//		en.setDataSource(ds);
	 }
	 catch (Exception e) {
		e.printStackTrace();
		throw e;
	 }

	 try {
		//AppDataHandler ds = new AppDataHandler();
		RegisterTable[] rt1 = getProjectSavingsData(peiProjectViewBeanCollection);
		for (int i = 0; i < rt1.length; i++) {
		 ds.RegisterTable(rt1[i].getData(), rt1[i].getName(), rt1[i].getMethods(),
			rt1[i].getWhere());
		}
	 }
	 catch (Exception e) {
		e.printStackTrace();
		throw e;
	 }

	 try {
		RegisterTable[] rt2 = getProjectDocumentData(peiProjectViewBeanCollection);
		for (int i = 0; i < rt2.length; i++) {
		 ds.RegisterTable(rt2[i].getData(), rt2[i].getName(), rt2[i].getMethods(),
			rt2[i].getWhere());
		}
	 }
	 catch (Exception e) {
		e.printStackTrace();
		throw e;
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

 public RegisterTable[] getProjectData(Collection projectData,Collection vvKeywordCollection,Collection vvStatusCollection,Collection vvCategoryCollection,Collection vvPriorityCollection) throws Exception {
	boolean result = true;
	RegisterTable[] rt = new RegisterTable[1];
	try {
	 rt[0] = new RegisterTable(PeiProjectData.getVector(projectData,vvKeywordCollection,vvStatusCollection,vvCategoryCollection,vvPriorityCollection), "PROJECTDATA",
		PeiProjectData.getFieldVector(), null);
	}
	catch (Exception e1) {
	 e1.printStackTrace();
	 throw e1;
	}
	return rt;
 }

 public RegisterTable[] getProjectSavingsData(Collection projectData) throws Exception {
	boolean result = true;
	RegisterTable[] rt1 = new RegisterTable[1];
	try {
	 rt1[0] = new RegisterTable(PeiProjectSavingsData.getVector(projectData), "PROJECTSAVINGSDATA",
		PeiProjectSavingsData.getFieldVector(), null);
	}
	catch (Exception e1) {
	 e1.printStackTrace();
	 throw e1;
	}
	return rt1;
 }

 public RegisterTable[] getProjectDocumentData(Collection projectData) throws Exception {
 boolean result = true;
 RegisterTable[] rt2 = new RegisterTable[1];
 try {
	rt2[0] = new RegisterTable(PeiProjectDocumentsData.getVector(projectData), "PROJECTDOCUMENTDATA",
	 PeiProjectDocumentsData.getFieldVector(), null);
 }
 catch (Exception e1) {
	e1.printStackTrace();
	throw e1;
 }
 return rt2;
}




}