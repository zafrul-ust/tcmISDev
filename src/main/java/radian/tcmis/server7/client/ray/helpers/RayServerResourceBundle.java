package radian.tcmis.server7.client.ray.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RayServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ray"},
          {"DEBUG","false"},
          {"DB_CLIENT","Ray"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ray/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ray/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ray/cancel?"},
          {"WASTE_ORDER","/tcmIS/ray/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ray/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ray/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ray/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ray/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ray/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ray/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ray.servlets.RayBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          //{"ERW_REPORT_DIR_ACTIVE","c:\\reports\\"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public RayServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Ray");
     }

}