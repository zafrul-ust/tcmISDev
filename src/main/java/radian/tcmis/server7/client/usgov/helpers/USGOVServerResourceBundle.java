package radian.tcmis.server7.client.usgov.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class USGOVServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/usgov"},
          {"DEBUG","false"},
          {"DB_CLIENT","USGOV"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/usgov/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/usgov/schdelivery?"},
          {"CANCEL_MR","/tcmIS/usgov/cancel?"},
          {"WASTE_ORDER","/tcmIS/usgov/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/usgov/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/usgov/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/usgov/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/usgov/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/usgov/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/usgov/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.usgov.servlets.USGOVBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public USGOVServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("USGOV");
     }

}