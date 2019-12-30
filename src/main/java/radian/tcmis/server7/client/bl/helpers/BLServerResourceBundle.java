package radian.tcmis.server7.client.bl.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/bl"},
          {"DEBUG","false"},
          {"DB_CLIENT","BL"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/bl/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/bl/schdelivery?"},
          {"CANCEL_MR","/tcmIS/bl/cancel?"},
          {"WASTE_ORDER","/tcmIS/bl/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/bl/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/bl/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/bl/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/bl/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bl/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/bl/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.bl.servlets.BLBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BLServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BL");
     }

}