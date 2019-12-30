package radian.tcmis.server7.client.doe.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DOEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/doe"},
          {"DEBUG","false"},
          {"DB_CLIENT","DOE"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/doe/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/doe/schdelivery?"},
          {"CANCEL_MR","/tcmIS/doe/cancel?"},
          {"WASTE_ORDER","/tcmIS/doe/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/doe/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/doe/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/doe/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/doe/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/doe/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/doe/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.doe.servlets.DOEBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public DOEServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("DOE");
     }

}