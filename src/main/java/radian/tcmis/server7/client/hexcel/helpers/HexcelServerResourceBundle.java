package radian.tcmis.server7.client.hexcel.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HexcelServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/hexcel"},
          {"DEBUG","false"},
          {"DB_CLIENT","Hexcel"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/hexcel/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/hexcel/schdelivery?"},
          {"CANCEL_MR","/tcmIS/hexcel/cancel?"},
          {"WASTE_ORDER","/tcmIS/hexcel/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/hexcel/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/hexcel/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/hexcel/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/hexcel/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hexcel/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/hexcel/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.hexcel.servlets.HexcelBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public HexcelServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Hexcel");
     }

}