package com.tcmis.internal.print.process;

import java.io.File;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.print.erw.PackingGroupInstructionData;
import com.tcmis.internal.hub.erw.RegisterTable;

public class PrintPackingGroupInstructionProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public PrintPackingGroupInstructionProcess(String client) {
	super(client);
    }

 public Object [] buildPackingGroupInstructionPdf(Collection packingGroupInstructionViewBeanCollection) throws Exception {
	ResourceLibrary resource = new ResourceLibrary("report");
	File dir = new File(resource.getString("report.serverpath"));
	File file = File.createTempFile("PackingGroupInstruction", ".pdf", dir);
  String fileAbsolutePath = file.getAbsolutePath();

  try {
	 ACJEngine en = new ACJEngine();
	 en.setDebugMode(true);
	 en.setX11GfxAvailibility(false);
	 en.setTargetOutputDevice(ACJConstants.PDF);

	 ACJOutputProcessor eClient = new ACJOutputProcessor();
	 String fontMapPath = resource.getString("report.font.path");
	 String templatPath = resource.getString("report.template.path");
	 String templateName = resource.getString("packinggroupinstruction.template");
	 String writeFilePath = resource.getString("report.serverpath");
	 //en.setCacheOption(true, "" + writeFilePath + file.getName() + ".joi");
	 eClient.setPathForFontMapFile(fontMapPath);

	 en.readTemplate(templatPath + templateName);

	 AppDataHandler ds = new AppDataHandler();
	 try {
		RegisterTable[] rt = getPackingGroupInstructionData(packingGroupInstructionViewBeanCollection);
		for (int i = 0; i < rt.length; i++) {
		 ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
			rt[i].getWhere());
		}
	 }
	 catch (Exception e) {
		//e.printStackTrace();
    log.debug(e.getMessage());
    throw e;
	 }

	 en.setDataSource(ds);
	 eClient.setReportData(en.generateReport());
	 eClient.setPDFProperty("FileName", "" + writeFilePath + file.getName());
	 eClient.generatePDF();
	}
	catch (Exception ex1) {
	 //ex1.printStackTrace();
   log.debug(ex1.getMessage());
   throw ex1;
	}

  String labelUrl = resource.getString("report.hosturl") + resource.getString("report.urlpath") + file.getName();
  Object[] objs = {fileAbsolutePath,labelUrl};
	return objs;
 }

 public RegisterTable[] getPackingGroupInstructionData(Collection packingGroupInstructionViewBeanCollection) throws Exception {
	boolean result = true;
	RegisterTable[] rt = new RegisterTable[1];
	try {
	 rt[0] = new RegisterTable(PackingGroupInstructionData.getVector(packingGroupInstructionViewBeanCollection), "PGIDATA",PackingGroupInstructionData.getFieldVector(), null);
	}
	catch (Exception e1) {
	 e1.printStackTrace();
	 throw e1;
	}
	return rt;
 }

}