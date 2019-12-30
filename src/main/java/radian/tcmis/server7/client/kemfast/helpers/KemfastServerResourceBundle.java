package radian.tcmis.server7.client.kemfast.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KemfastServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/kemfast"},
          {"DEBUG","false"},
          {"DB_CLIENT","Kemfast"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/kemfast/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/kemfast/schdelivery?"},
          {"CANCEL_MR","/tcmIS/kemfast/cancel?"},
          {"WASTE_ORDER","/tcmIS/kemfast/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/kemfast/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/kemfast/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/kemfast/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/kemfast/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/kemfast/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/kemfast/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.kemfast.servlets.KemfastBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public KemfastServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Kemfast");
     }

}