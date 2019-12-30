package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.OracleTypes;

public class TrackView {

   protected TcmISDBModel  db;
   protected Integer item_id;
   protected Integer pr_number;
   protected Integer quantity;
   protected String pr_status;
   protected String fac_part_no;
   protected String material;
   protected String line_item;
   protected String po_number;
   protected String required_date;
   protected String release_num;

   protected String facility_id;
   protected String work_area;
   protected Integer requested_finance_approver;
   protected String date_submitted;
   protected Integer sales_order;
   protected String projected_delivery;
   protected String last_shipped;
   protected String item_type;
   protected String uom;
   protected Integer total_shipped;
   protected String request_id;
   protected Integer requestor;
   protected Integer requested_releaser;
   protected String critical;
   protected String tcmis_gen;
   protected Integer radian_po;
   protected String notes;

 static final String[]  colHeads = new String[]
{"Status",     "Facility",    "Workarea",
 "Part Num.",  "Part Desc",   "Type",             "MR Line",
 "Notes",      "Released",    "Needed",           "Picked",     "Delivered",          "Last Del.",
 "Ship to", 	"Deliver to",	"PO",
 "Requester",  "Crit",        "Catalog",          "Item",       "Approver",
 "Canceled",   "RealNotes",   "Cancel Requested", "DPAS",       "Cancel Requestor",   "Submitted Date"};

  static final Hashtable keyCols = new Hashtable();

  static final int []  colWidths = new int[]
 {  66,         77,             85,
    75,         120,            35,               50,
    38,         65,             65,               60,           60,                     65 ,
	 85,			 85,				  110,
    65,         65,             80,               90,           110,
    0,          0,              0,                0,            0,                      0};

  public static final int COL_STATUS             = 0 ;
  public static final int COL_FAC_ID             = 1 ;
  public static final int COL_WORK_AREA          = 2 ;
  public static final int COL_PART_NUM           = 3 ;
  public static final int COL_PART_DESC          = 4 ;
  public static final int COL_ORDER_TYPE         = 5 ;
  public static final int COL_MR_LINE            = 6 ;
  public static final int COL_NOTES              = 7 ;
  public static final int COL_RELEASED_DATE      = 8 ;
  public static final int COL_NEEDED_DATE        = 9 ;
  public static final int COL_PICKED_QTY         = 10 ;
  public static final int COL_DELIVERED_QTY      = 11;
  public static final int COL_LAST_DEL_DATE      = 12 ;
  public static final int COL_SHIP_TO				 = 13;
  public static final int COL_DELIVER_TO			 = 14;
  public static final int COL_PO                 = 15 ;
  public static final int COL_REQUESTER          = 16 ;
  public static final int COL_CRIT               = 17 ;
  public static final int COL_CATALOG_ID         = 18 ;
  public static final int COL_ITEM_ID            = 19 ;
  public static final int COL_APPROVER           = 20 ;
  public static final int COL_CANCELED           = 21 ;
  public static final int COL_REAL_NOTES         = 22;
  public static final int COL_CANCEL_REQ         = 23;
  public static final int COL_DPAS               = 24;
  public static final int COL_CANCEL_REQUESTOR   = 25;
  public static final int COL_SUBMITTED_DATE     = 26;


  //key_cols
  static final String[] mainCols =
  {"STATUS",      "FAC_ID",       "WORK_AREA",
   "PN_COL",      "PART DESC",    "TYPE",        "MR_NUM_COL",
   "NOTES" ,      "RELEASED",     "NEEDED",       "DELIVERED",    "PICKED",        "LAST_DEL",
	"SHIP_TO",		"DELIVER_TO",	 "PO",
   "REQUESTER",   "CRIT_COL",     "CATALOG",      "ITEM_ID" ,   "APPROVER",
   "CANCELED",    "REAL_NOTES",   "CANCEL_REQ",   "DPAS",       "CANCEL_REQUESTOR", "SUBMITTED_DATE"};

  //Note: See WasteCosNInvoice.java in dbObjs
  static final int[] colTypes = new int[] {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
															BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_DATE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_DATE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_DATE_STRING,
												         BothHelpObjs.TABLE_COL_TYPE_STRING,
															BothHelpObjs.TABLE_COL_TYPE_STRING,
															BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING,
                                             BothHelpObjs.TABLE_COL_TYPE_STRING};

   //static intializer
   static {

      int size = mainCols.length;
      for (int i=0;i<size;i++)
      {
         keyCols.put(mainCols[i],String.valueOf(i));
      }
   };

   public TrackView() throws java.rmi.RemoteException {
   }

   public TrackView(TcmISDBModel  db) throws java.rmi.RemoteException {
     this.db = db;
   }

    protected void resetAllVars()
    {
       for (int i=0;i<colHeads.length;i++)
            keyCols.put(colHeads[i],String.valueOf(i));
    }

   public void setDB(TcmISDBModel  db){
     this.db = db;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return item_id;
   }

   public void setPRNumber(int id) {
     this.pr_number = new Integer(id);
   }

   public Integer getPRNumber() {
     return (pr_number.intValue()==0?null:pr_number);
   }

   public void setQuantity(int id) {
     this.quantity = new Integer(id);
   }

   public Integer getQuantity() {
     return quantity;
   }

   public void setPRStatus(String d) {
     this.pr_status = d;
   }

   public String getPRStatus() {
     return pr_status;
   }

   public void setFacPartNo(String d) {
     this.fac_part_no = d;
   }

   public String getFacPartNo() {
     return fac_part_no;
   }

   public void setMaterial(String d) {
     this.material = d;
   }

   public String getMaterial() {
     return material;
   }

   public void setLineItem(String d) {
     this.line_item = d;
   }

   public String getLineItem() {
     return line_item;
   }

   public void setPONumber(String d) {
     this.po_number = d;
   }

   public String getPONumber() {
     return po_number;
   }

   public void setReleaseNumber(String d) {
     this.release_num = d;
   }

   public String getReleaseNumber() {
     return release_num;
   }

   public void setRequiredDate(String d) {
     this.required_date = d;
   }

   public String getRequiredDate() {
     return required_date;
   }

   public String getFacilityId() {
     return facility_id;
   }
   public void  setFacilityId(String f) {
     facility_id = f;
   }
   public String getWorkArea() {
     return work_area;
   }
   public void setWorkArea(String w) {
     work_area = w;
   }
   public void setRequestedFinanceApprover(Integer i) {
     requested_finance_approver = i;
   }
   public Integer getRequestedFinanceApprover() {
     return requested_finance_approver;
   }
   public String getDateSubmitted() {
     return date_submitted;
   }
   public void setDateSubmitted(String d) {
     date_submitted =d;
   }
   public void setSalesOrder(Integer s) {
     sales_order = s;
   }
   public Integer getSalesOrder() {
     return sales_order;
   }
   public void setProjectedDelivery(String p) {
     projected_delivery=p;
   }
   public String getProjectedDelivery() {
     return projected_delivery;
   }
   public void setLastShipped(String l) {
     last_shipped = l;
   }
   public String getLastShipped() {
     return last_shipped;
   }
   public void setItemType(String i) {
     item_type=i;
   }
   public String getItemType() {
     return item_type;
   }
   public String getUom() {
     return uom;
   }
   public void setUom(String u) {
     uom=u;
   }
   public Integer getTotalShipped() {
     return total_shipped;
   }
   public void setTotalShipped(Integer t) {
     total_shipped=t;
   }
   public String getRequestId() {
     return request_id;
   }
   public void setRequestId(String r) {
     request_id = r;
   }
   public Integer getRequestor() {
     return requestor;
   }
   public void setRequestor(Integer r) {
     requestor = r;
   }
   public Integer getRequestedReleaser() {
     return requested_releaser;
   }
   public void setRequestedReleaser(Integer r ) {
     requested_releaser=r;
   }
   public String getCritical() {
     return critical;
   }
   public void setCritical(String r) {
     critical = r;
   }
   public String getTcmisGen() {
     return tcmis_gen;
   }
   public void setTcmisGen(String r) {
     tcmis_gen = r;
   }
   public Integer getRadianPo() {
     return radian_po;
   }
   public void setRadianPo(Integer r ) {
     radian_po=r;
   }

   public String getNotes() {
     return notes;
   }
   public void setNotes(String n) {
     notes = n;
   }

   public void load()  throws Exception {
      String query = "select * from tracking_view where pr_number = " + pr_number.toString();

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setItemId((int)rs.getInt("ITEM_ID"));
           setPRStatus(BothHelpObjs.makeBlankFromNull(rs.getString("PR_STATUS")));
           setPRNumber((int) rs.getInt("PR_NUMBER"));
           setFacPartNo(BothHelpObjs.makeBlankFromNull(rs.getString("FAC_PART_NO")));
           setLineItem(BothHelpObjs.makeBlankFromNull(rs.getString("LINE_ITEM")));
           setMaterial(BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_DESC")));
           setQuantity((int)rs.getInt("QUANTITY"));
           setPONumber(BothHelpObjs.makeBlankFromNull(rs.getString("PO_NUMBER")));
           setReleaseNumber(BothHelpObjs.makeBlankFromNull(rs.getString("RELEASE_NUMBER")));
           setRequiredDate(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("REQUIRED_DATETIME"))));
           facility_id = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
           work_area = BothHelpObjs.makeBlankFromNull(rs.getString("WORK_AREA"));
           requested_finance_approver = new Integer(rs.getInt("REQUESTED_FINANCE_APPROVER"));
           date_submitted = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("DATE_SUBMITTED")));
           sales_order = new Integer(rs.getInt("SALES_ORDER"));
           projected_delivery = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("PROJECTED_DELIVERY")));
           last_shipped = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("LAST_SHIPPED")));
           item_type = BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_TYPE"));
           uom = BothHelpObjs.makeBlankFromNull(rs.getString("UOM"));
           total_shipped = new Integer(rs.getInt("TOTAL_SHIPPED"));
           request_id = BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_ID"));
           requestor = new Integer(rs.getInt("REQUESTOR"));
           requested_releaser = new Integer(rs.getInt("REQUESTED_RELEASER"));
           critical = BothHelpObjs.makeBlankFromNull(rs.getString("CRITICAL"));
           tcmis_gen = BothHelpObjs.makeBlankFromNull(rs.getString("TCMIS_GEN"));
           radian_po = new Integer(rs.getInt("RADIAN_PO"));
           notes =  BothHelpObjs.makeBlankFromNull(rs.getString("NOTES"));
         }

      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
             dbrs.close();
            }
   }

   public String[] getColHeader() {
    return colHeads;
   }

   public Vector getPrintScreenData(Hashtable inData) throws Exception {
      Vector dataV = new Vector();
      String query = "";
      Connection connect1 = null;
      CallableStatement cs = null;
      try{
        String userId = (String)inData.get("USER_ID");
        String requestor = (String)inData.get("REQUESTOR");
        String facilityID = (String)inData.get("FACILITY");
        String wa = (String)inData.get("WORK_AREA");
        String searchText = (String)inData.get("SEARCH_TEXT");
        String searchType = (String)inData.get("SEARCH_TYPE");
        String searchContent = (String)inData.get("SEARCH_CONTENT");
        String days = "";
        boolean needingMyApproval = ((Boolean)inData.get("NEED_MY_APPROVAL")).booleanValue();
        boolean draft = ((Boolean)inData.get("STATUS_DRAFT")).booleanValue();
        boolean open = ((Boolean)inData.get("STATUS_OPEN")).booleanValue();
        boolean archived = ((Boolean)inData.get("STATUS_ARCHIVED")).booleanValue();
        boolean critical= ((Boolean)inData.get("STATUS_CRIT_ONLY")).booleanValue();

        boolean cancelled = ((Boolean)inData.get("STATUS_CANCELLED")).booleanValue();
        boolean anyStatus = ((Boolean)inData.get("STATUS_ANY")).booleanValue();
        if (archived) {
         days = (String)inData.get("DAYS");
        }

        connect1 = db.getConnection();
        connect1.commit();
        connect1.setAutoCommit(false);                       //since we will be writing data to a temporary table turn off auto commit
        cs = connect1.prepareCall("{call PKG_ORDER_TRACK_REPORT.PR_ORDER_TRACK(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        if (needingMyApproval) {
         cs.setString(1,null);
         cs.setString(2,null);
         cs.setString(3,null);
         cs.setString(4,null);
         cs.setString(5,null);
         cs.setString(6,null);
         cs.setString(7,null);
         cs.setString(8,null);
         cs.setString(9,"Y");
         cs.setString(10,null);
         cs.setString(11,null);
         cs.setString(12,null);
         cs.setString(13,null);
         cs.setString(14,null);
         cs.setString(15,null);
        }else {
         if (BothHelpObjs.isBlankString(requestor)) {
           cs.setString(1,null);
         }else {
           cs.setString(1,requestor);
         }
         if (BothHelpObjs.isBlankString(facilityID) || "All".equalsIgnoreCase(facilityID)) {
           cs.setString(2,null);
         }else {
           cs.setString(2,facilityID);
         }
         if (BothHelpObjs.isBlankString(wa) || "All".equalsIgnoreCase(wa)) {
           cs.setString(3,null);
         }else {
           cs.setString(3,wa);
         }

         if(anyStatus) {
           cs.setString(4,null);
           cs.setString(5,null);
           cs.setString(6,null);
           cs.setString(7,null);
           cs.setString(8,null);
         }else {
           cs.setString(4,draft?"Y":null);
           cs.setString(5,cancelled?"Y":null);
           cs.setString(6,archived?"Y":null);
           if (archived) {
             cs.setInt(7,Integer.parseInt(days));
           }else {
             cs.setString(7,null);
           }
           cs.setString(8,open?"Y":null);
         }
         cs.setString(9,null);
         cs.setString(10,critical?"Y":null);
         //search type
         if (BothHelpObjs.isBlankString(searchText)) {
           cs.setString(11,null);
           cs.setString(12,null);
           cs.setString(13,null);
           cs.setString(14,null);
           cs.setString(15,null);
         }else {
           if ("Material Request No.".equalsIgnoreCase(searchType)) {
             cs.setInt(11,Integer.parseInt(searchText));
             cs.setString(12,null);
             cs.setString(13,null);
             cs.setString(14,null);
             if ("is".equalsIgnoreCase(searchContent)) {
               cs.setString(15,null);
             }else {
               cs.setString(15,"Y");
             }
           }else if ("Item".equalsIgnoreCase(searchType)) {
             cs.setString(11,null);
             cs.setInt(12,Integer.parseInt(searchText));
             cs.setString(13,null);
             cs.setString(14,null);
             if ("is".equalsIgnoreCase(searchContent)) {
               cs.setString(15,null);
             }else {
               cs.setString(15,"Y");
             }
           }else if ("Description".equalsIgnoreCase(searchType)) {
             cs.setString(11,null);
             cs.setString(12,null);
             cs.setString(13,searchText);
             cs.setString(14,null);
             if ("is".equalsIgnoreCase(searchContent)) {
               cs.setString(15,null);
             }else {
               cs.setString(15,"Y");
             }
           }else {
             cs.setString(11,null);
             cs.setString(12,null);
             cs.setString(13,null);
             cs.setString(14,searchText);
             if ("is".equalsIgnoreCase(searchContent)) {
               cs.setString(15,null);
             }else {
               cs.setString(15,"Y");
             }
           }
         }
        }
        cs.setInt(16,Integer.parseInt(userId));
        cs.registerOutParameter(17, OracleTypes.VARCHAR);
        cs.execute();
        query = (String)cs.getObject(17);
        //System.out.println("---------- order track printscreen sql:\n"+query);

        Statement st = connect1.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
          HashMap h = new HashMap(20);
          h.put("DETAIL_0",rs.getString("request_line_status"));
          h.put("DETAIL_1",rs.getString("pr_number")+"-"+rs.getString("line_item"));
          h.put("DETAIL_2",BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
          String crit = BothHelpObjs.makeBlankFromNull(rs.getString("critical"));
          if ("y".equalsIgnoreCase(crit)) {
            crit = "Yes";
          }else {
            crit = "No";
          }
          h.put("DETAIL_3",crit);
          h.put("DETAIL_4",BothHelpObjs.makeBlankFromNull(rs.getString("release_date")));
          h.put("DETAIL_5",rs.getString("facility_name"));
          h.put("DETAIL_6",BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
          h.put("DETAIL_7",rs.getString("requestor_name"));
          h.put("DETAIL_8",rs.getString("fac_part_no"));
          h.put("DETAIL_9",BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
          h.put("DETAIL_10",rs.getString("item_type"));
          h.put("DETAIL_11",BothHelpObjs.makeBlankFromNull(rs.getString("required_datetime")));
          h.put("DETAIL_12",rs.getInt("total_picked")+" of "+rs.getInt("order_quantity"));
          if (!BothHelpObjs.isBlankString(rs.getString("allocation_ref"))) {
            h.put("DETAIL_13",rs.getString("allocation_status")+" ("+rs.getString("allocation_ref")+")");
          }else {
            h.put("DETAIL_13",rs.getString("allocation_status"));
          }
          /*
          if ("Schedule".equalsIgnoreCase(rs.getString("delivery_type"))) {
            h.put("DETAIL_14",rs.getString("allocated_quantity")+" on "+BothHelpObjs.makeBlankFromNull(rs.getString("allocation_reference_date")));
          }else {
            h.put("DETAIL_14",rs.getString("allocated_quantity")+" by "+BothHelpObjs.makeBlankFromNull(rs.getString("allocation_reference_date")));
          }*/
          h.put("DETAIL_14",rs.getString("allocated_quantity"));
          h.put("DETAIL_15",BothHelpObjs.makeBlankFromNull(rs.getString("allocation_reference_date")));

          h.put("DETAIL_16",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
          h.put("DETAIL_17",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
          h.put("DETAIL_18",BothHelpObjs.makeBlankFromNull(rs.getString("approver_name")));
          h.put("DETAIL_19",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc")));
			 h.put("DETAIL_20",BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
			 h.put("DETAIL_21",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_desc")));
			 //h.put("DETAIL_10",rs.getString("quantity"));
          //System.out.println("----- line data: "+h);
          dataV.addElement(h);
        }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     } finally{
       connect1.commit();
       connect1.setAutoCommit(true);
       cs.close();
     }
    dataV.trimToSize();
    return dataV;
  }

  public String buildQuery(Hashtable inData) throws Exception {
     String query = "";
     Connection connect1 = null;
     CallableStatement cs = null;
     try{
       // first the easy ones
       String userId = (String)inData.get("USER_ID");
       String requestor = (String)inData.get("REQUESTOR");
       String facilityID = (String)inData.get("FACILITY");
       String wa = (String)inData.get("WORK_AREA");
       String searchText = (String)inData.get("SEARCH_TEXT");
       String searchType = (String)inData.get("SEARCH_TYPE");
       String searchContent = (String)inData.get("SEARCH_CONTENT");
       String days = "";
       boolean needingMyApproval = ((Boolean)inData.get("NEED_MY_APPROVAL")).booleanValue();
       boolean draft = ((Boolean)inData.get("STATUS_DRAFT")).booleanValue();
       boolean open = ((Boolean)inData.get("STATUS_OPEN")).booleanValue();
       boolean archived = ((Boolean)inData.get("STATUS_ARCHIVED")).booleanValue();
       boolean critical= ((Boolean)inData.get("STATUS_CRIT_ONLY")).booleanValue();

       boolean cancelled = ((Boolean)inData.get("STATUS_CANCELLED")).booleanValue();
       boolean anyStatus = ((Boolean)inData.get("STATUS_ANY")).booleanValue();
       if (archived) {
        days = (String)inData.get("DAYS");
       }

       connect1 = db.getConnection();
       connect1.setAutoCommit(false);
                                                                     //1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8
       cs = connect1.prepareCall("{call PKG_ORDER_TRACK.PR_ORDER_TRACK(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
       if (needingMyApproval) {
        cs.setString(1,null);
        cs.setString(2,null);
        cs.setString(3,null);
        cs.setString(4,null);
        cs.setString(5,null);
        cs.setString(6,null);
        cs.setString(7,null);
        cs.setString(8,null);
        cs.setString(9,"Y");
        cs.setString(10,null);
        cs.setString(11,null);
        cs.setString(12,null);
        cs.setString(13,null);
        cs.setString(14,null);
        cs.setString(15,null);
       }else {
        if (BothHelpObjs.isBlankString(requestor)) {
          cs.setString(1,null);
        }else {
          cs.setString(1,requestor);
        }
        if (BothHelpObjs.isBlankString(facilityID) || "All".equalsIgnoreCase(facilityID)) {
          cs.setString(2,null);
        }else {
          cs.setString(2,facilityID);
        }
        if (BothHelpObjs.isBlankString(wa) || "All".equalsIgnoreCase(wa)) {
          cs.setString(3,null);
        }else {
          cs.setString(3,wa);
        }

        if(anyStatus) {
          cs.setString(4,null);
          cs.setString(5,null);
          cs.setString(6,null);
          cs.setString(7,null);
          cs.setString(8,null);
        }else {
          cs.setString(4,draft?"Y":null);
          cs.setString(5,cancelled?"Y":null);
          cs.setString(6,archived?"Y":null);
          if (archived) {
            cs.setInt(7,Integer.parseInt(days));
          }else {
            cs.setString(7,null);
          }
          cs.setString(8,open?"Y":null);
        }
        cs.setString(9,null);
        cs.setString(10,critical?"Y":null);
        //search type
        if (BothHelpObjs.isBlankString(searchText)) {
          cs.setString(11,null);
          cs.setString(12,null);
          cs.setString(13,null);
          cs.setString(14,null);
          cs.setString(15,null);
        }else {
          if ("Material Request No.".equalsIgnoreCase(searchType)) {
            cs.setInt(11,Integer.parseInt(searchText));
            cs.setString(12,null);
            cs.setString(13,null);
            cs.setString(14,null);
            if ("is".equalsIgnoreCase(searchContent)) {
              cs.setString(15,null);
            }else {
              cs.setString(15,"Y");
            }
          }else if ("Item".equalsIgnoreCase(searchType)) {
            cs.setString(11,null);
            cs.setInt(12,Integer.parseInt(searchText));
            cs.setString(13,null);
            cs.setString(14,null);
            if ("is".equalsIgnoreCase(searchContent)) {
              cs.setString(15,null);
            }else {
              cs.setString(15,"Y");
            }
          }else if ("Description".equalsIgnoreCase(searchType)) {
            cs.setString(11,null);
            cs.setString(12,null);
            cs.setString(13,searchText);
            cs.setString(14,null);
            if ("is".equalsIgnoreCase(searchContent)) {
              cs.setString(15,null);
            }else {
              cs.setString(15,"Y");
            }
          }else {
            cs.setString(11,null);
            cs.setString(12,null);
            cs.setString(13,null);
            cs.setString(14,searchText);
            if ("is".equalsIgnoreCase(searchContent)) {
              cs.setString(15,null);
            }else {
              cs.setString(15,"Y");
            }
          }
        }
       }
       cs.setInt(16,Integer.parseInt(userId));
       cs.setString(17,null);
       cs.registerOutParameter(18, OracleTypes.VARCHAR);
       cs.execute();
       query = (String)cs.getObject(18);
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    } finally{
      connect1.commit();
      connect1.setAutoCommit(true);
      cs.close();
    }
    return query;
  }

  public Hashtable getTableDataNew(Hashtable inData)  throws Exception {
   long sTime = new java.util.Date().getTime();
   Hashtable h = new Hashtable();
   Vector data = new Vector();
   DBResultSet dbrs = null;
   ResultSet rs = null;
   try{
     String query = buildQuery(inData);
     dbrs = db.doQuery(query);
     rs=dbrs.getResultSet();

     sTime = new java.util.Date().getTime();
     while (rs.next()){
       Object[] oa = new Object[colHeads.length];
       //int ts = rs.getInt("total_picked");
       //int qty = rs.getInt("quantity");
       String ts = rs.getString("total_picked");
       String qty = rs.getString("quantity");
       oa[COL_STATUS]        = rs.getString("request_line_status");
       oa[COL_ITEM_ID]       = rs.getString("item_id");
       oa[COL_CATALOG_ID]    = rs.getString("catalog_desc");
       oa[COL_FAC_ID]        = rs.getString("facility_name");
       oa[COL_WORK_AREA]     = rs.getString("application_desc");    //12-15-01
		 oa[COL_PART_NUM]      = rs.getString("fac_part_no");
       oa[COL_PART_DESC]     = rs.getString("part_description");
       String deliveryType = rs.getString("delivery_type");
       if ("schedule".equalsIgnoreCase(deliveryType)) {
        oa[COL_ORDER_TYPE]   = "SCH";
       }else {
        oa[COL_ORDER_TYPE]    = rs.getString("item_type");
       }
       oa[COL_RELEASED_DATE] = rs.getString("release_date");
       oa[COL_NEEDED_DATE]   = rs.getString("required_datetime");
       oa[COL_PICKED_QTY]    = (ts+" of "+qty);
       oa[COL_DELIVERED_QTY] = rs.getString("total_shipped");
       oa[COL_LAST_DEL_DATE] = rs.getString("last_shipped");
		 oa[COL_SHIP_TO]       = rs.getString("location_desc");
		 oa[COL_DELIVER_TO]    = rs.getString("delivery_point_desc");
		 oa[COL_MR_LINE]       = rs.getString("pr_number")+"-"+rs.getString("line_item");
       oa[COL_PO]            = rs.getString("po_number");
       oa[COL_REQUESTER]     = rs.getString("requestor_name");
       oa[COL_APPROVER]      = rs.getString("approver_name");
       oa[COL_CRIT]          = (new Boolean((rs.getString("critical")!=null?rs.getString("critical").equalsIgnoreCase("y"):false)));
       String notes          = rs.getString("notes");
       if (notes.length() > 1) {
         oa[COL_NOTES] = ("     +");
       } else {
         oa[COL_NOTES] =  ("   ");
       }
       boolean cancel        = "cancelled".equalsIgnoreCase(rs.getString("category_status"));
       oa[COL_CANCELED]      = (new Boolean(cancel));
       oa[COL_REAL_NOTES]    = (notes);
       //only shows "Cancellation Requested" in Order Status if cancel_status is rqcancel
       if ("rqcancel".equalsIgnoreCase(rs.getString("cancel_status"))) {
         oa[COL_CANCEL_REQ] = rs.getString("cancel_request");
         oa[COL_CANCEL_REQUESTOR] = rs.getString("cancel_requester_name");
       }else {
         oa[COL_CANCEL_REQ] = "";
         oa[COL_CANCEL_REQUESTOR] = "";
       }
       oa[COL_DPAS]          = new Boolean(!"None".equalsIgnoreCase(rs.getString("dpas_code")));
       oa[COL_SUBMITTED_DATE] = rs.getString("submitted_date");
       data.addElement(oa);
     }
   }catch(Exception e){
     e.printStackTrace();
     throw e;
   } finally{
    dbrs.close();
   }

   h.put("TABLE_COL",colHeads);
   h.put("TABLE_WIDTH",colWidths);
   h.put("TABLE_TYPE",colTypes);
   h.put("TABLE_DATA",data);
   h.put("KEY_COLS", keyCols );
   h.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
   //System.out.println("\n\n---- ahaha done: "+(new java.util.Date().getTime() - sTime)+" milliseconds");
   return h;
 }

 protected static String getCorrectStatus(String s){
      for (int i=0;i<TrackingTable.STATUS_NAME.length;i++){
         if (s.equalsIgnoreCase(TrackingTable.STATUS_NAME[i])) return TrackingTable.STATUS_DESC[i];
      }
      return " ";
   }

   public Hashtable getScheduleDelivery(String userId, String prNumber, String lineItem) throws Exception {
    Hashtable result = new Hashtable(8);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //get header
      String query = "select pr.requestor,pr.pr_status,rli.quantity,rli.item_type,rli.fac_part_no,nvl(rli.item_id,rli.example_item_id) item_id,i.item_desc,"+
                     "decode(pr.pr_status,'entered','Draft','compchk','Pending Finance Approval','compchk2','Pending Use Approval',"+
                     "'posubmit','Submitted','rejected','Rejected','cancel','Cancelled') status_desc from "+
                     "purchase_request pr, request_line_item rli, item i where pr.pr_number = "+prNumber+" and "+
                     "pr.pr_number = rli.pr_number and rli.line_item = '"+lineItem+"' and nvl(rli.item_id,rli.example_item_id) = i.item_id";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.put("REQUESTOR",rs.getString("requestor"));
        result.put("STATUS",rs.getString("pr_status"));
        result.put("STATUS_DESC",rs.getString("status_desc"));
        result.put("QTY",rs.getString("quantity"));
        result.put("CAT_PART_NO",rs.getString("fac_part_no"));
        result.put("ITEM_TYPE",rs.getString("item_type"));
        result.put("ITEM_ID",rs.getString("item_id"));
        result.put("ITEM_DESC",rs.getString("item_desc"));
      }
      //if person who look at MR is not the requestor then check to see if he/she is
      //the alternate requestor
      if (!userId.equalsIgnoreCase(result.get("REQUESTOR").toString())) {
        Personnel thisUser = new Personnel(db);
        thisUser.setPersonnelId(Integer.parseInt(userId));
        //if current user is requestor alternate requestor then set requestor or MR to this user
        if (thisUser.isAlternateRequestor(Integer.parseInt(prNumber))) {
          result.put("REQUESTOR", userId);
        }
      }

      //schedule delivery info
      DeliverySchedule ds = new DeliverySchedule(db);
      ds.setPrNum(Integer.parseInt(prNumber));
      ds.setLineNum(lineItem);
      ds.load();
      result.put("DELIVERY_SCHEDULE",ds.getSchedule());
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    return result;
   }  //end of method

} //end of class

















