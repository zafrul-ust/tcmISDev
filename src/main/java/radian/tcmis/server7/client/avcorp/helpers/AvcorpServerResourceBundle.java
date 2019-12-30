package radian.tcmis.server7.client.avcorp.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AvcorpServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/avcorp"},
          {"DEBUG","false"},
          {"DB_CLIENT","Avcorp"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/avcorp/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/avcorp/schdelivery?"},
          {"CANCEL_MR","/tcmIS/avcorp/cancel?"},
          {"WASTE_ORDER","/tcmIS/avcorp/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/avcorp/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/avcorp/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/avcorp/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/avcorp/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/avcorp/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/avcorp/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.avcorp.servlets.AvcorpBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AvcorpServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Avcorp");
     }

}