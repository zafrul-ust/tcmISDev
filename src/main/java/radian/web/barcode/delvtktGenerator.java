package radian.web.barcode;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

import radian.tcmis.both1.reports.RegisterTable;
import radian.web.tcmisResourceLoader;

public class delvtktGenerator {
	Log log = LogFactory.getLog(this.getClass());

	public delvtktGenerator() {
	}

	public void buildTest(Vector AllTheData, String NameofFile) throws Exception {
		generateDeliveryTicketPDF(AllTheData, tcmisResourceLoader.getsavelabelpath(), NameofFile);
	}

	public String generateDeliveryTicketPDF(Vector deliveryTicketData, String saveDir, String NameofFile) throws Exception {

		String fileName = saveDir + "deliverytkt" + NameofFile + ".pdf";

		try {
			ACJEngine en = new ACJEngine();
			ACJEngine.setX11GfxAvailibility(false);
			en.setTargetOutputDevice(ACJConstants.PDF);
			en.readTemplate(tcmisResourceLoader.getlabeltempplatepath() + "delvtkt.erw");

			AppDataHandler ds = new AppDataHandler();
			RegisterTable[] rt = getData(deliveryTicketData);
			for (int i = 0; i < rt.length; i++) {
				ds.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
			}
			en.setDataSource(ds);

			ACJOutputProcessor eClient = new ACJOutputProcessor();
			eClient.setPathForFontMapFile(tcmisResourceLoader.getfontpropertiespath());
			eClient.setReportData(en.generateReport());
			eClient.setPDFProperty("FileName", fileName);
			eClient.generatePDF();
		}
		catch (Exception e) {
			log.error("Error generating DeliveryTicket", e);
			throw e;
		}
		return fileName;
	}

	private RegisterTable[] getData(Vector reportData1) {
		RegisterTable[] rt = new RegisterTable[1];
		rt[0] = new RegisterTable(delvtktData.getVector(reportData1, 16), "DELVERYTKT", delvtktData.getFieldVector(16), null);
		return rt;
	}
}
