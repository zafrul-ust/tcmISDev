package radian.web.erw.printScreen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.InventoryView;
import radian.tcmis.server7.share.dbObjs.NewChemTrackTable;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TrackView;
import radian.tcmis.server7.share.dbObjs.ItemCatalog;
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

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 02-20-04 Making the Ctalog Printscreen work with the new procedure
 *
 * 3-25-04 passing user_id to inventoryView so I can decide whether user is using JRE 1.3 or 1.4
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class SPrintScreen {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private ACJEngine erw = null;
  private JDBCHandler ds = null;
  private TemplateManager tm = null;
  private ACJOutputProcessor ec = null;
  private String fileType = null;
  private String method = null;
  private String userId = null;
  private String session = null;
  private String NewURL = "";
  private String currentClient = null;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

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

  public SPrintScreen(ServerResourceBundle b, TcmISDBModel d) {
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
      } else if (tempClient.startsWith("UTC")) {
        ds.connect(dbDriver, phoenixUrl, UTCPhoenixUserName, UTCPhoenixPasswd, true);
      } else if (tempClient.startsWith("BAE")) {
        ds.connect(dbDriver, phoenixUrl, BAEPhoenixUserName, BAEPhoenixPasswd, true);
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
  		} else if (tempClient.startsWith("GKN")) {
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

  public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    currentClient = URLEncoder.encode(request.getParameter("client"));
    PrintWriter out = new PrintWriter(response.getOutputStream());
    response.setContentType("text/html");

    session = request.getParameter("session");
    //------------------------------------------------------SESSION = NULL------------------------------------------------
    if (session == null) {
      String whichScreen = request.getParameter("which_screen");
      //
      // reoprtlog.info("getResult Active  Screen:   " + whichScreen + " ");
      if (whichScreen.equalsIgnoreCase("CATALOG")) {
        String currentScreen = URLEncoder.encode(request.getParameter("which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter("facility_id"));
        String workArea = URLEncoder.encode(request.getParameter("work_area"));
        String searchText = URLEncoder.encode(request.getParameter("search_text"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        String engEvalCat = URLEncoder.encode(request.getParameter("eng_eval_cat"));
        String allFac = URLEncoder.encode(request.getParameter("all_fac"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        String approved = URLEncoder.encode(request.getParameter("approved"));
        String catalogType = URLEncoder.encode(request.getParameter("catalogType"));
        String active = URLEncoder.encode(request.getParameter("active"));
        String facility_name = URLEncoder.encode(request.getParameter("facility_name"));
        String workarea_name = URLEncoder.encode(request.getParameter("workarea_name"));

        NewURL = "&which_screen=" + currentScreen + "&facility_id=" + facilityId + "&work_area=" + workArea + "&search_text=" + searchText + "" +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" + userId + "&eng_eval_cat=" + engEvalCat + "&all_fac=" + allFac + "&approved=" + approved + "&catalogType=" + catalogType + "&active=" + active + "";
        NewURL += "&facility_name=" + facility_name + "";
        NewURL += "&workarea_name=" + workarea_name + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("WC")) { //waste catalog
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String requestorName = URLEncoder.encode(request.getParameter(
            "requestor_name"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String generationPt = URLEncoder.encode(request.getParameter(
            "generation_pt"));
        String searchText = URLEncoder.encode(request.getParameter(
            "search_text"));
        String previousTransfer = URLEncoder.encode(request.
            getParameter("pre_transfer"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&requestor_name=" +
            requestorName + "&facility_id=" + facilityId +
            "&generation_pt=" + generationPt + "&search_text=" + searchText +
            "" + "&pre_transfer=" + previousTransfer +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("WT")) { //waste track win
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String requestorName = URLEncoder.encode(request.getParameter(
            "requestor_name"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String transferred = URLEncoder.encode(request.getParameter(
            "transferred"));
        String daysSincePickup = URLEncoder.encode(request.
            getParameter("days"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&requestor_name=" +
            requestorName + "&facility_id=" + facilityId + "&transferred=" +
            transferred +
            "&days=" + daysSincePickup + "" + "&file_type=" + fileType +
            "&method=" + method + "&user_id=" + userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("WM")) { //waste management
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String requestorName = URLEncoder.encode(request.getParameter(
            "requestor_name"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String storageArea = URLEncoder.encode(request.getParameter(
            "storage_area"));
        String vendorId = URLEncoder.encode(request.getParameter(
            "vendor_id"));
        String vendorName = URLEncoder.encode(request.getParameter(
            "vendor_name"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&requestor_name=" +
            requestorName + "&facility_id=" + facilityId +
            "&storage_area=" + storageArea + "&vendor_id=" + vendorId +
            "&vendor_name=" + vendorName +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("WOT")) { //waste order track
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String orderNo = URLEncoder.encode(request.getParameter(
            "order"));
        String storageArea = URLEncoder.encode(request.getParameter(
            "storage_area"));
        //String vendorId =      URLEncoder.encode(request.getParameter("vendor_id"));
        String vendorName = URLEncoder.encode(request.getParameter(
            "company_name"));
        String shippedAfter = URLEncoder.encode(request.getParameter(
            "after"));
        String shippedBefore = URLEncoder.encode(request.getParameter(
            "before"));
        String draftD = URLEncoder.encode(request.getParameter(
            "generate"));
        String submittedD = URLEncoder.encode(request.getParameter(
            "submit"));
        String cancelledD = URLEncoder.encode(request.getParameter(
            "cancel"));
        String shippedD = URLEncoder.encode(request.getParameter(
            "pick_up"));
        String copy1ReturnD = URLEncoder.encode(request.getParameter(
            "copy_1"));
        String certOfDispD = URLEncoder.encode(request.getParameter(
            "cod"));
        String invoiceD = URLEncoder.encode(request.getParameter(
            "invoice"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId + "&order=" + orderNo + "" +
            "&storage_area=" + storageArea + "&company_name=" + vendorName +
            "&after=" + shippedAfter + "" +
            "&before=" + shippedBefore + "" + "&generate=" + draftD +
            "&submit=" + submittedD + "&cancel=" + cancelledD +
            "&pick_up=" + shippedD + "&copy_1=" + copy1ReturnD + "&cod=" +
            certOfDispD + "&invoice=" + invoiceD +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("IF")) { //Inventory Float (catalog R.M.C)
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String itemId = URLEncoder.encode(request.getParameter(
            "item_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId + "&item_id=" + itemId + "" +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      }

      else if (whichScreen.equalsIgnoreCase("UP")) { //User Profile
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        String userIdProfile = URLEncoder.encode(request.getParameter(
            "user_id_profile"));
        String browser = URLEncoder.encode(request.getParameter(
            "browser"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));

        browser = BothHelpObjs.addSlashUrl(browser);
        NewURL = "&which_screen=" + currentScreen + "&user_id_profile=" +
            userIdProfile + "" +
            "&browser=" + browser + "&file_type=" + fileType + "&method=" +
            method + "&user_id=" + userId + "";
        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("Inventory")) { //Inventory
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String searchText = URLEncoder.encode(request.getParameter(
            "search_text"));
        String wareHouse = URLEncoder.encode(request.getParameter(
            "warehouse"));
        String expDate = URLEncoder.encode(request.getParameter(
            "exp_date"));
        String exp2Date = URLEncoder.encode(request.getParameter(
            "exp_2_date"));
        String byDate = URLEncoder.encode(request.getParameter(
            "by_date"));
        String onHand = URLEncoder.encode(request.getParameter(
            "on_hand"));
        String onOrder = URLEncoder.encode(request.getParameter(
            "on_order"));
        String allParts = URLEncoder.encode(request.getParameter(
            "all_parts"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        String facility_name = URLEncoder.encode(request.getParameter(
            "facility_name"));
        String workarea_name = URLEncoder.encode(request.getParameter(
            "workarea_name"));
        String order_byin = URLEncoder.encode(request.getParameter(
            "order_byinven"));

        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId + "&search_text=" + searchText + "" +
            "&warehouse=" + wareHouse + "&exp_date=" + expDate + "&exp_2_date=" +
            exp2Date + "&by_date=" + byDate + "" +
            "&on_hand=" + onHand + "&on_order=" + onOrder + "&all_parts=" +
            allParts + "" +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        NewURL += "&fac_bol=" +
            URLEncoder.encode(request.getParameter("fac_bol"));
        NewURL += "&ware_bol=" +
            URLEncoder.encode(request.getParameter("ware_bol"));
        NewURL += "&exp_bol=" +
            URLEncoder.encode(request.getParameter("exp_bol"));
        NewURL += "&prom_bol=" +
            URLEncoder.encode(request.getParameter("prom_bol"));
        NewURL += "&on_hold_bol=" +
            URLEncoder.encode(request.getParameter("on_hold_bol"));
        NewURL += "&on_order_bol=" +
            URLEncoder.encode(request.getParameter("on_order_bol"));
        NewURL += "&user_fac=" +
            URLEncoder.encode(request.getParameter("user_fac"));
        NewURL += "&item=" +
            URLEncoder.encode(request.getParameter("item"));
        NewURL += "&facility_name=" + facility_name + "";
        NewURL += "&workarea_name=" + workarea_name + "";
        NewURL += "&order_byinven=" + order_byin + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("MR")) { //Material Request
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String accntSysId = URLEncoder.encode(request.getParameter(
            "accnt_sys_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&accnt_sys_id=" +
            accntSysId +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("Work_Area")) { //work area
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("MSDS")) { //Usage Report: MSDS revision report
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("Approvers")) { // Admin screeen Approvers
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("Use_App_Group")) { // Approver groups
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String groupdesc = URLEncoder.encode(request.getParameter(
            "group_desc"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId + "&group_desc=" + groupdesc +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("New_Chem_App")) { // New Chemical Approvers
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("Admin_Group")) { // Admin groups
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String facilityId = URLEncoder.encode(request.getParameter(
            "facility_id"));
        String groupdesc = URLEncoder.encode(request.getParameter(
            "group_desc"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        NewURL = "&which_screen=" + currentScreen + "&facility_id=" +
            facilityId + "&group_desc=" + groupdesc +
            "&file_type=" + fileType + "&method=" + method + "&user_id=" +
            userId + "";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("OT")) { //  Chemical Order Tracking
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String requestor = URLEncoder.encode(request.getParameter(
            "requestor"));
        String requestorName = URLEncoder.encode(request.getParameter(
            "requestorName"));
        String facId = URLEncoder.encode(request.getParameter("facId"));
        String workArea = URLEncoder.encode(request.getParameter(
            "workArea"));
        String searchType = URLEncoder.encode(request.getParameter(
            "searchType"));
        String searchContent = URLEncoder.encode(request.getParameter(
            "searchContent"));
        String searchText = URLEncoder.encode(request.getParameter(
            "searchText"));
        String anyStatus = URLEncoder.encode(request.getParameter(
            "any"));
        String draft = URLEncoder.encode(request.getParameter("draft"));
        String open = URLEncoder.encode(request.getParameter("open"));
        String archived = URLEncoder.encode(request.getParameter(
            "archived"));
        String day = "";
        if ("T".equalsIgnoreCase(archived)) {
          day = URLEncoder.encode(request.getParameter("days"));
        }
        String critical = URLEncoder.encode(request.getParameter(
            "critical"));
        String cancel = URLEncoder.encode(request.getParameter(
            "cancel"));
        String needApproval = URLEncoder.encode(request.getParameter(
            "needApproval"));

        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        String facility_name = URLEncoder.encode(request.getParameter(
            "facility_name"));
        String workarea_name = URLEncoder.encode(request.getParameter(
            "workarea_name"));

        NewURL = "&which_screen=" + currentScreen + "&requestor=" + requestor +
            "&requestorName=" + requestorName +
            "&needApproval=" + needApproval + "&facId=" + facId + "&workArea=" +
            workArea + "&searchContent=" + searchContent + "&searchType=" +
            searchType + "&searchText=" + searchText +
            "&any=" + anyStatus + "&draft=" + draft + "&open=" + open +
            "&archived=" + archived + "&days=" + day + "&critical=" + critical +
            "&cancel=" + cancel + "&file_type=" + fileType + "&method=" +
            method + "&user_id=" + userId + "&facility_name=" + facility_name +
            "&workarea_name=" + workarea_name;

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("NCT")) {
        String currentScreen = URLEncoder.encode(request.getParameter(
            "which_screen"));
        String requestor = URLEncoder.encode(request.getParameter(
            "requestor"));
        String approver = URLEncoder.encode(request.getParameter(
            "approver"));
        String searchText = URLEncoder.encode(request.getParameter(
            "searchText"));
        String facId = URLEncoder.encode(request.getParameter("facId"));
        String workArea = URLEncoder.encode(request.getParameter(
            "workArea"));
        String requestId = URLEncoder.encode(request.getParameter(
            "requestId"));
        String showAllRequests = URLEncoder.encode(request.
            getParameter("showAllReq"));
        String requestorName = URLEncoder.encode(request.getParameter(
            "reqName"));
        String approverName = URLEncoder.encode(request.getParameter(
            "approverName"));
        userId = URLEncoder.encode(request.getParameter("user_id"));
        fileType = URLEncoder.encode(request.getParameter("file_type"));
        method = URLEncoder.encode(request.getParameter("method"));
        String facility_name = URLEncoder.encode(request.getParameter(
            "fac_name"));
        String workarea_name = URLEncoder.encode(request.getParameter(
            "worka_name"));
        String status_selection = URLEncoder.encode(request.
            getParameter("status_sel"));
        //String need_approval = URLEncoder.encode(request.getParameter("need_approval"));

        NewURL = "&which_screen=" + currentScreen + "&requestor=" + requestor +
            "&requestorName=" + requestorName + "&approver=" + approver +
            "&approverName=" + approverName + "&searchText=" + searchText +
            "&facId=" + facId + "&workArea=" + workArea + "&requestId=" +
            requestId + "" +
            "&showAllRequests=" + showAllRequests + "&file_type=" + fileType +
            "&method=" + method + "&user_id=" + userId + "";
        NewURL += "&facility_name=" + facility_name + "";
        NewURL += "&workarea_name=" + workarea_name + "";
        NewURL += "&status_selection=" + status_selection + "";
        //NewURL += "&need_approval="+need_approval+"";

        buildHTML(out);
      } else if (whichScreen.equalsIgnoreCase("LIST") ||
                 whichScreen.equalsIgnoreCase("COST") ||
                 whichScreen.equalsIgnoreCase("INVOICE")) { // waste order: list && cost && invoice
        //String currentScreen = request.getParameter("which_screen");
        String vendorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
            "vendor_name"));
        String wasteOrder = BothHelpObjs.makeBlankFromNull(request.getParameter(
            "waste_order"));
        String requestorName = BothHelpObjs.makeBlankFromNull(request.
            getParameter("requestor_name"));
        String storageArea = BothHelpObjs.makeBlankFromNull(request.
            getParameter("storage_area"));
        String submitDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
            "submit_date"));
        String reSubmitDate = BothHelpObjs.makeBlankFromNull(request.
            getParameter("resubmit_date"));
        userId = request.getParameter("user_id");
        fileType = request.getParameter("file_type");
        method = request.getParameter("method");
        NewURL = "&which_screen=" + whichScreen + "&vendor_name=" +
            URLEncoder.encode(vendorName) + "&waste_order=" +
            URLEncoder.encode(wasteOrder) + "&requestor_name=" +
            URLEncoder.encode(requestorName) + "" +
            "&storage_area=" + URLEncoder.encode(storageArea) +
            "&submit_date=" + URLEncoder.encode(submitDate) +
            "&resubmit_date=" + URLEncoder.encode(reSubmitDate) +
            "&file_type=" + URLEncoder.encode(fileType) + "&method=" +
            URLEncoder.encode(method) + "&user_id=" +
            URLEncoder.encode(userId) + "";
        buildHTML(out);
      }
    } else if (session.equalsIgnoreCase("1")) {
      erw = new ACJEngine();
      ec = new ACJOutputProcessor();
      ds = new JDBCHandler();

      String whichScreen = request.getParameter("which_screen");
      if (whichScreen.equalsIgnoreCase("CATALOG")) { //catalog
        buildCatalogScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("WC")) { //waste catalog
        buildWasteCatalogScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("WT")) { //waste track win
        buildWasteTrackScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("WM")) { //waste management
        buildWasteManagementScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("WOT")) { //waste order track
        buildWasteOrderTrackScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("IF")) { //Inventory Float (catalog R.M.C)
        buildInventoryFloatScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("UP")) { //User Profile
        buildUserProfileScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("Inventory")) { //Inventory
        buildInventoryScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("MR")) { //material request
        buildMaterialRequestScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("Work_Area")) { //work area
        buildWorkAreaScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("MSDS")) { //Usage Report: MSDS revision report
        buildMSDSScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("Approvers")) { // Admin screeen Approvers
        buildAppScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("Use_App_Group")) { // Approver groups
        buildUseAppGrpScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("New_Chem_App")) { // New Chemical Approvers
        buildNewChemScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("Admin_Group")) { // Admin groups
        buildAdminGrpScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("NCT")) { //DMITRIY New Chem Track
        buildNewChemTrackingScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("OT")) { //DMITRIY order track
        buildOrderTrackingScreen(request, response);
      } else if (whichScreen.equalsIgnoreCase("LIST") ||
                 whichScreen.equalsIgnoreCase("COST") ||
                 whichScreen.equalsIgnoreCase("INVOICE")) { // waste order: list && cost
        buildListCostScreen(request, response);
      }
    }
  }

  public void buildHTML(PrintWriter out) {
    StringBuffer Msghtm = new StringBuffer();
    Msghtm.append("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
    Msghtm.append(
        "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    Msghtm.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    Msghtm.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
    Msghtm.append("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
    Msghtm.append("<!-- \n");
    Msghtm.append("    var Timer = null\n");
    Msghtm.append("    var winHandlewa = null\n");
    Msghtm.append(
        "    function winClose(){    // close all open pop-up windows\n");
    Msghtm.append(
        "      if ((winHandlewa != null) && !winHandlewa.closed) winHandlewa.close()\n");
    Msghtm.append("      }\n");
    Msghtm.append("function openwin2 (str)\n");
    Msghtm.append("        {\n");
    String hosturl = radian.web.tcmisResourceLoader.gethosturl();
    Msghtm.append("    winHandlewa = window.open(\"" + hosturl +
                  "reports/Timer.html\", \"Timer3\",\n");
    Msghtm.append(
        "        \"toolbar=no,location=no,directories=no,status=no\" +\n");
    Msghtm.append("        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
    Msghtm.append("        \",width=200,height=150\");\n");
    Msghtm.append("  return winHandlewa; \n");
    Msghtm.append("        }\n");
    Msghtm.append("function DoLoad()\n");
    Msghtm.append("{ \n");
    Msghtm.append("openwin2(); \n");
    Msghtm.append("window.location.replace(\"" +
                  bundle.getString("PRINT_SCREEN") + "?session=1&client=" +
                  currentClient + "" + NewURL + "\")\n");
    Msghtm.append("} \n");
    Msghtm.append("//-->     \n");
    Msghtm.append("</SCRIPT>\n");
    Msghtm.append("</HEAD>  \n");
    Msghtm.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
    Msghtm.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
    Msghtm.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
    Msghtm.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
    Msghtm.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
    Msghtm.append("</CENTER></BODY></HTML>    \n");

    out.println(Msghtm);
    out.close();
  }

  //trong 12/6/00 waste order: list && cost && invoice
  public void buildListCostScreen(HttpServletRequest request,
                                  HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String vendorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "vendor_name"));
    String wasteOrder = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "waste_order"));
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor_name"));
    String storageArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "storage_area"));
    String submitDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "submit_date"));
    String reSubmitDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "resubmit_date"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      if (currentScreen.equalsIgnoreCase("LIST")) {
        if (fileType.equalsIgnoreCase("PDF")) {
          erw.readTemplate("" + templDirectory + "list.erw"); //Query the result directly from database
        } else {
          erw.readTemplate("" + templDirectory + "listCSV.erw"); //Query the result directly from database
        }
      } else if (currentScreen.equalsIgnoreCase("COST")) {
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

    if (currentScreen.equalsIgnoreCase("LIST")) { //over-riding list.erw
      //bulk waste
      String where1 = " order_no = " + wasteOrder;
      where1 += " and bulk_or_container = 'B' order by container_id";

      String where2 = " order_no = " + wasteOrder;
      where2 += " and bulk_or_container = 'C' order by container_id";

      tm.setWHEREClause("SEC_00", where1);
      tm.setWHEREClause("SEC_01", where2);
    } else { //over-riding cost.erw
      String where = " order_no = " + wasteOrder;

      tm.setWHEREClause("SEC_00", where);
    }

    tm.setLabel("LAB007", vendorName);
    tm.setLabel("LAB008", wasteOrder);
    tm.setLabel("LAB009", requestorName);
    tm.setLabel("LAB010", storageArea);
    tm.setLabel("LAB011", submitDate);
    tm.setLabel("LAB012", reSubmitDate);
    generateReport(response, currentScreen);
  }

  public void buildAdminGrpScreen(HttpServletRequest request,
                                  HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String groupdesc = request.getParameter("group_desc");

    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    try {
      erw.readTemplate("" + templDirectory + "AdminGroup.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId +
        "' and user_group_desc = '" + groupdesc + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB002", facilityId);
    tm.setLabel("LAB003", groupdesc);

    generateReport(response, currentScreen);
  }

  public void buildNewChemScreen(HttpServletRequest request,
                                 HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      erw.readTemplate("" + templDirectory + "NewChemApp.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
      buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
      return;
    }

    String where = " facility_id = '" + facilityId + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB004", facilityId);

    generateReport(response, currentScreen);
  }

  public void buildUseAppGrpScreen(HttpServletRequest request,
                                   HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String groupdesc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(request.
        getParameter("group_desc"));

    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      erw.readTemplate("" + templDirectory + "UseGroupApproval.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId +
        "' and user_group_desc = '" + groupdesc + "'";
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB002", facilityId);
    tm.setLabel("LAB003", groupdesc);

    generateReport(response, currentScreen);
  }

  public void buildAppScreen(HttpServletRequest request,
                             HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    try {
      erw.readTemplate("" + templDirectory + "FinanceApprover.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId + "' and AMOUNT >'0'";

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB006", facilityId);

    generateReport(response, currentScreen);
  }

  public void buildMSDSScreen(HttpServletRequest request,
                              HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

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

    generateReport(response, currentScreen);
  }

  public void buildWorkAreaScreen(HttpServletRequest request,
                                  HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      erw.readTemplate("" + templDirectory + "WorkArea.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }

    String where = " facility_id = '" + facilityId + "'";

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    tm.setLabel("LAB006", facilityId);

    generateReport(response, currentScreen);
  }

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
      rt[0] = new RegisterTable(InventoryData.getVector(reportData),
                                "INVENTORY", InventoryData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public void buildInventoryScreen(HttpServletRequest request,
                                   HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "search_text"));
    String wareHouse = request.getParameter("warehouse");
    String expDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "exp_date"));
    String exp2Date = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "exp_2_date"));
    String byDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "by_date"));
    //String onHand = request.getParameter("on_hand");
    //String onOrder = request.getParameter("on_order");
    //String allParts = request.getParameter("all_parts");
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    String facility_name = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "facility_name"));
    String workarea_name = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "workarea_name"));

    StringBuffer Msg = new StringBuffer();

    //String fac_bol =        request.getParameter("fac_bol");
    //String ware_bol =       request.getParameter("ware_bol");
    //String exp_bol =        request.getParameter("exp_bol");
    //String prom_bol =       request.getParameter("prom_bol");
    String on_hold_bol = request.getParameter("on_hold_bol");
    String on_order_bol = request.getParameter("on_order_bol");
    String user_fac = request.getParameter("user_fac");
    //String item =        request.getParameter("item");

    Hashtable h = new Hashtable(11);
    h.put("USER_ID", userId);
    h.put("FACILITY", facilityId);
    h.put("USER_FAC", user_fac);
    h.put("WAREHOUSE", wareHouse);
    h.put("ITEM", searchText);
    h.put("EXP_VAL", expDate);
    h.put("EXP2_VAL", exp2Date);
    h.put("PROM_VAL", byDate);
    h.put("ON_HOLD_BOL", new Boolean("T".equalsIgnoreCase(on_hold_bol)));
    h.put("ON_ORDER_BOL", new Boolean("T".equalsIgnoreCase(on_order_bol)));

    Vector tableData = null;
    //String query = "";          //temp value
    try {
      InventoryView InvenView = new InventoryView(db);
      tableData = InvenView.buildQueryData(h);
    } catch (Exception e) {
      e.printStackTrace();
      buildERROR(response, " Error While Generating Query -- Please Try Again");
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
        if ("T".equalsIgnoreCase(on_hold_bol)) {
          tm.setLabel("RECT001", " X");
        }
        tm.setLabel("WITHINL", expDate + " days");
        tm.setLabel("AFTERL", exp2Date + " days");
        if ("T".equalsIgnoreCase(on_order_bol)) {
          tm.setLabel("RECT000", " X");
        }
        tm.setLabel("ARRIVEL", byDate + " days");

        AppDataHandler adh = new AppDataHandler();
        //register table...
        RegisterTable[] rt = getInventoryData(tableData);
        for (int i = 0; i < rt.length; i++) {
          //Vector v1 = rt[i].getData();
          //Vector v2 = rt[i].getMethods();
          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
                            rt[i].getWhere());
        }
        erw.setDataSource(adh);
      } catch (Exception ex) {
        System.out.println("ERROR LOADING INVENTORY TEMPLATE");
        buildERROR(response,
                   " Error While Generating Query -- Please Try Again");
        return;
      }
      generateReport(response, currentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.
            getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" +
            tmp2.toString() + ".xls";
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
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response,
                   " Error While Generating Query -- Please Try Again");
        return;
      }
    }
  }

  public void buildMaterialRequestScreen(HttpServletRequest request,
                                         HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    userId = request.getParameter("user_id");
    //String accntSysId = request.getParameter("accnt_sys_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    try {
      erw.readTemplate("" + templDirectory + "MaterialRequest.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
      buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
      return;
    }

    generateReport(response, currentScreen);
  }

  //changes by dmitriy
  public void buildNewChemTrackingScreen(HttpServletRequest request,
                                         HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String requestor = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor"));
    String approver = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "approver"));
    String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "searchText"));
    String facId = BothHelpObjs.makeBlankFromNull(request.getParameter("facId"));
    String workArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "workArea"));
    String requestId = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestId"));
    String showAllRequests = BothHelpObjs.makeBlankFromNull(request.
        getParameter("showAllRequests"));
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestorName"));
    String approverName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "approverName"));
    String facility_name = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "facility_name"));
    String workarea_name = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "workarea_name"));
    String status_sel = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "status_selection"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    StringBuffer Msg = new StringBuffer();
    boolean needApp = false;
    needApp = showAllRequests.equalsIgnoreCase("T");

    String query = "";
    try {
      NewChemTrackTable nctt = new NewChemTrackTable(db);
      query = nctt.buildQuery(userId, requestor, requestId, approver, facId,
                              workArea, searchText, status_sel, needApp);
    } catch (Exception e) {
      e.printStackTrace();
      buildERROR(response, " Error While Generating Query -- Please Try Again");
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
        AppDataHandler adh = new AppDataHandler();
        RegisterTable[] rt = getCatAddTrackData(query);
        for (i = 0; i < rt.length; i++) {
          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
                            rt[i].getWhere());
        }
        erw.setDataSource(adh);
      } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("ERROR LOADING TEMPLATE");
        buildERROR(response,
                   "An Error Occured While Generating Report -- Please Try Again");
        return;
      }
      generateReport(response, currentScreen);
    } else {
      /*
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
                  }else if ("UTC".equalsIgnoreCase(db.getClient())) {
           Msg.append("<TD ALIGN=\"CENTER\"><B>UTC Request ID</B></TD>\n");
                  }else if ("BAE".equalsIgnoreCase(db.getClient())) {
           Msg.append("<TD ALIGN=\"CENTER\"><B>BAE Request ID</B></TD>\n");
                  }else {
                    Msg.append("<TD ALIGN=\"CENTER\"><B>"+db.getClient().toUpperCase()+" Request ID</B></TD>\n");
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
                      buildERROR(response,"An Error Occured While Generating Report -- Please Try Again");
                      return;
                  }
                  Msg.append("</TABLE></FONT>");
                  generateXlsReport(response,Msg.toString(),currentScreen);
       */
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.
            getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" +
            tmp2.toString() + ".xls";
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
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response,
                   " Error While Generating Query -- Please Try Again");
        return;
      }
    }
  } //end of method

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
        h.put("DETAIL_2",
              BothHelpObjs.formatDate("toCharfromDB",
                                      BothHelpObjs.
                                      makeBlankFromNull(rs.
            getString("request_date"))));
        h.put("DETAIL_3",
              BothHelpObjs.makeBlankFromNull(rs.getString("request_status_desc")));
        h.put("DETAIL_4",
              BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
        h.put("DETAIL_5",
              BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DETAIL_6",
              BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
        h.put("DETAIL_7", BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        h.put("DETAIL_8",
              BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
        h.put("DETAIL_9",
              BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer")));
        h.put("DETAIL_10",
              BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        h.put("DETAIL_11",
              BothHelpObjs.makeBlankFromNull(rs.getString("mfg_catalog_id")));
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
      rt[0] = new RegisterTable(CatAddTrackData.getVector(reportData),
                                "CATADDTRACK", CatAddTrackData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method

  //trong 12-8-00
  public String getNewChemTrackQuery(HttpServletRequest request) throws
      ServletException, IOException {
    RayTcmISDBModel dbObj = new RayTcmISDBModel(bundle.getString(
        "DB_CLIENT_NAME"));
    String MSG = "";
    String requestor = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor"));
    String approver = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "approver"));
    String searchStringIn = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "searchText"));
    String facility = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "facId"));
    String workArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "workArea"));
    String reqID = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestId"));
    String draftT = BothHelpObjs.makeBlankFromNull(request.getParameter("draft"));
    String newPartT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "newPartNumber"));
    String newGroupT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "newGroup"));
    String pendingApprovalT = BothHelpObjs.makeBlankFromNull(request.
        getParameter("pendingApproval"));
    String rejectT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "reject"));
    String newMaterialT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "newMaterial"));
    String newSizeT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "newSize"));
    String newApprovalT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "newApproval"));
    String approvedT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "approvedC"));
    String bannedT = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "banned"));
    String showAllRequests = BothHelpObjs.makeBlankFromNull(request.
        getParameter("showAllRequests"));
    String userid = request.getParameter("user_id");

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
      approver = userid;
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

    //String s;
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
              Personnel p = new Personnel(dbObj);
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
          qu = qu + " and request_id in (select distinct request_id from catalog_add_approval where chemical_approver = " +
              approver + " and status is not null)";
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
      qu = qu + "(lower(material_desc) like lower('%" + searchString +
          "%') or ";
      qu = qu + "lower(manufacturer) like lower('%" + searchString + "%') or ";
      qu = qu + "lower(mfg_catalog_id) like lower('%" + searchString +
          "%') or ";
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
    RayTcmISDBModel dbObj = null;
    Vector v = new Vector();
    String myQuery = "select request_id ";
    myQuery = myQuery + "from catalog_add_request_view_new  ";
    myQuery = myQuery + "where chemical_approver = " + approver + " ";
    myQuery = myQuery + "and status is not null ";
    myQuery = myQuery + "order by request_id";

    Hashtable h = new Hashtable();
    dbObj = new RayTcmISDBModel(bundle.getString("DB_CLIENT_NAME"));
    ResultSet rs = null;
    DBResultSet dbrs = null;

    try {
      dbrs = dbObj.doQuery(myQuery);
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
    RayTcmISDBModel dbObj = null;
    Vector v = new Vector();
    String myQuery =
        "select v.request_id, v.approval_role, v.chemical_approver ";
    myQuery = myQuery +
        "from catalog_add_request_view_new v, catalog_add_request_new r ";
    myQuery = myQuery + "where r.request_status = 5 ";
    myQuery = myQuery + "and v.chemical_approver = " + approver + " ";
    myQuery = myQuery + "and v.status is null ";
    myQuery = myQuery + "and r.request_id = v.request_id ";
    myQuery = myQuery + "order by request_id, approval_role ";

    Hashtable h = new Hashtable();
    dbObj = new RayTcmISDBModel(bundle.getString("DB_CLIENT_NAME"));
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = dbObj.doQuery(myQuery);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String myReqId = rs.getString(1);
        String myAppRole = rs.getString(2);
        String myWhere = "where approval_date is not null and lower(status) in ('approved','rejected') and ";
        myWhere = myWhere + " request_id = '" + myReqId +
            "' and approval_role = '" + myAppRole + "'";
        if (DbHelpers.countQuery(dbObj, "catalog_add_request_view_new", myWhere) <
            1) {
          h.put(myReqId, "1");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (Enumeration E = h.keys(); E.hasMoreElements(); ) {
      v.addElement( (String) E.nextElement());
    }
    return v;
  }

  //changes by dmitriy
  public void buildOrderTrackingScreen(HttpServletRequest request,
                                       HttpServletResponse response) throws
      ServletException, IOException {
    Hashtable inData = new Hashtable();
    String currentScreen = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "which_screen"));
    String requestor = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor"));
    inData.put("REQUESTOR", requestor);
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestorName"));
    String facId = BothHelpObjs.makeBlankFromNull(request.getParameter("facId"));
    inData.put("FACILITY", facId);
    String workArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "workArea"));
    inData.put("WORK_AREA", workArea);
    String searchType = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "searchType"));
    inData.put("SEARCH_TYPE", searchType);
    String searchContent = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "searchContent"));
    inData.put("SEARCH_CONTENT", searchContent);
    String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "searchText"));
    inData.put("SEARCH_TEXT", searchText);
    String needMyApproval = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "needApproval"));
    inData.put("NEED_MY_APPROVAL", needMyApproval);

    String status = "";
    if ("T".equalsIgnoreCase(needMyApproval)) {
      inData.put("NEED_MY_APPROVAL", new Boolean(true));
      status = "Orders need my approval";
    } else {
      inData.put("NEED_MY_APPROVAL", new Boolean(false));
    }
    String critical = request.getParameter("critical");
    if ("T".equalsIgnoreCase(critical)) {
      inData.put("STATUS_CRIT_ONLY", new Boolean(true));
    } else {
      inData.put("STATUS_CRIT_ONLY", new Boolean(false));
    }
    String any = request.getParameter("any");
    if ("T".equalsIgnoreCase(any)) {
      inData.put("STATUS_ANY", new Boolean(true));
      status = "Any";
    } else {
      inData.put("STATUS_ANY", new Boolean(false));
    }
    String draft = request.getParameter("draft");
    if ("T".equalsIgnoreCase(draft)) {
      inData.put("STATUS_DRAFT", new Boolean(true));
      status = "Draft";
    } else {
      inData.put("STATUS_DRAFT", new Boolean(false));
    }
    String open = request.getParameter("open"); //Open
    if ("T".equalsIgnoreCase(open)) {
      inData.put("STATUS_OPEN", new Boolean(true));
      status = "Open";
    } else {
      inData.put("STATUS_OPEN", new Boolean(false));
    }
    String archived = request.getParameter("archived");
    String days = "";
    if ("T".equalsIgnoreCase(archived)) {
      inData.put("STATUS_ARCHIVED", new Boolean(true));
      days = BothHelpObjs.makeBlankFromNull(request.getParameter("days"));
      inData.put("DAYS", days);
      status = "Fully Delivered last " + days + " days";
    } else {
      inData.put("STATUS_ARCHIVED", new Boolean(false));
    }
    String cancel = request.getParameter("cancel");
    if ("T".equalsIgnoreCase(cancel)) {
      inData.put("STATUS_CANCELLED", new Boolean(true));
      status = "Cancelled/Rejected";
    } else {
      inData.put("STATUS_CANCELLED", new Boolean(false));
    }

    userId = BothHelpObjs.makeBlankFromNull(request.getParameter("user_id"));
    inData.put("USER_ID", userId);
    fileType = BothHelpObjs.makeBlankFromNull(request.getParameter("file_type"));
    method = BothHelpObjs.makeBlankFromNull(request.getParameter("method"));

    String facility_name = request.getParameter("facility_name");
    String workarea_name = request.getParameter("workarea_name");
    StringBuffer Msg = new StringBuffer();

    Vector dataV = null;
    try {
      TrackView tv = new TrackView(db);
      dataV = tv.getPrintScreenData(inData);
    } catch (Exception e) {
      e.printStackTrace();
      buildERROR(response,
                 "An Error Occured While Generating Report -- Please Try Again");
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
          tm.setLabel("SEARCHL",
                      (String) inData.get("SEARCH_TYPE") + " " +
                      (String) inData.get("SEARCH_CONTENT") + " " +
                      (String) inData.get("SEARCH_TEXT"));
        }

        //register table...
        AppDataHandler adh = new AppDataHandler();
        RegisterTable[] rt = getOrderTrackData(dataV);
        for (int i = 0; i < rt.length; i++) {
          adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(),
                            rt[i].getWhere());
        }
        erw.setDataSource(adh);

      } catch (Exception ex) {
        buildERROR(response,
                   "An Error Occured While Generating Report -- Please Try Again");
        return;
      }
      generateReport(response, currentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.
            getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" +
            tmp2.toString() + ".xls";
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
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response,
                   " Error While Generating Query -- Please Try Again");
        return;
      }
    } //end of CVS
  } //end of method

  public RegisterTable[] getOrderTrackData(Vector reportData) throws Exception {
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
      rt[0] = new RegisterTable(OrderTrackData.getVector(reportData),
                                "ORDERTRACK", OrderTrackData.getFieldVector(), null);
    } catch (Exception e1) {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  } //end of method

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

  public void buildUserProfileScreen(HttpServletRequest request,
                                     HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    userId = request.getParameter("user_id");
    String userIdProfile = request.getParameter("user_id_profile");
    String browser = request.getParameter("browser");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
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
        ex.printStackTrace();
        System.out.println("ERROR LOADING TEMPLATE USER PROFILE");
        buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
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
      generateReport(response, currentScreen);
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
                  Msg.append(UserPXlsReport.createXls(userIdProfile,bundle.getString("DB_CLIENT_NAME"),browser));
              }
              catch (Exception e)
              {
                  e.printStackTrace();
           buildERROR(response," ERROR Getting Data From Database -- Please Try Again");
                  return;
              }
              Msg.append("</TABLE></FONT>");
              generateXlsReport(response,Msg.toString(),currentScreen);
       */
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.
            getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" +
            tmp2.toString() + ".xls";
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
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response,
                   " Error While Generating Query -- Please Try Again");
        return;
      }
    }
  }

  public void buildInventoryFloatScreen(HttpServletRequest request,
                                        HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String itemId = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "item_id"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    try {
      erw.readTemplate("" + templDirectory + "InventoryFloat.erw"); //Query the result directly from database
      this.seterwDataSource();
    } catch (Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
      buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
      return;
    }

    String where00 = null;
    where00 = "item_id = " + itemId;
    where00 += " and facility_id = '" + facilityId + "'";

    String where01 = null;
    where01 = "item_id = " + itemId;
    where01 += " and facility_id = '" + facilityId +
        "' and lot_status != 'OnOrder' and lot_status != 'InPurchasing'";

    String where02 = null;
    where02 = "item_id = " + itemId;
    where02 += " and facility_id = '" + facilityId +
        "' and (lot_status = 'OnOrder' or lot_status = 'InPurchasing')";

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where00);
    tm.setWHEREClause("SEC_01", where01);
    tm.setWHEREClause("SEC_02", where02);

    tm.setLabel("ITEM", itemId);
    generateReport(response, currentScreen);
  }

  public void buildWasteOrderTrackScreen(HttpServletRequest request,
                                         HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String orderNo = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "order"));
    String storageArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "storage_area"));
    //String vendorId = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_id"));
    String vendorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "company_name"));
    String shippedAfter = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "after"));
    String shippedBefore = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "before"));
    String draftD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "generate"));
    String submittedD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "submit"));
    String cancelledD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "cancel"));
    String shippedD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "pick_up"));
    String copy1ReturnD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "copy_1"));
    String certOfDispD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "cod"));
    String invoiceD = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "invoice"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      if (fileType.equalsIgnoreCase("PDF")) {
        erw.readTemplate("" + templDirectory +
                         "WasteOrderTrackingSearch.erw"); //Query the result directly from database
      } else {
        erw.readTemplate("" + templDirectory +
                         "WasteOrderTrackingSearchCSV.erw"); //Query the result directly from database
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
      conditionV.addElement(" actual_ship_date > to_date('" + shippedAfter +
                            "','MM/DD/YYYY')");
    }
    if (shippedBefore.length() > 0) {
      conditionV.addElement(" actual_ship_date < to_date('" + shippedBefore +
                            "','MM/DD/YYYY')");
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

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);
    //--------------------------------------------------------------------Dmitriy--------------------------------
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

    generateReport(response, currentScreen);
  }

  public void buildWasteManagementScreen(HttpServletRequest request,
                                         HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor_name"));
    String facilityId = request.getParameter("facility_id");
    String storageArea = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "storage_area"));
    String vendorId = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "vendor_id"));
    String vendorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "vendor_name"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

    try {
      if (fileType.equalsIgnoreCase("PDF")) {
        erw.readTemplate("" + templDirectory + "WasteManagementSearch.erw"); //Query the result directly from database
      } else {
        erw.readTemplate("" + templDirectory +
                         "WasteManagementSearchCSV.erw"); //Query the result directly from database
      }
      this.seterwDataSource();
    }

    catch (Exception ex) {
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

    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00", where);

    tm.setLabel("LAB018", requestorName);
    tm.setLabel("LAB019", facilityId);
    tm.setLabel("LAB020", storageArea);
    tm.setLabel("LAB021", vendorName);

    generateReport(response, currentScreen);
  }

  public void buildWasteTrackScreen(HttpServletRequest request,
                                    HttpServletResponse response) throws
      ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "requestor_name"));
    String facilityId = request.getParameter("facility_id");
    String transferred = BothHelpObjs.makeBlankFromNull(request.getParameter(
        "transferred"));
    String daysSincePickup = BothHelpObjs.makeBlankFromNull(request.
        getParameter("days"));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");

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

    tm = erw.getTemplateManager();
    //if (cond.size() != 0) {
    tm.setWHEREClause("SEC_00", where);
    //}
    tm.setLabel("NAME", requestorName);
    tm.setLabel("FAC", facilityId);
    tm.setLabel("LAB016", daysSincePickup);
    if (transferred.equalsIgnoreCase("T")) {
      tm.setLabel("LAB009", "X");
    } else {
      tm.setLabel("LAB009", "");
    }
    generateReport(response, currentScreen);
  }

  public void buildWasteCatalogScreen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String currentScreen = request.getParameter("which_screen");
    String requestorName = BothHelpObjs.makeBlankFromNull(request.getParameter("requestor_name"));
    String facilityId = request.getParameter("facility_id");
    String generationPt = BothHelpObjs.makeBlankFromNull(request.getParameter("generation_pt"));
    String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter("search_text"));
    String previousTransfer = request.getParameter("pre_transfer");
    Boolean previousT = new Boolean("T".equalsIgnoreCase(previousTransfer));
    userId = request.getParameter("user_id");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    if ("PDF".equalsIgnoreCase(fileType)) {
      try {
        erw.readTemplate("" + templDirectory + "WasteCatalogSearch.erw"); //Query the result directly from database
      } catch (Exception ex) {
        buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
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
      generateReport(response, currentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".xls";
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
          buildERROR(response, " ERROR Getting Data From Database -- Please Try Again");
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
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response, " Error While Generating Query -- Please Try Again");
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

  void buildCatalogPDF(HttpServletResponse response, Hashtable h, String facName, String workAreaName) {
    Boolean allFac = (Boolean) h.get("ALL_CATALOG");
    Boolean approved = (Boolean) h.get("APPROVED");
    String searchText = (String) h.get("SEARCH_TEXT");
    Boolean active = (Boolean) h.get("ACTIVE");

    try {
      erw.readTemplate(templDirectory + "CatalogNew.erw");
    } catch (Exception ex) {
      ex.printStackTrace();
      buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
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
      Vector catData = CatXlsReport.getReportData(h, bundle.getString("DB_CLIENT_NAME"), false);
      //register table...
      AppDataHandler adh = new AppDataHandler();
      RegisterTable[] rt = getCatSearchData(catData);
      for (int i = 0; i < rt.length; i++) {
        adh.RegisterTable(rt[i].getData(), rt[i].getName(), rt[i].getMethods(), rt[i].getWhere());
      }
      erw.setDataSource(adh);
    } catch (Exception ex1) {
      ex1.printStackTrace();
      buildERROR(response, " Error While Generating Query -- Please Try Again");
      return;
    }
  } //end of method

  void buildItemCatalogPDF(HttpServletResponse response, Hashtable h, String facName, String workAreaName) {
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
      buildERROR(response, " ERROR LOADING TEMPLATE -- Please Try Again");
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
      buildERROR(response, " Error While Generating Query -- Please Try Again");
      return;
    }
  } //end of method

  public void buildCatalogXLS(HttpServletResponse response, PrintWriter pw, Hashtable h, String facility_name, String workarea_name) {
    Boolean allFac = (Boolean) h.get("ALL_CATALOG");
    Boolean approved = (Boolean) h.get("APPROVED");
    String searchText = (String) h.get("SEARCH_TEXT");
    Boolean active = (Boolean) h.get("ACTIVE");
    boolean showLeadTime = false;
    boolean showBaseLine = false;
    //
    try {
      showLeadTime = HelpObjs.countQuery(db, "Select count(*) from tcmis_feature where feature = 'main.rightMouseClick.displayLeadTimeChart' and feature_mode = '1'") > 0;
      showBaseLine = HelpObjs.countQuery(db, "Select count(*) from tcmis_feature where feature = 'main.rightMouseClick.displayBaselineDate' and feature_mode = '1'") > 0;
    } catch (Exception ee) {
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
      v = CatXlsReport.getReportData(h, bundle.getString("DB_CLIENT_NAME"), true);
    } catch (Exception e) {
      e.printStackTrace();
      buildERROR(response, " ERROR Getting Data From Database -- Please Try Again");
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

  public void buildItemCatalogXLS(HttpServletResponse response, PrintWriter pw, Hashtable h, String facility_name, String workarea_name) {
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
        Enumeration enumCollec = dataH.keys();
        int j = 0;
        while (enumCollec.hasMoreElements()) {
          String color = "";
          if (j % 2 == 0) {
            color = "bgcolor=\"#dddddd\"";
          } else {
            color = "bgcolor=\"#fcfcfc\"";
          }
          pw.println("<TR>\n");
          String key = enumCollec.nextElement().toString();
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
      buildERROR(response, " ERROR Getting Data From Database -- Please Try Again");
      return;
    }
    pw.println("</TABLE></FONT>");
  } //end of method

  public void buildCatalogScreen(HttpServletRequest request, HttpServletResponse response) {
    String currentScreen = request.getParameter("which_screen");
    String facilityId = request.getParameter("facility_id");
    String workArea = BothHelpObjs.makeBlankFromNull(request.getParameter("work_area"));
    String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter("search_text"));
    userId = request.getParameter("user_id");
    String allFac = request.getParameter("all_fac");
    fileType = request.getParameter("file_type");
    method = request.getParameter("method");
    String approved = request.getParameter("approved");
    String active = request.getParameter("active");
    String facility_name = BothHelpObjs.makeBlankFromNull(request.getParameter("facility_name"));
    String workarea_name = BothHelpObjs.makeBlankFromNull(request.getParameter("workarea_name"));
    if ("Select a Work Area".equalsIgnoreCase(workarea_name)) {
      workarea_name = "";
    }
    String catalogType = request.getParameter("catalogType");
    String requestorSelected = request.getParameter("eng_eval_cat");

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
        buildItemCatalogPDF(response, h, facility_name, workarea_name);
      } else {
        buildCatalogPDF(response, h, facility_name, workarea_name);
      }
      generateReport(response, currentScreen);
    } else {
      try {
        //get file name
        String file = "";
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);

        String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
        file = "" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".xls";
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        if ("Item Catalog".equalsIgnoreCase(catalogType)) {
          buildItemCatalogXLS(response, pw, h, facility_name, workarea_name);
        } else {
          buildCatalogXLS(response, pw, h, facility_name, workarea_name);
        }
        pw.close();
        generateXlsReport(response, tmp2.toString(), currentScreen);
      } catch (Exception e) {
        e.printStackTrace();
        buildERROR(response, " Error While Generating Query -- Please Try Again");
        return;
      }
    } //end of else xls
  } //end of method

  public void generateReport(HttpServletResponse response, String currentScreen) {
    String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
    Random ran = new Random();
    int tmp = ran.nextInt();
    Integer tmp2 = new Integer(tmp);

    erw.setCacheOption(true, "" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".joi");
    //reoprtlog.info("generateReport    Start  " + tmp2.toString() + "");
    try {
      IViewerInterface ivi = erw.generateReport();
      if (!ec.setReportData(ivi, null)) {
        System.exit(0);
      }
    } catch (Exception ex) {
      ex.printStackTrace(); ;
      System.out.println("ERROR: While generating report");
      buildERROR(response, "ERROR: While generating report -- Please Try Again");
      return;
    }

    try {
      String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
      ec.setPathForFontMapFile(fontmappath);

      if (fileType.equalsIgnoreCase("PDF")) {
        ec.setPDFProperty("ZipCompressed", new Boolean(false));
        ec.setPDFProperty( "FileName","" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".pdf" );
        ec.generatePDF();
        //ec.generatePDF("" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".pdf", null);
      } else if (fileType.equalsIgnoreCase("CSV")) {
        ec.setPDFProperty("ZipCompressed", new Boolean(false));
        ec.generateCSV("" + HomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".csv", null, ",");
      }
    } catch (Exception ex) {
      System.out.println("++++++++ ERRROR can't generate pdf/csv");
      ex.printStackTrace();
      buildERROR(response, "ERRROR Can't Generate PDF -- Please Try Again");
      return;
    }
    //reoprtlog.info("generateReport    Done  " + tmp2.toString() + "");
    response.setContentType("text/html");

    try {
      PrintWriter output = new PrintWriter(response.getOutputStream());
      String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWW_ERW_ACTIVE");
      String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
      wwwHome = wwwHome.substring(0, (wwwHome.length() - 1));
      output.println("<HTML><HEAD>\n");
      if (fileType.equalsIgnoreCase("PDF")) {
        output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwHome + WWWHomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".pdf\">");
      } else if (fileType.equalsIgnoreCase("CSV")) {
        output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwHome + WWWHomeDirectory + userId + currentScreen + "screen" + tmp2.toString() + ".csv\">");
      }
      output.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
      output.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      output.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      output.println("</HEAD>  \n");
      output.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      output.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
      output.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\">");
      output.println("<BR>    \n");
      output.println("<B>Report Finished </B><BR><BR>\n");
      output.println("</FONT>");
      output.println("</CENTER></BODY></HTML>    \n");
      //output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+WWWHomeDirectory+userId+currentScreen+"screen"+tmp2.toString()+".pdf\">");
      output.close();
    } catch (IOException io) {
      io.printStackTrace();
    }
    return;
  }

  //public void generateXlsReport(HttpServletResponse response,String contents, String currentScreen)
  public void generateXlsReport(HttpServletResponse response, String randonnum, String currentScreen) {
    /*
                String file = "";
                Random ran = new Random();
                int tmp = ran.nextInt();
                Integer tmp2 = new Integer(tmp);
         String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
         file = ""+HomeDirectory+userId+currentScreen+"screen"+tmp2.toString()+".xls";
                try
                {
                    File outFile = new File(file);
                    FileOutputStream DBO = new FileOutputStream(outFile);
                    byte outbuf[];
                    outbuf = new byte[contents.length()];
                    outbuf = contents.getBytes();
                    DBO.write(outbuf);
                    DBO.close();
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                    System.out.println("\n\nError opening output file: ");
         buildERROR(response," Error opening output file -- Please Try Again");
                    return;
                }
     */
    response.setContentType("text/html");
    try {
      PrintWriter output = new PrintWriter(response.getOutputStream());
      String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWW_ERW_ACTIVE");
      String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
      wwwHome = wwwHome.substring(0, (wwwHome.length() - 1));
      output.println("<HTML><HEAD>\n");
      output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwHome + WWWHomeDirectory + userId + currentScreen + "screen" + randonnum + ".xls\">");
      //output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + filePath + "\">");
      output.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
      output.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      output.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      output.println("</HEAD>  \n");
      output.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      output.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
      output.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\">");
      output.println("<BR>    \n");
      output.println("<B>Report Finished </B><BR><BR>\n");
      output.println("</FONT>");
      output.println("</CENTER></BODY></HTML>    \n");
      output.close();
    } catch (IOException io) {
      io.printStackTrace();
    }
    return;
  }

  public String getTimeDate() {
    Calendar cal = Calendar.getInstance();
    String cdate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) +
        "/" + cal.get(Calendar.YEAR);
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

    return "<TD COLSPAN=\"8\" ALIGN=\"CENTER\"><FONT size=\"4\" >Date: " +
        cdate + " Time: " + time + "</FONT></TD>\n";
  }

  public void buildERROR(HttpServletResponse response, String emsg) {
    try {
      PrintWriter out = new PrintWriter(response.getOutputStream());

      StringBuffer Msgerr = new StringBuffer();
      Msgerr.append("<HTML><HEAD>\n");
      Msgerr.append("</HEAD>  \n");
      Msgerr.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      Msgerr.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
      Msgerr.append("<font face=Arial size=\"4\" color=\"#000080\"><b>" + emsg +
                    "</b></font><P></P><BR>\n");
      Msgerr.append("</CENTER></body></HTML>    \n");
      out.println(Msgerr);
      out.close();
    } catch (IOException io) {
      io.printStackTrace();
    }
  }
}