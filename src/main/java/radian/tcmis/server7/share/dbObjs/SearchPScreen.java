package radian.tcmis.server7.share.dbObjs;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import oracle.jdbc.OracleTypes;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.servlets.TcmisOutputStreamServer;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Trong Ngo,Nawaz Shaik
 * @version 1.0
 * 08-21-03 - Not showing Obsolete items in the item right click approved work areas
 * 09-19-03 - Fixing the Bug for Date Format on the MR
 */

public class SearchPScreen {

  private TcmISDBModel db;
  private String user;
  private int userID;
  private String facility;
  private String workArea;
  private String item;
  private boolean oFac, fac, oWork, work, appr;
  private boolean printScreen = false;
  private String payloadId = "";

  private int ITEM_ID = 0;
  private int FAC_ID = 1;
  private int PART_NUM = 2;
  private int MAT_DESC = 3;
  private int GRADE = 4;
  private int MFG = 5;
  private int MFG_PART_NUM = 6;
  private int PACKAGING = 7;
  private int LPP = 8;
  private int TYPE = 9;
  private int SHELF_LIFE = 10;
  private int APPR_STATUS = 11;
  private int MSDS = 12;
  private int SPEC = 13;
  private int MAT_ID = 14;
  private int SPEC_ID = 15;

  public SearchPScreen() throws java.rmi.RemoteException {

  }

  public SearchPScreen(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public void setPrintScreen(boolean b) {
    printScreen = b;
  }

  public void setPayloadId(String payloadId) {
    this.payloadId = payloadId;
  }

  public String getPayloadId() {
    return payloadId;
  }

  //7-21-01 I am going to return table model back to the client
  //2-05-02 ***** REMEMBER to add/remove column in BothHelpObjs as WELL   *****
  public static final String[] colHeadsKey = {
      "Part Color", "Comment", "Part Group", "Catalog","Catalog Company ID", "Group", "Inventory Group", "Currency ID", "Part", "Description", "Type", "Catalog Price", "Unit Price", "Min Buy", "Example Packaging", "Shelf Life @ Storage Temp", "# per Part", "Item", "Item Group", "Component Description", "Packaging",
      "Grade", "Manufacturer", "Mfg Part No.", "Status", "MSDS", "Material", "Part Item Row", "Bulk Gas", "Size Unit Option", "Item Type", "Create MR", "Baseline Reset", "Baseline Expiration", "Inventory Group Name", "Order Quantity", "Order Quantity Rule", "Median Lead Time"};
  public static int[] colDetailWidths = {
      0, 0, 0, 70, 0, 0, 0, 0, 80, 40, 100, 60, 0, 0, 0, 100, 51, 40, 0, 100, 80, 60, 70, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  public static final int[] colTypes = {
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
		BothHelpObjs.TABLE_COL_TYPE_STRING,
		BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING,
      BothHelpObjs.TABLE_COL_TYPE_STRING};
  public static final int[] colWidths = {
      0, 0, 0, 60, 0, 0, 0, 130, 200, 40, 130, 60, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

  public String getItemInventoryInfo(Hashtable inData) throws Exception {
    String result = "0";
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
	 String itemID = "";
	 try {
		itemID = (String)inData.get("ITEM_ID");
		String facilityID = (String)inData.get("FACILITY_ID");
		String catalogID = (String)inData.get("CATALOG_ID");
		String catalogCompanyId = (String) inData.get("CATALOG_COMPANY_ID");
		String partNo = (String)inData.get("PART_NO");
		String workAreaID = "";
      if (inData.containsKey("SEL_WORK_AREA")) {
        workAreaID = (String)inData.get("SEL_WORK_AREA");
        if (workAreaID.length() < 1 || workAreaID.startsWith("All")) {
          workAreaID = "";
        }
      }

		connect1 = db.getConnection();
      cs = connect1.prepareCall("{call PKG_INVENTORY_DETAIL.PR_PART_INVENTORY(?,?,?,?,?,?,?)}");
      cs.setString(1, catalogID);
      cs.setString(2, partNo);
      cs.setString(3, facilityID);
      cs.setString(4, workAreaID);
      cs.setString(5, itemID);
		cs.registerOutParameter(6, OracleTypes.CURSOR);
      cs.setString(7, catalogCompanyId);
		cs.execute();
      rs = (ResultSet) cs.getObject(6);
      while (rs.next()) {
        result = BothHelpObjs.makeBlankFromNull(rs.getString("available_qty"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Error occured while trying to access 'Right Mouse Click'", "item_id: " + itemID, 86030, false);
      throw e;
    } finally {
      rs.close();
      cs.close();
    }
    return result;
  }

  public void getItemImageMfgLit(Hashtable mySendObj, String itemId) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery("select image_content, mfg_literature_content from item where item_id = " + itemId);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        mySendObj.put("IMAGE_URL", BothHelpObjs.makeBlankFromNull(rs.getString("image_content")));
        mySendObj.put("MFG_LIT_URL", BothHelpObjs.makeBlankFromNull(rs.getString("mfg_literature_content")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
  } //end of method

  public Hashtable getInventorySpecInfo(Hashtable infoH) throws Exception {
    Hashtable result = new Hashtable();
    String catalogID = (String) infoH.get("CATALOG_ID");
	 String catalogCompanyId = (String) infoH.get("CATALOG_COMPANY_ID");
	 String facilityID = (String) infoH.get("FACILITY_ID");
    String workAreaID = BothHelpObjs.makeBlankFromNull( (String) infoH.get("SEL_WORK_AREA"));
    if (workAreaID.length() < 1 || workAreaID.startsWith("All")) {
      workAreaID = "";
    }
    String partNo = (String) infoH.get("PART_NO");
    String partGroupNo = (String) infoH.get("PART_GROUP_NO");
    String itemType = (String) infoH.get("ITEM_TYPE");
    String inventoryGroup = (String) infoH.get("INVENTORY_GROUP");
    //displaying spec detail to users
    String query = "select spec_id,on_line from spec_display_view where" +
        	          " catalog_id = '" + catalogID + "' and company_id = '"+catalogCompanyId+"'"+
		  				 " and cat_part_no='" + partNo + "' order by spec_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    //first get the spec id
    Vector specID = new Vector();
    Vector specOnLine = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        specID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("spec_id")));
        specOnLine.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("on_line")));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    String[] specText = new String[specID.size()];
    String[] specOnLineText = new String[specOnLine.size()];
    for (int i = 0; i < specID.size(); i++) {
      specText[i] = specID.elementAt(i).toString();
      specOnLineText[i] = specOnLine.elementAt(i).toString();
    }
    result.put("SPEC", specText);
    result.put("SPEC_ON_LINE", specOnLineText);

    //getting item inventory and part inventory
    if (!BothHelpObjs.isBlankString(inventoryGroup)) {
      Connection connect1 = null;
      CallableStatement cs = null;
      Vector itemInv = new Vector();
      Vector itemID = new Vector();
      try {
		  connect1 = db.getConnection();
        cs = connect1.prepareCall("{call PKG_INVENTORY_DETAIL.PR_PART_INVENTORY(?,?,?,?,?,?,?)}");
        cs.setString(1, catalogID);
        cs.setString(2, partNo);
        cs.setString(3, facilityID);
        cs.setString(4, workAreaID);
        cs.setString(5, null);
		  cs.registerOutParameter(6, OracleTypes.CURSOR);
		  cs.setString(7, catalogCompanyId);
		  cs.execute();
        rs = (ResultSet) cs.getObject(6);
        while (rs.next()) {
          itemID.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
          itemInv.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("available_qty")));
        }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db, "Error occured while trying to access 'Right Mouse Click'", "item_id: " + itemID, 86030, false);
        throw e;
      } finally {
        rs.close();
        cs.close();
      }

      float partInv = 0;
      String[] itemText = new String[itemID.size()];
      for (int j = 0; j < itemID.size(); j++) {
        String qty = itemInv.elementAt(j).toString();
        itemText[j] = itemID.elementAt(j).toString() + ":     " + qty;
        Float tmp = new Float(qty);
        partInv += tmp.floatValue();
      }
      result.put("PART_INVENTORY", new Float(partInv));
      result.put("ITEM_INVENTORY", itemText);
    } else {
      result.put("PART_INVENTORY", new Float(0));
      String[] tmp = new String[1];
      tmp[0] = "";
      result.put("ITEM_INVENTORY", tmp);
    }

    //get flow down, reorder point and stocking level
    try {
      //first get quality info if catalog set to display it
      Vector flowDownV = new Vector(20);
      Vector flowDownURLV = new Vector(20);
      if (HelpObjs.countQuery(db, "select count(*) from catalog where display_flow_down = 'Y' and catalog_id = '" + catalogID + "' and catalog_company_id = '"+catalogCompanyId+"'") > 0) {
        query = "select a.flow_down,b.content from fac_item_flow_down a, vv_flow_down b where a.catalog_id = '" + catalogID + "'"+
				    " and a.company_id = '"+catalogCompanyId+"' and fac_part_no = '" + partNo + "' and a.flow_down = b.flow_down(+)";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          flowDownV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("flow_down")));
			 String tmp = BothHelpObjs.makeBlankFromNull(rs.getString("content"));
			 if (!BothHelpObjs.isBlankString(tmp)) {
				if (tmp.indexOf("https://www.tcmis.com") >-1) {
					tmp = tmp.substring("https://www.tcmis.com".length());
				}else if (tmp.indexOf("http://www.tcmis.com") >-1) {
					tmp = tmp.substring("http://www.tcmis.com".length());
				}
			 }
			 flowDownURLV.addElement(tmp);
        }
      }
      result.put("FLOW_DOWN", flowDownV);
      result.put("FLOW_DOWN_URL", flowDownURLV);

      //reorder point and stocking level
      if (!BothHelpObjs.isBlankString(inventoryGroup)) {
        query = "select reorder_point,stocking_level from catalog_part_inventory where catalog_id = '" + catalogID + "' and cat_part_no = '" + partNo + "' and " +
                " catalog_company_id = '"+catalogCompanyId+"' and part_group_no = " + partGroupNo + " and inventory_group = '" + inventoryGroup + "'";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int countcpi = 0;
        while (rs.next()) {
          countcpi++;
          result.put("REORDER_POINT", BothHelpObjs.makeBlankFromNull(rs.getString("reorder_point")));
          result.put("STOCKING_LEVEL", BothHelpObjs.makeBlankFromNull(rs.getString("stocking_level")));
        }
        //place holder
        if (countcpi == 0) {
          result.put("REORDER_POINT", "");
          result.put("STOCKING_LEVEL", "");
        }
      } else {
        result.put("REORDER_POINT", "");
        result.put("STOCKING_LEVEL", "");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  public Vector getApprovedWorkAreas(Hashtable infoH) throws Exception {
    Vector result = new Vector();
    Boolean allCatalog = (Boolean) infoH.get("ALL_CATALOG");
    String catalogID = (String) infoH.get("CATALOG_ID");
	 String catalogCompanyId = (String) infoH.get("CATALOG_COMPANY_ID");
	 String facilityID = (String) infoH.get("FACILITY_ID");
    String partNo = (String) infoH.get("PART_NO") == null ? "" : infoH.get("PART_NO").toString();
    String partGroupNo = (infoH.get("PART_GROUP_NO") == null ? "" : infoH.get("PART_GROUP_NO").toString());

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select facility_desc,nvl(application_desc,application) application_desc,user_group_id," +
          "nvl(limit1,' ')limit1,nvl(limit2,' ') limit2, nvl(user_group_members,' ') user_group_members" +
          " from use_approval_detail_view where fac_part_no='" + partNo + "' and catalog_id='" + catalogID + "'" +
          " and catalog_company_id = '"+catalogCompanyId+"' and part_group_no = " + partGroupNo;
      if (!allCatalog.booleanValue()) {
        query += " and facility_id = '" + facilityID + "'";
      }
      query += " order by facility_id,application_desc";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable h = new Hashtable(7);
        h.put("FACILITY_NAME", rs.getString("facility_desc"));
        h.put("WORK_AREAS", rs.getString("application_desc"));
        h.put("USER_GROUP", rs.getString("user_group_id"));
        h.put("RESTRICTION_1", rs.getString("limit1"));
        h.put("RESTRICTION_2", rs.getString("limit2"));
        h.put("MEMBERS", rs.getString("user_group_members"));
        result.addElement(h);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return result;
  }

  public Hashtable getApprovedProcess(Hashtable infoH) throws Exception {
    Hashtable result = new Hashtable();
    String catalogID = (String) infoH.get("CATALOG_ID") == null ? "" : infoH.get("CATALOG_ID").toString();
	 String catalogCompanyId = (String) infoH.get("CATALOG_COMPANY_ID") == null ? "" : infoH.get("CATALOG_COMPANY_ID").toString();
	 String facilityID = (String) infoH.get("FACILITY_ID") == null ? "" : infoH.get("FACILITY_ID").toString();
    String partNo = (String) infoH.get("PART_NO") == null ? "" : infoH.get("PART_NO").toString();
    String partGroupNo = (infoH.get("PART_GROUP_NO") == null ? "" : infoH.get("PART_GROUP_NO").toString());
    String selworkarea = (String) infoH.get("SEL_WORK_AREA") == null ? "" : infoH.get("SEL_WORK_AREA").toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    String appprocess = "";

    if (!selworkarea.equals("") && !selworkarea.equals("All")) {
      appprocess += "select nvl(p.process_name,p.process_id) process_name,p.process_id from process p, process_part_usage ppu where p.process_id = ppu.process_id "+
      				  "and ppu.facility_id = p.facility_id and p.application=ppu.application and p.PROCESS_STATUS = 'Approved' "+
      				  "and ppu.facility_id = '" + facilityID + "' and ppu.application  = '" + selworkarea + "' "+
      			     "and ppu.CAT_PART_NO = '" + partNo + "' "+
      				  "and ppu.catalog_id = '" + catalogID + "' "+
			           "and p.company_id = ppu.company_id and ppu.catalog_company_id = '"+catalogCompanyId+"' "+
						  "and ppu.PART_GROUP_NO = " + partGroupNo + " order by process_name ";

      try {
        dbrs = db.doQuery(appprocess);
        rs = dbrs.getResultSet();
        Vector processID = new Vector();
        Vector processName = new Vector();
        while (rs.next()) {
          processID.addElement(rs.getString("process_id"));
          processName.addElement(rs.getString("process_name"));
        }
        processID.trimToSize();
        processName.trimToSize();
        result.put("PROCESS_ID", processID);
        result.put("PROCESS_NAME", processName);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (dbrs != null) {
          dbrs.close();
        }
      }
    }
    return result;
  } //end of method

  public boolean updateComment(Hashtable inData) throws Exception {
	  String catalogID = (String)inData.get("CATALOG_ID");
	  String catalogCompanyId = (String)inData.get("CATALOG_COMPANY_ID");
	  String partNo = (String)inData.get("PART_NO");
     String partGroupNo = (String)inData.get("PART_GROUP_NO");
	  String facID = (String)inData.get("FACILITY_ID");
	  String comment = (String)inData.get("COMMENT");
	  
	 String query = "select count(*) from client_inventory_comments";
    String where = " where catalog_id = '" + catalogID + "' and catalog_company_id = '"+catalogCompanyId+"'"+
    	 				 " and cat_part_no = '" + partNo + "' and facility_id = '" + facID + "' and part_group_no = '" + partGroupNo + "'";
    try {
      //user enter somethin in the comment
      if (!BothHelpObjs.isBlankString(comment)) {
        query += where;
        int count = HelpObjs.countQuery(db, query);
        if (count > 0) {
          //update comment
          query = "update client_inventory_comments set comments = '" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comment) + "'";
          query += where;
          db.doUpdate(query);
        } else {
          //insert record to table
          query = "insert into client_inventory_comments (catalog_company_id,catalog_id,cat_part_no,part_group_no,facility_id,comments)";
          query += " values('"+catalogCompanyId+"','" + catalogID + "','" + partNo + "','" + partGroupNo + "','" + facID + "','" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comment) + "')";
          db.doUpdate(query);
        }
      } else {
        //user remove comments
        query = "delete from client_inventory_comments";
        query += where;
        db.doUpdate(query);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public String buildPOSSearchQuery(Connection connect1, Hashtable searchContaint) throws Exception {
    String facID = (String) searchContaint.get("FACILITY_ID");
    String workArea = (String) searchContaint.get("WORK_AREA");
    String searchText = (String) searchContaint.get("SEARCH_TEXT");
    String catalogType = (String) searchContaint.get("CATALOG_TYPE");
    String inventoryGroup = catalogType.substring(catalogType.indexOf(": ") + 2);
    if (inventoryGroup.startsWith("All")) {
      inventoryGroup = "All";
    }
    Boolean approved = (Boolean) searchContaint.get("POS_APPROVED_ONLY");
    Integer userID = (Integer) searchContaint.get("USER_ID");
    Integer customerID = (Integer) searchContaint.get("CUSTOMER_ID");
    String query = "";
    CallableStatement cs = null;
	 try {
		cs = connect1.prepareCall("{call PR_CATALOG_SCREEN_POS(?,?,?,?,?,?,?,?)}");
      cs.setString(1,"USER");
      //facility
      if ("All".equalsIgnoreCase(facID) || BothHelpObjs.isBlankString(facID)) {
        cs.setString(2, "All");
      } else {
        cs.setString(2, facID);
      }
      //work area
      if ("All".equalsIgnoreCase(workArea) || BothHelpObjs.isBlankString(workArea)) {
        cs.setString(3, "All");
      } else {
        cs.setString(3, workArea);
      }
      //search text
      if (BothHelpObjs.isBlankString(searchText)) {
        cs.setString(4, "");
      } else {
        cs.setString(4, HelpObjs.singleQuoteToDouble(searchText));
      }
      //approved only
      if (approved.booleanValue()) {
        cs.setString(5, "Y");
      } else {
        cs.setString(5, "N");
      }
      //personnel ID
      if (customerID.intValue() > 0) {
        cs.setInt(6, customerID.intValue());
      } else {
        cs.setInt(6, userID.intValue());
      }
      cs.setString(7, inventoryGroup);
      cs.registerOutParameter(8, OracleTypes.VARCHAR);
      cs.execute();
      query = (String) cs.getObject(8);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cs.close();
    }
    return query;
  } //end of method

  public String buildPartSearchQuery(Connection connect1, Hashtable searchContaint) throws Exception {
    String facID = (String) searchContaint.get("FACILITY_ID");
    String workArea = (String) searchContaint.get("WORK_AREA");
    String searchText = (String) searchContaint.get("SEARCH_TEXT");
    String catalogType = (String) searchContaint.get("CATALOG_TYPE");
    Boolean activeForFacility = (Boolean) searchContaint.get("ACTIVE_SELECTED");
    Boolean approved = (Boolean) searchContaint.get("APPROVED");
    Boolean allCatalog = (Boolean) searchContaint.get("ALL_CATALOG");
    Boolean active = (Boolean) searchContaint.get("ACTIVE");
    Integer userID = (Integer) searchContaint.get("USER_ID");
    String query = "";
    CallableStatement cs = null;
	 try {
      cs = connect1.prepareCall("{call PR_CATALOG_SCREEN_PART(?,?,?,?,?,?,?,?,?,?)}");
      cs.setString(1,"USER");     //company_id
      //check to see user selected specific catalog or all catalog
      if (allCatalog.booleanValue()) {
        //facility and work area and search type
        cs.setString(2, "My Facilities");    //facility_id
        cs.setString(3, "My Applications");  //work area
        cs.setString(4,"All Catalog");       //search_type
        cs.setString(6, "N");                //approved
        if (active.booleanValue()) {
          cs.setString(8, "Y");              //active
        } else {
          cs.setString(8, "N");
        }
      } else {
        //facility
        if ("All".equalsIgnoreCase(facID) || BothHelpObjs.isBlankString(facID)) {
          cs.setString(2, "My Facilities");    //facility
        } else {
          cs.setString(2, facID);
        }
        //work area
        if ("All".equalsIgnoreCase(workArea) || BothHelpObjs.isBlankString(workArea)) {
          cs.setString(3, "My Applications");    //work area
        } else {
          cs.setString(3, workArea);
        }
        //approved only
        if (approved.booleanValue()) {
          cs.setString(4,"Active For Workarea");  //search_type
          cs.setString(6, "Y");                   //approved
        } else {
          cs.setString(4,"Active For Facility");  //search_type
          cs.setString(6, "N");
        }
        //active
        cs.setString(8, "N");                     //active
      }
      //search text
      if (BothHelpObjs.isBlankString(searchText)) {
        cs.setString(5, "");                      //search_text
      } else {
        cs.setString(5,HelpObjs.singleQuoteToDouble(searchText));
      }
      //personnel ID
      cs.setInt(7, userID.intValue());
      cs.registerOutParameter(9, OracleTypes.VARCHAR);   //print screen
      cs.registerOutParameter(10, OracleTypes.VARCHAR);  // regular search
      cs.execute();
      if (this.printScreen) {
        query = (String) cs.getObject(9);
      } else {
        query = (String) cs.getObject(10);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cs.close();
    }
	 return query;
  } //end of method

  //  this method return the data to the client
  public Hashtable getTableDataNew(Hashtable searchContaint) throws Exception {
    String currentClient = "";
    if (db.getClient().equalsIgnoreCase("Ray")) {
      currentClient = "Raytheon";
    } else {
      currentClient = db.getClient();
    }
    String[] colHeads = {
        " \nPart\nColor", " \nComment\n ", "Part\nGroup\n ", " \nCatalog\n ","Catalog\nCompany\nId", " \nGroup\n ", " \nInventory\nGroup", " \nCurrency\nID", " \n" + currentClient + "\nPart", " \n" + currentClient + "\nDescription", " \nType\n ", " \nPrice\n ", " \nUnit\nPrice", " \nMin\nBuy", " \nExample\nPackaging",
        "Shelf Life\n@\nStorage Temp", "#\nper\nPart", " \nItem\n ", "Item\nGroup\n ", " \nComponent\nDescription", " \nPackaging\n ", " \nGrade\n ", " \nManufacturer\n ", "Mfg\nPart\nNo.", " \nStatus\n ", "MSDS\nONLINE\n ", " \nMaterial\n ", "Part\nItem\nRow", " \nBulk\nGas", "Size\nUnit\nOption",
        " \nItem\nType", " \nCreate\nMR", " \nBaseline\nReset", " \nBaseline\nExpiration", "\nInventory\nGroup\nName", " \nOrder\nQuantity", "Order\nQuantity\nRule", "Median\nLead\nTime"};
    Hashtable h = new Hashtable();
    String query = "";

    String catalogType = (String) searchContaint.get("CATALOG_TYPE");
    Boolean allCatalog = (Boolean) searchContaint.get("ALL_CATALOG");
    Connection connect1 = null;

    Vector part = new Vector();
    Hashtable catalogH = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int partCount = 0;
    int partGroupCount = 0;
    int itemCount = 0;
    int compCount = 0;
    try {
      boolean showCurrency = HelpObjs.showCurrency(db);

      if (catalogType.startsWith("Part")) {
        colDetailWidths = new int[] {
            0, 0, 0, 70, 0, 0, 0, 0, 80, 100, 40, 60, 0, 0, 0, 100, 51, 40, 0, 100, 160, 60, 70, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        connect1 = db.getConnection();
        connect1.setAutoCommit(false);
        query = buildPartSearchQuery(connect1, searchContaint);
      } else if (catalogType.startsWith("POS")) {
        connect1 = db.getConnection();
        connect1.setAutoCommit(false);
        query = buildPOSSearchQuery(connect1, searchContaint);
        if (catalogType.startsWith("POS: All")) {
          colDetailWidths = new int[] {
              0, 0, 0, 70, 0, 0, 80, 0, 100, 40, 60, 0, 0, 0, 100, 51, 40, 0, 100, 160, 60, 70, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        } else {
          colDetailWidths = new int[] {
              0, 0, 0, 70, 0, 0, 0, 0, 80, 100, 40, 60, 0, 0, 0, 100, 51, 40, 0, 100, 160, 60, 70, 50, 50, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0};
        }
      }

      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String lastPartGroupItem = "";
      String lastPartGroup = "";
      String lastGroup = "";
      String lastPart = "";
      String lastItem = "";
      String lastCatPart = "";
      long sTime = new java.util.Date().getTime();
      while (rs.next()) {
        Vector comp = new Vector();
        String currentPartItemGroup = BothHelpObjs.makeBlankFromNull(rs.getString("part_item_group"));
        String currentPartGroup = BothHelpObjs.makeBlankFromNull(rs.getString("part_group"));
        String currentPart = rs.getString("cat_part_no");
        if (currentPart == null) {
          currentPart = "";
        }
        String currentItem = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        String currentGroup = BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no"));
        String currentInventoryGroup = BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group"));

        //part
        String catalogId = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
		  String catalogCompanyId = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id"));
		  String partDesc = BothHelpObjs.makeBlankFromNull(rs.getString("part_description"));
        String shelfLife = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life"));
        String currentCatPart = currentPart+catalogId+catalogCompanyId+currentGroup;

		  String stTemp = BothHelpObjs.makeBlankFromNull(rs.getString("storage_temp"));
        if (BothHelpObjs.isBlankString(stTemp)) {
          shelfLife = "";
        } else {
          if (!"Indefinite".equalsIgnoreCase(shelfLife)) {
            String tmpB = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_basis"));
            if (tmpB.length() > 0) {
              shelfLife += " " + tmpB;
            }
          }
          shelfLife += " @ " + stTemp; //12-14-01
        }

        String minCatalogPrice = "";
        String maxCatalogPrice = "";
        String minUnitPrice = "";
        String maxUnitPrice = "";
        String minBuy = "";
        String currencyID = "";
        String currency = "";
        if (!catalogType.startsWith("POS")) {
          minCatalogPrice = BothHelpObjs.makeBlankFromNull(rs.getString("min_catalog_price"));
          maxCatalogPrice = BothHelpObjs.makeBlankFromNull(rs.getString("max_catalog_price"));
          minUnitPrice = BothHelpObjs.makeBlankFromNull(rs.getString("min_unit_price"));
          maxUnitPrice = BothHelpObjs.makeBlankFromNull(rs.getString("max_unit_price"));
          minBuy = BothHelpObjs.makeBlankFromNull(rs.getString("min_buy"));
          currencyID = BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));
        } else {
          minCatalogPrice = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price"));
          maxCatalogPrice = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price"));
          minUnitPrice = BothHelpObjs.makeBlankFromNull(rs.getString("unit_price"));
          maxUnitPrice = BothHelpObjs.makeBlankFromNull(rs.getString("unit_price"));
          minBuy = BothHelpObjs.makeBlankFromNull(rs.getString("min_buy"));
			 currencyID = BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));
			 //currencyID = "USD";
        }
        if (showCurrency) {
          currency = " " + currencyID;
        }

        //part group
        String comment = BothHelpObjs.makeBlankFromNull(rs.getString("comments"));
        String partType = BothHelpObjs.makeBlankFromNull(rs.getString("stocking_method"));

        //2-05-02 this is where I am going to get the quantity available for BG
        String availableQty = "0";
        if (!allCatalog.booleanValue() && ("BG".equalsIgnoreCase(partType) || "SF".equalsIgnoreCase(partType) || "TC".equalsIgnoreCase(partType) || "SP".equalsIgnoreCase(partType))) {
          String query2 = "select sum(quantity_available) quantity_available from no_buy_order_inventory_view where cat_part_no = '" + currentPart + "' and catalog_id = '" + catalogId + "'"+
				              " and catalog_company_id = '"+catalogCompanyId+"' and part_group_no = '" + currentGroup + "'"+
				 				  " group by catalog_id,catalog_company_id,cat_part_no,part_group_no";
          DBResultSet dbrs2 = null;
          ResultSet rs2 = null;
          try {
            dbrs2 = db.doQuery(query2);
            rs2 = dbrs2.getResultSet();
            while (rs2.next()) {
              availableQty = BothHelpObjs.makeBlankFromNull(rs2.getString("quantity_available"));
            }
          } catch (Exception ee2) {
            ee2.printStackTrace();
            availableQty = "0";
          } finally {
            dbrs2.close();
          }
        }

        //item
        String qtyPerBundle = BothHelpObjs.makeBlankFromNull(rs.getString("quantity_per_bundle"));
        String bundle = BothHelpObjs.makeBlankFromNull(rs.getString("bundle"));
        String status = BothHelpObjs.makeBlankFromNull(rs.getString("approval_status"));

        //component
        String matDesc = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
        String pkg = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        String grade = BothHelpObjs.makeBlankFromNull(rs.getString("grade"));
        String mfgDesc = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc"));
        String mpn = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no"));
        String msds = BothHelpObjs.makeBlankFromNull(rs.getString("msds_on_line"));
        String material = BothHelpObjs.makeBlankFromNull(rs.getString("material_id"));
        String sizeUnitOption = "";
        String itemType = "";
        if (catalogType.startsWith("POS")) {
          sizeUnitOption = BothHelpObjs.makeBlankFromNull(rs.getString("unit_conversion_option"));
          itemType = BothHelpObjs.makeBlankFromNull(rs.getString("item_type"));
        }
        String createMR = BothHelpObjs.makeBlankFromNull(rs.getString("mr_create_from_catalog"));

        String minBaselineReset = "";
        String maxBaselineReset = "";
        String minBaselineExpiration = "";
        String maxBaselineExpiration = "";
        String inventoryGroupName = "";
        String orderQuantity = "";
        String orderQuantityRule = "";
        String medianLeadTime = "";
        if (!catalogType.startsWith("POS")) {
          minBaselineReset = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("min_price_start_date")));
          maxBaselineReset = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("max_price_start_date")));
          minBaselineExpiration = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("min_price_end_date")));
          maxBaselineExpiration = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("max_price_end_date")));
          inventoryGroupName = BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group_name"));
          orderQuantity = BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity"));
          orderQuantityRule = BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity_rule"));
          medianLeadTime = BothHelpObjs.makeBlankFromNull(rs.getString("median_lead_time"));
        }

        String baselineReset = "";
        String baselineExpiration = "";
        //reset date
        if (BothHelpObjs.isBlankString(minBaselineReset) || BothHelpObjs.isBlankString(maxBaselineReset)) {
          if (BothHelpObjs.isBlankString(minBaselineReset)) {
            baselineReset = maxBaselineReset;
          } else {
            baselineReset = minBaselineReset;
          }
        } else {
          if (minBaselineReset.equalsIgnoreCase(maxBaselineReset)) {
            baselineReset = minBaselineReset;
          } else {
            baselineReset = minBaselineReset + "-" + maxBaselineReset;
          }
        }
        baselineReset = BothHelpObjs.formatDate("toNumfromChar", baselineReset);
        //expiration date
        if (BothHelpObjs.isBlankString(minBaselineExpiration) || BothHelpObjs.isBlankString(maxBaselineExpiration)) {
          if (BothHelpObjs.isBlankString(minBaselineExpiration)) {
            baselineExpiration = minBaselineExpiration;
          } else {
            baselineExpiration = minBaselineExpiration;
          }
        } else {
          if (minBaselineExpiration.equalsIgnoreCase(maxBaselineExpiration)) {
            baselineExpiration = minBaselineExpiration;
          } else {
            baselineExpiration = minBaselineExpiration + "-" + maxBaselineExpiration;
          }
        }
        baselineExpiration = BothHelpObjs.formatDate("toNumfromChar", baselineExpiration);

        if (!currentCatPart.equals(lastCatPart)) {
          partGroupCount = 0;
          itemCount = 0;
          //insert part info
          Hashtable partH = new Hashtable();
          partCount++;
          Hashtable partGroupH = new Hashtable();
          partGroupCount++;
          Hashtable itemH = new Hashtable();
          itemCount++;
          Vector compdV = new Vector(23);
          Vector compV = new Vector(23);
          compCount++;

          //new part
          partH.put("CATALOG_ID", catalogId);
			 partH.put("CATALOG_COMPANY_ID", catalogCompanyId);
			 partH.put("PART_NO", currentPart);
			 partH.put("PART_GROUP", currentPartGroup);
			 partH.put("PART_DESC", partDesc);
          if (allCatalog.booleanValue()) {
            //unit price
            if (BothHelpObjs.isBlankString(minUnitPrice) || BothHelpObjs.isBlankString(maxUnitPrice)) {
              if (BothHelpObjs.isBlankString(minUnitPrice)) {
                partH.put("UNIT_PRICE", maxUnitPrice);
              } else {
                partH.put("UNIT_PRICE", minUnitPrice);
              }
            } else {
              BigDecimal amt = new BigDecimal(minUnitPrice);
              amt = amt.setScale(4, BigDecimal.ROUND_HALF_UP);
              partH.put("UNIT_PRICE", amt.toString());
            }
            //catalog price
            if (BothHelpObjs.isBlankString(minCatalogPrice) || BothHelpObjs.isBlankString(maxCatalogPrice)) {
              if (BothHelpObjs.isBlankString(minCatalogPrice)) {
                if (!BothHelpObjs.isBlankString(maxCatalogPrice)) {
                  partH.put("CATALOG_PRICE", maxCatalogPrice + currency);
                } else {
                  partH.put("CATALOG_PRICE", minCatalogPrice);
                }
              } else {
                partH.put("CATALOG_PRICE", minCatalogPrice + currency);
              }
            } else {
              if (minCatalogPrice.equals(maxCatalogPrice)) {
                BigDecimal minAmt = new BigDecimal(minCatalogPrice);
                minAmt = minAmt.setScale(4, BigDecimal.ROUND_HALF_UP);
                partH.put("CATALOG_PRICE", minAmt.toString() + currency);
              } else {
                BigDecimal minAmt = new BigDecimal(minCatalogPrice);
                minAmt = minAmt.setScale(4, BigDecimal.ROUND_HALF_UP);
                BigDecimal maxAmt = new BigDecimal(maxCatalogPrice);
                maxAmt = maxAmt.setScale(4, BigDecimal.ROUND_HALF_UP);
                //partH.put("CATALOG_PRICE",minAmt.toString()+"-"+maxAmt.toString());
                partH.put("CATALOG_PRICE", minAmt.toString() + "-" + maxAmt.toString() + currency);
              }
            }
          } else { //not all catalog
            if (BothHelpObjs.isBlankString(minUnitPrice)) {
              partH.put("UNIT_PRICE", minUnitPrice);
            } else {
              BigDecimal amt = new BigDecimal(minUnitPrice);
              amt = amt.setScale(4, BigDecimal.ROUND_HALF_UP);
              partH.put("UNIT_PRICE", amt.toString());
            }
            if (BothHelpObjs.isBlankString(minCatalogPrice)) {
              partH.put("CATALOG_PRICE", minCatalogPrice);
            } else {
              BigDecimal amt = new BigDecimal(minCatalogPrice);
              amt = amt.setScale(4, BigDecimal.ROUND_HALF_UP);
              //partH.put("CATALOG_PRICE",amt.toString());
              partH.put("CATALOG_PRICE", amt.toString() + currency);
            }
          } //end of not all catalog
          partH.put("MIN_BUY", minBuy);
          //new part group
          partGroupH.put("COMMENT", comment);
          partGroupH.put("PART_TYPE", partType);
          partGroupH.put("PART_GROUP_NO", currentGroup);
          partGroupH.put("INVENTORY_GROUP", currentInventoryGroup);
          partGroupH.put("CURRENCY_ID", currencyID);
          //new item
          itemH.put("ITEM_PER_PART", bundle);
          itemH.put("ITEM", currentItem);
          itemH.put("STATUS", status);

          //new component
          compdV.addElement(shelfLife);
          compdV.addElement(matDesc);
          compdV.addElement(pkg);
          compdV.addElement(grade);
          compdV.addElement(mfgDesc);
          compdV.addElement(mpn);
          compdV.addElement(msds);
          compdV.addElement(material);
          BigDecimal amtQty = new BigDecimal(availableQty);
          amtQty = amtQty.setScale(0, BigDecimal.ROUND_UP);
          compdV.addElement(amtQty.toString());
          compdV.add(sizeUnitOption);
          compdV.add(itemType);
          compdV.add(createMR);
          compdV.add(baselineReset);
          compdV.add(baselineExpiration);
          compdV.add(inventoryGroupName);
          compdV.add(orderQuantity);
          compdV.add(orderQuantityRule);
          compdV.add(medianLeadTime);

          compV.addElement(compdV);
          itemH.put("COMP_VECTOR", compV);
          partGroupH.put(currentPartGroup + itemCount, itemH);
          //partGroupH.put(currentPartGroup+currentItem,itemH);
          partGroupH.put("ITEM_COUNT", new Integer(itemCount));
          partH.put("PART_GROUP_COUNT", new Integer(partGroupCount));
          partH.put(currentPartGroup, partGroupH);
          catalogH.put(currentCatPart, partH);
          lastPart = currentPart;
          lastGroup = currentGroup;
          lastItem = currentItem;
          lastCatPart = currentCatPart;
        } else {
          //reuse part hashtable
          Hashtable partH = (Hashtable) catalogH.get(currentCatPart);
          if (!currentGroup.equals(lastGroup)) {
            //if (!partH.containsKey(currentPartGroup)) {
            Hashtable partGroupH = new Hashtable();
            partGroupCount++;
            partCount++;

            //new part group
            partGroupH.put("COMMENT", comment);
            partGroupH.put("PART_TYPE", partType);
            partGroupH.put("PART_GROUP_NO", currentGroup);
            partGroupH.put("INVENTORY_GROUP", currentInventoryGroup);
            partGroupH.put("CURRENCY_ID", currencyID);
            //new item
            Hashtable itemH = new Hashtable();
            itemCount++;
            itemH.put("SHELF_LIFE", shelfLife);
            itemH.put("ITEM_PER_PART", bundle);
            itemH.put("ITEM", currentItem);
            itemH.put("STATUS", status);
            //new component
            Vector compdV = new Vector(23);
            Vector compV = new Vector(23);
            compCount++;
            compdV.addElement(shelfLife);
            compdV.addElement(matDesc);
            compdV.addElement(pkg);
            compdV.addElement(grade);
            compdV.addElement(mfgDesc);
            compdV.addElement(mpn);
            compdV.addElement(msds);
            compdV.addElement(material);
            compdV.addElement(availableQty); //2-05-02
            compdV.add(sizeUnitOption);
            compdV.add(itemType);
            compdV.add(createMR);
            compdV.add(baselineReset);
            compdV.add(baselineExpiration);
            compdV.add(inventoryGroupName);
            compdV.add(orderQuantity);
            compdV.add(orderQuantityRule);
            compdV.add(medianLeadTime);

            compV.addElement(compdV);

            itemH.put("COMP_VECTOR", compV);
            partGroupH.put(currentPartGroup + itemCount, itemH);
            //partGroupH.put(currentPartGroup+currentItem,itemH);
            partGroupH.put("ITEM_COUNT", new Integer(itemCount));
            partH.put("PART_GROUP_COUNT", new Integer(partGroupCount));
            partH.put(currentPartGroup, partGroupH);
            catalogH.put(currentCatPart, partH);
            lastGroup = currentGroup;
            lastItem = currentItem;
          } else {
            //reuse part group hashtable
            Hashtable partGroupH = (Hashtable) partH.get(currentPartGroup);
            if (!currentItem.equals(lastItem)) {
              //if (!partGroupH.containsKey(currentPartGroup+currentItem)) {
              //new item
              Hashtable itemH = new Hashtable();
              itemCount++;
              itemH.put("SHELF_LIFE", shelfLife);
              itemH.put("ITEM_PER_PART", bundle);
              itemH.put("ITEM", currentItem);
              itemH.put("STATUS", status);
              //new component
              Vector compdV = new Vector(23);
              Vector compV = new Vector(23);
              compCount++;
              compdV.addElement(shelfLife);
              compdV.addElement(matDesc);
              compdV.addElement(pkg);
              compdV.addElement(grade);
              compdV.addElement(mfgDesc);
              compdV.addElement(mpn);
              compdV.addElement(msds);
              compdV.addElement(material);
              compdV.addElement(availableQty); //2-05-02
              compdV.add(sizeUnitOption);
              compdV.add(itemType);
              compdV.add(createMR);
              compdV.add(baselineReset);
              compdV.add(baselineExpiration);
              compdV.add(inventoryGroupName);
              compdV.add(orderQuantity);
              compdV.add(orderQuantityRule);
              compdV.add(medianLeadTime);

              compV.addElement(compdV);
              itemH.put("COMP_VECTOR", compV);
              partGroupH.put(currentPartGroup + itemCount, itemH);
              //partGroupH.put(currentPartGroup+currentItem,itemH);
              partGroupH.put("ITEM_COUNT", new Integer(itemCount));
              partH.put("PART_GROUP_COUNT", new Integer(partGroupCount));
              partH.put(currentPartGroup, partGroupH);
              catalogH.put(currentCatPart, partH);
              lastItem = currentItem;
            } else {
              //reuse item hashtable
              Hashtable itemH = (Hashtable) partGroupH.get(currentPartGroup + itemCount);
              //Hashtable itemH = (Hashtable)partGroupH.get(currentPartGroup+currentItem);
              Vector compdV = new Vector(23);
              Vector compV = (Vector) itemH.get("COMP_VECTOR");

              //make sure that I don't add the same material to data
              boolean materialExist = false;
              for (int n = 0; n < compV.size(); n++) {
                Vector tmp = (Vector) compV.elementAt(n);
                if (tmp.contains(material)) {
                  materialExist = true;
                  break;
                }
              }
              if (materialExist) {
                continue;
              }

              compCount++;
              //new component
              compdV.addElement(shelfLife);
              compdV.addElement(matDesc);
              compdV.addElement(pkg);
              compdV.addElement(grade);
              compdV.addElement(mfgDesc);
              compdV.addElement(mpn);
              compdV.addElement(msds);
              compdV.addElement(material);
              compdV.addElement(availableQty); //2-05-02
              compdV.add(sizeUnitOption);
              compdV.add(itemType);
              compdV.add(createMR);
              compdV.add(baselineReset);
              compdV.add(baselineExpiration);
              compdV.add(inventoryGroupName);
              compdV.add(orderQuantity);
              compdV.add(orderQuantityRule);
              compdV.add(medianLeadTime);

              compV.addElement(compdV);

              itemH.put("COMP_VECTOR", compV);
              partGroupH.put(currentPartGroup + itemCount, itemH);
              //partGroupH.put(currentPartGroup+currentItem,itemH);
              partGroupH.put("ITEM_COUNT", new Integer(itemCount));
              partH.put("PART_GROUP_COUNT", new Integer(partGroupCount));
              partH.put(currentPartGroup, partGroupH);
              catalogH.put(currentCatPart, partH);
            }
          }
        }
      }

      long eTime = new java.util.Date().getTime();
      long min = (eTime - sTime);
      //System.out.println("Duration: " + min);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if ("POS".startsWith(catalogType) || "Part".startsWith(catalogType)) {
        connect1.commit();
        connect1.setAutoCommit(true);
      }
      dbrs.close();
    }

	 h.put("PART_COUNT", new Integer(partCount));
    h.put("COMP_COUNT", new Integer(compCount));
    h.put("COL_HEADS", colHeads);
    h.put("COL_TYPES", colTypes);
    h.put("COL_WIDTHS", colWidths);
    h.put("COL_WIDTHS_DETAIL", colDetailWidths);
    h.put("TABLE_DATA", catalogH);
    h.put("KEY_COLUMNS", getColKey());
    h.put("PART_COLOR", new Color(224, 226, 250));
    h.put("ITEM_COMP_COLOR", new Color(224, 250, 226));
    h.put("BULK_GAS_COLOR", new Color(150, 250, 150));

    //setting table attribute from here
    Hashtable tableAttribute = new Hashtable();
    tableAttribute.put("FONT_NAME", "Dialog");
    tableAttribute.put("FONT_STYLE", new Integer(0));
    tableAttribute.put("FONT_SIZE", new Integer(10));
    tableAttribute.put("HEADER_FOREGROUND", new Color(255, 255, 255));
    tableAttribute.put("HEADER_BACKGROUND", new Color(0, 0, 66));
    //cell border
    Vector v = new Vector();
    //part border
    Hashtable ph = new Hashtable();
    ph.put("BORDER_COLOR", new Color(255, 255, 255));
    ph.put("TOP_INSETS", new Integer(0));
    ph.put("LEFT_INSETS", new Integer(1));
    ph.put("BOTTOM_INSETS", new Integer(0));
    ph.put("RIGHT_INSETS", new Integer(1));
    ph.put("ALIGN", new Integer(3)); //center - 1: right, 2: left
    v.addElement(ph);
    //item border
    Hashtable ih = new Hashtable();
    ih.put("BORDER_COLOR", new Color(0, 0, 0));
    ih.put("TOP_INSETS", new Integer(0));
    ih.put("LEFT_INSETS", new Integer(0));
    ih.put("BOTTOM_INSETS", new Integer(1));
    ih.put("RIGHT_INSETS", new Integer(1));
    ih.put("ALIGN", new Integer(3)); //center - 1: right, 2: left
    v.addElement(ih);
    //component border
    Hashtable ch = new Hashtable();
    ch.put("BORDER_COLOR", new Color(255, 255, 255));
    ch.put("TOP_INSETS", new Integer(1));
    ch.put("LEFT_INSETS", new Integer(1));
    ch.put("BOTTOM_INSETS", new Integer(0));
    ch.put("RIGHT_INSETS", new Integer(1));
    ch.put("ALIGN", new Integer(1));
    v.addElement(ch);

    tableAttribute.put("BORDER_STYLE", v);

    h.put("TABLE_ATTRIBUTE", tableAttribute);
    return h;
  }

  protected Hashtable getColKey() {
    Hashtable h = new Hashtable();
    for (int i = 0; i < colHeadsKey.length; i++) {
      h.put(colHeadsKey[i], new Integer(i));
    }
    return h;
  }

  public synchronized void getTableData(TcmisOutputStreamServer out, int col, int userID, String facility, String workArea, String item, boolean fac, boolean work, boolean appr) throws Exception {
    // CBK begin
    item = HelpObjs.singleQuoteToDouble(item);
    // CBK end
    Vector cats = new Vector();
    boolean itemON, facON;
    String userStr = new String();
    String name = new String();
    String where = new String("");
    String temp = new String();
    int i;
    itemON = false;
    // System.out.println("Here on SearchPT");
    try {
      Catalog cat = new Catalog(db);

      // item id
      item = item == null ? null : (item.length() > 0 ? item : null);

      if (fac && work) {
        cats = cat.retrieveApprovedOnly(userID, facility, workArea, item);
      } else if (fac) {
        cats = cat.retrieveAllCatalog(userID, item, facility, workArea);
      } else {
        cats = cat.retrieveAllCatalog(userID, item, null, workArea);
      }
      cats.trimToSize();

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;

    }

    //System.out.println("First data:"+cats);

    Catalog tcat;
    Object[][] data = null;
    data = new Object[cats.size()][col];

    if (cats.size() == 0) {
      return;
    }

    //catalog view
    int p = 0;
    Hashtable tcatH = null;

    //System.out.println("step 0");

    for (i = 0; i < cats.size(); i++) {
      tcat = (Catalog) cats.elementAt(i);
      data[i][ITEM_ID] = tcat.getItemId();
      data[i][PART_NUM] = tcat.getFacItemId();
      data[i][MAT_DESC] = tcat.getMaterialDesc();
      data[i][GRADE] = tcat.getGrade();
      data[i][MFG] = tcat.getMfgDesc();

      //System.out.println("step 1");

      //Packaging
      temp = new String("");
      Float size = new Float(tcat.getPartSize());
      String pStyle = tcat.getSizeUnit();
      String unit = tcat.getPkgStyle();
      if (size == null || size.toString().equals("0.0")) {
        temp = new String("");
      } else if (size.toString().endsWith(".0")) {
        temp = size.toString().substring(0, size.toString().lastIndexOf("."));
      } else {
        temp = new String(size.toString());
      }

      if (pStyle == null) {
        pStyle = new String("");
      }
      if (unit == null) {
        unit = new String("");
      }
      String sss = new String(temp + " " + pStyle + " " + unit);

      //trong 3-27-01
      int caseQty = tcat.getCaseQty();
      if (caseQty > 1) {
        sss = "Case of " + caseQty + " x " + sss;
      }

      if (BothHelpObjs.isBlankString(sss)) {
        sss = "";
      }
      data[i][PACKAGING] = new String(sss.trim());

      // price
      String myPrice = tcat.getPrice();
      /*
            if(BothHelpObjs.isBlankString(myPrice)) {
        if(PriceRequest.priceIsRequested(db,tcat.getItemId().intValue(),tcat.getFacilityId())) {
          myPrice = "Requested";
        }else{
          myPrice = "";
        }
            } */
      data[i][LPP] = myPrice;

      data[i][TYPE] = tcat.getType();

      // shelf life
      Float sLife = new Float(tcat.getShelfLife());
      String sLifeUnit = tcat.getShelfLifeUnit();
      if (sLife == null || sLife.toString().equals("0.0")) {
        temp = new String("");
      } else if (sLife.toString().endsWith(".0")) {
        temp = sLife.toString().substring(0, sLife.toString().lastIndexOf("."));
      } else {
        temp = new String(sLife.toString());
      }

      if (sLifeUnit == null) {
        sLifeUnit = new String("");
      } else if ( (sLife == null || sLife.toString().equals("0.0")) &&
                 !sLifeUnit.equals("Indefinite")) {
        sLifeUnit = new String("");
      } else {
        if (temp.equals("1") && sLifeUnit.endsWith("s")) {
          sLifeUnit = sLifeUnit.substring(0, sLifeUnit.lastIndexOf("s"));
        }
        data[i][SHELF_LIFE] = new String(temp + " " + sLifeUnit);
      }
      if (p == 0) {
      }
      p++;

      if (tcat.getFacilityId().toString().trim().equals(facility)) {
        data[i][APPR_STATUS] = tcat.getApprovalStatus();
      }
      data[i][FAC_ID] = tcat.getFacilityId();
      data[i][MSDS] = tcat.getMsdsOn();
      data[i][SPEC] = tcat.getSpecOn();
      data[i][MAT_ID] = tcat.getMatId();
      data[i][SPEC_ID] = tcat.getSpecId();
      data[i][MFG_PART_NUM] = tcat.getMfgPartNum();

      for (int c = 0; c < col; c++) {
        String tmpStr = new String(data[i][c] == null ? "" : (data[i][c]).toString());
        out.println(tmpStr);
      }

    }
    return;
  }

  public String getUnionPart(String where, String userID) {
    where = where + " union ";
    where = where + " (  select distinct ITEM_ID, FAC_ITEM_ID, MATERIAL_DESC, GRADE, MFG_DESC, PART_SIZE, SIZE_UNIT, PKG_STYLE, TYPE, PRICE, SHELF_LIFE, SHELF_LIFE_UNIT, USEAGE, USEAGE_UNIT,null APPROVAL_STATUS, null APPLICATION,FACILITY_ID from catalog_snapshot";
    where = where + " where (item_id not in (select item_id from catalog_snapshot where (personnel_id = " + (new Integer(userID)).toString() + " or user_group_id = 'All')) )";
    where = where + " and personnel_id is null ";
    where = where + " and (application is null or application = 'All')  )";
    return where;
  }

  public String[] buildRequest(int userID, String facID, Hashtable items, String accSysId, boolean aribaLogon) {
    //System.out.println("\n\n------------- items hash: "+items);
    String msg = new String("");
    String[] returnValue = new String[2];
    int nx = 0;
    try {
      PurchaseRequest PRI = new PurchaseRequest(db);
      nx = PRI.getNext();
      if (nx == 0) {
        returnValue[0] = "0";
        returnValue[1] = "No PR Produced";
        return returnValue;
      }
      PRI.setPRNumber(nx);
      PRI.setRequestor(userID);
      PRI.setFacilityId(facID);
      PRI.setAccSysId(accSysId);
      PRI.setPRStatus("entered");
      PRI.setRequestDate(new String("nowDate")); // sysdate update
      PRI.setPONumber("");
      PRI.insert();
      boolean requiredMRScreen = HelpObjs.countQuery(db, "select count(*) from pr_rules where status = 'A' and mr_screen_required = 'Y' and facility_id = '" + facID + "' and account_sys_id = '" + accSysId + "'") > 0;
      Integer lineNum = new Integer(0);
      BigDecimal qty = new BigDecimal(0);
      Integer itemN = new Integer(0);
      Hashtable lineItem;
      Enumeration itemsE;
      for (itemsE = items.keys(); itemsE.hasMoreElements(); ) {
        lineNum = new Integer(lineNum.intValue() + 1);
        lineItem = (Hashtable) items.get(itemsE.nextElement());
        qty = new BigDecimal(lineItem.get("qty").toString());
        String itemID = (String) lineItem.get("itemID");
        String application = (String) lineItem.get("application");
        String column = "(pr_number,line_item,application,ship_to_location_id," +
            "fac_part_no,item_type,requested_item_type,catalog_id,catalog_company_id," +
            "example_packaging,part_group_no,inventory_group," +
            "application_desc,delivery_point";
        String values = " select " + nx + "," + lineNum + ",'" + application + "',decode(fla.dock_selection_rule,'FIXED',fla.location_id,'')" +
            ",'" + (String) lineItem.get("partID") + "','" + (String) lineItem.get("type") + "','" + (String) lineItem.get("type") + "','" + (String) lineItem.get("catalog") + "','" + (String) lineItem.get("catalogCompanyId") + "','" +
            HelpObjs.singleQuoteToDouble( (String) lineItem.get("packaging")) + "'," + (String) lineItem.get("partGroup") + ",'" + (String) lineItem.get("inventoryGroup") + "'," +
            "fla.application_desc,decode(fla.delivery_point_selection_rule,'FIXED',fla.delivery_point,'')";

        if (itemID.equalsIgnoreCase("Various")) {
          itemID = (String) lineItem.get("exampleItemID");
          column += ",example_item_id";
          values += "," + itemID;
        } else {
          column += ",item_id";
          values += "," + itemID;
        }
        //catalog price
        String tmpVal = (String) lineItem.get("catalogPrice");
        //insert data for catalog_price and quoted_price if MR screen is required
        if (!requiredMRScreen) {
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",quoted_price,catalog_price";
            values += "," + tmpVal + "," + tmpVal;
          }
        } else {
          //don't insert data for catalog_price if pr_rules.unit_price_required is Required
          boolean unitPriceRequired = HelpObjs.countQuery(db, "select count(*) from pr_rules where status = 'A' and unit_price_required = 'Required' and facility_id = '" + facID + "' and account_sys_id = '" + accSysId + "'") > 0;
          if (!unitPriceRequired) {
            if (!BothHelpObjs.isBlankString(tmpVal)) {
              column += ",quoted_price,catalog_price";
              values += "," + tmpVal + "," + tmpVal;
              //if pr_rules.unit_price_required = 'Display' then set unit_of_sale_price = catalog_price
              boolean unitPriceDisplay = HelpObjs.countQuery(db, "select count(*) from pr_rules where status = 'A' and unit_price_required = 'Display' and facility_id = '" + facID + "' and account_sys_id = '" + accSysId + "'") > 0;
              if (unitPriceDisplay) {
                //get unit_of_sale and unit_of_sale_quantity_per_each from catalog_part_unit_of_sale
                String[] uosNUosqpe = getUnitOfSaleAndUnitOfSaleQuantityPerEach( (String) lineItem.get("catalog"),(String) lineItem.get("catalogCompanyId"), (String) lineItem.get("partID"), (String) lineItem.get("inventoryGroup")); //catalog_id,cat_part_no,inventory_group
                //0 - unit_of_sale
                //1 - unit_of_sale_quantity_per_each
                if (!BothHelpObjs.isBlankString(uosNUosqpe[0])) {
                  column += ",unit_of_sale";
                  values += ",'" + uosNUosqpe[0] + "'";
                }
                if (!BothHelpObjs.isBlankString(uosNUosqpe[1])) {
                  try {
                    float uofqpe = new Float(tmpVal).floatValue();
                    float uosp = new Float(uosNUosqpe[1]).floatValue();
                    BigDecimal tmp = new BigDecimal(uofqpe / uosp);
                    tmp = tmp.setScale(4,tmp.ROUND_HALF_UP);
                    column += ",unit_of_sale_quantity_per_each,unit_of_sale_price";
                    values += ",'" + uosNUosqpe[1] + "'," + tmp.floatValue();
                  }catch(Exception e) {
                    //do nothing
                  }
                }
              } //end of unit price is not display
            } //end of catalog_price is not empty
          } //end of pr.unit_price_required <> 'Required'
        } //end of MR screen is required

        //unit price
        tmpVal = (String) lineItem.get("unitPrice");
        if (!BothHelpObjs.isBlankString(tmpVal)) {
          column += ",baseline_price";
          values += "," + tmpVal;
        }

        //required date
        tmpVal = (String) lineItem.get("requiredDate");
        if (!BothHelpObjs.isBlankString(tmpVal)) {
          column += ",required_datetime";
          //MMDDYY
          if (tmpVal.length() == 6) {
            values += ",to_date('" + tmpVal + "','MMddyy')";
          }
          //MMDDYYYY
          else if (tmpVal.length() == 8 && tmpVal.indexOf("/") == 0) {
            values += ",to_date('" + tmpVal + "','MMddyyyy')";
          }
          //MM/DD/YY
          else if (tmpVal.length() == 8) {
            values += ",to_date('" + tmpVal + "','MM/dd/yy')";
          }
          //MM/DD/YYYY
          else if (tmpVal.length() == 10) {
            values += ",to_date('" + tmpVal + "','MM/dd/yyyy')";
          } else {
            values += ",to_date('" + tmpVal + "','MM/dd/yyyy')";
          }
        }
        //notes
        tmpVal = (String) lineItem.get("note");
        if (!BothHelpObjs.isBlankString(tmpVal)) {
          column += ",notes";
          values += ",'" + HelpObjs.singleQuoteToDouble(tmpVal) + "'";
        }
        Boolean crit = (Boolean) lineItem.get("critical");
        if (crit.booleanValue()) {
          column += ",critical";
          values += ",'Y'";
        }
        //Point of sale MR
        if (lineItem.containsKey("MR_FOR_POS")) {
          if ( ( (Boolean) lineItem.get("MR_FOR_POS")).booleanValue()) {
            String itemType = (String) lineItem.get("DISPENSED_ITEM_TYPE");
            //if MD or MB then convert qty and insert UOM, otherwise leave qty the way it is
            if ("MD".equalsIgnoreCase(itemType) || "MB".equalsIgnoreCase(itemType)) {
              column += ",requested_dispensed_size_unit,quantity";
              values += ",'" + (String) lineItem.get("DISPENSED_UOM") +
                  "',(select fx_item_convert_from_unit(" + itemID + ",'" + (String) lineItem.get("DISPENSED_UOM") + "'," + qty + ") from dual)";
            } else {
              column += ",quantity";
              values += "," + qty;
            }
          } else {
            column += ",quantity";
            values += "," + qty;
          }
        } else {
          column += ",quantity";
          values += "," + qty;
        }
        //currency
        tmpVal = (String) lineItem.get("currencyID");
        if (!BothHelpObjs.isBlankString(tmpVal)) {
          column += ",currency_id";
          values += ",'" + tmpVal.trim() + "'";
        }
        //order quantity rule
        if (lineItem.containsKey("orderQuantityRule")) {
          tmpVal = (String) lineItem.get("orderQuantityRule");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",order_quantity_rule";
            values += ",'" + tmpVal.trim() + "'";
          }
        }
		  //allocate by distance
		  column += ",allocate_by_distance";
		  values += ",fx_allocate_by_distance(fla.company_id,fla.facility_id,fla.application,'"+(String) lineItem.get("catalogCompanyId")+"','"+
				      (String) lineItem.get("catalog")+"','"+(String) lineItem.get("partID")+"',"+(String) lineItem.get("partGroup")+")";

		  //Finally put everything together
        values += " from fac_loc_app fla where application = '" + application + "' and facility_id = '" + facID + "'";
        db.doUpdate("insert into request_line_item " + column + ")" + values);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      msg = "Error on updating PRI and RLI. Error: " + e.getMessage();
      returnValue[0] = "0";
      returnValue[1] = msg;
      return returnValue;
    }
    Integer temp = new Integer(nx);
    returnValue[0] = new String(temp.toString());
    returnValue[1] = "New request created.";
    return returnValue;
  }

  public String[] buildUpdateRequest(int nx, Hashtable items, String cFacID, boolean aribaLogon) {
    //System.out.println("\n\n------------- items hash: "+items);
    String msg = new String("");
    String[] returnValue = new String[2];
    try {
      String tmpQuery = "select count(*) from pr_rules a, purchase_request pr where a.status = 'A' and a.mr_screen_required = 'Y'" +
          " and a.facility_id = pr.facility_id and a.account_sys_id = pr.account_sys_id and pr.pr_number = " + nx;
      boolean requiredMRScreen = HelpObjs.countQuery(db, tmpQuery) > 0;
      int numberOfExistingLine =  HelpObjs.countQuery(db,"select count(*) from request_line_item where pr_number = "+nx);
      Integer lineNum = new Integer(0);
      BigDecimal qty = new BigDecimal("0");
      Integer itemN = new Integer(0);
      Hashtable lineItem;
      int count = 0;
      int numberOfNewLine = 0;
      while (count < items.size()) {
        lineNum = new Integer(count + 1);
        lineItem = (Hashtable) items.get(new Integer(count));
        count++;
        qty = new BigDecimal(lineItem.get("qty").toString());
        String itemID = (String) lineItem.get("itemID");
        String application = (String) lineItem.get("application");
        if (qty.intValue() < 1) {
          msg = "Quantity invalid for item " + itemN.toString();
          returnValue[0] = "0";
          returnValue[1] = msg;
          return returnValue;
        }
        //now determine whether to update or create new line item
        String cPRNum = (String) lineItem.get("pr_number");
        if (BothHelpObjs.isBlankString(cPRNum)) { //if line item does not contains pr_number then create new line item
          numberOfNewLine++;
          String column = "(pr_number,line_item,application,ship_to_location_id," +
              "quantity,fac_part_no,item_type,requested_item_type,catalog_id," +
              "example_packaging,part_group_no,inventory_group," +
              "delivery_point";
          String values = " values (" + nx + "," + (numberOfExistingLine+numberOfNewLine) + ",'" + application + "',(select location_id from fac_loc_app where application = '" + application + "' and facility_id = '" + cFacID + "' and dock_selection_rule = 'FIXED')," +
              qty + ",'" + (String) lineItem.get("partID") + "','" + (String) lineItem.get("type") + "','" + (String) lineItem.get("type") + "','" + (String) lineItem.get("catalog") + "','" +
              HelpObjs.singleQuoteToDouble( (String) lineItem.get("packaging")) + "'," + (String) lineItem.get("partGroup") + ",'" + (String) lineItem.get("inventoryGroup") + "'," +
              "(select delivery_point from fac_loc_app where application = '" + application + "' and facility_id = '" + cFacID + "'  and delivery_point_selection_rule = 'FIXED')";
          if (itemID.equalsIgnoreCase("Various")) {
            column += ",example_item_id";
            values += "," + (String) lineItem.get("exampleItemID");
          } else {
            column += ",item_id";
            values += "," + itemID;
          }
          //catalog price
          //don't insert data for catalog_price and quoted_price if MR screen is required
          String tmpVal = (String) lineItem.get("catalogPrice");
          if (!requiredMRScreen) {
            if (!BothHelpObjs.isBlankString(tmpVal)) {
              column += ",quoted_price,catalog_price";
              values += "," + tmpVal + "," + tmpVal;
            }
          } else {
            //don't insert data for catalog_price if pr_rules.unit_price_required is Required
            boolean unitPriceRequired = HelpObjs.countQuery(db, "select count(*) from pr_rules p, purchase_request pr where p.status = 'A' and p.unit_price_required = 'Required' and p.facility_id = pr.facility_id and p.account_sys_id = pr.account_sys_id and pr.pr_number = " + nx) > 0;
            if (!unitPriceRequired) {
              if (!BothHelpObjs.isBlankString(tmpVal)) {
                column += ",quoted_price,catalog_price";
                values += "," + tmpVal + "," + tmpVal;
                //if pr_rules.unit_price_required = 'Display' then set unit_of_sale_price = catalog_price
                boolean unitPriceDisplay = HelpObjs.countQuery(db, "select count(*) from pr_rules p, purchase_request pr where p.status = 'A' and p.unit_price_required = 'Display' and p.facility_id = pr.facility_id and p.account_sys_id = pr.account_sys_id and pr.pr_number = " + nx) > 0;
                if (unitPriceDisplay) {
                  //get unit_of_sale and unit_of_sale_quantity_per_each from catalog_part_unit_of_sale
                  String[] uosNUosqpe = getUnitOfSaleAndUnitOfSaleQuantityPerEach( (String) lineItem.get("catalog"),(String) lineItem.get("catalogCompanyId"), (String) lineItem.get("partID"),(String) lineItem.get("inventoryGroup")); //catalog_id,cat_part_no,inventory_group
                  //0 - unit_of_sale
                  //1 - unit_of_sale_quantity_per_each
                  if (!BothHelpObjs.isBlankString(uosNUosqpe[0])) {
                    column += ",unit_of_sale";
                    values += ",'" + uosNUosqpe[0] + "'";
                  }
                  if (!BothHelpObjs.isBlankString(uosNUosqpe[1])) {
                    try {
                      column += ",unit_of_sale_quantity_per_each,unit_of_sale_price";
                      values += ",'" + uosNUosqpe[1] + "'," + new Float(tmpVal).floatValue() / (new Float(uosNUosqpe[1])).floatValue();
                    }catch(Exception e) {
                      //do nothing
                    }
                  }
                } //end of unit price is not display
              } //end of catalog_price is not empty
            } //end of pr.unit_price_required <> 'Required'
          } //end of MR screen is required

          //unit price
          tmpVal = (String) lineItem.get("unitPrice");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",baseline_price";
            values += "," + tmpVal;
          }
          //required date
          tmpVal = (String) lineItem.get("requiredDate");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",required_datetime";
            values += ",to_date('" + tmpVal + "','MM/dd/yyyy')";
          }
          //notes
          tmpVal = (String) lineItem.get("note");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",notes";
            values += ",'" + HelpObjs.singleQuoteToDouble(tmpVal) + "'";
          }
          Boolean crit = (Boolean) lineItem.get("critical");
          if (crit.booleanValue()) {
            column += ",critical";
            values += ",'Y'";
          }
          //currency
          tmpVal = (String) lineItem.get("currencyID");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            column += ",currency_id";
            values += ",'" + tmpVal.trim() + "'";
          }
          //order quantity rule
          if (lineItem.containsKey("orderQuantityRule")) {
            tmpVal = (String) lineItem.get("orderQuantityRule");
            if (!BothHelpObjs.isBlankString(tmpVal)) {
              column += ",order_quantity_rule";
              values += ",'" + tmpVal.trim() + "'";
            }
          }
			 //allocate by distance
		  	 column += ",allocate_by_distance";
		    values += ",(select fx_allocate_by_distance(fla.company_id,fla.facility_id,fla.application,'"+(String) lineItem.get("catalogCompanyId")+"','"+
				          (String) lineItem.get("catalog")+"','"+(String) lineItem.get("partID")+"',"+(String) lineItem.get("partGroup")+")"+
        					" from fac_loc_app fla where application = '" + application + "' and facility_id = '" + cFacID + "')";

			 db.doUpdate("insert into request_line_item " + column + ")" + values + ")");
        } else { //line item already exist then update qty,catalog_price,unit_price,need date,notes,critical
          int currentLineItem = -1;
          try {
            currentLineItem = Integer.parseInt((String)lineItem.get("lineItem"));
          }catch (Exception ee) {
            continue;
          }
          String query = "update request_line_item set quantity = " + qty.toString();
          //catalog price
          String tmpVal = (String) lineItem.get("catalogPrice");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            query += ",quoted_price = " + tmpVal + ",catalog_price = " + tmpVal;
          }
          //unit price
          tmpVal = (String) lineItem.get("unitPrice");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            query += ",baseline_price = " + tmpVal;
          }
          //required date
          tmpVal = (String) lineItem.get("requiredDate");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            query += ",required_datetime = to_date('" + tmpVal + "','MM/dd/yyyy')";
          }
          //notes
          tmpVal = (String) lineItem.get("note");
          if (!BothHelpObjs.isBlankString(tmpVal)) {
            query += ",notes = '" + HelpObjs.singleQuoteToDouble(tmpVal) + "'";
          }
          Boolean crit = (Boolean) lineItem.get("critical");
          if (crit.booleanValue()) {
            query += ",critical = 'Y'";
          }

          query += " where pr_number = " + cPRNum + " and line_item = " + currentLineItem;
          db.doUpdate(query);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      msg = "Error on updating PRI and RLI. Error: " + e.getMessage();
      returnValue[0] = "0";
      returnValue[1] = msg;
      return returnValue;
    }
    Integer temp = new Integer(nx);
    returnValue[0] = new String(temp.toString());
    returnValue[1] = "New request created.";
    return returnValue;
  }

  public String[] getUnitOfSaleAndUnitOfSaleQuantityPerEach(String catalogId, String catalogCompanyId, String catPartNo, String inventoryGroup) {
    String[] result = new String[2];
    result[0] = "";
    result[1] = "";
    String query = "select cpuos.unit_of_sale,cpuos.unit_of_sale_quantity_per_each,cpi.unit_of_sale cpi_unit_of_sale"+
                   " from catalog_part_unit_of_sale cpuos, catalog_part_inventory cpi"+
                   " where cpuos.cat_part_no = '"+catPartNo+"' and cpuos.catalog_id = '"+catalogId+"' and cpuos.catalog_company_id = '"+catalogCompanyId+"'"+
                   " and cpuos.catalog_company_id = cpi.catalog_company_id(+) and cpuos.company_id = cpi.company_id(+) and cpuos.catalog_id = cpi.catalog_id(+)"+
                   " and cpuos.cat_part_no = cpi.cat_part_no(+) and cpuos.unit_of_sale = cpi.unit_of_sale(+)"+
                   " and cpi.inventory_group(+) = '"+inventoryGroup+"' order by cpi.unit_of_sale";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        if (!BothHelpObjs.isBlankString(rs.getString("cpi_unit_of_sale"))) {
          result[0] = BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale"));
          result[1] = BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_quantity_per_each"));
          break;
        }else {
          result[0] = BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale"));
          result[1] = BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_quantity_per_each"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      result[0] = "";
      result[1] = "";
    } finally {
      dbrs.close();
    }

    return result;
  } //end of method

  //4-18-01 this method will handle all purchase from SWA
  public String[] buildRequestForSWA(int userID, String facID, Hashtable items, String accSysId) {
    //System.out.println("\n\n------------- items hash: "+items);
    String msg = new String("");
    String[] returnValue = new String[2];
    int nx = 0;
    try {
      //Location LOC        = new Location(db);
      PurchaseRequest PRI = new PurchaseRequest(db);

      nx = PRI.getNext();
      if (nx == 0) {
        returnValue[0] = "0";
        returnValue[1] = "No PR Produced";
        return returnValue;
      }
      PRI.setPRNumber(nx);
      PRI.setRequestor(userID);
      PRI.setFacilityId(facID);

      //get location
      Facility FAC = new Facility(db);

      FAC.setFacilityId(facID);
      FAC.load();

      //trong 4/8
      PRI.setAccSysId(accSysId);

      PRI.setShipTo(FAC.getShippingLocation());
      PRI.setPRStatus("entered");
      PRI.setRequestDate(new String("nowDate")); // sysdate update
      PRI.setPONumber("");
      PRI.insert();
      PRI.commit();

      Integer lineNum = new Integer(0);
      BigDecimal qty = new BigDecimal("0");
      Integer itemN = new Integer(0);
      Integer itemID = new Integer(0);
      String partID;
      String app;
      String itemType;
      Hashtable lineItem;
      Enumeration itemsE;
      String myPo;
      String notes;
      String currentLpp; //2-15-01,4-28-01
      String needDate; //4-28-01
      for (itemsE = items.keys(); itemsE.hasMoreElements(); ) {
        lineNum = new Integer(lineNum.intValue() + 1);
        lineItem = (Hashtable) items.get(itemsE.nextElement());
        qty = new BigDecimal(lineItem.get("qty").toString());
        itemID = (Integer) lineItem.get("itemID");
        app = (String) lineItem.get("application");
        partID = (String) lineItem.get("partID");
        myPo = (String) lineItem.get("po");
        notes = (String) lineItem.get("notes");
        currentLpp = (String) lineItem.get("current_lpp"); //4-28-01
        needDate = (String) lineItem.get("needDate");
        itemType = Catalog.getItemType(db, partID, facID);
        if (qty.intValue() < 1) {
          msg = "Quantity invalid for item " + itemN.toString();
          returnValue[0] = "0";
          returnValue[1] = msg;
          return returnValue;
        }

        RequestLineItem RLI = new RequestLineItem(db);
        RLI.setPRNumber(nx);
        RLI.setLineItem(lineNum.toString());
        RLI.setApplication(app);
        RLI.setItemId(itemID.intValue());
        RLI.setShipToLocation(PRI.getShipTo());
        RLI.setQuantity(qty);
        RLI.setFacPartNo(partID);
        RLI.setItemType(itemType);
        RLI.setNotes(notes);
        RLI.setPONumber(myPo);
        float qp = Float.parseFloat(currentLpp);
        RLI.setQuotedPrice(qp);
        RLI.setRequiredDatetime(needDate);
        RLI.setRequestedItemType(itemType);

        RLI.insertSWA();
        RLI.commit();
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      msg = "Error on updating PRI and RLI. Error: " + e.getMessage();
      returnValue[0] = "0";
      returnValue[1] = msg;
      return returnValue;
    }
    Integer temp = new Integer(nx);
    returnValue[0] = new String(temp.toString());
    returnValue[1] = "New request created.";
    return returnValue;

  }

  public Hashtable getCreateMRForPOS(String userID) {
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select facility_id,point_of_sale_ig,create_mr from my_fac_pos_ig_view where " +
          " point_of_sale_ig is not null and personnel_id = " + userID + " order by facility_id,point_of_sale_ig";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String currentFacID = rs.getString("facility_id");
        String currentIG = rs.getString("point_of_sale_ig");
        if (result.containsKey(currentFacID)) {
          Vector v = (Vector) result.get(currentFacID);
          v.addElement(currentIG);
        } else {
          Vector v = new Vector();
          v.addElement(currentIG);
          result.put(currentFacID, v);
        }
        result.put(currentFacID + currentIG, rs.getString("create_mr"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
    } finally {
      dbrs.close();
    }
    return result;
  }

  public Hashtable getMyFacWorkAreaList(int userId) {
    //long sTime = new java.util.Date().getTime();
    Hashtable myResult = new Hashtable();
    Hashtable canCreateMRApp = new Hashtable();
    Hashtable facId = new Hashtable();
    Hashtable application = new Hashtable();
    String query = "select * from fac_app_acc_sys_view where personnel_id = " + userId +
        " and display_application = 'Y'";

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

	 query += " order by facility_name,display_application_name,account_sys_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String userName = "";
    String lastFac = "";
    Hashtable innerH = new Hashtable();
    boolean facilityChanged = false;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String currentFac = "";
      while (rs.next()) {
        userName = rs.getString("full_name");
        String facility = rs.getString("facility_id");
        if (BothHelpObjs.isBlankString(currentFac)) {
          currentFac = facility;
        }
        if (!currentFac.equals(facility)) {
          facilityChanged = true;
          currentFac = facility;
        }
        if (facilityChanged) {
          application.put(lastFac, innerH);
          innerH = new Hashtable();
          facilityChanged = false;
        }

        if (!facId.containsKey(facility)) {
          facId.put(facility, facility);
        }

        String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (!innerH.containsKey(app)) {
          innerH.put(app, BothHelpObjs.makeBlankFromNull(rs.getString("display_application_name")));
        }
        lastFac = facility;

        //get work area that I can create MR
        String canCreateMR = rs.getString("generate_order_application");
        if ("Y".equalsIgnoreCase(canCreateMR)) {
          if (canCreateMRApp.containsKey(facility)) {
            Vector tmpV = (Vector) canCreateMRApp.get(facility);
            if (!tmpV.contains(app)) {
              tmpV.addElement(app);
            }
          } else {
            Vector tmpV = new Vector();
            tmpV.addElement(app);
            canCreateMRApp.put(facility, tmpV);
          }
        } //end of can create MR
      } //end of while
      //getting application for last facility
      application.put(lastFac, innerH);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
    } finally {
      dbrs.close();
    }

    myResult.put("application", application);
    myResult.put("userName", userName);
    myResult.put("facility", facId);
    myResult.put("CAN_CREATE_MR_APP", canCreateMRApp);
    /*System.out.println("\n\n ==========  my new structure: "+myResult);
         long eTime = new java.util.Date().getTime();
         long min = (eTime-sTime);
         GregorianCalendar cal = new GregorianCalendar();
         cal.setTime(new java.util.Date(min));
         System.out.println("\n\n---- searchpscreen: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds - "+min);
     */
    return myResult;
  } //end of methed

  //trong 10-9 cutting down the number of queries
  public Hashtable loadAllDataNew(int userId) {
    Hashtable myResult = new Hashtable();
    Hashtable facId = new Hashtable();
    Hashtable application = new Hashtable();
    String query = "select * from fac_app_act_sys_view where personnel_id = " + userId;

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

    query += " order by facility_name,application_desc,account_sys_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String userName = "";
    String lastFac = null;
    Hashtable innerH = new Hashtable();
    boolean facilityChanged = false;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String currentFac = "";
      while (rs.next()) {
        userName = rs.getString("full_name");
        String facility = rs.getString("facility_id");
        if (BothHelpObjs.isBlankString(currentFac)) {
          currentFac = facility;
        }
        if (!currentFac.equals(facility)) {
          facilityChanged = true;
          currentFac = facility;
        }
        if (facilityChanged) {
          application.put(lastFac, innerH);
          innerH = new Hashtable();
          facilityChanged = false;
        }

        if (!facId.containsKey(facility)) {
          facId.put(facility, facility);
        }

        String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        if (!innerH.containsKey(app)) {
          innerH.put(app, BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        }
        lastFac = facility;
      }
      //getting application for last facility
      application.put(lastFac, innerH);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
    } finally {
      dbrs.close();
    }

    myResult.put("application", application);
    myResult.put("userName", userName);
    myResult.put("facility", facId);
    //System.out.println("\n\n ==========  my new structure: "+myResult);
    return myResult;
  }

  public Hashtable loadAllData(int userId) {
    Hashtable result = new Hashtable();
    try {
      Personnel personnel = new Personnel(db);
      personnel.setPersonnelId(userId);
      personnel.load();
      if ( ( (Integer) personnel.getPersonnelId()).intValue() == 0) { // didn't find
        return null;
      }

      result.put("userName", personnel.getFullName());

      //facs
      Hashtable facXref = (Hashtable) personnel.getRelatedFacilities();
      result.put("facility", facXref == null ? new Hashtable() : facXref);
      //application
      Enumeration E;
      Hashtable appXref2D = new Hashtable();
      for (E = facXref.keys(); E.hasMoreElements(); ) {
        String temp = (String) facXref.get(E.nextElement());
        Hashtable appXref = getApplication(temp);
        appXref2D.put(temp, appXref);
      }

      result.put("application", appXref2D);

      //System.out.println("\n\n\n========== " + result);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      return null;
    }
  }

  public Hashtable getApplication(String fac) {
    Hashtable result = new Hashtable();
    try {
      ApplicationView ap = new ApplicationView(db);
      ap.setFacilityId(fac);
      ap.load();
      Hashtable appXref = new Hashtable();
      Hashtable H = ap.getApplications();
      Enumeration E;
      String key;
      String appLabel;
      if (H.size() > 0) {
        for (E = H.keys(); E.hasMoreElements(); ) {
          key = (String) E.nextElement();
          if (key.equalsIgnoreCase("All")) {
            continue;
          }
          appLabel = H.get(key).toString();
          appLabel = ( (appLabel != null && appLabel.length() > 1) ? (key + " - " + appLabel) : key);
          appXref.put(appLabel, key); // to be used by basket table
        }
      }

      return appXref;
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      return null;
    }
  }

  public synchronized Object[][] getTableDataNewChem(int col, String facility, String item) throws Exception {
    Vector cats = new Vector();
    String temp = new String();
    int i;

    try {
      Catalog cat = new Catalog(db);
      cats = cat.retrieveAllCatalogPure(item, facility, null);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;

    }

    Catalog tcat;
    Object[][] data = null;
    data = new Object[cats.size()][col];

    if (cats.size() == 0) {
      return null;
    }

    //catalog view
    int p = 0;
    Hashtable tcatH = null;

    for (i = 0; i < cats.size(); i++) {
      tcat = (Catalog) cats.elementAt(i);
      data[i][0] = tcat.getItemId();
      data[i][2] = tcat.getFacItemId();
      data[i][3] = tcat.getMaterialDesc();
      data[i][4] = tcat.getGrade();
      data[i][5] = tcat.getMfgDesc();

      //Packaging
      temp = new String("");
      Float size = new Float(tcat.getPartSize());
      String pStyle = tcat.getSizeUnit();
      String unit = tcat.getPkgStyle();
      if (size == null || size.toString().equals("0.0")) {
        temp = new String("");
      } else if (size.toString().endsWith(".0")) {
        temp = size.toString().substring(0, size.toString().lastIndexOf("."));
      } else {
        temp = new String(size.toString());
      }
      if (pStyle == null) {
        pStyle = new String("");
      }
      if (unit == null) {
        unit = new String("");
      }
      data[i][6] = new String(temp + " " + pStyle + " " + unit);

      // shelf life
      Float sLife = new Float(tcat.getShelfLife());
      String sLifeUnit = tcat.getShelfLifeUnit();
      if (sLife == null || sLife.toString().equals("0.0")) {
        temp = new String("");
      } else if (sLife.toString().endsWith(".0")) {
        temp = sLife.toString().substring(0, sLife.toString().lastIndexOf("."));
      } else {
        temp = new String(sLife.toString());
      }
      if (sLifeUnit == null) {
        sLifeUnit = new String("");
      } else if ( (sLife == null || sLife.toString().equals("0.0")) &&
                 !sLifeUnit.equals("Indefinite")) {
        sLifeUnit = new String("");
      } else {
        if (temp.equals("1") && sLifeUnit.endsWith("s")) {
          sLifeUnit = sLifeUnit.substring(0, sLifeUnit.lastIndexOf("s"));
        }
        data[i][7] = new String(temp + " " + sLifeUnit);
      }
      p++;
      data[i][1] = tcat.getFacilityId();
      data[i][8] = tcat.getMatId();
    }
    return data;
  }

}
