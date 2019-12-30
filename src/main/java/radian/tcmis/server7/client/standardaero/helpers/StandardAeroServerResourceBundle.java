package radian.tcmis.server7.client.standardaero.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class StandardAeroServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/standardaero"},
          {"DEBUG","false"},
          {"DB_CLIENT","STANDARD_AERO"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/standardaero/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/standardaero/schdelivery?"},
          {"CANCEL_MR","/tcmIS/standardaero/cancel?"},
          {"WASTE_ORDER","/tcmIS/standardaero/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/standardaero/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/standardaero/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/standardaero/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/standardaero/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/standardaero/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/standardaero/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.standardaero.servlets.StandardAeroBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public StandardAeroServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("StandardAero");
     }

}