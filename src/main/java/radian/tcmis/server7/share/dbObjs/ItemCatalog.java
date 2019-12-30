package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class ItemCatalog  {

   protected TcmISDBModel db;

   public ItemCatalog()  throws java.rmi.RemoteException {

   }

   public ItemCatalog(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable doSearch(Hashtable searchH)  throws Exception {
      Hashtable result = new Hashtable();
      String searchText = (String)searchH.get("SEARCH_TEXT");
      Vector itemV = new Vector();
      String query = "select a.item_id,a.material_id,a.material_desc,a.packaging,a.grade,a.mfg_desc,a.country_name,a.mfg_part_no,a.shelf_life,a.msds_on_line,a.sample_only";
      String where = "";
      Boolean previousOrderCheck = (Boolean)searchH.get("PREVIOUS_ORDER");
      if (previousOrderCheck.booleanValue()) {
        query += ",'y' eng_eval from item_catalog_view a, catalog_add_request_new b, catalog_add_item cai";
        where += " where a.item_id = cai.item_id and lower(b.engineering_evaluation) = 'y' and b.request_status = 12";
		  where += " and b.company_id = cai.company_id and b.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1";
		  Boolean b = (Boolean)searchH.get("EVAL_REQUESTOR");
        if (b.booleanValue()) {
          where += " and b.requestor = "+(Integer)searchH.get("USER_ID");
        }
        b = (Boolean)searchH.get("EVAL_FACILITY");
        if (b.booleanValue()) {
          where += " and b.eng_eval_facility_id = '"+(String)searchH.get("FACILITY_ID")+"'";
        }
        b = (Boolean)searchH.get("EVAL_WORK_AREA");
        if (b.booleanValue()) {
          String tmp = (String)searchH.get("WORK_AREA_ID");
          if (!BothHelpObjs.isBlankString(tmp) && !tmp.equalsIgnoreCase("All")) {
            where += " and b.eng_eval_work_area = '"+tmp+"'";
          }
        }
      }else {
        query += ",nvl((select 'y' from catalog_add_request_new x, catalog_add_item cai where"+
					  " x.company_id = cai.company_id and x.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and"+
					  " cai.item_id=a.item_id and rownum=1 and lower(x.engineering_evaluation)='y'),'n') eng_eval";
        query += " from item_catalog_view a";
      }

      if (!BothHelpObjs.isBlankString(searchText)) {
        try {
          Catalog cat = new Catalog(db);
          if (BothHelpObjs.isBlankString(where)) {                    //where is an empty string if user doesn't limit search to show eng eval item
            where += " where " + cat.doItemCatSearchLogic(HelpObjs.singleQuoteToDouble(searchText));
          }else {
            where += " and " + cat.doItemCatSearchLogic(HelpObjs.singleQuoteToDouble(searchText));
          }
        }catch (Exception e) {
          e.printStackTrace();
        }
      }
      query += where + " order by item_id";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int rowCount = 0;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        String lastItem = "";
        while (rs.next()) {
          String item = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
          if (!lastItem.equals(item)) {
            //add item to item list
            itemV.addElement(item);
            //list of components
            int count = 1;      //save the first position for item_id
            Vector v = new Vector(5);
            String[] data = new String[13];
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("material_id"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("grade"));
            String sampleSizing = BothHelpObjs.makeBlankFromNull(rs.getString("sample_only"));
            if (sampleSizing.equalsIgnoreCase("Y")) {
              data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"))+" (Sample size)";
            }else {
              data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
            }
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("country_name"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("msds_on_line"));
            data[count++] = sampleSizing;
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("eng_eval"));
            v.addElement(data);
            result.put(item,v);
          }else {
            int count = 1;    //save the first position for item_id
            Vector v = (Vector)result.get(item);
            String[] data = new String[13];
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("material_id"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("grade"));
            String sampleSizing = BothHelpObjs.makeBlankFromNull(rs.getString("sample_only"));
            if (sampleSizing.equalsIgnoreCase("Y")) {
              data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"))+" (Sample size)";
            }else {
              data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
            }
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("country_name"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life"));
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("msds_on_line"));
            data[count++] = sampleSizing;
            data[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("eng_eval"));
            v.addElement(data);
            result.put(item,v);
          }
          lastItem = item;
          rowCount++;
        }
        result.put("ITEM_LIST",itemV);
        result.put("ROW_COUNT",new Integer(rowCount));
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      } finally{
        dbrs.close();
      }
      return result;
   }  //end of method
} //end of class