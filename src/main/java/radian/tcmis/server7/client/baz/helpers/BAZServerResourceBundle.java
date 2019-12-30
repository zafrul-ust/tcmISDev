package radian.tcmis.server7.client.baz.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAZServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/baz"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAZ"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/baz/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/baz/schdelivery?"},
          {"CANCEL_MR","/tcmIS/baz/cancel?"},
          {"WASTE_ORDER","/tcmIS/baz/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/baz/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/baz/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/baz/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/baz/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/baz/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/baz/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.baz.servlets.BAZBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BAZServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BAZ");
     }

}