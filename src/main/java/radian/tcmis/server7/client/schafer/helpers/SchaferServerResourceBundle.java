package radian.tcmis.server7.client.schafer.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SchaferServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/schafer"},
          {"DEBUG","false"},
          {"DB_CLIENT","SCHAFER_GEAR"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/schafer/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/schafer/schdelivery?"},
          {"CANCEL_MR","/tcmIS/schafer/cancel?"},
          {"WASTE_ORDER","/tcmIS/schafer/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/schafer/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/schafer/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/schafer/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/schafer/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/schafer/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/schafer/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.schafer.servlets.SchaferBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SchaferServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("SCHAFER_GEAR");
     }

}