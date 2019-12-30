package radian.tcmis.server7.client.maeet.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MAEETServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/maeet"},
          {"DEBUG","false"},
          {"DB_CLIENT","MAEET"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/maeet/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/maeet/schdelivery?"},
          {"CANCEL_MR","/tcmIS/maeet/cancel?"},
          {"WASTE_ORDER","/tcmIS/maeet/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/maeet/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/maeet/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/maeet/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/maeet/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/maeet/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/maeet/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.maeet.servlets.MAEETBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MAEETServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("MAEET");
     }

}