/*
SQLWKS> describe location
Column Name                    Null?    Type
------------------------------ -------- ----
LOCATION_ID                    NOT NULL VARCHAR2(30)
COUNTRY_ABBREV                          VARCHAR2(3)
STATE_ABBREV                            VARCHAR2(30)
ADDRESS_LINE_1                          VARCHAR2(30)
ADDRESS_LINE_2                          VARCHAR2(30)
ADDRESS_LINE_3                          VARCHAR2(30)
CITY                                    VARCHAR2(30)
ZIP                                     VARCHAR2(30)
LOCATION_DESC                           VARCHAR2(30)
CLIENT_LOCATION_CODE                    VARCHAR2(16)
*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class Location  {

   protected TcmISDBModel db;
   protected String location_id = null;
   protected String country_abbrev = null;
   protected String state_abbrev = null;
   protected String country = null;
   protected String state = null;
   protected String address_line1 = null;
   protected String address_line2 = null;
   protected String address_line3 = null;
   protected String city = null;
   protected String zip = null;
   protected String location_desc = null;
   protected String client_location_code = null;

   public Location()  throws Exception {

   }

   public Location(TcmISDBModel db)  throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setLocationId(String id) {
     this.location_id = id;
   }

   public String getLocationId() {
     return location_id;
   }

   public void setCountryAbbrev(String id) {
     this.country_abbrev = id;
   }

   public String getCountryAbbrev() {
     return country_abbrev;
   }

   public void setStateAbbrev(String id) {
     this.state_abbrev = id;
   }

   public String getStateAbbrev() {
     return state_abbrev;
   }

   public void setAddressLine1(String id) {
     this.address_line1 = id;
   }

   public String getAddressLine1() {
     return address_line1;
   }

   public void setAddressLine2(String id) {
     this.address_line2 = id;
   }

   public String getAddressLine2() {
     return address_line2;
   }

   public void setAddressLine3(String id) {
     this.address_line3 = id;
   }

   public String getAddressLine3() {
     return address_line3;
   }

   public void setCity(String id) {
     this.city = id;
   }

   public String getCity() {
     return city;
   }

   public void setZip(String id) {
     this.zip = id;
   }

   public String getZip() {
     return zip;
   }

   public void setLocationDesc(String id) {
     this.location_desc = id;
   }

   public String getLocationDesc() {
     return location_desc;
   }

   public void setClientLocationCode(String id) {
     this.client_location_code = id;
   }

   public String getClientLocationCode() {
     return client_location_code;
   }


   public String getState()  throws Exception {
     String query = "select state from v_state where state_abbrev = '" + HelpObjs.singleQuoteToDouble(this.state_abbrev) + "'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
      	dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
      	if (rs.next()) {
	        this.state = rs.getString(1);
	      }


      	return this.state;
     }catch (Exception e) {e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
           }
   }

   public String getCountry()  throws Exception {
     String query = "select country from vv_country where country_abbrev = '" + HelpObjs.singleQuoteToDouble(this.country_abbrev) + "'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	   if (rs.next()) {
	     this.country = rs.getString(1);
   	 }


 	   return this.country;
     }catch(Exception e) {	e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
           }
   }

   public void load()  throws Exception {
      String query = "select * from location where location_id = '" + HelpObjs.singleQuoteToDouble(location_id) + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           this.setLocationId(rs.getString("LOCATION_ID"));
           this.setCountryAbbrev(rs.getString("COUNTRY_ABBREV"));
           this.setStateAbbrev(rs.getString("STATE_ABBREV"));
           this.setAddressLine1(rs.getString("ADDRESS_LINE_1"));
           this.setAddressLine2(rs.getString("ADDRESS_LINE_2"));
           this.setAddressLine3(rs.getString("ADDRESS_LINE_3"));
           this.setCity(rs.getString("CITY"));
           this.setZip(rs.getString("ZIP"));
           this.setLocationDesc(rs.getString("LOCATION_DESC"));
           this.setClientLocationCode(rs.getString("CLIENT_LOCATION_CODE"));
         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return;
   }

   public String getAddressForId(String locId) throws Exception {
      this.setLocationId(locId);
      this.load();
      String tmpShip = new String(address_line1==null?"":(address_line1+"\n"));
      if (address_line2 !=  null) {
         tmpShip = tmpShip + new String(address_line2 + "\n");
      }
      if (address_line3 !=  null) {
         tmpShip = tmpShip + new String(address_line3 + "\n");
      }
      tmpShip = tmpShip + (city==null?"":(city + ", ")) + (state_abbrev==null?"":(state_abbrev + " ")) + (zip==null?"":zip);
      return tmpShip;
   }

   public Vector getAddressForIdVector(String locId) throws Exception {
      this.setLocationId(locId);
      this.load();
      Vector result = new Vector();
      result.addElement(address_line1==null?"":address_line1);
      if (address_line2 !=  null) {
         result.addElement(address_line2==null?"":address_line2);
      }
      if (address_line3 !=  null) {
         result.addElement(address_line3==null?"":address_line3);
      }
      result.addElement((city==null?"":(city + ", ")) + (state_abbrev==null?"":(state_abbrev + " ")) + (zip==null?"":zip));
      return result;
   }

   public Vector getLocIdByClient(String client) throws Exception{
     Vector v = new Vector();
     String query = "select location_id from location";
     if(!client.equalsIgnoreCase("Radian")) {
       query = query + " where location_desc like '" + HelpObjs.singleQuoteToDouble(client) + "%'";
     }

     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         v.addElement(rs.getString("LOCATION_ID"));
       }


     }catch (Exception e) { e.printStackTrace(); throw e;
     } finally{
             dbrs.close();

     }
     return v;
   }

   public Vector getAllLocationsForFacility(String facId) throws Exception{
     Vector v = new Vector();
     String query = "select dock_location_id from facility_dock where facility_id = '"+facId+"' and status = 'A' order by dock_location_id";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()){
         Location l = new Location(db);
         l.setLocationId(rs.getString("dock_location_id"));
         v.addElement(l);
       }
     }catch(Exception e){e.printStackTrace();throw e;
       } finally{
             dbrs.close();
           }
     return v;
   }

   public Object[][] getAllLocations(String textIn,String searchBy)  throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);

      Object[][] data = null;

      Vector result = new Vector();
      String query = "select location_id from location ";
      if (searchBy.equals("LOCATION_ID") ){
          query = query + " where lower(location_id) like lower('"+text+"%')" ;
        } else if (searchBy.equals("LOCATION_ADDRESS")){
          query = query + " where lower(address_line_1) like lower('%"+text+"%')" ;
          query = query + " or    lower(address_line_2) like lower('%"+text+"%')" ;
          query = query + " or    lower(address_line_3) like lower('%"+text+"%')" ;
          query = query + " or    lower(city) like lower('%"+text+"%')" ;
          query = query + " or    lower(zip) like lower('%"+text+"%')" ;
          query = query + " or    lower(state_abbrev) like lower('%"+text+"%')" ;

      }
      query = query + " order by location_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector tempV = new Vector();
      Object[] tempO;
      try {
           dbrs = db.doQuery(query);
           rs=dbrs.getResultSet();
           while (rs.next()){
             tempO = null;
             tempO = new Object[2];
             tempO[0] =  rs.getString(1);
             tempO[1] = "";
             tempV.addElement(tempO);
           }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }

      data = new Object[tempV.size()][2];
      for (int i=0;i<tempV.size();i++){
        tempO = null;
        tempO = new Object[2];
        tempO = (Object[]) tempV.elementAt(i);
        data[i][0] = tempO[0];
        data[i][1] = this.getAddressForId((String)tempO[0]).replace('\n',',');
      }
      return data;
   }

   public Vector getAllLocations()throws Exception{
     Vector v = new Vector();
     String query = "select * from location ";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()){
         Location l = new Location(db);
         l.setLocationId(rs.getString("LOCATION_ID"));
         l.setCountryAbbrev(rs.getString("COUNTRY_ABBREV"));
         l.setStateAbbrev(rs.getString("STATE_ABBREV"));
         l.setAddressLine1(rs.getString("ADDRESS_LINE_1"));
         l.setAddressLine2(rs.getString("ADDRESS_LINE_2"));
         l.setAddressLine3(rs.getString("ADDRESS_LINE_3"));
         l.setCity(rs.getString("CITY"));
         l.setZip(rs.getString("ZIP"));
         l.setLocationDesc(rs.getString("LOCATION_DESC"));
         l.setClientLocationCode(rs.getString("CLIENT_LOCATION_CODE"));
         v.addElement(l);
       }


     }catch(Exception e){e.printStackTrace();throw e;
       } finally{
             dbrs.close();
           }
     return v;
   }

   public Object[][] getAllLocsForClient(String client)  throws Exception {
      Object[][] data = null;

      Vector result = new Vector();
      String query = "select location_id from location ";
      query = query + " where lower(location_desc) like lower('"+client+"%')" ;
      query = query + " order by location_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector tempV = new Vector();
      Object[] tempO;
      try {
           dbrs = db.doQuery(query);
           rs=dbrs.getResultSet();
           while (rs.next()){
             tempO = null;
             tempO = new Object[2];
             tempO[0] =  rs.getString(1);
             tempO[1] = "";
             tempV.addElement(tempO);
           }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }

      data = new Object[tempV.size()][2];
      for (int i=0;i<tempV.size();i++){
        tempO = null;
        tempO = new Object[2];
        tempO = (Object[]) tempV.elementAt(i);
        data[i][0] = tempO[0];
        data[i][1] = this.getAddressForId((String)tempO[0]).replace('\n',',');
      }
      return data;
   }

   public boolean locationExist(String s) throws Exception{
     try{
     return (DbHelpers.countQuery(db,"location", "where location_id =  '" + HelpObjs.singleQuoteToDouble(s) + "'")) > 0;
     }catch(Exception e) {throw e;

           }
   }

   public void addLocation(String id) {
       try {
         if(locationExist(id))return;

         String query = "insert into location ";
         query = query + "(location_id)";
         query = query + " values ('"+HelpObjs.singleQuoteToDouble(id)+"')";
        db.doUpdate(query);


      } catch (Exception e) { e.printStackTrace();}
   }

   public void updateLocation() {
      String query = "update location ";
      query = query + "set ";
      query = query + "country_abbrev = '"+ HelpObjs.singleQuoteToDouble(this.getCountryAbbrev()) +"', ";
      query = query + "state_abbrev = '"+HelpObjs.singleQuoteToDouble(this.getStateAbbrev())+"', ";
      query = query + "address_line_1 = '"+HelpObjs.singleQuoteToDouble(this.getAddressLine1())+"', ";
      query = query + "address_line_2 = '"+HelpObjs.singleQuoteToDouble(this.getAddressLine2())+"', ";
      query = query + "address_line_3 = '"+HelpObjs.singleQuoteToDouble(this.getAddressLine3())+"', ";
      query = query + "city = '"+HelpObjs.singleQuoteToDouble(this.getCity())+"', ";
      query = query + "zip = '"+HelpObjs.singleQuoteToDouble(this.getZip())+"', ";
      query = query + "location_desc = '"+HelpObjs.singleQuoteToDouble(this.getLocationDesc())+"', ";
      query = query + "client_location_code = '"+HelpObjs.singleQuoteToDouble(this.getClientLocationCode())+"' ";
      query = query + " where location_id = '"+HelpObjs.singleQuoteToDouble(this.getLocationId())+"'";


      try {
         db.doUpdate(query);


      } catch (Exception e) { e.printStackTrace();}
   }

   public void deleteLocation() throws Exception{
     try {
       String query = "delete location where location_id = '"+location_id+"'";
       db.doUpdate(query);


     }catch(Exception e) {
       e.printStackTrace();
       throw e;
       }
   }

   public Vector getAllStates()  throws Exception {
     Vector v = new Vector();
     String query = "select state_abbrev from vv_state order by state_abbrev";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
         v.addElement(rs.getString("STATE_ABBREV"));
       }


     }catch(Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
 	   return v;
   }

   public Vector getAllLocationInfo(String s) throws Exception{
     Vector v = new Vector();
     boolean clientOnly = !BothHelpObjs.isBlankString(s);
     String query = "select * from location order by location_id";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
         String t = rs.getString("location_desc");
         if(clientOnly && (BothHelpObjs.isBlankString(t) || t.startsWith("Radian"))) continue;
         v.addElement(rs.getString("location_id"));
         v.addElement(rs.getString("address_line_1"));
         v.addElement(rs.getString("address_line_2"));
         v.addElement(rs.getString("address_line_3"));
         v.addElement(rs.getString("city"));
         v.addElement(rs.getString("state_abbrev"));
         v.addElement(rs.getString("country_abbrev"));
         v.addElement(rs.getString("zip"));
         v.addElement(rs.getString("client_location_code"));

         if(BothHelpObjs.isBlankString(t)){
           v.addElement("");
           continue;
         }
         int n = t.indexOf("/");
         if(n > 2){
           v.addElement(t.substring(0,n-1));
         }else{
           v.addElement("");
         }
       }


     }catch(Exception e) {e.printStackTrace();throw e;
     } finally{
             dbrs.close();

     }
 	   return v;
   }
   public String getPrefLocForWorkArea(String facId, String workArea) throws Exception {
     String query = "select nvl(location_id,' ') location_id from fac_loc_app where facility_id = '"+facId+"' and application = '"+workArea+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String result = "";
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         result = rs.getString("LOCATION_ID");
       }
     }catch(Exception e){
       e.printStackTrace();
       result = "";;
     } finally{
       dbrs.close();
     }
     return result;
   }
}






























