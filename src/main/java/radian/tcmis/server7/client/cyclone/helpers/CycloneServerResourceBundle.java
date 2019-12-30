package radian.tcmis.server7.client.cyclone.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CycloneServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/cyclone"},
          {"DEBUG","false"},
          {"DB_CLIENT","Cyclone"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/cyclone/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/cyclone/schdelivery?"},
          {"CANCEL_MR","/tcmIS/cyclone/cancel?"},
          {"WASTE_ORDER","/tcmIS/cyclone/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/cyclone/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/cyclone/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/cyclone/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/cyclone/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cyclone/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/cyclone/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cyclone.servlets.CycloneBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public CycloneServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Cyclone");
     }

}