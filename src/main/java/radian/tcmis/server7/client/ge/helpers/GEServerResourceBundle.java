package radian.tcmis.server7.client.ge.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ge"},
          {"DEBUG","false"},
          {"DB_CLIENT","GE"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ge/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ge/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ge/cancel?"},
          {"WASTE_ORDER","/tcmIS/ge/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ge/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ge/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ge/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ge/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ge/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ge/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ge.servlets.GEBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public GEServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("GE");
     }

}