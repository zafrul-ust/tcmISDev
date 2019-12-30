package radian.tcmis.server7.client.miller.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MillerServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/miller"},
          {"DEBUG","false"},
          {"DB_CLIENT","miller"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/miller/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/miller/schdelivery?"},
          {"CANCEL_MR","/tcmIS/miller/cancel?"},
          {"WASTE_ORDER","/tcmIS/miller/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/miller/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/miller/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/miller/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/miller/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/miller/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/miller/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.miller.servlets.MillerBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MillerServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Miller");
     }

}