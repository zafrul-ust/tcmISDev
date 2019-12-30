package radian.tcmis.server7.client.sastech.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SasTechServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/sastech"},
          {"DEBUG","false"},
          {"DB_CLIENT","SAS_TECH"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/sastech/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/sastech/schdelivery?"},
          {"CANCEL_MR","/tcmIS/sastech/cancel?"},
          {"WASTE_ORDER","/tcmIS/sastech/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/sastech/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/sastech/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/sastech/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/sastech/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sastech/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/sastech/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.sastech.servlets.SasTechBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SasTechServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("SAS_TECH");
     }

}