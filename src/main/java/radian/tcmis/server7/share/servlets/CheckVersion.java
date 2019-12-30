package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class CheckVersion extends TcmisServlet {
  //Client Version
  String version = "0";
  String action = " ";
  protected Vector versions = null;
  protected String restart = null;
  protected Vector objects = null;
  protected String lastVersion = null;
  protected Vector objAction = null;

  String ip = null;  //trong 10-4


  public CheckVersion(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    version = "0";
    action = " ";
    versions = null;
    restart = null;
    objects = null;
    lastVersion = null;
    objAction = null;
  }

  protected void getResult(HttpInput httpI){
    //System.out.println("\n\n-------- inside check version servlet");
    version = httpI.getString("VERSION");
    action = httpI.getString("ACTION");

    ip = httpI.getClientIP();      //trong 10-4

    try{
         getTableData();
    }catch(Exception e){e.printStackTrace();}
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.printStart();
      out.println(versions);
      out.printEnd();
      out.printStart();
      out.println(restart);
      out.printEnd();
      out.printStart();
      out.println(objects);
      //// System.out.println("Object!!!:"+objects);
      out.printEnd();
      out.printStart();
      if (false) lastVersion = "99";  //use it to block all users to get in
      // to make release on incrementals
      /*
      if (
      //
          ip.startsWith("129.160.17.253")   ||        // Rodrigo
          ip.startsWith("129.160.")         ||        // Radian
          ip.startsWith("204.181.206.1")    ||        // Dallas
          ip.startsWith("204.181.206.2")    ||        // Dallas
          ip.startsWith("208.211.25.2")     ||        // Tucson
          ip.startsWith("208.211.25.3")     ||        // Tucson
          ip.startsWith("206.243.172.2")    ||        // Salem
          ip.startsWith("206.243.172.1")              // Salem
          /*
          !ip.startsWith("138.125.")         &&        // Raytheon
          !ip.startsWith("138.127.")         &&        // Raytheon
          !ip.startsWith("138.126.14.31")    &&        // 161480 from Ray (SC Bldg -	Yolanda	Taylor)
          !ip.startsWith("138.126.198.44")   &&        // 17343 from Ray (McKinney -	Kay	Sides)
          !ip.startsWith("144.219.140.101")  &&        // pphillips from Ray (Andover - Penny	Phillips-Powers)
          !ip.startsWith("138.126.200.75")   &&        // 454083 from Ray (McKinney	- Alan	Larsen)
          !ip.startsWith("199.242.253.26")   &&        // 00a8545 from Ray (Robert	Hannifan) - Tucson Airpot
          !ip.startsWith("147.24.234.29")    &&        // 0090490 from Ray (Eddie Portillo) - Tucson Airpot
          !ip.startsWith("147.24.164.14")              // 00j3101 from Ray (Gary Maley) - Tucson
          */
          /*
          ){
      } else {
         lastVersion = "1.4.04";
      }
        */

      out.println(lastVersion);
      out.printEnd();
      out.printStart();
      out.println(objAction);
      //// System.out.println("Action!!!:"+objAction);
      out.printEnd();

    }catch(Exception e){}
  }

  protected void getTableData() throws Exception{

  // System.out.println("Using OLD");
  // if Installing
      VersionControl vC = new VersionControl(db);

      Hashtable preReleaseInfo = vC.getPreReleaseInfo();
      Vector ipAddressV = (Vector)preReleaseInfo.get("IP_ADDRESSES");
      Vector versionV = (Vector)preReleaseInfo.get("VERSIONS");
      String preReleaseIp = null;
      boolean preReleaseUser = false;
      if (ipAddressV.size() > 0) {
        for (int i = 0; i < ipAddressV.size(); i++) {
          preReleaseIp = ipAddressV.elementAt(i).toString();
          if (ip.equals(preReleaseIp)) {
            lastVersion = versionV.elementAt(i).toString();
            preReleaseUser = true;
            break;
          }
        }
      }

      if (!preReleaseUser) {
        lastVersion = vC.getLastVersion();
      }
      vC.setVersion(version);
      vC.load();

      if (version.equals("99.99.99")){ //install version
            objects = (Vector) vC.getObject();
            objAction = (Vector) vC.getObjectAction();
            versions = new Vector();
            for (int j=0;j<objects.size();j++){
                versions.addElement(version) ; // so object and version have the same size
            }
      } else if (ip.equals(preReleaseIp)){
            //System.out.println("\n+++++++++++ let rock");
            Vector versionsV = new Vector();
            versionsV.addElement(lastVersion);
            objects = new Vector();
            objAction = new Vector();
            versions = new Vector();

            for (int i=0;i<versionsV.size();i++){
              vC.setVersion((String) versionsV.elementAt(i));
              vC.loadPreRelease(versionsV.elementAt(i).toString());
              Vector obj = (Vector) vC.getObject();
              Vector act = (Vector) vC.getObjectAction();
              for (int j=0;j<obj.size();j++){
                objects.addElement((String) obj.elementAt(j)) ;
                objAction.addElement((String) act.elementAt(j)) ;
                versions.addElement((String) versionsV.elementAt(i)) ; // so object and version have the same size
              }
              if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Version:" + (String) versionsV.elementAt(i),getBundle());
            }

      } else {
            Vector versionsV = vC.getAllVersions();
            objects = new Vector();
            objAction = new Vector();
            versions = new Vector();

            for (int i=0;i<versionsV.size();i++){
              vC.setVersion((String) versionsV.elementAt(i));
              vC.load();
              Vector obj = (Vector) vC.getObject();
              Vector act = (Vector) vC.getObjectAction();
              for (int j=0;j<obj.size();j++){
                objects.addElement((String) obj.elementAt(j)) ;
                objAction.addElement((String) act.elementAt(j)) ;
                versions.addElement((String) versionsV.elementAt(i)) ; // so object and version have the same size
              }
              if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Version:" + (String) versionsV.elementAt(i),getBundle());
            }

      }
      if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"version:"+versions+"\nObjects:"+objects,getBundle());
      restart = "M";
  }
  /*
    protected void getTableData() throws Exception{

  // System.out.println("Using OLD");
  // if Installing
      VersionControl vC = new VersionControl(db);
      lastVersion = vC.getLastVersion();
      vC.setVersion(version);
      vC.load();

      if (version.equals("99.99.99")){ //install version
            objects = (Vector) vC.getObject();
            objAction = (Vector) vC.getObjectAction();
            versions = new Vector();
            for (int j=0;j<objects.size();j++){
                versions.addElement(version) ; // so object and version have the same size
            }
      } else {
            Vector versionsV = vC.getAllVersions();
            objects = new Vector();
            objAction = new Vector();
            versions = new Vector();

            for (int i=0;i<versionsV.size();i++){
              vC.setVersion((String) versionsV.elementAt(i));
              vC.load();
              Vector obj = (Vector) vC.getObject();
              Vector act = (Vector) vC.getObjectAction();
              for (int j=0;j<obj.size();j++){
                objects.addElement((String) obj.elementAt(j)) ;
                objAction.addElement((String) act.elementAt(j)) ;
                versions.addElement((String) versionsV.elementAt(i)) ; // so object and version have the same size
              }
              if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"Version:" + (String) versionsV.elementAt(i),getBundle());
            }

      }
      if (getBundle().getString("DEBUG").equals("true")) HelpObjs.monitor(1,"version:"+versions+"\nObjects:"+objects,getBundle());
      restart = "M";
  }
  */


  protected  int getServInt(){
    return TcmisOutputStreamServer.CHECK_VERSION;
  }

}


































