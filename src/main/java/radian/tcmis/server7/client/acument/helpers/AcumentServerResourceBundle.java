package radian.tcmis.server7.client.acument.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AcumentServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/acument"},
          {"DEBUG","false"},
          {"DB_CLIENT","ACUMENT"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/acument/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/acument/schdelivery?"},
          {"CANCEL_MR","/tcmIS/acument/cancel?"},
          {"WASTE_ORDER","/tcmIS/acument/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/acument/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/acument/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/acument/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/acument/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/acument/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/acument/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.acument.servlets.AcumentBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AcumentServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("ACUMENT");
     }

}