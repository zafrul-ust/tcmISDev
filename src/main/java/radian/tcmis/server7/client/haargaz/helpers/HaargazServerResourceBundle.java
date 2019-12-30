package radian.tcmis.server7.client.haargaz.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HaargazServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/haargaz"},
          {"DEBUG","false"},
          {"DB_CLIENT","Haargaz"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/haargaz/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/haargaz/schdelivery?"},
          {"CANCEL_MR","/tcmIS/haargaz/cancel?"},
          {"WASTE_ORDER","/tcmIS/haargaz/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/haargaz/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/haargaz/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/haargaz/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/haargaz/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/haargaz/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/haargaz/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.haargaz.servlets.HaargazBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public HaargazServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Haargaz");
     }

}