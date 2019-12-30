package radian.tcmis.server7.client.moog.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MoogServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/tomkins"},
          {"DEBUG","false"},
          {"DB_CLIENT","MOOG"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/moog/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/moog/schdelivery?"},
          {"CANCEL_MR","/tcmIS/moog/cancel?"},
          {"WASTE_ORDER","/tcmIS/moog/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/moog/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/moog/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/moog/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/moog/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/moog/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/moog/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.moog.servlets.MoogBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MoogServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Moog");
     }

}