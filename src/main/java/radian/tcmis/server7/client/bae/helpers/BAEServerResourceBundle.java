package radian.tcmis.server7.client.bae.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/bae"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAE"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/bae/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/bae/schdelivery?"},
          {"CANCEL_MR","/tcmIS/bae/cancel?"},
          {"WASTE_ORDER","/tcmIS/bae/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/bae/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/bae/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/bae/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/bae/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bae/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/bae/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.bae.servlets.BAEBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BAEServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BAE");
     }

}