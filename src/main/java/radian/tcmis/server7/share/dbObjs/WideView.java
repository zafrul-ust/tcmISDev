package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class WideView{
   protected TcmISDBModel db;
   protected String waste_item_id = "";
   protected String act = "";
   protected String vendor_id = "";
   protected String company_name = "";
   protected String country = "";
   protected String address1 = "";
   protected String address2 = "";
   protected String address3 = "";
   protected String city = "";
   protected String state = "";
   protected String zip = "";
   protected String contact_name = "";
   protected String phone = "";
   protected String email = "";
   protected String fax = "";
   protected String state_id = "";
   protected String epa_id = "";
   protected String title = "";
   protected String cas_no = "";
   protected String pack_type = "";
   protected String stw_code = "";
   protected String stw_state = "";
   protected String epa_code = "";
   protected String prop_ship_name = "";
   protected String ship_haz_class = "";
   protected String ship_id_num = "";
   protected String packing_group = "";
   protected String url = "";
   protected String man_option = "";
   protected String man_location = "";
   protected String MGMT_OP_ADDRESS_1 = "";
   protected String MGMT_OP_ADDRESS_2 = "";
   protected String MGMT_OP_ADDRESS_3 = "";
   protected String MGMT_OP_CITY = "";
   protected String MGMT_OP_STATE = "";
   protected String MGMT_OP_POSTAL_CODE = "";
   protected String MGMT_OP_COUNTRY = "";
   protected String vendor_profile_id = ""; //nawaz
   protected String description = "";
   protected String man_option_desc = "";
   protected String man_option_comname = "";
   protected String Man_Op_Contact_Name = "";
   protected String Man_Op_Phone = "";
   protected String Man_Op_Email = "";
   protected String Man_Op_Fax = "";
   protected String EPA_ID = "";
   protected String MGMT_OP_EPA_ID = "";
   protected String TRANS_2_EPA_ID = "";

   protected int count = 0;

//Get methods
public String getURL(){return url;}
public String getTitle(){return title;}
public String getStateWasteCode(){return stw_code;}
public String getStateWasteState(){return stw_state;}
public String getEpaWasteCode(){return epa_code;}
public String getState(){return state;}
public String getVendorId(){return vendor_id;}
public String getCompanyName(){return company_name;}
public String getCountry(){return country;}
public String getAddress1(){return address1;}
public String getAddress2(){return address2;}
public String getAddress3(){return address3;}
public String getCity(){return city;}
public String getZip(){return zip;}
public String getContactName(){return contact_name;}
public String getPhone(){return phone;}
public String getEmail(){return email;}
public String getFax(){return fax;}
public String getStateId(){return state_id;}
public String getEpaId(){return epa_id;}
public String getProperShippingName(){return prop_ship_name;}
public String getShippingHazardClass(){return ship_haz_class;}
public String getShippingIdNumber(){return ship_id_num;}
public String getPackingGroup(){return packing_group;}
public String getManagementOption(){return man_option;}
public String getManagementOptionLocation(){return man_location;}
//Nawaz

public String getvendor_profile_id(){return vendor_profile_id;}
public String getdescription(){return description;}
public String getman_option_desc(){return man_option_desc;}
public String getman_option_comname(){ return man_option_comname;}
public String getman_op_Address1(){ return MGMT_OP_ADDRESS_1;}
public String getman_op_Address2(){ return MGMT_OP_ADDRESS_2;}
public String getman_op_Address3(){ return MGMT_OP_ADDRESS_3;}
public String getman_op_City(){ return MGMT_OP_CITY;}
public String getman_op_State(){ return MGMT_OP_STATE;}
public String getman_op_Zip(){ return MGMT_OP_POSTAL_CODE;}
public String getman_op_Country(){ return MGMT_OP_COUNTRY;}
public String getMan_Op_Contact_Name(){ return Man_Op_Contact_Name;}
public String getMan_Op_Phone(){ return Man_Op_Phone;}
public String getMan_Op_Email(){ return  Man_Op_Email;}
public String getMan_Op_Fax(){ return Man_Op_Fax;}
public String getEPA_ID(){ return EPA_ID;}
public String getMGMT_OP_EPA_ID(){ return MGMT_OP_EPA_ID;}
public String getTRANS_2_EPA_ID(){ return TRANS_2_EPA_ID;}

//Set Methods
public void setvendor_profile_id(String x){vendor_profile_id = x;}      //by Nawaz
public void setdescription(String x){description = x;}
public void setman_option_desc(String x){man_option_desc = x;}
public void setman_option_comname(String x){man_option_comname = x;}
public void setman_op_Address1(String x){MGMT_OP_ADDRESS_1= x;}
public void setman_op_Address2(String x){MGMT_OP_ADDRESS_2= x;}
public void setman_op_Address3(String x){MGMT_OP_ADDRESS_3= x;}
public void setman_op_City(String x){MGMT_OP_CITY= x;}
public void setman_op_State(String x){MGMT_OP_STATE= x;}
public void setman_op_Zip(String x){MGMT_OP_POSTAL_CODE= x;}
public void setman_op_Country(String x){MGMT_OP_COUNTRY= x;}
public void setMan_Op_Contact_Name(String x){Man_Op_Contact_Name= x;}
public void setMan_Op_Phone(String x){Man_Op_Phone= x;}
public void setMan_Op_Email(String x){Man_Op_Email= x;}
public void setMan_Op_Fax(String x){Man_Op_Fax= x;}
public void setEPA_ID(String x){EPA_ID= x;}
public void setMGMT_OP_EPA_ID(String x){MGMT_OP_EPA_ID= x;}
public void setTRANS_2_EPA_ID(String x){TRANS_2_EPA_ID= x;}

public void setURL(String x){url = x;}
public void setTitle(String x){title = x;}

public void setStateWasteCode(String x){stw_code += x;}
public void setStateWasteState(String x){stw_state += x;}
public void setEpaWasteCode(String x){epa_code += x + ", ";}
public void setState(String x){state = x;}
public void setVendorId(String x){vendor_id = x;}
public void setCompanyName(String x){company_name = x;}
public void setCountry(String x){country = x;}
public void setAddress1(String x){address1 = x;}
public void setAddress2(String x){address2 = x;}
public void setAddress3(String x){address3 = x;}
public void setCity(String x){city = x;}
public void setZip(String x){zip = x;}
public void setContactName(String x){contact_name = x;}
public void setPhone(String x){phone = x;}
public void setEmail(String x){if (x!=null)email = "<a href=\"mailto:" + x + "\">" + x + "</a>";}
public void setFax(String x){fax = x;}
public void setStateId(String x){state_id = x;}
public void setEpaId(String x){epa_id = x;}
public void setProperShippingName(String x){
if (x==null)x="<font color=red size=2>Not Available</font>";
prop_ship_name = x + ", ";}
public void setShippingHazardClass(String x){
if (x==null)x="<font color=red size=2>Not Available</font>";
ship_haz_class = x + ", ";}
public void setShippingIdNumber(String x){
if (x==null)x="<font color=red size=2>Not Available</font>";
ship_id_num = x + ", ";}
public void setPackingGroup(String x){
if (x==null)x="<font color=red size=2>Not Available</font>";
packing_group = x;}
public void setManagementOption(String x){if (x==null)x="Not Listed"; man_option = x;}
public void setManagementOptionLocation(String x){if (x==null)x="Not Listed"; man_location = x;}

   protected String doDateFormat(String s){
     if(BothHelpObjs.isBlankString(s)){
       return "";
     }
     return BothHelpObjs.formatDate("toCharfromDB",s);
  }

//Set methods

 //Constructor
   public WideView(TcmISDBModel db){
      this.db = db;
   }
   public WideView(){
   }
   public void setDbModel(TcmISDBModel db){
     this.db = db;
   }

//Query the database and return values
  public static Vector getContentVector(TcmISDBModel db,String waste_item_id,String act,String act2)throws Exception
  {
  //   System.out.println("\n\n============= wideview check");
     Vector v = new Vector();

     String query = "";
     String query1 = "";
     String query2 = "";
     String query3 = "";
     String query4 = "";
 try{
     if (act2.equalsIgnoreCase("codes"))
     {
       query = "select a.*, b.* from wst_epa_waste_code_view a, wst_state_waste_code_view b where a.waste_item_id=b.waste_item_id and a.waste_item_id='" + waste_item_id + "'";
     //=================================================//
     }
    if (act.equalsIgnoreCase("profile"))
    {
    //=================================================//
       query = "select url from waste_profile_url_view where waste_item_id ='" + waste_item_id + "' ";

//=================================================//

     }else if (act.equalsIgnoreCase("title")){
       query = "select vendor_profile_id,waste_item_id,description,company_name from waste_tracking_info_view where waste_item_id='" + waste_item_id + "'";
//=================================================//
     }else if (act.equalsIgnoreCase("prop")){

     query = "Select WASTE_ITEM_ID,PARAMETER,VALUE,UNIT from waste_characterization_view where waste_item_id='" + waste_item_id + "'";

     query1 = "select WASTE_ITEM_ID,SHIPPING from waste_shipping_view where waste_item_id='" + waste_item_id + "'";

     query2 = "select WASTE_ITEM_ID,CODES from waste_epa_code_view where waste_item_id='" + waste_item_id + "'";

     query3 = "select WASTE_ITEM_ID,CODES from waste_state_code_view where waste_item_id='" + waste_item_id + "'";

     query4 = "select WASTE_ITEM_ID,CONSTITUENT,VALUE from waste_constituent_view where waste_item_id=" + waste_item_id + "";

     //  Changed Query on 10/10/00 by Nawaz
     //  query = "select * from waste_item wi, vendor_waste_profile vwp where waste_item_id='" + waste_item_id + "'";
     //  query += " and wi.vendor_id = vwp.vendor_id and wi.vendor_profile_id = vwp.vendor_profile_id";

//=================================================//

     }else if (act.equalsIgnoreCase("disp")){
     query = "select contact_name,phone, email, fax, company_name, address_1, address_2, address_3," +
           " city, state,postal_code, country, vendor_id, state_id, epa_id, management_option," +
           " management_option_location, mgmt_op_company_name,management_option_desc,"+
           " MGMT_OP_ADDRESS_1,MGMT_OP_ADDRESS_2,MGMT_OP_ADDRESS_3,"+
           " MGMT_OP_CITY,MGMT_OP_POSTAL_CODE,MGMT_OP_STATE,MGMT_OP_COUNTRY,"+
           " MGMT_OP_CONTACT_NAME, MGMT_OP_PHONE, MGMT_OP_EMAIL,MGMT_OP_FAX,EPA_ID,MGMT_OP_EPA_ID,TRANS_2_EPA_ID from " +
           "waste_vendor_with_option_view where waste_item_id='" + waste_item_id + "'";
     }
//=================================================//
     }catch(Exception ex){ex.printStackTrace();}

     DBResultSet dbrs = null;
     DBResultSet dbrs1 = null;
     DBResultSet dbrs2 = null;
     DBResultSet dbrs3 = null;
     DBResultSet dbrs4 = null;

     ResultSet rs = null;
     ResultSet rs1 = null;
     ResultSet rs2 = null;
     ResultSet rs3 = null;
     ResultSet rs4 = null;

     if (act.equalsIgnoreCase("prop"))
     {
      try
      {

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector v1 = new Vector();

       while (rs.next())
       {
        Hashtable tmp12 = new Hashtable();
        tmp12.put("PARAMETER",rs.getString("PARAMETER")==null?"&nbsp;":rs.getString("PARAMETER"));
        tmp12.put("UNIT",rs.getString("UNIT")==null?"&nbsp;":rs.getString("UNIT"));
        tmp12.put("VALUE",rs.getString("VALUE")==null?"&nbsp;":rs.getString("VALUE"));
        v1.addElement(tmp12);
       }
       v.addElement(v1);
       dbrs.close();

       dbrs1 = db.doQuery(query1);
       rs1=dbrs1.getResultSet();
       Vector v2 = new Vector();
       while (rs1.next())
       {
        Hashtable tmp1 = new Hashtable();
        tmp1.put("SHIPPING",rs1.getString("SHIPPING")==null?"":rs1.getString("SHIPPING"));
        v2.addElement(tmp1);
       }
       v.addElement(v2);
       dbrs1.close();


       dbrs2 = db.doQuery(query2);
       rs2=dbrs2.getResultSet();
       Vector v3 = new Vector();
       while (rs2.next())
       {
       Hashtable tmp1 = new Hashtable();
       tmp1.put("WASTE_EPA_CODE",rs2.getString("CODES")==null?"":rs2.getString("CODES"));
       v3.addElement(tmp1);
       }
       v.addElement(v3);
       dbrs2.close();

       dbrs3 = db.doQuery(query3);
       rs3=dbrs3.getResultSet();
       Vector v4 = new Vector();
       while (rs3.next())
       {
       Hashtable tmp1 = new Hashtable();
       tmp1.put("WASTE_STATE_CODE",rs3.getString("CODES")==null?"":rs3.getString("CODES"));
       v4.addElement(tmp1);
       }
       v.addElement(v4);
       dbrs3.close();


       dbrs4 = db.doQuery(query4);
       rs4=dbrs4.getResultSet();
       Vector v5 = new Vector();
       while (rs4.next())
       {
       Hashtable tmp1 = new Hashtable();
       tmp1.put("CONSTITUENT",rs4.getString("CONSTITUENT")==null?"":rs4.getString("CONSTITUENT"));
       tmp1.put("VALUE",rs4.getString("VALUE")==null?"":rs4.getString("VALUE"));
       v5.addElement(tmp1);
       }
       v.addElement(v5);
       dbrs4.close();

     }
     catch (Exception e)
     {
      e.printStackTrace();
      throw e;
     }
     finally
     {

     }
    }
    else
    {
     try
     {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
      WideView sv = new WideView(db);
       while (rs.next()){
         if (act.equalsIgnoreCase("title")){
          sv.setTitle(rs.getString("waste_item_id"));
          sv.setCompanyName(rs.getString("company_name"));
          sv.setvendor_profile_id(rs.getString("vendor_profile_id"));
          sv.setdescription(rs.getString("description"));

         }else if (act.equalsIgnoreCase("profile")){
           sv.setURL(rs.getString("url"));
         }else if (act.equalsIgnoreCase("disp"))
         {
           sv.setContactName(rs.getString("contact_name"));
           sv.setPhone(rs.getString("phone"));
           sv.setFax(rs.getString("fax"));
           sv.setEmail(rs.getString("email"));
           sv.setCompanyName(rs.getString("company_name"));
           sv.setAddress1(rs.getString("address_1"));
           sv.setAddress2(rs.getString("address_2"));
           sv.setAddress3(rs.getString("address_3"));
           sv.setCity(rs.getString("city"));
           sv.setState(rs.getString("state"));
           sv.setZip(rs.getString("postal_code"));
           sv.setCountry(rs.getString("country"));
           sv.setVendorId(rs.getString("vendor_id"));
           sv.setStateId(rs.getString("state_id"));
           sv.setEpaId(rs.getString("epa_id"));
           sv.setManagementOption(rs.getString("management_option")==null?"":rs.getString("management_option"));
           sv.setManagementOptionLocation(rs.getString("management_option_location")==null?"":rs.getString("management_option_location"));
           sv.setman_option_desc(rs.getString("management_option_desc")==null?"":rs.getString("management_option_desc"));
           sv.setman_option_comname(rs.getString("mgmt_op_company_name")==null?"":rs.getString("mgmt_op_company_name"));
           sv.setman_op_Address1(rs.getString("MGMT_OP_ADDRESS_1")==null?"":rs.getString("MGMT_OP_ADDRESS_1"));
           sv.setman_op_Address2(rs.getString("MGMT_OP_ADDRESS_2")==null?"":rs.getString("MGMT_OP_ADDRESS_2"));
           sv.setman_op_Address3(rs.getString("MGMT_OP_ADDRESS_3")==null?"":rs.getString("MGMT_OP_ADDRESS_3"));
           sv.setman_op_City(rs.getString("MGMT_OP_CITY")==null?"":rs.getString("MGMT_OP_CITY"));
           sv.setman_op_State(rs.getString("MGMT_OP_STATE")==null?"":rs.getString("MGMT_OP_STATE"));
           sv.setman_op_Zip(rs.getString("MGMT_OP_POSTAL_CODE")==null?"":rs.getString("MGMT_OP_POSTAL_CODE"));
           sv.setman_op_Country(rs.getString("MGMT_OP_COUNTRY")==null?"":rs.getString("MGMT_OP_COUNTRY"));
           sv.setMan_Op_Contact_Name(rs.getString("MGMT_OP_CONTACT_NAME")==null?"":rs.getString("MGMT_OP_CONTACT_NAME"));
           sv.setMan_Op_Phone(rs.getString("MGMT_OP_PHONE")==null?"":rs.getString("MGMT_OP_PHONE"));
           sv.setMan_Op_Fax(rs.getString("MGMT_OP_FAX")==null?"":rs.getString("MGMT_OP_FAX"));
           sv.setMan_Op_Email(rs.getString("MGMT_OP_EMAIL")==null?"":rs.getString("MGMT_OP_EMAIL"));
           sv.setEpaId(rs.getString("EPA_ID")==null?"":rs.getString("EPA_ID"));
           sv.setMGMT_OP_EPA_ID(rs.getString("MGMT_OP_EPA_ID")==null?"":rs.getString("MGMT_OP_EPA_ID"));
           sv.setTRANS_2_EPA_ID(rs.getString("TRANS_2_EPA_ID")==null?"":rs.getString("TRANS_2_EPA_ID"));
         }
         v.addElement(sv);
       }
      }
     catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object(WideView): " + e.getMessage(),null);
       throw e;
     }finally
     {
       dbrs.close();

    }

 }
 return v;
}


// Additions By Nawaz on 11/1/00
// This method is addded to get the reports listed in the database.
//Changes on 04/04/01

   public static Vector getReport(TcmISDBModel db, String Logon_Id)
        throws Exception
    {
        Vector v = new Vector();
        String query = "";
        query = "Select PERSONNEL_ID, to_char(REPORT_DATE,'mm/dd/yyyy') REPORT_DATE, REPORT_NAME, CONTENT, STATUS from batch_report_view where Logon_Id ='"+Logon_Id+"' order by REPORT_DATE desc";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            Hashtable tmp;
            for(rs = dbrs.getResultSet(); rs.next(); v.addElement(tmp))
            {
                tmp = new Hashtable();
                tmp.put("PERSONNEL_ID", rs.getString("PERSONNEL_ID") != null ? ((Object) (rs.getString("PERSONNEL_ID"))) : "&nbsp;");
                tmp.put("REPORT_DATE", rs.getString("REPORT_DATE") != null ? ((Object) (rs.getString("REPORT_DATE"))) : "&nbsp;");
                tmp.put("REPORT_NAME", rs.getString("REPORT_NAME") != null ? ((Object) (rs.getString("REPORT_NAME"))) : "&nbsp;");
                tmp.put("CONTENT", rs.getString("CONTENT") != null ? ((Object) (rs.getString("CONTENT"))) : "&nbsp;");
                tmp.put("STATUS", rs.getString("STATUS") != null ? ((Object) (rs.getString("STATUS"))) : "&nbsp");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return v;
    }

    public void delReport(TcmISDBModel db, String user_id, String content)
        throws Exception
    {
        String query = "update batch_report set STATUS='delete' where PERSONNEL_ID ='"+user_id+"' and CONTENT ='"+content+"'";
        try
        {
            db.doUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for Update !!!");
            throw e;
        }
    }
}



