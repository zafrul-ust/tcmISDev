package radian.tcmis.server7.client.optimetal.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OptimetalServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/optimetal"},
          {"DEBUG","false"},
          {"DB_CLIENT","Optimetal"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/optimetal/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/optimetal/schdelivery?"},
          {"CANCEL_MR","/tcmIS/optimetal/cancel?"},
          {"WASTE_ORDER","/tcmIS/optimetal/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/optimetal/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/optimetal/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/optimetal/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/optimetal/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/optimetal/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/optimetal/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.optimetal.servlets.OptimetalBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public OptimetalServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Optimetal");
     }

}