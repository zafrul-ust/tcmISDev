 /*
SQLWKS> describe facility
Column Name                    Null?    Type
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
COMPANY_ID                              VARCHAR2(30)
FACILITY_TYPE                           VARCHAR2(30)
MAIL_LOCATION                           VARCHAR2(30)
FACILITY_NAME                  NOT NULL VARCHAR2(30)
SHIPPING_LOCATION                       VARCHAR2(30)
BRANCH_PLANT                            VARCHAR2(12)
PREFERRED_WAREHOUSE                     VARCHAR2(30)
JDE_ID                                  NUMBER
JDE_BILL_TO                             NUMBER
LOCALE                                  VARCHAR2(60)
 */

/* builds catalog choices based on
SQL> describe catalog_facility
 Name                            Null?    Type
 ------------------------------- -------- ----
 CATALOG_ID                               VARCHAR2(30)
 FACILITY_ID                              VARCHAR2(30)
*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.sql.*;
import java.util.*;
import radian.tcmis.server7.share.db.*;

public class Facility {

   protected TcmISDBModel db;
   protected String facility_id;
   protected String company_id;
   protected String company_name;
   protected String facility_type;
   protected String facility_type_desc;
   protected String mail_location;
   protected String facility_name;
   protected String shipping_location;
   protected String branch_plant;
   protected String preferred_warehouse;
   protected String jde_id;
   protected String jde_bill_to;
   protected String locale;
   protected String payloadId;

   public Facility()  throws java.rmi.RemoteException {

   }

   public Facility(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPayloadId(String payloadId) {
     this.payloadId = payloadId;
   }

   public String getPayloadId() {
     return payloadId;
   }

   public void setFacilityId(String id) {
     this.facility_id = id;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setCompanyId(String id) {
     this.company_id = id;
   }

   public String getCompanyId() {

     return company_id;
   }

   public void setFacilityType(String id) {
     this.facility_type = id;
   }

   public String getFacilityType() {
     return facility_type;
   }

   public String getJdeID() {
     return jde_id;
   }
   public String getJdeBillTo() {
     return jde_bill_to;
   }

   public void setMailLocation(String loc) {
     this.mail_location = loc;
   }

   public String getMailLocation() {
     return mail_location;
   }

   public void setBranchPlant(String loc) {
     this.branch_plant = loc;
   }

   public String getBranchPlant() {
     return branch_plant;
   }

   public void setJdeID(String id) {
     this.jde_id = id;
   }
   public void setJdeBillTo(String id) {
     this.jde_bill_to = id;
   }

   public void setFacilityName(String id) {
     this.facility_name = id;
   }

   public void setPreferredWare(String ware) {
     this.preferred_warehouse = ware;
   }

   public String getPreferredWare() {
     return preferred_warehouse;
   }
   public void setLocale(String s) {
     this.locale = s;
   }

   public String getLocale() {
     return locale;
   }

   public String getFacilityName()  throws Exception {
     if (facility_name == (String) null){
       String query = new String("select facility_name from facility where facility_id = '" + this.facility_id + "'");
       DBResultSet dbrs = null;
      ResultSet rs = null;
       try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
      	  if (rs.next()) {
      	    this.facility_name = rs.getString("facility_name");
      	  }

       } catch (Exception e) {
          e.printStackTrace();
           HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
           throw e;
       } finally{
         dbrs.close();
       }
     }
     return this.facility_name;
   }

   public void setShippingLocation(String loc) {
     this.shipping_location = loc;
   }

   public String getShippingLocation() {
     return shipping_location;
   }

   public String getCompanyName()  throws Exception  {
     String query = new String("select company_name from company where company_id = '" + this.company_id + "'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     if (rs.next()) {
	       this.company_name = rs.getString("company_name");
	     }

 	     return this.company_name;
     }catch (Exception e) {
	     e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     } finally{
         dbrs.close();
       }
   }

   public String getFacilityDesc()  throws Exception {
     String query = new String("select facility_type_desc from vv_facility_type where facility_type = '" + this.facility_type + "'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
	     dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     if (rs.next()) {
	       this.facility_type_desc = rs.getString("facility_type_desc");
       }

 	     return this.facility_type_desc;
     }catch (Exception e) {
	     e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
         dbrs.close();
       }
   }

   public void load()  throws Exception {
      String query = "select * from facility where facility_id = '" + HelpObjs.singleQuoteToDouble(facility_id) +"'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           facility_id = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
           company_id = BothHelpObjs.makeBlankFromNull(rs.getString("company_id"));
           facility_type = BothHelpObjs.makeBlankFromNull(rs.getString("facility_type"));
           mail_location = BothHelpObjs.makeBlankFromNull(rs.getString("mail_location"));
           facility_name = BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"));
           shipping_location = BothHelpObjs.makeBlankFromNull(rs.getString("shipping_location"));
           branch_plant = BothHelpObjs.makeBlankFromNull(rs.getString("branch_plant"));
           preferred_warehouse =  BothHelpObjs.makeBlankFromNull(rs.getString("preferred_warehouse"));
           jde_id = BothHelpObjs.makeBlankFromNull(rs.getString("jde_id"));
           jde_bill_to = BothHelpObjs.makeBlankFromNull(rs.getString("jde_bill_to"));
           //locale = rs.getString("locale");
         }


      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
   }

   public Vector getAllFacilityIds()  throws Exception {
      String query = "select facility_id from facility where branch_plant is null order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector allF = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           allF.addElement(rs.getString("facility_id"));
         }
         allF.trimToSize();


      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);

      throw e;
      } finally{
         dbrs.close();
       }
      return allF;
   }

   public Vector getAllWarehouseIds()  throws Exception {
      String query = "select facility_id from facility where branch_plant is not null";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector allF = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           allF.addElement(rs.getString("facility_id"));
         }
         allF.trimToSize();

     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
     } finally{
         dbrs.close();
       }
      return allF;
   }


   public Vector retrieve(String where,String sortBy)  throws Exception {
      String query = "select * from facility";
      if (where != (String)null) query = query + " where " + where ;
      if (sortBy != (String)null) query = query + " order by " + sortBy;

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Facility f;
      Vector allF = new Vector();
      try {
         f = new Facility();
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           f.setFacilityId(rs.getString("facility_id"));
           f.setCompanyId(rs.getString("company_id"));
           f.setFacilityType(rs.getString("facility_type"));
           f.setMailLocation(rs.getString("mail_location"));
           f.setFacilityName(rs.getString("facility_name"));
           f.setShippingLocation(rs.getString("shipping_location"));
           f.setBranchPlant(rs.getString("branch_plant"));
           f.setPreferredWare(rs.getString("preferred_warehouse"));
           f.setJdeID(rs.getString("jde_id"));
           f.jde_bill_to = rs.getString("jde_bill_to");
           allF.addElement(f);
         }
         allF.trimToSize();

      } catch (Exception e) {
      e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
      return allF;
   }

   public Object[][] getAllFacs(String textIn,String searchBy)  throws Exception {
     String text = HelpObjs.singleQuoteToDouble(textIn);

      Object[][] data = null;

      Vector result = new Vector();
      String query = "select facility_id, facility_name from facility where branch_plant is null";
      if (searchBy.equals("FACILITY_ID") ){
          query = query + " and lower(facility_id) like lower('"+text+"%')" ;
      } else if (searchBy.equals("FACILITY_NAME")){
          query = query + " and lower(facility_name) like lower('%"+text+"%')" ;
      }
      query = query + " order by facility_id";
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
             tempO[0] =  rs.getString("facility_id");
             tempO[1] =  rs.getString("facility_name");
             tempV.addElement(tempO);
           }


     } catch (Exception e) {
      e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     } finally{
         dbrs.close();
       }

      data = new Object[tempV.size()][2];
      for (int i=0;i<tempV.size();i++){
        tempO = null;
        tempO = new Object[2];
        tempO = (Object[]) tempV.elementAt(i);
        data[i][0] = tempO[0];
        data[i][1] = tempO[1];
      }
      return data;
   }
   public Vector getAllFacs()  throws Exception {
      Vector ids = new Vector();
      String query = "select facility_id from facility where branch_plant is null";
      query = query + " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          ids.addElement(rs.getString("facility_id"));
        }
     } catch (Exception e) {
      e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     } finally{
         dbrs.close();
     }

     Vector data = new Vector();
     for(int i=0;i<ids.size();i++){
       Facility f = new Facility(db);
       f.setFacilityId(ids.elementAt(i).toString());
       f.load();
       data.addElement(f);
     }
     return data;
   }

   //trong 10-9
   public Hashtable getAllFacsNew()  throws Exception {
      Hashtable myResult = new Hashtable();
      String query = "select facility_id,preferred_warehouse from facility where branch_plant is null";
      query = query + " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          myResult.put(rs.getString("facility_id"),rs.getString("preferred_warehouse")==null?"":rs.getString("preferred_warehouse"));
          //myResult.put(rs.getString("facility_id"),rs.getString("preferred_warehouse"));
        }
     } catch (Exception e) {
      e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
         dbrs.close();
     }
     return myResult;
   }

   public Hashtable getAllFacXRef()  throws Exception {

      String query = "select facility_id, facility_name from facility where branch_plant is null order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = new Hashtable();
      Vector resultV = new Vector();
      try {
           dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
           while (rs.next()){
             String faci  = (rs.getString("facility_id")==null?"":rs.getString("facility_id"));
             String facn  = (rs.getString("facility_name")==null?"":rs.getString("facility_name"));
             result.put(faci,facn);
           }


      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }

      return result;
   }

   public Vector getAllFacXRef2()  throws Exception {

      String query = "select facility_id, facility_name from facility where branch_plant is null and FACILITY_TYPE is null and active = 'y' order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = null;
      Vector resultV = new Vector();
      try {
           dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
           while (rs.next()){
             result = new Hashtable();
             String faci  = (rs.getString("facility_id")==null?"":rs.getString("facility_id"));
             String facn  = (rs.getString("facility_name")==null?"":rs.getString("facility_name"));
             result.put(faci,facn);
             resultV.addElement(result);
           }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }

      return resultV;
   }

   public void addFacility(String id,String name) throws Exception{
     try{
       if(facilityExist(id)) return;

       String query = "insert into facility ";
       query = query + "(facility_id, facility_name)";
       query = query + " values ('"+ HelpObjs.singleQuoteToDouble(id)  +"','"+name+"')";
       db.doUpdate(query);

     }catch(Exception e) {throw e;}
   }


   public void addFacility(String id,String name,String facType) throws Exception{
     try{
       if(facilityExist(id)) return;

       String query = "insert into facility ";
       query = query + "(facility_id, facility_name, facility_type)";
       query = query + " values ('"+ HelpObjs.singleQuoteToDouble(id)  +"','"+name+"','"+facType+"')";
       db.doUpdate(query);

     }catch(Exception e) {throw e;}
   }

   public Hashtable getAllWarehouses() throws Exception{
     String query = "select facility_id, facility_name from facility where branch_plant is not null order by facility_id";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Hashtable result = new Hashtable();
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         result.put(rs.getString("facility_id"),rs.getString("facility_name"));
       }


     }catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
     } finally{
         dbrs.close();
     }
     return result;
   }

   public boolean facilityExist(String in) throws Exception{
     String s = HelpObjs.singleQuoteToDouble(in);
     try{
       return (DbHelpers.countQuery(db,"facility", "where facility_id =  '" + s + "'")) > 0;
     }catch(Exception e) {throw e;}
   }

   public void updateFacility() throws Exception {
      try {
        String query = "update facility ";
        query = query + "set ";
        query = query + "company_id = '"+this.getCompanyId()+"', ";
        query = query + "facility_type = '"+this.getFacilityType()+"', ";
        query = query + "mail_location = '"+this.getMailLocation()+"', ";
        query = query + "facility_name = '"+ HelpObjs.singleQuoteToDouble(this.getFacilityName()) +"', ";
        query = query + "shipping_location = '"+this.getShippingLocation()+"', ";
        query = query + "branch_plant = '"+ HelpObjs.singleQuoteToDouble(this.getBranchPlant()) +"', ";
        query = query + "preferred_warehouse = '"+this.getPreferredWare()+"', ";
        query = query + "jde_id = "+this.getJdeID()+", ";
        query = query + "jde_bill_to = "+this.getJdeBillTo()+" ";
        query = query + " where facility_id = '"+ HelpObjs.singleQuoteToDouble(this.getFacilityId()) +"'";

       db.doUpdate(query);
      } catch (Exception e) { e.printStackTrace(); throw e;}


   }

   public String getFacIDFromJde(String i) throws Exception{
     String query = "select facility_id from facility where jde_id = '" +i+"'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String result = "";
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         result = rs.getString("facility_id");
       }


     }catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    } finally{
         dbrs.close();
     }
     return result;
   }

   public Vector getEveryFac(String where)  throws Exception {
      Vector result = new Vector();
      String query = "select facility_id, facility_name from facility ";
      query = query + where + " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector tempV = new Vector();
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Facility f = new Facility(db);
          f.setFacilityId(rs.getString("facility_id"));
          result.addElement(f);
        }

     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
     throw e;
     } finally{
         dbrs.close();
     }
      return result;
   }

   // this method does not get delivery_point
   public Object[][] getAppsForFac(String myFacID)  throws Exception {
      Object[][] data = null;

      Vector result = new Vector();
      String query = "select application, application_desc from fac_loc_app where facility_id = '"+ HelpObjs.singleQuoteToDouble(myFacID) +"'";
      query = query + " order by application";
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
          tempO[0] =  rs.getString("application");
          tempO[1] =  BothHelpObjs.isBlankString(rs.getString("application_desc"))?"":rs.getString("application_desc");
          tempV.addElement(tempO);
        }


     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
     throw e;
     } finally{
         dbrs.close();
     }

      data = new Object[tempV.size()][2];
      for (int i=0;i<tempV.size();i++){
        tempO = null;
        tempO = new Object[2];
        tempO = (Object[]) tempV.elementAt(i);
        data[i][0] = tempO[0];
        data[i][1] = tempO[1];
      }
      return data;
   }

   public boolean deleteFacility(){
     try{
       String query = "delete facility where facility_id = '"+facility_id+"'";
       db.doUpdate(query);
     }catch(Exception e) {return false;}
     return true;
   }

   /* This will load just the facilities that the user is allowed
      on facPref table
   */
   public Hashtable getAllCatXRef(int userid)  throws Exception {
      String query = "select distinct c.catalog_id, c.catalog_desc from catalog c,fac_pref f, catalog_facility cf where "+
             			" cf.facility_id = f.facility_id and c.catalog_id=cf.catalog_id and c.catalog_company_id = cf.catalog_company_id "+
							"and personnel_id = "+userid+ " order by c.catalog_desc ";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = new Hashtable();
      try {
           dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
           while (rs.next()){
             String c = rs.getString("catalog_id");
             String d = rs.getString("catalog_desc");
             String faci  = (c==null?"":c);
             String facn  = (d==null?"":d);
             result.put(facn,faci);
           }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
     }

      return result;
   }

   public Hashtable getAllcatXFac(int userid) throws Exception {
      Hashtable catxfac = new Hashtable();
      Hashtable catXref = this.getAllCatXRef(userid);
      for (Enumeration E=catXref.keys();E.hasMoreElements();){
        String temp = (String) catXref.get(E.nextElement());
        Hashtable facXref = getFacsForCat(temp);
        catxfac.put(temp,facXref);
      }

      return catxfac;
   }

   protected Hashtable getFacsForCat(String cat) throws Exception {
      String query = "select c.facility_id , f.facility_name from catalog_facility c, facility f where c.facility_id=f.facility_id and c.catalog_id = '"+ HelpObjs.singleQuoteToDouble(cat) +"'";
      query = query + " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = new Hashtable();
      try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while (rs.next()){
          result.put(rs.getString("facility_name"),rs.getString("facility_id"));
        }
     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      dbrs.close();
      throw e;

      } finally{
         dbrs.close();
     }

     return result;
   }

   public  Vector getCatForFac(String fac) throws Exception {
      String query = "select c.catalog_id , c.catalog_desc from catalog c, catalog_facility f "+
							"where c.catalog_company_id = f.catalog_company_id and c.catalog_id=f.catalog_id "+
							"and f.display = 'Y' and f.facility_id = '"+ HelpObjs.singleQuoteToDouble(fac) +"'"+
      				   " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector result = new Vector();
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          result.addElement(rs.getString(1));
          result.addElement(rs.getString(2));
        }
     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
     }

     return result;
   }

   public  Hashtable getCatalogForFacility(String fac) throws Exception {
      String query = "select c.catalog_id , c.catalog_desc, f.catalog_company_id from catalog c, catalog_facility f"+
		 				   " where c.catalog_company_id = f.catalog_company_id and c.catalog_id = f.catalog_id and f.display = 'Y' and f.catalog_add_allowed = 'Y'"+
		               " and f.facility_id = '"+ HelpObjs.singleQuoteToDouble(fac) +"'"+
      				   " order by facility_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = new Hashtable();
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
			 String[] oa = new String[3];
			 oa[0] = rs.getString("catalog_company_id");
			 oa[1] = rs.getString("catalog_id");
			 oa[2] = rs.getString("catalog_desc");
			 result.put(rs.getString("catalog_id")+rs.getString("catalog_company_id"),oa);
		  }
     } catch (Exception e) {
     	e.printStackTrace();
     	HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
     } finally{
         dbrs.close();
     }

     return result;
   }

	public static Vector getCatalogFacilityInfo(TcmISDBModel db,int userId) throws Exception{
     Vector v = new Vector();
     Facility f = new Facility(db);
     // facName --> facId
     Hashtable cats = f.getAllCatXRef(userId);
     for (Enumeration E=cats.keys();E.hasMoreElements();){
       String catName = E.nextElement().toString();
       Hashtable facs = f.getFacsForCat(cats.get(catName).toString());
       Vector facIds = new Vector();
       for(Enumeration E1= facs.keys();E1.hasMoreElements();){
         String facName = E1.nextElement().toString();
         facIds.addElement(facs.get(facName).toString());
       }
       Hashtable out = new Hashtable();
       out.put("CATALOG_ID",cats.get(catName).toString());
       out.put("CATALOG_DESC",catName);
       out.put("FAC_IDS",facIds);
       v.addElement(out);
     }
     return v;
   }

   //trong 4/4
   public String getFacAppDesc(String fac, String app) throws Exception {
    String query = "";
    query = "select application_desc application_display from fac_loc_app where facility_id = '"+ HelpObjs.singleQuoteToDouble(fac) +"'"+
            " and application = '"+ HelpObjs.singleQuoteToDouble(app) +"'";
    String result = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          result = BothHelpObjs.makeBlankFromNull(rs.getString("application_display"));
        }
     } catch (Exception e) {
     e.printStackTrace();
     throw e;
     } finally{
         dbrs.close();
     }
     return result;
   }


   //trong 10-7,1-29-01
   public Hashtable getEveryFacNew(String userId, String workAreaStatus) throws Exception{
    Hashtable myResult = new Hashtable();
    String query = "select * from fac_app_act_sys_view where personnel_id = '"+userId+"'";

    //trong 1-29-01
    if (workAreaStatus.equalsIgnoreCase("A")) {
      query += " and status = 'A'";         //only get active workArea
    }
    query += " order by facility_id,application_display";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector facID = new Vector();
    Vector facName = new Vector();
    Vector appID = new Vector();
    Vector appDesc = new Vector();
    Vector facXApp = new Vector();
    Hashtable prefActSys = new Hashtable();
    Hashtable actSys = new Hashtable();
    Hashtable innerH = new Hashtable();
    Vector actSysV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = null;
      String currentFac = "";
      boolean facilityChanged = false;
      Vector tmpAppId = new Vector();
      while (rs.next()){
        String facility = rs.getString("facility_id");
        if(BothHelpObjs.isBlankString(currentFac)) {
          currentFac = facility;
        }

        if (!currentFac.equals(facility)) {
          facilityChanged = true;
          currentFac = facility;
        }

        if (!facID.contains(facility)) {
          facID.addElement(facility);
          facName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          prefActSys.put(facility,BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
        }

        //String workArea = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (facilityChanged){
          innerH = new Hashtable();
          innerH.put("ACC_SYS_V",actSysV);
          actSys.put(lastFac,innerH);
          actSysV = new Vector();
          facilityChanged = false;
          tmpAppId = new Vector();
        }

        String workArea = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (!facilityChanged) {
          if (!tmpAppId.contains(workArea)) {
            tmpAppId.addElement(workArea);     //don't allow the same application for the same facility
            appID.addElement(workArea);
            appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            facXApp.addElement(facility);
          }
        }else {
          appID.addElement(workArea);
          appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
          facXApp.addElement(facility);
        }

        String actSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        if (!actSysV.contains(actSysId)) {
          actSysV.addElement(actSysId);
        }
        lastFac = facility;
      }
      //getting the last facility
      innerH = new Hashtable();
      innerH.put("ACC_SYS_V",actSysV);
      actSys.put(lastFac,innerH);

     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }

     myResult.put("FAC_ID",facID);
     myResult.put("FAC_NAME",facName);
     myResult.put("APP_ID",appID);
     myResult.put("APP_DESC",appDesc);
     myResult.put("PREF_ACC_SYS",prefActSys);
     myResult.put("FAC_X_APP",facXApp);
     myResult.put("ACC_SYS",actSys);
     return myResult;
   }

   public Hashtable getMyFacWorkAreaList(String userId, String workAreaStatus, String currentScreen) throws Exception{
     //System.out.println("-------- current screen: "+currentScreen);
     if ("NewChemTrack".equalsIgnoreCase(currentScreen) || "OrderTrack".equalsIgnoreCase(currentScreen) ||
         "ReportWin".equalsIgnoreCase(currentScreen) ) {
       return getMyFacWorkAreaListForTracking(userId,workAreaStatus,currentScreen);
     }else {
       return getMyFacWorkAreaListForCatalog(userId,workAreaStatus,currentScreen);
     }
   } //end of method

   public Hashtable getMyFacWorkAreaListForTracking(String userId, String workAreaStatus, String currentScreen) throws Exception{
     Hashtable myResult = new Hashtable();
     String query = "select a.facility_id,nvl(a.facility_name,a.facility_id) facility_name,a.application,"+
                    "a.application_desc display_application_name"+
                    " from my_workarea_facility_view a";
     String where = " where a.personnel_id = "+userId;
     if ("ReportWin".equalsIgnoreCase(currentScreen) ) {
       query += " ,user_group_member ug";
       where += " and a.personnel_id = ug.personnel_id "+
                " and a.facility_id = ug.facility_id "+
                " and ug.user_group_id = 'CreateReport'";
     }
     query += where + " order by a.facility_id,a.application_desc";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     Vector facID = new Vector();
     Vector facName = new Vector();
     Vector appID = new Vector();
     Vector appDesc = new Vector();
     Vector facXApp = new Vector();
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       String lastFac = null;
       String currentFac = "";
       boolean facilityChanged = false;
       Vector tmpAppId = new Vector();
       int count = 0;
       Vector tmpV = new Vector();
       while (rs.next()){
         String facility = rs.getString("facility_id");
         if(BothHelpObjs.isBlankString(currentFac)) {
           currentFac = facility;
         }

         if (!currentFac.equals(facility)) {
           facilityChanged = true;
           currentFac = facility;
         }

         if (!facID.contains(facility)) {
           facID.addElement(facility);
           facName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
         }

         if (facilityChanged){
           facilityChanged = false;
           tmpAppId = new Vector();
           count = 0;
         }

         String workArea = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
         if (!facilityChanged) {
           if (!tmpAppId.contains(workArea)) {
             tmpAppId.addElement(workArea);     //don't allow the same application for the same facility
             appID.addElement(workArea);
             appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("display_application_name")));
             facXApp.addElement(facility);
           }
           //add my work areas if there are more than one and it is order track or cat add track
           if (count > 1) {
             if (!tmpV.contains(facility)) {
               appID.insertElementAt("My Work Areas",appID.size()-count);
               appDesc.insertElementAt(" My Work Areas",appDesc.size()-count);
               facXApp.insertElementAt(facility,facXApp.size()-count);
               tmpV.addElement(facility);
             }
           }
           count++;
         }else {
           appID.addElement(workArea);
           appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("display_application_name")));
           facXApp.addElement(facility);
           count = 0;
         }
         lastFac = facility;
       }
      } catch (Exception e) {
       e.printStackTrace();
       throw e;
      } finally{
       dbrs.close();
      }

      //adding my facilities and my work areas
      if (facID.size() > 1) {
        facID.insertElementAt("My Facilities", 0);
        facName.insertElementAt(" My Facilities", 0);
        appID.insertElementAt("My Work Areas", 0);
        appDesc.insertElementAt("My Work Areas", 0);
        facXApp.insertElementAt("My Facilities", 0);
        Hashtable tmpH = new Hashtable(1);
        Vector tmpV = new Vector(1);
        tmpV.addElement("Account Sys");
        tmpH.put("ACC_SYS_V", tmpH);
      }
      myResult.put("FAC_ID",facID);
      myResult.put("FAC_NAME",facName);
      myResult.put("APP_ID",appID);
      myResult.put("APP_DESC",appDesc);
      myResult.put("PREF_ACC_SYS",new Hashtable(0));
      myResult.put("FAC_X_APP",facXApp);
      myResult.put("ACC_SYS",new Hashtable(0));
      /*
      long eTime = new java.util.Date().getTime();
      long min = (eTime-sTime);
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(new java.util.Date(min));
      System.out.println("\n\n---- facility catalog: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds - "+min);
      */
      return myResult;
   } //end of method

  public Hashtable getMyFacWorkAreaListForCatalog(String userId, String workAreaStatus, String currentScreen) throws Exception{
    //System.out.println("-------- current screen catalog: "+currentScreen);
    //long sTime = new java.util.Date().getTime();
    Hashtable myResult = new Hashtable();
    String query = "select a.* from fac_app_acc_sys_view a where a.personnel_id = "+userId;
    if (workAreaStatus.equalsIgnoreCase("A")) {
      query += " and a.status = 'A'";         //only get active workArea
    }

	  /*
	 //if seagate then figure out which set of account systems to show to user
    if ("Seagate".equalsIgnoreCase(db.getClient())) {
      String tmpSql = "select count(*) from cxml_document_data where lower(from_identity) like '%ers%'"+
                      " and payload_id = '"+payloadId+"'";
      try {
        if (HelpObjs.countQuery(db, tmpSql) > 0) {
          query += " and lower(account_sys_id) like '%ers'";
        }else {
          query += " and lower(account_sys_id) not like '%ers'";
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
    } //end of seagate logic
    */

    query += " and a.display_application = 'Y' order by a.facility_name,a.display_application_name,a.account_sys_id";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector facID = new Vector();
    Vector facName = new Vector();
    Vector appID = new Vector();
    Vector appDesc = new Vector();
    Vector facXApp = new Vector();
    Hashtable prefActSys = new Hashtable();
    Hashtable actSys = new Hashtable();
    Hashtable innerH = new Hashtable();
    Vector actSysV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = "";
      String currentFac = "";
      boolean facilityChanged = false;
      Vector tmpAppId = new Vector();
      while (rs.next()){
        String facility = rs.getString("facility_id");
        if(BothHelpObjs.isBlankString(currentFac)) {
          currentFac = facility;
        }

        if (!currentFac.equals(facility)) {
          facilityChanged = true;
          currentFac = facility;
        }

        if (!facID.contains(facility)) {
          facID.addElement(facility);
          facName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          prefActSys.put(facility,BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
        }

        if (facilityChanged){
          innerH = new Hashtable();
          innerH.put("ACC_SYS_V",actSysV);
          actSys.put(lastFac,innerH);
          actSysV = new Vector();
          facilityChanged = false;
          tmpAppId = new Vector();
        }

        String workArea = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (!facilityChanged) {
          if (!tmpAppId.contains(workArea)) {
            tmpAppId.addElement(workArea);     //don't allow the same application for the same facility
            appID.addElement(workArea);
            appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("display_application_name")));
            facXApp.addElement(facility);
          }
        }else {
          appID.addElement(workArea);
          appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("display_application_name")));
          facXApp.addElement(facility);
        }

        String actSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        if (!actSysV.contains(actSysId)) {
          actSysV.addElement(actSysId);
        }
        lastFac = facility;
      }
      //getting the last facility
      innerH = new Hashtable();
      innerH.put("ACC_SYS_V",actSysV);
      actSys.put(lastFac,innerH);

     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }

     myResult.put("FAC_ID",facID);
     myResult.put("FAC_NAME",facName);
     myResult.put("APP_ID",appID);
     myResult.put("APP_DESC",appDesc);
     myResult.put("PREF_ACC_SYS",prefActSys);
     myResult.put("FAC_X_APP",facXApp);
     myResult.put("ACC_SYS",actSys);
     /*
     long eTime = new java.util.Date().getTime();
     long min = (eTime-sTime);
     GregorianCalendar cal = new GregorianCalendar();
     cal.setTime(new java.util.Date(min));
     System.out.println("\n\n---- facility catalog: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds - "+min);
     */
     return myResult;
  } //end of method

  public Vector getMyWarehouseList(String userID) throws Exception {
    Vector result = new Vector();
    String query = "select preferred_warehouse from facility where facility_id in"+
                   " (select distinct facility_id from user_group_member_application"+
                   " where user_group_id in ('GenerateOrders','DisplayApplication') and personnel_id = "+userID+") order by preferred_warehouse";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Facility f = new Facility(db);
        f.setFacilityId(rs.getString("preferred_warehouse"));
        result.addElement(f);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    } finally{
      dbrs.close();
    }
    return result;
  } //enf of method

  public Hashtable getFacAppActSys(String userId, String workAreaStatus) throws Exception{
    Hashtable myResult = new Hashtable();
    String query = "select * from fac_app_act_sys_view where personnel_id = '"+userId+"'";

    //trong 1-29-01
    if (workAreaStatus.equalsIgnoreCase("A")) {
      query += " and status = 'A'";         //only get active workArea
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector facID = new Vector();
    Vector facName = new Vector();
    Hashtable prefActSys = new Hashtable();
    Hashtable innerH = new Hashtable();
    Hashtable actSysH = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = "";
      while (rs.next()){
        String facility = rs.getString("facility_id");
        if (!facID.contains(facility)) {
          facID.addElement(facility);
          facName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          prefActSys.put(facility,BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
        }

        if (lastFac.equals(facility)) {
          Object[] appActSys = (Object[])innerH.get(facility);
          Vector appID = (Vector)appActSys[0];
          Vector appDesc = (Vector)appActSys[1];
          Vector actSys = (Vector)actSysH.get(facility);
          String workArea = rs.getString("application");
          if (!appID.contains(workArea)) {
            appID.addElement(workArea);
            appDesc.addElement(rs.getString("application_display"));
          }
          String actSysID = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
          if (!actSys.contains(actSysID)) {
            actSys.addElement(actSysID);
          }
          appActSys[0] = appID;
          appActSys[1] = appDesc;
          innerH.put(facility,appActSys);
          actSysH.put(facility,actSys);
        }else {
          Object[] appActSys = new Object[2];
          Vector appID = new Vector();
          Vector appDesc = new Vector();
          String workArea = rs.getString("application");
          Vector actSys = new Vector();
          if (!appID.contains(workArea)) {
            appID.addElement(workArea);
            appDesc.addElement(rs.getString("application_display"));
          }
          String actSysID = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
          if (!actSys.contains(actSysID)) {
            actSys.addElement(actSysID);
          }
          appActSys[0] = appID;
          appActSys[1] = appDesc;
          innerH.put(facility,appActSys);
          actSysH.put(facility,actSys);
        }
        lastFac = facility;
      }
     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }

     myResult.put("FAC_ID",facID);
     myResult.put("FAC_NAME",facName);
     myResult.put("PREF_ACC_SYS",prefActSys);
     myResult.put("APP_INFO",innerH);
     myResult.put("ACT_SYS",actSysH);
     return myResult;
   }

   //4-24-01
   public Hashtable getEveryReportFac(String userId, String workAreaStatus) throws Exception{
    Hashtable myResult = new Hashtable();
    String query = "select * from fac_app_act_sys_report_view where personnel_id = '"+userId+"' and user_group_id = 'CreateReport'";

    //trong 1-29-01
    if (workAreaStatus.equalsIgnoreCase("A")) {
      query += " and status = 'A'";         //only get active workArea
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector facID = new Vector();
    Vector facName = new Vector();
    Vector appID = new Vector();
    Vector appDesc = new Vector();
    Vector facXApp = new Vector();
    Hashtable prefActSys = new Hashtable();
    Hashtable actSys = new Hashtable();
    Hashtable innerH = new Hashtable();
    Vector actSysV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = null;
      String currentFac = "";
      boolean facilityChanged = false;
      Vector tmpAppId = new Vector();
      while (rs.next()){
        String facility = rs.getString("facility_id");
        if(BothHelpObjs.isBlankString(currentFac)) {
          currentFac = facility;
        }

        if (!currentFac.equals(facility)) {
          facilityChanged = true;
          currentFac = facility;
        }

        if (facilityChanged){
          innerH = new Hashtable();
          innerH.put("ACC_SYS_V",actSysV);
          actSys.put(lastFac,innerH);
          actSysV = new Vector();
          facilityChanged = false;
          tmpAppId = new Vector();
        }

        if (!facID.contains(facility)) {
          facID.addElement(facility);
          facName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          prefActSys.put(facility,BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
        }

        String workArea = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (!facilityChanged) {
          if (!tmpAppId.contains(workArea)) {
            tmpAppId.addElement(workArea);     //don't allow the same application for the same facility
            appID.addElement(workArea);
            appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
            facXApp.addElement(facility);
          }
        }else {
          appID.addElement(workArea);
          appDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
          facXApp.addElement(facility);
        }

        String actSysId = BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id"));
        if (!actSysV.contains(actSysId)) {
          actSysV.addElement(actSysId);
        }
        lastFac = facility;
      }
      //getting the last facility
      innerH = new Hashtable();
      innerH.put("ACC_SYS_V",actSysV);
      actSys.put(lastFac,innerH);

     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }

     myResult.put("FAC_ID",facID);
     myResult.put("FAC_NAME",facName);
     myResult.put("APP_ID",appID);
     myResult.put("APP_DESC",appDesc);
     myResult.put("PREF_ACC_SYS",prefActSys);
     myResult.put("FAC_X_APP",facXApp);
     myResult.put("ACC_SYS",actSys);
     //System.out.println("\n\n--------- facility getEveryReportFac: "+facID+"\n"+facName+"\n"+appID+"\n"+appDesc+"\n"+facXApp+"\n"+actSys);
     return myResult;

   }

    //Nawaz Changes 07-11-01. Method to calculte the number of cataloges for a client.
    public Hashtable getCataloguCount()  throws Exception {

      String query = "select MSDS_VIEWER_SEQ.nextval  , Test_Count from (select count(distinct catalog_id)  Test_Count from catalog_facility)";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable Test_Count = new Hashtable();
      try {
           dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
           while (rs.next())
           {
             Test_Count.put("COUNT",(rs.getString("Test_Count")==null?"":rs.getString("Test_Count")));
             Test_Count.put("SESSION_IDN",(rs.getString("NEXTVAL").toString()));
           }
      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }

      return Test_Count;
   }
} //END OF FACILITY
