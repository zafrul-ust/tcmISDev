package radian.tcmis.server7.client.tai.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/tai"},
          {"DEBUG","false"},
          {"DB_CLIENT","TAI"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/tai/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/tai/schdelivery?"},
          {"CANCEL_MR","/tcmIS/tai/cancel?"},
          {"WASTE_ORDER","/tcmIS/tai/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/tai/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/tai/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/tai/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/tai/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tai/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/tai/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tai.servlets.TAIBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public TAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("TAI");
     }

}