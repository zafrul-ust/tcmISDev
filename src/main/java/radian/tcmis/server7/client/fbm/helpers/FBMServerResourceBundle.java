package radian.tcmis.server7.client.fbm.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FBMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/fbm"},
          {"DEBUG","false"},
          {"DB_CLIENT","FBM"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/fbm/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/fbm/schdelivery?"},
          {"CANCEL_MR","/tcmIS/fbm/cancel?"},
          {"WASTE_ORDER","/tcmIS/fbm/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/fbm/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/fbm/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/fbm/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/fbm/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fbm/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/fbm/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.fbm.servlets.FBMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public FBMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("FBM");
     }

}