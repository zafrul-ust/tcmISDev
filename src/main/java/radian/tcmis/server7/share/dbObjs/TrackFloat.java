/*
SQLWKS> DESCRIBE INVENTORY_STATUS
Column Name                    Null?    Type
------------------------------ -------- ----
BRANCH_PLANT                            VARCHAR2(12)
ITEM_ID                                 NUMBER
LOT_NUM                                 VARCHAR2(30)
LOT_STATUS                              VARCHAR2(13)
AVAILABLE                               NUMBER
ON_HOLD                                 NUMBER
ON_ORDER                                NUMBER
EXPIRE_DATE                             DATE
PROMISED_DATE                           DATE
PO_NUMBER                               NUMBER
REV_PROM_DATE                           VARCHAR2(20)
RECEIVED_DATE                           DATE
READY_TO_SHIP_DATE                      DATE


SQLWKS> describe OOR_STATUS
Column Name                    Null?    Type
------------------------------ -------- ----
SALES_ORDER_ID                          NUMBER
ITEM_TYPE                               VARCHAR2(8)
ITEM_ID                                 NUMBER
RADIAN_PO                               NUMBER
LOT_NUM                                 VARCHAR2(30)
ON_ORDER                                NUMBER
IN_PURCHASING                           NUMBER
DELIVERY_DATE                           DATE
REV_PROM_DATE                           VARCHAR2(20)
LAST_UPDATED                            DATE
ON_HAND                                 NUMBER
LOT_STATUS                              VARCHAR2(13)
READY_TO_SHIP_DATE                      DATE
NOTES                                   VARCHAR2(160)


SQLWKS> describe orders_shipped
Column Name                    Null?    Type
------------------------------ -------- ----
SALES_ORDER_ID                          NUMBER
DATE_SHIPPED                            DATEf
QTY_SHIPPED                             NUMBER
LOT_SHIPPED                             CHAR(30)
EXPIRE_DATE                             DATE
EXPIRE_FLAG                             VARCHAR2(7)

*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import oracle.jdbc.OracleTypes;

public class TrackFloat
{
    protected TcmISDBModel  db;
    protected String so;
    protected Hashtable headerInfo = null;
    protected Hashtable orderStatus = null;
    protected Hashtable deliveryStatus = null;
    protected Hashtable partDescription = null;
    private boolean loaded = false;
    private boolean radianPoIssued = false;
    protected int item=0;
    protected String type=null;
    protected int qty=0;
    protected int delQty=0;
    protected String MR;
    protected String line;

    //Table Track Float
    String[]             leftColHeads =  { "Qty","Status","REF","Est. Ship Date","Notes","real notes" };
    static final int []  colWidthsL   = new int[] { 30,73,80,80,40,0};

    String[]             rightColHeads =  { "Qty","Pick Date","Ship Date","Lot","Exp. Date"  };
    static final int []  colWidthsR    = new int[] {  30    , 66,   66     ,55   ,  66  };

    String[]             centerColHeads =  { "Description","Packaging","Grade"  };
    static final int []  colWidthsC     = new int[] { 360    ,   150          ,100   };

    //static final Hashtable keyCols = new Hashtable();
    static final Hashtable keyColsL = new Hashtable();
    static final Hashtable keyColsC = new Hashtable();
    static final Hashtable keyColsR = new Hashtable();

    static final String[] rightCols =   { "Qty","Pick Date","Ship Date","Lot","Exp. Date"  };
    static final String[] leftCols  =   { "Qty","Status","REF","Est. Ship Date","Notes","Real Notes"   };
    static final String[] centerCols =  { "Description","Packaging","Grade"   };

    static final int[] colTypesC = new int[]  {
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING
                                     };

    static final int[] colTypesR = new int[] {
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING
                                     };

    static final int[] colTypesL = new int[]  {
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING,
                                    BothHelpObjs.TABLE_COL_TYPE_STRING
                                     };

    static
    {
        for (int i=0;i<leftCols.length;i++)
            keyColsL.put(leftCols[i],String.valueOf(i));
        for (int i=0;i<centerCols.length;i++)
            keyColsC.put(centerCols[i],String.valueOf(i));
        for (int i=0;i<rightCols.length;i++)
            keyColsR.put(rightCols[i],String.valueOf(i));

    }
    //
    public TrackFloat(TcmISDBModel db)
    {
        this.db = db;
     }

    protected void resetAllVars()
    {
        for (int i=0;i<leftCols.length;i++)
            keyColsL.put(leftCols[i],String.valueOf(i));
        for (int i=0;i<centerCols.length;i++)
            keyColsC.put(centerCols[i],String.valueOf(i));
        for (int i=0;i<rightCols.length;i++)
            keyColsR.put(rightCols[i],String.valueOf(i));
    }

    public void setMR(String s) {
      this.MR = s;
    }
    public String getMR() {
      return this.MR;
    }
    public void setLine(String s) {
      this.line = s;
    }
    public String getLine() {
      return this.line;
    }

    public void setSo(String so)
    {
        this.so = so;
    }

    public void setItem(int item)
    {
        this.item = item;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSo()
    {
        return so;
    }

  public Hashtable getOrderDetailInfo(String mrLine,String itemID) throws Exception {
    Hashtable result = new Hashtable(3);
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
      String prNumber = mrLine.substring(0,mrLine.indexOf("-"));
      String lineItem = mrLine.substring(mrLine.indexOf("-")+1);
      connect1 = db.getConnection();
      cs = connect1.prepareCall("{call PKG_ORDER_TRACK_DETAIL.PR_ORDER_TRACK_DETAIL(?,?,?,?,?,?)}");
      cs.setInt(1,Integer.parseInt(prNumber));
      cs.setInt(2,Integer.parseInt(lineItem));
      cs.setInt(3,Integer.parseInt(itemID));
      cs.registerOutParameter(4, OracleTypes.CURSOR);
      cs.registerOutParameter(5, OracleTypes.CURSOR);
      cs.registerOutParameter(6, OracleTypes.CURSOR);
      cs.execute();
      getDeliveryStatus((ResultSet)cs.getObject(4),result);
      //System.out.println("\n\n-------- before order status");
      getOrderStatus((ResultSet)cs.getObject(5),result);
      //System.out.println("\n\n-------- before part desc");
      getPartDesc((ResultSet)cs.getObject(6),result);
    }catch (Exception e){
      //System.out.println("\n\n--------- Erros in Get Open Inventory Procedure \n\n");
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error occured while trying to access 'Status Detail'","PKG_ORDER_TRACK_DETAIL.PR_ORDER_TRACK_DETAIL(?,?,?,?,?,?)\npr_number: "+mrLine+" Item: "+itemID,86030,false);
      throw e;
    }finally{
      //rs.close();
      cs.close();
    }
    return result;
  }

  public void getPartDesc(ResultSet rs, Hashtable result) throws Exception {
    try {
      Hashtable dataH = new Hashtable(5);
      Vector dataV = new Vector();
      while (rs.next()) {
         Object[] oa = new Object[leftCols.length];
         int i=0;
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("pkg_size")));
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("grade")));
         dataV.addElement(oa);
      }
      dataH.put("TABLE_HEADERS",centerCols);
      dataH.put("TABLE_WIDTHS",colWidthsC);
      dataH.put("TABLE_TYPES",colTypesC);
      dataV.trimToSize();
      dataH.put("TABLE_DATA",dataV);
      dataH.put("KEY_COLS",keyColsC);
      result.put("PART_DESC",dataH);
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
  }
  public void getOrderStatus(ResultSet rs, Hashtable result) throws Exception {
    try {
      Hashtable dataH = new Hashtable(5);
      Vector dataV = new Vector();
      while (rs.next()) {
         Object[] oa = new Object[leftCols.length];
         int i=0;
         //oa[i++] = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("quantity")));
         oa[i++] = rs.getString("quantity");
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("status")));
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("ref")));
         oa[i++] = (BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("rpd"))));
         String notes = (BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
         if (!BothHelpObjs.isBlankString(notes)) {
          oa[i++] = "+";
         }else {
          oa[i++] = "";
         }
         oa[i++] = notes;
         dataV.addElement(oa);
      }
      dataH.put("TABLE_HEADERS",leftCols);
      dataH.put("TABLE_WIDTHS",colWidthsL);
      dataH.put("TABLE_TYPES",colTypesL);
      dataV.trimToSize();
      dataH.put("TABLE_DATA",dataV);
      dataH.put("KEY_COLS",keyColsL);
      result.put("ORDER_STATUS",dataH);
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
  }
  public void getDeliveryStatus(ResultSet rs, Hashtable result) throws Exception {
    try {
      Hashtable dataH = new Hashtable(5);
      Vector dataV = new Vector();
      while (rs.next()) {
         Object[] oa = new Object[rightCols.length];
         int i=0;
         //oa[i++] = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("quantity")));
         oa[i++] = rs.getString("quantity");
         oa[i++] = (BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("date_picked"))));
         oa[i++] = (BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("date_delivered"))));
         oa[i++] = (BothHelpObjs.makeBlankFromNull(rs.getString("mfg_lot")));
         String tmpD = (BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("expire_date"))));
         if ("Jan 01, 3000".equalsIgnoreCase(tmpD)) {
          tmpD = "Indefinite";
         }
         oa[i++] = tmpD;
         dataV.addElement(oa);
      }
      dataH.put("TABLE_HEADERS",rightCols);
      dataH.put("TABLE_WIDTHS",colWidthsR);
      dataH.put("TABLE_TYPES",colTypesR);
      dataV.trimToSize();
      dataH.put("TABLE_DATA",dataV);
      dataH.put("KEY_COLS",keyColsR);
      result.put("DELIVERY_STATUS",dataH);
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
  }

  public Hashtable loadHeaderInfo() throws Exception
    {
        Hashtable h = new Hashtable();
        Facility fac = new Facility(db);
        ApplicationView app = new ApplicationView(db);
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            String query = "select * from order_tracking_view where pr_number = " + getMR()+ " and line_item = "+getLine();

            dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
            while (rs.next())
            {
                /*****/
                int ts = BothHelpObjs.makeZeroFromNull(rs.getString("total_shipped"));
                int qty = BothHelpObjs.makeZeroFromNull(rs.getString("quantity"));
                String status = BothHelpObjs.makeBlankFromNull(rs.getString("pr_status"));
                String ds = BothHelpObjs.makeBlankFromNull(rs.getString("last_shipped"));
                /* 3-21-02 doesn't need this coz the view handle the logic below
                if (status.equalsIgnoreCase("posubmit"))
                {
                    //
                    if (ts == qty)
                    {
                        status = "delivered";
                        GregorianCalendar g = new GregorianCalendar();
                        g.add(Calendar.DATE,-30);
                        java.util.Date thatDay = g.getTime();
                        String dtemp = (ds.length()>0?ds.substring(0,
                                                                   10).trim():"");
                        if (dtemp.length()>0)
                        {
                            java.util.Date shipD = java.sql.Date.valueOf(dtemp);
                            if(!thatDay.before(shipD))
                            {
                                status = "archived";
                            }
                        }
                    }
                    else if (ts != 0 && ts<qty) status = "partial";
                }    */
                /*****/
                h.put("CANCEL_REQUEST",
                      BothHelpObjs.formatDate("toCharfromDB",
                                              BothHelpObjs.makeBlankFromNull(rs.getString("CANCEL_REQUEST"))));
                h.put("CANCEL_REQUESTER",
                      BothHelpObjs.makeBlankFromNull(rs.getString("CANCEL_REQUESTER_NAME")));
                h.put("PR_NUMBER",
                      BothHelpObjs.makeBlankFromNull(rs.getString("PR_NUMBER")));
                h.put("PR_STATUS",status);
                h.put("DATE_SUBMITTED",
                      BothHelpObjs.formatDate("toCharfromDB",
                                              BothHelpObjs.makeBlankFromNull(rs.getString("DATE_SUBMITTED"))));
                h.put("REQUIRED_DATETIME",
                      BothHelpObjs.formatDate("toCharfromDB",
                                              BothHelpObjs.makeBlankFromNull(rs.getString("REQUIRED_DATETIME"))));
                h.put("REQUESTOR_NAME",
                      BothHelpObjs.makeBlankFromNull(rs.getString("REQUESTOR_NAME")));
                h.put("APPROVER_NAME",
                      BothHelpObjs.makeBlankFromNull(rs.getString("APPROVER_NAME")));
                fac.setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID")));
                h.put("FACILITY_ID",
                      BothHelpObjs.makeBlankFromNull(fac.getFacilityName()));
                app.setApplication(BothHelpObjs.makeBlankFromNull(rs.getString("WORK_AREA")));
                app.setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID")));
                app.load();
                String desc = (app.getApplicationDesc()==null?BothHelpObjs.makeBlankFromNull(rs.getString("WORK_AREA")):BothHelpObjs.makeBlankFromNull(app.getApplicationDesc()));
                h.put("WORK_AREA",desc);
                h.put("FAC_PART_NO",
                      BothHelpObjs.makeBlankFromNull(rs.getString("FAC_PART_NO")));
                h.put("ITEM_TYPE",
                      BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_TYPE")));
                h.put("MATERIAL_DESC",
                      BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_DESC")));
                qty = BothHelpObjs.makeZeroFromNull(rs.getString("QUANTITY"));
                h.put("QUANTITY",String.valueOf(qty));
                h.put("PROJECTED_DELIVERY",
                      BothHelpObjs.formatDate("toCharfromDB",
                                              BothHelpObjs.makeBlankFromNull(rs.getString("PROJECTED_DELIVERY"))));
                h.put("LAST_UPDATED",
                      BothHelpObjs.formatDate("toCharfromDB",
                                              BothHelpObjs.makeBlankFromNull(rs.getString("LAST_UPDATED"))));
                h.put("PO_NUMBER",
                      BothHelpObjs.makeBlankFromNull(rs.getString("PO_NUMBER")));
                break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();throw e;
        }
        finally
        {
            dbrs.close();
        }
        return h;
    }



    public static String getNowDate(TcmISDBModel  db)  throws Exception
    {
        String query = "select sysdate from dual";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String next = new String("");
        try
        {
            dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
            while (rs.next())
            {
                next = rs.getString(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return next;
    }

    protected int getNextAfterWeekend(int d)
    {
        SimpleDateFormat orderF = new SimpleDateFormat("yyyyDDD");
        orderF.setTimeZone(TimeZone.getDefault());
        ParsePosition pos = new ParsePosition(0);
        java.util.Date D = orderF.parse(String.valueOf(d),pos);
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(D);
        if (g.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
            g.add(Calendar.DATE,2);
        else if (g.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
            g.add(Calendar.DATE,1);
        d = Integer.parseInt(String.valueOf(g.get(Calendar.YEAR))+ String.valueOf(g.get(Calendar.DAY_OF_YEAR)));
        //
        return d;
    }

}
