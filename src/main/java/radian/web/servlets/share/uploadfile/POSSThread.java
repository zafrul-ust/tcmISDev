package radian.web.servlets.share.uploadfile;

import java.io.*;
import java.util.*;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.PossObj;


public class POSSThread extends Thread {
  private String fileName;
  public POSSThread(String fileName) {
    this.fileName = fileName;
  }

  public void run(){
    try {
      //System.out.println("-------- start POSS Thread: "+fileName);
      processPOSSRequest();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  void processPOSSRequest() {
    RayTcmISDBModel db = null;
    try{
      db = new RayTcmISDBModel("Ray","2");
      if (db == null) {
        radian.web.emailHelpObj.sendtrongemail("Unable to get database connect","POSSThread - filename: "+fileName);
        return;
      }
      //getting data from file
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      Vector v = new Vector();
      String line = null;
      while((line=in.readLine()) != null) {
			v.addElement(new String(line));
		}
      in.close();
      //now process data
      PossObj p = new PossObj(db);
      p.updateTable(v,fileName);
    }catch (Exception ee) {
      ee.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Unable to process POSS file","POSSThread - filename: "+fileName+"\n"+ee.getMessage());
    }finally {
      db.close();
    }
  }
}  //end of class