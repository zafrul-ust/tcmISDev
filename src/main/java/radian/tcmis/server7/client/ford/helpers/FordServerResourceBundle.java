package radian.tcmis.server7.client.ford.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FordServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ford"},
          {"DEBUG","false"},
          {"DB_CLIENT","Ford"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ford/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ford/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ford/cancel?"},
          {"WASTE_ORDER","/tcmIS/ford/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ford/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ford/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ford/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ford/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ford/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ford/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ford.servlets.FordBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public FordServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Ford");
     }

}