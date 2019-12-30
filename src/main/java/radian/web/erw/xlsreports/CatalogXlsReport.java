package radian.web.erw.xlsreports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.math.BigDecimal;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.SearchPScreen;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 02-20-04 Making the Ctalog Printscreen work with the new procedure
 */

public class CatalogXlsReport
{
    public CatalogXlsReport()
    {

    }

    public Vector getReportData( Hashtable searchContaint,String client, boolean extendedData) throws Exception {
      TcmISDBModel db=null;
      DBResultSet dbrs=null;
      ResultSet searchRs=null;

      db=new RayTcmISDBModel( client );
      Connection connect1=null;
      SearchPScreen searchPScreen=new SearchPScreen();
      searchPScreen.setPrintScreen(extendedData);
      Vector ResultF=new Vector();

      try {
        connect1=db.getConnection();
        connect1.setAutoCommit( false );
        String query=searchPScreen.buildPartSearchQuery( connect1,searchContaint );
        dbrs=db.doQuery( query + " order by CAT_PART_NO,ITEM_ID,PART_ID" );
        searchRs=dbrs.getResultSet();
        Boolean allCatalog = (Boolean)searchContaint.get("ALL_CATALOG");
        while ( searchRs.next() ) {
          Hashtable DataH=new Hashtable();
          //COMMENTS
          DataH.put( "COMMENTS",searchRs.getString( "COMMENTS" ) == null ? "" : searchRs.getString( "COMMENTS" ) );
          //CATALOG_ID
          DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
          //CAT_PART_NO
          DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
          //PART_GROUP_NO
          DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "" : searchRs.getString( "PART_GROUP_NO" ) );
          //INVENTORY_GROUP
          DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
          //PART_GROUP
          DataH.put( "PART_GROUP",searchRs.getString( "PART_GROUP" ) == null ? "" : searchRs.getString( "PART_GROUP" ) );
          //PART_ITEM_GROUP
          DataH.put( "PART_ITEM_GROUP",searchRs.getString( "PART_ITEM_GROUP" ) == null ? "" : searchRs.getString( "PART_ITEM_GROUP" ) );
          //shelf life
          String shelfLife=BothHelpObjs.makeSpaceFromNull( searchRs.getString( "SHELF_LIFE" ) );
          String stTemp=BothHelpObjs.makeBlankFromNull( searchRs.getString( "STORAGE_TEMP" ) );
          if ( BothHelpObjs.isBlankString( stTemp ) ) {
            shelfLife="";
          }else {
            if (!"Indefinite".equalsIgnoreCase(shelfLife)) {
              String tmpB = BothHelpObjs.makeBlankFromNull(searchRs.getString("shelf_life_basis"));
              if (tmpB.length() > 0) {
                shelfLife += " " + tmpB;
              }
            }
            shelfLife+=" @ " + stTemp;
          }
          DataH.put( "SHELF_LIFE",shelfLife );
          //ITEM_ID
          DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
          //BUNDLE
          DataH.put( "BUNDLE",searchRs.getString( "BUNDLE" ) == null ? "" : searchRs.getString( "BUNDLE" ) );
          //currency
          String tmpCurrency = searchRs.getString( "CURRENCY_ID" ) == null ? "" : searchRs.getString( "CURRENCY_ID" );
          //CATALOG_PRICE and unit price
          if (extendedData) {
            DataH.put( "CATALOG_PRICE",searchRs.getString( "CATALOG_PRICE" ) == null ? "" : searchRs.getString( "CATALOG_PRICE" ) );
            DataH.put( "UNIT_PRICE",searchRs.getString( "UNIT_PRICE" ) == null ? "" : searchRs.getString( "UNIT_PRICE" ) );
            DataH.put( "PRICE_GROUP",searchRs.getString( "PRICE_GROUP_ID" ) == null ? "" : searchRs.getString( "PRICE_GROUP_ID" ) );
            DataH.put("CURRENCY",tmpCurrency);
            DataH.put("BASE_LINE_RESET",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("start_date"))));
            DataH.put("BASE_LINE_EXPIRATION",BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("end_date"))));
            DataH.put( "ORDER_QUANTITY",searchRs.getString( "order_quantity" ) == null ? "" : searchRs.getString( "order_quantity" ) );
            DataH.put( "ORDER_QUANTITY_RULE",searchRs.getString( "order_quantity_rule" ) == null ? "" : searchRs.getString( "order_quantity_rule" ) );
            DataH.put( "MEDIAN_MR_LEAD_TIME",searchRs.getString( "median_mr_lead_time" ) == null ? "" : searchRs.getString( "median_mr_lead_time" ) );
            DataH.put( "MEDIAN_SUPPLY_LEAD_TIME",searchRs.getString( "median_supply_lead_time" ) == null ? "" : searchRs.getString( "median_supply_lead_time" ) );
          }else {
            tmpCurrency = " "+tmpCurrency;
            if (allCatalog.booleanValue()) {
              DataH.put( "UNIT_PRICE",searchRs.getString( "MIN_UNIT_PRICE" ) == null ? "" : searchRs.getString( "MIN_UNIT_PRICE" ) );
              String minCatalogPrice = BothHelpObjs.makeBlankFromNull(searchRs.getString("min_catalog_price"));
              String maxCatalogPrice = BothHelpObjs.makeBlankFromNull(searchRs.getString("max_catalog_price"));
              if (BothHelpObjs.isBlankString(minCatalogPrice) || BothHelpObjs.isBlankString(maxCatalogPrice)) {
                if (BothHelpObjs.isBlankString(minCatalogPrice)) {
                  DataH.put("CATALOG_PRICE",maxCatalogPrice+tmpCurrency);
                }else {
                  DataH.put("CATALOG_PRICE",minCatalogPrice+tmpCurrency);
                }
              }else {
                if (minCatalogPrice.equals(maxCatalogPrice)) {
                  BigDecimal minAmt = new BigDecimal(minCatalogPrice);
                  minAmt = minAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
                  DataH.put("CATALOG_PRICE",minAmt.toString()+tmpCurrency);
                }else {
                  BigDecimal minAmt = new BigDecimal(minCatalogPrice);
                  minAmt = minAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
                  BigDecimal maxAmt = new BigDecimal(maxCatalogPrice);
                  maxAmt = maxAmt.setScale(4,BigDecimal.ROUND_HALF_UP);
                  DataH.put("CATALOG_PRICE",minAmt.toString()+"-"+maxAmt.toString()+tmpCurrency);
                }
              }
            }else {
              String minCatalogPrice = BothHelpObjs.makeBlankFromNull(searchRs.getString("min_catalog_price"));
              if (minCatalogPrice.length() > 0) {
                BigDecimal minAmt = new BigDecimal(minCatalogPrice);
                minAmt = minAmt.setScale(4, BigDecimal.ROUND_HALF_UP);
                DataH.put("CATALOG_PRICE", minAmt.toString() + tmpCurrency);
              }else {
                DataH.put("CATALOG_PRICE",minCatalogPrice);
              }
              String minUnitPrice = BothHelpObjs.makeBlankFromNull(searchRs.getString("min_unit_price"));
              if (minUnitPrice.length() > 0) {
                BigDecimal minUnitAmt = new BigDecimal(minUnitPrice);
                minUnitAmt = minUnitAmt.setScale(4, BigDecimal.ROUND_HALF_UP);
                DataH.put("UNIT_PRICE", minUnitAmt.toString() + tmpCurrency);
              }else {
                DataH.put("UNIT_PRICE",minUnitPrice);
              }
            }

            DataH.put( "ORDER_QUANTITY",searchRs.getString( "order_quantity" ) == null ? "" : searchRs.getString( "order_quantity" ) );
            DataH.put( "ORDER_QUANTITY_RULE",searchRs.getString( "order_quantity_rule" ) == null ? "" : searchRs.getString( "order_quantity_rule" ) );
            DataH.put( "MEDIAN_MR_LEAD_TIME",searchRs.getString( "median_mr_lead_time" ) == null ? "" : searchRs.getString( "median_mr_lead_time" ) );
            DataH.put( "MEDIAN_SUPPLY_LEAD_TIME",searchRs.getString( "median_supply_lead_time" ) == null ? "" : searchRs.getString( "median_supply_lead_time" ) );
            //baseline reset and expiration
            String minBaselineReset = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("min_price_start_date")));
            String maxBaselineReset = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("max_price_start_date")));
            String minBaselineExpiration = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("min_price_end_date")));
            String maxBaselineExpiration = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(searchRs.getString("max_price_end_date")));
            String baselineReset = "";
            String baselineExpiration = "";
            //reset date
            if (BothHelpObjs.isBlankString(minBaselineReset) || BothHelpObjs.isBlankString(maxBaselineReset)) {
              if (BothHelpObjs.isBlankString(minBaselineReset)) {
                baselineReset = maxBaselineReset;
              }else {
                baselineReset = minBaselineReset;
              }
            }else {
              if (minBaselineReset.equalsIgnoreCase(maxBaselineReset)) {
                baselineReset = minBaselineReset;
              }else {
                baselineReset = minBaselineReset + "-" + maxBaselineReset;
              }
            }
            DataH.put("BASE_LINE_RESET",BothHelpObjs.formatDate("toNumfromChar",baselineReset));
            //expiration date
            if (BothHelpObjs.isBlankString(minBaselineExpiration) || BothHelpObjs.isBlankString(maxBaselineExpiration)) {
              if (BothHelpObjs.isBlankString(minBaselineExpiration)) {
                baselineExpiration = minBaselineExpiration;
              }else {
                baselineExpiration = minBaselineExpiration;
              }
            }else {
              if (minBaselineExpiration.equalsIgnoreCase(maxBaselineExpiration)) {
                baselineExpiration = minBaselineExpiration;
              }else {
                baselineExpiration = minBaselineExpiration + "-" + maxBaselineExpiration;
              }
            }
            DataH.put("BASE_LINE_EXPIRATION",BothHelpObjs.formatDate("toNumfromChar",baselineExpiration));
          }
          //MIN_BUY
          DataH.put( "MIN_BUY",searchRs.getString( "MIN_BUY" ) == null ? "" : searchRs.getString( "MIN_BUY" ) );
          //PART_DESCRIPTION
          DataH.put( "PART_DESCRIPTION",searchRs.getString( "PART_DESCRIPTION" ) == null ? "" : searchRs.getString( "PART_DESCRIPTION" ) );
          //QUANTITY_PER_BUNDLE
          DataH.put( "QUANTITY_PER_BUNDLE",searchRs.getString( "QUANTITY_PER_BUNDLE" ) == null ? "" : searchRs.getString( "QUANTITY_PER_BUNDLE" ) );
          //PART_ID
          DataH.put( "PART_ID",searchRs.getString( "PART_ID" ) == null ? "" : searchRs.getString( "PART_ID" ) );
          //COMPONENTS_PER_ITEM
          DataH.put( "COMPONENTS_PER_ITEM",searchRs.getString( "COMPONENTS_PER_ITEM" ) == null ? "" : searchRs.getString( "COMPONENTS_PER_ITEM" ) );
          //SIZE_VARIES
          DataH.put( "SIZE_VARIES",searchRs.getString( "SIZE_VARIES" ) == null ? "" : searchRs.getString( "SIZE_VARIES" ) );
          //MATERIAL_DESC
          DataH.put( "MATERIAL_DESC",searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
          //MFG_DESC
          DataH.put( "MFG_DESC",searchRs.getString( "MFG_DESC" ) == null ? "" : searchRs.getString( "MFG_DESC" ) );
          //MATERIAL_ID
          DataH.put( "MATERIAL_ID",searchRs.getString( "MATERIAL_ID" ) == null ? "" : searchRs.getString( "MATERIAL_ID" ) );
          //PACKAGING
          DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" ) );
          //GRADE
          DataH.put( "GRADE",searchRs.getString( "GRADE" ) == null ? "" : searchRs.getString( "GRADE" ) );
          //DIMENSION
          DataH.put( "DIMENSION",searchRs.getString( "DIMENSION" ) == null ? "" : searchRs.getString( "DIMENSION" ) );
          //MFG_PART_NO
          DataH.put( "MFG_PART_NO",searchRs.getString( "MFG_PART_NO" ) == null ? "" : searchRs.getString( "MFG_PART_NO" ) );
          //MSDS_ON_LINE
          DataH.put( "MSDS_ON_LINE",searchRs.getString( "MSDS_ON_LINE" ) == null ? "" : searchRs.getString( "MSDS_ON_LINE" ) );
          //APPROVAL_STATUS
          DataH.put( "APPROVAL_STATUS",searchRs.getString( "APPROVAL_STATUS" ) == null ? "" : searchRs.getString( "APPROVAL_STATUS" ) );
          //use limits
          String lmtqtyperiod1=BothHelpObjs.makeBlankFromNull( searchRs.getString( "LIMIT_QUANTITY_PERIOD1" ) );
          String lmtqtyperiod2=BothHelpObjs.makeBlankFromNull( searchRs.getString( "LIMIT_QUANTITY_PERIOD2" ) );
          String daysperiod1=BothHelpObjs.makeBlankFromNull( searchRs.getString( "DAYS_PERIOD1" ) );
          String daysperiod2=BothHelpObjs.makeBlankFromNull( searchRs.getString( "DAYS_PERIOD2" ) );
          if (lmtqtyperiod1.length() > 0 && daysperiod1.length() > 0) {
            DataH.put("LIMIT_QUANTITY_PERIOD1", "" + lmtqtyperiod1 + " per " + daysperiod1 + "days");
          }else {
            DataH.put("LIMIT_QUANTITY_PERIOD1", "");
          }
          if (lmtqtyperiod2.length() > 0 && daysperiod2.length() > 0) {
            DataH.put("LIMIT_QUANTITY_PERIOD2","" + lmtqtyperiod2 + " per " + daysperiod2 + "days" );
          }else {
            DataH.put("LIMIT_QUANTITY_PERIOD2","");
          }

          //SOURCE_HUB
          DataH.put( "SOURCE_HUB",searchRs.getString( "SOURCE_HUB" ) == null ? "" : searchRs.getString( "SOURCE_HUB" ) );
          //STOCKING_METHOD
          DataH.put( "STOCKING_METHOD",searchRs.getString( "STOCKING_METHOD" ) == null ? "" : searchRs.getString( "STOCKING_METHOD" ) );
          //SPECS
          DataH.put( "SPECS",searchRs.getString( "SPECS" ) == null ? "" : searchRs.getString( "SPECS" ) );

          ResultF.addElement( DataH );
        }
      }catch ( Exception e ){
        e.printStackTrace();
      }finally {
        dbrs.close();
        connect1.setAutoCommit( true );
      }
      return ResultF;
    } //end of method

    public StringBuffer createXls( Hashtable searchContaint,String client, boolean extendedData) throws Exception {
        Vector ResultF = new Vector();
        ResultF = getReportData(searchContaint,client,extendedData);
        Hashtable Final = new Hashtable();
        StringBuffer Msgcx=new StringBuffer();

        if (ResultF.size() > 0) {
          try {
            for(int j=0;j < ResultF.size();j++) {
                String Color = " ";
                if (j%2==0) {
                    Color = "bgcolor=\"#dddddd\"";
                }else {
                    Color = "bgcolor=\"#fcfcfc\"";
                }
                Final = (Hashtable)ResultF.elementAt(j);

                Msgcx.append("<TR>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("CATALOG_ID"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("CAT_PART_NO"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("PART_ID"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("PART_DESCRIPTION"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("SPECS"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("STOCKING_METHOD"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("SHELF_LIFE"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("CATALOG_PRICE"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("BUNDLE"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("ITEM_ID"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("MATERIAL_DESC"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("PACKAGING"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("GRADE"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("MFG_DESC"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("MFG_PART_NO"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("APPROVAL_STATUS"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("MATERIAL_ID"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("LIMIT_QUANTITY_PERIOD1"));Msgcx.append("</TD>\n");
                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");Msgcx.append(Final.get("LIMIT_QUANTITY_PERIOD2"));Msgcx.append("</TD>\n");
                Msgcx.append("</TR>\n");
              }
            }catch(Exception e1) {
              e1.printStackTrace();
              return Msgcx;
            }
        }else {
          Msgcx.append("<TR>\n");
          Msgcx.append("<TD> <B>No Records Found</B> </TD>\n");
          Msgcx.append("<TR>\n");
        }
    return Msgcx;
  } //end of method
} //end of class
