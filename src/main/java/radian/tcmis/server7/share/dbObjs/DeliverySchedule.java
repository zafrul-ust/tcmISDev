package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;



import java.math.BigDecimal;
import java.util.*;
import java.sql.*;

public class DeliverySchedule {
  //A delivery schedule is a Vector of Hashtables
  //Each Hashtable contains at least 2 elements
  //DATE -- a Calendar object
  //QTY  -- a BigDecimal object

  //The hashtable can also contain these
  //STATUS -- a String
  //REV_QTY -- a BigDecimal
  //DATE_DELIVERED -- a Calendar object
  //QTY_DELIVERED -- an BigDecimal
  protected TcmISDBModel db;
  protected int prNum;
  protected String lineNum;
  protected Vector delInfo;
  public static final int ON_OR_BEFORE = 0;
  public static final int SCHEDULE = 1;
  public static final String onOrBeforeString = "Deliver by";
  public static final String scheduleString = "Schedule";
  //public static int HOLD = 2;
  protected BigDecimal qty = new BigDecimal("0");
  protected boolean allowQtyChange = false;
  protected boolean changedSchedule = false;

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public void setAllowQtyChange(boolean b) {
    allowQtyChange = b;
  }

  public boolean getChangedSchedule() {
    return changedSchedule;
  }

  // Schedule is not enabled right now
  // to enable it, put the scheduleString in the String[]
  protected static String[] typeName = new String[]{onOrBeforeString,scheduleString};
  //protected static String[] typeName = new String[]{onOrBeforeString};
  protected static String[] larry = new String[]{onOrBeforeString,scheduleString};

  public static Vector getAllDeliveryTypes(){
    Vector v = new Vector();
    for(int i = 0;i<typeName.length;i++){
      v.addElement(new String(typeName[i]));
    }
    return v;
  }
  public static Vector getLarrysDeliveryTypes(){
    Vector v = new Vector();
    for(int i = 0;i<larry.length;i++){
      v.addElement(new String(larry[i]));
    }
    return v;
  }
  public DeliverySchedule(TcmISDBModel db){
    this.db = db;
  }
  public DeliverySchedule(TcmISDBModel db,RequestLineItem rli){
    this(db);
    setPrNum(rli.getPRNumber().intValue());
    setLineNum(rli.getLineItem());
  }
  public DeliverySchedule(){
  }
  public void setDB(TcmISDBModel db){
    this.db = db;
  }
  public void setPrNum(int i){
    prNum = i;
  }
  public void setLineNum(int i){
    lineNum = String.valueOf(i);
  }
  public void setLineNum(String i){
    lineNum = i;
  }
  public void load()throws Exception{
    delInfo = new Vector();
    synchronized(delInfo){
      ResultSet rs = null;
      Connection connect1 = null;
      CallableStatement cs = null;
      try {
        Statement st = null;
        connect1 = db.getConnection();
        connect1.setAutoCommit(false);      //since we will be writing data to a temporary table turn off auto commit
        cs = connect1.prepareCall("{call p_delivery_summary(?,?)}");
        cs.setInt(1,prNum);
        cs.setString(2,lineNum);
        cs.execute();

        st = connect1.createStatement();
        rs = st.executeQuery("select * from temp_delivery_summary_view order by requested_date_to_deliver");
        int count = 0;
        Calendar tmpC = Calendar.getInstance();
        while(rs.next()){
          Hashtable h = new Hashtable();
          Calendar c = Calendar.getInstance();
          java.sql.Date d = rs.getDate("REQUESTED_DATE_TO_DELIVER");
          c.setTime(d);
          h.put("DATE",c);
          h.put("QTY",rs.getBigDecimal("REQUESTED_QTY"));
          h.put("DELIVERED",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("DATE_DELIVERED"))));
          String qty = BothHelpObjs.makeBlankFromNull(rs.getString("DELIVERED_QTY"));
          h.put("DELIVERED_QTY",qty);
          if (qty.length() > 0) {
            h.put("STATUS","delivered");
          }else {
            h.put("STATUS","schedule");
          }
          count++;
          delInfo.addElement(h);
        }
        //calculate combine
        String lastSchedNQty = "";
        int lastCombineRow = 0;
        for (int i = 0; i < delInfo.size(); i++) {
          Hashtable h = (Hashtable)delInfo.elementAt(i);
          Calendar cal = (Calendar)h.get("DATE");
          String currentSchedNQty = (cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)+h.get("QTY").toString();
          if (lastSchedNQty.equalsIgnoreCase(currentSchedNQty)) {
            h.put("COMBINE_ROW",(new Integer(lastCombineRow)).toString());
          }else {
            lastCombineRow = i;
            h.put("COMBINE_ROW",(new Integer(lastCombineRow)).toString());
          }
          lastSchedNQty = currentSchedNQty;
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }finally {
        connect1.commit();
        connect1.setAutoCommit(true);
        cs.close();
      }
    }
  }

  public Vector getSchedule(){
    return delInfo;
  }
  public Vector getApprovedSchedule()throws Exception{
    load();
    Vector v = (Vector)delInfo.clone();
    Vector changes = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try{
      String q = "select * from delivery_schedule_change ";
      q = q + "where ( expedite_approval is null or lower(expedite_approval) != 'y' or ";
      q = q + "csr_approval is null or lower(csr_approval) != 'y') and ";
      q = q + "pr_number = "+prNum+" and ";
      q = q + "line_item = '"+lineNum+"' ";
      q = q + "order by date_to_deliver";
      dbrs = db.doQuery(q); rs=dbrs.getResultSet();
      int i=1;
      while(rs.next()){
        Hashtable h = new Hashtable();
        Calendar c = Calendar.getInstance();
        c.setTime(rs.getDate("date_to_deliver"));
        h.put("DATE",c);
        h.put("ORIGINAL",rs.getBigDecimal("original_qty"));
        h.put("REVISED",rs.getBigDecimal("revised_qty"));
        changes.addElement(h);
      }
    }catch(Exception e){e.printStackTrace();throw e;}
    finally { dbrs.close(); }
    if(changes.size()<1){
      return v;
    }
    // now reverse the changes
    for(int i=0;i<changes.size();i++){
      Hashtable cHash = (Hashtable)changes.elementAt(i);
      Calendar cCal = (Calendar)cHash.get("DATE");
      boolean foundIt = false;
      for(int j=0;j<v.size();j++){
        Hashtable h = (Hashtable)v.elementAt(j);
        Calendar c = (Calendar)h.get("DATE");
        if(BothHelpObjs.sameDay(cCal,c)){
          BigDecimal cur = new BigDecimal((String)h.get("QTY"));
          BigDecimal ori = new BigDecimal((String)cHash.get("ORIGINAL"));
          BigDecimal rev = new BigDecimal((String)cHash.get("REVISED"));
          cur = cur.subtract(rev);
          cur = cur.add(ori);
          h.put("QTY", cur);
          v.setElementAt(h,j);
          foundIt = true;
          break;
        }
      }
      if(!foundIt){
        Hashtable h = new Hashtable();
        h.put("DATE",cCal);
        BigDecimal ori = new BigDecimal((String)cHash.get("ORIGINAL"));
        BigDecimal rev = new BigDecimal((String)cHash.get("REVISED"));
        ori = ori.subtract(rev);
        h.put("QTY", ori);
        v.addElement(h);
      }
    }
    // now sort the Vector in chronological order
    Vector vSorted = new Vector();
    for(int i=v.size();i>0;i--){
      Hashtable h1 = (Hashtable)v.elementAt(i-1);
      Calendar c1 = (Calendar)h1.get("DATE");
      boolean b = false;
      for(int j=0;j<vSorted.size();j++){
        Hashtable h2 = (Hashtable)vSorted.elementAt(j);
        Calendar c2 = (Calendar)h2.get("DATE");
        if(c1.before(c2)){
          vSorted.insertElementAt(h1,j);
          b = true;
          break;
        }
      }
      if(!b){
        vSorted.addElement(h1);
      }
    }
    return vSorted;
  }
  public boolean needsCsrApproval()throws Exception{
    try{
      String query = "select count(*) from delivery_schedule_change";
      query = query + " where (csr_approval is null or lower(csr_approval) != 'y') and ";
      query = query + "pr_number = "+prNum+" and ";
      query = query + "line_item = '"+lineNum+"' ";
      return DbHelpers.countQuery(db,query) > 0;
    }catch(Exception e){e.printStackTrace();throw e;}
  }
  public boolean needsExpediteApproval()throws Exception{
    try{
      String query = "select count(*) from delivery_schedule_change";
      query = query + " where (expedite_approval is null or lower(expedite_approval) != 'y') and ";
      query = query + "pr_number = "+prNum+" and ";
      query = query + "line_item = '"+lineNum+"' ";
      return DbHelpers.countQuery(db,query) > 0;
    }catch(Exception e){e.printStackTrace();throw e;}
  }

  public void saveSchedule(Vector v,int userId,String dbClient)throws Exception{
    changedSchedule = false;
    try{
      //if pr_status is posubmit then change and notify purchaser and hub personnels
      if(HelpObjs.countQuery(db,"select count(*) from purchase_request where pr_status = 'posubmit' and pr_number = "+prNum) > 0){
        // this is where we populate the delivery_schedule_change table
        Calendar now = Calendar.getInstance();
        String nowTime = formatDateTime(now);
        for(int i=0;i<v.size();i++){
          Hashtable h = (Hashtable)v.elementAt(i);

          if(!h.containsKey("REVISED_QTY"))continue;
          BigDecimal orig = (BigDecimal)h.get("QTY");
          BigDecimal revised = (BigDecimal)h.get("REVISED_QTY");
          // quantity wasn't changed, so don't store anything
          if(orig.compareTo(revised) == 0)continue;
          String myDate = formatDate((Calendar)h.get("DATE"));
          String s = "insert into delivery_schedule_change ";
          s = s + "(pr_number, line_item,date_to_deliver,original_qty,revised_qty,updated_date,updated_by) ";
          s = s + "values("+prNum+",'"+lineNum+"',to_date('"+myDate+"','mm/dd/yyyy'),"+orig+","+revised+",to_date('"+nowTime+"','mm/dd/yyyy/hh24/mi/ss'),"+userId+")";
          db.doUpdate(s);
          changedSchedule = true;
        }
      }else {
        if (allowQtyChange && qty.compareTo(new BigDecimal("0")) > 0) {
          db.doUpdate("update request_line_item set quantity = "+qty+" where pr_number = "+prNum+" and line_item = '"+lineNum+"'");
        }
      }

      // delete the old schedule
      String q = "delete from delivery_schedule where pr_number = "+prNum+" and line_item = '"+lineNum+"'";
      db.doUpdate(q);

      // now store the new schedule
      String lastCombineRow = "";
      for(int i=0;i<v.size();i++){
        Hashtable h = (Hashtable)v.elementAt(i);
        //combine row
        if (h.containsKey("COMBINE_ROW")) {
          String currentCombineRow = (String)h.get("COMBINE_ROW");
          if (lastCombineRow.equalsIgnoreCase(currentCombineRow)) {     //ship to next row if it is a combine row
            lastCombineRow = currentCombineRow;
            continue;
          }else {
            lastCombineRow = currentCombineRow;
          }
        }

        BigDecimal delQ = new BigDecimal("0");
        if(h.containsKey("REVISED_QTY")){
          delQ = (BigDecimal)h.get("REVISED_QTY");
        }else{
          delQ = (BigDecimal)h.get("QTY");
        }
        if(delQ.compareTo(new BigDecimal("0")) <= 0)continue;
        String myDate = formatDate((Calendar)h.get("DATE"));
        String s = "insert into delivery_schedule ";
        s = s + "(pr_number, line_item,date_to_deliver,qty) ";
        s = s + "values("+prNum+",'"+lineNum+"',to_date('"+myDate+"','mm/dd/yyyy'),"+delQ+")";
        db.doUpdate(s);
      }
      load();
    }catch(Exception e){e.printStackTrace();throw e;}
    //notify personnels so they can take the right action
    if(changedSchedule){
      //System.out.println("\n\n++++++++++++ NOT SENDING NOTIFICATION EMAIL TO BUYERS/CSR");
      this.notifyExpediterCSR(dbClient);
    }
  } //end of method

  private String formatDate(Calendar c){
    String myDate = (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR);
    return myDate;
  }
  private String formatDateTime(Calendar c){
    String myDate = (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR)+"/"+c.get(Calendar.HOUR_OF_DAY)+"/"+c.get(Calendar.MINUTE)+"/"+c.get(Calendar.SECOND);
    return myDate;
    }
  private void notifyExpediterCSR(String dbClient){
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try{
      // send a message to the expediter and CustomerServiceRep
      // about the schedule change
      Vector pids = new Vector(20);
      boolean hasPR = false;
      String query = "select distinct bo.branch_plant,bo.buyer_id,fx_customer_personnel_name(bo.buyer_id) full_name,bo.pr_number,"+
                     "bo.radian_po,r.receipt_id from tcm_ops.buy_order bo, tcm_ops.receipt r"+
                     " where bo.mr_number = "+prNum+" and bo.mr_line_item = '"+lineNum+"' and"+
                     " bo.date_issued = (select max(date_issued) from tcm_ops.buy_order where mr_number = "+prNum+" and mr_line_item = '"+lineNum+"')"+
                     " and bo.radian_po = r.radian_po(+) and bo.item_id = r.item_id(+)";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String tmpBuyer = "";
      String tmpBuyerName = "";
      String tmpBranchPlant = "";
      boolean firstTime = true;
      Vector radianPO = new Vector(11);
      Vector receipt = new Vector(11);
      while(rs.next()) {
        hasPR = true;
        if (firstTime) {
          tmpBuyer = BothHelpObjs.makeBlankFromNull(rs.getString("buyer_id"));
          tmpBuyerName = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));
          tmpBranchPlant = BothHelpObjs.makeBlankFromNull(rs.getString("branch_plant"));
          firstTime = false;
        }
        String tmpPO = BothHelpObjs.makeBlankFromNull(rs.getString("radian_po"));
        String tmpReceipt = BothHelpObjs.makeBlankFromNull(rs.getString("receipt_id"));
        if (!radianPO.contains(tmpPO)) {
          radianPO.addElement(tmpPO);
        }
        if (!receipt.contains(tmpReceipt)) {
          receipt.addElement(tmpReceipt);
        }
      }

      String subject = "Delivery Schedule Change, branch_plant: "+tmpBranchPlant;
      String msg = "";
      if (hasPR) {
        String tmpPO = "";
        String tmpReceipt = "";
        for (int i = 0; i < radianPO.size(); i++) {
          tmpPO += (String) radianPO.elementAt(i)+",";
        }
        //removing the last commas ","
        if (tmpPO.length() > 1) {
          tmpPO = tmpPO.substring(0,tmpPO.length()-1);
        }
        for (int ii = 0; ii < receipt.size(); ii++) {
          tmpReceipt += (String)receipt.elementAt(ii)+",";
        }
        //removing the last commas ","
        if (tmpReceipt.length() > 1) {
          tmpReceipt = tmpReceipt.substring(0,tmpReceipt.length()-1);
        }

        //buyer is assigned then get buyer name from personnel_id
        if (tmpBuyer.length() > 1) {
          if (!pids.contains(tmpBuyer)) {
            pids.addElement(tmpBuyer);
          }
          if (tmpPO.length() > 0 || tmpReceipt.length() > 0) {
            subject += " ("+tmpBuyerName;
            if (tmpPO.length() > 0) {
              subject += " - PO: "+tmpPO;
            }
            if (tmpReceipt.length() > 0) {
              subject += " - Receipt: "+tmpReceipt;
            }
            subject += ")";
          }else {
            subject += " ("+tmpBuyerName+")";
          }
        }else {
          if (tmpPO.length() > 0 || tmpReceipt.length() > 0) {
            subject += " (unassigned";
            if (tmpPO.length() > 0) {
              subject += " - PO: "+tmpPO;
            }
            if (tmpReceipt.length() > 0) {
              subject += " - Receipt: "+tmpReceipt;
            }
            subject += ")";
          }else {
            subject += " (unassigned)";
          }
        }
        //get CRS and Expediter for hub
        query = "select distinct p.personnel_id from personnel p, facility f, user_group_member ugm "+
                "where p.personnel_id = ugm.personnel_id and ugm.facility_id = f.facility_id and "+
                "f.branch_plant = "+tmpBranchPlant+" and ugm.user_group_id = 'CSR' union "+
                "select personnel_id from user_group_member where user_group_id = 'Expediter'";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          String tmp = rs.getString("personnel_id");
          if (!pids.contains(tmp)) {
            pids.addElement(tmp);
          }
        }
      }else {
        msg = "This request was treated like MM no buyer was notified.\n\n";
        //serving this schedule delivery from MM, notify CSR only
        query = "select distinct p.personnel_id from personnel p, facility f, user_group_member ugm, purchase_request pr "+
                "where p.personnel_id = ugm.personnel_id and ugm.facility_id = f.preferred_warehouse and "+
                "ugm.user_group_id = 'CSR' and pr.facility_id = f.facility_id and pr.pr_number = "+prNum;
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          String tmp = rs.getString("personnel_id");
          if (!pids.contains(tmp)) {
            pids.addElement(tmp);
          }
        }
      }

      msg += "Please review the following delivery schedule change:\n";
      // build the URL
      String url = "http://www.tcmis.com/tcmIS/servlet/radian.web.delivery."+dbClient.toLowerCase()+"."+dbClient+"DeliveryScheduleAdmin?prnumber="+prNum+"&lineItem="+lineNum+"&action=view";
      msg = msg + url+" \n\n";
      msg = msg + "Please review the schedule. If the schedule changes are not acceptable, ";
      msg = msg + "please contact the requestor to discuss other scheduling options.";
      for(int i=0;i<pids.size();i++) {
        Integer I = new Integer((String)pids.elementAt(i));
        HelpObjs.sendMail(db,subject,msg,I.intValue(),false);
      }

      //HelpObjs.sendMail(db,subject,msg,86030,false);    //TEST

    }catch(Exception e){
      e.printStackTrace();
      //System.out.println("###################TAKING OUT MAIL MESSAGE#########################");
      //HelpObjs.sendMail(db,"Error: Delivery Schedule Change","Delivery Schedule Change unable to send email notification mr: "+prNum+"-"+lineNum,86030,false);
    }finally {
      dbrs.close();
    }
  }

  public String toString(){
    String s = "";
    for(int i=0;i<delInfo.size();i++){
      Hashtable h = (Hashtable)delInfo.elementAt(i);
      Calendar c = (Calendar)h.get("DATE");
      BigDecimal qty = new BigDecimal((String)h.get("QTY"));
      s = s + "Date:"+BothHelpObjs.getDateStringFromCalendar(c)+"\tQty:"+qty+"\n";
    }
    return s;
  }
  public void printApprovedSchedule(){
    try{
      this.printScheduleVector(this.getApprovedSchedule());
    }catch(Exception e){e.printStackTrace();}

  }
  public void printScheduleVector(Vector v){
    for(int i=0;i<v.size();i++){
      Hashtable h = (Hashtable)v.elementAt(i);
      Calendar c = (Calendar)h.get("DATE");
      BigDecimal qty = new BigDecimal((String)h.get("QTY"));
      //System.out.println("Date:"+BothHelpObjs.getDateStringFromCalendar(c)+"\tQty:"+qty);
    }
  }

  public void expediterApprove()throws Exception{
    try{
      String query = "update delivery_schedule_change ";
      query = query + "set expedite_approval = 'y' ";
      query = query + "where pr_number = "+prNum+" and ";
      query = query + "line_item = '"+lineNum+"' ";
      db.doUpdate(query);
    }catch(Exception e){e.printStackTrace();throw e;}
  }
  public void csrApprove()throws Exception{
    try{
      String query = "update delivery_schedule_change ";
      query = query + "set csr_approval = 'y' ";
      query = query + "where pr_number = "+prNum+" and ";
      query = query + "line_item = '"+lineNum+"' ";
      db.doUpdate(query);
    }catch(Exception e){e.printStackTrace();throw e;}
  }
}
