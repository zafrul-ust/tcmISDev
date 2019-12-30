package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.sql.*;

/*
SQLWKS> describe vendor_waste_profile
Column Name                    Null?    Type
------------------------------ -------- ----
VENDOR_ID                      NOT NULL VARCHAR2(20)
VENDOR_PROFILE_ID              NOT NULL VARCHAR2(60)
DESCRIPTION                             VARCHAR2(200)
MANAGEMENT_OPTION                       VARCHAR2(3)
URL                                     VARCHAR2(200)
MANAGEMENT_OPTION_LOCATION              VARCHAR2(100)
EPA_HAZARD_CLASS                        VARCHAR2(1)
LAND_BAN_REQUIRED                       VARCHAR2(1)
RCRA_CLASSIFICATION                     VARCHAR2(20)
*/

public class WasteVendorProfile {
  protected TcmISDBModel db;
  protected String vendorId;
  protected String vendorProfileId;
  protected String description;
  protected String wasteCategoryId;
  protected String wasteTypeId;
  protected String managementOption;
  protected String managementOptionLocation;
  protected String url;
  protected String landBanRequire;
  protected String rcraClassification;
  protected String packingGroup;
  protected String hazardClass;
  protected String properShippingName;
  protected String shippingId;
  protected String transporter1Id;   // '1' it is a one
  protected String transporter2Id;
  protected String transporter3Id;



  public WasteVendorProfile(TcmISDBModel db){
      this.db = db;
   }

  public WasteVendorProfile(){
  }

  //set methods
  public void setVendorId(String s){vendorId = s;}
  public void setVendorProfileId(String s){vendorProfileId = s;}
  public void setDescription(String s){description = s;}
  public void setWasteCategoryId(String s){wasteCategoryId = s;}
  public void setWasteTypeId(String s){wasteTypeId = s;}
  public void setUrl(String s){url = s;}
  public void setManagementOption(String s){managementOption = s;}
  public void setManagementOptionLocation(String s){managementOptionLocation = s;}
  public void setHazardClass(String s){hazardClass = s;}
  public void setLandBanRequire(String s){landBanRequire = s;}
  public void setRcraClassification(String s){rcraClassification = s;}
  public void setPackingGroup(String s){packingGroup = s;}
  public void setProperShippingName(String s){properShippingName = s;}
  public void setShippingId(String s){shippingId = s;}
  public void setTransporter1Id(String s){transporter1Id = s;}
  public void setTransporter2Id(String s){transporter2Id = s;}
  public void setTransporter3Id(String s){transporter3Id = s;}

  //get methods
  public String getVendorId(){return vendorId;}
  public String getVendorProfileId(){return vendorProfileId;}
  public String getDescription(){return description;}
  public String getUrl(){return url;}
  public String getManagementOption(){return managementOption;}
  public String getManagementOptionLocation(){return managementOptionLocation;}
  public String getHazardClass(){return hazardClass;}
  public String getLandBanRequire(){return landBanRequire;}
  public String getRcraClassification(){return rcraClassification;}
  public String getWasteCategoryId(){return wasteCategoryId;}
  public String getWasteTypeId(){return wasteTypeId;}
  public String getPackingGroup(){return packingGroup;}
  public String getProperShippingName(){return properShippingName;}
  public String getShippingId(){return shippingId;}
  public String getTransporter1Id(){return transporter1Id;}
  public String getTransporter2Id(){return transporter2Id;}
  public String getTransporter3Id(){return transporter3Id;}

  public void load()throws Exception{
     String query = "select * from vendor_waste_profile where vendor_id = '"+getVendorId()+"'";
     query += " and vendor_profile_id = '"+getVendorProfileId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setDescription(BothHelpObjs.makeBlankFromNull(rs.getString("description")));
         setUrl(BothHelpObjs.makeBlankFromNull(rs.getString("url")));
         setManagementOption(BothHelpObjs.makeBlankFromNull(rs.getString("management_option")));
         setManagementOptionLocation(BothHelpObjs.makeBlankFromNull(rs.getString("management_option_location")));
         setLandBanRequire(BothHelpObjs.makeBlankFromNull(rs.getString("land_ban_required")));
         setRcraClassification (BothHelpObjs.makeBlankFromNull(rs.getString("rcra_classification")));
         setHazardClass(BothHelpObjs.makeBlankFromNull(rs.getString("hazard_class")));
         setWasteCategoryId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id")));
         setWasteTypeId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id")));
         setPackingGroup(BothHelpObjs.makeBlankFromNull(rs.getString("packing_group")));
         setProperShippingName(BothHelpObjs.makeBlankFromNull(rs.getString("proper_shipping_name")));
         setShippingId(BothHelpObjs.makeBlankFromNull(rs.getString("shipping_id")));
         setTransporter1Id(BothHelpObjs.makeBlankFromNull(rs.getString("transporter_1_id")));
         setTransporter2Id(BothHelpObjs.makeBlankFromNull(rs.getString("transporter_2_id")));
         setTransporter3Id(BothHelpObjs.makeBlankFromNull(rs.getString("transporter_3_id")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

}