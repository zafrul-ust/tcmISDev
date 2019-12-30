package radian.tcmis.server7.share.servlets;

//ACJEngine
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.both1.helpers.UsageReportDataServ;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.InventoryView;
import radian.tcmis.server7.share.dbObjs.NewChemTrackTable;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.ReportData;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TrackView;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.erw.pdfReports.CatAddTrackData;
import radian.web.erw.pdfReports.InventoryData;
import radian.web.erw.pdfReports.OrderTrackData;
import radian.web.erw.pdfReports.catalogSearchData;
import radian.web.erw.xlsreports.CatalogXlsReport;
import radian.tcmis.server7.share.dbObjs.WasteProfile;
import radian.web.erw.pdfReports.WasteCatalogScreen;
import radian.web.erw.pdfReports.ItemCatalogData;
import radian.tcmis.server7.share.dbObjs.ItemCatalog;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 02-20-04 Making the Ctalog Printscreen work with the new procedure
 *
 * 3-25-04 passing user_id to inventoryView so I can determine if user is running JRE 1.3 or 1.4 - trong
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class PrintScreen extends TcmISServletReportObj {
  private static final String[] groupStringLabel = new String[] {
      "Facility", "Work Area", "Cas #/SARA Group", "Delivery Point", "Month"};
  private static final int GROUP_BY_FAC = 0;
  private static final int GROUP_BY_WORK_AREA = 1;
  private static final int GROUP_BY_CAS_NUM = 2;
  private static final int GROUP_BY_DEL_POINT = 3;
  private static final int GROUP_BY_MONTH = 4;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  //Client Version
  private String facName = "";
  private String workAreaDesc = "";
  private String chemType = "";
  private String begMonth = "";
  private String begYear = "";
  private String endMonth = "";
  private String endYear = "";
  private String searchType = "";
  private String listId = "";
  private String keyword = "";
  private String cas_num = "";
  private String chemListDesc = "";
  private int[] groupBy;

  //Client Version
  private ACJEngine erw = null;
  private JDBCHandler ds = null;
  private AppDataHandler adh = null;
  private TemplateManager tm = null;
  private ACJOutputProcessor ec = null;
  private String function = null;
  private Hashtable mySendObj = null;
  private Hashtable inData = null;
  private String userId = null;
  private String fileType = null;
  private String method = null;

  private String dbDriver;
  private String phoenixUrl;
  private String rayPhoenixUserName;
  private String rayPhoenixPasswd;
  private String DRSPhoenixUserName;
  private String DRSPhoenixPasswd;
  private String SWAPhoenixUserName;
  private String SWAPhoenixPasswd;
  private String LMCOPhoenixUserName;
  private String LMCOPhoenixPasswd;
  private String SeagatePhoenixUserName;
  private String SeagatePhoenixPasswd;
  private String UTCPhoenixUserName;
  private String UTCPhoenixPasswd;
  private String BAEPhoenixUserName;
  private String BAEPhoenixPasswd;
  private String SDPhoenixUserName;
  private String SDPhoenixPasswd;
  private String MillerPhoenixUserName;
  private String MillerPhoenixPasswd;
  private String GMPhoenixUserName;
  private String GMPhoenixPasswd;
  private String CALPhoenixUserName;
  private String CALPhoenixPasswd;
  private String FECPhoenixUserName;
  private String FECPhoenixPasswd;
  private String templDirectory;
  private String BoeingPhoenixUserName;
  private String BoeingPhoenixPasswd;
  private String AeroEcoPhoenixUserName;
  private String AeroEcoPhoenixPasswd;
  private String DanaPhoenixUserName;
  private String DanaPhoenixPasswd;
  private String ABLaeroPhoenixUserName;
  private String ABLaeroPhoenixPasswd;
  private String DOEPhoenixUserName;
  private String DOEPhoenixPasswd;
  private String IAIPhoenixUserName;
  private String IAIPhoenixPasswd;
  private String L3PhoenixUserName;
  private String L3PhoenixPasswd;
  private String AMPhoenixUserName;
  private String AMPhoenixPasswd;
  private String GemaPhoenixUserName;
  private String GemaPhoenixPasswd;
  private String PGEPhoenixUserName;
  private String PGEPhoenixPasswd;
  private String QOSPhoenixUserName;
  private String QOSPhoenixPasswd;
  private String DCXPhoenixUserName;
  private String DCXPhoenixPasswd;
  private String FordPhoenixUserName;
  private String FordPhoenixPasswd;
  private String url = null;
  private String AlgatPhoenixUserName;
  private String AlgatPhoenixPasswd;
  private String BAZPhoenixUserName;
  private String BAZPhoenixPasswd;
  private String CMMPhoenixUserName;
  private String CMMPhoenixPasswd;
  private String KanfitPhoenixUserName;
  private String KanfitPhoenixPasswd;
  private String FedcoPhoenixUserName;
  private String FedcoPhoenixPasswd;
  private String GDPhoenixUserName;
  private String GDPhoenixPasswd;
  private String IMCOPhoenixUserName;
  private String IMCOPhoenixPasswd;
  private String KemfastPhoenixUserName;
  private String KemfastPhoenixPasswd;
  private String BLPhoenixUserName;
  private String BLPhoenixPasswd;
  private String VerasunPhoenixUserName;
  private String VerasunPhoenixPasswd;
  private String HrgivonPhoenixUserName;
  private String HrgivonPhoenixPasswd;
  private String HaargazPhoenixUserName;
  private String HaargazPhoenixPasswd;
  private String CyclonePhoenixUserName;
  private String CyclonePhoenixPasswd;
  private String VolvoPhoenixUserName;
  private String VolvoPhoenixPasswd;
  private String NalcoPhoenixUserName;
  private String NalcoPhoenixPasswd;
  private String PepsiPhoenixUserName;
  private String PepsiPhoenixPasswd;
  private String HALPhoenixUserName;
  private String HALPhoenixPasswd;
  private String ABMPhoenixUserName;
  private String ABMPhoenixPasswd;
  private String MTLPhoenixUserName;
  private String MTLPhoenixPasswd;
  private String TimkenPhoenixUserName;
  private String TimkenPhoenixPasswd;
  private String NRGPhoenixUserName;
  private String NRGPhoenixPasswd;
  private String MAEETPhoenixUserName;
  private String MAEETPhoenixPasswd;
  private String SGDPhoenixUserName;
  private String SGDPhoenixPasswd;
  private String CaterpillarPhoenixUserName;
  private String CaterpillarPhoenixPasswd;
  private String OMAPhoenixUserName;
  private String OMAPhoenixPasswd;
  private String SiemensPhoenixUserName;
  private String SiemensPhoenixPasswd;
  private String GetragPhoenixUserName;
  private String GetragPhoenixPasswd;
  private String AlcoaPhoenixUserName;
  private String AlcoaPhoenixPasswd;
  private String ZWLPhoenixUserName;
  private String ZWLPhoenixPasswd;
  private String HansSasserathPhoenixUserName;
  private String HansSasserathPhoenixPasswd;
  private String DDPhoenixUserName;
  private String DDPhoenixPasswd;
  private String GKNPhoenixUserName;
  private String GKNPhoenixPasswd;
  private String AeropiaPhoenixUserName;
  private String AeropiaPhoenixPasswd;
  private String GoodrichPhoenixUserName;
  private String GoodrichPhoenixPasswd;
  private String OMMCPhoenixUserName;
  private String OMMCPhoenixPasswd;
  private String FBMPhoenixUserName;
  private String FBMPhoenixPasswd;
  private String AeroczPhoenixUserName;
  private String AeroczPhoenixPasswd;
  private String GEPhoenixUserName;
  private String GEPhoenixPasswd;
  private String SalesPhoenixUserName;
  private String SalesPhoenixPasswd;
  private String HexcelPhoenixUserName;
  private String HexcelPhoenixPasswd;
  private String ZfPhoenixUserName;
  private String ZfPhoenixPasswd;
  private String AvcorpPhoenixUserName;
  private String AvcorpPhoenixPasswd;
  private String BaPhoenixUserName;
  private String BaPhoenixPasswd;

  public PrintScreen(ServerResourceBundle b, TcmISDBModel d) {
    super();
    bundle = b;
    db = d;
    try {
      dbDriver = radian.web.databaseResourceLoader.getdbDriver();
      phoenixUrl = radian.web.databaseResourceLoader.getproddburl();
      rayPhoenixUserName = radian.web.databaseResourceLoader.getrayuserid();
      rayPhoenixPasswd = radian.web.databaseResourceLoader.getraypassword();
      DRSPhoenixUserName = radian.web.databaseResourceLoader.getdrsuserid();
      DRSPhoenixPasswd = radian.web.databaseResourceLoader.getdrspassword();
      SWAPhoenixUserName = radian.web.databaseResourceLoader.getswauserid();
      SWAPhoenixPasswd = radian.web.databaseResourceLoader.getswapassword();
      LMCOPhoenixUserName = radian.web.databaseResourceLoader.getlmcouserid();
      LMCOPhoenixPasswd = radian.web.databaseResourceLoader.getlmcopassword();
      SeagatePhoenixUserName = radian.web.databaseResourceLoader.getseagateuserid();
      SeagatePhoenixPasswd = radian.web.databaseResourceLoader.getseagatepassword();
      UTCPhoenixUserName = radian.web.databaseResourceLoader.getutcuserid();
      UTCPhoenixPasswd = radian.web.databaseResourceLoader.getutcpassword();
      BAEPhoenixUserName = radian.web.databaseResourceLoader.getbaeuserid();
      BAEPhoenixPasswd = radian.web.databaseResourceLoader.getbaepassword();
      SDPhoenixUserName = radian.web.databaseResourceLoader.getsduserid();
      SDPhoenixPasswd = radian.web.databaseResourceLoader.getsdpassword();
      MillerPhoenixUserName = radian.web.databaseResourceLoader.getmilleruserid();
      MillerPhoenixPasswd = radian.web.databaseResourceLoader.getmillerpassword();
      GMPhoenixUserName = radian.web.databaseResourceLoader.getgmuserid();
      GMPhoenixPasswd = radian.web.databaseResourceLoader.getgmpassword();
      CALPhoenixUserName = radian.web.databaseResourceLoader.getcaluserid();
      CALPhoenixPasswd = radian.web.databaseResourceLoader.getcalpassword();
      FECPhoenixUserName = radian.web.databaseResourceLoader.getfecuserid();
      FECPhoenixPasswd = radian.web.databaseResourceLoader.getfecpassword();
      templDirectory = radian.web.tcmisResourceLoader.getreportstempplatepath();
      BoeingPhoenixUserName = radian.web.databaseResourceLoader.getboeinguserid();
      BoeingPhoenixPasswd = radian.web.databaseResourceLoader.getboeingpassword();
      AeroEcoPhoenixUserName = radian.web.databaseResourceLoader.getaeroecouserid();
      AeroEcoPhoenixPasswd = radian.web.databaseResourceLoader.getaeroecopassword();
      DanaPhoenixUserName = radian.web.databaseResourceLoader.getdanauserid();
      DanaPhoenixPasswd = radian.web.databaseResourceLoader.getdanapassword();
      ABLaeroPhoenixUserName = radian.web.databaseResourceLoader.getablaerouserid();
      ABLaeroPhoenixPasswd = radian.web.databaseResourceLoader.getablaeropassword();
      DOEPhoenixUserName = radian.web.databaseResourceLoader.getdoeuserid();
      DOEPhoenixPasswd = radian.web.databaseResourceLoader.getdoepassword();
      IAIPhoenixUserName = radian.web.databaseResourceLoader.getIAIUserId();
      IAIPhoenixPasswd = radian.web.databaseResourceLoader.getIAIPassword();
      L3PhoenixUserName = radian.web.databaseResourceLoader.getL3UserId();
      L3PhoenixPasswd = radian.web.databaseResourceLoader.getL3Password();
      AMPhoenixUserName = radian.web.databaseResourceLoader.getAMUserId();
      AMPhoenixPasswd = radian.web.databaseResourceLoader.getAMPassword();
      GemaPhoenixUserName = radian.web.databaseResourceLoader.getGemaUserId();
      GemaPhoenixPasswd = radian.web.databaseResourceLoader.getGemaPassword();
      PGEPhoenixUserName = radian.web.databaseResourceLoader.getPGEUserId();
      PGEPhoenixPasswd = radian.web.databaseResourceLoader.getPGEPassword();
      QOSPhoenixUserName = radian.web.databaseResourceLoader.getQOSUserId();
      QOSPhoenixPasswd = radian.web.databaseResourceLoader.getQOSPassword();
      DCXPhoenixUserName = radian.web.databaseResourceLoader.getDCXUserId();
      DCXPhoenixPasswd = radian.web.databaseResourceLoader.getDCXPassword();
      FordPhoenixUserName = radian.web.databaseResourceLoader.getFordUserId();
      FordPhoenixPasswd = radian.web.databaseResourceLoader.getFordPassword();
      AlgatPhoenixUserName = radian.web.databaseResourceLoader.getAlgatUserId();
      AlgatPhoenixPasswd = radian.web.databaseResourceLoader.getAlgatPassword();
      BAZPhoenixUserName = radian.web.databaseResourceLoader.getBAZUserId();
      BAZPhoenixPasswd = radian.web.databaseResourceLoader.getBAZPassword();
      CMMPhoenixUserName = radian.web.databaseResourceLoader.getCMMUserId();
      CMMPhoenixPasswd = radian.web.databaseResourceLoader.getCMMPassword();
      KanfitPhoenixUserName = radian.web.databaseResourceLoader.getKanfitUserId();
      KanfitPhoenixPasswd = radian.web.databaseResourceLoader.getKanfitPassword();
      FedcoPhoenixUserName = radian.web.databaseResourceLoader.getFedcoUserId();
      FedcoPhoenixPasswd = radian.web.databaseResourceLoader.getFedcoPassword();
      GDPhoenixUserName = radian.web.databaseResourceLoader.getGDUserId();
      GDPhoenixPasswd = radian.web.databaseResourceLoader.getGDPassword();
      IMCOPhoenixUserName = radian.web.databaseResourceLoader.getIMCOUserId();
      IMCOPhoenixPasswd = radian.web.databaseResourceLoader.getIMCOPassword();
      KemfastPhoenixUserName = radian.web.databaseResourceLoader.getKemfastUserId();
      KemfastPhoenixPasswd = radian.web.databaseResourceLoader.getKemfastPassword();
      BLPhoenixUserName = radian.web.databaseResourceLoader.getBLUserId();
      BLPhoenixPasswd = radian.web.databaseResourceLoader.getBLPassword();
      VerasunPhoenixUserName = radian.web.databaseResourceLoader.getVerasunUserId();
      VerasunPhoenixPasswd = radian.web.databaseResourceLoader.getVerasunPassword();
      HrgivonPhoenixUserName = radian.web.databaseResourceLoader.getHrgivonUserId();
      HrgivonPhoenixPasswd = radian.web.databaseResourceLoader.getHrgivonPassword();
      HaargazPhoenixUserName = radian.web.databaseResourceLoader.getHaargazUserId();
      HaargazPhoenixPasswd = radian.web.databaseResourceLoader.getHaargazPassword();
      CyclonePhoenixUserName = radian.web.databaseResourceLoader.getCycloneUserId();
      CyclonePhoenixPasswd = radian.web.databaseResourceLoader.getCyclonePassword();
      VolvoPhoenixUserName = radian.web.databaseResourceLoader.getVolvoUserId();
      VolvoPhoenixPasswd = radian.web.databaseResourceLoader.getVolvoPassword();
      NalcoPhoenixUserName = radian.web.databaseResourceLoader.getNalcoUserId();
      NalcoPhoenixPasswd = radian.web.databaseResourceLoader.getNalcoPassword();
      PepsiPhoenixUserName = radian.web.databaseResourceLoader.getPepsiUserId();
      PepsiPhoenixPasswd = radian.web.databaseResourceLoader.getPepsiPassword();
      HALPhoenixUserName = radian.web.databaseResourceLoader.getHALUserId();
      HALPhoenixPasswd = radian.web.databaseResourceLoader.getHALPassword();
      ABMPhoenixUserName = radian.web.databaseResourceLoader.getABMUserId();
      ABMPhoenixPasswd = radian.web.databaseResourceLoader.getABMPassword();
      MTLPhoenixUserName = radian.web.databaseResourceLoader.getMTLUserId();
      MTLPhoenixPasswd = radian.web.databaseResourceLoader.getMTLPassword();
      TimkenPhoenixUserName = radian.web.databaseResourceLoader.getTimkenUserId();
      TimkenPhoenixPasswd = radian.web.databaseResourceLoader.getTimkenPassword();
      NRGPhoenixUserName = radian.web.databaseResourceLoader.getNRGUserId();
      NRGPhoenixPasswd = radian.web.databaseResourceLoader.getNRGPassword();
      MAEETPhoenixUserName = radian.web.databaseResourceLoader.getMAEETUserId();
      MAEETPhoenixPasswd = radian.web.databaseResourceLoader.getMAEETPassword();
      SGDPhoenixUserName = radian.web.databaseResourceLoader.getSGDUserId();
      SGDPhoenixPasswd = radian.web.databaseResourceLoader.getSGDPassword();
      CaterpillarPhoenixUserName = radian.web.databaseResourceLoader.getCaterpillarUserId();
      CaterpillarPhoenixPasswd = radian.web.databaseResourceLoader.getCaterpillarPassword();
      OMAPhoenixUserName = radian.web.databaseResourceLoader.getOMAUserId();
      OMAPhoenixPasswd = radian.web.databaseResourceLoader.getOMAPassword();
      SiemensPhoenixUserName = radian.web.databaseResourceLoader.getSiemensUserId();
      SiemensPhoenixPasswd = radian.web.databaseResourceLoader.getSiemensPassword();
      GetragPhoenixUserName = radian.web.databaseResourceLoader.getGetragUserId();
      GetragPhoenixPasswd = radian.web.databaseResourceLoader.getGetragPassword();
      AlcoaPhoenixUserName = radian.web.databaseResourceLoader.getAlcoaUserId();
      AlcoaPhoenixPasswd = radian.web.databaseResourceLoader.getAlcoaPassword();
      ZWLPhoenixUserName = radian.web.databaseResourceLoader.getZWLUserId();
      ZWLPhoenixPasswd = radian.web.databaseResourceLoader.getZWLPassword();
      HansSasserathPhoenixUserName = radian.web.databaseResourceLoader.getHansSasserathUserId();
      HansSasserathPhoenixPasswd = radian.web.databaseResourceLoader.getHansSasserathPassword();
		DDPhoenixUserName = radian.web.databaseResourceLoader.getDDUserId();
      DDPhoenixPasswd = radian.web.databaseResourceLoader.getDDPassword();
		GKNPhoenixUserName = radian.web.databaseResourceLoader.getGknUserId();
      GKNPhoenixPasswd = radian.web.databaseResourceLoader.getGknPassword();
		AeropiaPhoenixUserName = radian.web.databaseResourceLoader.getAeropiaUserId();
      AeropiaPhoenixPasswd = radian.web.databaseResourceLoader.getAeropiaPassword();
		GoodrichPhoenixUserName = radian.web.databaseResourceLoader.getGoodrichUserId();
      GoodrichPhoenixPasswd = radian.web.databaseResourceLoader.getGoodrichPassword();
		OMMCPhoenixUserName = radian.web.databaseResourceLoader.getOMMCUserId();
      OMMCPhoenixPasswd = radian.web.databaseResourceLoader.getOMMCPassword();
		FBMPhoenixUserName = radian.web.databaseResourceLoader.getFBMUserId();
      FBMPhoenixPasswd = radian.web.databaseResourceLoader.getFBMPassword();
		AeroczPhoenixUserName = radian.web.databaseResourceLoader.getAeroczUserId();
      AeroczPhoenixPasswd = radian.web.databaseResourceLoader.getAeroczPassword();
		GEPhoenixUserName = radian.web.databaseResourceLoader.getGEUserId();
      GEPhoenixPasswd = radian.web.databaseResourceLoader.getGEPassword();
		SalesPhoenixUserName = radian.web.databaseResourceLoader.getSalesUserId();
      SalesPhoenixPasswd = radian.web.databaseResourceLoader.getSalesPassword();
		HexcelPhoenixUserName = radian.web.databaseResourceLoader.getHexcelUserId();
      HexcelPhoenixPasswd = radian.web.databaseResourceLoader.getHexcelPassword();
		ZfPhoenixUserName = radian.web.databaseResourceLoader.getZfUserId();
      ZfPhoenixPasswd = radian.web.databaseResourceLoader.getZfPassword();
		AvcorpPhoenixUserName = radian.web.databaseResourceLoader.getAvcorpUserId();
      AvcorpPhoenixPasswd = radian.web.databaseResourceLoader.getAvcorpPassword();
		BaPhoenixUserName = radian.web.databaseResourceLoader.getBaUserId();
      BaPhoenixPasswd = radian.web.databaseResourceLoader.getBaPassword();
	 } catch (Exception e) {
      System.out.println("ERROR READING resources in PrintScreen:" + e.getMessage());
    }
    //currentScreen = null;
    url = null;
  }

  private void seterwDataSource() {
    try {
      String tempClient = db.getClient();
      if (tempClient.startsWith("Ray")) {
        ds.connect(dbDriver, phoenixUrl, rayPhoenixUserName, rayPhoenixPasswd, true);
      } else if (tempClient.startsWith("DRS")) {
        ds.connect(dbDriver, phoenixUrl, DRSPhoenixUserName, DRSPhoenixPasswd, true);
      } else if (tempClient.startsWith("SWA")) {
        ds.connect(dbDriver, phoenixUrl, SWAPhoenixUserName, SWAPhoenixPasswd, true);
      } else if (tempClient.startsWith("LMCO")) {
        ds.connect(dbDriver, phoenixUrl, LMCOPhoenixUserName, LMCOPhoenixPasswd, true);
      } else if (tempClient.startsWith("Seagate")) {
        ds.connect(dbDriver, phoenixUrl, SeagatePhoenixUserName, SeagatePhoenixPasswd, true);
      } else if (tempClient.startsWith("BAE")) {
        ds.connect(dbDriver, phoenixUrl, BAEPhoenixUserName, BAEPhoenixPasswd, true);
      } else if (tempClient.startsWith("UTC")) {
        ds.connect(dbDriver, phoenixUrl, UTCPhoenixUserName, UTCPhoenixPasswd, true);
      } else if (tempClient.startsWith("SD")) {
        ds.connect(dbDriver, phoenixUrl, SDPhoenixUserName, SDPhoenixPasswd, true);
      } else if (tempClient.startsWith("Miller")) {
        ds.connect(dbDriver, phoenixUrl, MillerPhoenixUserName, MillerPhoenixPasswd, true);
      } else if (tempClient.startsWith("GM")) {
        ds.connect(dbDriver, phoenixUrl, GMPhoenixUserName, GMPhoenixPasswd, true);
      } else if (tempClient.startsWith("CAL")) {
        ds.connect(dbDriver, phoenixUrl, CALPhoenixUserName, CALPhoenixPasswd, true);
      } else if (tempClient.startsWith("FEC")) {
        ds.connect(dbDriver, phoenixUrl, FECPhoenixUserName, FECPhoenixPasswd, true);
      } else if (tempClient.startsWith("Boeing")) {
        ds.connect(dbDriver, phoenixUrl, BoeingPhoenixUserName, BoeingPhoenixPasswd, true);
      } else if (tempClient.startsWith("AeroEco")) {
        ds.connect(dbDriver, phoenixUrl, AeroEcoPhoenixUserName, AeroEcoPhoenixPasswd, true);
      } else if (tempClient.startsWith("Dana")) {
        ds.connect(dbDriver, phoenixUrl, DanaPhoenixUserName, DanaPhoenixPasswd, true);
      } else if (tempClient.startsWith("ABLaero")) {
        ds.connect(dbDriver, phoenixUrl, ABLaeroPhoenixUserName, ABLaeroPhoenixPasswd, true);
      } else if (tempClient.startsWith("DOE")) {
        ds.connect(dbDriver, phoenixUrl, DOEPhoenixUserName, DOEPhoenixPasswd, true);
      } else if (tempClient.startsWith("IAI")) {
        ds.connect(dbDriver, phoenixUrl, IAIPhoenixUserName, IAIPhoenixPasswd, true);
      } else if (tempClient.startsWith("L3")) {
        ds.connect(dbDriver, phoenixUrl, L3PhoenixUserName, L3PhoenixPasswd, true);
      } else if (tempClient.startsWith("AM")) {
        ds.connect(dbDriver, phoenixUrl, AMPhoenixUserName, AMPhoenixPasswd, true);
      } else if (tempClient.startsWith("GEMA")) {
        ds.connect(dbDriver, phoenixUrl, GemaPhoenixUserName, GemaPhoenixPasswd, true);
      } else if (tempClient.startsWith("PGE")) {
        ds.connect(dbDriver, phoenixUrl, PGEPhoenixUserName, PGEPhoenixPasswd, true);
      } else if (tempClient.startsWith("QOS")) {
        ds.connect(dbDriver, phoenixUrl, QOSPhoenixUserName, QOSPhoenixPasswd, true);
      } else if (tempClient.startsWith("DCX")) {
        ds.connect(dbDriver, phoenixUrl, DCXPhoenixUserName, DCXPhoenixPasswd, true);
      } else if (tempClient.startsWith("FORD")) {
        ds.connect(dbDriver, phoenixUrl, FordPhoenixUserName, FordPhoenixPasswd, true);
      } else if (tempClient.startsWith("Algat")) {
        ds.connect(dbDriver, phoenixUrl, AlgatPhoenixUserName, AlgatPhoenixPasswd, true);
      } else if (tempClient.startsWith("BAZ")) {
        ds.connect(dbDriver, phoenixUrl, BAZPhoenixUserName, BAZPhoenixPasswd, true);
      } else if (tempClient.startsWith("CMM")) {
        ds.connect(dbDriver, phoenixUrl, CMMPhoenixUserName, CMMPhoenixPasswd, true);
      } else if (tempClient.startsWith("Kanfit")) {
        ds.connect(dbDriver, phoenixUrl, KanfitPhoenixUserName, KanfitPhoenixPasswd, true);
      } else if (tempClient.startsWith("Fedco")) {
        ds.connect(dbDriver, phoenixUrl, FedcoPhoenixUserName, FedcoPhoenixPasswd, true);
      } else if (tempClient.startsWith("GD")) {
        ds.connect(dbDriver, phoenixUrl, GDPhoenixUserName, GDPhoenixPasswd, true);
      } else if (tempClient.startsWith("IMCO")) {
        ds.connect(dbDriver, phoenixUrl, IMCOPhoenixUserName, IMCOPhoenixPasswd, true);
      } else if (tempClient.startsWith("Kemfast")) {
        ds.connect(dbDriver, phoenixUrl, KemfastPhoenixUserName, KemfastPhoenixPasswd, true);
      } else if (tempClient.startsWith("BL")) {
        ds.connect(dbDriver, phoenixUrl, BLPhoenixUserName, BLPhoenixPasswd, true);
      } else if (tempClient.startsWith("Verasun")) {
        ds.connect(dbDriver, phoenixUrl, VerasunPhoenixUserName, VerasunPhoenixPasswd, true);
      } else if (tempClient.startsWith("Hrgivon")) {
        ds.connect(dbDriver, phoenixUrl, HrgivonPhoenixUserName, HrgivonPhoenixPasswd, true);
      } else if (tempClient.startsWith("Haargaz")) {
        ds.connect(dbDriver, phoenixUrl, HaargazPhoenixUserName, HaargazPhoenixPasswd, true);
      } else if (tempClient.startsWith("Cyclone")) {
        ds.connect(dbDriver, phoenixUrl, CyclonePhoenixUserName, CyclonePhoenixPasswd, true);
      } else if (tempClient.startsWith("Volvo")) {
        ds.connect(dbDriver, phoenixUrl, VolvoPhoenixUserName, VolvoPhoenixPasswd, true);
      } else if (tempClient.startsWith("Nalco")) {
        ds.connect(dbDriver, phoenixUrl, NalcoPhoenixUserName, NalcoPhoenixPasswd, true);
      } else if (tempClient.startsWith("Pepsi")) {
        ds.connect(dbDriver, phoenixUrl, PepsiPhoenixUserName, PepsiPhoenixPasswd, true);
      } else if (tempClient.startsWith("HAL")) {
        ds.connect(dbDriver, phoenixUrl, HALPhoenixUserName, HALPhoenixPasswd, true);
      } else if (tempClient.startsWith("ABM")) {
        ds.connect(dbDriver, phoenixUrl, ABMPhoenixUserName, ABMPhoenixPasswd, true);
      } else if (tempClient.startsWith("MTL")) {
        ds.connect(dbDriver, phoenixUrl, MTLPhoenixUserName, MTLPhoenixPasswd, true);
      } else if (tempClient.startsWith("Timken")) {
        ds.connect(dbDriver, phoenixUrl, TimkenPhoenixUserName, TimkenPhoenixPasswd, true);
      } else if (tempClient.startsWith("NRG")) {
        ds.connect(dbDriver, phoenixUrl, NRGPhoenixUserName, NRGPhoenixPasswd, true);
      } else if (tempClient.startsWith("MAEET")) {
        ds.connect(dbDriver, phoenixUrl, MAEETPhoenixUserName, MAEETPhoenixPasswd, true);
      } else if (tempClient.startsWith("SGD")) {
        ds.connect(dbDriver, phoenixUrl, SGDPhoenixUserName, SGDPhoenixPasswd, true);
      } else if (tempClient.startsWith("Caterpillar")) {
        ds.connect(dbDriver, phoenixUrl, CaterpillarPhoenixUserName, CaterpillarPhoenixPasswd, true);
      } else if (tempClient.startsWith("OMA")) {
        ds.connect(dbDriver, phoenixUrl, OMAPhoenixUserName, OMAPhoenixPasswd, true);
      } else if (tempClient.startsWith("Siemens")) {
        ds.connect(dbDriver, phoenixUrl, SiemensPhoenixUserName, SiemensPhoenixPasswd, true);
      } else if (tempClient.startsWith("Getrag")) {
        ds.connect(dbDriver, phoenixUrl, GetragPhoenixUserName, GetragPhoenixPasswd, true);
      } else if (tempClient.startsWith("Alcoa")) {
        ds.connect(dbDriver, phoenixUrl, AlcoaPhoenixUserName, AlcoaPhoenixPasswd, true);
      } else if (tempClient.startsWith("ZWL")) {
        ds.connect(dbDriver, phoenixUrl, ZWLPhoenixUserName, ZWLPhoenixPasswd, true);
      } else if (tempClient.startsWith("HansSasserath")) {
        ds.connect(dbDriver, phoenixUrl, HansSasserathPhoenixUserName, HansSasserathPhoenixPasswd, true);
      } else if (tempClient.startsWith("DD")) {
        ds.connect(dbDriver, phoenixUrl, DDPhoenixUserName, DDPhoenixPasswd, true);
      }  else if (tempClient.startsWith("GKN")) {
        ds.connect(dbDriver, phoenixUrl, GKNPhoenixUserName, GKNPhoenixPasswd, true);
      } else if (tempClient.startsWith("Aeropia")) {
        ds.connect(dbDriver, phoenixUrl, AeropiaPhoenixUserName, AeropiaPhoenixPasswd, true);
      } else if (tempClient.startsWith("Goodrich")) {
        ds.connect(dbDriver, phoenixUrl, GoodrichPhoenixUserName, GoodrichPhoenixPasswd, true);
      } else if (tempClient.startsWith("OMMC")) {
        ds.connect(dbDriver, phoenixUrl, OMMCPhoenixUserName, OMMCPhoenixPasswd, true);
      } else if (tempClient.startsWith("FBM")) {
        ds.connect(dbDriver, phoenixUrl, FBMPhoenixUserName, FBMPhoenixPasswd, true);
      } else if (tempClient.startsWith("Aerocz")) {
        ds.connect(dbDriver, phoenixUrl, AeroczPhoenixUserName, AeroczPhoenixPasswd, true);
      } else if (tempClient.startsWith("GE")) {
        ds.connect(dbDriver, phoenixUrl, GEPhoenixUserName, GEPhoenixPasswd, true);
      } else if (tempClient.startsWith("Sales")) {
        ds.connect(dbDriver, phoenixUrl, SalesPhoenixUserName, SalesPhoenixPasswd, true);
      } else if (tempClient.startsWith("Hexcel")) {
        ds.connect(dbDriver, phoenixUrl, HexcelPhoenixUserName, HexcelPhoenixPasswd, true);
      } else if (tempClient.startsWith("ZF")) {
        ds.connect(dbDriver, phoenixUrl, ZfPhoenixUserName, ZfPhoenixPasswd, true);
      } else if (tempClient.startsWith("Avcorp")) {
        ds.connect(dbDriver, phoenixUrl, AvcorpPhoenixUserName, AvcorpPhoenixPasswd, true);
      } else if (tempClient.startsWith("BA")) {
        ds.connect(dbDriver, phoenixUrl, BaPhoenixUserName, BaPhoenixPasswd, true);
      }

      erw.setDataSource(ds);
    } catch (Exception ed) {
      ed.printStackTrace();
    }
  }

  protected void resetAllVars() {
    function = null;
    inData = null;
  }

  protected void print(TcmisOutputStreamServer out) {
    mySendObj = new Hashtable();
    mySendObj.put("MSG", "Request received");
    try {
      out.sendObject( (Hashtable) mySendObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setInData(Hashtable myRecvObj) {
    inData = myRecvObj;
  }

  protected void getResult() {
    //System.out.println("----------- got here");
    String whichScreen = null;
    whichScreen = inData.get("CURRENT_SCREEN").toString();
    reoprtlog.info("getResult Batch  Screen:   " + whichScreen + " ");
    erw = new ACJEngine();
    erw.setDebugMode(true);
    erw.setX11GfxAvailibility(false);
    erw.setTargetOutputDevice(ACJConstants.PDF);
    ec = new ACJOutputProcessor();
    if (whichScreen.equalsIgnoreCase("Inventory") || whichScreen.equalsIgnoreCase("NCT") || whichScreen.equalsIgnoreCase("OT")) {
      adh = new AppDataHandler();
    } else {
      ds = new JDBCHandler();
    }

    if (whichScreen.equalsIgnoreCase("CATALOG")) {
      buildCatalogScreen();
    } else if (whichScreen.equalsIgnoreCase("WC")) { //waste catalog
      buildWasteCatalogScreen();
    } else if (whichScreen.equalsIgnoreCase("WT")) { //waste track win
      buildWasteTrackScreen();
    } else if (whichScreen.equalsIgnoreCase("WM")) { //waste management
      buildWasteManagementScreen();
    } else if (whichScreen.equalsIgnoreCase("WOT")) { //waste order track
      buildWasteOrderTrackScreen();
    } else if (whichScreen.equalsIgnoreCase("UP")) { //User Profile
      buildUserProfileScreen();
    } else if (whichScreen.equalsIgnoreCase("Inventory")) { //Inventory
      buildInventoryScreen();
    } else if (whichScreen.equalsIgnoreCase("Work_Area")) { //work area
      buildWorkAreaScreen();
    } else if (whichScreen.equalsIgnoreCase("Approvers")) { // Admin screeen Approvers
      buildAppScreen();
    } else if (whichScreen.equalsIgnoreCase("Use_App_Group")) { // Approver groups
      buildUseAppGrpScreen();
    } else if (whichScreen.equalsIgnoreCase("New_Chem_App")) { // New Chemical Approvers
      buildNewChemScreen();
    } else if (whichScreen.equalsIgnoreCase("Admin_Group")) { // Admin groups
      buildAdminGrpScreen();
    } else if (whichScreen.equalsIgnoreCase("OT")) { //DMITRIY order track
      buildOrderTrackingScreen();
    } else if (whichScreen.equalsIgnoreCase("NCT")) { //DMITRIY New Chem Track
      buildNewChemTrackingScreen();
    } else if (whichScreen.equalsIgnoreCase("LIST") || whichScreen.equalsIgnoreCase("COST") || whichScreen.equalsIgnoreCase("INVOICE")) {
      buildListCostScreen();
    } else if (whichScreen.equalsIgnoreCase("VocUsage Report")) {
      userId = inData.get("USER_ID").toString().trim();
      String fileType1 = inData.get("FILE_TYPE").toString();
      //String session_id = userId + tmpReqNum.toString();
      //String client = db.getClient().toString().trim();

      //Save the data in the Database
      function = inData.get("ACTION").toString();
      facName = inData.get("FACILITY").toString();
      workAreaDesc = inData.get("WORK_AREA").toString();
      chemType = inData.get("KEYWORD").toString();
      begMonth = inData.get("BEGMONTH").toString();
      begYear = inData.get("BEGYEAR").toString();
      endMonth = inData.get("ENDMONTH").toString();
      endYear = inData.get("ENDYEAR").toString();
      searchType = inData.get("SEARCH_TYPE").toString();
      listId = inData.get("LIST_ID").toString();
      keyword = inData.get("KEYWORD").toString();
      cas_num = inData.get("CAS_NUM").toString();
      chemListDesc = inData.get("CHEM_DESC").toString();
      groupBy = (int[]) inData.get("Group_Matrix");
      Vector groupBy1 = (Vector) inData.get("GROUP_BY");
      String orderby = "";

      for (int i = 0; i < groupBy1.size(); i++) {
        String s = groupBy1.elementAt(i).toString();
        if (s.equalsIgnoreCase("FACILITY")) {
          s = "facility";
        } else if (s.equalsIgnoreCase("WORK_AREA")) {
          s = "location";
        } else if (s.equalsIgnoreCase("CAS_NUM")) {
          if (function.equalsIgnoreCase("VOC_USAGE")) {
            s = "year_month";
          } else {
            s = "cas_number";
          }
        } else if (s.equalsIgnoreCase("DEL_POINT")) {
          s = "delivery_point";
        } else if (s.equalsIgnoreCase("MONTH")) {
          s = "year_month";
        }
        orderby += s + ",";
      }

      String groupmatrix = "";
      int g[] = new int[groupBy.length];
      for (int i = 0; i < g.length; i++) {
        switch (groupBy[i]) {
          case 0: // '\0'
            groupmatrix += "0,";
            break;

          case 2: // '\002'
            groupmatrix += "2,";
            break;

          case 4: // '\004'
            groupmatrix += "4,";
            break;

          case 3: // '\003'
            groupmatrix += "3,";
            break;

          case 1: // '\001'
            groupmatrix += "1,";
            break;
        }
      }

      String reportname = inData.get("REPORT_NAME").toString();
      String sortby1 = inData.get("SORT_BY").toString();
      String voccurrentScreen = inData.get("CURRENT_SCREEN").toString();
      //String Groupmatrix = "";
      long query_end = System.currentTimeMillis();

      String query = "insert into report_parameter (FILE_TYPE,ACTION,FACILITY,WORK_AREA,BEGMONTH,BEGYEAR,ENDMONTH,ENDYEAR,";
      query += "SEARCH_TYPE,LIST_ID,KEYWORD,CAS_NUM,CHEM_DESC,USER_ID,REPORT_NAME,SORT_BY,GROUP_BY,CURRENT_SCREEN,Group_Matrix,unique_id) ";
      query += "values ('" + fileType1 + "','" + function + "','" + facName + "','" + workAreaDesc + "','" + begMonth + "','" + begYear + "','" + endMonth + "','" + endYear + "','";
      query += "" + searchType + "','" + listId + "','" + keyword + "','" + cas_num + "','" + chemListDesc + "','" + userId + "','" + reportname + "','" + sortby1 + "','" + orderby.substring(0, orderby.length() - 1) + "','" + voccurrentScreen + "','" +
          groupmatrix.substring(0, groupmatrix.length() - 1) + "','" + query_end + "')";

      try {
        db.doUpdate(query);
      } catch (Exception e) {
        //System.out.println("\n\n--------- Erros in Inserting report paramters For Batch Report. \n\n");
        sendErrorEmail("Error in Submitting report paramters for Batch Report.", "Error in Submitting report paramters for Batch Report.");
        e.printStackTrace();
        return;
      }

      buildUsageRepScreen();
    } else if (whichScreen.equalsIgnoreCase("MSDS")) {
      buildMSDSScreen();
    }
  }

  // Nawaz 04-10-2001
  public void buildMSDSScreen() {
    String msdscurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

	 //record who's running this report
	 try {
		db.doUpdate("insert into formatted_report_usage(report_id,report_name,requested_by,requested_date,file_type) select formatted_report_usage_seq.nextval,'FORMATTED_MSDS',"+userId+",sysdate,'"+fileType+"' from dual");
	 }catch (Exception e) {
		//do nothing
	 }

	 try {
      erw.readTemplate("" + templDirectory + "MSDS.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility = '" + facilityId + "'";

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB001", facilityId);

    generateReport(msdscurrentScreen);

    try {
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  //Nawaz 12-11-00
  public void buildUsageRepScreen() {
    mySendObj = new Hashtable();

    function = inData.get("ACTION").toString();
    facName = inData.get("FACILITY").toString();
    chemType = inData.get("KEYWORD").toString();
    begMonth = inData.get("BEGMONTH").toString();
    begYear = inData.get("BEGYEAR").toString();
    endMonth = inData.get("ENDMONTH").toString();
    endYear = inData.get("ENDYEAR").toString();
    searchType = inData.get("SEARCH_TYPE").toString();
    listId = inData.get("LIST_ID").toString();
    keyword = inData.get("KEYWORD").toString();
    cas_num = inData.get("CAS_NUM").toString();
    chemListDesc = inData.get("CHEM_DESC").toString();
    groupBy = (int[]) inData.get("Group_Matrix");
    fileType = inData.get("FILE_TYPE").toString();
    userId = inData.get("USER_ID").toString();
    String currentScreen = inData.get("CURRENT_SCREEN").toString();
	 //record who's running this report
	  try {
		db.doUpdate("insert into formatted_report_usage(report_id,report_name,requested_by,requested_date,file_type) select formatted_report_usage_seq.nextval,'"+function+"',"+userId+",sysdate,'"+fileType+"' from dual");
	  }catch (Exception e) {

	  }

	 if (function.equalsIgnoreCase("REPORTABLE_USAGE")) {
      buildUsageReport(currentScreen);
    } else if (function.equalsIgnoreCase("VOC_USAGE")) {
      buildVocUsageReport(currentScreen);
    }
  }

  protected void buildUsageReport(String currentusscreen) {
    try {
      Vector reportData = ReportData.getReportableUsageReport(db, inData);
      if (reportData.size() > 0) {
        fileType = inData.get("FILE_TYPE").toString();
        if (fileType.equalsIgnoreCase("PDF")) {
          url = buildPDFUsageReport(reportData, currentusscreen);
        } else if (fileType.equalsIgnoreCase("CSV")) {
          url = buildXlsUsageReport(reportData, currentusscreen);
        }
      } else {
        //System.out.println("\n\nIn BuildUsage Report No Resutls from the Querry");
        String esub1 = "No records were found matching your search criteria.\n";
        String emsg1 = " The batch report you requested was not created.\n";
        sendErrorEmail(esub1, emsg1);
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected String buildPDFUsageReport(Vector lineItems, String currentusageScreen) {
    adh = new AppDataHandler();
    //System.out.println("----------- function: " + function);
    try {
      if (function.equalsIgnoreCase("REPORTABLE_USAGE")) {
        erw.readTemplate("" + templDirectory + "UsageReport.erw");
      } else if (function.equalsIgnoreCase("VOC_USAGE")) {
        erw.readTemplate("" + templDirectory + "VOC.erw");
      }
    } catch (Exception e) {
      //System.out.println("ERROR loading template");
      e.printStackTrace();
      String s = url;
      return s;
    }
    tm = erw.getTemplateManager();
    modifyTemplate(tm);

    RegisterTable rt[] = getData(lineItems);
    for (int i = 0; i < rt.length; i++) {
      adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
    }

    try {
      erw.setDataSource(adh);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
    reoprtlog.info("buildPDFUsageReport   Start " + tmpReqNum.toString() + "    Size: " + lineItems.size() + "  ");
    erw.setCacheOption(true, "" + HomeDirectory + userId + currentusageScreen + "screen" + tmpReqNum.toString() + ".joi");

    try {
      String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
      ec.setPathForFontMapFile(fontmappath);

      ec.setReportData(erw.generateReport());
      if (function.equalsIgnoreCase("REPORTABLE_USAGE")) {
        currentusageScreen = "REPORTABLE_USAGE";
        ec.setPDFProperty("FileName", "" + HomeDirectory + userId + currentusageScreen + "screen" + tmpReqNum.toString() + ".pdf");
        ec.setPDFProperty("ZipCompressed", new Boolean(false));
      } else if (function.equalsIgnoreCase("VOC_USAGE")) {
        currentusageScreen = "VOC_USAGE";
        ec.setPDFProperty("FileName", "" + HomeDirectory + userId + currentusageScreen + "screen" + tmpReqNum.toString() + ".pdf");
        ec.setPDFProperty("ZipCompressed", new Boolean(false));
      }
      ec.generatePDF();
    } catch (Exception e) {
      //System.out.println("ERROR generating report");
      e.printStackTrace();
      String s2 = url;
      return s2;
    }
    reoprtlog.info("buildPDFUsageReport    Done  " + tmpReqNum.toString() + "");
    updateReport(tmpReqNum, currentusageScreen);
    sendEmail();

    return url;
  }

  //Nawaz 02-14-02
  protected String buildXlsVocReport(Vector FinalData, String usagecurrentScreen) throws Exception {
    StringBuffer Msgxl = new StringBuffer();
    String url1 = "";

    Msgxl.append("<FONT FACE=\"Arial\">");
    Msgxl.append("<TABLE BORDER=\"0\">\n");
    Msgxl.append("<TR>\n");

    String s = "";
    if (searchType.equalsIgnoreCase("KEYWORD")) {
      s = "Chemicals matching '" + keyword + "'";
    } else
    if (searchType.equalsIgnoreCase("CAS_NUM")) {
      s = "Cas Number" + cas_num + " Usage";
    } else
    if (searchType.equalsIgnoreCase("LIST")) {
      s = chemListDesc + " Usage";
    } else
    if (searchType.equalsIgnoreCase("ALL")) {
      s = "All Chemical Usage";
    } else
    if (searchType.equalsIgnoreCase("VOC")) {
      s = "VOC Usage Report";
    } else {
      s = "Usage Report";

    }
    Msgxl.append("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\">" + s + "</FONT></B></TD>\n");
    Msgxl.append(getTimeDate());
    Msgxl.append("</TR>\n");
    Msgxl.append("<TR>\n");
    Msgxl.append("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
    Msgxl.append("</TR>\n");
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " + facName + "</FONT></TD></TR>\n");
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: " + workAreaDesc + "</FONT></TD></TR>\n");

    DateFormatSymbols dfs = new DateFormatSymbols();
    String months[] = dfs.getMonths();
    String desc = "";

    if (begMonth.equals(endMonth) && begYear.equals(endYear)) {
      desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
    } else {
      desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
    }
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Time Period: " + desc + "</FONT></TD></TR>\n");
    Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
    Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
    Msgxl.append("</TABLE>\n");

    Msgxl.append("<TABLE BORDER=\"1\">\n");
    Msgxl.append("<TR>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Part Number</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Trade Name</B></TD>\n");
    //Msgxl.append("<TD ALIGN=\"CENTER\"><B>Manufactures Name</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Units Used</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. per Unit</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Used</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>% VOCs</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Mixture VOC (lbs./unit)</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Reportable</B></TD>\n");
    Msgxl.append("</TR>\n");

    Hashtable Final = new Hashtable();
    Random rand = new Random();

    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    String contents = "";
    contents += Msgxl;

    String file = "";
    usagecurrentScreen = "VOC_USAGE";
    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
    file = HomeDirectory + userId + usagecurrentScreen + "screen" + tmpReqNum.toString() + ".xls";
    //File outFile = new File(file);

    try {
      FileOutputStream DBO = new FileOutputStream(file, true);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    } catch (IOException ioe) {
      //System.out.println("\n\nError opening output file: ");
      System.out.println(String.valueOf(ioe));
      System.out.println("\n");
      return url1;
    } finally {
      Msgxl = new StringBuffer();
    }
    try {
      for (int j = 0; j < FinalData.size(); j++) {
        String Color = " ";

        if (j % 2 == 0) {
          Color = "bgcolor=\"#dddddd\"";
        } else {
          Color = "bgcolor=\"#fcfcfc\"";
        }

        Final = (Hashtable) FinalData.elementAt(j);

        Msgxl.append("<TR>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("FACILITY").toString().length() < 1 ? "&nbsp;" : Final.get("FACILITY").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WORK_AREA").toString().length() < 1 ? "&nbsp;" : Final.get("WORK_AREA").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("FAC_PART_ID").toString().length() < 1 ? "&nbsp;" : Final.get("FAC_PART_ID").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("TRADE_NAME").toString().length() < 1 ? "&nbsp;" : Final.get("TRADE_NAME").toString());
        Msgxl.append("</TD>\n");
        //Msgxl.append("<TD>");Msgxl.append(Final.get("MFG_DESC").toString().length()<1?"&nbsp;":Final.get("MFG_DESC").toString());Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("QTY_SHIPPED").toString().length() < 1 ? "&nbsp;" : Final.get("QTY_SHIPPED").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WT_PER_UNIT").toString().length() < 1 ? "&nbsp;" : Final.get("WT_PER_UNIT").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WT_SHIPPED").toString().length() < 1 ? "&nbsp;" : Final.get("WT_SHIPPED").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("PERCENT").toString().length() < 1 ? "&nbsp;" : Final.get("PERCENT").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("MIXTURE_VOC").toString().length() < 1 ? "&nbsp;" : Final.get("MIXTURE_VOC").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("LBS_REPORTABLE").toString().length() < 1 ? "&nbsp;" : Final.get("LBS_REPORTABLE").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("</TR>\n");

        boolean status = true;

        if ( (j % 50 == 0) && (j != 0)) {
          contents = "";
          contents += Msgxl;

          try {
            FileOutputStream DBO = new FileOutputStream(file, status);
            byte outbuf[];
            outbuf = new byte[contents.length()];
            outbuf = contents.getBytes();
            DBO.write(outbuf);
            DBO.close();
          } catch (IOException ioe) {
            System.out.println("\n\nError opening output file: ");
            System.out.println(String.valueOf(ioe));
            System.out.println("\n");
            return url1;
          } finally {
            Msgxl = new StringBuffer();
          }
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
      return url1;
    } finally {

    }

    Msgxl.append("</TABLE></FONT>");
    contents = "";
    contents += Msgxl;
    try {
      FileOutputStream DBO = new FileOutputStream(file, true);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    } catch (IOException ioe) {
      System.out.println("\n\nError opening output file: ");
      System.out.println(String.valueOf(ioe));
      System.out.println("\n");
      return url1;
    } finally {
      Msgxl = new StringBuffer();
    }

    updateReport(tmpReqNum, usagecurrentScreen);
    sendEmail();

    return url1;
  }

  //Nawaz 02-14-02
  protected String buildXlsUsageReport(Vector FinalData, String usagecurrentScreen) throws Exception {
    StringBuffer Msgxl = new StringBuffer();
    String url1 = "";

    Msgxl.append("<FONT FACE=\"Arial\">");
    Msgxl.append("<TABLE BORDER=\"0\">\n");
    Msgxl.append("<TR>\n");

    String s = "";
    if (searchType.equalsIgnoreCase("KEYWORD")) {
      s = "Chemicals matching '" + keyword + "'";
    } else
    if (searchType.equalsIgnoreCase("CAS_NUM")) {
      s = "Cas Number" + cas_num + " Usage";
    } else
    if (searchType.equalsIgnoreCase("LIST")) {
      s = chemListDesc + " Usage";
    } else
    if (searchType.equalsIgnoreCase("ALL")) {
      s = "All Chemical Usage";
    } else
    if (searchType.equalsIgnoreCase("VOC")) {
      s = "VOC Usage Report";
    } else {
      s = "Usage Report";

    }
    Msgxl.append("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\">" + s + "</FONT></B></TD>\n");
    Msgxl.append(getTimeDate());
    Msgxl.append("</TR>\n");
    Msgxl.append("<TR>\n");
    Msgxl.append("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
    Msgxl.append("</TR>\n");
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " + facName + "</FONT></TD></TR>\n");
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: " + workAreaDesc + "</FONT></TD></TR>\n");

    DateFormatSymbols dfs = new DateFormatSymbols();
    String months[] = dfs.getMonths();
    String desc = "";

    if (begMonth.equals(endMonth) && begYear.equals(endYear)) {
      desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
    } else {
      desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
    }
    Msgxl.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Time Period: " + desc + "</FONT></TD></TR>\n");
    Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
    Msgxl.append("<TR><TD>&nbsp;</TD></TR>\n");
    Msgxl.append("</TABLE>\n");

    Msgxl.append("<TABLE BORDER=\"1\">\n");
    Msgxl.append("<TR>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Cas #/SARA Group</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Constituent</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Part Number</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Trade Name</B></TD>\n");
    //Msgxl.append("<TD ALIGN=\"CENTER\"><B>Manufactures Name</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Delivery Point</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>Units Used</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. per Unit</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Used</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>% Wt. of Constituent</B></TD>\n");
    Msgxl.append("<TD ALIGN=\"CENTER\"><B>lbs. Reportable</B></TD>\n");
    Msgxl.append("</TR>\n");

    Hashtable Final = new Hashtable();
    Random rand = new Random();

    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    String contents = "";
    contents += Msgxl;

    String file = "";
    usagecurrentScreen = "REPORTABLE_USAGE";
    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
    file = HomeDirectory + userId + usagecurrentScreen + "screen" + tmpReqNum.toString() + ".xls";
    //File outFile = new File(file);

    try {
      FileOutputStream DBO = new FileOutputStream(file, true);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    } catch (IOException ioe) {
      System.out.println("\n\nError opening output file: ");
      System.out.println(String.valueOf(ioe));
      System.out.println("\n");
      return url1;
    } finally {
      Msgxl = new StringBuffer();
    }
    try {
      for (int j = 0; j < FinalData.size(); j++) {
        String Color = " ";

        if (j % 2 == 0) {
          Color = "bgcolor=\"#dddddd\"";
        } else {
          Color = "bgcolor=\"#fcfcfc\"";
        }

        Final = (Hashtable) FinalData.elementAt(j);

        Msgxl.append("<TR>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("FACILITY").toString().length() < 1 ? "&nbsp;" : Final.get("FACILITY").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WORK_AREA").toString().length() < 1 ? "&nbsp;" : Final.get("WORK_AREA").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("CAS_NUMBER").toString().length() < 1 ? "&nbsp;" : Final.get("CAS_NUMBER").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("RPT_CHEMICAL").toString().length() < 1 ? "&nbsp;" : Final.get("RPT_CHEMICAL").toString());
        Msgxl.append("</TD>\n");

        Msgxl.append("<TD>");
        Msgxl.append(Final.get("FAC_PART_ID").toString().length() < 1 ? "&nbsp;" : Final.get("FAC_PART_ID").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("TRADE_NAME").toString().length() < 1 ? "&nbsp;" : Final.get("TRADE_NAME").toString());
        Msgxl.append("</TD>\n");
        //Msgxl.append("<TD>");Msgxl.append(Final.get("MFG_DESC").toString().length()<1?"&nbsp;":Final.get("MFG_DESC").toString());Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("DELIVERY_POINT").toString().length() < 1 ? "&nbsp;" : Final.get("DELIVERY_POINT").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("QTY_SHIPPED").toString().length() < 1 ? "&nbsp;" : Final.get("QTY_SHIPPED").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WT_PER_UNIT").toString().length() < 1 ? "&nbsp;" : Final.get("WT_PER_UNIT").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("WT_SHIPPED").toString().length() < 1 ? "&nbsp;" : Final.get("WT_SHIPPED").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("PERCENT").toString().length() < 1 ? "&nbsp;" : Final.get("PERCENT").toString());
        Msgxl.append("</TD>\n");
        Msgxl.append("<TD>");
        Msgxl.append(Final.get("LBS_REPORTABLE").toString().length() < 1 ? "&nbsp;" : Final.get("LBS_REPORTABLE").toString());
        Msgxl.append("</TD>\n");

        Msgxl.append("</TR>\n");

        boolean status = true;

        if ( (j % 50 == 0) && (j != 0)) {
          contents = "";
          contents += Msgxl;

          try {
            FileOutputStream DBO = new FileOutputStream(file, status);
            byte outbuf[];
            outbuf = new byte[contents.length()];
            outbuf = contents.getBytes();
            DBO.write(outbuf);
            DBO.close();
          } catch (IOException ioe) {
            System.out.println("\n\nError opening output file: ");
            System.out.println(String.valueOf(ioe));
            System.out.println("\n");
            return url1;
          } finally {
            Msgxl = new StringBuffer();
          }
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
      return url1;
    } finally {

    }

    Msgxl.append("</TABLE></FONT>");
    contents = "";
    contents += Msgxl;
    try {
      FileOutputStream DBO = new FileOutputStream(file, true);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    } catch (IOException ioe) {
      System.out.println("\n\nError opening output file: ");
      System.out.println(String.valueOf(ioe));
      System.out.println("\n");
      return url1;
    } finally {
      Msgxl = new StringBuffer();
    }

    updateReport(tmpReqNum, usagecurrentScreen);
    sendEmail();

    return url1;
  }

  public RegisterTable[] getData(Vector reportData1) {
    int[] g = new int[groupBy.length];
    for (int i = 0; i < g.length; i++) {
      switch (groupBy[i]) {
        case GROUP_BY_FAC:
          g[i] = UsageReportDataServ.FACILITY;
          break;
        case GROUP_BY_CAS_NUM:
          g[i] = UsageReportDataServ.CASNUM;
          break;
        case GROUP_BY_MONTH:
          g[i] = UsageReportDataServ.YEARMONTH;
          break;
        case GROUP_BY_DEL_POINT:
          g[i] = UsageReportDataServ.DELIVERY_POINT;
          break;
        case GROUP_BY_WORK_AREA:
          g[i] = UsageReportDataServ.WORKAREA;
          break;
        default:
          g[i] = -1;
      }
    }
    //   System.out.println("\n\n-------HI! its in Register Table getData"+ reportData1 +"\n" + g );
    RegisterTable[] rt = new RegisterTable[1];
    rt[0] = new RegisterTable(UsageReportDataServ.getVector(reportData1, g), "UsageReportData", UsageReportDataServ.getFieldVector(), null);
    return rt;
  }

  public void modifyTemplate(TemplateManager tmplt) {
    tmplt.setLabel("FACILITY_LABEL", facName);
    tmplt.setLabel("WORK_AREA_LABEL", workAreaDesc);
    String s = "";
    if (chemType.equalsIgnoreCase("KEYWORD")) {
      s = "Chemicals matching '" + keyword + "'";
    } else if (chemType.equalsIgnoreCase("CAS_NUM")) {
      s = "Cas Number" + cas_num + " Usage";
    } else if (chemType.equalsIgnoreCase("LIST")) {
      s = chemListDesc + " Usage";
    } else if (chemType.equalsIgnoreCase("ALL")) {
      s = "All Chemical Usage";
    } else if (chemType.equalsIgnoreCase("VOC")) {
      s = "VOC Usage Report";
    } else {
      s = "Usage Report";
    }

    tmplt.setLabel("REPORT_NAME", s);

    // report head desc label(group/facility/dates)
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String desc = "";
    if (begMonth.equals(endMonth) && begYear.equals(endYear)) {
      desc = months[Integer.parseInt(begMonth)] + ", " + begYear;
    } else {
      desc = months[Integer.parseInt(begMonth)] + " " + begYear + " through " + months[Integer.parseInt(endMonth)] + " " + endYear;
    }
    tmplt.setLabel("RP_HEAD_DESC", desc);

    // set Zones visible

    int[] temp = new int[] {
        -1, -1, -1, -1, -1
    };
    temp[4] = groupBy[groupBy.length - 1];
    for (int i = 0; i < groupBy.length - 1; i++) {
      temp[i] = groupBy[i];
    }
    for (int i = 0; i < temp.length; i++) {
      String z = "GROUP_" + i + "_HEAD";
      if (temp[i] == -1) {
        tmplt.setVisible(z, false);
        z = "GROUP_" + i + "_FOOT";
        tmplt.setVisible(z, false);
        continue;
      }
      if (fileType.equalsIgnoreCase("PDF")) {
        tmplt.setVisible(z, true);
        z = "GROUP_" + i + "_FOOT";
        tmplt.setVisible(z, true);
      }

      z = "HEAD_" + i + "_LABEL";
      tmplt.setLabel(z, groupStringLabel[temp[i]]);
    }
  }

  protected void buildVocUsageReport(String currentscreenusage) {
    try {
      Vector reportData = ReportData.getVocUsageReport(db, inData);
      mySendObj.put("REPORT_DATA", reportData);
      if (reportData.size() > 0) {
        //System.out.println("\n\n-----Its in Build Usage Report --Size = " + reportData.size() + "\n");
        fileType = inData.get("FILE_TYPE").toString();
        if (fileType.equalsIgnoreCase("PDF")) {
          url = buildPDFUsageReport(reportData, currentscreenusage);
        } else if (fileType.equalsIgnoreCase("CSV")) {
          url = buildXlsVocReport(reportData, currentscreenusage);
        }
        //url = buildTest(reportData);
      } else {
        //System.out.println("\n\nIn BuildUsage Report No Resutls from the Querry");
      }
      Hashtable retH = new Hashtable();
      if (url.length() > 0) {
        retH.put("SUCCEEDED", new Boolean(true));
      } else {
        retH.put("SUCCEEDED", new Boolean(false));
      }
      retH.put("MSG",
               " Generating report failed.\n Please restart tcmIS and try again.\n If problem recurs, contact your tcmIS Coustomer Service Representative (CSR).");
      mySendObj.put("RETURN_CODE", retH);
      mySendObj.put("URL", url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void buildNewChemTrackingScreen() {
    String nchemcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String requestor = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR"));
    String approver = BothHelpObjs.makeBlankFromNull( (String) inData.get("APPROVER"));
    String searchText = BothHelpObjs.makeBlankFromNull( (String) inData.get("SEARCH_TEXT"));
    String facId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String workArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("WORK_AREA"));
    String requestId = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUEST_ID"));
    String showAllRequests = BothHelpObjs.makeBlankFromNull( (String) inData.get("SHOW_ALL_REQUESTS"));
    String requestorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR_NAME"));
    String approverName = BothHelpObjs.makeBlankFromNull( (String) inData.get("APPROVER_NAME"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    String facility_name = (String) inData.get("FACILITY_NAME");
    String workarea_name = (String) inData.get("WORK_AREA_NAME");
    String status_sel = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_SELECTION"));
    String need_approval = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEED_APPROVAL"));
    StringBuffer Msg = new StringBuffer();
    boolean needApp = false;
    needApp = need_approval.equalsIgnoreCase("T");

    String query = "";
    try {
      NewChemTrackTable nctt = new NewChemTrackTable(db);
      query = nctt.buildQuery(userId, requestor, requestId, approver, facId, workArea, searchText, status_sel, need_approval.equalsIgnoreCase("T"));
    } catch (Exception e) {
      e.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.";
      sendErrorEmail(esub1, emsg1);
      return;
    }

    //setting status
    int i = 0;
    String stat = "";
    try {
      i = Integer.parseInt(status_sel);
    } catch (Exception e) {
      i = 0;
    }

    //boolean statusSet = i>0;
    if (i >= 1024) { // return no record
      stat += "";
      i = i - 1024;
    }
    if (i >= 512) { // pending PN
      stat += "Pending Catalog, ";
      i = i - 512;
    }
    if (i >= 256) { //  new Approval
      stat += "Draft(New Approval), ";
      i = i - 256;
    }
    if (i >= 128) { // new Size/Packaging
      stat += "Draft(New Size/Packaging), ";
      i = i - 128;
    }
    if (i >= 64) { // pending Pricing
      stat += "Pending Pricing, ";
      i = i - 64;
    }
    if (i >= 32) { // rejected
      stat += "Rejected, ";
      i = i - 32;
    }
    if (i >= 16) { // ready to order
      stat += "Ready to Order, ";
      i = i - 16;
    }
    if (i >= 8) { // pending QC
      if ("Seagate".equalsIgnoreCase(db.getClient())) {
        stat += "Pending QC, ";
      } else {
        stat += "Pending TCM QC, ";
      }
      i = i - 8;
    }
    if (i >= 4) { // new Part
      stat += "Draft(New Part), ";
      i = i - 4;
    }
    if (i >= 2) { // new material
      stat += "Draft(New Material), ";
      i = i - 2;
    }
    if (i >= 1) { // pending approval(SWA,Raytheon,DRS...) or CRA (Seagate)
      if ("Seagate".equalsIgnoreCase(db.getClient())) {
        stat += "Pending CRA, ";
      } else {
        stat += "Pending Approval, ";
      }
      i = i - 1;
    }
    if (stat.length() > 3) {
      stat = stat.substring(0, stat.length() - 2);
    }

    if (fileType.equalsIgnoreCase("PDF")) {
      try {
        erw.readTemplate("" + templDirectory + "NewChemTrack.erw"); //Query the result directly from database
        tm = erw.getTemplateManager();
        tm.setLabel("REQL", requestorName);
        tm.setLabel("APPL", approver);
        tm.setLabel("SEARCHL", searchText);
        if (needApp) {
          tm.setLabel("RECT001", " X");
        }
        tm.setLabel("FACL", facility_name);
        tm.setLabel("WAL", workarea_name);
        tm.setLabel("REQIDL", requestId);
        tm.setLabel("STATUSL", stat);
        //register table...
        RegisterTable[] rt = getCatAddTrackData(query);
        for (i = 0; i < rt.length; i++) {

          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }
        erw.setDataSource(adh);
      } catch (Exception ex) {
        ex.printStackTrace();
        //System.out.println("ERROR LOADING TEMPLATE");
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING TEMPLATE";
        sendErrorEmail(esub1, emsg1);
        return;
      }
      generateReport(nchemcurrentScreen);
    } else { /*
            Msg.append("<FONT FACE=\"Arial\">");
            Msg.append("<TABLE BORDER=\"0\">\n");
            Msg.append("<TR>\n");
            Msg.append("<TD COLSPAN=\"8\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >New Chemical Tracking Screen</FONT></B></TD>\n");
            Msg.append(getTimeDate());
            Msg.append("</TR>\n");
            Msg.append("<TR>\n");
            Msg.append("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
            Msg.append("</TR>\n");
            Msg.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Requestor: "+requestorName+"</FONT></TD></TR>\n");
            Msg.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Approver: "+approverName+"</FONT></TD></TR>\n");
            Msg.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: "+facility_name+"</FONT></TD></TR>\n");
            Msg.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >WorkArea: "+workarea_name+"</FONT></TD></TR>\n");
            Msg.append("<TR><TD COLSPAN=\"3\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Text: "+searchText+"</FONT></TD></TR>\n");
            Msg.append("<TR><TD COLSPAN=\"15\" ALIGN=\"LEFT\"><FONT size=\"4\" >Status Criteria: '"+stat+"'</FONT></TD></TR>\n");
            if ("T".equalsIgnoreCase(showAllRequests))
            {
            Msg.append("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >All Requests Needing My Approval</FONT></TD></TR>\n");
            }
            Msg.append("<TR><TD>&nbsp;</TD></TR>\n");
            Msg.append("<TR><TD>&nbsp;</TD></TR>\n");
            Msg.append("</TABLE>\n");
            Msg.append("<TABLE BORDER=\"1\">\n");
            Msg.append("<TR>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Request ID</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Requestor</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Requested</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Status</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Part No.</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Item</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Material Description</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Manufacturer</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Packaging</B></TD>\n");
            Msg.append("<TD ALIGN=\"CENTER\"><B>Mfg Part No.</B></TD>\n");
            if ("Ray".equalsIgnoreCase(db.getClient())) {
              Msg.append("<TD ALIGN=\"CENTER\"><B>Raytheon Request ID</B></TD>\n");
            }else if ("DRS".equalsIgnoreCase(db.getClient())) {
              Msg.append("<TD ALIGN=\"CENTER\"><B>DRS Request ID</B></TD>\n");
            }else if ("SWA".equalsIgnoreCase(db.getClient())) {
              Msg.append("<TD ALIGN=\"CENTER\"><B>SWA Request ID</B></TD>\n");
            }else if ("Seagate".equalsIgnoreCase(db.getClient())) {
              Msg.append("<TD ALIGN=\"CENTER\"><B>Seagate Request ID</B></TD>\n");
            }else {
              Msg.append("<TD ALIGN=\"CENTER\"><B>"+db.getClient()+" Request ID</B></TD>\n");
            }
            Msg.append("</TR>\n");
            try
            {
              NewChemTrackXlsReport NCTrackXlsReport = new NewChemTrackXlsReport();
              Msg.append(NCTrackXlsReport.createXls(query,db.getClient()));
            }
            catch (Exception e)
            {
              e.printStackTrace();
              String esub1 = "An Error Occured While Generating Report.\n";
              String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
              sendErrorEmail(esub1,emsg1);
              return;
            }
            Msg.append("</TABLE></FONT>");
            generateXlsReport(Msg.toString(),nchemcurrentScreen);*/
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
        file = "" + HomeDirectory + userId + nchemcurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        //write file
        pw.println("<FONT FACE=\"Arial\">");
        pw.println("<TABLE BORDER=\"0\">\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"8\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >New Chemical Tracking Screen</FONT></B></TD>\n");
        pw.println(getTimeDate());
        pw.println("</TR>\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
        pw.println("</TR>\n");

        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Requestor: " +
            requestorName + "</FONT></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Approver: " +
            approverName + "</FONT></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " +
            facility_name + "</FONT></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >WorkArea: " +
            workarea_name + "</FONT></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"3\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Text: " +
            searchText + "</FONT></TD></TR>\n");

        pw.println(
            "<TR><TD COLSPAN=\"15\" ALIGN=\"LEFT\"><FONT size=\"4\" >Status Criteria: '" +
            stat + "'</FONT></TD></TR>\n");

        if ("T".equalsIgnoreCase(showAllRequests)) {
          pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >All Requests Needing My Approval</FONT></TD></TR>\n");
        }

        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("</TABLE>\n");

        pw.println("<TABLE BORDER=\"1\">\n");
        pw.println("<TR>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Request ID</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Requestor</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Requested</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Status</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Work Area</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Part No.</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Item</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Material Description</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Manufacturer</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Packaging</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Mfg Part No.</B></TD>\n");
        if ("Ray".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>Raytheon Request ID</B></TD>\n");
        } else if ("DRS".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>DRS Request ID</B></TD>\n");
        } else if ("SWA".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>SWA Request ID</B></TD>\n");
        } else if ("Seagate".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>Seagate Request ID</B></TD>\n");
        } else if ("UTC".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>UTC Request ID</B></TD>\n");
        } else if ("BAE".equalsIgnoreCase(db.getClient())) {
          pw.println("<TD ALIGN=\"CENTER\"><B>BAE Request ID</B></TD>\n");
        } else {
          pw.println("<TD ALIGN=\"CENTER\"><B>" + db.getClient().toUpperCase() +
                     " Request ID</B></TD>\n");
        }
        pw.println("</TR>\n");
        try {
          DBResultSet dbrs = db.doQuery(query);
          ResultSet rs = dbrs.getResultSet();
          while (rs.next()) {
            pw.println("<TR>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID")) +
                       "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(rs.getString("NAME")) +
                       "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("REQUEST_DATE")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("REQUEST_STATUS")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("FACILITY_ID")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("application_display")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("CAT_PART_NO")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("item_id")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("MATERIAL_DESC")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("MANUFACTURER")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING")) +
                       "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(
                rs.getString("MFG_CATALOG_ID")) + "</TD>\n");
            pw.println("<TD ALIGN=\"LEFT\">" +
                       BothHelpObjs.makeSpaceFromNull(rs.getString("CUSTOMER_REQUEST_ID")) +
                       "</TD>\n");
            pw.println("</TR>\n");
          }
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }

        pw.println("</TABLE></FONT>");
        pw.close();
        generateXlsReport(tmp2, nchemcurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);
        return;
      }

     }
  }

  //trong 12-8-00
  public String getNewChemTrackQuery() { //throws IOException
    //RayTcmISDBModel db = new RayTcmISDBModel("Ray");
    String MSG = "";
    String requestor = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR"));
    String approver = BothHelpObjs.makeBlankFromNull( (String) inData.get("APPROVER"));
    String searchStringIn = BothHelpObjs.makeBlankFromNull( (String) inData.get("SEARCH_TEXT"));
    String facility = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String workArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("WORK_AREA"));
    String reqID = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUEST_ID"));
    String draftT = BothHelpObjs.makeBlankFromNull( (String) inData.get("DRAFT"));
    String newPartT = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEW_PART_NUMBER"));
    String newGroupT = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEW_GROUP"));
    String pendingApprovalT = BothHelpObjs.makeBlankFromNull( (String) inData.get("PENDING_APPROVAL"));
    String rejectT = BothHelpObjs.makeBlankFromNull( (String) inData.get("REJECT"));
    String newMaterialT = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEW_MATERIAL"));
    String newSizeT = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEW_SIZE"));
    String newApprovalT = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEW_APPROVAL"));
    String approvedT = BothHelpObjs.makeBlankFromNull( (String) inData.get("APPROVED_C"));
    String bannedT = BothHelpObjs.makeBlankFromNull( (String) inData.get("BANNED"));
    String showAllRequests = BothHelpObjs.makeBlankFromNull( (String) inData.get("SHOW_ALL_REQUESTS"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    String searchString = HelpObjs.singleQuoteToDouble(searchStringIn);
    boolean needApp = false;
    boolean newMaterial = false;
    boolean newSizePackage = false;
    boolean newPart = false;
    boolean newApproval = false;
    boolean newGroup = false;
    boolean pendAppr = false;
    boolean draft = false;
    boolean rejected = false;
    boolean approved = false;
    boolean banned = false;
    if (showAllRequests.equalsIgnoreCase("T")) {
      needApp = true;
    }
    int i = 0;

    String query = "";
    String qu = "";
    Vector wheres = new Vector();

    if (needApp) {
      pendAppr = true;
      approver = userId;
      requestor = "";
      reqID = "";
      facility = "";
      workArea = "";
      searchString = "";
      i = 1;
    } else {

      if (bannedT.equalsIgnoreCase("T")) { // banned
        banned = true;
        i = 1;
      }
      if (newApprovalT.equalsIgnoreCase("T")) { //  new Approval
        newApproval = true;
        i = 1;
      }
      if (newSizeT.equalsIgnoreCase("T")) { // new Size/Packaging
        newSizePackage = true;
        i = 1;
      }
      if (pendingApprovalT.equalsIgnoreCase("T")) { // pending approval
        pendAppr = true;
        i = 1;
      }
      if (rejectT.equalsIgnoreCase("T")) { // rejected
        rejected = true;
        i = 1;
      }
      if (approvedT.equalsIgnoreCase("T")) { // approved
        approved = true;
        i = 1;
      }
      if (newGroupT.equalsIgnoreCase("T")) { // new Group
        newGroup = true;
        i = 1;
      }
      if (newPartT.equalsIgnoreCase("T")) { // new Part
        newPart = true;
        i = 1;
      }
      if (newMaterialT.equalsIgnoreCase("T")) { // new material
        newMaterial = true;
        i = 1;
      }
      if (draftT.equalsIgnoreCase("T")) { // draft
        draft = true;
        i = 1;
      }
    }
    boolean statusSet = i > 0;

    // request ID
    if (reqID.length() > 0) {
      try {
        i = Integer.parseInt(reqID);
        wheres.addElement("request_id = '" + i + "'");
      } catch (Exception e) {
        return MSG;
      }
    }

    // requestor
    if (requestor.length() > 0 && !requestor.equalsIgnoreCase("0")) {
      try {
        i = Integer.parseInt(requestor);
        wheres.addElement("requestor = '" + i + "'");
      } catch (Exception e) {
        wheres.addElement("requestor = '" + i + "'");
      }
    }

    // facility
    if (facility.length() > 0 && !facility.equalsIgnoreCase("all")) {
      wheres.addElement("facility_id = '" + facility + "'"); //trong change
    }

    // work area
    if (workArea.length() > 0 && !workArea.equalsIgnoreCase("all")) {
      wheres.addElement("application = '" + workArea + "'");
    }

    // status
    if (statusSet) {
      Vector ss = new Vector();
      qu = "";
      //if(approver.length() > 0 && !approver.equals("0")) {
      if (approver.length() > 0 && !approver.equalsIgnoreCase("0")) {
        if (newMaterial
            || newSizePackage
            || newPart || newApproval || newGroup || draft) {
          qu = "(request_status in (";
          if (newMaterial) {
            qu = qu + "0,";
          }
          if (newSizePackage) {
            qu = qu + "1,";
          }
          if (newPart) {
            qu = qu + "2,";
          }
          if (newApproval) {
            qu = qu + "3,";
          }
          if (newGroup) {
            qu = qu + "4,";
          }
          if (draft) {
            qu = qu + "6,";
          }
          if (qu.endsWith(",")) {
            qu = qu.substring(0, qu.length() - 1);
          }
          qu = qu + ")";
          qu = qu + " and facility_id in(";
          if (BothHelpObjs.isBlankString(facility)) {
            try {
              Personnel p = new Personnel(db);
              p.setPersonnelId(new Integer(approver).intValue());
              Vector fv = p.getNewChemApprovalFacilities();
              for (int q = 0; q < fv.size(); q++) {
                qu = qu + "'" + fv.elementAt(q).toString() + "',";
              }
            } catch (Exception e1) {
              e1.printStackTrace();
              return MSG;
            }
            if (qu.endsWith(",")) {
              qu = qu.substring(0, qu.length() - 1);
            }
          } else {
            qu = qu + "'" + facility + "'";
          }
          qu = qu + "))";
          ss.addElement(qu);
        }
        qu = "";
        if (rejected || approved || banned) {
          if (rejected && approved && banned) {
            qu = "request_status in (7,8,9,10)";
          } else if (rejected) {
            qu = "request_status = 7";
          } else if (approved) {
            qu = "request_status in (8,9)";
          } else if (banned) {
            qu = "request_status = 10";
          }
          qu = qu + " and request_id in (select distinct request_id from catalog_add_approval where chemical_approver = " + approver + " and status is not null)";
          qu = "(" + qu + ")";
          ss.addElement(qu);
        }

        qu = "";
        if (pendAppr = true) {
          Vector all = null;
          Vector maybe = null;
          try {
            all = getAllApprovals(approver);
            maybe = getPotentialApprovals(approver);
          } catch (Exception e2) {
            e2.printStackTrace();
            return MSG;
          }
          if (!needApp) {
            for (int v = 0; v < all.size(); v++) {
              maybe.addElement(all.elementAt(v));
            }
          }
          String idList = "";
          for (int v = 0; v < maybe.size(); v++) {
            if (v == 0) {
              idList = maybe.elementAt(v).toString();
            } else {
              idList = idList + ", " + maybe.elementAt(v).toString();
            }
          }
          if (maybe.size() == 0) {
            // none found, so we put in some fake data
            idList = "99999999";
          }

          qu = "(request_status = 5 and request_id in (" + idList + "))";
          ss.addElement(qu);
        }

        // put together the status criteria
        String tmp = "";
        if (ss.size() == 1) {
          tmp = ss.elementAt(0).toString();
        } else {
          for (int io = 0; io < ss.size(); io++) {
            if (io == 0) {
              tmp = ss.elementAt(io).toString();
            } else {
              tmp = tmp + " or " + ss.elementAt(io).toString();
            }
          }
          tmp = "(" + tmp + ")";
        }
        wheres.addElement(tmp);
      } else {
        qu = "";
        qu = "request_status in (";
        if (newMaterial) {
          qu = qu + "0,";
        }
        if (newSizePackage) {
          qu = qu + "1,";
        }
        if (newPart) {
          qu = qu + "2,";
        }
        if (newApproval) {
          qu = qu + "3,";
        }
        if (newGroup) {
          qu = qu + "4,";
        }
        if (pendAppr) {
          qu = qu + "5,";
        }
        if (draft) {
          qu = qu + "6,";
        }
        if (rejected) {
          qu = qu + "7,";
        }
        if (approved) {
          qu = qu + "8,9,";
        }
        if (banned) {
          qu = qu + "10,";
        }
        if (qu.endsWith(",")) {
          qu = qu.substring(0, qu.length() - 1);
        }
        qu = qu + ")";
        wheres.addElement(qu);
      }
    }

    qu = "";
    if (searchString.length() > 0) {
      qu = qu + "(lower(material_desc) like lower('%" + searchString + "%') or ";
      qu = qu + "lower(manufacturer) like lower('%" + searchString + "%') or ";
      qu = qu + "lower(mfg_catalog_id) like lower('%" + searchString + "%') or ";
      qu = qu + "lower(cat_part_no) like lower('%" + searchString + "%')) "; //trong 6/5/00
      wheres.addElement(qu);
    }

    String where = "";
    if (wheres.size() == 1) {
      where = wheres.elementAt(0).toString();
    } else {
      for (int io = 0; io < wheres.size(); io++) {
        if (io == 0) {
          where = wheres.elementAt(io).toString();
        } else {
          where = where + " and " + wheres.elementAt(io).toString();
        }
      }
    }

    query = "select * from new_chem_tracking_view ";
    query = query + "where " + where + " ";
    query = query + "order by request_id, part_id";

    return query;
  }

  /** returns a vector of distinct request_ids for all request that the approver has
       approved (or rejected) regardless of status or facility*/
  public Vector getAllApprovals(String approver) throws Exception {
    //RayTcmISDBModel db = null;
    Vector v = new Vector();
    String myQuery = "select request_id ";
    myQuery = myQuery + "from catalog_add_request_view_new  ";
    myQuery = myQuery + "where chemical_approver = " + approver + " ";
    myQuery = myQuery + "and status is not null ";
    myQuery = myQuery + "order by request_id";

    Hashtable h = new Hashtable();
    //db = new RayTcmISDBModel("Ray");
    ResultSet rs = null;
    DBResultSet dbrs = null;

    try {
      dbrs = db.doQuery(myQuery);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        h.put(rs.getString(1), "1");
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
    for (Enumeration E = h.keys(); E.hasMoreElements(); ) {
      v.addElement( (String) E.nextElement());
    }
    return v;
  }

  /** returns a vector of distinct request_ids for all request that the approver can
       approve and haven't been approved by someone else and status = 5, regardless of facility*/
  public Vector getPotentialApprovals(String approver) throws Exception {
    //RayTcmISDBModel db = null;
    Vector v = new Vector();
    String myQuery = "select v.request_id, v.approval_role, v.chemical_approver ";
    myQuery = myQuery + "from catalog_add_request_view_new v, catalog_add_request_new r ";
    myQuery = myQuery + "where r.request_status = 5 ";
    myQuery = myQuery + "and v.chemical_approver = " + approver + " ";
    myQuery = myQuery + "and v.status is null ";
    myQuery = myQuery + "and r.request_id = v.request_id ";
    myQuery = myQuery + "order by request_id, approval_role ";

    Hashtable h = new Hashtable();
    // db = new RayTcmISDBModel("Ray");
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(myQuery);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String myReqId = rs.getString(1);
        String myAppRole = rs.getString(2);
        String myWhere = "where approval_date is not null and lower(status) in ('approved','rejected') and ";
        myWhere = myWhere + " request_id = '" + myReqId + "' and approval_role = '" + myAppRole + "'";
        if (DbHelpers.countQuery(db,
                                 "catalog_add_request_view_new",
                                 myWhere) < 1) {
          h.put(myReqId, "1");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
    for (Enumeration E = h.keys(); E.hasMoreElements(); ) {
      v.addElement( (String) E.nextElement());
    }
    return v;
  }

  void buildCatalogPDF(Hashtable h, String facName, String workAreaName) {
    Boolean allFac = (Boolean) h.get("ALL_CATALOG");
    Boolean approved = (Boolean) h.get("APPROVED");
    String searchText = (String) h.get("SEARCH_TEXT");
    Boolean active = (Boolean) h.get("ACTIVE");

    try {
      erw.readTemplate(templDirectory + "CatalogNew.erw");
    } catch (Exception ex) {
      ex.printStackTrace();
      String esub1 = "An Error Occured While Loading report template.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Loading report template.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
    //setting the where clause
    tm = erw.getTemplateManager();

    if (allFac.booleanValue()) {
      tm.setLabel("FACL", "All");
      tm.setLabel("APPL", "All");
      tm.setLabel("APPROVEL", "");
    } else {
      tm.setLabel("FACL", facName);
      tm.setLabel("APPL", workAreaName);
      if (approved.booleanValue()) {
        tm.setLabel("APPROVEL", "-Only Approved Items for This Facility/Work Area");
      }
    }

    tm.setLabel("SEARCHL", "All Items Containing '" + searchText + "'");
    if (active.booleanValue() && allFac.booleanValue()) {
      tm.setLabel("ACTIVE", "-Active Items For All Catalogs");
    } else if (! (allFac.booleanValue())) {
      tm.setLabel("ACTIVE", "-Active For Selected Facility/Work Area");
    } else {
      tm.setLabel("ACTIVE", "");
    }

    try {
      CatalogXlsReport CatXlsReport = new CatalogXlsReport();
      Vector catData = CatXlsReport.getReportData(h, db.getClient(), false);
      //register table...
      AppDataHandler adh = new AppDataHandler();
      RegisterTable[] rt = getCatSearchData(catData);
      for (int i = 0; i < rt.length; i++) {
        adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
      }
      erw.setDataSource(adh);
    } catch (Exception ex1) {
      ex1.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
  } //end of method

  void buildItemCatalogPDF(Hashtable h, String facName, String workAreaName) {
    Boolean previousOrder = (Boolean) h.get("ALL_CATALOG");
    Boolean requestorSelected = (Boolean) h.get("REQUESTOR_SELECTED");
    Boolean facilitySelected = (Boolean) h.get("APPROVED");
    Boolean workAreaSelected = (Boolean) h.get("ACTIVE");
    String searchText = (String) h.get("SEARCH_TEXT");
    h.put("PREVIOUS_ORDER", previousOrder);
    h.put("EVAL_REQUESTOR", requestorSelected);
    h.put("EVAL_FACILITY", facilitySelected);
    h.put("EVAL_WORK_AREA", workAreaSelected);
    h.put("WORK_AREA_ID", (String) h.get("WORK_AREA"));
    try {
      erw.readTemplate(templDirectory + "ItemCatalog.erw");
    } catch (Exception ex) {
      ex.printStackTrace();
      String esub1 = "An Error Occured While Loading report template.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Loading report template.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
    //setting the where clause
    tm = erw.getTemplateManager();
    tm.setLabel("FACL", facName);
    tm.setLabel("APPL", workAreaName);
    tm.setLabel("SEARCHL", "All Items Containing '" + searchText + "'");
    if (previousOrder.booleanValue()) {
      String msg = "- Only Previous Eval Order for: ";
      if (requestorSelected.booleanValue()) {
        try {
          Personnel p = new Personnel(db);
          p.setPersonnelId( ( (Integer) h.get("USER_ID")).intValue());
          p.load();
          msg += p.getLastName() + ", " + p.getFirstName() + " : ";
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (facilitySelected.booleanValue()) {
        msg += facName + " : ";
      }
      if (workAreaSelected.booleanValue()) {
        msg += workAreaName;
      }
      tm.setLabel("APPROVEL", msg);
    }
    try {
      ItemCatalog ic = new ItemCatalog(db);
      Hashtable dataH = ic.doSearch(h);
      dataH.remove("ITEM_LIST");
      dataH.remove("ROW_COUNT");
      //register table...
      AppDataHandler adh = new AppDataHandler();
      RegisterTable[] rt = new RegisterTable[1];
      rt[0] = new RegisterTable(ItemCatalogData.getVector(dataH), "ITEMCATALOGDATA", ItemCatalogData.getFieldVector(), null);
      for (int i = 0; i < rt.length; i++) {
        adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
      }
      erw.setDataSource(adh);
    } catch (Exception ex1) {
      ex1.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
  } //end of method

  public void buildCatalogXLS(PrintWriter pw, Hashtable h, String facility_name, String workarea_name) {
    Boolean allFac = (Boolean) h.get("ALL_CATALOG");
    Boolean approved = (Boolean) h.get("APPROVED");
    String searchText = (String) h.get("SEARCH_TEXT");
    Boolean active = (Boolean) h.get("ACTIVE");
    boolean showLeadTime = false;
    boolean showBaseLine = false;
    //
    try {
      showLeadTime = HelpObjs.countQuery(db,"Select count(*) from tcmis_feature where feature = 'main.rightMouseClick.displayLeadTimeChart' and feature_mode = '1'") > 0;
      showBaseLine = HelpObjs.countQuery(db,"Select count(*) from tcmis_feature where feature = 'main.rightMouseClick.displayBaselineDate' and feature_mode = '1'") > 0;
    }catch (Exception ee) {
      showLeadTime = false;
      showBaseLine = false;
    }

    //write file
    pw.println("<FONT FACE=\"Arial\">");
    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >Catalog Search Screen</FONT></B></TD>\n");
    pw.println(getTimeDate());
    pw.println("</TR>\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
    pw.println("</TR>\n");
    if (allFac.booleanValue()) {
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: All</FONT></TD></TR>\n");
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: All</FONT></TD></TR>\n");
    } else {
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " + facility_name + "</FONT></TD></TR>\n");
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: " + workarea_name + "</FONT></TD></TR>\n");
    }
    pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Text: " + searchText + "</FONT></TD></TR>\n");

    if ( (approved.booleanValue()) && ! (allFac.booleanValue())) {
      pw.println("<TR><TD COLSPAN=\"8\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Approved : Only Approved Items for This Facility/Work Area</FONT></TD></TR>\n");
    }

    if (active.booleanValue() && allFac.booleanValue()) {
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Active : Active Items For All Catalogs</FONT></TD></TR>\n");
    } else if (! (allFac.booleanValue())) {
      pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Active : Active For Selected Facility/Work Area</FONT></TD></TR>\n");
    }

    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"1\">\n");
    pw.println("<TR>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>CATALOG ID</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>PRICE GROUP</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>CAT PART NO</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>PART DESCRIPTION</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>SPECIFICATION</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>STOCKING METHOD</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>SHELF LIFE</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>CATALOG PRICE</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>CURRENCY</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>APPROVAL STATUS</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>LIMIT 1</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>LIMIT 2</B></TD>\n");
    if (showBaseLine) {
      pw.println("<TD ALIGN=\"CENTER\"><B>BASELINE RESET</B></TD>\n");
      pw.println("<TD ALIGN=\"CENTER\"><B>BASELINE EXPIRATION</B></TD>\n");
    }
    pw.println("<TD ALIGN=\"CENTER\"><B>ORDER QUANTITY</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>ORDER QUANTITY RULE</B></TD>\n");
    if (showLeadTime) {
      pw.println("<TD ALIGN=\"CENTER\"><B>MEDIAN MR LEAD TIME (days)</B></TD>\n");
      pw.println("<TD ALIGN=\"CENTER\"><B>MEDIAN SUPPLY LEAD TIME (days)</B></TD>\n");
    }
    pw.println("<TD ALIGN=\"CENTER\"><B>BUNDLE</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>ITEM ID</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>COMPONENT #</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>MATERIAL DESC</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>PACKAGING</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>GRADE</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>MFG DESC</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>MFG PART NO</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>MATERIAL ID</B></TD>\n");
    pw.println("</TR>\n");
    Vector v = null;
    try {
      CatalogXlsReport CatXlsReport = new CatalogXlsReport();
      v = CatXlsReport.getReportData(h, db.getClient(), true);
    } catch (Exception e) {
      e.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
    for (int j = 0; j < v.size(); j++) {
      String Color = " ";

      if (j % 2 == 0) {
        Color = "bgcolor=\"#dddddd\"";
      } else {
        Color = "bgcolor=\"#fcfcfc\"";
      }
      Hashtable hashTable = (Hashtable) v.elementAt(j);

      pw.println("<TR>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("CATALOG_ID") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("PRICE_GROUP") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("CAT_PART_NO") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("PART_DESCRIPTION") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("SPECS") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("STOCKING_METHOD") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("SHELF_LIFE") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("CATALOG_PRICE") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("CURRENCY") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("APPROVAL_STATUS") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("LIMIT_QUANTITY_PERIOD1") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("LIMIT_QUANTITY_PERIOD2") + "</TD>\n");
      if (showBaseLine) {
        pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                   hashTable.get("BASE_LINE_RESET") + "</TD>\n");
        pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                   hashTable.get("BASE_LINE_EXPIRATION") + "</TD>\n");
      }
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("ORDER_QUANTITY") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("ORDER_QUANTITY_RULE") + "</TD>\n");
      if (showLeadTime) {
        pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                   hashTable.get("MEDIAN_MR_LEAD_TIME") + "</TD>\n");
        pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                   hashTable.get("MEDIAN_SUPPLY_LEAD_TIME") + "</TD>\n");
      }
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("BUNDLE") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("ITEM_ID") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("PART_ID") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("MATERIAL_DESC") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("PACKAGING") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("GRADE") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("MFG_DESC") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("MFG_PART_NO") + "</TD>\n");
      pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                 hashTable.get("MATERIAL_ID") + "</TD>\n");
      pw.println("</TR>\n");
    }
    pw.println("</TABLE></FONT>");
  } //end of method

  public void buildItemCatalogXLS(PrintWriter pw, Hashtable h, String facility_name, String workarea_name) {
    Boolean previousOrder = (Boolean) h.get("ALL_CATALOG");
    Boolean requestorSelected = (Boolean) h.get("REQUESTOR_SELECTED");
    Boolean facilitySelected = (Boolean) h.get("APPROVED");
    Boolean workAreaSelected = (Boolean) h.get("ACTIVE");
    String searchText = (String) h.get("SEARCH_TEXT");
    h.put("PREVIOUS_ORDER", previousOrder);
    h.put("EVAL_REQUESTOR", requestorSelected);
    h.put("EVAL_FACILITY", facilitySelected);
    h.put("EVAL_WORK_AREA", workAreaSelected);
    h.put("WORK_AREA_ID", (String) h.get("WORK_AREA"));

    //write file
    pw.println("<FONT FACE=\"Arial\">");
    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >Item Catalog Search Screen</FONT></B></TD>\n");
    pw.println(getTimeDate());
    pw.println("</TR>\n");
    pw.println("<TR>\n");
    pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
    pw.println("</TR>\n");
    pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " + facility_name + "</FONT></TD></TR>\n");
    pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Work Area: " + workarea_name + "</FONT></TD></TR>\n");
    pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Text: " + searchText + "</FONT></TD></TR>\n");
    if (previousOrder.booleanValue()) {
      String msg = "- Only Previous Eval Order for: ";
      if (requestorSelected.booleanValue()) {
        try {
          Personnel p = new Personnel(db);
          p.setPersonnelId( ( (Integer) h.get("USER_ID")).intValue());
          p.load();
          msg += p.getLastName() + ", " + p.getFirstName() + " : ";
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (facilitySelected.booleanValue()) {
        msg += facility_name + " : ";
      }
      if (workAreaSelected.booleanValue()) {
        msg += workarea_name;
      }
      pw.println("<TR><TD COLSPAN=\"8\" ALIGN=\"LEFT\" ><FONT size=\"4\" >" + msg + "</FONT></TD></TR>\n");
    }
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"1\">\n");
    pw.println("<TR>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Item</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Component Description</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Grade</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Packaging</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Manufacturer</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Country</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Mfg Part No.</B></TD>\n");
    pw.println("<TD ALIGN=\"CENTER\"><B>Mfg Shelf Life @ Storage Temp</B></TD>\n");
    pw.println("</TR>\n");
    try {
      ItemCatalog ic = new ItemCatalog(db);
      Hashtable dataH = ic.doSearch(h);
      dataH.remove("ITEM_LIST");
      dataH.remove("ROW_COUNT");
      if (dataH.isEmpty()) {
        String color = "bgcolor=\"#fcfcfc\"";
        pw.println("<TR>\n");
        pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">No Record Found</TD>\n");
        pw.println("</TR>\n");
      } else {
        Enumeration enuma = dataH.keys();
        int j = 0;
        while (enuma.hasMoreElements()) {
          String color = "";
          if (j % 2 == 0) {
            color = "bgcolor=\"#dddddd\"";
          } else {
            color = "bgcolor=\"#fcfcfc\"";
          }
          pw.println("<TR>\n");
          String key = enuma.nextElement().toString();
          Vector v = (Vector) dataH.get(key);
          for (int i = 0; i < v.size(); i++) {
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + key + "</TD>\n");
            String[] data = (String[]) v.elementAt(i);
            int count = 2;
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("<TD " + color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" + data[count++] + "</TD>\n");
            pw.println("</TR>\n");
          }
          j++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data.";
      sendErrorEmail(esub1, emsg1);
      return;
    }
    pw.println("</TABLE></FONT>");
  } //end of method

  public void buildCatalogScreen() {
    String bcatcurrentScreen = (String) inData.get("CURRENT_SCREEN");
    String facilityId = (String) inData.get("FACILITY_ID");
    String workArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("WORK_AREA"));
    String searchText = BothHelpObjs.makeBlankFromNull( (String) inData.get("SEARCH_TEXT"));
    userId = (String) inData.get("USER_ID");
    String requestorSelected = (String) inData.get("ENG_EVAL_CAT");
    String allFac = (String) inData.get("ALL_FAC");
    fileType = (String) inData.get("FILE_TYPE");
    String active = (String) inData.get("ACTIVE");
    String approved = (String) inData.get("APPROVED");
    String facility_name = (String) inData.get("FACILITY_NAME");
    String workarea_name = (String) inData.get("WORK_AREA_NAME");
    if ("Select a Work Area".equalsIgnoreCase(workarea_name)) {
      workarea_name = "";
    }
    String catalogType = (String) inData.get("CATALOG_TYPE");
    StringBuffer Msg = new StringBuffer();

    Hashtable h = new Hashtable();
    h.put("FACILITY_ID", facilityId);
    h.put("WORK_AREA", workArea);
    h.put("SEARCH_TEXT", searchText);
    h.put("USER_ID", new Integer(userId));
    h.put("CATALOG_TYPE", catalogType);
    h.put("ACTIVE_SELECTED", new Boolean("F".equalsIgnoreCase(allFac)));
    h.put("APPROVED", new Boolean("T".equalsIgnoreCase(approved)));
    h.put("ALL_CATALOG", new Boolean("T".equalsIgnoreCase(allFac)));
    h.put("ACTIVE", new Boolean("T".equalsIgnoreCase(active)));
    h.put("REQUESTOR_SELECTED", new Boolean("T".equalsIgnoreCase(requestorSelected)));

    if (fileType.equalsIgnoreCase("PDF")) {
      if ("Item Catalog".equalsIgnoreCase(catalogType)) {
        buildItemCatalogPDF(h, facility_name, workarea_name);
      } else {
        buildCatalogPDF(h, facility_name, workarea_name);
      }

      generateReport(bcatcurrentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
        file = "" + HomeDirectory + userId + bcatcurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        if ("Item Catalog".equalsIgnoreCase(catalogType)) {
          buildItemCatalogXLS(pw, h, facility_name, workarea_name);
        } else {
          buildCatalogXLS(pw, h, facility_name, workarea_name);
        }
        pw.close();
        generateXlsReport(tmp2, bcatcurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);
        return;
      }
    }
  } //end of method

  public void buildOrderTrackingScreen() {
    String otrcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    //String requestor = BothHelpObjs.makeBlankFromNull((String)inData.get("REQUESTOR"));
    String requestorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR_NAME"));
    //String facId = BothHelpObjs.makeBlankFromNull((String)inData.get("FACILITY"));
    //String workArea = BothHelpObjs.makeBlankFromNull((String)inData.get("WORK_AREA"));
    //String searchType = BothHelpObjs.makeBlankFromNull((String)inData.get("SEARCH_TYPE"));
    //String searchContent = BothHelpObjs.makeBlankFromNull((String)inData.get("SEARCH_CONTENT"));
    String searchText = BothHelpObjs.makeBlankFromNull( (String) inData.get("SEARCH_TEXT"));
    String needMyApproval = BothHelpObjs.makeBlankFromNull( (String) inData.get("NEED_MY_APPROVAL"));
    String status = "";
    if ("T".equalsIgnoreCase(needMyApproval)) {
      inData.put("NEED_MY_APPROVAL", new Boolean(true));
      status = "Orders need my approval";
    } else {
      inData.put("NEED_MY_APPROVAL", new Boolean(false));
    }
    String critical = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_CRIT_ONLY"));
    if ("T".equalsIgnoreCase(critical)) {
      inData.put("STATUS_CRIT_ONLY", new Boolean(true));
    } else {
      inData.put("STATUS_CRIT_ONLY", new Boolean(false));
    }
    String any = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_ANY"));
    if ("T".equalsIgnoreCase(any)) {
      inData.put("STATUS_ANY", new Boolean(true));
      status = "Any";
    } else {
      inData.put("STATUS_ANY", new Boolean(false));
    }
    String draft = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_DRAFT"));
    if ("T".equalsIgnoreCase(draft)) {
      inData.put("STATUS_DRAFT", new Boolean(true));
      status = "Draft";
    } else {
      inData.put("STATUS_DRAFT", new Boolean(false));
    }
    String open = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_OPEN")); //Open
    if ("T".equalsIgnoreCase(open)) {
      inData.put("STATUS_OPEN", new Boolean(true));
      status = "Open";
    } else {
      inData.put("STATUS_OPEN", new Boolean(false));
    }
    String archived = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_ARCHIVED"));
    String days = "";
    if ("T".equalsIgnoreCase(archived)) {
      inData.put("STATUS_ARCHIVED", new Boolean(true));
      days = BothHelpObjs.makeBlankFromNull( (String) inData.get("DAYS"));
      status = "Fully Delivered last " + days + " days";
    } else {
      inData.put("STATUS_ARCHIVED", new Boolean(false));
    }
    String cancel = BothHelpObjs.makeBlankFromNull( (String) inData.get("STATUS_CANCELLED"));
    if ("T".equalsIgnoreCase(cancel)) {
      inData.put("STATUS_CANCELLED", new Boolean(true));
      status = "Cancelled/Rejected";
    } else {
      inData.put("STATUS_CANCELLED", new Boolean(false));
    }

    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    String facility_name = (String) inData.get("FACILITY_NAME");
    String workarea_name = (String) inData.get("WORK_AREA_NAME");

    StringBuffer Msg = new StringBuffer();

    Vector dataV = null;
    try {
      TrackView tv = new TrackView(db);
      dataV = tv.getPrintScreenData(inData);
      /*
                       if (fileType.equalsIgnoreCase("PDF")) {
         query1 = tv.buildQuery(inData);
                       }else {
         dataV = tv.getPrintScreenData(inData);
                       }*/
    } catch (Exception e) {
      e.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.";
      sendErrorEmail(esub1, emsg1);
      return;
    }

    if (fileType.equalsIgnoreCase("PDF")) {
      try {
        erw.readTemplate("" + templDirectory + "OrderTrack.erw"); //Query the result directly from database

        tm = erw.getTemplateManager();
        tm.setLabel("REQL", requestorName);
        if ("T".equalsIgnoreCase(needMyApproval)) {
          tm.setLabel("RECT001", " X");
        }
        if ("T".equalsIgnoreCase(critical)) {
          tm.setLabel("RECT000", " X");
        }
        tm.setLabel("STATUSL", status);
        tm.setLabel("FACL", facility_name);
        tm.setLabel("WAL", workarea_name);
        if (!BothHelpObjs.isBlankString( (String) inData.get("SEARCH_TEXT"))) {
          tm.setLabel("SEARCHL", (String) inData.get("SEARCH_TYPE") + " " + (String) inData.get("SEARCH_CONTENT") + " " + (String) inData.get("SEARCH_TEXT"));
        }

        //register table...
        RegisterTable[] rt = getOrderTrackData(dataV);
        for (int i = 0; i < rt.length; i++) {

          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }
        erw.setDataSource(adh);

      } catch (Exception ex) {
        //System.out.println("ERROR LOADING TEMPLATE");
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);
        return;
      }
      generateReport(otrcurrentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
        file = "" + HomeDirectory + userId + otrcurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        //write file
        pw.println("<FONT FACE=\"Arial\">");
        pw.println("<TABLE BORDER=\"0\">\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"8\" ALIGN=\"CENTER\" ><B><FONT size=\"3\" >Chemical Order Tracking Screen</FONT></B></TD>\n");
        pw.println(getTimeDate());
        pw.println("</TR>\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"2\" >Selection Criterion</FONT></B></TD>\n");
        pw.println("</TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><B><FONT size=\"2\" >Requestor: " +
            requestorName + "</FONT></B></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><B><FONT size=\"2\" >Facility: " +
            facility_name + "</FONT></B></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><B><FONT size=\"2\" >WorkArea: " +
            workarea_name + "</FONT></B></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"3\" ALIGN=\"LEFT\"><B><FONT size=\"2\" >Search Text: " +
            searchText + "</FONT></B></TD>\n");
        pw.println(
            "<TR><TD COLSPAN=\"15\" ALIGN=\"LEFT\"><B><FONT size=\"2\" >Status Criteria: '" +
            status + "'</FONT></B></TD></TR>\n");
        if ("T".equalsIgnoreCase(needMyApproval)) {
          pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"2\" >Orders Needing My Approval</FONT></TD></TR>\n");
        }
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("</TABLE>\n");

        pw.println("<TABLE BORDER=\"1\">\n");
        pw.println("<TR>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Status</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>MR Line</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>PO</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Critical</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Released</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Facility</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Workarea</B></TD>\n");
		  pw.println("<TD ALIGN=\"CENTER\"><B>Requester</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Part Num.</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Part Desc</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Type</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Needed</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Delivered</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>REF</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>(Allocated) Qty</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>(Projected) Delivery</B></TD>\n");
		  pw.println("<TD ALIGN=\"CENTER\"><B>Ship To</B></TD>\n");
		  pw.println("<TD ALIGN=\"CENTER\"><B>Deliver To</B></TD>\n");
		  pw.println("<TD ALIGN=\"CENTER\"><B>Notes</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Item</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Approver</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Catalog</B></TD>\n");
        pw.println("</TR>\n");
        //loop thru data vector and print data
        for (int i = 0; i < dataV.size(); i++) {
          HashMap h = (HashMap) dataV.elementAt(i);
          pw.println("<TR>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_0"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_1"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_2"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_3"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_4"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_5"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_6"));
          pw.println("</TD>\n");
			 pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_7"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_8"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_9"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_10"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_11"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_12"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_13"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_14"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_15"));
          pw.println("</TD>\n");
			 pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_20"));
          pw.println("</TD>\n");
			 pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_21"));
          pw.println("</TD>\n");
			 pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_16"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_17"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_18"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println(h.get("DETAIL_19"));
          pw.println("</TD>\n");
          pw.println("</TR>\n");
        }
        pw.println("</TABLE></FONT>");
        pw.close();
        generateXlsReport(tmp2, otrcurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);
        return;
      }

     } //end of CVS
  }

  public void buildInventoryScreen() {
    String bincurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String wareHouse = BothHelpObjs.makeBlankFromNull( (String) inData.get("WAREHOUSE"));
    String searchText = BothHelpObjs.makeBlankFromNull( (String) inData.get("ITEM"));
    String facility_name = (String) inData.get("FACILITY_NAME");
    String workarea_name = (String) inData.get("WORK_AREA_NAME");
    String expDate = BothHelpObjs.makeBlankFromNull( (String) inData.get("EXP_VAL"));
    String exp2Date = BothHelpObjs.makeBlankFromNull( (String) inData.get("EXP2_VAL"));
    String byDate = BothHelpObjs.makeBlankFromNull( (String) inData.get("PROM_VAL"));
    String userFac = (String) inData.get("USER_FAC");
    //String fac_bol = BothHelpObjs.makeBlankFromNull((String)inData.get("FAC_BOL"));
    //String ware_bol = BothHelpObjs.makeBlankFromNull((String)inData.get("WARE_BOL"));
    //String exp_bol = BothHelpObjs.makeBlankFromNull((String)inData.get("EXP_BOL"));
    //String prom_bol = BothHelpObjs.makeBlankFromNull((String)inData.get("PROM_BOL"));
    String oH_bol = BothHelpObjs.makeBlankFromNull( (String) inData.get("ON_HOLD_BOL"));
    String oO_bol = BothHelpObjs.makeBlankFromNull( (String) inData.get("ON_ORDER_BOL"));
    //String allParts = BothHelpObjs.makeBlankFromNull((String)inData.get("ALL_PARTS"));
    //String order_byin = "ITEM";

    StringBuffer Msg = new StringBuffer();
    Hashtable h = new Hashtable(11);
    h.put("USER_ID", userId);
    h.put("FACILITY", facilityId);
    h.put("USER_FAC", userFac);
    h.put("WAREHOUSE", wareHouse);
    h.put("ITEM", searchText);
    h.put("EXP_VAL", expDate);
    h.put("EXP2_VAL", exp2Date);
    h.put("PROM_VAL", byDate);
    h.put("ON_HOLD_BOL", new Boolean("T".equalsIgnoreCase(oH_bol)));
    h.put("ON_ORDER_BOL", new Boolean("T".equalsIgnoreCase(oO_bol)));

    Vector tableData = null;

    try {
      InventoryView InvenView = new InventoryView(db);
      tableData = InvenView.buildQueryData(h);
    } catch (Exception e) {
      e.printStackTrace();
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.";
      sendErrorEmail(esub1, emsg1);
      return;
    }

    String searchCriteria = " ";

    if (!BothHelpObjs.isBlankString(searchText) && searchText != null) {
      searchCriteria += "Search text '" + searchText + "', ";
    }
    if (!BothHelpObjs.isBlankString(expDate) && expDate != null) {
      searchCriteria += "Expire within '" + expDate + "', ";
    }
    if (!BothHelpObjs.isBlankString(exp2Date) && expDate != null) {
      searchCriteria += "Expire after '" + exp2Date + "', ";
    }
    if (!BothHelpObjs.isBlankString(byDate) && byDate != null) {
      searchCriteria += "Arrive within '" + byDate + "', ";
    }

    if (fileType.equalsIgnoreCase("PDF")) {
      try {
        erw.readTemplate("" + templDirectory + "Inventory.erw"); //Query the result directly from database
        tm = erw.getTemplateManager();
        tm.setLabel("SEARCHL", searchText);
        tm.setLabel("FACL", facility_name);
        tm.setLabel("INVENL", workarea_name);
        if ("T".equalsIgnoreCase(oH_bol)) {
          tm.setLabel("RECT001", " X");
        }
        tm.setLabel("WITHINL", expDate + " days");
        tm.setLabel("AFTERL", exp2Date + " days");
        if ("T".equalsIgnoreCase(oO_bol)) {
          tm.setLabel("RECT000", " X");
        }
        tm.setLabel("ARRIVEL", byDate + " days");

        //register table...
        RegisterTable[] rt = getInventoryData(tableData);
        for (int i = 0; i < rt.length; i++) {

          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }
        erw.setDataSource(adh);
      } catch (Exception ex) {
        //System.out.println("ERROR LOADING INVENTORY TEMPLATE");
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING TEMPLATE";
        sendErrorEmail(esub1, emsg1);
        return;
      }
      generateReport(bincurrentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
        file = "" + HomeDirectory + userId + bincurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        //write file
        pw.println("<FONT FACE=\"Arial\">");
        pw.println("<TABLE BORDER=\"0\">\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >Inventory Screen</FONT></B></TD>\n");
        pw.println(getTimeDate());
        pw.println("</TR>\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
        pw.println("</TR>\n");

        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " +
            facility_name + "</FONT></TD></TR>\n");
        pw.println(
            "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"4\" >Warehouse: " +
            workarea_name + "</FONT></TD></TR>\n");

        pw.println(
            "<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Criteria: " +
            searchCriteria + "</FONT></TD></TR>\n");
        pw.println("</TR>\n");

        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("</TABLE>\n");

        pw.println("<TABLE BORDER=\"1\">\n");
        pw.println("<TR>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Catalog</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Part</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Part Description</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Type</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Reorder Point</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Stocking Level</B></TD>\n");
        //pw.println("<TD ALIGN=\"CENTER\"><B>Shelf Life @ Storage Temp</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B># per Part</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Item</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Available</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Held</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>On Order</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>In Purchasing</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Component Description</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Packaging</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Manufacturer</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>Mfg Part No.</B></TD>\n");
        pw.println("</TR>\n");
        for (int i = 0; i < tableData.size(); i++) {
          HashMap data = (HashMap) tableData.elementAt(i);
          pw.println("<TR>\n");
          int j = 0;
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_0"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_1"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_2"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_4"));
          pw.println("</TD>\n");

          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_15"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_16"));
          pw.println("</TD>\n");
          //pw.println("<TD ALIGN=\"LEFT\">");
          //pw.println( (String) data.get("DETAIL_17"));
          //pw.println("</TD>\n");

          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_5"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_6"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_7"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_8"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_9"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_10"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_11"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_12"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_13"));
          pw.println("</TD>\n");
          pw.println("<TD ALIGN=\"LEFT\">");
          pw.println( (String) data.get("DETAIL_14"));
          pw.println("</TD>\n");
          pw.println("</TR>\n");
        }
        pw.println("</TABLE></FONT>");
        pw.close();
        generateXlsReport(tmp2, bincurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);

        return;
      }

    }
  }

  public void buildWasteCatalogScreen() {
    String wccurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String requestorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR_NAME"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String generationPt = BothHelpObjs.makeBlankFromNull( (String) inData.get("GENERATION_PT"));
    String searchText = BothHelpObjs.makeBlankFromNull( (String) inData.get("SEARCH_TEXT"));
    String previousTransfer = BothHelpObjs.makeBlankFromNull( (String) inData.get("PREVIOUS_TRANSFER"));
    Boolean previousT = new Boolean("T".equalsIgnoreCase(previousTransfer));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    if ("PDF".equalsIgnoreCase(fileType)) {
      try {
        erw.readTemplate("" + templDirectory + "WasteCatalogSearch.erw"); //Query the result directly from database
      } catch (Exception ex) {
        //System.out.println("ERROR LOADING WASTE CATALOG TEMPLATE");
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING WASTE CATALOG TEMPLATE";
        sendErrorEmail(esub1, emsg1);
        return;
      }
      tm = erw.getTemplateManager();
      tm.setLabel("LAB006", requestorName);
      tm.setLabel("LAB007", facilityId);
      tm.setLabel("LAB008", generationPt);
      tm.setLabel("LAB009", "All Items Containing '" + searchText + "'");
      if (previousTransfer.equalsIgnoreCase("T")) {
        tm.setLabel("PREL", "X");
      } else {
        tm.setLabel("PREL", "");
      }
      //set resource data
      try {
        Hashtable reportData = WasteProfile.doSearch(db, previousT, facilityId, generationPt, searchText, false);
        Vector dataV = (Vector) reportData.get("TABLE_DATA");
        //register table...
        AppDataHandler adh = new AppDataHandler();
        RegisterTable[] rt = getWasteCatalogData(dataV);
        for (int i = 0; i < rt.length; i++) {
          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
        }
        erw.setDataSource(adh);
      } catch (Exception ex1) {
        ex1.printStackTrace();
        return;
      }
      generateReport(wccurrentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
        file = "" + HomeDirectory + userId + wccurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        //write file
        pw.println("<FONT FACE=\"Arial\">");
        pw.println("<TABLE BORDER=\"0\">\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"5\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >Waste Catalog Search Screen</FONT></B></TD>\n");
        pw.println(getTimeDate());
        pw.println("</TR>\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"3\" ALIGN=\"LEFT\" ><B><FONT size=\"3\" >Selection Criterion</FONT></B></TD>\n");
        pw.println("</TR>\n");
        pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Facility: " + facilityId + "</FONT></TD></TR>\n");
        pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\" ><FONT size=\"4\" >Generation Pt: " + generationPt + "</FONT></TD></TR>\n");
        pw.println("<TR><TD COLSPAN=\"5\" ALIGN=\"LEFT\"><FONT size=\"4\" >Search Text: " + searchText + "</FONT></TD></TR>\n");

        if ("T".equalsIgnoreCase(previousTransfer)) {
          pw.println("<TR><TD COLSPAN=\"8\" ALIGN=\"LEFT\" ><FONT size=\"4\" >-Previous Transfer Only</FONT></TD></TR>\n");
        }
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("</TABLE>\n");

        pw.println("<TABLE BORDER=\"1\">\n");
        pw.println("<TR>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>STATE WASTE CODE</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>DESCRIPTION</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>PACKAGING</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>VENDOR PROFILE</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>VENDOR</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>WASTE CATEGORY</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>WASTE TYPE</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>WASTE MGMT OPT</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>HAAS PROFILE</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>FACILITY</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>LPP</B></TD>\n");
        pw.println("<TD ALIGN=\"CENTER\"><B>CURRENCY</B></TD>\n");
        pw.println("</TR>\n");
        Vector dataV = null;
        try {
          Hashtable reportData = WasteProfile.doSearch(db, previousT, facilityId, generationPt, searchText, true);
          dataV = (Vector) reportData.get("PRINT_SCR_DATA");
        } catch (Exception e) {
          e.printStackTrace();
          String esub1 = "An Error Occured While Generating Report.\n";
          String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING WASTE CATALOG DATA";
          sendErrorEmail(esub1, emsg1);
          return;
        }
        for (int j = 0; j < dataV.size(); j++) {
          String Color = " ";

          if (j % 2 == 0) {
            Color = "bgcolor=\"#dddddd\"";
          } else {
            Color = "bgcolor=\"#fcfcfc\"";
          }
          Hashtable hashTable = (Hashtable) dataV.elementAt(j);

          pw.println("<TR>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("STATE_WASTE_CODE") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("WASTE_DESC") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("PACKAGING") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("VENDOR_PROFILE") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("VENDOR_ID") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("WASTE_CATEGORY") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("WASTE_TYPE") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("WASTE_MGMT_OPT") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("WASTE_ITEM_ID") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("FACILITY_ID") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("LPP") + "</TD>\n");
          pw.println("<TD " + Color + " ALIGN=\"LEFT\" VALIGN=\"TOP\">" +
                     hashTable.get("CURRENCY") + "</TD>\n");
          pw.println("</TR>\n");
        }
        pw.println("</TABLE></FONT>");
        pw.close();
        generateXlsReport(tmp2, wccurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING WASTE CATALOG DATA";
        sendErrorEmail(esub1, emsg1);
        return;
      }
    } //end of else xls
  } //end of method

  public RegisterTable[] getWasteCatalogData(Vector reportData) throws Exception {
    boolean result = true;
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(WasteCatalogScreen.getVector(reportData), "WASTECATALOG", WasteCatalogScreen.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public void buildWasteManagementScreen() {
    String wmcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String requestorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR_NAME"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String storageArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("STORAGE_AREA"));
    String vendorId = BothHelpObjs.makeBlankFromNull( (String) inData.get("VENDOR_ID"));
    String vendorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("VENDOR_NAME"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    try {
      if (fileType.equalsIgnoreCase("PDF")) {
        erw.readTemplate("" + templDirectory + "WasteManagementSearch.erw"); //Query the result directly from database
      } else {
        erw.readTemplate("" + templDirectory + "WasteManagementSearchCSV.erw"); //Query the result directly from database
      }
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = null;
    where = " actual_ship_date is null and (transfer_date is not null or bulk_or_container = 'B' or (transfer_date is null and accumulation = 'Y'))";
    Vector cond = new Vector();

    if (!BothHelpObjs.isBlankString(facilityId)
        && !facilityId.startsWith("All") && facilityId != null) {
      cond.addElement("facility_id ='" + facilityId + "'");
    }
    if (!BothHelpObjs.isBlankString(storageArea)
        && (!storageArea.equalsIgnoreCase("all"))) {
      cond.addElement("storage_location = '" + storageArea + "'");
    }
    if ( (!BothHelpObjs.isBlankString(vendorId))
        && (!vendorId.equalsIgnoreCase("all"))) {
      cond.addElement("vendor_id = '" + vendorId + "'");

    }

    if (cond.size() == 1) {
      where = " and " + cond.elementAt(0).toString();
    } else if (cond.size() > 1) {
      for (int i = 0; i < cond.size(); i++) {

        where += " and " + cond.elementAt(i).toString();
      }
    }
    where += " order by container_id";
    //System.out.println("\n\n------------where :" + where);
    //setting the where clause
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);

    tm.setLabel("LAB018", requestorName);
    tm.setLabel("LAB019", facilityId);
    tm.setLabel("LAB020", storageArea);
    tm.setLabel("LAB021", vendorName);

    generateReport(wmcurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildWasteTrackScreen() {
    String wtccurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String transferred = BothHelpObjs.makeBlankFromNull( (String) inData.get("TRANSFERRED"));
    String daysSincePickup = BothHelpObjs.makeBlankFromNull( (String) inData.get("DAYS_SINCE_PICKUP"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    try {
      if (fileType.equalsIgnoreCase("PDF")) {
        erw.readTemplate("" + templDirectory + "WasteTrackingSearch.erw"); //Query the result directly from database
      } else {
        erw.readTemplate("" + templDirectory + "WasteTrackingSearchCSV.erw"); //Query the result directly from database
      }
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = null;
    Vector cond = new Vector();
    if (!BothHelpObjs.isBlankString(facilityId)
        && !facilityId.startsWith("All") && facilityId != null) {
      cond.addElement("facility_id ='" + facilityId + "'");
    }
    if (transferred.equalsIgnoreCase("T")
        && !BothHelpObjs.isBlankString(daysSincePickup)) {
      cond.addElement("days_since_transfer <= " + daysSincePickup);
    } else {
      cond.addElement("transfer_date is null");
    }

    if (cond.size() == 1) {
      where = cond.elementAt(0).toString();
    } else if (cond.size() > 1) {
      where = cond.elementAt(0).toString();
      for (int i = 1; i < cond.size(); i++) {

        where += " and " + cond.elementAt(i).toString();
      }
    }
    where += " order by transfer_date desc";
    //System.out.println("\n\n------------where :"+where);
    //setting the where clause
    tm = erw.getTemplateManager();
    //if (cond.size() != 0) {
    tm.setWHEREClause("SEC_00", where);
    //}
    tm.setLabel("FAC", facilityId);
    tm.setLabel("LAB016", daysSincePickup);
    if (transferred.equalsIgnoreCase("T")) {
      tm.setLabel("LAB009", "X");
    } else {
      tm.setLabel("LAB009", "");
    }
    generateReport(wtccurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildWasteOrderTrackScreen() {
    String wotcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String orderNo = BothHelpObjs.makeBlankFromNull( (String) inData.get("WASTE_ORDER"));
    String storageArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("STORAGE_AREA"));
    String vendorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("VENDOR_NAME"));
    String shippedAfter = BothHelpObjs.makeBlankFromNull( (String) inData.get("AFTER"));
    String shippedBefore = BothHelpObjs.makeBlankFromNull( (String) inData.get("BEFORE"));
    String draftD = BothHelpObjs.makeBlankFromNull( (String) inData.get("GENERATED"));
    String submittedD = BothHelpObjs.makeBlankFromNull( (String) inData.get("SUBMITTED"));
    String cancelledD = BothHelpObjs.makeBlankFromNull( (String) inData.get("CANCELLED"));
    String shippedD = BothHelpObjs.makeBlankFromNull( (String) inData.get("PICKUP"));
    String copy1ReturnD = BothHelpObjs.makeBlankFromNull( (String) inData.get("COPY_1"));
    String certOfDispD = BothHelpObjs.makeBlankFromNull( (String) inData.get("COD"));
    String invoiceD = BothHelpObjs.makeBlankFromNull( (String) inData.get("INVOICED"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    try {
      if (fileType.equalsIgnoreCase("PDF")) {
        erw.readTemplate("" + templDirectory + "WasteOrderTrackingSearch.erw"); //Query the result directly from database
      } else {
        erw.readTemplate("" + templDirectory + "WasteOrderTrackingSearchCSV.erw"); //Query the result directly from database
      }
      this.seterwDataSource();
    }

    catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = "";
    Vector conditionV = new Vector();
    if (facilityId.length() > 0) {
      conditionV.addElement(" facility_id = '" + facilityId + "'");
    }
    if (storageArea.length() > 0
        && !BothHelpObjs.isBlankString(storageArea)
        && !storageArea.equalsIgnoreCase("all")) {
      conditionV.addElement(" storage_location_id = '" + storageArea + "'");
    }
    if (orderNo.length() > 0) {
      conditionV.addElement(" order_no = '" + orderNo + "'");
    }
    if (vendorName.length() > 0) {
      //conditionV.addElement(" vendor_id = '"+String.valueOf(vendor).toUpperCase()+"' ");
      conditionV.addElement(" company_name    = '" + vendorName + "' ");
    }
    if (shippedAfter.length() > 0) {
      conditionV.addElement(" actual_ship_date > to_date('" + shippedAfter + "','MM/DD/YYYY')");
    }
    if (shippedBefore.length() > 0) {
      conditionV.addElement(" actual_ship_date < to_date('" + shippedBefore + "','MM/DD/YYYY')");
    }

    Vector statusV = new Vector();
    if (draftD.equalsIgnoreCase("T")) {
      statusV.addElement(" status = 'draft'");
    }
    if (submittedD.equalsIgnoreCase("T")) {
      statusV.addElement("  status = 'submitted'");
      statusV.addElement(" status = 'resubmitted'");
    }
    if (cancelledD.equalsIgnoreCase("T")) {
      statusV.addElement(" status = 'cancelled'");
    }
    if (shippedD.equalsIgnoreCase("T")) {
      statusV.addElement(" actual_ship_date is not null");
    }
    if (invoiceD.equalsIgnoreCase("T")) {
      statusV.addElement(" invoice_date is not null");
    }
    if (copy1ReturnD.equalsIgnoreCase("T")) {
      statusV.addElement("  manifest_return_date is null");
    }
    if (certOfDispD.equalsIgnoreCase("T")) {
      statusV.addElement("  last_cod_date != 'all' and last_cod_date != 'n/a'");

      //combine status clauses with OR;
    }
    String statusQ = "";
    for (int i = 0; i < statusV.size(); i++) {
      if (i > 0) {
        statusQ += " or ";
      }
      statusQ += "(" + statusV.elementAt(i).toString() + ")";
    }
    if (!BothHelpObjs.isBlankString(statusQ)) {
      statusQ = "(" + statusQ + ")";
      conditionV.addElement(statusQ);
    }

    //now combine all the conditions with AND
    for (int j = 0; j < conditionV.size(); j++) {
      if (j > 0) {
        where += " and ";
      }
      where += "(" + conditionV.elementAt(j).toString() + ")";
    }

    where += " order by shipment_id";
    //System.out.println("\n\n------------where :"+where);
    //setting the where clause
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);

    tm.setLabel("FAC", facilityId);
    tm.setLabel("AREA", storageArea);
    tm.setLabel("ORDER", orderNo);
    tm.setLabel("VEND", vendorName);
    tm.setLabel("AFTER", shippedAfter);
    tm.setLabel("BEFORE", shippedBefore);

    if (draftD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX3", "X");
    } else {
      tm.setLabel("BOX3", "");
    }
    if (submittedD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX5", "X");
    } else {
      tm.setLabel("BOX5", "");
    }
    if (cancelledD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX6", "X");
    } else {
      tm.setLabel("BOX6", "");
    }
    if (shippedD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX7", "X");
    } else {
      tm.setLabel("BOX7", "");
    }
    if (copy1ReturnD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX", "X");
    } else {
      tm.setLabel("BOX", "");
    }
    if (certOfDispD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX2", "X");
    } else {
      tm.setLabel("BOX2", "");
    }
    if (invoiceD.equalsIgnoreCase("T")) {
      tm.setLabel("BOX8", "X");
    } else {
      tm.setLabel("BOX8", "");
    }

    generateReport(wotcurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildAdminGrpScreen() {
    String agscurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String groupdesc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( (String) inData.get("GROUP_DESC"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    try {
      erw.readTemplate("" + templDirectory + "AdminGroup.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId + "' and user_group_desc = '" + groupdesc + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB002", facilityId);
    tm.setLabel("LAB003", groupdesc);

    generateReport(agscurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildAppScreen() {
    String apcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    try {
      erw.readTemplate("" + templDirectory + "FinanceApprover.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    //tm.setWHEREClause("SEC_01",where2);
    tm.setLabel("LAB006", facilityId);

    generateReport(apcurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildListCostScreen() {
    String lcscurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String vendorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("VENDOR_NAME"));
    String wasteOrder = BothHelpObjs.makeBlankFromNull( (String) inData.get("WASTE_ORDER"));
    String requestorName = BothHelpObjs.makeBlankFromNull( (String) inData.get("REQUESTOR_NAME"));
    String storageArea = BothHelpObjs.makeBlankFromNull( (String) inData.get("STORAGE_AREA"));
    String submitDate = BothHelpObjs.makeBlankFromNull( (String) inData.get("SUBMIT_DATE"));
    String reSubmitDate = BothHelpObjs.makeBlankFromNull( (String) inData.get("RESUBMIT_DATE"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    /*query.put("CURRENT_SCREEN",currentScreen);
                 query.put("VENDOR_NAME",this.vendorName);
                 query.put("WASTE_ORDER",this.wasteOrder);
                 query.put("REQUESTOR_NAME",this.requestorName);
                 query.put("STORAGE_AREA",this.storageArea);
                 query.put("SUBMIT_DATE",this.submitDate);
                 query.put("RESUBMIT_DATE",this.reSubmitDate);*/

    try {
      if (lcscurrentScreen.equalsIgnoreCase("LIST")) {
        if (fileType.equalsIgnoreCase("PDF")) {
          erw.readTemplate("" + templDirectory + "list.erw"); //Query the result directly from database
        } else {
          erw.readTemplate("" + templDirectory + "listCSV.erw"); //Query the result directly from database
        }
      } else if (lcscurrentScreen.equalsIgnoreCase("COST")) {
        if (fileType.equalsIgnoreCase("PDF")) {
          erw.readTemplate("" + templDirectory + "cost.erw"); //Query the result directly from database
        } else {
          erw.readTemplate("" + templDirectory + "costCSV.erw"); //Query the result directly from database
        }
      } else {
        if (fileType.equalsIgnoreCase("PDF")) {
          erw.readTemplate("" + templDirectory + "invoice.erw"); //Query the result directly from database
        } else {
          erw.readTemplate("" + templDirectory + "invoiceCSV.erw"); //Query the result directly from database
        }
      }
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    tm = erw.getTemplateManager();

    if (lcscurrentScreen.equalsIgnoreCase("LIST")) { //over-riding list.erw
      //bulk waste
      String where1 = " order_no = " + wasteOrder;
      where1 += " and bulk_or_container = 'B' order by container_id";
      //System.out.println("\n\n------------where1 :"+where1);

      //container(s) waste
      String where2 = " order_no = " + wasteOrder;
      where2 += " and bulk_or_container = 'C' order by container_id";
      //System.out.println("\n\n------------where2 :"+where2);

      //setting the where clause
      tm.setWHEREClause("SEC_00", where1);
      tm.setWHEREClause("SEC_01", where2);
    } else { //over-riding cost.erw
      String where = " order_no = " + wasteOrder;
      where += " order by shipment_id";
      tm.setWHEREClause("SEC_00", where);
    }

    tm.setLabel("LAB007", vendorName);
    tm.setLabel("LAB008", wasteOrder);
    tm.setLabel("LAB009", requestorName);
    tm.setLabel("LAB010", storageArea);
    tm.setLabel("LAB011", submitDate);
    tm.setLabel("LAB012", reSubmitDate);

    generateReport(lcscurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildUserProfileScreen() {
    String upcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String userIdProfile = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID_PROFILE"));
    String browser = BothHelpObjs.makeBlankFromNull( (String) inData.get("BROWSER"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    StringBuffer Msg = new StringBuffer();

    StringTokenizer st = new StringTokenizer(browser, "\\");
    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        result += tok + "/";
      }
    }
    if (result.length() > 1) {
      browser = result.substring(0, result.length() - 1);
    }

    if (fileType.equalsIgnoreCase("PDF")) {
      try {
        erw.readTemplate("" + templDirectory + "UserProfile.erw"); //Query the result directly from database
        this.seterwDataSource();
      } catch (Exception ex) {
        System.out.println("ERROR LOADING TEMPLATE");
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING TEMPLATE";
        sendErrorEmail(esub1, emsg1);
        return;
      }

      String where00 = null;
      where00 = "personnel_id = " + userIdProfile;
      String where01 = null;
      where01 = "personnel_id = " + userIdProfile;
      //setting the where clause
      tm = erw.getTemplateManager();
      tm.setWHEREClause("SEC_00", where00);
      tm.setWHEREClause("SEC_01", where01);
      tm.setLabel("BROWSERL", browser);
      generateReport(upcurrentScreen);
    } else {
      /*
                     Msg.append("<FONT FACE=\"Arial\">");
                     Msg.append("<TABLE BORDER=\"0\">\n");
                     Msg.append("<TR>\n");
                     Msg.append("<TD COLSPAN=\"4\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >User Profile</FONT></B></TD>\n");
                     Msg.append(getTimeDate());
                     Msg.append("</TR>\n");
                     Msg.append("<TR><TD>&nbsp;</TD></TR>\n");
                     Msg.append("<TR><TD>&nbsp;</TD></TR>\n");
                     Msg.append("</TABLE>\n");
                     try
                     {
           UserProfileXlsReport UserPXlsReport = new UserProfileXlsReport();
           Msg.append(UserPXlsReport.createXls(userIdProfile,db.getClient(),browser));
                     }
                     catch (Exception e)
                     {
           e.printStackTrace();
           String esub1 = "An Error Occured While Generating Report.\n";
           String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
           sendErrorEmail(esub1,emsg1);
           return;
                     }
                     Msg.append("</TABLE></FONT>");
                     generateXlsReport(Msg.toString(),upcurrentScreen);*/
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
        file = "" + HomeDirectory + userId + upcurrentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        //write file
        pw.println("<FONT FACE=\"Arial\">");
        pw.println("<TABLE BORDER=\"0\">\n");
        pw.println("<TR>\n");
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"CENTER\" ><B><FONT size=\"5\" >User Profile</FONT></B></TD>\n");
        pw.println(getTimeDate());
        pw.println("</TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("<TR><TD>&nbsp;</TD></TR>\n");
        pw.println("</TABLE>\n");
        String query1 = "Select * from PERSONNEL where personnel_id = " +
            userIdProfile;

        String query2 =
            "Select * from FACILITY_USER_GROUP_VIEW where personnel_id = " +
            userIdProfile;

        pw.println("<TABLE BORDER=\"0\">\n");
        try {
          try {
            DBResultSet dbrs = db.doQuery(query1);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              pw.println("<TR>\n");
              pw.println("<TD ALIGN=\"LEFT\">Name: ");
              pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString(
                  "LAST_NAME")));
              pw.println(",");
              pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString(
                  "FIRST_NAME")));
              pw.println("</TD>\n");

              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Login Name: " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("LOGON_ID")) +
                         "</TD></TR>\n");
              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Login ID: " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString(
                  "PERSONNEL_ID")) + "</TD></TR>\n");
              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Phone: " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("PHONE")) +
                         "</TD></TR>\n");
              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Phone(2nd): " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString(
                  "ALT_PHONE")) + "</TD></TR>\n");
              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Pager: " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("PAGER")) +
                         "</TD></TR>\n");
              pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Fax: " +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("FAX")) +
                         "</TD></TR>\n");
              pw.println(
                  "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Shipping Location: " +
                  BothHelpObjs.makeSpaceFromNull(rs.getString(
                  "SHIPPING_LOCATION")) + "</TD></TR>\n");
              pw.println(
                  "<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Mailing Location: " +
                  BothHelpObjs.makeSpaceFromNull(rs.getString("MAIL_LOCATION")) +
                  "</TD></TR>\n");
              pw.println("</TR>\n");
            }
          } catch (Exception e1) {
            e1.printStackTrace();
            throw e1;
          }

          pw.println("<TR><TD>&nbsp;</TD></TR>\n");
          pw.println("<TR><TD>&nbsp;</TD></TR>\n");
          pw.println("</TABLE>\n");

          pw.println("<TABLE BORDER=\"1\">\n");
          pw.println("<TR>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>FACILITY</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>PREFERRED</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>CREATE MR</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>APPROVER</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>AMOUNT</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>RELEASER</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>CREATE NEW CHEMICAL</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>ADMIN</B></TD>\n");
          pw.println("<TD ALIGN=\"CENTER\"><B>WASTE MANAGER</B></TD>\n");
          pw.println("</TR>\n");

          try {
            DBResultSet dbrs = db.doQuery(query2);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
              pw.println("<TR>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("FACILITY_ID")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("PREFERRED_FACILITY")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("CREATE_MR")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("APPROVER")) +
                         "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("COST_LIMIT")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(rs.getString("RELEASER")) +
                         "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("CREATE_NEW_CHEM")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("ADMINISTRATOR")) + "</TD>\n");
              pw.println("<TD ALIGN=\"LEFT\">" +
                         BothHelpObjs.makeSpaceFromNull(
                  rs.getString("WASTE_MANAGER")) + "</TD>\n");
              pw.println("</TR>\n");
            }
          } catch (Exception e2) {
            e2.printStackTrace();
            throw e2;
          }
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }

        pw.println("</TABLE></FONT>");
        pw.close();
        generateXlsReport(tmp2, upcurrentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        String esub1 = "An Error Occured While Generating Report.\n";
        String emsg1 = " The batch report you requested was not created.\n\nError While Getting Data";
        sendErrorEmail(esub1, emsg1);
        return;
      }

    }
    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildWorkAreaScreen() {
    String wacurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    try {
      erw.readTemplate("" + templDirectory + "WorkArea.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      //System.out.println("ERROR LOADING TEMPLATE");
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING TEMPLATE";
      sendErrorEmail(esub1, emsg1);
      return;
    }

    // 11-27-00 need to change this where clause right now it is for the use app group.

    String where = " facility_id = '" + facilityId + "'";

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    //tm.setWHEREClause("SEC_01",where2);
    tm.setLabel("LAB006", facilityId);

    generateReport(wacurrentScreen);

    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }

  }

  public void buildUseAppGrpScreen() {
    String uagcurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    String groupdesc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( (String) inData.get("GROUP_DESC"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));

    try {
      erw.readTemplate("" + templDirectory + "UseGroupApproval.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId + "' and user_group_desc = '" + groupdesc + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB002", facilityId);
    tm.setLabel("LAB003", groupdesc);

    generateReport(uagcurrentScreen);
    //closing JDBC connection
    try {
      //System.out.println("\n******** going to close database connection.");
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public void buildNewChemScreen() {
    String ncscurrentScreen = BothHelpObjs.makeBlankFromNull( (String) inData.get("CURRENT_SCREEN"));
    String facilityId = BothHelpObjs.makeBlankFromNull( (String) inData.get("FACILITY_ID"));
    userId = BothHelpObjs.makeBlankFromNull( (String) inData.get("USER_ID"));
    fileType = BothHelpObjs.makeBlankFromNull( (String) inData.get("FILE_TYPE"));
    method = BothHelpObjs.makeBlankFromNull( (String) inData.get("METHOD"));
    try {
      erw.readTemplate("" + templDirectory + "NewChemApp.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      //System.out.println("ERROR LOADING TEMPLATE");
      String esub1 = "An Error Occured While Generating Report.\n";
      String emsg1 = " The batch report you requested was not created.\n\nERROR LOADING TEMPLATE";
      sendErrorEmail(esub1, emsg1);
      return;
    }

    String where = " facility_id = '" + facilityId + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB004", facilityId);

    generateReport(ncscurrentScreen);

    //closing JDBC connection
    try {
      ds.Disconnect();
    } catch (Exception e2222) {
      System.out.println("\n\n--------- unable to close connection from database.");
    }
  }

  public RegisterTable[] getCatSearchData(Vector reportData) throws Exception {
    //in case query return no data
    if (reportData.size() < 1) {
      Hashtable DataH = new Hashtable();
      DataH.put("COMMENTS", "");
      DataH.put("CATALOG_ID", "");
      DataH.put("CAT_PART_NO", "");
      DataH.put("PART_GROUP_NO", "");
      DataH.put("INVENTORY_GROUP", "");
      DataH.put("PART_GROUP", "");
      DataH.put("PART_ITEM_GROUP", "");
      //DataH.put("STORAGE_TEMP","");
      DataH.put("SHELF_LIFE", "");
      //DataH.put("SHELF_LIFE_BASIS","");
      DataH.put("ITEM_ID", "");
      DataH.put("BUNDLE", "");
      DataH.put("CATALOG_PRICE", "");
      DataH.put("UNIT_PRICE", "");
      DataH.put("MIN_BUY", "");
      DataH.put("PART_DESCRIPTION", "");
      DataH.put("QUANTITY_PER_BUNDLE", "");
      DataH.put("PART_ID", "");
      DataH.put("COMPONENTS_PER_ITEM", "");
      DataH.put("SIZE_VARIES", "");
      DataH.put("MATERIAL_DESC", "");
      DataH.put("MFG_DESC", "");
      DataH.put("MATERIAL_ID", "");
      DataH.put("PACKAGING", "");
      DataH.put("GRADE", "");
      DataH.put("DIMENSION", "");
      DataH.put("MFG_PART_NO", "");
      DataH.put("MSDS_ON_LINE", "");
      DataH.put("APPROVAL_STATUS", "");
      DataH.put("LIMIT_QUANTITY_PERIOD1", "");
      //DataH.put("DAYS_PERIOD1","");
      DataH.put("LIMIT_QUANTITY_PERIOD2", "");
      //DataH.put("DAYS_PERIOD2","");
      DataH.put("SOURCE_HUB", "");
      DataH.put("STOCKING_METHOD", "");
      DataH.put("SPECS", "");

      reportData.addElement(DataH);
    }

    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(catalogSearchData.getVector(reportData), "CATSEARCHDATA", catalogSearchData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method

  public RegisterTable[] getInventoryData(Vector reportData) throws Exception {
    //in case query return no data
    if (reportData.size() < 1) {
      HashMap h = new HashMap(15);
      h.put("DETAIL_0", " ");
      h.put("DETAIL_1", " ");
      h.put("DETAIL_2", " ");
      h.put("DETAIL_3", " ");
      h.put("DETAIL_4", " ");
      h.put("DETAIL_5", " ");
      h.put("DETAIL_6", " ");
      h.put("DETAIL_7", " ");
      h.put("DETAIL_8", " ");
      h.put("DETAIL_9", " ");
      h.put("DETAIL_10", " ");
      h.put("DETAIL_11", " ");
      h.put("DETAIL_12", " ");
      h.put("DETAIL_13", " ");
      h.put("DETAIL_14", " ");
      reportData.addElement(h);
    }

    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(InventoryData.getVector(reportData), "INVENTORY", InventoryData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public RegisterTable[] getCatAddTrackData(String query) throws Exception {
    //first get data
    Vector reportData = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        HashMap h = new HashMap(12);
        h.put("DETAIL_0", rs.getString("request_id"));
        h.put("DETAIL_1", rs.getString("name"));
        h.put("DETAIL_2", BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("request_date"))));
        h.put("DETAIL_3", BothHelpObjs.makeBlankFromNull(rs.getString("request_status_desc")));
        h.put("DETAIL_4", BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
        h.put("DETAIL_5", BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DETAIL_6", BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
        h.put("DETAIL_7", BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        h.put("DETAIL_8", BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
        h.put("DETAIL_9", BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer")));
        h.put("DETAIL_10", BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        h.put("DETAIL_11", BothHelpObjs.makeBlankFromNull(rs.getString("mfg_catalog_id")));
        reportData.addElement(h);
      }

      //in case query return no data
      if (reportData.size() < 1) {
        HashMap h = new HashMap(12);
        h.put("DETAIL_0", " ");
        h.put("DETAIL_1", " ");
        h.put("DETAIL_2", " ");
        h.put("DETAIL_3", " ");
        h.put("DETAIL_4", " ");
        h.put("DETAIL_5", " ");
        h.put("DETAIL_6", " ");
        h.put("DETAIL_7", " ");
        h.put("DETAIL_8", " ");
        h.put("DETAIL_9", " ");
        h.put("DETAIL_10", " ");
        h.put("DETAIL_11", " ");
        reportData.addElement(h);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(CatAddTrackData.getVector(reportData), "CATADDTRACK", CatAddTrackData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public RegisterTable[] getOrderTrackData(Vector reportData) throws Exception {
    /*
               //first get data
               Vector reportData = new Vector();
               DBResultSet dbrs = null;
               ResultSet rs = null;
               try{
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        HashMap h = new HashMap(19);
        h.put("DETAIL_0",rs.getString("request_line_status"));
        h.put("DETAIL_1",rs.getString("facility_name"));
        h.put("DETAIL_2",rs.getString("application_desc"));
        h.put("DETAIL_3",rs.getString("fac_part_no"));
        h.put("DETAIL_4",BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        h.put("DETAIL_5",rs.getString("item_type"));
        h.put("DETAIL_6",rs.getString("pr_number")+"-"+rs.getString("line_item"));
        h.put("DETAIL_7",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
        h.put("DETAIL_8",BothHelpObjs.makeBlankFromNull(rs.getString("release_date")));
        h.put("DETAIL_9",BothHelpObjs.makeBlankFromNull(rs.getString("required_datetime")));
        h.put("DETAIL_10",rs.getInt("total_picked")+" of "+rs.getInt("quantity"));
        h.put("DETAIL_11",rs.getString("total_shipped"));
        h.put("DETAIL_12",BothHelpObjs.makeBlankFromNull(rs.getString("last_shipped")));
        h.put("DETAIL_13",BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
        h.put("DETAIL_14",rs.getString("requestor_name"));
        h.put("DETAIL_15",BothHelpObjs.makeBlankFromNull(rs.getString("critical")));
        h.put("DETAIL_16",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc")));
        h.put("DETAIL_17",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        h.put("DETAIL_18",BothHelpObjs.makeBlankFromNull(rs.getString("approver_name")));
        reportData.addElement(h);
      }
      //in case query return no data
      if (reportData.size() < 1) {
        HashMap h = new HashMap(19);
        h.put("DETAIL_0"," ");
        h.put("DETAIL_1"," ");
        h.put("DETAIL_2"," ");
        h.put("DETAIL_3"," ");
        h.put("DETAIL_4"," ");
        h.put("DETAIL_5"," ");
        h.put("DETAIL_6"," ");
        h.put("DETAIL_7"," ");
        h.put("DETAIL_8"," ");
        h.put("DETAIL_9"," ");
        h.put("DETAIL_10"," ");
        h.put("DETAIL_11"," ");
        h.put("DETAIL_12"," ");
        h.put("DETAIL_13"," ");
        h.put("DETAIL_14"," ");
        h.put("DETAIL_15"," ");
        h.put("DETAIL_16"," ");
        h.put("DETAIL_17"," ");
        h.put("DETAIL_18"," ");
        reportData.addElement(h);
      }
               }catch(Exception e){
      e.printStackTrace();
      throw e;
               }finally {
      dbrs.close();
               }
     */
    //in case query return no data
    if (reportData.size() < 1) {
      HashMap h = new HashMap(19);
      h.put("DETAIL_0", " ");
      h.put("DETAIL_1", " ");
      h.put("DETAIL_2", " ");
      h.put("DETAIL_3", " ");
      h.put("DETAIL_4", " ");
      h.put("DETAIL_5", " ");
      h.put("DETAIL_6", " ");
      h.put("DETAIL_7", " ");
      h.put("DETAIL_8", " ");
      h.put("DETAIL_9", " ");
      h.put("DETAIL_10", " ");
      h.put("DETAIL_11", " ");
      h.put("DETAIL_12", " ");
      h.put("DETAIL_13", " ");
      h.put("DETAIL_14", " ");
      h.put("DETAIL_15", " ");
      h.put("DETAIL_16", " ");
      h.put("DETAIL_17", " ");
      h.put("DETAIL_18", " ");
      reportData.addElement(h);
    }
    RegisterTable[] rt = new RegisterTable[1];
    try {
      rt[0] = new RegisterTable(OrderTrackData.getVector(reportData), "ORDERTRACK", OrderTrackData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public void generateReport(String grcurrentScreen) {
    Random ran = new Random();
    int tmp = ran.nextInt();
    Integer tmp2 = new Integer(tmp);

    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath();
    reoprtlog.info("generateReport   Start " + tmp2.toString() + "   ");
    erw.setCacheOption(true, "" + HomeDirectory + userId + grcurrentScreen + "screen" + tmp2.toString() + ".joi");

    try {
      String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
      ec.setPathForFontMapFile(fontmappath);
      ec.setReportData(erw.generateReport());
      //handle it a little differently if MSDS
      if ("MSDS".equalsIgnoreCase(grcurrentScreen)) {
        if (fileType.equalsIgnoreCase("PDF")) {
          ec.setPDFProperty("FileName", HomeDirectory + userId + grcurrentScreen + "screen" + tmp2.toString() + ".pdf");
          ec.setPDFProperty("ZipCompressed", new Boolean(false));
          ec.generatePDF();
        } else {
          ec.setPDFProperty("ZipCompressed", new Boolean(false));
          ec.generateCSV(HomeDirectory + userId + grcurrentScreen + "screen" + tmp2.toString() + ".csv", null, ",");
        }
      }else {
        ec.setPDFProperty("FileName", HomeDirectory + userId + grcurrentScreen + "screen" + tmp2.toString() + ".pdf");
        ec.setPDFProperty("ZipCompressed", new Boolean(false));
        ec.generatePDF();
      }
    } catch (Exception ex) {
      //System.out.println("++++++++ ERRROR can't generate PDF");
      ex.printStackTrace();
    }
    reoprtlog.info("generateReport    Done  " + tmp2.toString() + "");
    updateReport(tmp2, grcurrentScreen);
    sendEmail();
  }

  public void updateReport(Integer ran, String updcurrentScreen) {
    String reportName = null;

    String Name = inData.get("REPORT_NAME").toString();
    if (Name.length() > 0) {
      reportName = Name;
    } else {
      if (updcurrentScreen.equalsIgnoreCase("CATALOG")) {
        reportName = "Catalog";
      } else if (updcurrentScreen.equalsIgnoreCase("WC")) { //waste catalog
        reportName = "Waste Catalog";
      } else if (updcurrentScreen.equalsIgnoreCase("OT")) { //order track
        reportName = "Order Tracking";
      } else if (updcurrentScreen.equalsIgnoreCase("WT")) { //waste track win
        reportName = "Waste Transfer Management";
      } else if (updcurrentScreen.equalsIgnoreCase("WM")) { //waste management
        reportName = "Waste Management";
      } else if (updcurrentScreen.equalsIgnoreCase("WOT")) { //waste order track
        reportName = "Waste Order Track";
      } else if (updcurrentScreen.equalsIgnoreCase("IF")) { //Inventory Float (catalog R.M.C)
        reportName = "Inventory Float";
      } else if (updcurrentScreen.equalsIgnoreCase("UP")) { //User Profile
        reportName = "User Profile";
      } else if (updcurrentScreen.equalsIgnoreCase("Inventory")) {
        reportName = "Inventory";
      } else if (updcurrentScreen.equalsIgnoreCase("MR")) {
        reportName = "Material Request";
      } else if (updcurrentScreen.equalsIgnoreCase("Work Area")) {
        reportName = "Work Area";
      } else if (updcurrentScreen.equalsIgnoreCase("MSDS")) { //Usage Report: MSDS revision report
        reportName = "MSDS";
      } else if (updcurrentScreen.equalsIgnoreCase("Approvers")) { // Admin screeen Approvers
        reportName = "Approvers";
      } else if (updcurrentScreen.equalsIgnoreCase("Use_App_Group")) { // Approver groups
        reportName = "Use Approval Groups";
      } else if (updcurrentScreen.equalsIgnoreCase("New_Chem_App")) { // New Chemical Approvers
        reportName = "New Chemical Approval";
      } else if (updcurrentScreen.equalsIgnoreCase("Admin_Group")) { // Admin groups
        reportName = "Administrative Groups";
      } else if (updcurrentScreen.equalsIgnoreCase("NCT")) { //DMITRIY New Chem Track
        reportName = "New Chemical Track";
      } else if (updcurrentScreen.equalsIgnoreCase("UsageReport")) { //trong 1-11-01
        reportName = "Usage Report";
      } else if (updcurrentScreen.equalsIgnoreCase("Voc_Usage")) { //trong 1-11-01
        reportName = "VOC Report";
      }
    }
    try {
      String query = "insert into batch_report (personnel_id,report_date,report_name,content)";
      String HomeDirectory = radian.web.tcmisResourceLoader.gethosturl() + "reports/";

      if (this.fileType.equalsIgnoreCase("PDF")) {
        query += " values(" + userId + ",sysdate,'" + reportName + "','" + HomeDirectory + userId + updcurrentScreen + "screen" + ran.toString() + ".pdf')";
      } else {
        if ("MSDS".equalsIgnoreCase(updcurrentScreen)) {
          query += " values(" + userId + ",sysdate,'" + reportName + "','" + HomeDirectory + userId + updcurrentScreen + "screen" + ran.toString() + ".csv')";
        }else {
          query += " values(" + userId + ",sysdate,'" + reportName + "','" + HomeDirectory + userId + updcurrentScreen + "screen" + ran.toString() + ".xls')";
        }
      }
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendEmail() {
    try {
      String logonId = null;
      String query = "select logon_id from personnel where personnel_id = " + userId;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        logonId = (BothHelpObjs.makeBlankFromNull(rs.getString("logon_id")));
      }

      //String batchreportser = bundle.getString("BATCH_REPORT_SERVLET");
      String myC = db.getClient();

      String esub = "Your batch report(s) is ready.";
      String emsg = " The batch report you requested is now ready.\n Click link below:\n";
      String hosturl = radian.web.tcmisResourceLoader.gethosturl();
      if (myC.startsWith("Ray")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ray/batchreportviewermain.do");
      } else
      if (myC.startsWith("DRS")) {
        emsg = emsg + (" " + hosturl + "tcmIS/drs/batchreportviewermain.do");
      } else
      if (myC.startsWith("SWA")) {
        emsg = emsg + (" " + hosturl + "tcmIS/swa/batchreportviewermain.do");
      } else
      if (myC.startsWith("LMCO")) {
        emsg = emsg + (" " + hosturl + "tcmIS/lmco/batchreportviewermain.do");
      } else
      if (myC.startsWith("Seagate")) {
        emsg = emsg + (" " + hosturl + "tcmIS/seagate/batchreportviewermain.do");
      } else
      if (myC.startsWith("BAE")) {
        emsg = emsg + (" " + hosturl + "tcmIS/bae/batchreportviewermain.do");
      } else
      if (myC.startsWith("UTC")) {
        emsg = emsg + (" " + hosturl + "tcmIS/utc/batchreportviewermain.do");
      } else
      if (myC.startsWith("SD")) {
        emsg = emsg + (" " + hosturl + "tcmIS/sd/batchreportviewermain.do");
      } else
      if (myC.startsWith("Miller")) {
        emsg = emsg + (" " + hosturl + "tcmIS/miller/batchreportviewermain.do");
      } else
      if (myC.startsWith("GM")) {
        emsg = emsg + (" " + hosturl + "tcmIS/gm/batchreportviewermain.do");
      } else
      if (myC.startsWith("CAL")) {
        emsg = emsg + (" " + hosturl + "tcmIS/united/batchreportviewermain.do");
      } else
      if (myC.startsWith("FEC")) {
        emsg = emsg + (" " + hosturl + "tcmIS/fec/batchreportviewermain.do");
      } else
      if (myC.startsWith("Boeing")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ula/batchreportviewermain.do");
      } else
      if (myC.startsWith("AeroEco")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ael/batchreportviewermain.do");
      } else
      if (myC.startsWith("Dana")) {
        emsg = emsg + (" " + hosturl + "tcmIS/dana/batchreportviewermain.do");
      } else
      if (myC.startsWith("ABLaero")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ablaero/batchreportviewermain.do");
      } else
      if (myC.startsWith("DOE")) {
        emsg = emsg + (" " + hosturl + "tcmIS/doe/batchreportviewermain.do");
      } else
      if (myC.startsWith("IAI")) {
        emsg = emsg + (" " + hosturl + "tcmIS/iai/batchreportviewermain.do");
      } else
      if (myC.startsWith("L3")) {
        emsg = emsg + (" " + hosturl + "tcmIS/l3/batchreportviewermain.do");
      } else
      if (myC.startsWith("AM")) {
        emsg = emsg + (" " + hosturl + "tcmIS/am/batchreportviewermain.do");
      } else
      if (myC.startsWith("GEMA")) {
        emsg = emsg + (" " + hosturl + "tcmIS/gema/batchreportviewermain.do");
      } else
      if (myC.startsWith("PGE")) {
        emsg = emsg + (" " + hosturl + "tcmIS/pge/batchreportviewermain.do");
      } else
      if (myC.startsWith("QOS")) {
        emsg = emsg + (" " + hosturl + "tcmIS/qos/batchreportviewermain.do");
      } else
      if (myC.startsWith("DCX")) {
        emsg = emsg + (" " + hosturl + "tcmIS/dcx/batchreportviewermain.do");
      } else
      if (myC.startsWith("FORD")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ford/batchreportviewermain.do");
      } else
      if (myC.startsWith("Algat")) {
        emsg = emsg + (" " + hosturl + "tcmIS/algat/batchreportviewermain.do");
      } else
      if (myC.startsWith("BAZ")) {
        emsg = emsg + (" " + hosturl + "tcmIS/baz/batchreportviewermain.do");
      } else
      if (myC.startsWith("CMM")) {
        emsg = emsg + (" " + hosturl + "tcmIS/cmm/batchreportviewermain.do");
      } else
      if (myC.startsWith("Kanfit")) {
        emsg = emsg + (" " + hosturl + "tcmIS/kanfit/batchreportviewermain.do");
      } else
      if (myC.startsWith("Fedco")) {
        emsg = emsg + (" " + hosturl + "tcmIS/fedco/batchreportviewermain.do");
      } else
      if (myC.startsWith("GD")) {
        emsg = emsg + (" " + hosturl + "tcmIS/gd/batchreportviewermain.do");
      } else
      if (myC.startsWith("IMCO")) {
        emsg = emsg + (" " + hosturl + "tcmIS/imgo/batchreportviewermain.do");
      } else
      if (myC.startsWith("Kemfast")) {
        emsg = emsg + (" " + hosturl + "tcmIS/kemfast/batchreportviewermain.do");
      } else
      if (myC.startsWith("BL")) {
        emsg = emsg + (" " + hosturl + "tcmIS/bl/batchreportviewermain.do");
      } else
      if (myC.startsWith("Verasun")) {
        emsg = emsg + (" " + hosturl + "tcmIS/verasun/batchreportviewermain.do");
      } else
      if (myC.startsWith("Hrgivon")) {
        emsg = emsg + (" " + hosturl + "tcmIS/hrgivon/batchreportviewermain.do");
      } else
      if (myC.startsWith("Haargaz")) {
        emsg = emsg + (" " + hosturl + "tcmIS/haargaz/batchreportviewermain.do");
      } else
      if (myC.startsWith("Cyclone")) {
        emsg = emsg + (" " + hosturl + "tcmIS/cyclone/batchreportviewermain.do");
      } else
      if (myC.startsWith("Volvo")) {
        emsg = emsg + (" " + hosturl + "tcmIS/volvo/batchreportviewermain.do");
      } else
      if (myC.startsWith("Nalco")) {
        emsg = emsg + (" " + hosturl + "tcmIS/nalco/batchreportviewermain.do");
      } else
      if (myC.startsWith("Pepsi")) {
        emsg = emsg + (" " + hosturl + "tcmIS/pepsi/batchreportviewermain.do");
      } else
      if (myC.startsWith("HAL")) {
        emsg = emsg + (" " + hosturl + "tcmIS/hal/batchreportviewermain.do");
      } else
      if (myC.startsWith("ABM")) {
        emsg = emsg + (" " + hosturl + "tcmIS/abm/batchreportviewermain.do");
      } else
      if (myC.startsWith("MTL")) {
        emsg = emsg + (" " + hosturl + "tcmIS/mtl/batchreportviewermain.do");
      } else
      if (myC.startsWith("TIMKEN")) {
        emsg = emsg + (" " + hosturl + "tcmIS/timken/batchreportviewermain.do");
      } else
      if (myC.startsWith("NRG")) {
        emsg = emsg + (" " + hosturl + "tcmIS/nrg/batchreportviewermain.do");
      } else
      if (myC.startsWith("MAEET")) {
        emsg = emsg + (" " + hosturl + "tcmIS/maeet/batchreportviewermain.do");
      } else
      if (myC.startsWith("SGD")) {
        emsg = emsg + (" " + hosturl + "tcmIS/sgd/batchreportviewermain.do");
      } else
      if (myC.startsWith("Caterpillar")) {
        emsg = emsg + (" " + hosturl + "tcmIS/cat/batchreportviewermain.do");
      } else
      if (myC.startsWith("OMA")) {
        emsg = emsg + (" " + hosturl + "tcmIS/oma/batchreportviewermain.do");
      } else
      if (myC.startsWith("Siemens")) {
        emsg = emsg + (" " + hosturl + "tcmIS/siemens/batchreportviewermain.do");
      } else
      if (myC.startsWith("Getrag")) {
        emsg = emsg + (" " + hosturl + "tcmIS/getrag/batchreportviewermain.do");
      } else
      if (myC.startsWith("Alcoa")) {
        emsg = emsg + (" " + hosturl + "tcmIS/alcoa/batchreportviewermain.do");
      } else
      if (myC.startsWith("ZWL")) {
        emsg = emsg + (" " + hosturl + "tcmIS/zwl/batchreportviewermain.do");
      } else
      if (myC.startsWith("HansSasserath")) {
        emsg = emsg + (" " + hosturl + "tcmIS/hans_sasserath/batchreportviewermain.do");
		} else
      if (myC.startsWith("DD")) {
        emsg = emsg + (" " + hosturl + "tcmIS/dd/batchreportviewermain.do");
		} else
      if (myC.startsWith("GKN")) {
        emsg = emsg + (" " + hosturl + "tcmIS/gkn/batchreportviewermain.do");
		} else
      if (myC.startsWith("Aeropia")) {
        emsg = emsg + (" " + hosturl + "tcmIS/aeropia/batchreportviewermain.do");
		} else
      if (myC.startsWith("Goodrich")) {
        emsg = emsg + (" " + hosturl + "tcmIS/goodrich/batchreportviewermain.do");
		} else
      if (myC.startsWith("OMMC")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ommc/batchreportviewermain.do");
		} else
      if (myC.startsWith("FBM")) {
        emsg = emsg + (" " + hosturl + "tcmIS/fbm/batchreportviewermain.do");
		} else
      if (myC.startsWith("Aerocz")) {
        emsg = emsg + (" " + hosturl + "tcmIS/aerocz/batchreportviewermain.do");
		} else
      if (myC.startsWith("GE")) {
        emsg = emsg + (" " + hosturl + "tcmIS/ge/batchreportviewermain.do");
		} else {
        emsg = emsg + (" " + hosturl + "tcmIS/" + myC.toLowerCase() + "/batchreportviewermain.do");
      }

      Integer user = new Integer(this.userId);
      emsg += "\nBatch report(s) that is older than two weeks will be deleted.";
      HelpObjs.sendMail(db, esub, emsg, user.intValue(), false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendErrorEmail(String esub, String emsg) {
    try {
      String logonId = null;
      String query = "select logon_id from personnel where personnel_id = " + userId;
      DBResultSet dbrs = null;
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
    }
  }

  public void generateXlsReport(Integer tmp2, String xlscurrentScreen) {
    /*String file="";
         Random ran=new Random();
         int tmp=ran.nextInt();
         Integer tmp2=new Integer( tmp );
         String HomeDirectory=radian.web.tcmisResourceLoader.getsavelreportpath();
         file="" + HomeDirectory + userId + xlscurrentScreen + "screen" + tmp2.toString() + ".xls";
         reoprtlog.info("generateXlsReport   Start "+tmp2.toString()+"   ");
         try
         {
        File outFile=new File( file );
        FileOutputStream DBO=new FileOutputStream( outFile );
        byte outbuf[];
        outbuf=new byte[contents.length()];
        outbuf=contents.getBytes();
        DBO.write( outbuf );
        DBO.close();
         }
         catch ( IOException ioe )
         {
        System.out.println( "\n\nError opening output file: " );
        System.out.println( String.valueOf( ioe ) );
        System.out.println( "\n" );
         }
         reoprtlog.info("generateXlsReport    Done  "+tmp2.toString()+"");*/
    updateReport(tmp2, xlscurrentScreen);
    sendEmail();
  }

  public String getTimeDate() {
    Calendar cal = Calendar.getInstance();
    String cdate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR);
    int am_pm = cal.get(Calendar.AM_PM);
    String am = "";
    if (am_pm == 0) {
      am = "AM";
    } else if (am_pm == 1) {
      am = "PM";
    }
    int min = cal.get(Calendar.MINUTE);
    int hours = cal.get(Calendar.HOUR);
    String time = "";

    if (hours < 10) {
      time += "0" + hours;
    } else {
      time += hours;
    }
    if (min < 10) {
      time += ":0" + min;
    } else {
      time += ":" + min;
    }
    time += " " + am;

    return "<TD COLSPAN=\"8\" ALIGN=\"CENTER\"><FONT size=\"4\" >Date: " + cdate + " Time: " + time + "</FONT></TD>\n";
  }
}