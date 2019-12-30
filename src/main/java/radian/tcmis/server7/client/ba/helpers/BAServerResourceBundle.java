package radian.tcmis.server7.client.ba.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ba"},
          {"DEBUG","false"},
          {"DB_CLIENT","BA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ba/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ba/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ba/cancel?"},
          {"WASTE_ORDER","/tcmIS/ba/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ba/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ba/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ba/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ba/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ba/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ba/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ba.servlets.BABuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BAServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BA");
     }

}