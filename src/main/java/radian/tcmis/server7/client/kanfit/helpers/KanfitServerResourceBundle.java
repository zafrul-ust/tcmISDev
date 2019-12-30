package radian.tcmis.server7.client.kanfit.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KanfitServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/kanfit"},
          {"DEBUG","false"},
          {"DB_CLIENT","Kanfit"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/kanfit/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/kanfit/schdelivery?"},
          {"CANCEL_MR","/tcmIS/kanfit/cancel?"},
          {"WASTE_ORDER","/tcmIS/kanfit/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/kanfit/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/kanfit/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/kanfit/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/kanfit/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/kanfit/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/kanfit/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.kanfit.servlets.KanfitBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public KanfitServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Kanfit");
     }

}