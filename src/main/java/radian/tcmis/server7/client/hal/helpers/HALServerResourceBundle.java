package radian.tcmis.server7.client.hal.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HALServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/hal"},
          {"DEBUG","false"},
          {"DB_CLIENT","HAL"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/hal/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/hal/schdelivery?"},
          {"CANCEL_MR","/tcmIS/hal/cancel?"},
          {"WASTE_ORDER","/tcmIS/hal/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/hal/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/hal/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/hal/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/hal/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hal/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/hal/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.hal.servlets.HALBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public HALServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("HAL");
     }

}