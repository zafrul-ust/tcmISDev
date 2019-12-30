package radian.tcmis.server7.share.dbObjs;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.CallableStatement;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class CreateMRForPentaconScanObj {
  private TcmISDBModel db;

  public CreateMRForPentaconScanObj(TcmISDBModel db) {
    this.db = db;
  }

  //load data into stage table replenish_app_barcode_stage
  public boolean loadDataIntoStageTable(Vector dataV, String fileName) {
    boolean result = false;
    try {
      String query = "insert into replenish_app_barcode_stage(";
      for (int i = 0; i < dataV.size(); i++) {
        String column = "file_name,tcm_load_date,comments";
        String value = " values('"+fileName+"',sysdate,null";
        Hashtable h = (Hashtable) dataV.elementAt(i);
        Enumeration enuma = h.keys();
        while(enuma.hasMoreElements()) {
          String key = enuma.nextElement().toString();
          //skip company_id because I already now what company I am processing the request for
          if ("COMPANY_ID".equalsIgnoreCase(key)) {
            continue;
          }
          column += ","+key;
          String data = (String)h.get(key);
          value += ",'"+data+"'";
        }
        //put it all together and insert record in table
        column += ")";
        value += ")";
        db.doUpdate(query+column+value);
      }
      result = true;
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while insert data into table (replenish_app_barcode_stage)",fileName,86030,false);
      result = false;
    }
    return result;
  } //end of method

  public void createMRFromStageTable() throws Exception {
    try {
     callCreateMRProcedure();
    }catch (Exception e) {
      throw e;
    }
  } //end of method

  public void callCreateMRProcedure () throws Exception {
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
     connect1 = db.getConnection();
     cs = connect1.prepareCall("{call create_mr_replenish_barcode}");
     cs.execute();
    }catch (Exception e){
     e.printStackTrace();
     HelpObjs.sendMail(db,"Error occured while trying to call p_create_mr","Error occured while trying to call create_mr_replenish_barcode from pentacon scan",86030,false);
     throw e;
    }finally{
     cs.close();
    }
  } //end of method


} //end of class