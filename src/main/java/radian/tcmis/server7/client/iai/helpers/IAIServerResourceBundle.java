package radian.tcmis.server7.client.iai.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/iai"},
          {"DEBUG","false"},
          {"DB_CLIENT","IAI"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/iai/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/iai/schdelivery?"},
          {"CANCEL_MR","/tcmIS/iai/cancel?"},
          {"WASTE_ORDER","/tcmIS/iai/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/iai/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/iai/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/iai/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/iai/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/iai/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/iai/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.iai.servlets.IAIBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public IAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("IAI");
     }

}