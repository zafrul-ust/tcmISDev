package radian.tcmis.server7.client.revima.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RevimaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/revima"},
          {"DEBUG","false"},
          {"DB_CLIENT","REVIMA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/revima/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/revima/schdelivery?"},
          {"CANCEL_MR","/tcmIS/revima/cancel?"},
          {"WASTE_ORDER","/tcmIS/revima/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/revima/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/revima/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/revima/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/revima/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/revima/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/revima/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.revima.servlets.RevimaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public RevimaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("REVIMA");
     }

}