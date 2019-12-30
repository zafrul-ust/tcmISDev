
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


import radian.tcmis.server7.share.db.*;

import java.applet.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.rmi.server.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.lang.*;
import java.net.URLEncoder;
import oracle.jdbc.OracleTypes;

public class NChem  {

   protected TcmISDBModel db;


   public NChem() throws java.rmi.RemoteException {

   }

   public NChem(TcmISDBModel db) throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getMaterialForItem(String itemID) {
    Hashtable result = new Hashtable();
    Connection connect1 = null;
    CallableStatement cs = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector matID = new Vector(10);
    Vector matDesc = new Vector(10);
    Vector mfg = new Vector(10);
    Vector msdsNo = new Vector(10);
    Vector mfgPartNo = new Vector(10);
    Vector tradeName = new Vector(10);
    Vector compPerItem = new Vector(10);
    Vector grade = new Vector(10);
    Vector partSize = new Vector(10);
    Vector sizeUnit = new Vector(10);
    Vector pkgStyle = new Vector(10);
    Vector dimension = new Vector(10);
    Vector netWt = new Vector(10);
    Vector netWtUnit = new Vector(10);
    try {
      String query = "";
      try {
        connect1 = db.getConnection();
        cs = connect1.prepareCall("{call PR_CATALOG_ITEM_MSDS(?,?)}");
        cs.setString(1, itemID);
        cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        query = (String) cs.getObject(2);
      }catch(SQLException ee){
        ee.printStackTrace();
      } finally{
        cs.close();
      }
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        matID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("material_id")));
        matDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
        mfg.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc")));
        tradeName.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        mfgPartNo.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no")));
        msdsNo.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("fac_msds_id")));
        compPerItem.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("components_per_item")));
        grade.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("grade")));
        partSize.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_size")));
        sizeUnit.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("size_unit")));
        pkgStyle.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("pkg_style")));
        dimension.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("dimension")));
        netWt.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("net_wt")));
        netWtUnit.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("net_wt_unit")));
      }
    } catch (Exception ee) {
      ee.printStackTrace();
    } finally{
      dbrs.close();
    }
    result.put("MATERIAL_ID",matID);
    result.put("MATERIAL_DESC",matDesc);
    result.put("MFG_DESC",mfg);
    result.put("TRADE_NAME",tradeName);
    result.put("MFG_PART_NO",mfgPartNo);
    result.put("MSDS_NO",msdsNo);
    result.put("COMP_PER_ITEM",compPerItem);
    result.put("GRADE",grade);
    result.put("PART_SIZE",partSize);
    result.put("SIZE_UNIT",sizeUnit);
    result.put("PKG_STYLE",pkgStyle);
    result.put("DIMENSION",dimension);
    result.put("NET_WT",netWt);
    result.put("NET_WT_UNIT",netWtUnit);
    return result;
   }

   /*
   public Hashtable getScreenData(String req_id, String user_id) throws Exception {
     //String facility = (String) inData.get("FACILITY");
     Hashtable result = new Hashtable();

     CatalogAddRequestNew car = new CatalogAddRequestNew(db);
     car.setRequestId(BothHelpObjs.makeZeroFromNull(req_id));
     if (car.getCount()<=0){
        result.put("MESSAGE","Error. No record found for reqId "+req_id);
        return result;
     }
     car.load();

     result.put("STATIC_DATA",getAllStaticFields());

     try {
       Integer status = car.getRequestStatus();
       result.put("DYNAMIC_DATA",getDynamicData(Integer.parseInt(req_id),user_id));

     } catch (Exception e){
       e.printStackTrace();
       result.put("MESSAGE","Error:"+e.getMessage());
       return result;
     }

     return result;
   }
   */

   public Hashtable getScreenData(String req_id, String user_id) throws Exception {
     Hashtable result = new Hashtable();
     if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new where request_id = "+req_id) <=0){
        result.put("MESSAGE","Error. No record found for reqId "+req_id);
        return result;
     }

     try {
       Hashtable tmpH = getAllStaticFields(req_id);
       if (HelpObjs.countQuery(db,"select count(*) from facility a,catalog_add_request_new b where a.facility_id = b.eng_eval_facility_id"+
                                  " and a.cat_add_approval_detail_needed = 'Y' and b.request_id = "+req_id) > 0) {
         tmpH.put("APPROVAL_DETAIL_REQUIRED",new Boolean(true));
       }else {
         tmpH.put("APPROVAL_DETAIL_REQUIRED",new Boolean(false));
       }

       result.put("STATIC_DATA",tmpH);
       result.put("DYNAMIC_DATA",getDynamicData(Integer.parseInt(req_id),user_id));
     } catch (Exception e){
       e.printStackTrace();
       result.put("MESSAGE","Error:"+e.getMessage());
       return result;
     }

     return result;
   }


   protected Hashtable getAllStaticFields(String reqID) throws Exception {
     Hashtable res = new Hashtable();
     /**
       For Kists screen, we have units and pack style
     */
     res.put("PACKAGES",VVs.getVVPkgStyle(db));
     res.put("PERIOD",VVs.getVVPeriodUnit(db));
     res.put("NETWT",VVs.getVVNetWt(db));
     res.put("TEMPERATURES",VVs.getVVTemperatures(db,reqID));
     res.put("LABEL_COLORS",VVs.getLabelColors(db,reqID));
     res.put("REQUIRE_LABEL_COLOR",VVs.getRequireLabelColor(db,reqID));
     res.put("COMPONENT_INVENTORY_GROUP_RESTRICTED",VVs.isComponentInventoryGroupRestricted(db,reqID));

     Hashtable h = VVs.getSizeUnitNetWtRequired(db);
     Vector unit = (Vector)h.get("SIZE_UNIT");
     Hashtable uHash = (Hashtable)h.get("NET_WT_REQUIRED");
     res.put("UNITS",uHash);

     Personnel pe = new Personnel(db);
     Hashtable grp = pe.getAllApprovalGroupsHash("","");
     res.put("GROUPS",grp);

     // getting info to determine whether a catalog requires directed_charge
     res.put("CATALOG_DIRECTED_CHARGE_INFO",getCatalogDirectedChargeInfo());
     res.put("CATALOG_CONSUMABLE_OPTION_INFO",getCatalogConsumableOptionInfo());

     //get cat_add_requestor_edit_msds_id
     getCatAddRequestorEditMSDSId(res,reqID);

     /*
     System.out.println("---------STATIC-------------------------------");
     System.out.println(res);
     System.out.println("----------------------------------------------");
     */
     return res;
   }

   protected void getCatAddRequestorEditMSDSId(Hashtable staticH, String reqID) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       String query = "select nvl(cat_add_requestor_edit_msds_id,'NO') cat_add_requestor_edit_msds_id from facility f,"+
                      " catalog_add_request_new carn where f.facility_id = carn.eng_eval_facility_id"+
                       " and carn.request_id = "+reqID;

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        staticH.put("EDIT_CUSTOMER_MSDS",rs.getString("cat_add_requestor_edit_msds_id"));
       }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
   } //end of method

   protected Hashtable getCatalogConsumableOptionInfo() {
    Hashtable result = new Hashtable(50);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       String query = "select catalog_id,nvl(consumable_option,'N') consumable_option from catalog";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result.put(rs.getString("catalog_id"),rs.getString("consumable_option"));
       }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
   }

   protected Hashtable getCatalogDirectedChargeInfo() {
    Hashtable result = new Hashtable(50);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       String query = "select catalog_id,directed_charge_by_part from catalog";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result.put(rs.getString("catalog_id"),rs.getString("directed_charge_by_part"));
       }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
   }

   public String getCostELement(Hashtable h) {
    String result = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       String catalogItemID = (String)h.get("CATALOG_ITEM_ID");
       String tmpCatalogID = (String)h.get("CATALOG_ID");
       String query = "select a.charge_number from catalog_part_directed_charge a, catalog_part_item_group b "+
                      "where b.catalog_id = '"+tmpCatalogID+"' and b.cat_part_no = '"+catalogItemID+"' and "+
                      "b.catalog_id = a.catalog_id and b.company_id = a.catalog_company_id and b.cat_part_no = a.cat_part_no and rownum = 1";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number"));
       }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
   }

   public String[] getCatalogItemIDNChargeNo(String catalogID, String catPartNo, String partGroupNo, String itemID) {
    String[] result = new String[2];
    result[0] = "";
    result[1] = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       String query = "select a.catalog_item_id,b.charge_number from catalog_part_item_group a, catalog_part_directed_charge b "+
                      "where a.catalog_id = '"+catalogID+"' and a.cat_part_no = '"+catPartNo+"' and a.part_group_no = "+partGroupNo+" and "+
                      "a.item_id = "+itemID+" and a.catalog_id = b.catalog_id and a.company_id = b.catalog_company_id and a.cat_part_no = b.cat_part_no and rownum = 1";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result[0] = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_item_id"));
        result[1] = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number"));
       }
    }catch (Exception e) {
      e.printStackTrace();
      result[0] = "";
      result[1] = "";
    }finally {
      dbrs.close();
    }
    return result;
   }


   protected Hashtable getDynamicData(int req,String user) throws Exception {
     Hashtable res = new Hashtable();

     CatalogAddRequestNew car = new CatalogAddRequestNew(db);
     car.setRequestId(req);
     car.load();

     String requestor=BothHelpObjs.makeBlankFromNull(car.requestor.toString());

     // CatalogAddRequest
     res.put("REQUESTOR",requestor);
     res.put("REQUEST_DATE",car.request_date);
     res.put("REQUEST_STATUS",car.request_status.toString());
     res.put("CATALOG_ID",car.catalog_id);
	  res.put("CATALOG_COMPANY_ID",car.catalog_company_id);
	  res.put("CAT_PART_NO",car.cat_part_no);
     res.put("REPLACES_PART_NO",car.replaces_part_no);
     res.put("PART_DESC",car.part_desc);
     res.put("STOCKED",car.stocked);
     res.put("INIT_90_DAY",car.init_90_day.toString());
     res.put("SUBMIT_DATE",car.getSubmitDate());

     res.put("SHELF_LIFE_OPTION",BothHelpObjs.isBlankString(car.getShelfLifeSource())?"Mfg":car.getShelfLifeSource());  //3-7-02
     res.put("SAME_SHELF_LIFE_STORAGE_TEMP",BothHelpObjs.isBlankString(car.getManageKitsAsSingleUnit())?"N":car.getManageKitsAsSingleUnit());

     res.put("ITEM_ID",car.item_id.toString());
     res.put("STARTING_VIEW",car.starting_view.toString());
     res.put("SUGGESTED_VENDOR",car.suggested_vendor);
     res.put("VENDOR_PART_NO",car.vendor_part_no);
     res.put("CONTACT_NAME",car.contact_name);
     res.put("CONTACT_EMAIL",car.contact_email);
     res.put("CONTACT_PHONE",car.contact_phone);
     res.put("CONTACT_FAX",car.contact_fax);
     res.put("ENGINEERING_EVAL",car.engineering_eval);
     res.put("FREE_SAMPLE",car.free_sample);
     res.put("CAT_PART_DIRECTED_CHRG_NO","");
     res.put("CATALOG_ITEM_ID",car.catalog_item_id);
     res.put("CONSUMABLE",car.consumable);
     res.put("PART_NUMBER_COMMENT",car.getPartNumberComment());
     Hashtable tcmISFeature = getTcmISFeature();
     res.put("TCMIS_FEATURE",tcmISFeature);      //hashtable
     if ("1".equals((String)tcmISFeature.get("newChem.2.category"))) {
       res.put("CATEGORY_DELIMITER","yYaBzZaA");
       Hashtable tmpH = getSelectedCategories(req,"yYaBzZaA");
       res.put("CATEGORY",(Collection)tmpH.get("CATEGORY"));
       res.put("CATEGORY_COMMENT",(Hashtable)tmpH.get("CATEGORY_COMMENT"));
       res.put("CATEGORY_STATUS",getCategoryStatus(req,"Y"));
       res.put("CATEGORY_STATUS_FOR_BUTTON",getCategoryStatus(req,"N"));
       if (car.request_status.intValue() > 4 && car.request_status.intValue() < 9) {
         res.put("VERIFY_CATEGORY_REQUIRED", categoryRequired(req, user));
       }else {
         res.put("VERIFY_CATEGORY_REQUIRED",new Boolean(false));
       }
     }else {
       res.put("CATEGORY_DELIMITER","");
       res.put("CATEGORY",new Vector(0));
       res.put("CATEGORY_COMMENT",new Hashtable(0));
       res.put("CATEGORY_STATUS","");
       res.put("CATEGORY_STATUS_FOR_BUTTON","");
       res.put("VERIFY_CATEGORY_REQUIRED",new Boolean(false));
     }
     //6-23-01 if client uses paint booth then set default percent to '90' currently it is only true for
     //SWA but it will work for now.
     if (tcmISFeature.get("newChem.3.paintBooth").toString().equalsIgnoreCase("1")) {
      res.put("PAINT_BOOTH_PERCENT","90");
     }

     if (car.getRequestStatus().intValue() < 5) {
      res.put("STATUS_DESC","Draft");
     }else {
      String status_desc = VVs.getVVAddRequestStatus(db,car.request_status.intValue());
      res.put("STATUS_DESC",(status_desc == null?"":status_desc.toString()));
     }
     String startingViewDesc = VVs.getVVAddRequestStatus(db,car.getStartingView().intValue());
     if (car.getStartingView().intValue() == 2 || car.getStartingView().intValue() == 3) {
      res.put("STARTING_VIEW_DESC",(startingViewDesc == null?"":startingViewDesc+"("+car.item_id.toString()+")"));           //5-13-01,12-20-01
     }else {
      res.put("STARTING_VIEW_DESC",(startingViewDesc == null?"":startingViewDesc));
     }
     Personnel myP = new Personnel(db);
     myP.setPersonnelId(car.getRequestor().intValue());
     myP.load();
     res.put("REQUESTOR_NAME",myP.getFullName()+" ( "+myP.getPhone()+" )");


     CatalogAddItemNew ci = new CatalogAddItemNew(db);
     ci.setRequestId(car.getRequestId().intValue());
     Vector allItems = ci.retrieve();

     res.put("PARTS_NUM",String.valueOf(allItems.size()));

     if (allItems != null){
       for (int i=0;i<allItems.size();i++){
          Hashtable items = new Hashtable();
          ci  = (CatalogAddItemNew) allItems.elementAt(i);
          items.put("MAT_DESC",BothHelpObjs.makeBlankFromNull(ci.getMaterialDesc()));
          items.put("MANUF",BothHelpObjs.makeBlankFromNull(ci.getManufacturer()));
          items.put("MAT_ID",BothHelpObjs.makeBlankFromNull(ci.getMaterialId().toString()));
          items.put("PART_SIZE",BothHelpObjs.makeBlankFromNull(ci.getPartSize().toString()));
          items.put("SIZE_UNIT",BothHelpObjs.makeBlankFromNull(ci.getSizeUnit()));
          items.put("PKG_STYLE",BothHelpObjs.makeBlankFromNull(ci.getPkgStyle()));
          items.put("MFG_PART_ID",BothHelpObjs.makeBlankFromNull(ci.getMfgCatID()));
          items.put("PART_ID",BothHelpObjs.makeBlankFromNull(ci.getPartId().toString()));
          items.put("MAT_TYPE",BothHelpObjs.makeBlankFromNull(ci.getMaterialType()));
          items.put("DIMENSION",BothHelpObjs.makeBlankFromNull(ci.getDimension()));
          items.put("NETWT",BothHelpObjs.makeBlankFromNull(ci.getNetWt()));
          items.put("NETWT_UNIT",BothHelpObjs.makeBlankFromNull(ci.getNetWtUnit()));
          items.put("TRADE",BothHelpObjs.makeBlankFromNull(ci.getTrade()));
          items.put("GRADE",BothHelpObjs.makeBlankFromNull(ci.getGrade()));
          Integer myMaterialID = ci.getMaterialId();
          String msds = "N";
          if (myMaterialID.intValue() > 0) {
            msds = VVs.hasMSDS(db,myMaterialID)?"Y":"N";
          }
          items.put("MSDS",msds);
          items.put("CUSTOMER_MSDS",BothHelpObjs.makeBlankFromNull(ci.getCustomerMSDS()));
          items.put("NUMBER_PER_PART",BothHelpObjs.makeBlankFromNull(ci.getNumberPerPart()));
          items.put("SAMPLE_SIZING",BothHelpObjs.makeBlankFromNull(ci.getSampleSizing()));
          // fate
          Vector fate = ci.getFate();
          items.put("FATE",fate);
          //get manufacturer recommended shelf life and storage temp
          if (!BothHelpObjs.isBlankString(car.item_id.toString()) && car.item_id.intValue() != 0) {
            getMfgShelfLifeStorage(items,car.item_id.toString(),ci.getPartId().toString());
          }else {
            items.put("MFG_SHELF_LIFE_STORAGE","");
          }
          //shelf life and storage
          items.put("SHELF_LIFE",ci.getShelfLife());
          //trong 11-14-00 if 'No shelf life' default to 'Indefinite'
          String shelfLifeUnit = ci.getShelfLifeUnit();
          if (shelfLifeUnit.equalsIgnoreCase("No shelf life")) {
           items.put("SHELF_LIFE_UNIT","Indefinite");
          }else {
           items.put("SHELF_LIFE_UNIT",shelfLifeUnit);
          }
          items.put("SHELF_LIFE_BASIS",ci.getShelfLifeBasis());
          items.put("STORAGE_TEMP",ci.getStorageTemp());
          items.put("LABEL_COLOR",ci.getLabelColor());
          //NOW put all together
          res.put("ITEM_"+i,items);
       } //end of for loop

       //Specs
       Hashtable sp = (Hashtable) car.getSpecV();
       res.put("SPEC_NUM", String.valueOf(sp.size()));
       res.put("SPECS", sp);
       //Flows
       res.put("FLOWS", car.getFlowV());
     }

     // Approval data
     res.put("APPROVAL_HASH",car.getApprovalInformationNew(db,req));

     //System.out.println("\n\n--- here in get dynamic data");
     res.put("APPROVERS",getChemicalApprovers(req,"All"));
     //if engineering evals or new work area approvals then limit the catalog to
	  //what is in catalog_add_request_new
	  if ("Y".equalsIgnoreCase(car.getEngineeringEval())) {
      res.put("CATALOG_FAC_WA_INFO",getEvalCatalogFacWAInfo(user,req));
     }else {
      res.put("CATALOG_FAC_WA_INFO",getCatalogFacWAInfoNew(user,req));
     }

     //check to see if this request already passed QC
     res.put("PASSED_QC",new Boolean(HelpObjs.countQuery(db,"select count(*) from catalog_add_approval where approval_role = 'Item QC' and request_id = "+req) > 0));

     //check to see if catalog part directed charge is required
     res.put("CATALOG_PART_DIRECTED_CHARGE_REQUIRED",new Boolean(false));
     res.put("CATALOG_PART_DIRECTED_CHARGE_REQUIRED_NEW",getCatalogPartDirectedChargeRequired(req,car.getRequestStatus()));

     return res;
   }  //end of method

   Vector getCatalogPartDirectedChargeRequired(int reqId, Integer requestStatus) {
     Vector result = new Vector(5);
     String query = "";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //if request is in draft status
       if (requestStatus.intValue() < 5) {
         query = "select distinct c.catalog_id from catalog c, catalog_facility cf, catalog_add_request_new carn"+
                 " where c.catalog_id = cf.catalog_id and c.catalog_company_id = cf.catalog_company_id and carn.eng_eval_facility_id = cf.facility_id"+
                 " and c.directed_charge_by_part = 'Required' and carn.request_id = "+reqId;
       }else {
         query = "select distinct c.catalog_id from catalog_add_request_new a, vv_catalog_add_request_status b, vv_chemical_approval_role c "+
                 "where a.request_id = "+reqId+" and a.request_status = b.request_status and b.approval_group = c.approval_group and a.catalog_id = c.catalog_id and "+
                 "a.catalog_company_id = c.catalog_company_id and a.approval_group_seq = c.approval_group_seq and a.eng_eval_facility_id = c.facility_id and c.directed_charge_by_part = 'Y'"+
                 " and c.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqId+")";
       }
       dbrs = db.doQuery(query);
       rs = dbrs.getResultSet();
       while (rs.next()) {
         result.addElement(rs.getString("catalog_id"));
       }
     }catch (Exception e) {
       e.printStackTrace();
     }
     return result;
   } //end of method


   Boolean categoryRequired(int reqID, String userID) {
     Boolean result = new Boolean(false);
     String query = "select count(*) from catalog_add_request_new carn, vv_catalog_add_request_status v,"+
                    " vv_chemical_approval_role car, chemical_approver ca"+
                    " where carn.request_id = "+reqID+" and carn.request_status = v.request_status"+
                    " and v.approval_group = car.approval_group and carn.eng_eval_facility_id = car.facility_id and carn.catalog_id = car.catalog_id"+
                    " and carn.catalog_company_id = car.catalog_company_id and carn.approval_group_seq = car.approval_group_seq and upper(car.active) = 'Y' and upper(verify_category) = 'Y'"+
                    " and car.approval_role not in (select approval_role from catalog_add_approval where request_id = "+reqID+")"+
                    " and car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and car.approval_role = ca.approval_role"+
                    " and upper(ca.active) = 'Y' and ca.personnel_id = "+userID+
                    " and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")";
     try {
       result = new Boolean(HelpObjs.countQuery(db,query) > 0);
     }catch (Exception e) {
       e.printStackTrace();
       result = new Boolean(false);
     }
     return result;
   } //end of method

   public Hashtable getSelectedCategories(int reqID, String categoryDelimiter) {
     Hashtable h = new Hashtable(2);
     Collection result = new Vector();
     Hashtable commentH = new Hashtable(11);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery("select category_system,nvl(category,' ') category,nvl(category_comment,' ') category_comment from cat_part_category_add where request_id = "+reqID);
       rs = dbrs.getResultSet();
       while (rs.next()) {
         result.add(rs.getString("category_system")+categoryDelimiter+rs.getString("category"));
         commentH.put(rs.getString("category_system"),rs.getString("category_comment"));
       }
     }catch (Exception e) {
       e.printStackTrace();
     }finally {
       dbrs.close();
     }
     h.put("CATEGORY",result);
     h.put("CATEGORY_COMMENT",commentH);
     return h;
   } //end of method

   public String getCategoryStatus(int reqID, String option) {
     String result = "";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery("select fx_criteria_condition(" + reqID + ",'"+option+"') category_status from dual");
       rs = dbrs.getResultSet();
       while (rs.next()) {
         result = rs.getString("category_status");
       }
     }catch (Exception e) {
       e.printStackTrace();
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

   void getMfgShelfLifeStorage(Hashtable items, String itemID, String partID) {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     Vector catalogV = new Vector();
     Vector catalogDescV = new Vector();
     try {
       String query = "select decode(shelf_life_days,-1,'Indefinite',null,' ',shelf_life_days) shelf_life,"+
                      "nvl(ltrim(substr(storage_temp,instr(storage_temp,' '))),' ') storage from part where item_id = "+itemID+" and part_id = "+partID;
       dbrs = db.doQuery(query);
       rs = dbrs.getResultSet();
       String tmpShelfLife = "";
       String tmpStorage = "";
       while (rs.next()) {
         tmpShelfLife = rs.getString("shelf_life");
         tmpStorage = rs.getString("storage");
       }
       String tmpVal = "";
       if (!BothHelpObjs.isBlankString(tmpShelfLife)) {
         if (tmpShelfLife.equalsIgnoreCase("Indefinite")) {
           tmpVal = tmpShelfLife;
         }else {
           tmpVal = tmpShelfLife + " days";
         }
       }
       if (!BothHelpObjs.isBlankString(tmpStorage)) {
         if (tmpVal.length() > 0) {
           tmpVal += " @ " + tmpStorage;
         }else {
           tmpVal = tmpStorage;
         }
       }
       items.put("MFG_SHELF_LIFE_STORAGE",tmpVal);
     }catch(Exception e) {
       e.printStackTrace();
       items.put("MFG_SHELF_LIFE_STORAGE","");
     }finally {
       dbrs.close();
     }
   } //end of method

   public Hashtable getEvalCatalogFacWAInfo(String user, int req) throws Exception {
    Hashtable resultH = new Hashtable();
    String query = "select "+user+" personnel_id,f.facility_id,f.facility_name,f.process_detail_required,c.catalog_id,c.catalog_company_id,c.catalog_desc,nvl(a.application,'All') application,application_desc application_display,cat_add_allow_all_for_apps"+
                   " from catalog_add_request_new ca, facility f, catalog c,catalog_facility cf, fac_loc_app a where "+
                   " ca.eng_eval_facility_id = f.facility_id and ca.eng_eval_work_area = a.application and ca.catalog_id = c.catalog_id and ca.catalog_company_id = c.catalog_company_id and"+
                   " f.facility_id = a.facility_id and f.facility_id = cf.facility_id and ca.request_id = "+req+
                   " order by catalog_desc,facility_name,application_display";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector catalogV = new Vector();
    Vector catalogDescV = new Vector();
	 Vector catalogCompanyV = new Vector();
	 try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastCatalog = "";
      String lastFacility = "";
      while (rs.next()){
        String catalog = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
        String facility = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        if (!catalogV.contains(catalog)) {
          catalogV.addElement(catalog);
          catalogDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc")));
			 catalogCompanyV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
			 //catalogDescV.addElement(catalog);
        }

        if (lastCatalog.equals(catalog)) {
          Hashtable h = (Hashtable)resultH.get(catalog);
          Vector facilityV = (Vector)h.get("FACILITY_ID");
          if (!facilityV.contains(facility)) {
            facilityV.addElement(facility);
            Vector facilityDescV = (Vector)h.get("FACILITY_DESC");
            facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
            Vector facProcessDetailV = (Vector)h.get("FAC_PROCESS_DETAIL");
            facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
            h.put("FACILITY_ID",facilityV);
            h.put("FACILITY_DESC",facilityDescV);
            h.put("FAC_PROCESS_DETAIL",facProcessDetailV);
          }

          if (lastFacility.equals(facility)) {
            Vector innerV = (Vector)h.get(facility);
            Vector waV = (Vector)innerV.elementAt(0);
            Vector waDescV = (Vector)innerV.elementAt(1);
            innerV.removeAllElements();
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
            h.put(facility,innerV);
          }else {
            Vector innerV = new Vector(3);
            Vector waV = new Vector(50);
            Vector waDescV = new Vector(50);
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
            h.put(facility,innerV);
          }
          resultH.put(catalog,h);
        }else {
          Hashtable h = new Hashtable(10);
          Vector facilityV = new Vector(10);
          Vector facilityDescV = new Vector(10);
          Vector facProcessDetailV = new Vector(10);
          facilityV.addElement(facility);
          facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
          h.put("FACILITY_ID",facilityV);
          h.put("FACILITY_DESC",facilityDescV);
          h.put("FAC_PROCESS_DETAIL",facProcessDetailV);

          Vector innerV = new Vector(3);
          Vector waV = new Vector(50);
          Vector waDescV = new Vector(50);
          waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
          waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
          innerV.addElement(waV);
          innerV.addElement(waDescV);
          innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
          h.put(facility,innerV);
          resultH.put(catalog,h);
        }

        lastCatalog = catalog;
        lastFacility = facility;
      }
      resultH.put("CATALOG_ID",catalogV);
      resultH.put("CATALOG_DESC",catalogDescV);
		resultH.put("CATALOG_COMPANY_ID",catalogCompanyV);
	 } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return resultH;
   }

   public Hashtable getCatalogFacWAInfoNew(String user, int req) throws Exception {
    Hashtable resultH = new Hashtable();
   String query = "select "+user+" personnel_id,f.facility_id,f.facility_name,f.process_detail_required,c.catalog_id,c.catalog_company_id,c.catalog_desc,nvl(a.application,'All') application,application_desc application_display,cat_add_allow_all_for_apps"+
                  " from catalog_add_request_new ca, facility f, catalog c, fac_loc_app a,catalog_facility d where "+
                  " ca.eng_eval_facility_id = f.facility_id and ca.eng_eval_facility_id = d.facility_id and d.catalog_id = c.catalog_id and d.catalog_company_id = c.catalog_company_id and"+
                  " d.catalog_add_allowed = 'Y' and ca.eng_eval_facility_id = a.facility_id and a.status = 'A' and ca.request_id = "+req+
                  " union "+
                  "select "+user+" personnel_id,f.facility_id,f.facility_name,f.process_detail_required,c.catalog_id,c.catalog_company_id,c.catalog_desc,nvl(a.application,'All') application,application_desc application_display,cat_add_allow_all_for_apps"+
                  " from catalog_add_request_new ca, facility f, catalog c, fac_loc_app a,catalog_facility d where "+
                  " ca.eng_eval_facility_id = f.facility_id and ca.eng_eval_facility_id = d.facility_id and d.catalog_id = c.catalog_id and d.catalog_company_id = c.catalog_company_id and"+
                  " ca.catalog_id = d.catalog_id and ca.catalog_company_id = d.catalog_company_id and ca.eng_eval_facility_id = a.facility_id and a.status = 'A' and ca.request_id = "+req+
                  " order by catalog_desc,facility_name,application_display";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector catalogV = new Vector();
    Vector catalogDescV = new Vector();
	 Vector catalogCompanyV = new Vector();
	 try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastCatalog = "";
      String lastFacility = "";
      while (rs.next()){
        String catalog = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
        String facility = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        if (!catalogV.contains(catalog)) {
          catalogV.addElement(catalog);
          catalogDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc")));
			 catalogCompanyV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
		  }

        if (lastCatalog.equals(catalog)) {
          Hashtable h = (Hashtable)resultH.get(catalog);
          Vector facilityV = (Vector)h.get("FACILITY_ID");
          if (!facilityV.contains(facility)) {
            facilityV.addElement(facility);
            Vector facilityDescV = (Vector)h.get("FACILITY_DESC");
            facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
            Vector facProcessDetailV = (Vector)h.get("FAC_PROCESS_DETAIL");
            facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
            h.put("FACILITY_ID",facilityV);
            h.put("FACILITY_DESC",facilityDescV);
            h.put("FAC_PROCESS_DETAIL",facProcessDetailV);
          }

          if (lastFacility.equals(facility)) {
            Vector innerV = (Vector)h.get(facility);
            Vector waV = (Vector)innerV.elementAt(0);
            Vector waDescV = (Vector)innerV.elementAt(1);
            innerV.removeAllElements();
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
            h.put(facility,innerV);
          }else {
            Vector innerV = new Vector(3);
            Vector waV = new Vector(50);
            Vector waDescV = new Vector(50);
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
            h.put(facility,innerV);
          }
          resultH.put(catalog,h);
        }else {
          Hashtable h = new Hashtable(10);
          Vector facilityV = new Vector(10);
          Vector facilityDescV = new Vector(10);
          Vector facProcessDetailV = new Vector(10);
          facilityV.addElement(facility);
          facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
          h.put("FACILITY_ID",facilityV);
          h.put("FACILITY_DESC",facilityDescV);
          h.put("FAC_PROCESS_DETAIL",facProcessDetailV);

          Vector innerV = new Vector(3);
          Vector waV = new Vector(50);
          Vector waDescV = new Vector(50);
          waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
          waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
          innerV.addElement(waV);
          innerV.addElement(waDescV);
          innerV.addElement(rs.getString("cat_add_allow_all_for_apps"));
          h.put(facility,innerV);
          resultH.put(catalog,h);
        }

        lastCatalog = catalog;
        lastFacility = facility;
      }
      resultH.put("CATALOG_ID",catalogV);
      resultH.put("CATALOG_DESC",catalogDescV);
		resultH.put("CATALOG_COMPANY_ID",catalogCompanyV);
	 } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return resultH;
   }

   public Hashtable getCatalogFacWAInfo(String user, int req) throws Exception {
    Hashtable resultH = new Hashtable();
    String query = "select personnel_id,facility_id,facility_name,process_detail_required,catalog_id,catalog_desc,application,"+
                   "application_desc application_display from cat_fac_app_view where personnel_id = "+user+
                   " union (select "+user+" personnel_id,f.facility_id,f.facility_name,f.process_detail_required,c.catalog_id,c.catalog_desc,"+
                   "nvl(a.application,'All') application,application_desc application_display"+
                   " from catalog_add_request_new ca,catalog_add_user_group caug, facility f, catalog c,fac_loc_app a "+
                   " where ca.request_id = caug.request_id and caug.facility_id = f.facility_id and caug.facility_id = a.facility_id(+) and"+
                   " caug.work_area = a.application(+) and ca.catalog_id = c.catalog_id and ca.catalog_company_id = c.catalog_company_id and ca.request_id = "+req+")"+
                   " order by catalog_desc,facility_name,application_display";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector catalogV = new Vector();
    Vector catalogDescV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastCatalog = "";
      String lastFacility = "";
      while (rs.next()){
        String catalog = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
        String facility = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        if (!catalogV.contains(catalog)) {
          catalogV.addElement(catalog);
          catalogDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc")));
          //catalogDescV.addElement(catalog);
        }

        if (lastCatalog.equals(catalog)) {
          Hashtable h = (Hashtable)resultH.get(catalog);
          Vector facilityV = (Vector)h.get("FACILITY_ID");
          if (!facilityV.contains(facility)) {
            facilityV.addElement(facility);
            Vector facilityDescV = (Vector)h.get("FACILITY_DESC");
            Vector facProcessDetailV = (Vector)h.get("FAC_PROCESS_DETAIL");
            facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
            facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
            h.put("FACILITY_ID",facilityV);
            h.put("FACILITY_DESC",facilityDescV);
            h.put("FAC_PROCESS_DETAIL",facProcessDetailV);
          }

          if (lastFacility.equals(facility)) {
            Vector innerV = (Vector)h.get(facility);
            Vector waV = (Vector)innerV.elementAt(0);
            Vector waDescV = (Vector)innerV.elementAt(1);
            innerV.removeAllElements();
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            h.put(facility,innerV);
          }else {
            Vector innerV = new Vector(2);
            Vector waV = new Vector(50);
            Vector waDescV = new Vector(50);
            waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
            waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
            innerV.addElement(waV);
            innerV.addElement(waDescV);
            h.put(facility,innerV);
          }
          resultH.put(catalog,h);
        }else {
          Hashtable h = new Hashtable(10);
          Vector facilityV = new Vector(10);
          Vector facilityDescV = new Vector(10);
          Vector facProcessDetailV = new Vector(10);
          facilityV.addElement(facility);
          facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
          facProcessDetailV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("process_detail_required")));
          h.put("FACILITY_ID",facilityV);
          h.put("FACILITY_DESC",facilityDescV);
          h.put("FAC_PROCESS_DETAIL",facProcessDetailV);

          Vector innerV = new Vector(2);
          Vector waV = new Vector(50);
          Vector waDescV = new Vector(50);
          waV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
          waDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
          innerV.addElement(waV);
          innerV.addElement(waDescV);
          h.put(facility,innerV);
          resultH.put(catalog,h);
        }

        lastCatalog = catalog;
        lastFacility = facility;
      }
      resultH.put("CATALOG_ID",catalogV);
      resultH.put("CATALOG_DESC",catalogDescV);
    } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return resultH;
   }


   public Vector getChemicalApprovers(int reqID,String role) throws Exception {
    Vector resultV = new Vector();
    String query = "select ca.personnel_id,ca.approval_role from catalog_add_request_new carn,chemical_approver ca,"+
                   "vv_chemical_approval_role car,vv_catalog_add_request_status ars where carn.request_status = ars.request_status and"+
                   " ars.approval_group = car.approval_group and"+
                   " carn.approval_group_seq = car.approval_group_seq and car.active = 'Y' and car.approval_role = ca.approval_role and"+
	           	    " car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and ca.active = 'y' and"+
                   " car.facility_id = carn.eng_eval_facility_id and car.catalog_id = carn.catalog_id and car.catalog_company_id = carn.catalog_company_id and"+
                   " 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view),0"+
                   ",car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y') and"+
                   " carn.request_id = "+reqID+
                   " and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")"+
                   " minus select chemical_approver,approval_role from catalog_add_approval where request_id = "+reqID;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        resultV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("personnel_id")));
      }
    } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return resultV;
   }

   public Hashtable getTcmISFeature()throws Exception {
    Hashtable h = new Hashtable();
    String query = "select feature,feature_mode from tcmis_feature where feature like 'newChem%'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        h.put(BothHelpObjs.makeBlankFromNull(rs.getString("feature")),BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode")));
      }
    } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    //System.out.println("\n\n----------- getTcmISFeature: "+h);
    return h;
   }

   //11-08-01
   String getMaterialIDForCustomerMSDS(String customerMSDS, String catalogID) throws Exception {
    String query = "select material_id from customer_msds_xref where lower(customer_msds_number) = lower('"+customerMSDS+"')";
    query += " and customer_msds_db = '"+catalogID+"'";
    String materialID = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          materialID = BothHelpObjs.makeBlankFromNull(rs.getString("material_id"));
        }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return materialID;
   }

  private String encodeSpecialCharacterForCRA(String val) {
    if(!BothHelpObjs.isBlankString(val)) {
        val = val.replace("<","&lt;");
        val = val.replace(">","&gt;");
        val = val.replace("'","&#39;");
        val = val.replace("\"","&quot;");
        val = val.replace("(","&#40;");
        val = val.replace(")","&#41;");
        val = val.replace("\\","&#92;");
        val = val.replace("/","&#47;");
    }
    return val;
  }

   public String buildURLString(String requestID) throws Exception {
    String url = "";
    String query = "select * from new_chem_url_view where request_id = "+requestID;
    String cmsds = "";
    String csmnum = "";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
		  boolean firstTime = true;
		  String tmpBuilding = "";
		  String tmpWorkArea = "";
		  String tmpProposeUse = "";
		  while(rs.next()) {
			 String application = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
			 if (firstTime) {
				 url += "?facilid="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"))));
				 if ("All".equalsIgnoreCase(application)) {
					tmpBuilding += "%20";
				 }else {
					tmpBuilding = URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(0,application.indexOf("/"))));
				 }
				 cmsds = BothHelpObjs.makeBlankFromNull(rs.getString("customer_msds_number"));
				 csmnum = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));          //1-12-02
				 url += "&productname="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_trade_name"))));
				 url += "&containertype="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("pkg_style"))));
				 url += "&containersize="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("part_size"))));
				 url += "&containerunit="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("size_unit"))));
				 url += "&kitflag="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("kit"))));
				 url += "&radianpurchaseid="+requestID;
				 tmpProposeUse += "&proposeduse="+URLEncoder.encode(encodeSpecialCharacterForCRA(BothHelpObjs.makeBlankFromNull(rs.getString("process_desc"))));
				 firstTime = false;
			 }
			 //allow users to pick multiple work area
			 if ("All".equalsIgnoreCase(application)) {
				tmpBuilding += "%20";
				tmpWorkArea += "%20";
			 }else {
				tmpBuilding = URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(0,application.indexOf("/"))));
				if (tmpWorkArea.length() == 0) {
					tmpWorkArea += URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(application.indexOf("/")+1)));
				}else {
					tmpWorkArea += URLEncoder.encode("|"+encodeSpecialCharacterForCRA(application.substring(application.indexOf("/")+1)));
				}
			 }
		  }
		  url += "&building="+tmpBuilding+"&location="+tmpWorkArea+tmpProposeUse;
		 //System.out.println("url:"+url);
	 }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }

    //1-12-02 decide whether to send MSDS and/or CSM or not
    if (!BothHelpObjs.isBlankString(csmnum)) {
      query = "select msds_number from seagate_part_stage_view where catalog_id = 'Seagate CSM' and seagate_part_number = '"+csmnum+"'";
      //query += " group by seagate_part_number,catalog_id";
      String seagateMSDS = "";
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          seagateMSDS = BothHelpObjs.makeBlankFromNull(rs.getString("msds_number"));
        }
      }catch (Exception e) {
        e.printStackTrace();
      }finally {
        dbrs.close();
      }
      if (!BothHelpObjs.isBlankString(seagateMSDS)) {
        url += "&msdsnumber="+BothHelpObjs.addSpaceForUrl(seagateMSDS);
        url += "&csmnumber="+BothHelpObjs.addSpaceForUrl(csmnum);
      }
    }else {
      if (!BothHelpObjs.isBlankString(cmsds)) {
        query = "select count(*) from seagate_part where msds_number = '"+cmsds+"'";
        if (HelpObjs.countQuery(db,query) > 0) {
          url += "&msdsnumber="+BothHelpObjs.addSpaceForUrl(cmsds);
        }
      }
    }
    return url;
   }

   public Hashtable getCatalogAddItemDatabaseData(String reqId) {
     Hashtable result = new Hashtable();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery("select item_id,starting_view from catalog_add_item where line_item = 1 and request_id = "+reqId);
       rs = dbrs.getResultSet();
       while (rs.next()) {
         result.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
         result.put("STARTING_VIEW",BothHelpObjs.makeBlankFromNull(rs.getString("starting_view")));
       }
     }catch (Exception e) {
       e.printStackTrace();
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

	public String updateScreenData(Hashtable inData, boolean isSubmit) throws Exception {
     String reqid       = (String) inData.get("REQ_ID");
     String where  = " where request_id = " + reqid;
     Hashtable partHash1 = (Hashtable) inData.get("PART_DATA");
     Hashtable partHash = (Hashtable) partHash1.get("PART_DATA");


     Enumeration E;
     Hashtable matHash = (Hashtable) inData.get("MAT_DATA");
     // find how many parts
     int parts=0;
     for (E=matHash.keys();E.hasMoreElements();){
        String key = (String) E.nextElement();
        if (key.startsWith("KIT_")) parts++;
     }

	  //first need to get data from database because I did not pass it thru the client
	  Hashtable catalogAddItemDatabaseData = getCatalogAddItemDatabaseData(reqid);

	  // insert catalog_add_item
     // changing part_id starting point from 0 -> 1
     // delete first
     db.doUpdate("delete from catalog_add_item"+where);
     int partId = 0;
     for (int i=0;i<parts;i++){
        partId = i + 1;
        Hashtable h = (Hashtable) matHash.get("KIT_"+i);
        db.doUpdate(updateCatalogAddItem(h,reqid,partId,matHash,catalogAddItemDatabaseData));
     }

     // update catalog_add_request_new
     db.doUpdate(updateCatalogAddRequestNew(matHash,partHash,reqid,(String) inData.get("USER_ID")));
     if ("CAL".equalsIgnoreCase(db.getClient())) {
       updateCategory(partHash, reqid);
     }

     //flows
     String catid = BothHelpObjs.makeBlankFromNull((String) partHash.get("catalogC"));
     Hashtable flow = (Hashtable) partHash1.get("FLOW_DOWN");
     Vector qFlow = (Vector) flow.get("QUALITY_FLOW");
     for (int i=0;i<qFlow.size();i++) insertFlow(catid,reqid,(String)qFlow.elementAt(i));
     Vector pFlow = (Vector) flow.get("PACKAGE_FLOW");
     for (int i=0;i<pFlow.size();i++) insertFlow(catid,reqid,(String)pFlow.elementAt(i));

     // update catalog_add_spec
     Hashtable h = null;
     updateCatalogAddSpec(h,partHash1,where,reqid);
     //end of spec

     Hashtable userGroup = (Hashtable) inData.get("APPR_DATA");
     Hashtable ugh = (Hashtable) userGroup.get("ALL_DATA");

     //trong 4/4
     CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
     Integer myReqId = new Integer(reqid);
     catR.setRequestId(myReqId.intValue());
     catR.load();
     String myFacilityId = catR.getEngEvalFacilityId();
     String myWorkArea = catR.getEngEvalWorkArea();
     String myEval = catR.getEngineeringEval();
     String engEval = (String) inData.get("EVAL_TYPE");
     //Delete only delete if it is not eng eval or hashtable contain data
     if ("y".equalsIgnoreCase(catR.getEngineeringEval())) {
       if (!ugh.isEmpty()) {
         db.doUpdate("delete from catalog_add_user_group" + where);
       }
     }else {
       db.doUpdate("delete from catalog_add_user_group" + where);
       NChem.deleteFate(db, where);
     }


     for (E=ugh.keys();E.hasMoreElements();){
        h  = (Hashtable) ugh.get(E.nextElement());
        String tempString = BothHelpObjs.makeBlankFromNull((String) h.get("PROCESS"));
        tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
        //trong 7-21 put facility id and work area id so I could access it in insertFate method
        h.put("IS_EVAL",myEval);
        h.put("ENG_EVAL_TYPE",engEval);
        h.put("EVAL_FACILITY_ID",myFacilityId);
        h.put("EVAL_WORK_AREA",myWorkArea);
        //update catalog_add_user_group
        updateCatalogUserGroup(reqid,h,myEval,engEval,myFacilityId,myWorkArea,tempString);
        //6-08-01 calling store procedure to put data for painting operation
        if (catR.usesPaintBooth(db)) {
         String[] val;
         Boolean b = (Boolean)h.get("PAINT_BOOTH");
         if (b.booleanValue()) {
          val = new String[]{reqid,"paint","paint booth",(String)h.get("PAINT_BOOTH_PERCENT")};
         }else {
          val = new String[]{reqid,"nonpaint"};
         }
         try {
          db.doProcedure("p_catalog_add_category",val);
         }catch(Exception ed) {
          ed.printStackTrace();
         }
        }

        //fate
        Enumeration E1;
        for (E1=h.keys();E1.hasMoreElements();){
          String key = (String) E1.nextElement();
          if (!key.startsWith("FATE")) continue;
          Vector v = (Vector) h.get(key);
          insertFate(Integer.parseInt(key.substring(key.indexOf("FATE")+"FATE".length())),v,h,reqid);
        }
     }  //end of for

     //last MR info for Eng Eval
     if (inData.containsKey("MR_INFO")) {
      //if carn.eng_eval_facility_id = 'El Segundo' then update cat_add_item.label_color = 'Orange-R&D'
      if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new where eng_eval_facility_id = 'El Segundo' and request_id = "+reqid) > 0) {
        db.doUpdate("update catalog_add_item set label_color = 'Orange-R&D' where request_id = "+reqid);
      }

      Hashtable MRInfoH = (Hashtable)inData.get("MR_INFO");
      String prNumber = (String)MRInfoH.get("PR_NUMBER");
      //first update purchase_request
      db.doUpdate(updatePurchaseRequest(MRInfoH,prNumber));

      //next update request_line_item
      db.doUpdate(updateRequestLineItem(MRInfoH,prNumber));

      //next insert into pr_account
      if (MRInfoH.containsKey("CHARGE_NUMBER")) {
        Vector chargeV = (Vector)MRInfoH.get("CHARGE_NUMBER");
        //if submit then I need to audit the charge numbers before inserting them into pr_account
        if (isSubmit) {
          Hashtable header = new Hashtable(4);
          header.put("PACK",(Hashtable)MRInfoH.get("PACK"));
          header.put("ACC_SYS_ID",(String)MRInfoH.get("ACC_SYS_ID"));
          header.put("FACILITY_ID",myFacilityId);
          header.put("CHARGE_TYPE",(String)MRInfoH.get("CHARGE_TYPE"));
          String error = auditChargeNumber(prNumber,chargeV,header);
          if (!BothHelpObjs.isBlankString(error)) {
            if (error.equalsIgnoreCase("1") || error.equalsIgnoreCase("2")) {
              return ("Error - Invalid charge number.");
            }
          }else {
              error = additionalChargeNumberValidation(chargeV,(String)MRInfoH.get("CHARGE_TYPE"),(String)MRInfoH.get("ACC_SYS_ID"));
              if (!"OK".equals(error)) {
                  return "Error - "+error;
              }
          }
        }
        String query = "";
        db.doUpdate("delete from pr_account where pr_number = "+prNumber);
        //10-04-02 Padding charge number
        PRAccount pra = new PRAccount(db);
        pra.setPRNumber(Integer.parseInt(prNumber));
        pra.setLineItem("1");
        for (int n = 0; n < chargeV.size(); n++) {
          String[] oa = (String[])chargeV.elementAt(n);
          if (oa.length == 3) {
            if (BothHelpObjs.isBlankString(oa[2])) continue;
            pra.setAccountNumber(oa[0]);
            pra.setAccountNumber2(oa[1]);
            pra.setPct(oa[2]);
          }else {
            if (BothHelpObjs.isBlankString(oa[1])) continue;
            pra.setAccountNumber(oa[0]);
            pra.setPct(oa[1]);
          }
          pra.setChargeType((String)MRInfoH.get("CHARGE_TYPE"));    //10-03-02
          pra.insert();
        }
      }
     }  //end of if contains MR_INFO

     //finally if submit then copy data from catalog_add_item -> catalog_add_item_qc
     if (isSubmit) {
        //first remove record just incase user already hit submit and failed.
        db.doUpdate("delete from catalog_add_item_qc where request_id = "+reqid);

        String tmpComment = "";
        if (!BothHelpObjs.isBlankString(catR.getCatPartNum())) {
        tmpComment = catR.getCatPartNum();
        }

        String query = "insert into catalog_add_item_qc (request_id,line_item,item_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,status,comments,label_color,"+
                 "vendor_contact_name,vendor_contact_email,vendor_contact_phone,vendor_contact_fax,suggested_vendor,min_storage_temp,"+
                 "max_storage_temp,storage_temp_unit,starting_view)"+
                  " (select request_id,line_item,item_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,"+
              "decode(material_id,null,'Pending MSDS','0','Pending MSDS','Pending QC'),'"+tmpComment+"',label_color,"+
                 "vendor_contact_name,vendor_contact_email,vendor_contact_phone,vendor_contact_fax,suggested_vendor,min_storage_temp,"+
                 "max_storage_temp,storage_temp_unit,starting_view"+
                  " from catalog_add_item where request_id = "+reqid+")";
        db.doUpdate(query);

        //save user data
        db.doUpdate("delete from catalog_add_item_orig where request_id = "+reqid);

        query = "insert into catalog_add_item_orig (request_id,line_item,item_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,label_color,"+
                 "vendor_contact_name,vendor_contact_email,vendor_contact_phone,vendor_contact_fax,suggested_vendor,min_storage_temp,"+
                 "max_storage_temp,storage_temp_unit,starting_view)"+
                  " (select request_id,line_item,item_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,label_color,"+
                 "vendor_contact_name,vendor_contact_email,vendor_contact_phone,vendor_contact_fax,suggested_vendor,min_storage_temp,"+
                 "max_storage_temp,storage_temp_unit,starting_view"+
                  " from catalog_add_item where request_id = "+reqid+")";
        db.doUpdate(query);

        //catalog_add_spec_qc
        //first remove record just incase user already hit submit and failed.
        db.doUpdate("delete from catalog_add_spec_qc where request_id = "+reqid);
        //catalog_add_spec_qc
        query = "insert into catalog_add_spec_qc (company_id,request_id,spec_id,spec_name,spec_title"+
              ",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,spec_source)"+
              " select company_id,request_id,spec_id,spec_name,spec_title"+
              ",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,"+
              "decode(spec_source,null,'catalog_add_spec_qc','catalog_add_spec','catalog_add_spec_qc',spec_source)"+
              " from catalog_add_spec where request_id = "+reqid;
        db.doUpdate(query);
     } //end of if submit

     String mymsg = "Record successfully stored.";
     return mymsg;
   } //end of method

   public String getFacilityDefaultIg(String reqId)throws Exception {
    String result = "";
    String query = "select inventory_group_default from facility f, catalog_add_request_new carn where f.facility_id = carn.eng_eval_facility_id"+
                   " and carn.request_id = "+reqId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result = BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group_default"));
      }
    } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return result;
   }

  void updateCatalogAddSpec(Hashtable h, Hashtable partHash1, String where, String reqid) {
    try {
      //first delete old data
      db.doUpdate("delete from catalog_add_spec" + where);
      //inserting new data
      Vector specs = (Vector) partHash1.get("SPECS");
      for (int i = 0; i < specs.size(); i++) {
        h = (Hashtable) specs.elementAt(i);
        String id = (String) h.get("SPEC_ID");
        String title = BothHelpObjs.makeBlankFromNull( (String) h.get("TITLE"));
        String name = BothHelpObjs.makeBlankFromNull( (String) h.get("NAME"));
        String revision = BothHelpObjs.makeBlankFromNull( (String) h.get("REVISION"));
        String amendment = BothHelpObjs.makeBlankFromNull( (String) h.get("AMENDMENT"));
        String specLibrary = BothHelpObjs.makeBlankFromNull( (String) h.get("SPEC_LIBRARY"));
        String coc = "N";
        if (((Boolean)h.get("COC")).booleanValue()) {
          coc = "Y";
        }
        String coa = "N";
        if (((Boolean)h.get("COA")).booleanValue()) {
          coa = "Y";
        }

        //No Specification
        if ("No Specification".equalsIgnoreCase(id)) {
          title = "No Specification";
          name = "No Specification";
          specLibrary = "global";
        }else if ("Std Mfg Cert".equalsIgnoreCase(id)) {
          title = "Std Mfg Cert";
          name = "Std Mfg Cert";
          specLibrary = "global";
        }else {
          if (BothHelpObjs.isBlankString(id)) {
            id = name;
            if (revision.length() > 0)
              id = id + "_" + revision;
            if (amendment.length() > 0)
              id = id + "_" + amendment;
          }
        }

        //don't insert data into table if spec id is empty
        if (BothHelpObjs.isBlankString(id)) continue;

        String maxLineItem = getMaxLineItem(reqid);
        db.doUpdate("insert into catalog_add_spec " +
                    "(request_id,spec_id,spec_title,spec_name,spec_version,spec_amendment,coc,coa,spec_library,line_item,spec_source)" +
                    "values (" + reqid + "," +
                    "'" + id + "'," +
                    "'" + title + "'," +
                    "'" + name + "'," +
                    "'" + revision + "'," +
                    "'" + amendment + "'," +
                    "'" + coc + "'," +
                    "'" + coa + "'," +
                    "'" + specLibrary + "'," +
                    "'" + maxLineItem + "'," +
                    "'catalog_add_spec')");
      }
    }catch (Exception e) {
      e.printStackTrace();
    }

  } //end of method

  String getMaxLineItem(String reqid) {
        String maxLineItem = "1";
        String query = "select nvl(max(line_item)+1,1) max_line_item from catalog_add_spec where request_id = "+reqid;

        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                maxLineItem = BothHelpObjs.makeBlankFromNull(rs.getString("max_line_item"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            dbrs.close();
        }
        return maxLineItem;
  } //end of method

  void updateCatalogUserGroup(String reqid,Hashtable h,String myEval,String engEval,String myFacilityId,String myWorkArea,String tempString) {
    try {
      String queryVal = "insert into catalog_add_user_group (request_id";
      String tmpVal = " values("+reqid;
      if ( myEval.equalsIgnoreCase("y") && !engEval.equalsIgnoreCase("convertEval")) {
        //facility_id
        if (!BothHelpObjs.isBlankString(myFacilityId)) {
          queryVal += ",facility_id";
          tmpVal += ",'" + myFacilityId + "'";
        }
        //work_area
        if (!BothHelpObjs.isBlankString(myWorkArea)) {
          queryVal += ",work_area";
          tmpVal += ",'" + myWorkArea + "'";
        }
        //user_group
        String tmp = (String) h.get("UGID");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",user_group_id";
          tmpVal += ",'" + tmp + "'";
        }
        //process_desc
        if (!BothHelpObjs.isBlankString(tempString)) {
          queryVal += ",process_desc";
          tmpVal += ",'" + tempString + "'";
        }
        //qty_1
        tmp = (String) h.get("QTY1");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",quantity_1";
          tmpVal += "," + tmp;
        }
        //period_1
        tmp = (String) h.get("PER1");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",per_1";
          tmpVal += "," + tmp;
        }
        //unit_1
        tmp = (String) h.get("UNIT1");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",period_1";
          tmpVal += ",'" + tmp +"'";
        }
        //qty_2
        tmp = (String) h.get("QTY2");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",quantity_2";
          tmpVal += "," + tmp;
        }
        //period_2
        tmp = (String) h.get("PER2");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",per_2";
          tmpVal += "," + tmp;
        }
        //unit_2
        tmp = (String) h.get("UNIT2");
        if (!BothHelpObjs.isBlankString(tmp)) {
          queryVal += ",period_2";
          tmpVal += ",'" + tmp +"'";
        }

        //finally put things together
        queryVal += ")" +tmpVal+")";
        db.doUpdate(queryVal);
    }else {
      //facility_id
      String tmp = (String) h.get("FACID");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",facility_id";
        tmpVal += ",'" + tmp + "'";
      }
      //work_area
      tmp = (String) h.get("WAID");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",work_area";
        tmpVal += ",'" + tmp + "'";
      }
      //user_group
      tmp = (String) h.get("UGID");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",user_group_id";
        tmpVal += ",'" + tmp + "'";
      }
      //process_desc
      if (!BothHelpObjs.isBlankString(tempString)) {
        queryVal += ",process_desc";
        tmpVal += ",'" + tempString + "'";
      }
      //qty_1
      tmp = (String) h.get("QTY1");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",quantity_1";
        tmpVal += "," + tmp;
      }
      //period_1
      tmp = (String) h.get("PER1");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",per_1";
        tmpVal += "," + tmp;
      }
      //unit_1
      tmp = (String) h.get("UNIT1");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",period_1";
        tmpVal += ",'" + tmp +"'";
      }
      //qty_2
      tmp = (String) h.get("QTY2");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",quantity_2";
        tmpVal += "," + tmp;
      }
      //period_2
      tmp = (String) h.get("PER2");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",per_2";
        tmpVal += "," + tmp;
      }
      //unit_2
      tmp = (String) h.get("UNIT2");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",period_2";
        tmpVal += ",'" + tmp +"'";
      }
      //directed charge
      tmp = (String) h.get("DIRECTED_CHARGE");
      if (!BothHelpObjs.isBlankString(tmp)) {
        queryVal += ",charge_number";
        tmpVal += ",'" + tmp +"'";
      }

      //finally put things together
      queryVal += ")" +tmpVal+")";
      db.doUpdate(queryVal);
    }
  }catch (Exception e) {
    e.printStackTrace();
  }
 } //end of method

  public Vector getSpecIDs(String requestID) throws Exception {
    Vector result = new Vector(10);
    String query = "select spec_id from catalog_add_spec where request_id = "+requestID;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("spec_id")));
      }
    } catch (Exception e) {
      throw e;
    } finally{
      dbrs.close();
    }
    return result;
  } //end of method

    String getAdditionalValidationFunction(String cType, String accSysId) {
        String result = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            StringBuilder query = new StringBuilder("select additional_validation_function from account_sys where");
            query.append(" account_sys_id = '").append(accSysId).append("' and charge_type = '").append(cType).append("'");
            dbrs = db.doQuery(query.toString());
            rs=dbrs.getResultSet();
            while(rs.next()) {
                result = rs.getString("additional_validation_function");
                break;
            }
        }catch (Exception e) {
            radian.web.emailHelpObj.sendtrongemail("Failed to get additioncal validation function","Account Sys : "+accSysId+", charge type: "+cType);
        }finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return result;
    } //end of method

    String getResultFromFunction(String query) {
        String result = "OK";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while(rs.next()) {
                result = rs.getString("additional_validate");
                break;
            }
        }catch (Exception e) {
            radian.web.emailHelpObj.sendtrongemail("Failed to get value from validation function",query);
        }finally {
            if (dbrs != null) {
                dbrs.close();
            }
        }
        return result;
    } //end of method

    String additionalChargeNumberValidation(Vector cv, String cType, String accSysId) {
        String result = "OK";
        try {
            String tmpVal = getAdditionalValidationFunction(cType,accSysId);
            if (!BothHelpObjs.isBlankString(tmpVal)) {
                //parse string to determine how to use it
                //example: fx_ray_apex_validate(charge_number_1,charge_number_2...)
                String chargeInfo = tmpVal.substring(tmpVal.indexOf("(")+1,tmpVal.indexOf(")"));
                if (!BothHelpObjs.isBlankString(chargeInfo)) {
                    for (int j = 0; j < cv.size(); j++) {
                        String[] oa = (String[])cv.elementAt(j);
                        //skip if percent is empty
                        if (oa.length == 3) {
                          if (BothHelpObjs.isBlankString(oa[2])) continue;
                        }else {
                          if (BothHelpObjs.isBlankString(oa[1])) continue;
                        }

                        StringBuilder query = new StringBuilder("select ").append(tmpVal.substring(0,tmpVal.indexOf("(")+1));
                        //loop each function
                        String[] chargeNumber = chargeInfo.split(",");
                        boolean checkData = false;
                        StringBuilder tmp2 = new StringBuilder("");
                        for (int i = 0; i < chargeNumber.length; i++) {
                            if ("charge_number_1".equals(chargeNumber[i])) {
                                if (!BothHelpObjs.isBlankString(tmp2.toString()))
                                    tmp2.append(",");
                                tmp2.append("'").append(HelpObjs.singleQuoteToDouble(oa[0])).append("'");
                            }else if ("charge_number_2".equals(chargeNumber[i])) {
                                if (!BothHelpObjs.isBlankString(tmp2.toString()))
                                    tmp2.append(",");
                                if (oa.length == 3) {
                                    tmp2.append("'").append(HelpObjs.singleQuoteToDouble(oa[1])).append("'");
                                }else {
                                    tmp2.append("''");
                                }
                            }
                        } //end of for loop
                        //put thing together
                        if (!BothHelpObjs.isBlankString(tmp2.toString())) {
                            query.append(tmp2).append(") additional_validate from dual");
                            String functionResult = getResultFromFunction(query.toString());
                            if (!"OK".equals(functionResult)) {
                                if ("label.costcenterpoolingerror".equals(functionResult))
                                    result =  "Please enter a valid Statistical Order for Pooling Cost Center.";
                                else if ("label.statisticalordererror".equals(functionResult))
                                    result = "Please enter a non-zero Cost Center for Statistical Order.";
                                else
                                    result = functionResult;
                            }
                        }
                    } //end of each pr_account record
                }
            }
     }catch(Exception e) {
       e.printStackTrace();
       result = "OK";
     }
     return result;
   } //end of method     

  String auditChargeNumber(String prNumber, Vector cv, Hashtable header) throws Exception {
    String result = "";
    try {
      for(int j=0;j<cv.size();j++){
        ChargeNumber myCN = new ChargeNumber(db);
        myCN.setChargeType((String)header.get("CHARGE_TYPE"));
        String[] oa = (String[])cv.elementAt(j);
        if (oa.length == 3) {
          if (BothHelpObjs.isBlankString(oa[2])) continue;
          myCN.setAccountNumber(oa[0]);
          myCN.setAccountNumber2(oa[1]);
        }else {
          if (BothHelpObjs.isBlankString(oa[1])) continue;
          myCN.setAccountNumber(oa[0]);
          myCN.setAccountNumber2("");
        }

        Vector vv = myCN.checkChargeNumberNew(myCN,header);
        Boolean val1 = (Boolean)vv.elementAt(0);
        Boolean val2 = (Boolean)vv.elementAt(1);
        if (!val1.booleanValue()){
          result = "1";
        }
        if (!val2.booleanValue()){
          result = "2";
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      result = "1";
    }
    return result;
  } //end of method

  String updateCatalogAddItem(Hashtable h, String reqid, int partId, Hashtable matHash, Hashtable catalogAddItemDatabaseData) {
	 String itemId = (String)catalogAddItemDatabaseData.get("ITEM_ID");
	 String startingView = (String)catalogAddItemDatabaseData.get("STARTING_VIEW");
	 String insertQ = "insert into catalog_add_item (request_id,part_id,line_item";
    String insertV = " values("+reqid+","+partId+",1";
    if (!BothHelpObjs.isBlankString((String)h.get("matDescT"))) {
      insertQ += ",material_desc";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("matDescT"))+"'";
    }else {
      insertQ += ",material_desc";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("manufT"))) {
      insertQ += ",manufacturer";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("manufT"))+"'";
    }else {
      insertQ += ",manufacturer";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("sizeT"))) {
      insertQ += ",part_size";
      insertV += ","+(String)h.get("sizeT");
    }else {
      insertQ += ",part_size";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("unitC")) && !h.get("unitC").toString().startsWith("Select")) {
      insertQ += ",size_unit";
      insertV += ",'"+(String)h.get("unitC")+"'";
    }else {
      insertQ += ",size_unit";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("packC")) && !h.get("packC").toString().startsWith("Select")) {
      insertQ += ",pkg_style";
      insertV += ",'"+(String)h.get("packC")+"'";
    }else {
      insertQ += ",pkg_style";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("manufPartT"))) {
      insertQ += ",mfg_catalog_id";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("manufPartT"))+"'";
    }else {
      insertQ += ",mfg_catalog_id";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("numberPerPart"))) {
      insertQ += ",components_per_item";
      insertV += ","+(String)h.get("numberPerPart");
    }else {
      insertQ += ",components_per_item";
      insertV += ",null";
    }
    if ("Y".equalsIgnoreCase((String)h.get("sampleSizing"))) {
      insertQ += ",sample_only";
      insertV += ",'"+(String)h.get("sampleSizing")+"'";
    }else {
      insertQ += ",sample_only";
      insertV += ",null";
    }
    String customerMSDS = (String)h.get("customerMSDS");
    if (!BothHelpObjs.isBlankString(customerMSDS)) {
      insertQ += ",customer_msds_number";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("customerMSDS"))+"'";
    }
    insertQ += ",material_id";
    if (!BothHelpObjs.isBlankString((String)h.get("matID")) && !"0".equalsIgnoreCase((String)h.get("matID"))) {
      insertV += ","+(String)h.get("matID");
    }else {
		if (!BothHelpObjs.isBlankString(itemId)) {
			insertV += ",(select a.material_id from part a where a.item_id = "+itemId+" and a.part_id = "+partId+")";
		}else {
			insertV += ",null";
		}
	 }

    if (!BothHelpObjs.isBlankString((String)h.get("netwtT"))) {
      insertQ += ",netwt";
      insertV += ","+(String)h.get("netwtT");
    }else {
      insertQ += ",netwt";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("netwtC")) && !h.get("netwtC").toString().startsWith("Select")) {
      insertQ += ",netwt_unit";
      insertV += ",'"+(String)h.get("netwtC")+"'";
    }else {
      insertQ += ",netwt_unit";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("dimT"))) {
      insertQ += ",dimension";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("dimT"))+"'";
    }else {
      insertQ += ",dimension";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("gradeT"))) {
      insertQ += ",grade";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("gradeT"))+"'";
    }else {
      insertQ += ",grade";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("tradeT"))) {
      insertQ += ",mfg_trade_name";
      insertV += ",'"+HelpObjs.singleQuoteToDouble((String)h.get("tradeT"))+"'";
    }else {
      insertQ += ",mfg_trade_name";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("shelfC"))) {
      if ("days".equalsIgnoreCase((String)h.get("shelfC"))) {
        if (!BothHelpObjs.isBlankString((String)h.get("shelfT"))) {
          insertQ += ",shelf_life_days";
          insertV += ","+(String)h.get("shelfT");
        }else {
          insertQ += ",shelf_life_days";
          insertV += ",null";
        }
      }else if ("Indefinite".equalsIgnoreCase((String)h.get("shelfC"))) {
          insertQ += ",shelf_life_days";
          insertV += ",-1";
      }else {
        insertQ += ",shelf_life_days";
        insertV += ",null";
      }
    }else {
      insertQ += ",shelf_life_days";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("shelfBasis"))) {
      insertQ += ",shelf_life_basis";
      insertV += ",'"+(String)h.get("shelfBasis")+"'";
    }else {
      insertQ += ",shelf_life_basis";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("tempC"))) {
      if (!h.get("tempC").toString().startsWith("Select")) {
        insertQ += ",storage_temp";
        insertV += ",'"+(String)h.get("tempC")+"'";
      }else {
        insertQ += ",storage_temp";
        insertV += ",null";
      }
    }else {
      insertQ += ",storage_temp";
      insertV += ",null";
    }
    if (!BothHelpObjs.isBlankString((String)h.get("labelColor"))) {
     if (!h.get("labelColor").toString().startsWith("Select")) {
       insertQ += ",label_color";
       insertV += ",'"+(String)h.get("labelColor")+"'";
     }else {
       insertQ += ",label_color";
       insertV += ",null";
     }
   }else {
     insertQ += ",label_color";
     insertV += ",null";
   }

	insertQ += ",item_id";
	if (!BothHelpObjs.isBlankString(itemId)) {
		insertV += ","+itemId;
	}else {
		insertV += ",null";
	}

	insertQ += ",starting_view";
	if (!BothHelpObjs.isBlankString(startingView)) {
		insertV += ","+startingView;
	}else {
		insertV += ",0";
	}

	 if(partId == 1) {
		Hashtable supplierH = (Hashtable) matHash.get("SUPPLIER");
		insertQ += ",vendor_contact_name";
		if (!BothHelpObjs.isBlankString((String)supplierH.get("supContT"))) {
			insertV += ",'"+HelpObjs.singleQuoteToDouble((String)supplierH.get("supContT"))+"'";
		}else {
			insertV += ",null";
		}
		insertQ += ",vendor_contact_email";
		if (!BothHelpObjs.isBlankString((String)supplierH.get("supEmailT"))) {
			insertV += ",'"+HelpObjs.singleQuoteToDouble((String)supplierH.get("supEmailT"))+"'";
		}else {
			insertV += ",null";
		}
		insertQ += ",vendor_contact_phone";
		if (!BothHelpObjs.isBlankString((String)supplierH.get("supPhoneT"))) {
			insertV += ",'"+HelpObjs.singleQuoteToDouble((String)supplierH.get("supPhoneT"))+"'";
		}else {
			insertV += ",null";
		}
		insertQ += ",vendor_contact_fax";
		if (!BothHelpObjs.isBlankString((String)supplierH.get("supFaxT"))) {
			insertV += ",'"+HelpObjs.singleQuoteToDouble((String)supplierH.get("supFaxT"))+"'";
		}else {
			insertV += ",null";
		}
		insertQ += ",suggested_vendor";
		if (!BothHelpObjs.isBlankString((String)supplierH.get("supNameT"))) {
			insertV += ",'"+HelpObjs.singleQuoteToDouble((String)supplierH.get("supNameT"))+"'";
		}else {
			insertV += ",null";
		}
	 }

	 insertQ += ")";
    insertV += ")";
    return(insertQ+insertV);
  } //end of method

  String updateCatalogAddRequestNew(Hashtable matHash, Hashtable partHash, String reqid, String userID) {
    Hashtable h = (Hashtable) matHash.get("SUPPLIER");
    Boolean freeSample = (Boolean)h.get("freeSample");
    String updateMat   = "update catalog_add_request_new set last_updated = sysdate,last_changed_by = "+userID+",free_sample = '"+(freeSample.booleanValue()?"Y":"N")+"'";

    if (!BothHelpObjs.isBlankString((String)partHash.get("catalogC"))) {
      updateMat += ",catalog_id = '"+(String)partHash.get("catalogC")+"'";
    }else {
      updateMat += ",catalog_id = null";
    }
	 if (!BothHelpObjs.isBlankString((String)partHash.get("catalogCompanyId"))) {
      updateMat += ",catalog_company_id = '"+(String)partHash.get("catalogCompanyId")+"'";
    }else {
      updateMat += ",catalog_company_id = null";
    }
	 if (!BothHelpObjs.isBlankString((String)partHash.get("partNumT"))) {
      updateMat += ",cat_part_no = '"+HelpObjs.singleQuoteToDouble((String)partHash.get("partNumT"))+"'";
    }else {
      updateMat += ",cat_part_no = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("replacePartNumT"))) {
      updateMat += ",replaces_part_no = '"+HelpObjs.singleQuoteToDouble((String)partHash.get("replacePartNumT"))+"'";
    }else {
      updateMat += ",replaces_part_no = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("partDesc"))) {
      updateMat += ",part_description = '"+HelpObjs.singleQuoteToDouble((String)partHash.get("partDesc"))+"'";
    }else {
      updateMat += ",part_description = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("OORMM"))) {
      updateMat += ",stocked = '"+(String)partHash.get("OORMM")+"'";
    }else {
      updateMat += ",stocked = null";
    }
	 if (!BothHelpObjs.isBlankString((String)partHash.get("initQty"))) {
      updateMat += ",init_90_day = "+(String)partHash.get("initQty");
    }else {
      updateMat += ",init_90_day = null";
    }
    if ("Y".equalsIgnoreCase((String)partHash.get("sameShelfLifeStorageTemp"))) {
     updateMat += ",manage_kits_as_single_unit = 'Y'";
    }else {
     updateMat += ",manage_kits_as_single_unit = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("shelfLifeOption"))) {
      updateMat += ",shelf_life_source = '"+(String)partHash.get("shelfLifeOption")+"'";
    }else {
      updateMat += ",shelf_life_source = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("catalogItemID"))) {
      updateMat += ",catalog_item_id = '"+(String)partHash.get("catalogItemID")+"'";
    }else {
      updateMat += ",catalog_item_id = null";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("consumable"))) {
      updateMat += ",consumable = '"+(String)partHash.get("consumable")+"'";
    }else {
      updateMat += ",consumable = 'N'";
    }
    if (!BothHelpObjs.isBlankString((String)partHash.get("partNumberComment"))) {
      updateMat += ",part_no_comment = '"+HelpObjs.singleQuoteToDouble((String)partHash.get("partNumberComment"))+"'";
    }else {
      updateMat += ",part_no_comment = null";
    }
    return(updateMat+" where request_status not in (7,9,12) and request_id = "+reqid);
  } //end of method

  void updateCategory(Hashtable partHash, String reqid) {
    try {
      Vector categoryV = new Vector( (Collection) partHash.get("category"));
      //first delete records for request
      if (categoryV.size() > 0) {
        db.doUpdate("delete from cat_part_category_add where request_id = " + reqid);
      }
      //insert new records for request
      String categoryDelimiter = (String) partHash.get("category_delimiter");
      Hashtable commentH = (Hashtable) partHash.get("category_comment");
      for (int i = 0; i < categoryV.size(); i++) {
        String tmpCategory = (String) categoryV.elementAt(i);
        String[] category = tmpCategory.split(categoryDelimiter);
        String query = "insert into cat_part_category_add (request_id,category_system,category,category_comment) values("+reqid;
        String categoryComment = commentH.get(category[0]).toString();
        if (categoryComment != null) {
          if (categoryComment.length() > 0) {
            query += ",'"+category[0]+"','"+category[1]+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(categoryComment)+"')";
          }else {
            query += ",'"+category[0]+"','"+category[1]+"',null)";
          }

        }else {
          query += ",'"+category[0]+"','"+category[1]+"',null)";
        }
        db.doUpdate(query);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }

  } //end of method

  String updateRequestLineItem(Hashtable MRInfoH, String prNumber) {
    Boolean critical = (Boolean)MRInfoH.get("CRITICAL");
    String query = "update request_line_item set charge_type = '"+(String)MRInfoH.get("CHARGE_TYPE")+"',critical = '"+(critical.booleanValue()?"y":"n")+"'";
    query += ",delivery_type = 'Deliver by',scrap = 'n',catalog_id = '"+(String)MRInfoH.get("CATALOG_ID")+"',catalog_company_id = '"+(String)MRInfoH.get("CATALOG_COMPANY_ID")+"',relax_shelf_life = 'n'";
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("DOCK")) && !MRInfoH.get("DOCK").toString().startsWith("Select")) {
      query += ",ship_to_location_id = '"+(String)MRInfoH.get("DOCK")+"'";
    }else {
      query += ",ship_to_location_id = null";
    }
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("DELIVER_TO")) && !MRInfoH.get("DELIVER_TO").toString().startsWith("Select")) {
      query += ",delivery_point = '"+(String)MRInfoH.get("DELIVER_TO")+"'";
    }else {
      query += ",delivery_point = null";
    }
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("QTY"))) {
      query += ",quantity = "+(String)MRInfoH.get("QTY");
    }else {
      query += ",quantity = null";
    }
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("NOTES"))) {
      query += ",notes = '"+HelpObjs.singleQuoteToDouble((String)MRInfoH.get("NOTES"))+"'";
    }else {
      query += ",notes = null";
    }
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("NEED_DATE"))) {
      query += ",required_datetime = to_date('"+(String)MRInfoH.get("NEED_DATE")+"','MM/dd/yyyy HH24:MI:SS')";
    }else {
      query += ",required_datetime = null";
    }
    //po_number
    if (MRInfoH.containsKey("PO_NUMBER")) {
      if (!BothHelpObjs.isBlankString((String)MRInfoH.get("PO_NUMBER"))) {
        query += ",po_number = '"+HelpObjs.singleQuoteToDouble((String)MRInfoH.get("PO_NUMBER"))+"'";
      }else {
        query += ",po_number = null";
      }
      if (!BothHelpObjs.isBlankString((String)MRInfoH.get("RELEASE_NUMBER"))) {
        query += ",release_number = '"+HelpObjs.singleQuoteToDouble((String)MRInfoH.get("RELEASE_NUMBER"))+"'";
      }else {
        //query += ",release_number = to_char(customer_po_release_seq.nextval)";
        query += ",release_number = null";
      }
    }
    //po unit price
    if (MRInfoH.containsKey("PO_UNIT_PRICE")) {
      String poUnitPrice = MRInfoH.get("PO_UNIT_PRICE").toString();
      String poUnitPriceCurrency = (String)MRInfoH.get("PO_CURRENCY_ID");
      if (!BothHelpObjs.isBlankString(poUnitPrice)) {
        query += ",catalog_price = "+poUnitPrice+",quoted_price = "+poUnitPrice+", baseline_price = "+poUnitPrice;
      }
      if (!"Select One".equalsIgnoreCase(poUnitPriceCurrency) && !BothHelpObjs.isBlankString(poUnitPriceCurrency)) {
        query += ",currency_id = '"+poUnitPriceCurrency+"'";
      }
    }
    return (query+" where pr_number = "+prNumber);
  } //end of method

  String updatePurchaseRequest(Hashtable MRInfoH, String prNumber) {
    String query = "update purchase_request set";
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("DEPARTMENT"))) {
      query += " department = '"+HelpObjs.singleQuoteToDouble((String)MRInfoH.get("DEPARTMENT"))+"',";
    }else {
      query += " department = null,";
    }
    if (!BothHelpObjs.isBlankString((String)MRInfoH.get("END_USER"))) {
      query += " end_user = '"+HelpObjs.singleQuoteToDouble((String)MRInfoH.get("END_USER"))+"'";
    }else {
      query += " end_user = null";
    }
    return (query+" where pr_number = "+prNumber);
  } //end of method

   void updateItem(Hashtable h,String tname,String clname,boolean n,String where) throws Exception {
       //System.out.println("\n\n---------- got here in updateItem: "+h);
        String s;
        String q;
        String updateItem  = "update catalog_add_item set ";
        String myS = "";
        if (n){
          q = updateItem+tname+"="+BothHelpObjs.makeFloatFromNull((String) h.get(clname))+" where "+where;
        } else {
          s=BothHelpObjs.makeBlankFromNull((String) h.get(clname));
          if (s != null) {
            myS = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(s);
          }
          s = myS;
          q = updateItem+tname+"="+"'"+s+"'"+" where "+where;
        }
        db.doUpdate(q);
   }

   void updateMat(Hashtable h,String tname,String clname,boolean n,String where) throws Exception {
        String s;
        String q;
        String updateMat   = "update catalog_add_request_new set ";
        String myS = "";
        if (n){
          q = updateMat+tname+"="+BothHelpObjs.makeFloatFromNull((String) h.get(clname))+" where "+where;
        } else {
          s=BothHelpObjs.makeBlankFromNull((String) h.get(clname));
          if (s != null) {
            myS = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(s);
          }
          s = myS;
          q = updateMat+tname+"="+"'"+s+"'"+" where "+where;
        }
        db.doUpdate(q);
   }

   //trong 5/8
   void updateBooleanValue(Hashtable h,String tname,String clname,String where) throws Exception {
        String s;
        String q;
        String updateMat   = "update catalog_add_request_new set ";
        Boolean b = (Boolean) h.get(clname);
        if (b.booleanValue()) {
          q = updateMat+tname+" = 'Y' where "+where;
        }else {
          q = updateMat+tname+" = 'N' where "+where;
        }

        db.doUpdate(q);
   }



   public static void deleteFate(TcmISDBModel db, String where)  throws Exception {
     Integer I;
     String query = new String("delete from catalog_add_fate_new "+ where);
     db.doUpdate(query);
   }

   public static void deleteChildreen(TcmISDBModel db, String where)  throws Exception {
     Integer I;
     String query = new String("delete from catalog_add_fate_new " + where);
     db.doUpdate(query);

     query = new String("delete from catalog_add_user_group " + where);
     db.doUpdate(query);
   }

   void insertFate(int part_id,Vector fate,Hashtable data,String reqid)  throws Exception {
     String query = new String("");
     String myEval = (String)data.get("IS_EVAL");
     String engEval = (String)data.get("ENG_EVAL_TYPE");
     part_id += 1;
     for (int i=0;i<fate.size();i=i+2){
       //if eval then use the facility id and work area from catalog_add_request_new
       if (myEval.equalsIgnoreCase("y") && !engEval.equalsIgnoreCase("convertEval")) {
          query = " insert into catalog_add_fate_new "+
               " (REQUEST_ID,FATE_ID,FATE_PERCENTAGE,USER_GROUP_ID,PART_ID, "+
               " FACILITY_ID,WORK_AREA) " +
               " select "+reqid+",fate_id,"+fate.elementAt(i+1).toString()+","+
               "'"+BothHelpObjs.makeBlankFromNull((String) data.get("UGID")) +"',"+
               part_id+
               ",'"+BothHelpObjs.makeBlankFromNull((String)data.get("EVAL_FACILITY_ID"))+"',"+
               "'"+BothHelpObjs.makeBlankFromNull((String) data.get("EVAL_WORK_AREA"))+"'"+
               " from vv_fate where fate_desc = '"+(String)fate.elementAt(i)+"'";
       } else {
          query = " insert into catalog_add_fate_new "+
               " (REQUEST_ID,FATE_ID,FATE_PERCENTAGE,USER_GROUP_ID,PART_ID, "+
               " FACILITY_ID,WORK_AREA) " +
               " select "+reqid+",fate_id,"+fate.elementAt(i+1).toString()+","+
               "'"+BothHelpObjs.makeBlankFromNull((String) data.get("UGID")) +"',"+
               part_id+
               ",'"+BothHelpObjs.makeBlankFromNull((String) data.get("FACID"))+"',"+
               "'"+BothHelpObjs.makeBlankFromNull((String) data.get("WAID"))+"'"+
               "from vv_fate where fate_desc = '"+(String)fate.elementAt(i)+"'";
       }
       db.doUpdate(query);
     }
   }

   public Integer getVVFateId(String desc) throws Exception {
      String query = "select FATE_ID from vv_fate where lower(fate_desc) like lower('%"+desc+"%')";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer d = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
          d = new Integer(rs.getInt("FATE_ID"));
         }

     } catch (Exception e) {
       throw e;
     } finally{
         dbrs.close();
     }
     return d;
   }

    //flow
    void deleteFlow(String reqid,String flow,String catid)  throws Exception  {
      int count = HelpObjs.countQuery(db,"catalog_add_flow_down","request_id="+reqid.toString()+" and flow_down='"+flow+"' and catalog_id='"+catid+"'");
      if (count>0){
        String query = "delete from catalog_add_flow_down where request_id="+reqid+" and flow_down='"+flow+"' and catalog_id='"+catid+"'";
        db.doUpdate(query);
      }
    }

    public void insertFlow(String catid,String reqid,String flow)  throws Exception {

     if (BothHelpObjs.isBlankString(flow)) return;

     String query = null;

     this.deleteFlow(reqid,flow,catid);

     query = new String("insert into catalog_add_flow_down (request_id,flow_down,catalog_id)");
     query = query + " values ( "    + reqid         +
                                ",'" + flow          + "'" +
                                ",'" + catid    + "')" ;

     db.doUpdate(query);
   } //end of method
} //end of class
