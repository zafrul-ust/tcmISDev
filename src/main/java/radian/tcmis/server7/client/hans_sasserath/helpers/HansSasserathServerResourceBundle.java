package radian.tcmis.server7.client.hans_sasserath.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HansSasserathServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/hans_sasserath"},
          {"DEBUG","false"},
          {"DB_CLIENT","HansSasserath"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/hans_sasserath/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/hans_sasserath/schdelivery?"},
          {"CANCEL_MR","/tcmIS/hans_sasserath/cancel?"},
          {"WASTE_ORDER","/tcmIS/hans_sasserath/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/hans_sasserath/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/hans_sasserath/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/hans_sasserath/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/hans_sasserath/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hans_sasserath/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/hans_sasserath/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.hans_sasserath.servlets.HansSasserathBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public HansSasserathServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("HansSasserath");
     }

}