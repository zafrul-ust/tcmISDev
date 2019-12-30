package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;



import java.util.*;
import java.sql.*;

public class ApplicationView  {

   protected TcmISDBModel db;
   protected String facility_id;
   protected String application;
   protected String location_id;
   protected String application_desc;
   protected String deliveryPoint;
   protected String workAreaStatus;   //trong 1-29-01

   public ApplicationView()  throws java.rmi.RemoteException {

   }

   public ApplicationView(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setFacilityId(String id) {
     this.facility_id = id;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setApplication(String s) {
     this.application = s;
   }

   public String getApplication() {
     return application;
   }

   public void setApplicationDesc(String s) {
     this.application_desc = s;
   }

   //trong 1-29-01
   public void setWorkAreaStatus(String s) {
    if (s.equalsIgnoreCase("A")) {
      this.workAreaStatus = "Active";
    }else if (s.equalsIgnoreCase("I")) {
      this.workAreaStatus = "Inactive";
    }else {
      this.workAreaStatus = "";
    }
   }
   public void setWorkAreaStatusUpdate(String s) {
    if (s.equalsIgnoreCase("Active")) {
      this.workAreaStatus = "A";
    }else {
      this.workAreaStatus = "I";
    }
   }
   public String getWorkAreaStatus() {
    return workAreaStatus;
   }

   public String getApplicationDesc() {
     return application_desc;
   }
   public void setDeliveryPoint(String s) {
     if(BothHelpObjs.isBlankString(s) || s.equalsIgnoreCase("null")){
       s = "";
     }
     this.deliveryPoint = s;
   }

   public String getDeliveryPoint() {
     return deliveryPoint;
   }


   public void setLocationId(String s) {
     this.location_id = s;
   }

   public String getLocationId() {
     return location_id;
   }

   public void load()  throws Exception {
      String query = "select location_id, application_desc,delivery_point from fac_loc_app where facility_id = '" + HelpObjs.singleQuoteToDouble(facility_id) + "' and application = '" + HelpObjs.singleQuoteToDouble(application) + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           this.setLocationId(rs.getString("location_id"));
           this.setApplicationDesc(BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
           this.setDeliveryPoint(BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point")));
         }
       } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
         dbrs.close();
       }
       return;
   }

   public Vector getAllLocationIds()  throws Exception {
      String query = "select location_id from fac_loc_app where facility_id = '" + HelpObjs.singleQuoteToDouble(facility_id) + "' and application = '" + HelpObjs.singleQuoteToDouble(application) + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector V = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           V.addElement(rs.getString("location_id"));
         }
       } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
         dbrs.close();
       }
       return V;
   }

   // this method does not get the delivery_point
   public Hashtable getApplications()  throws Exception {
      String query = "select application, application_desc from fac_loc_app where facility_id = '" + HelpObjs.singleQuoteToDouble(facility_id) + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      String app = new String();
      String appDesc = new String();
      Hashtable H = new Hashtable();

      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           if(BothHelpObjs.isBlankString(rs.getString("application_desc"))){
             H.put(rs.getString("application"),"");
           }else{
             H.put(rs.getString("application"),rs.getString("application_desc"));
           }
         }
       } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
      return H;
   }

   public Vector getFacilities()  throws Exception {
      String query = "select facility_id from fac_loc_app where application = '" + HelpObjs.singleQuoteToDouble(application) + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector V = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           V.addElement(rs.getString("facility_id"));
         }

       } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
      return V;
   }

   public boolean applicationExist(String fac, String app) throws Exception{
     try{
       return (DbHelpers.countQuery(db,"fac_loc_app", "where facility_id =  '" + HelpObjs.singleQuoteToDouble(fac) + "' and application = '"+HelpObjs.singleQuoteToDouble(app)+"'")) > 0;
     }catch(Exception e) {throw e;}
   }

   //trong 10-24  the reason I add an extra string s at the end is because addApplication with (string,string,string,string) already taken
   public void addApplication(String fac,String app, String appDesc, String loc, String s) throws Exception{
     try{
       if(applicationExist(fac,app)) return;

       String query = "insert into fac_loc_app ";
       query = query + "(facility_id, application,application_desc, location_id)";
       query = query + " values ('"+HelpObjs.singleQuoteToDouble(fac)+"','"+HelpObjs.singleQuoteToDouble(app)+"','"+HelpObjs.singleQuoteToDouble(appDesc)+"','"+HelpObjs.singleQuoteToDouble(loc)+"')";
       db.doUpdate(query);
     }catch(Exception e) {throw e;}
   }
   public void addApplication(String fac,String app, String loc) throws Exception{
     try{
       if(applicationExist(fac,app)) return;

       String query = "insert into fac_loc_app ";
       query = query + "(facility_id, application, location_id)";
       query = query + " values ('"+HelpObjs.singleQuoteToDouble(fac)+"','"+HelpObjs.singleQuoteToDouble(app)+"','"+HelpObjs.singleQuoteToDouble(loc)+"')";
       db.doUpdate(query);
     }catch(Exception e) {throw e;}
   }
   public void addApplication(String fac,String app, String loc,String defaultDelPoint) throws Exception{
     try{
       if(applicationExist(fac,app)) return;

       String query = "insert into fac_loc_app ";
       query = query + "(facility_id, application, location_id,delivery_point)";
       query = query + " values ('"+HelpObjs.singleQuoteToDouble(fac)+"','"+HelpObjs.singleQuoteToDouble(app)+"','"+HelpObjs.singleQuoteToDouble(loc)+"','"+HelpObjs.singleQuoteToDouble(defaultDelPoint)+"')";

      db.doUpdate(query);
     }catch(Exception e) {throw e;}
   }

   public void updateApplication() throws Exception {
      String query = "update fac_loc_app ";
      query = query + "set ";
      query = query + "location_id = '"+HelpObjs.singleQuoteToDouble(this.getLocationId())+"', ";
      query = query + "application_desc = '"+HelpObjs.singleQuoteToDouble(this.getApplicationDesc())+"', ";
      query = query + "delivery_point = '"+HelpObjs.singleQuoteToDouble(this.getDeliveryPoint())+"' ";
      query = query + " where application = '"+HelpObjs.singleQuoteToDouble(this.getApplication())+"' and facility_id = '"+HelpObjs.singleQuoteToDouble(this.getFacilityId())+"'";


      try {
         db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   //trong 1-29-01
   public void updateApplicationNew() throws Exception {
      String query = "update fac_loc_app ";
      query = query + "set ";
      query = query + "location_id = '"+HelpObjs.singleQuoteToDouble(this.getLocationId())+"', ";
      query = query + "application_desc = '"+HelpObjs.singleQuoteToDouble(this.getApplicationDesc())+"', ";
      query = query + "delivery_point = '"+HelpObjs.singleQuoteToDouble(this.getDeliveryPoint())+"', ";
      query = query + "status = '"+HelpObjs.singleQuoteToDouble(this.getWorkAreaStatus())+"' ";
      query = query + " where application = '"+HelpObjs.singleQuoteToDouble(this.getApplication())+"' and facility_id = '"+HelpObjs.singleQuoteToDouble(this.getFacilityId())+"'";
      //System.out.println("\n\n--------- update application query: "+query);
      try {
         db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   public void deleteApplication() throws Exception {
      String query = "delete fac_loc_app ";
      query = query + "where application = '"+HelpObjs.singleQuoteToDouble(this.getApplication())+"' and facility_id = '"+HelpObjs.singleQuoteToDouble(this.getFacilityId())+"'";

      try {
         db.doUpdate(query);
      } catch (Exception e) { e.printStackTrace();throw e;}
  }

   public Hashtable getWorkAreaInfo(String userID) throws Exception {
     Hashtable result = new Hashtable(3);
     Vector v = new Vector();
     Vector facIDs = new Vector();
     Vector facNames = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       String query = "select count(*) from user_group_member where user_group_id = 'SuperUser' and personnel_id = "+userID;
       //if user is a super user, then allow him/her to see all facilities and work areas.
       //Otherwise, he/she could only see facilities and work areas that he/she is an adminstrator for
       if (HelpObjs.countQuery(db,query) > 1) {
        query = "select fla.facility_id,f.facility_name,fla.application,fla.application_desc,fla.location_id,fla.status,fla.delivery_point,fld.delivery_point_desc"+
                " from fac_loc_delivery_point fld,facility f,fac_loc_app fla where fld.delivery_point(+) = fla.delivery_point and fla.facility_id = f.facility_id and"+
                " fla.application not in ('BG','CR','TC','All')";
       }else {
        query = "select fla.facility_id,f.facility_name,fla.application,fla.application_desc,fla.location_id,fla.status,fla.delivery_point,fld.delivery_point_desc"+
                " from fac_loc_delivery_point fld,user_group_member ugm,facility f,fac_loc_app fla where fld.delivery_point(+) = fla.delivery_point and fla.facility_id = f.facility_id and"+
                " ugm.facility_id = f.facility_id and ugm.user_group_id = 'Administrator' and ugm.personnel_id = "+userID+
                " and fla.application not in ('BG','CR','TC','All')";
       }

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         String tmp = rs.getString("facility_id");
         if (!facIDs.contains(tmp)) {
          facIDs.addElement(tmp);
          facNames.addElement(rs.getString("facility_name"));
         }
         v.addElement(tmp);
         v.addElement(rs.getString("application"));
         v.addElement(BothHelpObjs.isBlankString(rs.getString("application_desc"))?"":rs.getString("application_desc"));
         v.addElement(BothHelpObjs.isBlankString(rs.getString("location_id"))?"":rs.getString("location_id"));
         v.addElement(BothHelpObjs.isBlankString(rs.getString("delivery_point"))?"":rs.getString("delivery_point"));
         v.addElement(BothHelpObjs.isBlankString(rs.getString("delivery_point_desc"))?"":rs.getString("delivery_point_desc"));
         v.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("status")));
       }
       result.put("FAC_LOC_APP",v);
       result.put("FACILITY_ID",facIDs);
       result.put("FACILITY_NAME",facNames);

       //next get dock for facility
       query = "select fstl.facility_id,l.location_id,decode(l.location_desc,null,l.location_id,' ',l.location_id,l.location_id||' : '||l.location_desc) location_desc,"+
               "'Y' status from facility_ship_to_location where facility_id in (";
       for (int i = 0; i < facIDs.size(); i++) {
        query += "'"+(String)facIDs.elementAt(i)+"',";
       }
          //removing the last commas and add close parathesis
       query = query.substring(0,query.length()-1) + ")";
       Hashtable locationInfo = new Hashtable(facIDs.size());
       String lastFacID = "";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         String tmp = rs.getString("facility_id");
         if (!tmp.equalsIgnoreCase(lastFacID)) {
          Hashtable h = new Hashtable(3);
          Vector locationID = new Vector(5);
          Vector locationDesc = new Vector(5);
          Vector locationStatus = new Vector(5);
          locationID.addElement(rs.getString("location_id"));
          locationDesc.addElement(rs.getString("location_desc"));
          locationStatus.addElement(rs.getString("status"));
          h.put("LOCATION_ID",locationID);
          h.put("LOCATION_DESC",locationDesc);
          h.put("LOCATION_STATUS",locationStatus);
          locationInfo.put(tmp,h);
         }else {
          Hashtable h = (Hashtable)locationInfo.get(tmp);
          Vector locationID = (Vector)h.get("LOCAITON_ID");
          Vector locationDesc = (Vector)h.get("LOCAITON_DESC");
          Vector locationStatus = (Vector)h.get("LOCATION_STATUS");
          locationID.addElement(rs.getString("location_id"));
          locationDesc.addElement(rs.getString("location_desc"));
          locationStatus.addElement(rs.getString("status"));
          h.put("LOCATION_ID",locationID);
          h.put("LOCATION_DESC",locationDesc);
          h.put("LOCATION_STATUS",locationStatus);
          locationInfo.put(tmp,h);
         }
         lastFacID = tmp;
       }
       result.put("LOCATION_INFO",locationInfo);

       //finally get delivery point

     }catch(Exception e){
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }
     return result;
   }  //end of method

   public Vector getAllApps(String where) throws Exception{
     Vector v = new Vector();
     DBResultSet dbrs = null;
       ResultSet rs = null;
     try{
       String query = "select * from fac_loc_app ";
       if(!BothHelpObjs.isBlankString(where)){
         query = query + where;
       }

       //System.out.println("\n\n========== what the ******** "+query);

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         ApplicationView av = new ApplicationView(db);
         av.setFacilityId(rs.getString("facility_id"));
         av.setApplication(rs.getString("application"));
         av.setWorkAreaStatus(BothHelpObjs.makeBlankFromNull(rs.getString("status")));        //trong 1-29-01
         av.setApplicationDesc(BothHelpObjs.isBlankString(rs.getString("application_desc"))?"":rs.getString("application_desc"));
         av.setLocationId(BothHelpObjs.isBlankString(rs.getString("location_id"))?"":rs.getString("location_id"));
         av.setDeliveryPoint(BothHelpObjs.isBlankString(rs.getString("delivery_point"))?"":rs.getString("delivery_point"));
         v.addElement(av);
       }

     }catch(Exception e){e.printStackTrace();throw e;
     } finally{
         dbrs.close();
       }
     return v;
   }

   public Vector getAppsForFacs(Facility[] fa) throws Exception{
     //System.out.println("fasize:"+fa.length);
     Vector v = new Vector();
     try{
       for(int i=0;i<fa.length;i++){
         Vector v1 = new Vector();
         v1 = getAppsForFac(fa[i]);
         for(int j=0;j<v1.size();j++){
           v.addElement(v1.elementAt(j));
         }
       }
     }catch(Exception e){e.printStackTrace();throw e;}
     return v;
   }

   public Vector getAppsForFac(Facility f) throws Exception{
     Vector v = new Vector();
     try{
       v = getAllApps("where facility_id = '"+f.getFacilityId() +"'");
     }catch(Exception e){e.printStackTrace();throw e;}
     return v;
   }
}
