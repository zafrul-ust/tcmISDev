package radian.tcmis.server7.client.boeing.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/boeing"},
          {"DEBUG","false"},
          {"DB_CLIENT","Boeing"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ula/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ula/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ula/cancel?"},
          {"WASTE_ORDER","/tcmIS/ula/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ula/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ula/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ula/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ula/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ula/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ula/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.boeing.servlets.BoeingBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BoeingServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Boeing");
     }

}