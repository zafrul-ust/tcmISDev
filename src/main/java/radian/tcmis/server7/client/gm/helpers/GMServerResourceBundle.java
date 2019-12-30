package radian.tcmis.server7.client.gm.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/gm"},
          {"DEBUG","false"},
          {"DB_CLIENT","GM"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/gm/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/gm/schdelivery?"},
          {"CANCEL_MR","/tcmIS/gm/cancel?"},
          {"WASTE_ORDER","/tcmIS/gm/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/gm/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/gm/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/gm/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/gm/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gm/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/gm/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.gm.servlets.GMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public GMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("GM");
     }

}