package radian.tcmis.server7.share.dbObjs;

import java.sql.ResultSet;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class MaterialRequestObj {
  private TcmISDBModel db;

  public MaterialRequestObj(TcmISDBModel db) {
    this.db = db;
  }

  public void sendReleasedMsgToUser(String prNumber) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select rli.pr_number||'-'||rli.line_item mr_line,to_char(rli.release_date,'mm/dd/yyyy hh24:mi:ss') release_date,pr.requestor"+
                     " from purchase_request pr,request_line_item rli where pr.pr_number = rli.pr_number and rli.release_date is not null"+
                     " and pr.requestor <> nvl(pr.requested_releaser,-12323223425456) and pr.pr_number = "+prNumber;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String subject = "Your Material Request (#"+prNumber+") was approved.";
      String msg = "Your Material Request was approved:\n";
      int requestor = 0;
      while (rs.next()) {
        msg += "   MR-line: "+rs.getString("mr_line")+" released to Haas TCM at: "+rs.getString("release_date")+" (Central Time)\n";
        requestor = rs.getInt("requestor");
      }
      //send email to user
      if (requestor != 0) {
        HelpObjs.sendMail(db,subject,msg,requestor,false);
      }
      //System.out.println("----- "+subject+"\n"+msg+requestor);
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while trying to send release msg to user","Error while trying to send release msg to user: "+prNumber,86030,false);
    }finally {
      dbrs.close();
    }
  } //end of method

} //end of class