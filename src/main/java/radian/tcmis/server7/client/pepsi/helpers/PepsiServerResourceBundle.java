package radian.tcmis.server7.client.pepsi.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PepsiServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/pepsi"},
          {"DEBUG","false"},
          {"DB_CLIENT","Pepsi"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/pepsi/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/pepsi/schdelivery?"},
          {"CANCEL_MR","/tcmIS/pepsi/cancel?"},
          {"WASTE_ORDER","/tcmIS/pepsi/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/pepsi/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/pepsi/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/pepsi/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/pepsi/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/pepsi/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/pepsi/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.pepsi.servlets.PepsiBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public PepsiServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Pepsi");
     }

}