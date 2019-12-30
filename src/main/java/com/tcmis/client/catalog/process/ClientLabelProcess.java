package com.tcmis.client.catalog.process;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.client.catalog.beans.ClientLabelInputBean;
import com.tcmis.client.catalog.erw.CalLabelData;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.erw.RegisterTable;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ClientLabelProcess
extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	private ACJEngine erw = null;
	private OutputStream os = null;
	private TemplateManager tm = null;
	private ACJOutputProcessor ec = null;

	public ClientLabelProcess(String client) {
		super(client);
	}

	public String load(Collection detailData) {
		String url = "";
		Random rand = new Random();
		int tmpRnd = rand.nextInt();
		Integer randomNum = new Integer(tmpRnd);

		try {
			erw = new ACJEngine();
			erw.setDebugMode(true);
			erw.setX11GfxAvailibility(false);
			erw.setTargetOutputDevice(ACJConstants.PDF);
			ec = new ACJOutputProcessor();
			ResourceLibrary resource = new ResourceLibrary("label");
			String fontMapPath = resource.getString("label.font.path");
			ec.setPathForFontMapFile(fontMapPath);

			//loading template
			String templatPath = resource.getString("label.template.path");
			String templatName = resource.getString("continental.template.name");
			erw.readTemplate("" + templatPath + templatName);
			//modifying template
			tm = erw.getTemplateManager();

			//register table...
			String tempwritefilepath = resource.getString("label.serverpath");
			//erw.setCacheOption(true,"" + tempwritefilepath + "clientlabels" + randomNum.toString() + ".joi");

			AppDataHandler ds = new AppDataHandler();
			RegisterTable[] rt = getData(detailData);
			for (int i = 0; i < rt.length; i++) {
				Vector v1 = rt[i].getData();
				Vector v2 = rt[i].getMethods();
				ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
						rt[i].getWhere());
			}
			erw.setDataSource(ds);
			//generating report
			ec.setReportData(erw.generateReport());
			ec.setPDFProperty("FileName",
					"" + tempwritefilepath + "clientlabels" + randomNum.toString() + ".pdf");
			ec.setPDFProperty("ZipCompressed", new Boolean(false));
			ec.generatePDF();

			//send url back to client to open
			String tempurlpath = resource.getString("label.hosturl") +resource.getString("label.urlpath");
			url = tempurlpath + "clientlabels" + randomNum.toString() + ".pdf";
			//log.info("Client Label URL "+url+"");
		}
		catch (Exception e) {
			e.printStackTrace();
			url = "";
		}
		return url;
	}

	public RegisterTable[] getData(Collection detailData) throws Exception {
		boolean result = true;
		RegisterTable[] rt = new RegisterTable[1];
		try {
			rt[0] = new RegisterTable(CalLabelData.getVector(detailData), "LABEL",
					CalLabelData.getFieldVector(), null);
		}
		catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}
		return rt;
	}

	public void writeZplFile(ClientLabelInputBean bean,
			String filePath) throws BaseException, Exception {
		PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
		pw.print(loadtemplates("clientcallabel.txt"));

		//print rows
		String[] catPartNoArray = bean.getCatPartNo();
		String[] partDescriptionArray = bean.getPartDescription();
		String[] shelfLifeArray = bean.getShelfLife();
		String[] customerPoArray = bean.getCustomerPo();
		String[] expirationDateArray = bean.getExpirationDate();
		String[] recertExpDateArray = bean.getRecertExpDate();
		String[] employeeNumArray = bean.getEmployeeNum();
		String[] labelQtyArray = bean.getLabelQty();

		for (int i = 0; i < catPartNoArray.length; i++) {
			//StringBuilder reclabel = new StringBuilder();
			String linefeedd = "";
			linefeedd += (char) (13);
			linefeedd += (char) (10);

			pw.print("^XA" + linefeedd);
			pw.print("^XFPMC^FS" + linefeedd);
			pw.print("^FN1^FD" + catPartNoArray[i] + "^FS" + linefeedd);
			pw.print("^FN2^FD" + partDescriptionArray[i] + "^FS" + linefeedd);
			pw.print("^FN3^FD" + customerPoArray[i] + "^FS" + linefeedd);
			pw.print("^FN4^FD" + shelfLifeArray[i] + "^FS" + linefeedd);
			pw.print("^FN5^FD" + expirationDateArray[i] + "^FS" + linefeedd);
			pw.print("^FN6^FD" + recertExpDateArray[i] + "^FS" + linefeedd);
			pw.print("^FN7^FD" + employeeNumArray[i] + "^FS" + linefeedd);

			pw.print("^PQ" + labelQtyArray[i] + "" + linefeedd);
			pw.print("^XZ" + linefeedd);
		}

		pw.close();
	}

	public StringBuilder loadtemplates(String filepath) {
		StringBuilder itmlbltemp = new StringBuilder();
		String linefeedd = "";
		linefeedd += (char) (13);
		linefeedd += (char) (10);

		ResourceLibrary resource = new ResourceLibrary("label");
		String templatpath = resource.getString("label.template.path");

		try {
			BufferedReader buf = new BufferedReader(new FileReader("" + templatpath +
					filepath + ""));
			int i = 0;
			while ( (i = buf.read()) != -1) {
				String temp = buf.readLine();
				itmlbltemp.append("^" + temp + linefeedd);
			}
			buf.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return itmlbltemp;
	}
}