package radian.web.servlets.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ael.helpers.AeroEcoServerResourceBundle;
import radian.tcmis.server7.client.bae.helpers.BAEServerResourceBundle;
import radian.tcmis.server7.client.boeing.helpers.BoeingServerResourceBundle;
import radian.tcmis.server7.client.cal.helpers.CALServerResourceBundle;
import radian.tcmis.server7.client.drs.dbObjs.DRSTcmISDBModel;
import radian.tcmis.server7.client.drs.helpers.DRSServerResourceBundle;
import radian.tcmis.server7.client.fec.helpers.FECServerResourceBundle;
import radian.tcmis.server7.client.gm.helpers.GMServerResourceBundle;
import radian.tcmis.server7.client.lmco.dbObjs.LMCOTcmISDBModel;
import radian.tcmis.server7.client.lmco.helpers.LMCOServerResourceBundle;
import radian.tcmis.server7.client.miller.helpers.MillerServerResourceBundle;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.ray.helpers.RayServerResourceBundle;
import radian.tcmis.server7.client.sd.helpers.SDServerResourceBundle;
import radian.tcmis.server7.client.seagate.helpers.SeagateServerResourceBundle;
import radian.tcmis.server7.client.swa.dbObjs.SWATcmISDBModel;
import radian.tcmis.server7.client.swa.helpers.SWAServerResourceBundle;
import radian.tcmis.server7.client.utc.helpers.UTCServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.servlets.ReportObjNew;
import radian.tcmis.server7.client.dana.helpers.DanaServerResourceBundle;
import radian.tcmis.server7.client.ablaero.helpers.ABLaeroServerResourceBundle;
import radian.tcmis.server7.client.doe.helpers.DOEServerResourceBundle;
import radian.tcmis.server7.client.iai.helpers.IAIServerResourceBundle;
import radian.tcmis.server7.client.l3.helpers.L3ServerResourceBundle;
import radian.tcmis.server7.client.am.helpers.AMServerResourceBundle;
import radian.tcmis.server7.client.gema.helpers.GemaServerResourceBundle;
import radian.tcmis.server7.client.pge.helpers.PGEServerResourceBundle;
import radian.tcmis.server7.client.qos.helpers.QOSServerResourceBundle;
import radian.tcmis.server7.client.dcx.helpers.DCXServerResourceBundle;
import radian.tcmis.server7.client.ford.helpers.FordServerResourceBundle;
import radian.tcmis.server7.client.algat.helpers.AlgatServerResourceBundle;
import radian.tcmis.server7.client.baz.helpers.BAZServerResourceBundle;
import radian.tcmis.server7.client.cmm.helpers.CMMServerResourceBundle;
import radian.tcmis.server7.client.kanfit.helpers.KanfitServerResourceBundle;
import radian.tcmis.server7.client.fedco.helpers.FedcoServerResourceBundle;
import radian.tcmis.server7.client.gd.helpers.GDServerResourceBundle;
import radian.tcmis.server7.client.imco.helpers.IMCOServerResourceBundle;
import radian.tcmis.server7.client.kemfast.helpers.KemfastServerResourceBundle;
import radian.tcmis.server7.client.bl.helpers.BLServerResourceBundle;
import radian.tcmis.server7.client.verasun.helpers.VerasunServerResourceBundle;
import radian.tcmis.server7.client.hrgivon.helpers.HrgivonServerResourceBundle;
import radian.tcmis.server7.client.haargaz.helpers.HaargazServerResourceBundle;
import radian.tcmis.server7.client.cyclone.helpers.CycloneServerResourceBundle;
import radian.tcmis.server7.client.volvo.helpers.VolvoServerResourceBundle;
import radian.tcmis.server7.client.nalco.helpers.NalcoServerResourceBundle;
import radian.tcmis.server7.client.pepsi.helpers.PepsiServerResourceBundle;
import radian.tcmis.server7.client.hal.helpers.HALServerResourceBundle;
import radian.tcmis.server7.client.abm.helpers.ABMServerResourceBundle;
import radian.tcmis.server7.client.mtl.helpers.MTLServerResourceBundle;
import radian.tcmis.server7.client.timken.helpers.TimkenServerResourceBundle;
import radian.tcmis.server7.client.nrg.helpers.NRGServerResourceBundle;
import radian.tcmis.server7.client.sgd.helpers.SGDServerResourceBundle;
import radian.tcmis.server7.client.cat.helpers.CaterpillarServerResourceBundle;
import radian.tcmis.server7.client.oma.helpers.OMAServerResourceBundle;
import radian.tcmis.server7.client.siemens.helpers.SiemensServerResourceBundle;
import radian.tcmis.server7.client.getrag.helpers.GetragServerResourceBundle;
import radian.tcmis.server7.client.alcoa.helpers.AlcoaServerResourceBundle;
import radian.tcmis.server7.client.zwl.helpers.ZWLServerResourceBundle;
import radian.tcmis.server7.client.hans_sasserath.helpers.HansSasserathServerResourceBundle;
import radian.tcmis.server7.client.dd.helpers.DDServerResourceBundle;
import radian.tcmis.server7.client.gkn.helpers.GKNServerResourceBundle;
import radian.tcmis.server7.client.aeropia.helpers.AeropiaServerResourceBundle;
import radian.tcmis.server7.client.goodrich.helpers.GoodrichServerResourceBundle;
import radian.tcmis.server7.client.ommc.helpers.OMMCServerResourceBundle;
import radian.tcmis.server7.client.fbm.helpers.FBMServerResourceBundle;
import radian.tcmis.server7.client.aerocz.helpers.AeroczServerResourceBundle;
import radian.tcmis.server7.client.ge.helpers.GEServerResourceBundle;
import radian.tcmis.server7.client.sales.helpers.SalesServerResourceBundle;
import radian.tcmis.server7.client.hexcel.helpers.HexcelServerResourceBundle;
import radian.tcmis.server7.client.zf.helpers.ZFServerResourceBundle;
import radian.tcmis.server7.client.avcorp.helpers.AvcorpServerResourceBundle;
import radian.tcmis.server7.client.ba.helpers.BAServerResourceBundle;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 07-02-03 - Code Clean up
 * 09-24-03 - Not setting the static variable ReportTable1 to null and closing the db only if it is not null
 * 11-18-03 - Changed language on final page
 */

public class WebUsageFormated extends HttpServlet {
  public WebUsageFormated() {
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = new PrintWriter(response.getOutputStream());
    String type = "";
    response.setContentType("text/html");

    try {
      type = request.getParameter("type").toString();
    } catch (Exception e1) {
      type = "";
    }
    try {
      String Session_ID = BothHelpObjs.makeBlankFromNull( (String) request.getParameter("sessionID"));
      String Session = request.getParameter("session");
      String client = BothHelpObjs.makeBlankFromNull( (String) request.getParameter("client"));
      String userID = BothHelpObjs.makeBlankFromNull( (String) request.getParameter("userID"));

      if (Session == null) {
        buildHTML(out, Session_ID, client, userID);
      } else if (Session.equalsIgnoreCase("1")) {
        buildURL(out, Session_ID, client, userID);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void buildURL(PrintWriter out, String Session_ID, String client, String userID) {
    Hashtable Result = new Hashtable();
    TcmISDBModel db = null;
    if (client.equalsIgnoreCase("Ray")) {
      db = new RayTcmISDBModel("Ray");
      ReportObjNew obj = new ReportObjNew(new RayServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("SWA")) {
      db = new SWATcmISDBModel("SWA");
      ReportObjNew obj = new ReportObjNew(new SWAServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else
    if (client.equalsIgnoreCase("LMCO")) {
      db = new LMCOTcmISDBModel("LMCO");
      ReportObjNew obj = new ReportObjNew(new LMCOServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else
    if (client.equalsIgnoreCase("DRS")) {
      db = new DRSTcmISDBModel("DRS");
      ReportObjNew obj = new ReportObjNew(new DRSServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else
    if (client.equalsIgnoreCase("Seagate")) {
      db = new RayTcmISDBModel("Seagate");
      ReportObjNew obj = new ReportObjNew(new SeagateServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else
    if (client.equalsIgnoreCase("BAE")) {
      db = new RayTcmISDBModel("BAE");
      ReportObjNew obj = new ReportObjNew(new BAEServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else
    if (client.equalsIgnoreCase("UTC")) {
      db = new RayTcmISDBModel("UTC");
      ReportObjNew obj = new ReportObjNew(new UTCServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    }
    if (client.equalsIgnoreCase("SD")) {
      db = new RayTcmISDBModel("SD");
      ReportObjNew obj = new ReportObjNew(new SDServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        String emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
        buildERROR(out, emsg);
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Miller")) {
      db = new RayTcmISDBModel("Miller", "2");
      ReportObjNew obj = new ReportObjNew(new MillerServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("CAL")) {
      db = new RayTcmISDBModel("CAL", "2");
      ReportObjNew obj = new ReportObjNew(new CALServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("GM")) {
      db = new RayTcmISDBModel("GM", "2");
      ReportObjNew obj = new ReportObjNew(new GMServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("FEC")) {
      db = new RayTcmISDBModel("FEC", "2");
      ReportObjNew obj = new ReportObjNew(new FECServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Boeing")) {
      db = new RayTcmISDBModel("Boeing", "2");
      ReportObjNew obj = new ReportObjNew(new BoeingServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("AeroEco")) {
      db = new RayTcmISDBModel("AeroEco", "2");
      ReportObjNew obj = new ReportObjNew(new AeroEcoServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Dana")) {
      db = new RayTcmISDBModel("Dana", "2");
      ReportObjNew obj = new ReportObjNew(new DanaServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("ABLaero")) {
      db = new RayTcmISDBModel("ABLaero", "2");
      ReportObjNew obj = new ReportObjNew(new ABLaeroServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("DOE")) {
      db = new RayTcmISDBModel("DOE", "2");
      ReportObjNew obj = new ReportObjNew(new DOEServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("IAI")) {
      db = new RayTcmISDBModel("IAI", "2");
      ReportObjNew obj = new ReportObjNew(new IAIServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("L3")) {
      db = new RayTcmISDBModel("L3", "2");
      ReportObjNew obj = new ReportObjNew(new L3ServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("AM")) {
      db = new RayTcmISDBModel("AM", "2");
      ReportObjNew obj = new ReportObjNew(new AMServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("GEMA")) {
      db = new RayTcmISDBModel("GEMA", "2");
      ReportObjNew obj = new ReportObjNew(new GemaServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("PGE")) {
      db = new RayTcmISDBModel("PGE", "2");
      ReportObjNew obj = new ReportObjNew(new PGEServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("QOS")) {
      db = new RayTcmISDBModel("QOS", "2");
      ReportObjNew obj = new ReportObjNew(new QOSServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("DCX")) {
      db = new RayTcmISDBModel("DCX", "2");
      ReportObjNew obj = new ReportObjNew(new DCXServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Ford")) {
      db = new RayTcmISDBModel("Ford", "2");
      ReportObjNew obj = new ReportObjNew(new FordServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Algat")) {
      db = new RayTcmISDBModel("Algat", "2");
      ReportObjNew obj = new ReportObjNew(new AlgatServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("BAZ")) {
      db = new RayTcmISDBModel("BAZ", "2");
      ReportObjNew obj = new ReportObjNew(new BAZServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("CMM")) {
      db = new RayTcmISDBModel("CMM", "2");
      ReportObjNew obj = new ReportObjNew(new CMMServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Kanfit")) {
      db = new RayTcmISDBModel("Kanfit", "2");
      ReportObjNew obj = new ReportObjNew(new KanfitServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Fedco")) {
      db = new RayTcmISDBModel("Fedco", "2");
      ReportObjNew obj = new ReportObjNew(new FedcoServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("GD")) {
      db = new RayTcmISDBModel("GD", "2");
      ReportObjNew obj = new ReportObjNew(new GDServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("IMCO")) {
      db = new RayTcmISDBModel("IMCO", "2");
      ReportObjNew obj = new ReportObjNew(new IMCOServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Kemfast")) {
      db = new RayTcmISDBModel("Kemfast", "2");
      ReportObjNew obj = new ReportObjNew(new KemfastServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("BL")) {
      db = new RayTcmISDBModel("BL", "2");
      ReportObjNew obj = new ReportObjNew(new BLServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Verasun")) {
      db = new RayTcmISDBModel("Verasun", "2");
      ReportObjNew obj = new ReportObjNew(new VerasunServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Hrgivon")) {
      db = new RayTcmISDBModel("Hrgivon", "2");
      ReportObjNew obj = new ReportObjNew(new HrgivonServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Haargaz")) {
      db = new RayTcmISDBModel("Haargaz", "2");
      ReportObjNew obj = new ReportObjNew(new HaargazServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Cyclone")) {
      db = new RayTcmISDBModel("Cyclone", "2");
      ReportObjNew obj = new ReportObjNew(new CycloneServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Volvo")) {
      db = new RayTcmISDBModel("Volvo", "2");
      ReportObjNew obj = new ReportObjNew(new VolvoServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Nalco")) {
      db = new RayTcmISDBModel("Nalco", "2");
      ReportObjNew obj = new ReportObjNew(new NalcoServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Pepsi")) {
      db = new RayTcmISDBModel("Pepsi", "2");
      ReportObjNew obj = new ReportObjNew(new PepsiServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("HAL")) {
      db = new RayTcmISDBModel("HAL", "2");
      ReportObjNew obj = new ReportObjNew(new HALServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("ABM")) {
      db = new RayTcmISDBModel("ABM", "2");
      ReportObjNew obj = new ReportObjNew(new ABMServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("MTL")) {
      db = new RayTcmISDBModel("MTL", "2");
      ReportObjNew obj = new ReportObjNew(new MTLServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Timken")) {
      db = new RayTcmISDBModel("Timken", "2");
      ReportObjNew obj = new ReportObjNew(new TimkenServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("NRG")) {
      db = new RayTcmISDBModel("NRG", "2");
      ReportObjNew obj = new ReportObjNew(new NRGServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("SGD")) {
      db = new RayTcmISDBModel("SGD", "2");
      ReportObjNew obj = new ReportObjNew(new SGDServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Caterpillar")) {
      db = new RayTcmISDBModel("Caterpillar", "2");
      ReportObjNew obj = new ReportObjNew(new CaterpillarServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("OMA")) {
      db = new RayTcmISDBModel("OMA", "2");
      ReportObjNew obj = new ReportObjNew(new OMAServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Siemens")) {
      db = new RayTcmISDBModel("Siemens", "2");
      ReportObjNew obj = new ReportObjNew(new SiemensServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Getrag")) {
      db = new RayTcmISDBModel("Getrag", "2");
      ReportObjNew obj = new ReportObjNew(new GetragServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Alcoa")) {
      db = new RayTcmISDBModel("Alcoa", "2");
      ReportObjNew obj = new ReportObjNew(new AlcoaServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("ZWL")) {
      db = new RayTcmISDBModel("ZWL", "2");
      ReportObjNew obj = new ReportObjNew(new ZWLServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("HansSasserath")) {
      db = new RayTcmISDBModel("HansSasserath", "2");
      ReportObjNew obj = new ReportObjNew(new HansSasserathServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("DD")) {
      db = new RayTcmISDBModel("DD", "2");
      ReportObjNew obj = new ReportObjNew(new DDServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("GKN")) {
      db = new RayTcmISDBModel("GKN", "2");
      ReportObjNew obj = new ReportObjNew(new GKNServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Aeropia")) {
      db = new RayTcmISDBModel("Aeropia", "2");
      ReportObjNew obj = new ReportObjNew(new AeropiaServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Goodrich")) {
      db = new RayTcmISDBModel("Goodrich", "2");
      ReportObjNew obj = new ReportObjNew(new GoodrichServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("OMMC")) {
      db = new RayTcmISDBModel("OMMC", "2");
      ReportObjNew obj = new ReportObjNew(new OMMCServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("FBM")) {
      db = new RayTcmISDBModel("FBM", "2");
      ReportObjNew obj = new ReportObjNew(new FBMServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Aerocz")) {
      db = new RayTcmISDBModel("Aerocz", "2");
      ReportObjNew obj = new ReportObjNew(new AeroczServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("GE")) {
      db = new RayTcmISDBModel("GE", "2");
      ReportObjNew obj = new ReportObjNew(new GEServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Sales")) {
      db = new RayTcmISDBModel("Sales", "2");
      ReportObjNew obj = new ReportObjNew(new SalesServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Hexcel")) {
      db = new RayTcmISDBModel("Hexcel", "2");
      ReportObjNew obj = new ReportObjNew(new HexcelServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("ZF")) {
      db = new RayTcmISDBModel("ZF", "2");
      ReportObjNew obj = new ReportObjNew(new ZFServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("Avcorp")) {
      db = new RayTcmISDBModel("Avcorp", "2");
      ReportObjNew obj = new ReportObjNew(new AvcorpServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    } else if (client.equalsIgnoreCase("BA")) {
      db = new RayTcmISDBModel("BA", "2");
      ReportObjNew obj = new ReportObjNew(new BAServerResourceBundle(), db);
      try {
        Result = obj.createReportNew(Session_ID, userID);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(out, "");
      } finally {
        if (db != null) {
          db.close();
        }
      }
      obj = null;
    }

    db = null;
    ShowResult(out, Result);
  }

  public void buildHTML(PrintWriter out, String sessionID, String client, String userID) {
    //StringBuffer MsgSB = new StringBuffer();

    out.println("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
    out.println("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
    out.println("<!-- \n");
    out.println("    var Timer = null\n");
    out.println("    var winHandlefu = null\n");
    out.println("    function winClose(){    // close all open pop-up windows\n");
    out.println("      if ((winHandlefu != null) && !winHandlefu.closed) winHandlefu.close()\n");
    out.println("      }\n");
    out.println("function openwin2 (str)\n");
    out.println("        {\n");
    out.println("    winHandlefu = window.open(\"/Timer.html\", \"Timer4\",\n");
    out.println("        \"toolbar=no,location=no,directories=no,status=no\" +\n");
    out.println("        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
    out.println("        \",width=200,height=150\");\n");
    out.println("  return winHandlefu; \n");
    out.println("        }\n");
    out.println("function DoLoad()\n");
    out.println("{ \n");
    out.println("openwin2(); \n");
    out.println("window.location.replace(\"/tcmIS/servlet/radian.web.servlets.reports.WebUsageFormated?session=1&client=" + client + "&sessionID=" + sessionID + "&userID=" + userID + "\");\n");
    out.println("} \n");
    out.println("//-->     \n");
    out.println("</SCRIPT>\n");
    out.println("</HEAD>  \n");
    out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
    out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
    out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
    out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
    out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
    out.println("</CENTER></BODY></HTML>    \n");
    //out.println(MsgSB);
    out.close();
  }

  public void buildERROR(PrintWriter out, String emsg) {
    //StringBuffer MsgSB = new StringBuffer();
    try {
      if (emsg.length() < 1) {
        emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
      }
    } catch (Exception eeee) {
      emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
    }
    out.println("<HTML><HEAD>\n");
    out.println("</HEAD>  \n");
    out.println("<BODY BGCOLOR=#FFFFFF TEXT=#000000>  \n");
    out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
    out.println("<font face=Arial size=\"4\" color=\"#000080\"><b>" + emsg + "</b></font><P></P><BR>\n");
    out.println("</CENTER></body></HTML>    \n");
    //out.println(MsgSB);
    out.close();
  }

  public void buildTmpUnava(PrintWriter out) {
    //StringBuffer MsgSB = new StringBuffer();
    out.println("<HTML><HEAD>\n");
    out.println("</HEAD>  \n");
    out.println("<BODY BGCOLOR=#FFFFFF TEXT=#000000>\n");
    out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
    out.println("<font face=Arial size=\"3\" color=\"#000080\"><b>\n");
    out.println("<BR><BR> Interactive Formatted reports in PDF format are temporarily converted to Batch report.<BR><BR>\n");
    out.println(" Your report request was posted to the server and you will receive an email <BR>when the report is ready.<BR><BR>\n");
    out.println(" We are sorry for this inconvenience and we are trying to restore this ASAP.<BR>\n");
    out.println(" Questions please call tcmIS Customer Service Representative(CSR).<BR>Thanks.\n");
    out.println("</b></font><P></P><BR>\n");
    out.println("</CENTER></body></HTML>    \n");
    //out.println(MsgSB);
    out.close();
  }

  public void ShowResult(PrintWriter out, Hashtable Result) {
    //StringBuffer MsgSB = new StringBuffer();
    try {
      Boolean suc = (Boolean) Result.get("SUCCEED");
      String emsg = (String) Result.get("MSG");
      if (suc.booleanValue()) {
        String url = (String) Result.get("URL");
        try {
          if (url.length() < 1) {
            emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
            buildERROR(out, "");
          }
        } catch (Exception eee) {
          emsg = "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).";
          buildERROR(out, "");
        }
        out.println("<HTML><HEAD>\n");
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">");
        out.println("</HEAD>  \n");
        out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
        out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\">");
        out.println("<BR>    \n");
        out.println("<B>Report Finished </B><BR><BR>\n");
        out.println("<B>Retrieving Data.</B>\n");
        out.println("</FONT>");
        out.println("</CENTER></BODY></HTML>    \n");
        //out.println(MsgSB);
        out.close();
      } else {
        buildERROR(out, emsg);
      }
    } catch (Exception e) {
      e.printStackTrace();
      buildERROR(out, "Generating report failed.\n Please try again.\n If problem recurs, contact tcmIS Customer Service Representative(CSR).");
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

}
