package radian.tcmis.server7.client.swa.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SWAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/swa"},
          {"DEBUG","false"},
          {"DB_CLIENT","SWA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/swa/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/swa/schdelivery?"},
          {"CANCEL_MR","/tcmIS/swa/cancel?"},
          {"WASTE_ORDER","/tcmIS/swa/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/swa/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/swa/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/swa/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/swa/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/swa/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/swa/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.swa.servlets.SWABuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SWAServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("SWA");
     }

}