package radian.tcmis.server7.client.cal.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CALServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/cal"},
          {"DEBUG","false"},
          {"DB_CLIENT","CAL"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/cal/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/cal/schdelivery?"},
          {"CANCEL_MR","/tcmIS/cal/cancel?"},
          {"WASTE_ORDER","/tcmIS/cal/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/cal/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/cal/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/cal/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/cal/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/united/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/cal/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cal.servlets.CALBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public CALServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("CAL");
     }

}