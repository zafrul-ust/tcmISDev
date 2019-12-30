package radian.tcmis.server7.client.am.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/am"},
          {"DEBUG","false"},
          {"DB_CLIENT","AM"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/am/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/am/schdelivery?"},
          {"CANCEL_MR","/tcmIS/am/cancel?"},
          {"WASTE_ORDER","/tcmIS/am/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/am/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/am/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/am/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/am/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/am/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/am/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.am.servlets.AMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("AM");
     }

}