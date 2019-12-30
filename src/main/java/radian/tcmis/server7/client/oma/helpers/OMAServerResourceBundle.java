package radian.tcmis.server7.client.oma.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/oma"},
          {"DEBUG","false"},
          {"DB_CLIENT","OMA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/oma/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/oma/schdelivery?"},
          {"CANCEL_MR","/tcmIS/oma/cancel?"},
          {"WASTE_ORDER","/tcmIS/oma/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/oma/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/oma/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/oma/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/oma/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/oma/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/oma/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.oma.servlets.OMABuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public OMAServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("OMA");
     }

}