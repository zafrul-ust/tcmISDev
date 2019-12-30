package radian.tcmis.server7.share.dbObjs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.bae.dbObjs.BAETcmISDBModel;
import radian.tcmis.server7.client.cal.dbObjs.CALTcmISDBModel;
import radian.tcmis.server7.client.drs.dbObjs.DRSTcmISDBModel;
import radian.tcmis.server7.client.gm.dbObjs.GMTcmISDBModel;
import radian.tcmis.server7.client.lmco.dbObjs.LMCOTcmISDBModel;
import radian.tcmis.server7.client.miller.dbObjs.MillerTcmISDBModel;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.sd.dbObjs.SDTcmISDBModel;
import radian.tcmis.server7.client.seagate.dbObjs.SeagateTcmISDBModel;
import radian.tcmis.server7.client.swa.dbObjs.SWATcmISDBModel;
import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.client.fec.dbObjs.FECTcmISDBModel;
import radian.tcmis.server7.client.boeing.dbObjs.BoeingTcmISDBModel;
import radian.tcmis.server7.client.ael.dbObjs.AeroEcoTcmISDBModel;
import radian.tcmis.server7.client.dana.dbObjs.DanaTcmISDBModel;
import radian.tcmis.server7.client.ablaero.dbObjs.ABLaeroTcmISDBModel;
import radian.tcmis.server7.client.doe.dbObjs.DOETcmISDBModel;
import radian.tcmis.server7.client.iai.dbObjs.IAITcmISDBModel;
import radian.tcmis.server7.client.l3.dbObjs.L3TcmISDBModel;
import radian.tcmis.server7.client.am.dbObjs.AMTcmISDBModel;
import radian.tcmis.server7.client.gema.dbObjs.GemaTcmISDBModel;
import radian.tcmis.server7.client.pge.dbObjs.PGETcmISDBModel;
import radian.tcmis.server7.client.qos.dbObjs.QOSTcmISDBModel;
import radian.tcmis.server7.client.dcx.dbObjs.DCXTcmISDBModel;
import radian.tcmis.server7.client.ford.dbObjs.FordTcmISDBModel;
import radian.tcmis.server7.client.algat.dbObjs.AlgatTcmISDBModel;
import radian.tcmis.server7.client.baz.dbObjs.BAZTcmISDBModel;
import radian.tcmis.server7.client.cmm.dbObjs.CMMTcmISDBModel;
import radian.tcmis.server7.client.kanfit.dbObjs.KanfitTcmISDBModel;
import radian.tcmis.server7.client.fedco.dbObjs.FedcoTcmISDBModel;
import radian.tcmis.server7.client.gd.dbObjs.GDTcmISDBModel;
import radian.tcmis.server7.client.imco.dbObjs.IMCOTcmISDBModel;
import radian.tcmis.server7.client.kemfast.dbObjs.KemfastTcmISDBModel;
import radian.tcmis.server7.client.bl.dbObjs.BLTcmISDBModel;
import radian.tcmis.server7.client.verasun.dbObjs.VerasunTcmISDBModel;
import radian.tcmis.server7.client.hrgivon.dbObjs.HrgivonTcmISDBModel;
import radian.tcmis.server7.client.haargaz.dbObjs.HaargazTcmISDBModel;
import radian.tcmis.server7.client.cyclone.dbObjs.CycloneTcmISDBModel;
import radian.tcmis.server7.client.volvo.dbObjs.VolvoTcmISDBModel;
import radian.tcmis.server7.client.nalco.dbObjs.NalcoTcmISDBModel;
import radian.tcmis.server7.client.pepsi.dbObjs.PepsiTcmISDBModel;
import radian.tcmis.server7.client.hal.dbObjs.HALTcmISDBModel;
import radian.tcmis.server7.client.abm.dbObjs.ABMTcmISDBModel;
import radian.tcmis.server7.client.mtl.dbObjs.MTLTcmISDBModel;
import radian.tcmis.server7.client.timken.dbObjs.TimkenTcmISDBModel;
import radian.tcmis.server7.client.nrg.dbObjs.NRGTcmISDBModel;
import radian.tcmis.server7.client.maeet.dbObjs.MAEETTcmISDBModel;
import radian.tcmis.server7.client.sgd.dbObjs.SGDTcmISDBModel;
import radian.tcmis.server7.client.cat.dbObjs.CaterpillarTcmISDBModel;
import radian.tcmis.server7.client.oma.dbObjs.OMATcmISDBModel;
import radian.tcmis.server7.client.siemens.dbObjs.SiemensTcmISDBModel;
import radian.tcmis.server7.client.getrag.dbObjs.GetragTcmISDBModel;
import radian.tcmis.server7.client.alcoa.dbObjs.AlcoaTcmISDBModel;
import radian.tcmis.server7.client.zwl.dbObjs.ZWLTcmISDBModel;
import radian.tcmis.server7.client.hans_sasserath.dbObjs.HansSasserathTcmISDBModel;
import radian.tcmis.server7.client.dd.dbObjs.DDTcmISDBModel;
import radian.tcmis.server7.client.aeropia.dbObjs.AeropiaTcmISDBModel;
import radian.tcmis.server7.client.goodrich.dbObjs.GoodrichTcmISDBModel;
import radian.tcmis.server7.client.ommc.dbObjs.OMMCTcmISDBModel;
import radian.tcmis.server7.client.fbm.dbObjs.FBMTcmISDBModel;
import radian.tcmis.server7.client.aerocz.dbObjs.AeroczTcmISDBModel;
import radian.tcmis.server7.client.bai.dbObjs.BAITcmISDBModel;
import radian.tcmis.server7.client.optimetal.dbObjs.OptimetalTcmISDBModel;
import radian.tcmis.server7.client.orlite.dbObjs.OrliteTcmISDBModel;
import radian.tcmis.server7.client.tambour.dbObjs.TambourTcmISDBModel;
import radian.tcmis.server7.client.team.dbObjs.TeamTcmISDBModel;
import radian.tcmis.server7.client.ge.dbObjs.GETcmISDBModel;
import radian.tcmis.server7.client.sales.dbObjs.SalesTcmISDBModel;
import radian.tcmis.server7.client.hexcel.dbObjs.HexcelTcmISDBModel;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class NoBuyOrderPo {
   protected TcmISDBModel db;
   public NoBuyOrderPo(TcmISDBModel  db){
     this.db = db;
   }

   public NoBuyOrderPo() {

   }

  private String getBatchNo(RayTcmISDBModel tcmOpsdb) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String bNo = "";
    String bquery = "select print_batch_seq.nextval BATCHNO from dual";
    try
    {
        dbrs = tcmOpsdb.doQuery(bquery);
        rs=dbrs.getResultSet();
        while (rs.next())
        {
           bNo = rs.getString("BATCHNO");
        }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return bNo;
  }

  public Timestamp getDateFromString(String CountDateString) throws Exception {
    Date countdate1 = null;
    Timestamp CountDateTimeStamp = null;
    SimpleDateFormat countDateT = new SimpleDateFormat("MM/dd/yyyy");
    try
    {
        countdate1 = countDateT.parse(CountDateString);
        CountDateTimeStamp = new Timestamp(countdate1.getTime());

    } catch (Exception e)
    {
    e.printStackTrace();
    throw e;
    }

    Date d = new Date();
          Calendar cal = Calendar.getInstance();
          String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));

    return CountDateTimeStamp;
  }

  public Timestamp getCurrentDate() throws Exception {
    //get system time
    Date d = new Date();
    Calendar cal = Calendar.getInstance();
    String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
    //convert
    Date countdate1 = null;
    Timestamp CountDateTimeStamp = null;
    SimpleDateFormat countDateT = new SimpleDateFormat("MM/dd/yyyy");
    try
    {
        countdate1 = countDateT.parse(cdate);
        CountDateTimeStamp = new Timestamp(countdate1.getTime());

    } catch (Exception e)
    {
    e.printStackTrace();
    throw e;
    }
    return CountDateTimeStamp;
  }

  public void autoProcessRequest(RayTcmISDBModel tcmOpsdb) {
    DBResultSet dbrs = null;
    try {
      ResultSet rs = null;
      Connection connect1 = tcmOpsdb.getConnection();;
      CallableStatement cs = null;
      //first get data and create MR
      String[] resulthg = new String[3];
      String query = "select distinct company_id,catalog_id,item_id,cat_part_no,ordering_facility_id,ordering_application,part_group_no,inventory_group,receipt_id,"+
                     "quantity_received,stocked,unit_price,catalog_price,account_sys_id,packaging,hub,date_of_receipt,charge_type,currency_id"+
							" from no_buy_order_mr_create_view where inventory_group = 'Virtual BOC' and company_id in ('RAYTHEON','DRS','L3') order by receipt_id";

      dbrs = tcmOpsdb.doQuery(query);
      rs=dbrs.getResultSet();
      String resultok = "";
      String prnumber = "";
      String lineItem = "";
      int issueId = 0;
      String errmsg = "";
      String receiptID = "";
      String loginID = "-1";
      String companyID = "";
      String branchPlant = "";
      Vector nboOrderInfo = new Vector();
      while (rs.next()) {
        Hashtable hDdata = new Hashtable(20);
        hDdata.put("RECEIPT_ID",rs.getString("RECEIPT_ID"));
        hDdata.put("COMPANY_ID",rs.getString("COMPANY_ID"));
        hDdata.put("HUB",rs.getString("HUB"));
        hDdata.put("CATALOG_ID",rs.getString("CATALOG_ID")==null?" ":rs.getString("CATALOG_ID"));
        hDdata.put("ITEM_ID",rs.getString("ITEM_ID")==null?" ":rs.getString("ITEM_ID"));
        hDdata.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?" ":rs.getString("CAT_PART_NO"));
        hDdata.put("ORDERING_FACILITY_ID",rs.getString("ORDERING_FACILITY_ID")==null?" ":rs.getString("ORDERING_FACILITY_ID"));
        hDdata.put("ORDERING_APPLICATION",rs.getString("ORDERING_APPLICATION")==null?" ":rs.getString("ORDERING_APPLICATION"));
        hDdata.put("PART_GROUP_NO",rs.getString("PART_GROUP_NO")==null?" ":rs.getString("PART_GROUP_NO"));
        hDdata.put("INVENTORY_GROUP",rs.getString("INVENTORY_GROUP")==null?" ":rs.getString("INVENTORY_GROUP"));
        hDdata.put("QUANTITY_RECEIVED",rs.getString("QUANTITY_RECEIVED"));
        hDdata.put("QUANTITY_ISSUE",rs.getString("QUANTITY_RECEIVED"));
        hDdata.put("STOCKED",rs.getString("STOCKED")==null?" ":rs.getString("STOCKED"));
        hDdata.put("UNIT_PRICE",rs.getString("UNIT_PRICE")==null?" ":rs.getString("UNIT_PRICE"));
        hDdata.put("CATALOG_PRICE",rs.getString("CATALOG_PRICE")==null?" ":rs.getString("CATALOG_PRICE"));
        hDdata.put("ACCOUNT_SYS_ID",rs.getString("ACCOUNT_SYS_ID")==null?" ":rs.getString("ACCOUNT_SYS_ID"));
        hDdata.put("PACKAGING",rs.getString("PACKAGING")==null?" ":rs.getString("PACKAGING"));
        hDdata.put("REQUESTOR","-1");
        hDdata.put("DATE_OF_RECEIPT",BothHelpObjs.formatDate("toCharfromDB",rs.getString("DATE_OF_RECEIPT")==null?" ":rs.getString("DATE_OF_RECEIPT")));
        hDdata.put("CHARGE_TYPE",rs.getString("charge_type"));
        hDdata.put("CURRENCY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
        nboOrderInfo.addElement(hDdata);
      }

      for (int i = 0; i < nboOrderInfo.size(); i++) {
        Hashtable h = (Hashtable)nboOrderInfo.elementAt(i);
        receiptID = (String)h.get("RECEIPT_ID");
        companyID = (String)h.get("COMPANY_ID");
        branchPlant = (String)h.get("HUB");
        //first create MR - each receipt is a new MR
        resulthg = processRequest(h);
        resultok = resulthg[0].toString();
        prnumber = resulthg[1].toString();
        lineItem = resulthg[2].toString();
		  if ("OK".equalsIgnoreCase(resultok)) {
			 //Call Issue Procedures
          String batchno = getBatchNo(tcmOpsdb);
          Timestamp pickdate = getCurrentDate();
          //System.out.println("Doing Update Act: "+h);

          CallableStatement cstmt = connect1.prepareCall("{call p_issue_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
          cstmt.setString(1, companyID);
          cstmt.setString(2, prnumber);
          cstmt.setString(3, lineItem);
          cstmt.setString(4, branchPlant);
          cstmt.setString(5, receiptID);
          cstmt.setString(6, (String)h.get("ITEM_ID"));
          cstmt.setTimestamp(7, pickdate);
          cstmt.setString(8, (String)h.get("QUANTITY_ISSUE"));
          cstmt.setTimestamp(9, pickdate);
          cstmt.setString(10, batchno);
          cstmt.setString(11, "");
          cstmt.setString(12, "N");
          cstmt.setString(13, loginID);
          cstmt.registerOutParameter(14, Types.INTEGER);
          cstmt.registerOutParameter(15, Types.VARCHAR);
          cstmt.executeQuery();
          issueId = cstmt.getInt(14);
          errmsg = cstmt.getString(15);
          //System.out.println("Issue Id: "+issueId);
          //System.out.println("Error Message: "+errmsg);
          connect1.commit();
          cstmt.close();
          if (issueId < 0) {
            String[] recipients1 = new String[]{"tngo@haastcm"};
            HelpObjs.javaSendMail("tcmIS@tcmis.com","Error while calling p_issue_insert for Receipt ID :"+receiptID+"",recipients1,"\nSomething Went wrong while calling NoBuyOrderPo processRequest for Receipt ID :"+receiptID+"");
          }else {
            String deliverDate = BothHelpObjs.formatDate("toNumfromChar",(String)h.get("DATE_OF_RECEIPT"));
            String updquery = "update issue set CONFIRMED='Y', date_delivered=to_date('"+deliverDate+"','MM/DD/YYYY'), ship_confirm_user="+loginID+",ship_confirm_date=SYSDATE,QC_USER="+loginID+",QC_DATE=SYSDATE ";
            updquery +=" where ISSUE_ID = "+issueId+" ";
            tcmOpsdb.doUpdate(updquery);
            cs = connect1.prepareCall("{call P_UPDATE_RLI_STATUS_SHIPPED(?,?)}");
            cs.setInt(1,Integer.parseInt(prnumber));
            cs.setString(2,lineItem);
            cs.execute();
          }
        }else {
          HelpObjs.sendMail(tcmOpsdb,"NoBuyOrderPo","Unable to create MR for receipt: "+receiptID,86030,false);
        }
		}
    }catch (Exception e) {
      HelpObjs.sendMail(tcmOpsdb,"NoBuyOrderPo","Error occurred while scheduler processing NBO request ",86030,false);
    }finally {
      dbrs.close();
    }
  } //end of method


   public String[] processRequest(Hashtable h) {
    String[] result = new String[3];
    try {
      String companyID = (String)h.get("COMPANY_ID");
      if ("RAYTHEON".equalsIgnoreCase(companyID)) {
        db = new RayTcmISDBModel("Ray","2");
      }else if ("DRS".equalsIgnoreCase(companyID)) {
        db = new DRSTcmISDBModel("DRS","2");
      }else if ("BAE".equalsIgnoreCase(companyID)) {
        db = new BAETcmISDBModel("BAE","2");
      }else if ("SEAGATE".equalsIgnoreCase(companyID)) {
        db = new SeagateTcmISDBModel("Seagate");
      }else if ("UTC".equalsIgnoreCase(companyID)) {
        db = new UTCTcmISDBModel("UTC","2");
      }else if ("SWA".equalsIgnoreCase(companyID)) {
        db = new SWATcmISDBModel("SWA","2");
      }else if ("SAUER_DANFOSS".equalsIgnoreCase(companyID)) {
        db = new SDTcmISDBModel("SD","2");
      }else if ("LOCKHEED".equalsIgnoreCase(companyID)) {
        db = new LMCOTcmISDBModel("LMCO");
      }else if ("MILLER".equalsIgnoreCase(companyID)) {
        db = new MillerTcmISDBModel("Miller","2");
      }else if ("GM".equalsIgnoreCase(companyID)) {
        db = new GMTcmISDBModel("GM","2");
      }else if ("CAL".equalsIgnoreCase(companyID)) {
        db = new CALTcmISDBModel("CAL","2");
      }else if ("FEC".equalsIgnoreCase(companyID)) {
        db = new FECTcmISDBModel("FEC","2");
      }else if ("Boeing".equalsIgnoreCase(companyID)) {
        db = new BoeingTcmISDBModel("Boeing","2");
      }else if ("AeroEco".equalsIgnoreCase(companyID)) {
        db = new AeroEcoTcmISDBModel("AeroEco","2");
      }else if ("Dana".equalsIgnoreCase(companyID)) {
        db = new DanaTcmISDBModel("Dana","2");
      }else if ("ABLaero".equalsIgnoreCase(companyID)) {
        db = new ABLaeroTcmISDBModel("ABLaero","2");
      }else if ("DOE".equalsIgnoreCase(companyID)) {
        db = new DOETcmISDBModel("DOE","2");
      }else if ("IAI".equalsIgnoreCase(companyID)) {
        db = new IAITcmISDBModel("IAI","2");
      }else if ("L3".equalsIgnoreCase(companyID)) {
        db = new L3TcmISDBModel("L3","2");
      }else if ("ARVIN_MERITOR".equalsIgnoreCase(companyID)) {
        db = new AMTcmISDBModel("AM","2");
      }else if ("GEMA".equalsIgnoreCase(companyID)) {
        db = new GemaTcmISDBModel("GEMA","2");
      }else if ("PGE".equalsIgnoreCase(companyID)) {
        db = new PGETcmISDBModel("PGE","2");
      }else if ("QUALITY_ON_SITE".equalsIgnoreCase(companyID)) {
        db = new QOSTcmISDBModel("QOS","2");
      }else if ("DCX".equalsIgnoreCase(companyID)) {
        db = new DCXTcmISDBModel("DCX","2");
      }else if ("FORD".equalsIgnoreCase(companyID)) {
        db = new FordTcmISDBModel("Ford","2");
      }else if ("Algat".equalsIgnoreCase(companyID)) {
        db = new AlgatTcmISDBModel("Algat","2");
      }else if ("BAZ".equalsIgnoreCase(companyID)) {
        db = new BAZTcmISDBModel("BAZ","2");
      }else if ("CMM".equalsIgnoreCase(companyID)) {
        db = new CMMTcmISDBModel("CMM","2");
      }else if ("Kanfit".equalsIgnoreCase(companyID)) {
        db = new KanfitTcmISDBModel("Kanfit","2");
      }else if ("Fedco".equalsIgnoreCase(companyID)) {
        db = new FedcoTcmISDBModel("Fedco","2");
      }else if ("GD".equalsIgnoreCase(companyID)) {
        db = new GDTcmISDBModel("GD","2");
      }else if ("IMCO".equalsIgnoreCase(companyID)) {
        db = new IMCOTcmISDBModel("IMCO","2");
      }else if ("Kemfast".equalsIgnoreCase(companyID)) {
        db = new KemfastTcmISDBModel("Kemfast","2");
      }else if ("BL".equalsIgnoreCase(companyID)) {
        db = new BLTcmISDBModel("BL","2");
      }else if ("Verasun".equalsIgnoreCase(companyID)) {
        db = new VerasunTcmISDBModel("Verasun","2");
      }else if ("Hrgivon".equalsIgnoreCase(companyID)) {
        db = new HrgivonTcmISDBModel("Hrgivon","2");
      }else if ("Haargaz".equalsIgnoreCase(companyID)) {
        db = new HaargazTcmISDBModel("Haargaz","2");
      }else if ("Cyclone".equalsIgnoreCase(companyID)) {
        db = new CycloneTcmISDBModel("Cyclone","2");
      }else if ("Volvo".equalsIgnoreCase(companyID)) {
        db = new VolvoTcmISDBModel("Volvo","2");
      }else if ("Nalco".equalsIgnoreCase(companyID)) {
        db = new NalcoTcmISDBModel("Nalco","2");
      }else if ("Pepsi".equalsIgnoreCase(companyID)) {
        db = new PepsiTcmISDBModel("Pepsi","2");
      }else if ("HAL".equalsIgnoreCase(companyID)) {
        db = new HALTcmISDBModel("HAL","2");
      }else if ("ABM".equalsIgnoreCase(companyID)) {
        db = new ABMTcmISDBModel("ABM","2");
      }else if ("MTL".equalsIgnoreCase(companyID)) {
        db = new MTLTcmISDBModel("MTL","2");
      }else if ("Timken".equalsIgnoreCase(companyID)) {
        db = new TimkenTcmISDBModel("Timken","2");
      }else if ("NRG".equalsIgnoreCase(companyID)) {
        db = new NRGTcmISDBModel("NRG","2");
      }else if ("MAEET".equalsIgnoreCase(companyID)) {
        db = new MAEETTcmISDBModel("MAEET","2");
      }else if ("SGD".equalsIgnoreCase(companyID)) {
        db = new SGDTcmISDBModel("SGD","2");
      }else if ("Caterpillar".equalsIgnoreCase(companyID)) {
        db = new CaterpillarTcmISDBModel("Caterpillar","2");
      }else if ("OMA".equalsIgnoreCase(companyID)) {
        db = new OMATcmISDBModel("OMA","2");
      }else if ("Siemens".equalsIgnoreCase(companyID)) {
        db = new SiemensTcmISDBModel("Siemens","2");
      }else if ("Getrag".equalsIgnoreCase(companyID)) {
        db = new GetragTcmISDBModel("Getrag","2");
      }else if ("Alcoa".equalsIgnoreCase(companyID)) {
        db = new AlcoaTcmISDBModel("Alcoa","2");
      }else if ("ZWL".equalsIgnoreCase(companyID)) {
        db = new ZWLTcmISDBModel("ZWL","2");
      }else if ("Hans_sasserath".equalsIgnoreCase(companyID)) {
        db = new HansSasserathTcmISDBModel("HansSasserath","2");
      }else if ("DETROIT_DIESEL".equalsIgnoreCase(companyID)) {
        db = new DDTcmISDBModel("DD","2");
      }else if ("Aeropia".equalsIgnoreCase(companyID)) {
        db = new AeropiaTcmISDBModel("Aeropia","2");
      }else if ("Goodrich".equalsIgnoreCase(companyID)) {
        db = new GoodrichTcmISDBModel("Goodrich","2");
      }else if ("OMMC".equalsIgnoreCase(companyID)) {
        db = new OMMCTcmISDBModel("OMMC","2");
      }else if ("FBM".equalsIgnoreCase(companyID)) {
        db = new FBMTcmISDBModel("FBM","2");
      }else if ("Aerocz".equalsIgnoreCase(companyID)) {
        db = new AeroczTcmISDBModel("Aerocz","2");
      }else if ("BAI".equalsIgnoreCase(companyID)) {
        db = new BAITcmISDBModel("BAI","2");
      }else if ("Optimetal".equalsIgnoreCase(companyID)) {
        db = new OptimetalTcmISDBModel("Optimetal","2");
      }else if ("Orlite".equalsIgnoreCase(companyID)) {
        db = new OrliteTcmISDBModel("Orlite","2");
      }else if ("Tambour".equalsIgnoreCase(companyID)) {
        db = new TambourTcmISDBModel("Tambour","2");
      }else if ("Team".equalsIgnoreCase(companyID)) {
        db = new TeamTcmISDBModel("Team","2");
      }else if ("GE".equalsIgnoreCase(companyID)) {
        db = new GETcmISDBModel("GE","2");
      }else if ("Sales".equalsIgnoreCase(companyID)) {
        db = new SalesTcmISDBModel("Sales","2");
      }else if ("Hexcel".equalsIgnoreCase(companyID)) {
        db = new HexcelTcmISDBModel("Hexcel","2");
      }

      //first build request
      String[] msg = buildRequest(h);
      //get get data and submit
      if ("OK".equalsIgnoreCase(msg[0])) {
        Hashtable header = new Hashtable();
        Vector detail = new Vector();
        //get screen data for given pr_number
        RequestsScreen rs = new RequestsScreen(db);
        Vector vv = rs.getScreenData(Integer.parseInt((String)h.get("REQUESTOR")),Integer.parseInt(msg[1]));
        header = (Hashtable)vv.elementAt(0);
        detail = (Vector)vv.elementAt(1);
        //now modify detail -- set po_number and delivery_type
        for (int j = 0; j < detail.size(); j++) {
          Hashtable lineItems = (Hashtable)detail.elementAt(j);
          lineItems.put("DELIVERY_TYPE","Deliver by");
        }

        //need to put directed charge in the right place
        Hashtable line = (Hashtable)detail.firstElement();
        if ("d".equalsIgnoreCase((String)line.get("CHARGE_TYPE"))) {
          if (line.containsKey("DIRECTED_CHARGE_NUMBERd")) {
            line.put("CHARGE_NUM_DIRECT",line.get("DIRECTED_CHARGE_NUMBERd"));
          }
        }else {
          if (line.containsKey("DIRECTED_CHARGE_NUMBERi")) {
            line.put("CHARGE_NUM_INDIRECT",line.get("DIRECTED_CHARGE_NUMBERi"));
          }
        }
        if (line.containsKey("ALL_POS")) {
          Vector tmpPo = (Vector)line.get("ALL_POS");
          if (tmpPo.size() > 0) {
            line.put("PO",tmpPo.firstElement());
          }
        }

		  //finally submit the given request
        Hashtable moreH = rs.submitNBORequest("SUBMIT",header,detail,(String)h.get("REQUESTOR"),db.getClient());
        Boolean b = (Boolean)moreH.get("RETURN_CODE");
        //if submitted okay than create allocation for the given request
        if(b.booleanValue()){
			 if(moreH.get("NEXT_STATUS").toString().equalsIgnoreCase("posubmit")){
            rs.setReleaseInfo(header,detail);
            if (h.containsKey("CABINET_REPLENISHMENT")) {
              if ("Y".equalsIgnoreCase((String)h.get("CABINET_REPLENISHMENT"))) {
                try {
                  String[] args = new String[1];
                  args[0] = header.get("REQ_NUM").toString();
                  db.doProcedure("p_mr_allocate",args);
                }catch(Exception dbe) {
                  HelpObjs.sendMail(db,"p_mr_allocate error (pr_number: "+header.get("REQ_NUM").toString()+")","Error occured while trying to call procedure",86030,false);
                }
              }
            }
          }
          result[0] = "OK";
          result[1] = msg[1];
          result[2] = "1";
        }else {
          result[0] = "Error";
          result[1] = "Error";
          result[2] = "1";
        }
      }else {
        result[0] = "Error";
        result[1] = "Error";
        result[2] = "1";
      }
    }catch (Exception e) {
      e.printStackTrace();
      result[0] = "Error";
      result[1] = "Error";
      result[2] = "1";
    }finally {
      db.close();
    }
    return result;
   }
   public String[] buildRequest(Hashtable h){
    String[] msg = new String[2];
    int nx = 0;
    try {
      //first create purchase_request
      PurchaseRequest PRI = new PurchaseRequest(db);
      nx = PRI.getNext();
      if ( nx == 0) {
        msg[0] = "Error";
        msg[1] = "0";
        return msg;
      }
      String facID = (String)h.get("ORDERING_FACILITY_ID");
      PRI.setPRNumber(nx);
      PRI.setRequestor(Integer.parseInt((String)h.get("REQUESTOR")));
      PRI.setFacilityId(facID);
      PRI.setAccSysId((String)h.get("ACCOUNT_SYS_ID"));
      PRI.setPRStatus("entered");
      PRI.setRequestDate(new String("nowDate")); // sysdate update
      PRI.setPONumber("");
      PRI.insert();
      //next create request_line_item
      String application = (String)h.get("ORDERING_APPLICATION");
      String itemID = (String)h.get("ITEM_ID");
      String column = "(pr_number,line_item,application,ship_to_location_id,"+
                       "quantity,fac_part_no,item_type,catalog_id,"+
                       "example_packaging,part_group_no,inventory_group,"+
                       "application_desc,delivery_point";
      String values = " select "+nx+",1,'"+application+"',fla.location_id,"+
                        (String)h.get("QUANTITY_RECEIVED")+",'"+(String)h.get("CAT_PART_NO")+"','"+(String)h.get("STOCKED")+"','"+(String)h.get("CATALOG_ID")+"','"+
                        (String)h.get("PACKAGING")+"',"+(String)h.get("PART_GROUP_NO")+",'"+(String)h.get("INVENTORY_GROUP")+"',"+
                        "fla.application_desc,fla.delivery_point";

      column += ",item_id";
      values += ","+itemID;

		//charge type
		column += ",charge_type";
		if (h.get("CHARGE_TYPE") == null) {
			values += ",'d'";
		}else {
			values += ",'"+(String)h.get("CHARGE_TYPE")+"'";
		}

		//prices
      String tmpVal = (String)h.get("CATALOG_PRICE");
      if (!BothHelpObjs.isBlankString(tmpVal)) {
        column += ",quoted_price,catalog_price";
        values += ","+tmpVal+","+tmpVal;
      }
      tmpVal = (String)h.get("UNIT_PRICE");
      if (!BothHelpObjs.isBlankString(tmpVal)) {
        column += ",baseline_price";
        values += ","+tmpVal;
      }
      //notes
      tmpVal = "";
      if (!BothHelpObjs.isBlankString(tmpVal)) {
        column += ",notes";
        values += ",'"+HelpObjs.singleQuoteToDouble(tmpVal)+"'";
      }
      Boolean crit = new Boolean(false);
      if (crit.booleanValue()) {
        column += ",critical";
        values += ",'Y'";
      }
      //currency ID
      tmpVal = (String)h.get("CURRENCY_ID");
      if (!BothHelpObjs.isBlankString(tmpVal)) {
        column += ",currency_id";
        values += ",'"+tmpVal+"'";
      }
      //cabinet replenishment flag
      if (h.containsKey("CABINET_REPLENISHMENT")) {
        if ("Y".equalsIgnoreCase((String)h.get("CABINET_REPLENISHMENT"))) {
          column += ",cabinet_replenishment";
          values += ",'Y'";
          //get default need date
          LineItemView liv = new LineItemView(db);
          liv.setFacilityID(facID);
          liv.setPRNumber(nx);
          liv.setCatalogID((String)h.get("CATALOG_ID"));
          liv.setItemType((String)h.get("STOCKED"));
          liv.setFacPartNo((String)h.get("CAT_PART_NO"));
          liv.setPartGroupNo((String)h.get("PART_GROUP_NO"));
          liv.setInventoryGroup((String)h.get("INVENTORY_GROUP"));
          if ("MM".equalsIgnoreCase((String)h.get("STOCKED"))) {
            liv.setCabinetMMReplenishment(true);
          }
          String defaultNeedDate = liv.getDefNeedByDate();
          column += ",required_datetime";
          values += ",to_date('"+defaultNeedDate+"','MM/dd/yyyy')";
        }else {
          //required date
          column += ",required_datetime";
          values += ",sysdate";
        }
      }else {
        //required date
        column += ",required_datetime";
        values += ",sysdate";
      }
      values += " from fac_loc_app fla where application = '"+application+"' and facility_id = '"+facID+"'";
      //create request line item record
      db.doUpdate("insert into request_line_item "+column+")"+values);
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      msg[0] = "Error";
      msg[1] = "0";
      return msg;
    }
    msg[0] = "OK";
    Integer temp = new Integer(nx);
    msg[1] = new String(temp.toString());
    return msg;
   }  //end of method

} //end of class