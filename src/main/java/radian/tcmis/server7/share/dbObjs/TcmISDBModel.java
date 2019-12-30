package radian.tcmis.server7.share.dbObjs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.common.db.SqlManager;
import radian.tcmis.common.util.DataSet;
import radian.tcmis.server7.client.aam.dbObjs.AamDBConnectionFactory;
import radian.tcmis.server7.client.ablaero.dbObjs.ABLaeroDBConnectionFactory;
import radian.tcmis.server7.client.abm.dbObjs.ABMDBConnectionFactory;
import radian.tcmis.server7.client.acument.dbObjs.AcumentDBConnectionFactory;
import radian.tcmis.server7.client.ael.dbObjs.AeroEcoDBConnectionFactory;
import radian.tcmis.server7.client.aernnova.dbObjs.AernnovaDBConnectionFactory;
import radian.tcmis.server7.client.aerocz.dbObjs.AeroczDBConnectionFactory;
import radian.tcmis.server7.client.aerojet.dbObjs.AerojetDBConnectionFactory;
import radian.tcmis.server7.client.aeropia.dbObjs.AeropiaDBConnectionFactory;
import radian.tcmis.server7.client.aim.dbObjs.AIMDBConnectionFactory;
import radian.tcmis.server7.client.aircelle.dbObjs.AircelleDBConnectionFactory;
import radian.tcmis.server7.client.ajw.dbObjs.AjwDBConnectionFactory;
import radian.tcmis.server7.client.alcoa.dbObjs.AlcoaDBConnectionFactory;
import radian.tcmis.server7.client.algat.dbObjs.AlgatDBConnectionFactory;
import radian.tcmis.server7.client.am.dbObjs.AMDBConnectionFactory;
import radian.tcmis.server7.client.amt.dbObjs.AmtDBConnectionFactory;
import radian.tcmis.server7.client.avcorp.dbObjs.AvcorpDBConnectionFactory;
import radian.tcmis.server7.client.ba.dbObjs.BADBConnectionFactory;
import radian.tcmis.server7.client.bae.dbObjs.BAEDBConnectionFactory;
import radian.tcmis.server7.client.bai.dbObjs.BAIDBConnectionFactory;
import radian.tcmis.server7.client.baz.dbObjs.BAZDBConnectionFactory;
import radian.tcmis.server7.client.beechcraft.dbObjs.BeechcraftDBConnectionFactory;
import radian.tcmis.server7.client.bell.dbObjs.BellDBConnectionFactory;
import radian.tcmis.server7.client.bl.dbObjs.BLDBConnectionFactory;
import radian.tcmis.server7.client.boeing.dbObjs.BoeingDBConnectionFactory;
import radian.tcmis.server7.client.boeingco.dbObjs.BoeingCoDBConnectionFactory;
import radian.tcmis.server7.client.boeingcomair.dbObjs.BoeingComairDBConnectionFactory;
import radian.tcmis.server7.client.bombardier.dbObjs.BombardierDBConnectionFactory;
import radian.tcmis.server7.client.cal.dbObjs.CALDBConnectionFactory;
import radian.tcmis.server7.client.cat.dbObjs.CaterpillarDBConnectionFactory;
import radian.tcmis.server7.client.cessna.dbObjs.CessnaDBConnectionFactory;
import radian.tcmis.server7.client.cmm.dbObjs.CMMDBConnectionFactory;
import radian.tcmis.server7.client.cpp.dbObjs.CPPDBConnectionFactory;
//import radian.tcmis.server7.client.cv.dbObjs.CatalogVendorDBConnectionFactory;
import radian.tcmis.server7.client.cyclone.dbObjs.CycloneDBConnectionFactory;
import radian.tcmis.server7.client.dana.dbObjs.DanaDBConnectionFactory;
import radian.tcmis.server7.client.dcx.dbObjs.DCXDBConnectionFactory;
import radian.tcmis.server7.client.dd.dbObjs.DDDBConnectionFactory;
import radian.tcmis.server7.client.dksh.dbObjs.DkshDBConnectionFactory;
import radian.tcmis.server7.client.doe.dbObjs.DOEDBConnectionFactory;
import radian.tcmis.server7.client.drs.dbObjs.DRSDBConnectionFactory;
import radian.tcmis.server7.client.eaton.dbObjs.EatonDBConnectionFactory;
import radian.tcmis.server7.client.embraer.dbObjs.EmbraerDBConnectionFactory;
import radian.tcmis.server7.client.fbm.dbObjs.FBMDBConnectionFactory;
import radian.tcmis.server7.client.fec.dbObjs.FECDBConnectionFactory;
import radian.tcmis.server7.client.fedco.dbObjs.FedcoDBConnectionFactory;
import radian.tcmis.server7.client.fnc.dbObjs.FNCDBConnectionFactory;
import radian.tcmis.server7.client.ford.dbObjs.FordDBConnectionFactory;
import radian.tcmis.server7.client.gd.dbObjs.GDDBConnectionFactory;
import radian.tcmis.server7.client.ge.dbObjs.GEDBConnectionFactory;
import radian.tcmis.server7.client.gema.dbObjs.GemaDBConnectionFactory;
import radian.tcmis.server7.client.getrag.dbObjs.GetragDBConnectionFactory;
import radian.tcmis.server7.client.gkn.dbObjs.GKNDBConnectionFactory;
import radian.tcmis.server7.client.gm.dbObjs.GMDBConnectionFactory;
import radian.tcmis.server7.client.goodrich.dbObjs.GoodrichDBConnectionFactory;
import radian.tcmis.server7.client.haargaz.dbObjs.HaargazDBConnectionFactory;
import radian.tcmis.server7.client.hai.dbObjs.HAIDBConnectionFactory;
import radian.tcmis.server7.client.hal.dbObjs.HALDBConnectionFactory;
import radian.tcmis.server7.client.hans_sasserath.dbObjs.HansSasserathDBConnectionFactory;
import radian.tcmis.server7.client.hexcel.dbObjs.HexcelDBConnectionFactory;
import radian.tcmis.server7.client.hrgivon.dbObjs.HrgivonDBConnectionFactory;
import radian.tcmis.server7.client.hudson.dbObjs.HudsonDBConnectionFactory;
import radian.tcmis.server7.client.iai.dbObjs.IAIDBConnectionFactory;
import radian.tcmis.server7.client.ibm.dbObjs.IBMDBConnectionFactory;
import radian.tcmis.server7.client.imco.dbObjs.IMCODBConnectionFactory;
import radian.tcmis.server7.client.internal.dbObjs.InternalDBConnectionFactory;
import radian.tcmis.server7.client.jdeere.dbObjs.JDeereDBConnectionFactory;
import radian.tcmis.server7.client.jtekt.dbObjs.JtektDBConnectionFactory;
import radian.tcmis.server7.client.kai.dbObjs.KAIDBConnectionFactory;
import radian.tcmis.server7.client.kaman.dbObjs.KamanDBConnectionFactory;
import radian.tcmis.server7.client.kanfit.dbObjs.KanfitDBConnectionFactory;
import radian.tcmis.server7.client.kemfast.dbObjs.KemfastDBConnectionFactory;
import radian.tcmis.server7.client.l3.dbObjs.L3DBConnectionFactory;
import radian.tcmis.server7.client.latecoere.dbObjs.LatecoereDBConnectionFactory;
import radian.tcmis.server7.client.lmco.dbObjs.LMCODBConnectionFactory;
import radian.tcmis.server7.client.maeet.dbObjs.MAEETDBConnectionFactory;
import radian.tcmis.server7.client.magellan.dbObjs.MagellanDBConnectionFactory;
import radian.tcmis.server7.client.magna.dbObjs.MagnaDBConnectionFactory;
import radian.tcmis.server7.client.merck.dbObjs.MerckDBConnectionFactory;
import radian.tcmis.server7.client.miller.dbObjs.MillerDBConnectionFactory;
import radian.tcmis.server7.client.mitsubishi.dbObjs.MitsubishiDBConnectionFactory;
import radian.tcmis.server7.client.moog.dbObjs.MoogDBConnectionFactory;
import radian.tcmis.server7.client.mtl.dbObjs.MTLDBConnectionFactory;
import radian.tcmis.server7.client.nalco.dbObjs.NalcoDBConnectionFactory;
import radian.tcmis.server7.client.northrop.dbObjs.NorthropGrummanDBConnectionFactory;
import radian.tcmis.server7.client.norwegian.dbObjs.NorwegianDBConnectionFactory;
import radian.tcmis.server7.client.nrg.dbObjs.NRGDBConnectionFactory;
import radian.tcmis.server7.client.oma.dbObjs.OMADBConnectionFactory;
import radian.tcmis.server7.client.ommc.dbObjs.OMMCDBConnectionFactory;
import radian.tcmis.server7.client.optimetal.dbObjs.OptimetalDBConnectionFactory;
import radian.tcmis.server7.client.orlite.dbObjs.OrliteDBConnectionFactory;
import radian.tcmis.server7.client.pcc.dbObjs.PCCDBConnectionFactory;
import radian.tcmis.server7.client.pepsi.dbObjs.PepsiDBConnectionFactory;
import radian.tcmis.server7.client.pge.dbObjs.PGEDBConnectionFactory;
import radian.tcmis.server7.client.qos.dbObjs.QOSDBConnectionFactory;
import radian.tcmis.server7.client.ray.dbObjs.RayDBConnectionFactory;
import radian.tcmis.server7.client.revima.dbObjs.RevimaDBConnectionFactory;
import radian.tcmis.server7.client.rockwellcollins.dbObjs.RockwellCollinsDBConnectionFactory;
import radian.tcmis.server7.client.rollsroyce.dbObjs.RollsRoyceDBConnectionFactory;
import radian.tcmis.server7.client.sabreliner.dbObjs.SabrelinerDBConnectionFactory;
import radian.tcmis.server7.client.safran.dbObjs.SafranDBConnectionFactory;
import radian.tcmis.server7.client.sales.dbObjs.SalesDBConnectionFactory;
import radian.tcmis.server7.client.samsung.dbObjs.SamsungDBConnectionFactory;
import radian.tcmis.server7.client.sastech.dbObjs.SasTechDBConnectionFactory;
import radian.tcmis.server7.client.schafer.dbObjs.SchaferDBConnectionFactory;
import radian.tcmis.server7.client.sd.dbObjs.SDDBConnectionFactory;
import radian.tcmis.server7.client.seagate.dbObjs.SeagateDBConnectionFactory;
import radian.tcmis.server7.client.sgd.dbObjs.SGDDBConnectionFactory;
import radian.tcmis.server7.client.siemens.dbObjs.SiemensDBConnectionFactory;
import radian.tcmis.server7.client.spirit.dbObjs.SpiritDBConnectionFactory;
import radian.tcmis.server7.client.standardaero.dbObjs.StandardAeroDBConnectionFactory;
import radian.tcmis.server7.client.supplier.dbObjs.supplierDBConnectionFactory;
import radian.tcmis.server7.client.swa.dbObjs.SWADBConnectionFactory;
import radian.tcmis.server7.client.tai.dbObjs.TAIDBConnectionFactory;
import radian.tcmis.server7.client.taiwanairforce.dbObjs.TaiwanAirForceDBConnectionFactory;
import radian.tcmis.server7.client.tambour.dbObjs.TambourDBConnectionFactory;
import radian.tcmis.server7.client.team.dbObjs.TeamDBConnectionFactory;
import radian.tcmis.server7.client.timken.dbObjs.TimkenDBConnectionFactory;
import radian.tcmis.server7.client.tomkins.dbObjs.TomkinsDBConnectionFactory;
import radian.tcmis.server7.client.tpl_dhl.dbObjs.Tpl_DhlDBConnectionFactory;
import radian.tcmis.server7.client.tpl_kpt.dbObjs.Tpl_KptDBConnectionFactory;
import radian.tcmis.server7.client.tpl_ll.dbObjs.Tpl_llDBConnectionFactory;
import radian.tcmis.server7.client.triumph.dbObjs.TriumphDBConnectionFactory;
import radian.tcmis.server7.client.usgov.dbObjs.USGOVDBConnectionFactory;
import radian.tcmis.server7.client.utc.dbObjs.UTCDBConnectionFactory;
import radian.tcmis.server7.client.verasun.dbObjs.VerasunDBConnectionFactory;
import radian.tcmis.server7.client.volvo.dbObjs.VolvoDBConnectionFactory;
import radian.tcmis.server7.client.was.dbObjs.WASDBConnectionFactory;
import radian.tcmis.server7.client.woodward.dbObjs.WoodwardDBConnectionFactory;
import radian.tcmis.server7.client.zf.dbObjs.ZFDBConnectionFactory;
import radian.tcmis.server7.client.zodiac.dbObjs.ZodiacDBConnectionFactory;
import radian.tcmis.server7.client.zwl.dbObjs.ZWLDBConnectionFactory;
import radian.tcmis.server7.client.pfs.dbObjs.PfsDBConnectionFactory;
import radian.tcmis.server7.client.coproducer.dbObjs.CoproducerDBConnectionFactory;
//Next Customer Config Auto Gen Tag0
import radian.tcmis.server7.share.db.DBConnection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.db.ResourceLibrary;
import radian.tcmis.server7.share.helpers.HelpObjs;

//import radian.web.jmonfile;

/**
 * This class is used to connect and query the database 09-08-03 - If an query
 * fails anf it has a ORA in the error sending an email out to Nish. 07-07-04 -
 * Writting every 5 minutes the JMON page to an excel file
 */

public abstract class TcmISDBModel {
	protected Connection					connect;
	protected DatabaseMetaData				dbdma;
	protected DBConnection					db;
	protected String						dbUrl			= "owldev";
	protected Exception						queryException	= null;
	protected DBResultSet					dbrs			= null;
	protected static String[]				longer_tables	= {"report_detail_view", "msds_revision_view", "voc_detail_view", "report_ad_hoc_chem_view",
			"report_ad_hoc_chem_chem_view", "report_ad_hoc_List_chem_view", "report_ad_hoc_chem_part_view", "report_add_hoc_list_part_view", "report_add_hoc_list_view",
			"hour_list", "order_tracking_view", "waste_order_view", "wst_ship_invoice_view"};

	private static ResourceLibrary			deBugresource	= null;
	private boolean							showDebugMain	= true;
	private static org.apache.log4j.Logger	log				= org.apache.log4j.Logger.getLogger("");

	String									client;																										// trong
																																							// 7-25
	String									facilityId;																									// trong
																																							// 8-28
	String									logonVersion;
	boolean									sqlTimeOut		= false;

	protected String						locale			= "en_US";

	/**
	 * Default Constructor
	 */
	public TcmISDBModel() {
		this("");
		showDebugMain = getDebugStatus();
	}

	/**
	 * Default Constructor
	 */
	public TcmISDBModel(String cl) {
		this.client = cl;
		this.logonVersion = "2";
		buildConnection(cl);
		showDebugMain = getDebugStatus();
	}

	public TcmISDBModel(String cl, String logonVersion) {
		this.client = cl;
		this.logonVersion = logonVersion;
		buildConnection(cl);
		showDebugMain = getDebugStatus();
	}

	public TcmISDBModel(String cl, String logonVersion, String locale) {
		this.client = cl;
		this.logonVersion = logonVersion;
		this.locale = locale;
		buildConnection(cl);
		showDebugMain = getDebugStatus();
	}

	// trong 7-25
	public String getClient() {
		return this.client;
	}

	/**
	 * Constructor, used before....
	 */
	public TcmISDBModel(String driver, String dburl, String user, String passwd, String cl) {
		this(cl);
	}

	public void printConnectionPoolData() {
		// (new
		// RayDBConnectionFactory()).getInstance(logonVersion).printConnectionPoolData();
	}

	protected void buildConnection(String cl) {
		boolean validConnection = false;
		int count = 0;
		do {
			printConnectionPoolData();
			try {
				if (cl.equalsIgnoreCase("Ray")) {
					db = ((new RayDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("DRS")) {
					db = ((new DRSDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("LMCO")) {
					db = ((new LMCODBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SWA")) {
					db = ((new SWADBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Seagate")) {
					db = ((new SeagateDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Tcm_Ops")) {
					db = ((new InternalDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BAE")) {
					db = ((new BAEDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("UTC")) {
					db = ((new UTCDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SD")) {
					db = ((new SDDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Miller")) {
					db = ((new MillerDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("GM")) {
					db = ((new GMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("CAL")) {
					db = ((new CALDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("FEC")) {
					db = ((new FECDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Boeing")) {
					db = ((new BoeingDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AeroEco")) {
					db = ((new AeroEcoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Dana")) {
					db = ((new DanaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ABLaero")) {
					db = ((new ABLaeroDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("DOE")) {
					db = ((new DOEDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("supplier")) {
					db = ((new supplierDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("L3")) {
					db = ((new L3DBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("IAI")) {
					db = ((new IAIDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AM")) {
					db = ((new AMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("GEMA")) {
					db = ((new GemaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("PGE")) {
					db = ((new PGEDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("QOS")) {
					db = ((new QOSDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("DCX")) {
					db = ((new DCXDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Ford")) {
					db = ((new FordDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Algat")) {
					db = ((new AlgatDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BAZ")) {
					db = ((new BAZDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("CMM")) {
					db = ((new CMMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Kanfit")) {
					db = ((new KanfitDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Fedco")) {
					db = ((new FedcoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("GD")) {
					db = ((new GDDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("IMCO")) {
					db = ((new IMCODBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Kemfast")) {
					db = ((new KemfastDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BL")) {
					db = ((new BLDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Verasun")) {
					db = ((new VerasunDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Hrgivon")) {
					db = ((new HrgivonDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Haargaz")) {
					db = ((new HaargazDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Cyclone")) {
					db = ((new CycloneDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Volvo")) {
					db = ((new VolvoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Nalco")) {
					db = ((new NalcoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Pepsi")) {
					db = ((new PepsiDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("HAL")) {
					db = ((new HALDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ABM")) {
					db = ((new ABMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("MTL")) {
					db = ((new MTLDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Timken")) {
					db = ((new TimkenDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("NRG")) {
					db = ((new NRGDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("MAEET")) {
					db = ((new MAEETDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SGD")) {
					db = ((new SGDDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("USGOV")) {
					db = ((new USGOVDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Caterpillar")) {
					db = ((new CaterpillarDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("OMA")) {
					db = ((new OMADBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Siemens")) {
					db = ((new SiemensDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Getrag")) {
					db = ((new GetragDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Alcoa")) {
					db = ((new AlcoaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ZWL")) {
					db = ((new ZWLDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("HansSasserath")) {
					db = ((new HansSasserathDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("DD")) {
					db = ((new DDDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("GKN")) {
					db = ((new GKNDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Aeropia")) {
					db = ((new AeropiaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Goodrich")) {
					db = ((new GoodrichDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("OMMC")) {
					db = ((new OMMCDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("FBM")) {
					db = ((new FBMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Aerocz")) {
					db = ((new AeroczDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BAI")) {
					db = ((new BAIDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Optimetal")) {
					db = ((new OptimetalDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Orlite")) {
					db = ((new OrliteDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Tambour")) {
					db = ((new TambourDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Team")) {
					db = ((new TeamDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("GE")) {
					db = ((new GEDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Sales")) {
					db = ((new SalesDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Hexcel")) {
					db = ((new HexcelDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ZF")) {
					db = ((new ZFDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("Avcorp")) {
					db = ((new AvcorpDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BA")) {
					db = ((new BADBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BOEING_COMAIR")) {
					db = ((new BoeingComairDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ROLLS_ROYCE")) {
					db = ((new RollsRoyceDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("STANDARD_AERO")) {
					db = ((new StandardAeroDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("HAI")) {
					db = ((new HAIDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("KOREA_AEROSPACE_INDUSTRIES")) {
					db = ((new KAIDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("NORTHROP_GRUMMAN")) {
					db = ((new NorthropGrummanDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TAI")) {
					db = ((new TAIDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TOMKINS")) {
					db = ((new TomkinsDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("MOOG")) {
					db = ((new MoogDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BELL")) {
					db = ((new BellDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ROCKWELL")) {
					db = ((new RockwellCollinsDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SABRELINER")) {
					db = ((new SabrelinerDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BOEING_CO")) {
					db = ((new BoeingCoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("WOODWARD")) {
					db = ((new WoodwardDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TAIWAN_AIR_FORCE")) {
					db = ((new TaiwanAirForceDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AEROJET")) {
					db = ((new AerojetDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (client.equalsIgnoreCase("MERCK")) {
					db = ((new MerckDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (client.equalsIgnoreCase("SCHAFER_GEAR")) {
					db = ((new SchaferDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (client.equalsIgnoreCase("FINMECCANICA") || client.equalsIgnoreCase("FNC")) {
					db = ((new FNCDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("CESSNA")) {
					db = ((new CessnaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("WHIPPANY_ACTUATION_SYSTEMS")) {
					db = ((new WASDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
			    else if (cl.equalsIgnoreCase("MAGNA")) {
			    	db = ((new MagnaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
			    else if (cl.equalsIgnoreCase("ACUMENT")) {
			    	db = ((new AcumentDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
			    else if (client.equalsIgnoreCase("JTEKT")) {
			    	db = ((new JtektDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
			    else if (cl.equalsIgnoreCase("REVIMA")) {
			    	db = ((new RevimaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
			    else if (cl.equalsIgnoreCase("BEECHCRAFT")) {
			    	db = ((new BeechcraftDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
			    else if (cl.equalsIgnoreCase("AIM")) {
			    	db = ((new AIMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
			    }
				else if (cl.equalsIgnoreCase("DKSH")) {
					db = ((new DkshDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("IBM")) {
					db = ((new IBMDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("PCC")) {
					db = ((new PCCDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("HUDSON")) {
					db = ((new HudsonDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("MITSUBISHI")) {
					db = ((new MitsubishiDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("EMBRAER")) {
					db = ((new EmbraerDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("LATECOERE")) {
					db = ((new LatecoereDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SAMSUNG")) {
					db = ((new SamsungDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AIRCELLE")) {
					db = ((new AircelleDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TRIUMPH")) {
					db = ((new TriumphDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("NORWEGIAN")) {
					db = ((new NorwegianDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("CPP")) {
					db = ((new CPPDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("SAFRAN")) {
					db = ((new SafranDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AJW")) {
					db = ((new AjwDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("BOMBARDIER")) {
					db = ((new BombardierDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TPL_LL")) {
					db = ((new Tpl_llDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TPL_DHL")) {
					db = ((new Tpl_DhlDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("TPL_KPT")) {
					db = ((new Tpl_KptDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AMT")) {
					db = ((new AmtDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (client.equalsIgnoreCase("SPIRIT")) {
					db = (new SpiritDBConnectionFactory()).getInstance().getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AAM")) {
					db = ((new AamDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("AERNNOVA")) {
					db = ((new AernnovaDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("EATON")) {
					db = ((new EatonDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("MAGELLAN")) {
					db = ((new MagellanDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("JDEERE")) {
					db = ((new JDeereDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("ZODIAC")) {
					db = ((new ZodiacDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("KAMAN")) {
					db = ((new KamanDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
//				else if (cl.equalsIgnoreCase("CATALOG_VENDOR")) {
//					db = ((new CatalogVendorDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
//				}
				else if (cl.equalsIgnoreCase("SAS_TECH")) {
					db = ((new SasTechDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
				else if (cl.equalsIgnoreCase("PFS")) {
					db = ((new PfsDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
				}
//Next Customer Config Auto Gen Tag1
				connect = db.getConnection();
				DatabaseMetaData dbdma = connect.getMetaData();
				// set dbUrl
				this.dbUrl = dbdma.getURL();
				validConnection = true;
			}
			catch (Throwable ex) {
				ex.printStackTrace();
				this.close();
				validConnection = false;
			}
			count++;
	}
		while (!validConnection && count < 11);
	}

	/**
	 * Close db connection
	 */
	public void close() {
		try {
			if (client.equalsIgnoreCase("Ray")) {
				(new RayDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("DRS")) {
				(new DRSDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("LMCO")) {
				(new LMCODBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("SWA")) {
				(new SWADBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("Seagate")) {
				(new SeagateDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("Tcm_Ops")) {
				(new InternalDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BAE")) {
				(new BAEDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("UTC")) {
				(new UTCDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("SD")) {
				(new SDDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("Miller")) {
				(new MillerDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("GM")) {
				(new GMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("CAL")) {
				(new CALDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("FEC")) {
				(new FECDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("Boeing")) {
				(new BoeingDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("AeroEco")) {
				(new AeroEcoDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("Dana")) {
				(new DanaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("ABLaero")) {
				(new ABLaeroDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("DOE")) {
				(new DOEDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																									// 7-25
			}
			else if (client.equalsIgnoreCase("supplier")) {
				(new supplierDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion); // trong
																										// 7-25
			}
			else if (client.equalsIgnoreCase("L3")) {
				(new L3DBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("IAI")) {
				(new IAIDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AM")) {
				(new AMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("GEMA")) {
				(new GemaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("PGE")) {
				(new PGEDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("QOS")) {
				(new QOSDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("DCX")) {
				(new DCXDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Ford")) {
				(new FordDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Algat")) {
				(new AlgatDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BAZ")) {
				(new BAZDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("CMM")) {
				(new CMMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Kanfit")) {
				(new KanfitDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Fedco")) {
				(new FedcoDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("GD")) {
				(new GDDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("IMCO")) {
				(new IMCODBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Kemfast")) {
				(new KemfastDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BL")) {
				(new BLDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Verasun")) {
				(new VerasunDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Hrgivon")) {
				(new HrgivonDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Haargaz")) {
				(new HaargazDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Cyclone")) {
				(new CycloneDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Volvo")) {
				(new VolvoDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Nalco")) {
				(new NalcoDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Pepsi")) {
				(new PepsiDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("HAL")) {
				(new HALDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ABM")) {
				(new ABMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MTL")) {
				(new MTLDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Timken")) {
				(new TimkenDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("NRG")) {
				(new NRGDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MAEET")) {
				(new MAEETDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SGD")) {
				(new SGDDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("USGOV")) {
				(new USGOVDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Caterpillar")) {
				(new CaterpillarDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("OMA")) {
				(new OMADBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Siemens")) {
				(new SiemensDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Getrag")) {
				(new GetragDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Alcoa")) {
				(new AlcoaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ZWL")) {
				(new ZWLDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("HansSasserath")) {
				(new HansSasserathDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("DD")) {
				(new DDDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("GKN")) {
				(new GKNDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Aeropia")) {
				(new AeropiaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Goodrich")) {
				(new GoodrichDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("OMMC")) {
				(new OMMCDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("FBM")) {
				(new FBMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Aerocz")) {
				(new AeroczDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BAI")) {
				(new BAIDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Optimetal")) {
				(new OptimetalDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Orlite")) {
				(new OrliteDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Tambour")) {
				(new TambourDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Team")) {
				(new TeamDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("GE")) {
				(new GEDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Sales")) {
				(new SalesDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Hexcel")) {
				(new HexcelDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ZF")) {
				(new ZFDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("Avcorp")) {
				(new AvcorpDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BA")) {
				(new BADBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BOEING_COMAIR")) {
				(new BoeingComairDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ROLLS_ROYCE")) {
				(new RollsRoyceDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("STANDARD_AERO")) {
				(new StandardAeroDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("HAI")) {
				(new HAIDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("KOREA_AEROSPACE_INDUSTRIES")) {
				(new KAIDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("NORTHROP_GRUMMAN")) {
				(new NorthropGrummanDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TAI")) {
				(new TAIDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TOMKINS")) {
				(new TomkinsDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MOOG")) {
				(new MoogDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BELL")) {
				(new BellDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ROCKWELL")) {
				(new RockwellCollinsDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SABRELINER")) {
				(new SabrelinerDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BOEING_CO")) {
				(new BoeingCoDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("WOODWARD")) {
				(new WoodwardDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TAIWAN_AIR_FORCE")) {
				(new TaiwanAirForceDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AEROJET")) {
				(new AerojetDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("FINMECCANICA") || client.equalsIgnoreCase("FNC")) {
				(new FNCDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SCHAFER_GEAR")) {
				(new SchaferDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MERCK")) {
				(new MerckDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("CESSNA")) {
				(new CessnaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("WHIPPANY_ACTUATION_SYSTEMS")) {
				(new WASDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MAGNA")) {
				(new MagnaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ACUMENT")) {
				(new AcumentDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("JTEKT")) {
				(new JtektDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("REVIMA")) {
				(new RevimaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BEECHCRAFT")) {
				(new BeechcraftDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AIM")) {
				(new AIMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("DKSH")) {
				(new DkshDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("IBM")) {
				(new IBMDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("PCC")) {
				(new PCCDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("HUDSON")) {
				(new HudsonDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MITSUBISHI")) {
				(new MitsubishiDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("EMBRAER")) {
				(new EmbraerDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("LATECOERE")) {
				(new LatecoereDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SAMSUNG")) {
				(new SamsungDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AIRCELLE")) {
				(new AircelleDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TRIUMPH")) {
				(new TriumphDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("NORWEGIAN")) {
				(new NorwegianDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("CPP")) {
				(new CPPDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SAFRAN")) {
				(new SafranDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AJW")) {
				(new AjwDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("BOMBARDIER")) {
				(new BombardierDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TPL_LL")) {
				(new Tpl_llDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TPL_DHL")) {
				(new Tpl_DhlDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("TPL_KPT")) {
				(new Tpl_KptDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AMT")) {
					(new AmtDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("SPIRIT")) {
				(new SpiritDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("AAM")) {
				(new AamDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
		}
			else if (client.equalsIgnoreCase("AERNNOVA")) {
					(new AernnovaDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("EATON")) {
					(new EatonDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("MAGELLAN")) {
				(new MagellanDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("JDEERE")) {
				(new JDeereDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("ZODIAC")) {
					(new ZodiacDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("KAMAN")) {
					(new KamanDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
//			else if (client.equalsIgnoreCase("CATALOG_VENDOR")) {
//					(new CatalogVendorDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
//			}
			else if (client.equalsIgnoreCase("SAS_TECH")) {
					(new SasTechDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
			else if (client.equalsIgnoreCase("PFS")) {
					(new PfsDBConnectionFactory()).getInstance().releaseConnection(db, logonVersion);
			}
//Next Customer Config Auto Gen Tag2
		}
		catch (Exception ex) {
			log.error(">>>>>>>>>> Could not RELEASE the DB:" + client + " <<<<<<<<<<<<<");
		}
	}

	// this method determine whether to turn on the debug
	public boolean getDebugStatus() {
		boolean result = false;
		try {
			// PROD
			deBugresource = new ResourceLibrary("DebugResource");
			String showDebugfromfile = deBugresource.getString("debug.show");
			if ("Yes".equalsIgnoreCase(showDebugfromfile)) {
				result = true;
			}

			// result = true; //local since I am unable to find debugResource
		}
		catch (Exception debuge) {
			debuge.printStackTrace();
			result = false;
		}

		return result;
	}

	/**
	 * Execute query and return ResultSet
	 */
	public DBResultSet doQuery(String query) throws Exception {
		// 9-24-01 NOT sure if this is the right way to do it.
		// boolean showDebug = getDebugStatus();

		// starts a thread for time-out
		Thread cT = Thread.currentThread();
		QueryThread_TcmISDBModel queryT = new QueryThread_TcmISDBModel(this, query, cT, this.showDebugMain);
		// try
		// {
		long timeout = 240000; // 4min
		// long timeout = 5000; // 4min
		for (int i = 0; i < longer_tables.length; i++) {
			if (query.toLowerCase().indexOf(longer_tables[i].toLowerCase()) > -1) {
				timeout = 3600000; // 1 hr
				break;
			}
		}
		queryT.setDoneFlag(false);
		queryT.start();
		int count = 0;
		do {
			cT.sleep(100);
			count++;
		}
		while (!queryT.getDoneFlag() && count <= (timeout / 100));
		// if query run for longer then time specified
		if (count > timeout / 100) {
			db.setSqlTimeOut(true);
			queryT.interrupt();
			// sqlTimeOut = true;
			radian.web.emailHelpObj.senddeveloperemail("Query Time Out", "Query Time Out:\n " + query + ""); // send
																												// myself
																												// email
																												// everytime
																												// a
																												// query
																												// takes
																												// longer
																												// than
																												// 2
																												// minutes
																												// to
																												// run.
			throw new Exception("Time out on query : \n" + query + " \n");
		}
		else {
			db.setSqlTimeOut(false);
			if (dbrs == null) {
				throw queryException;
			}
			return dbrs;
		}
		/*
		 * } catch (InterruptedException e) { if (dbrs == null) throw
		 * queryException; return dbrs; }
		 */
		// HelpObjs.sendMail("Query Time Out",query,"tcmIS@urscorp.com",new
		// String[]{"trong_ngo@urscorp.com"}); //send myself email everytime a
		// query takes longer than 2 minutes to run.
		// throw new Exception("Time out on query : \n"+query+" \n");
	}

	public DBResultSet doQuery(String query, boolean logQuery) throws Exception {
		// 9-24-01 NOT sure if this is the right way to do it.
		// boolean showDebug = getDebugStatus();

		// starts a thread for time-out
		Thread cT = Thread.currentThread();
		QueryThread_TcmISDBModel queryT = new QueryThread_TcmISDBModel(this, query, cT, logQuery);
		// try
		// {
		long timeout = 240000; // 4min
		// long timeout = 5000; // 4min
		for (int i = 0; i < longer_tables.length; i++) {
			if (query.toLowerCase().indexOf(longer_tables[i].toLowerCase()) > -1) {
				timeout = 3600000; // 1 hr
				break;
			}
		}
		queryT.setDoneFlag(false);
		queryT.start();
		int count = 0;
		do {
			cT.sleep(100);
			count++;
		}
		while (!queryT.getDoneFlag() && count <= (timeout / 100));
		// if query run for longer then time specified
		if (count > timeout / 100) {
			db.setSqlTimeOut(true);
			queryT.interrupt();
			// sqlTimeOut = true;
			radian.web.emailHelpObj.senddeveloperemail("Query Time Out", "Query Time Out:\n " + query + ""); // send
																												// myself
																												// email
																												// everytime
																												// a
																												// query
																												// takes
																												// longer
																												// than
																												// 2
																												// minutes
																												// to
																												// run.
			throw new Exception("Time out on query : \n" + query + " \n");
		}
		else {
			db.setSqlTimeOut(false);
			if (dbrs == null) {
				throw queryException;
			}
			return dbrs;
		}
		/*
		 * } catch (InterruptedException e) { if (dbrs == null) throw
		 * queryException; return dbrs; }
		 */
		// HelpObjs.sendMail("Query Time Out",query,"tcmIS@urscorp.com",new
		// String[]{"trong_ngo@urscorp.com"}); //send myself email everytime a
		// query takes longer than 2 minutes to run.
		// throw new Exception("Time out on query : \n"+query+" \n");
	}

	public void doUpdate(String query) throws Exception {
		Statement st = null;
		if (log.isDebugEnabled()) {
			log.debug(query);
		}
		String error_stmt = null;
		try {
			error_stmt = "createStatement";
			st = connect.createStatement();
			error_stmt = "executeUpdate";
			int returnVal = st.executeUpdate(query);
			// log.debug("\n\n-------- return value from doUpdate: "+returnVal);
		}
		catch (Exception ex) {
			if (ex.getMessage().indexOf("ORA") > -1) {
				radian.web.emailHelpObj.sendnishemail(" Query ORA ERROR : " + ex.getMessage() + "",
						"Query Error\n Error Message: \n " + ex.getMessage() + "\n Query:\n " + query + "");
			}

			throw ex;
		}
		finally {
			try {
				if (st != null) {
					connect.commit();
					st.close();
				}
			}
			catch (SQLException ex2) {
				String state = ex2.getSQLState();
				int errorCode = ex2.getErrorCode();
				log.error("commit error: ********state: " + state + " error code: " + errorCode);
			}
		}
	}

	public void doMultipleUpdate(Vector query) throws SQLException {
		Statement st = null;
		if (log.isDebugEnabled()) {
			log.debug(query);
		}
		String error_stmt = null;
		try {
			error_stmt = "createStatement";
			st = connect.createStatement();
			error_stmt = "executeUpdate";
			for (int i = 0; i < query.size(); i++) {
				int returnVal = st.executeUpdate((String) query.elementAt(i));
			}
			// log.debug("\n\n-------- return value from doUpdate: "+returnVal);
		}
		catch (SQLException ex) {
			log.error("update error:" + error_stmt + " " + query);
			String state = ex.getSQLState();
			int errorCode = ex.getErrorCode();
			log.error("update error: state: " + state + " error code: " + errorCode);
			while (ex != null) {
				log.error(ex.toString());
				ex = ex.getNextException();
			}
			throw ex;
		}
		finally {
			try {
				if (st != null) {
					connect.commit();
					st.close();
				}
			}
			catch (SQLException ex2) {
				String state = ex2.getSQLState();
				int errorCode = ex2.getErrorCode();
				log.error("commit error: state: " + state + " error code: " + errorCode);
				while (ex2 != null) {
					log.error(ex2.toString());
					ex2 = ex2.getNextException();
				}
			}
		}
	}

	public void doJDEProcedure(String procEnv, String procName, String[] args) throws Exception {

		String query = "{call " + procEnv + "." + procName + "@jde(";
		for (int i = 0; i < args.length; i++) {
			query = query + "?,";
		}
		// remove the extra comma (,)
		query = query.substring(0, query.length() - 1);
		query = query + ")}";
		doProcedure(query, args);
	}

	public void doProcedure(String procName, String[] args) throws Exception {

		// build a new connection just for that
		Connection newConnect = null;
		if (client.equalsIgnoreCase("Ray")) {
			newConnect = (new RayDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DRS")) {
			newConnect = (new DRSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LMCO")) {
			newConnect = (new LMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SWA")) {
			newConnect = (new SWADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Seagate")) {
			newConnect = (new SeagateDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tcm_Ops")) {
			newConnect = (new InternalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAE")) {
			newConnect = (new BAEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("UTC")) {
			newConnect = (new UTCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SD")) {
			newConnect = (new SDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Miller")) {
			newConnect = (new MillerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GM")) {
			newConnect = (new GMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CAL")) {
			newConnect = (new CALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FEC")) {
			newConnect = (new FECDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Boeing")) {
			newConnect = (new BoeingDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AeroEco")) {
			newConnect = (new AeroEcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Dana")) {
			newConnect = (new DanaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABLaero")) {
			newConnect = (new ABLaeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DOE")) {
			newConnect = (new DOEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("supplier")) {
			newConnect = (new supplierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("L3")) {
			newConnect = (new L3DBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IAI")) {
			newConnect = (new IAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AM")) {
			newConnect = (new AMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GEMA")) {
			newConnect = (new GemaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PGE")) {
			newConnect = (new PGEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("QOS")) {
			newConnect = (new QOSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DCX")) {
			newConnect = (new DCXDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Ford")) {
			newConnect = (new FordDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Algat")) {
			newConnect = (new AlgatDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAZ")) {
			newConnect = (new BAZDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CMM")) {
			newConnect = (new CMMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kanfit")) {
			newConnect = (new KanfitDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Fedco")) {
			newConnect = (new FedcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GD")) {
			newConnect = (new GDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IMCO")) {
			newConnect = (new IMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kemfast")) {
			newConnect = (new KemfastDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BL")) {
			newConnect = (new BLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Verasun")) {
			newConnect = (new VerasunDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hrgivon")) {
			newConnect = (new HrgivonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Haargaz")) {
			newConnect = (new HaargazDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Cyclone")) {
			newConnect = (new CycloneDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Volvo")) {
			newConnect = (new VolvoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Nalco")) {
			newConnect = (new NalcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Pepsi")) {
			newConnect = (new PepsiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAL")) {
			newConnect = (new HALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABM")) {
			newConnect = (new ABMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MTL")) {
			newConnect = (new MTLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Timken")) {
			newConnect = (new TimkenDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NRG")) {
			newConnect = (new NRGDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAEET")) {
			newConnect = (new MAEETDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SGD")) {
			newConnect = (new SGDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("USGOV")) {
			newConnect = (new USGOVDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Caterpillar")) {
			newConnect = (new CaterpillarDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMA")) {
			newConnect = (new OMADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Siemens")) {
			newConnect = (new SiemensDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Getrag")) {
			newConnect = (new GetragDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Alcoa")) {
			newConnect = (new AlcoaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZWL")) {
			newConnect = (new ZWLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HansSasserath")) {
			newConnect = (new HansSasserathDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DD")) {
			newConnect = (new DDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GKN")) {
			newConnect = (new GKNDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aeropia")) {
			newConnect = (new AeropiaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Goodrich")) {
			newConnect = (new GoodrichDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMMC")) {
			newConnect = (new OMMCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FBM")) {
			newConnect = (new FBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aerocz")) {
			newConnect = (new AeroczDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAI")) {
			newConnect = (new BAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Optimetal")) {
			newConnect = (new OptimetalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Orlite")) {
			newConnect = (new OrliteDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tambour")) {
			newConnect = (new TambourDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Team")) {
			newConnect = (new TeamDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GE")) {
			newConnect = (new GEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Sales")) {
			newConnect = (new SalesDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hexcel")) {
			newConnect = (new HexcelDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZF")) {
			newConnect = (new ZFDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Avcorp")) {
			newConnect = (new AvcorpDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BA")) {
			newConnect = (new BADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_COMAIR")) {
			newConnect = (new BoeingComairDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROLLS_ROYCE")) {
			newConnect = (new RollsRoyceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("STANDARD_AERO")) {
			newConnect = (new StandardAeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAI")) {
			newConnect = (new HAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KOREA_AEROSPACE_INDUSTRIES")) {
			newConnect = (new KAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORTHROP_GRUMMAN")) {
			newConnect = (new NorthropGrummanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAI")) {
			newConnect = (new TAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TOMKINS")) {
			newConnect = (new TomkinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MOOG")) {
			newConnect = (new MoogDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BELL")) {
			newConnect = (new BellDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROCKWELL")) {
			newConnect = (new RockwellCollinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SABRELINER")) {
			newConnect = (new SabrelinerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_CO")) {
			db = ((new BoeingCoDBConnectionFactory()).getInstance()).getConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WOODWARD")) {
			newConnect = (new WoodwardDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAIWAN_AIR_FORCE")) {
			newConnect = (new TaiwanAirForceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AEROJET")) {
			newConnect = (new AerojetDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MERCK")) {
			newConnect = (new MerckDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SCHAFER_GEAR")) {
			newConnect = (new SchaferDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FINMECCANICA") || client.equalsIgnoreCase("FNC")) {
			newConnect = (new FNCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CESSNA")) {
			newConnect = (new CessnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WHIPPANY_ACTUATION_SYSTEMS")) {
			newConnect = (new WASDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGNA")) {
			newConnect = (new MagnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ACUMENT")) {
			newConnect = (new AcumentDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JTEKT")) {
			newConnect = (new JtektDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("REVIMA")) {
			newConnect = (new RevimaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BEECHCRAFT")) {
			newConnect = (new BeechcraftDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AIM")) {
			newConnect = (new AIMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DKSH")) {
			newConnect = (new DkshDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IBM")) {
			newConnect = (new IBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PCC")) {
			newConnect = (new PCCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HUDSON")) {
			newConnect = (new HudsonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MITSUBISHI")) {
			newConnect = (new MitsubishiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EMBRAER")) {
			newConnect = (new EmbraerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LATECOERE")) {
			newConnect = (new LatecoereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAMSUNG")) {
			newConnect = (new SamsungDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AIRCELLE")) {
			newConnect = (new AircelleDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TRIUMPH")) {
			newConnect = (new TriumphDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORWEGIAN")) {
			newConnect = (new NorwegianDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CPP")) {
			newConnect = (new CPPDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAFRAN")) {
			newConnect = (new SafranDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AJW")) {
			newConnect = (new AjwDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOMBARDIER")) {
			newConnect = (new BombardierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_LL")) {
			newConnect = (new Tpl_llDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_DHL")) {
			newConnect = (new Tpl_DhlDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_KPT")) {
			newConnect = (new Tpl_KptDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AMT")) {
			newConnect = (new AmtDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SPIRIT")) {
			newConnect = (new SpiritDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AERNNOVA")) {
			newConnect = (new AernnovaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EATON")) {
			newConnect = (new EatonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGELLAN")) {
			newConnect = (new MagellanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JDEERE")) {
			newConnect = (new JDeereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZODIAC")) {
			newConnect = (new ZodiacDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KAMAN")) {
			newConnect = (new KamanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//		else if (client.equalsIgnoreCase("CATALOG_VENDOR")) {
//			newConnect = (new CatalogVendorDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
//		}
		else if (client.equalsIgnoreCase("SAS_TECH")) {
			newConnect = (new SasTechDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PFS")) {
			newConnect = (new PfsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//Next Customer Config Auto Gen Tag3
		CallableStatement cs = null;
		try {
			String query = "{call " + procName + "(";
			for (int i = 0; i < args.length; i++) {
				query = query + "?,";
			}
			// remove the extra comma (,)
			query = query.substring(0, query.length() - 1);
			query = query + ")}";
			cs = newConnect.prepareCall(query);
			for (int x = 0; x < args.length; x++) {
				cs.setString(x + 1, args[x]);
				if ("p_issue_insert".equalsIgnoreCase(procName)) {
					if (x == 5 || x == 7) {
						java.util.Date myDate = new java.util.Date(BothHelpObjs.formatDate("toCharfromDB", args[x]));
						cs.setDate(x + 1, new java.sql.Date(myDate.getTime()));
					}
				}
			}
			cs.execute();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (cs != null) {
				cs.close();
			}
			(new RayDBConnectionFactory()).getInstance().returnConnection(newConnect, logonVersion);
		}
	}

	// trong 3-8-01 this will call a store procedure and await for output
	public String doInvoiceProcedure(String procName, Vector args) throws Exception {
		String errorVal = "";
		// build a new connection just for that
		Connection newConnect = null;
		if (client.equalsIgnoreCase("Ray")) {
			newConnect = (new RayDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DRS")) {
			newConnect = (new DRSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LMCO")) {
			newConnect = (new LMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SWA")) {
			newConnect = (new SWADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Seagate")) {
			newConnect = (new SeagateDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tcm_Ops")) {
			newConnect = (new InternalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAE")) {
			newConnect = (new BAEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("UTC")) {
			newConnect = (new UTCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SD")) {
			newConnect = (new SDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Miller")) {
			newConnect = (new MillerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GM")) {
			newConnect = (new GMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CAL")) {
			newConnect = (new CALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FEC")) {
			newConnect = (new FECDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Boeing")) {
			newConnect = (new BoeingDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AeroEco")) {
			newConnect = (new AeroEcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Dana")) {
			newConnect = (new DanaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABLaero")) {
			newConnect = (new ABLaeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DOE")) {
			newConnect = (new DOEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("supplier")) {
			newConnect = (new supplierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("L3")) {
			newConnect = (new L3DBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IAI")) {
			newConnect = (new IAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AM")) {
			newConnect = (new AMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GEMA")) {
			newConnect = (new GemaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PGE")) {
			newConnect = (new PGEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("QOS")) {
			newConnect = (new QOSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DCX")) {
			newConnect = (new DCXDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Ford")) {
			newConnect = (new FordDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Algat")) {
			newConnect = (new AlgatDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAZ")) {
			newConnect = (new BAZDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CMM")) {
			newConnect = (new CMMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kanfit")) {
			newConnect = (new KanfitDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Fedco")) {
			newConnect = (new FedcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GD")) {
			newConnect = (new GDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IMCO")) {
			newConnect = (new IMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kemfast")) {
			newConnect = (new KemfastDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BL")) {
			newConnect = (new BLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Verasun")) {
			newConnect = (new VerasunDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hrgivon")) {
			newConnect = (new HrgivonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Haargaz")) {
			newConnect = (new HaargazDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Cyclone")) {
			newConnect = (new CycloneDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Volvo")) {
			newConnect = (new VolvoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Nalco")) {
			newConnect = (new NalcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Pepsi")) {
			newConnect = (new PepsiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAL")) {
			newConnect = (new HALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABM")) {
			newConnect = (new ABMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MTL")) {
			newConnect = (new MTLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Timken")) {
			newConnect = (new TimkenDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NRG")) {
			newConnect = (new NRGDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAEET")) {
			newConnect = (new MAEETDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SGD")) {
			newConnect = (new SGDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("USGOV")) {
			newConnect = (new USGOVDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Caterpillar")) {
			newConnect = (new CaterpillarDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMA")) {
			newConnect = (new OMADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Siemens")) {
			newConnect = (new SiemensDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Getrag")) {
			newConnect = (new GetragDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Alcoa")) {
			newConnect = (new AlcoaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZWL")) {
			newConnect = (new ZWLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HansSasserath")) {
			newConnect = (new HansSasserathDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DD")) {
			newConnect = (new DDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GKN")) {
			newConnect = (new GKNDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aeropia")) {
			newConnect = (new AeropiaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Goodrich")) {
			newConnect = (new GoodrichDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMMC")) {
			newConnect = (new OMMCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FBM")) {
			newConnect = (new FBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aerocz")) {
			newConnect = (new AeroczDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAI")) {
			newConnect = (new BAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Optimetal")) {
			newConnect = (new OptimetalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Orlite")) {
			newConnect = (new OrliteDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tambour")) {
			newConnect = (new TambourDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Team")) {
			newConnect = (new TeamDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GE")) {
			newConnect = (new GEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Sales")) {
			newConnect = (new SalesDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hexcel")) {
			newConnect = (new HexcelDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZF")) {
			newConnect = (new ZFDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Avcorp")) {
			newConnect = (new AvcorpDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BA")) {
			newConnect = (new BADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_COMAIR")) {
			newConnect = (new BoeingComairDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROLLS_ROYCE")) {
			newConnect = (new RollsRoyceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("STANDARD_AERO")) {
			newConnect = (new StandardAeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAI")) {
			newConnect = (new HAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KOREA_AEROSPACE_INDUSTRIES")) {
			newConnect = (new KAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORTHROP_GRUMMAN")) {
			newConnect = (new NorthropGrummanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAI")) {
			newConnect = (new TAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TOMKINS")) {
			newConnect = (new TomkinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MOOG")) {
			newConnect = (new MoogDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}

		else if (client.equalsIgnoreCase("BELL")) {
			newConnect = (new BellDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROCKWELL")) {
			newConnect = (new RockwellCollinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SABRELINER")) {
			newConnect = (new SabrelinerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_CO")) {
			newConnect = (new BoeingCoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WOODWARD")) {
			newConnect = (new WoodwardDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAIWAN_AIR_FORCE")) {
			newConnect = (new TaiwanAirForceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AEROJET")) {
			newConnect = (new AerojetDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FINMECCANICA") || client.equalsIgnoreCase("FNC")) {
			newConnect = (new FNCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SCHAFER_GEAR")) {
			newConnect = (new SchaferDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MERCK")) {
			newConnect = (new MerckDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CESSNA")) {
			newConnect = (new CessnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WHIPPANY_ACTUATION_SYSTEMS")) {
			newConnect = (new WASDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGNA")) {
			newConnect = (new MagnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ACUMENT")) {
			newConnect = (new AcumentDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JTEKT")) {
			newConnect = (new JtektDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("REVIMA")) {
			newConnect = (new RevimaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BEECHCRAFT")) {
			newConnect = (new BeechcraftDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AIM")) {
			newConnect = (new AIMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DKSH")) {
			newConnect = (new DkshDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IBM")) {
			newConnect = (new IBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PCC")) {
			newConnect = (new PCCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HUDSON")) {
			newConnect = (new HudsonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MITSUBISHI")) {
			newConnect = (new MitsubishiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EMBRAER")) {
			newConnect = (new EmbraerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LATECOERE")) {
			newConnect = (new LatecoereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAMSUNG")) {
			newConnect = (new SamsungDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AIRCELLE")) {
			newConnect = (new AircelleDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TRIUMPH")) {
			newConnect = (new TriumphDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORWEGIAN")) {
			newConnect = (new NorwegianDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CPP")) {
			newConnect = (new CPPDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAFRAN")) {
			newConnect = (new SafranDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AJW")) {
			newConnect = (new AjwDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOMBARDIER")) {
			newConnect = (new BombardierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_LL")) {
			newConnect = (new Tpl_llDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_DHL")) {
			newConnect = (new Tpl_DhlDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_KPT")) {
			newConnect = (new Tpl_KptDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AMT")) {
			newConnect = (new AmtDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SPIRIT")) {
			newConnect = (new SpiritDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AERNNOVA")) {
			newConnect = (new AernnovaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EATON")) {
			newConnect = (new EatonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGELLAN")) {
			newConnect = (new MagellanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JDEERE")) {
			newConnect = (new JDeereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZODIAC")) {
			newConnect = (new ZodiacDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KAMAN")) {
			newConnect = (new KamanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//		else if (client.equalsIgnoreCase("CATALOG_VENDOR")) {
//			newConnect = (new CatalogVendorDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
//		}
		else if (client.equalsIgnoreCase("SAS_TECH")) {
			newConnect = (new SasTechDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PFS")) {
			newConnect = (new PfsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//Next Customer Config Auto Gen Tag3
		CallableStatement cs = null;
		try {
			String query = "{call " + procName + "(";
			for (int i = 0; i < args.size(); i++) {
				query = query + "?,";
			}
			// remove the extra comma (,)
			query = query.substring(0, query.length() - 1);
			query = query + ")}";
			cs = newConnect.prepareCall(query);
			for (int x = 0; x < args.size(); x++) {
				cs.setString(x + 1, (String) args.elementAt(x));
			}
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);

			cs.execute();
			errorVal = BothHelpObjs.makeBlankFromNull(cs.getString(2));
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (cs != null) {
				cs.close();
			}
			(new RayDBConnectionFactory()).getInstance().returnConnection(newConnect, logonVersion);
		}
		return errorVal;
	}

	public String doProcedureWithErrorMsg(String procName, Vector args, int errorPosition) throws Exception {
		String errorVal = "";
		// build a new connection just for that
		Connection newConnect = null;

		if (client.equalsIgnoreCase("Ray")) {
			newConnect = (new RayDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DRS")) {
			newConnect = (new DRSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LMCO")) {
			newConnect = (new LMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SWA")) {
			newConnect = (new SWADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Seagate")) {
			newConnect = (new SeagateDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tcm_Ops")) {
			newConnect = (new InternalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAE")) {
			newConnect = (new BAEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("UTC")) {
			newConnect = (new UTCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SD")) {
			newConnect = (new SDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Miller")) {
			newConnect = (new MillerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GM")) {
			newConnect = (new GMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CAL")) {
			newConnect = (new CALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FEC")) {
			newConnect = (new FECDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Boeing")) {
			newConnect = (new BoeingDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AeroEco")) {
			newConnect = (new AeroEcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Dana")) {
			newConnect = (new DanaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABLaero")) {
			newConnect = (new ABLaeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DOE")) {
			newConnect = (new DOEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("supplier")) {
			newConnect = (new supplierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("L3")) {
			newConnect = (new L3DBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IAI")) {
			newConnect = (new IAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AM")) {
			newConnect = (new AMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GEMA")) {
			newConnect = (new GemaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PGE")) {
			newConnect = (new PGEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("QOS")) {
			newConnect = (new QOSDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DCX")) {
			newConnect = (new DCXDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Ford")) {
			newConnect = (new FordDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Algat")) {
			newConnect = (new AlgatDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAZ")) {
			newConnect = (new BAZDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CMM")) {
			newConnect = (new CMMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kanfit")) {
			newConnect = (new KanfitDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Fedco")) {
			newConnect = (new FedcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GD")) {
			newConnect = (new GDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IMCO")) {
			newConnect = (new IMCODBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Kemfast")) {
			newConnect = (new KemfastDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BL")) {
			newConnect = (new BLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Verasun")) {
			newConnect = (new VerasunDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hrgivon")) {
			newConnect = (new HrgivonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Haargaz")) {
			newConnect = (new HaargazDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Cyclone")) {
			newConnect = (new CycloneDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Volvo")) {
			newConnect = (new VolvoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Nalco")) {
			newConnect = (new NalcoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Pepsi")) {
			newConnect = (new PepsiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAL")) {
			newConnect = (new HALDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ABM")) {
			newConnect = (new ABMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MTL")) {
			newConnect = (new MTLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Timken")) {
			newConnect = (new TimkenDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NRG")) {
			newConnect = (new NRGDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAEET")) {
			newConnect = (new MAEETDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SGD")) {
			newConnect = (new SGDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("USGOV")) {
			newConnect = (new USGOVDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Caterpillar")) {
			newConnect = (new CaterpillarDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMA")) {
			newConnect = (new OMADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Siemens")) {
			newConnect = (new SiemensDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Getrag")) {
			newConnect = (new GetragDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Alcoa")) {
			newConnect = (new AlcoaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZWL")) {
			newConnect = (new ZWLDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HansSasserath")) {
			newConnect = (new HansSasserathDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DD")) {
			newConnect = (new DDDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GKN")) {
			newConnect = (new GKNDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aeropia")) {
			newConnect = (new AeropiaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Goodrich")) {
			newConnect = (new GoodrichDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("OMMC")) {
			newConnect = (new OMMCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FBM")) {
			newConnect = (new FBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Aerocz")) {
			newConnect = (new AeroczDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BAI")) {
			newConnect = (new BAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Optimetal")) {
			newConnect = (new OptimetalDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Orlite")) {
			newConnect = (new OrliteDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Tambour")) {
			newConnect = (new TambourDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Team")) {
			newConnect = (new TeamDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("GE")) {
			newConnect = (new GEDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Sales")) {
			newConnect = (new SalesDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Hexcel")) {
			newConnect = (new HexcelDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZF")) {
			newConnect = (new ZFDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("Avcorp")) {
			newConnect = (new AvcorpDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BA")) {
			newConnect = (new BADBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_COMAIR")) {
			newConnect = (new BoeingComairDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROLLS_ROYCE")) {
			newConnect = (new RollsRoyceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("STANDARD_AERO")) {
			newConnect = (new StandardAeroDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HAI")) {
			newConnect = (new HAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KOREA_AEROSPACE_INDUSTRIES")) {
			newConnect = (new KAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORTHROP_GRUMMAN")) {
			newConnect = (new NorthropGrummanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAI")) {
			newConnect = (new TAIDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TOMKINS")) {
			newConnect = (new TomkinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MOOG")) {
			newConnect = (new MoogDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BELL")) {
			newConnect = (new BellDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ROCKWELL")) {
			newConnect = (new RockwellCollinsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SABRELINER")) {
			newConnect = (new SabrelinerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOEING_CO")) {
			newConnect = (new BoeingCoDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WOODWARD")) {
			newConnect = (new WoodwardDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TAIWAN_AIR_FORCE")) {
			newConnect = (new TaiwanAirForceDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AEROJET")) {
			newConnect = (new AerojetDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("FINMECCANICA") || client.equalsIgnoreCase("FNC")) {
			newConnect = (new FNCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SCHAFER_GEAR")) {
			newConnect = (new SchaferDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MERCK")) {
			newConnect = (new MerckDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CESSNA")) {
			newConnect = (new CessnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("WHIPPANY_ACTUATION_SYSTEMS")) {
			newConnect = (new WASDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGNA")) {
			newConnect = (new MagnaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ACUMENT")) {
			newConnect = (new AcumentDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JTEKT")) {
			newConnect = (new JtektDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("REVIMA")) {
			newConnect = (new RevimaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
	    else if (client.equalsIgnoreCase("BEECHCRAFT")) {
			newConnect = (new BeechcraftDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
	    }
	    else if (client.equalsIgnoreCase("AIM")) {
			newConnect = (new AIMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("DKSH")) {
			newConnect = (new DkshDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("IBM")) {
			newConnect = (new IBMDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PCC")) {
			newConnect = (new PCCDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("HUDSON")) {
			newConnect = (new HudsonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MITSUBISHI")) {
			newConnect = (new MitsubishiDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EMBRAER")) {
			newConnect = (new EmbraerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("LATECOERE")) {
			newConnect = (new LatecoereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAMSUNG")) {
			newConnect = (new SamsungDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AIRCELLE")) {
			newConnect = (new AircelleDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TRIUMPH")) {
			newConnect = (new TriumphDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("NORWEGIAN")) {
			newConnect = (new NorwegianDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("CPP")) {
			newConnect = (new CPPDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SAFRAN")) {
			newConnect = (new SafranDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AJW")) {
			newConnect = (new AjwDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("BOMBARDIER")) {
			newConnect = (new BombardierDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_LL")) {
			newConnect = (new Tpl_llDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_DHL")) {
			newConnect = (new Tpl_DhlDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("TPL_Kpt")) {
			newConnect = (new Tpl_KptDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AMT")) {
			newConnect = (new AmtDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("SPIRIT")) {
			newConnect = (new SpiritDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("AERNNOVA")) {
			newConnect = (new AernnovaDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("EATON")) {
			newConnect = (new EatonDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("MAGELLAN")) {
			newConnect = (new MagellanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("JDEERE")) {
			newConnect = (new JDeereDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("ZODIAC")) {
			newConnect = (new ZodiacDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("KAMAN")) {
			newConnect = (new KamanDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//		else if (client.equalsIgnoreCase("CATALOG_VENDOR")) {
//			newConnect = (new CatalogVendorDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
//		}
		else if (client.equalsIgnoreCase("SAS_TECH")) {
			newConnect = (new SasTechDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("PFS")) {
			newConnect = (new PfsDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
		else if (client.equalsIgnoreCase("COPRODUCER")) {
			newConnect = (new CoproducerDBConnectionFactory()).getInstance().getFreshConnectionWithLocale(logonVersion, locale);
		}
//Next Customer Config Auto Gen Tag3
		CallableStatement cs = null;
		try {
			String query = "{call " + procName + "(";
			for (int i = 0; i < args.size(); i++) {
				query = query + "?,";
			}
			// remove the extra comma (,)
			query = query.substring(0, query.length() - 1);
			query = query + ")}";
			cs = newConnect.prepareCall(query);
			for (int x = 0; x < args.size(); x++) {
				cs.setString(x + 1, (String) args.elementAt(x));
			}
			cs.registerOutParameter(errorPosition, java.sql.Types.VARCHAR);

			cs.execute();
			errorVal = BothHelpObjs.makeBlankFromNull(cs.getString(errorPosition));
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (cs != null) {
				cs.close();
			}
			(new RayDBConnectionFactory()).getInstance().returnConnection(newConnect, logonVersion);
		}
		return errorVal;
	}

	public String getDBUrl() {
		return dbUrl;
	}

	public Connection getConnection() {
		return connect;
	}

	public void setQueryException(Exception e) {
		queryException = e;
	}

	public void setDBRS(DBResultSet d) {
		dbrs = d;
	}

	// protected abstract ServerResourceBundle getBundle();

	/****************************************************************************
	 * Constructs a <code>DataSet</code> holding the results of the submitted
	 * query.
	 * 
	 * @param query
	 *            the SQL query to run
	 * @throws Exception
	 *             if there are problems communicating with the database
	 ****************************************************************************/
	public DataSet select(String query) throws Exception {
		SqlManager sqlManager = new SqlManager();
		return sqlManager.select(db.getConnection(), query);
	}

	/******************************************************************************
	 * Executes update, insert, or delete query submitted.<BR>
	 * Returns integer resulting from statement.executeUpdate(query). <BR>
	 * 
	 * @param query
	 *            SQL query to be executed.
	 * @return the number of records affected by the SQL query
	 * @throws Exception
	 *             if there are problems communicating with the database
	 *****************************************************************************/
	public int update(String query) throws Exception {
		SqlManager sqlManager = new SqlManager();
		return sqlManager.update(db.getConnection(), query);
	}

}

class QueryThread_TcmISDBModel extends Thread {

	DBResultSet								dbrs			= null;
	TcmISDBModel							parent			= null;
	String									query			= null;
	Thread									pT				= null;
	boolean									showDebugThread	= true;
	boolean									doneFlag		= false;
	private static org.apache.log4j.Logger	log				= org.apache.log4j.Logger.getLogger("");

	public QueryThread_TcmISDBModel(TcmISDBModel p, String q, Thread t, boolean show) {
		super("Query");
		parent = p;
		query = q;
		pT = t;
		showDebugThread = show;
	}

	public void run() {
		try {
			Statement st = null;
			dbrs = new DBResultSet();
			int i = 0;
			if (log.isDebugEnabled()) {
				log.debug(query);
			}
			while (true) {
				i++;
				ResultSet rs = null;
				try {
					st = parent.getConnection().createStatement();
					rs = st.executeQuery(query);

					dbrs.setResultSet(rs);
					dbrs.setStatement(st);
					synchronized (this) {
						parent.setDBRS(dbrs);
						doneFlag = true;
						// pT.interrupt();
					}
					return;
				}
				catch (SQLException ex) {
					HelpObjs.monitor(1, "Error on query: " + query, null);
					if (ex.getMessage().indexOf("ORA") > -1) {
						radian.web.emailHelpObj.sendnishemail(" Query ORA ERROR : " + ex.getMessage() + "",
								"Query Error\n Error Message: \n " + ex.getMessage() + "\n Query:\n " + query + "");
					}

					if (i > 3) {
						synchronized (this) {
							parent.setDBRS(null);
							parent.setQueryException(ex);
							doneFlag = true;
							// pT.interrupt();
						}
						return;
					}

					if (ex.getMessage().indexOf("ORA-00942") > -1) { // no table
																		// found
						try {
							Thread.currentThread().sleep(2000);
						}
						catch (Exception e) {

						} ;
						continue;
					}
					synchronized (this) {
						parent.setDBRS(null);
						parent.setQueryException(ex);
						doneFlag = true;
					}
					return;
				}
			}
		}
		catch (Exception e) {
			parent.setDBRS(null);
			parent.setQueryException(e);
			doneFlag = true;
		}
	}

	public synchronized boolean getDoneFlag() {
		return doneFlag;
	}

	public synchronized void setDoneFlag(boolean b) {
		doneFlag = b;
	}

	public DBResultSet getDBResultSet() {
		return dbrs;
	}
}
