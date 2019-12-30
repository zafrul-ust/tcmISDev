package radian.tcmis.server7.client.gkn.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GKNServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/gkn"},
          {"DEBUG","false"},
          {"DB_CLIENT","GKN"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/gkn/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/gkn/schdelivery?"},
          {"CANCEL_MR","/tcmIS/gkn/cancel?"},
          {"WASTE_ORDER","/tcmIS/gkn/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/gkn/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/gkn/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/gkn/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/gkn/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gkn/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/gkn/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.gkn.servlets.GKNBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public GKNServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("GKN");
     }

}