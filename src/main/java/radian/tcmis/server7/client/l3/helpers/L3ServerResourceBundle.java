package radian.tcmis.server7.client.l3.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3ServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/l3"},
          {"DEBUG","false"},
          {"DB_CLIENT","L3"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/l3/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/l3/schdelivery?"},
          {"CANCEL_MR","/tcmIS/l3/cancel?"},
          {"WASTE_ORDER","/tcmIS/l3/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/l3/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/l3/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/l3/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/l3/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/l3/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/l3/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.l3.servlets.L3BuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public L3ServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("L3");
     }

}