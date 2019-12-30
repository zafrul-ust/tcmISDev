package radian.tcmis.server7.client.dd.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/dd"},
          {"DEBUG","false"},
          {"DB_CLIENT","DD"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/dd/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/dd/schdelivery?"},
          {"CANCEL_MR","/tcmIS/dd/cancel?"},
          {"WASTE_ORDER","/tcmIS/dd/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/dd/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/dd/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/dd/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/dd/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/dd/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/dd/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.dd.servlets.DDBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public DDServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("DD");
     }

}