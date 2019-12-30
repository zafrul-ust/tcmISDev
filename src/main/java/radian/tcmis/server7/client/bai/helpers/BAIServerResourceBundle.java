package radian.tcmis.server7.client.bai.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAIServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/bai"},
          {"DEBUG","false"},
          {"DB_CLIENT","BAI"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/bai/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/bai/schdelivery?"},
          {"CANCEL_MR","/tcmIS/bai/cancel?"},
          {"WASTE_ORDER","/tcmIS/bai/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/bai/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/bai/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/bai/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/bai/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bai/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/bai/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.bai.servlets.BAIBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BAIServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BAI");
     }

}