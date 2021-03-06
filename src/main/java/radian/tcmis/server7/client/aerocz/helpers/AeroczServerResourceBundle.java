package radian.tcmis.server7.client.aerocz.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroczServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/aerocz"},
          {"DEBUG","false"},
          {"DB_CLIENT","Aerocz"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/aerocz/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/aerocz/schdelivery?"},
          {"CANCEL_MR","/tcmIS/aerocz/cancel?"},
          {"WASTE_ORDER","/tcmIS/aerocz/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/aerocz/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/aerocz/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/aerocz/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/aerocz/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/aerocz/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/aerocz/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.aerocz.servlets.AeroczBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AeroczServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Aerocz");
     }

}