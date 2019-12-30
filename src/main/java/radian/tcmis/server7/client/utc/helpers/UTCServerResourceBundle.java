package radian.tcmis.server7.client.utc.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class UTCServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/utc"},
          {"DEBUG","false"},
          {"DB_CLIENT","UTC"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/utc/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/utc/schdelivery?"},
          {"CANCEL_MR","/tcmIS/utc/cancel?"},
          {"WASTE_ORDER","/tcmIS/utc/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/utc/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/utc/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/utc/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/utc/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/utc/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/utc/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.utc.servlets.UTCBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public UTCServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("UTC");
     }

}