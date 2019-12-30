package radian.tcmis.server7.client.northrop.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NorthropGrummanServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ngc"},
          {"DEBUG","false"},
          {"DB_CLIENT","NORTHROP_GRUMMAN"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ngc/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ngc/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ngc/cancel?"},
          {"WASTE_ORDER","/tcmIS/ngc/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ngc/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ngc/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ngc/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ngc/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ngc/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ngc/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.northropgrumman.servlets.TAIBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public NorthropGrummanServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("NORTHROP_GRUMMAN");
     }

}