package radian.tcmis.server7.share.servlets;

//Nawaz 12-11-00

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 *
 * @author Nawaz Shaik
 * @version 1.0
 *          08-27-03 Code cleanup and removed true from request.getsession()
 *          11-25-03 Cleaning up the email sent for batch reports
 *          01-28-04 Making relative URLs to go to the property file to get hosturl
 */

public class AdHocBatch extends TcmISServletReportObj {
	private Vector reportfields = new Vector();
	private Hashtable mySendObj = null;
	private Hashtable inData = null;
	private String userId = null;

	public void doPost() throws ServletException, IOException {

	}

	public AdHocBatch(ServerResourceBundle b, TcmISDBModel d) {
		super();
		bundle = b;
		db = d;
	}

	protected void resetAllVars() {
		inData = null;
	}

	protected void print(TcmisOutputStreamServer out) {
		mySendObj = new Hashtable();
		mySendObj.put("MSG", "Request received");
		try {
			out.sendObject((Hashtable) mySendObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setInData(Hashtable myRecvObj) {
		inData = myRecvObj;
	}

	protected void getResult() {
		try {
			createReport();
		} catch (Exception E) {
			E.printStackTrace();
		}
	}

	protected void createReport() throws Exception {
		String emsg = "";
		try {
			Hashtable SelectedData = new Hashtable();
			Hashtable outdData = new Hashtable();
			SelectedData = (Hashtable) inData.get("SELECTED_DATA");
			//String method = SelectedData.get("METHOD").toString();
			reportfields = (Vector) SelectedData.get("REPORT_FIELDS");
			//System.out.println("In  createreport   ----"+reportfields+"\n\n");
			String function = (String) inData.get("WHICH_SCREEN");
			userId = inData.get("USER_ID").toString();
			String url = "";

			if (function.equalsIgnoreCase("ADHOC_USAGE")) {
				AdHocReports batchDb = new AdHocReports(bundle, db);
				outdData = batchDb.createReport(inData);
			} else if (function.equalsIgnoreCase("MATERIAL_MATRIX")) {
				MaterialMatrix batchDb = new MaterialMatrix(bundle, db);
				outdData = batchDb.createReport(inData);
			} else if (function.equalsIgnoreCase("WASTE_USAGE")) {
				WasteAhocReport batchDb = new WasteAhocReport(bundle, db);
				outdData = batchDb.createReport(inData);
			} else if (function.equalsIgnoreCase("FORMATTED_VOC_MONTHLY_USAGE")) {
				AdHocHourlyVocReport batchDb = new AdHocHourlyVocReport(bundle, db);
				outdData = batchDb.createReport(inData);
			}

			Boolean suc = (Boolean) outdData.get("SUCCEED");
			emsg = (String) outdData.get("MSG");

			if (suc.booleanValue()) {
				url = (String) outdData.get("URL");
				updateReport(url, function);
				sendEmail();
			} else {
				String subj = "The batch report you requested was not created.\n";
				emsg += " The batch report you requested was not created.\n";
				sendErrorEmail(subj, emsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String subj = "The batch report you requested was not created.\n";
			emsg += " The batch report you requested was not created.\n";
			sendErrorEmail(subj, emsg);
			throw e;
		}

	}

	public void updateReport(String url, String currentScreen) throws Exception {
		String reportName = null;

		String Name = inData.get("REPORT_NAME").toString();
		if (Name.length() > 0) {
			reportName = Name;
		} else {
			if (currentScreen.equalsIgnoreCase("ADHOC_USAGE")) {
				reportName = "ADHOC USAGE";
			} else if (currentScreen.equalsIgnoreCase("MATERIAL_MATRIX")) {
				reportName = "MATERIAL MATRIX";
			} else if (currentScreen.equalsIgnoreCase("WASTE_USAGE")) {
				reportName = "WASTE USAGE";
			} else if (currentScreen.equalsIgnoreCase("FORMATTED_VOC_MONTHLY_USAGE")) {
				reportName = "MONTHLY VOC REPORT";
			}

		}
		try {
			String query = "insert into batch_report (personnel_id,report_date,report_name,content)";
			query += " values(" + userId + ",sysdate,'" + reportName + "','" + url + "')";
			db.doUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void sendEmail() throws Exception {
		DBResultSet dbrs = null;
		try {
			String logonId = null;
			String query = "select logon_id from personnel where personnel_id = " + userId;
			ResultSet rs = null;
			dbrs = db.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				logonId = (BothHelpObjs.makeBlankFromNull(rs.getString("logon_id")));
			}

			String myC = db.getClient();
			String esub = "Your batch report(s) is ready.";
			String emsg = " The batch report you requested is now ready.\n Click link below:\n";
			String hosturl = radian.web.tcmisResourceLoader.gethosturl();
			if (myC.startsWith("Ray")) emsg = emsg + (" " + hosturl + "tcmIS/ray/batchreportviewermain.do");
			else if (myC.startsWith("DRS")) emsg = emsg + (" " + hosturl + "tcmIS/drs/batchreportviewermain.do");
			else if (myC.startsWith("SWA")) emsg = emsg + (" " + hosturl + "tcmIS/swa/batchreportviewermain.do");
			else if (myC.startsWith("LMCO")) emsg = emsg + (" " + hosturl + "tcmIS/lmco/batchreportviewermain.do");
			else if (myC.startsWith("Seagate")) emsg = emsg + (" " + hosturl + "tcmIS/seagate/batchreportviewermain.do");
			else if (myC.startsWith("BAE")) emsg = emsg + (" " + hosturl + "tcmIS/bae/batchreportviewermain.do");
			else if (myC.startsWith("UTC")) emsg = emsg + (" " + hosturl + "tcmIS/utc/batchreportviewermain.do");
			else if (myC.startsWith("SD")) emsg = emsg + (" " + hosturl + "tcmIS/sd/batchreportviewermain.do");
			else if (myC.startsWith("Miller")) emsg = emsg + (" " + hosturl + "tcmIS/miller/batchreportviewermain.do");
			else if (myC.startsWith("GM")) emsg = emsg + (" " + hosturl + "tcmIS/gm/batchreportviewermain.do");
			else if (myC.startsWith("CAL")) emsg = emsg + (" " + hosturl + "tcmIS/united/batchreportviewermain.do");
			else if (myC.startsWith("FEC")) emsg = emsg + (" " + hosturl + "tcmIS/fec/batchreportviewermain.do");
			else if (myC.startsWith("Boeing")) emsg = emsg + (" " + hosturl + "tcmIS/ula/batchreportviewermain.do");
			else if (myC.startsWith("AeroEco")) emsg = emsg + (" " + hosturl + "tcmIS/ael/batchreportviewermain.do");
			else if (myC.startsWith("Dana")) emsg = emsg + (" " + hosturl + "tcmIS/dana/batchreportviewermain.do");
			else if (myC.startsWith("ABLaero")) emsg = emsg + (" " + hosturl + "tcmIS/ablaero/batchreportviewermain.do");
			else if (myC.startsWith("DOE")) emsg = emsg + (" " + hosturl + "tcmIS/doe/batchreportviewermain.do");
			else if (myC.startsWith("IAI")) emsg = emsg + (" " + hosturl + "tcmIS/iai/batchreportviewermain.do");
			else if (myC.startsWith("L3")) emsg = emsg + (" " + hosturl + "tcmIS/l3/batchreportviewermain.do");
			else if (myC.startsWith("AM")) emsg = emsg + (" " + hosturl + "tcmIS/am/batchreportviewermain.do");
			else if (myC.startsWith("GEMA")) emsg = emsg + (" " + hosturl + "tcmIS/gema/batchreportviewermain.do");
			else if (myC.startsWith("PGE")) emsg = emsg + (" " + hosturl + "tcmIS/pge/batchreportviewermain.do");
			else if (myC.startsWith("QOS")) emsg = emsg + (" " + hosturl + "tcmIS/qos/batchreportviewermain.do");
			else if (myC.startsWith("DCX")) emsg = emsg + (" " + hosturl + "tcmIS/dcx/batchreportviewermain.do");
			else if (myC.startsWith("FORD")) emsg = emsg + (" " + hosturl + "tcmIS/ford/batchreportviewermain.do");
			else if (myC.startsWith("Algat")) emsg = emsg + (" " + hosturl + "tcmIS/algat/batchreportviewermain.do");
			else if (myC.startsWith("BAZ")) emsg = emsg + (" " + hosturl + "tcmIS/baz/batchreportviewermain.do");
			else if (myC.startsWith("CMM")) emsg = emsg + (" " + hosturl + "tcmIS/cmm/batchreportviewermain.do");
			else if (myC.startsWith("Kanfit")) emsg = emsg + (" " + hosturl + "tcmIS/kanfit/batchreportviewermain.do");
			else if (myC.startsWith("Fedco")) emsg = emsg + (" " + hosturl + "tcmIS/fedco/batchreportviewermain.do");
			else if (myC.startsWith("GD")) emsg = emsg + (" " + hosturl + "tcmIS/gd/batchreportviewermain.do");
			else if (myC.startsWith("IMCO")) emsg = emsg + (" " + hosturl + "tcmIS/imco/batchreportviewermain.do");
			else if (myC.startsWith("Kemfast")) emsg = emsg + (" " + hosturl + "tcmIS/kemfast/batchreportviewermain.do");
			else if (myC.startsWith("BL")) emsg = emsg + (" " + hosturl + "tcmIS/bl/batchreportviewermain.do");
			else if (myC.startsWith("Verasun")) emsg = emsg + (" " + hosturl + "tcmIS/verasun/batchreportviewermain.do");
			else if (myC.startsWith("Hrgivon")) emsg = emsg + (" " + hosturl + "tcmIS/hrgivon/batchreportviewermain.do");
			else if (myC.startsWith("Haargaz")) emsg = emsg + (" " + hosturl + "tcmIS/haargaz/batchreportviewermain.do");
			else if (myC.startsWith("Cyclone")) emsg = emsg + (" " + hosturl + "tcmIS/cyclone/batchreportviewermain.do");
			else if (myC.startsWith("Volvo")) emsg = emsg + (" " + hosturl + "tcmIS/volvo/batchreportviewermain.do");
			else if (myC.startsWith("Nalco")) emsg = emsg + (" " + hosturl + "tcmIS/nalco/batchreportviewermain.do");
			else if (myC.startsWith("Pepsi")) emsg = emsg + (" " + hosturl + "tcmIS/pepsi/batchreportviewermain.do");
			else if (myC.startsWith("HAL")) emsg = emsg + (" " + hosturl + "tcmIS/hal/batchreportviewermain.do");
			else if (myC.startsWith("ABM")) emsg = emsg + (" " + hosturl + "tcmIS/abm/batchreportviewermain.do");
			else if (myC.startsWith("MTL")) emsg = emsg + (" " + hosturl + "tcmIS/mtl/batchreportviewermain.do");
			else if (myC.startsWith("Timken")) emsg = emsg + (" " + hosturl + "tcmIS/timken/batchreportviewermain.do");
			else if (myC.startsWith("NRG")) emsg = emsg + (" " + hosturl + "tcmIS/nrg/batchreportviewermain.do");
			else if (myC.startsWith("MAEET")) emsg = emsg + (" " + hosturl + "tcmIS/maeet/batchreportviewermain.do");
			else if (myC.startsWith("SGD")) emsg = emsg + (" " + hosturl + "tcmIS/sgd/batchreportviewermain.do");
			else if (myC.startsWith("Caterpillar")) emsg = emsg + (" " + hosturl + "tcmIS/cat/batchreportviewermain.do");
			else if (myC.startsWith("OMA")) emsg = emsg + (" " + hosturl + "tcmIS/oma/batchreportviewermain.do");
			else if (myC.startsWith("Siemens")) emsg = emsg + (" " + hosturl + "tcmIS/siemens/batchreportviewermain.do");
			else if (myC.startsWith("Getrag")) emsg = emsg + (" " + hosturl + "tcmIS/getrag/batchreportviewermain.do");
			else if (myC.startsWith("Alcoa")) emsg = emsg + (" " + hosturl + "tcmIS/alcoa/batchreportviewermain.do");
			else if (myC.startsWith("ZWL")) emsg = emsg + (" " + hosturl + "tcmIS/zwl/batchreportviewermain.do");
			else if (myC.startsWith("HansSasserath"))
				emsg = emsg + (" " + hosturl + "tcmIS/hans_sasserath/batchreportviewermain.do");
			else if (myC.startsWith("DD")) emsg = emsg + (" " + hosturl + "tcmIS/dd/batchreportviewermain.do");
			else if (myC.startsWith("GKN")) emsg = emsg + (" " + hosturl + "tcmIS/gkn/batchreportviewermain.do");
			else if (myC.startsWith("Aeropia")) emsg = emsg + (" " + hosturl + "tcmIS/aeropia/batchreportviewermain.do");
			else if (myC.startsWith("Goodrich")) emsg = emsg + (" " + hosturl + "tcmIS/goodrich/batchreportviewermain.do");
			else if (myC.startsWith("OMMC")) emsg = emsg + (" " + hosturl + "tcmIS/ommc/batchreportviewermain.do");
			else if (myC.startsWith("FBM")) emsg = emsg + (" " + hosturl + "tcmIS/fbm/batchreportviewermain.do");
			else if (myC.startsWith("Aerocz")) emsg = emsg + (" " + hosturl + "tcmIS/aerocz/batchreportviewermain.do");
			else if (myC.startsWith("GE")) emsg = emsg + (" " + hosturl + "tcmIS/ge/batchreportviewermain.do");
			else if (myC.startsWith("BAI")) emsg = emsg + (" " + hosturl + "tcmIS/bai/batchreportviewermain.do");
			else if (myC.startsWith("Optimetal")) emsg = emsg + (" " + hosturl + "tcmIS/optimetal/batchreportviewermain.do");
			else if (myC.startsWith("Orlite")) emsg = emsg + (" " + hosturl + "tcmIS/orlite/batchreportviewermain.do");
			else if (myC.startsWith("Tambour")) emsg = emsg + (" " + hosturl + "tcmIS/tambour/batchreportviewermain.do");
			else if (myC.startsWith("Team")) emsg = emsg + (" " + hosturl + "tcmIS/team/batchreportviewermain.do");
			else if (myC.startsWith("Sales")) emsg = emsg + (" " + hosturl + "tcmIS/sales/batchreportviewermain.do");
			else if (myC.startsWith("Hexcel")) emsg = emsg + (" " + hosturl + "tcmIS/hexcel/batchreportviewermain.do");
			else if (myC.startsWith("ZF")) emsg = emsg + (" " + hosturl + "tcmIS/zf/batchreportviewermain.do");
			else if (myC.startsWith("Avcorp")) emsg = emsg + (" " + hosturl + "tcmIS/avcorp/batchreportviewermain.do");
			else if (myC.startsWith("BA")) emsg = emsg + (" " + hosturl + "tcmIS/ba/batchreportviewermain.do");
			else emsg = emsg + (" " + hosturl + "tcmIS/" + myC.toLowerCase() + "/batchreportviewermain.do");


			Integer user = new Integer(this.userId);
			emsg += "\nBatch report(s) that is older then two weeks will be deleted.";
			HelpObjs.sendMail(db, esub, emsg, user.intValue(), false);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
	}

	public void sendErrorEmail(String esub, String emsg) throws Exception {
		DBResultSet dbrs = null;
		try {
			String logonId = null;
			String query = "select logon_id from personnel where personnel_id = " + userId;
			ResultSet rs = null;
			dbrs = db.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				logonId = (BothHelpObjs.makeBlankFromNull(rs.getString("logon_id")));
			}

			Integer user = new Integer(this.userId);
			HelpObjs.sendMail(db, esub, emsg, user.intValue(), false);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
	}
}

