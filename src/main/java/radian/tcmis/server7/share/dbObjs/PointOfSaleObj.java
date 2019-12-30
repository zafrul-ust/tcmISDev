package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import radian.tcmis.server7.share.db.*;
import oracle.jdbc.OracleTypes;

public class PointOfSaleObj  {

   protected TcmISDBModel db;

   public PointOfSaleObj()  throws java.rmi.RemoteException {

   }

   public PointOfSaleObj(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public String goCancelPOSRequest(Hashtable inData) throws Exception {
     String result = "OK";
     String prNumber = "";
     try {
       Integer clerkID = (Integer)inData.get("CLERK");
       String mrLine = (String) inData.get("MR_LINE");
       prNumber = mrLine.substring(0,mrLine.indexOf("-"));
       String lineItem = mrLine.substring(mrLine.indexOf("-")+1);
       String query = "update request_line_item set last_updated = sysdate,last_updated_by = "+clerkID.intValue()+",cancel_status = 'canceled',"+
                      "cancel_authorizer = "+clerkID.intValue()+",cancel_action_date = sysdate,cancel_comment = decode(cancel_comment,null,'',cancel_comment||'; ')||'Set to canceled because clerk close window.',"+
                      "request_line_status = 'Cancelled', category_status = 'Cancelled' where pr_number = "+prNumber;
       db.doUpdate(query);
       //reallocate MR
       try {
         String[] args = new String[1];
         args[0] = prNumber;
         db.doProcedure("p_mr_allocate",args);
       }catch(Exception dbe) {
         HelpObjs.sendMail(db,"p_mr_allocate error (pr_number: "+prNumber+")","POS Error occured while trying to call procedure",86030,false);
         throw dbe;
       }
     }catch (Exception e) {
       HelpObjs.sendMail(db,"Cancel POS MR (pr_number: "+prNumber+")","POS Error occured while trying cancelling MR",86030,false);
       throw e;
     }
     return result;
   } //end of method

   public String goCreateNewBin(Hashtable inData) throws Exception {
     String result = "OK";
     try {
       String inventoryGroup = (String) inData.get("INVENTORY_GROUP");
       String bin = (String) inData.get("BIN");
       if (HelpObjs.countQuery(db,"select count(*) from facility_ig_bin where inventory_group = '"+inventoryGroup+"'"+
                               " and bin = '"+bin+"'") < 1) {
        db.doUpdate("insert into vv_hub_bins(branch_plant,bin)"+
                    " select b.hub,'"+bin+"' bin from inventory_group_definition b"+
                    " where b.inventory_group = '"+inventoryGroup+"'");
       }else {
         result = "The bin that you are trying to create already exist.";
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
     return result;
   } //end of method

   public String goProcessPOSRepack(Hashtable inData) throws Exception {
     String result = "OK";
     Connection connect1 = null;
     CallableStatement cs = null;
     Vector repackData = (Vector) inData.get("REPACK_DATA");
     String itemID = (String) inData.get("ITEM_ID");
     try{
       connect1 = db.getConnection();
       for (int i = 0; i < repackData.size(); i++) {
         Hashtable h = (Hashtable) repackData.elementAt(i);
         cs = connect1.prepareCall("{call P_REPACKAGE(?,?,?,?,?,?,?)}");
         cs.setString(1, (String) h.get("RECEIPT_ID"));
         cs.setString(2, itemID);
         cs.setString(3, (String) h.get("QTY"));
         cs.setString(4, (String) h.get("BIN"));
         cs.setString(5, null);
         cs.registerOutParameter(6, OracleTypes.NUMERIC);
         cs.registerOutParameter(7, OracleTypes.VARCHAR);
         cs.execute();
         String msg = (String) cs.getObject(7);
         //procedure return error message
         if (BothHelpObjs.isBlankString(msg)) {
           result = "OK";
         }else {
           return msg;
         }
       } //end of for loop
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    } finally{
      cs.close();
    }
    return result;
   } //end of method

   public String getHubName(String hub) throws Exception {
      String result = "";
      String query = "select nvl(facility_name,facility_id) facility_name from facility where branch_plant = '"+hub+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          result = rs.getString("facility_name");
        }
      }catch (Exception e) {
        e.printStackTrace();
        throw e;
      }finally {
        dbrs.close();
      }
      return result;
   } //end of method

   public Vector getPOSReceiptData(String prNumber) throws Exception {
      Vector result = new Vector();
      String query = "select  p.last_name||', '||p.first_name requestor_name,"+
                     "p2.last_name||', '||p2.first_name clerk_name,"+
                     "to_char(sysdate, 'mm/dd/yyyy hh24:mi:ss') receipt_date,"+
                     "b.pr_number||'-'||b.line_item mr_line,b.fac_part_no,nvl(b.item_id,b.example_item_id) item_id,"+
                     "to_number(fx_sig_fig(fx_item_convert_to_unit(nvl(b.item_id,b.example_item_id),b.requested_dispensed_size_unit,b.quantity),4))quantity,fla.application_desc"+
                     " from request_line_item b,purchase_request a,personnel p,personnel p2,fac_loc_app fla"+
                     " where a.pr_number = b.pr_number and a.requestor = p.personnel_id and b.application = fla.application"+
                     " and a.facility_id = fla.facility_id and a.pos_user = p2.personnel_id and a.pr_number = "+prNumber+
                     " order by a.pr_number,b.line_item";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          Hashtable h = new Hashtable();
          h.put("REQUESTOR_NAME",rs.getString("requestor_name"));
          h.put("CLERK_NAME",rs.getString("clerk_name"));
          h.put("RECEIPT_DATE",rs.getString("receipt_date"));
          h.put("MR_LINE",rs.getString("mr_line"));
          h.put("WORK_AREA",rs.getString("application_desc"));
          h.put("CAT_PART_NO",rs.getString("fac_part_no"));
          h.put("ITEM_ID",rs.getString("item_id"));
          h.put("QUANTITY",rs.getString("quantity"));
          result.addElement(h);
        }
      }catch (Exception e) {
        e.printStackTrace();
        throw e;
      }finally {
        dbrs.close();
      }
      return result;
   }

   String getActualQtyForMR(String prNumber, String lineItem) {
     String result = "";
     String query = "select quantity from request_line_item where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         result = rs.getString("quantity");
       }
     }catch (Exception e) {
       e.printStackTrace();
       result = "";
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

   void updateRLIQTY(Integer clerkID, String prNumber, String lineItem, String pickedQty) {
     try {
       String query = "";
       float pQty = Float.parseFloat(pickedQty);
       //first check to see if picked qty is zero - cancel
       if (pQty == 0) {
         query = "update request_line_item set quantity = 0,last_updated = sysdate,last_updated_by = "+clerkID.intValue()+",cancel_status = 'canceled',"+
                 "cancel_authorizer = "+clerkID.intValue()+",cancel_action_date = sysdate,cancel_comment = decode(cancel_comment,null,'',cancel_comment||'; ')||'Set to canceled because picked qty is zero',"+
                 "request_line_status = 'Cancelled', category_status = 'Cancelled' where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
       }else {
         query = "update request_line_item set quantity = fx_item_convert_from_unit(nvl(item_id,example_item_id),requested_dispensed_size_unit,"+pQty+"),last_updated = sysdate,last_updated_by = "+clerkID.intValue()+
                 " where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
       }
       db.doUpdate(query);
     }catch (Exception e) {
       e.printStackTrace();
     }
   } //end of method

   String [] getPickedDateNBatchNo() {
     String[] tmp = new String[2];
     String query = "select to_char(sysdate,'dd Mon yyyy hh24:mi') pick_date, print_batch_seq.nextval batch_no from dual";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         tmp[0] = rs.getString("pick_date");
         tmp[1] = rs.getString("batch_no");
       }
     }catch (Exception e) {
       e.printStackTrace();
       tmp[0] = "";
       tmp[1] = "";
     }finally {
       dbrs.close();
     }
     return tmp;
   }

   public String[] goProcessPOSPickData(Hashtable inData) throws Exception {
     String result[] = new String[2];
     result[0] = "An Error occured while processing pick data.";
     Integer clerkID = (Integer)inData.get("CLERK");
     Hashtable pickedDataH = (Hashtable)inData.get("PICKED_DATA");
     String prNumber = "0";
     boolean qtyChanged = false;
     try {
       String pickDate = "";
       String batchNo = "";
       String[] tmp = getPickedDateNBatchNo();
       pickDate = tmp[0];
       batchNo = tmp[1];
       Enumeration enuma = pickedDataH.keys();
       //loop for every line item
       while (enuma.hasMoreElements()) {
         String mrLine = enuma.nextElement().toString();
         prNumber = mrLine.substring(0,mrLine.indexOf("-"));
         String lineItem = mrLine.substring(mrLine.indexOf("-")+1);
         Vector v = (Vector)pickedDataH.get(mrLine);
         //since only the first line contains the order qty and picked qty
         //calculate here
         Hashtable tmpH = (Hashtable)v.firstElement();
         String pickedQty = (String) tmpH.get("PICKED_QTY");
         String orderedQty = (String) tmpH.get("ORDERED_QTY");
			String deliverDate = "";
			if (tmpH.containsKey("DELIVER_DATE")) {
				deliverDate = (String)tmpH.get("DELIVER_DATE");
			}else {
				Calendar cal = Calendar.getInstance();
				deliverDate = (cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR);
			}
			//loop for every receipt ID
         for (int i = 0; i < v.size(); i++) {
           Hashtable innerH = (Hashtable) v.elementAt(i);
           //first compare ordered qty with picked qty
           if (!orderedQty.equalsIgnoreCase(pickedQty)) {
             qtyChanged = true;
             updateRLIQTY(clerkID,prNumber,lineItem,pickedQty);
             if ("0".equalsIgnoreCase(pickedQty)) {
               break;
             }
             orderedQty = pickedQty;
           }
           //ship receipt if user did not pick from this current receipt
           if (BothHelpObjs.isBlankString((String)innerH.get("ACTUAL_QTY")) || "0".equalsIgnoreCase((String)innerH.get("ACTUAL_QTY"))) {
             continue;
           }
           //next call issue procedure
           Connection connect1 = null;
           CallableStatement cs = null;
           try{
             connect1 = db.getConnection();
             cs = connect1.prepareCall("{call P_ISSUE_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
             cs.setInt(1,Integer.parseInt(prNumber));
             cs.setString(2,lineItem);
             cs.setString(3,(String)innerH.get("HUB"));
             cs.setString(4,(String)innerH.get("RECEIPT_ID"));
             String itemID = (String)innerH.get("ITEM_ID");
             itemID = itemID.substring(0,itemID.indexOf(" ("));
             cs.setString(5,itemID);
             cs.setTimestamp(6,radian.web.HTMLHelpObj.getDateFromString( "dd MMM yyyy HH:mm",pickDate));
             cs.setString(7,getUOMQtyForReceipt(prNumber,lineItem,itemID,(String)innerH.get("ACTUAL_QTY")));
             cs.setTimestamp(8,radian.web.HTMLHelpObj.getDateFromString( "MM/dd/yyyy",deliverDate));
				 cs.setString(9,batchNo);
             cs.setString(10,null);
             cs.setString(11,null);
             cs.setInt(12,clerkID.intValue());
             cs.registerOutParameter(13, OracleTypes.NUMERIC);
             cs.registerOutParameter(14, OracleTypes.VARCHAR);
             cs.execute();
             String msg = (String)cs.getObject(14);
             //procedure return error message
             if (!BothHelpObjs.isBlankString(msg)) {
               result[0] = msg;
               return result;
             }else {
               int issueID = cs.getInt(13);   //get this from pervious procedure
               cs = connect1.prepareCall("{call P_ISSUE_SHIP_CONFIRM(?,?,?,?)}");
               cs.setInt(1,issueID);
               cs.setTimestamp(2,radian.web.HTMLHelpObj.getDateFromString( "MM/dd/yyyy",deliverDate));
               cs.setInt(3,clerkID.intValue());
               cs.registerOutParameter(4, OracleTypes.VARCHAR);
               cs.execute();
               String msg2 = (String)cs.getObject(4);
               //System.out.println("------ p_issue_ship_confirm: "+msg2);
               if (!"OK".equalsIgnoreCase(msg2)) {
                 result[0] = msg2;
                 return result;
               }else {
                 result[0] = "OK";
                 result[1] = prNumber;
               }
             }
           }catch(Exception e){
             e.printStackTrace();
             throw e;
           } finally{
             cs.close();
           }
         } //end of each receipt
       } //end of each line item
       //finally reallocate when necessary
       if (qtyChanged) {
         try {
           String[] args = new String[1];
           args[0] = prNumber;
           db.doProcedure("p_mr_allocate",args);
         }catch(Exception dbe) {
           HelpObjs.sendMail(db,"p_mr_allocate error (pr_number: "+prNumber+")","POS Error occured while trying to call procedure",86030,false);
           throw dbe;
         }
       } //end of qty changed
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return result;
   } //end of method

   String getUOMQtyForReceipt(String prNumber, String lineItem, String itemID, String qty) {
     String result = "0";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select fx_item_convert_from_unit("+itemID+",requested_dispensed_size_unit,"+qty+") qty from request_line_item where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         result = rs.getString("qty");
       }
     }catch (Exception e) {
       e.printStackTrace();
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

   public String goPOSCloseReceipt(Hashtable inData) throws Exception {
     String result = "OK";
     Connection connect1 = null;
     CallableStatement cs = null;
     try{
       connect1 = db.getConnection();
       cs = connect1.prepareCall("{call P_TAP_CLOSE(?,?,?)}");
       cs.setString(1,(String)inData.get("RECEIPT_ID"));
       cs.registerOutParameter(2, OracleTypes.NUMERIC);
       cs.registerOutParameter(3, OracleTypes.VARCHAR);
       cs.execute();
       String msg = (String)cs.getObject(3);
       //procedure return error message
       if (!BothHelpObjs.isBlankString(msg)) {
         result = msg;
       }else {
         result ="OK";
       }
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    } finally{
      cs.close();
    }
    return result;
   } //end of method

   public Hashtable goPOSTap(Hashtable inData) throws Exception {
     Hashtable result = new Hashtable(5);
     Connection connect1 = null;
     CallableStatement cs = null;
     try{
       connect1 = db.getConnection();
       cs = connect1.prepareCall("{call P_TAP(?,?,?,?,?)}");
       cs.setString(1,(String)inData.get("RECEIPT_ID"));
       cs.setString(2,(String)inData.get("ITEM_ID"));
       cs.setString(3,(String)inData.get("BIN"));
       cs.registerOutParameter(4, OracleTypes.VARCHAR);
       cs.registerOutParameter(5, OracleTypes.VARCHAR);
       cs.execute();
       String receiptID = (String)cs.getObject(4);
       String msg = (String)cs.getObject(5);
       //procedure return error message
       if (!BothHelpObjs.isBlankString(msg)) {
         result.put("MSG",msg);
       }else {
         result.put("MSG","OK");
         result.put("RECEIPT_ID",receiptID);
         result.put("BIN",(String)inData.get("BIN"));
       }
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    } finally{
      cs.close();
    }
    return result;
   } //end of method

   public Vector goGetBinData(Hashtable inData) throws Exception {
     Vector result = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select distinct bin from facility_ig_bin "+
                      " where status = 'A' and inventory_group = '"+(String)inData.get("INVENTORY_GROUP")+"' order by bin";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
        result.addElement(rs.getString("bin"));
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
     if (result.size() > 1) {
       result.insertElementAt("Select a Bin",0);
     }
     return result;
   }

   public Hashtable goGetPOSTapData(Hashtable inData) throws Exception {
     Hashtable result = new Hashtable();
     Vector dataV = new Vector();
     Vector binV = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //first get source data
       String query = "select receipt_id,bin from tappable_inventory_view where item_id = "+(String)inData.get("ITEM_ID")+" and inventory_group = '"+(String)inData.get("INVENTORY_GROUP")+"' order by receipt_id,bin";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         Object[] oa = new Object[3];
         oa[0] = rs.getString("receipt_id");
         oa[1] = rs.getString("bin");
         dataV.addElement(oa);
       }
       //next get bin info
       query = "select distinct bin from receipt where item_id = "+(String)inData.get("ITEM_ID")+" and inventory_group = '"+(String)inData.get("INVENTORY_GROUP")+"' order by bin";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         binV.addElement(rs.getString("bin"));
       }
       if (binV.size() == 0) {
         binV.addElement("None");
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
     //now put it all together
     result.put("DATA",dataV);
     result.put("BIN",binV);
     return result;
   }

   public Vector goGetPOSRepackData(Hashtable inData) throws Exception {
     Vector result = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select receipt_id,nvl(bin,' ') bin from repackaging_inventory_view where item_id = "+(String)inData.get("ITEM_ID")+
                      " and inventory_group = '"+(String)inData.get("INVENTORY_GROUP")+"'";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         Object[] oa = new Object[4];
         oa[0] = rs.getString("receipt_id");
         oa[1] = rs.getString("bin");
         oa[2] = "";
         oa[3] = "Close";
         result.addElement(oa);
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
     return result;
   }

   public String getPickInfo(Hashtable h) {
     String result = "OK";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //FOR TESTING
       String query = "select pr_number,line_item,pr_number||'-'||line_item mr_line,cat_part_no fac_part_no,part_group_no,nvl(part_description,' ') part_desc,"+
                      "inventory_group,nvl(to_char(catalog_price),' ') catalog_price,nvl(to_char(mr_quantity),' ') ordered_qty,nvl(item_type,' ') item_type,"+
                      "nvl(to_char(item_id),' ') item_id,nvl(packaging,' ') packaging,nvl(to_char(receipt_id),' ') receipt_id,decode(quantity_on_hand,null,'No Stock',decode(bin,null,' ',bin)) bin,"+
                      "nvl(to_char(expire_date,'MM/dd/yyyy'),' ') expire_date ,nvl(to_char(round(quantity_on_hand,2)),' ') qty_on_hand,"+
                      "nvl(hub,' ') hub,nvl(requested_dispensed_size_unit,' ') requested_dispensed_size_unit, nvl(to_char(quantity_allocated),' ') quantity_allocated"+
                      " from point_of_sale_inventory_view where pr_number = "+(String)h.get("PR_NUMBER")+" and facility_id = '"+(String)h.get("FACILITY_ID")+"'"+
                      " order by pr_number,line_item,item_id,receipt_id,bin";

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector v = new Vector();
       while (rs.next()) {
         Hashtable tmpH = new Hashtable();
         tmpH.put("INVENTORY_GROUP",rs.getString("inventory_group"));
         tmpH.put("MR_LINE",rs.getString("mr_line"));
         tmpH.put("LINE",rs.getString("line_item"));
         tmpH.put("CAT_PART_NO",rs.getString("fac_part_no"));
         tmpH.put("PART_GROUP",rs.getString("part_group_no"));
         tmpH.put("PART_DESC",rs.getString("part_desc"));
         tmpH.put("CATALOG_PRICE",rs.getString("catalog_price"));
         String orderedQty = rs.getString("ordered_qty");
         String itemType = rs.getString("item_type");
         tmpH.put("ITEM_TYPE",itemType);
         String itemID = rs.getString("item_id");
         tmpH.put("ITEM_ID",itemID);
         tmpH.put("RID",rs.getString("receipt_id"));
         tmpH.put("BIN",rs.getString("bin"));
         tmpH.put("EXP_DATE",rs.getString("expire_date"));
         String tmpQty = rs.getString("qty_on_hand");
         String tmpAllocatedQty = rs.getString("quantity_allocated");

         //calculate ordered and avaliable qty
         if ("MD".equalsIgnoreCase(itemType) || "MB".equalsIgnoreCase(itemType)) {
           String dispensedSizeUnit = rs.getString("requested_dispensed_size_unit");
           DBResultSet dbrs2 = null;
           ResultSet rs2 = null;
           String availableQty = "";
           String tmpOrderedQty = "";
           try {
             //first convert ordered qty from dispensed UOM to requested dispensed UOM
             String query2 = "select to_number(fx_sig_fig(fx_item_convert_to_unit("+itemID+",'"+dispensedSizeUnit+"',"+orderedQty+"),4)) ordered_qty from dual";
             dbrs2 = db.doQuery(query2);
             rs2 = dbrs2.getResultSet();
             while (rs2.next()){
               tmpOrderedQty = BothHelpObjs.makeBlankFromNull(rs2.getString("ordered_qty"));
             }
             //convert inventory on hand to requested dispensed UOM
             query2 = "select round(fx_item_convert_to_unit("+itemID+",'"+dispensedSizeUnit+"',"+tmpQty+"),2) quantity_available from dual";
             dbrs2 = db.doQuery(query2);
             rs2 = dbrs2.getResultSet();
             while (rs2.next()){
               availableQty = BothHelpObjs.makeBlankFromNull(rs2.getString("quantity_available"));
             }
             //convert allocated qty from dispensed UOM to requested dispensed UOM
             if (tmpAllocatedQty.length() > 0 && !"0".equalsIgnoreCase(tmpAllocatedQty)) {
               query2 = "select to_number(fx_sig_fig(fx_item_convert_to_unit("+itemID+",'"+dispensedSizeUnit+"',"+tmpAllocatedQty+"),4)) allocated_qty from dual";
               dbrs2 = db.doQuery(query2);
               rs2 = dbrs2.getResultSet();
               while (rs2.next()){
                 tmpAllocatedQty = BothHelpObjs.makeBlankFromNull(rs2.getString("allocated_qty"));
               }
             }
           }catch (Exception ee2) {
             ee2.printStackTrace();
             availableQty = "0";
           }finally {
             dbrs2.close();
           }
           tmpH.put("ORDERED_QTY",tmpOrderedQty);
           tmpH.put("QTY_ON_HAND", availableQty);
           tmpH.put("PACKAGING",dispensedSizeUnit);
         }else {
           tmpH.put("ORDERED_QTY",orderedQty);
           tmpH.put("QTY_ON_HAND", tmpQty);
           tmpH.put("PACKAGING",rs.getString("packaging"));
         }

         if (BothHelpObjs.isBlankString(tmpQty)) {
           tmpH.put("ACTUAL_QTY", "0");
         }else {
           if ("0".equalsIgnoreCase(tmpAllocatedQty)) {
             tmpAllocatedQty = "";
           }
           tmpH.put("ACTUAL_QTY", tmpAllocatedQty);
         }
         tmpH.put("PICKED_QTY","");
         tmpH.put("HUB",rs.getString("hub"));
         v.addElement(tmpH);
       }
       h.put("PICK_INFO",v);
		 //cancel MR is no pick data
		 if (v.size() == 0) {
			 result = "tcmIS encountered an error while getting pick info.\nPlease try again.";
			 HelpObjs.sendMail(db,"POS MR (pr_number: "+(String)h.get("PR_NUMBER")+")","POS no data while trying to call point_of_sale_inventory_view",86030,false);
			 cancelPOSRequest((String)h.get("PR_NUMBER"));
		 }
	  } catch (Exception e) {
       e.printStackTrace();
       result = "tcmIS encountered an error while getting pick info.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.";
     } finally{
       dbrs.close();
     }
     return result;
   } //end of method

   Vector getMaterialRequestInfo(String prNumber) {
     Vector result = new Vector();
	  DBResultSet dbrs = null;
     ResultSet rs = null;
	  try {
		  String query = "select pr.company_id,pr.facility_id,pr.account_sys_id,rli.line_item,rli.application,rli.catalog_company_id,rli.catalog_id,rli.fac_part_no"+
							  " from purchase_request pr, request_line_item rli where pr.company_id = rli.company_id and pr.pr_number = rli.pr_number and"+
							  " pr.pr_number = "+prNumber+" order by to_number(line_item)";
		  dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
		  	  Hashtable dataH = new Hashtable();
			  dataH.put("COMPANY_ID",rs.getString("company_id"));
			  dataH.put("FACILITY_ID",rs.getString("facility_id"));
			  dataH.put("ACCOUNT_SYS_ID",rs.getString("account_sys_id"));
			  dataH.put("LINE_ITEM",rs.getString("line_item"));
			  dataH.put("APPLICATION",rs.getString("application"));
			  dataH.put("CATALOG_COMPANY_ID",rs.getString("catalog_company_id"));
			  dataH.put("CATALOG_ID",rs.getString("catalog_id"));
			  dataH.put("FAC_PART_NO",rs.getString("fac_part_no"));
			  result.add(dataH);
		  }
     }catch (Exception e) {
       e.printStackTrace();
     } finally{
	 	 dbrs.close();
	  }
     return result;
   } //end of method

   Vector getDirectedChargeInfo(Hashtable dataH, String chargeType) {
     Vector result = new Vector();
	  DBResultSet dbrs = null;
     ResultSet rs = null;
	  try {
		  String query = "select charge_number_1,charge_number_2,percent,charge_type,po_number,po_line"+
                    			" from table (pkg_directed_charge_util.fx_get_directed_charges('"+(String)dataH.get("COMPANY_ID")+"','"+(String)dataH.get("FACILITY_ID")+"','"+
						  			(String)dataH.get("APPLICATION")+"','','"+(String)dataH.get("ACCOUNT_SYS_ID")+"','"+chargeType+"','Material','"+(String)dataH.get("CATALOG_COMPANY_ID")+"','"+
									(String)dataH.get("CATALOG_ID")+"','"+(String)dataH.get("FAC_PART_NO")+"',1))";
		  dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
			  Hashtable h = new Hashtable(5);
			  h.put("CHARGE_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
			  h.put("ACCT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
			  h.put("ACCT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
			  h.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
			  h.put("PO_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
			  h.put("PO_LINE",BothHelpObjs.makeBlankFromNull(rs.getString("po_line")));
			  result.addElement(h);
		  }
     }catch (Exception e) {
       e.printStackTrace();
     } finally{
	 	 dbrs.close();
	  }
     return result;
   } //end of method		

	boolean insertPRAccountFromDirectedCharge(String prNumber) {
     boolean result = true;
	  String query = "";
	  try {
		  Vector materialRequestInfoV = getMaterialRequestInfo(prNumber);
		  //for each line_item
		  for (int i = 0; i < materialRequestInfoV.size(); i++) {
			  Hashtable dataH = (Hashtable)materialRequestInfoV.elementAt(i);
			  Vector directedChargeInfoV = getDirectedChargeInfo(dataH,"d");
			  if (directedChargeInfoV.size() == 0) {
			     directedChargeInfoV = getDirectedChargeInfo(dataH,"i");
			  }
			  //for each split charge numbers
			  for (int j = 0; j < directedChargeInfoV.size(); j++) {
				  Hashtable h = (Hashtable)directedChargeInfoV.elementAt(j);
				  query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2)"+
				          " values ("+prNumber+","+(String)dataH.get("LINE_ITEM")+",'"+(String)h.get("ACCT_NUM_1")+"',"+(String)h.get("PERCENTAGE")+",'"+
						    (String)h.get("ACCT_NUM_2")+"')";
				  db.doUpdate(query);
				  //update charge_type and po for line_item
				  if (j == 0) {
					  query = "update request_line_item set charge_type = '"+(String)h.get("CHARGE_TYPE")+"'";
					  if (!BothHelpObjs.isBlankString((String)h.get("PO_NUMBER"))) {
							query += ", po_number = '"+(String)h.get("PO_NUMBER")+"'";
					  }
					  if (!BothHelpObjs.isBlankString((String)h.get("PO_LINE"))) {
							query += ", release_number = '"+(String)h.get("PO_LINE")+"'";
					  }
					  query += " where pr_number = "+prNumber+
							     " and line_item = "+(String)dataH.get("LINE_ITEM");
					  db.doUpdate(query);
				  }
			  }
		  }
     }catch (Exception e) {
       e.printStackTrace();
       result = false;
     }
     return result;
   } //end of method

   boolean insertPRAccount(Vector chargeNumberV,String prNumber,int lineItem) {
     boolean result = false;
     try {
       //return true if no charge number
       if (chargeNumberV == null) return true;

       for (int j = 0; j < chargeNumberV.size(); j++) {
         Hashtable cHash = (Hashtable) chargeNumberV.elementAt(j);
         if (!cHash.containsKey("PERCENTAGE") || cHash.get("PERCENTAGE") == null ||
             BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())) {
           continue;
         }
         String ps = (String) cHash.get("PERCENTAGE");
         String chargeNum1 = (String) cHash.get("ACCT_NUM_1");
         String chargeNum2 = "";
         if (!cHash.containsKey("ACCT_NUM_2") || cHash.get("ACCT_NUM_2") == null) {
           chargeNum2 = "";
         }else {
           chargeNum2 = (String) cHash.get("ACCT_NUM_2");
         }
         String query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2)"+
                        " values("+prNumber+",'"+lineItem+"','"+chargeNum1+"',"+ps+",'"+chargeNum2+"')";
         db.doUpdate(query);
       } //end of for each charge number loop
       result = true;
     }catch (Exception e) {
       e.printStackTrace();
       result = false;
     }
     return result;
   } //end of method

   String checkChargeNumber(Hashtable inData, String prNumber) throws Exception {
     String result = "OK";
     String facid = (String)inData.get("FACILITY_ID");
     String accSysId = (String)inData.get("ACCOUNT_SYS_ID");
     Boolean directedCharge = (Boolean)inData.get("DIRECTED_CHARGE");
     try {
       //if not directed charge than check charge number
       if (!directedCharge.booleanValue()) {
         String chargeType = (String)inData.get("CHARGE_TYPE");
         Hashtable pack = (Hashtable)inData.get("PACK");
         Hashtable innerH = (Hashtable)pack.get(accSysId+chargeType);
         String prAccountRequired = (String)innerH.get("PR_ACCOUNT_REQUIRED");
         String poRequired = (String)innerH.get("PO_REQUIRED");
         if ("y".equalsIgnoreCase(prAccountRequired)) {
           String valid1 = (String)innerH.get("VALID_1");
           String valid2 = (String)innerH.get("VALID_2");
           String chargeID1 = (String)innerH.get("CHARGE_ID_1");
           String chargeID2 = (String)innerH.get("CHARGE_ID_2");
           Hashtable chargeNumberH = (Hashtable)inData.get("CHARGE_NUMBER");
           Vector chargeNumberV;
           if ("d".equalsIgnoreCase(chargeType)) {
             chargeNumberV = (Vector)chargeNumberH.get("CHARGE_NUM_DIRECT");
           }else {
             chargeNumberV = (Vector)chargeNumberH.get("CHARGE_NUM_INDIRECT");
           }
           String chargeNumberOkay = "OK";
           if ("y".equalsIgnoreCase(valid1) || "y".equalsIgnoreCase(valid2)) {
             inData.put("ACC_SYS_ID",accSysId);
             //first check to make sure that charge number is okay
             for(int j=0;j<chargeNumberV.size();j++){
              Hashtable cHash = (Hashtable)chargeNumberV.elementAt(j);
              if(!cHash.containsKey("PERCENTAGE") ||
                cHash.get("PERCENTAGE") == null ||
                BothHelpObjs.isBlankString(cHash.get("PERCENTAGE").toString())){
                continue;
              }
              ChargeNumber myCN = new ChargeNumber(db);
              myCN.setChargeType(chargeType);
              myCN.setAccountNumber(cHash.get("ACCT_NUM_1").toString());
              if(!cHash.containsKey("ACCT_NUM_2") ||
                cHash.get("ACCT_NUM_2") == null){
                myCN.setAccountNumber2("");
              }else{
                myCN.setAccountNumber2(cHash.get("ACCT_NUM_2").toString());
              }
              Vector vv = myCN.checkChargeNumberNew(myCN,inData);
              Boolean val1 = (Boolean)vv.elementAt(0);
              Boolean val2 = (Boolean)vv.elementAt(1);
              if (!val1.booleanValue()){
                result =  "Charge number is invalid.";
              }
              if (!val2.booleanValue()){
                result =  "Charge number is invalid.";
              }
             }
           } //end of if charge numbers need validation
         } //end of if pr_account_required
       } //end of directed charge is not used
     }catch (Exception e) {
       e.printStackTrace();
       result = "Error";
       throw e;
     }
     return result;
   }

   public Hashtable buildPOSRequest(Hashtable inData) throws Exception {
     Hashtable result = new Hashtable();
     result.put("MSG","OK");
     Hashtable partItemH = (Hashtable)inData.get("PART_ITEM_DETAIL");
     Integer userID = (Integer)inData.get("USER_ID");
     Integer customerID = (Integer)inData.get("CUSTOMER_ID");
     String facid = (String)inData.get("FACILITY_ID");
     String accSysId = (String)inData.get("ACCOUNT_SYS_ID");
     Boolean directedCharge = (Boolean)inData.get("DIRECTED_CHARGE");
     String[] temp = new String[2];
     String prNumber = "0";
     try {
       SearchPScreen scr = new SearchPScreen(db);
       temp = scr.buildRequest(customerID.intValue(),facid,partItemH,accSysId,false);
       prNumber = temp[0];
       if (!"0".equalsIgnoreCase(prNumber)) {
			 //unit of sale and cost center
			 updateUosAndCostCenter(prNumber);

			//check charge number
         String msg = checkChargeNumber(inData,prNumber);
         if (!"OK".equalsIgnoreCase(msg)) {
           result.put("MSG",msg);
           deletePOSRequest(prNumber);
           return result;
         }
         //insert charge number into pr_account
         if (!directedCharge.booleanValue()) {
           Enumeration itemsE ;
           int lineNum = 1;
           String chargeType = (String)inData.get("CHARGE_TYPE");
           Hashtable chargeNumberH = (Hashtable)inData.get("CHARGE_NUMBER");
           Vector chargeNumberV;
           if ("d".equalsIgnoreCase(chargeType)) {
             chargeNumberV = (Vector) chargeNumberH.get("CHARGE_NUM_DIRECT");
           }else {
             chargeNumberV = (Vector) chargeNumberH.get("CHARGE_NUM_INDIRECT");
           }
           for (itemsE = partItemH.keys();itemsE.hasMoreElements();itemsE.nextElement()) {
             if (!insertPRAccount(chargeNumberV,prNumber,lineNum)) {
               result.put("MSG","tcmIS encountered an error while inserting charge info.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
               deletePOSRequest(prNumber);
               return result;
             }
             lineNum++;
           } //end of for loop
         }else { //end of not directed charge
           if (!insertPRAccountFromDirectedCharge(prNumber)) {
             result.put("MSG","tcmIS encountered an error while inserting charge info.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
             deletePOSRequest(prNumber);
             return result;
           }
			}
         //next update request line item info
         String query = "update request_line_item set release_date = sysdate,last_updated = sysdate,last_updated_by = "+userID+",delivery_type = 'Deliver by'"+
                        ",use_approval_status = 'approved',use_approver = "+customerID+",use_approval_date = sysdate"+
                        ",relax_shelf_life = 'y',request_line_status = 'In Progress',category_status = 'Open',list_approval_status = 'Approved',list_approval_date = sysdate";

         //next determine charge type
         if (!directedCharge.booleanValue()) {
           String tmpVal = (String)inData.get("CHARGE_TPYE");
           if (BothHelpObjs.isBlankString(tmpVal)) {
             query += ",charge_type = 'd'";
           }else {
             query += ",charge_type = '"+tmpVal+"'";
           }
			  tmpVal = (String)inData.get("PO_NUMBER");
			  if (!BothHelpObjs.isBlankString(tmpVal)) {
				  query += ",po_number = '"+tmpVal+"'";
			  }else {
				  query += ",po_number = null";
			  }
			  tmpVal = (String)inData.get("RELEASE_NUMBER");
			  if (BothHelpObjs.isBlankString(tmpVal)) {
				  query += ",release_number = null";
			  }else {
				  query += ",release_number = '"+tmpVal+"'";
			  }
			} //end of user entering po_number and release_number
         query += " where pr_number = "+prNumber;
         db.doUpdate(query);
         //update purchase request
         db.doUpdate("update purchase_request set pr_status = 'posubmit',submitted_date = sysdate,last_updated = sysdate,"+
                     "last_updated_by = "+userID.intValue()+",pos_user = "+userID.intValue()+" where pr_number = "+prNumber);
         //finally allocate MR
         try {
           String[] args = new String[1];
           args[0] = prNumber;
           db.doProcedure("p_mr_allocate",args);
         }catch(Exception dbe) {
           HelpObjs.sendMail(db,"p_mr_allocate error (pr_number: "+prNumber+")","POS Error occured while trying to call procedure",86030,false);
         }
         result.put("PR_NUMBER",prNumber);
         result.put("USER_ID",userID);
         result.put("CUSTOMER_ID",customerID);
         result.put("FACILITY_ID",facid);
       }else {   //else prNumber is not valid
         result.put("MSG","tcmIS encountered an error while submitting (POS) data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
         return result;
       }
     }catch (Exception e) {
       e.printStackTrace();
       deletePOSRequest(prNumber);
       throw e;
     }
     return result;
   } //end of method

	String getUosQtyPerEach(String companyId, String catalogId, String catPartNo, String uos) {
		 DBResultSet dbrs = null;
		 ResultSet rs = null;
		 String result = "";
		 try {
      	String query = "select unit_of_sale_quantity_per_each from catalog_part_unit_of_sale"+
				 				" where company_id = '"+companyId+"' and catalog_id = '"+catalogId+"'"+
				            " and cat_part_no = '"+catPartNo+"' and unit_of_sale = '"+uos+"'";
			dbrs = db.doQuery(query);
      	rs = dbrs.getResultSet();
      	while (rs.next()) {
				result = rs.getString("unit_of_sale_quantity_per_each");
			}
		 }catch (Exception e) {
			e.printStackTrace();
		 }finally {
			dbrs.close();
	 	 }
		 return result;
	}

	void updateUosAndCostCenter(String prNumber) {
		 DBResultSet dbrs = null;
		 ResultSet rs = null;
		 try {
      	String query = "select cpi.company_id,cpi.catalog_id,cpi.cat_part_no,cpi.cost_center,cpi.unit_of_sale,rli.catalog_price,rli.line_item"+
			 					" from catalog_part_inventory cpi,request_line_item rli" +
				 				" where cpi.company_id = rli.company_id and cpi.catalog_id = rli.catalog_id" +
				            " and cpi.cat_part_no = rli.fac_part_no and cpi.part_group_no = rli.part_group_no" +
				 				" and cpi.inventory_group = rli.inventory_group and rli.pr_number = "+prNumber+
				 				" order by to_number(line_item)";
      	dbrs = db.doQuery(query);
      	rs = dbrs.getResultSet();
      	while (rs.next()) {
				String updateString = "";
				String costCenter = rs.getString("cost_center");
				if (!BothHelpObjs.isBlankString(costCenter)) {
					updateString = "cost_center = '"+costCenter+"',";
				}
				//unit of sale
				String uos = rs.getString("unit_of_sale");
				if(!BothHelpObjs.isBlankString(uos)) {
					updateString += "unit_of_sale = '"+uos+"',";
					String uosqpe = getUosQtyPerEach(rs.getString("company_id"),rs.getString("catalog_id"),rs.getString("cat_part_no"),uos);
					if (!BothHelpObjs.isBlankString(uosqpe)) {
						updateString += "unit_of_sale_quantity_per_each = '"+uosqpe+"',";
						String catalogPrice = rs.getString("catalog_price");
						if (!BothHelpObjs.isBlankString(catalogPrice)) {
							float catPrice = new Float(catalogPrice).floatValue();
                    	float uosQtyPerEach = new Float(uosqpe).floatValue();
                     BigDecimal tmp = new BigDecimal(catPrice / uosQtyPerEach);
                     tmp = tmp.setScale(4,tmp.ROUND_HALF_UP);
							updateString += "unit_of_sale_price = "+tmp.floatValue()+",";
						}
					}
				}
				//update rli
				if (updateString.length() > 1) {
					//removing the last commas
					updateString = updateString.substring(0,updateString.length()-1);
					String lineItem = rs.getString("line_item");
					db.doUpdate("update request_line_item set "+updateString+" where pr_number = "+prNumber+" and line_item = '"+lineItem+"'");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbrs.close();
	 	}
	} //end of method

	void cancelPOSRequest(String prNumber) {
		try {
			db.doUpdate("update request_line_item set release_date = null,cancel_status = 'canceled',cancel_authorizer=-1,"+
				         "cancel_action_date = sysdate,cancel_comment='point_of_sale_inventory_view failed to return data',"+ 
				 			"request_line_status='Cancelled',category_status='Cancelled' where pr_number = "+prNumber);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	void deletePOSRequest(String prNumber) {
     try {
       db.doUpdate("delete from pr_account where pr_number = "+prNumber);
       db.doUpdate("delete from request_line_item where pr_number = "+prNumber);
       db.doUpdate("delete from purchase_request where pr_number = "+prNumber);
     }catch (Exception e) {
       e.printStackTrace();
     }
   }

   boolean hasThisChargeNum(Vector ofHash, ChargeNumber cn){
     for(int i=0;i<ofHash.size();i++){
       Hashtable h = (Hashtable)ofHash.elementAt(i);
       //String t = h.get("CHARGE_TYPE").toString();
       //if(!cn.getChargeType().equalsIgnoreCase(t))continue;
       String a1 = h.get("ACCT_NUM_1").toString();

       if(!cn.getAccountNumber().equalsIgnoreCase(a1))continue;
       String a2 = h.get("ACCT_NUM_2").toString();

       if(BothHelpObjs.isBlankString(cn.getAccountNumber2()) &&
          BothHelpObjs.isBlankString(a2)) return true;
       if(cn.getAccountNumber2().equalsIgnoreCase(a2))return true;
     }
     return false;
   }

   void getPreDefineChargeNumber(Hashtable result) throws Exception{
     try {
       // get pre defined charge numbers
       ChargeNumber cn = new ChargeNumber(db);
       Vector cnV = cn.getChargeNumsForFacNew(result, "Material");
       Vector iCnV = new Vector();
       Vector dCnV = new Vector();
       boolean canEditI = false;
       boolean canEditD = false;
       for (int r = 0; r < cnV.size(); r++) {
         ChargeNumber myCN = (ChargeNumber) cnV.elementAt(r);
         Hashtable cnh = new Hashtable();
         cnh.put("ACCT_NUM_1", myCN.getAccountNumber());
         cnh.put("ACCT_NUM_2", myCN.getAccountNumber2());
         //if one charge number then set percentage -> 100%
         if (cnV.size() == 1) {
           cnh.put("PERCENTAGE", "100");
         }
         else {
           cnh.put("PERCENTAGE", "");
         }
         cnh.put("CHARGE_TYPE", myCN.getChargeType());

         if (myCN.getChargeType().equalsIgnoreCase("i")) {
           if (!hasThisChargeNum(iCnV, myCN)) {
             iCnV.addElement(cnh);
           }
         }
         else if (myCN.getChargeType().equalsIgnoreCase("d")) {
           if (!hasThisChargeNum(dCnV, myCN)) {
             dCnV.addElement(cnh);
           }
         }
       }

       //this will handle editing of direct/indirect all combination
       Hashtable tempH = (Hashtable) result.get("ACC_TYPE_H");
       Hashtable tempH2 = (Hashtable) tempH.get( (String) result.get("ACC_SYS_ID"));
       Vector display1 = (Vector) tempH2.get("DISPLAY_1");
       Vector display2 = (Vector) tempH2.get("DISPLAY_2");
       Vector chargeType = (Vector) tempH2.get("CHARGE_TYPE");
       for (int ii = 0; ii < chargeType.size(); ii++) {
         if (chargeType.elementAt(ii).toString().equalsIgnoreCase("i")) {
           if (display1.elementAt(ii).toString().equalsIgnoreCase("y")) {
             canEditI = iCnV.size() == 0;
           }else { //if display is 'n' then allows edit
             canEditI = true;
           }
         }else {
           if (display1.elementAt(ii).toString().equalsIgnoreCase("y")) {
             canEditD = dCnV.size() == 0;
           }else { //if display is 'n' then allows edit
             canEditD = true;
           }
         }
       } //end of for loop
       //PUTTING IT ALL TOGETHER
       result.put("CAN_EDIT_I",new Boolean(canEditI));
       result.put("CAN_EDIT_D",new Boolean(canEditD));
       result.put("CHARGE_NUMBER_I",iCnV);
       result.put("CHARGE_NUMBER_D",dCnV);
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
   } //end of method

  void getPONumber(Hashtable result) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      String query = "select distinct a.po_number, nvl(a.preferred,'N') preferred from fac_account_sys_po a, pr_rules b where a.status = 'A' and a.facility_id = b.facility_id"+
                     " and a.account_sys_id = b.account_sys_id and b.po_required = 'p' and b.facility_id = '"+(String) result.get("FACILITY_ID")+"'"+
                     " and b.account_sys_id = '"+(String) result.get("ACC_SYS_ID")+"'";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String prefPO = "";
      while (rs.next()) {
        String tmp = rs.getString("preferred");
        String tmpPO = rs.getString("po_number");
        if ("Y".equalsIgnoreCase(tmp)) {
          prefPO = tmpPO;
        }
        v.addElement(tmpPO);
      }
      //if there are more than one po then check to see if any is preferred
      //if not then add Select a PO
      if (v.size() > 1) {
        if (prefPO.length() < 1) {
          v.insertElementAt("Select a PO",0);
        }else {
          result.put("PREF_PO",prefPO);
        }
      }else if (v.size() == 0) {
        v.addElement("Select a PO");
      }
      result.put("ALL_PO",v);
    }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }finally {
       dbrs.close();
     }
  } //end of method

   public Hashtable getChargeInfo(Hashtable inData)  throws Exception {
      Hashtable result = new Hashtable();
      String accountSysID = (String) inData.get("ACCOUNT_SYS_ID");
      String facID = (String) inData.get("FACILITY_ID");
      String workAreaID = (String) inData.get("WORK_AREA_ID");
      Integer customerID = (Integer) inData.get("CUSTOMER_ID");
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        //next get point of sale that user is a clerk
        String query = "select distinct application from directed_charge where account_sys_id = '"+accountSysID+"' and facility_id = '"+facID+"'";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        Vector directedChargeWA = new Vector();
        while (rs.next()) {
          directedChargeWA.addElement(rs.getString("application"));
        }
        result.put("DIRECTED_CHARGE_WORK_AREA",directedChargeWA);
        result.put("FACILITY_ID",facID);
        //if the work area does not uses directed charge then
        //get charge number info
        if (!directedChargeWA.contains(workAreaID)) {
          PrRules pr = new PrRules(db);
          pr.setFacilityId(facID);
          pr.setAccountSysId(accountSysID);
          pr.load();
          result.put("FACILITY_ID",facID);
          result.put("ACC_TYPE_H",pr.getAccTypeH());
          result.put("ACC_SYS_ID",accountSysID);
          Hashtable pack = pr.packAccSysType(result);
          result.put("PACK",pack);
          //get pre define charge number
          getPreDefineChargeNumber(result);
          //get PO if required
          getPONumber(result);
        }
        //get info to determine whether the facility and accounting system required
        //finance approver
        query = "select distinct decode(a.cost_limit,null,0,-1,'Unlimited',a.cost_limit) cost_limit,nvl(b.approver_required,'n') approver_required from finance_approver_list a, pr_rules b"+
                " where a.facility_id(+) = b.facility_id and b.approver_required = 'y' and b.status = 'A'"+
                " and b.account_sys_id = '"+accountSysID+"' and b.facility_id = '"+facID+"' and a.personnel_id(+) = "+customerID.intValue();
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        int count = 0;
        while (rs.next()) {
          result.put("CUSTOMER_COST_LIMIT",rs.getString("cost_limit"));
          result.put("APPROVER_REQUIRED",rs.getString("approver_required"));
          count++;
        }
        if (count == 0) {
          result.put("CUSTOMER_COST_LIMIT","0");
          result.put("APPROVER_REQUIRED","n");
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally{
        dbrs.close();
      }
      return result;
   }  //end of method

   public Hashtable getCustomerData(Hashtable inData)  throws Exception {
      Hashtable result = new Hashtable();
      String customerID = (String) inData.get("CUSTOMER_ID");
      String userID = (String) inData.get("USER_ID");
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        //first get customer facilities and work areas
        ServerHelperObj sho = new ServerHelperObj(db);
        String constraint = " from my_workarea_facility_view a, user_group_member_ig b where a.facility_id = b.facility_id and b.personnel_id = "+userID;
        result.put("CUSTOMER_FACILITY_INFO",sho.getFacilityWorkAreaList(new Integer(customerID),"A",constraint));
        //next get point of sale that user is a clerk
        String query = "select distinct inventory_group from user_group_member_ig where user_group_id = 'PointOfSale' and personnel_id = "+userID+
                       " order by inventory_group";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        Vector v = new Vector(30);
        while (rs.next()) {
          v.addElement(rs.getString("inventory_group"));
        }
        result.put("CLERK_POS_INFO",v);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally{
        dbrs.close();
      }
      return result;
   }  //end of method
} //end of class


/*
	boolean insertPRAccountFromDirectedCharge(String prNumber) {
     boolean result = true;
	  String query = "";
	  try {
		  Vector materialRequestInfoV = getMaterialRequestInfo(prNumber);
		  //for each line_item
		  for (int i = 0; i < materialRequestInfoV.size(); i++) {
			  Hashtable dataH = (Hashtable)materialRequestInfoV.elementAt(i);
			  Vector directedChargeInfoV = getDirectedChargeInfo(dataH,"d");
			  if (directedChargeInfoV.size() == 0) {
			     directedChargeInfoV = getDirectedChargeInfo(dataH,"i");
			  }
			  for (int j = 0; j < directedChargeInfoV.size(); j++) {
				  Hashtable h = (Hashtable)directedChargeInfoV.elementAt(j);
				  query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2)"+
				          " values ("+prNumber+","+(String)dataH.get("LINE_ITEM")+",'"+(String)h.get("ACCT_NUM_1")+"',"+(String)h.get("PERCENTAGE")+",'"+
						    (String)h.get("ACCT_NUM_1")+"')";
				  db.doUpdate(query);
				  //update charge_type for line_item
				  if (j == 0) {
					  query = "update request_line_item set charge_type = '"+(String)h.get("CHARGE_TYPE")+"' where pr_number = "+prNumber+
							    " and line_item = "+(String)dataH.get("LINE_ITEM");
					  db.doUpdate(query);
				  }
			  }
		  }


		 //need to replace below with NEW table function
		 //because directed_charge = for eng eval and regular MRs: part -> work area
		 // and for cabinet MRs: part -> work area (cabinet) -> (use work area)
		 //should handle po here too instead of handling in updatePoNumberFromDirectedCharge

		 query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2)"+
                      " select pr_number,line_item,charge_number_1,percent,charge_number_2"+
                      " from pos_directed_charge_view where banned <> 'b' and pr_number = "+prNumber;
       db.doUpdate(query);
       //now update charge type to line item
       query = "update request_line_item x set charge_type = (select distinct y.charge_type"+
               " from pos_directed_charge_view y where y.banned <> 'b' and y.pr_number = "+prNumber+" and"+
               " y.pr_number=x.pr_number and x.line_item=y.line_item) where"+
               " (x.pr_number, x.line_item) in (select pr_number,line_item"+
               " from pos_directed_charge_view where banned <> 'b' and pr_number = "+prNumber+")";
       db.doUpdate(query);
       result = true;
     }catch (Exception e) {
       e.printStackTrace();
       result = false;
     }
     return result;
   } //end of method

boolean updatePoNumberFromDirectedCharge( String facId, String accSysId, String prNumber, int numberOfLineItem) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
	 boolean result = true;
	 String tmpPONumber = "";
	 try {
		//check to make sure account_sys is requiring po_number
		String query = "select count(*) from pr_rules a, purchase_request pr, request_line_item rli"+
						   " where a.company_id = pr.company_id and a.facility_id = pr.facility_id"+
							" and a.account_sys_id = pr.account_sys_id and a.status = 'A' and a.po_required = 'p'"+
							" and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number"+
							" and a.charge_type = rli.charge_type and pr.pr_number = "+prNumber;
		if (HelpObjs.countQuery(db,query) == 0) {
			//return if account system does not required po_number
			return true;
		}

		for (int i = 0; i < numberOfLineItem; i++) {
			//first try to get PO from directed charge
			query = "select fx_cat_part_fac_app_po(catalog_id,fac_part_no,facility_id,application,catalog_company_id) po_number"+
								" from request_line_item where pr_number = "+prNumber+" and line_item = "+(i+1);
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			while (rs.next()) {
			  tmpPONumber = BothHelpObjs.makeBlankFromNull(rs.getString("po_number"));
			}
			//if PO not in catalog_part_directed_charge
			if (tmpPONumber.length() < 1) {
			  //try to get PO from directed charge
			  query = "select po_number from directed_charge where facility_id = '" + facId + "' and account_sys_id = '" + accSysId + "'" +
					" and rownum = 1 and application in (select application from request_line_item where pr_number = "+prNumber+
					" and line_item = "+(i+1)+")";
			  dbrs = db.doQuery(query);
			  rs = dbrs.getResultSet();
			  while (rs.next()) {
				 tmpPONumber = BothHelpObjs.makeBlankFromNull(rs.getString("po_number"));
			  }
			}
			//if PO is catalog_part_directed_charge or directed_charge
			//then user can not chance
			if (tmpPONumber.length() < 1) {
			  //otherwise fac_account_sys_po
			  query = "select po_number from fac_account_sys_po "+
						 "where status = 'A' and facility_id = '" + facId + "' and account_sys_id = '"+accSysId+"'"+
						 " and rownum = 1";
			  dbrs = db.doQuery(query);
			  rs=dbrs.getResultSet();
			  while (rs.next()) {
				 tmpPONumber = rs.getString("po_number");
			  }
			}  //end of not directed PO
			//update po_number
			if (tmpPONumber.length() > 0) {
				query = "update request_line_item set po_number='"+tmpPONumber+"' where pr_number = "+prNumber+
					     " and line_item = "+(i+1);
				db.doUpdate(query);
			}
		} //end of for each line item
	 } catch (Exception e) {
       e.printStackTrace();
		 String[] receiver = {"deverror@haastcm.com"};
		 HelpObjs.sendMail("Point of sale update rli.po_number failed",prNumber,receiver);
	 } finally{
		if (dbrs != null) {
			dbrs.close();
		}
	 }
    return result;
  } //end of method   
 */