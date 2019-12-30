/*SQLWKS> describe waste_vendor
Column Name                    Null?    Type
------------------------------ -------- ----
VENDOR_ID                      NOT NULL VARCHAR2(20)
COMPANY_NAME                   NOT NULL VARCHAR2(30)
ADDRESS_1                               VARCHAR2(30)
ADDRESS_2                               VARCHAR2(30)
ADDRESS_3                               VARCHAR2(30)
CITY                                    VARCHAR2(30)
STATE                                   VARCHAR2(2)
COUNTRY                                 VARCHAR2(30)
POSTAL_CODE                             VARCHAR2(20)
CONTACT_NAME                            VARCHAR2(60)
PHONE                                   VARCHAR2(20)
EMAIL                                   VARCHAR2(60)
FAX                                     VARCHAR2(20)
*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class WasteVendor {

   protected TcmISDBModel db;
   protected String vendorId;
   protected String companyName;
   protected String address_1;
   protected String address_2;
   protected String address_3;
   protected String city;
   protected String state;
   protected String country;
   protected String postalCode;
   protected String contactName;
   protected String phone;
   protected String email;
   protected String fax;

   public WasteVendor(TcmISDBModel db){
      this.db = db;
   }
   public WasteVendor(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   //set methods
   public void setVendorId(String s){vendorId = s;}
   public void setCompanyName(String s){companyName = s;}
   public void setAddress_1(String s){address_1 = s;}
   public void setAddress_2(String s){address_2 = s;}
   public void setAddress_3(String s){address_3 = s;}
   public void setCity(String s){city = s;}
   public void setState(String s){state = s;}
   public void setCountry(String s){country = s;}
   public void setPostalCode(String s){postalCode = s;}
   public void setContactName(String s){contactName = s;}
   public void setPhone(String s){phone = s;}
   public void setEmail(String s){email = s;}
   public void setFax(String s){fax = s;}

   //get methods
   public String getVendorId(){return vendorId;}
   public String getCompanyName(){return companyName;}
   public String getAddress_1(){return address_1;}
   public String getAddress_2(){return address_2;}
   public String getAddress_3(){return address_3;}
   public String getCity(){return city;}
   public String getState(){return state;}
   public String getCountry(){return country;}
   public String getPostalCode(){return postalCode;}
   public String getContactName(){return contactName;}
   public String getPhone(){return phone;}
   public String getEmail(){return email;}
   public String getFax(){return fax;}

   public void load()throws Exception{
     String query = "select * from waste_directory where epa_id = '"+getVendorId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setCompanyName(BothHelpObjs.makeBlankFromNull(rs.getString("company_name")));
         setAddress_1(BothHelpObjs.makeBlankFromNull(rs.getString("address_1")));
         setAddress_2(BothHelpObjs.makeBlankFromNull(rs.getString("address_2")));
         setAddress_3(BothHelpObjs.makeBlankFromNull(rs.getString("address_3")));
         setCity(BothHelpObjs.makeBlankFromNull(rs.getString("city")));
         setState(BothHelpObjs.makeBlankFromNull(rs.getString("state")));
         setCountry(BothHelpObjs.makeBlankFromNull(rs.getString("country")));
         setPostalCode(BothHelpObjs.makeBlankFromNull(rs.getString("postal_code")));
         setContactName(BothHelpObjs.makeBlankFromNull(rs.getString("contact_name")));
         setPhone(BothHelpObjs.makeBlankFromNull(rs.getString("phone")));
         setEmail(BothHelpObjs.makeBlankFromNull(rs.getString("email")));
         setFax(BothHelpObjs.makeBlankFromNull(rs.getString("fax")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static Hashtable getAllVendor(TcmISDBModel db) throws Exception {
    String query = "select * from waste_vendor_view";
    Vector companyName = new Vector();
    Vector vendorId = new Vector();
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        companyName.addElement(rs.getString("company_name"));
        vendorId.addElement(rs.getString("vendor_id"));
      }
    }catch (Exception e) { e.printStackTrace(); throw e;
    }finally{dbrs.close();}

    result.put("COMPANY_NAME",companyName);
    result.put("VENDOR_ID",vendorId);
    return result;
   }

}
