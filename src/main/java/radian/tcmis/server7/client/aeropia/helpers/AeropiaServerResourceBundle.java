package radian.tcmis.server7.client.aeropia.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeropiaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/aeropia"},
          {"DEBUG","false"},
          {"DB_CLIENT","Aeropia"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/aeropia/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/aeropia/schdelivery?"},
          {"CANCEL_MR","/tcmIS/aeropia/cancel?"},
          {"WASTE_ORDER","/tcmIS/aeropia/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/aeropia/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/aeropia/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/aeropia/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/aeropia/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/aeropia/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/aeropia/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.aeropia.servlets.AeropiaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AeropiaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Aeropia");
     }

}