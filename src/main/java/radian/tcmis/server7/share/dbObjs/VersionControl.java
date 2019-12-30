package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
 

import java.sql.*;
import java.util.*;


public class VersionControl {

   protected TcmISDBModel db;
   protected String version;
   protected String releasedDate; // yyyyddd
   protected String restart;     // 1 char
   protected Vector objects;
   protected Vector objAction;

   public VersionControl() throws Exception {

   }

   public VersionControl(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public String getVersion(){
     return version;
   }

   public void setVersion(String v){
     version = v;
   }

   public String getReleasedDate(){
     return releasedDate;
   }

   public void setReleasedDate(String d){
     releasedDate = d;
   }

   public String getRestart(){
     return restart;
   }

   public void setRestart(String r){
     restart = r;
   }

   public Vector getObject(){
     return objects;
   }

   public void setObject(Vector o){
     objects = o;
   }

   public Vector getObjectAction(){
     return objAction;
   }

   public void setObjectAction(Vector o){
     objAction = o;
   }

   public void load() throws Exception {
      //System.out.println("version:"+version);
      String query = "select version,to_char(date_released,'yyyyddd'),restart from tcmis_version where version= '" + version + "'";
      query = query + " order by date_released asc ";
      String query2 = new String();
      DBResultSet dbrs = null;
      DBResultSet dbrs2 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      Vector v;
      Vector v2;
      //System.out.println("query:"+query);
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setVersion(rs.getString("version"));
           setReleasedDate(rs.getString(2));
           setRestart(rs.getString("restart"));
           query2 = new String("select object_name,action from tcmis_object_update where version = '"+version+"' and action = 'D' order by vsize(object_name)");  // directories first
           v  = new Vector();
           v2  = new Vector();
           dbrs2 = db.doQuery(query2);   rs2=dbrs2.getResultSet();
           while (rs2.next()){
                 v.addElement(rs2.getString("object_name"));
                 v2.addElement(rs2.getString("action"));
           }
           dbrs2.close();

           query2 = new String("select object_name,action from tcmis_object_update where version = '"+version+"' and action != 'D'");  // then, files
           dbrs2 = db.doQuery(query2);   rs2=dbrs2.getResultSet();
           while (rs2.next()){
                 v.addElement(rs2.getString("object_name"));
                 v2.addElement(rs2.getString("action"));
           }
           dbrs2.close();
           
           setObject(v);
           setObjectAction(v2);



         }
         
         
     } catch (Exception e) {
       throw e;
       } finally{
             dbrs.close();
             dbrs2.close();
           }
   }

   //trong 10-4
   public void loadPreRelease(String curVersion) throws Exception {
      //System.out.println("version:"+version);
      String query2 = new String();
      DBResultSet dbrs = null;
      DBResultSet dbrs2 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      Vector v;
      Vector v2;
      //System.out.println("query:"+query);
      try {
        setVersion(curVersion);
        setRestart("M");
        query2 = new String("select object_name,action from tcmis_object_update where version = '"+version+"' and action = 'D' order by vsize(object_name)");  // directories first
        v  = new Vector();
        v2  = new Vector();
        dbrs2 = db.doQuery(query2);   rs2=dbrs2.getResultSet();
        while (rs2.next()){
          v.addElement(rs2.getString("object_name"));
          v2.addElement(rs2.getString("action"));
        }
        dbrs2.close();

        query2 = new String("select object_name,action from tcmis_object_update where version = '"+version+"' and action != 'D'");  // then, files
        dbrs2 = db.doQuery(query2);   rs2=dbrs2.getResultSet();
        while (rs2.next()){
          v.addElement(rs2.getString("object_name"));
          v2.addElement(rs2.getString("action"));
        }
        setObject(v);
        setObjectAction(v2);
     } catch (Exception e) {
       throw e;
     } finally{
        dbrs2.close();
     }
   }



   public Vector retrieve(String where,String sortBy) throws Exception {
      String query = "select version,to_char(date_released,'yyyyddd'),restart from tcmis_version ";
      if (where != (String)null) query = query + " where " + where ;
      if (sortBy != (String)null) query = query + " order by " + sortBy;

      DBResultSet dbrs = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      DBResultSet dbrs2 = null;
      Vector result = new Vector();
      String query2 = new String();
      try {
         VersionControl vc = new VersionControl(db);
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         Vector v;
         Vector v2;
         while (rs.next()){
           vc.setVersion(rs.getString("version"));
           vc.setReleasedDate(rs.getString("to_char(date_released,'yyyyddd')"));
           vc.setRestart(rs.getString("restart"));

           query2 = "select object_name, action from tcmis_object_update where version = '"+version+"'";
           v  = new Vector();
           v2  = new Vector();
           dbrs2 = db.doQuery(query2);   rs2=dbrs2.getResultSet();
           while (rs2.next()){
                 v.addElement(rs2.getString("object_name"));
                 v2.addElement(rs2.getString("action"));
           }
           dbrs2.close();
           vc.setObject(v);
           vc.setObjectAction(v2);

           result.addElement(vc);
         }
         
      
      } catch (Exception e) {  rs2.close();      
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); throw e;
       } finally{
             dbrs.close();
             dbrs2.close();
            }


      return result;
   }


   public String getLastVersion() throws Exception {
      String query = "select version from tcmis_version where date_released = ";
      query = query + " (select max(date_released) from tcmis_version)";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setVersion(rs.getString("version"));
         }
         
         
       } catch (Exception e) {
        
     
         //HelpObjs.monitor(1,"Error VersionControl:getLastVersion ="+e.getMessage());
        throw e;
       } finally{
             dbrs.close();
           
      }
      
      return version;
   }

   public Vector getAllVersions() throws Exception{
      String query = "select version,restart from tcmis_version where date_released > ";
      query = query + " to_date('"+this.releasedDate+"','yyyyddd') order by date_released asc ";
      Vector result = new Vector();
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           result.addElement(rs.getString("version"));
           if (rs.getString("restart").equalsIgnoreCase("O")) break; // stop if flag is "O"brigatory, so the user will do 1+ updates
         }
         
      
      } catch (Exception e) {       
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);throw e;
       } finally{
             dbrs.close();
            }
      return result;
   }

   public boolean isMandatory(String version) throws Exception {
      String query = "select restart from tcmis_version where version = ";
      query = query + " '"+version+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setRestart(rs.getString("restart"));
         }
         
     
       } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);       
  throw e;
       } finally{
             dbrs.close();
            }
      if (getRestart().equals("M")){
        return true;
      }
      return false;
   }

    public boolean needRestart(String version) throws Exception{
      String query = "select restart from tcmis_version where version = ";
      query = query + " '"+version+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setRestart(rs.getString("restart"));
         }
         
      
      } catch (Exception e) {
  e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); throw e;
       } finally{
             dbrs.close();
           }
      if (getRestart().equals("M") || getRestart().equals("Y")){
        return true;
      }
      return false;
   }

   //trong 10-4
   public Hashtable getPreReleaseInfo()throws Exception {
    Hashtable resultH = new Hashtable();
    String query = "select * from tcmis_prerelease_version";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector ipAddressV = new Vector();
      Vector versionV = new Vector();
      while (rs.next()){
        ipAddressV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("ip_address")));
        versionV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("version")));
      }
      resultH.put("IP_ADDRESSES",ipAddressV);
      resultH.put("VERSIONS",versionV);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    } finally{
      dbrs.close();
    }
    return resultH;
   }


}


